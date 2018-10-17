package com.lheia.eia.platForm
/**
 * 环境敏感区
 */
class EiaSensArea {
    /**
     * 名称
     */
    String projectName
    /**
     * 数据类别
     */
    String dataType

    /**
     * 基本情况简介
     */
    String projectMemo
    /**
     * 所在地区名称
     */
    String regionName
    /**
     * 所在地区字典表code
     */
    String  regionCode
    /**
     * 所在地区级别
     */
    Integer regionLevel
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
     * 是否收藏
     */
    Boolean ifFav = false
    /**
     * 删除标记
     */
    Boolean ifDel = false
    /**
     * 是否公示
     */
    Boolean ifPub = false
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
     * 建设地点中心坐标E
     */
    String  geographicEast
    /**
     * 区域级别
     */
    String areaLv
    /**
     * 区域类型
     */
    String areaType
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

    static mapping = {
        projectMemo column: "project_memo", sqlType: "LONGTEXT"
        dataType index: "idx_data_sources"
        regionName index: "idx_region_name"
        projectName index: "idx_project_name"
    }

    static constraints = {
        projectName nullable: true
        inputDeptId nullable:true
        inputDept nullable:true
        inputDeptCode nullable:true
        inputUserId nullable:true
        inputUser nullable:true
        projectMemo nullable: true
        regionName nullable: true
        regionCode nullable: true
        regionLevel nullable: true
        fileId nullable: true
        geographicEast nullable:true
        geographicNorth nullable:true
        geographicStartEast nullable:true
        geographicStartNorth nullable:true
        geographicEndEast nullable:true
        geographicEndNorth nullable:true
        ifPub nullable:true
        areaLv nullable:true
        areaType nullable:true
    }
}
