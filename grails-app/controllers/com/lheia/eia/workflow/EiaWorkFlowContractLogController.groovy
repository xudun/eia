package com.lheia.eia.workflow

import com.lheia.eia.common.GeneConstants
import com.lheia.eia.common.HttpMesConstants
import com.lheia.eia.contract.EiaContractLog
import grails.converters.JSON

class EiaWorkFlowContractLogController {
    def eiaContractLogService
    def eiaWorkFlowService
    /**
     * 提交到下个节点
     * @return
     */
    def nextWorkFlowNodeEnd() {
       def resMap = eiaWorkFlowService.nextWorkFlowNode(Long.valueOf(params.eiaWorkFlowBusiId), params.processUrlParams
                , params.opinion, params.processCode, params.approvalDate,Long.valueOf(params.version), session)
        if(resMap.code == HttpMesConstants.CODE_OK && resMap?.data?.tableName == GeneConstants.DOMAIN_EIA_CONTRACT_LOG){
            /**
             * 合同流程结束后清除合同相关数据
             */
            def eiaContractLog = EiaContractLog.findByIdAndIfDel(resMap.data.tableNameId,false)
            eiaContractLogService.contractWorkFlowHalt(eiaContractLog.eiaContractId)
        }
        render(resMap as JSON)
    }


}
