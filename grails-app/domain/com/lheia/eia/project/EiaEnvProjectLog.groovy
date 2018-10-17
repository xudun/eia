package com.lheia.eia.project
/**
* 环保咨询变更日志
*/
class EiaEnvProjectLog {
    /**
     * 项目ID
     */
    Long eiaProjectId
    /**
     * 项目变更日志ID
     */
    Long eiaProjectLogId
    /**---------------变更前信息(开始)---------------**/
    /**
     * 环保咨询ID
     */
    Long eiaEnvProjectId
    /**
     * 建设性质
     */
    String natureConstructio
    /**
     * 项目代码
     */
    String projectCode
    /**
     *国民经济行业类型及代码
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
     * 环境影响评价行业类别
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
     * 踏勘时间
     */
    Date exploreDate
    /**
     * 踏勘记录
     */
    String exploreRecord
    /**---------------变更前信息(结束)---------------**/
    /**---------------变更后信息(开始)---------------**/
    /**
     * 环保咨询ID
     */
    Long eiaEnvProjectIdEd
    /**
     * 建设性质
     */
    String natureConstructioEd
    /**
     * 项目代码
     */
    String projectCodeEd
    /**
     *国民经济行业类型及代码
     */
    String industryTypeEd
    /**
     * 国民经济行业类型及代码Code
     */
    String industryTypeCodeEd
    /**
     * 国民经济行业类型代码
     */
    String industryTypeDescEd
    /**
     * 环境影响评价行业类别
     */
    String environmentaTypeEd
    /**
     * 环境影响评价行业类别Code
     */
    String environmentaTypeCodeEd
    /**
     * 环境影响评价行业类别
     */
    String environmentaTypeDescEd
    /**
     * 生产工艺（1-5个关键词逗号间隔）
     */
    String productionEngineerEd
    /**
     * 产品功能（1-5个关键词逗号间隔）
     */
    String productFunctionEd
    /**
     * 园区规划环评开展情况(下拉)
     */
    String parkPlanningEd
    /**
     * 规划环评文件名
     */
    String seaFileNameEd
    /**
     * 规划环评审查机关
     */
    String seaReviewOrgEd
    /**
     * 规划环评审查意见文号
     */
    String seaReviewNoEd
    /**
     * 总投资
     */
    BigDecimal projectInvestMoneyEd
    /**
     * 建设内容、规模
     */
    String contentScaleEd
    /**
     * 规划面积
     */
    Long planAreaEd
    /**
     * 基本情况介绍
     */
    String projectMemoEd
    /**
     * 是否涉及水源、风景名胜敏感区
     */
    Boolean ifSensAreaEd
    /**
     * 是否符合城市（园区总规划）
     */
    Boolean ifCityPlanEd
    /**
     * 踏勘时间
     */
    Date exploreDateEd
    /**
     * 踏勘记录
     */
    String exploreRecordEd
    /**---------------变更后信息(结束)---------------**/
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
    /**
     * 日志录入部门
     */
    String logInputDept
    /**
     * 日志录入部门Id
     */
    String logInputDeptId
    /**
     * 日志录入部门Code
     */
    String logInputDeptCode
    /**
     * 日志录入人员
     */
    String logInputUser
    /**
     * 日志录入人员Id
     */
    Long logInputUserId

    static mapping = {
        exploreRecord column: "explore_record", sqlType: "TEXT"
        exploreRecordEd column: "explore_record_ed", sqlType: "TEXT"
        projectMemo column: "project_memo", sqlType: "LONGTEXT"
        projectMemoEd column: "project_memo_ed", sqlType: "LONGTEXT"
    }

    static constraints = {
        eiaProjectLogId nullable: true
        eiaProjectId nullable: true
        /**---------------变更前信息(开始)---------------**/
        eiaEnvProjectId nullable: true
        natureConstructio nullable: true
        projectCode nullable: true
        industryType nullable: true
        industryTypeCode nullable: true
        industryTypeDesc nullable: true
        environmentaType nullable: true
        environmentaTypeCode nullable: true
        environmentaTypeDesc nullable: true
        productionEngineer nullable: true
        productFunction nullable: true
        parkPlanning nullable: true
        projectInvestMoney nullable: true,scale: 4
        seaFileName nullable: true
        seaReviewOrg nullable: true
        seaReviewNo nullable: true
        contentScale nullable: true
        planArea nullable: true
        projectMemo nullable: true
        ifSensArea nullable: true
        ifCityPlan nullable: true
        exploreDate nullable: true
        exploreRecord nullable: true
        /**---------------变更前信息(结束)---------------**/
        /**---------------变更后信息(开始)---------------**/
        eiaEnvProjectIdEd nullable: true
        natureConstructioEd nullable: true
        projectCodeEd nullable: true
        industryTypeEd nullable: true
        industryTypeCodeEd nullable: true
        industryTypeDescEd nullable: true
        environmentaTypeEd nullable: true
        environmentaTypeCodeEd nullable: true
        environmentaTypeDescEd nullable: true
        productionEngineerEd nullable: true
        productFunctionEd nullable: true
        parkPlanningEd nullable: true
        projectInvestMoneyEd nullable: true,scale: 4
        seaFileNameEd nullable: true
        seaReviewOrgEd nullable: true
        seaReviewNoEd nullable: true
        contentScaleEd nullable: true
        planAreaEd nullable: true
        projectMemoEd nullable: true
        ifSensAreaEd nullable: true
        ifCityPlanEd nullable: true
        exploreDateEd nullable: true
        exploreRecordEd nullable: true
        /**---------------变更后信息(结束)---------------**/
        ifDel nullable: true
        inputDept nullable: true
        inputDeptId nullable: true
        inputUser nullable: true
        inputUserId nullable: true
        inputDeptCode nullable: true
        logInputDept nullable: true
        logInputDeptId nullable: true
        logInputDeptCode nullable: true
        logInputUser nullable: true
        logInputUserId nullable: true
    }
}
