package com.lheia.eia.project

import com.lheia.eia.common.GeneConstants
import com.lheia.eia.common.HttpMesConstants
import com.lheia.eia.config.EiaDomainCode
import grails.converters.JSON

class EiaProjectLogController {

    def eiaProjectLogService

    def eiaProjectLogIndex() {
        render(view: "/eiaProject/eiaProjectLogIndex.gsp")
    }
    def eiaProjectLogSelect() {
        render(view: "/eiaProject/eiaProjectLogSelect.gsp")
    }
    def eiaProjectLogCreate() {
        render(view: "/eiaProject/eiaProjectLogCreate.gsp")
    }
    def eiaProjectLogDetail() {
        render(view: "/eiaProject/eiaProjectLogDetail.gsp")
    }

    /**
     * 项目变更列表
     */
    def getEiaProjectLogDataList() {
        def dataMap = eiaProjectLogService.eiaProjectLogQueryPage(params, session)
        if (dataMap) {
            render([code: HttpMesConstants.CODE_OK, count: dataMap.total, data: dataMap.data] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }

    /**
     * 项目变更信息保存
     */
    def eiaProjectLogSave() {
        def eiaProjectId = params.long("eiaProjectId")
        /** 先保存变更前的项目信息 */
        def eiaProjectLog = this.copyProjectBefore(eiaProjectId)
        if (eiaProjectLog) {
            /** 修改项目信息，然后保存变更后的项目信息 */
            if (eiaProjectLogService.eiaProjectLogSave(eiaProjectLog.id, params, session)) {
                render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SAVE_OK, data: eiaProjectLog] as JSON)
            } else {
                render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
            }
        }
    }
    /**
     * 项目变更信息回显
     */
    def getEiaProjectLogDataMap() {
        def dataMap = eiaProjectLogService.getEiaProjectLogDataMap(params.long("eiaProjectLogId"))
        if (dataMap) {
            render([code: HttpMesConstants.CODE_OK, data: dataMap, logInputDate: dataMap.dateCreated.format("yyyy-MM-dd HH:ss:mm")] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
    /**
     * 根据文件类型获取详情页需要显示的项目子表数据
     */
    def getEnvProLogDataMap(){
        def dataMap = eiaProjectLogService.getEnvProLogDataMap(params)
        if (dataMap) {
            render([code: HttpMesConstants.CODE_OK, data: dataMap] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
    /**
     * 获取项目子表信息
     */
    def getEneOrEnvOrGreenLogDataMap() {
        def dataMap = eiaProjectLogService.getEneOrEnvOrGreenLogDataMap(params.long("eiaProjectLogId"))
        if (dataMap) {
            render([code: HttpMesConstants.CODE_OK, data: dataMap, logInputDate: dataMap.dateCreated.format("yyyy-MM-dd HH:ss:mm")] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
    /**
     * 保存项目变更前信息
     */
    def copyProjectBefore(Long eiaProjectId) {
        def eiaProject = EiaProject.findById(eiaProjectId)
        if (eiaProject) {
            def eiaProjectLog = new EiaProjectLog()
            bindData(eiaProjectLog, eiaProject.properties)
            eiaProjectLog.eiaProjectId = eiaProjectId
            if (eiaProjectLog.save(flush: true, failOnError: true)) {
                /** 更新项目子表信息 */
                def envList = GeneConstants.ENVLIST
                def eseList = GeneConstants.ESELIST
                def greenList = GeneConstants.GREENLIST
                def parentCode = EiaDomainCode.findByDomainAndCode(GeneConstants.PROJECT_FILE_TYPE, eiaProjectLog.fileTypeChildCode)?.parentCode
                if (parentCode in envList) {
                    def eiaEnvProject = EiaEnvProject.findByEiaProjectIdAndIfDel(eiaProject.id, false)
                    def eiaEnvProjectLog = new EiaEnvProjectLog()
                    if (eiaEnvProject) {
                        bindData(eiaEnvProjectLog, eiaEnvProject.properties)
                        eiaEnvProjectLog.eiaEnvProjectId = eiaEnvProject.id
                    }
                    eiaEnvProjectLog.eiaProjectId = eiaProjectId
                    eiaEnvProjectLog.eiaProjectLogId = eiaProjectLog.id
                    eiaEnvProjectLog.save(flush: true, failOnError: true)
                } else if (parentCode in eseList) {
                    def eiaEneProject = EiaEneProject.findByEiaProjectIdAndIfDel(eiaProject.id, false)
                    def eiaEneProjectLog = new EiaEneProjectLog()
                    if (eiaEneProject) {
                        bindData(eiaEneProjectLog, eiaEneProject.properties)
                        eiaEneProjectLog.eiaEneProjectId = eiaEneProject.id
                    }
                    eiaEneProjectLog.eiaProjectId = eiaProjectId
                    eiaEneProjectLog.eiaProjectLogId = eiaProjectLog.id
                    eiaEneProjectLog.save(flush: true, failOnError: true)
                } else if (parentCode in greenList) {
                    def eiaGreenProject = EiaGreenProject.findByEiaProjectIdAndIfDel(eiaProject.id, false)
                    def eiaGreenProjectLog = new EiaGreenProjectLog()
                    if (eiaGreenProject) {
                        bindData(eiaGreenProjectLog, eiaGreenProject.properties)
                        eiaGreenProjectLog.eiaGreenProjectId = eiaGreenProject.id
                    }
                    eiaGreenProjectLog.eiaProjectId = eiaProjectId
                    eiaGreenProjectLog.eiaProjectLogId = eiaProjectLog.id
                    eiaGreenProjectLog.save(flush: true, failOnError: true)
                }
            }
            return eiaProjectLog
        }
    }
    /**
     * 获取项目所需要的金额的字段
     */
    def getProjectLogMoneyShow() {
        def dataMap = eiaProjectLogService.getProjectLogMoneyShow(params)
        if (dataMap) {
            render([code: HttpMesConstants.CODE_OK, data: dataMap] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
}
