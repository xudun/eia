package com.lheia.eia.platForm
/**
 * 审批公示项目
 */
class EiaPubProject {
    /**
     * 项目名称（公司名称）
     */
    String projectName
    /**
     * 建设性质
     */
    String natureConstructio
    /**
     *环境影响评价行业类别
     */
    String  environmentaType
    /**
     * 行业类型及代码
     */
    String  industryType
    /**
     * 生产工艺
     */
    String productionEngineer
    /**
     *产品或功能
     */
    String productFunction
    /**
     * 建设单位
     */
    String developmentOrg
    /**
     * 环评单位
     */
    String eiaUnit
    /**
     * 审批部门
     */
    String approveDept
    /**
     * 审批年度
     */
    String publictyYear
    /**
     * 项目基本情况说明
     */
    String projectMemo
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
     * 建设地点中心坐标E
     */
    String  geographicEast
    /**
     * 建设地点中心坐标N
     */
    String  geographicNorth
    /**
     * 建设地点线性坐标起点E
     */
    String  geographicStartEast
    /**
     * 建设地点线性坐标起点N
     */
    String geographicStartNorth
    /**
     * 建设地点线性坐标终点E
     */
    String  geographicEndEast
    /**
     * 建设地点线性坐标终点E
     */
    String geographicEndNorth
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
     * 数据类型 (审批公示、验收公示)
     */
    String dataType
    /**
     * 是否发布
     */
    Boolean ifPub = false
    /**
     * 审批时间
     */
    Date approveDate
    /**
     * 公示日期
     */
    Date pubDate
    /**
     * 建设地点
     */
    String projectLoc
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
     * 附件ID
     */
    String fileId
    /**
     * 爬虫文件下载地址
     */
    String spiderFileDownloadUrl
    /**
     * 爬虫文件来源
     */
    String spiderFileUrl
    /**
     * 爬虫_pk
     */
    String spiderFilePk


    static mapping = {
        projectMemo column: "project_memo", sqlType: "LONGTEXT"
        projectLoc column: "project_loc", sqlType: "LONGTEXT"
        spiderFileDownloadUrl column: "spider_file_download_url", sqlType: "LONGTEXT"
        spiderFileUrl column: "spider_file_url", sqlType: "LONGTEXT"
        approveDate column: "approve_date", sqlType: "date"
        pubDate column: "pub_date", sqlType: "date"
        regionName index: "idx_region_name"
        projectName index: "idx_project_name"
        publictyYear index: "idx_publicty_year"
        natureConstructio index: "idx_nature_constructio"
    }

    static constraints = {
        projectName nullable: true
        natureConstructio nullable: true
        industryType nullable: true
        environmentaType nullable: true
        productionEngineer nullable: true
        productFunction nullable: true
        inputDeptId nullable:true
        inputDept nullable:true
        inputDeptCode nullable:true
        inputUserId nullable:true
        inputUser nullable:true
        approveDept nullable: true
        publictyYear nullable: true
        eiaUnit nullable: true
        developmentOrg nullable: true
        projectMemo nullable: true
        regionName nullable: true
        regionCode nullable: true
        regionLevel nullable: true
        viewsCount nullable:true
        downloadsCount nullable: true
        approveDate nullable: true
        fileId nullable: true
        spiderFileDownloadUrl nullable: true
        spiderFileUrl nullable: true
        spiderFilePk nullable: true
        dataType nullable: true
        ifPub nullable: true
        pubDate nullable: true
        projectLoc nullable: true
        geographicEast nullable: true
        geographicNorth nullable: true
        geographicStartEast nullable: true
        geographicStartNorth  nullable: true
        geographicEndEast  nullable: true
        geographicEndNorth  nullable: true
    }
}
