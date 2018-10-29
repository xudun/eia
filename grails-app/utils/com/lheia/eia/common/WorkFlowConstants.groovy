package com.lheia.eia.common

class WorkFlowConstants {
    /***
     * 工作流配置启用
     */
    static String WORKFLOW_START                      = "start"
    /***
     * 工作流配置进行中
     */
    static String WORKFLOW_UNDER_WAY                  = "doing"
    /***
     * 工作流结束
     */
    static String WORKFLOW_END                        = "end"
    /**
     * 工作流终止
     */
    static String WORKFLOW_HALT                       = 'halt'
    /***
     * 无用户信息提示
     */
    static String MSG_NO_STAFF_INFO                   = "session中无用户信息"
    /***
     * 内审流程
     */
    static String PROJECT_EXPLORE_WORK_FLOW            = "PROJECT_EXPLORE_WORK_FLOW"
    static Map    PROJECT_EXPLORE_WORK_FLOW_MAP        = ["HBFGS": PROJECT_EXPLORE_WORK_FLOW_HBFGS, "HNFGS": PROJECT_EXPLORE_WORK_FLOW_HNFGS, "SDFGS": PROJECT_EXPLORE_WORK_FLOW_SDFGS,"YNFGS":PROJECT_EXPLORE_WORK_FLOW_YNFGS]
    static String PROJECT_EXPLORE_WORK_FLOW_HBFGS      = "PROJECT_EXPLORE_WORK_FLOW_HBFGS"
    static String PROJECT_EXPLORE_WORK_FLOW_HNFGS      = "PROJECT_EXPLORE_WORK_FLOW_HNFGS"
    static String PROJECT_EXPLORE_WORK_FLOW_SDFGS      = "PROJECT_EXPLORE_WORK_FLOW_SDFGS"
    static String PROJECT_EXPLORE_WORK_FLOW_YNFGS      = "PROJECT_EXPLORE_WORK_FLOW_YNFGS"
    /***
     * 内审节点:助理审批
     */
    static String NODE_CODE_ZLSP      = "ZLSP"
    static String NODE_NAME_ZLSP      = "助理审批"
    /***
     * 内审节点:部门经理审批
     */
    static String NODE_CODE_BMJLSP      = "BMJLSP"
    static String NODE_NAME_BMJLSP      = "部门经理审批"
    /***
     * 内审节点:分公司负责人审批
     */
    static String NODE_CODE_FGSFZRSP      = "FGSFZRSP"
    static String NODE_NAME_FGSFZRSP      = "分公司负责人审批"
    /***
     * 内审节点:分公司董事长审批
     */
    static String NODE_CODE_FGSDSZSP      = "FGSDSZSP"
    static String NODE_NAME_FGSDSZSP      = "分公司董事长审批"
    /**
     * 内审流程开始节点
     */
    static String PROJECT_EXPLORE_WORK_FLOW_START_NODE = "CJNSD"
    /**
     * 总经理审批合同工作流
     */
    static String CONTRACT_WORK_FLOW_ZJL              = "CONTRACT_WORK_FLOW_ZJL"
    static String CONTRACT_WORKFLOW                   = 'CONTRACT_WORK_FLOW'
    /**
     * 合同中止流程
     */
    static String HALT_CONTRACT_WORKFLOW              = 'HALT_CONTRACT_WORK_FLOW'
    static String CONTRACT_WORKFLOW_START_NODE        = "HTCJ"
    static String CHANGE_CONTRACT_WORKFLOW_START_NODE = "HTCJ"
    /**
     * 总经理审批报价工作流
     */
    static String OFFER_WORK_FLOW_ZJL                 = "OFFER_WORK_FLOW_ZJL"
    /**
     * 报价工作流
     */
    static String OFFER_WORKFLOW                      = 'OFFER_WORK_FLOW'
    /**
     * 报价流程开启节点
     */
    static String OFFER_WORKFLOW_START_NODE           = 'BJCJ'
    /**
     * 资质工作流配置id
     */
    static Long   CERT_WORKFLOW_ID                    = 2
    /**
     * 权限类型:权限编码
     */
    static String AUTH_TYPE_AUTH_CODE                 = "AUTH_CODE"
    /**
     * 权限类型：用户编码
     */
    static String AUTH_TYPE_USER_CODE                 = "USER_CODE"
    /**
     * 流程编码：资质流程编码
     */
    static String CERT_APPLY_WORK_FLOW                = "CERT_APPLY_WORK_FLOW"
    /**
     * 节点：现场勘察
     */
    static String NODE_NAME_XCKC                      = "现场勘察"
    static String NODE_CODE_XCKC                      = "XCKC"
    /**
     * 节点：检测方案
     */
    static String NODE_NAME_JCFA                      = "检测方案"
    static String NODE_CODE_JCFA                      = "JCFA"
    /**
     * 节点：编制完成
     */
    static String NODE_NAME_BZWC                      = "编制完成"
    static String NODE_CODE_BZWC                      = "BZWC"
    /**
     * 节点：一审
     */
    static String NODE_NAME_YS                        = "一审"
    static String NODE_CODE_YS                        = "YS"
    /**
     * 节点：一审编制
     */
    static String NODE_NAME_YSBZ                      = "一审编制"
    static String NODE_CODE_YSBZ                      = "YSBZ"
    /**
     * 节点：二审
     */
    static String NODE_NAME_ES                        = "二审"
    static String NODE_CODE_ES                        = "ES"
    /**
     * 节点：二审编制
     */
    static String NODE_NAME_ESBZ                      = "二审编制"
    static String NODE_CODE_ESBZ                      = "ESBZ"
    /**
     * 节点：三审
     */
    static String NODE_NAME_SS                        = "三审"
    static String NODE_CODE_SS                        = "SS"
    /**
     * 节点：报告总结
     */
    static String NODE_NAME_BGZJ                      = "报告总结"
    static String NODE_CODE_BGZJ                      = "BGZJ"
    /**
     * 节点：三审编制
     */
    static String NODE_NAME_SSBZ                      = "三审编制"
    static String NODE_CODE_SSBZ                      = "SSBZ"
    /**
     * 节点：轮审
     */
    static String NODE_NAME_LS                        = "轮审"
    static String NODE_CODE_LS                        = "LS"
    /**
     * 节点：轮审编制
     */
    static String NODE_NAME_LSBZ                      = "轮审编制"
    static String NODE_CODE_LSBZ                      = "LSBZ"
    /**
     * 节点：技术评审会
     */
    static String NODE_NAME_JSPSH                     = "函审/上会"
    static String NODE_CODE_JSPSH                     = "JSPSH"
    /**
     * 节点：报批版上报
     */
    static String NODE_NAME_BPBSB                     = "报批版上报"
    static String NODE_CODE_BPBSB                     = "BPBSB"
    /**
     * 节点：项目归档
     */
    static String NODE_NAME_XMGD                      = "项目归档"
    static String NODE_CODE_XMGD                      = "XMGD"
    /**
     * 节点：结束
     */
    static String NODE_NAME_END                       = "结束"
    static String NODE_CODE_END                       = "END"
    /**
     * 流程中止
     */
    static String NODE_NAME_LCZZ                      = '流程终止'
    static String NODE_CODE_LCZZ                      = 'LCZZ'

    /***************印章流程******************/
    /**
     * 业务用章申请流程
     */
    static String STAMP_WORK_FLOW_BUSS        = 'STAMP_WORK_FLOW_BUSS'
    /**
     * 非业务用章申请流程
     */
    static String STAMP_WORK_FLOW_NOBUSS      = 'STAMP_WORK_FLOW_NOBUSS'
    /**
     * 分公司用章申请流程
     */
    static String STAMP_WORK_FLOW_BRANCH      = 'STAMP_WORK_FLOW_BRANCH'
    /**
     * 节点 部门审核
     */
    static String STAMP_WORK_FLOW_NODE_BMSH   = 'BMSH'
    /**
     * 节点 总经理审核
     */
    static String STAMP_WORK_FLOW_NODE_ZJLSH  = 'ZJLSH'
    /**
     * 节点 行政经理审核
     */
    static String STAMP_WORK_FLOW_NODE_XZJLSH = 'XZJLSH'
    /**
     * 节点 行政盖章
     */
    static String STAMP_WORK_FLOW_NODE_XZGZ   = 'XZGZ'
    /***************资质流程******************/
    /**
     * 出资质送审流程
     */
    static String CERT_WORK_FLOW_CZZSSLC      = "CERT_WORK_FLOW_CZZSSLC"
    /**
     * 资质流程节点 资质申请
     */
    static String CERT_WORK_FLOW_NODE_ZZSQ    = "ZZSQ"
    /**
     * 资质流程节点 出具资质
     */
    static String CERT_WORK_FLOW_NODE_CJZZ    = "CJZZ"
    /**
     * 资质流程节点 报告上传
     */
    static String CERT_WORK_FLOW_NODE_BGSC    = "BGSC"
    /**
     * 资质流程节点 报告上传
     */
    static String CERT_WORK_FLOW_NODE_DYBG    = "DYBG"
    /**
     * 资质流程节点 合规审核
     */
    static String CERT_WORK_FLOW_NODE_HGSH    = "HGSH"
    /**
     * 资质流程节点 财务审核
     */
    static String CERT_WORK_FLOW_NODE_CWSH    = "CWSH"
    /**
     * 资质流程节点 总经理审核
     */
    static String CERT_WORK_FLOW_NODE_ZJLSH   = "ZJLSH"
    /**
     * 资质流程节点 部门审核
     */
    static String CERT_WORK_FLOW_NODE_BMJLSH  = "BMJLSH"
    /**
     * 出资质报批流程
     */
    static String CERT_WORK_FLOW_CZZBPLC      = "CERT_WORK_FLOW_CZZBPLC"
    /**
     * 无资质送审流程
     */
    static String CERT_WORK_FLOW_WZZSSLC      = "CERT_WORK_FLOW_WZZSSLC"
    /**
     * 无资质报批流程
     */
    static String CERT_WORK_FLOW_WZZBPLC      = "CERT_WORK_FLOW_WZZBPLC"

    /***************合同流程******************/
    /**
     * 合同流程节点 部门审核
     */
    static String CONTRACT_WORK_FLOW_NODE_BMSH = "BMSH"
    /**
     * 合同流程节点 合同盖章
     */
    static String CONTRACT_WORK_FLOW_NODE_HTGZ = "HTGZ"

    /***************监测方案流程******************/
    /**
     * 监测方案工作流
     */
    static String       LAB_OFFER_WORK_FLOW           = 'LAB_OFFER_WORK_FLOW'
    /**
     * 监测方案流程开启节点
     */
    static String       LAB_OFFER_WORKFLOW_START_NODE = 'YXBJ'
    /**
     * 监测方案中止流程后需要保留的字段
     */
    static List<String> LAB_OFFER_NEED_DATA           = ['wtClientId', 'wtClientName', 'wtClientAddr', 'wtClientContact', 'wtClientPhone', 'sjClientId', 'sjClientName',
                                                         'sjClientAddr', 'sjClientContact', 'sjClientPhone', 'projectType', 'sampleType', 'clientAddr', 'invoiceComp', 'bankAccount',
                                                         'taxCode', 'openAccountBank', 'clientPhone', 'clientNameCn', 'clientAddrCn', 'contractTax', 'defReportTemp',
                                                         'ifTestComplete', 'reportTemp', 'labClientFinanceInfoId', 'labClientContractInfoId', 'inputDept', 'inputDeptId',
                                                         'inputDeptCode', 'inputUser', 'inputUserId', 'ifYxTest', 'offerState', 'contractDiscount', 'ifDel']
    /**印章审核流程节点**/
    static String       STAMP_NODE_CODE_XZJLSH        = 'XZJLSH'
    static String       STAMP_NODE_NAME_XZJLSH        = '行政经理审核'

}
