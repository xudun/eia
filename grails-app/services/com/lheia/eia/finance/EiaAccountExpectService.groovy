package com.lheia.eia.finance

import com.lheia.eia.common.FuncConstants
import com.lheia.eia.common.GeneConstants
import com.lheia.eia.common.HttpMesConstants
import com.lheia.eia.contract.EiaContract
import grails.gorm.transactions.Transactional

import java.text.SimpleDateFormat

@Transactional
class EiaAccountExpectService {
    /**
     * 财务预计信息保存
     */
    def eiaAccountExpectSave(params,session) {
        def eiaAccountExpect

        if(params.eiaAccountExpectId){
            eiaAccountExpect = EiaAccountExpect.findByIfDelAndId(false,params.long("eiaAccountExpectId"))
            eiaAccountExpect.properties = params
        }else {
            /**
             * 当月如果期次已经存在，则不重新生成
             */
            def eiaAccountExpectList = EiaAccountExpect.findAllByIfDelAndExpectPeriodAndInputUserIdAndEiaContractId(false,params.expectPeriod,session.staff.staffId,params.long("contractId"))
            if(eiaAccountExpectList.size()>0){
                return HttpMesConstants.MSG_DATA_EXIST
            }else{
                eiaAccountExpect = new EiaAccountExpect(params)
                def contract = EiaContract.findByIdAndIfDel(params.long("contractId"),false)
                eiaAccountExpect.eiaClientId = contract?.eiaClientId
                eiaAccountExpect.eiaContractId = contract?.id
                eiaAccountExpect.contractNo = contract?.contractNo
                eiaAccountExpect.contractName = contract?.contractName
                eiaAccountExpect.accountState = GeneConstants.INVOICE_ACCOUNT_STATE_NEW
                eiaAccountExpect.inputDept = session.staff.orgName
                eiaAccountExpect.inputDeptCode = session.staff.orgCode
                eiaAccountExpect.inputDeptId = Long.parseLong(session.staff.orgId)
                eiaAccountExpect.inputUser = session.staff.staffName
                eiaAccountExpect.inputUserId = Long.parseLong(session.staff.staffId)
            }

        }
        eiaAccountExpect.save(flush:true,failOnError:true)
    }
    /**
     * 预计列表
     */
    def eiaExpectQuery(params,session){
        int page = params.int('page');
        def  eiaAccountExpect = EiaAccountExpect.createCriteria().list(offset: 10*(page-1) , max: 10) {
            eq("ifDel",false)
            /**
             * 如果是财务审核，则现在已提交
             */
            if(params.accountState){
                eq("accountState",GeneConstants.INVOICE_ACCOUNT_STATE_SUB)
            }else{
                'in'("accountState",[GeneConstants.INVOICE_ACCOUNT_STATE_NEW,GeneConstants.INVOICE_BACK,GeneConstants.INVOICE_ACCOUNT_STATE_SUB])
            }
            def contractName = params.contractName
            if (contractName && !"合同名称,合同编号,录入部门,录入人".equals(contractName)) {
                or{
                    like("contractName", "%" + contractName + "%")
                    like("inputDept", "%" + contractName + "%")
                    like("inputUser", "%" + contractName + "%")
                    like("contractNo", "%" + contractName + "%")
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
        }
        def result = []
        eiaAccountExpect.each {
            def map=[:]
            map.id = it?.id
            map.contractNo =  it?.contractNo
            map.contractName =  it?.contractName
            map.inputUser =  it?.inputUser?:""
            map.inputDept =  it?.inputDept?:""
            map.expectInvoiceMoney =  it?.expectInvoiceMoney?:""
            map.expectIncomeMoney =  it?.expectIncomeMoney?:""
            map.expertFee =  it?.expertFee?:""
            map.monitorFee =  it?.monitorFee?:""
            map.otherFee =  it?.otherFee?:""
            map.expectPeriod =  it?.expectPeriod?:""
            map.inputUserId =  it?.inputUserId?:""
            map.accountState =  it?.accountState?:""
            result.add(map)
        }
        def dataMap = [:]
        dataMap.data = result
        dataMap.total = eiaAccountExpect.totalCount
        return dataMap
    }
    /**
     * 编辑回显
     */
    def eiaAccountExpectMap(eiaAccountExpectId){
        def eiaInvoice = EiaAccountExpect.findByIdAndIfDel(eiaAccountExpectId, false)
        return eiaInvoice
    }
    /**
     * 预计数量(业务人员)
     */
    def eiaExpectNum(session){
        def revenueNum = EiaAccountExpect.countByIfDelAndAccountStateInList(false,[GeneConstants.INVOICE_ACCOUNT_STATE_NEW,GeneConstants.INVOICE_BACK,GeneConstants.INVOICE_ACCOUNT_STATE_SUB])
        if (!session?.staff?.funcCode?.contains(FuncConstants.EIA_CWGL_CWRYZWGL_VIEWALL)) {
            /**
             * 财务查看本部门财务数据
             */
            if (session?.staff?.funcCode?.contains(FuncConstants.EIA_CWGL_CWRYZWGL_VIEWDEPT)) {
                revenueNum = EiaAccountExpect.countByIfDelAndInputDeptCodeLikeAndAccountStateInList(false,"%"+ session.staff.orgCode +"%",[GeneConstants.INVOICE_ACCOUNT_STATE_NEW,GeneConstants.INVOICE_BACK,GeneConstants.INVOICE_ACCOUNT_STATE_SUB])
            }
            /**
             * 业务查看本部门财务数据
             */
            else if (session?.staff?.funcCode?.contains(FuncConstants.EIA_CWGL_YWRYZWGL_VIEWDEPT)) {
                revenueNum = EiaAccountExpect.countByIfDelAndInputDeptCodeLikeAndAccountStateInList(false,"%"+ session.staff.orgCode +"%",[GeneConstants.INVOICE_ACCOUNT_STATE_NEW,GeneConstants.INVOICE_BACK,GeneConstants.INVOICE_ACCOUNT_STATE_SUB])
            }
            /**
             * 查看本人客户数据
             */
            else if (session?.staff?.funcCode?.contains(FuncConstants.EIA_CWGL_YWRYZWGL_VIEWSELF)) {
                revenueNum = EiaAccountExpect.countByIfDelAndInputUserIdAndAccountStateInList(false,Long.parseLong(session.staff.staffId),[GeneConstants.INVOICE_ACCOUNT_STATE_NEW,GeneConstants.INVOICE_BACK,GeneConstants.INVOICE_ACCOUNT_STATE_SUB])
            }
        }
        return  revenueNum
    }
    /**
     * 预计数量（财务人员）
     */
    def eiaExpectCissNum(session){
        def revenueNum = EiaAccountExpect.countByIfDelAndAccountStateInList(false,[GeneConstants.INVOICE_ACCOUNT_STATE_SUB])
        if (!session?.staff?.funcCode?.contains(FuncConstants.EIA_CWGL_CWRYZWGL_VIEWALL)) {
            /**
             * 财务查看本部门财务数据
             */
            if (session?.staff?.funcCode?.contains(FuncConstants.EIA_CWGL_CWRYZWGL_VIEWDEPT)) {
                revenueNum = EiaAccountExpect.countByIfDelAndInputDeptCodeLikeAndAccountStateInList(false,"%"+ session.staff.orgCode +"%",[GeneConstants.INVOICE_ACCOUNT_STATE_SUB])
            }
            /**
             * 业务查看本部门财务数据
             */
            else if (session?.staff?.funcCode?.contains(FuncConstants.EIA_CWGL_YWRYZWGL_VIEWDEPT)) {
                revenueNum = EiaAccountExpect.countByIfDelAndInputDeptCodeLikeAndAccountStateInList(false,"%"+ session.staff.orgCode +"%",[GeneConstants.INVOICE_ACCOUNT_STATE_SUB])
            }
            /**
             * 查看本人客户数据
             */
            else if (session?.staff?.funcCode?.contains(FuncConstants.EIA_CWGL_YWRYZWGL_VIEWSELF)) {
                revenueNum = EiaAccountExpect.countByIfDelAndInputUserIdAndAccountStateInList(false,Long.parseLong(session.staff.staffId),[GeneConstants.INVOICE_ACCOUNT_STATE_SUB])
            }
        }
        return  revenueNum
    }
    /**
     * 预计提交
     */
    def eiaExpectSubmit(eiaAccountExpectId){
        def eiaAccountExpect = EiaAccountExpect.findByIdAndIfDel(eiaAccountExpectId, false)
        eiaAccountExpect.accountState = GeneConstants.INVOICE_ACCOUNT_STATE_SUB
        eiaAccountExpect.save(flush: true, failOnError: true)
    }
    /**
     * 预计删除
     */
    def eiaAccountExpectDel(eiaAccountExpectId){
        def eiaAccountExpect = EiaAccountExpect.findByIdAndIfDel(eiaAccountExpectId, false)
        eiaAccountExpect.ifDel = true
        eiaAccountExpect.save(flush:true,failOnError:true)
    }
    /**
     * 财务预计确认
     */
    def eiaAccountExpectConfirm(eiaAccountExpectId){
        def eiaAccountExpect = EiaAccountExpect.findByIdAndIfDel(eiaAccountExpectId, false)
        eiaAccountExpect.accountState = GeneConstants.INVOICE_CONFIRM_YES
        eiaAccountExpect.save(flush:true,failOnError:true)
    }
    /**
     * 驳回财务预计
     */
    def eiaAccountExpectBack(eiaAccountExpectId){
        def eiaAccountExpect = EiaAccountExpect.findByIdAndIfDel(eiaAccountExpectId, false)
        eiaAccountExpect.accountState = GeneConstants.INVOICE_BACK
        eiaAccountExpect.save(flush:true,failOnError:true)
    }
    /**
     * 财务预计信息
     */
    def getEiaAccountExpectData(params,session){
        int page
        int limit
        if (params.page && params.limit) {
            page = params.int('page') - 1
            limit = params.int('limit')
        }
        def  eiaAccountExpect = EiaAccountExpect.createCriteria().list(offset: 10*(page-1) , max: 10) {
            eq("ifDel",false)
            eq("accountState",GeneConstants.INVOICE_CONFIRM_YES)
            if(params.long("contractId")){
                eq("eiaContractId",params.long("contractId"))
            }
            if(params.expectPeriod){
                eq("expectPeriod",params.expectPeriod)
            }else {
                Calendar calendar = Calendar.getInstance();
                def expectPeriodMonth
                int month = calendar.get(Calendar.MONTH)
                if(month+1>9){
                    expectPeriodMonth = month+1
                }else{
                    expectPeriodMonth = "0"+(month+1)
                }
                def expectPeriod = calendar.get(Calendar.YEAR)+"-"+expectPeriodMonth
                eq("expectPeriod",expectPeriod)
            }
            def contractName = params.contractName
            if (contractName && !"合同名称,合同编号,录入部门,录入人".equals(contractName)) {
                or{
                    like("contractName", "%" + contractName + "%")
                    like("inputDept", "%" + contractName + "%")
                    like("inputUser", "%" + contractName + "%")
                    like("contractNo", "%" + contractName + "%")
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
        }
        def result = []
        eiaAccountExpect.each {
            def map=[:]
            map.id = it?.id
            map.contractNo =  it?.contractNo
            map.contractName =  it?.contractName
            map.inputUser =  it?.inputUser?:""
            map.inputDept =  it?.inputDept?:""
            map.expectInvoiceMoney =  it?.expectInvoiceMoney?:""
            map.expectIncomeMoney =  it?.expectIncomeMoney?:""
            map.expertFee =  it?.expertFee?:""
            map.monitorFee =  it?.monitorFee?:""
            map.otherFee =  it?.otherFee?:""
            map.expectPeriod =  it?.expectPeriod?:""
            map.inputUserId =  it?.inputUserId?:""
            map.accountState =  it?.accountState?:""
            def eiaContract =  EiaContract.findByIfDelAndId(false,it.eiaContractId)
            map.contractMoney =  eiaContract?.contractMoney?:""
            /**
             * 实际回款和开票信息
             */
            def eiaIncomeOut = EiaIncomeOut.findByIfDelAndContractIdAndAccountStateAndCostTypes(false,it.eiaContractId,GeneConstants.INVOICE_CONFIRM_YES,GeneConstants.INVOICE_TYPE_INCOME)
            map.incomeMoney =  eiaIncomeOut?.noteIncomeMoney?:0
            /**
             * 截止上月累计发生业务成本
             */
            Calendar calendar = Calendar.getInstance();
            def expectPeriodMonth
            int month = calendar.get(Calendar.MONTH)
            if(month+1>9){
                expectPeriodMonth = month+1
            }else{
                expectPeriodMonth = "0"+(month+1)
            }
            def eiaContractId = it?.eiaContractId
            def startDate
            def sdf = new SimpleDateFormat("yyyy-MM-dd")
            def expectPeriod = calendar.get(Calendar.YEAR)+"-"+expectPeriodMonth+"-"+"01"
            startDate =  sdf.parse(expectPeriod)
            def  monitorFeeSum = EiaIncomeOut.createCriteria().list() {
                eq('contractId', eiaContractId)
                eq("costTypes",GeneConstants.INVOICE_MONITORING_FEES)
                eq("accountState",GeneConstants.INVOICE_CONFIRM_YES)
                eq("ifDel",false)
                le("noteIncomeDate", startDate)
            }.noteIncomeMoney.sum()
            map.monitorFeeSum =monitorFeeSum?:0
            /**
             * 专家费
             */
            def  expertFeeSum = EiaIncomeOut.createCriteria().list() {
                eq('contractId', eiaContractId)
                eq("costTypes",GeneConstants.INVOICE_ECPERT_FEES)
                eq("accountState",GeneConstants.INVOICE_CONFIRM_YES)
                eq("ifDel",false)
                le("noteIncomeDate", startDate)
            }.noteIncomeMoney.sum()
            map.expertFeeSum =expertFeeSum?:0
            /**
             * 其他
             */
            def  otherFeeSum = EiaIncomeOut.createCriteria().list() {
                eq('contractId', eiaContractId)
                eq("costTypes",GeneConstants.INVOICE_OTHER_FEES)
                eq("accountState",GeneConstants.INVOICE_CONFIRM_YES)
                eq("ifDel",false)
                le("noteIncomeDate", startDate)
            }.noteIncomeMoney.sum()
            map.otherFeeSum =otherFeeSum?:0
            result.add(map)
        }
        def dataMap = [:]
        dataMap.data = result
        dataMap.total = eiaAccountExpect.totalCount
        return dataMap
    }
}
