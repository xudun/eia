package com.lheia.eia.task

import com.lheia.eia.common.FuncConstants
import com.lheia.eia.common.GeneConstants
import com.lheia.eia.contract.EiaContract
import com.lheia.eia.project.EiaProject
import grails.gorm.transactions.Transactional
import org.grails.web.json.JSONArray
import org.grails.web.json.JSONObject

import java.text.DecimalFormat

@Transactional
class EiaTaskService {
    /**
     * 通过id或者eiaTask数据
     * @param params
     * @return
     */
    def getEiaTaskDataMap(taskId) {
        return EiaTask.findByIdAndIfDel(taskId, false)
    }
    /**
     * 任务列表数据显示
     */
    def eiaTaskQuery(params, session) {
        int page
        int limit
        if (params.page && params.limit) {
            page = params.int('page') - 1
            limit = params.int('limit')
        }
        def eiaTaskList = EiaTask.createCriteria().list(max: limit, offset: page * limit) {
            def taskName = params.taskName
            if (taskName && !"任务名称、任务单号、录入部门、录入人".equals(taskName)) {
                or{
                    like("taskName", "%" + taskName + "%")
                    like("inputDept", "%" + taskName + "%")
                    like("inputUser", "%" + taskName + "%")
                    like("taskNo", "%" + taskName + "%")
                }
            }
            def taskState = params.taskState
            if (taskState && !"请选择任务状态".equals(taskState)) {
                eq("taskState", taskState)
            }
            /**
             * 查看全部的任务数据
             */
            if (!session.staff.funcCode.contains(FuncConstants.EIA_YWCX_RWCX_VIEWALL)) {
                /**
                 * 查看本部门任务数据
                 */
                if (session.staff.funcCode.contains(FuncConstants.EIA_YWCX_RWCX_VIEWDEPT)) {
                    or {
                        like ("inputDeptCode", "%" + session.staff.orgCode + "%")
//                        like("taskAssignUser", "%" + session.staff.staffName + "_" + session.staff.staffId + "%")
                    }
                }
                /**
                 * 查看本人任务数据
                 */
                else if (session.staff.funcCode.contains(FuncConstants.EIA_YWCX_RWCX_VIEWSELF)) {
                    or {
                        eq("inputUserId", Long.valueOf(session.staff.staffId))
                        like("taskAssignUser", "%" + session.staff.staffName+"_"+session.staff.staffId + "%")
                    }
                }
            }
            eq("ifDel", false)
            order("id", "desc")
        }
        def eiaTaskData = []
        eiaTaskList.each {
            def map = [:]
            map.id = it.id
            map.taskNo = it.taskNo
            map.taskName = it.taskName
            map.busiType = it.busiType
            map.taskLeaderDept = it.taskLeaderDept
            map.taskState = it.taskState
            map.inputDept = it.inputDept
            map.inputUser = it.inputUser
            map.inputUserId = it.inputUserId
            def taskRoleList = EiaTaskAssign.findAllByTaskIdAndTaskAssignUserAndIfDel(it.id, session.staff.staffName, false)
            if (taskRoleList.size() > 0) {
                def taskRole = ""
                taskRoleList.each {
                    taskRole += it.taskAssignRole + ","
                }
                map.taskRole = taskRole.substring(0, taskRole.length() - 1)
            } else {
                map.taskRole = "无"
            }
            eiaTaskData << map
        }
        def dataMap = [:]
        dataMap.data = eiaTaskData
        dataMap.total = eiaTaskList.totalCount
        return dataMap
    }
    /**
     * 任务保存
     */
    def eiaTaskSave(params, session) {
        /**
         * 项目审核人
         */
        def taskRoleExam = params.taskRoleExam
        /**
         * 主持编写人
         */
        def taskRoleToast = params.taskRoleToast
        /**
         * 项目编写人
         */
        def taskRoleWriter = params.taskRoleWriter
        JSONArray taskRoleExams = new JSONArray(taskRoleExam)
        String taskAssignUserInfo = ""
        for (int i = 0; i < taskRoleExams.length(); i++) {
            JSONObject jsonTaskAssignUser = taskRoleExams.getJSONObject(i);
            def staffId = jsonTaskAssignUser.getString("staffId")
            def staffUser = jsonTaskAssignUser.getString("staffName")
            taskAssignUserInfo += staffUser + "_" + staffId + ','
        }
        if(taskRoleToast){
            JSONArray taskRoleToasts = new JSONArray(taskRoleToast)
            for (int i = 0; i < taskRoleToasts.length(); i++) {
                JSONObject jsonTaskAssignUser = taskRoleToasts.getJSONObject(i);
                def staffId = jsonTaskAssignUser.getString("staffId")
                def staffUser = jsonTaskAssignUser.getString("staffName")
                taskAssignUserInfo += staffUser + "_" + staffId + ','
            }
        }

        JSONArray taskRoleWriters = new JSONArray(taskRoleWriter)
        for (int i = 0; i < taskRoleWriters.length(); i++) {
            JSONObject jsonTaskAssignUser = taskRoleWriters.getJSONObject(i);
            def staffId = jsonTaskAssignUser.getString("staffId")
            def staffUser = jsonTaskAssignUser.getString("staffName")
            taskAssignUserInfo += staffUser + "_" + staffId + ','
        }
        def eiaTask = new EiaTask()
        eiaTask.properties = params
        eiaTask.taskAssignUser = taskAssignUserInfo.substring(0, taskAssignUserInfo.length() - 1)
        eiaTask.inputDept = session.staff.orgName
        eiaTask.inputDeptCode = session.staff.orgCode
        eiaTask.inputDeptId = Long.parseLong(session.staff.orgId)
        eiaTask.inputUser = session.staff.staffName
        eiaTask.inputUserId = Long.parseLong(session.staff.staffId)
        eiaTask.taskState = GeneConstants.TASK_STATE_NEW
        if (eiaTask.save(flush: true, failOnError: true)) {
            /**
             * 获取任务单号
             */
            def taskNo = this.getTaskNo(eiaTask.id)
            eiaTask.taskNo = taskNo
            eiaTask.save(flush: true, failOnError: true)
        }
        /**
         * 获取任务负责人
         */
        if (taskRoleExam) {
            JSONArray taskRoleExamArray = new JSONArray(taskRoleExam)
            this.eiaTaskAssignSave(GeneConstants.TASK_ASSIGN_ROLE_EXAM, taskRoleExamArray, eiaTask)
        }
        if (taskRoleToast) {
            JSONArray taskRoleToastArray = new JSONArray(taskRoleToast)
            this.eiaTaskAssignSave(GeneConstants.TASK_ASSIGN_ROLE_TOAST, taskRoleToastArray, eiaTask)
        }
        /**
         * 把部门和人员相关的字符串转成JSON格式
         */
        if (taskRoleWriter) {
            JSONArray taskRoleWriterArray = new JSONArray(taskRoleWriter)
            this.eiaTaskAssignSave(GeneConstants.TASK_ASSIGN_ROLE_WRITER, taskRoleWriterArray, eiaTask)
        }
        return eiaTask
    }
    /**
     * 任务修改保存
     */
    def eiaTaskUpdate(params) {
        /**
         * 项目审核人
         */
        def taskRoleExam = params.taskRoleExam
        /**
         * 主持编写人
         */
        def taskRoleToast = params.taskRoleToast
        /**
         * 项目编写人
         */
        def taskRoleWriter = params.taskRoleWriter

        def taskAssignUserInfo = ""
        JSONArray taskRoleExams = new JSONArray(taskRoleExam)
        for (int i = 0; i < taskRoleExams.length(); i++) {
            JSONObject jsonTaskRoleExam = taskRoleExams.getJSONObject(i);
            def staffId = jsonTaskRoleExam.getString("staffId")
            def staffUser = jsonTaskRoleExam.getString("staffName")
            taskAssignUserInfo += staffUser + "_" + staffId + ','
        }
        if(taskRoleToast){
            JSONArray taskRoleToasts = new JSONArray(taskRoleToast)
            for (int i = 0; i < taskRoleToasts.length(); i++) {
                JSONObject jsonTaskRoleToast = taskRoleToasts.getJSONObject(i);
                def staffId = jsonTaskRoleToast.getString("staffId")
                def staffUser = jsonTaskRoleToast.getString("staffName")
                taskAssignUserInfo += staffUser + "_" + staffId + ','
            }
        }
        JSONArray taskRoleWriters = new JSONArray(taskRoleWriter)
        for (int i = 0; i < taskRoleWriters.length(); i++) {
            JSONObject jsonTaskRoleWriter = taskRoleWriters.getJSONObject(i)
            def staffId = jsonTaskRoleWriter.getString("staffId")
            def staffUser = jsonTaskRoleWriter.getString("staffName")
            taskAssignUserInfo += staffUser + "_" + staffId + ','
        }

        def eiaTask = getEiaTaskDataMap(params.long("taskId"))
        eiaTask.properties = params
        eiaTask.taskAssignUser = taskAssignUserInfo.substring(0, taskAssignUserInfo.length() - 1)
        eiaTask.save(flush: true, failOnError: true)
        /**
         * 删除已分配的人员
         */
        def eiaTaskAssignInfo = EiaTaskAssign.findAllByIfDelAndTaskId(false, params.long("taskId"))
        eiaTaskAssignInfo.each {
            it.ifDel = true
        }
        /**
         * 获取任务负责人
         */
        if (taskRoleExam) {
            JSONArray taskRoleExamArray = new JSONArray(taskRoleExam)
            this.eiaTaskAssignSave(GeneConstants.TASK_ASSIGN_ROLE_EXAM, taskRoleExamArray, eiaTask)
        }
        if (taskRoleToast) {
            JSONArray taskRoleToastArray = new JSONArray(taskRoleToast)
            this.eiaTaskAssignSave(GeneConstants.TASK_ASSIGN_ROLE_TOAST, taskRoleToastArray, eiaTask)
        }
        /**
         * 把部门和人员相关的字符串转成JSON格式
         */
        if (taskRoleWriter) {
            JSONArray taskRoleWriterArray = new JSONArray(taskRoleWriter)
            this.eiaTaskAssignSave(GeneConstants.TASK_ASSIGN_ROLE_WRITER, taskRoleWriterArray, eiaTask)
        }
        return eiaTask
    }
    /**
     * 保存项目相关人员
     */
    def eiaTaskAssignSave(taskAssignRole, taskRoleArray, eiaTask) {
        for (int i = 0; i < taskRoleArray.length(); i++) {
            JSONObject taskRoleJson = taskRoleArray.getJSONObject(i);
            def orgName = taskRoleJson.getString("orgName")
            def orgId = taskRoleJson.getString("orgId")
            def staffId = taskRoleJson.getString("staffId")
            def staffUser = taskRoleJson.getString("staffName")
            def eiaTaskAssign = new EiaTaskAssign()
            eiaTaskAssign.taskId = eiaTask?.id
            eiaTaskAssign.taskNo = eiaTask.taskNo
            eiaTaskAssign.taskName = eiaTask.taskName
            eiaTaskAssign.ifDel = false
            eiaTaskAssign.taskAssignDept = orgName
            eiaTaskAssign.taskAssignDeptId = Long.valueOf(orgId)
            eiaTaskAssign.taskAssignUser = staffUser
            eiaTaskAssign.taskAssignUserId = Long.valueOf(staffId)
            eiaTaskAssign.inputDeptId = eiaTask.inputDeptId
            eiaTaskAssign.inputDept = eiaTask.inputDept
            eiaTaskAssign.inputUserId = eiaTask.inputUserId
            eiaTaskAssign.inputUser = eiaTask.inputUser
            eiaTaskAssign.taskAssignRole = taskAssignRole
            eiaTaskAssign.save(flush: true, failOnError: true)
        }
    }
    /**
     * 任务删除
     */
    def eiaTaskDel(taskId) {
        def eiaTask = getEiaTaskDataMap(taskId)
        eiaTask.ifDel = true
        /**
         * 根据任务id删除人员分配表
         */
        def eiaTaskAssign = EiaTaskAssign.findAllByIfDelAndTaskId(false, taskId)
        if (eiaTaskAssign) {
            eiaTaskAssign.each {
                it.ifDel = true
            }
        }
        eiaTask.save(flush: true, failOnError: true)
    }
    /**
     * 任务提交保存
     */
    def eiaTaskSubmit(taskId) {
        def eiaTask = getEiaTaskDataMap(taskId)
        eiaTask.taskState = GeneConstants.TASK_STATE_SUBMIT
        eiaTask.save(flush: true, failOnError: true)
    }
    /**
     * 任务列表数据显示
     */
    def eiaTaskAssignDetailsQuery(params, session) {
        int page
        int limit
        if (params.page && params.limit) {
            page = params.int('page') - 1
            limit = params.int('limit')
        }
        def eiaTaskList = EiaTaskAssign.createCriteria().list(max: limit, offset: page * limit) {
            eq("ifDel", false)
            eq("taskId", params.long("taskId"))
            order("id", "desc")
        }
        def eiaTaskData = []
        eiaTaskList.each {
            def map = [:]
            map.id = it.id
            map.taskAssignDept = it.taskAssignDept
            map.taskAssignUser = it.taskAssignUser
            map.taskAssignRole = it.taskAssignRole
            eiaTaskData << map
        }
        def dataMap = [:]
        dataMap.data = eiaTaskData
        dataMap.total = eiaTaskList.totalCount
        return dataMap
    }
    /**
     * 根据年份或者任务单号
     * @return
     */
    def getTaskNo(Long eiaTaskId) {
        Calendar date = Calendar.getInstance()
        String year = String.valueOf(date.get(Calendar.YEAR))
//        def taskDomain = EiaDomainCode.findByDomainAndCodeDesc(GeneConstants.TASK_NUM, year)
//        def offerNum = Integer.parseInt(taskDomain.code)
//        /**
//         * 更新EiaDomainCode的对应年份任务数量
//         */
//        taskDomain.code = offerNum + 1
//        taskDomain.save(flush: true, failOnError: true)
        /**
         * 格式化任务号
         */
        DecimalFormat df = new DecimalFormat("0000")
        String taskNumFormat = df.format(eiaTaskId)
        String taskNo = "T-" + year + "-" + taskNumFormat
        return taskNo
    }
    /**
     * 任务分派中人员回显
     */
    def eiaTaskAssignDataList(params) {
        def projectCount = EiaProject.createCriteria().list() {
            and {
                eq("ifArc", false)
                eq("ifDel", false)
            }
            projections {
                count()
                groupProperty("inputUserId")
            }
        }
        def eiaTask = this.getEiaTaskDataMap(params.long("taskId"))
        def eiaTaskAssign = EiaTaskAssign.findAllByIfDelAndTaskId(false, eiaTask?.id)
        def eiaTaskAssignList = []
        if (eiaTaskAssign) {
            eiaTaskAssign.each {
                def eiaTaskAssignMap = [:]
                eiaTaskAssignMap.id = it.taskAssignUserId
                eiaTaskAssignMap.staffId = it.taskAssignUserId
                eiaTaskAssignMap.name = it.taskAssignUser
                eiaTaskAssignMap.staffName = it.taskAssignUser
                eiaTaskAssignMap.orgId = it.taskAssignDeptId
                eiaTaskAssignMap.orgName = it.taskAssignDept
                eiaTaskAssignMap.taskAssignRole = it.taskAssignRole
                if (projectCount) {
                    projectCount.each { pc ->
                        if (pc[1] == it.taskAssignUserId) {
                            eiaTaskAssignMap.name = it.taskAssignUser + " (当前项目个数:" + pc[0] + ")"
                        } else {
                            eiaTaskAssignMap.name = it.taskAssignUser + " (当前项目个数:" + 0 + ")"
                        }
                    }
                } else {
                    eiaTaskAssignMap.name = it.taskAssignUser + " (当前项目个数:" + 0 + ")"
                }

                eiaTaskAssignList << eiaTaskAssignMap
            }
            return eiaTaskAssignList
        } else {
            return false
        }

    }
}
