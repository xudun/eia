package com.lheia.eia.workflow

import com.lheia.eia.common.WorkFlowConstants
import com.lheia.eia.contract.EiaContractLog
import com.lheia.eia.tools.JsonHandler
import grails.gorm.transactions.Transactional

@Transactional
class EiaWorkFlowContractLogService {
    def eiaWorkFlowWeaverService
    /**
     * 开启资质工作流
     * @param eiaWorkFlowConfigId
     * @param tableName
     * @param tableNameId
     * @param startNodeCode
     * @return
     */
    def startContractLogWorkFlow(String eiaWorkFlowCode, String tableName, Long tableNameId, String startNodeCode, session) {
        def eiaWorkFlowBusi = new EiaWorkFlowBusi()
        def eiaContractLog = EiaContractLog.findByIfDelAndId(false, tableNameId)
        def workFlowConfigList = EiaWorkFlowConfig.findAllByWorkFlowCodeAndIfDelAndIfValid(eiaWorkFlowCode, false, true, [sort: "workFlowVersion", order: "desc"])
        if(!workFlowConfigList){
            return false
        }
        def workFlowConfig = workFlowConfigList.get(0)
        def workFlowConfigMap = JsonHandler.jsonToMap(workFlowConfig.workFlowJson)
        def startNode = workFlowConfigMap.workFlowNode[workFlowConfigMap.workFlowNodeDic[startNodeCode]]
        eiaWorkFlowBusi.eiaWorkFlowId = workFlowConfigMap.eiaWorkFlowId
        eiaWorkFlowBusi.workFlowTitle = eiaContractLog.contractName
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

}
