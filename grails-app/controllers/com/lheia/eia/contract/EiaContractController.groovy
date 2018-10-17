package com.lheia.eia.contract

import com.lheia.eia.common.GeneConstants
import com.lheia.eia.common.HttpMesConstants
import com.lheia.eia.common.WorkFlowConstants
import grails.converters.JSON

import javax.servlet.ServletOutputStream
import java.text.SimpleDateFormat

class EiaContractController {
    def eiaContractService
    def eiaDomainCodeService
    def exportContractService
    def eiaWorkFlowContractService
    /**
     * 合同列表显示
     */
    def eiaContractIndex() {}
    /**
     * 合同下载页
     */
    def eiaContractDownload() {}
    /**
     * 合同审批
     */
    def eiaContractProcessIndex() {}
    /**
     * 合同金额
     */
    def eiaContractFillSelect() {}
    /**
     * 报价金额
     */
    def eiaOfferFillSelect() {}
    /**
     * 合同印章申请审批单
     */
    def eiaContractSealApply() {}
    /**
     * 报价印章申请审批单
     */
    def eiaOfferSealApply() {}
    /**
     * 合同信息列表
     */
    def getEiaContractDataList() {
        def dataMap = eiaContractService.eiaContractQueryPage(params, session)
        if (dataMap) {
            render([code: HttpMesConstants.CODE_OK, count: dataMap.total, data: dataMap.data] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
    /**
     * 获取合同列表
     */
    def getEiaContractSelectList(){
        def contractList = eiaContractService.getEiaContractSelectList(params,session)
        if(contractList){
            render([code: HttpMesConstants.CODE_OK, data: contractList] as JSON)
        }else {
            render([code: HttpMesConstants.CODE_FAIL, msg:HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
    /**
     * 合同信息新增
     * @return
     */
    def eiaContractCreate() {
        def eiaContract = eiaContractService.getEiaContractDataMap(params.long("eiaContractId"))
        [eiaContract: eiaContract, pageType: params.pageType, eiaContractId: params.eiaContractId]
    }
    /**
     * 合同信息保存
     */
    def eiaContractSave() {
        if (params.long("eiaContractId")) {
            def eiaContract = eiaContractService.eiaContractUpdate(params, session)
            if (eiaContract) {
                render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SAVE_OK, data: eiaContract] as JSON)
            } else {
                render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
            }
        } else {
            def eiaContract = eiaContractService.eiaContractSave(params, session)
            if (eiaContract) {
                render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SAVE_OK, data: eiaContract] as JSON)
            } else {
                render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
            }
        }
    }
    /**
     *  合同信息回显
     */
    def eiaContractDataMap() {
        def eiaContract = eiaContractService.getEiaContractDataMap(params.long("eiaContractId"))
        if (eiaContract) {
            render([code: HttpMesConstants.CODE_OK, data: eiaContract, contractDate: eiaContract.contractDate?.format("yyyy-MM-dd")] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
    /**
     * 合同信息删除
     */
    def eiaContractDel() {
        def eiaContract = eiaContractService.eiaContractDel(params.long("eiaContractId"))
        if (eiaContract) {
            render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_DEL_OK, data: eiaContract] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DEL_FAIL] as JSON)
        }
    }
    /**
     * 合同信息详情
     */
    def eiaContractDetail() {
        def eiaContract = eiaContractService.getEiaContractDataMap(params.long("eiaContractId"))
        [eiaContract: eiaContract]
    }
    /**
     * 合同详情列表
     * @return
     */
    def getEiaContractDetail() {
        if(params.eiaContractId){
            def eiaContract = eiaContractService.getEiaContractDataMap(params.long("eiaContractId"))
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String contractDate = ""
            if(eiaContract.contractDate){
                contractDate = formatter.format(eiaContract?.contractDate)
            }
            else {
                contractDate = ""
            }
            def contractUse = eiaDomainCodeService.translate(GeneConstants.CONTRACT_USE, eiaContract.contractUse)
            def province = eiaDomainCodeService.translate(GeneConstants.PROVINCE_CODE, eiaContract.province)
            render([code: HttpMesConstants.CODE_OK, data: eiaContract, contractDate: contractDate, contractUse: contractUse, province: province] as JSON)
        }else{
            render([code: HttpMesConstants.CODE_FAIL] as JSON)
        }
    }
    /**
     * 合同印章申请审批单
     * @return
     */
    def contractSealApplyApprove() {
        def dataMap = eiaContractService.contractSealApplyApprove(params)
        if (dataMap) {
            render([code: HttpMesConstants.CODE_OK, data: dataMap] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }

    /**—————————————————————报价功能—————————————————————**/
    /**
     * 报价列表页
     */
    def eiaConOfferIndex() {}
    /**
     * 报价信息列表
     */
    def getEiaOfferDataList() {
        def dataMap = eiaContractService.eiaOfferQueryPage(params, session)
        if (dataMap) {
            render([code: HttpMesConstants.CODE_OK, count: dataMap.total, data: dataMap.data] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
    /**
     * 报价信息新增
     */
    def eiaOfferCreate() {
        def eiaOffer = eiaContractService.getEiaOfferDataMap(params.long("eiaOfferId"))
        [pageType: params.pageType, eiaOfferId: params.eiaOfferId, eiaOffer: eiaOffer]
    }
    /**
     * 报价信息保存
     */
    def eiaOfferSave() {
        if (params.long("eiaOfferId")) {
            def eiaOffer = eiaContractService.eiaOfferUpdate(params, session)
            if (eiaOffer) {
                render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SAVE_OK, data: eiaOffer] as JSON)
            } else {
                render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
            }
        } else {
            def eiaOffer = eiaContractService.eiaOfferSave(params, session)
            if (eiaOffer) {
                render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SAVE_OK, data: eiaOffer] as JSON)
            } else {
                render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
            }
        }
    }
    /**
     *  报价信息回显
     */
    def eiaOfferDataMap() {
        def eiaOffer = eiaContractService.getEiaOfferDataMap(params.long("eiaOfferId"))
        def contractUse = eiaDomainCodeService.translate(GeneConstants.CONTRACT_USE, eiaOffer.contractUse)
        def province = eiaDomainCodeService.translate(GeneConstants.PROVINCE_CODE, eiaOffer.province)
        if (eiaOffer) {
            render([code: HttpMesConstants.CODE_OK, data: eiaOffer, offerDate: eiaOffer.offerDate.format("yyyy-MM-dd"),contractUse:contractUse,province:province] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
    /**
     * 报价信息删除
     */
    def eiaOfferDel() {
        def eiaOffer = eiaContractService.eiaOfferDel(params.long("eiaOfferId"))
        if (eiaOffer) {
            render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_DEL_OK, data: eiaOffer] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DEL_FAIL] as JSON)
        }
    }
    /***
     * 提交合同并开启工作流
     * @return
     */
    def eiaContractSub() {
        long eiaContractId = Long.valueOf(params.eiaContractId)
        def eiaContract = EiaContract.findByIdAndIfDel(eiaContractId,false)
        if(eiaContract.ifSub){
            render([code: HttpMesConstants.CODE_FAIL,msg:HttpMesConstants.MSG_FCONF_NULL] as JSON)
        }else{
            def workFlowCode = eiaWorkFlowContractService.getContractWorkFlowCode(eiaContractId)
            if(workFlowCode){
                def eiaWorkFlowBusi = eiaWorkFlowContractService.startContractWorkFlow(workFlowCode, GeneConstants.DOMAIN_EIA_CONTRACT, eiaContractId, WorkFlowConstants.CONTRACT_WORKFLOW_START_NODE,session)
                if(eiaWorkFlowBusi){
                    eiaContract = eiaContractService.eiaContractSub(eiaContractId)
                    if (eiaContract && eiaWorkFlowBusi) {
                        render([code: HttpMesConstants.CODE_OK, data: eiaContract] as JSON)
                    } else {
                        render([code: HttpMesConstants.CODE_FAIL] as JSON)
                    }
                }else{
                    render([code: HttpMesConstants.CODE_FAIL,msg:HttpMesConstants.MSG_FCONF_NULL] as JSON)
                }

            }else{
                render([code: HttpMesConstants.CODE_FAIL,msg:HttpMesConstants.MSG_CONTRACT_NO_FEE] as JSON)
            }
        }
    }
    /**
     * 报价信息详情
     */
    def eiaOfferDetail() {
        def eiaOffer = eiaContractService.getEiaOfferDataMap(params.long("eiaOfferId"))
        [eiaOffer: eiaOffer]
    }
    /**
     * 生成合同信息
     */
    def eiaContractGenerateSave() {
        def eiaContract = eiaContractService.eiaContractGenerateSave(params, session)
        if (eiaContract) {
            render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SAVE_OK, data: eiaContract] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
        }
    }
    /**
     * 报价详情查看
     * @return
     */
    def getEiaOfferDetail() {
        def eiaOffer = eiaContractService.getEiaOfferDataMap(params.long("eiaOfferId"))
        if(!eiaOffer){
            render([code:HttpMesConstants.CODE_FAIL,msg:HttpMesConstants.MSG_DATA_NULL] as JSON)
        }else{
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String offerDate
            (eiaOffer.offerDate) ? (offerDate = formatter.format(eiaOffer?.offerDate)) : (offDate = "")
            def contractUse = eiaDomainCodeService.translate(GeneConstants.CONTRACT_USE, eiaOffer.contractUse)
            def province = eiaDomainCodeService.translate(GeneConstants.PROVINCE_CODE, eiaOffer.province)
            render([offerNo: eiaOffer.offerNo, offerDate: offerDate, offerMoney: eiaOffer.offerMoney, eiaClientName: eiaOffer.eiaClientName, clientAddress: eiaOffer.clientAddress, contactPhone: eiaOffer.contactPhone, offerName: eiaOffer.offerName, reportFees: eiaOffer.reportFees, enviroMonitoringFee: eiaOffer.enviroMonitoringFee, groundWater: eiaOffer.groundWater, expertFee: eiaOffer.expertFee, otherFee: eiaOffer.otherFee, contractType: eiaOffer.contractType, taskName: eiaOffer.taskName, province: province, contractUse: contractUse, contactName: eiaOffer.contactName] as JSON)
        }
    }
    /**
     * 报价生成合同
     */
    def eiaConGenerate() {
        def eiaOffer = eiaContractService.getEiaOfferDataMap(params.long("eiaOfferId"))
        def contractUse = eiaDomainCodeService.translate(GeneConstants.CONTRACT_USE, eiaOffer.contractUse)
        def province = eiaDomainCodeService.translate(GeneConstants.PROVINCE_CODE, eiaOffer.province)
        [eiaOffer: eiaOffer, eiaOfferId: eiaOffer.id,contractUse:contractUse,province:province]
    }
    /**
     * 检查项目的文件类型是否有对应的资质模板
     */
    def matchProve() {
        def result = exportContractService.matchProve(params.long('eiaProjectId'))
        if (result != HttpMesConstants.MSG_NO_MATCH_PROVE) {
            render([code: HttpMesConstants.CODE_OK] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_NO_MATCH_PROVE] as JSON)
        }
    }
    /**
     * 导出环评资格证书
     */
    def exportProCert() {
        File fileContract = null
        InputStream inputStreamContract = null
        ServletOutputStream servletOutputStreamContract = null
        try {
            /**
             * 调用导出word的方法
             */
            fileContract = exportContractService.exportProCert(Long.valueOf(params.eiaProjectId))
            if (!fileContract) {
                render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
            }
            inputStreamContract = new FileInputStream(fileContract)

            response.setCharacterEncoding("UTF-8")
            response.setContentType("application/msword")
            /**
             * 设置浏览器以下载的方式处理该文件
             */
            response.addHeader("Content-Disposition", "attachment;filename=" + fileContract.getName())

            servletOutputStreamContract = response.getOutputStream()
            byte[] buffer = new byte[1024 * 5]  // 缓冲区
            def bytesToRead = -1
            /**
             * 通过循环将读入的Word文件的内容输出到浏览器中
             */
            while ((bytesToRead = inputStreamContract.read(buffer)) != -1) {
                servletOutputStreamContract.write(buffer, 0, bytesToRead)
            }
        }
        finally {
            if (inputStreamContract != null) inputStreamContract.close()
            if (servletOutputStreamContract != null) servletOutputStreamContract.close()
            if (fileContract != null) fileContract.delete()
        }
    }
    /**
     * 根据不同的文件类型获取要填写的金额名称
     * @return
     */
    def getContractMoneyList() {
        def moneyList = eiaContractService.getContractMoneyList(params)
        if (moneyList) {
            render([code: HttpMesConstants.CODE_OK, data: moneyList] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
    /**
     * 根据不同的文件类型获取要填写的金额名称和对应的值
     * @return
     */
    def getContractMoney() {
        def moneyList = eiaContractService.getContractMoney(params)
        if (moneyList) {
            render([code: HttpMesConstants.CODE_OK, data: moneyList] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
    /**
     * 报价生成合同
     * @return
     */
    def eiaContractGene() {
        if (eiaContractService.eiaContractGene(params, session)) {
            render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SAVE_OK] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
    /***
     * 提交报价并开启工作流
     * @return
     */
    def eiaOfferSub() {
        long eiaOfferId = params.long("eiaOfferId")
        def eiaOffer = EiaOffer.findByIdAndIfDel(eiaOfferId,false)
        if (eiaOffer.ifSub) {
            render([code: HttpMesConstants.CODE_FAIL,msg:HttpMesConstants.MSG_FCONF_NULL] as JSON)
        } else {
            def workFlowCode = eiaWorkFlowContractService.getOfferWorkFlowCode(eiaOfferId)
            if (workFlowCode) {
                def eiaWorkFlowBusi = eiaWorkFlowContractService.startOfferWorkFlow(workFlowCode, GeneConstants.DOMAIN_EIA_OFFER, eiaOfferId, WorkFlowConstants.OFFER_WORKFLOW_START_NODE, session)
                if (eiaWorkFlowBusi) {
                    def ifSub = Integer.valueOf(1)
                    eiaOffer = eiaContractService.eiaOfferSub(eiaOfferId, ifSub)
                    if (eiaOffer && eiaWorkFlowBusi) {
                        render([code: HttpMesConstants.CODE_OK, data: eiaOffer] as JSON)
                    } else {
                        render([code: HttpMesConstants.CODE_FAIL] as JSON)
                    }
                } else {
                    render([code: HttpMesConstants.CODE_FAIL,msg:HttpMesConstants.MSG_FCONF_NULL] as JSON)
                }
            } else {
                render([code: HttpMesConstants.CODE_FAIL,msg:HttpMesConstants.MSG_CONTRACT_NO_FEE] as JSON)
            }
        }
    }
    /**
     * 更新报价ifSub字段
     */
    def updateEiaOfferIfSub() {
        def dataMap = eiaContractService.eiaOfferSub(params.long('eiaOfferId'), params.int('ifSub'))
        if (dataMap) {
            render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SAVE_OK] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
        }
    }
    /**
     * 报价印章申请审批单
     * @return
     */
    def offerSealApplyApprove() {
        def dataMap = eiaContractService.offerSealApplyApprove(params)
        if (dataMap) {
            render([code: HttpMesConstants.CODE_OK, data: dataMap] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
}
