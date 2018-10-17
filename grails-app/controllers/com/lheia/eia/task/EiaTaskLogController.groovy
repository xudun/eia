package com.lheia.eia.task

import com.lheia.eia.common.GeneConstants
import com.lheia.eia.common.HttpMesConstants
import grails.converters.JSON

class EiaTaskLogController {

    def eiaTaskLogService

    /**
     * 任务变更列表显示页面
     */
    def eiaTaskLogIndex(){
        render(view: "/eiaTask/eiaTaskLogIndex.gsp")
    }
    /**
     * 任务变更列表显示页面
     */
    def eiaTaskLogSelect(){
        render(view: "/eiaTask/eiaTaskLogSelect.gsp")
    }
    /**
     * 任务变更新增页面
     */
    def eiaTaskLogCreate(){
        render(view: "/eiaTask/eiaTaskLogCreate.gsp")
    }
    /**
     * 任务变更查看
     */
    def eiaTaskLogDetail(){
        render(view: "/eiaTask/eiaTaskLogDetail.gsp")
    }

    /**
     * 任务变更列表数据显示
     */
    def getEiaTaskLogDataList(){
        def dataMap = eiaTaskLogService.eiaTaskLogQuery(params, session)
        if (dataMap) {
            render([code: HttpMesConstants.CODE_OK, count: dataMap.total, data: dataMap.data] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
    /**
     * 任务变更保存
     */
    def eiaTaskLogSave(){
        def eiaTaskId = params.long("taskId")
        /** 先保存变更前的任务信息 */
        def eiaTaskLog = this.copyTaskBefore(eiaTaskId)
        if (eiaTaskLog) {
            /** 修改任务信息，并保存变更后的任务信息 */
            if (eiaTaskLogService.eiaTaskLogSave(eiaTaskLog.id, params, session)) {
                render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SAVE_OK, data: eiaTaskLog] as JSON)
            } else {
                render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
            }
        }
    }
    /**
     *  任务变更回显
     */
    def eiaTaskLogDataMap(){
        def dataMap = eiaTaskLogService.getEiaTaskLogDataMap(params.long("eiaTaskLogId"))
        if (dataMap) {
            render([code: HttpMesConstants.CODE_OK, data: dataMap, logInputDate: dataMap.dateCreated.format("yyyy-MM-dd HH:ss:mm")] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
    /**
     * 任务变更信息查看中分配人员查看
     */
    def eiaTaskAssignLogDetailsList(){
        def dataMap = eiaTaskLogService.eiaTaskAssignLogDetailsQuery(params)
        if (dataMap) {
            render([code: HttpMesConstants.CODE_OK, count: dataMap.total, data: dataMap.data] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
    /**
     * 保存任务变更前信息
     */
    def copyTaskBefore(Long eiaTaskId) {
        def eiaTask = EiaTask.findById(eiaTaskId)
        if (eiaTask) {
            def eiaTaskLog = new EiaTaskLog()
            bindData(eiaTaskLog, eiaTask.properties)
            eiaTaskLog.eiaTaskId = eiaTaskId
            /** 日志录入时间、人员 */
            eiaTaskLog.logInputDept = session.staff.orgName
            eiaTaskLog.logInputDeptId = Long.parseLong(session.staff.orgId)
            eiaTaskLog.logInputDeptCode = session.staff.orgCode
            eiaTaskLog.logInputUser = session.staff.staffName
            eiaTaskLog.logInputUserId = Long.parseLong(session.staff.staffId)
            if (eiaTaskLog.save(flush: true, failOnError: true)) {
                def eiaTaskAssignList = EiaTaskAssign.findAllByTaskIdAndIfDel(eiaTaskId, false)
                if (eiaTaskAssignList) {
                    eiaTaskAssignList.each {
                        def eiaTaskAssignLog = new EiaTaskAssignLog()
                        bindData(eiaTaskAssignLog, it.properties)
                        eiaTaskAssignLog.eiaTaskLogId = eiaTaskLog.id
                        eiaTaskAssignLog.eiaTaskAssignId = it.id
                        eiaTaskAssignLog.state = GeneConstants.TASK_ASSIGN_DEL
                        /** 日志录入时间、人员 */
                        eiaTaskAssignLog.logInputDept = session.staff.orgName
                        eiaTaskAssignLog.logInputDeptId = Long.parseLong(session.staff.orgId)
                        eiaTaskAssignLog.logInputDeptCode = session.staff.orgCode
                        eiaTaskAssignLog.logInputUser = session.staff.staffName
                        eiaTaskAssignLog.logInputUserId = Long.parseLong(session.staff.staffId)
                        eiaTaskAssignLog.save(flush: true, failOnError: true)
                    }
                }
                return eiaTaskLog
            }
        }
    }
}
