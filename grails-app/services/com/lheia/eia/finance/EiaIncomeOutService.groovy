package com.lheia.eia.finance

import com.lheia.eia.common.FuncConstants
import com.lheia.eia.common.GeneConstants
import com.lheia.eia.contract.EiaContract
import grails.gorm.transactions.Transactional

import java.text.SimpleDateFormat

@Transactional
class EiaIncomeOutService {
    /**
     * 进账信息
     */
    def getEiaIncomeOutDataMap(eiaIncomeOutId){
        def eiaIncomeOut = EiaIncomeOut.findByIdAndIfDel(eiaIncomeOutId,false)
        return  eiaIncomeOut
    }
    /**
     * 出账信息
     */
    def eiaInvoiceOutFinQuery(params,session){
        int page = params.int('page');
        def  invoiceRevenue = EiaIncomeOut.createCriteria().list(offset: 10*(page-1) , max: 10) {
            if(params.contractName && params.contractName!="合同名称、合同编号"){
                or{
                    like('contractNo','%'+params.contractName+'%')
                    like('contractName','%'+params.contractName+'%')
                    like("inputDept", "%" + params.contractName + "%")
                    like("inputUser", "%" + params.contractName + "%")
                }
            }
            eq("invoiceType",GeneConstants.INVOICE_TYPE_OUT)
            /**
             * 数据权限编码所在位置
             */
            /**
             * 查看全部的客户数据
             */
            if (!session?.staff?.funcCode?.contains(FuncConstants.EIA_CWGL_CWRYZWGL_VIEWALL)) {
                /**
                 * 财务查看本部门财务数据
                 */
                if (session?.staff?.funcCode?.contains(FuncConstants.EIA_CWGL_CWRYZWGL_VIEWDEPT)) {
                    like("inputDeptCode", "%"+ session.staff.orgCode +"%")
                }
                /**
                 * 业务查看本部门财务数据
                 */
                else if (session?.staff?.funcCode?.contains(FuncConstants.EIA_CWGL_YWRYZWGL_VIEWDEPT)) {
                    like("inputDeptCode", "%"+ session.staff.orgCode +"%")
                }
                /**
                 * 查看本人客户数据
                 */
                else if (session?.staff?.funcCode?.contains(FuncConstants.EIA_CWGL_YWRYZWGL_VIEWSELF)) {
                    eq ("inputUserId", Long.valueOf(session.staff.staffId))
                }
            }
            /**
             * 如果是财务确认开票
             */
            if(params.accountState.equals("1")){
                    eq("accountState",GeneConstants.INVOICE_ACCOUNT_STATE_SUB)
            }else if(params.accountState.equals("2")){
                    eq("accountState",GeneConstants.INVOICE_CONFIRM_YES)
            }else {
                    or{
                        eq("accountState",GeneConstants.INVOICE_ACCOUNT_STATE_NEW)
                        eq("accountState",GeneConstants.INVOICE_BACK)
                        eq("accountState",GeneConstants.INVOICE_ACCOUNT_STATE_SUB)
                    }

                }
                eq("ifDel",false)
            }
            def result = []
            invoiceRevenue.each {
                def map=[:]
                map.id = it?.id
                map.contractNo =  it?.contractNo
                map.contractName =  it?.contractName
                def eiaContract = EiaContract.findByIdAndIfDel(it.contractId,false)
                map.contractMoney =  eiaContract?.contractMoney
                map.invoiceType =  it?.invoiceType?:""
                map.costTypes =  it?.costTypes?:""
                def noteIncomeMoneySum = EiaIncomeOut.findAllByIfDelAndContractIdAndCostTypes(false,it.contractId,GeneConstants.INVOICE_TYPE_INCOME)*.noteIncomeMoney.sum()?:0
                SimpleDateFormat formaDate = new SimpleDateFormat("yyyy-MM-dd");
                if(it.noteIncomeDate){
                    map.noteIncomeDate = formaDate.format(it.noteIncomeDate)
                }
                map.accountState =  it?.accountState?:""
                map.noteIncomeMoney =  it?.noteIncomeMoney?:""
                map.inputUser =  it?.inputUser?:""
                map.inputUserId =  it?.inputUserId?:""
                map.inputDept =  it?.inputDept?:""
                map.incomeMoney =  noteIncomeMoneySum
                result.add(map)
            }
            def dataMap = [:]
            dataMap.data = result
            dataMap.total = invoiceRevenue.totalCount
            return dataMap
    }
    /**
     * 出账信息保存
     */
    def eiaIncomeOutSave(params,session){
        def contract = EiaContract.findByIdAndIfDel(params.long("contractId"),false)
        def eiaIncomeOut = new EiaIncomeOut(params)
        eiaIncomeOut.contractId = contract?.id
        eiaIncomeOut.contractNo = contract?.contractNo
        eiaIncomeOut.contractName = contract?.contractName
        eiaIncomeOut.invoiceType = GeneConstants.INVOICE_TYPE_OUT
        eiaIncomeOut.accountState = GeneConstants.INVOICE_ACCOUNT_STATE_NEW
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd")
        eiaIncomeOut.noteIncomeDate = format.parse(params.noteIncomeDateS)
        eiaIncomeOut.inputDept = session.staff.orgName
        eiaIncomeOut.inputDeptCode = session.staff.orgCode
        eiaIncomeOut.inputDeptId = Long.parseLong(session.staff.orgId)
        eiaIncomeOut.inputUser = session.staff.staffName
        eiaIncomeOut.inputUserId = Long.parseLong(session.staff.staffId)
        eiaIncomeOut.save(flush:true,failOnError:true)
    }
    /**
     * 进账新增保存
     */
    def eiaInvoiceIncomeSave(params,session){
        def contract = EiaContract.findByIdAndIfDel(params.long("contractId"),false)
        def eiaIncomeOut = new EiaIncomeOut()
        eiaIncomeOut.properties = params
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd")
        eiaIncomeOut.noteIncomeDate = format.parse(params.noteIncomeDateS)
        eiaIncomeOut.contractId = contract?.id
        eiaIncomeOut.contractNo = contract?.contractNo
        eiaIncomeOut.contractName = contract?.contractName
        eiaIncomeOut.invoiceType = GeneConstants.INVOICE_TYPE_INCOME
        eiaIncomeOut.costTypes = GeneConstants.INVOICE_TYPE_INCOME
        eiaIncomeOut.accountState = GeneConstants.INVOICE_CONFIRM_YES
        eiaIncomeOut.inputDept = session.staff.orgName
        eiaIncomeOut.inputDeptCode = session.staff.orgCode
        eiaIncomeOut.inputDeptId = Long.parseLong(session.staff.orgId)
        eiaIncomeOut.inputUser = session.staff.staffName
        eiaIncomeOut.inputUserId = Long.parseLong(session.staff.staffId)
        eiaIncomeOut.save(flush:true,failOnError:true)
    }
    /**
     * 出账金额编辑保存
     */
    def eiaInvoiceIncomeUpdate(params){
        def eiaIncomeOut = getEiaIncomeOutDataMap(params.long("eiaIncomeOutId"))
        eiaIncomeOut.properties = params
        eiaIncomeOut.save(flush:true,failOnError:true)
    }
    /**
     * 删除出账信息
     */
    def eiaIncomeOutDel(eiaIncomeOutId){
        def eiaIncomeOut = getEiaIncomeOutDataMap(eiaIncomeOutId)
        eiaIncomeOut.ifDel = true
        eiaIncomeOut.save(flush:true,failOnError:true)
    }
    /**
     * 出账信息提交
     */
    def eiaIncomeOutSubmit(eiaIncomeOutId){
        def eiaIncomeOut = getEiaIncomeOutDataMap(eiaIncomeOutId)
        eiaIncomeOut.accountState = GeneConstants.INVOICE_ACCOUNT_STATE_SUB
        eiaIncomeOut.save(flush: true, failOnError: true)
    }
    /**
     * 出账信息确认
     */
    def eiaInvoiceOutConfirm(eiaIncomeOutId){
        def eiaIncomeOut = getEiaIncomeOutDataMap(eiaIncomeOutId)
        eiaIncomeOut.accountState = GeneConstants.INVOICE_CONFIRM_YES
        eiaIncomeOut.save(flush:true,failOnError:true)
    }
    /**
     * 出账信息退回
     */
    def eiaInvoiceOutBack(eiaIncomeOutId){
        def eiaIncomeOut = getEiaIncomeOutDataMap(eiaIncomeOutId)
        eiaIncomeOut.accountState = GeneConstants.INVOICE_BACK
        eiaIncomeOut.save(flush:true,failOnError:true)
    }
    /**
     *合同对应进账信息
     */
    def eiaInvoiceIncomeQuery(params,session){
        int page = params.int('page');
        def  invoiceRevenue = EiaIncomeOut.createCriteria().list(offset: 10*(page-1) , max: 10) {

            eq('contractId', params.long("contractId"))
            eq("invoiceType",GeneConstants.INVOICE_TYPE_INCOME)
            eq("ifDel",false)
        }
        def result = []
        invoiceRevenue.each {
            def map=[:]
            map.id = it?.id
            map.contractNo =  it?.contractNo
            map.contractName =  it?.contractName
            map.invoiceType =  it?.invoiceType?:""
            SimpleDateFormat formaDate = new SimpleDateFormat("yyyy-MM-dd");
            map.noteIncomeDate = formaDate.format(it.noteIncomeDate)
            map.accountState =  it?.accountState?:""
            map.noteIncomeMoney =  it?.noteIncomeMoney?:""
            map.inputUser =  it?.inputUser?:""
            map.inputDept =  it?.inputDept?:""
            map.lastUpdated = formaDate.format(it?.lastUpdated)
            result.add(map)
        }
        def dataMap = [:]
        dataMap.data = result
        dataMap.total = invoiceRevenue.totalCount
        return dataMap
    }
    /**
     *合同对应出账信息
     */
    def eiaInvoiceOutQuery(params,session){
        int page = params.int('page');
        def result = []
        def dataMap = [:]
        if(params.contractId) {
            def invoiceRevenue = EiaIncomeOut.createCriteria().list(offset: 10 * (page - 1), max: 10) {
                eq('contractId', params.long("contractId"))
                eq("invoiceType", GeneConstants.INVOICE_TYPE_OUT)
                eq("accountState", GeneConstants.INVOICE_CONFIRM_YES)
                eq("ifDel", false)
            }
            invoiceRevenue.each {
                def map = [:]
                map.id = it?.id
                map.contractNo = it?.contractNo
                map.contractName = it?.contractName
                map.invoiceType = it?.invoiceType ?: ""
                map.costTypes = it?.costTypes ?: ""
                SimpleDateFormat formaDate = new SimpleDateFormat("yyyy-MM-dd");
                map.noteIncomeDate = formaDate.format(it.noteIncomeDate)
                map.accountState = it?.accountState ?: ""
                map.noteIncomeMoney = it?.noteIncomeMoney ?: ""
                map.inputUser = it?.inputUser ?: ""
                map.inputDept = it?.inputDept ?: ""
                map.lastUpdated = formaDate.format(it?.lastUpdated)
                result.add(map)
            }
            dataMap.total = invoiceRevenue.totalCount
        }
        dataMap.data = result
        return dataMap
    }
}
