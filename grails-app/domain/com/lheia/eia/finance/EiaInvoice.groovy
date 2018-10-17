package com.lheia.eia.finance
/**
 * 开票信息
 */
class EiaInvoice {
    /**
     * 客户配置id
     */
    Long clientConfigId
    /**
     * 客户配置名称
     */
    String clientAccountName
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
     * 合同金额
     */
    BigDecimal contractMoney
    /**
     * 发票申请机构（赤道，泰泽）
     */
    String billOrg
    /**
     * 开票日期
     */
    Date billDate
    /**
     * 开票金额
     */
    BigDecimal billMoney
    /**
     *客户名称
     */
    String clientName
    /**
     * 客户id
     */
    Long eiaClientId
    /**
     * 税务登记代码
     */
    String taxRegCode
    /**
     * 开户行
     */
    String bankName
    /**
     * 开户行户名
     */
    String bankAccount
    /**
     * 地址及电话
     */
    String addrTel
    /**
     * 开票类别
     */
    String billType
    /**
     * 发票类别
     */
    String invoiceType
    /**
     * 预计收款日期
     */
    Date estDate
    /**
     * 实际收款情况
     */
    String realMoney
    /**
     * 开票类别其他名称
     */
    String billOtherType
    /**
     * 工作流状态
     */
    String accountState
    /**
     * 删除标记
     */
    Boolean ifDel = false
    /**
     * 备注
     */
    String memo
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
        billDate column: "bill_date", sqlType: "date"
        estDate column: "est_date", sqlType: "date"
        contractId index: "idx_eia_contract_id"
    }
    static constraints = {
        contractId nullable: true
        contractNo nullable: true
        contractName nullable: true
        contractMoney nullable: true,scale: 4
        inputDeptId nullable:true
        inputDept nullable:true
        inputUserId nullable:true
        inputDeptCode nullable: true
        inputUser nullable:true
        billDate nullable: true
        billOrg nullable: true
        billMoney nullable: true,scale: 4
        clientName nullable: true
        eiaClientId nullable: true
        taxRegCode nullable: true
        bankAccount nullable: true
        addrTel nullable: true
        billType nullable: true
        invoiceType nullable: true
        estDate nullable: true
        realMoney nullable: true
        billOtherType nullable: true
        bankName nullable: true
        memo nullable: true
        accountState nullable: true
        clientConfigId nullable: true
        clientAccountName nullable: true
    }
}
