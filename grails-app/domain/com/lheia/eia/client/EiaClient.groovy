package com.lheia.eia.client

class EiaClient {
    /**
     * 客户名称
     */
    String clientName
    /**
     * 住所地址
     */
    String clientAddress
    /**
     * 邮政编码
     */
    String clientPostCode
    /**
     * 法人代表
     */
    String clientCorporate
    /**
     * 传真
     */
    String clientFax
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
     * 老业务系统企业id
     */
    Long oldEntId
    /**
     * 客户类型
     */
    String clientType
    /**
     * 创建时间
     */
    Date dateCreated
    /**
     * 更新时间
     */
    Date lastUpdated
    static constraints = {
        clientName nullable: true
        clientAddress nullable: true
        clientPostCode nullable: true
        clientCorporate nullable: true
        clientFax nullable: true
        ifDel nullable: true
        inputDept nullable: true
        inputDeptId nullable: true
        inputDeptCode nullable: true
        inputUser nullable: true
        inputUserId nullable: true
        oldEntId nullable: true
        clientType nullable: true
    }
}
