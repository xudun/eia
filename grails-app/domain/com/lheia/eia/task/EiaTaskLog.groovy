package com.lheia.eia.task
/**
 * 任务变更日志表
 */
class EiaTaskLog {
    /**
     * 任务Id
     */
    Long eiaTaskId
    /**
     * 任务单号
     */
    String taskNo
    /**---------------变更前信息(开始)---------------**/
    /**
     * 任务名称
     */
    String taskName
    /**
     * 业务类型
     */
    String busiType
    /**
     * 业务类型Code
     */
    String busiTypeCode
    /**
     * 负责部门
     */
    String taskLeaderDept
    /**
     * 负责部门id
     */
    Long taskLeaderDeptId
    /**
     * 任务状态
     */
    String taskState
    /**
     * 分配人员
     */
    String taskAssignUser
    /**---------------变更前信息(结束)---------------**/
    /**---------------变更后信息(开始)---------------**/
    /**
     * 任务名称
     */
    String taskNameEd
    /**
     * 业务类型
     */
    String busiTypeEd
    /**
     * 业务类型Code
     */
    String busiTypeCodeEd
    /**
     * 负责部门
     */
    String taskLeaderDeptEd
    /**
     * 负责部门id
     */
    Long taskLeaderDeptIdEd
    /**
     * 任务状态
     */
    String taskStateEd
    /**
     * 分配人员
     */
    String taskAssignUserEd
    /**---------------变更后信息(结束)---------------**/
    /**
     * 删除标记
     */
    Boolean ifDel = false
    /**
     * 录入部门
     */
    String inputDept
    /**
     * 录入部门ID
     */
    Long inputDeptId
    /**
     * 录入部门code
     */
    String inputDeptCode
    /**
     * 录入人
     */
    String inputUser
    /**
     * 录入人ID
     */
    Long inputUserId
    /**
     * 创建时间
     */
    Date dateCreated
    /**
     * 更新时间
     */
    Date lastUpdated
    /**
     * 日志录入部门
     */
    String logInputDept
    /**
     * 日志录入部门Id
     */
    Long logInputDeptId
    /**
     * 日志录入部门code
     */
    String logInputDeptCode
    /**
     * 日志录入人员
     */
    String logInputUser
    /**
     * 日志录入人员Id
     */
    Long logInputUserId

    static mapping = {
        taskAssignUser column: "task_assign_user", sqlType: "TEXT"
        taskAssignUserEd column: "task_assign_user_ed", sqlType: "TEXT"
    }

    static constraints = {
        eiaTaskId nullable: true
        taskNo nullable: true
        /**---------------变更前信息(开始)---------------**/
        taskName nullable: true
        busiType nullable: true
        busiTypeCode nullable: true
        taskLeaderDept nullable: true
        taskLeaderDeptId nullable: true
        taskState nullable: true
        taskAssignUser nullable: true
        /**---------------变更前信息(结束)---------------**/
        /**---------------变更后信息(开始)---------------**/
        taskNameEd nullable: true
        busiTypeEd nullable: true
        busiTypeCodeEd nullable: true
        taskLeaderDeptEd nullable: true
        taskLeaderDeptIdEd nullable: true
        taskStateEd nullable: true
        taskAssignUserEd nullable: true
        /**---------------变更后信息(结束)---------------**/
        ifDel nullable: true
        inputDept nullable: true
        inputDeptId nullable: true
        inputDeptCode nullable: true
        inputUser nullable: true
        inputUserId nullable: true
        logInputDept nullable: true
        logInputDeptId nullable: true
        logInputDeptCode nullable: true
        logInputUser nullable: true
        logInputUserId nullable: true
    }
}
