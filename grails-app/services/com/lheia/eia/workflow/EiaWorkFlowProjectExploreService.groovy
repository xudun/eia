package com.lheia.eia.workflow

import com.lheia.eia.cert.EiaCert
import com.lheia.eia.common.GeneConstants
import com.lheia.eia.common.HttpMesConstants
import com.lheia.eia.common.HttpUrlConstants
import com.lheia.eia.common.WorkFlowConstants
import com.lheia.eia.project.EiaProjectExplore
import com.lheia.eia.tools.HttpConnectTools
import com.lheia.eia.tools.JsonHandler
import grails.gorm.transactions.Transactional

@Transactional
class EiaWorkFlowProjectExploreService {

    def eiaWorkFlowWeaverService
    def eiaWorkFlowService

    /***
     * 根据用户部门编码获取工作流编码
     */
    def getWorkFlowCode(session){
        String orgCode = session.staff.orgCode
        def workFlowCode = WorkFlowConstants.PROJECT_EXPLORE_WORKFLOW
        if(orgCode){
            WorkFlowConstants.PROJECT_EXPLORE_WORKFLOW_MAP.each{
                if(orgCode.contains(it.key)){
                    workFlowCode = it.value
                }
            }
        }
        return workFlowCode
    }
    /**
     * 开启内审工作流
     * @param eiaWorkFlowConfigId
     * @param tableName
     * @param tableNameId
     * @param startNodeCode
     * @return
     */
    def startProjectExploreWorkFlow(String eiaWorkFlowCode, String tableName, Long tableNameId, String startNodeCode, session) {
        def eiaWorkFlowBusi = new EiaWorkFlowBusi()
        def eiaProjectExplore = EiaProjectExplore.findByIfDelAndId(false, tableNameId)
        def workFlowConfigList = EiaWorkFlowConfig.findAllByWorkFlowCodeAndIfDelAndIfValid(eiaWorkFlowCode, false, true, [sort: "workFlowVersion", order: "desc"])
        if(!workFlowConfigList){
            return false
        }
        def workFlowConfig = workFlowConfigList.get(0)
        def workFlowConfigMap = JsonHandler.jsonToMap(workFlowConfig.workFlowJson)
        def startNode = workFlowConfigMap.workFlowNode[workFlowConfigMap.workFlowNodeDic[startNodeCode]]
        eiaWorkFlowBusi.eiaWorkFlowId = workFlowConfigMap.eiaWorkFlowId
        eiaWorkFlowBusi.workFlowTitle = eiaProjectExplore.projectName
        eiaWorkFlowBusi.workFlowJson = workFlowConfig.workFlowJson
        eiaWorkFlowBusi.workFlowCode = workFlowConfigMap.workFlowCode
        eiaWorkFlowBusi.workFlowName = workFlowConfigMap.workFlowName
        eiaWorkFlowBusi.eiaWorkFlowNodeId = startNode.eiaWorkFlowNodeId
        eiaWorkFlowBusi.nodesCode = startNode.nodesCode
        eiaWorkFlowBusi.nodesName = startNode.nodesName
        eiaWorkFlowBusi.authType = startNode.nodesAuthType
        eiaWorkFlowBusi.inputDept = session.staff.orgName
        eiaWorkFlowBusi.inputDeptId = Long.parseLong(session.staff.orgId)
        eiaWorkFlowBusi.inputDeptCode = session.staff.orgCode
        eiaWorkFlowBusi.inputUser = session.staff.staffName
        eiaWorkFlowBusi.inputUserId = Long.parseLong(session.staff.staffId)
        eiaWorkFlowBusi.updateDept = session.staff.orgName
        eiaWorkFlowBusi.updateDeptId = Long.parseLong(session.staff.orgId)
        eiaWorkFlowBusi.updateDeptCode = session.staff.orgCode
        eiaWorkFlowBusi.updateUser = session.staff.staffName
        eiaWorkFlowBusi.updateUserId = Long.parseLong(session.staff.staffId)
        if (WorkFlowConstants.AUTH_TYPE_AUTH_CODE.equals(startNode.nodesAuthType)) {
            eiaWorkFlowBusi.authCode = startNode.nodesAuthCode
        } else {
            eiaWorkFlowBusi.authCode = startNode.nodesAuthCode
        }
        eiaWorkFlowBusi.tableName = tableName
        eiaWorkFlowBusi.workFlowState = WorkFlowConstants.WORKFLOW_START
        eiaWorkFlowBusi.tableNameId = tableNameId
        eiaWorkFlowBusi.save(flush: true, failOnError: true)
        /**————————OA系统发送消息——————————**/
        eiaWorkFlowWeaverService.sendToDoWorkflowMsg(eiaWorkFlowBusi)
        return eiaWorkFlowBusi
    }

    /**
     * neishen工作流结束
     * @return
     */
    def nextWorkFlowNodeEnd(Long eiaWorkFlowBusiId, String nodesCode, String opinion, String processCode, String approvalDate,Long version, session) {
        def eiaWorkFlowBusi = eiaWorkFlowService.nextWorkFlowNode(eiaWorkFlowBusiId, nodesCode, opinion, processCode, approvalDate, version,session)
        if(eiaWorkFlowBusi.code ==HttpMesConstants.CODE_FAIL){
            return eiaWorkFlowBusi
        }
        if(eiaWorkFlowBusi.data.tableName == GeneConstants.DOMAIN_EIA_PROJECT_EXPLORE){
            def eiaProjectExplore = EiaProjectExplore.findByIdAndIfDel(eiaWorkFlowBusi.data.tableNameId,false)
            eiaProjectExplore.ifEnd = true
            /**内审单**/
            if(eiaProjectExplore.geoJson){
                def param = [:]
                param.putAll(eiaProjectExplore.properties)
                param.geoJson = eiaProjectExplore.geoJson
                param.eiaProjectExploreId = eiaProjectExplore.id
                param.geoName = eiaProjectExplore.buildArea
                HttpConnectTools.getResponseJson(HttpUrlConstants.GIS_GEO_PROJECT_EXPLORE_SAVE,param)
            }
            eiaProjectExplore.save(flush: true, failOnError: true)
        }
        return eiaWorkFlowBusi
    }
}
