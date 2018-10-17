package com.lheia.eia.project
/**
 * 项目变更日志
 */
class EiaProjectLog {
    /**
     * 项目外键ID
     */
    Long eiaProjectId
    /**
     * 项目编号
     */
    String projectNo
    /**
     * 任务外键ID
     */
    Long eiaTaskId
    /**
     * Gis系统中的projectId
     */
    Long gisProjectId
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
     * 老业务系统Id
     */
    Long oid
    /**---------------变更前信息(开始)---------------**/
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
     * 项目名称
     */
    String projectName
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
     * 项目负责人
     */
    String dutyUser
    /**
     * 项目负责人Id
     */
    String dutyUserId
    /**---------------变更前信息(结束)---------------**/
    /**---------------变更后信息(开始)---------------**/
    /**
     * 客户ID
     */
    Long eiaClientIdEd
    /**
     * 客户名称
     */
    String eiaClientNameEd
    /**
     * 合同id
     */
    Long eiaContractIdEd
    /**
     * 合同名称
     */
    String contractNameEd
    /**
     * 合同编号
     */
    String contractNoEd
    /**
     * 主管部门
     */
    String competentDeptEd
    /**
     * 项目类型（环保咨询，节能评估, 绿色金融）
     */
    String projectTypeEd
    /**
     * 项目名称
     */
    String projectNameEd
    /**
     * 文件父类型名称
     */
    String fileTypeEd
    /**
     * 文件父类型编码
     */
    String fileTypeCodeEd
    /**
     * 文件子类型名称
     */
    String fileTypeChildEd
    /**
     * 文件子类型编码
     */
    String fileTypeChildCodeEd
    /**
     * 项目金额
     */
    BigDecimal projectMoneyEd = 0
    /**
     * 项目编制费
     */
    BigDecimal projectComfeeEd = 0
    /**
     * 环境监测费
     */
    BigDecimal environmentalFeeEd = 0
    /**
     * 地下水专题评价费
     */
    BigDecimal groundwaterFeeEd = 0
    /**
     * 专家评审费
     */
    BigDecimal expertFeeEd = 0
    /**
     * 其他费用
     */
    BigDecimal otherFeeEd = 0
    /**
     * 仪器和劳保用品费
     */
    BigDecimal specialFeeEd = 0
    /**
     * 项目调查费
     */
    BigDecimal detectFeeEd = 0
    /**
     * 发行前认证费
     */
    BigDecimal preIssCertFeeEd = 0
    /**
     * 存续期认证费
     */
    BigDecimal preSurvCertFeeEd = 0
    /**
     * 认证服务费
     */
    BigDecimal certServeFeeEd = 0
    /**
     * 建设地点
     */
    String buildAreaEd
    /**
     * 建设地点中心坐标E
     */
    String coordEastEd
    /**
     * 建设地点中心坐标N
     */
    String coordNorthEd
    /**
     * 建设地点线性坐标起点E
     */
    String coordStartEastEd
    /**
     * 建设地点线性坐标起点N
     */
    String coordStartNorthEd
    /**
     * 建设地点线性坐标终点E
     */
    String coordEndEastEd
    /**
     * 建设地点线性坐标终点N
     */
    String coordEndNorthEd
    /**
     * 项目负责人
     */
    String dutyUserEd
    /**
     * 项目负责人Id
     */
    String dutyUserIdEd
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
     * 录入部门
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
    /**
     * 是否归档
     */
    Boolean ifArc = false

    static constraints = {
        eiaProjectId nullable: true
        projectNo nullable: true
        eiaTaskId nullable: true
        gisProjectId nullable: true
        randomCode nullable: true
        ifCert nullable: true
        ifLab nullable: true
        oid nullable: true
        /**---------------变更前信息(开始)---------------**/
        projectName nullable: true
        fileType nullable: true
        fileTypeCode nullable: true
        fileTypeChild nullable: true
        fileTypeChildCode nullable: true
        projectMoney nullable: true, scale: 4
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
        competentDept nullable: true
        buildArea nullable: true
        coordEast nullable: true
        coordNorth nullable: true
        coordStartNorth nullable: true
        coordStartEast nullable: true
        coordEndEast nullable: true
        coordEndNorth nullable: true
        dutyUser nullable: true
        dutyUserId nullable: true
        /**---------------变更前信息(结束)---------------**/
        /**---------------变更后信息(开始)---------------**/
        projectNameEd nullable: true
        fileTypeEd nullable: true
        fileTypeCodeEd nullable: true
        fileTypeChildEd nullable: true
        fileTypeChildCodeEd nullable: true
        projectMoneyEd nullable: true, scale: 4
        eiaClientIdEd nullable: true
        eiaClientNameEd nullable: true
        eiaContractIdEd nullable: true
        contractNameEd nullable: true
        contractNoEd nullable: true
        projectComfeeEd nullable: true, scale: 4
        environmentalFeeEd nullable: true, scale: 4
        groundwaterFeeEd nullable: true, scale: 4
        expertFeeEd nullable: true, scale: 4
        otherFeeEd nullable: true, scale: 4
        specialFeeEd nullable: true, scale: 4
        detectFeeEd nullable: true, scale: 4
        preIssCertFeeEd nullable: true, scale: 4
        preSurvCertFeeEd nullable: true, scale: 4
        certServeFeeEd nullable: true, scale: 4
        projectTypeEd nullable: true
        competentDeptEd nullable: true
        buildAreaEd nullable: true
        coordEastEd nullable: true
        coordNorthEd nullable: true
        coordStartNorthEd nullable: true
        coordStartEastEd nullable: true
        coordEndEastEd nullable: true
        coordEndNorthEd nullable: true
        dutyUserEd nullable: true
        dutyUserIdEd nullable: true
        /**---------------变更后信息(结束)---------------**/
        ifDel nullable: true
        inputDept nullable: true
        inputDeptId nullable: true
        inputDeptCode nullable: true
        inputUser nullable: true
        inputUserId nullable: true
        logInputDept nullable: true
        logInputDeptId nullable: true
        logInputUser nullable: true
        logInputUserId nullable: true
        logInputDeptCode nullable: true
        ifArc nullable: true
    }
}
