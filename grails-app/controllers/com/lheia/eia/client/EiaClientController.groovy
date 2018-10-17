package com.lheia.eia.client

import com.lheia.eia.common.HttpMesConstants
import grails.converters.JSON

class EiaClientController {
    def eiaClientService
    /**
     * 客户信息列表
     */
    def eiaClientIndex() {
    }
    /**
     * 客户信息列表
     */
    def getEiaClientDataList(){
        def dataMap = eiaClientService.eiaClientQueryPage(params, session)
        if (dataMap) {
            render([code: HttpMesConstants.CODE_OK, count: dataMap.total, data: dataMap.data] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
    /**
     * 新增客户
     */
    def eiaClientCreate(){
        def eiaClient = eiaClientService.getEiaClientDataMap(params.long("eiaClientId"))
        [eiaClient:eiaClient,pageType:params.pageType]
    }
    /**
     * 客户信息编辑回显
     * @return
     */
    def getEiaClientDataMap() {
        if (params.eiaClientId) {
            def eiaClient = eiaClientService.getEiaClientDataMap(params.long("eiaClientId"))
            if (eiaClient) {
                render([code: HttpMesConstants.CODE_OK, data: eiaClient] as JSON)
            } else {
                render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
            }
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
    /**
     * 客户信息保存
     */
    def eiaClientSave(){
        if(params.long("eiaClientId")){
            def eiaClient = eiaClientService.eiaClientUpdate(params,session)
            if (eiaClient) {
                if (eiaClient == HttpMesConstants.MSG_ADDCLIENT_FALSE) {
                    render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_ADDCLIENT_FALSE, data: eiaClient] as JSON)
                }else {
                    render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SAVE_OK, data: eiaClient] as JSON)
                }
            } else {
                render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
            }
        }else{
            def eiaClient = eiaClientService.eiaClientSave(params,session)
            if (eiaClient) {
                if (eiaClient == HttpMesConstants.MSG_ADDCLIENT_FALSE) {
                    render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_ADDCLIENT_FALSE, data: eiaClient] as JSON)
                }else{
                    render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SAVE_OK, data: eiaClient] as JSON)
                }
            } else {
                render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
            }
        }
    }
    /**
     * 客户联系人信息列表
     */
    def getEiaClientContactsDataList(){
        def dataMap = eiaClientService.eiaClientContactsQueryPage(params, session)
        if (dataMap) {
            render([code: HttpMesConstants.CODE_OK, count: dataMap.total, data: dataMap.data] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
    /**
     * 新增客户联系人
     */
    def eiaClientContactsCreate(){
        def eiaClient = eiaClientService.getEiaClientContactsDataMap(params.long("clientContactsId"))
        [eiaClient:eiaClient]
    }
    /**
     * 客户联系人信息编辑回显
     * @return
     */
    def getEiaClientContactsDataMap() {
        if (params.clientContactsId) {
            def clientContacts = eiaClientService.getEiaClientContactsDataMap(params.long("clientContactsId"))
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
     * 客户联系人新增保存
     */
    def eiaClientContactsSave(){

        if(params.long("clientContactsId")){
            def eiaClientContacts = eiaClientService.eiaClientContactsUpdate(params,session)
            if (eiaClientContacts) {
                if (eiaClientContacts == HttpMesConstants.MSG_ADD_CONTACT_FALSE) {
                    render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_ADDCLIENT_FALSE, data: eiaClientContacts] as JSON)
                }else {
                    render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SAVE_OK, data: eiaClientContacts] as JSON)
                }
            } else {
                render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
            }
        }else{
            def eiaClientContacts = eiaClientService.eiaClientContactsSave(params,session)
            if (eiaClientContacts) {
                if (eiaClientContacts == HttpMesConstants.MSG_ADD_CONTACT_FALSE) {
                    render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_ADD_CONTACT_FALSE, data: eiaClientContacts] as JSON)
                }else {
                    render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SAVE_OK, data: eiaClientContacts] as JSON)
                }
            } else {
                render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
            }
        }
    }
    /**
     * 客户信息删除
     */
    def eiaClientDel(){
        def eiaClient = eiaClientService.eiaClientDel(params)
        if (eiaClient) {
            render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_DEL_OK, data: eiaClient] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DEL_FAIL] as JSON)
        }
    }
    /**
     * 客户联系人信息删除
     */
    def eiaClientContactsDel(){
        def eiaClientContacts = eiaClientService.eiaClientContactsDel(params.long("clientContactsId"))
        if (eiaClientContacts) {
            render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_DEL_OK, data: eiaClientContacts] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DEL_FAIL] as JSON)
        }
    }
    /**
     * 客户信息详情
     */
    def eiaClientDetail(){
        def eiaClient = eiaClientService.getEiaClientDataMap(params.long("eiaClientId"))
        [eiaClient:eiaClient]
    }

    /**
     * 报价和合同新增客户信息保存
     */
    def eiaOfferClientCreate(){
    }
    /**
     * 报价和合同新增客户保存
     */
    def eiaOfferClientSave(){
        def eiaClient = eiaClientService.eiaOfferClientSave(params,session)
        if (eiaClient) {
            if (eiaClient == HttpMesConstants.MSG_ADDCLIENT_FALSE) {
                render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_ADDCLIENT_FALSE, data: eiaClient] as JSON)
            }else{
                render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SAVE_OK, data: eiaClient] as JSON)
            }
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
        }
    }
    /**
     * 报价中选择客户
     */
    def eiaClientSelect(){

    }
    /**
     * 报价中选择客户联系人
     */
    def eiaContactSelect(){
        /**
         * 如果联系人名称为空，则删除
         */
        eiaClientService.clientContactDel()
        [eiaClientId:params.eiaClientId]
    }
    /**
     * 修改联系人信息
     */
    def clientContactUpdate(){
        def eiaClient = eiaClientService.clientContactUpdate(params,session)
        if (eiaClient) {
            if (eiaClient == HttpMesConstants.MSG_ADD_CONTACT_FALSE) {
                render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_ADD_CONTACT_FALSE, data: eiaClient] as JSON)
            }else{
                render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SAVE_OK, data: eiaClient] as JSON)
            }
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
        }

    }
    /**
     * 联系人新增行
     */
    def clientContactAddUpdate(){
        def eiaClient = eiaClientService.clientContactAddUpdate(params,session)
        if (eiaClient) {
            if (eiaClient == HttpMesConstants.MSG_ADD_CONTACT_FALSE) {
                render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_ADD_CONTACT_FALSE, data: eiaClient] as JSON)
            }else{
                render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SAVE_OK, data: eiaClient] as JSON)
            }
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
        }
    }
    /**
     * 删除联系人和联系电话为空
     */
    def clientContactDel(){
        def eiaClient = eiaClientService.clientContactDel(params,session)
        if (eiaClient) {
            render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SAVE_OK, data: eiaClient] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
        }
    }
    /**
     * 修改客户信息
     */
    def eiaClientUpdate(){
        def eiaClient = eiaClientService.eiaClientUpdate(params,session)
        if (eiaClient) {
            if (eiaClient == HttpMesConstants.MSG_ADDCLIENT_FALSE) {
                render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_ADD_CONTACT_FALSE, data: eiaClient] as JSON)
            }else{
                render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SAVE_OK, data: eiaClient] as JSON)
            }
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
        }

    }
}
