package com.lheia.eia.stamp

import com.lheia.eia.common.GeneConstants
import com.lheia.eia.common.HttpMesConstants
import com.lheia.eia.common.WorkFlowConstants
import grails.converters.JSON

class EiaStampController {
    def eiaStampService
    def eiaWorkFlowStampService

    def eiaStampEdit() {}

    def eiaStampDetail() {}

    def eiaStampIndex() {}

    def eiaStampPrintPage() {}

    def eiaStampPrintPageNoBuss() {}
    /**
     * 保存
     */
    def eiaStampSave() {
        def eiaStamp = eiaStampService.eiaStampSave(params, session)
        if (eiaStamp) {
            render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SAVE_OK, data: eiaStamp] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
        }
    }
    /**
     * 删除
     */
    def eiaStampDel() {
        def eiaStamp = eiaStampService.eiaStampDel(params)
        if (eiaStamp) {
            render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_DEL_OK] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DEL_FAIL] as JSON)
        }
    }

    /**
     * 获取印章申请列表
     */
    def getEiaStampDataList() {
        def dataMap = eiaStampService.getEiaStampDataList(params, session)
        if (dataMap) {
            render([code: HttpMesConstants.CODE_OK, count: dataMap.total, data: dataMap.data] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
    /**
     * 获取印章申请详情
     */
    def getEiaStampDataMap() {
        def eiaStamp = eiaStampService.getEiaStampDataMap(params)
        if (eiaStamp) {
            render([code: HttpMesConstants.CODE_OK, data: eiaStamp] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }

    /**
     * 启动印章申请流程
     */
    def eiaStampSub() {
        def eiaStamp = eiaStampService.eiaStampSub(params)
        def workFlowCode
        def startNodeCode = WorkFlowConstants.STAMP_WORK_FLOW_NODE_BMSH
        if (eiaStamp) {
            if (eiaStamp.ifBussUse == true) {
                workFlowCode = WorkFlowConstants.STAMP_WORK_FLOW_BUSS
            } else if (eiaStamp.ifBussUse == false) {
                workFlowCode = WorkFlowConstants.STAMP_WORK_FLOW_NOBUSS
            }
            def eiaWorkFlowBusi = eiaWorkFlowStampService.startStampWorkFlow(workFlowCode, GeneConstants.DOMAIN_EIA_STAMP, eiaStamp.id, startNodeCode, session)
            if (eiaWorkFlowBusi) {
                render([code: HttpMesConstants.CODE_OK, data: eiaStamp] as JSON)
            } else {
                eiaStamp.ifSub = false
                eiaStamp.save(flush: true, failOnError: true)
                render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_FCONF_NULL] as JSON)
            }
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_CERT_FLOW_SEC_SUB] as JSON)
        }
    }

    /**
     * 报价印章申请审批单
     * @return
     */
    def eiaStampPrintDataMap() {
        def dataMap = eiaStampService.eiaStampPrintDataMap(params)
        if (dataMap) {
            render([code: HttpMesConstants.CODE_OK, data: dataMap] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
    /**
     * 获取印章公司名称
     */
    def getStampCompList() {
        def stampCompList = eiaStampService.getStampCompList()
        if (stampCompList) {
            render([code: HttpMesConstants.CODE_OK, data: stampCompList] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
}
