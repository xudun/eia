package com.lheia.eia.task

import com.lheia.eia.common.GeneConstants
import com.lheia.eia.common.HttpMesConstants
import com.lheia.eia.common.HttpUrlConstants
import com.lheia.eia.config.EiaDomainCode
import com.lheia.eia.tools.HttpConnectTools
import grails.converters.JSON

class EiaTaskController {
    def eiaTaskService
    /**
     * 任务列表显示页面
     */
    def eiaTaskIndex() {
    }
    /**
     * 任务列表数据显示
     */
    def getEiaTaskDataList() {
        def dataMap = eiaTaskService.eiaTaskQuery(params, session)
        if (dataMap) {
            render([code: HttpMesConstants.CODE_OK, count: dataMap.total, data: dataMap.data] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
    /**
     * 任务新增页面
     */
    def eiaTaskCreate() {
    }
    /**
     * domainCode获取业务类型
     */
    def taskDomainBusType() {
        def domainBusiTypeList = EiaDomainCode.findAllByDomain(GeneConstants.TASK_BUSINESS_TYPE)
        def eiaDomainBusinTypeList = []
        domainBusiTypeList.each {
            def domainBusiTypeMap = [:]
            domainBusiTypeMap.busiTypeCode = it.code
            domainBusiTypeMap.busiType = it.codeDesc
            eiaDomainBusinTypeList << domainBusiTypeMap
        }
        render(eiaDomainBusinTypeList as JSON)
    }
    /**
     * 获取auth中的机构部门（负责部门）
     */
    def getAuthOrgList() {
        def param = [:]
        def eiaOrgInfo = HttpConnectTools.getResponseJson(HttpUrlConstants.EIA_AUTH_ORG_INFO, param)
        render eiaOrgInfo
    }
    /**
     * 获取auth中机构部门和用户(分配人员)
     */
    def getOrgStaffTreeList() {
        def param = [:]
        def eiaOrgStaffInfo = HttpConnectTools.getResponseJson(HttpUrlConstants.EIA_AUTH_STAFF_INFO, param)
        render eiaOrgStaffInfo
    }
    /**
     * 任务保存和修改
     */
    def eiaTaskSave() {
        if (params.long("taskId")) {
            def eiaTask = eiaTaskService.eiaTaskUpdate(params)
            if (eiaTask) {
                render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SAVE_OK, data: eiaTask] as JSON)
            } else {
                render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
            }
        } else {
            def eiaTask = eiaTaskService.eiaTaskSave(params, session)
            if (eiaTask) {
                render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SAVE_OK, data: eiaTask] as JSON)
            } else {
                render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
            }
        }

    }
    /**
     * 任务修改
     */
    def eiaTaskEdit() {
        def eiaTask = eiaTaskService.getEiaTaskDataMap(params.long("taskId"))
        [eiaTask: eiaTask]
    }
    /**
     *  任务修改回显
     */
    def eiaTaskDataMap() {
        def eiaTaskDetail = eiaTaskService.getEiaTaskDataMap(params.long("taskId"))
        if (eiaTaskDetail) {
            render([code: HttpMesConstants.CODE_OK, data: eiaTaskDetail] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
    /**
     * 任务删除
     */
    def eiaTaskDel() {
        def eiaTask = eiaTaskService.eiaTaskDel(params.long("taskId"))
        if (eiaTask) {
            render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_DEL_OK, data: eiaTask] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DEL_FAIL] as JSON)
        }
    }
    /**
     * 任务人员分配页面
     */
    def eiaTaskAssignCreate() {
        def eiaTask = eiaTaskService.getEiaTaskDataMap(params.long("taskId"))
        [eiaTask: eiaTask]
    }
    /**
     * 任务提交保存
     */
    def eiaTaskSubmit() {
        /**
         * 改变任务状态为已提交
         */
        def eiaTask = eiaTaskService.eiaTaskSubmit(params.long("taskId"))
        if (eiaTask) {
            render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SUBMIT_OK, data: eiaTask] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SUBMIT_FAIL] as JSON)
        }
    }
    /**
     * 任务分派编辑
     */
    def eiaTaskAssignEdit() {
        def eiaTask = eiaTaskService.getEiaTaskDataMap(params.long("taskId"))
        [eiaTask: eiaTask]
    }
    /**
     * 任务分派中人员回显
     */
    def eiaTaskAssignDataList() {
        def eiaTaskAssignList = eiaTaskService.eiaTaskAssignDataList(params)
        if (eiaTaskAssignList) {
            render(eiaTaskAssignList as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
    /**
     * 任务信息查看
     */
    def eiaTaskDetail() {
        def eiaTask = eiaTaskService.getEiaTaskDataMap(params.long("taskId"))
        [eiaTask: eiaTask]
    }
    /**
     * 任务信息查看中分配人员查看
     */
    def eiaTaskAssignDetailsList() {
        def dataMap = eiaTaskService.eiaTaskAssignDetailsQuery(params, session)
        if (dataMap) {
            render([code: HttpMesConstants.CODE_OK, count: dataMap.total, data: dataMap.data] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
    /**
     * 报价表中获取任务编号
     * @return
     */
    def getEiaTaskName() {
       def eiaTask = EiaTask.createCriteria().list() {
           or{
               like("taskAssignUser", '%' + session.staff.staffName + '_' + session.staff.staffId + '%')
               eq("inputUserId", Long.valueOf(session.staff.staffId))
           }

            eq("ifDel", false)
            order("lastUpdated", "desc")
        }
        def taskResult = []
        eiaTask.each{
            def taskMap = [:]
            taskMap.id = it.id
            taskMap.name = it.taskNo
            taskMap.taskName = it.taskName + " (" + it.taskNo + ")"
            taskResult.add(taskMap)
        }
        render(taskResult as JSON);
    }
}
