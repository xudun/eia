package com.lheia.eia.workflow

import com.lheia.eia.cert.EiaCert
import com.lheia.eia.common.GeneConstants
import com.lheia.eia.common.HttpMesConstants
import com.lheia.eia.common.HttpUrlConstants
import com.lheia.eia.common.WorkFlowConstants
import com.lheia.eia.contract.EiaContract
import com.lheia.eia.contract.EiaContractLog
import com.lheia.eia.contract.EiaOffer
import com.lheia.eia.lab.EiaLabOffer
import com.lheia.eia.project.EiaProjectExplore
import com.lheia.eia.project.EiaProjectPlan
import com.lheia.eia.project.EiaProjectPlanItem
import com.lheia.eia.stamp.EiaStamp
import com.lheia.eia.tools.DateTransTools
import com.lheia.eia.tools.HttpConnectTools
import com.lheia.eia.tools.JsonHandler
import grails.gorm.transactions.Transactional

@Transactional
class EiaWorkFlowService {
    def eiaWorkFlowProjectService
    def eiaWorkFlowWeaverService
    /**
     * 工作流程判重
     * @param params
     */
    def checkEiaWorkFlow(params) {
        def eiaWorkFlow = EiaWorkFlow.findByWorkFlowVersionAndWorkFlowCodeAndIfDel(Integer.valueOf(params.workFlowVersion), params.workFlowCode, false)
        if (eiaWorkFlow) {
            if (eiaWorkFlow.id != params.long('eiaWorkFlowId')) {
                return HttpMesConstants.MSG_DATA_EXIST
            }
        }
    }
    /**
     * 保存工作流程
     * @param params
     * @return
     */
    def eiaWorkFlowSave(params) {
        def eiaWorkFlow = new EiaWorkFlow(params)
        eiaWorkFlow.save(flush: true, failOnError: true)
    }
    /**
     * 工作流程修改
     * @param params
     */
    def eiaWorkFlowUpdate(params) {
        def eiaWorkFlow = EiaWorkFlow.findById(params.long('eiaWorkFlowId'))
        if (eiaWorkFlow) {
            eiaWorkFlow.properties = params
            eiaWorkFlow.save(flush: true, failOnError: true)
        }
    }
    /**
     * 工作流程详情
     * @param params
     */
    def getEiaWorkFlowDataMap(eiaWorkFlowId) {
        return EiaWorkFlow.findById(eiaWorkFlowId)
    }
    /**
     * 工作流程删除
     * @param params
     */
    def eiaWorkFlowDel(params) {
        def eiaWorkFlowId = params.long('eiaWorkFlowId')
        def eiaWorkFlow = EiaWorkFlow.findById(eiaWorkFlowId)
        if (eiaWorkFlow) {
            eiaWorkFlow.ifDel = true
            if (eiaWorkFlow.save(flush: true, failOnError: true)) {
                /** 删除工作流程下的所有节点 */
                def nodeList = EiaWorkFlowNode.findAllByEiaWorkFlowIdAndIfDel(eiaWorkFlowId, false)
                if (nodeList) {
                    nodeList.each {
                        it.ifDel = true
                        it.save(flush: true, failOnError: true)
                    }
                }
                /** 删除工作流程下的所有节点动作 */
                def processList = EiaWorkFlowNodeProcess.findAllByEiaWorkFlowIdAndIfDel(eiaWorkFlowId, false)
                if (processList) {
                    processList.each {
                        it.ifDel = true
                        it.save(flush: true, failOnError: true)
                    }
                }
                /**
                 * 删除工作流配置
                 */
                def eiaWorkFlowConfig = EiaWorkFlowConfig.findByIdAndIfDel(eiaWorkFlowId, false)
                if (eiaWorkFlowConfig) {
                    eiaWorkFlowConfig.ifDel = true
                    eiaWorkFlowConfig.save(flush: true, failOnError: true)
                }
                return true
            }
        }
    }


    /**
     * 工作流程分页查询
     * @param params
     */
    def eiaWorkFlowQuery(params) {
        int page
        int limit
        if (params.page && params.limit) {
            page = params.int('page') - 1
            limit = params.int('limit')
        }
        def eiaWorkFlowList = EiaWorkFlow.createCriteria().list(max: limit, offset: page * limit,sort: "workFlowVersion", order: "desc") {
            def workFlowName = params.workFlowName
            if (workFlowName) {
                like("workFlowName", "%" + workFlowName + "%")
            }
            eq("ifDel", false)
        }
        def data = []
        eiaWorkFlowList.each {
            def map = [:]
            map.id = it.id
            map.workFlowName = it?.workFlowName
            map.workFlowCode = it?.workFlowCode
            map.workFlowVersion = it.workFlowVersion
            data << map
        }
        def dataMap = [:]
        dataMap.data = data
        dataMap.total = eiaWorkFlowList.totalCount
        return dataMap
    }
    /**
     * 查找所有流程
     * @param params
     * @return
     */
    def getEiaWorkFlowDataList(params) {
        return EiaWorkFlow.findAllByIfDel(false)
    }
    /**
     * 根据用户id获取签名图片
     * @param userId
     * @return
     */
    def getReport(long userId) {
        def param = [:]
        param.staffId = (userId).toString()
        def staffJson = HttpConnectTools.getResponseJson(HttpUrlConstants.SINGLE_EIA_AUTH_STAFF_INFO, param)
        def signImagePath
        if (staffJson) {
            def staff = JsonHandler.jsonToMap(staffJson).data[0]
            if (staff.signImagePath) {
                signImagePath = GeneConstants.AUTH_FILE_URL_PATH + staff.signImagePath
            }
        }
        return signImagePath
    }
    /***
     * 根据处理开启下个节点
     * @param eiaWorkFlowBusiId 当前业务流程id
     * @param nextNodeNum 下个节点的索引
     * @param opinion 审批意见
     * @param processCode
     * @return
     */
    def nextWorkFlowNode(Long eiaWorkFlowBusiId, String nodesCode, String opinion, String processCode, String approvalDate,Long version, session) {
        def eiaWorkFlowBusi = EiaWorkFlowBusi.findByIdAndIfDel(eiaWorkFlowBusiId, false)
        def currNodeName = eiaWorkFlowBusi.nodesName
        def currAuthCode = eiaWorkFlowBusi.authCode
        def currAuthType = eiaWorkFlowBusi.authType
        if(Long.valueOf(eiaWorkFlowBusi.version)!=version){
            return [code:HttpMesConstants.CODE_FAIL,msg:HttpMesConstants.MSG_PRO_FLOW_PROCESSED]
        }
        def workFlowConfigMap = JsonHandler.jsonToMap(eiaWorkFlowBusi.workFlowJson)
        println(nodesCode)
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
        if(approvalDate){
            eiaWorkFlowBusiLog.approvalDate = DateTransTools.getFormatDateD(approvalDate)
        }
        if(session.staff.staffSignImage!="无"){
            eiaWorkFlowBusiLog.inputUserSign = GeneConstants.AUTH_FILE_URL_PATH + session.staff.staffSignImage
        }
        eiaWorkFlowBusiLog.save(flush: true, failOnError: true)

        if (nextNodeNum == -1) {//当目标节点索引为零时结束工作流
            eiaWorkFlowBusi.workFlowState = WorkFlowConstants.WORKFLOW_END
            if(eiaWorkFlowBusi.tableName == GeneConstants.DOMAIN_EIA_CONTRACT){
                def eiaContract = EiaContract.findById(eiaWorkFlowBusi.tableNameId)
                eiaContract.ifSign = true
                eiaContract.save(flush: true, failOnError: true)
            }
        } else {
            def nextNode = workFlowConfigMap.workFlowNode[nextNodeNum]
            eiaWorkFlowBusi.eiaWorkFlowNodeId = nextNode.eiaWorkFlowNodeId
            eiaWorkFlowBusi.nodesCode = nextNode.nodesCode
            eiaWorkFlowBusi.nodesName = nextNode.nodesName
            eiaWorkFlowBusi.authType = nextNode.nodesAuthType
            if (WorkFlowConstants.AUTH_TYPE_AUTH_CODE.equals(nextNode.nodesAuthType)) {
                eiaWorkFlowBusi.authCode = nextNode.nodesAuthCode
            } else {
                eiaWorkFlowBusi.authName = nextNode.nodeUserName
                eiaWorkFlowBusi.authCode = nextNode.nodeUserId
            }
            eiaWorkFlowBusi.workFlowState = WorkFlowConstants.WORKFLOW_UNDER_WAY
        }
        /**——————————OA系统发送消息————————————**/
        if (nextNodeNum == -1) {//当目标节点索引为零时结束工作流
            eiaWorkFlowWeaverService.sendOverWorkFlowMsg(eiaWorkFlowBusi,currNodeName,currAuthCode,currAuthType,session)
        } else {
            eiaWorkFlowWeaverService.sendDoneWorkFlowMsg(eiaWorkFlowBusi,currNodeName,currAuthCode,currAuthType,session)

            eiaWorkFlowWeaverService.sendToDoWorkflowMsg(eiaWorkFlowBusi)
        }
        return [code:HttpMesConstants.CODE_OK,msg:HttpMesConstants.MSG_SUBMIT_OK,data:eiaWorkFlowBusi.save(flush: true, failOnError: true)]
    }

    /**
     * 流程终止
     */
    def workFlowHalt(Long eiaWorkFlowBusiId, String opinion, String approvalDate,long version,session) {
        def eiaWorkFlowBusi = EiaWorkFlowBusi.findByIdAndIfDel(eiaWorkFlowBusiId, false)
        if(eiaWorkFlowBusi.version != version){
            return [code:HttpMesConstants.CODE_FAIL,msg:HttpMesConstants.MSG_PRO_FLOW_PROCESSED]
        }
        if (eiaWorkFlowBusi) {
            def currNodeName = eiaWorkFlowBusi.nodesName
            def currAuthCode = eiaWorkFlowBusi.authCode
            def currAuthType = eiaWorkFlowBusi.authType
            def tableName = eiaWorkFlowBusi?.tableName
            def tableNameId = eiaWorkFlowBusi?.tableNameId
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
                        eiaWorkFlowBusiLog.approvalDate = DateTransTools.getFormatDateD(approvalDate)
                        eiaWorkFlowBusiLog.save(flush: true, failOnError: true)
                        eiaWorkFlowWeaverService.sendOverWorkFlowMsg(eiaWorkFlowBusi,currNodeName,currAuthCode,currAuthType,session)
                        return eiaWorkFlowBusiLog
                    } else {
                        return [code:HttpMesConstants.CODE_FAIL,msg:HttpMesConstants.MSG_SUBMIT_FAIL]
                    }
                }
            } else if (tableName == GeneConstants.DOMAIN_EIA_CONTRACT) {
                def eiaContract = EiaContract.findById(tableNameId)
                if(eiaContract){
                    eiaContract.ifSub = false
                    eiaWorkFlowWeaverService.sendOverWorkFlowMsg(eiaWorkFlowBusi,currNodeName,currAuthCode,currAuthType,session)
                    eiaContract.save(flush: true, failOnError: true)
                }
            } else if(tableName == GeneConstants.DOMAIN_EIA_CERT){
               def eiaCert = EiaCert.findById(tableNameId)
                if(eiaCert){
                    eiaCert.ifSub =false
                    eiaWorkFlowWeaverService.sendOverWorkFlowMsg(eiaWorkFlowBusi,currNodeName,currAuthCode,currAuthType,session)
                    eiaCert.save(flush: true, failOnError: true)
                }
            }else if(tableName == GeneConstants.DOMAIN_EIA_CONTRACT_LOG){
                def eiaContractLog = EiaContractLog.findByIdAndIfDel(tableNameId,false)
                if(eiaContractLog){
                    eiaContractLog.ifSub = false
                    eiaWorkFlowWeaverService.sendOverWorkFlowMsg(eiaWorkFlowBusi,currNodeName,currAuthCode,currAuthType,session)
                    eiaContractLog.save(flush: true, failOnError: true)
                }
            } else if (tableName == GeneConstants.DOMAIN_EIA_OFFER) {
                def eiaOffer = EiaOffer.findByIdAndIfDel(tableNameId, false)
                if (eiaOffer) {
                    eiaOffer.ifSub = GeneConstants.OFFER_ORIGINAL_STATE
                    eiaWorkFlowWeaverService.sendOverWorkFlowMsg(eiaWorkFlowBusi,currNodeName,currAuthCode,currAuthType,session)
                    eiaOffer.save(flush: true, failOnError: true)
                }
            }  else if (tableName == GeneConstants.DOMAIN_EIA_LAB_OFFER) {
                def eiaLabOffer = EiaLabOffer.findByIdAndIfDel(tableNameId, false)
                if (eiaLabOffer) {
                    eiaLabOffer.pushState = GeneConstants.TAIZE_CREATE_INNER_OFFER
                    eiaWorkFlowWeaverService.sendOverWorkFlowMsg(eiaWorkFlowBusi,currNodeName,currAuthCode,currAuthType,session)
                    eiaLabOffer.save(flush: true, failOnError: true)
                }
            }else if(tableName == GeneConstants.DOMAIN_EIA_STAMP){
                def eiaStamp = EiaStamp.findByIdAndIfDel(tableNameId,false)
                if(eiaStamp){
                    eiaStamp.ifSub = false
                    eiaWorkFlowWeaverService.sendOverWorkFlowMsg(eiaWorkFlowBusi,currNodeName,currAuthCode,currAuthType,session)
                    eiaStamp.save(flush: true,failOnError: true)
                }
            }else if(tableName == GeneConstants.DOMAIN_EIA_PROJECT_EXPLORE){
                def eiaProjectExplore = EiaProjectExplore.findByIdAndIfDel(tableNameId,false)
                if(eiaProjectExplore){
                    eiaProjectExplore.ifSub = false
                    eiaWorkFlowWeaverService.sendOverWorkFlowMsg(eiaWorkFlowBusi,currNodeName,currAuthCode,currAuthType,session)
                    eiaProjectExplore.save(flush: true,failOnError: true)
                }
            }
            return [code:HttpMesConstants.CODE_OK,msg:HttpMesConstants.MSG_FLOW_HALT_OK]
        } else {
            return [code:HttpMesConstants.CODE_FAIL,msg:HttpMesConstants.MSG_SUBMIT_FAIL]
        }
    }

}



