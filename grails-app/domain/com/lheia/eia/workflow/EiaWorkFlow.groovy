package com.lheia.eia.workflow

class EiaWorkFlow {

    /**
     * 流程编码
     */
    String workFlowCode
    /**
     * 流程名称
     */
    String workFlowName
    /**
     * 工作流版本号
     */
    Integer workFlowVersion
    /**
     * 删除标记
     */
    Boolean ifDel = false
    /**
     * 创建时间
     */
    Date dateCreated
    /**
     * 更新时间
     */
    Date lastUpdated

    static constraints = {
        workFlowCode nullable: true
        workFlowName nullable: true
        ifDel nullable: true
        workFlowVersion nullable: true
    }
}
