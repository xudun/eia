package com.lheia.eia.finance

import com.lheia.eia.common.GeneConstants
import com.lheia.eia.common.HttpMesConstants
import com.lheia.eia.common.HttpUrlConstants
import com.lheia.eia.contract.EiaContract
import com.lheia.eia.tools.HttpConnectTools
import com.lheia.eia.tools.JsonHandler
import grails.converters.JSON
import org.grails.web.json.JSONArray

class EiaInvoiceLogController {
    def eiaInvoiceService
    def eiaContractService
    /**
     * 财务信息列表
     */
    def eiaInvoiceIndex(){
        def invoiceIncomeOutMap = eiaInvoiceService.invoiceIncomeOutNum(session)
        [invoiceOutNum:invoiceIncomeOutMap.invoiceOutNum,revenueNum:invoiceIncomeOutMap.revenueNum]
    }
    /**
     * 开票信息新增
     */
    def invoiceRevenueSave() {
        def eiaContract = eiaContractService.getEiaContractDataMap(params.long("contractId"))
        def noteIncomeMoney = eiaContract.contractMoney
        def noteIncomeMoneyAf = new BigDecimal(params.billMoney)
        if (noteIncomeMoney < noteIncomeMoneyAf) {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_REVE_MONEY, data: eiaContract] as JSON)
            return
        } else{
            def eiaTask = eiaInvoiceService.invoiceRevenueSave(params,session)
            if (eiaTask) {
                render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SAVE_OK, data: eiaTask] as JSON)
            } else {
                render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
            }

        }
    }
    def eiaInvoiceReveCreate(){
        def contract = EiaContract.findByIdAndIfDel(params.long("contractId"),false)
        [contract:contract]
    }
    /**
     * 客户选择
     */
    def eiaClientRevenSelect(){

    }
    /**
     * 开票确认
     */
    def eiaInvoiceFinQuery(){
        def dataMap = eiaInvoiceService.eiaInvoiceFinQuery(params, session)
        if (dataMap) {
            render([code: HttpMesConstants.CODE_OK, count: dataMap.total, data: dataMap.data] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
    /**
     * 开票信息编辑
     */
    def eiaInvoiceReveEdit(){
        def eiaInvoice = EiaInvoice.findByIdAndIfDel(params.long("eiaInvoiceId"),false)
        def contract = EiaContract.findByIdAndIfDel(eiaInvoice?.contractId,false)
        [eiaInvoice:eiaInvoice,contract:contract]
    }
    /**
     * 获取开票信息
     */
    def getEiaInvoiceDataMap() {
        def eiaInvoice = eiaInvoiceService.getEiaInvoiceDataMap(params.long('eiaInvoiceId'))
        if (eiaInvoice) {
            render([code: HttpMesConstants.CODE_OK, data: eiaInvoice] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
    /**
     * 开票信息编辑保存
     */
    def invoiceRevenueUpate(){
        def eiaTask = eiaInvoiceService.invoiceRevenueUpate(params,session)
        if (eiaTask) {
            render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SAVE_OK, data: eiaTask] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
        }
    }
    /**
     * 开票信息删除
     */
    def eiaInvoiceDel(){
        def eiaInvoice = eiaInvoiceService.eiaInvoiceDel(params.long("eiaInvoiceId"))
        if (eiaInvoice) {
            render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_DEL_OK, data: eiaInvoice] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DEL_FAIL] as JSON)
        }
    }
    /**
     * 开票信息提交
     */
    def eiaInvoiceSubmit(){
        def eiaInvoice = eiaInvoiceService.eiaInvoiceSubmit(params.long("eiaInvoiceId"))
        if (eiaInvoice) {
            render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SUBMIT_OK, data: eiaInvoice] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SUBMIT_FAIL] as JSON)
        }
    }
    /**
     * 开票信息查看
     */
    def eiaInvoiceReveDetail(){
        def eiaInvoice = eiaInvoiceService.eiaInvoiceReveDetail(params.long("eiaInvoiceId"))
        [eiaInvoice:eiaInvoice]
    }
    /**
     * 开票信息详细
     */
    def getEiaInvoiceDetails(){
        def eiaInvoice = eiaInvoiceService.eiaInvoiceReveDetail(params.long("eiaInvoiceId"))
        render([data: eiaInvoice, billDate: eiaInvoice?.billDate?.format("yyyy-MM-dd"),estDate:eiaInvoice?.estDate?.format("yyyy-MM-dd")] as JSON)
    }
    /**
     * 财务人员财务管理列表
     */
    def eiaInvoiceReveFinIndex(){

    }
    /**
     * 财务人员财务数据
     * @return
     */
    def getInvoiceRevenueFinData(){
        def dataMap = eiaInvoiceService.eiaInvoiceFinQueryPage(params, session)
        if (dataMap) {
            render([code: HttpMesConstants.CODE_OK, count: dataMap.total, data: dataMap.data,totalDetail:dataMap.totalDetail] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
    /**
     * 开票信息确认
     */
    def eiaInvoiceConfirm(){
        def eiaInvoice = eiaInvoiceService.eiaInvoiceConfirm(params.long("eiaInvoiceId"))
        if (eiaInvoice) {
            render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SUBMIT_OK, data: eiaInvoice] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SUBMIT_FAIL] as JSON)
        }
    }
    /**
     * 开票信息驳回
     */
    def eiaInvoiceRevenueBack(){
        def eiaInvoice = eiaInvoiceService.eiaInvoiceRevenueBack(params.long("eiaInvoiceId"))
        if (eiaInvoice) {
            render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SUBMIT_OK, data: eiaInvoice] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SUBMIT_FAIL] as JSON)
        }
    }
    /**
     * 业务人员开票信息数目
     * @return
     */
    def eiaInvoiceRevNum(){
        def eiaInvoice = eiaInvoiceService.eiaInvoiceRevNum(session)
        if (eiaInvoice) {
            render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_DEL_OK, data: eiaInvoice] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DEL_FAIL] as JSON)
        }
    }
    /**
     * 业务人员出账信息数目
     * @return
     */
    def eiaInvoiceOutNum(){
        def eiaInvoice = eiaInvoiceService.eiaInvoiceOutNum(session)
        if (eiaInvoice) {
            render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_DEL_OK, data: eiaInvoice] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DEL_FAIL] as JSON)
        }
    }
    /**
     * 财务人员开票信息数目
     * @return
     */
    def eiaInvoiceRevCissNum(){
        def eiaInvoice = eiaInvoiceService.eiaInvoiceRevCissNum(session)
        if (eiaInvoice) {
            render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_DEL_OK, data: eiaInvoice] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DEL_FAIL] as JSON)
        }
    }
    /**
     * 财务人员出账信息数目
     * @return
     */
    def eiaInvoiceOutCissNum(){
        def eiaInvoice = eiaInvoiceService.eiaInvoiceOutCissNum(session)
        if (eiaInvoice) {
            render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_DEL_OK, data: eiaInvoice] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DEL_FAIL] as JSON)
        }
    }
    /**
     * 根据合同信息查看对应的信息
     */
    def eiaInvoiceList(){
    }
    /**
     * 合同对应开票信息
     */
    def eiaInvoiceQuery(){
        def dataMap = eiaInvoiceService.eiaInvoiceQuery(params, session)
        if (dataMap) {
            render([code: HttpMesConstants.CODE_OK, count: dataMap.total, data: dataMap.data] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
    /**
     * 开票信息打印
     */
    def eiaInvoiceRevePrint(){
        def eiaInvoice = eiaInvoiceService.eiaInvoiceReveDetail(params.long("eiaInvoiceId"))
        /**
         * 获取部门总经理
         */
        def staffLenderId
        def param = [:]
        param.orgId = session.staff.orgId.toString()
        def staffLenderJson = HttpConnectTools.getResponseJson(HttpUrlConstants.EIA_AUTH_ORG_STAFF_LENDER_INFO, param)
        JSONArray staffLenderInfo = new JSONArray(staffLenderJson)
        if(staffLenderInfo){
            staffLenderId = staffLenderInfo.getJSONObject(0)getString("id")
        }
        def deptUserId = staffLenderId.toString()
        def deptUserSign
        def secParam = [:]
        secParam.staffId = deptUserId
        def detpStaffJson = HttpConnectTools.getResponseJson(HttpUrlConstants.SINGLE_EIA_AUTH_STAFF_INFO, secParam)
        /**
         * 判断是否为空值(如果没有值，则一定小于21)
         */
        if (detpStaffJson.size()>21) {
            def staff = JsonHandler.jsonToMap(detpStaffJson)?.data[0]
            if (staff.signImagePath) {
                deptUserSign = GeneConstants.AUTH_FILE_URL_PATH + staff.signImagePath
            }
        }
        def staffSign
        def staffParam = [:]
        staffParam.staffId = eiaInvoice.inputUserId.toString()
        def staffJson = HttpConnectTools.getResponseJson(HttpUrlConstants.SINGLE_EIA_AUTH_STAFF_INFO, staffParam)
        if (staffJson) {
            def staff = JsonHandler.jsonToMap(staffJson)?.data[0]
            if (staff.signImagePath) {
                staffSign = GeneConstants.AUTH_FILE_URL_PATH + staff.signImagePath
            }
        }
        [eiaInvoice:eiaInvoice,deptUserSign:deptUserSign,staffSign:staffSign]
    }
}
