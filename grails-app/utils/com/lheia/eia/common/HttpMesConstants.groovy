package com.lheia.eia.common

class HttpMesConstants {
    /**
     * response 返回值 成功200,服务器请求失败 401
     */
    static String RESPONSE_SC_OK = '200'
    static String RESPONSE_SC_UNAUTHORIZED = '401'
    /**
     * 前端所需code返回值
     * code:0 成功
     * code:-1 失败
     */
    static int CODE_OK = 0
    static int CODE_FAIL = -1
    /**
     * 前端所需msg返回值
     */
    static String MSG_SAVE_OK = '保存成功'
    static String MSG_SAVE_FAIL = '保存失败'
    static String MSG_DEL_OK = '删除成功'
    static String MSG_DEL_FAIL = '删除失败'
    static String MSG_SUBMIT_OK = '提交成功'
    static String MSG_SUBMIT_FAIL = '提交失败'
    static String MSG_UPLOAD_OK = '上传成功'
    static String MSG_UPLOAD_FAIL = '上传失败'
    static String MSG_DATA_NULL = '无数据'
    static String MSG_DATA_EXIST = '数据已经存在'
    static String MSG_NO_GIS = '地理信息不存在或未绘制空间对象！'
    static String MSG_REVE_MONEY = '开票金额不能大于合同金额，请确认！'
    static String MSG_INCOME_MONEY = '进账金额不能大于合同金额，请确认！'
    static String MSG_APP_NULL = '存在节点无预计时间，请确认'
    static String MSG_PRO_FLOW_NULL = '工作方案尚未提交，工作流未启动'
    static String MSG_FLOW_NULL = '流程尚未提交，工作流未启动'
    static String MSG_CON_FLOW_MFEE_NULL = "无检测费，无法提交"
    static String MSG_CERT_FLOW_SEC_SUB = "该流程已提交，请刷新页面！"
    static String MSG_CERT_NO_CON = "该项目未关联合同，无法显示印章申请单数据"
    static String MSG_CERT_APPROVAL_NO_CON = "该项目未关联合同，无法申请报批！"
    static String MSG_PROJECT_MONEY_IS_NULL = "请填写项目金额，再进行提交"
    static String MSG_PROJECT_EXPLORE_IS_NULL = "请填写现场勘察信息，再进行提交"
    static String MSG_CON_FLOW_DFEE_NULL = "无合同总额，无法提交"
    static String MSG_CON_FLOW_NO_WWHT = "该合同流程需外委合同附件，请上传"
    static String MSG_CON_FLOW_NO_PRO = "该合同尚未关联项目，无法提交"
    static String MSG_CON_FLOW_NO_HSSMB = "该合同流程需合同扫描版附件，请上传"
    static String MSG_CON_FLOW_NO_END = "该合同流程未结束!"
    static String MSG_CON_FLOW_HAVE_DOING = "该合同存在合同变更流程未结束!"
    static String MSG_CON_FLOW_HALT_CHECK = "该合同已存在合同中止流程，无法新增中止!"
    static String MES_DATE_ERROR = '开始日期不能大于结束日期'
    static String MSG_OFFER_PLAN_REPEAT = '套餐包含重复的检测计划'
    static String MSG_EIA_LAB_OFFER_PROJECT_REPEAT = '该项目已生成监测方案，请重新选择项目'
    static String MSG_EIA_LAB_OFFER__NEED_FINISH = '请检查监测方案是否填写完成'
    static String MSG_FCONF_NULL = '无工作流模板，请先配置'
    static String MSG_FCONF_NODE_NULL = '无节点，请添加'
    static String MSG_CERT_FLOW_REPEAT = "该流程已创建，请提交重复资质申请流程！"
    static String MSG_FLOW_HALT_OK = '流程节点终止成功'
    static String MSG_CONTRACT_NO_FEE = '无合同费用或者监测费用无法提交流程'
    static String MSG_PRO_FLOW_STARTED = '项目已经启动流程，不能删除'
    static String MSG_PRO_FLOW_PROCESSED = '该流程已处理，请退出！'
    static String MSG_NO_MATCH_PROVE = '该文件无对应的资质证明！'
    static String MSG_NO_MATCH_COVER = '该文件无对应的封皮！'
    static String MSG_NOT_CHOOSE_PROJECT = '请选择项目并保存，再进行提交'
    /**
     * 客户判重
     */
    static String MSG_ADDCLIENT_FALSE = '添加客户失败，客户信息已存在'
    static String MSG_ADD_CONTACT_FALSE = '添加联系人失败，联系人电话重复'
    /**
     * 开户信息判重
     */
    static String MSG_ADD_INVOICE_FALSE = '添加财务开户信息失败，开户信息重复'
}
