package com.lheia.eia.project

class EiaProject {
    /**
     * 任务外键ID
     */
    Long eiaTaskId
    /**
     * 客户ID
     */
    Long eiaClientId
    /**
     * 客户名称
     */
    String eiaClientName
    /**
     * 合同id
     */
    Long eiaContractId
    /**
     * 合同名称
     */
    String contractName
    /**
     * 合同编号
     */
    String contractNo
    /**
     * 主管部门
     */
    String competentDept
    /**
     * 项目类型（环保咨询，节能评估, 绿色金融）
     */
    String projectType
    /**
     * 项目类型编码（EPC，ESE, GREEN）
     */
    String projectTypeCode
    /**
     * 项目名称
     */
    String projectName
    /**
     * 项目编号
     */
    String projectNo
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
    /**
     * 项目金额
     */
    BigDecimal projectMoney = 0
    /**
     * 项目编制费
     */
    BigDecimal projectComfee = 0
    /**
     * 环境监测费
     */
    BigDecimal environmentalFee = 0
    /**
     * 地下水专题评价费
     */
    BigDecimal groundwaterFee = 0
    /**
     * 专家评审费
     */
    BigDecimal expertFee = 0
    /**
     * 其他费用
     */
    BigDecimal otherFee = 0
    /**
     * 仪器和劳保用品费
     */
    BigDecimal specialFee = 0
    /**
     * 项目调查费
     */
    BigDecimal detectFee = 0
    /**
     * 发行前认证费
     */
    BigDecimal preIssCertFee = 0
    /**
     * 存续期认证费
     */
    BigDecimal preSurvCertFee = 0
    /**
     * 认证服务费
     */
    BigDecimal certServeFee = 0
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
     * 建设地点
     */
    String buildArea
    /**
     * 建设地点中心坐标E
     */
    String coordEast
    /**
     * 建设地点中心坐标N
     */
    String coordNorth
    /**
     * 建设地点线性坐标起点E
     */
    String coordStartEast
    /**
     * 建设地点线性坐标起点N
     */
    String coordStartNorth
    /**
     * 建设地点线性坐标终点E
     */
    String coordEndEast
    /**
     * 建设地点线性坐标终点N
     */
    String coordEndNorth
    /**
     * Gis系统中的projectId
     */
    Long gisProjectId
    /**
     * 是否归档
     */
    Boolean ifArc = false
    /**
     * 根据Id随机生成的code,用于二维码的查看
     */
    String randomCode
    /**
     * 是否可申请资质
     */
    Boolean ifCert
    /**
     * 检测机构
     */
    Boolean ifLab
    /**
     * 项目负责人
     */
    String dutyUser
    /**
     * 项目负责人Id
     */
    String dutyUserId
    /**
     * 老业务系统Id
     */
    Long oid

    static mapping = {
        eiaClientId index: "idx_eia_client_id"
        eiaTaskId index: "idx_eia_task_id"
        eiaContractId index: "idx_eia_contract_id"
        inputDeptCode index: "idx_input_dept_code"
        inputUserId index: "idx_input_user_id"
        inputDeptId index: "idx_input_dept_id"
    }

    static constraints = {
        eiaTaskId nullable: true
        projectName nullable: true
        projectNo nullable: true
        fileType nullable: true
        fileTypeCode nullable: true
        fileTypeChild nullable: true
        fileTypeChildCode nullable: true
        projectMoney nullable: true, scale: 4
        inputDept nullable: true
        inputDeptId nullable: true
        inputDeptCode nullable: true
        inputUser nullable: true
        inputUserId nullable: true
        eiaClientId nullable: true
        eiaClientName nullable: true
        eiaContractId nullable: true
        contractName nullable: true
        contractNo nullable: true
        projectComfee nullable: true, scale: 4
        environmentalFee nullable: true, scale: 4
        groundwaterFee nullable: true, scale: 4
        expertFee nullable: true, scale: 4
        otherFee nullable: true, scale: 4
        specialFee nullable: true, scale: 4
        detectFee nullable: true, scale: 4
        preIssCertFee nullable: true, scale: 4
        preSurvCertFee nullable: true, scale: 4
        certServeFee nullable: true, scale: 4
        projectType nullable: true
        projectTypeCode nullable: true
        competentDept nullable: true
        buildArea nullable: true
        coordEast nullable: true
        coordNorth nullable: true
        coordStartNorth nullable: true
        coordStartEast nullable: true
        coordEndEast nullable: true
        coordEndNorth nullable: true
        gisProjectId nullable: true
        ifArc nullable: true
        randomCode nullable: true
        ifCert nullable: true
        ifLab nullable: true
        dutyUser nullable: true
        dutyUserId nullable: true
        oid nullable: true
    }
}
