package com.lheia.eia.task
/**
 * 任务人员分配表
 */
class EiaTaskAssign {
    /**
     * 任务id
     */
    Long taskId
    /**
     * 任务单号
     */
    String taskNo
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
    /**
     * 删除标记
     */
    Boolean ifDel
    /**
     * 录入部门id
     */
    Long inputDeptId
    /**
     * 录入部门
     */
    String inputDept
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
    static constraints = {
        taskId nullable: true
        taskNo nullable: true
        taskName nullable: true
        taskAssignDept nullable: true
        taskAssignDeptId nullable: true
        taskAssignUser nullable: true
        taskAssignUserId nullable: true
        ifDel nullable: true
        inputDeptId nullable: true
        inputDept nullable: true
        inputUserId nullable: true
        inputUser nullable: true
        taskAssignRole nullable: true
    }
}
