package com.lheia.eia.workflow

import com.lheia.eia.common.GeneConstants
import com.lheia.eia.common.HttpMesConstants
import com.lheia.eia.config.EiaFileUpload
import com.lheia.eia.contract.EiaContract
import com.lheia.eia.lab.EiaLabOffer
import com.lheia.eia.project.EiaProject
import grails.converters.JSON

class EiaWorkFlowContractController {
    def eiaWorkFlowService
    def eiaFileUploadService
    /***
     * 合同流程结束
     */
    def nextWorkFlowNodeEnd(){
        long eiaContractId = EiaWorkFlowBusi.findByIdAndIfDel(Long.valueOf(params.eiaWorkFlowBusiId),false).tableNameId
        def eiaContract = EiaContract.findByIdAndIfDel(eiaContractId,false)
        boolean subEnable = true

        if(eiaContract.enviroMonitoringFee||eiaContract.groundWater) {

//            def eiaProjectIdList = EiaProject.findAllByEiaContractIdAndIfDel(eiaContractId,false).id
//            boolean needWWHT = false
//            /***查询合同关联项目再通过项目关联监测报价通过监测报价查询是否有非宇相的监测合同若有则需要上传外委合同才能进行合同归档操作***/
//            if(eiaProjectIdList){
//                def eiaLabOffer = EiaLabOffer.findAllByEiaProjectIdInListAndIfDel(eiaProjectIdList,false)
//                eiaLabOffer.each {
//                    if(!it.ifYxTest){
//                        needWWHT = true
//                    }
//                }
//                if(needWWHT){
//                    def fileList = EiaFileUpload.findAllByTableNameAndTableIdAndFileUploadType(GeneConstants.DOMAIN_EIA_CONTRACT, eiaContractId, GeneConstants.CONTRACT_FILT_TYPE_WWHT)
//                    if (!fileList) {
//                        subEnable = false
//                        render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_CON_FLOW_NO_WWHT] as JSON)
//                        return
//                    }
//                }
//            }else{
//                render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_CON_FLOW_NO_PRO] as JSON)
//                return
//            }


            def contractList = EiaFileUpload.findAllByTableNameAndTableIdAndFileUploadType(GeneConstants.DOMAIN_EIA_CONTRACT, eiaContractId, GeneConstants.CONTRACT_FILT_TYPE_HTSMJ)
            if (!contractList) {
                subEnable = false
                render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_CON_FLOW_NO_HSSMB] as JSON)
                return
            }
        }
        if(subEnable){
            def resMap = eiaWorkFlowService.nextWorkFlowNode(Long.valueOf(params.eiaWorkFlowBusiId), params.processUrlParams
                    , params.opinion, params.processCode, params.approvalDate,Long.valueOf(params.version), session)
            if(resMap&&resMap.code == HttpMesConstants.CODE_OK){
                def nodeData = resMap.data
                eiaFileUploadService.UpdateFileReadOnly(nodeData.tableName,nodeData.tableNameId)
            }
            render(resMap as JSON)
        }
    }

}
