package com.lheia.eia.project
/**
 * 绿色金融
 */
class EiaGreenProjectLog {
    /**
     * 项目ID
     */
    Long eiaProjectId
    /**
     * 项目变更日志ID
     */
    Long eiaProjectLogId
    /**---------------变更前信息(开始)---------------**/
    /**
     * 绿色金融ID
     */
    Long eiaGreenProjectId
    /**
     * 发行总额（万元）
     */
    BigDecimal publishMoney
    /**
     * 发行年限（年）
     */
    Integer publishYear
    /**
     * 债券代码
     */
    String bondCode
    /**
     * 债券类型
     */
    String bondType
    /**
     * 债券名称
     */
    String bondName
    /**
     * 拟投资项目是否具有完备的合规性文件
     */
    String ifCompFile
    /**
     * 拟投资项目是否符合产业（行业）
     */
    String ifIndPolicy
    /**
     * 拟投资项目信息披露制度是否健全
     */
    String ifPublishComplete
    /**
     * 拟投资项目是否符合《绿色债券支持项目目录》
     */
    String ifGreenCatalog
    /**
     * 拟投资项目能否保证资金专款专用
     */
    Boolean ifGuarFund
    /**---------------变更前信息(结束)---------------**/
    /**---------------变更后信息(开始)---------------**/
    /**
     * 绿色金融ID
     */
    Long eiaGreenProjectIdEd
    /**
     * 发行总额（万元）
     */
    BigDecimal publishMoneyEd
    /**
     * 发行年限（年）
     */
    Integer publishYearEd
    /**
     * 债券代码
     */
    String bondCodeEd
    /**
     * 债券类型
     */
    String bondTypeEd
    /**
     * 债券名称
     */
    String bondNameEd
    /**
     * 拟投资项目是否具有完备的合规性文件
     */
    String ifCompFileEd
    /**
     * 拟投资项目是否符合产业（行业）
     */
    String ifIndPolicyEd
    /**
     * 拟投资项目信息披露制度是否健全
     */
    String ifPublishCompleteEd
    /**
     * 拟投资项目是否符合《绿色债券支持项目目录》
     */
    String ifGreenCatalogEd
    /**
     * 拟投资项目能否保证资金专款专用
     */
    Boolean ifGuarFundEd
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
     * 录入部门Code
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
        eiaGreenProjectId nullable: true
        publishMoney nullable: true
        publishYear nullable: true
        bondCode nullable: true
        bondType nullable: true
        bondName nullable: true
        ifCompFile nullable: true
        ifPublishComplete nullable: true
        ifIndPolicy nullable: true
        ifGreenCatalog nullable: true
        ifGuarFund nullable: true
        /**---------------变更前信息(结束)---------------**/
        /**---------------变更后信息(开始)---------------**/
        eiaGreenProjectIdEd nullable: true
        publishMoneyEd nullable: true
        publishYearEd nullable: true
        bondCodeEd nullable: true
        bondTypeEd nullable: true
        bondNameEd nullable: true
        ifCompFileEd nullable: true
        ifPublishCompleteEd nullable: true
        ifIndPolicyEd nullable: true
        ifGreenCatalogEd nullable: true
        ifGuarFundEd nullable: true
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
