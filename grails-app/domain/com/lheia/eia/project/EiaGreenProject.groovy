package com.lheia.eia.project
/**
 * 绿色金融
 */
class EiaGreenProject {
    /**
     * 项目id
     */
    Long eiaProjectId
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
    Boolean ifCompFile
    /**
     * 拟投资项目是否符合产业（行业）
     */
    Boolean ifIndPolicy
    /**
     * 拟投资项目信息披露制度是否健全
     */
    Boolean ifPublishComplete
    /**
     * 拟投资项目是否符合《绿色债券支持项目目录》
     */
    Boolean ifGreenCatalog
    /**
     * 拟投资项目能否保证资金专款专用
     */
    Boolean ifGuarFund
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
        publishMoney nullable: true
        bondCode nullable: true
        bondType nullable: true
        bondName nullable: true
        ifCompFile nullable: true
        ifPublishComplete nullable: true
        ifIndPolicy nullable: true
        ifGreenCatalog nullable: true
        publishYear nullable: true
        ifGuarFund nullable: true
    }
}
