package com.lheia.eia.finance

import com.lheia.eia.common.FuncConstants
import com.lheia.eia.common.GeneConstants
import com.lheia.eia.contract.EiaContract
import grails.gorm.transactions.Transactional

import java.math.RoundingMode
import java.text.SimpleDateFormat

@Transactional
class EiaInvoiceService {

    /**
     * 业务人员各标签个数
     */
    def invoiceIncomeOutNum(session){
        def revenueNum = 0
        def invoiceOutNum = 0
        /**
         * 开票确认
         */
        revenueNum = EiaIncomeOut.countByIfDelAndInvoiceTypeAndAccountStateNotEqualAndInputUserId(false,GeneConstants.INVOICE_ACCOUNT_NAME,GeneConstants.INVOICE_CONFIRM_YES,session.staff.id)
        /**
         * 出账确认
         */
        invoiceOutNum = EiaIncomeOut.countByIfDelAndInvoiceTypeAndAccountStateNotEqualAndInputUserId(false,GeneConstants.INVOICE_TYPE_OUT,GeneConstants.INVOICE_CONFIRM_YES,session.staff.id)
        def invoiceIncomeOutMap = [:]
        invoiceIncomeOutMap.invoiceOutNum = invoiceOutNum
        invoiceIncomeOutMap.revenueNum = revenueNum
        return  invoiceIncomeOutMap
    }
    /**
     * 开票信息保存
     */
    def invoiceRevenueSave(params,session){
        def contract = EiaContract.findByIdAndIfDel(params.long("contractId"),false)
        def eiaInvoice =new EiaInvoice(params)
        eiaInvoice.contractId = contract.id
        eiaInvoice.contractNo = contract.contractNo
        eiaInvoice.contractMoney = contract.contractMoney
        eiaInvoice.contractName = contract.contractName
        eiaInvoice.contractName = contract.contractName
        eiaInvoice.clientName = contract.eiaClientName
        eiaInvoice.eiaClientId = contract.eiaClientId
        eiaInvoice.accountState = GeneConstants.INVOICE_ACCOUNT_STATE_NEW
        if(params.clientName){
            eiaInvoice.clientAccountName = params.clientName
        }
        eiaInvoice.inputDept = session.staff.orgName
        eiaInvoice.inputDeptCode = session.staff.orgCode
        eiaInvoice.inputDeptId = Long.parseLong(session.staff.orgId)
        eiaInvoice.inputUser = session.staff.staffName
        eiaInvoice.inputUserId = Long.parseLong(session.staff.staffId)
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd")
        eiaInvoice.billDate = format.parse(params.billDateS)
        eiaInvoice.estDate = format.parse(params.estimateDateS)
        eiaInvoice.save(flush:true,failOnError:true)
    }

    /**
     * 开票确认
     */
    def eiaInvoiceFinQuery(params,session){
        int page = params.int('page');
        def  invoiceRevenue = EiaInvoice.createCriteria().list(offset: 10*(page-1) , max: 10) {
            if(params.contractName && params.contractName !="合同名称、合同编号、录入部门、录入人"){
                or{
                    like('contractNo','%'+params.contractName+'%')
                    like('contractName','%'+params.contractName+'%')
                    like("inputDept", "%" + params.contractName + "%")
                    like("inputUser", "%" + params.contractName + "%")
                }
            }
            /**
             * 如果是财务确认开票
             */
            if(params.accountState){
                eq("accountState",GeneConstants.INVOICE_ACCOUNT_STATE_SUB)
            }else {
                "in"("accountState",[GeneConstants.INVOICE_ACCOUNT_STATE_NEW,GeneConstants.INVOICE_ACCOUNT_STATE_SUB,GeneConstants.INVOICE_BACK])
            }
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
            eq("ifDel",false)
            order("id","desc")
        }
        def result = []
        invoiceRevenue.each {
            def map=[:]

            map.id = it?.id
            map.contractNo =  it?.contractNo
            map.contractName =  it?.contractName
            map.accountState =  it?.accountState?:""
            SimpleDateFormat formaDate = new SimpleDateFormat("yyyy-MM-dd");
            map.billDate = formaDate.format(it.billDate)
            map.addrTel =  it?.addrTel?:""
            map.billMoney =  it?.billMoney?:""
            map.inputUser =  it?.inputUser?:""
            map.inputUserId =  it?.inputUserId?:""
            map.inputDept =  it?.inputDept?:""
            result.add(map)
        }
        def dataMap = [:]
        dataMap.data = result
        dataMap.total = invoiceRevenue.totalCount
        return dataMap
    }
    /**
     * 开票已确认
     */
    def eiaInvoiceAlreadyFinQuery(params,session){
        int page = params.int('page');
        def  invoiceRevenue = EiaInvoice.createCriteria().list(offset: 10*(page-1) , max: 10) {
            if(params.contractName && params.contractName !="合同名称、合同编号、录入部门、录入人"){
                or{
                    like('contractNo','%'+params.contractName+'%')
                    like('contractName','%'+params.contractName+'%')
                    like("inputDept", "%" + params.contractName + "%")
                    like("inputUser", "%" + params.contractName + "%")
                }
            }
            /**
             * 如果是财务确认开票
             */
            eq("accountState",GeneConstants.INVOICE_CONFIRM_YES)
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
            eq("ifDel",false)
            order("id","desc")
        }
        def result = []
        invoiceRevenue.each {
            def map=[:]

            map.id = it?.id
            map.contractNo =  it?.contractNo
            map.contractName =  it?.contractName
            map.accountState =  it?.accountState?:""
            SimpleDateFormat formaDate = new SimpleDateFormat("yyyy-MM-dd");
            if(it.billDate){
                map.billDate = formaDate.format(it.billDate)
            }
            map.addrTel =  it?.addrTel?:""
            map.billMoney =  it?.billMoney?:""
            map.inputUser =  it?.inputUser?:""
            map.inputDept =  it?.inputDept?:""
            result.add(map)
        }
        def dataMap = [:]
        dataMap.data = result
        dataMap.total = invoiceRevenue.totalCount
        return dataMap
    }
    /**
     * 开票信息编辑保存
     */
    def invoiceRevenueUpate(params,session){
        def eiaInvoice =  EiaInvoice.findByIdAndIfDel(params.long("eiaInvoiceId"),false)
        eiaInvoice.properties = params
        if(params.clientName){
            eiaInvoice.clientName = params.clientName
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd")
        eiaInvoice.billDate = format.parse(params.billDateS)
        eiaInvoice.estDate = format.parse(params.estDateS)
        eiaInvoice.save(flush:true,failOnError:true)
    }
    /**
     * 获取开票信息
     */
    def getEiaInvoiceDataMap(eiaInvoiceId){
        def eiaInvoice = EiaInvoice.findByIdAndIfDel(eiaInvoiceId, false)
        return eiaInvoice
    }
    /**
     * 开票信息删除
     */
    def eiaInvoiceDel(eiaInvoiceId){
        def eiaInvoice = EiaInvoice.findByIdAndIfDel(eiaInvoiceId, false)
        eiaInvoice.ifDel = true
        eiaInvoice.save(flush: true, failOnError: true)
    }
    /**
     * 开票信息提交
     */
    def eiaInvoiceSubmit(eiaInvoiceId){
        def eiaInvoice = EiaInvoice.findByIdAndIfDel(eiaInvoiceId, false)
        eiaInvoice.accountState = GeneConstants.INVOICE_ACCOUNT_STATE_SUB
        eiaInvoice.save(flush: true, failOnError: true)
    }
    /**
     * 开票信息查看
     */
    def eiaInvoiceReveDetail(eiaInvoiceId){
        def eiaInvoice = EiaInvoice.findByIdAndIfDel(eiaInvoiceId, false)
        return eiaInvoice
    }
    /***
     *财务人员财务管理
     */
    def eiaInvoiceFinQueryPage(params,session){
        def sdf = new SimpleDateFormat("yyyy-MM-dd")
        int page
        int limit
        if (params.page && params.limit) {
            page = params.int('page') - 1
            limit = params.int('limit')
        }
        def contractList = EiaContract.createCriteria().list(max: limit, offset: page * limit) {

            if(params.eiaContractId){
                eq("id",Long.valueOf(params.eiaContractId))
            }
            else if(params.contractName && params.contractName !="合同名称、合同编号、录入部门、录入人"){
                or{
                    like('contractNo','%'+params.contractName+'%')
                    like('contractName','%'+params.contractName+'%')
                    like("inputDept", "%" + params.contractName + "%")
                    like("inputUser", "%" + params.contractName + "%")
                }
            }else {
                if (session?.staff?.funcCode?.contains(FuncConstants.EIA_CWGL_YWRYZWGL_VIEWDEPT)) {
                    eq ("inputUserId", Long.valueOf(session.staff.staffId))
                }
            }
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
            eq("ifDel", false)
            eq("ifShow", true)
            order("id", "desc")
        }
        def eiaContractData = []
        contractList.each {
            def map = [:]
            map.id = it.id
            map.contractName = it.contractName
            map.contractNo = it.contractNo
            map.contractMoney = it.contractMoney
            map.inputUser = it.inputUser
            map.inputUserId = it.inputUserId

            def eiaInvoice =  EiaInvoice.findAllByContractIdAndIfDelAndAccountState(it.id,false,GeneConstants.INVOICE_CONFIRM_YES)
            /**
             * 开票金额
             */
            def eiaInvoiceSum = 0
            eiaInvoice.each{
                eiaInvoiceSum+=it.billMoney
            }
            /**
             * 进账金额
             */
            def invoiceIncomeSum = 0
            def invoiceIncome =  EiaIncomeOut.findAllByContractIdAndIfDelAndInvoiceTypeAndAccountState(it.id,false,GeneConstants.INVOICE_TYPE_INCOME,GeneConstants.INVOICE_CONFIRM_YES)
            invoiceIncome.each{
                invoiceIncomeSum+=it.noteIncomeMoney
            }
            /**
             * 未结清
             */
            def outstandingAmountSum = it.contractMoney - invoiceIncomeSum
            /**
             * 出账信息(
             *专家费(万元))
             */
            def expertFeeSum = 0
            def invoiceOut =  EiaIncomeOut.findAllByContractIdAndIfDelAndInvoiceTypeAndCostTypesAndAccountState(it.id,false,GeneConstants.INVOICE_TYPE_OUT,GeneConstants.INVOICE_ECPERT_FEES,GeneConstants.INVOICE_CONFIRM_YES)
            invoiceOut.each{
                expertFeeSum+=it.noteIncomeMoney
            }
            /**
             * 出账信息(
             *监测费(万元))
             */
            def monitorFeeSum = 0
            def monitorFee  =  EiaIncomeOut.findAllByContractIdAndIfDelAndInvoiceTypeAndCostTypesAndAccountState(it.id,false,GeneConstants.INVOICE_TYPE_OUT,GeneConstants.INVOICE_MONITORING_FEES,GeneConstants.INVOICE_CONFIRM_YES)
            monitorFee .each{
                monitorFeeSum+=it.noteIncomeMoney
            }
            /**
             * 出账信息(
             *其他((万元))
             */
            def otherFeeSum = 0
            def otherFee  =  EiaIncomeOut.findAllByContractIdAndIfDelAndInvoiceTypeAndCostTypesAndAccountState(it.id,false,GeneConstants.INVOICE_TYPE_OUT,GeneConstants.INVOICE_OTHER_FEES,GeneConstants.INVOICE_CONFIRM_YES)
            otherFee .each{
                otherFeeSum+=it.noteIncomeMoney
            }
            map.billMoney = eiaInvoiceSum
            map.noteIncomeMoney = invoiceIncomeSum
            def ifFinished = "否"
            if(outstandingAmountSum<=0){
                ifFinished = "是"
            }
            /**
             * 出账信息
             */
            def invoiceOutSum = 0
            def invoiceOutInfo  =  EiaIncomeOut.findAllByContractIdAndIfDelAndInvoiceTypeAndAccountState(it.id,false,GeneConstants.INVOICE_TYPE_OUT,GeneConstants.INVOICE_CONFIRM_YES)
            invoiceOutInfo .each{
                invoiceOutSum+=it.noteIncomeMoney
            }
            def reportFeeSum = 0
            def serSurPreSum = 0
            def preSurvCertFee = 0
            def preIssCertFee = 0
            def certServeFee = 0
            def otherContractFee = 0
            /**
             * 项目编制费
             */
            if(it?.reportFees>0){
                reportFeeSum =it.reportFees?:0
            }else{
                /**
                 * 其他费用
                 */
                if(it.otherFee){
                    otherContractFee =it.otherFee?:0
                }
                /**
                 * 发行前认证费
                 */
                if(it.preIssCertFee){
                    preIssCertFee =it.preIssCertFee?:0
                }
                /**
                 * 存续期认证费
                 */
                if(it.preSurvCertFee){
                    preSurvCertFee =it.preSurvCertFee?:0
                }
                /**
                 * 认证服务费
                 */
                if(it.certServeFee){
                    certServeFee =it.certServeFee?:0
                }
            }
            map.outstandingAmount = outstandingAmountSum
            map.ifFinished = ifFinished
            map.inputUserId = it.inputUserId
            map.expertFee   = expertFeeSum
            map.monitorFee = monitorFeeSum
            /**
             * 判断是否有财务预计
             */
            def isExcpet = false
            if(session?.staff?.funcCode.contains(FuncConstants.EIA_CWGL_YWRYZWGL_CWYJ)){
                isExcpet = true
            }
            map.isExcpet = isExcpet
            map.otherFee = otherFeeSum

            /**
             * 计算利润率
             */
            BigDecimal preIssCertFeeSum = new BigDecimal(preIssCertFee ? preIssCertFee : 0);
            BigDecimal preSurvCertFeeSum = new BigDecimal(preSurvCertFee ? preSurvCertFee : 0);
            BigDecimal certServeFeeSum = new BigDecimal(certServeFee ? certServeFee : 0);
            BigDecimal otherFeeContractSum = new BigDecimal(otherContractFee ? otherContractFee : 0);
            serSurPreSum =  preIssCertFeeSum+preSurvCertFeeSum+certServeFeeSum+otherFeeContractSum
            BigDecimal profitMarginMin = serSurPreSum + reportFeeSum
            if(profitMarginMin>0 &&it.contractMoney>0){
                map.profitMargin = profitMarginMin.divide(it.contractMoney?:0,4, RoundingMode.HALF_UP)?:0
            }else{
                map.profitMargin = new BigDecimal(0);
            }
            map.contractDate = it.contractDate?.format("yyyy-MM-dd")
            eiaContractData << map
        }
        /**
         * 合同不分页金额统计
         */
        def contractNotPageList = EiaContract.createCriteria().list() {
            if(params.contractName && params.contractName !="合同名称、合同编号、录入部门、录入人"){
                or{
                    like('contractNo','%'+params.contractName+'%')
                    like('contractName','%'+params.contractName+'%')
                    like("inputDept", "%" + params.contractName + "%")
                    like("inputUser", "%" + params.contractName + "%")
                }
            }else {
                if (session?.staff?.funcCode?.contains(FuncConstants.EIA_CWGL_YWRYZWGL_VIEWDEPT)) {
                    eq ("inputUserId", Long.valueOf(session.staff.staffId))
                }
            }
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
            eq("ifDel", false)
            eq("ifShow", true)
            order("id", "desc")
        }
        /**
         * 开票金额
         */
        def eiaInvoiceSum = 0
        /**
         * 进账金额
         */
        def invoiceIncomeSum = 0
        /**
         * 未结清
         */
        def outstandingAmountSum = 0
        /**
         * 出账信息(
         * 专家费(万元))
         */
        def expertFeeSum = 0
        /**
         * 出账信息(
         * 监测费(万元))
         */
        def monitorFeeSum = 0
        /**
         * 出账信息(
         * 其他((万元))
         */
        def otherFeeSum = 0
        /**
         * 合同金额
         */
        def contractMoneySum = 0
        def eiaContractNotPageData = []
        /**
         * 判断是否有合同，如果有合同执行计算各项的总计
         */
        if(contractNotPageList){
            eiaInvoiceSum=  EiaInvoice.findAllByContractIdInListAndIfDelAndAccountState(contractNotPageList.id,false,GeneConstants.INVOICE_CONFIRM_YES)*.billMoney.sum()?:0
            invoiceIncomeSum =  EiaIncomeOut.findAllByContractIdInListAndIfDelAndInvoiceTypeAndAccountState(contractNotPageList.id,false,GeneConstants.INVOICE_TYPE_INCOME,GeneConstants.INVOICE_CONFIRM_YES)*.noteIncomeMoney.sum()?:0
            expertFeeSum =  EiaIncomeOut.findAllByContractIdInListAndIfDelAndInvoiceTypeAndCostTypesAndAccountState(contractNotPageList.id,false,GeneConstants.INVOICE_TYPE_OUT,GeneConstants.INVOICE_ECPERT_FEES,GeneConstants.INVOICE_CONFIRM_YES)*.noteIncomeMoney.sum()?:0
            monitorFeeSum =  EiaIncomeOut.findAllByContractIdInListAndIfDelAndInvoiceTypeAndCostTypesAndAccountState(contractNotPageList.id,false,GeneConstants.INVOICE_TYPE_OUT,GeneConstants.INVOICE_MONITORING_FEES,GeneConstants.INVOICE_CONFIRM_YES)*.noteIncomeMoney.sum()?:0
            otherFeeSum  =  EiaIncomeOut.findAllByContractIdInListAndIfDelAndInvoiceTypeAndCostTypesAndAccountState(contractNotPageList.id,false,GeneConstants.INVOICE_TYPE_OUT,GeneConstants.INVOICE_OTHER_FEES,GeneConstants.INVOICE_CONFIRM_YES)*.noteIncomeMoney.sum()?:0
            contractMoneySum += contractNotPageList*.contractMoney.sum()?:0
        }
        def map = [:]
        map.billMoney = eiaInvoiceSum
        map.contractMoney = contractMoneySum
        map.noteIncomeMoney = invoiceIncomeSum
        map.outstandingAmount = contractMoneySum - invoiceIncomeSum
        map.expertFee   = expertFeeSum
        map.monitorFee = monitorFeeSum
        map.otherFee = otherFeeSum
        eiaContractNotPageData << map
        def dataMap = [:]
        dataMap.data = eiaContractData
        dataMap.totalDetail = eiaContractNotPageData
        dataMap.total = contractList.totalCount
        return dataMap
    }
    /**
     * 财务人员开票信息确认
     */
    def eiaInvoiceConfirm(eiaInvoiceId){
        def eiaInvoice = EiaInvoice.findByIdAndIfDel(eiaInvoiceId, false)
        eiaInvoice.accountState = GeneConstants.INVOICE_CONFIRM_YES
        eiaInvoice.save(flush:true,failOnError:true)
    }
    /**
     * 开票信息退回
     */
    def eiaInvoiceRevenueBack(eiaInvoiceId){
        def eiaInvoice = EiaInvoice.findByIdAndIfDel(eiaInvoiceId, false)
        eiaInvoice.accountState = GeneConstants.INVOICE_BACK
        eiaInvoice.save(flush:true,failOnError:true)
    }
    /**
     * 业务人员开票未确认信息数目
     */
    def eiaInvoiceRevNum(session){
        def revenueNum = EiaInvoice.countByIfDelAndAccountStateInList(false,[GeneConstants.INVOICE_ACCOUNT_STATE_NEW,GeneConstants.INVOICE_BACK,GeneConstants.INVOICE_ACCOUNT_STATE_SUB])
        if (!session?.staff?.funcCode?.contains(FuncConstants.EIA_CWGL_CWRYZWGL_VIEWALL)) {
            /**
             * 财务查看本部门财务数据
             */
            if (session?.staff?.funcCode?.contains(FuncConstants.EIA_CWGL_CWRYZWGL_VIEWDEPT)) {
                revenueNum = EiaInvoice.countByIfDelAndInputDeptCodeLikeAndAccountStateInList(false,"%"+ session.staff.orgCode +"%",[GeneConstants.INVOICE_ACCOUNT_STATE_NEW,GeneConstants.INVOICE_BACK,GeneConstants.INVOICE_ACCOUNT_STATE_SUB])
            }
            /**
             * 业务查看本部门财务数据
             */
            else if (session?.staff?.funcCode?.contains(FuncConstants.EIA_CWGL_YWRYZWGL_VIEWDEPT)) {
                revenueNum = EiaInvoice.countByIfDelAndInputDeptCodeLikeAndAccountStateInList(false,"%"+ session.staff.orgCode +"%",[GeneConstants.INVOICE_ACCOUNT_STATE_NEW,GeneConstants.INVOICE_BACK,GeneConstants.INVOICE_ACCOUNT_STATE_SUB])
            }
            /**
             * 查看本人客户数据
             */
            else if (session?.staff?.funcCode?.contains(FuncConstants.EIA_CWGL_YWRYZWGL_VIEWSELF)) {
                revenueNum = EiaInvoice.countByIfDelAndInputUserIdAndAccountStateInList(false, Long.valueOf(session.staff.staffId),[GeneConstants.INVOICE_ACCOUNT_STATE_NEW,GeneConstants.INVOICE_BACK,GeneConstants.INVOICE_ACCOUNT_STATE_SUB])

            }
        }
        return  revenueNum
    }
    /**
     * 业务人员开票已确认信息数目
     */
    def eiaInvoiceAlreadyRevNum(session){
        def revenueNum = EiaInvoice.countByIfDelAndAccountState(false,GeneConstants.INVOICE_CONFIRM_YES)
        if (!session?.staff?.funcCode?.contains(FuncConstants.EIA_CWGL_CWRYZWGL_VIEWALL)) {
            /**
             * 财务查看本部门财务数据
             */
            if (session?.staff?.funcCode?.contains(FuncConstants.EIA_CWGL_CWRYZWGL_VIEWDEPT)) {
                revenueNum = EiaInvoice.countByIfDelAndInputDeptCodeLikeAndAccountState(false,"%"+ session.staff.orgCode +"%",GeneConstants.INVOICE_CONFIRM_YES)
            }
            /**
             * 业务查看本部门财务数据
             */
            else if (session?.staff?.funcCode?.contains(FuncConstants.EIA_CWGL_YWRYZWGL_VIEWDEPT)) {
                revenueNum = EiaInvoice.countByIfDelAndInputDeptCodeLikeAndAccountState(false,"%"+ session.staff.orgCode +"%",GeneConstants.INVOICE_CONFIRM_YES)
            }
            /**
             * 查看本人客户数据
             */
            else if (session?.staff?.funcCode?.contains(FuncConstants.EIA_CWGL_YWRYZWGL_VIEWSELF)) {
                revenueNum = EiaInvoice.countByIfDelAndInputUserIdAndAccountState(false, Long.valueOf(session.staff.staffId),GeneConstants.INVOICE_CONFIRM_YES)

            }
        }
        return  revenueNum
    }
    /**
     * 业务人员出账信息数目
     */
    def eiaInvoiceOutNum(session){
        def revenueNum = EiaIncomeOut.countByIfDelAndInvoiceTypeAndAccountStateInList(false,GeneConstants.INVOICE_TYPE_OUT,[GeneConstants.INVOICE_ACCOUNT_STATE_NEW,GeneConstants.INVOICE_BACK,GeneConstants.INVOICE_ACCOUNT_STATE_SUB])
        if (!session?.staff?.funcCode?.contains(FuncConstants.EIA_CWGL_CWRYZWGL_VIEWALL)) {
            /**
             * 财务查看本部门财务数据
             */
            if (session?.staff?.funcCode?.contains(FuncConstants.EIA_CWGL_CWRYZWGL_VIEWDEPT)) {
                revenueNum = EiaIncomeOut.countByIfDelAndInvoiceTypeAndInputDeptCodeLikeAndAccountStateInList(false,GeneConstants.INVOICE_TYPE_OUT,"%"+ session.staff.orgCode +"%",[GeneConstants.INVOICE_ACCOUNT_STATE_NEW,GeneConstants.INVOICE_BACK,GeneConstants.INVOICE_ACCOUNT_STATE_SUB])
            }
            /**
             * 业务查看本部门财务数据
             */
            else if (session?.staff?.funcCode?.contains(FuncConstants.EIA_CWGL_YWRYZWGL_VIEWDEPT)) {
                revenueNum = EiaIncomeOut.countByIfDelAndInvoiceTypeAndInputDeptCodeLikeAndAccountStateInList(false,GeneConstants.INVOICE_TYPE_OUT,"%"+ session.staff.orgCode +"%",[GeneConstants.INVOICE_ACCOUNT_STATE_NEW,GeneConstants.INVOICE_BACK,GeneConstants.INVOICE_ACCOUNT_STATE_SUB])
            }
            /**
             * 查看本人客户数据
             */
            else if (session?.staff?.funcCode?.contains(FuncConstants.EIA_CWGL_YWRYZWGL_VIEWSELF)) {
                revenueNum = EiaIncomeOut.countByIfDelAndInvoiceTypeAndInputUserIdAndAccountStateInList(false,GeneConstants.INVOICE_TYPE_OUT,Long.parseLong(session.staff.staffId),[GeneConstants.INVOICE_ACCOUNT_STATE_NEW,GeneConstants.INVOICE_BACK,GeneConstants.INVOICE_ACCOUNT_STATE_SUB])
            }
        }
        return  revenueNum
    }
    /**
     * 财务人员开票信息数目
     */
    def eiaInvoiceRevCissNum(session){
        def revenueNum = EiaInvoice.countByIfDelAndAccountState(false,GeneConstants.INVOICE_ACCOUNT_STATE_SUB)
        if (!session?.staff?.funcCode?.contains(FuncConstants.EIA_CWGL_CWRYZWGL_VIEWALL)) {
            /**
             * 财务查看本部门财务数据
             */
            if (session?.staff?.funcCode?.contains(FuncConstants.EIA_CWGL_CWRYZWGL_VIEWDEPT)) {
                revenueNum = EiaInvoice.countByIfDelAndAccountStateAndInputDeptCodeLike(false,GeneConstants.INVOICE_ACCOUNT_STATE_SUB,"%"+ session.staff.orgCode +"%")
            }
            /**
             * 业务查看本部门财务数据
             */
            else if (session?.staff?.funcCode?.contains(FuncConstants.EIA_CWGL_YWRYZWGL_VIEWDEPT)) {
                revenueNum = EiaInvoice.countByIfDelAndAccountStateAndInputDeptCodeLike(false,GeneConstants.INVOICE_ACCOUNT_STATE_SUB,"%"+ session.staff.orgCode +"%")
            }
            /**
             * 查看本人客户数据
             */
            else if (session?.staff?.funcCode?.contains(FuncConstants.EIA_CWGL_YWRYZWGL_VIEWSELF)) {
                revenueNum = EiaInvoice.countByIfDelAndAccountStateAndInputUserId(false,GeneConstants.INVOICE_ACCOUNT_STATE_SUB,Long.valueOf(session.staff.staffId))
            }
        }
        return  revenueNum
    }
    /**
     * 财务人员出账信息数目
     */
    def eiaInvoiceOutCissNum(session){
        def revenueNum = EiaIncomeOut.countByIfDelAndAccountStateAndInvoiceType(false,GeneConstants.INVOICE_ACCOUNT_STATE_SUB,GeneConstants.INVOICE_TYPE_OUT)
        if (!session?.staff?.funcCode?.contains(FuncConstants.EIA_CWGL_CWRYZWGL_VIEWALL)) {
            /**
             * 财务查看本部门财务数据
             */
            if (session?.staff?.funcCode?.contains(FuncConstants.EIA_CWGL_CWRYZWGL_VIEWDEPT)) {
                revenueNum = EiaIncomeOut.countByIfDelAndAccountStateAndInvoiceTypeAndInputDeptCodeLike(false,GeneConstants.INVOICE_ACCOUNT_STATE_SUB,GeneConstants.INVOICE_TYPE_OUT,"%"+ session.staff.orgCode +"%")
            }
            /**
             * 业务查看本部门财务数据
             */
            else if (session?.staff?.funcCode?.contains(FuncConstants.EIA_CWGL_YWRYZWGL_VIEWDEPT)) {
                revenueNum = EiaIncomeOut.countByIfDelAndAccountStateAndInvoiceTypeAndInputDeptCodeLike(false,GeneConstants.INVOICE_ACCOUNT_STATE_SUB,GeneConstants.INVOICE_TYPE_OUT,"%"+ session.staff.orgCode +"%")
            }
            /**
             * 查看本人客户数据
             */
            else if (session?.staff?.funcCode?.contains(FuncConstants.EIA_CWGL_YWRYZWGL_VIEWSELF)) {
                revenueNum = EiaIncomeOut.countByIfDelAndAccountStateAndInvoiceTypeAndInputUserId(false,GeneConstants.INVOICE_ACCOUNT_STATE_SUB,GeneConstants.INVOICE_TYPE_OUT,Long.valueOf(session.staff.staffId))
            }
        }
        return  revenueNum
    }
    /**
     * 合同对应开票信息
     */
    def eiaInvoiceQuery(params,session){
        int page = params.int('page');
        def dataMap = [:]
        def result = []
        if(params.contractId){
            def  invoiceRevenue = EiaInvoice.createCriteria().list(offset: 10*(page-1) , max: 10) {
                eq('contractId', params.long("contractId"))
                eq("accountState",GeneConstants.INVOICE_CONFIRM_YES)
                eq("ifDel",false)
            }

            invoiceRevenue.each {
                def map=[:]

                map.id = it?.id
                map.contractNo =  it?.contractNo
                map.contractName =  it?.contractName
                map.accountState =  it?.accountState?:""
                SimpleDateFormat formaDate = new SimpleDateFormat("yyyy-MM-dd");
                map.billDate = formaDate.format(it.billDate)
                map.addrTel =  it?.addrTel?:""
                map.billMoney =  it?.billMoney?:""
                map.inputUser =  it?.inputUser?:""
                map.inputDept =  it?.inputDept?:""
                result.add(map)
            }
            dataMap.total = invoiceRevenue.totalCount
        }
        dataMap.data = result
        return dataMap
    }
}
