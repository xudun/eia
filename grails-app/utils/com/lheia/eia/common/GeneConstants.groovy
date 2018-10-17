package com.lheia.eia.common

import grails.util.Holders


class GeneConstants {
    /**
     * 联合泰泽组织代码
     */
    public static String ORG_CODE_TAIZE = 'LHCD_LHTZ'
    /**
     * 任务状态
     */
    public static String TASK_STATE_NEW = '新建'
    /**
     * 任务状态
     */
    public static String TASK_STATE_SUBMIT = '已提交'
    /**
     * 任务状态
     */
    static String TASK_STATE_END = '结束'
    /**
     * 获取domainCode中业务类型
     */
    public static String TASK_BUSINESS_TYPE = 'BUSINESS'
    /**
     * 任务分配角色(任务负责人)
     */
    public static String TASK_ASSIGN_ROLE_EXAM = '项目审核人'
    /**
     * 任务分配角色(任务分配人)
     */
    public static String TASK_ASSIGN_ROLE_TOAST = '主持编制人'
    /**
     * 任务分配角色（项目编写人）
     */
    public static String TASK_ASSIGN_ROLE_WRITER = '项目编写人'
    /**
     * 任务单号domainCode类型
     */
    public static String TASK_NUM = 'TASK_NUM'
    /**
     * 报价编号
     */
    public static String OFFER_NUM = 'OFFER_NUM'
    /**
     * 合同编号
     */
    public static String CONTRACT_NUM = 'CONTRACT_NUM'
    /**
     * 项目编号
     */
    public static String PROJECT_NUM = 'PROJECT_NUM'
    /**
     * 合同状态(未签订)
     */
    public static String CONTRACT_STATE_NOT_SIGNED = '未签订'
    /**
     * 合同状态(已签订)
     */
    public static String CONTRACT_STATE_SIGNED = '已签订'
    /**
     * 开票状态(已开票)
     */
    static String INVOICE_STATUS_HAS = '已开票'
    /**
     * 开票状态(未开票)
     */
    static String INVOICE_STATUS_NO = '未开票'
    /**
     * 进账状态(已进账)
     */
    static String INCOME_STATUS_HAS = '已进账'
    /**
     * 进账状态(未进账)
     */
    static String INCOME_STATUS_NO = '未进账'
    /**
     * 出账状态(已出账)
     */
    static String INVOICE_OUT_STATUS_HAS = '已出账'
    /**
     * 出账状态(未出账)
     */
    static String INVOICE_OUT_STATUS_NO = '未出账'
    /**
     * 财务确认状态(未确认)
     */
    static String INVOICE_CONFIRM_NO = '未确认'
    /**
     * 财务确认状态(已确认)
     */
    static String INVOICE_CONFIRM_YES = '已确认'
    /**
     * 财务类型(进账)
     */
    static String INVOICE_TYPE_INCOME = '进账'
    /**
     * 财务类型(出账)
     */
    static String INVOICE_TYPE_OUT = '出账'
    /**
     * 财务状态（新建）
     */
    static String INVOICE_ACCOUNT_STATE_NEW = '未提交'
    /**
     * 财务状态
     */
    static String INVOICE_ACCOUNT_STATE_SUB = '已提交'
    /**
     * 财务状态
     */
    static String INVOICE_ACCOUNT_STATE_COM = '已完成'
    /**
     * 开票
     */
    static String INVOICE_ACCOUNT_NAME = '开票'
    /**
     * 费用类型（专家费）
     */
    static String INVOICE_ECPERT_FEES = '专家费'
    /**
     * 费用类型（监测费)
     */
    static String INVOICE_MONITORING_FEES = '检测费'
    /**
     * 费用类型（其他）
     */
    static String INVOICE_OTHER_FEES = '其他'
    /**
     * 开票和出账退回
     */
    static String INVOICE_BACK = '驳回'
    /**
     * 合同用途
     */
    static String CONTRACT_USE = 'CONTRACT_USE'
    /**
     * 省份
     */
    static String PROVINCE_CODE = 'PROVINCE_CODE'
    /**
     * 任务分配人员变更——无变更
     */
    static String TASK_ASSIGN_STAY = 'TASK_ASSIGN_STAY'
    /**
     * 任务分配人员变更——新增
     */
    static String TASK_ASSIGN_ADD = 'TASK_ASSIGN_ADD'
    /**
     * 任务分配人员变更——删除
     */
    static String TASK_ASSIGN_DEL = 'TASK_ASSIGN_DEL'

    /**
     * 资质申请类型 报批
     */
    static String CERT_TYPE_APPROVAL = 'CERT_TYPE_APPROVAL'
    /**
     * 资质申请类型 送审
     */
    static String CERT_TYPE_REVIEW = 'CERT_TYPE_REVIEW'
    /**
     * 文件类型
     */
    static String PROJECT_FILE_TYPE = 'PROJECT_FILE_TYPE'
    static String GNS_TYPE_CODE = 'GNS_TYPE_CODE'
    static String INS_TYPE_CODE = 'INS_TYPE_CODE'
    /**
     * 合同文件类型
     */
    static String CONTRACT_TYPE = 'CONTRACT_TYPE'
    /**
     *     外委合同
     */
    static String CONTRACT_FILT_TYPE_WWHT = "WWHT"
    /**
     * 合同扫描件
     */
    static String CONTRACT_FILT_TYPE_HTSMJ = "HTSMJ"
    /**
     * 印章名
     */
    static String CONTRACT_TRUST_LHTZ = "联合泰泽"
    static String CONTRACT_TRUST_LHCD = "联合赤道"

    /**
     * 共享查询泰泽赤道详情显示文件类型
     */
    /***项目归档附件***/
    static String SEARCH_XMGDFJ = "XMGDFJ"
    /***终稿***/
    static String SEARCH_ZG = "ZG"
    /***批复或备案上传***/
    static String SEARCH_PFHBA = "PFHBA"

    /**
     * 文件类型（第三级:fileCode）
     */
/*    public static List<String> HBLIST = ["pGeneRep", "pSpecialRep"]
    public static List<String> HSLIST = ["pLightSpin", "pMeta", "pBuildFire", "pFarmFore", "pExca", "pTraffic", "pSocial", "pMariEng", "pTransRadio", "pNucInd"]
    public static List<String> HCLIST = ["pEnvAnaly"]
    public static List<String> YSLIST = ["pProtMoni", "pProtRese"]
    public static List<String> YALIST = ["pEmerPlan"]*/
    /**
     * 文件类型第二级
     */
    static List<String> ENVLIST = ["EPC_HP", "EPC_HY", "EPC_HB", "EPC_YS", "EPC_YA", "EPC_JL", "EPC_GH", "EPC_HH", "EPC_SH", "EPC_XZ", "EPC_PF", "EPC_CD", "EPC_ST", "EPC_QT"]
    static List<String> ESELIST = ["ESE_LZ", "ESE_QT"]
    static List<String> GREENLIST = ["GREEN_LZ", "GREEN_LQ", "GREEN_SC", "GREEN_ZR", "GREEN_TH", "GREEN_FP", "GREEN_QT"]
    /**
     * 工作方案节点预计时间为必填的
     */
    static List<String> PLAN_DATE_NODE_LIST = ['现场踏勘', '现场勘查', '编写监测', '一审', '二审', '三审', '编写报告', '内部审查', '周报月报', '总结报告', '审核', '审定', '现场访谈', '其它']
    /**
     * 不同文件类型需要的字段(根据第二级判断)
     */
    static List<String> HPPROPLIST = ["natureConstructio", "projectCode", "industryType", "environmentaType", "productionEngineer", "productFunction", "parkPlanning", "seaFileName", "seaReviewOrg", "seaReviewNo", "projectInvestMoney", "contentScale"]
    static List<String> HYPROPLIST = ["natureConstructio", "projectCode", "industryType", "environmentaType", "productionEngineer", "productFunction", "parkPlanning", "seaFileName", "seaReviewOrg", "seaReviewNo", "projectInvestMoney", "contentScale"]
    static List<String> HBPROPLIST = ["industryType", "environmentaType", "productionEngineer", "productFunction", "contentScale"]
    static List<String> YSPROPLIST = ["productionEngineer", "productFunction", "projectInvestMoney", "contentScale"]
    static List<String> YAPROPLIST = ["contentScale"]
    static List<String> JLPROPLIST = []
    static List<String> GHPROPLIST = ["planArea", "productFunction", "projectMemo"]
    static List<String> HHPROPLIST = ["industryType", "environmentaType", "productionEngineer", "productFunction"]
    static List<String> SHPROPLIST = []
    static List<String> XZPROPLIST = ["industryType", "environmentaType", "productionEngineer", "parkPlanning", "seaFileName", "seaReviewOrg", "seaReviewNo", "contentScale"]
    static List<String> PFPROPLIST = ["contentScale"]
    static List<String> STPROPLIST = []
    static List<String> CDPROPLIST = ["projectMemo"]
    static List<String> ESELZPROPLIST = ["industryType", "contentScale"]
    /**————绿色金融项目的字段一致——————**/
    static List<String> GREENPROPLIST = ["publishMoney", "bondCode", "bondType", "bondName", "ifCompFile", "ifIndPolicy", "ifPublishComplete", "ifGreenCatalog", "ifGuarFund", "publishYear"]
    /**
     * 项目变更时，不同文件类型需要的字段
     */
    static List<String> HPPROPLOGLIST = ["natureConstructioEd", "projectCodeEd", "industryTypeEd", "environmentaTypeEd", "productionEngineerEd", "productFunctionEd", "parkPlanningEd", "seaFileNameEd", "seaReviewOrgEd", "seaReviewNoEd", "projectInvestMoneyEd", "contentScaleEd"]
    static List<String> HYPROPLOGLIST = ["natureConstructioEd", "projectCodeEd", "industryTypeEd", "environmentaTypeEd", "productionEngineerEd", "productFunctionEd", "parkPlanningEd", "seaFileNameEd", "seaReviewOrgEd", "seaReviewNoEd", "projectInvestMoneyEd", "contentScaleEd"]
    static List<String> HBPROPLOGLIST = ["industryTypeEd", "environmentaTypeEd", "productionEngineerEd", "productFunctionEd", "contentScaleEd"]
    static List<String> YSPROPLOGLIST = ["productionEngineerEd", "productFunctionEd", "projectInvestMoneyEd", "contentScaleEd"]
    static List<String> YAPROPLOGLIST = ["contentScaleEd"]
    static List<String> JLPROPLOGLIST = []
    static List<String> GHPROPLOGLIST = ["planAreaEd", "productFunctionEd", "projectMemoEd"]
    static List<String> HHPROPLOGLIST = ["industryTypeEd", "environmentaTypeEd", "productionEngineerEd", "productFunctionEd"]
    static List<String> SHPROPLOGLIST = []
    static List<String> XZPROPLOGLIST = ["industryTypeEd", "environmentaTypeEd", "productionEngineerEd", "parkPlanningEd", "seaFileNameEd", "seaReviewOrgEd", "seaReviewNoEd", "contentScaleEd"]
    static List<String> PFPROPLOGLIST = ["contentScaleEd"]
    static List<String> STPROPLOGLIST = []
    static List<String> CDPROPLOGLIST = ["projectMemoEd"]
    static List<String> ESELZPROPLOGLIST = ["industryTypeEd", "contentScaleEd"]
    /**————项目变更时，绿色金融项目的字段一致——————**/
    static List<String> GREENPROPLOGLIST = ["publishMoneyEd", "bondCodeEd", "bondTypeEd", "bondNameEd", "ifCompFileEd", "ifIndPolicyEd", "ifPublishCompleteEd", "ifGreenCatalogEd", "ifGuarFundEd", "publishYearEd"]
    /**
     * 不同文件类型需要的金额参数（项目）
     */
    static List<String> HP_MONEY_LIST = ["projectComfee", "environmentalFee", "groundwaterFee", "expertFee", "otherFee"]
    static List<String> HY_MONEY_LIST = ["projectComfee", "environmentalFee", "groundwaterFee", "expertFee", "otherFee"]
    static List<String> HB_MONEY_LIST = ["projectComfee", "environmentalFee", "groundwaterFee", "expertFee", "otherFee"]
    static List<String> YS_MONEY_LIST = ["projectComfee", "environmentalFee", "expertFee", "otherFee"]
    static List<String> YA_MONEY_LIST = ["projectComfee", "expertFee", "otherFee"]
    static List<String> JL_MONEY_LIST = ["projectComfee", "specialFee", "otherFee"]
    static List<String> GH_MONEY_LIST = ["projectComfee", "environmentalFee", "groundwaterFee", "expertFee", "otherFee"]
    static List<String> HH_MONEY_LIST = ["projectComfee", "environmentalFee", "groundwaterFee", "expertFee", "otherFee"]
    static List<String> SH_MONEY_LIST = ["projectMoney"]     //没有子项只有项目金额
    static List<String> XZ_MONEY_LIST = ["projectComfee", "environmentalFee", "expertFee", "otherFee"]
    static List<String> PF_MONEY_LIST = ["projectComfee", "expertFee", "otherFee"]
    static List<String> ST_MONEY_LIST = ["projectComfee", "environmentalFee", "expertFee", "otherFee"]
    static List<String> CD_MONEY_LIST = ["projectComfee", "detectFee", "expertFee", "otherFee"]
    /**——————节能评估和绿金需要的字段都一样（项目）——————**/
    static List<String> ESE_MONEY_LIST = ["projectComfee", "environmentalFee", "otherFee"]
    static List<String> LZ_MONEY_LIST = ["preIssCertFee", "preSurvCertFee"]
    static List<String> LQ_MONEY_LIST = ["certServeFee", "otherFee"]
    static List<String> GREEN_MONEY_LIST = ["projectComfee", "otherFee"]
    /**
     * 不同文件类型需要的金额参数（项目变更）
     */
    static List<String> PRO_LOG_HP_MONEY_LIST = ["projectComfeeEd", "environmentalFeeEd", "groundwaterFeeEd", "expertFeeEd", "otherFeeEd"]
    static List<String> PRO_LOG_HY_MONEY_LIST = ["projectComfeeEd", "environmentalFeeEd", "groundwaterFeeEd", "expertFeeEd", "otherFeeEd"]
    static List<String> PRO_LOG_HB_MONEY_LIST = ["projectComfeeEd", "environmentalFeeEd", "groundwaterFeeEd", "expertFeeEd", "otherFeeEd"]
    static List<String> PRO_LOG_YS_MONEY_LIST = ["projectComfeeEd", "environmentalFeeEd", "expertFeeEd", "otherFeeEd"]
    static List<String> PRO_LOG_YA_MONEY_LIST = ["projectComfeeEd", "expertFeeEd", "otherFeeEd"]
    static List<String> PRO_LOG_JL_MONEY_LIST = ["projectComfeeEd", "specialFeeEd", "otherFeeEd"]
    static List<String> PRO_LOG_GH_MONEY_LIST = ["projectComfeeEd", "environmentalFeeEd", "groundwaterFeeEd", "expertFeeEd", "otherFeeEd"]
    static List<String> PRO_LOG_HH_MONEY_LIST = ["projectComfeeEd", "environmentalFeeEd", "groundwaterFeeEd", "expertFeeEd", "otherFeeEd"]
    static List<String> PRO_LOG_SH_MONEY_LIST = ["projectMoneyEd"]     //没有子项只有项目金额
    static List<String> PRO_LOG_XZ_MONEY_LIST = ["projectComfeeEd", "environmentalFeeEd", "expertFeeEd", "otherFeeEd"]
    static List<String> PRO_LOG_PF_MONEY_LIST = ["projectComfeeEd", "expertFeeEd", "otherFeeEd"]
    static List<String> PRO_LOG_ST_MONEY_LIST = ["projectComfeeEd", "environmentalFeeEd", "expertFeeEd", "otherFeeEd"]
    static List<String> PRO_LOG_CD_MONEY_LIST = ["projectComfeeEd", "detectFee", "expertFeeEd", "otherFeeEd"]
    /**——————节能评估和绿金需要的字段都一样（项目变更）——————**/
    static List<String> PRO_LOG_ESE_MONEY_LIST = ["projectComfeeEd", "environmentalFeeEd", "otherFeeEd"]
    static List<String> PRO_LOG_LZ_MONEY_LIST = ["preIssCertFeeEd", "preSurvCertFeeEd"]
    static List<String> PRO_LOG_LQ_MONEY_LIST = ["certServeFeeEd", "otherFeeEd"]
    static List<String> PRO_LOG_GREEN_MONEY_LIST = ["projectComfeeEd", "otherFeeEd"]
    /**
     * 不同文件类型需要的金额参数（合同）
     */
    static List<String> CON_HD_MONEY_LIST = ["reportFees"]
    static List<String> CON_HP_MONEY_LIST = ["reportFees", "enviroMonitoringFee", "groundWater", "expertFee", "otherFee"]
    static List<String> CON_HY_MONEY_LIST = ["reportFees", "enviroMonitoringFee", "groundWater", "expertFee", "otherFee"]
    static List<String> CON_HB_MONEY_LIST = ["reportFees", "enviroMonitoringFee", "groundWater", "expertFee", "otherFee"]
    static List<String> CON_GH_MONEY_LIST = ["reportFees", "enviroMonitoringFee", "groundWater", "expertFee", "otherFee"]
    static List<String> CON_JC_MONEY_LIST = ["reportFees", "enviroMonitoringFee", "expertFee", "otherFee"]
    static List<String> CON_DC_MONEY_LIST = ["reportFees", "enviroMonitoringFee", "expertFee", "otherFee"]
    static List<String> CON_YA_MONEY_LIST = ["reportFees", "expertFee", "otherFee"]
    static List<String> CON_JL_MONEY_LIST = ["reportFees", "specialFee", "otherFee"]
    static List<String> CON_HH_MONEY_LIST = ["reportFees", "enviroMonitoringFee", "groundWater", "expertFee", "otherFee"]
    static List<String> CON_GJ_MONEY_LIST = ["serveFee", "otherFee"]
    static List<String> CON_XZ_MONEY_LIST = ["reportFees", "enviroMonitoringFee", "expertFee", "otherFee"]
    static List<String> CON_PF_MONEY_LIST = ["reportFees", "expertFee", "otherFee"]
    static List<String> CON_PW_MONEY_LIST = ["reportFees", "otherFee"]
    static List<String> CON_CD_MONEY_LIST = ["reportFees", "enviroMonitoringFee", "expertFee", "otherFee"]
    static List<String> CON_ST_MONEY_LIST = ["reportFees", "expertFee", "otherFee", "ecoDetectFee"]
    static List<String> CON_JN_MONEY_LIST = ["reportFees", "otherFee"]
    static List<String> CON_QJ_MONEY_LIST = ["reportFees", "enviroMonitoringFee", "otherFee"]
    static List<String> CON_LZ_MONEY_LIST = ["preIssCertFee", "preSurvCertFee"]
    static List<String> CON_LQ_MONEY_LIST = ["certServeFee", "otherFee"]
    static List<String> CON_GREEN_MONEY_LIST = ["reportFees", "otherFee"]
    static List<String> CON_EPC_ZH_MONEY_LIST = ["reportFees", "enviroMonitoringFee", "expertFee", "otherFee"]
    static List<String> CON_ZH_MONEY_LIST = ["reportFees", "otherFee"]
    static List<String> CON_QT_MONEY_LIST = ["reportFees", "otherFee"]
    /**
     * 不同文件类型需要的金额参数（合同变更）
     */
    static List<String> CON_LOG_HD_MONEY_LIST = ["reportFeesEd"]
    static List<String> CON_LOG_HP_MONEY_LIST = ["reportFeesEd", "enviroMonitoringFeeEd", "groundWaterEd", "expertFeeEd", "otherFeeEd"]
    static List<String> CON_LOG_HY_MONEY_LIST = ["reportFeesEd", "enviroMonitoringFeeEd", "groundWaterEd", "expertFeeEd", "otherFeeEd"]
    static List<String> CON_LOG_HB_MONEY_LIST = ["reportFeesEd", "enviroMonitoringFeeEd", "groundWaterEd", "expertFeeEd", "otherFeeEd"]
    static List<String> CON_LOG_GH_MONEY_LIST = ["reportFeesEd", "enviroMonitoringFeeEd", "groundWaterEd", "expertFeeEd", "otherFeeEd"]
    static List<String> CON_LOG_JC_MONEY_LIST = ["reportFeesEd", "enviroMonitoringFeeEd", "expertFeeEd", "otherFeeEd"]
    static List<String> CON_LOG_DC_MONEY_LIST = ["reportFeesEd", "enviroMonitoringFeeEd", "expertFeeEd", "otherFeeEd"]
    static List<String> CON_LOG_YA_MONEY_LIST = ["reportFeesEd", "expertFeeEd", "otherFeeEd"]
    static List<String> CON_LOG_JL_MONEY_LIST = ["reportFeesEd", "specialFee", "otherFeeEd"]
    static List<String> CON_LOG_HH_MONEY_LIST = ["reportFeesEd", "enviroMonitoringFeeEd", "groundWaterEd", "expertFeeEd", "otherFeeEd"]
    static List<String> CON_LOG_GJ_MONEY_LIST = ["serveFeeEd", "otherFeeEd"]
    static List<String> CON_LOG_XZ_MONEY_LIST = ["reportFeesEd", "enviroMonitoringFeeEd", "expertFeeEd", "otherFeeEd"]
    static List<String> CON_LOG_PF_MONEY_LIST = ["reportFeesEd", "expertFeeEd", "otherFeeEd"]
    static List<String> CON_LOG_PW_MONEY_LIST = ["reportFeesEd", "otherFeeEd"]
    static List<String> CON_LOG_CD_MONEY_LIST = ["reportFeesEd", "enviroMonitoringFeeEd", "expertFeeEd", "otherFeeEd"]
    static List<String> CON_LOG_ST_MONEY_LIST = ["reportFeesEd", "expertFeeEd", "otherFeeEd", "ecoDetectFeeEd"]
    static List<String> CON_LOG_JN_MONEY_LIST = ["reportFeesEd", "otherFeeEd"]
    static List<String> CON_LOG_QJ_MONEY_LIST = ["reportFeesEd", "enviroMonitoringFeeEd", "otherFeeEd"]
    static List<String> CON_LOG_LZ_MONEY_LIST = ["preIssCertFeeEd", "preSurvCertFeeEd"]
    static List<String> CON_LOG_LQ_MONEY_LIST = ["certServeFeeEd", "otherFeeEd"]
    static List<String> CON_LOG_GREEN_MONEY_LIST = ["reportFeesEd", "otherFeeEd"]
    static List<String> CON_LOG_EPC_ZH_MONEY_LIST = ["reportFeesEd", "enviroMonitoringFeeEd", "expertFeeEd", "otherFeeEd"]
    static List<String> CON_LOG_ZH_MONEY_LIST = ["reportFeesEd", "otherFeeEd"]
    static List<String> CON_LOG_QT_MONEY_LIST = ["reportFeesEd", "otherFeeEd"]
    /**
     * 所有可以导出环评报告资质模板对应的文件类型
     */
    static List<String> ALL_EXPORT_PROVE_FILE_TYPE_CODE_LIST = ["EPC_HP", "EPC_HY", "EPC_GH", "EPC_HB", "EPC_XZ"]
    /**
     * 导出环评报告资质模板对应的文件类型（报告书、报告表、规划）
     */
    static List<String> EXPORT_PROVE_FILE_TYPE_CODE_LIST = ["EPC_HP", "EPC_HY", "EPC_GH"]
    /**
     * 受托方名称domain
     */
    static String TRUSTEE_CLIENT = 'TRUSTEE_CLIENT'
    /**
     * 受托方开户银行domain
     */
    static String TRUSTEE_BANK = 'TRUSTEE_BANK'
    /**
     * 受托方地址domain
     */
    static String TRUSTEE_ADDR = 'TRUSTEE_ADDR'
    /**
     * 受托方账号domain
     */
    static String TRUSTEE_ACCOUNT = 'TRUSTEE_ACCOUNT'
    /**
     * 受托方账户名称domain
     */
    static String TRUSTEE_ACC_NAME = 'TRUSTEE_ACC_NAME'
    /**
     * 联合泰泽公司名称code
     */
    static String TAIZE_NAME = 'TAIZE_NAME'
    /**
     * 联合泰泽开户银行code
     */
    static String TAIZE_BANK = 'TAIZE_BANK'
    /**
     * 联合泰泽地址code
     */
    static String TAIZE_ADDR = 'TAIZE_ADDR'
    /**
     * 联合泰泽账号code
     */
    static String TAIZE_ACCOUNT = 'TAIZE_ACCOUNT'
    /**
     * 联合泰泽账户名称code
     */
    static String TAIZE_ACC_NAME = 'TAIZE_ACC_NAME'
    /**
     * 联合赤道公司名称code
     */
    static String CHIDAO_NAME = 'CHIDAO_NAME'
    /**
     * 联合赤道开户银行code
     */
    static String CHIDAO_BANK = 'CHIDAO_BANK'
    /**
     * 联合赤道地址code
     */
    static String CHIDAO_ADDR = 'CHIDAO_ADDR'
    /**
     * 联合赤道账号code
     */
    static String CHIDAO_ACCOUNT = 'CHIDAO_ACCOUNT'
    /**
     * 联合赤道账户名称code
     */
    static String CHIDAO_ACC_NAME = 'CHIDAO_ACC_NAME'
    /**
     * 考核表类型（表一）
     */
    static String EIA_HR_EVAL_EMP = '员工评分表'
    /**
     * 考核表类型（表二）
     */
    static String EIA_HR_EVAL_ASS = '助理评分表'
    /**————————————————————————报价状态————————————————————————**/
    /**
     * 已完成报价
     */
    static String OFFER_COMPLETE = '生成报价'
    /**
     * 已生成订单
     */
    static String CREATE_JOB = '生成订单'
    /**
     * 报价已作废
     */
    static String OFFER_STOP = '报价作废'

    /**
     * 泰泽录入检测方案，推送状态为0
     */
    static Integer TAIZE_CREATE_INNER_OFFER = 0
    /**
     * 泰泽提交检测方案，推送状态为1
     */
    static Integer TAIZE_SUBMIT_INNER_OFFER = 1

    /**
     * lab系统中项目类型domain
     */
    static String PROJECT_TYPE = 'PROJECT_TYPE'
    /**
     * 委托检测方domain
     */
    static String TEST_COMPANY = 'TEST_COMPANY'
    /**
     * lab系统中检测计划状态domain
     */
    static String OFFER_STATE = 'OFFER_STATE'
    /**
     * lab系统中其他费用domain
     */
    static String FEE_TYPE = 'FEE_TYPE'
    /**
     * lab系统中联合泰泽的Id
     */
    static String TAIZE_BUS_CLIENT_ID_IN_LAB = "-1"
    /**
     * 分配角色 项目编写人
     */
    static String TASK_ASSIGN_PROJECT_EDITOR = "项目编写人"
    /**
     * 分配角色 主持编制人
     */
    static String TASK_ASSIGN_MAIN_EDITOR = "主持编制人"

    /**********************DomainCode*******************************/
    /**
     * 轮审人员
     */
    static String LSRY = 'LSRY'
    /**
     * 项目归档人员
     */
    static String XMGDRY = 'XMGDRY'
    /***********************TABLENAME*********************************/
    static String DOMAIN_EIA_PROJECT = 'EiaProject'
    static String DOMAIN_EIA_CONTRACT = 'EiaContract'
    static String DOMAIN_EIA_CONTRACT_LOG = 'EiaContractLog'
    static String DOMAIN_EIA_CERT = "EiaCert"
    static String DOMAIN_EIA_STAMP = "EiaStamp"
    static String DOMAIN_EIA_ENV_PROJECT = 'EiaEnvProject'
    static String DOMAIN_EIA_PUB_PROJECT = 'EiaPubProject'
    static String DOMAIN_EIA_PLAN_SHOW = 'EiaPlanShow'
    static String DOMAIN_EIA_AREA_INFO = 'EiaAreaInfo'
    static String DOMAIN_EIA_SENS_AREA = 'EiaSensArea'
    static String DOMAIN_EIA_LAB_OFFER = 'EiaLabOffer'
    static String DOMAIN_EIA_PROJECT_PLAN = 'EiaProjectPlan'
    static String DOMAIN_EIA_TASK = 'EiaTask'
    static String DOMAIN_EIA_CLIENT = 'EiaClient'
    static String DOMAIN_EIA_OFFER = 'EiaOffer'
    static String DOMAIN_EIA_WLOW_BUSI = 'EiaWorkFlowBusi'
    static String DOMAIN_EIA_WLOW_BUSI_LOG = 'EiaWorkFlowBusiLog'
    /**
     * 市区domain
     */
    static String PROVINCE_CITY = "PROVINCE_CITY"
    /**
     * 区域概况数据类别domain
     */
    static String DATA_CURRENCY = "DATA_CURRENCY"
    /**
     * 环境敏感区数据类别domain
     */
    static String SENSITIVE_AREA_TYPE = "SENSITIVE_AREA_TYPE"
    /**
     * 审批公示tableName
     */
    static String EIA_PUB_PROJECT_TABLE_NAME = "EiaPubProject"
    /**
     * 区域概况tableName
     */
    static String EIA_AREA_INFO_TABLE_NAME = "EiaAreaInfo"
    /**
     * 环境敏感区tableName
     */
    static String EIA_SENS_AREA_TABLE_NAME = "EiaSensArea"
    /**
     * 共享查询tableName
     */
    static String EIA_DATA_SHARE_TABLE_NAME = "EiaDataShare"
    /**
     * 文档下载权限domain
     */
    static String FILE_DOWN_IPACCESS = "FILE_DOWN_IPACCESS"
    /**
     * 区域情况——污水处理厂对应的填写项
     */
    static List<String> WSCLCLIST = ["coordinateE", "coordinateN", "handAbility", "treatProcess", "execStandard", "projectMemo"]
    /**
     * 区域情况——规划环评对应的填写项
     */
    static List<String> GHHPLIST = ["eiaUnit", "approveDept", "approveNo"]
    /**
     * 区域情况——声功能区划对应的填写项
     */
    static List<String> SGNQHLIST = ["planType"]
    /**
     * 数据来源-联合赤道
     */
    static String DATA_SOURCE_LH = '联合项目'
    /**
     * 数据来源-联合赤道
     */
    static String DATA_SOURCE_CHIDAO = '联合赤道'
    /**
     * 数据来源-审批公示
     */
    static String DATA_SOURCE_SHENPI = '审批公示'
    /**
     * 数据来源-验收公示
     */
    static String DATA_SOURCE_YANSHOU = '验收公示'
    /**
     * 数据来源-联合泰泽
     */
    static String DATA_SOURCE_TAIZE = '联合泰泽'
    /**
     * 审批年度domain
     */
    static String PUBLICTY_YEAR = 'PUBLICTY_YEAR'
    /**
     * 建设性质domain
     */
    static String NATURE_CONSTRUCTION = 'NATURE_CONSTRUCTION'
    /**
     * 文件类型domain
     */
    static String FILE_UPLOAD_TYPE = 'FILE_UPLOAD_TYPE'
    /**
     * 审批部门domain
     */
    static String CHECK_ORG = 'CHECK_ORG'
    /**
     * 部门总经理角色
     */
    static String JOB_RATING_ROLE = '部门总经理'
    /**
     * 部门总经理助理
     */
    static String JOB_RATING_ASS_ROLE = '部门总经理助理'
    /**
     * 部门副总经理
     */
    static String JOB_RATING_MANAGER_ROLE = '部门副总经理'
    /**
     * 职员
     */
    static String JOB_RATING_PM_ROLE = '部门职员'
    /**
     * 机构总经理
     */
    static String JOB_RATING_GMANAGER_ROLE = '机构总经理'
    /**
     * 系统管理员
     */
    static String JOB_RATING_ADMIN = '超级系统管理员'

    /********************************文件上传******************************/
    /**
     * 文件显示路径
     */
    static String AUTH_FILE_URL_PATH = Holders.getConfig().getProperty('webroots.authFileUrlPath')
    static String EIA_FILE_URL_PATH = Holders.getConfig().getProperty('webroots.eiaFileUrlPath')
    /**
     * 文件上传类型
     */
    static String UPLOADTYPE_EXPLORE_IMAGE = 'exploreImage'
    /**
     * 组织结构根节点ID
     */
    static Long ORG_ROOT = 2
    /**
     * 有对应封皮的项目文件类型
     */
    static List<String> MATCH_EXPORT_FILE_TYPE_CODE_LIST = ["EPC_HP_YBHPBG", "EPC_HP_TSHPBG", "EPC_HY_QGFZHXL", "EPC_HY_HGSHYYL", "EPC_HY_YJJDL", "EPC_HY_JCHDL", "EPC_HY_NLSLL", "EPC_HY_CJL", "EPC_HY_JTYSL", "EPC_HY_SHQYL",
                                                            "EPC_HY_HYGCL", "EPC_HY_SBDJGDTXL", "EPC_HY_HGYL", "EPC_HB_HJYXBCFX", "EPC_YS_JGHJBHYSJC", "EPC_YS_JGHJBHYSDC", "EPC_XZ_XZPG", "EPC_ST_STLZBG", "EPC_YA_YJYAWJ",
                                                            "EPC_PF_DBPF", "EPC_QT_QT", "ESE_QT_QT", "GREEN_QT_QT", "ESE_LZ_NPBG", "ESE_LZ_QJSCSH"]
    /**
     * 环评报告表封皮
     */
    static List<String> EXPORT_PROVE_FILE_TYPE_CODE_FROM_LIST = ["EPC_HP_YBHPBG", "EPC_HP_TSHPBG"]
    /**
     * 环评报告书封皮
     */
    static List<String> EXPORT_PROVE_FILE_TYPE_CODE_BOOK_LIST = ["EPC_HY_QGFZHXL", "EPC_HY_HGSHYYL", "EPC_HY_YJJDL", "EPC_HY_JCHDL", "EPC_HY_NLSLL", "EPC_HY_CJL", "EPC_HY_JTYSL", "EPC_HY_SHQYL", "EPC_HY_HYGCL", "EPC_HY_SBDJGDTXL", "EPC_HY_HGYL"]
    /**
     * 环境影响补充分析封皮
     */
    static List<String> EXPORT_PROVE_FILE_TYPE_CODE_HB_LIST = ["EPC_HB_HJYXBCFX"]
    /**
     * 竣工环境保护验收监测封皮
     */
    static List<String> EXPORT_PROVE_FILE_TYPE_CODE_YS_JC = ["EPC_YS_JGHJBHYSJC"]
    /**
     * 竣工环境保护验收调查封皮
     */
    static List<String> EXPORT_PROVE_FILE_TYPE_CODE_YS_DC = ["EPC_YS_JGHJBHYSDC"]
    /**
     * 现状评估报告封皮模板
     */
    static List<String> EXPORT_PROVE_FILE_TYPE_CODE_XZ_LIST = ["EPC_XZ_XZPG"]
    /**
     * 生态论证封皮模板
     */
    static List<String> EXPORT_PROVE_FILE_TYPE_CODE_ST_LIST = ["EPC_ST_STLZBG"]
    /**
     * 紧急预案报告封皮模板
     */
    static List<String> EXPORT_PROVE_FILE_TYPE_CODE_YA_LIST = ["EPC_YA_YJYAWJ"]
    /**
     * 全面达标排放封皮模板
     */
    static List<String> EXPORT_PROVE_FILE_TYPE_CODE_PF_LIST = ["EPC_PF_DBPF"]
    /**
     * 节能评估报告封皮模板
     */
    static List<String> EXPORT_PROVE_FILE_TYPE_CODE_LZ_LIST = ["ESE_LZ_NPBG"]
    /**
     * 清洁生产审核报告封皮模板
     */
    static List<String> EXPORT_PROVE_FILE_TYPE_CODE_QJSCSH_LIST = ["ESE_LZ_QJSCSH"]
    /**
     * 项目文件类型为其它的封皮模板
     */
    static List<String> EXPORT_PROVE_FILE_TYPE_CODE_QT_LIST = ["EPC_QT_QT", "ESE_QT_QT", "GREEN_QT_QT"]
    /**
     * 机构名称为了导模板用
     */
    static String EIA_LHCIS_ORG = "天津市联合泰泽环境科技发展有限公司"
    /**
     * 合同受托方——联合赤道
     */
    static String CONTRACT_TRUST_CHIDAO = "联合赤道"
    /**
     * 报价原始状态
     */
    static int OFFER_ORIGINAL_STATE = 0
    /**
     * 报价不生成流程，待生成报价的状态
     */
    static int OFFER_NO_FLOW = -1
    /**
     * 报价生成流程，暂时不能生成报价的状态
     */
    static int OFFER_HAVE_FLOW = 1
    /**
     * 月考部门选择
     */
    static String EVAL_ORG_NAME = "EVAL_ORG_NAME"

    /**
     * 月考打分角色
     */
    static List<String> JOB_RATING_JG_ROLE_LIST = ["机构总经理", "部门总经理", "机构总经理", "财务行政助理", "合规助理", "分公司负责人"]

    /**
     * 印章公司并称Domain
     */
    static String DOMAIN_STAMP_COMPANY = 'STAMP_COMPANY'
    /**
     * 是否有财务预计统计功能（用于配置机构）
     */
    static String EXPECT_ACCOUNT = 'EXPECT_ACCOUNT'
    /**
     * 行政部经理信息
     */
    static String AdminManagerName = '李晨'
    static String AdminManagerId = '47'
    static String AdminManagerSign = '/authStaff/47/sign/112/112_2018-06-11.bmp'
}
