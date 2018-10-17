package com.lheia.eia.workflow

import com.lheia.eia.common.HttpMesConstants
import grails.converters.JSON

class EiaWorkFlowNodeProcessController {

    def eiaWorkFlowNodeProcessService

    def eiaWorkFlowNodeProCreate() {
        render (view: "/eiaWorkFlow/eiaWorkFlowNodeProCreate.gsp")
    }
    def eiaWorkFlowNodeProDetail() {
        render (view: "/eiaWorkFlow/eiaWorkFlowNodeProDetail.gsp")
    }

    /**
     * 工作流程节点动作新增和修改
     */
    def eiaWorkFlowNodeProcessSave() {
        if (!params.eiaWorkFlowNodeProcessId) {
            def dataMap = eiaWorkFlowNodeProcessService.eiaWorkFlowNodeProcessSave(params)
            if (dataMap) {
                render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SAVE_OK, data: dataMap] as JSON)
            } else {
                render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
            }
        } else {
            def dataMap = eiaWorkFlowNodeProcessService.eiaWorkFlowNodeProcessUpdate(params)
            if (dataMap) {
                render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SAVE_OK, data: dataMap] as JSON)
            } else {
                render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
            }
        }
    }
    /**
     * 工作流程节点动作详情
     */
    def getEiaWorkFlowNodeProcessDataMap() {
        def dataMap = eiaWorkFlowNodeProcessService.getEiaWorkFlowNodeProcessDataMap(params.long('eiaWorkFlowNodeProcessId'))
        if (dataMap) {
            render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SAVE_OK, data: dataMap] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
        }
    }
    /**
     * 工作流程节点动作删除
     */
    def eiaWorkFlowNodeProcessDel() {
        if (eiaWorkFlowNodeProcessService.eiaWorkFlowNodeProcessDel(params)) {
            render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_DEL_OK] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DEL_FAIL] as JSON)
        }
    }
    /**
     * 工作流程节点动作分页查询
     */
    def getEiaWorkFlowNodeProcessDataList() {
        def dataMap = eiaWorkFlowNodeProcessService.eiaWorkFlowNodeProcessQuery(params)
        if (dataMap) {
            render([code: HttpMesConstants.CODE_OK, count: dataMap.total, data: dataMap.data] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
}
