package com.lheia.eia.hr
/**
 * 月考核不参与打分
 */
class EiaHrOrgStaffEval {
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
     *姓名
     */
    String  staffName
    /**
     * 人员Id
     */
    Long staffId
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
        orgName nullable:true
        orgId nullable: true
        orgCode nullable: true
        staffName nullable: true
        staffId nullable: true
    }
}
