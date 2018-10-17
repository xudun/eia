package com.lheia.eia.project

class EiaProjectPlan {
    /**
     * 项目Id
     */
    Long eiaProjectId
    /**
     * 项目名称
     */
    String projectName
    /**
     * 任务Id
     */
    Long eiaTaskId
    /**
     * 任务名称
     */
    String taskName
    /**
     * 项目流程模板Id
     */
    Long eiaWorkFlowConfigId
    /**
     * 项目流程模板
     */
    String workFlowJson
    /**
     * 录入人Id
     */
    Long inputUserId
    /**
     * 录入人姓名
     */
    String inputUser
    /**
     * 录入部门Id
     */
    Long inputDeptId
    /**
     * 录入部门Code
     */
    String inputDeptCode
    /**
     * 录入部门
     */
    String inputDept
    /**
     * 工作方案是否提交
     */
    Boolean ifSub = false
    /**
     * 创建时间
     */
    Date dateCreated
    /**
     * 更新时间
     */
    Date lastUpdated
    /**
     * 删除标记
     */
    Boolean ifDel = false
    /**
     * 监控预警
     */
    String planMonitor
    static mapping = {
        workFlowJson column: "work_flow_json", sqlType: "LONGTEXT"
        eiaTaskId index: "idx_eia_task_id"
        eiaProjectId index: "idx_eia_project_id"
    }
    static constraints = {
        eiaProjectId nullable: true
        eiaTaskId nullable: true
        taskName nullable: true
        eiaWorkFlowConfigId nullable: true
        workFlowJson nullable: true
        inputUserId nullable: true
        inputUser nullable: true
        inputDeptId nullable: true
        inputDept nullable: true
        inputDeptCode nullable: true
        planMonitor nullable: true
    }
}
