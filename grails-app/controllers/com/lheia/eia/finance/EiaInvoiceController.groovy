package com.lheia.eia.finance

import com.lheia.eia.common.FuncConstants
import com.lheia.eia.common.GeneConstants
import com.lheia.eia.common.HttpMesConstants
import com.lheia.eia.common.HttpUrlConstants
import com.lheia.eia.contract.EiaContract
import com.lheia.eia.tools.HttpConnectTools
import com.lheia.eia.tools.JsonHandler
import grails.converters.JSON
import org.grails.web.json.JSONArray


class EiaInvoiceController {
    def eiaInvoiceService
    def eiaContractService
    /**
     * 财务信息列表
     */
    def eiaInvoiceIndex(){
        def invoiceIncomeOutMap = eiaInvoiceService.invoiceIncomeOutNum(session)
        def isExcpet = false
        if(session.staff.funcCode.contains(FuncConstants.EIA_CWGL_YWRYZWGL_CWYJ)){
            isExcpet = true
        }
        [invoiceOutNum:invoiceIncomeOutMap.invoiceOutNum,revenueNum:invoiceIncomeOutMap.revenueNum,isExcpet:isExcpet]
    }
    /**
     * 开票信息新增
     */
    def invoiceRevenueSave() {
        def eiaContract = eiaContractService.getEiaContractDataMap(params.long("contractId"))
        def noteIncomeMoney = eiaContract?.contractMoney?:0
        def eiaInvoiceSum = 0
        def eiaInvoice = EiaInvoice.findAllByIfDelAndContractIdAndInputUserId(false,params.long("contractId"),session.staff.staffId)
        eiaInvoice.each{
            eiaInvoiceSum += it.billMoney?:0
        }
        def billMoney = new BigDecimal(params.billMoney)?:0
        /**
         * 判断开票金额不能大于合同金额
         */
        def noteIncomeMoneyAf = billMoney + eiaInvoiceSum
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
     * 开票未确认
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
     * 开票已确认
     */
    def eiaInvoiceAlreadyFinQuery(){
        def dataMap = eiaInvoiceService.eiaInvoiceAlreadyFinQuery(params, session)
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
        def eiaInvoiceInfo = EiaInvoice.findByIdAndIfDel(params.long("eiaInvoiceId"),false)
        def eiaContract = eiaContractService.getEiaContractDataMap(eiaInvoiceInfo?.contractId)
        def noteIncomeMoney = eiaContract?.contractMoney?:0
        def eiaInvoiceSum = 0
        def eiaInvoice = EiaInvoice.findAllByIfDelAndContractIdAndInputUserIdAndIdNotEqual(false,eiaContract?.id,session.staff.staffId,eiaInvoiceInfo?.id)
        eiaInvoice.each{
            eiaInvoiceSum += it.billMoney?:0
        }
        def billMoney = new BigDecimal(params.billMoney)?:0
        /**
         * 判断开票金额不能大于合同金额
         */
        def noteIncomeMoneyAf = billMoney + eiaInvoiceSum
        if (noteIncomeMoney < noteIncomeMoneyAf) {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_REVE_MONEY, data: eiaContract] as JSON)
            return
        } else{
            def invoiceRevenue = eiaInvoiceService.invoiceRevenueUpate(params,session)
            if (invoiceRevenue) {
                render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SAVE_OK, data: invoiceRevenue] as JSON)
            } else {
                render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
            }

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

    def getInvoiceRevenueFinDataWorkFlow(){
        if(params.eiaContractId){
            def dataMap = eiaInvoiceService.eiaInvoiceFinQueryPage(params, session)
            if (dataMap) {
                render([code: HttpMesConstants.CODE_OK, count: dataMap.total, data: dataMap.data,totalDetail:dataMap.totalDetail] as JSON)
            } else {
                render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
            }
        }else{
            render([code: HttpMesConstants.CODE_OK, count: 0, data: []] as JSON)
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
     * 业务人员开票未确认信息数目
     * @return
     */
    def eiaInvoiceRevNum(){
        def eiaInvoice = eiaInvoiceService.eiaInvoiceRevNum(session)
        if (eiaInvoice) {
            render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_DEL_OK, data: eiaInvoice] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DEL_FAIL] as JSON)
        }
    } /**
     * 业务人员已开票信息数目
     * @return
     */
    def eiaInvoiceAlreadyRevNum(){
        def eiaInvoice = eiaInvoiceService.eiaInvoiceAlreadyRevNum(session)
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
    /**
     * 判断是否未被审核开票信息
     */
    def isAuditEiaInvoice(){
        def eiaInvoice = EiaInvoice.findAllByIfDelAndContractIdAndInputUserIdAndAccountState(false,params.long("contractId"),Long.parseLong(session.staff.staffId),GeneConstants.INVOICE_ACCOUNT_STATE_SUB)
        if (eiaInvoice.size()>0) {
            render([code: HttpMesConstants.CODE_OK] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL] as JSON)
        }
    }
    /**
     * 判断是否出账未被审核通过的出账信息
     */
    def isAuditEiaInvoiceOut(){
        def eiaIncomeOut = EiaIncomeOut.findAllByIfDelAndContractIdAndInputUserIdAndAccountState(false,params.long("contractId"),Long.parseLong(session.staff.staffId),GeneConstants.INVOICE_ACCOUNT_STATE_SUB)
        if (eiaIncomeOut.size()>0) {
            render([code: HttpMesConstants.CODE_OK] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL] as JSON)
        }
    }
}
