package com.lheia.eia.workflow

import grails.gorm.transactions.Transactional

@Transactional
class EiaWorkFlowNodeProcessService {
    /**
     * 节点动作保存
     * @param params
     * @return
     */
    def eiaWorkFlowNodeProcessSave(params) {
        def eiaWorkFlowNodeProcess = new EiaWorkFlowNodeProcess(params)
        eiaWorkFlowNodeProcess.eiaWorkFlowId = params.long('eiaWorkFlowId')
        eiaWorkFlowNodeProcess.eiaWorkFlowNodeId = params.long('eiaWorkFlowNodeId')
        eiaWorkFlowNodeProcess.save(flush:true,failOnError:true)
    }
    /**
     * 节点动作修改
     * @param params
     */
    def eiaWorkFlowNodeProcessUpdate(params) {
        def eiaWorkFlowNodeProcess = EiaWorkFlowNodeProcess.findById(params.long('eiaWorkFlowNodeProcessId'))
        if (eiaWorkFlowNodeProcess) {
            eiaWorkFlowNodeProcess.properties = params
            eiaWorkFlowNodeProcess.save(flush:true,failOnError:true)
        }
    }
    /**
     * 节点动作详情
     * @return
     */
    def getEiaWorkFlowNodeProcessDataMap(eiaWorkFlowNodeProcessId) {
        return EiaWorkFlowNodeProcess.findById(eiaWorkFlowNodeProcessId)
    }
    /**
     * 节点动作删除
     * @param params
     */
    def eiaWorkFlowNodeProcessDel(params) {
        def eiaWorkFlowNodeProcess = EiaWorkFlowNodeProcess.findById(params.long('eiaWorkFlowNodeProcessId'))
        if (eiaWorkFlowNodeProcess) {
            eiaWorkFlowNodeProcess.ifDel = true
            eiaWorkFlowNodeProcess.save(flush:true,failOnError:true)
        }
    }
    /**
     * 工作流程节点动作分页查询
     * @param params
     */
    def eiaWorkFlowNodeProcessQuery(params) {
        int page
        int limit
        if (params.page && params.limit) {
            page = params.int('page') - 1
            limit = params.int('limit')
        }
        def processList = EiaWorkFlowNodeProcess.createCriteria().list(max: limit, offset: page * limit) {
            eq("eiaWorkFlowId", params.long('eiaWorkFlowId'))
            eq("eiaWorkFlowNodeId", params.long('eiaWorkFlowNodeId'))
            eq("ifDel", false)
        }
        def data = []
        processList.each {
            def map = [:]
            map.id = it.id
            map.processName = it?.processName
            map.processShowName = it?.processShowName
            map.processNum = it?.processNum
            map.processCode = it?.processCode
            map.processUrl = it?.processUrl
            map.processUrlParams = it?.processUrlParams
            map.processColor = it?.processColor
            map.processIconName = it?.processIconName
            data << map
        }
        def dataMap = [:]
        dataMap.data = data
        dataMap.total = processList.totalCount
        return dataMap
    }
    /**
     * 根据节点id查询节点动作列表
     * @param params
     * @return
     */
    def getEiaWorkFlowNodeProcessDataList(params){
        if(params.eiaFlowNodeId){
            return EiaWorkFlowNodeProcess.findAllByEiaWorkFlowNodeIdAndIfDel(Long.valueOf(params.eiaFlowNodeId),false)
        }else{
            return false
        }
    }
}
