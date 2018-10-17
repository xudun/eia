package com.lheia.eia.workflow

class EiaWorkFlowBusi {

    /**
     * 流程Id
     */
    Long eiaWorkFlowId
    /**
     * 流程编码
     */
    String workFlowCode
    /***
     * 审批标题
     */
    String workFlowTitle
    /**
     * 流程名称
     */
    String workFlowName
    /**
     * 权限名称
     */
    String authName
    /**
     *权限编码
     */
    String authCode
    /**
     * 权限类型
     */
    String authType
    /**
     * 审批人部门
     */
    String authDept
    /**
     * 审批人部门Id
     */
    Long authDeptId
    /**
     * 审批人部门Code
     */
    String authDeptCode
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
     * 流程状态(开始（start）、进行中（under way）、结束(end)、终止(halt))
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
     * 按钮CODE
     */
    String processCode
    /**
     * 删除标记
     */
    Boolean ifDel = false
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
        workFlowJson column: "work_flow_json", sqlType: "LONGTEXT"
        tableNameId index: "idx_table_name_id"
        inputUserId index: "idx_input_user_id"
        authCode index: "idx_auth_code"
        inputDeptCode index: "idx_input_dept_code"
        authDeptId index: "idx_auth_dept_id"
        inputDeptId index: "idx_input_dept_id"
    }
    static constraints = {
        eiaWorkFlowId nullable:true
        eiaWorkFlowNodeId nullable:true
        authType nullable:true
        authCode nullable:true
        authName nullable:true
        workFlowCode nullable: true
        workFlowName nullable: true
        workFlowJson nullable:true
        workFlowState nullable: true
        nodesCode nullable: true
        nodesName nullable: true
        eiaWorkFlowNodeProcessId nullable: true
        processName nullable: true
        processCode nullable: true
        tableName nullable:true
        tableNameId nullable: true
        ifDel nullable: true
        inputDept nullable: true
        inputDeptCode nullable: true
        inputDeptId nullable: true
        inputUser nullable: true
        inputUserId nullable: true
        workFlowTitle nullable: true
        updateDept nullable: true
        updateDeptCode nullable: true
        updateDeptId nullable: true
        updateUser nullable: true
        updateUserId nullable: true
        authDept nullable: true
        authDeptId nullable: true
        authDeptCode nullable: true
    }
}
