package com.lheia.eia.client

import com.lheia.eia.common.HttpMesConstants
import grails.converters.JSON

class EiaClientConfigController {
    def eiaClientConfigService
    def eiaCliConfigIndex() {

    }
    /**
     * 客户选择列表
     */
    def invoiceSelectQueryPage(){
        def dataMap = eiaClientConfigService.invoiceSelectQueryPage(params, session)
        if (dataMap) {
            render([code: HttpMesConstants.CODE_OK, count: dataMap.total, data: dataMap.data] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
    /**
     * 客户信息新增
     */
    def eiaCliConfigCreate(){

    }
    /**
     * 客户配置信息保存
     */
    def eiaClientConfigSave(){
        if(params.long("eiaClientConfigId")){
            def eiaClient = eiaClientConfigService.eiaClientConfigUpdate(params)
            if (eiaClient) {
                if (eiaClient == HttpMesConstants.MSG_ADD_INVOICE_FALSE) {
                    render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_ADD_INVOICE_FALSE, data: eiaClient] as JSON)
                }else {
                    render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SAVE_OK, data: eiaClient] as JSON)
                }
            } else {
                render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
            }
        }else{
            def eiaClient = eiaClientConfigService.eiaClientConfigSave(params,session)
            if (eiaClient) {
                if (eiaClient == HttpMesConstants.MSG_ADD_INVOICE_FALSE) {
                    render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_ADD_INVOICE_FALSE, data: eiaClient] as JSON)
                }else{
                    render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SAVE_OK, data: eiaClient] as JSON)
                }
            } else {
                render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
            }
        }
    }
    /**
     * 财务开户信息编辑回显
     * @return
     */
    def getEiaClientConfigDataMap() {
        if (params.eiaClientConfigId) {
            def clientContacts = eiaClientConfigService.getEiaClientConfigDataMap(params.long("eiaClientConfigId"))
            if (clientContacts) {
                render([code: HttpMesConstants.CODE_OK, data: clientContacts] as JSON)
            } else {
                render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
            }
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
    /**
     * 客户信息删除
     */
    def eiaClientConfigDel(){
        def eiaClient = eiaClientConfigService.eiaClientConfigDel(params.long("eiaClientConfigId"))
        if (eiaClient) {
            render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_DEL_OK, data: eiaClient] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DEL_FAIL] as JSON)
        }
    }
}
