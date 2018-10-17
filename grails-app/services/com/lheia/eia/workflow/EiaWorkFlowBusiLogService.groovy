package com.lheia.eia.workflow

import com.lheia.eia.common.WorkFlowConstants
import grails.gorm.transactions.Transactional

@Transactional
class EiaWorkFlowBusiLogService {
    /**
     * 根据流程对象id获取流程日志
     * 当传入staffId时为与我相关数据
     * @param params
     * @return
     */
    def getEiaWorkFlowBusiLogDataList(params) {
        int page
        int limit
        if (params.page && params.limit) {
            page = params.int('page') - 1
            limit = params.int('limit')
        }
        def eiaWorkFlowBusi = EiaWorkFlowBusi.createCriteria().get(){
            eq('tableName',params.tableName)
            eq('tableNameId',Long.parseLong(params.tableNameId))
            not { 'in'('workFlowState', [WorkFlowConstants.WORKFLOW_HALT]) }
            eq("ifDel",false)
        }
        def eiaWorkFlowBusiId = eiaWorkFlowBusi.id
        def logList = EiaWorkFlowBusiLog.createCriteria().list(max: limit, offset: page * limit) {
            eq("ifDel",false)
            eq("tableName", params.tableName)
            eq("tableNameId", Long.valueOf(params.tableNameId))
            eq("eiaWorkFlowBusiId", eiaWorkFlowBusiId)
            if (params.staffId) {
                eq("updateUserId", Long.valueOf(params.staffId))
            }
            order("dateCreated")
        }
        def resList = []
        logList.each {
            def resMap = [:]
            resMap.workFlowName = it.workFlowName
            resMap.nodesName = it.nodesName
            resMap.processName = it.processName
            resMap.opinion = it.opinion
            resMap.inputDept = it.updateDept
            resMap.inputUser = it.updateUser
            resMap.signImg = it.inputUserSign
            resMap.receiver = it.receiver
            resMap.approvalDate = it.approvalDate.format("yyyy-MM-dd")
            resList << resMap
        }
        def dataMap = [:]
        dataMap.data = resList
        dataMap.total = logList.totalCount
        return dataMap
    }
}
