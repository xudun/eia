package com.lheia.eia.stamp

class EiaStamp {
    /*
     * 公司名称（联合泰泽、联合赤道）
     */
    String stampCompany
    /**
     * 印章类型（公章、财务章）
     */
    String stampType
    /**
     * 印章是否外带(是，否)
     */
    Boolean ifTakeOut
    /**
     * 监督人
     */
    String supervisor
    /**
     * 申请人
     */
    String applicant
    /**
     * 申请事由
     */
    String appReason
    /**
     * 申请时间
     */
    Date appTime
    /**
     * 申请部门
     */
    String appDept
    /**
     * 用章次数
     */
    Integer stampNum
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
    Boolean ifSub = false
    /**
     * 是否业务用章
     */
    Boolean ifBussUse
    static constraints = {
        stampCompany nullable: true
        stampType nullable: true
        ifTakeOut nullable: true
        applicant nullable: true
        appReason nullable: true, type: 'text'
        appTime nullable: true
        appDept nullable: true
        stampNum nullable: true
        inputDept nullable: true
        inputUser nullable: true
        inputDeptId nullable: true
        inputDeptCode nullable: true
        inputUserId nullable: true
        dateCreated nullable: true
        lastUpdated nullable: true
        supervisor nullable: true
        ifSub nullable: true
        ifBussUse nullable: true
    }
}
