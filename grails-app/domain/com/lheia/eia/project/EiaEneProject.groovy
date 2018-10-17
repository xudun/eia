package com.lheia.eia.project
/**
 * 节能评估
 */
class EiaEneProject {
    /**
     * 项目id
     */
    Long eiaProjectId
    /**
     * 国民经济行业类型及代码全称
     */
    String industryType
    /**
     * 国民经济行业类型及代码Code
     */
    String industryTypeCode
    /**
     * 国民经济行业类型代码
     */
    String industryTypeDesc
    /**
     * 建设内容、规模
     */
    String contentScale
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
    static constraints = {
        eiaProjectId nullable: true
        ifDel nullable: true
        inputDept nullable: true
        inputDeptId nullable: true
        inputDeptCode nullable: true
        inputUser nullable: true
        inputUserId nullable: true
        industryType nullable: true
        contentScale nullable: true
        industryTypeCode nullable: true
        industryTypeDesc nullable: true
    }
}
