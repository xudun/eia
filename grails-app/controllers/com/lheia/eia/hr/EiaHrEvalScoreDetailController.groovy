package com.lheia.eia.hr

import com.lheia.eia.common.GeneConstants
import com.lheia.eia.common.HttpMesConstants
import com.lheia.eia.common.HttpUrlConstants
import com.lheia.eia.tools.HttpConnectTools
import grails.converters.JSON
import org.grails.web.json.JSONArray
import org.grails.web.json.JSONObject

class EiaHrEvalScoreDetailController {
    def eiaHrEvalScoreDetailService
    def index() {

    }
    /**
     * 判断是否有公司职员
     */
    def isHaveEmp(){
        def eiaHrEval = EiaHrEval.findByIdAndIfDel(params.long("eiaHrEvalId"),false)
        def staffNotEvalList = []
        def param = [:]
        param.orgId = eiaHrEval.orgId.toString()
        /**
         * 获取不参与打分人员
         */
        def staffNotEvalJson = HttpConnectTools.getResponseJson(HttpUrlConstants.EIA_AUTH_NOT_EVAL_STAFF_INFO, param)
        JSONArray staffNotEvalnfo = new JSONArray(staffNotEvalJson)
        for (int i = 0; i < staffNotEvalnfo.length(); i++) {
            JSONObject staffInfo= staffNotEvalnfo.getJSONObject(i);
            def staffId = staffInfo.getString("id")
            staffNotEvalList.add(Long.parseLong(staffId))
        }
        /**
         * 不参与打分人员提示
         */
        for (String temp : staffNotEvalList) {
            if(temp.equals((session.staff.staffId).toString())){
                render "isNotEval"
                return
            }
        }
        staffNotEvalList.add(session.staff.staffId)
        param.evalNot =   staffNotEvalList.toString()

        def staffJson = HttpConnectTools.getResponseJson(HttpUrlConstants.EIA_AUTH_ORG_STAFF_INFO, param)
        /**
         * 返回一个数组[]，判断是否为空，所以长度为2,则为空
         */
        if(HttpMesConstants.MSG_DATA_NULL.equals(staffJson)){
            render "isHave"
            return
        }
        render([success:true] as JSON)
    }
    /**
     * 判断是否有助理角色
     */
    def isHaveAss(){
        def eiaHrEval = EiaHrEval.findByIdAndIfDel(params.long("eiaHrEvalId"),false)
        def staffNotEvalList = []

        def param = [:]
        param.orgId = eiaHrEval.orgId.toString()
        /**
         * 获取不参与打分人员
         */
        def staffNotEvalJson = HttpConnectTools.getResponseJson(HttpUrlConstants.EIA_AUTH_NOT_EVAL_STAFF_INFO, param)
        JSONArray staffNotEvalnfo = new JSONArray(staffNotEvalJson)
        for (int i = 0; i < staffNotEvalnfo.length(); i++) {
            JSONObject staffInfo= staffNotEvalnfo.getJSONObject(i);
            def staffId = staffInfo.getString("id")
            staffNotEvalList.add(Long.parseLong(staffId))
        }
        for (String temp : staffNotEvalList) {
            if(temp.equals((session.staff.staffId).toString())){
                render "isNotEval"
                return
            }
        }
        staffNotEvalList.add(session.staff.staffId)
        /**
         * 判断是否有打分权限，用于多角色切换中
         */
        def eiaHrOrgStaffEval = EiaHrOrgStaffEval.findAllByIfDelAndOrgId(false,eiaHrEval.orgId)
        if(eiaHrOrgStaffEval.size()>0){
            eiaHrOrgStaffEval.each{
                staffNotEvalList.add(it.staffId)
            }
        }
        param.evalNot =   staffNotEvalList.toString()
        param.ass = '1'

        /**
         * 获取打分人员
         */
        def staffJson = HttpConnectTools.getResponseJson(HttpUrlConstants.EIA_AUTH_ORG_STAFF_INFO, param)
        /**
         * 返回一个数组[]，判断是否为空，所以长度为2,则为空
         */
        if(HttpMesConstants.MSG_DATA_NULL.equals(staffJson)){
            render "isHave"
            return
        }
        render([success:true] as JSON)
    }
    /**
     *  员工考核互评
     */
    def eiaHrEvalEmpToEmpInput(){
        [eiaHrEvalId:params.eiaHrEvalId]
    }
    /**
     *  员工助理互评
     */
    def eiaHrEvalEmpToAssInput(){
        [eiaHrEvalId:params.eiaHrEvalId]
    }
    /**
     *  总经理给员工打分
     */
    def eiaHrEvalDMToEmpInput(){
        [eiaHrEvalId:params.eiaHrEvalId]
    }
    /**
     * 员工打分
     */
    def eiaHrEvalScoreDetailSave(){
        def eiaHrEvalScoreDetail = eiaHrEvalScoreDetailService.eiaHrEvalScoreDetailSave(params,session)
        if (eiaHrEvalScoreDetail) {
            render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SAVE_OK, data: eiaHrEvalScoreDetail] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
        }
    }
    /**
     * 员工给员工打分
     * @return
     */
    def eiaHrEvalEmpEdit(){
        def eiaHrEval = EiaHrEval.findByIdAndIfDel(params.long("eiaHrEvalId"),false)
        /**
         * 判断登录用户是什么角色
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
         * 调用方法取各部门下的人员
         */
        def empRatingEmp = eiaHrEvalScoreDetailService.empRatingEmpInput(params,session,roleName)
        if (empRatingEmp) {
            render(empRatingEmp as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
    /**
     * 查看全部的人员打分
     */
    def empRatingEmpDetail(){
        def eiaHrEval = EiaHrEval.findByIfDel(false)
        /**
         * 判断登录用户是什么角色
         */
        def param = [:]
        param.orgId = eiaHrEval.orgId.toString()
        param.staffId = session.staff.staffId.toString()
        def staffJson = HttpConnectTools.getResponseJson(HttpUrlConstants.EIA_AUTH_ORG_STAFF_ROLE_INFO, param)
        JSONArray staffInfo = new JSONArray(staffJson)
        def roleName
        for (int i = 0; i < staffInfo.length(); i++) {
            JSONObject staffJsonJect = staffInfo.getJSONObject(i);
            roleName = staffJsonJect.getString("role_name")
        }
        /**
         * 调用方法取各部门下的人员
         */
        def empRatingEmp = eiaHrEvalScoreDetailService.empRatingEmpDetail(params,session,roleName)
        /**
         * 判断角色和点击类型
         */
        if(GeneConstants.JOB_RATING_PM_ROLE.equals(roleName) && eiaHrEval.jobRatingType.equals(GeneConstants.EIA_HR_EVAL_EMP)){
            /**
             * 不同角色之前打分，根据角色不同调用的取值方法不同
             */
            empRatingEmp
        }
    }
    /**
     * 判断是否打分，总经理打分以后才能进行评语
     */
    def isEiaHrEvalScore(){
        if(params.eiaHrEvalScoreDetailId){
            def eiaHrEvalScoreDetail = EiaHrEvalScoreDetail.findAllByIfDelAndId(false,params.long("eiaHrEvalScoreDetailId"))
            if(eiaHrEvalScoreDetail.size()<=0){
                render "isNotScore"
                return
            }
        }
        render([success:true] as JSON)
    }
    /**
     * 员工评语录入
     */
    def eiaHrEvalComment(){
        [eiaHrEvalScoreDetailId:params.eiaHrEvalScoreDetailId]
    }
    /**
     * 查看员工评语
     */
    def eiaHrEvalCommentDetail(){
        [staffId:params.staffId]
    }
    /**
     * 员工评语展示
     */
    def eiaHrcomment(){
        if(params.long("eiaHrEvalScoreDetailId")){
            def eiaHrEvalScoreDetail = EiaHrEvalScoreDetail.findByIfDelAndId(false,params.long("eiaHrEvalScoreDetailId"))
            if (eiaHrEvalScoreDetail) {
                def map = [:]
                map["leaderComments"] = eiaHrEvalScoreDetail.leaderComments
                render(map as JSON)
            } else {
                render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
            }
        }
        if(params.long("eiaHrEvalScoreId")){
            def eiaHrEvalScoreDetail = EiaHrEvalScore.findByIfDelAndId(false,params.long("eiaHrEvalScoreId"))
            if (eiaHrEvalScoreDetail) {
                def map = [:]
                map["leaderComments"] = eiaHrEvalScoreDetail.leaderComments
                render(map as JSON)
            } else {
                render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
            }
        }

    }
    /**
     * 员工评语保存
     */
    def leaderCommentsSave(){
        def eiaHrEvalScoreDetail = eiaHrEvalScoreDetailService.leaderCommentsSave(params)
        if (eiaHrEvalScoreDetail) {
            render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SAVE_OK, data: eiaHrEvalScoreDetail] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
        }
    }
    /**
     * 员工打分详细表
     */
    def eiaHrEvalEmpDetail(){
        [staffId:params.staffId]
    }
    /**
     * 总经理或管理员点击员工出现该员工详细打分信息
     */
    def getEiaHrEvalEmpDetailData(){
        def dataMap = eiaHrEvalScoreDetailService.eiaHrEvalEmpDetailQueryPage(params,session)
        if (dataMap) {
            render([code: HttpMesConstants.CODE_OK, count: dataMap.total, data: dataMap.data] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
    /**
     *  经理给助理打分
     */
    def eiaHrEvalDMToAssInput(){}
    /**
     * 查看员工给助理打分情况
     */
    def eiaHrEvalAssDetail(){
        [staffId:params.staffId]
    }
    /**
     * /**
     * 判断人员是否打分,打分以后才能提交打分
     * @return
     */
    def isGrade(){
        def eiaHrEval =  EiaHrEval.findByIdAndIfDel(params.long("eiaHrEvalId"),false)
        def eiaHrEvalScoreDetailList =  EiaHrEvalScoreDetail.findAllByIfDelAndInputDeptIdAndAssessmentMonthAndJobRatingTypeAndInputUserId(false,eiaHrEval.orgId,eiaHrEval?.assessmentMonth,eiaHrEval?.jobRatingType,session.staff.staffId)
        if(eiaHrEvalScoreDetailList.size()<=0){
            render([code: HttpMesConstants.CODE_OK] as JSON)
        }else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
    /**
     * 提交，提交后不能编辑
     */
    def empRatingSubmit(){
        def dataMap = eiaHrEvalScoreDetailService.empRatingSubmit(params,session)
        if(dataMap){
            render([code: HttpMesConstants.CODE_OK] as JSON)
        }else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
    /**
     * 员工考核互评详情,打分以后只能看，不能修改
     */
    def eiaHrEvalEmpToEmpDetail(){}
    /**
     * 员工给助理打分详情，只能看，不能修改
     */
    def eiaHrEvalEmpToAssDetail(){
    }
    /**
     * 经理给员工打分详情
     */
    def eiaHrEvalDMToEmpDetail(){}

    /**
     * 经理给助理打分详情
     */
    def eiaHrEvalDMToAssDetail(){}

    /**
     * 助理得分情况
     */
    def eiaHrEvalAss(){}
}
