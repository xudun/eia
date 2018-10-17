package com.lheia.eia.workflow

import com.lheia.eia.common.HttpMesConstants
import com.lheia.eia.common.WorkFlowConstants
import com.lheia.eia.tools.JsonHandler
import grails.converters.JSON

class EiaWorkFlowBusiController {
    def eiaWorkFlowBusiService

    /**
     * 检查是否工作流是否能查看
     */
    def checkWorkFlow() {
        def proFlow = eiaWorkFlowBusiService.checkWorkFlow(params.tableName,Long.valueOf(params.tableNameId))
        if (proFlow) {
            render([code: HttpMesConstants.CODE_OK] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_FLOW_NULL] as JSON)
        }
    }
    /**
     * 流程步骤数据
     * @param params
     * @return
     */
    def getWorkFlowNodeDataList() {
        render([code: HttpMesConstants.CODE_OK, data: eiaWorkFlowBusiService.getWorkFlowNodeDataList(params)] as JSON)
    }
    /**
     * 返回待办事件
     * @return
     */
    def getEiaWorkFlowBusiDataList() {
        def busiMap = eiaWorkFlowBusiService.getEiaWorkFlowBusiDataList(params, session)
        render([code: HttpMesConstants.CODE_OK,count:busiMap.count ,data: busiMap.data] as JSON)
    }

    /**
     * 返回待办事件数量
     * @return
     */
    def getEiaWorkFlowBusiCount() {
        def count = eiaWorkFlowBusiService.getEiaWorkFlowBusiCount(params,session)
        render([code: HttpMesConstants.CODE_OK,count:count] as JSON)
    }

    /**
     * 获取按钮配置
     * 根据权限查询按钮
     * @param params
     * @param session
     * @return
     */
    def getWorkFlowNodeProcessDataList() {
        render([code: HttpMesConstants.CODE_OK, data: eiaWorkFlowBusiService.getWorkFlowNodeProcessDataList(params, session)] as JSON)
    }
    /**
     * 检查当前节点是否存在与工作流中
     * @return
     */
    def checkNodeExist() {
        def eiaWorkFlowBusiId = params.long('eiaWorkFlowBusiId')
        def eiaWorkFlowBusi = EiaWorkFlowBusi.findByIdAndIfDel(eiaWorkFlowBusiId, false)
        def workFlowNodeDic = JsonHandler.jsonToMap(eiaWorkFlowBusi.workFlowJson).workFlowNodeDic
        boolean inDic
        workFlowNodeDic.each { key, value ->
            inDic |= (key == params.processUrlParams)
        }
        if(params.processUrlParams == WorkFlowConstants.NODE_CODE_END||params.processUrlParams ==WorkFlowConstants.NODE_CODE_LCZZ){
            render([code: HttpMesConstants.CODE_OK] as JSON)
        }
        else if (!inDic) {
            render([code: HttpMesConstants.CODE_FAIL, msg: "没有下一个节点"] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_OK] as JSON)
        }
    }
}
