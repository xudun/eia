package com.lheia.eia.task

import com.lheia.eia.common.FuncConstants
import com.lheia.eia.common.GeneConstants
import com.lheia.eia.contract.EiaContract
import grails.gorm.transactions.Transactional

@Transactional
class EiaTaskLogService {

    def eiaTaskService

    /**
     * 获取任务变更详情
     */
    def getEiaTaskLogDataMap(eiaTaskLogId) {
        return EiaTaskLog.findByIdAndIfDel(eiaTaskLogId,false)
    }
    /**
     * 任务变更列表数据显示
     */
    def eiaTaskLogQuery(params, session) {
        int page
        int limit
        if (params.page && params.limit) {
            page = params.int('page') - 1
            limit = params.int('limit')
        }
        def eiaTaskLogList = EiaTaskLog.createCriteria().list(max: limit, offset: page * limit) {
            def taskName = params.taskName
            if (taskName && !"任务名称、任务单号、录入部门、录入人".equals(taskName)) {
                or{
                    like("taskNameEd", "%" + taskName + "%")
                    like("taskNo", "%" + taskName + "%")
                    like("logInputUser", "%" + taskName + "%")
                    like("logInputDept", "%" + taskName + "%")
                }
            }
            /**
             * 查看全部的任务变更数据
             */
            if (!session?.staff?.funcCode?.contains(FuncConstants.EIA_YWCX_XMCX_VIEWALL)) {
                /**
                 * 查看本部门任务变更数据
                 */
                if (session?.staff?.funcCode?.contains(FuncConstants.EIA_YWCX_XMCX_VIEWDEPT)) {
                    like("inputDeptCode", "%"+ session.staff.orgCode+ "%")
                }
                /**
                 * 查看本人任务变更数据
                 */
                else if (session?.staff?.funcCode?.contains(FuncConstants.EIA_YWCX_XMCX_VIEWSELF)) {
                    like("taskAssignUser", "%" + session.staff.staffName+"_"+session.staff.staffId + "%")
                }
            }
            eq("ifDel", false)
            order("id", "desc")
        }
        def eiaTaskLogData = []
        eiaTaskLogList.each {
            def map = [:]
            map.id = it.id
            map.taskNo = it.taskNo
            map.taskName = it?.taskNameEd
            map.busiType = it?.busiTypeEd
            map.taskLeaderDept = it?.taskLeaderDeptEd
            map.inputDept = it?.inputDept
            map.inputUser = it?.inputUser
            map.taskState = it?.taskStateEd
            eiaTaskLogData << map
        }
        def dataMap = [:]
        dataMap.data = eiaTaskLogData
        dataMap.total = eiaTaskLogList.totalCount
        return dataMap
    }
    /**
     * 任务变更保存
     */
    def eiaTaskLogSave(eiaTaskLogId, params, session) {
        /** 修改任务信息 */
        def eiaTask = eiaTaskService.eiaTaskUpdate(params)
        if (eiaTask) {
            this.copyTaskAfter(eiaTask.id, eiaTaskLogId, session)
        }
    }
    /**
     * 任务变更列表人员分配数据显示
     */
    def eiaTaskAssignLogDetailsQuery(params) {
        int page
        int limit
        if (params.page && params.limit) {
            page = params.int('page') - 1
            limit = params.int('limit')
        }
        def assignLogList = EiaTaskAssignLog.createCriteria().list(max: limit, offset: page * limit) {
            eq("eiaTaskLogId", params.long("eiaTaskLogId"))
            eq("ifDel", false)
            order("taskAssignRoleEd", "asc")
        }
        def assignData = []
        assignLogList.each {
            def map = [:]
            map.id = it.id
            if (it.state == GeneConstants.TASK_ASSIGN_DEL) {
                map.state = "删除"
                map.taskAssignDept = it?.taskAssignDept
                map.taskAssignUser = it?.taskAssignUser
                map.taskAssignRole = it?.taskAssignRole
            } else {
                if (it.state == GeneConstants.TASK_ASSIGN_ADD) {
                    map.state = "新增"
                } else if (it.state == GeneConstants.TASK_ASSIGN_STAY) {
                    map.state = "无变更"
                }
                map.taskAssignDept = it?.taskAssignDeptEd
                map.taskAssignUser = it?.taskAssignUserEd
                map.taskAssignRole = it?.taskAssignRoleEd
                if (it?.taskAssignDept != it?.taskAssignDeptEd) {
                    map.dept = it?.taskAssignDept
                }
                if (it?.taskAssignUser != it?.taskAssignUserEd) {
                    map.user = it?.taskAssignUser
                }
                if (it?.taskAssignRole != it?.taskAssignRoleEd) {
                    map.role = it?.taskAssignRole
                }
            }
            assignData << map
        }
        def dataMap = [:]
        dataMap.data = assignData
        dataMap.total = assignLogList.totalCount
        return dataMap
    }
    /**
     * 保存任务变更后信息
     */
    def copyTaskAfter(eiaTaskId, eiaTaskLogId, session) {
        def eiaTask = EiaTask.findById(eiaTaskId)
        /**
         * 任务变更后合同相关联的任务也得改变
         */
        def eiaContract = EiaContract.findAllByIfDelAndTaskId(false,eiaTask?.id)
        if(eiaContract){
            eiaContract.each{
                it.taskAssignUser =  eiaTask?.taskAssignUser
            }
        }
        def eiaTaskLog = EiaTaskLog.findById(eiaTaskLogId)
        if (eiaTask && eiaTaskLog) {
            eiaTaskLog.taskNameEd = eiaTask.taskName
            eiaTaskLog.busiTypeEd = eiaTask.busiType
            eiaTaskLog.busiTypeCodeEd = eiaTask.busiTypeCode
            eiaTaskLog.taskLeaderDeptEd = eiaTask.taskLeaderDept
            eiaTaskLog.taskLeaderDeptIdEd = eiaTask.taskLeaderDeptId
            eiaTaskLog.taskStateEd = eiaTask.taskState
            eiaTaskLog.taskAssignUserEd = eiaTask.taskAssignUser
            if (eiaTaskLog.save(flush: true, failOnError: true)) {
                def eiaTaskAssignList = EiaTaskAssign.findAllByTaskIdAndIfDel(eiaTaskId, false)
                if (eiaTaskAssignList) {
                    eiaTaskAssignList.each {
                        def eiaTaskAssignLog
                        eiaTaskAssignLog = EiaTaskAssignLog.findByEiaTaskLogIdAndTaskAssignUserIdAndTaskAssignRole(eiaTaskLogId, it.taskAssignUserId, it.taskAssignRole)
                        if (!eiaTaskAssignLog) {
                            eiaTaskAssignLog = new EiaTaskAssignLog()
                            eiaTaskAssignLog.state = GeneConstants.TASK_ASSIGN_ADD
                            eiaTaskAssignLog.taskId = eiaTaskId
                            eiaTaskAssignLog.eiaTaskLogId = eiaTaskLogId
                            eiaTaskAssignLog.eiaTaskAssignId = it.id
                            eiaTaskAssignLog.inputDept = session.staff.orgName
                            eiaTaskAssignLog.inputDeptCode = session.staff.orgCode
                            eiaTaskAssignLog.inputDeptId = Long.parseLong(session.staff.orgId)
                            eiaTaskAssignLog.inputUser = session.staff.staffName
                            eiaTaskAssignLog.inputUserId = Long.parseLong(session.staff.staffId)
                            /** 日志录入时间、人员 */
                            eiaTaskAssignLog.logInputDept = session.staff.orgName
                            eiaTaskAssignLog.logInputDeptCode = session.staff.orgCode
                            eiaTaskAssignLog.logInputDeptId = Long.parseLong(session.staff.orgId)
                            eiaTaskAssignLog.logInputUser = session.staff.staffName
                            eiaTaskAssignLog.logInputUserId = Long.parseLong(session.staff.staffId)
                        } else {
                            if (it.taskAssignRole == eiaTaskAssignLog.taskAssignRole) {
                                eiaTaskAssignLog.state = GeneConstants.TASK_ASSIGN_STAY
                            }
                        }
                        eiaTaskAssignLog.taskNameEd = it?.taskName
                        eiaTaskAssignLog.taskAssignDeptEd = it?.taskAssignDept
                        eiaTaskAssignLog.taskAssignDeptIdEd = it?.taskAssignDeptId
                        eiaTaskAssignLog.taskAssignUserEd = it?.taskAssignUser
                        eiaTaskAssignLog.taskAssignUserIdEd = it?.taskAssignUserId
                        eiaTaskAssignLog.taskAssignRoleEd = it?.taskAssignRole
                        eiaTaskAssignLog.save(flush: true, failOnError: true)
                    }
                }
                return eiaTaskLog
            }
        }
    }
}
