package com.lheia.eia.workflow

import com.lheia.eia.cert.EiaCert
import com.lheia.eia.common.GeneConstants
import com.lheia.eia.common.HttpMesConstants
import com.lheia.eia.common.HttpUrlConstants
import com.lheia.eia.common.WorkFlowConstants
import com.lheia.eia.tools.HttpConnectTools
import com.lheia.eia.tools.JsonHandler
import grails.gorm.transactions.Transactional

@Transactional
class EiaWorkFlowCertService {
    def eiaWorkFlowService
    def eiaFileUploadService
    def eiaWorkFlowWeaverService
    /**
     * 开启资质工作流
     * @param eiaWorkFlowConfigId
     * @param tableName
     * @param tableNameId
     * @param startNodeCode
     * @return
     */
    def startCertWorkFlow(String eiaWorkFlowCode, String tableName, Long tableNameId, String startNodeCode, session) {
        def eiaWorkFlowBusi = new EiaWorkFlowBusi()
        def eiaCert = EiaCert.findByIfDelAndId(false, tableNameId)
        def workFlowConfigList = EiaWorkFlowConfig.findAllByWorkFlowCodeAndIfDelAndIfValid(eiaWorkFlowCode, false, true, [sort: "workFlowVersion", order: "desc"])
        if(!workFlowConfigList){
            return false
        }
        def workFlowConfig = workFlowConfigList.get(0)
        def workFlowConfigMap = JsonHandler.jsonToMap(workFlowConfig.workFlowJson)
        def startNode = workFlowConfigMap.workFlowNode[workFlowConfigMap.workFlowNodeDic[startNodeCode]]
        eiaWorkFlowBusi.eiaWorkFlowId = workFlowConfigMap.eiaWorkFlowId
        eiaWorkFlowBusi.workFlowTitle = eiaCert.projectName
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
     * 资质工作流结束
     * @return
     */
    def nextWorkFlowNodeCertEnd(Long eiaWorkFlowBusiId, String nodesCode, String opinion, String processCode, String approvalDate,Long version, session) {
        def eiaWorkFlowBusi = eiaWorkFlowService.nextWorkFlowNode(eiaWorkFlowBusiId, nodesCode, opinion, processCode, approvalDate, version,session)
        if(eiaWorkFlowBusi.code ==HttpMesConstants.CODE_FAIL){
            return eiaWorkFlowBusi
        }
        if (eiaWorkFlowBusi.data.tableName == GeneConstants.DOMAIN_EIA_CERT) {
            eiaFileUploadService.UpdateFileReadOnly(eiaWorkFlowBusi.data.tableName,Long.valueOf(eiaWorkFlowBusi.data.tableNameId))
            def eiaCert = EiaCert.findById(Long.valueOf(eiaWorkFlowBusi.data.tableNameId))
            eiaCert.ifEnd = true
            eiaCert.save(flush: true, failOnError: true)
        }
        return eiaWorkFlowBusi
    }

    /***
     * 获取合规审核、总经理审核、财务审核、部门负责人审核
     */
    def getReportList(eiaCertId, nodeList) {
        def resMap = [:]
        nodeList.each {
            def eiaWorkFlowBusiList = EiaWorkFlowBusiLog.findAllByTableNameAndTableNameIdAndNodesCodeAndIfDel(GeneConstants.DOMAIN_EIA_CERT
                    , eiaCertId, it,false, [sort: "dateCreated", order: "DESC"])
            if (eiaWorkFlowBusiList && eiaWorkFlowBusiList.size() > 0) {
                def eiaWorkFlowBusiLog = eiaWorkFlowBusiList.get(0);
                def param = [:]
                param.staffId = (eiaWorkFlowBusiLog.updateUserId).toString()
                def staffJson = HttpConnectTools.getResponseJson(HttpUrlConstants.SINGLE_EIA_AUTH_STAFF_INFO, param)
                def signImagePath
                if (staffJson) {
                    def staff = JsonHandler.jsonToMap(staffJson).data[0]
                    if (staff.signImagePath) {
                        signImagePath = GeneConstants.AUTH_FILE_URL_PATH + staff.signImagePath
                    }
                }
                resMap.put(it, signImagePath)
            }
        }
        return resMap
    }
}
