package com.lheia.eia.client
/**
 * 客户联系人
 */
class EiaClientContacts {
    /**
     * 客户表id
     */
    Long eiaClientId
    /**
     * 联系人名称
     */
    String contactName
    /**
     *职务
     */
    String contactPosition
    /**
     * 联系电话
     */
    String contactPhone
    /**
     * 电子邮件（微信、QQ）
     */
    String contactEmail
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
     * 创建时间
     */
    Date dateCreated
    /**
     * 更新时间
     */
    Date lastUpdated

    static mapping = {
        eiaClientId index: "idx_eia_client_id"
    }

    static constraints = {
        eiaClientId nullable: true
        contactName nullable: true
        contactPosition nullable: true
        contactPhone nullable: true
        contactEmail nullable: true
        clientFax nullable: true
        ifDel nullable: true
        inputDept nullable: true
        inputDeptId nullable: true
        inputDeptCode nullable: true
        inputUser nullable: true
        inputUserId nullable: true
    }
}
