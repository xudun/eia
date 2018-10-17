package com.lheia.eia.workflow

import com.lheia.eia.common.WorkFlowConstants
import com.lheia.eia.contract.EiaContract
import com.lheia.eia.contract.EiaOffer
import com.lheia.eia.tools.JsonHandler
import grails.gorm.transactions.Transactional

/***
 * 合同工作流
 */
@Transactional
class EiaWorkFlowContractService {
def eiaWorkFlowWeaverService
    /****
     * 根据合同ID获取合同流程编码
     * @param eiaContractId
     * @return
     */
    def getContractWorkFlowCode(eiaContractId){
        def eiaContract = EiaContract.findByIfDelAndId(false,eiaContractId)
        def fee = (eiaContract.enviroMonitoringFee?eiaContract.enviroMonitoringFee:0)+(eiaContract.groundWater?eiaContract.groundWater:0)
        if(eiaContract.otherFee != null && eiaContract.otherFee>0){
            return WorkFlowConstants.CONTRACT_WORK_FLOW_ZJL
        }
        if(!fee ){
            return WorkFlowConstants.CONTRACT_WORKFLOW
        }
        if(!eiaContract.contractMoney){
            return WorkFlowConstants.CONTRACT_WORKFLOW
        }
        if( fee > eiaContract.contractMoney * 0.4){
            return WorkFlowConstants.CONTRACT_WORK_FLOW_ZJL
        }else{
            return WorkFlowConstants.CONTRACT_WORKFLOW
        }
    }


    /***
     * 启动合同工作流
     * @param eiaWorkFlowId流程id
     * @param tableName流程对象表名
     * @param tableNameId流程对象id
     * @return
     */

    def startContractWorkFlow(String  workFlowCode, String tableName, Long tableNameId, String startNodeCode,session) {
        def eiaWorkFlowBusi = new EiaWorkFlowBusi()
        def workFlowConfig
        def workFlowConfigList = EiaWorkFlowConfig.findAllByWorkFlowCodeAndIfDelAndIfValid(workFlowCode, false, true,[sort:"workFlowVersion",order:"desc"])
        if(workFlowConfigList.size()>0){
            workFlowConfig = workFlowConfigList.get(0)
        }else{
            return false
        }
        def eiaContract = EiaContract.findByIfDelAndId(false,tableNameId)
        def workFlowConfigMap = JsonHandler.jsonToMap(workFlowConfig.workFlowJson)
        def startNode = workFlowConfigMap.workFlowNode[workFlowConfigMap.workFlowNodeDic[startNodeCode]]
        eiaWorkFlowBusi.eiaWorkFlowId = workFlowConfigMap.eiaWorkFlowId
        eiaWorkFlowBusi.workFlowJson = workFlowConfig.workFlowJson
        eiaWorkFlowBusi.workFlowTitle = eiaContract.contractName
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

    /****
     * 根据报价ID获取报价流程编码
     * @param eiaOfferId
     * @return
     */
    def getOfferWorkFlowCode(eiaOfferId){
        def eiaOffer = EiaOffer.findByIdAndIfDel(eiaOfferId, false)
        def fee = (eiaOffer.enviroMonitoringFee?eiaOffer.enviroMonitoringFee:0)+(eiaOffer.groundWater?eiaOffer.groundWater:0)
        if(eiaOffer.otherFee){
            return WorkFlowConstants.OFFER_WORK_FLOW_ZJL
        }
        if (!fee) {
            return WorkFlowConstants.OFFER_WORKFLOW
        }
        if (!eiaOffer.offerMoney) {
            return WorkFlowConstants.OFFER_WORKFLOW
        }
        if (fee > eiaOffer.offerMoney * 0.4) {
            return WorkFlowConstants.OFFER_WORK_FLOW_ZJL
        } else {
            return WorkFlowConstants.OFFER_WORKFLOW
        }
    }
    /***
     * 启动报价工作流
     * @param eiaWorkFlowId流程id
     * @param tableName流程对象表名
     * @param tableNameId流程对象id
     * @return
     */
    def startOfferWorkFlow(String workFlowCode, String tableName, Long tableNameId, String startNodeCode,session) {
        def eiaWorkFlowBusi = new EiaWorkFlowBusi()
        def workFlowConfig
        def workFlowConfigList = EiaWorkFlowConfig.findAllByWorkFlowCodeAndIfDelAndIfValid(workFlowCode, false, true, [sort:"workFlowVersion",order:"desc"])
        if (workFlowConfigList.size() > 0) {
            workFlowConfig = workFlowConfigList.get(0)
        } else {
            return false
        }
        def eiaOffer = EiaOffer.findByIdAndIfDel(tableNameId, false)
        def workFlowConfigMap = JsonHandler.jsonToMap(workFlowConfig.workFlowJson)
        def startNode = workFlowConfigMap.workFlowNode[workFlowConfigMap.workFlowNodeDic[startNodeCode]]
        eiaWorkFlowBusi.eiaWorkFlowId = workFlowConfigMap.eiaWorkFlowId
        eiaWorkFlowBusi.workFlowJson = workFlowConfig.workFlowJson
        eiaWorkFlowBusi.workFlowTitle = eiaOffer.offerName
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
}
