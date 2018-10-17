package com.lheia.eia.workflow
/***
 * 节点动作
 */
class EiaWorkFlowNodeProcess {

    Long eiaWorkFlowId
    /**
     * 流程节点Id
     */
    Long eiaWorkFlowNodeId
    /**
     * 按钮名称
     */
    String processName
    /**
     * 按钮别名(显示名称)
     */
    String processShowName
    /**
     * 按钮位置序号
     */
    int processNum
    /**
     * 按钮CODE
     */
    String processCode
    /**
     * 按钮提交URL
     */
    String processUrl
    /**
     * 按钮提交URL参数
     */
    String processUrlParams
    /**
     * 按钮弹出页面url
     */
    String processJumpUrl
    /**
     * 按钮弹出页面参数
     */
    String processJumpUrlParams
    /**
     * 按钮颜色
     */
    String processColor
    /**
     * 按钮图标
     */
    String processIconName
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
        eiaWorkFlowId nullable: true
        eiaWorkFlowNodeId nullable: true
        processName nullable: true
        processShowName nullable: true
        processCode nullable: true
        processUrl nullable: true
        processUrlParams nullable: true
        processJumpUrl nullable: true
        processJumpUrlParams nullable: true
        processIconName nullable: true
        processColor nullable: true
        processNum nullable: true
        ifDel nullable: true
    }
}
