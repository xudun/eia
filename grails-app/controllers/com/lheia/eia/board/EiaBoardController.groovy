package com.lheia.eia.board

import com.lheia.eia.common.HttpMesConstants
import grails.converters.JSON

class EiaBoardController {
    def eiaTaskBoard() {}
    def eiaWorkPlatForm() {}
    def eiaConOfferBacklog() {}
    def eiaLabOfferBacklog() {}
    def eiaProjectBacklog() {}
    def eiaCertBackLog() {}
    def eiaStampBacklog() {}
    def eiaProjectExpBacklog() {}
    def eiaWorkFlowBusiDone() {}
    def eiaWorkFlowBusiService
    def eiaBoardService
    /**
     * 所有代办事项
     * @return
     */
    def getEiaWorkFlowBusiDataList() {
        def param = [:]
        params.each {
            param.put(it.key, it.value)
        }
        param.authType = 'ALL'
        def busiMap = eiaWorkFlowBusiService.getEiaWorkFlowBusiDataList(param, session)
        render([code: HttpMesConstants.CODE_OK, count: busiMap.count, data: busiMap.data] as JSON)
    }

    /**
     * 获取我的任务数量
     */
    def getTaskNums() {
        def dataMap = eiaBoardService.eiaTaskQuery(session)
        if (dataMap) {
            render([code: HttpMesConstants.CODE_OK, count: dataMap.total] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }

    /**
     * 获取我的合同数量
     */
    def getContractNums() {
        def dataMap = eiaBoardService.eiaContractQueryPage(session)
        if (dataMap) {
            render([code: HttpMesConstants.CODE_OK, count: dataMap.total] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }

    /**
     * 获取我的项目数量
     */
    def getProjectNums() {
        def dataMap = eiaBoardService.eiaProjectQueryPage(session)
        if (dataMap) {
            render([code: HttpMesConstants.CODE_OK, count: dataMap.total] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }

    /**
     * 获取我的客户数量
     */
    def getClientNums() {
        def dataMap = eiaBoardService.eiaClientQueryPage(session)
        if (dataMap) {
            render([code: HttpMesConstants.CODE_OK, count: dataMap.total] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }

    /**
     * 部门统计金额
     */
    def getDeptCountMoney() {
        def data = eiaBoardService.getDeptCount()
        render([code: HttpMesConstants.CODE_OK, count: data] as JSON)
    }

    /**
     * 判断当前人员是否有工作台按钮权限
     */
    def checkStaffClickBtn() {
        def result = eiaBoardService.checkStaffClickBtn(params, session)
        if (result) {
            render([code: HttpMesConstants.CODE_OK, data: result] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
}
