package com.lheia.eia.finance
/**
 * 进出账信息
 */
class EiaIncomeOut {
    /**
     * 合同ID
     */
    Long contractId
    /**
     * 合同编号
     */
    String contractNo
    /**
     * 合同名称
     */
    String contractName
    /**
     *费用状态（出账,进账）
     */
    String invoiceType
    /**
     * 工作流状态
     */
    String accountState
    /**
     * 费用类型(进账，专家费，监测费，其他)
     */
    String costTypes
    /**
     * 金额
     */
    BigDecimal noteIncomeMoney
    /**
     * 日期
     */
    Date noteIncomeDate
    /**
     * 有无票据
     */
    String thereNoBill
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
     * 备注
     */
    String memo
    /**
     * 创建日期
     */
    Date dateCreated
    /**
     * 修改日期
     */
    Date lastUpdated
    static mapping = {
        noteIncomeDate column: "note_income_date", sqlType: "date"
        contractId index: "idx_eia_contract_id"
    }
    static constraints = {
        contractId nullable: true
        inputDeptId nullable:true
        inputDept nullable:true
        inputDeptCode nullable: true
        inputUserId nullable:true
        inputUser nullable:true
        thereNoBill nullable: true
        noteIncomeMoney nullable: true,scale: 4
        noteIncomeDate nullable: true
        memo nullable: true
        costTypes nullable: true
        accountState nullable: true
        invoiceType nullable: true
        contractNo nullable: true
        contractName nullable: true
    }
}
