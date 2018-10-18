package com.lheia.eia.workflow

import com.lheia.eia.common.FuncConstants
import com.lheia.eia.common.GeneConstants
import com.lheia.eia.common.WorkFlowConstants
import com.lheia.eia.stamp.EiaStamp
import com.lheia.eia.tools.DateTransTools
import com.lheia.eia.tools.JsonHandler
import grails.gorm.transactions.Transactional

@Transactional
class EiaWorkFlowStampService {
    def eiaWorkFlowWeaverService
    /**
     * 开启资质工作流
     * @param eiaWorkFlowConfigId
     * @param tableName
     * @param tableNameId
     * @param startNodeCode
     * @return
     */
    def startStampWorkFlow(String eiaWorkFlowCode, String tableName, Long tableNameId, String startNodeCode, session) {
        def eiaWorkFlowBusi = new EiaWorkFlowBusi()
        def eiaStamp = EiaStamp.findByIfDelAndId(false, tableNameId)
        def workFlowConfigList = EiaWorkFlowConfig.findAllByWorkFlowCodeAndIfDelAndIfValid(eiaWorkFlowCode, false, true, [sort: "workFlowVersion", order: "desc"])
        if (!workFlowConfigList) {
            return false
        }
        def workFlowConfig = workFlowConfigList.get(0)
        def workFlowConfigMap = JsonHandler.jsonToMap(workFlowConfig.workFlowJson)
        def startNode = workFlowConfigMap.workFlowNode[workFlowConfigMap.workFlowNodeDic[startNodeCode]]
        eiaWorkFlowBusi.eiaWorkFlowId = workFlowConfigMap.eiaWorkFlowId
        eiaWorkFlowBusi.workFlowTitle = session.staff.orgName + session.staff.staffName+'的申请'
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

        if (session.staff.orgName == '联合泰泽行政部' && eiaWorkFlowCode == 'STAMP_WORK_FLOW_NOBUSS') {
            def eiaWorkFlowBusiLog = new EiaWorkFlowBusiLog()
            eiaWorkFlowBusiLog.properties = eiaWorkFlowBusi.properties
            eiaWorkFlowBusiLog.eiaWorkFlowBusiId = eiaWorkFlowBusi.id
            eiaWorkFlowBusiLog.opinion = '无'
            eiaWorkFlowBusiLog.approvalDate = new Date()
            eiaWorkFlowBusiLog.inputUserSign = GeneConstants.AUTH_FILE_URL_PATH + GeneConstants.AdminManagerSign
            eiaWorkFlowBusiLog.updateUser = GeneConstants.AdminManagerName
            eiaWorkFlowBusiLog.updateUserId = Long.parseLong(GeneConstants.AdminManagerId)
            eiaWorkFlowBusiLog.processCode == 'BMSH_SUBMIT'
            eiaWorkFlowBusiLog.processName == '提交至行政经理审核'
            eiaWorkFlowBusi.nodesCode = WorkFlowConstants.STAMP_NODE_CODE_XZJLSH
            eiaWorkFlowBusi.nodesName = WorkFlowConstants.STAMP_NODE_NAME_XZJLSH
            eiaWorkFlowBusi.processName = '提交至总经理审核'
            eiaWorkFlowBusi.processCode = 'ZJLSH_SUBMIT'
            eiaWorkFlowBusi.authCode = FuncConstants.EIA_HGGL_YZSH_XZJLSH
            eiaWorkFlowBusi.workFlowState = WorkFlowConstants.WORKFLOW_UNDER_WAY
            eiaWorkFlowBusi.save(flush: true, failOnError: true)
            eiaWorkFlowBusiLog.eiaWorkFlowBusiId = eiaWorkFlowBusi.id
            eiaWorkFlowBusiLog.save(flush: true, failOnError: true)
        }else{
            eiaWorkFlowBusi.save(flush: true, failOnError: true)
        }

        /**————————OA系统发送消息——————————**/
        eiaWorkFlowWeaverService.sendToDoWorkflowMsg(eiaWorkFlowBusi)
        return eiaWorkFlowBusi
    }
}
