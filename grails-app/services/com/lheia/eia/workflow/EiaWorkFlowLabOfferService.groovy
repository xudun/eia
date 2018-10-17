package com.lheia.eia.workflow

import com.lheia.eia.common.GeneConstants
import com.lheia.eia.common.HttpMesConstants
import com.lheia.eia.common.WorkFlowConstants
import com.lheia.eia.lab.EiaLabOffer
import com.lheia.eia.tools.JsonHandler
import grails.gorm.transactions.Transactional

/***
 * 合同工作流
 */
@Transactional
class EiaWorkFlowLabOfferService {

    def eiaWorkFlowService
    def eiaWorkFlowWeaverService
    def eiaFileUploadService

    /***
     * 启动监测方案工作流
     * @param eiaWorkFlowId流程id
     * @param tableName流程对象表名
     * @param tableNameId流程对象id
     * @return
     */
    def startLabOfferWorkFlow(String workFlowCode, String tableName, Long tableNameId, String startNodeCode,session) {
        def eiaWorkFlowBusi = new EiaWorkFlowBusi()
        def workFlowConfig
        def workFlowConfigList = EiaWorkFlowConfig.findAllByWorkFlowCodeAndIfDelAndIfValid(workFlowCode, false, true, [sort:"workFlowVersion", order:"desc"])
        if (workFlowConfigList.size() > 0) {
            workFlowConfig = workFlowConfigList.get(0)
        } else {
            return false
        }
        def eiaLabOffer = EiaLabOffer.findByIdAndIfDel(tableNameId, false)
        def workFlowConfigMap = JsonHandler.jsonToMap(workFlowConfig.workFlowJson)
        def startNode = workFlowConfigMap.workFlowNode[workFlowConfigMap.workFlowNodeDic[startNodeCode]]
        eiaWorkFlowBusi.eiaWorkFlowId = workFlowConfigMap.eiaWorkFlowId
        eiaWorkFlowBusi.workFlowJson = workFlowConfig.workFlowJson
        eiaWorkFlowBusi.workFlowTitle = eiaLabOffer.sjClientName
        eiaWorkFlowBusi.workFlowCode = workFlowConfigMap.workFlowCode
        eiaWorkFlowBusi.workFlowName = workFlowConfigMap.workFlowName
        eiaWorkFlowBusi.eiaWorkFlowNodeId = startNode.eiaWorkFlowNodeId
        eiaWorkFlowBusi.nodesCode = startNode.nodesCode
        eiaWorkFlowBusi.nodesName = startNode.nodesName
        eiaWorkFlowBusi.authType = startNode.nodesAuthType
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
        /**————————OA系统发送消息——————————**/
        eiaWorkFlowWeaverService.sendToDoWorkflowMsg(eiaWorkFlowBusi)
        return eiaWorkFlowBusi
    }
    /**
     * 监测方案工作流结束
     * @return
     */
    def nextWorkFlowNodeLabOfferEnd(Long eiaWorkFlowBusiId, String nodesCode, String opinion, String processCode, String approvalDate, Long version, session) {
        def eiaWorkFlowBusi = eiaWorkFlowService.nextWorkFlowNode(eiaWorkFlowBusiId, nodesCode, opinion, processCode, approvalDate, version,session)
        if(eiaWorkFlowBusi.code == HttpMesConstants.CODE_FAIL){
            return eiaWorkFlowBusi
        }
        if (eiaWorkFlowBusi.data.tableName == GeneConstants.DOMAIN_EIA_LAB_OFFER) {
            eiaFileUploadService.UpdateFileReadOnly(eiaWorkFlowBusi.data.tableName,Long.valueOf(eiaWorkFlowBusi.data.tableNameId))
            def eiaLabOffer = EiaLabOffer.findById(Long.valueOf(eiaWorkFlowBusi.data.tableNameId))
            eiaLabOffer.ifTestComplete = true
            eiaLabOffer.save(flush: true, failOnError: true)
        }
        return eiaWorkFlowBusi
    }
}
