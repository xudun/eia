package com.lheia.eia.lab

class EiaLabOfferPlan {

    /**
     * 客户外键
     */
    Long labClientId
    /**
     * 内部报价外键
     */
    Long eiaLabOfferId
    /**
     * 能力表外键
     */
    Long labTestCapId
    /**
     * 检测基质外键
     */
    Long labTestBaseId
    /**
     * 检测项目外键
     */
    Long labTestParamId
    /**
     * 检测标准外键
     */
    Long labTestSchemeId
    /**
     * 检测基质名称
     */
    String baseName
    /**
     * 检测基质代码
     */
    String baseNameCode
    /**
     * 参数名称
     */
    String paramNameCn
    /**
     * 参数英文名称
     */
    String paramNameEn
    /**
     * 标准名称
     */
    String schemeName
    /**
     * 标准编号
     */
    String schemeCode
    /**
     * 标准所需价格
     */
    BigDecimal schemeMoney
    /**
     * 预计最高费用
     */
    BigDecimal maxSchemeMoney
    /**
     * 样品个数
     */
    Integer sampleNum
    /**
     * 检测方法依据
     */
    String testMethod
    /**
     * 点位
     */
    Integer pointNum = 0
    /**
     * 频次
     */
    Integer freqNum = 0
    /**
     * 天数周期
     */
    Integer dayNum = 0
    /**
     * 样品类型(采样/送样)
     */
    String sampleType
    /**
     * 参考限值标准
     */
    String referenceLimitStandard
    /**
     * 参考限值
     */
    String referenceLimit
    /**
     * 折扣
     */
    BigDecimal discount
    /**
     * 是否分包
     */
    Boolean ifSub
    /**
     * 分包商Id
     */
    Long supplierId
    /**
     * 分包商名称
     */
    String supplierName
    /**
     * 是否属于套餐
     */
    Boolean ifGroup
    /**
     * 检测套餐外键
     */
    Long labTestParamGroupId
    /**
     * 是否删除
     */
    Boolean ifDel = false
    /**
     * 排序顺序
     */
    Integer displayOrder = 1
    /**
     * 备注
     */
    String memo
    /**
     * 是否CMA认证
     */
    Boolean ifCma
    /**
     * 需要合并显示的标记
     */
    String mergeShowCode
    /**
     * 录入部门
     */
    String inputDept
    /**
     * 录入部门ID
     */
    Long inputDeptId
    /**
     * 录入部门code
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

    Date dateCreated
    Date lastUpdated


    static constraints = {
        labTestCapId nullable: true
        labTestBaseId nullable: true
        labTestParamId nullable: true
        labTestSchemeId nullable: true
        paramNameCn nullable: true
        paramNameEn nullable: true
        schemeName nullable: true
        schemeCode nullable: true
        schemeMoney nullable: true
        sampleNum nullable: true
        testMethod nullable: true
        sampleType nullable: true
        baseName nullable: true
        baseNameCode nullable: true
        referenceLimitStandard nullable: true
        referenceLimit nullable: true
        discount nullable: true
        ifSub nullable: true
        ifGroup nullable: true
        labTestParamGroupId nullable: true
        supplierId nullable: true
        supplierName nullable: true
        displayOrder nullable: true
        memo nullable: true
        ifCma nullable: true
        mergeShowCode nullable: true
        maxSchemeMoney nullable: true
        inputDept nullable: true
        inputDeptId nullable: true
        inputDeptCode nullable: true
        inputUser nullable: true
        inputUserId nullable: true
    }
}
