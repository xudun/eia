package com.lheia.eia.workflow

import com.lheia.eia.common.GeneConstants
import com.lheia.eia.common.HttpUrlConstants
import com.lheia.eia.tools.HttpConnectTools
import grails.gorm.transactions.Transactional

@Transactional
class EiaWorkFlowWeaverService {
    /**
     * 发送待办消息
     */
    def sendToDoWorkflowMsg(EiaWorkFlowBusi eiaWorkFlowBusi) {
        def responseData = [:]
        if (eiaWorkFlowBusi.tableName == GeneConstants.DOMAIN_EIA_PROJECT) {
            responseData.recieverId = eiaWorkFlowBusi.authCode       //提交到的人
            responseData.workFlowBusiId = String.valueOf(eiaWorkFlowBusi.id)
            responseData.workFlowName = eiaWorkFlowBusi.workFlowName
            responseData.nodesName = eiaWorkFlowBusi.nodesName
            responseData.tableName = eiaWorkFlowBusi.tableName
            responseData.tableNameId = String.valueOf(eiaWorkFlowBusi.tableNameId)
            responseData.updateUserId = String.valueOf(eiaWorkFlowBusi.updateUserId) //提交人
        } else {
            responseData.workFlowBusiId = String.valueOf(eiaWorkFlowBusi.id)
            responseData.workFlowName = eiaWorkFlowBusi.workFlowName
            responseData.nodesName = eiaWorkFlowBusi.nodesName
            responseData.tableName = eiaWorkFlowBusi.tableName
            responseData.tableNameId = String.valueOf(eiaWorkFlowBusi.tableNameId)
            responseData.updateUserId = String.valueOf(eiaWorkFlowBusi.updateUserId) //提交人
            responseData.authCode = eiaWorkFlowBusi.authCode
            responseData.authType = eiaWorkFlowBusi.authType
            responseData.inputUserId = String.valueOf(eiaWorkFlowBusi.inputUserId)
            responseData.inputDeptId = String.valueOf(eiaWorkFlowBusi.inputDeptId)
        }


        if (HttpUrlConstants.WF_API_URL) {
            Thread todoWorkFlow = new Thread(new Runnable() {
                @Override
                void run() {
                    HttpConnectTools.getResponseJson(HttpUrlConstants.TODOWF, responseData)
                }
            })
            todoWorkFlow.start()
        } else {
            return true
        }
    }
    /**
     * 发送已办消息
     */
    def sendDoneWorkFlowMsg(EiaWorkFlowBusi eiaWorkFlowBusi, String currNodeName, String currAuthCode, String currAuthType, session) {
        def responseData = [:]
        if (eiaWorkFlowBusi.tableNameId == GeneConstants.DOMAIN_EIA_PROJECT) {
            responseData.workFlowBusiId = String.valueOf(eiaWorkFlowBusi.id)
            responseData.workFlowName = eiaWorkFlowBusi.workFlowName
            responseData.nodesName = currNodeName
            responseData.updateUserId = session.staff.staffId
        } else {
            responseData.workFlowBusiId = String.valueOf(eiaWorkFlowBusi.id)
            responseData.workFlowName = eiaWorkFlowBusi.workFlowName
            responseData.nodesName = currNodeName
            responseData.tableName = eiaWorkFlowBusi.tableName
            responseData.tableNameId = String.valueOf(eiaWorkFlowBusi.tableNameId)
            responseData.updateUserId = session.staff.staffId
            responseData.authCode = currAuthCode
            responseData.authType = currAuthType
            responseData.inputUserId = String.valueOf(eiaWorkFlowBusi.inputUserId)
            responseData.inputDeptId = String.valueOf(eiaWorkFlowBusi.inputDeptId)
        }

        if (HttpUrlConstants.WF_API_URL) {
            Thread doneWorkFlow = new Thread(new Runnable() {
                @Override
                void run() {
                    HttpConnectTools.getResponseJson(HttpUrlConstants.DONEWF, responseData)
                }
            })
            doneWorkFlow.start()
        } else {
            return true
        }
    }

    /**
     * 发送办结消息
     */
    def sendOverWorkFlowMsg(EiaWorkFlowBusi eiaWorkFlowBusi, String currNodeName, String currAuthCode, String currAuthType, session) {
        def responseData = [:]
        if (eiaWorkFlowBusi.tableNameId == GeneConstants.DOMAIN_EIA_PROJECT) {
            responseData.workFlowBusiId = String.valueOf(eiaWorkFlowBusi.id)
            responseData.workFlowName = eiaWorkFlowBusi.workFlowName
            responseData.nodesName = currNodeName
            responseData.updateUserId = session.staff.staffId
        } else {
            responseData.workFlowBusiId = String.valueOf(eiaWorkFlowBusi.id)
            responseData.workFlowName = eiaWorkFlowBusi.workFlowName
            responseData.nodesName = currNodeName
            responseData.tableName = eiaWorkFlowBusi.tableName
            responseData.tableNameId = String.valueOf(eiaWorkFlowBusi.tableNameId)
            responseData.updateUserId = session.staff.staffId
            responseData.authCode = currAuthCode
            responseData.authType = currAuthType
            responseData.inputUserId = String.valueOf(eiaWorkFlowBusi.inputUserId)
            responseData.inputDeptId = String.valueOf(eiaWorkFlowBusi.inputDeptId)
        }
        if (HttpUrlConstants.WF_API_URL) {
            Thread overWorkFlow = new Thread(new Runnable() {
                @Override
                void run() {
                    HttpConnectTools.getResponseJson(HttpUrlConstants.OVERWF, responseData)
                }
            })
            overWorkFlow.start()
        } else {
            return true
        }
    }
}
