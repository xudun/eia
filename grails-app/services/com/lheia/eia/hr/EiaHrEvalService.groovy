package com.lheia.eia.hr

import com.lheia.eia.common.FuncConstants
import com.lheia.eia.common.GeneConstants
import com.lheia.eia.common.HttpUrlConstants
import com.lheia.eia.config.EiaDomainCode
import com.lheia.eia.tools.HttpConnectTools
import grails.gorm.transactions.Transactional
import org.grails.web.json.JSONArray
import org.grails.web.json.JSONObject

@Transactional
class EiaHrEvalService {
    /**
     * 初始化数据
     * @return
     */
    def initHrEval() {
        def assessmentMonth
        Calendar cal = Calendar.getInstance();
        String str = String.format("%02d", cal.get(Calendar.MONTH));
        /**
         * 判断是否为新的一年
         */
        if("00".equals(str)){
            str = "12"
            assessmentMonth = (cal.get(Calendar.YEAR)-1)+'年'+str+'月';
        }else{
            assessmentMonth = cal.get(Calendar.YEAR)+'年'+str+'月';
        }
        /**
         * 获取全部机构名称
         */
        def eiaDomainCode =EiaDomainCode.findByDomain(GeneConstants.EVAL_ORG_NAME)
        def param = [:]
        param.orgId = eiaDomainCode?.code.toString()
        def eiaAuthOrgInfo = HttpConnectTools.getResponseJson(HttpUrlConstants.EIA_AUTH_ORG_ALL_INFO, param)
        JSONArray taskRoleExams = new JSONArray(eiaAuthOrgInfo)
        for (int i = 0; i < taskRoleExams.length(); i++) {
            JSONObject jsonTaskAssignUser = taskRoleExams.getJSONObject(i);
            def orgId = jsonTaskAssignUser.getString("id")
            def orgName = jsonTaskAssignUser.getString("orgName")
            def orgCode = jsonTaskAssignUser.getString("orgCode")
            def eiaHrEvalList = EiaHrEval.findAllByIfDelAndAssessmentMonthAndOrgId(false,assessmentMonth,Long.parseLong(orgId))
            if(eiaHrEvalList.size()<=0){
                def jobRatingTypeList = []
                /**
                 * 分别初始化员工和助理
                 */
                jobRatingTypeList.add(GeneConstants.EIA_HR_EVAL_EMP)
                jobRatingTypeList.add(GeneConstants.EIA_HR_EVAL_ASS)
                 for(int j = 0;j<jobRatingTypeList.size();j++){
                     def eiaHrEval =  new EiaHrEval()
                     eiaHrEval.orgId = Long.parseLong(orgId)
                     eiaHrEval.orgName = orgName
                     eiaHrEval.orgCode = orgCode
                     eiaHrEval.assessmentMonth = assessmentMonth
                     eiaHrEval.jobRatingType = jobRatingTypeList.get(j)
                     eiaHrEval.save(flush:true,failOnError:true)
                 }
            }
        }



    }
    /**
     * 员工考核列表
     */
    def eiaHrEvalQueryPage(params,session) {
        int page
        int limit
        if (params.page && params.limit) {
            page = params.int('page') - 1
            limit = params.int('limit')
        }
        def eiaHrEvalDataList = EiaHrEval.createCriteria().list(max: limit, offset: page * limit) {
            /**
             * 查看全部的客户数据
             */
            /**
             * 获取orgCode，分公司要获取ogCode需要截取
             */
            def orgCode = session.staff.orgCode
            if (!session?.staff?.funcCode?.contains(FuncConstants.EIA_XZGL_YDKH_VIEWALL)) {
                /**
                 * 区分生态事业部和分公司，用orgCode区分
                 */
                if(orgCode?.contains("FGS")){
                    orgCode = orgCode.split ("_").getAt(0)+'_'+orgCode.split ("_").getAt(1)+'_'+orgCode.split ("_").getAt(2)
                    like("orgCode",orgCode)
                }else{
                    like("orgCode",orgCode)
                }
                /**
                 * 判断是否有打分权限
                 */
                def eiaHrOrgStaffEval = EiaHrOrgStaffEval.findAllByIfDelAndStaffIdAndOrgCodeLike(false,session.staff.staffId,orgCode)
                if(eiaHrOrgStaffEval.size()>0){
                    eq("id",  Long.parseLong("-1"))
                }
            }

            eq("ifDel", false)
            order("assessmentMonth", "desc")
        }
        def eiaHrEvalData = []
        eiaHrEvalDataList.each {
            def map = [:]
            map.id = it.id
            map.orgName = it.orgName
            map.orgId = it.orgId
            map.orgCode = it.orgCode
            map.assessmentMonth = it.assessmentMonth
            map.jobRatingType = it.jobRatingType
            /**
             * 判断是否提交打分
             */
            def eiaHrEvalScoreDetailList =  EiaHrEvalScoreDetail.findAllByIfDelAndInputDeptIdAndAssessmentMonthAndJobRatingTypeAndInputUserIdAndIfSubmit(false,it.orgId,it?.assessmentMonth,it?.jobRatingType,session.staff.staffId,true)
            if(eiaHrEvalScoreDetailList.size()>0){
                map.isShowGrade = false
            }else {
                map.isShowGrade = true
            }
            /**
             * 判断员工是否有最终得分
             */
            def eiaHrEvalScoreList =  EiaHrEvalScore.findAllByIfDelAndInputDeptIdAndAssessmentMonthAndJobRatingTypeAndStaffId(false,it.orgId,it?.assessmentMonth,it?.jobRatingType,session.staff.staffId)
            if(eiaHrEvalScoreList.size()>0){
                map.isShowScore = true
            }else {
                map.isShowScore = false
            }
            /**
             * 判断登录用户是否在改列表用户中有权限，用于区分一个人员多个部门
             */
            if(session.staff.orgCode.contains(it.orgCode)){
                map.isEvalAll = true
            }else{
                map.isEvalAll = false
            }
            eiaHrEvalData << map
        }
        def dataMap = [:]
        dataMap.data = eiaHrEvalData
        dataMap.total = eiaHrEvalDataList.totalCount
        return dataMap
    }
}
