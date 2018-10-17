package com.lheia.eia.finance

import com.lheia.eia.client.EiaClient
import com.lheia.eia.common.GeneConstants
import com.lheia.eia.common.HttpMesConstants
import grails.converters.JSON


class EiaIncomeOutController {
    def eiaIncomeOutService
    def eiaContractService
    /**
     * 出账未确认
     */
    def eiaInvoiceOutFinQuery(){
        def dataMap = eiaIncomeOutService.eiaInvoiceOutFinQuery(params, session)
        if (dataMap) {
            render([code: HttpMesConstants.CODE_OK, count: dataMap.total, data: dataMap.data] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
    /**
     * 出账信息新增
     */
    def eiaInvoiceOutCreate(){
        [contractId:params.contractId]
    }
    /**
     * 出账信息保存
     */
    def eiaIncomeOutSave(){
        def eiaIncomeOut = eiaIncomeOutService.eiaIncomeOutSave(params,session)
        if (eiaIncomeOut) {
            render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SAVE_OK, data: eiaIncomeOut] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
        }
    }
    /**
     * 进账信息新增
     */
    def eiaInvoiceIncomeCreate(){
        def eiaContract = eiaContractService.getEiaContractDataMap(params.long("contractId"))
        def eiaClient = EiaClient.findByIdAndIfDel(eiaContract?.eiaClientId,false)
        [contractId:params.contractId,eiaContract:eiaContract,eiaClient:eiaClient]
    }
    /**
     * 进账信息保存
     */
    def eiaInvoiceIncomeSave(){
        def eiaContract = eiaContractService.getEiaContractDataMap(params.long("contractId"))
        def invoiceIncomeOutMoneySum = 0
        def invoiceIncomeOutMoney = EiaIncomeOut.findAllByContractIdAndIfDelAndInvoiceType(eiaContract?.id,false,GeneConstants.INVOICE_TYPE_INCOME)
        invoiceIncomeOutMoney.each{
            invoiceIncomeOutMoneySum += it.noteIncomeMoney
        }
        /**
         * 判断进账金额不能大于合同金额
         */
        def noteIncomeMoney = new BigDecimal(params.noteIncomeMoney)
        if(invoiceIncomeOutMoneySum + noteIncomeMoney > eiaContract.contractMoney){
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_INCOME_MONEY, data: eiaContract] as JSON)
            return
        } else{
            def invoiceIncome = eiaIncomeOutService.eiaInvoiceIncomeSave(params,session)
            if (invoiceIncome) {
                render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SAVE_OK, data: invoiceIncome] as JSON)
            } else {
                render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
            }
        }
    }
    /**
     * 出账信息编辑
     */
    def eiaInvoiceOutEdit(){
        def eiaIncomeOut = EiaIncomeOut.findByIdAndIfDel(params.long("eiaIncomeOutId"),false)
        [eiaIncomeOut:eiaIncomeOut]
    }
    /**
     * 获取出账信息数据用于编辑
     */
    def eiaIncomeOutDataMap(){
        def eiaIncomeOut = eiaIncomeOutService.getEiaIncomeOutDataMap(params.long("eiaIncomeOutId"))
        if (eiaIncomeOut) {
            render([code: HttpMesConstants.CODE_OK, data: eiaIncomeOut,noteIncomeDate: eiaIncomeOut?.noteIncomeDate?.format("yyyy-MM-dd")] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
    /**
     * 出账信息编辑
     */
    def eiaInvoiceIncomeUpdate(){
        def invoiceIncome = eiaIncomeOutService.eiaInvoiceIncomeUpdate(params)
        if (invoiceIncome) {
            render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SAVE_OK, data: invoiceIncome] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
        }
    }
    /**
     * 删除出账信息
     */
    def eiaIncomeOutDel(){
        def eiaIncomeOut = eiaIncomeOutService.eiaIncomeOutDel(params.long("eiaIncomeOutId"))
        if (eiaIncomeOut) {
            render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_DEL_OK, data: eiaIncomeOut] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DEL_FAIL] as JSON)
        }
    }
    /**
     * 出账信息提交
     */
    def eiaIncomeOutSubmit(){
        def eiaIncomeOut = eiaIncomeOutService.eiaIncomeOutSubmit(params.long("eiaIncomeOutId"))
        if (eiaIncomeOut) {
            render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SUBMIT_OK, data: eiaIncomeOut] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SUBMIT_FAIL] as JSON)
        }
    }
    /**
     * 确认出账信息
     */
    def eiaInvoiceOutConfirm(){
        def eiaInvoice = eiaIncomeOutService.eiaInvoiceOutConfirm(params.long("eiaIncomeOutId"))
        if (eiaInvoice) {
            render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SUBMIT_OK, data: eiaInvoice] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SUBMIT_FAIL] as JSON)
        }
    }
    /**
     * 出账信息驳回
     */
    def eiaInvoiceOutBack(){
        def eiaInvoice = eiaIncomeOutService.eiaInvoiceOutBack(params.long("eiaIncomeOutId"))
        if (eiaInvoice) {
            render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SUBMIT_OK, data: eiaInvoice] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SUBMIT_FAIL] as JSON)
        }
    }
    /**
     * 合同对应进账信息
     */
    def eiaInvoiceIncomeQuery(){
        def dataMap = eiaIncomeOutService.eiaInvoiceIncomeQuery(params, session)
        if (dataMap) {
            render([code: HttpMesConstants.CODE_OK, count: dataMap.total, data: dataMap.data] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
    /**
     * 合同对应出账信息
     */
    def eiaInvoiceOutQuery(){
        def dataMap = eiaIncomeOutService.eiaInvoiceOutQuery(params, session)
        if (dataMap) {
            render([code: HttpMesConstants.CODE_OK, count: dataMap.total, data: dataMap.data] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
    /**
     * 出账信息查看
     */
    def eiaInvoiceOutDetail(){
        def eiaIncomeOut = EiaIncomeOut.findByIdAndIfDel(params.long("eiaIncomeOutId"),false)
        [eiaIncomeOut:eiaIncomeOut]
    }
}
