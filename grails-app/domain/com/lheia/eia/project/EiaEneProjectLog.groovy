package com.lheia.eia.project
/**
 * 节能评估变更日志
 */
class EiaEneProjectLog {
    /**
     * 项目ID
     */
    Long eiaProjectId
    /**
     * 项目变更ID
     */
    Long eiaProjectLogId
    /**---------------变更前信息(开始)---------------**/
    /**
     * 节能评估ID
     */
    Long eiaEneProjectId
    /**
     * 国民经济行业类型及代码
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
    /**---------------变更前信息(结束)---------------**/
    /**---------------变更后信息(开始)---------------**/
    /**
     * 节能评估ID
     */
    Long eiaEneProjectIdEd
    /**
     * 国民经济行业类型及代码
     */
    String industryTypeEd
    /**
     * 国民经济行业类型及代码Code
     */
    String industryTypeCodeEd
    /**
     * 国民经济行业类型代码
     */
    String industryTypeDescEd
    /**
     * 建设内容、规模
     */
    String contentScaleEd
    /**---------------变更后信息(结束)---------------**/
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
     * 日志录入部门
     */
    String logInputDept
    /**
     * 日志录入部门Id
     */
    String logInputDeptId
    /**
     * 日志录入部门Id
     */
    String logInputDeptCode
    /**
     * 日志录入人员
     */
    String logInputUser
    /**
     * 日志录入人员Id
     */
    Long logInputUserId

    static constraints = {
        eiaProjectId nullable: true
        eiaProjectLogId nullable: true
        /**---------------变更前信息(开始)---------------**/
        eiaEneProjectId nullable: true
        industryType nullable: true
        industryTypeCode nullable: true
        industryTypeDesc nullable: true
        contentScale nullable: true
        /**---------------变更前信息(结束)---------------**/
        /**---------------变更后信息(开始)---------------**/
        eiaEneProjectIdEd nullable: true
        industryTypeEd nullable: true
        industryTypeCodeEd nullable: true
        industryTypeDescEd nullable: true
        contentScaleEd nullable: true
        /**---------------变更后信息(结束)---------------**/
        ifDel nullable: true
        inputDept nullable: true
        inputDeptId nullable: true
        inputDeptCode nullable: true
        inputUser nullable: true
        inputUserId nullable: true
        logInputDept nullable: true
        logInputDeptId nullable: true
        logInputDeptCode nullable: true
        logInputUser nullable: true
        logInputUserId nullable: true
    }
}
