package com.lheia.eia.project

class EiaProjectExplore {

    /***
     * 项目名称
     */
    String projectName
    /***
     * 项目建设性质
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
     * 建设规模
     */
    String buildScale
    /**
     * 地理位置
     */
    String location
    /**
     * 是否立项
     */
    String ifSet
    /**
     *审批类型、审批制、备案制、核准制
     */
    String approvalType
    /**
     * 立项说明
     */
    String setInfo
    /**
     * 改扩建企业是否存在问题 企业限批、未批先建、限期治理、落后淘汰、其他(情况说明）
     */
    String existProblem
    /**
     * 有无排水去向（有、无、不排水）
     */
    String hasWaterWhere
    /**
     * 有无环境容量(有、无、不确定)
     */
    String hasEnvironmentCap
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
     * 两高一资项目
     */
    Boolean ifLgyzxm
    /**
     * 情况说明
     */
    String lgyzxmInfo
    /**
     * 卫生防护距离要求（满足，不满足，无要求）
     */
    String sanitaryDistance
    /**
     * 园区产业定位布局要求（满足，不满足，无要求）
     */
    String parkLayout
    /**
     * 涉及保护区(生态红线（红线、黄线）、自然保护区、风景名胜区、南水北调、地下水源保护区、地表水源保护区、文物古迹、基本农田保护区、水土流失区、其他保护区、不涉及)
     */
    String involveReserve
    /**
     * 其他敏感目标(军事设施、机场、敏感企业（食品厂）、电力及通讯设备、铁路交通干道、河道洪泛区、其他、不涉及)
     */
    String otherSense
    /**
     * 其他问题
     */
    String otherIssue
    static constraints = {
        projectName nullable:true
        buildProp nullable:true
        buildContent nullable:true
        process nullable:true
        buildScale nullable:true
        location nullable:true
        ifSet nullable:true
        approvalType nullable:true
        setInfo nullable:true
        existProblem nullable:true
        hasWaterWhere nullable:true
        hasEnvironmentCap nullable:true
        hasTotal nullable:true
        industrialDir nullable:true
        foreignDir nullable:true
        ifLgyzxm nullable:true
        lgyzxmInfo nullable:true
        sanitaryDistance nullable:true
        parkLayout nullable:true
        involveReserve nullable:true
        otherSense nullable:true
        otherIssue nullable:true
    }
}
