package com.lheia.eia.common

import grails.util.Holders

class HttpUrlConstants {
    /**
     * cookie的域
     */
    public static String cookieDomain = Holders.getConfig().getProperty('webroots.cookieDomain')
    /**
     * cookie的路径
     */
    public static String cookiePath = Holders.getConfig().getProperty('webroots.cookiePath')
   /**
     * 跳转地址
     */
    /** 超时 **/
    public static String LOGIN_FORWARD_JUMP = Holders.getConfig().getProperty('webroots.forwardPath.auth') + "login_forward_jump"
    public static String ACCID_FORWARD_JUMP = Holders.getConfig().getProperty('webroots.forwardPath.auth') + "accid_forward_jump"
    public static String EIA_FORWARD_JUMP = Holders.getConfig().getProperty('webroots.forwardPath.eia')
    /**
     * httpclient参数设置
     */
    public static int SOCKET_TIME_OUT = Integer.parseInt(Holders.getConfig().getProperty('webroots.httpClientParams.setSocketTimeout'))
    public static int CONNECT_TIME_OUT = Integer.parseInt(Holders.getConfig().getProperty('webroots.httpClientParams.setConnectTimeout'))
    /**
     * 检查sessionId,并且获取权限
     */
    public static String checkAndGetAuth = Holders.getConfig().getProperty('webroots.contextPath.api') + 'auth/checkAndGetAuth'
    public static String getSingleLoginState = Holders.getConfig().getProperty('webroots.contextPath.api') + "auth/getSingleLoginState"
    /**
     * 获取auth信息机构人员接口
     */
    public static String EIA_AUTH_STAFF_INFO = Holders.getConfig().getProperty('webroots.contextPath.api') +'auth/getEiaOrgStaffTreeList'
    /**
     * 获取authOrg机构部门接口
     */
    public static String EIA_AUTH_ORG_INFO = Holders.getConfig().getProperty('webroots.contextPath.api') +'auth/getEiaOrgTreeList'
    /**
     * 获取authStaff机构人员接口
     */
    public static String SINGLE_EIA_AUTH_STAFF_INFO = Holders.getConfig().getProperty('webroots.contextPath.api') +'auth/getAuthStaffById'
    /**
     * 根据orgId获取父节点
     */
    public static String EIA_GET_PARENT_ORG = Holders.getConfig().getProperty('webroots.contextPath.api') + 'eia/getParentNode'
    /**
     * 关闭页签时
     */
    public static String EIA_GET_CHILD_ORG_CLOSE = Holders.getConfig().getProperty('webroots.contextPath.api') + 'eia/getChildListClose'
    /**
     * 根据orgId获取子节点
     */
    public static String EIA_GET_CHILD_ORG = Holders.getConfig().getProperty('webroots.contextPath.api') + 'eia/getChildList'
    /**
     *
     */
    public static String EIA_GET_CHILD_ORG_DEPT = Holders.getConfig().getProperty('webroots.contextPath.api') + 'eia/getChildListDept'

    /**
     *
     */
    public static String EIA_GET_MY_ORG = Holders.getConfig().getProperty('webroots.contextPath.api')+'eia/getMyOrgList'
    /**
     * 根据orgId获取子节点
     */
    public static String EIA_GET_THIS_ORG = Holders.getConfig().getProperty('webroots.contextPath.api') + 'eia/getThisNode'
    /**
     * 获取Gis项目数据
     */
    static String GET_GIS_PROJECT_MAP=Holders.getConfig().getProperty('webroots.contextPath.api')+'gis/getGisGeoProjectMap'
    /**
     * 获取全有机构名称
     */
    public static String EIA_AUTH_ORG_ALL_INFO = Holders.getConfig().getProperty('webroots.contextPath.api') +'auth/getEiaOrgAllList'
    /**
     * 获取orgId获取下所有的人员
     */
    public static String EIA_AUTH_ORG_STAFF_INFO = Holders.getConfig().getProperty('webroots.contextPath.api') +'auth/getEiaOrgStaffList'
    /**
     * 判断日期是否是工作日的接口
     */
    public static String CHECK_IF_WORK_DAY  = 'http://api.goseek.cn/Tools/holiday?date='
    /**
     * 二维码显示url
     */
    public static String QRCODE_ADDR ='http://lheia.lhcis.com/qrcode.html?qrcode='
    /**
     * 获取LabDomainCode
     */
    public static String LAB_DOMAIN_CODE_LIST_INFO = Holders.getConfig().getProperty('webroots.contextPath.api') + 'lab/getLabDomainCodeList'
    /**
     * 获取lab中的客户信息
     */
    public static String LAB_CLIENT_INFO = Holders.getConfig().getProperty('webroots.contextPath.api') + 'lab/getLabClientDataMap'
    /**
     * 获取lab客户下的联系人List
     */
    public static String LAB_CONTACT_CLIENT_LIST_INFO = Holders.getConfig().getProperty('webroots.contextPath.api') + 'lab/getLabClientContactList'
    /**
     * 获取lab中的客户报告信息
     */
    public static String LAB_CLIENT_REPORT_INFO = Holders.getConfig().getProperty('webroots.contextPath.api') + 'lab/getLabClientReportDataMap'
    /**
     * 获取lab中的客户合同信息
     */
    public static String LAB_CLIENT_CONTRACT_INFO = Holders.getConfig().getProperty('webroots.contextPath.api') + 'lab/getLabClientContractInfoDataMap'
    /**
     * 获取lab中的客户财务信息
     */
    public static String LAB_CLIENT_FINANCE_INFO = Holders.getConfig().getProperty('webroots.contextPath.api') + 'lab/getLabClientFinanceInfoDataMap'
    /**
     * 获取lab检测基质list
     */
    public static String LAB_TEST_BASE_LIST_INFO = Holders.getConfig().getProperty('webroots.contextPath.api') + 'lab/getLabTestBaseList'
    /**
     * 获取lab检测项目list
     */
    public static String LAB_TEST_PARAM_LIST_INFO = Holders.getConfig().getProperty('webroots.contextPath.api') + 'lab/getLabTestParamList'
    /**
     * 获取选择的检测项目下，最高的检测费用
     */
    public static String MAX_SCHEME_MONEY_INFO = Holders.getConfig().getProperty('webroots.contextPath.api') + 'lab/getMaxSchemeMoney'
    /**
     * 检查是否是分包的检测项目
     */
    public static String CHECK_IF_SUB_INFO = Holders.getConfig().getProperty('webroots.contextPath.api') + 'lab/checkIfSub'
    /**
     * 获取lab检测基质
     */
    public static String LAB_TEST_BASE_INFO = Holders.getConfig().getProperty('webroots.contextPath.api') + 'lab/getLabTestBaseDataMap'
    /**
     * 获取lab检测项目
     */
    public static String LAB_TEST_PARAM_INFO = Holders.getConfig().getProperty('webroots.contextPath.api') + 'lab/getLabTestParamDataMap'
    /**
     * 获取lab检测套餐list
     */
    public static String LAB_TEST_PARAM_GROUP_LIST_INFO = Holders.getConfig().getProperty('webroots.contextPath.api') + 'lab/getLabTestParamGroupList'
    /**
     * 获取lab检测套餐
     */
    public static String LAB_TEST_PARAM_GROUP_INFO = Holders.getConfig().getProperty('webroots.contextPath.api') + 'lab/getLabTestParamGroupDataMap'
    /**
     * 获取lab检测套餐包含的检测计划list
     */
    public static String LAB_TEST_CAP_GROUP_LIST_INFO = Holders.getConfig().getProperty('webroots.contextPath.api') + 'lab/getLabTestCapGroupList'
    /**
     * 获取lab其他费用list
     */
    public static String LAB_OTHER_FEE_LIST_INFO = Holders.getConfig().getProperty('webroots.contextPath.api') + 'lab/getLabOtherFeeList'
    /**
     * 提交检测方案到lab
     */
    public static String EIA_LAB_OFFER_SUBMIT = Holders.getConfig().getProperty('webroots.contextPath.api') + 'lab/eiaLabOfferSubmit'
    /**
     * 更新lab的项目信息
     */
    public static String UPDATE_LAB_OFFER_PROJECT_INFO = Holders.getConfig().getProperty('webroots.contextPath.api') + 'lab/updateLabOfferProjectInfo'
    /**
     * 监测方案中止后，删除lab的报价
     */
    public static String DEL_LAB_OFFER = Holders.getConfig().getProperty('webroots.contextPath.api') + 'lab/delLabOffer'
    /**
     * 获取lab能力表信息
     */
    public static String LAB_TEST_CAP_INFO = Holders.getConfig().getProperty('webroots.contextPath.api') + 'lab/getLabTestCapDataMap'
    /**
     * 获取登录用户角色
     */
    public static String EIA_AUTH_ORG_STAFF_ROLE_INFO = Holders.getConfig().getProperty('webroots.contextPath.api') +'auth/getEiaStaffRoleList'
    /**
     * 获取部门直属领导
     */
    public static String EIA_AUTH_ORG_STAFF_LENDER_INFO = Holders.getConfig().getProperty('webroots.contextPath.api') +'auth/getEiaStaffLeaderList'
    /**
     * 获取不参与打分人员
     */
    public static String EIA_AUTH_NOT_EVAL_STAFF_INFO = Holders.getConfig().getProperty('webroots.contextPath.api') +'auth/getEiaNotEvalStaffList'
    /**
     * api流程
     */
    public static String WF_API_URL =  Holders.getConfig().getProperty('webroots.contextPath.wfapi')
    public static String TODOWF = Holders.getConfig().getProperty('webroots.contextPath.wfapi')+'workFlow/todoWorkFlow'
    public static String DONEWF = Holders.getConfig().getProperty('webroots.contextPath.wfapi')+'workFlow/doneWorkFlow'
    public static String OVERWF = Holders.getConfig().getProperty('webroots.contextPath.wfapi')+'workFlow/overWorkFlow'
    /**
     * 审批公示项目提交获取gis信息
     */
    static String GIS_GEO_PUB_QUERY = Holders.getConfig().getProperty('webroots.contextPath.api')+'gis/gisGeoPubQuery'
    static String GIS_GEO_PROJECT_EXPLORE_SAVE = Holders.getConfig().getProperty('webroots.contextPath.api')+'gis/gisGeoProjectExploreSave'
    /**
     * gis绘图跳转路径
     */
     static String GIS_DRAW_PATH = Holders.getConfig().getProperty('webroots.forwardPath.gis')+'gisGeoPub/gisGeoPubIndex'
     static String EXPLORE_DRAW_PATH = Holders.getConfig().getProperty('webroots.forwardPath.gis')

    /**
     * 根据orgId获取父节点(用于财务预计信息统计)
     */
    public static String EIA_EXPECT_CHILD_ORG = Holders.getConfig().getProperty('webroots.contextPath.api') + 'eia/getExpectChildList'

    /**
     * 根据orgId获取父节点(财务预计)
     */
    public static String EIA_EXCEPT_PARENT_ORG = Holders.getConfig().getProperty('webroots.contextPath.api') + 'eia/getExpectParentNode'
    /**
     * 通过staffId查找该员工所在部门信息
     */
    public static String EIA_GET_ORG_BY_STAFF = Holders.getConfig().getProperty('webroots.contextPath.api') + 'auth/getOrgByStaffId'
}
