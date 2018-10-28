package com.lheia.eia.project

class EiaProjectExplore {

    /***
     * 项目名称
     */
    String projectName
    /***
     * 内审单编号
     */
    String exploreNo
    /***
     * 项目建设性质（新建（迁建）	改扩建	技术改造 ）
     */
    String buildProp
    /**
     * 建设内容
     */
    String buildContent
    /**
     * 工艺
     */
    String process
    /**
     * 地理位置
     */
    String buildArea
    /**
     * 是否立项
     */
    String ifSet
    /**
     *审批类型、审批制、备案制、核准制
     */
    String approvalType
    /**
     * 立项情况说明
     */
    String setInfo
    /**
     * 改扩建企业是否存在问题 企业限批、未批先建、限期治理、落后淘汰、其他(情况说明）
     */
    String existProblem
    /**
     * 改扩建企业是否存在问题编码
     */
    String existProblemCode
    /**
     * 改扩建企业是否存在问题 其他情況説明
     */
    String existProblemInfo
    /**
     * 有无排水去向（有、无、不排水）
     */
    String hasWaterWhere
    /**
     * 有无总量(有、无、不确定)
     */
    String hasTotal
    /**
     * 产业结构指导目录2011（鼓励类、限制类、淘汰类、允许类）
     */
    String industrialDir
    /**
     * 外商投资指导目录2011（鼓励类、限制类、淘汰类、允许类、不涉及）
     */
    String foreignDir
    /**
     * 卫生防护距离要求（满足，不满足，无要求）
     */
    String sanitaryDistance
    /**
     * 园区产业定位布局要求（满足，不满足，无要求）
     */
    String parkLayout
    /**
     * 是否在园区（在，不在，不涉及）
     */
    String inPark
    /**
     * 涉及保护区(生态红线（红线、黄线）、自然保护区、风景名胜区、南水北调、地下水源保护区、地表水源保护区、文物古迹、基本农田保护区、水土流失区、其他保护区、不涉及)
     */
    String involveReserve
    String involveReserveCode
    /**
     * 其他敏感目标(军事设施、机场、敏感企业（食品厂）、电力及通讯设备、铁路交通干道、河道洪泛区、其他、不涉及)
     */
    String otherSense
    String otherSenseCode
    String otherSenseInfo
    /**
     * 地下水评价等级
     */
    String groundWater
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
     * 其他问题
     */
    String otherIssue
    /**
     * 删除标记
     */
    Boolean ifDel = false
    /**
     * 是否提交
     */
    Boolean ifSub = false
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
     *流程是否结束
     */
    Boolean ifEnd = false
    /**
     * gis项目id
     */
    Long gisGeoProjectId
    /**
     * 任务id
     */
    Long eiaTaskId
    /**
     * 项目id
     */
    Long eiaProjectId
    /**
     * 空间对象
     */
    String geoJson
    /**
     * 文件父类型名称
     */
    String fileType
    /**
     * 文件父类型编码
     */
    String fileTypeCode
    /**
     * 文件子类型名称
     */
    String fileTypeChild
    /**
     * 文件子类型编码
     */
    String fileTypeChildCode
    static mapping = {
        geoJson column: "geo_json", sqlType: "TEXT"
    }
    static constraints = {
        fileType nullable: true
        fileTypeCode nullable: true
        fileTypeChild nullable: true
        fileTypeChildCode nullable: true
        projectName nullable:true
        ifEnd nullable:true
        buildProp nullable:true
        buildContent nullable:true
        process nullable:true
        buildArea nullable: true
        ifSet nullable:true
        approvalType nullable:true
        setInfo nullable:true
        existProblem nullable:true
        existProblemCode nullable:true
        hasWaterWhere nullable:true
        hasTotal nullable:true
        industrialDir nullable:true
        foreignDir nullable:true
        otherIssue nullable:true
        otherIssue nullable:true
        inPark nullable:true
        sanitaryDistance nullable:true
        parkLayout nullable:true
        involveReserve nullable:true
        involveReserveCode nullable:true
        otherSense nullable:true
        otherSenseCode nullable:true
        inputDept nullable:true
        inputDeptId nullable:true
        inputDeptCode nullable:true
        inputUserId nullable:true
        dateCreated nullable:true
        lastUpdated nullable:true
        existProblemInfo nullable:true
        environmentaType nullable:true
        environmentaTypeCode nullable:true
        environmentaTypeDesc nullable:true
        exploreNo nullable:true
        otherSenseInfo nullable:true
        ifSub nullable:true
        gisGeoProjectId nullable:true
        eiaProjectId nullable:true
        eiaTaskId nullable:true
        geoJson nullable:true
    }
}
