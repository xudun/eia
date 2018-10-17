package com.lheia.eia.hr

import com.lheia.eia.common.FuncConstants
import com.lheia.eia.common.GeneConstants
import com.lheia.eia.common.HttpMesConstants
import com.lheia.eia.common.HttpUrlConstants
import com.lheia.eia.config.EiaDomainCode
import com.lheia.eia.tools.HttpConnectTools
import com.lheia.eia.tools.PoiUtils
import grails.converters.JSON
import grails.util.Holders
import org.apache.poi.ss.usermodel.Workbook
import org.grails.web.json.JSONArray
import org.grails.web.json.JSONObject

class EiaHrEvalScoreController {
    public static final String webRootDir = Holders.getConfig().filePath;
    def eiaHrEvalScoreService
    def index() {}
    /**
     * 机构考核人员详情
     */
    def eiaHrEvalStatis(){
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
        def isShowAss = false
        if(roleName.equals(GeneConstants.JOB_RATING_ROLE) || roleName.equals(GeneConstants.JOB_RATING_ADMIN) || roleName.equals(GeneConstants.JOB_RATING_GMANAGER_ROLE)){
            isShowAss = true
        }
        [isShowAss:isShowAss]
    }
    /**
     * 机构人员详情（员工）
     */
    def getEiaHrEvalScoreDataList(){
        def dataMap = eiaHrEvalScoreService.eiaHrEvalScoreQueryPage(params, session)
        if (dataMap) {
            render([items: dataMap.data,total:dataMap.total,jobRatingScoreDeptList:dataMap.jobRatingScoreDeptList,jobRatingScoreDeptListId:dataMap.jobRatingScoreDeptListId] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
    /**
     * 部门经理修改员工得分
     */
    def eiaHrEvalEmpEdit(){
        [eiaHrEvalScoreId:params.eiaHrEvalScoreId]
    }
    /**
     * 部门经理修改员工信息
     */
    def eiaHrEvalEmpList(){
        def dataMap = eiaHrEvalScoreService.eiaHrEvalEmpList(params,session)
        if (dataMap) {
            render(dataMap as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
    /**
     * 修改员工最终得分
     */
    def eiaHrEvalScoreSave(){
        def eiaHrEvalScore = eiaHrEvalScoreService.eiaHrEvalScoreSave(params)
        if (eiaHrEvalScore) {
            render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SAVE_OK, data: eiaHrEvalScore] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
        }
    }
    /**
     * 打印员工最终得分信息
     */
    def eiaHrEvalPrintEmp(){
        [eiaHrEvalScoreId:params.eiaHrEvalScoreId,eiaHrEvalId:params.eiaHrEvalId]
    }
    /**
     * 打印助理得分
     */
    def eiaHrEvalPrintAss(){
        [eiaHrEvalScoreId:params.eiaHrEvalScoreId,eiaHrEvalId:params.eiaHrEvalId]
    }
    /**
     *打印员工最终得分
     */
    def eiaHrEvalPrintEmpList(){
        def dataMap = eiaHrEvalScoreService.eiaHrEvalEmpList(params,session)
        if (dataMap) {
            render(dataMap as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
    /**
     * 修改助理最终得分
     */
    def  eiaHrEvalAssEdit(){
        [eiaHrEvalScoreId:params.eiaHrEvalScoreId]
    }
    /**
     * 部门经理修改助理最终得分
     */
    def eiaHrEvalAssList(){
        def dataMap = eiaHrEvalScoreService.eiaHrEvalAssList(params,session)
        if (dataMap) {
            render(dataMap as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
    /**
     * 保存助理最终得分信息
     */
    def eiaHrEvalScoreAssSave(){
        def eiaHrEvalScore = eiaHrEvalScoreService.eiaHrEvalScoreAssSave(params)
        if (eiaHrEvalScore) {
            render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SAVE_OK, data: eiaHrEvalScore] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
        }
    }
    /**
     * 员工得分情况
     */
    def eiaHrEvalEmp(){}
    /**
     * 员工得分情况列表数据
     */
    def getEiaHrEvalEmpDataList(){
        def dataMap = eiaHrEvalScoreService.getEiaHrEvalEmpDataList(params,session)
        if (dataMap) {
            render(dataMap as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
    /**
     * 判断是否全部打分完成
     */
    def isAllGrade(){
        /**
         * 管理员生成全部,总经理，判断每个部门，如果都打分，则生成
         */
        def isJobRating = false
        if (session?.staff?.funcCode?.contains(FuncConstants.EIA_XZGL_YDKH_VIEWALL)) {
            isJobRating = true
        }
        /**
         * 如果code中有配置，并且eiaHrEvalId为空，则表示批量跑每个部门的最终得分
         */
        if(isJobRating && !params.eiaHrEvalId){
            Calendar cal = Calendar.getInstance();
            String str = String.format("%02d", cal.get(Calendar.MONTH));
            def assessmentMonth
            /**
             * 判断是否为新的一年
             */
            if("00".equals(str)){
                str = "12"
                assessmentMonth = (cal.get(Calendar.YEAR)-1)+'年'+str+'月';
            }else{
                assessmentMonth = cal.get(Calendar.YEAR)+'年'+str+'月';
            }
            def param = [:]
            def eiaDomainCode =EiaDomainCode.findByDomain(GeneConstants.EVAL_ORG_NAME)
            param.orgId = eiaDomainCode?.code.toString()
            def eiaAuthOrgInfo = HttpConnectTools.getResponseJson(HttpUrlConstants.EIA_AUTH_ORG_ALL_INFO, param)
            JSONArray taskRoleExams = new JSONArray(eiaAuthOrgInfo)
            for (int i = 0; i < taskRoleExams.length(); i++) {
                JSONObject jsonTaskAssignUser = taskRoleExams.getJSONObject(i);
                def orgId = jsonTaskAssignUser.getString("id")
                def orgName = jsonTaskAssignUser.getString("orgName")
                def orgCode = jsonTaskAssignUser.getString("orgCode")
                /**
                 * 员工互评总人数
                 */
                Set setAll=new HashSet();
                def sataffEmpAllSize = 0
                def ratingEmpAllSize = 0

                /**
                 * 助理总人数
                 */
                Set setAllAss=new HashSet();
                /**
                 * 最终打分表，总经理打分分表，存到最终打分于统计
                 */
                /**
                 * 判断部门总人数(除总经理，部门总经理，公司特殊助理)
                 */
                def deptPersonTotalList = []
                deptPersonTotalList = GeneConstants.JOB_RATING_JG_ROLE_LIST
                /**
                 * 部门经理打分
                 */
                def jobRatingDetailOneList

                /**
                 * 判断打分人员是否有权限
                 */
                def staffNotDomainDeptList = []
                def paramStaff = [:]
                paramStaff.orgId = orgId.toString()
                /**
                 * 获取不参与打分人员
                 */
                def staffNotEval = HttpConnectTools.getResponseJson(HttpUrlConstants.EIA_AUTH_NOT_EVAL_STAFF_INFO, paramStaff)
                JSONArray staffIdNotEval= new JSONArray(staffNotEval)
                for (int k = 0; k < staffIdNotEval.length(); k++) {
                    JSONObject staffInfo= staffIdNotEval.getJSONObject(k);
                    def staffId = staffInfo.getString("id")
                    staffNotDomainDeptList.add(Long.parseLong(staffId))
                }
                /**
                 * 判断是否有打分权限，用于多角色切换中
                 */
                def eiaHrOrgStaffEval = EiaHrOrgStaffEval.findAllByIfDelAndOrgId(false,orgId)
                if(eiaHrOrgStaffEval.size()>0){
                    eiaHrOrgStaffEval.each{
                        staffNotDomainDeptList.add(it.staffId)
                    }
                }
                if(staffNotDomainDeptList){
                    jobRatingDetailOneList =  EiaHrEvalScoreDetail.findAllByIfDelAndInputDeptIdAndAssessmentMonthAndJobRatingTypeAndRoleNameNotInListAndIfSubmitAndInputUserIdNotInList(false,orgId,assessmentMonth,GeneConstants.EIA_HR_EVAL_EMP,deptPersonTotalList,true,staffNotDomainDeptList)
                }else {
                    jobRatingDetailOneList =  EiaHrEvalScoreDetail.findAllByIfDelAndInputDeptIdAndAssessmentMonthAndJobRatingTypeAndRoleNameNotInListAndIfSubmit(false,orgId,assessmentMonth,GeneConstants.EIA_HR_EVAL_EMP,deptPersonTotalList,true)
                }

                /**
                 * 员工互评打分
                 */
                jobRatingDetailOneList.each{
                    setAll.add(it.inputUser);
                }

                ratingEmpAllSize = setAll.size()
                def paramPerson = [:]
                paramPerson.orgId = orgId.toString()
                paramPerson.evalNot = staffNotDomainDeptList.toString()
                /**
                 * 员工打分人数，不包括助理，部门经理
                 */
                def staffJson = HttpConnectTools.getResponseJson(HttpUrlConstants.EIA_AUTH_ORG_STAFF_INFO, paramPerson)
                if(!HttpMesConstants.MSG_DATA_NULL.equals(staffJson)){
                    JSONArray staffInfo = new JSONArray(staffJson)
                    sataffEmpAllSize = staffInfo.length()
                }

                /**
                 * 给助理打分
                 */
                def deptPersonList = []
                deptPersonList.add(GeneConstants.JOB_RATING_ASS_ROLE)
                deptPersonList.add(GeneConstants.JOB_RATING_MANAGER_ROLE)
                /**
                 * 角色名称
                 */
                def jobRatingDetailTwoList
                def jobRatingDetailMagerList
                def jobRatingDetailMagerTwoList
                def jobRatingDetailList

                if(staffNotDomainDeptList){
                    jobRatingDetailTwoList =  EiaHrEvalScoreDetail.findAllByIfDelAndInputDeptIdAndAssessmentMonthAndJobRatingTypeAndRoleNameNotInListAndIfSubmitAndInputUserIdNotInList(false,orgId,assessmentMonth,GeneConstants.EIA_HR_EVAL_ASS,deptPersonList,true,staffNotDomainDeptList)
                    /**
                     * 总经理打分（表一）
                     */
                    jobRatingDetailMagerList =  EiaHrEvalScoreDetail.findAllByIfDelAndInputDeptIdAndAssessmentMonthAndJobRatingTypeAndRoleNameAndIfSubmitAndInputUserIdNotInList(false,orgId,assessmentMonth,GeneConstants.EIA_HR_EVAL_EMP,GeneConstants.JOB_RATING_ROLE,true,staffNotDomainDeptList)
                    /**
                     * 总经理打分（表二）
                     */
                    jobRatingDetailMagerTwoList =  EiaHrEvalScoreDetail.findAllByIfDelAndInputDeptIdAndAssessmentMonthAndJobRatingTypeAndRoleNameAndIfSubmitAndInputUserIdNotInList(false,orgId,assessmentMonth,GeneConstants.EIA_HR_EVAL_ASS,GeneConstants.JOB_RATING_ROLE,true,staffNotDomainDeptList)
                    jobRatingDetailList =  EiaHrEvalScore.findAllByIfDelAndInputDeptIdAndAssessmentMonth(false,orgId,assessmentMonth)
                }else {
                    jobRatingDetailTwoList =  EiaHrEvalScoreDetail.findAllByIfDelAndInputDeptIdAndAssessmentMonthAndJobRatingTypeAndRoleNameNotInListAndIfSubmit(false,orgId,assessmentMonth,GeneConstants.EIA_HR_EVAL_ASS,deptPersonList,true)
                    /**
                     * 总经理打分（表一）
                     */
                    jobRatingDetailMagerList =  EiaHrEvalScoreDetail.findAllByIfDelAndInputDeptIdAndAssessmentMonthAndJobRatingTypeAndRoleNameAndIfSubmit(false,orgId,assessmentMonth,GeneConstants.EIA_HR_EVAL_EMP,GeneConstants.JOB_RATING_ROLE,true)
                    /**
                     * 总经理打分（表二）
                     */
                    jobRatingDetailMagerTwoList =  EiaHrEvalScoreDetail.findAllByIfDelAndInputDeptIdAndAssessmentMonthAndJobRatingTypeAndRoleNameAndIfSubmit(false,orgId,assessmentMonth,GeneConstants.EIA_HR_EVAL_ASS,GeneConstants.JOB_RATING_ROLE,true)
                    jobRatingDetailList =  EiaHrEvalScore.findAllByIfDelAndInputDeptIdAndAssessmentMonth(false,orgId,assessmentMonth)

                }
                jobRatingDetailTwoList.each{
                    setAllAss.add(it.inputUser);
                }
                def paramAss = [:]
                paramAss.orgId = orgId.toString()
                paramAss.evalNot = staffNotDomainDeptList.toString()
                paramAss.ass = "ass"
                /**
                 * 员工打分人数，不包括助理，部门经理
                 */
                def staffJsonAss = HttpConnectTools.getResponseJson(HttpUrlConstants.EIA_AUTH_ORG_STAFF_INFO, paramAss)
                JSONArray staffAssInfo
                if(!HttpMesConstants.MSG_DATA_NULL.equals(staffJsonAss)){
                    staffAssInfo = new JSONArray(staffJsonAss)
                }

                if(jobRatingDetailList.size()<=0){
                    /**
                     * 员工互评打分ratingEmpSize,如果有助理,则去掉助理，去掉助理以后则提示员工打分情况
                     */
                    if(staffAssInfo){
                        if(staffAssInfo.size()>0){
                            ratingEmpAllSize = ratingEmpAllSize-staffAssInfo.size()
                        }
                    }

                    if(ratingEmpAllSize == sataffEmpAllSize && (jobRatingDetailMagerList.size()>=0 && jobRatingDetailMagerTwoList.size()>=0)){
                        eiaHrEvalScoreService.finalScoreRatingAll(orgId,assessmentMonth,sataffEmpAllSize-1,orgCode)
                    }
                }
            }
        }else{
            /**
             * 员工互评总人数
             */
            Set setEmp=new HashSet();
            def sataffEmpSize
            def ratingEmpSize
            /**
             * 员工互评总人数
             */
            Set setAss=new HashSet();
            def sataffAsspSize = 0
            def ratingAssSize =0
            /**
             * 最终打分表，总经理打分分表，存到最终打分于统计
             */
            /**
             * 判断部门总人数(除总经理，部门总经理)
             */
            def deptPersonTotalList = []
            deptPersonTotalList = GeneConstants.JOB_RATING_JG_ROLE_LIST
            def eiaHrEval
            def assessmentMonth
            /**
             * 机构考核人员（批量）和单独生成（按期次）orgId取值不一样
             */
            def orgId
            def orgCode
            if(params.long("eiaHrEvalId")){
                eiaHrEval =  EiaHrEval.findByIdAndIfDel(params.long("eiaHrEvalId"),false)
                assessmentMonth = eiaHrEval?.assessmentMonth
                orgId = eiaHrEval.orgId
                orgCode = eiaHrEval.orgCode
            }else{
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
                def eiaHrEvalInfo = EiaHrEval.findByIfDelAndAssessmentMonthAndOrgCode(false,assessmentMonth,session.staff.orgCode)
                if(eiaHrEvalInfo){
                    orgId = eiaHrEvalInfo?.orgId
                    orgCode = eiaHrEvalInfo.orgCode
                }else{
                    eiaHrEval = EiaHrEval.findAllByIfDelAndJobRatingType(false,GeneConstants.EIA_HR_EVAL_EMP)
                    eiaHrEval.each{
                        if(session.staff.orgCode.contains(it.orgCode)){
                            orgId = it.orgId
                            orgCode = it.orgCode
                        }
                    }
                }
            }


            /**
             * 部门经理打分
             */
            def jobRatingDetailOneList
            def staffNotEvalList = []
            /**
             * 判断打分人员是否有权限
             */
            def staffNotDomainDeptList = []
            def paramStaff = [:]
            paramStaff.orgId = orgId.toString()
            /**
             * 获取不参与打分人员
             */
            def staffNotEval = HttpConnectTools.getResponseJson(HttpUrlConstants.EIA_AUTH_NOT_EVAL_STAFF_INFO, paramStaff)
            if(staffNotEval){
                JSONArray staffIdNotEval= new JSONArray(staffNotEval)
                for (int i = 0; i < staffIdNotEval.length(); i++) {
                    JSONObject staffInfo= staffIdNotEval.getJSONObject(i);
                    def staffId = staffInfo.getString("id")
                    staffNotDomainDeptList.add(Long.parseLong(staffId))

                }
            }
            if(staffNotDomainDeptList){
                if(staffNotDomainDeptList.contains(session.staff.staffId)){
                    staffNotDomainDeptList.each{
                        staffNotEvalList.add(it.id.toString())
                    }
                }
                /**
                 * 判断是否有打分权限，用于多角色切换中
                 */
                def eiaHrOrgStaffEval = EiaHrOrgStaffEval.findAllByIfDelAndOrgId(false,orgId)
                if(eiaHrOrgStaffEval.size()>0){
                    eiaHrOrgStaffEval.each{
                        staffNotEvalList.add(it.staffId)
                    }
                }
            }
            if(staffNotEvalList){
                /**
                 * 员工互评打分
                 */
                jobRatingDetailOneList =  EiaHrEvalScoreDetail.findAllByIfDelAndInputDeptIdAndAssessmentMonthAndJobRatingTypeAndRoleNameNotInListAndIfSubmitAndInputUserIdNotInList(false,orgId,assessmentMonth,GeneConstants.EIA_HR_EVAL_EMP,deptPersonTotalList,true,staffNotEvalList)
            }else {
                /**
                 * 员工互评打分
                 */
                jobRatingDetailOneList =  EiaHrEvalScoreDetail.findAllByIfDelAndInputDeptIdAndAssessmentMonthAndJobRatingTypeAndRoleNameNotInListAndIfSubmit(false,orgId,assessmentMonth,GeneConstants.EIA_HR_EVAL_EMP,deptPersonTotalList,true)
            }
            /**
             * 员工互评打分
             */
            jobRatingDetailOneList.each{
                setEmp.add(it.inputUser);
            }
            /**
             * 根据打分人去重，得到被打分人数
             */
            def jobRatingScoreList = []
            List<Integer> list = new ArrayList<Integer>(setEmp);
            for (int i = 0; i < list.size(); i++) {
                jobRatingScoreList.add(list.get(i))
            }
            ratingEmpSize = setEmp.size()
            def staffNotDomainLists = []
            def param = [:]
            param.orgId = orgId.toString()
            /**
             * 获取不参与打分人员
             */
            def staffNotEvalJson = HttpConnectTools.getResponseJson(HttpUrlConstants.EIA_AUTH_NOT_EVAL_STAFF_INFO, param)
            if(staffNotEvalJson){
                JSONArray staffNotEvalnfo = new JSONArray(staffNotEvalJson)
                for (int i = 0; i < staffNotEvalnfo.length(); i++) {
                    JSONObject staffInfo= staffNotEvalnfo.getJSONObject(i);
                    def staffId = staffInfo.getString("id")
                    staffNotDomainLists.add(Long.parseLong(staffId))
                }
            }
            /**
             * 判断是否有打分权限，用于多角色切换中
             */
            def eiaHrOrgStaffEval = EiaHrOrgStaffEval.findAllByIfDelAndOrgId(false,orgId)
            if(eiaHrOrgStaffEval.size()>0){
                eiaHrOrgStaffEval.each{
                    staffNotDomainLists.add(it.staffId)
                }
            }
            param.evalNot = staffNotDomainLists.toString()
            param.evalAll = "evalAll"
            /**
             * 区分是点击最终得分，还是员工打分按钮
             */
            param.evalType='evalType'
            /**
             * 员工打分人数,
             */
            def staffJson = HttpConnectTools.getResponseJson(HttpUrlConstants.EIA_AUTH_ORG_STAFF_INFO, param)
            JSONArray staffInfo
            if(!HttpMesConstants.MSG_DATA_NULL.equals(staffJson)){
                staffInfo = new JSONArray(staffJson)
                sataffEmpSize = staffInfo.length()
            }

            /**
             * 给助理打分
             */
            def deptPersonList = []
            deptPersonList.add(GeneConstants.JOB_RATING_ASS_ROLE)
            deptPersonList.add(GeneConstants.JOB_RATING_MANAGER_ROLE)
            /**
             * 查询员工，助理，部门经理打分情况
             */
            /**
             * 助理给助理打分情况
             */
            def jobRatingDetailTwoList
            /**
             * 经理给员工打分情况
             */
            def jobRatingDetailMagerList
            /**
             * 经理给助理打分情况
             */
            def jobRatingDetailMagerTwoList
            if(staffNotEvalList){
                jobRatingDetailTwoList =  EiaHrEvalScoreDetail.findAllByIfDelAndInputDeptIdAndAssessmentMonthAndJobRatingTypeAndRoleNameInListAndIfSubmitAndInputUserIdNotInList(false,orgId,assessmentMonth,GeneConstants.EIA_HR_EVAL_ASS,deptPersonList,true,staffNotEvalList)
                /**
                 * 总经理打分（表一）
                 */
                jobRatingDetailMagerList =  EiaHrEvalScoreDetail.findAllByIfDelAndInputDeptIdAndAssessmentMonthAndJobRatingTypeAndRoleNameInListAndIfSubmitAndInputUserIdNotInList(false,orgId,assessmentMonth,GeneConstants.EIA_HR_EVAL_EMP,deptPersonTotalList,true,staffNotEvalList)
                /**
                 * 总经理打分（表二）
                 */
                jobRatingDetailMagerTwoList =  EiaHrEvalScoreDetail.findAllByIfDelAndInputDeptIdAndAssessmentMonthAndJobRatingTypeAndRoleNameInListAndIfSubmitAndInputUserIdNotInList(false,orgId,assessmentMonth,GeneConstants.EIA_HR_EVAL_ASS,deptPersonTotalList,true,staffNotEvalList)
            }else {
                jobRatingDetailTwoList =  EiaHrEvalScoreDetail.findAllByIfDelAndInputDeptIdAndAssessmentMonthAndJobRatingTypeAndRoleNameInListAndIfSubmit(false,orgId,assessmentMonth,GeneConstants.EIA_HR_EVAL_ASS,deptPersonList,true)
                /**
                 * 总经理打分（表一）
                 */
                jobRatingDetailMagerList =  EiaHrEvalScoreDetail.findAllByIfDelAndInputDeptIdAndAssessmentMonthAndJobRatingTypeAndRoleNameInListAndIfSubmit(false,orgId,assessmentMonth,GeneConstants.EIA_HR_EVAL_EMP,deptPersonTotalList,true)
                /**
                 * 总经理打分（表二）
                 */
                jobRatingDetailMagerTwoList =  EiaHrEvalScoreDetail.findAllByIfDelAndInputDeptIdAndAssessmentMonthAndJobRatingTypeAndRoleNameInListAndIfSubmit(false,orgId,assessmentMonth,GeneConstants.EIA_HR_EVAL_ASS,deptPersonTotalList,true)
            }
            jobRatingDetailTwoList.each{
                setAss.add(it.inputUser);
            }
            def jobRatingScoreAssList = []
            List<Integer> evalScoreList = new ArrayList<Integer>(setAss);
            for (int i = 0; i < evalScoreList.size(); i++) {
                jobRatingScoreAssList.add(evalScoreList.get(i))
            }
            def paramAss = [:]
            paramAss.orgId = orgId.toString()
            paramAss.evalNot = staffNotDomainLists.toString()
            paramAss.ass = "ass"
            paramAss.evalType='evalType'
            /**
             * 助理打分人数，不包括员工，部门经理
             */
            def staffJsonAss = HttpConnectTools.getResponseJson(HttpUrlConstants.EIA_AUTH_ORG_STAFF_INFO, paramAss)
            JSONArray staffAssInfo
            if(!HttpMesConstants.MSG_DATA_NULL.equals(staffJsonAss)){
                staffAssInfo = new JSONArray(staffJsonAss)
                sataffAsspSize = staffAssInfo.length()
            }
            ratingAssSize = setAss.size()
            /**
             * 判断当前点击类型（助理打分，员工打分）eiaScoreType不为空，说明点击助理按钮
             * 由于助理和员工点击不同的按钮，但是生成最终得分是都有打分完成，才能生成最终得分，
             * 所以下面方法中重写了两个同样的方法，只是为了提示先后顺序
             */
            if(params.eiaScoreType){
                /**
                 * 判断助理是否打分完成，如果只有一个助理，则助理不能给自己打分，所以不用判断助理打分情况
                 * 首先判断助理个数
                 */
                if(sataffAsspSize>1){
                    def notScoreStaff =""
                    for(String staffNots : staffAssInfo.staffName){
                        if(!jobRatingScoreAssList.contains(staffNots)){
                            notScoreStaff+=staffNots+"，"
                        }
                    }
                    if(notScoreStaff){
                        notScoreStaff = notScoreStaff.substring(0,notScoreStaff.length() - 1)
                        render "isScore@"+notScoreStaff
                        return
                    }
                }
                /**
                 * 判断员工是否打分
                 */
                def notScoreStaff =""
                if(staffInfo){
                    for(String staffNots : staffInfo.staffName){
                        if(!jobRatingScoreList.contains(staffNots)){
                            notScoreStaff+=staffNots+"，"
                        }
                    }
                }
                if(notScoreStaff){
                    notScoreStaff = notScoreStaff.substring(0,notScoreStaff.length() - 1)
                    render "isScore@"+notScoreStaff
                    return
                }
                if(sataffAsspSize<=0){
                    render "isNotAss"
                    return
                }
            }else{
                /**
                 * 点击员工判断
                 */
                def notScoreStaff =""
                if(staffInfo){
                    for(String staffNots : staffInfo.staffName){
                        if(!jobRatingScoreList.contains(staffNots)){
                            notScoreStaff+=staffNots+"，"
                        }
                    }
                }
                if(notScoreStaff){
                    notScoreStaff = notScoreStaff.substring(0,notScoreStaff.length() - 1)
                    render "isScore@"+notScoreStaff
                    return
                }
                /**
                 * 判断助理是否打分完成，如果只有一个助理，则助理不能给自己打分，所以不用判断助理打分情况
                 */
                if(sataffAsspSize>1){
                    for(String staffNots : staffAssInfo.staffName){
                        if(!jobRatingScoreAssList.contains(staffNots)){
                            notScoreStaff+=staffNots+"，"
                        }
                    }
                    if(notScoreStaff){
                        notScoreStaff = notScoreStaff.substring(0,notScoreStaff.length() - 1)
                        render "isScore@"+notScoreStaff
                        return
                    }
                }
            }
            /**
             * 首先判断当前登录
             */
            def paramRole = [:]
            paramRole.orgId = session.staff.orgId.toString()
            paramRole.staffId = session.staff.staffId.toString()
            def staffJsonRole = HttpConnectTools.getResponseJson(HttpUrlConstants.EIA_AUTH_ORG_STAFF_ROLE_INFO, paramRole)
            JSONArray staffRoleInfo = new JSONArray(staffJsonRole)
            def roleName
            for (int i = 0; i < staffRoleInfo.length(); i++) {
                JSONObject staffJsonJect = staffRoleInfo.getJSONObject(i);
                roleName = staffJsonJect.getString("roleName")
            }
            /**
             * 项目经理或者是在有权限的人，都可以生成最终得分
             */
            if(deptPersonTotalList.contains(roleName)|| (isJobRating && params.eiaHrEvalId)){
                /**
                 * 员工互评打分ratingEmpSize,如果有助理,则去掉助理，去掉助理以后则提示员工打分情况
                 */
                if(sataffAsspSize>0){
                    ratingEmpSize = ratingEmpSize-sataffAsspSize
                }
               if(ratingAssSize != sataffAsspSize && sataffAsspSize!=1){
                    render 'assMag'
                    return
                    /*
                    *jobRatingDetailMagerTwoList判断不为空，首先判断是否有助理，如果这个助理没有打分权限，就直接忽略
                    */
                }else if(jobRatingDetailMagerList.size()<=0){
                    render 'magerMag'
                    return
                }else if(jobRatingDetailMagerTwoList.size()<=0 && sataffAsspSize>0){
                    /**
                     * 判断有助理并且有助理打分
                     */
                    render 'magerAssMag'
                    return
                }else{
                    /**
                     * 全部打分完成
                     */
                    def jobRatingDetailList
                    if(staffNotEvalList){
                        jobRatingDetailList =  EiaHrEvalScore.findAllByIfDelAndInputDeptIdAndAssessmentMonthAndStaffIdNotInList(false,orgId,assessmentMonth,staffNotEvalList)
                    }else {
                        jobRatingDetailList = EiaHrEvalScore.findAllByIfDelAndInputDeptIdAndAssessmentMonth(false, orgId, assessmentMonth)
                    }
                    if(jobRatingDetailList.size()<=0){
                        eiaHrEvalScoreService.finalScoreRatingAll(orgId,assessmentMonth,sataffEmpSize-1,orgCode)
                    }
                    render 'success'
                }
            }
        }

        render([success:true] as JSON)
    }
    /**
     * 单独计算员工最终得分，根据不同的月份和类型计算
     */
    def eiaHrEvalScoreEmp(){
        [eiaHrEvalId:params.eiaHrEvalId]
    }
    /**
     * 单独计算助理最终得分，根据不同的月份和类型计算
     */
    def eiaHrEvalScoreAss(){
        [eiaHrEvalId:params.eiaHrEvalId]
    }
    /**
     * 员工最终得分，用于有权限的人点击操作中的员工得分详情)
     */
    def getEiaHrEvalEmpDataDetailList(){
        def dataMap = eiaHrEvalScoreService.getEiaHrEvalEmpDataDetailList(params,session)
        if (dataMap) {
            render(dataMap as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
    /**
     * 导出所有员工考核信息
     */
    def eiaHrEvalScoreExport(){
        def titleList = ['机构名称','评分对象','考核年月','工作执行力(20)','工作业绩(20)','工作技能(20)','工作态度(20)','团队精神(10)','企业文化认知(10)','最终得分']
        def dataList = []
        def eiaHrEvalScore
        def orgId
        def eiaHrEvalInfo = EiaHrEval.findByIfDelAndOrgCode(false,session.staff.orgCode)
        if(eiaHrEvalInfo){
            orgId = eiaHrEvalInfo?.orgId
        }else {
            eiaHrEvalInfo = EiaHrEval.findAllByIfDelAndJobRatingType(false,GeneConstants.EIA_HR_EVAL_EMP)
            eiaHrEvalInfo.each{
                if(session.staff.orgCode.contains(it.orgCode)){
                    orgId = it.orgId
                }
            }
        }
        if (session?.staff?.funcCode?.contains(FuncConstants.EIA_XZGL_YDKH_VIEWALL)) {
            eiaHrEvalScore = EiaHrEvalScore.findAllByIfDelAndJobRatingType(false,GeneConstants.EIA_HR_EVAL_EMP)
        }else{
            eiaHrEvalScore = EiaHrEvalScore.findAllByIfDelAndJobRatingTypeAndInputDeptId(false,GeneConstants.EIA_HR_EVAL_EMP,orgId)
        }

        eiaHrEvalScore.each {
            def tmpList = []
            tmpList.add(it?.inputDept)
            tmpList.add(it?.staffName)
            tmpList.add(it?.assessmentMonth)
            tmpList.add(it?.workExecution)
            tmpList.add(it.performance)
            tmpList.add(it?.jobSkill)
            tmpList.add(it?.workingAttitude)
            tmpList.add(it?.teamSpirit)
            tmpList.add(it?.cultureCognition)
            if(90<= it?.finalScore){
                tmpList.add("优秀("+it?.finalScore+")")
            }else if(80<= it?.finalScore && 90>it?.finalScore ){
                tmpList.add("良好("+it?.finalScore+")")
            }else if(70<= it?.finalScore && 80>it?.finalScore ){
                tmpList.add("合格("+it?.finalScore+")")
            }else if(70>it?.finalScore ){
                tmpList.add("不合格("+it?.finalScore+")")
            }
            dataList.add(tmpList)
        }
        Workbook wb = PoiUtils.exportExcel(titleList, dataList)
        String downLoadName = new String((GeneConstants.EIA_HR_EVAL_EMP + ".xlsx").getBytes("UTF-8"), "iso-8859-1")
        response.reset()
        response.addHeader("Content-disposition", "attachment; filename=" + downLoadName)
        response.setContentType("application/msexcel;charset=UTF-8");
        OutputStream out = response.getOutputStream()
        wb.write(out)
        out.flush()
        out.close()
    }
    /**
     * 导出助理最后得分
     */
    def eiaHrEvalScoreAssExport(){
        def jobRatingScore
        def orgId
        def eiaHrEvalInfo = EiaHrEval.findByIfDelAndOrgCode(false,session.staff.orgCode)
        if(eiaHrEvalInfo){
            orgId = eiaHrEvalInfo?.orgId
        }else {
            eiaHrEvalInfo = EiaHrEval.findAllByIfDelAndJobRatingType(false,GeneConstants.EIA_HR_EVAL_EMP)
            eiaHrEvalInfo.each{
                if(session.staff.orgCode.contains(it.orgCode)){
                    orgId = it.orgId
                }
            }
        }
        def titleList = ['机构名称','评分对象','考核年月','团队精神(10)','领导能力(10)','专业能力(10)','工作执行力(20)','工作绩效(20)','工作态度(20)','领导能力(10)','最终得分']
        def dataList = []
        if (session?.staff?.funcCode?.contains(FuncConstants.EIA_XZGL_YDKH_VIEWALL)) {
            jobRatingScore = EiaHrEvalScore.findAllByIfDelAndJobRatingType(false,GeneConstants.EIA_HR_EVAL_ASS)
        }else{
            jobRatingScore = EiaHrEvalScore.findAllByIfDelAndJobRatingTypeAndInputDeptId(false,GeneConstants.EIA_HR_EVAL_ASS,orgId)
        }
        jobRatingScore.each {
            def tmpList = []
            tmpList.add(it?.inputDept)
            tmpList.add(it?.staffName)
            tmpList.add(it?.assessmentMonth)
            tmpList.add(it?.teamSpirit)
            tmpList.add(it.leadership)
            tmpList.add(it?.proAbility)
            tmpList.add(it?.workExecution)
            tmpList.add(it?.performance)
            tmpList.add(it?.workingAttitude)
            tmpList.add(it?.leadershipManager)
            if(90<= it?.finalScore){
                tmpList.add("优秀("+it?.finalScore+")")
            }else if(80<= it?.finalScore && 90>it?.finalScore ){
                tmpList.add("良好("+it?.finalScore+")")
            }else if(70<= it?.finalScore && 80>it?.finalScore ){
                tmpList.add("合格("+it?.finalScore+")")
            }else if(70>it?.finalScore ){
                tmpList.add("不合格("+it?.finalScore+")")
            }
            dataList.add(tmpList)
        }

        Workbook wb = PoiUtils.exportExcel(titleList, dataList)
        String downLoadName = new String((GeneConstants.EIA_HR_EVAL_ASS + ".xlsx").getBytes("UTF-8"), "iso-8859-1")
        response.reset()
        response.addHeader("Content-disposition", "attachment; filename=" + downLoadName)
        response.setContentType("application/msexcel;charset=UTF-8");
        OutputStream out = response.getOutputStream()
        wb.write(out)
        out.flush()
        out.close()
    }
}
