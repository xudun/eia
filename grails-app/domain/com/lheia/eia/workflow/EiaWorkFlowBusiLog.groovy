package com.lheia.eia.workflow

class EiaWorkFlowBusiLog {
    /**
     * workFlowBusi外键
     */
    Long eiaWorkFlowBusiId
    /**
     * 流程Id
     */
    Long eiaWorkFlowId
    /**
     * 流程编码
     */
    String workFlowCode
    /**
     * 流程名称
     */
    String workFlowName
    /**
     * 流程模板
     */
    String workFlowJson
    /**
     * 当前节点Id
     */
    Long eiaWorkFlowNodeId
    /**
     * 当前节点编码
     */
    String nodesCode
    /**
     * 当前节点名称
     */
    String nodesName
    /**
     * 对象表名
     */
    String tableName
    /**
     * 对象表Id
     */
    Long tableNameId
    /**
     * 流程状态(创建、进行中、结束、终止)
     */
    String workFlowState
    /**
     * 按钮ID
     */
    Long eiaWorkFlowNodeProcessId
    /**
     * 按钮名称
     */
    String processName
    /**
     * 权限名称
     */
    String authName
    /**
     *权限编码
     */
    String authCode
    /**
     * 按钮CODE
     */
    String processCode
    /**
     * 审批意见
     */
    String opinion
    /**
     * 审核时间
     */
    Date approvalDate
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
     * 录入人
     */
    String inputUser
    /**
     * 录入人ID
     */
    Long inputUserId
    /**
     * 录入人签名
     */
    String inputUserSign
    /**
     * 创建时间
     */
    Date dateCreated
    /**
     * 更新时间
     */
    Date lastUpdated
    /**
     * 更新部门
     */
    String updateDept
    /**
     * 更新部门ID
     */
    Long updateDeptId
    /**
     * 更新部门Code
     */
    String updateDeptCode
    /**
     * 更新人
     */
    String updateUser
    /**
     * 更新人ID
     */
    Long updateUserId
    /**
     * 接收人
     */
    String receiver
    /**
     * 接收人Id
     */
    Long receiverId
    static mapping = {
        opinion column: "opinion", sqlType: "LONGTEXT"
        workFlowJson column: "work_flow_json", sqlType: "LONGTEXT"
        eiaWorkFlowBusiId index: "idx_eia_work_flow_busi_id"
        inputUserId index: "idx_input_user_id"
        authCode index: "idx_auth_code"
    }
    static constraints = {
        eiaWorkFlowId nullable:true
        workFlowCode nullable: true
        workFlowName nullable: true
        workFlowState nullable: true
        eiaWorkFlowNodeId nullable:true
        workFlowJson nullable: true
        nodesCode nullable: true
        nodesName nullable: true
        authName nullable: true
        authCode nullable: true
        tableName nullable:true
        tableNameId nullable: true
        eiaWorkFlowNodeProcessId nullable: true
        processName nullable: true
        processCode nullable: true
        opinion nullable: true
        approvalDate nullable: true
        ifDel nullable: true
        inputDept nullable: true
        inputDeptId nullable: true
        inputUser nullable: true
        inputUserId nullable: true
        eiaWorkFlowBusiId nullable: true
        inputUserSign nullable: true
        updateDept nullable: true
        updateDeptCode nullable: true
        updateDeptId nullable: true
        updateUser nullable: true
        updateUserId nullable: true
        receiver nullable:true
        receiverId nullable: true
    }
}
