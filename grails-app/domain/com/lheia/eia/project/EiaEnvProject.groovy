package com.lheia.eia.project
/**
 * 环保咨询
 */
class EiaEnvProject {
    /**
     * 项目id
     */
    Long eiaProjectId
    /**
     * 建设性质
     */
    String natureConstructio
    /**
     * 项目代码
     */
    String projectCode
    /**
     * 国民经济行业类型及代码全称
     */
    String industryType
    /**
     * 国民经济行业类型及代码Code
     */
    String industryTypeCode
    /**
     * 国民经济行业类型代码
     */
    String industryTypeDesc
    /**
     * 环境影响评价行业类别全称
     */
    String environmentaType
    /**
     * 环境影响评价行业类别Code
     */
    String environmentaTypeCode
    /**
     * 环境影响评价行业类别
     */
    String environmentaTypeDesc
    /**
     * 生产工艺（1-5个关键词逗号间隔）
     */
    String productionEngineer
    /**
     * 产品功能（1-5个关键词逗号间隔）
     */
    String productFunction
    /**
     * 园区规划环评开展情况(下拉)
     */
    String parkPlanning
    /**
     * 规划环评文件名
     */
    String seaFileName
    /**
     * 规划环评审查机关
     */
    String seaReviewOrg
    /**
     * 规划环评审查意见文号
     */
    String seaReviewNo
    /**
     * 总投资
     */
    BigDecimal projectInvestMoney
    /**
     * 建设内容、规模
     */
    String contentScale
    /**
     * 规划面积
     */
    Long planArea
    /**
     * 基本情况介绍
     */
    String projectMemo
    /**
     * 是否涉及水源、风景名胜敏感区
     */
    Boolean ifSensArea
    /**
     * 是否符合城市（园区总规划）
     */
    Boolean ifCityPlan
    /**
     * 踏勘记录
     */
    String exploreRecord
    /**
     * 踏勘时间
     */
    Date  exploreDate
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
        exploreRecord column: "explore_record", sqlType: "TEXT"
        projectMemo column: "project_memo", sqlType: "LONGTEXT"
    }
    static constraints = {
        eiaProjectId nullable: true
        natureConstructio nullable: true
        projectCode nullable: true
        industryType nullable: true
        environmentaType nullable: true
        productionEngineer nullable: true
        productFunction nullable: true
        parkPlanning nullable: true
        projectInvestMoney nullable: true, scale: 4
        seaFileName nullable: true
        seaReviewOrg nullable: true
        seaReviewNo nullable: true
        contentScale nullable: true
        ifSensArea nullable: true
        ifCityPlan nullable: true
        exploreDate nullable: true
        exploreRecord nullable: true
        ifDel nullable: true
        inputDept nullable: true
        inputDeptId nullable: true
        inputDeptCode nullable: true
        inputUser nullable: true
        inputUserId nullable: true
        planArea nullable: true
        projectMemo nullable: true
        industryTypeCode nullable: true
        environmentaTypeCode nullable: true
        environmentaTypeDesc nullable: true
        industryTypeDesc nullable: true
    }
}
