package com.lheia.eia.hr
/**
 * 月考核初始
 */
class EiaHrEval {
    /**
     *部门
     */
    String  orgName
    /**
     * 部门Id
     */
    Long orgId
    /**
     * 部门orgCode
     */
    String orgCode
    /**
     * 考核月份
     */
    String assessmentMonth
    /**
     * 类型
     */
    String jobRatingType
    /**
     * 是否删除
     */
    Boolean ifDel = false
    /**
     * 创建日期
     */
    Date dateCreated
    /**
     * 更新日期
     */
    Date lastUpdated
    static constraints = {
        ifDel nullable:true
        assessmentMonth nullable:true
        orgName nullable:true
        orgId nullable: true
        jobRatingType nullable: true
        orgCode nullable: true
    }
}
