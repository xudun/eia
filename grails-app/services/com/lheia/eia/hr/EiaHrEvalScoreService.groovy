package com.lheia.eia.hr

import com.lheia.eia.common.FuncConstants
import com.lheia.eia.common.GeneConstants
import com.lheia.eia.common.HttpUrlConstants
import com.lheia.eia.tools.HttpConnectTools
import grails.gorm.transactions.Transactional
import org.grails.web.json.JSONArray
import org.grails.web.json.JSONObject

@Transactional
class EiaHrEvalScoreService {
    /**
     * 机构人员详情（员工）
     */
    def eiaHrEvalScoreQueryPage(params, session) {
        def assessmentMonth
        /**
         * 处理分公司的orgCode取值
         */
        def orgCode
        def eiaHrEvalInfo = EiaHrEval.findByIfDelAndOrgCode(false,session.staff.orgCode)
        if(eiaHrEvalInfo){
            orgCode = eiaHrEvalInfo?.orgCode
        }else {
            eiaHrEvalInfo = EiaHrEval.findAllByIfDelAndJobRatingType(false,GeneConstants.EIA_HR_EVAL_EMP)
            eiaHrEvalInfo.each{
                if(session.staff.orgCode.contains(it.orgCode)){
                    orgCode = it.orgCode
                }
            }
        }
        def eiaHrEvalDataList = EiaHrEvalScore.createCriteria().list() {

            if(params["where[ratingYear]"] && params["where[ratingMonth]"]){
                assessmentMonth = params["where[ratingYear]"]+"年"+params["where[ratingMonth]"]+"月"
                eq("assessmentMonth",assessmentMonth)
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
                eq("assessmentMonth",assessmentMonth)
            }
            /**
             * 部门助理详情
             */
            if(params.staticAss){
                eq("jobRatingType",GeneConstants.EIA_HR_EVAL_ASS)
            }else{
                /**
                 * 部门员工详情
                 */
                eq("jobRatingType",GeneConstants.EIA_HR_EVAL_EMP)
            }
            /**
             * 判断是否有查看全部列表权限
             */
            if (!session?.staff?.funcCode?.contains(FuncConstants.EIA_XZGL_YDKH_VIEWALL)) {
                if(session?.staff?.funcCode?.contains(FuncConstants.EIA_XZGL_NDKH_VIEWDEPT)){
                    like("inputDeptCode", "%"+ orgCode+ "%")
                }
            }
            eq("ifDel", false)
            order("id", "desc")
        }
        def eiaHrEvalData = []
        eiaHrEvalDataList.each {
            def map = [:]
            map.id = it.id
            map.staffName = it.staffName
            map.inputDept = it.inputDept
            map.inputDeptId = it.inputDeptId
            map.workExecution = it.workExecution
            map.assessmentMonth = it.assessmentMonth
            map.performance = it.performance
            map.jobSkill = it.jobSkill
            map.workingAttitude = it.workingAttitude
            map.teamSpirit = it.teamSpirit
            map.cultureCognition = it.cultureCognition
            map.leadership = it.leadership
            map.leadershipManager = it.leadershipManager
            map.proAbility = it.proAbility
            map.finalScore = it.finalScore
            eiaHrEvalData << map
        }
        def dataMap = [:]
        dataMap.data = eiaHrEvalData
        /**
         * 拼接需要的json格式，需要机构名称和机构id
         */
        def eiaHrEval
        if(params.staticAss){
            if(!session?.staff?.funcCode?.contains(FuncConstants.EIA_XZGL_YDKH_VIEWALL)){
                eiaHrEval = EiaHrEvalScore.findAllByIfDelAndAssessmentMonthAndJobRatingTypeAndInputDeptCodeLike(false,assessmentMonth, GeneConstants.EIA_HR_EVAL_ASS, "%"+ orgCode+ "%")
            }else {
                eiaHrEval = EiaHrEvalScore.findAllByIfDelAndAssessmentMonthAndJobRatingType(false,assessmentMonth, GeneConstants.EIA_HR_EVAL_ASS)
            }
        }else {
            if(!session?.staff?.funcCode?.contains(FuncConstants.EIA_XZGL_YDKH_VIEWALL)){
                eiaHrEval = EiaHrEvalScore.findAllByIfDelAndAssessmentMonthAndJobRatingTypeAndInputDeptCodeLike(false,assessmentMonth, GeneConstants.EIA_HR_EVAL_EMP, "%"+ orgCode+ "%")
            }else{
                eiaHrEval = EiaHrEvalScore.findAllByIfDelAndAssessmentMonthAndJobRatingType(false,assessmentMonth, GeneConstants.EIA_HR_EVAL_EMP)
            }
        }
        /**
         * 取公司名称，根据id和机构名称去重，用来显示机构名称
         */
        Set setRating = new HashSet()
        Set setRatingId = new HashSet()
        def jobRatingScoreList = []
        eiaHrEval.each{
            setRating.add(it.inputDept)
        }
        eiaHrEval.each{
            setRatingId.add(it.inputDeptId)
        }
        List<Integer> list = new ArrayList<Integer>(setRating);
        for (int i = 0; i < list.size(); i++) {
            jobRatingScoreList.add(list.get(i))
        }
        dataMap.jobRatingScoreDeptList = jobRatingScoreList
        dataMap.jobRatingScoreDeptListId = setRatingId
        dataMap.total = eiaHrEvalDataList.size()
        return dataMap
    }

    /**
     * 部门经理修改员工最终得分
     */
    def eiaHrEvalEmpList(params,session){
        /**
         * 如果eiaHrEvalId不为空，则说明是员工点击本人的最终得分打印，
         * 如果为空，则表示能最终修改员工打分的人点击该打印，
         */
        def eiaHrEvalScore
        if(params.eiaHrEvalId){
            def eiaHrEval = EiaHrEval.findByIdAndIfDel(params.long("eiaHrEvalId"),false)
            eiaHrEvalScore = EiaHrEvalScore.findByIfDelAndInputDeptIdAndAssessmentMonthAndStaffId(false, eiaHrEval?.orgId, eiaHrEval?.assessmentMonth,session.staff.staffId)
        }else{
            eiaHrEvalScore = EiaHrEvalScore.findByIdAndIfDel(params.long("eiaHrEvalScoreId"),false)
        }
        def eiaHrEvalData = []
        def map = [:]
        map.id = eiaHrEvalScore.id
        map.jobRatingType = eiaHrEvalScore.jobRatingType
        map.staffName = eiaHrEvalScore.staffName
        map.staffId = eiaHrEvalScore.staffId
        /**
         * 取直属领导
         */
        def param = [:]
        param.orgId = eiaHrEvalScore.inputDeptId.toString()
        def staffLenderJson = HttpConnectTools.getResponseJson(HttpUrlConstants.EIA_AUTH_ORG_STAFF_LENDER_INFO, param)
        JSONArray staffLenderInfo = new JSONArray(staffLenderJson)
        map.leader = staffLenderInfo.getJSONObject(0)getString("staffName")
        map.jobRatingScoreId = eiaHrEvalScore.id
        map.orgName = eiaHrEvalScore.inputDept
        map.orgId = eiaHrEvalScore.inputDeptId
        def eiaHrEvalScoreDetail = EiaHrEvalScoreDetail.findByIfDelAndInputDeptIdAndAssessmentMonthAndInputUserId(false,eiaHrEvalScore.inputDeptId,eiaHrEvalScore?.assessmentMonth,eiaHrEvalScore.staffId)
        map.roleName = eiaHrEvalScoreDetail?.roleName
        map.workExecution = eiaHrEvalScore.workExecution
        map.assessmentMonth = eiaHrEvalScore.assessmentMonth
        map.performance = eiaHrEvalScore.performance
        map.jobSkill = eiaHrEvalScore.jobSkill
        map.workingAttitude = eiaHrEvalScore.workingAttitude
        map.teamSpirit = eiaHrEvalScore.teamSpirit
        map.cultureCognition = eiaHrEvalScore.cultureCognition
        map.finalScore = eiaHrEvalScore.finalScore
        map.leaderComments = eiaHrEvalScore.leaderComments
        eiaHrEvalData << map
        return  map
    }
    /**
     * 修改员工最终得分
     */
    def eiaHrEvalScoreSave(params){
        def jobRatingScore = EiaHrEvalScore.findByIdAndIfDel(params.long("jobRatingScoreId"),false)
        if(jobRatingScore){
            jobRatingScore.properties = params
            jobRatingScore.finalScore = Integer.valueOf(params.workExecution)+Integer.valueOf(params.performance)+Integer.valueOf(params.jobSkill)+Integer.valueOf(params.workingAttitude)+Integer.valueOf(params.teamSpirit)+Integer.valueOf(params.cultureCognition)
            jobRatingScore.save(flush:true,failOnError:true)
        }
    }
    /**
     * 修改助理最终得分
     */
    def eiaHrEvalAssList(params,session){
        /**
         * 如果eiaHrEvalId不为空，则说明是员工点击本人的最终得分打印，
         * 如果为空，则表示能最终修改员工打分的人点击该打印，
         */
        def eiaHrEvalScore
        if(params.eiaHrEvalId){
            def eiaHrEval = EiaHrEval.findByIdAndIfDel(params.long("eiaHrEvalId"),false)
            eiaHrEvalScore = EiaHrEvalScore.findByIfDelAndInputDeptIdAndAssessmentMonthAndStaffId(false, eiaHrEval?.orgId, eiaHrEval?.assessmentMonth,session.staff.staffId)
        }else{
            eiaHrEvalScore = EiaHrEvalScore.findByIdAndIfDel(params.long("eiaHrEvalScoreId"),false)
        }
        def eiaHrEvalData = []
        def map = [:]
        map.id = eiaHrEvalScore.id
        map.jobRatingType = eiaHrEvalScore.jobRatingType
        map.staffName = eiaHrEvalScore.staffName
        map.staffId = eiaHrEvalScore.staffId
        /**
         * 取直属领导
         */
        def param = [:]
        param.orgId = eiaHrEvalScore.inputDeptId.toString()
        def staffLenderJson = HttpConnectTools.getResponseJson(HttpUrlConstants.EIA_AUTH_ORG_STAFF_LENDER_INFO, param)
        JSONArray staffLenderInfo = new JSONArray(staffLenderJson)
        map.leader = staffLenderInfo.getJSONObject(0)getString("staffName")
        map.jobRatingScoreId = eiaHrEvalScore.id
        map.orgName = eiaHrEvalScore.inputDept
        map.orgId = eiaHrEvalScore.inputDeptId
        def eiaHrEvalScoreDetail = EiaHrEvalScoreDetail.findByIfDelAndInputDeptIdAndAssessmentMonthAndInputUserId(false,eiaHrEvalScore.inputDeptId,eiaHrEvalScore?.assessmentMonth,eiaHrEvalScore.staffId)
        if(eiaHrEvalScoreDetail?.roleName){
            map.roleName = eiaHrEvalScoreDetail?.roleName
        }
        map.workExecution = eiaHrEvalScore.workExecution
        map.assessmentMonth = eiaHrEvalScore.assessmentMonth
        map.performance = eiaHrEvalScore.performance
        map.jobSkill = eiaHrEvalScore.jobSkill
        map.workingAttitude = eiaHrEvalScore.workingAttitude
        map.teamSpirit = eiaHrEvalScore.teamSpirit
        map.leadership = eiaHrEvalScore.leadership
        map.finalScore = eiaHrEvalScore.finalScore
        map.proAbility = eiaHrEvalScore.proAbility
        map.leadershipManager = eiaHrEvalScore.leadershipManager
        map.leaderComments = eiaHrEvalScore.leaderComments
        eiaHrEvalData << map
        return  map
    }
    /**
     * 修改助理最终得分
     */
    def eiaHrEvalScoreAssSave(params){
        def jobRatingScore = EiaHrEvalScore.findByIdAndIfDel(params.long("jobRatingScoreId"),false)
        if(jobRatingScore){
            jobRatingScore.properties = params
            jobRatingScore.finalScore = Integer.valueOf(params.leadershipManager)+Integer.valueOf(params.teamSpirit)+Integer.valueOf(params.leadership)+Integer.valueOf(params.workExecution)+Integer.valueOf(params.performance)+Integer.valueOf(params.workingAttitude)+Integer.valueOf(params.proAbility)
            jobRatingScore.save(flush:true,failOnError:true)
        }
    }
    /**
     *员工得分情况列表数据(员工最终得分)
     */
    def getEiaHrEvalEmpDataList(params,session){

        def eiaHrEval = EiaHrEval.findByIdAndIfDel(params.long("eiaHrEvalId"),false)
        def eiaHrEvalScore = EiaHrEvalScore.findByIfDelAndStaffId(false,session.staff.staffId)
        def eiaHrEvalData = []
        def map = [:]
        map.id = eiaHrEvalScore.id
        map.jobRatingType = eiaHrEvalScore.jobRatingType
        map.staffName = eiaHrEvalScore.staffName
        map.jobRatingScoreId = eiaHrEvalScore.id
        map.orgName = eiaHrEvalScore.inputDept
        map.orgId = eiaHrEvalScore.inputDeptId
        map.workExecution = eiaHrEvalScore.workExecution
        map.assessmentMonth = eiaHrEvalScore.assessmentMonth
        map.performance = eiaHrEvalScore.performance
        map.jobSkill = eiaHrEvalScore.jobSkill
        map.workingAttitude = eiaHrEvalScore.workingAttitude
        map.teamSpirit = eiaHrEvalScore.teamSpirit
        map.leadership = eiaHrEvalScore.leadership
        map.finalScore = eiaHrEvalScore.finalScore
        map.proAbility = eiaHrEvalScore.proAbility
        map.leadershipManager = eiaHrEvalScore.leadershipManager
        map.leaderComments = eiaHrEvalScore.leaderComments
        eiaHrEvalData << map
        def dataMap = [:]
        dataMap.items = eiaHrEvalData
        dataMap.orgName = eiaHrEval.orgName
        dataMap.assessmentMonth = eiaHrEval.assessmentMonth
        return  dataMap
    }
    /**
     * 全部最终得分计算
     * @param orgId
     * @param assessmentMonth
     * @param sataffEmpSize
     */
    def finalScoreRatingAll(orgId,assessmentMonth,sataffEmpSize,orgCode){
        Set setEmp=new HashSet();
        /**
         * 员工，助理，副经理全部打分
         */
        /**
         * 判断无打分人员权限
         */
        def staffNotEvalList = []
        def paramStaff = [:]
        paramStaff.orgId = orgId.toString()
        /**
         * 获取不参与打分人员
         */
        def staffNotEval = HttpConnectTools.getResponseJson(HttpUrlConstants.EIA_AUTH_NOT_EVAL_STAFF_INFO, paramStaff)
        JSONArray staffIdNotEval= new JSONArray(staffNotEval)
        for (int i = 0; i < staffIdNotEval.length(); i++) {
            JSONObject staffInfo= staffIdNotEval.getJSONObject(i);
            def staffId = staffInfo.getString("id")
            staffNotEvalList.add(Long.parseLong(staffId))
        }
        def jobRatingDetail
        if(staffNotEvalList){
            jobRatingDetail = EiaHrEvalScoreDetail.findAllByIfDelAndInputDeptIdAndAssessmentMonthAndJobRatingTypeAndIfSubmitAndInputUserIdNotInList(false,orgId,assessmentMonth,GeneConstants.EIA_HR_EVAL_EMP,true,staffNotEvalList)
        }else {
            jobRatingDetail = EiaHrEvalScoreDetail.findAllByIfDelAndInputDeptIdAndAssessmentMonthAndJobRatingTypeAndIfSubmit(false,orgId,assessmentMonth,GeneConstants.EIA_HR_EVAL_EMP,true)
        }
        jobRatingDetail.each{
            setEmp.add(it.staffId)
        }
        /**
         * 根据打分人去重，得到被打分人数
         */
        def jobRatingScoreList = []
        List<Integer> list = new ArrayList<Integer>(setEmp);
        for (int i = 0; i < list.size(); i++) {
            jobRatingScoreList.add(list.get(i))
        }
        /**
         * 计算每一项的最终得分
         */
        def workingAttitudeScore = 0
        def teamSpiritScore = 0
        def cultureCognitionScore = 0
        /**
         * 总经理，部门总经理
         */
        def depManagerList = []
        depManagerList = GeneConstants.JOB_RATING_JG_ROLE_LIST
        /**
         * 取部门经理打分情况(给员工打分)
         */
        for(int j = 0;j<jobRatingScoreList.size();j++){
            /**
             * 员工打分表
             */
            def workingAttitude = 0
            def teamSpirit = 0
            def cultureCognition = 0
            /**
             * 总经理，部门总经理(员工评分)
             */
            def maxMinTeamSpiritList = []
            def maxMinWorkingAttitudeList = []
            def maxMincultureCognitionList = []
            /**
             * 部门经理得分情况
             */
            def jobRatingDetailManger
            if(staffNotEvalList){
                jobRatingDetailManger = EiaHrEvalScoreDetail.findByIfDelAndInputDeptIdAndAssessmentMonthAndJobRatingTypeAndRoleNameInListAndStaffIdAndIfSubmitAndInputUserIdNotInList(false,orgId,assessmentMonth,GeneConstants.EIA_HR_EVAL_EMP,depManagerList,jobRatingScoreList.get(j),true,staffNotEvalList)
            }else {
                jobRatingDetailManger = EiaHrEvalScoreDetail.findByIfDelAndInputDeptIdAndAssessmentMonthAndJobRatingTypeAndRoleNameInListAndStaffIdAndIfSubmit(false,orgId,assessmentMonth,GeneConstants.EIA_HR_EVAL_EMP,depManagerList,jobRatingScoreList.get(j),true)
            }
            if(jobRatingDetailManger){
                /**
                 * 员工互评情况
                 */
                def jobRatingSum
                if(staffNotEvalList){
                    jobRatingSum = EiaHrEvalScoreDetail.findAllByIfDelAndInputDeptIdAndAssessmentMonthAndJobRatingTypeAndStaffIdAndRoleNameNotInListAndIfSubmitAndInputUserIdNotInList(false,orgId,assessmentMonth,GeneConstants.EIA_HR_EVAL_EMP,jobRatingScoreList.get(j),depManagerList,true,staffNotEvalList)
                }else {
                    jobRatingSum = EiaHrEvalScoreDetail.findAllByIfDelAndInputDeptIdAndAssessmentMonthAndJobRatingTypeAndStaffIdAndRoleNameNotInListAndIfSubmit(false,orgId,assessmentMonth,GeneConstants.EIA_HR_EVAL_EMP,jobRatingScoreList.get(j),depManagerList,true,staffNotEvalList)
                }
                jobRatingSum.each{
                    workingAttitude += it?.workingAttitude?:0
                    teamSpirit += it?.teamSpirit?:0
                    cultureCognition += it?.cultureCognition?:0
                    maxMinWorkingAttitudeList.add(it?.workingAttitude?:0)
                    maxMinTeamSpiritList.add(it?.teamSpirit?:0)
                    maxMincultureCognitionList.add(it?.cultureCognition?:0)
                }
                /**
                 * 判断员工人数，如果部门人数小于等于2，则不去掉最高和最低值
                 */
                def staffNum = 0
                if(sataffEmpSize>2){
                    staffNum = sataffEmpSize-2
                }else{
                    staffNum = sataffEmpSize
                }
                /**
                 * 根据打分人员计算最终得分
                 */
                def eiaHrEvalScore = new EiaHrEvalScore()
                eiaHrEvalScore.assessmentMonth = assessmentMonth
                eiaHrEvalScore.staffId = jobRatingScoreList.get(j)
                eiaHrEvalScore.staffName = jobRatingDetailManger.staffName
                eiaHrEvalScore.jobRatingType =GeneConstants.EIA_HR_EVAL_EMP
                if(maxMinWorkingAttitudeList){
                    /**
                     * 人数小于等于2
                     */
                    if(sataffEmpSize>2) {
                        /**
                         * 员工互评等于去掉最高、最低除以人数-2 ,员工互评40%
                         */
                        def totalSum = (workingAttitude-Collections.max(maxMinWorkingAttitudeList) - Collections.min(maxMinWorkingAttitudeList))/staffNum
                        workingAttitudeScore = Math.round((totalSum*0.4)+(jobRatingDetailManger?.workingAttitude*0.6))
                        eiaHrEvalScore.workingAttitude = workingAttitudeScore
                    }else{
                        def totalSum = workingAttitude/staffNum
                        workingAttitudeScore = Math.round(totalSum)
                        eiaHrEvalScore.workingAttitude = workingAttitudeScore
                    }
                }
                if(maxMinTeamSpiritList){
                    /**
                     * 人数小于等于2
                     */
                    if(sataffEmpSize>2){
                        def totalSum = (teamSpirit-Collections.max(maxMinTeamSpiritList) -Collections.min(maxMinTeamSpiritList))/staffNum
                        teamSpiritScore = Math.round((totalSum*0.4)+(jobRatingDetailManger?.teamSpirit*0.6))
                        eiaHrEvalScore.teamSpirit = teamSpiritScore
                    }else{
                        def totalSum = teamSpirit/staffNum
                        teamSpiritScore = Math.round((totalSum*0.4)+(jobRatingDetailManger?.teamSpirit*0.6))
                        eiaHrEvalScore.teamSpirit = teamSpiritScore
                    }

                }
                if(maxMincultureCognitionList){
                    /**
                     * 人数小于等于2
                     */
                    if(sataffEmpSize>2){
                        def totalSum = (cultureCognition-Collections.max(maxMincultureCognitionList) -Collections.min(maxMincultureCognitionList))/staffNum
                        cultureCognitionScore = Math.round((totalSum*0.4)+(jobRatingDetailManger?.cultureCognition*0.6))
                        eiaHrEvalScore.cultureCognition = cultureCognitionScore
                    }else{
                        def totalSum = cultureCognition/staffNum
                        cultureCognitionScore = Math.round((totalSum*0.4)+(jobRatingDetailManger?.cultureCognition*0.6))
                        eiaHrEvalScore.cultureCognition = cultureCognitionScore
                    }

                }
                eiaHrEvalScore.workExecution = jobRatingDetailManger?.workExecution
                eiaHrEvalScore.performance = jobRatingDetailManger?.performance
                eiaHrEvalScore.jobSkill = jobRatingDetailManger?.jobSkill
                eiaHrEvalScore.leaderComments = jobRatingDetailManger?.leaderComments
                eiaHrEvalScore.finalScore = cultureCognitionScore+teamSpiritScore+workingAttitudeScore+jobRatingDetailManger?.workExecution+jobRatingDetailManger?.performance+jobRatingDetailManger?.jobSkill
                eiaHrEvalScore.inputDeptId = Long.parseLong(orgId.toString())
                def eiaHrEval = EiaHrEval.findByIfDelAndAssessmentMonthAndOrgId(false,assessmentMonth,orgId)
                eiaHrEvalScore.inputDept = eiaHrEval?.orgName
                eiaHrEvalScore.inputDeptCode = eiaHrEval?.orgCode
                eiaHrEvalScore.save(flush: true, failOnError: true)
            }
        }
        /**
         * 助理全部打分
         */
        Set setAss=new HashSet();
        def jobRatingDetailAss
        if(staffNotEvalList){
            jobRatingDetailAss = EiaHrEvalScoreDetail.findAllByIfDelAndInputDeptIdAndAssessmentMonthAndJobRatingTypeAndIfSubmitAndInputUserIdNotInList(false,orgId,assessmentMonth,GeneConstants.EIA_HR_EVAL_ASS,true,staffNotEvalList)
        }else {
            jobRatingDetailAss = EiaHrEvalScoreDetail.findAllByIfDelAndInputDeptIdAndAssessmentMonthAndJobRatingTypeAndIfSubmit(false,orgId,assessmentMonth,GeneConstants.EIA_HR_EVAL_ASS,true)
        }
        jobRatingDetailAss.each{
            setAss.add(it.staffId)
        }
        def jobRatingScoreAssList = []
        List<Integer> listAss = new ArrayList<Integer>(setAss);
        for (int i = 0; i < listAss.size(); i++) {
            jobRatingScoreAssList.add(listAss.get(i))
        }
        def leadershipScore = 0
        def proAbilityScore = 0

        for(int k =0;k<jobRatingScoreAssList.size();k++){
            def leadership = 0
            def teamSpirit = 0
            def proAbility = 0
            /**
             * 团队精神
             */
            def maxMinleadershipList = []
            /**
             * 专业能力
             */
            def maxMinProAbilityList = []
            def maxMinTeamSpiritList = []
            /**
             * 给助理打分
             */
            /**
             * 总经理，部门总经理(助理评分)
             */
            def jobRatingDetailManger
            /**
             * 判断是否为分公司，因为分公司没有总经理，所以总经理不能打分
             */

            boolean evatlStatusBor = orgCode.contains("FGS");
            boolean evatlStatusHg= orgCode.contains("HGB");
            if(evatlStatusBor || evatlStatusHg){
                if(staffNotEvalList){
                    jobRatingDetailManger = EiaHrEvalScoreDetail.findByIfDelAndInputDeptIdAndAssessmentMonthAndJobRatingTypeAndStaffIdAndIfSubmitAndInputUserIdNotInList(false,orgId,assessmentMonth,GeneConstants.EIA_HR_EVAL_ASS,jobRatingScoreAssList.get(k),true,staffNotEvalList)
                }else {
                    jobRatingDetailManger = EiaHrEvalScoreDetail.findByIfDelAndInputDeptIdAndAssessmentMonthAndJobRatingTypeAndStaffIdAndIfSubmit(false,orgId,assessmentMonth,GeneConstants.EIA_HR_EVAL_ASS,jobRatingScoreAssList.get(k),true)
                }
            }else{
                if(staffNotEvalList){
                    jobRatingDetailManger = EiaHrEvalScoreDetail.findByIfDelAndInputDeptIdAndAssessmentMonthAndJobRatingTypeAndRoleNameInListAndStaffIdAndIfSubmitAndInputUserIdNotInList(false,orgId,assessmentMonth,GeneConstants.EIA_HR_EVAL_ASS,depManagerList,jobRatingScoreAssList.get(k),true,staffNotEvalList)
                }else {
                    jobRatingDetailManger = EiaHrEvalScoreDetail.findByIfDelAndInputDeptIdAndAssessmentMonthAndJobRatingTypeAndRoleNameInListAndStaffIdAndIfSubmit(false,orgId,assessmentMonth,GeneConstants.EIA_HR_EVAL_ASS,depManagerList,jobRatingScoreAssList.get(k),true)
                }
            }

            def jobRatingSum
            if(jobRatingDetailManger){
                if(staffNotEvalList){
                    jobRatingSum = EiaHrEvalScoreDetail.findAllByIfDelAndInputDeptIdAndAssessmentMonthAndJobRatingTypeAndStaffIdAndRoleNameNotInListAndIfSubmitAndInputUserIdNotInList(false,orgId,assessmentMonth,GeneConstants.EIA_HR_EVAL_ASS,jobRatingScoreAssList.get(k),depManagerList,true,staffNotEvalList)
                }else {
                    jobRatingSum = EiaHrEvalScoreDetail.findAllByIfDelAndInputDeptIdAndAssessmentMonthAndJobRatingTypeAndStaffIdAndRoleNameNotInListAndIfSubmit(false,orgId,assessmentMonth,GeneConstants.EIA_HR_EVAL_ASS,jobRatingScoreAssList.get(k),depManagerList,true)
                }
                sataffEmpSize = jobRatingSum.size()
                jobRatingSum.each{
                    teamSpirit += it.teamSpirit?:0
                    leadership += it.leadership?:0
                    proAbility += it.proAbility?:0
                    maxMinleadershipList.add(it.leadership?:0)
                    maxMinTeamSpiritList.add(it.teamSpirit?:0)
                    maxMinProAbilityList.add(it.proAbility?:0)
                }
                def staffNum = 0
                if(sataffEmpSize>2){
                    staffNum = sataffEmpSize-2
                }else{
                    staffNum = sataffEmpSize
                }
                def eiaHrEvalScore = new EiaHrEvalScore()
                eiaHrEvalScore.assessmentMonth = assessmentMonth
                eiaHrEvalScore.staffId = jobRatingScoreAssList.get(k)
                eiaHrEvalScore.staffName = jobRatingDetailManger.staffName
                eiaHrEvalScore.jobRatingType =GeneConstants.EIA_HR_EVAL_ASS
                if(maxMinleadershipList){
                    /**
                     * 人数小于等于2
                     */
                    if(sataffEmpSize>2) {
                        def totalSum = (leadership-Collections.max(maxMinleadershipList) -Collections.min(maxMinleadershipList))/staffNum
                        leadershipScore =  Math.round(totalSum)
                        eiaHrEvalScore.leadership = leadershipScore
                    }else {
                        def totalSum = leadership/staffNum
                        leadershipScore =  Math.round(totalSum)
                        eiaHrEvalScore.leadership = leadershipScore
                    }

                }
                if(maxMinTeamSpiritList){
                    /**
                     * 人数小于等于2
                     */
                    if(sataffEmpSize>2) {
                        def totalSum = (teamSpirit-Collections.max(maxMinTeamSpiritList) -Collections.min(maxMinTeamSpiritList))/staffNum
                        teamSpiritScore =  Math.round(totalSum)
                        eiaHrEvalScore.teamSpirit = teamSpiritScore
                    }else{
                        def totalSum = teamSpirit/staffNum
                        teamSpiritScore =  Math.round(totalSum)
                        eiaHrEvalScore.teamSpirit = teamSpiritScore
                    }

                }
                if(maxMinProAbilityList){
                    /**
                     * 人数小于等于2
                     */
                    if(sataffEmpSize>2) {
                        def totalSum = (proAbility-Collections.max(maxMinProAbilityList) -Collections.min(maxMinProAbilityList))/staffNum
                        proAbilityScore =  Math.round(totalSum)
                        eiaHrEvalScore.proAbility = proAbilityScore
                    }else{
                        def totalSum = proAbility/staffNum
                        proAbilityScore =  Math.round(totalSum)
                        eiaHrEvalScore.proAbility = proAbilityScore
                    }
                }
                eiaHrEvalScore.workExecution = jobRatingDetailManger?.workExecution?:20
                eiaHrEvalScore.performance = jobRatingDetailManger?.performance?:20
                eiaHrEvalScore.workingAttitude = jobRatingDetailManger?.workingAttitude?:20
                eiaHrEvalScore.leaderComments = jobRatingDetailManger?.leaderComments
                if(evatlStatusBor || evatlStatusHg){
                    eiaHrEvalScore.leadershipManager = 10
                    /**
                     * 分工，没有部门经理，只有部门助理，所以分公司人员担保值统计员工人员打分，默认总经理打分为满分
                     */
                    eiaHrEvalScore.finalScore = leadershipScore + teamSpiritScore + proAbilityScore+70
                }else{
                    eiaHrEvalScore.leadershipManager = jobRatingDetailManger?.leadership
                    eiaHrEvalScore.finalScore =  leadershipScore + teamSpiritScore + proAbilityScore +jobRatingDetailManger?.workExecution + jobRatingDetailManger?.performance + jobRatingDetailManger?.workingAttitude +jobRatingDetailManger?.leadership
                }

                eiaHrEvalScore.inputDeptId = Long.parseLong(orgId.toString())
                def eiaHrEval = EiaHrEval.findByIfDelAndAssessmentMonthAndOrgId(false,assessmentMonth,orgId)
                eiaHrEvalScore.inputDept = eiaHrEval?.orgName
                eiaHrEvalScore.inputDeptCode = eiaHrEval?.orgCode
                eiaHrEvalScore.save(flush: true, failOnError: true)
            }
        }
    }

    /**
     *员工最终得分情况数据(员工最终得分，用于有权限的人点击操作中的员工得分详情)
     */
    def getEiaHrEvalEmpDataDetailList(params,session){

        def eiaHrEval = EiaHrEval.findByIdAndIfDel(params.long("eiaHrEvalId"),false)
        /**
         * 获取当前直接领导
         */
        def param = [:]
        param.orgId = eiaHrEval.orgId.toString()
        def staffLenderJson = HttpConnectTools.getResponseJson(HttpUrlConstants.EIA_AUTH_ORG_STAFF_LENDER_INFO, param)
        JSONArray staffLenderInfo = new JSONArray(staffLenderJson)
        def eiaHrEvalScore = EiaHrEvalScore.findAllByIfDelAndInputDeptIdAndAssessmentMonthAndJobRatingType(false,eiaHrEval.orgId,eiaHrEval.assessmentMonth,eiaHrEval.jobRatingType)
        def eiaHrEvalData = []
        eiaHrEvalScore.each{
            def map = [:]
            map.id = it.id
            map.jobRatingType = it.jobRatingType
            map.staffName = it.staffName
            map.staffId = it.staffId
            map.orgName = it.inputDept
            map.orgId = it.inputDeptId
            map.workExecution = it.workExecution
            map.assessmentMonth = it.assessmentMonth
            map.performance = it.performance
            map.jobSkill = it.jobSkill
            map.workingAttitude = it.workingAttitude
            map.teamSpirit = it.teamSpirit
            map.leadership = it.leadership
            map.finalScore = it.finalScore
            map.proAbility = it.proAbility
            map.leadershipManager = it.leadershipManager
            map.leaderComments = it.leaderComments
            map.cultureCognition = it.cultureCognition
            eiaHrEvalData << map
        }
        def dataMap = [:]
        dataMap.items = eiaHrEvalData
        dataMap.total = eiaHrEvalScore.size()
        dataMap.orgName = eiaHrEval.orgName
        dataMap.assessmentMonth = eiaHrEval.assessmentMonth
        dataMap.leader = staffLenderInfo.getJSONObject(0)getString("staffName")
        return  dataMap
    }
}
