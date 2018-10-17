package com.lheia.eia.contract

class EiaOffer {
    /**
     * 客户ID
     */
    Long eiaClientId
    /**
     * 客户名称
     */
    String eiaClientName
    /**
     * 客户地址
     */
    String clientAddress
    /**
     * 客户联系人
     */
    String contactName
    /**
     *客户联系电话
     */
    String contactPhone
    /**
     * 甲方ID
     */
    Long ownerClientId
    /**
     * 甲方名称
     */
    String ownerClientName
    /**
     * 甲方地址
     */
    String ownerClientAddress
    /**
     * 甲方联系人
     */
    String ownerContactName
    /**
     * 甲方联系人电话
     */
    String ownerContactPhone
    /**
     * 任务Id
     */
    Long taskId
    /**
     * 任务单号
     */
    String taskNo
    /**
     * 任务名称
     */
    String taskName
    /**
     * 报价名称
     */
    String offerName
    /**
     * 报价编号
     */
    String offerNo
    /**
     * 合同用途
     */
    String contractUse
    /**
     * 报价状态（已签，待签）
     */
    String offerState
    /**
     * 合同类型（存最下面节点）
     */
    String contractType
    /**
     * 合同类型编码
     */
    String contractTypeCode
    /**
     * 报价金额
     */
    BigDecimal offerMoney = 0
    /**
     * 省份
     */
    String province
    /**
     * 报告编制费
     */
    BigDecimal reportFees = 0
    /**
     *环境监测费
     */
    BigDecimal enviroMonitoringFee = 0
    /**
     * 专家评审费
     */
    BigDecimal expertFee = 0
    /**
     * 地下水专题评价费
     */
    BigDecimal groundWater = 0
    /**
     * 其他费用
     */
    BigDecimal otherFee = 0
    /**
     * 仪器和劳保用品费
     */
    BigDecimal specialFee = 0
    /**
     * 服务费用
     */
    BigDecimal serveFee = 0
    /**
     * 生态调查费用
     */
    BigDecimal ecoDetectFee = 0
    /**
     * 项目规模
     */
    BigDecimal projectScale = 0
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
     * 报价时间
     */
    Date offerDate
    /**
     * 合同受托方
     */
    String contractTrust
    /**
     * 老业务系统Id
     */
    Long oid
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
     * 是否提交
     */
    Integer ifSub

    static mapping = {
        offerDate column: "offer_date", sqlType: "date"
        taskId index: "idx_eia_task_id"
        inputDeptCode index: "idx_input_dept_code"
        inputUserId index: "idx_input_user_id"
    }
    static constraints = {
        taskId nullable: true
        taskNo nullable: true
        taskName nullable: true
        eiaClientId nullable: true
        eiaClientName nullable: true
        clientAddress nullable: true
        contactName nullable: true
        contactPhone nullable: true
        ownerClientId nullable: true
        ownerClientName nullable: true
        ownerClientAddress nullable: true
        ownerContactName nullable: true
        ownerContactPhone nullable: true
        offerName nullable: true
        offerNo nullable: true
        contractType nullable: true
        contractTypeCode nullable: true
        offerMoney nullable: true, scale: 4
        province nullable: true
        reportFees nullable: true, scale: 4
        enviroMonitoringFee nullable: true, scale: 4
        expertFee nullable: true, scale: 4
        groundWater nullable: true, scale: 4
        otherFee nullable: true, scale: 4
        specialFee nullable: true, scale: 4
        serveFee nullable: true, scale: 4
        ecoDetectFee nullable: true, scale: 4
        projectScale nullable: true, scale: 4
        preIssCertFee nullable: true, scale: 4
        preSurvCertFee nullable: true, scale: 4
        certServeFee nullable: true, scale: 4
        offerDate nullable: true
        ifDel nullable: true
        inputDept nullable: true
        inputDeptId nullable: true
        inputDeptCode nullable: true
        inputUser nullable: true
        inputUserId nullable: true
        offerState nullable: true
        contractUse nullable: true
        oid nullable: true
        contractTrust nullable: true
        ifSub nullable: true
    }
}
