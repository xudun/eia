package com.lheia.eia.workflow

import com.lheia.eia.common.HttpMesConstants
import grails.converters.JSON

class EiaWorkFlowNodeController {

    def eiaWorkFlowNodeService

    def eiaWorkFlowNodeCreate() {
        render (view: "/eiaWorkFlow/eiaWorkFlowNodeCreate.gsp", model: [pageType: params.pageType])
    }
    def eiaWorkFlowNodeDetail() {
        render (view: "/eiaWorkFlow/eiaWorkFlowNodeDetail.gsp")
    }

    /**
     * 工作流程节点新增和修改
     */
    def eiaWorkFlowNodeSave() {
        if (!params.eiaWorkFlowNodeId) {
            def dataMap = eiaWorkFlowNodeService.eiaWorkFlowNodeSave(params)
            if (dataMap) {
                render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SAVE_OK, data: dataMap] as JSON)
            } else {
                render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
            }
        } else {
            def dataMap = eiaWorkFlowNodeService.eiaWorkFlowNodeUpdate(params)
            if (dataMap) {
                render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SAVE_OK, data: dataMap] as JSON)
            } else {
                render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
            }
        }
    }
    /**
     * 工作流程节点详情
     */
    def getEiaWorkFlowNodeDataMap() {
        def dataMap = eiaWorkFlowNodeService.getEiaWorkFlowNodeDataMap(params.long('eiaWorkFlowNodeId'))
        if (dataMap) {
            render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SAVE_OK, data: dataMap] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
        }
    }
    /**
     * 工作流程节点删除
     */
    def eiaWorkFlowNodeDel() {
        if (eiaWorkFlowNodeService.eiaWorkFlowNodeDel(params)) {
            render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_DEL_OK] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DEL_FAIL] as JSON)
        }
    }
    /**
     * 工作流程节点分页查询
     */
    def getEiaWorkFlowNodeDataList() {
        def dataMap = eiaWorkFlowNodeService.eiaWorkFlowNodeQuery(params)
        if (dataMap) {
            render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_DEL_OK, count: dataMap.total, data: dataMap.data] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DEL_FAIL] as JSON)
        }
    }
}
