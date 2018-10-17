package com.lheia.eia.finance

import com.lheia.eia.common.HttpMesConstants
import grails.converters.JSON

class EiaAccountExpectController {
    def eiaAccountExpectService
    def index() {}
    /**
     * 预计费用新增
     */
    def eiaExpectCreate(){

    }
    /**
     * 财务预计新增
     */
    def eiaAccountExpectSave(){
        def eiaAccountExpect = eiaAccountExpectService.eiaAccountExpectSave(params,session)
        if (eiaAccountExpect) {
            if(eiaAccountExpect == HttpMesConstants.MSG_DATA_EXIST){
                render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
            }else{
                render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SAVE_OK, data: eiaAccountExpect] as JSON)}
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
        }
    }
    /**
     * 预计列表
     */
    def eiaExpectQuery(){
        def dataMap = eiaAccountExpectService.eiaExpectQuery(params, session)
        if (dataMap) {
            render([code: HttpMesConstants.CODE_OK, count: dataMap.total, data: dataMap.data] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
    /**
     *编辑回显
     */
    def eiaAccountExpectMap(){
        def eiaAccountExpect = eiaAccountExpectService.eiaAccountExpectMap(params.long('eiaAccountExpectId'))
        if (eiaAccountExpect) {
            render([code: HttpMesConstants.CODE_OK, data: eiaAccountExpect] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
    /**
     * 预计数量
     */
    def eiaExpectNum(){
        def eiaAccountExpect = eiaAccountExpectService.eiaExpectNum(session)
        if (eiaAccountExpect) {
            render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_DEL_OK, data: eiaAccountExpect] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DEL_FAIL] as JSON)
        }
    }
    /**
     * 预计数量（财务）
     */
    def eiaExpectCissNum(){
        def eiaAccountExpect = eiaAccountExpectService.eiaExpectCissNum(session)
        if (eiaAccountExpect) {
            render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_DEL_OK, data: eiaAccountExpect] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DEL_FAIL] as JSON)
        }
    }

    /**
     * 财务预计信息提交
     */
    def eiaExpectSubmit(){
        def eiaAccountExpect = eiaAccountExpectService.eiaExpectSubmit(params.long("eiaAccountExpectId"))
        if (eiaAccountExpect) {
            render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SUBMIT_OK, data: eiaAccountExpect] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SUBMIT_FAIL] as JSON)
        }
    }
    /**
     * 预计信息详情
     */
    def  eiaExpectDetail(){

    }
    /**
     * 预计删除
     */
    def eiaAccountExpectDel(){
        def eiaAccountExpect = eiaAccountExpectService.eiaAccountExpectDel(params.long("eiaAccountExpectId"))
        if (eiaAccountExpect) {
            render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_DEL_OK, data: eiaAccountExpect] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DEL_FAIL] as JSON)
        }
    }
    /**
     * 确认预计财务信息
     */
    def eiaAccountExpectConfirm(){
        def eiaAccountExpect = eiaAccountExpectService.eiaAccountExpectConfirm(params.long("eiaAccountExpectId"))
        if (eiaAccountExpect) {
            render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SUBMIT_OK, data: eiaAccountExpect] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SUBMIT_FAIL] as JSON)
        }
    }
    /**
     * 驳回预计财务信息
     */
    def eiaAccountExpectBack(){
        def eiaAccountExpect = eiaAccountExpectService.eiaAccountExpectBack(params.long("eiaAccountExpectId"))
        if (eiaAccountExpect) {
            render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SUBMIT_OK, data: eiaAccountExpect] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SUBMIT_FAIL] as JSON)
        }
    }

    /**
     * 财务预计信息
     */
    def getEiaAccountExpectData(){
        def dataMap = eiaAccountExpectService.getEiaAccountExpectData(params, session)
        if (dataMap) {
            render([code: HttpMesConstants.CODE_OK, count: dataMap.total, data: dataMap.data,totalDetail:dataMap.totalDetail] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
}
