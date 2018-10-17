package com.lheia.eia.platForm

/**
 * 数据共享平台中间表
 */
class EiaDataShare {
    /**
     * 项目名称（公司名称）
     */
    String projectName
    /**
     * 审批公示和已归档项目id
     */
    Long publicProId
    /**
     * 数据来源
     */
    String  dataSources
    /**
     * 审批年度
     */
    String publictyYear
    /**
     * 建设性质
     */
    String natureConstructio
    /**
     * 项目负责人id
     */
    Long projectLeaderId
    /**
     * 项目负责人
     */
    String projectLeader
    /**
     * 项目负责人部门Id
     */
    Long projectLeaderDeptId
    /**
     * 项目负责人部门
     */
    String projectLeaderDept
    /**
     * 录入人
     */
    String oneEntry
    /**
     *环境影响评价行业类别
     */
    String environmentaType
    /**
     * 行业类型及代码
     */
    String industryType
    /**
     * 生产工艺
     */
    String productionEngineer
    /**
     *产品或功能
     */
    String productFunction
    /**
     * 审批时间
     */
    Date approveDate
    /**
     * 审批文号
     */
    String approveNo
    /**
     * 审批部门
     */
    String approveDept
    /**
     * 建设单位
     */
    String developmentOrg
    /**
     * 环评单位
     */
    String eiaUnit
    /**
     * 所在地区名称
     */
    String regionName
    /**
     * 所在地区字典表ID
     */
    String  regionCode
    /**
     * 所在地区级别
     */
    Integer regionLevel
    /**
     * 浏览次数
     */
    Integer viewsCount = 0
    /**
     * 下载次数
     */
    Integer downloadsCount = 0
    /**
     * 是否收藏
     */
    Boolean ifFav = false
    /**
     * 项目基本情况说明
     */
    String projectMemo
    /**
     * 删除标记
     */
    Boolean ifDel = false
    /**
     * 创建日期
     */
    Date dateCreated
    /**
     * 修改日期
     */
    Date lastUpdated
    /**
     * 录人部门ID
     */
    Long inputDeptId
    /**
     * 录入部门
     */
    String inputDept
    /**
     * 录入部门Code
     */
    String inputDeptCode
    /**
     * 录入人ID
     */
    Long inputUserId
    /**
     * 录入人
     */
    String inputUser

    static mapping = {
        projectMemo column: "project_memo", sqlType: "LONGTEXT"
        approveDate column: "approve_date", sqlType: "date"
        dataSources index: "idx_data_sources"
        regionName index: "idx_region_name"
        projectName index: "idx_project_name"
        publictyYear index: "idx_publicty_year"
        natureConstructio index: "idx_nature_constructio"
    }

    static constraints = {
        dataSources nullable:true
        publictyYear nullable:true
        natureConstructio nullable:true
        projectLeaderId nullable:true
        projectLeader nullable:true
        projectLeaderDeptId nullable:true
        projectLeaderDept nullable:true
        oneEntry nullable:true
        ifDel nullable:true
        projectMemo nullable:true
        natureConstructio nullable: true
        industryType nullable: true
        environmentaType nullable: true
        productionEngineer nullable: true
        productFunction nullable: true
        regionName nullable:true
        regionCode nullable:true
        regionLevel nullable:true
        approveDate nullable:true
        approveNo nullable:true
        approveDept nullable:true
        developmentOrg nullable:true
        eiaUnit nullable:true
        viewsCount nullable:true
        downloadsCount nullable:true
        projectMemo nullable:true
        projectName nullable: true
        inputDeptId nullable:true
        inputDept nullable:true
        inputDeptCode nullable:true
        inputUserId nullable:true
        inputUser nullable:true
    }
}
