package com.lheia.eia.workflow

import com.lheia.eia.tools.JsonHandler
import grails.converters.JSON
import grails.gorm.transactions.Transactional

@Transactional
class EiaWorkFlowConfigService {

    /**
     * 需要拼装的流程
     * @param eiaWorkFlowId
     */
    def workFlowConfigJSONAssemble(Long eiaWorkFlowId) {

        def eiaWorkFlow = EiaWorkFlow.findByIdAndIfDel(eiaWorkFlowId, false)
        def eiaWorkFlowMap = [:]
        eiaWorkFlowMap.putAll(eiaWorkFlow.properties)
        eiaWorkFlowMap.eiaWorkFlowId = eiaWorkFlow.id
        def nodeList = []
        eiaWorkFlowMap.workFlowNode = nodeList
        eiaWorkFlowMap.workFlowNodeDic = [:]
        def eiaWorkFlowNodeList = EiaWorkFlowNode.createCriteria().list() {
            eq("eiaWorkFlowId", eiaWorkFlowId)
            eq("ifDel", false)
            order("nodesNum")
        }
        /***
         * 遍历节点
         */
        for(int i=0;i<eiaWorkFlowNodeList.size();i++){
            def nodeMap = [:]
            nodeMap.putAll(eiaWorkFlow.properties)
            nodeMap.eiaWorkFlowId = eiaWorkFlow.id
            nodeMap.putAll(eiaWorkFlowNodeList.get(i).properties)
            nodeMap.eiaWorkFlowNodeId = eiaWorkFlowNodeList.get(i).id
            eiaWorkFlowMap.workFlowNodeDic.put(eiaWorkFlowNodeList.get(i).nodesCode, i)
            def processList = EiaWorkFlowNodeProcess.findAllByEiaWorkFlowIdAndEiaWorkFlowNodeIdAndIfDel(eiaWorkFlowId, eiaWorkFlowNodeList.get(i).id, false,[sort:"processNum"])
            nodeMap.nodeProcessMap = [:]
            /***
             * 遍历按钮配置
             */
            processList.each {
                nodeMap.nodeProcessMap.put(it.processCode, it)
            }
            nodeList.add(nodeMap)
        }
        return eiaWorkFlowMap as JSON
    }

    def workFlowConfigJsonParse(String json){
        Map configMap = JsonHandler.jsonToMap(json)
        /****保存定义工作流****/
        def workFlow = new EiaWorkFlow()
        workFlow.properties = configMap
        workFlow.save(flush: true, failOnError: true)
        def eiaWorkFlowId = workFlow.id
        configMap.workFlowNode.each{
            /***保存工作流节点***/
            def workFlowNode = new EiaWorkFlowNode()
            workFlowNode.properties = it
            workFlowNode.eiaWorkFlowId = eiaWorkFlowId
            workFlowNode.save(flush: true, failOnError: true)
            def eiaWorkFlowNodeId = workFlowNode.id
            it.nodeProcessMap.each{
                /***保存工作流节点配置按钮***/
                def workFlowNodeProcess = new EiaWorkFlowNodeProcess()
                workFlowNodeProcess.properties = it.value
                workFlowNodeProcess.eiaWorkFlowNodeId = eiaWorkFlowNodeId
                workFlowNodeProcess.eiaWorkFlowId = eiaWorkFlowId
                workFlowNodeProcess.save(flush: true, failOnError: true)
            }
        }
    }
    def getEiaWorkFlowConfigDataMap(params){
        if(params.eiaWorkFlowConfigId){
           return EiaWorkFlowConfig.findByIdAndIfDelAndIfValid(Long.valueOf(params.eiaWorkFlowConfigId),false,true)
        }else if(params.workFlowCode){
            return EiaWorkFlowConfig.findByWorkFlowCodeAndIfDelAndIfValid(params.workFlowCode,false,true)
        }
    }

    def eiaWorkFlowConfigSave(eiaWorkFlowId, workFlowJson) {
        def eiaWorkFlow = EiaWorkFlow.findById(eiaWorkFlowId)
        if (eiaWorkFlow) {
            def eiaWorkFlowConfig
                eiaWorkFlowConfig = EiaWorkFlowConfig.findByEiaWorkFlowId(eiaWorkFlowId)
            if(eiaWorkFlowConfig){
                eiaWorkFlowConfig.properties = eiaWorkFlow.properties
                eiaWorkFlowConfig.eiaWorkFlowId = eiaWorkFlowId
                eiaWorkFlowConfig.ifValid = true
                eiaWorkFlowConfig.workFlowJson = workFlowJson
                eiaWorkFlowConfig.save(flush: true, failOnError: true)
            }else{
                eiaWorkFlowConfig = new EiaWorkFlowConfig()
                eiaWorkFlowConfig.properties = eiaWorkFlow.properties
                eiaWorkFlowConfig.eiaWorkFlowId = eiaWorkFlowId
                eiaWorkFlowConfig.ifValid = true
                eiaWorkFlowConfig.workFlowJson = workFlowJson
                eiaWorkFlowConfig.save(flush: true, failOnError: true)
            }
        }
    }
}
