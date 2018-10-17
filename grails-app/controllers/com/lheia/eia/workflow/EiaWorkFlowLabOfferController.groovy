package com.lheia.eia.workflow

import com.lheia.eia.common.HttpMesConstants
import com.lheia.eia.common.HttpUrlConstants
import com.lheia.eia.lab.EiaLabOffer
import com.lheia.eia.tools.HttpConnectTools
import com.lheia.eia.tools.JsonHandler
import grails.converters.JSON
import grails.gorm.transactions.Transactional

@Transactional
class EiaWorkFlowLabOfferController {

    def eiaWorkFlowService
    def eiaLabOfferService
    def eiaWorkFlowLabOfferService

    /**
     * 提交到下个节点
     * @return
     */
    def nextWorkFlowNode() {
        def eiaWorkFlowBusiId = params.long('eiaWorkFlowBusiId')
        def eiaWorkFlowBusi = EiaWorkFlowBusi.findById(eiaWorkFlowBusiId)
        def eiaLabOffer = EiaLabOffer.findById(eiaWorkFlowBusi?.tableNameId)
        if (!eiaLabOffer?.eiaProjectId) {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_NOT_CHOOSE_PROJECT] as JSON)
        } else {
            def resMap = eiaWorkFlowService.nextWorkFlowNode(Long.valueOf(params.eiaWorkFlowBusiId), params.processUrlParams, params.opinion, params.processCode, params.approvalDate,Long.valueOf(params.version), session)
            render(resMap as JSON)
        }
    }

    /**
     * 监测方案流程中止
     */
    def workFlowHalt(){
        def resMap = eiaWorkFlowService.workFlowHalt(Long.valueOf(params.eiaWorkFlowBusiId),params.opinion,params.approvalDate,Long.valueOf(params.version),session)
        if (resMap) {
            def eiaWorkFlowBusi = EiaWorkFlowBusi.findByIdAndIfDel(Long.valueOf(params.eiaWorkFlowBusiId), false)
            if (eiaWorkFlowBusi) {
                /** 保留监测方案基本信息 */
                def eiaLabOffer = eiaLabOfferService.eiaLabOfferDelPartData(eiaWorkFlowBusi.tableNameId)
                if (eiaLabOffer) {
                    def param = [:]
                    param.labInnerOfferId = eiaLabOffer.id.toString()
                    def resultJson = HttpConnectTools.getResponseJson(HttpUrlConstants.DEL_LAB_OFFER, param)
                    if (resultJson) {
                        def resultCode = JsonHandler.jsonToMap(resultJson).code
                        if (resultCode == HttpMesConstants.CODE_FAIL) {
                            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SUBMIT_FAIL] as JSON)
                        }
                    }
                }
            }
        }
        render(resMap as JSON)
    }

    /**
     * 监测方案流程结束
     * @return
     */
    def nextWorkFlowNodeLabOfferEnd() {
        def resMap = eiaWorkFlowLabOfferService.nextWorkFlowNodeLabOfferEnd(Long.valueOf(params.eiaWorkFlowBusiId), params.processUrlParams, params.opinion, params.processCode, params.approvalDate,Long.valueOf(params.version), session)
        render(resMap as JSON)
    }
}
