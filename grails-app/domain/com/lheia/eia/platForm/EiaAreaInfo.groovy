package com.lheia.eia.platForm

class EiaAreaInfo {
    /**
     * 区域或公司名称
     */
    String projectName
    /**
     * 数据类别
     */
    String dataType
    /**
     * 声功能区规划类别
     */
    String planType
    /**
     * 地理坐标E
     */
    String coordinateE
    /**
     * 地理坐标N
     */
    String coordinateN
    /**
     * 处理能力
     */
    String handAbility
    /**
     * 处理工艺
     */
    String treatProcess
    /**
     * 执行标准
     */
    String execStandard
    /**
     * 环评单位
     */
    String eiaUnit
    /**
     * 审批部门
     */
    String approveDept
    /**
     * 审批文号
     */
    String approveNo
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
     * 是否公示
     */
    Boolean ifPub = false
    /**
     * 是否收藏
     */
    Boolean ifFav = false
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


    static mapping = {
        projectMemo column: "project_memo", sqlType: "LONGTEXT"
        dataType index: "idx_data_type"
        regionCode index: "idx_region_code"
        projectName index: "idx_project_name"
    }

    static constraints = {
        projectName nullable: true
        coordinateE nullable: true
        coordinateN nullable: true
        handAbility nullable: true
        treatProcess nullable: true
        execStandard nullable: true
        inputDeptId nullable:true
        inputDept nullable:true
        inputDeptCode nullable:true
        inputUserId nullable:true
        inputUser nullable:true
        approveDept nullable: true
        approveNo nullable: true
        eiaUnit nullable: true
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
        planType nullable:true
    }
}
