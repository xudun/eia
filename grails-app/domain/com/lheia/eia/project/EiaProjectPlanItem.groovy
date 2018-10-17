package com.lheia.eia.project

class EiaProjectPlanItem {
    /**
     * 项目id
     */
    Long eiaProjectId
    /**
     * 工作方案id
     */
    Long eiaProjectPlanId
    /**
     * 节点名称
     */
    String nodesName
    /**
     * 节点编码
     */
    String nodesCode
    /**
     * 节点审批人Id
     */
    Long nodeUserId
    /**
     * 节点审批人姓名
     */
    String nodeUserName
    /**
     * 节点审批人部门id
     */
    Long nodeDeptId
    /**
     * 节点审批人部门
     */
    String nodeDept
    /**
     * 节点审批人部门code
     */
    String nodeDeptCode
    /**
     * 工作方案节点人员名称
     */
    String userNames
    /**
     * 预计开始日期
     */
    Date planStartDate
    /**
     * 预计结束日期
     */
    Date planEndDate
    /**
     * 实际开始日期
     */
    Date actStartDate
    /**
     * 实际结束日期
     */
    Date actEndDate
    /**
     * 预计完成天数
     */
    Integer planFinDay
    /**
     * 实际完成天数
     */
    Integer actFinDay
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
     * 审批意见
     */
    String opinion
    /**
     * 修改内容
     */
    String modiContent
    /**
     * 是否显示图片
     */
    Boolean ifIcon
    /**
     * 操作时间
     */
    Date operateTime
    static mapping = {
        opinion column: "opinion", sqlType: "TEXT"
        modiContent column: "modi_content", sqlType: "TEXT"
        eiaProjectPlanId index: "idx_eia_project_plan_id"
        eiaProjectId index: "idx_eia_project_id"
        nodeDeptCode index: "idx_node_dept_code"
        nodeUserId index: "idx_node_user_id"
        nodeDeptId index: "idx_node_dept_id"
    }
    static constraints = {
        eiaProjectId nullable: true
        eiaProjectPlanId nullable: true
        nodeUserId nullable: true
        nodesName nullable: true
        nodesCode nullable:true
        nodeUserName nullable: true
        userNames nullable: true
        planStartDate nullable: true
        planEndDate nullable: true
        actStartDate nullable: true
        actEndDate nullable: true
        planFinDay nullable: true
        actFinDay nullable: true
        opinion nullable: true
        modiContent nullable: true
        ifIcon nullable: true
        operateTime nullable: true
        nodeDeptId nullable: true
        nodeDept nullable: true
        nodeDeptCode nullable: true
    }
}
