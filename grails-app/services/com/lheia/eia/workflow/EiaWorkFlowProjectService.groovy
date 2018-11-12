package com.lheia.eia.workflow

import com.lheia.eia.common.GeneConstants
import com.lheia.eia.common.HttpMesConstants
import com.lheia.eia.common.HttpUrlConstants
import com.lheia.eia.common.WorkFlowConstants
import com.lheia.eia.contract.EiaContract
import com.lheia.eia.project.EiaProject
import com.lheia.eia.project.EiaProjectPlan
import com.lheia.eia.project.EiaProjectPlanItem
import com.lheia.eia.tools.DateTransTools
import com.lheia.eia.tools.HttpConnectTools
import com.lheia.eia.tools.JsonHandler
import grails.gorm.transactions.Transactional
import org.grails.web.json.JSONArray

@Transactional
class EiaWorkFlowProjectService {
    def eiaWorkFlowWeaverService
    def eiaWorkFlowProjectService
    /***
     * 启动项目工作流
     * @param eiaWorkFlowId流程id
     * @param tableName流程对象表名
     * @param tableNameId流程对象id
     * @return
     */
    def startProjectWorkFlow(String workFlowJson, String tableName, Long tableNameId, session) {
        def eiaWorkFlowBusi = new EiaWorkFlowBusi()
        def eiaProject = EiaProject.findByIfDelAndId(false, tableNameId)
        def workFlowConfigMap = JsonHandler.jsonToMap(workFlowJson)
        def startNode = workFlowConfigMap.workFlowNode[0]
        eiaWorkFlowBusi.eiaWorkFlowId = workFlowConfigMap.eiaWorkFlowId
        eiaWorkFlowBusi.workFlowTitle = eiaProject.projectName
        eiaWorkFlowBusi.workFlowJson = workFlowJson
        eiaWorkFlowBusi.workFlowCode = workFlowConfigMap.workFlowCode
        eiaWorkFlowBusi.workFlowName = workFlowConfigMap.workFlowName
        eiaWorkFlowBusi.eiaWorkFlowNodeId = startNode.eiaWorkFlowNodeId
        eiaWorkFlowBusi.nodesCode = startNode.nodesCode
        eiaWorkFlowBusi.nodesName = startNode.nodesName
        eiaWorkFlowBusi.inputUser = session.staff.staffName
        eiaWorkFlowBusi.inputUserId = Long.valueOf(session.staff.staffId)
        eiaWorkFlowBusi.inputDept = session.staff.orgName
        eiaWorkFlowBusi.inputDeptId = Long.valueOf(session.staff.orgId)
        eiaWorkFlowBusi.inputDeptCode = session.staff.orgCode

        eiaWorkFlowBusi.updateUser = session.staff.staffName
        eiaWorkFlowBusi.updateUserId = Long.valueOf(session.staff.staffId)
        eiaWorkFlowBusi.updateDept = session.staff.orgName
        eiaWorkFlowBusi.updateDeptId = Long.valueOf(session.staff.orgId)
        eiaWorkFlowBusi.updateDeptCode = session.staff.orgCode

        eiaWorkFlowBusi.authType = startNode.nodesAuthType
        if (WorkFlowConstants.AUTH_TYPE_AUTH_CODE.equals(startNode.nodesAuthType)) {
            eiaWorkFlowBusi.authCode = startNode.nodesAuthCode
        } else {
            eiaWorkFlowBusi.authName = startNode.nodeUserName
            eiaWorkFlowBusi.authCode = startNode.nodeUserId
        }
        eiaWorkFlowBusi.tableName = tableName
        eiaWorkFlowBusi.workFlowState = WorkFlowConstants.WORKFLOW_START
        eiaWorkFlowBusi.tableNameId = tableNameId
        eiaWorkFlowBusi.save(flush: true, failOnError: true)
        eiaWorkFlowWeaverService.sendToDoWorkflowMsg(eiaWorkFlowBusi)

        return true
    }

    /**
     * 开启下个节点前的动作（仅更新节点开始时间和节点结束时间，如果需要保存其他内容，则在controller中进行处理）
     * @param nodesNameNext ( 要进入的节点名称 )
     * @param params
     * @return
     */
    def beforeNextNode(String nodesCode, params) {
        def eiaWorkFlowBusiId = params.long('eiaWorkFlowBusiId')
        def eiaWorkFlowBusi = EiaWorkFlowBusi.findByIdAndIfDel(eiaWorkFlowBusiId, false)
        if (Long.valueOf(eiaWorkFlowBusi.version) != Long.valueOf(params.version)) {
            return [code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_PRO_FLOW_PROCESSED]
        } else {
            def actStartDate
            def actEndDate
            def actFinDay
            if (params.actStartDate && params.actEndDate) {
                actStartDate = DateTransTools.getFormatDateD(params.actStartDate)
                actEndDate = DateTransTools.getFormatDateD(params.actEndDate)
                if (actStartDate.getTime() > actEndDate.getTime()) {
                    return [code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MES_DATE_ERROR]
                }
                actFinDay = DateTransTools.getWorkDays(actStartDate, actEndDate) + 1
            }
            def tableName = eiaWorkFlowBusi.tableName
            def tableNameId = eiaWorkFlowBusi.tableNameId
            /**
             * 更新节点的实际开始时间，实际结束时间，实际工作天数。BusiLog表更新提交时间，审核时间
             */
            def nodesCodeLast = params.nodesCode
            //取当前节点
            def eiaProjectPlanId = EiaProjectPlan.findByEiaProjectIdAndIfDel(tableNameId, false).id
            def eiaPlanItemLast = EiaProjectPlanItem.findByEiaProjectPlanIdAndNodesCode(eiaProjectPlanId, nodesCodeLast)
            eiaPlanItemLast.actStartDate = actStartDate
            eiaPlanItemLast.actEndDate = actEndDate
            eiaPlanItemLast.actFinDay = actFinDay
            /**
             *  更新审批意见和修改内容
             */
            /** 如果驳回不更新意见 **/
            if(!params.ifOpinion){
                params.modiContent ? (eiaPlanItemLast.modiContent = params.modiContent) : (eiaPlanItemLast.modiContent = '无')
            }
            eiaPlanItemLast.opinion = params.opinion
            if (params.nodesUsers) {
                JSONArray userNamesList = new JSONArray(params.nodesUsers)
                String userNames = ""
                userNamesList.each {
                    userNames += it.name + "_" + it.id + ","
                }
                eiaPlanItemLast.userNames = userNames.substring(0, userNames.length() - 1)
            }
            eiaPlanItemLast.operateTime = new Date()
            eiaPlanItemLast.save(flush: true, failOnError: true).properties
            if (params.eiaContractId) {
                eiaWorkFlowProjectService.projectContractSave(params)
            }
            /**
             * 更新下一个节点的审批人和相关工作人员
             */
            def eiaPlanItemNext = EiaProjectPlanItem.findByEiaProjectPlanIdAndNodesCodeAndIfDel(eiaProjectPlanId, nodesCode,false)
            if (eiaPlanItemNext) {
                eiaPlanItemNext.eiaProjectId = tableNameId
                if (params.nodeUserName && params.nodeUserId) {
                    eiaPlanItemNext.nodeUserId = params.long('nodeUserId')
                    eiaPlanItemNext.nodeUserName = params.nodeUserName
                    def resJson = HttpConnectTools.getResponseJson(HttpUrlConstants.EIA_GET_ORG_BY_STAFF, [staffId: params.nodeUserId])
                    if (resJson) {
                        def resMap = JsonHandler.jsonToMap(resJson)
                        if (resMap && resMap.code == HttpMesConstants.CODE_OK) {
                            eiaPlanItemNext.nodeDeptId = resMap.data.id
                            eiaPlanItemNext.nodeDept = resMap.data.orgName
                            eiaPlanItemNext.nodeDeptCode = resMap.data.orgCode
                        }
                    }
                }
                /**————提交到一审编制、二审编制和三审编制的动作同时更新下个节点的审批意见——————**/
                /** 如果驳回不更新意见 **/
                if(!params.ifOpinion){
                    if (params.opinion && (nodesCode == WorkFlowConstants.NODE_CODE_YSBZ || nodesCode == WorkFlowConstants.NODE_CODE_ESBZ || nodesCode == WorkFlowConstants.NODE_CODE_SSBZ || nodesCode == WorkFlowConstants.NODE_CODE_LSBZ)) {
                        eiaPlanItemNext.opinion = params.opinion
                    }
                }else{
                    eiaPlanItemLast.nodeUserName= null
                    eiaPlanItemLast.nodeUserId = null
                    eiaPlanItemLast.nodeDept = null
                    eiaPlanItemLast.nodeDeptCode = null
                    eiaPlanItemLast.nodeDeptId = null
                    eiaPlanItemLast.save(flush:true,failOnError:true)
                }
                eiaPlanItemNext.save(flush: true, failOnError: true)
            }else{
                return false
            }
            /**————————更新项目表的ifCert——————————**/
            if (nodesCode == WorkFlowConstants.NODE_CODE_SSBZ || nodesCode == WorkFlowConstants.NODE_CODE_BGZJ) {
                def eiaProject = EiaProject.findByIdAndIfDel(tableNameId, false)
                eiaProject.ifCert = true
                eiaProject.save(flush: true, failOnError: true)
            } else if (nodesCode == WorkFlowConstants.NODE_CODE_END) {
                def eiaProject = EiaProject.findByIdAndIfDel(tableNameId, false)
                eiaProject.ifArc = true
                eiaProject.save(flush: true, failOnError: true)
            } else if (nodesCode == WorkFlowConstants.NODE_CODE_SS) {
                def eiaProject = EiaProject.findByIdAndIfDel(tableNameId, false)
                eiaProject.ifCert = false
                eiaProject.save(flush: true, failOnError: true)
            }

            return eiaWorkFlowBusi.properties
        }
    }

    /**
     *
     * @param eiaWorkFlowBusiId
     * @param nodesCode
     * @param opinion
     * @param processCode
     * @param approvalDate
     * @param session
     * @param nextUserName 下个节点审批人姓名
     * @param nextUserId 下个节点审批人Id
     */
    def nextWorkFlowNode(Long eiaWorkFlowBusiId, String nodesCode, String opinion, String processCode, session, String nextUserName, Long nextUserId) {
        def eiaWorkFlowBusi = EiaWorkFlowBusi.findByIdAndIfDel(eiaWorkFlowBusiId, false)
        def currNodeName = eiaWorkFlowBusi.nodesName
        def workFlowConfigMap = JsonHandler.jsonToMap(eiaWorkFlowBusi.workFlowJson)
        Integer nextNodeNum = workFlowConfigMap.workFlowNodeDic[nodesCode]
        if (nextNodeNum == null) {
            nextNodeNum = -1
        }
        def thisNode = workFlowConfigMap.workFlowNode[workFlowConfigMap.workFlowNodeDic[eiaWorkFlowBusi.nodesCode]]
        eiaWorkFlowBusi.processName = thisNode.nodeProcessMap[processCode].processName
        eiaWorkFlowBusi.eiaWorkFlowNodeProcessId = thisNode.nodeProcessMap[processCode].id
        eiaWorkFlowBusi.processCode = processCode

        eiaWorkFlowBusi.updateUser = session.staff.staffName
        eiaWorkFlowBusi.updateUserId = Long.parseLong(session.staff.staffId)
        eiaWorkFlowBusi.updateDept = session.staff.orgName
        eiaWorkFlowBusi.updateDeptCode = session.staff.orgCode
        eiaWorkFlowBusi.updateDeptId = Long.parseLong(session.staff.orgId)

        /**保存节点流转日志**/
        def eiaWorkFlowBusiLog = new EiaWorkFlowBusiLog()
        eiaWorkFlowBusiLog.properties = eiaWorkFlowBusi.properties
        eiaWorkFlowBusiLog.eiaWorkFlowBusiId = eiaWorkFlowBusi.id
        eiaWorkFlowBusiLog.opinion = opinion
        eiaWorkFlowBusiLog.approvalDate = new Date()
        if (session.staff.staffSignImage != "无") {
            eiaWorkFlowBusiLog.inputUserSign = GeneConstants.AUTH_FILE_URL_PATH + session.staff.staffSignImage
        }
        eiaWorkFlowBusiLog.receiver = nextUserName      //存下个
        eiaWorkFlowBusiLog.receiverId = nextUserId
        eiaWorkFlowBusiLog.save(flush: true, failOnError: true)

        if (nextNodeNum == -1) {//当目标节点索引为零时结束工作流
            eiaWorkFlowBusi.workFlowState = WorkFlowConstants.WORKFLOW_END
        } else {
            def nextNode = workFlowConfigMap.workFlowNode[nextNodeNum]
            eiaWorkFlowBusi.eiaWorkFlowNodeId = nextNode.eiaWorkFlowNodeId
            eiaWorkFlowBusi.nodesCode = nextNode.nodesCode
            eiaWorkFlowBusi.nodesName = nextNode.nodesName
            eiaWorkFlowBusi.authType = nextNode.nodesAuthType
            if (WorkFlowConstants.AUTH_TYPE_AUTH_CODE.equals(nextNode.nodesAuthType)) {
                eiaWorkFlowBusi.authCode = nextNode.nodesAuthCode
            } else {
                /**
                 * 返回节点时没有审批人，从item表取退回节点的审批人
                 */
                if (!nextUserId && !nextUserName) {
                    def eiaProject = EiaProject.findByIdAndIfDel(eiaWorkFlowBusi.tableNameId, false)
                    if (eiaProject) {
                        def eiaProjectPlan = EiaProjectPlan.findByEiaProjectIdAndIfDel(eiaProject.id, false)
                        if (eiaProjectPlan) {
                            def eiaProjectPlanItem = EiaProjectPlanItem.findByEiaProjectPlanIdAndIfDelAndNodesCode(eiaProjectPlan.id, false, nextNode.nodesCode)
                            if (eiaProjectPlanItem) {
                                eiaWorkFlowBusi.authName = eiaProjectPlanItem.nodeUserName
                                eiaWorkFlowBusi.authCode = eiaProjectPlanItem.nodeUserId
                                def resMap = JsonHandler.jsonToMap(HttpConnectTools.getResponseJson(HttpUrlConstants.EIA_GET_ORG_BY_STAFF, [staffId: eiaProjectPlanItem.nodeUserId.toString()]))
                                if (resMap.code == HttpMesConstants.CODE_OK) {
                                    eiaWorkFlowBusi.authDeptId = resMap.data.id
                                    eiaWorkFlowBusi.authDept = resMap.data.orgName
                                    eiaWorkFlowBusi.authDeptCode = resMap.data.orgCode
                                }

                                eiaWorkFlowBusi.save(flush: true, failOnError: true)
                            }
                        }
                    }
                } else {
                    eiaWorkFlowBusi.authName = nextUserName
                    eiaWorkFlowBusi.authCode = nextUserId
                    def resMap = JsonHandler.jsonToMap(HttpConnectTools.getResponseJson(HttpUrlConstants.EIA_GET_ORG_BY_STAFF, [staffId: nextUserId.toString()]))
                    if (resMap.code == HttpMesConstants.CODE_OK) {
                        eiaWorkFlowBusi.authDeptId = resMap.data.id
                        eiaWorkFlowBusi.authDept = resMap.data.orgName
                        eiaWorkFlowBusi.authDeptCode = resMap.data.orgCode
                    }
                }
            }
            eiaWorkFlowBusi.workFlowState = WorkFlowConstants.WORKFLOW_UNDER_WAY
        }
        eiaWorkFlowBusi.save(flush: true, failOnError: true)
        if (nextNodeNum == -1) {//当目标节点索引为零时结束工作流
            eiaWorkFlowWeaverService.sendOverWorkFlowMsg(eiaWorkFlowBusi, currNodeName, '', '', session)
        } else {
              eiaWorkFlowWeaverService.sendDoneWorkFlowMsg(eiaWorkFlowBusi, currNodeName, '', '', session)
              eiaWorkFlowWeaverService.sendToDoWorkflowMsg(eiaWorkFlowBusi)
        }
        return eiaWorkFlowBusi
    }

    /**
     * 获取流程节点的审批意见
     */
    def getNodeOpinion(params) {
        def nodesCode = params.nodesCode    //当前节点
        def processUrlParams = params.processUrlParams //要提交到的节点
        def eiaWorkFlowBusiId = params.long('eiaWorkFlowBusiId')
        def EiaWorkFlowBusi = EiaWorkFlowBusi.findByIdAndIfDel(eiaWorkFlowBusiId, false)
        if (EiaWorkFlowBusi) {
            def tableNameId = EiaWorkFlowBusi.tableNameId
            def tableName = EiaWorkFlowBusi.tableName
            if (tableName == GeneConstants.DOMAIN_EIA_PROJECT) {
                def eiaProjectPlanId = EiaProjectPlan.findByEiaProjectIdAndIfDelAndIfSub(tableNameId, false, true)?.id
                def eiaProPlanItem = EiaProjectPlanItem.findByEiaProjectPlanIdAndNodesNameAndIfDel(eiaProjectPlanId, EiaWorkFlowBusi.nodesName, false)
                if (eiaProPlanItem) {
                    def memo = [:]
                    if(eiaProPlanItem.opinion){
                        memo.opinion = eiaProPlanItem.opinion
                    }
                    else{
                        memo.opinion = "无"
                    }
                    if(eiaProPlanItem.modiContent){
                        memo.modiContent = eiaProPlanItem.modiContent
                    }
                    else {
                        memo.modiContent = ""
                    }
                    return memo
                } else {
                    return false
                }
            }
        } else {
            return false
        }
    }

    /**
     * 流程终止
     */
    def workFlowHalt(Long eiaWorkFlowBusiId, String opinion, long version, session) {
        def eiaWorkFlowBusi = EiaWorkFlowBusi.findByIdAndIfDel(eiaWorkFlowBusiId, false)
        if (Long.valueOf(eiaWorkFlowBusi.version) != version) {
            return [code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_PRO_FLOW_PROCESSED]
        }
        if (eiaWorkFlowBusi) {
            def tableName = eiaWorkFlowBusi?.tableName
            def tableNameId = eiaWorkFlowBusi?.tableNameId
            def currNodeName = eiaWorkFlowBusi.nodesName
            eiaWorkFlowBusi.workFlowState = WorkFlowConstants.WORKFLOW_HALT  /**工作流状态变更为终止**/
            eiaWorkFlowBusi.updateUser = session.staff.staffName
            eiaWorkFlowBusi.updateUserId = Long.parseLong(session.staff.staffId)
            eiaWorkFlowBusi.updateDept = session.staff.orgName
            eiaWorkFlowBusi.updateDeptCode = session.staff.orgCode
            eiaWorkFlowBusi.updateDeptId = Long.parseLong(session.staff.orgId)
            eiaWorkFlowBusi.processName = WorkFlowConstants.NODE_NAME_LCZZ
            eiaWorkFlowBusi.processCode = WorkFlowConstants.NODE_CODE_LCZZ
            eiaWorkFlowBusi.save(flush: true, failOnError: true)
            if (tableName == GeneConstants.DOMAIN_EIA_PROJECT) {
                /**
                 * 把工作方案变成未提交状态
                 */
                def eiaProjectPlan = EiaProjectPlan.findByEiaProjectIdAndIfDelAndIfSub(tableNameId, false, true)
                if (eiaProjectPlan) {
                    eiaProjectPlan.ifSub = false
                    if (eiaProjectPlan.save(flush: true, failOnError: true)) {
                        /**
                         * 删除工作方案下的节点的实际日期及审批意见
                         */
                        def proPlanItemList = EiaProjectPlanItem.findAllByEiaProjectPlanIdAndIfDel(eiaProjectPlan.id, false)
                        for (int i = 0; i < proPlanItemList.size(); i++) {
                            def planItem = proPlanItemList.get(i)
                            planItem.actStartDate = null
                            planItem.actEndDate = null
                            planItem.actFinDay = null
                            planItem.modiContent = null
                            planItem.opinion = null
                            planItem.save(flush: true, failOnError: true)
                        }
                        /**
                         *  记录busiLog
                         */
                        def eiaWorkFlowBusiLog = new EiaWorkFlowBusiLog()
                        eiaWorkFlowBusiLog.eiaWorkFlowBusiId = eiaWorkFlowBusi.id
                        eiaWorkFlowBusiLog.properties = eiaWorkFlowBusi.properties
                        eiaWorkFlowBusiLog.opinion = opinion
                        eiaWorkFlowBusiLog.approvalDate = new Date()
                        eiaWorkFlowBusiLog.save(flush: true, failOnError: true)
                        /**——————调用OA系统——————**/
                        eiaWorkFlowWeaverService.sendOverWorkFlowMsg(eiaWorkFlowBusi, currNodeName, '', '', session)
                        return eiaWorkFlowBusiLog
                    } else {
                        return [code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SUBMIT_FAIL]
                    }
                }
            } else if (tableName == GeneConstants.DOMAIN_EIA_CONTRACT) {
                def eiaContract = EiaContract.findById(tableNameId)
                if (eiaContract) {
                    eiaContract.ifSub = false
                    eiaContract.save(flush: true, failOnError: true)
                }
            } else if (tableName == GeneConstants.DOMAIN_EIA_CERT) {

            } else {
                return [code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SUBMIT_FAIL]
            }
        } else {
            return [code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SUBMIT_FAIL]
        }
    }

    /**
     *  保存项目中合同信息
     */
    def projectContractSave(params) {
        def eiaWorkFlowBusiId = params.long('eiaWorkFlowBusiId')
        def eiaWorkFlowBusi = EiaWorkFlowBusi.findByIdAndIfDel(eiaWorkFlowBusiId, false)
        def eiaContractId = params.long('eiaContractId')
        def eiaContract = EiaContract.findByIdAndIfDel(eiaContractId, false)
        def eiaProject = EiaProject.findById(eiaWorkFlowBusi.tableNameId)
        eiaProject.eiaContractId = eiaContractId
        eiaProject.eiaClientId = eiaContract.eiaClientId
        eiaProject.eiaClientName = eiaContract.eiaClientName
        eiaProject.contractNo = eiaContract.contractNo
        eiaProject.contractName = eiaContract.contractName
        eiaProject.save(flush: true, failOnError: true)
    }
}
