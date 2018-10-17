package com.lheia.eia.workflow

import grails.converters.JSON

class EiaWorkFlowCertController {
    def eiaWorkFlowCertService
    /**
     * 提交到下个节点
     * @return
     */
    def nextWorkFlowNode() {
       def resMap = eiaWorkFlowCertService.nextWorkFlowNodeCertEnd(Long.valueOf(params.eiaWorkFlowBusiId), params.processUrlParams
                , params.opinion, params.processCode, params.approvalDate,Long.valueOf(params.version), session)
        render(resMap as JSON)
    }


}
