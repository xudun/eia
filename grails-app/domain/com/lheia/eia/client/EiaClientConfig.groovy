package com.lheia.eia.client

class EiaClientConfig {
    /**
     * 客户表id
     */
    Long eiaClientId
    /**
     * 客户名称
     */
    String clientName
    /**
     * 税务登记代码
     */
    String taxRegCode
    /**
     * 开户行
     */
    String bankName
    /**
     * 开户行账号
     */
    String bankAccount
    /**
     * 地址及电话
     */
    String addrTel
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
    static constraints = {
        clientName nullable: true
        inputDeptId nullable: true
        inputDept nullable: true
        inputDeptCode nullable: true
        inputUserId nullable: true
        inputUser nullable: true
        bankAccount nullable: true
        addrTel nullable: true
        bankName nullable: true
        taxRegCode nullable: true
        eiaClientId nullable: true
    }
}
