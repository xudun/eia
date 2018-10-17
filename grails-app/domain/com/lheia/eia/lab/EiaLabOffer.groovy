package com.lheia.eia.lab

class EiaLabOffer {

    /**
     * 任务ID
     */
    Long eiaTaskId
    /**
     * 项目ID
     */
    Long eiaProjectId
    /**
     * Gis系统中的projectId
     */
    Long gisProjectId
    /**
     * 报告出具日期
     */
    Date reportDate
    /**-----------报价基本信息-----------**/
     /**
     * 委托客户ID
     */
    Long wtClientId
    /**
     * 委托客户名称
     */
    String wtClientName
    /**
     * 委托客户地址
     */
    String wtClientAddr
    /**
     * 委托客户联系人
     */
    String wtClientContact
    /**
     * 委托客户联系人电话
     */
    String wtClientPhone
    /**
     * 受检客户ID
     */
    Long sjClientId
    /**
     * 受检客户名称
     */
    String sjClientName
    /**
     * 受检客户地址
     */
    String sjClientAddr
    /**
     * 受检客户联系人
     */
    String sjClientContact
    /**
     * 受检客户联系人电话
     */
    String sjClientPhone
    /**
     * 项目类型
     */
    String projectType
    /**
     * 样品类型
     */
    String sampleType
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
    /**
     * 报价编号
     */
    String offerCode
    /**
     *项目名称
     */
    String projectName
    /**
     * 项目地址
     */
    String projectAddr
    /**
     * 项目联系人
     */
    String projectContact
    /**
     * 项目联系人电话
     */
    String projectPhone
    /**
     * 是否宇相检测
     */
    Boolean ifYxTest
    /**
     * 委托检测方
     */
    String testCompany
    /**-----------委托书费用明细-----------**/
    /**
     * 样品小计费用
     */
    BigDecimal sampleFee
    /**
     * 样品数量
     */
    Integer sampleNum
    /**
     * 现场勘查费用
     */
    BigDecimal exploreFee
    /**
     * 现场勘查人数
     */
    Integer explorePeopleNum
    /**
     * 现场勘查天数
     */
    Integer exploreDayNum
    /**
     * 勘查费用小计（元）
     */
    BigDecimal exploreFeeTotal
    /**
     * 交通费用
     */
    BigDecimal trafficFee
    /**
     * 差旅天数
     */
    Integer travelDayNum
    /**
     * 交通费用小计（元）
     */
    BigDecimal trafficFeeTotal
    /**
     * 住宿费
     */
    BigDecimal hotelFee
    /**
     * 房间个数
     */
    Integer roomNum
    /**
     * 住宿天数
     */
    Integer liveDayNum
    /**
     * 住宿费用小计（元）
     */
    BigDecimal hotelFeeTotal
    /**
     * 报告编制费用（元）
     */
    BigDecimal workOutFee
    /**
     * 专家费用（元）
     */
    BigDecimal expertFee
    /**
     * 其他费用类型
     */
    String otherFeeType
    /**
     * 其他费用（元）
     */
    BigDecimal otherFee
    /**
     * 费用总计
     */
    BigDecimal countFee
    /**
     * 费用总计(含税)
     */
    BigDecimal countFeeTax
    /**
     * 优惠前价格
     */
    BigDecimal prePrefFee
    /**
     * 优惠前价格（含税）
     */
    BigDecimal prePrefFeeTax
    /**
     * 商务洽谈价
     */
    BigDecimal busiFee
    /**
     * 采样费用折扣
     */
    BigDecimal exploreSubTotalDisc
    /**
     * 其他费用折扣
     */
    BigDecimal otherFeeTotalDisc
    /**-----------测试信息确认-----------**/
    /**
     * 测试周期
     */
    String testPeriod
    /**
     * 报告语言
     */
    String reportLanguage
    /**
     * 是否添加限值
     */
    Boolean ifLimit
    /**
     * 报告模板
     */
    String reportTemp
    /**
     * 测试方法
     */
    String testMethod
    /**
     * 检测分包
     */
    String testSub
    /**
     * 剩余处理
     */
    String remainDeal
    /**
     * 余样处理备注
     */
    String remainDealMemo
    /**
     * 开票单位名称
     */
    String invoiceComp
    /**
     * 税号
     */
    String taxCode
    /**
     * 单位地址
     */
    String clientAddr
    /**
     * 开户银行
     */
    String openAccountBank
    /**
     * 银行账户
     */
    String bankAccount
    /**
     * 票据类型
     */
    String ticketType
    /**
     * 电话
     */
    String clientPhone
    /**
     * 是否有CMA章
     */
    Boolean ifCma
    /**
     * 是否废气折算浓度
     */
    Boolean ifFlueGasConvConcn
    /**
     * 是否废气排放速率
     */
    Boolean ifFlueGasEmisRate
    /**
     * 测试天数
     */
    Integer testDayNum
    /**
     * 检测分包类型
     */
    String testSubType
    /**
     * 采样需求备注
     */
    String sampRequMemo
    /**
     * 测试需求备注
     */
    String testRequMemo
    /**
     * 商务需求备注
     */
    String busiRequMemo
    /**-----------报告信息-----------**/
    /**
     * 客户名称中文
     */
    String clientNameCn
    /**
     * 客户名称英文
     */
    String clientNameEn
    /**
     * 客户地址中文
     */
    String clientAddrCn
    /**
     * 客户地址英文
     */
    String clientAddrEn
    /**------------合同信息-----------**/
    /**
     * 默认合同周期
     */
    Integer contractPeriod
    /**
     * 默认合同折扣
     */
    BigDecimal contractDiscount
    /**
     * 默认合同付款周期
     */
    Integer contractPayPeriod
    /**
     * 默认报告模板
     */
    String defReportTemp
    /**
     * 默认税费
     */
    BigDecimal contractTax
    /**
     * 财务信息外键
     */
    Long labClientFinanceInfoId
    /**
     * 合同信息外键
     */
    Long labClientContractInfoId
    /**
     * 报价状态
     */
    String offerState
    /**
     * 推送状态
     */
    Integer pushState
    /**
     * 是否监测完成
     */
    Boolean ifTestComplete

    Boolean ifDel = false
    Date dateCreated
    Date lastUpdated

    static mapping = {
        reportDate column: "report_date", sqlType: "date"
        otherFeeType sqlType: "text"
        eiaTaskId index: "idx_eia_task_id"
        eiaProjectId index: "idx_eia_project_id"
    }

    static constraints = {
        eiaTaskId nullable: true
        eiaProjectId nullable: true
        gisProjectId nullable: true
        wtClientId nullable: true
        wtClientName nullable: true
        wtClientAddr nullable: true
        wtClientContact nullable: true
        wtClientPhone nullable: true
        sjClientId nullable: true
        sjClientName nullable: true
        sjClientAddr nullable: true
        sjClientContact nullable: true
        sjClientPhone nullable: true
        inputDept nullable: true
        inputDeptId nullable: true
        inputDeptCode nullable: true
        inputUser nullable: true
        inputUserId nullable: true
        projectType nullable: true
        sampleFee nullable: true, scale: 2
        sampleNum nullable: true
        exploreFee nullable: true, scale: 2
        trafficFee nullable: true, scale: 2
        hotelFee nullable: true, scale: 2
        countFee nullable: true, scale: 2
        testPeriod nullable: true
        reportLanguage nullable: true
        ifLimit nullable: true
        reportTemp nullable: true
        testMethod nullable: true
        testSub nullable: true
        remainDeal nullable: true
        invoiceComp nullable: true
        taxCode nullable: true
        clientAddr nullable: true
        openAccountBank nullable: true
        bankAccount nullable: true
        ticketType nullable: true
        clientNameCn nullable: true
        clientNameEn nullable: true
        clientAddrCn nullable: true
        clientAddrEn nullable: true
        contractPeriod nullable: true
        contractDiscount nullable: true, scale: 2
        contractPayPeriod nullable: true
        contractTax nullable: true
        defReportTemp nullable: true
        reportDate nullable: true
        clientPhone nullable: true
        sampleType nullable: true
        offerCode nullable: true
        labClientFinanceInfoId nullable: true
        labClientContractInfoId nullable: true
        countFeeTax nullable: true
        ifCma nullable: true
        projectName nullable: true
        explorePeopleNum nullable: true
        exploreDayNum nullable: true
        travelDayNum nullable: true
        roomNum nullable: true
        liveDayNum nullable: true
        workOutFee nullable: true
        expertFee nullable: true
        exploreFeeTotal nullable: true
        trafficFeeTotal nullable: true
        hotelFeeTotal nullable: true
        ifFlueGasConvConcn nullable: true
        ifFlueGasEmisRate nullable: true
        testDayNum nullable: true
        testSubType nullable: true
        remainDealMemo nullable: true
        otherFeeType nullable: true
        otherFee nullable: true
        offerState nullable: true
        ifYxTest nullable: true
        testCompany nullable: true
        pushState nullable: true
        prePrefFee nullable: true
        prePrefFeeTax nullable: true
        busiFee nullable: true
        sampRequMemo nullable:true
        testRequMemo nullable: true
        busiRequMemo nullable: true
        projectAddr nullable: true
        projectContact nullable: true
        projectPhone nullable: true
        exploreSubTotalDisc nullable: true, scale: 2
        otherFeeTotalDisc nullable: true, scale: 2
        ifTestComplete nullable: true
    }
}
