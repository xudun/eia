package com.lheia.eia.project

import com.lheia.eia.common.GeneConstants
import com.lheia.eia.common.HttpMesConstants
import com.lheia.eia.common.WorkFlowConstants
import com.lheia.eia.config.EiaDomainCode
import com.lheia.eia.contract.EiaContract
import com.lheia.eia.task.EiaTask
import com.lheia.eia.tools.DateTransTools
import com.lheia.eia.tools.JsonHandler
import com.lheia.eia.workflow.EiaWorkFlowBusi
import grails.converters.JSON
import grails.gorm.transactions.Transactional
import org.grails.web.json.JSONArray

@Transactional
class EiaProjectPlanService {
    def eiaWorkFlowProjectService
    /**
     * 工作方案保存
     */
    def eiaProjectPlanSave(params) {
        def eiaProjectId = params.long('eiaProjectId')
        def eiaProjectPlan = new EiaProjectPlan(params)
        params.eiaProjectId = eiaProjectId
        eiaProjectPlan.properties = params
        eiaProjectPlan.save(flush: true, failOnError: true)
    }
    /**
     * 工作方案更新
     */
    def eiaProjectPlanUpdate(params) {
        def eiaProjectPlanId = params.long('eiaProjectPlanId')
        def eiaProjectPlan = EiaProjectPlan.findByIdAndIfDel(eiaProjectPlanId, false)
        eiaProjectPlan.properties = params
        eiaProjectPlan.save(flush: true, failOnError: true)
    }
    /**
     * 工作方案节点更新
     */
    def eiaProjectPlanItemUpdate(params) {
        def proPlanItemId = params.long('proPlanItemId')
        def eiaProjectPlanItem = EiaProjectPlanItem.findByIdAndIfDel(proPlanItemId, false)
        def eiaProPlanId = eiaProjectPlanItem.eiaProjectPlanId
        def eiaProPlan = EiaProjectPlan.findByIdAndIfDel(eiaProPlanId, false)
        def eiaProjectId = eiaProPlan.eiaProjectId
        def eiaProjectCurr = EiaProject.findByIdAndIfDel(eiaProjectId, false)
        if (eiaProjectCurr.oid != null && eiaProjectCurr.oid != "") {
            if (eiaProjectPlanItem.nodesCode == 'YS') {
                def bzItem = EiaProjectPlanItem.findByEiaProjectPlanIdAndNodesCodeAndIfDel(eiaProPlanId, WorkFlowConstants.NODE_CODE_YSBZ, false)
                if (params.modiContent) {
                    bzItem.modiContent = params.modiContent
                }
                if (params.opinion) {
                    bzItem.opinion = params.opinion
                }
                bzItem.save(flush: true, failOnError: true)
            } else if (eiaProjectPlanItem.nodesCode == 'ES') {
                def bzItem = EiaProjectPlanItem.findByEiaProjectPlanIdAndNodesCodeAndIfDel(eiaProPlanId, WorkFlowConstants.NODE_CODE_ESBZ, false)
                if (params.modiContent) {
                    bzItem.modiContent = params.modiContent
                }
                if (params.opinion) {
                    bzItem.opinion = params.opinion
                }
                bzItem.save(flush: true, failOnError: true)
            } else if (eiaProjectPlanItem.nodesCode == 'SS') {
                def bzItem = EiaProjectPlanItem.findByEiaProjectPlanIdAndNodesCodeAndIfDel(eiaProPlanId, WorkFlowConstants.NODE_CODE_SSBZ, false)
                if (params.modiContent) {
                    bzItem.modiContent = params.modiContent
                }
                if (params.opinion) {
                    bzItem.opinion = params.opinion
                }
                bzItem.save(flush: true, failOnError: true)
            } else if (eiaProjectPlanItem.nodesCode == 'LS') {
                def bzItem = EiaProjectPlanItem.findByEiaProjectPlanIdAndNodesCodeAndIfDel(eiaProPlanId, WorkFlowConstants.NODE_CODE_LSBZ, false)
                if (params.modiContent) {
                    bzItem.modiContent = params.modiContent
                }
                if (params.opinion) {
                    bzItem.opinion = params.opinion
                }
                bzItem.save(flush: true, failOnError: true)
            }
        }
        if (params.planStartDate) {
            params.planStartDate = DateTransTools.getFormatDateD(params.planStartDate)
        }
        if (params.planEndDate) {
            params.planEndDate = DateTransTools.getFormatDateD(params.planEndDate)
        }
        if (params.actStartDate) {
            params.actStartDate = DateTransTools.getFormatDateD(params.actStartDate)
        }
        if (params.actEndDate) {
            params.actEndDate = DateTransTools.getFormatDateD(params.actEndDate)
        }
        def planFinDay
        def actFinDay
        if (params.planStartDate && params.planEndDate) {
            if (params.planStartDate.getTime() > params.planEndDate.getTime()) {
                return HttpMesConstants.MES_DATE_ERROR
            }
            planFinDay = DateTransTools.getWorkDays(params.planStartDate, params.planEndDate)
        }
        if (params.actStartDate && params.actEndDate) {
            if (params.actStartDate.getTime() > params.actEndDate.getTime()) {
                return HttpMesConstants.MES_DATE_ERROR
            }
            actFinDay = DateTransTools.getWorkDays(params.actStartDate, params.actEndDate)
        }
        if (params.contractId) {
            def eiaProjectPlan = EiaProjectPlan.findByIdAndIfDel(params.proPlanId, false)
            if (eiaProjectPlan) {
                if (eiaProjectPlan.eiaProjectId) {
                    def eiaProject = EiaProject.findByIdAndIfDel(eiaProjectPlan.eiaProjectId, false)
                    if (eiaProject) {
                        def eiaContract = EiaContract.findByIdAndIfDel(params.long('contractId'), false)
                        if (eiaContract) {
                            eiaProject.eiaContractId = params.long('contractId')
                            eiaProject.contractName = eiaContract.contractName
                            eiaProject.contractNo = eiaContract.contractNo
                        }
                    }
                }
            }
        }
        if (params.nodeUserName == '请选择')
            params.nodeUserName = ''
        eiaProjectPlanItem.properties = params
        eiaProjectPlanItem.nodeUserId = params.long('nodeUser')
        eiaProjectPlanItem.planFinDay = planFinDay
        eiaProjectPlanItem.actFinDay = actFinDay
        if (params.userNames) {
            JSONArray userNamesList = new JSONArray(params.userNames)
            String userNames = ""
            if (userNamesList.size() > 0) {
                userNamesList.each {
                    userNames += it.name + "_" + it.id + ","
                }
                eiaProjectPlanItem.userNames = userNames.substring(0, userNames.length() - 1)
            } else {
                eiaProjectPlanItem.userNames = ""
            }
        }

        eiaProjectPlanItem.save(flush: true, failOnError: true)
    }
    /**
     *  工作方案节点更新新
     */
    def eiaProjectPlanItemUpdateNew(params) {
        def proPlanId = params.long('proPlanId')
        def planStartDate = params.planStartDate
        def planEndDate = params.planEndDate
        Integer workDayNum = null
        if (params.workDayNum) {
            workDayNum = Integer.parseInt(params.workDayNum)
        }
        def eiaProPlanItem = EiaProjectPlanItem.findAllByEiaProjectPlanIdAndIfDel(proPlanId, false)
        if (params.showTitle == '初稿完成' && params.proPlanItemId == null || params.proPlanItemId == '') {
            def fee = DateTransTools.getWorkDate(DateTransTools.getFormatDateD(planStartDate), workDayNum)
            for (int i = 0; i < eiaProPlanItem.size(); i++) {
                def currItem = eiaProPlanItem.get(i)
                if (currItem.nodesCode == 'XCKC') {
                    currItem.planStartDate = DateTransTools.getFormatDateD(planStartDate)
                    // currItem.planEndDate = DateTransTools.getFormatDateD(planEndDate)
                    currItem.planEndDate = fee
                    currItem.planFinDay = workDayNum
                    currItem.save(flush: true, failOnError: true)
                } else if (currItem.nodesCode == 'BZWC' || currItem.nodesCode == 'ZBYB') {
//                    currItem.planEndDate = DateTransTools.getFormatDateD(planEndDate)
                    currItem.planEndDate = fee
                    currItem.planStartDate = DateTransTools.getFormatDateD(planStartDate)
                    currItem.planFinDay = workDayNum
                    currItem.save(flush: true, failOnError: true)
                }
            }

            true
        } else {
            def proPlanItemId = params.long('proPlanItemId')
            def eiaProjectPlanItem = EiaProjectPlanItem.findByIdAndIfDel(proPlanItemId, false)
            def eiaProPlanId = eiaProjectPlanItem.eiaProjectPlanId
            def eiaProPlan = EiaProjectPlan.findByIdAndIfDel(eiaProPlanId, false)
            def eiaProjectId = eiaProPlan.eiaProjectId
            def eiaProjectCurr = EiaProject.findByIdAndIfDel(eiaProjectId, false)
            if (eiaProjectCurr.oid != null && eiaProjectCurr.oid != "") {
                if (eiaProjectPlanItem.nodesCode == 'YS') {
                    def bzItem = EiaProjectPlanItem.findByEiaProjectPlanIdAndNodesCodeAndIfDel(eiaProPlanId, WorkFlowConstants.NODE_CODE_YSBZ, false)
                    if (params.modiContent) {
                        bzItem.modiContent = params.modiContent
                    }
                    if (params.opinion) {
                        bzItem.opinion = params.opinion
                    }
                    bzItem.save(flush: true, failOnError: true)
                } else if (eiaProjectPlanItem.nodesCode == 'ES') {
                    def bzItem = EiaProjectPlanItem.findByEiaProjectPlanIdAndNodesCodeAndIfDel(eiaProPlanId, WorkFlowConstants.NODE_CODE_ESBZ, false)
                    if (params.modiContent) {
                        bzItem.modiContent = params.modiContent
                    }
                    if (params.opinion) {
                        bzItem.opinion = params.opinion
                    }
                    bzItem.save(flush: true, failOnError: true)
                } else if (eiaProjectPlanItem.nodesCode == 'SS') {
                    def bzItem = EiaProjectPlanItem.findByEiaProjectPlanIdAndNodesCodeAndIfDel(eiaProPlanId, WorkFlowConstants.NODE_CODE_SSBZ, false)
                    if (params.modiContent) {
                        bzItem.modiContent = params.modiContent
                    }
                    if (params.opinion) {
                        bzItem.opinion = params.opinion
                    }
                    bzItem.save(flush: true, failOnError: true)
                } else if (eiaProjectPlanItem.nodesCode == 'LS') {
                    def bzItem = EiaProjectPlanItem.findByEiaProjectPlanIdAndNodesCodeAndIfDel(eiaProPlanId, WorkFlowConstants.NODE_CODE_LSBZ, false)
                    if (params.modiContent) {
                        bzItem.modiContent = params.modiContent
                    }
                    if (params.opinion) {
                        bzItem.opinion = params.opinion
                    }
                    bzItem.save(flush: true, failOnError: true)
                }
            }

            if (params.planStartDate) {
                params.planStartDate = DateTransTools.getFormatDateD(params.planStartDate)
            }
            if (params.planEndDate) {
                params.planEndDate = DateTransTools.getFormatDateD(params.planEndDate)
            }
            if (params.actStartDate) {
                params.actStartDate = DateTransTools.getFormatDateD(params.actStartDate)
            }
            if (params.actEndDate) {
                params.actEndDate = DateTransTools.getFormatDateD(params.actEndDate)
            }
            def planFinDay
            def actFinDay

            planFinDay = workDayNum
            if (planStartDate && planFinDay) {
                params.planEndDate = DateTransTools.getWorkDate(DateTransTools.getFormatDateD(planStartDate), workDayNum)
            }
            /*    if (params.planStartDate && params.planEndDate) {
                    if (params.planStartDate.getTime() > params.planEndDate.getTime()) {
                        return HttpMesConstants.MES_DATE_ERROR
                    }
                    planFinDay = DateTransTools.getWorkDays(params.planStartDate, params.planEndDate)
                }*/
            if (params.actStartDate && params.actEndDate) {
                if (params.actStartDate.getTime() > params.actEndDate.getTime()) {
                    return HttpMesConstants.MES_DATE_ERROR
                }
                actFinDay = DateTransTools.getWorkDays(params.actStartDate, params.actEndDate)
            }
            if (params.contractId) {
                def eiaProjectPlan = EiaProjectPlan.findByIdAndIfDel(params.proPlanId, false)
                if (eiaProjectPlan) {
                    if (eiaProjectPlan.eiaProjectId) {
                        def eiaProject = EiaProject.findByIdAndIfDel(eiaProjectPlan.eiaProjectId, false)
                        if (eiaProject) {
                            def eiaContract = EiaContract.findByIdAndIfDel(params.long('contractId'), false)
                            if (eiaContract) {
                                eiaProject.eiaContractId = params.long('contractId')
                                eiaProject.contractName = eiaContract.contractName
                                eiaProject.contractNo = eiaContract.contractNo
                            }
                        }
                    }
                }
            }
            if (params.nodeUserName == '请选择')
                params.nodeUserName = ''
            eiaProjectPlanItem.properties = params
            eiaProjectPlanItem.nodeUserId = params.long('nodeUser')
            eiaProjectPlanItem.planFinDay = planFinDay
            eiaProjectPlanItem.actFinDay = actFinDay
            if (params.userNames) {
                JSONArray userNamesList = new JSONArray(params.userNames)
                String userNames = ""
                if (userNamesList.size() > 0) {
                    userNamesList.each {
                        userNames += it.name + "_" + it.id + ","
                    }
                    eiaProjectPlanItem.userNames = userNames.substring(0, userNames.length() - 1)
                } else {
                    eiaProjectPlanItem.userNames = ""
                }
            }

            eiaProjectPlanItem.save(flush: true, failOnError: true)
        }
    }
    /**
     * 工作方案节点删除
     */
    def proPlanItemDel(params) {
        def proPlanItemId = params.long('proPlanItemId')
        def eiaProjectPlanItem = EiaProjectPlanItem.findByIdAndIfDel(proPlanItemId, false)
        eiaProjectPlanItem.ifDel = true
        eiaProjectPlanItem.save(flush: true, failOnError: true)
    }
    /**
     * 获取工作方案节点列表
     */
    def getProjectNodeList(params) {
        int page
        int limit
        if (params.page && params.limit) {
            page = params.int('page') - 1
            limit = params.int('limit')
        }
        def proPlanItemList = EiaProjectPlanItem.createCriteria().list(max: limit, offset: page * limit) {
            def proPlanId = params.long('proPlanId')
            if (proPlanId) {
                eq("eiaProjectPlanId", proPlanId)
            }

            eq("ifIcon", true)
            eq("ifDel", false)
        }
        def data = []
        proPlanItemList.each {
            def map = [:]
            map.id = it.id
            map.nodesName = it?.nodesName
            map.nodeUserName = it?.nodeUserName
            map.userNames = it?.userNames
            if (it?.planStartDate) {
                map.planStartDate = it?.planStartDate.format("yyyy-MM-dd")
            } else {
                map.planStartDate = ""
            }
            if (it?.planEndDate) {
                map.planEndDate = it?.planEndDate.format("yyyy-MM-dd")
            } else {
                map.planEndDate = ""
            }
            if (it?.actStartDate) {
                map.actStartDate = it?.actStartDate.format("yyyy-MM-dd")
            } else {
                map.actStartDate = ""
            }
            if (it?.actEndDate) {
                map.actEndDate = it?.actEndDate.format("yyyy-MM-dd")
            } else {
                map.actEndDate = ""
            }
            map.planFinDay = it?.planFinDay
            map.actFinDay = it?.actFinDay
            data << map
        }
        def dataMap = [:]
        dataMap.data = data
        dataMap.total = proPlanItemList.totalCount
        return dataMap
    }

    /**
     * 获取工作方案节点列表
     */
    def getProjectNodeListNew(params) {
        int page
        int limit
        if (params.page && params.limit) {
            page = params.int('page') - 1
            limit = params.int('limit')
        }
        def proPlanItemList = EiaProjectPlanItem.createCriteria().list(max: limit, offset: page * limit) {
            def proPlanId = params.long('proPlanId')
            if (proPlanId) {
                eq("eiaProjectPlanId", proPlanId)
            }

            eq("ifIcon", true)
            eq("ifDel", false)
            not { 'in'("nodesCode", ['JSPSH', 'BPBSB', 'XMGD', 'JLJS']) }

        }
        def data = []
        def firstDraftData = [:]
        data << firstDraftData
        firstDraftData.nodesName = '初稿完成'

        proPlanItemList.each {
            if (it.nodesCode == 'XCKC') {
                firstDraftData.planStartDate = it?.planStartDate?.format('yyyy-MM-dd')
                firstDraftData.planEndDate = it?.planEndDate?.format('yyyy-MM-dd')
                firstDraftData.planFinDay = it?.planFinDay
            } else if (it.nodesCode == 'BZWC' || it.nodesCode == 'ZBYB') {
                firstDraftData.planStartDate ? (firstDraftData.planStartDate) : (firstDraftData.planStartDate = it?.planStartDate?.format('yyyy-MM-dd'))
                firstDraftData.planEndDate = it?.planEndDate?.format('yyyy-MM-dd')
                firstDraftData.planFinDay = it?.planFinDay
            } else {
                def map = [:]
                map.id = it.id
                map.nodesName = it?.nodesName
                map.nodeUserName = it?.nodeUserName
                map.userNames = it?.userNames
                if (it?.planStartDate) {
                    map.planStartDate = it?.planStartDate.format("yyyy-MM-dd")
                } else {
                    map.planStartDate = ""
                }
                if (it?.planEndDate) {
                    map.planEndDate = it?.planEndDate.format("yyyy-MM-dd")
                } else {
                    map.planEndDate = ""
                }
                if (it?.actStartDate) {
                    map.actStartDate = it?.actStartDate.format("yyyy-MM-dd")
                } else {
                    map.actStartDate = ""
                }
                if (it?.actEndDate) {
                    map.actEndDate = it?.actEndDate.format("yyyy-MM-dd")
                } else {
                    map.actEndDate = ""
                }
                map.planFinDay = it?.planFinDay
                map.actFinDay = it?.actFinDay
                data << map
            }

        }

        def dataMap = [:]
        dataMap.data = data
        dataMap.total = proPlanItemList.totalCount
        return dataMap
    }

    /**
     * 获取工作方案节点详情
     */
    def getPlanItemDataMap(params) {
        if (!params.proPlanItemId && params.showTitle == '初稿完成') {
            def resMap = [:]
            def xckcNode = EiaProjectPlanItem.findByEiaProjectPlanIdAndNodesCode(params.long('proPlanId'), 'XCKC')
            if (xckcNode) {
                resMap.planStartDate = xckcNode.planStartDate?.format('yyyy-MM-dd')
                def bzwcNode = EiaProjectPlanItem.findByEiaProjectPlanIdAndNodesCode(params.long('proPlanId'), 'BZWC')
                if (bzwcNode) {
                    resMap.planEndDate = bzwcNode.planEndDate?.format('yyyy-MM-dd')
                } else {
                    def zbybNode = EiaProjectPlanItem.findByEiaProjectPlanIdAndNodesCode(params.long('proPlanId'), 'BZWC')
                    resMap.planEndDate = zbybNode.planEndDate?.format('yyyy-MM-dd')
                }
            }
            return resMap
        }
        def proPlanItemId = params.long('proPlanItemId')
        def eiaProPlanItem
        if (params.eiaWorkFlowBusiId && params.nodesCode) {
            def eiaWorkFlowBusi = EiaWorkFlowBusi.findByIdAndIfDel(params.long('eiaWorkFlowBusiId'), false)
            if (eiaWorkFlowBusi) {
                def projectId = eiaWorkFlowBusi.tableNameId
                if (projectId) {
                    def proPlan = EiaProjectPlan.findByEiaProjectIdAndIfDel(projectId, false)
                    if (proPlan) {
                        def proPlanId = proPlan.id
                        eiaProPlanItem = EiaProjectPlanItem.findByEiaProjectPlanIdAndNodesCode(proPlanId, params.nodesCode)
                    } else {
                        return false
                    }
                } else {
                    return false
                }
            } else {
                return false
            }
        } else {
            eiaProPlanItem = EiaProjectPlanItem.findByIdAndIfDel(proPlanItemId, false)
        }
        if (eiaProPlanItem) {
            def resMap = [:]
            resMap.planEndDate = eiaProPlanItem?.planEndDate?.format("yyyy-MM-dd")
            resMap.planStartDate = eiaProPlanItem?.planStartDate?.format("yyyy-MM-dd")
            resMap.planFinDay = eiaProPlanItem?.planFinDay
            if (eiaProPlanItem.actStartDate) {
                resMap.actStartDate = eiaProPlanItem?.actStartDate.format("yyyy-MM-dd")
            }
            if (eiaProPlanItem.actEndDate) {
                resMap.actEndDate = eiaProPlanItem?.actEndDate.format("yyyy-MM-dd")
            }
            resMap.actFinDay = eiaProPlanItem?.actFinDay
            resMap.planFinDay = eiaProPlanItem?.planFinDay
            resMap.opinion = eiaProPlanItem?.opinion
            resMap.modiContent = eiaProPlanItem?.modiContent
            resMap.nodeUserName = eiaProPlanItem?.nodeUserName
            resMap.nodesName = eiaProPlanItem?.nodesName
            resMap.nodeUserId = eiaProPlanItem?.nodeUserId
            if (eiaProPlanItem.userNames) {
                def userNames = eiaProPlanItem?.userNames.split(",")
                def nodeUserList = ""
                userNames.each { it ->
                    def assignUser = it.split("_")
                    nodeUserList += assignUser[0] + ","
                }
                resMap.userNames = nodeUserList.substring(0, nodeUserList.length() - 1)
            }

            resMap.nodeUser = eiaProPlanItem?.nodeUserName
            return resMap
        } else {
            return false
        }
    }
    /**
     * 推送工作方案到工作流
     */
    def proPlanToWorkFlow(params, session) {
        def proPlanId = params.long('proPlanId')
        def eiaProjectId = params.long('eiaProjectId')
        def planItemList = EiaProjectPlanItem.findAllByEiaProjectPlanIdAndIfDel(proPlanId, false)
        if (!planItemList) {
            return HttpMesConstants.MSG_APP_NULL
        }
        /**——————检查必填节点的预计时间——————**/
        for (int i = 0; i < planItemList.size(); i++) {
            def flgItem = planItemList.get(i)
            if ((flgItem.nodesName in GeneConstants.PLAN_DATE_NODE_LIST) && (!flgItem.planStartDate || !flgItem.planEndDate)) {
                return HttpMesConstants.MSG_APP_NULL
            }
        }
        /**——————获取流程模板————————**/
        def nodeNameList = planItemList.nodesName
        def EiaProjectPlan = EiaProjectPlan.findByIdAndIfDel(proPlanId, false)
        EiaProjectPlan.ifSub = true
        EiaProjectPlan.save(flush: true, failOnError: true)
        def workConfJson = EiaProjectPlan.workFlowJson
        def workFlowMap = JsonHandler.jsonToMap(workConfJson)
        def workFlowNodeList = workFlowMap.workFlowNode
        //   def proFlowNodeDic = workFlowMap.workFlowNodeDic
        /**——————根据planItem拼接工作流节点——————**/
        def proNodeList = []
        workFlowNodeList.eachWithIndex { it, i ->
            def proItem = EiaProjectPlanItem.findByEiaProjectPlanIdAndIfDelAndNodesCode(proPlanId, false, it.nodesCode)
            it.nodeUserName = proItem?.nodeUserName
            it.nodeUserId = proItem?.nodeUserId
            /**
             * 第一个节点的审批人如果没有选择的话则默认为提交工作流的人，同时更新节点的审批人（存疑）
             */
            if (i == 0) {
                if (!it.nodeUserName || !it.nodeUserId) {
                    it.nodeUserName = session.staff.staffName
                    it.nodeUserId = Long.valueOf(session.staff.staffId)
                    proItem.nodeUserName = session.staff.staffName
                    proItem.nodeUserId = Long.valueOf(session.staff.staffId)
                    proItem.save(flush: true, failOnError: true)
                }
            }
            proNodeList << it
        }
        workFlowMap.workFlowNode = proNodeList
        def proFlowNodeDic = [:]
        proNodeList.eachWithIndex { it, i ->
            proFlowNodeDic.(it.nodesCode) = i
        }
        workFlowMap.workFlowNodeDic = proFlowNodeDic
        if (eiaWorkFlowProjectService.startProjectWorkFlow((workFlowMap as JSON).toString(), GeneConstants.DOMAIN_EIA_PROJECT, eiaProjectId, session)) {
            return HttpMesConstants.MSG_SAVE_OK
        } else {
            return HttpMesConstants.MSG_SAVE_FAIL
        }
    }
    /**
     * 提交到根据老合同提交到报批版上报（admin可以用）
     */
    def makeProjectWorkFlow() {
        def proPlanId = params.long('proPlanId')
        def eiaProjectId = params.long('eiaProjectId')
        def eiaProject = EiaProject.findByIdAndIfDel(params.long('eiaProjectId'), false)
        def oldContractId
        if (eiaProject) {
            eiaProject.ifCert = true
            eiaProject.save(flush: true, failOnError: true)
            if (eiaProject.eiaContractId) {
                oldContractId = EiaContract.findByIdAndIfDel(eiaProject.eiaContractId, false).oid
            }
        }
        if (oldContractId) {
            //老合同(任务)新创建的项目……………………………………………………
            def planItemList = EiaProjectPlanItem.findAllByEiaProjectPlanIdAndIfDel(proPlanId, false)
            /**——————先判断每个节点是否有节点审批人(不判断了，在流程里面选)——————————**/
            /*    for (int i = 0; i < planItemList.size(); i++) {
                    if (!planItemList.get(i).nodeUserId) {
                        return HttpMesConstants.MSG_APP_NULL
                    }
                }*/
            /**——————获取流程模板————————**/
            def nodeNameList = planItemList.nodesName
            def EiaProjectPlan = EiaProjectPlan.findByIdAndIfDel(proPlanId, false)
            EiaProjectPlan.ifSub = true
            EiaProjectPlan.save(flush: true, failOnError: true)
            def workConfJson = EiaProjectPlan.workFlowJson
            def workFlowMap = JsonHandler.jsonToMap(workConfJson)
            def workFlowNodeList = workFlowMap.workFlowNode
            //   def proFlowNodeDic = workFlowMap.workFlowNodeDic
            /**——————根据planItem拼接工作流节点——————**/
            def proNodeList = []
            workFlowNodeList.eachWithIndex { it, i ->
                def proItem = EiaProjectPlanItem.findByEiaProjectPlanIdAndIfDelAndNodesCode(proPlanId, false, it.nodesCode)
                it.nodeUserName = proItem?.nodeUserName
                it.nodeUserId = proItem?.nodeUserId
                /**
                 * 第一个节点的审批人如果没有选择的话则默认为提交工作流的人，同时更新节点的审批人（存疑）
                 */
                if (i == 0) {
                    if (!it.nodeUserName || !it.nodeUserId) {
                        it.nodeUserName = session.staff.staffName
                        it.nodeUserId = Long.valueOf(session.staff.staffId)
                        proItem.nodeUserName = session.staff.staffName
                        proItem.nodeUserId = session.staff.staffId
                        proItem.save(flush: true, failOnError: true)
                    }
                }
                proNodeList << it
            }
            workFlowMap.workFlowNode = proNodeList
            def proFlowNodeDic = [:]
            proNodeList.eachWithIndex { it, i ->
                proFlowNodeDic.(it.nodesCode) = i
            }
            workFlowMap.workFlowNodeDic = proFlowNodeDic
            def wfBusi = eiaWorkFlowProjectService.startProjectWorkFlow((workFlowMap as JSON).toString(), GeneConstants.DOMAIN_EIA_PROJECT, eiaProjectId, session)
            if (wfBusi) {
                wfBusi.workFlowState = 'doing'
                wfBusi.nodesName = '报批版上报'
                wfBusi.nodesCode = 'BPBSB'
                def bpNode = EiaProjectPlanItem.findByEiaProjectPlanIdAndIfDelAndNodesCode(proPlanId, false, 'BPBSB')
                wfBusi.authName = bpNode.nodeUserName
                wfBusi.authCode = bpNode.nodeUserId
                wfBusi.updateUser = session.staff.staffName
                wfBusi.updateUserId = Long.valueOf(session.staff.staffId)
                wfBusi.updateDept = session.staff.orgName
                wfBusi.updateDeptCode = session.staff.orgCode
                wfBusi.updateDeptId = Long.valueOf(session.staff.orgId)
                wfBusi.save(flush: true, failOnError: true)
                /**
                 * 更新工作流到报批版上报
                 */
                return HttpMesConstants.MSG_SAVE_OK
            } else {
                return HttpMesConstants.MSG_SAVE_FAIL
            }
        } else {
            def planItemList = EiaProjectPlanItem.findAllByEiaProjectPlanIdAndIfDel(proPlanId, false)
            /**——————先判断每个节点是否有节点审批人(不判断了，在流程里面选)——————————**/
            /*    for (int i = 0; i < planItemList.size(); i++) {
                    if (!planItemList.get(i).nodeUserId) {
                        return HttpMesConstants.MSG_APP_NULL
                    }
                }*/
            /**——————获取流程模板————————**/
            def nodeNameList = planItemList.nodesName
            def EiaProjectPlan = EiaProjectPlan.findByIdAndIfDel(proPlanId, false)
            EiaProjectPlan.ifSub = true
            EiaProjectPlan.save(flush: true, failOnError: true)
            def workConfJson = EiaProjectPlan.workFlowJson
            def workFlowMap = JsonHandler.jsonToMap(workConfJson)
            def workFlowNodeList = workFlowMap.workFlowNode
            //   def proFlowNodeDic = workFlowMap.workFlowNodeDic
            /**——————根据planItem拼接工作流节点——————**/
            def proNodeList = []
            workFlowNodeList.eachWithIndex { it, i ->
                def proItem = EiaProjectPlanItem.findByEiaProjectPlanIdAndIfDelAndNodesCode(proPlanId, false, it.nodesCode)
                it.nodeUserName = proItem?.nodeUserName
                it.nodeUserId = proItem?.nodeUserId
                /**
                 * 第一个节点的审批人如果没有选择的话则默认为提交工作流的人，同时更新节点的审批人（存疑）
                 */
                if (i == 0) {
                    if (!it.nodeUserName || !it.nodeUserId) {
                        it.nodeUserName = session.staff.staffName
                        it.nodeUserId = Long.valueOf(session.staff.staffId)
                        proItem.nodeUserName = session.staff.staffName
                        proItem.nodeUserId = Long.valueOf(session.staff.staffId)
                        proItem.save(flush: true, failOnError: true)
                    }
                }
                proNodeList << it
            }
            workFlowMap.workFlowNode = proNodeList
            def proFlowNodeDic = [:]
            proNodeList.eachWithIndex { it, i ->
                proFlowNodeDic.(it.nodesCode) = i
            }
            workFlowMap.workFlowNodeDic = proFlowNodeDic
            if (eiaWorkFlowProjectService.startProjectWorkFlow((workFlowMap as JSON).toString(), GeneConstants.DOMAIN_EIA_PROJECT, eiaProjectId, session)) {
                return HttpMesConstants.MSG_SAVE_OK
            } else {
                return HttpMesConstants.MSG_SAVE_FAIL
            }
        }
    }
    /**
     * 获取节点审批人员列表(eiaTask表的taskAssignUserId)
     * 用两个list拼接，免从api查询
     */
    def getNodeUsers(params) {
        def proPlanId
        proPlanId = params.long('proPlanId')
        def proPlanItemId = params.long('proPlanItemId')
        def eiaProPlanItem

        if (params.eiaWorkFlowBusiId && params.nodesCode) {
            def eiaWorkFlowBusi = EiaWorkFlowBusi.findByIdAndIfDel(params.long('eiaWorkFlowBusiId'), false)
            if (eiaWorkFlowBusi) {
                def projectId = eiaWorkFlowBusi.tableNameId
                if (projectId) {
                    def proPlan = EiaProjectPlan.findByEiaProjectIdAndIfDel(projectId, false)
                    if (proPlan) {
                        proPlanId = proPlan.id
                        eiaProPlanItem = EiaProjectPlanItem.findByEiaProjectPlanIdAndNodesCode(proPlanId, params.nodesCode)
                    } else {
                        return false
                    }
                } else {
                    return false
                }
            } else {
                return false
            }
        } else {
            eiaProPlanItem = EiaProjectPlanItem.findByIdAndIfDel(proPlanItemId, false)
        }
        def itemName = eiaProPlanItem?.nodesName
        def itemCode = eiaProPlanItem?.nodesCode
        def eiaTaskId = EiaProjectPlan.findByIdAndIfDel(proPlanId, false).eiaTaskId
        def eiaTask = EiaTask.findByIdAndIfDel(eiaTaskId, false)
        def taskAssignUsers = eiaTask.taskAssignUser.split(",")
        def nodeUserList = []
        if (itemCode == WorkFlowConstants.NODE_CODE_XMGD) {
            def xmgdUser = EiaDomainCode.findAllByDomain(GeneConstants.XMGDRY)
            if (xmgdUser) {
                xmgdUser.each {
                    def userMap = [:]
                    userMap.name = it.codeDesc
                    userMap.id = it.code
                    nodeUserList << userMap
                }
            }
        } else {
            taskAssignUsers.each { it ->
                def nodeUser = [:]
                def assignUser = it.split("_")
                nodeUser.name = assignUser[0]
                nodeUser.id = assignUser[1]
                nodeUserList << nodeUser
            }
            if (itemCode == WorkFlowConstants.NODE_CODE_LS) {
                def lsUser = EiaDomainCode.findAllByDomain(GeneConstants.LSRY)
                lsUser.each {
                    def lsUserMap = [:]
                    lsUserMap.name = it.codeDesc
                    lsUserMap.id = it.code
                    nodeUserList << lsUserMap
                }
            }
        }
        return nodeUserList.unique()
    }
    /**
     * 获取节点相关工作人员下拉树
     */
    def getNodeUsersTree(params) {
        def proPlanId = params.long('proPlanId')
        def proPlanItemId = params.long('proPlanItemId')
        def eiaProPlanItem
        if (params.eiaWorkFlowBusiId && params.nodesCode) {
            def eiaWorkFlowBusi = EiaWorkFlowBusi.findByIdAndIfDel(params.long('eiaWorkFlowBusiId'), false)
            if (eiaWorkFlowBusi) {
                def projectId = eiaWorkFlowBusi.tableNameId
                if (projectId) {
                    def proPlan = EiaProjectPlan.findByEiaProjectIdAndIfDel(projectId, false)
                    if (proPlan) {
                        proPlanId = proPlan.id
                        eiaProPlanItem = EiaProjectPlanItem.findByEiaProjectPlanIdAndNodesCode(proPlanId, params.nodesCode)
                    } else {
                        return false
                    }
                } else {
                    return false
                }
            } else {
                return false
            }
        } else {
            eiaProPlanItem = EiaProjectPlanItem.findByIdAndIfDel(proPlanItemId, false)
        }
        def itemCode = eiaProPlanItem?.nodesCode
        def eiaTaskId = EiaProjectPlan.findByIdAndIfDel(proPlanId, false).eiaTaskId
        def eiaTask = EiaTask.findByIdAndIfDel(eiaTaskId, false)
        def taskAssignUsers = eiaTask.taskAssignUser.split(",")
        def nodeUserList = []
        taskAssignUsers.each { it ->
            def nodeUser = [:]
            def assignUser = it.split("_")
            nodeUser.name = assignUser[0]
            nodeUser.id = assignUser[1]
            nodeUser.attributes = [:]
            nodeUser.cheched = true
            nodeUserList << nodeUser
        }
        if (itemCode == WorkFlowConstants.NODE_CODE_LS || itemCode == WorkFlowConstants.NODE_CODE_XMGD) {
            def lsUser = EiaDomainCode.findAllByDomain(GeneConstants.LSRY)
            lsUser.each {
                def lsUserMap = [:]
                lsUserMap.name = it.codeDesc
                lsUserMap.id = it.code
                lsUserMap.attributes = [:]
                lsUserMap.cheched = true
                nodeUserList << lsUserMap
            }
        }
        return nodeUserList.unique()
    }

    /**
     * 相关工作人员回显
     */
    def getItemUserNames(params) {
        def proPlanItemId = params.long('proPlanItemId')
        def eiaProPlanItem
//         eiaProPlanItem = EiaProjectPlanItem.findByIdAndIfDel(proPlanItemId, false)
        if (params.eiaWorkFlowBusiId && params.nodesCode) {
            def eiaWorkFlowBusi = EiaWorkFlowBusi.findByIdAndIfDel(params.long('eiaWorkFlowBusiId'), false)
            if (eiaWorkFlowBusi) {
                def projectId = eiaWorkFlowBusi.tableNameId
                if (projectId) {
                    def proPlan = EiaProjectPlan.findByEiaProjectIdAndIfDel(projectId, false)
                    if (proPlan) {
                        eiaProPlanItem = EiaProjectPlanItem.findByEiaProjectPlanIdAndNodesCode(proPlan.id, params.nodesCode)
                    } else {
                        return false
                    }
                } else {
                    return false
                }
            } else {
                return false
            }
        } else {
            eiaProPlanItem = EiaProjectPlanItem.findByIdAndIfDel(proPlanItemId, false)
        }
        if (eiaProPlanItem) {
            if (eiaProPlanItem.userNames) {
                def userNames = eiaProPlanItem?.userNames.split(",")
                def nodeUserList = []
                userNames.each { it ->
                    def nodeUser = [:]
                    def assignUser = it.split("_")
                    nodeUser.name = assignUser[0]
                    nodeUser.id = assignUser[1]
                    nodeUserList << nodeUser
                }
                return nodeUserList.unique()
            } else {
                return false
            }
        } else {
            return false
        }
    }
    /**
     *  获取项目节点列表
     */
    def getProItems(params) {
        def proPlanId = params.long('proPlanId')
        def proItemList = EiaProjectPlanItem.findAllByEiaProjectPlanIdAndIfDel(proPlanId, false)
        if (proItemList) {
            return proItemList
        } else {
            return false
        }
    }
    /**
     *  干预流程
     */
    def changeWorkFlowNode(params) {
        def eiaProjectId = params.long('eiaProjectId')
        def busi = EiaWorkFlowBusi.findByTableNameAndTableNameIdAndIfDelAndWorkFlowStateNotInList('EiaProject', eiaProjectId, false, ['halt', 'end'])
        busi.authName = params.nodeUserName
        busi.authCode = params.nodeUser
        def proItem = EiaProjectPlanItem.findByIdAndIfDel(params.long('proPlanItemId'), false)
        proItem.nodeUserName = params.nodeUserName
        proItem.nodeDeptId = params.long('nodeUserId')
        proItem.save(flush:true,failOnError:true)
        busi.nodesName = proItem.nodesName
        busi.nodesCode = proItem.nodesCode
        if (proItem.nodesCode in ['LSBZ', 'LS', 'JSPSH', 'BPBSB', 'XMGD', 'SSBZ']) {
            def eiaProject = EiaProject.findByIdAndIfDel(eiaProjectId, false)
            eiaProject.ifCert = true
            eiaProject.save(flush: true, failOnError: true)
        }
        busi.save(flush: true, failOnError: true)
    }
}
