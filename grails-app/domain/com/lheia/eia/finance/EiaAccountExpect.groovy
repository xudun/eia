package com.lheia.eia.finance
/**
 * 财务预计信息
 */
class EiaAccountExpect {
    /**
     * 合同ID
     */
    Long eiaContractId
    /**
     * 合同编号
     */
    String contractNo
    /**
     * 合同名称
     */
    String contractName
    /**
     * 客户id
     */
    Long eiaClientId
    /**
     * 预计开票
     */
    BigDecimal expectInvoiceMoney
    /**
     * 预计收款
     */
    BigDecimal expectIncomeMoney
    /**
     * 专家费
     */
    BigDecimal expertFee = 0
    /**
     * 检测费
     */
    BigDecimal monitorFee = 0
    /**
     * 其他费
     */
    BigDecimal otherFee = 0
    /**
     * 预计期次
     */
    String expectPeriod
    /**
     * 流程状态
     */
    String accountState
    /**
     * 删除标记
     */
    Boolean ifDel = false
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
     * 创建日期
     */
    Date dateCreated
    /**
     * 修改日期
     */
    Date lastUpdated
    static mapping = {
        eiaContractId index: "idx_eia_contract_id"
    }
    static constraints = {
        eiaContractId nullable: true
        contractNo nullable: true
        contractName nullable: true
        expertFee nullable: true,scale: 4
        monitorFee nullable: true,scale: 4
        otherFee nullable: true,scale: 4
        inputDeptId nullable:true
        inputDept nullable:true
        inputUserId nullable:true
        inputDeptCode nullable: true
        inputUser nullable:true
        eiaClientId nullable: true
        expectPeriod nullable: true
        expectInvoiceMoney  nullable: true
        expectIncomeMoney nullable: true
        accountState nullable: true
    }
}
