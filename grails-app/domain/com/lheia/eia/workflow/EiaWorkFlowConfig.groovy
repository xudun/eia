package com.lheia.eia.workflow

class EiaWorkFlowConfig {
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
     * 流程JSON
     */
    String workFlowJson
    /**
     * 工作流版本号
     */
    Integer workFlowVersion
    /**
     * 流程是否启用或停用
     */
    Boolean ifValid
    /**
     * 是否删除
     */
    Boolean ifDel=false
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
    }
    static constraints = {
        eiaWorkFlowId nullable: true
        workFlowName nullable: true
        workFlowCode nullable: true
        ifDel nullable: true
        ifValid nullable: true
        workFlowVersion nullable: true
    }
}
