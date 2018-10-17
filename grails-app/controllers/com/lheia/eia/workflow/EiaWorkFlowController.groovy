package com.lheia.eia.workflow

import com.lheia.eia.common.HttpMesConstants
import grails.converters.JSON

class EiaWorkFlowController {

    def eiaWorkFlowService
    def eiaWorkFlowConfigService
    def eiaWorkFlowStateIndex(){}
    def eiaWorkFlowIndex() {
        render(view: "/eiaWorkFlow/eiaWorkFlowIndex.gsp")
    }

    def eiaWorkFlowCreate() {
        [pageType: params.pageType]
    }

    def eiaWorkFlowDetail() {
        render(view: "/eiaWorkFlow/eiaWorkFlowDetail.gsp")
    }

    def eiaWorkFlowConfigImport(){

    }

    def eiaWorkFlowTrans() {}

    def eiaWorkFlowSubmit() {}

    def eiaWorkFlowConfirm() {}

    def eiaWorkFlowHalt(){}

    def eiaWorkFlowProjectModi() {}

    def eiaWorkFlowProjectContract(){}

    /**
     * 项目列表工作流页面
     */
    def eiaWorkFlowProject() {}

    def eiaWorkFlowProjectConfirm() {}

    def eiaWorkFlowProjectSubmit() {}

    def eiaWorkFlowProjectReback(){}

    def eiaWorkFlowConfirmNoReason(){}

    /**
     * 财务审核列表
     */
    def eiaWorkFlowCertFinance(){}
    def workFlowConfigJsonParse(){
        def workFlowConfig = eiaWorkFlowConfigService.workFlowConfigJsonParse(params.workFlowConfig)
        render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SAVE_OK] as JSON)
    }

    /**
     * 工作流程新增和修改
     */
    def eiaWorkFlowSave() {
        /** 工作流程判重 */
        def checkResult = eiaWorkFlowService.checkEiaWorkFlow(params)
        if (checkResult == HttpMesConstants.MSG_DATA_EXIST) {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_EXIST] as JSON)
        } else {
            if (!params.eiaWorkFlowId) {
                def dataMap = eiaWorkFlowService.eiaWorkFlowSave(params)
                if (dataMap) {
                    render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SAVE_OK, data: dataMap] as JSON)
                } else {
                    render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
                }
            } else {
                def dataMap = eiaWorkFlowService.eiaWorkFlowUpdate(params)
                if (dataMap) {
                    render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SAVE_OK, data: dataMap] as JSON)
                } else {
                    render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
                }
            }
        }
    }
    /**
     * 工作流程详情
     */
    def getEiaWorkFlowDataMap() {
        def dataMap = eiaWorkFlowService.getEiaWorkFlowDataMap(params.long('eiaWorkFlowId'))
        if (dataMap) {
            render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SAVE_OK, data: dataMap] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
        }
    }
    /**
     * 工作流程删除
     */
    def eiaWorkFlowDel() {
        if (eiaWorkFlowService.eiaWorkFlowDel(params)) {
            render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_DEL_OK] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DEL_FAIL] as JSON)
        }
    }
    /**
     * 工作流程分页查询
     */
    def getEiaWorkFlowDataList() {
        def dataMap = eiaWorkFlowService.eiaWorkFlowQuery(params)
        if (dataMap) {
            render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_DEL_OK, count: dataMap.total, data: dataMap.data] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DEL_FAIL] as JSON)
        }
    }
    /**
     * 提交工作流程
     */
    def submitEiaWorkFlow() {
        def workFlowNodes = EiaWorkFlowNode.findByEiaWorkFlowIdAndIfDel(params.long('eiaWorkFlowId'),false)
        if(!workFlowNodes){
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_FCONF_NODE_NULL] as JSON)
        }else{
            def workFlowJson = eiaWorkFlowConfigService.workFlowConfigJSONAssemble(params.long('eiaWorkFlowId'))
            if (workFlowJson) {
                def dataMap = eiaWorkFlowConfigService.eiaWorkFlowConfigSave(params.long('eiaWorkFlowId'), workFlowJson)
                if (dataMap) {
                    render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SAVE_OK] as JSON)
                } else {
                    render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
                }
            }
        }
    }
/**
 * 提交到下个节点
 * @return
 */
    def nextWorkFlowNode() {
        def resMap = eiaWorkFlowService.nextWorkFlowNode(Long.valueOf(params.eiaWorkFlowBusiId), params.processUrlParams
                , params.opinion, params.processCode, params.approvalDate,Long.valueOf(params.version), session)
        render(resMap as JSON)
    }

    /**
     * 流程终止
     */
    def workFlowHalt(){
        def resMap = eiaWorkFlowService.workFlowHalt(Long.valueOf(params.eiaWorkFlowBusiId),params.opinion,params.approvalDate,Long.valueOf(params.version),session)
        render(resMap as JSON)
    }


}
