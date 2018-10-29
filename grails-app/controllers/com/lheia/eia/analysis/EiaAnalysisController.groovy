package com.lheia.eia.analysis

import com.lheia.eia.common.HttpMesConstants
import grails.converters.JSON

class EiaAnalysisController {

    /** 财务统计分析 */
    def eiaFinanceAnalysis() {}
    /** 合同统计分析 */
    def eiaContractAnalysis() {}
    /** 项目统计分析 */
    def eiaProjectAnalysis() {}
    /** 项目台账 */
    def eiaProjectAccount() {}
    /** 合同列表*/
    def eiaContractIndex() {}
    /** 项目统计分析下钻到项目列表 */
    def eiaProjectList() {}

    def eiaBoardService
    def eiaProjectService
    def eiaProjectAnalysisService
    def eiaContractAnalysisService
    /**
     * 获取部门财务进出帐明细
     */
    def getEiaInvoice() {
        def data = eiaBoardService.getEiaInvoice(params)
        render([code: HttpMesConstants.CODE_OK, count: data.size(), data: data] as JSON)
    }
    /**
     * 开票未进账
     */
    def getEiaInvoiceIncomeDataList() {
        def dataMap = eiaBoardService.getEiaInvoiceIncome(params, session)
        if (dataMap) {
            render([code: HttpMesConstants.CODE_OK, count: dataMap.total, data: dataMap.data, totalDetail: dataMap.totalDetail] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
    /**
     * 财务预计信息
     */
    def getEiaAccountExpectDataList() {
        def data = eiaBoardService.getEiaAccountExpect(params, session)
        render([code: HttpMesConstants.CODE_OK, count: data.size(), data: data] as JSON)
    }
    /**
     * 合同统计分析，取部门合计
     */
    def getTotalMoney() {
        def data = eiaContractAnalysisService.getTotalMoney(params, session)
        render([code: HttpMesConstants.CODE_OK, count: data.size(), data: data] as JSON)
    }
    /***
     * 业务类型统计
     */
    def getBusiTypeCountMoney() {
        def data = eiaBoardService.getBusiTypeTotalMoney(session,params)
        render([code: HttpMesConstants.CODE_OK, data: data] as JSON)
    }
    /**
     * 人员项目数量统计
     */
    def getTotalProjectCount() {
        def data = eiaProjectAnalysisService.getTotalProjectCount(params, session)
        render([code: HttpMesConstants.CODE_OK, count: data.size(), data: data] as JSON)
    }
    /**
     * 获取项目台账信息
     */
    def getProjectAccountDataList() {
        def resMap = eiaProjectAnalysisService.getProjectAccountDataList(params, session)
        render([code: HttpMesConstants.CODE_OK, data: resMap.data, count: resMap.count, totalDetail: resMap.totalDetail] as JSON)
    }

    /**
     * 合同信息列表
     */
    def getEiaContractDataList() {
        def dataMap = eiaContractAnalysisService.eiaContractQueryPage(params, session)
        if (dataMap) {
            render([code: HttpMesConstants.CODE_OK, count: dataMap.total, data: dataMap.data] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
    /**
     * 获取项目列表
     */
    def eiaProjectQuery() {
        def dataMap = eiaProjectAnalysisService.eiaProjectQuery(params)
        if (dataMap) {
            render([code: HttpMesConstants.CODE_OK, count: dataMap.total, data: dataMap.data] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
}
