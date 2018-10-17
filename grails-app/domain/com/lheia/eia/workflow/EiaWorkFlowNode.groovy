package com.lheia.eia.workflow

class EiaWorkFlowNode {
    /**
     * 流程Id
     */
    Long eiaWorkFlowId
    /**
     * 流程节点编码
     */
    String nodesCode
    /**
     * 流程节点序号
     */
    int nodesNum
    /**
     * 流程节点名称
     */
    String nodesName
    /**
     * 流程节点权限控制类型
     * ABOUT_USER与人相关
     * ABOUT_DEPT与部门相关
     * ABOUT_AUTH_CODE与权限编码相关
     */
    String nodesAuthType
    /**
     * 节点权限编码
     */
    String nodesAuthCode
    /**
     * 流程节点样式图标
     */
    String nodesIconName
    /**
     * 流程节点颜色
     */
    String nodesColor
    /**
     * 流程节点Url(对应不同Content页面)
     */
    String nodesUrl
    /**
     * 节点显示表单title
     */
    String nodesTabTitle
    Boolean ifDel = false
    /**
     * 创建时间
     */
    Date dateCreated
    /**
     * 更新时间
     */
    Date lastUpdated
    /**
     * 是否显示图片
     */
    Boolean ifIcon

    static constraints = {
        eiaWorkFlowId nullable: true
        nodesCode nullable: true
        nodesName nullable: true
        nodesColor nullable: true
        nodesIconName nullable: true
        nodesAuthType nullable: true
        nodesAuthCode nullable: true
        nodesNum nullable: true
        nodesUrl nullable: true
        nodesTabTitle nullable: true
        ifDel nullable: true
        ifIcon nullable: true
    }

}
