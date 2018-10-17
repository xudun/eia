package com.lheia.eia.client

import com.lheia.eia.common.HttpMesConstants
import grails.gorm.transactions.Transactional

@Transactional
class EiaClientConfigService {
    /**
     * 客户选择列表
     */
    def invoiceSelectQueryPage(params,session){
        int page = params.int('page');
        def  invoiceRevenue = EiaClientConfig.createCriteria().list(offset: 10*(page-1) , max: 10) {
            if(params.eiaClientName && params.eiaClientName!="客户名称"){
                like('clientName','%'+params.eiaClientName+'%')
            }
//            /**
//             * 查看全部的客户数据
//             */
//            if (!session?.staff?.funcCode?.contains(FuncConstants.EIA_YWCX_HTCX_VIEWALL)) {
//                /**
//                 * 查看本部门客户数据
//                 */
//                if (session?.staff?.funcCode?.contains(FuncConstants.EIA_YWCX_HTCX_VIEWDEPT)) {
//                    like("inputDeptCode", "%"+ session.staff.orgCode+ "%")
//                }
//                /**
//                 * 查看本人客户数据
//                 */
//                else if (session?.staff?.funcCode?.contains(FuncConstants.EIA_YWCX_HTCX_VIEWSELF)) {
//                    eq ("inputUserId", Long.valueOf(session.staff.staffId))
//                }
//            }
            /**
             * invoiceType判断类型
             * 如果不为空，则是根据客户信息中进入，如果为空，在是在开票信息中进入
             */
            if(params.invoiceType){
                eq("eiaClientId",params.long("eiaClientId"))
            }
            eq("ifDel",false)
            order("id", "desc")
        }
        def result = []
        invoiceRevenue.each {
            def map=[:]
            map.id = it?.id
            map.clientName =  it?.clientName
            map.taxRegCode =  it?.taxRegCode
            map.bankName =  it?.bankName?:""
            map.bankAccount =  it?.bankAccount?:""
            map.addrTel =  it?.addrTel?:""
            result.add(map)
        }
        def dataMap = [:]
        dataMap.data = result
        dataMap.total = invoiceRevenue.totalCount
        return dataMap
    }
    /**
     * 保存客户配置信息
     * @param params
     * @param session
     * @return
     */
    def eiaClientConfigSave(params,session) {
        /**
         * 判断客户联系人是否存在
         */
        def eiaClient = EiaClient.findByIdAndIfDel(params.long("eiaClientId"),false)
        def eiaClientExist = eiaClientSaveExist(eiaClient?.clientName,params.taxRegCode,params.bankName,params.bankAccount)
        if(eiaClientExist){
            return HttpMesConstants.MSG_ADD_INVOICE_FALSE
        }else{
            def eiaClientConfig = new EiaClientConfig()
            eiaClientConfig.properties = params
            eiaClientConfig.inputDept = session.staff.orgName
            eiaClientConfig.inputDeptCode = session.staff.orgCode
            eiaClientConfig.inputDeptId = Long.parseLong(session.staff.orgId)
            eiaClientConfig.inputUser = session.staff.staffName
            eiaClientConfig.inputUserId = Long.parseLong(session.staff.staffId)
            eiaClientConfig.eiaClientId = params.long("eiaClientId")
            eiaClientConfig.save(flush: true, failOnError: true)
        }
    }
    /**
     * 客户配置信息编辑
     */
    def eiaClientConfigUpdate(params){
        /**
         * 判断客户是否存在
         */
        def eiaClientConfig = EiaClientConfig.findByIdAndIfDel(params.long("eiaClientConfigId"),false)
        def eiaClientExist = eiaClientUpdateExist(eiaClientConfig?.clientName,params)
        if(eiaClientExist){
            return HttpMesConstants.MSG_ADD_INVOICE_FALSE
        }else{
            eiaClientConfig.properties = params
            eiaClientConfig.save(flush: true, failOnError: true)
        }

    }
    /**
     * 判断客户信息是否已存在（根据客户名称）
     */
    def eiaClientSaveExist(clientName,taxRegCode,bankName,bankAccount){
        def eiaClientList = EiaClientConfig.findAllByIfDelAndClientNameAndTaxRegCodeAndBankNameAndBankAccount(false,clientName,taxRegCode,bankName,bankAccount)
        /**
         * 判断客户信息是否存在
         */
        if(eiaClientList.size()>0){
            return true
        }else{
            return false
        }
    }
    /**
     * 判断客户信息是否已存在（根据客户名称）
     */
    def eiaClientUpdateExist(clientName,params){
        def eiaClientList = EiaClientConfig.findAllByIfDelAndClientNameAndTaxRegCodeAndBankNameAndBankAccountAndIdNotEqual(false,clientName,params.taxRegCode,params.bankName,params.bankAccount,params.eiaClientConfigId)
        /**
         * 判断客户信息是否存在
         */
        if(eiaClientList.size()>0){
            return true
        }else{
            return false
        }
    }
    /**
     * 财务开票信息编辑回显
     */
    def getEiaClientConfigDataMap(eiaClientConfigId){
        return EiaClientConfig.findByIdAndIfDel(eiaClientConfigId,false)
    }
    /**
     * 开户信息删除
     */
    def eiaClientConfigDel(eiaClientConfigId){
        def eiaClientConfig = getEiaClientConfigDataMap(eiaClientConfigId)
        eiaClientConfig.ifDel = true
        eiaClientConfig.save(flush: true, failOnError: true)
    }

}
