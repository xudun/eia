package com.lheia.eia.workflow

import grails.gorm.transactions.Transactional

@Transactional
class EiaWorkFlowNodeService {
    /**
     * 保存工作流节点
     * @param params
     * @return
     */
    def eiaWorkFlowNodeSave(params) {
        def eiaWorkFlowNode = new EiaWorkFlowNode(params)
        eiaWorkFlowNode.eiaWorkFlowId = params.long('eiaWorkFlowId')
        eiaWorkFlowNode.save(flush: true, failOnError: true)
    }
    /**
     * 工作流节点修改
     * @param params
     */
    def eiaWorkFlowNodeUpdate(params) {
        def eiaWorkFlowNode = EiaWorkFlowNode.findById(params.long('eiaWorkFlowNodeId'))
        if (eiaWorkFlowNode) {
            eiaWorkFlowNode.properties = params
            eiaWorkFlowNode.save(flush: true, failOnError: true)
        }
    }
    /**
     * 工作流节点详情
     */
    def getEiaWorkFlowNodeDataMap(eiaWorkFlowNodeId) {
        return EiaWorkFlowNode.findById(eiaWorkFlowNodeId)
    }
    /**
     * 工作流节点删除
     * @param params
     */
    def eiaWorkFlowNodeDel(params) {
        def eiaWorkFlowNodeId = params.long('eiaWorkFlowNodeId')
        def eiaWorkFlowNode = EiaWorkFlowNode.findById(eiaWorkFlowNodeId)
        if (eiaWorkFlowNode) {
            eiaWorkFlowNode.ifDel = true
            if (eiaWorkFlowNode.save(flush: true, failOnError: true)) {
                /** 删除节点下的节点动作 */
                def processList = EiaWorkFlowNodeProcess.findAllByEiaWorkFlowNodeIdAndIfDel(eiaWorkFlowNodeId, false)
                if (processList) {
                    processList.each {
                        it.ifDel = true
                        it.save(flush: true, failOnError: true)
                    }
                }
            }
        }
    }
    /**
     * 工作流程节点分页查询
     * @param params
     */
    def eiaWorkFlowNodeQuery(params) {
        int page
        int limit
        if (params.page && params.limit) {
            page = params.int('page') - 1
            limit = params.int('limit')
        }
        def nodeList = EiaWorkFlowNode.createCriteria().list(max: limit, offset: page * limit) {
            eq("eiaWorkFlowId", params.long('eiaWorkFlowId'))
            eq("ifDel", false)
        }
        def data = []
        nodeList.each {
            def map = [:]
            map.id = it.id
            map.nodesCode = it?.nodesCode
            map.nodesNum = it?.nodesNum
            map.nodesName = it?.nodesName
            map.nodesAuthType = it?.nodesAuthType
            map.nodesAuthCode = it?.nodesAuthCode
            map.nodesIconName = it?.nodesIconName
            map.nodesColor = it?.nodesColor
            data << map
        }
        def dataMap = [:]
        dataMap.data = data
        dataMap.total = nodeList.totalCount
        return dataMap
    }
    /**
     * 根据流程Id获取所有节点
     * @param params
     * @return
     */
    def getEiaWorkFlowNodeDataList(params) {
        if (params.eiaWorkFlowId) {
            return EiaWorkFlowNode.findAllByEiaWorkFlowIdAndIfDel(Long.valueOf(params.eiaWorkFlowId), false)
        } else {
            return false
        }
    }
}
