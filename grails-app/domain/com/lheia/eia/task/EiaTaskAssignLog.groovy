package com.lheia.eia.task
/**
 * 任务人员分配变更日志表
 */
class EiaTaskAssignLog {
    /**
     * 任务变更Id
     */
    Long eiaTaskLogId
    /**
     * 任务id
     */
    Long taskId
    /**
     * 任务人员分配Id
     */
    Long eiaTaskAssignId
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
     * 分派部门
     */
    String taskAssignDept
    /**
     * 分派部门id
     */
    Long taskAssignDeptId
    /**
     * 分派人员
     */
    String taskAssignUser
    /**
     * 分派人员Id
     */
    Long taskAssignUserId
    /**
     * 分配角色
     */
    String taskAssignRole
    /**---------------变更前信息(结束)---------------**/
    /**---------------变更后信息(开始)---------------**/
    /**
     * 任务名称
     */
    String taskNameEd
    /**
     * 分派部门
     */
    String taskAssignDeptEd
    /**
     * 分派部门id
     */
    Long taskAssignDeptIdEd
    /**
     * 分派人员
     */
    String taskAssignUserEd
    /**
     * 分派人员Id
     */
    Long taskAssignUserIdEd
    /**
     * 分配角色
     */
    String taskAssignRoleEd
    /**---------------变更后信息(结束)---------------**/
    /**
     * 删除标记
     */
    Boolean ifDel = false
    /**
     * 录入部门id
     */
    Long inputDeptId
    /**
     * 录入部门
     */
    String inputDept
    /**
     * 录入部门
     */
    String inputDeptCode
    /**
     * 录入人ID
     */
    Long inputUserId
    /**
     * 录入人
     */
    String inputUser
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
     * 日志录入部门
     */
    String logInputDeptCode
    /**
     * 日志录入部门Id
     */
    Long logInputDeptId
    /**
     * 日志录入人员
     */
    String logInputUser
    /**
     * 日志录入人员Id
     */
    Long logInputUserId
    /**
     * 变更状态
     */
    String state

    static constraints = {
        eiaTaskLogId nullable: true
        taskId nullable: true
        eiaTaskAssignId nullable: true
        taskNo nullable: true
        /**---------------变更前信息(开始)---------------**/
        taskName nullable: true
        taskAssignDept nullable: true
        taskAssignDeptId nullable: true
        taskAssignUser nullable: true
        taskAssignUserId nullable: true
        taskAssignRole nullable: true
        /**---------------变更前信息(结束)---------------**/
        /**---------------变更后信息(开始)---------------**/
        taskNameEd nullable: true
        taskAssignDeptEd nullable: true
        taskAssignDeptIdEd nullable: true
        taskAssignUserEd nullable: true
        taskAssignUserIdEd nullable: true
        taskAssignRoleEd nullable: true
        /**---------------变更后信息(结束)---------------**/
        ifDel nullable: true
        inputDeptId nullable: true
        inputDept nullable: true
        inputDeptCode nullable: true
        inputUserId nullable: true
        inputUser nullable: true
        logInputDept nullable: true
        logInputDeptCode nullable: true
        logInputDeptId nullable: true
        logInputUser nullable: true
        logInputUserId nullable: true
        state nullable: true
    }
}
