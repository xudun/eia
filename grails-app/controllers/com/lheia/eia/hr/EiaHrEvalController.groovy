package com.lheia.eia.hr

import com.lheia.eia.common.FuncConstants
import com.lheia.eia.common.GeneConstants
import com.lheia.eia.common.HttpMesConstants
import com.lheia.eia.common.HttpUrlConstants
import com.lheia.eia.tools.HttpConnectTools
import grails.converters.JSON
import org.grails.web.json.JSONArray
import org.grails.web.json.JSONObject

class EiaHrEvalController {
    def eiaHrEvalService
    def eiaHrEvalScoreDetailService
    def index() {
      //  eiaHrEvalService.initHrEval()
        eiaHrEvalScoreDetailService.empRatingEmpInput(params,session)

    }
    /**
     * 考核打分列表
     */
    def eiaHrEvalIndex(){
        /**
         * 初始化
         */
        eiaHrEvalService.initHrEval()
        /**
         * 首先判断当前登录
         */
        def param = [:]
        param.orgId = session.staff.orgId.toString()
        param.staffId = session.staff.staffId.toString()
        def staffJson = HttpConnectTools.getResponseJson(HttpUrlConstants.EIA_AUTH_ORG_STAFF_ROLE_INFO, param)
        JSONArray staffInfo = new JSONArray(staffJson)
        def roleName
        for (int i = 0; i < staffInfo.length(); i++) {
            JSONObject staffJsonJect = staffInfo.getJSONObject(i);
            roleName = staffJsonJect.getString("roleName")
        }
        /**
         * 不同的角色显示不同的内容
         */
        def roleNameShow = ""
        /**
         * 机构考核人员按钮显示，只能是管理员，总经理，部门经理才能显示
         */
        def depEvalAllList = []
        depEvalAllList = GeneConstants.JOB_RATING_JG_ROLE_LIST
        def isShowJobRating = false
        if(GeneConstants.JOB_RATING_ADMIN.equals(roleName)){
            /**
             * 管理员登录
             */
            roleNameShow = GeneConstants.JOB_RATING_ADMIN
            isShowJobRating = true
        }else if(GeneConstants.JOB_RATING_GMANAGER_ROLE.equals(roleName)){
            /**
             * 总经理
             */
            roleNameShow = GeneConstants.JOB_RATING_GMANAGER_ROLE
            isShowJobRating = true
        }else  if(depEvalAllList.contains(roleName)){
            /**
             * 部门经理登录
             */
            roleNameShow = GeneConstants.JOB_RATING_ROLE
            isShowJobRating = true
        }else{
            /**
             * 员工、助理、副总经理
             */
            roleNameShow = GeneConstants.JOB_RATING_PM_ROLE
            /**
             * 有查看全部权限的人就能看的最终得分
             */
            if (session?.staff?.funcCode?.contains(FuncConstants.EIA_XZGL_YDKH_VIEWALL)) {
                isShowJobRating = true
            }
        }
        /**
         * 下面分为两种情况，如果不是分公司则直接取部门，如果是分公司，则下面的子公司合并成一个分公司显示
         */
        def orgId = -1
        def eiaHrEvalInfo = EiaHrEval.findByIfDelAndOrgCode(false,session.staff.orgCode)
        if(eiaHrEvalInfo){
            orgId = eiaHrEvalInfo?.orgId
        }else {
            def eiaHrEval = EiaHrEval.findAllByIfDelAndJobRatingType(false,GeneConstants.EIA_HR_EVAL_EMP)
            eiaHrEval.each{
                if(session.staff.orgCode.contains(it.orgCode)){
                    orgId = it.orgId
                }
            }
        }
        [roleNameShow:roleNameShow,isShowJobRating:isShowJobRating,orgId:orgId,orgCode:session.staff.orgCode]
    }
    /**
     *
     * 员工考核列表
     */
    def getEiaHrEvalDataList(){
        def dataMap = eiaHrEvalService.eiaHrEvalQueryPage(params, session)
        if (dataMap) {
            render([code: HttpMesConstants.CODE_OK, count: dataMap.total, data: dataMap.data] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
    /**
     *  员工给助理打分
     */
    def eiaHrEvalEmpToAssInput(){}

    /**
     *  经理给员工打分
     */
    def eiaHrEvalDMToEmpInput(){}

    /**
     * 部门经理修改助理得分
     */
    def eiaHrEvalAssEdit(){}

    /**
     * 领导评语
     */
    def eiaHrEvalComment(){}

}
