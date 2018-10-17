package com.lheia.eia.config

class EiaDataMaintainLog {
    /**
     * 操作对象表名
     */
    String tableName
    /**
     * 操作对象表Id
     */
    Long tableNameId
    /**
     * 维护前录入部门
     */
    String agoInputDept
    /**
     * 维护前录入部门ID
     */
    Long agoInputDeptId
    /**
     * 维护前录入部门Code
     */
    String agoInputDeptCode
    /**
     * 维护前录入人
     */
    String agoInputUser
    /**
     * 维护前录入人ID
     */
    Long agoInputUserId
    /**
     * 维护后录入部门
     */
    String afterInputDept
    /**
     * 维护后录入部门ID
     */
    Long afterInputDeptId
    /**
     * 维护后录入部门Code
     */
    String afterInputDeptCode
    /**
     * 维护后录入人
     */
    String afterInputUser
    /**
     * 维护后录入人ID
     */
    Long afterInputUserId
    /**
     * 录入部门
     */
    String inputDept
    /**
     * 录入部门code
     */
    String inputDeptCode
    /**
     * 录入部门ID
     */
    Long inputDeptId
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
    static constraints = {
        tableName nullable:true
        tableNameId nullable: true
        agoInputDept nullable:true
        agoInputDeptId nullable: true
        agoInputDeptCode nullable:true
        agoInputUser nullable: true
        agoInputUserId nullable:true
        afterInputDept nullable: true
        afterInputDeptId nullable:true
        afterInputDeptCode nullable: true
        afterInputUser nullable: true
        afterInputUserId nullable: true
        inputDept nullable: true
        inputDeptCode nullable: true
        inputDeptId nullable: true
        inputUser nullable: true
        inputUserId nullable: true

    }
}
