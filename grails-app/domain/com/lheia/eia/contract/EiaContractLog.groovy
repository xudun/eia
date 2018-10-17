package com.lheia.eia.contract
/**
 * 合同变更日志
 */
class EiaContractLog {
    /**
     * 合同外键ID
     */
    Long eiaContractId
    /**
     * 报价ID
     */
    Long eiaOfferId
    /**
     * 合同编号
     */
    String contractNo
    /**
     * 合同状态
     */
    String contractState
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
     * 客户地址
     */
    String clientAddress
    /**
     * 客户联系人
     */
    String contactName
    /**
     * 客户联系电话
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
     * 合同名称
     */
    String contractName
    /**
     * 合同用途
     */
    String contractUse
    /**
     * 合同类型（存最下面节点）
     */
    String contractType
    /**
     * 合同类型编码
     */
    String contractTypeCode
    /**
     * 合同金额
     */
    BigDecimal contractMoney = 0
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
     * 合同签订时间
     */
    Date contractDate
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
     * 合同受托方
     */
    String contractTrust
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
     * 客户地址
     */
    String clientAddressEd
    /**
     * 客户联系人
     */
    String contactNameEd
    /**
     *客户联系电话
     */
    String contactPhoneEd
    /**
     * 甲方ID
     */
    Long ownerClientIdEd
    /**
     * 甲方名称
     */
    String ownerClientNameEd
    /**
     * 甲方地址
     */
    String ownerClientAddressEd
    /**
     * 甲方联系人
     */
    String ownerContactNameEd
    /**
     * 甲方联系人电话
     */
    String ownerContactPhoneEd
    /**
     * 任务Id
     */
    Long taskIdEd
    /**
     * 任务单号
     */
    String taskNoEd
    /**
     * 任务名称
     */
    String taskNameEd
    /**
     * 合同名称
     */
    String contractNameEd
    /**
     * 合同用途
     */
    String contractUseEd
    /**
     * 合同类型（存最下面节点）
     */
    String contractTypeEd
    /**
     * 合同类型编码
     */
    String contractTypeCodeEd
    /**
     * 合同金额
     */
    BigDecimal contractMoneyEd = 0
    /**
     * 省份
     */
    String provinceEd
    /**
     * 报告编制费
     */
    BigDecimal reportFeesEd = 0
    /**
     *环境监测费
     */
    BigDecimal enviroMonitoringFeeEd = 0
    /**
     * 专家评审费
     */
    BigDecimal expertFeeEd = 0
    /**
     * 地下水专题评价费
     */
    BigDecimal groundWaterEd = 0
    /**
     * 合同签订时间
     */
    Date contractDateEd
    /**
     * 其他费用
     */
    BigDecimal otherFeeEd = 0
    /**
     * 仪器和劳保用品费
     */
    BigDecimal specialFeeEd = 0
    /**
     * 服务费用
     */
    BigDecimal serveFeeEd = 0
    /**
     * 生态调查费用
     */
    BigDecimal ecoDetectFeeEd = 0
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
     * 合同受托方
     */
    String contractTrustEd
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
    Long logInputDeptId
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
     * 是否提交
     */
    Boolean ifSub = false
    /**
     * 是否签订合同
     */
    Boolean ifSign
    /**
     * 老业务系统Id
     */
    Long oid
    /**
     * 是否中止
     */
    Boolean ifHaltEnd
    /**
     * 是否中止成功
     */
    Boolean ifHalt
    static mapping = {
        contractDate column: "contract_date", sqlType: "date"
        contractDateEd column: "contract_date_ed", sqlType: "date"
        taskId index: "idx_eia_task_id"
        eiaContractId index: "idx_eia_contract_id"
    }
    static constraints = {
        eiaContractId nullable:true
        eiaOfferId nullable: true
        contractNo nullable: true
        contractState nullable: true
        /**---------------变更前信息(开始)---------------**/
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
        contractName nullable: true
        contractType nullable: true
        contractTypeCode nullable: true
        contractUse nullable: true
        contractMoney nullable: true , scale: 4
        province nullable: true
        reportFees nullable: true, scale: 4
        enviroMonitoringFee nullable: true, scale: 4
        expertFee nullable: true, scale: 4
        groundWater nullable: true, scale: 4
        contractDate nullable: true
        otherFee nullable: true, scale: 4
        specialFee nullable: true, scale: 4
        serveFee nullable: true, scale: 4
        ecoDetectFee nullable: true, scale: 4
        preIssCertFee nullable: true, scale: 4
        preSurvCertFee nullable: true, scale: 4
        certServeFee nullable: true, scale: 4
        contractTrust nullable: true
        /**---------------变更前信息(结束)---------------**/
        /**---------------变更后信息(开始)---------------**/
        taskIdEd nullable: true
        taskNoEd nullable: true
        taskNameEd nullable: true
        eiaClientIdEd nullable: true
        eiaClientNameEd nullable: true
        clientAddressEd nullable: true
        contactNameEd nullable: true
        contactPhoneEd nullable: true
        ownerClientIdEd nullable: true
        ownerClientNameEd nullable: true
        ownerClientAddressEd nullable: true
        ownerContactNameEd nullable: true
        ownerContactPhoneEd nullable: true
        contractNameEd nullable: true
        contractTypeEd nullable: true
        contractTypeCodeEd nullable: true
        contractUseEd nullable: true
        contractMoneyEd nullable: true , scale: 4
        provinceEd nullable: true
        reportFeesEd nullable: true, scale: 4
        enviroMonitoringFeeEd nullable: true, scale: 4
        expertFeeEd nullable: true, scale: 4
        groundWaterEd nullable: true, scale: 4
        contractDateEd nullable: true
        otherFeeEd nullable: true, scale: 4
        specialFeeEd nullable: true, scale: 4
        serveFeeEd nullable: true, scale: 4
        ecoDetectFeeEd nullable: true, scale: 4
        preIssCertFeeEd nullable: true, scale: 4
        preSurvCertFeeEd nullable: true, scale: 4
        certServeFeeEd nullable: true, scale: 4
        contractTrustEd nullable: true
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
        ifSub nullable:true
        ifSign nullable:true
        oid  nullable: true
        ifHalt nullable:true
        ifHaltEnd nullable:true
    }
}
