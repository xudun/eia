package com.lheia.eia.task
/**
 * 任务表
 */
class EiaTask {
    /**
     * 任务单号
     */
    String taskNo
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
     * 录入部门Code
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
    static mapping = {
        taskAssignUser column: "task_assign_user", sqlType: "LONGTEXT"
        inputDeptCode index: "idx_input_dept_code"
        inputUserId index: "idx_input_user_id"
    }
    static constraints = {
        taskNo nullable: true
        taskName nullable: true
        busiType nullable: true
        taskLeaderDept nullable: true
        taskLeaderDeptId nullable: true
        taskState nullable: true
        ifDel nullable: true
        inputDept nullable: true
        inputDeptId nullable: true
        inputDeptCode nullable: true
        inputUser nullable: true
        inputUserId nullable: true
        taskNo nullable: true
        busiTypeCode nullable: true
        taskAssignUser nullable: true
    }
}
