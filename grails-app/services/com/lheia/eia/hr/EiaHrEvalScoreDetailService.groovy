package com.lheia.eia.hr

import com.lheia.eia.common.FuncConstants
import com.lheia.eia.common.GeneConstants
import com.lheia.eia.common.HttpMesConstants
import com.lheia.eia.common.HttpUrlConstants
import com.lheia.eia.tools.HttpConnectTools
import grails.gorm.transactions.Transactional
import org.grails.web.json.JSONArray
import org.grails.web.json.JSONObject

@Transactional
class EiaHrEvalScoreDetailService {

    def empRatingEmpInput(params,session,roleName){
        def staffOrgList = []
        def staffIdList = []
        def staffNameList = []
        def jobRatingDetailsList
        /**
         * 登录角色部门总经理
         */
        def showType = true
        /**
         * 获取机构下用户
         */
        /**
         * 不参加月考人员
         */
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
         * 判断是否有打分权限，用于多角色切换中
         */
        def eiaHrOrgStaffEval = EiaHrOrgStaffEval.findAllByIfDelAndOrgId(false,eiaHrEval.orgId)
        if(eiaHrOrgStaffEval.size()>0){
            eiaHrOrgStaffEval.each{
                staffNotEvalList.add(it.staffId)
            }
        }
        staffNotEvalList.add(session.staff.staffId)
        param.evalNot =   staffNotEvalList.toString()
        /**
         * 员工打分
         */
        def staffJson
        /**
         * 取直属领导
         */
        def staffLenderJson = HttpConnectTools.getResponseJson(HttpUrlConstants.EIA_AUTH_ORG_STAFF_LENDER_INFO, param)
        if(eiaHrEval?.jobRatingType.equals(GeneConstants.EIA_HR_EVAL_EMP)){
            staffJson = HttpConnectTools.getResponseJson(HttpUrlConstants.EIA_AUTH_ORG_STAFF_INFO, param)
        }else{
            /**
             * 助理打分
             */
            /**
             * ass 代表查询助理角色的人员
             */
            param.ass = '1'
            staffJson = HttpConnectTools.getResponseJson(HttpUrlConstants.EIA_AUTH_ORG_STAFF_INFO, param)
        }
        if(HttpMesConstants.MSG_DATA_NULL.equals(staffJson)){
            return null
        }
        JSONArray staffInfo = new JSONArray(staffJson)
        JSONArray staffLenderInfo = new JSONArray(staffLenderJson)
        def eiaHrEvalData = []
        for (int i = 0; i < staffInfo.length(); i++) {
            /**
             * 取所有的员工信息
             */
            JSONObject staffJsonJect = staffInfo.getJSONObject(i);
            def staffId = staffJsonJect.getString("id")
            def staffName = staffJsonJect.getString("staffName")
            def map = [:]
            jobRatingDetailsList = EiaHrEvalScoreDetail.findByIfDelAndAssessmentMonthAndJobRatingTypeAndStaffIdAndInputDeptIdAndInputUserId(false,eiaHrEval?.assessmentMonth,eiaHrEval?.jobRatingType,staffId,eiaHrEval.orgId,session.staff.staffId)
            if(jobRatingDetailsList){
                staffOrgList.add(jobRatingDetailsList)
                staffIdList.add(jobRatingDetailsList.staffId)
                map.id = jobRatingDetailsList.id
                map.staffId=jobRatingDetailsList.staffId
                map.staffName = jobRatingDetailsList.staffName
                map.workingAttitude = jobRatingDetailsList.workingAttitude
                map.teamSpirit = jobRatingDetailsList.teamSpirit
                map.cultureCognition = jobRatingDetailsList.cultureCognition
                map.leadership = jobRatingDetailsList.leadership
                map.proAbility = jobRatingDetailsList.proAbility
                map.workExecution = jobRatingDetailsList.workExecution
                map.performance = jobRatingDetailsList.performance
                map.jobSkill = jobRatingDetailsList.jobSkill
                staffNameList.add(jobRatingDetailsList.staffName)
            }else{
                def eiaHrEvalScoreDetail = new EiaHrEvalScoreDetail()
                eiaHrEvalScoreDetail.staffName = staffName
                eiaHrEvalScoreDetail.staffId = Long.parseLong(staffId)
                staffOrgList.add(eiaHrEvalScoreDetail)
                staffIdList.add(staffId)
                map.id = staffId
                map.staffId=staffId
                map.staffName = staffName
                map.workingAttitude = ""
                map.teamSpirit = ""
                map.cultureCognition = ""
                map.leadership = ""
                map.proAbility = ""
                map.workExecution = ""
                map.performance = ""
                map.jobSkill = ""
                staffNameList.add(staffName)
            }
            eiaHrEvalData << map
        }
        def dataMap = [:]
        dataMap.items = eiaHrEvalData
        dataMap.orgName = eiaHrEval.orgName
        dataMap.assessmentMonth = eiaHrEval.assessmentMonth
        if(staffLenderInfo){
            dataMap.leader = staffLenderInfo.getJSONObject(0)getString("staffName")
        }
        dataMap.total = staffIdList.size()
        dataMap.showType = true
        dataMap.staffIdList = staffIdList
        dataMap.staffNameList = staffNameList
        return dataMap
    }
    /**
     * 人员打分保存
     */
    def eiaHrEvalScoreDetailSave(params,session){
        def eiaHrEval = EiaHrEval.findByIdAndIfDel(params.long("eiaHrEvalId"),false)
        def jobRatingName
        jobRatingName = params.jobRatingName.replace("[","").replace("]","")
        def jobRatingNameList = jobRatingName.split(',')
        /**
         * 获取人员名称
         */
        def staffName
        staffName = params.staffNameList.replace("[","").replace("]","")
        def staffNameList = staffName.split(',')
        def workExecutionList
        if(params.workExecution){
            workExecutionList = params.workExecution.toString()
        }
        def performanceList
        if(params.performance){
            performanceList = params.performance.toString()
        }
        def jobSkillList
        if(params.jobSkill){
            jobSkillList = params.jobSkill.toString()
        }
        def leadershipDeptList
        if(params.leadership){
            leadershipDeptList = params.leadership.toString()
        }
        /**
         * 员工互评
         */
        def workingAttitudeList
        if(params.workingAttitude){
            workingAttitudeList = params.workingAttitude.toString()
        }
        def teamSpiritList
        if(params.teamSpirit){
            teamSpiritList = params.teamSpirit.toString()
        }
        def cultureCognitionList
        if(params.cultureCognition){
            cultureCognitionList = params.cultureCognition.toString()
        }
        def proAbilityList
        if(params.proAbility){
            proAbilityList = params.proAbility.toString()
        }
        def depManagerList = []
        depManagerList = GeneConstants.JOB_RATING_JG_ROLE_LIST
        for(int i=0;i< jobRatingNameList.size();i++){
            def assessmentMonth = eiaHrEval.assessmentMonth
            def jobRatingType = eiaHrEval.jobRatingType
            def eiaHrEvalScoreDetail
            /**
             * 判断登录用户是什么角色
             */
            def param = [:]
            param.orgId = session.staff.orgId.toString()
            param.staffId = session.staff.staffId.toString()
            def staffJson = HttpConnectTools.getResponseJson(HttpUrlConstants.EIA_AUTH_ORG_STAFF_ROLE_INFO, param)
            JSONArray staffInfo = new JSONArray(staffJson)
            def roleName
            def roleCode
            for (int j = 0; j < staffInfo.length(); j++) {
                JSONObject staffJsonJect = staffInfo.getJSONObject(j);
                roleName = staffJsonJect.getString("roleName")
                roleCode = staffJsonJect.getString("roleCode")
            }
            def staffJobRatingId = Long.parseLong(jobRatingNameList.getAt(i).toString())
            eiaHrEvalScoreDetail = EiaHrEvalScoreDetail.findAllByIfDelAndStaffIdAndAssessmentMonthAndJobRatingTypeAndInputUserId(false,staffJobRatingId,assessmentMonth,jobRatingType,session.staff.staffId)
            if(eiaHrEvalScoreDetail.size()>0){
                eiaHrEvalScoreDetail = EiaHrEvalScoreDetail.findByIfDelAndStaffIdAndAssessmentMonthAndJobRatingTypeAndInputUserId(false,staffJobRatingId,assessmentMonth,jobRatingType,session.staff.staffId)
            }else {
                eiaHrEvalScoreDetail = new EiaHrEvalScoreDetail()
            }

            eiaHrEvalScoreDetail.staffName = staffNameList.getAt(i)
            eiaHrEvalScoreDetail.staffId = Long.parseLong(jobRatingNameList.getAt(i).toString())
            eiaHrEvalScoreDetail.assessmentMonth = assessmentMonth

            eiaHrEvalScoreDetail.roleName = roleName
            eiaHrEvalScoreDetail.roleCode =roleCode
            eiaHrEvalScoreDetail.inputDept = eiaHrEval.orgName
            eiaHrEvalScoreDetail.inputDeptCode =  eiaHrEval.orgCode
            eiaHrEvalScoreDetail.inputDeptId = eiaHrEval.orgId
            eiaHrEvalScoreDetail.inputUser = session.staff.staffName
            eiaHrEvalScoreDetail.inputUserId = Long.parseLong(session.staff.staffId)
            /**
             * 总经理给员工打分
             */
            /**
             * 表一（员工互评）
             */
            if(jobRatingType.equals( GeneConstants.EIA_HR_EVAL_EMP)){
                eiaHrEvalScoreDetail.jobRatingType = GeneConstants.EIA_HR_EVAL_EMP
                /**
                 * 部门经理打分(经理给员工的打分),合规助理，分公司负责人
                 */
                if(depManagerList.contains(roleName)){
                    if(workExecutionList){
                        eiaHrEvalScoreDetail.workExecution = Integer.valueOf(workExecutionList.replace("[","").replace("]","").split(',').getAt(i).trim()?:0)
                    }
                    if(performanceList){
                        eiaHrEvalScoreDetail.performance = Integer.valueOf(performanceList.replace("[","").replace("]","").split(',').getAt(i).trim()?:0)
                    }
                    if(jobSkillList){
                        eiaHrEvalScoreDetail.jobSkill = Integer.valueOf(jobSkillList.replace("[","").replace("]","").split(',').getAt(i).trim()?:0)
                    }
                    if(workingAttitudeList){
                        eiaHrEvalScoreDetail.workingAttitude = Integer.valueOf(workingAttitudeList.replace("[","").replace("]","").split(',').getAt(i).trim()?:0)
                    }
                    if(teamSpiritList){
                        eiaHrEvalScoreDetail.teamSpirit = Integer.valueOf(teamSpiritList.replace("[","").replace("]","").split(',').getAt(i).trim()?:0)
                    }
                    if(cultureCognitionList){
                        eiaHrEvalScoreDetail.cultureCognition = Integer.valueOf(cultureCognitionList.replace("[","").replace("]","").split(',').getAt(i).trim()?:0)
                    }
                }else {
                    /**
                     * 员工互评，助理和员工打分
                     */
                    if(workingAttitudeList){
                        eiaHrEvalScoreDetail.workingAttitude = Integer.valueOf(workingAttitudeList.replace("[","").replace("]","").split(',').getAt(i).trim()?:0)
                    }
                    if(teamSpiritList){
                        eiaHrEvalScoreDetail.teamSpirit = Integer.valueOf(teamSpiritList.replace("[","").replace("]","").split(',').getAt(i).trim()?:0)
                    }
                    if(cultureCognitionList){
                        eiaHrEvalScoreDetail.cultureCognition = Integer.valueOf(cultureCognitionList.replace("[","").replace("]","").split(',').getAt(i).trim()?:0)
                    }
                }
            }else{
                /**
                 * 助理评分表
                 */
                eiaHrEvalScoreDetail.jobRatingType = GeneConstants.EIA_HR_EVAL_ASS
                /**
                 * 给助理打分，部门经理和助理打分
                 */
                if(depManagerList.contains(roleName)){
                    if(workingAttitudeList){
                        eiaHrEvalScoreDetail.workingAttitude = Integer.valueOf(workingAttitudeList.replace("[","").replace("]","").split(',').getAt(i).trim()?:0)
                    }
                    if(performanceList){
                        eiaHrEvalScoreDetail.performance = Integer.valueOf(performanceList.replace("[","").replace("]","").split(',').getAt(i).trim()?:0)
                    }
                    if(workingAttitudeList){
                        eiaHrEvalScoreDetail.workExecution = Integer.valueOf(workExecutionList.replace("[","").replace("]","").split(',').getAt(i).trim()?:0)
                    }
                    if(leadershipDeptList){
                        eiaHrEvalScoreDetail.leadership = Integer.valueOf(leadershipDeptList.replace("[","").replace("]","").split(',').getAt(i).trim()?:0)
                    }
                }else {
                    /**
                     * 员工给助理打分，助理给助理打分
                     */
                    if(teamSpiritList){
                        eiaHrEvalScoreDetail.teamSpirit = Integer.valueOf(teamSpiritList.replace("[","").replace("]","").split(',').getAt(i).trim()?:0)
                    }
                    if(leadershipDeptList){
                        eiaHrEvalScoreDetail.leadership = Integer.valueOf(leadershipDeptList.replace("[","").replace("]","").split(',').getAt(i).trim()?:0)
                    }
                    if(proAbilityList){
                        eiaHrEvalScoreDetail.proAbility = Integer.valueOf(proAbilityList.replace("[","").replace("]","").split(',').getAt(i).trim()?:0)
                    }
                }
            }
            eiaHrEvalScoreDetail.save(flush:true,failOnError:true)
        }
        return true
    }
    /**
     * 人员打分详情
     */
    def empRatingEmpDetail(params,session,roleName){
        def staffOrgList = []
        def staffIdList = []
        def jobRatingDetailsList
        /**
         * 登录角色部门总经理
         */
        def showType = true
        /**
         * 获取机构下用户
         */
        /**
         * 不参加月考人员
         */
        def eiaHrEval = EiaHrEval.findByIfDel(false)
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
        staffNotEvalList.add(session.staff.staffId)
        param.evalNot =   staffNotEvalList.toString()
        /**
         * 员工打分
         */
        def staffJson
        if(eiaHrEval?.jobRatingType.equals(GeneConstants.EIA_HR_EVAL_EMP)){
            staffJson = HttpConnectTools.getResponseJson(HttpUrlConstants.EIA_AUTH_ORG_STAFF_INFO, param)
        }else{
            /**
             * 助理打分
             */
            /**
             * ass 代表查询助理角色的人员
             */
            param.ass = '1'
            staffJson = HttpConnectTools.getResponseJson(HttpUrlConstants.EIA_AUTH_ORG_STAFF_INFO, param)
        }
        if(HttpMesConstants.MSG_DATA_NULL.equals(staffJson)){
            return
        }
        JSONArray staffInfo = new JSONArray(staffJson)
        for (int i = 0; i < staffInfo.length(); i++) {

            JSONObject staffJsonJect = staffInfo.getJSONObject(i);
            def staffId = staffJsonJect.getString("id")
            def staffName = staffJsonJect.getString("staffName")
            jobRatingDetailsList = EiaHrEvalScoreDetail.findByIfDelAndAssessmentMonthAndJobRatingTypeAndInputDeptIdAndStaffIdAndInputUserId(false,eiaHrEval?.assessmentMonth,eiaHrEval?.jobRatingType,eiaHrEval?.orgId,staffId,session.staff.id)
            if(jobRatingDetailsList){
                staffOrgList.add(jobRatingDetailsList)
                staffIdList.add(jobRatingDetailsList.staffId)
            }else{
                def eiaHrEvalScoreDetail = new EiaHrEvalScoreDetail()
                eiaHrEvalScoreDetail.staffName = staffName
                eiaHrEvalScoreDetail.staffId = staffId
                staffOrgList.add(eiaHrEvalScoreDetail)
                staffIdList.add(staffId)
            }
        }
        /**
         * 判断类型调整不同的页面
         */
        if(GeneConstants.JOB_RATING_PM_ROLE.equals(roleName) && eiaHrEval.jobRatingType.equals(GeneConstants.EIA_HR_EVAL_EMP)){
            /**
             * 员工给员工打分
             */
            [staffOrgList:staffOrgList,count:staffOrgList.size(),staffIdList:staffIdList,assessmentMonth:eiaHrEval?.assessmentMonth,jobRatingType:eiaHrEval?.jobRatingType,showType:showType,jobRatingDetailsList:jobRatingDetailsList]
        }else if(GeneConstants.JOB_RATING_PM_ROLE.equals(roleName) && eiaHrEval.jobRatingType.equals(GeneConstants.EIA_HR_EVAL_ASS)){
            /**
             * 员工给助理打分
             */
            return [staffOrgList:staffOrgList,count:staffOrgList.size(),staffIdList:staffIdList,assessmentMonth:eiaHrEval?.assessmentMonth,jobRatingType:eiaHrEval?.jobRatingType,showType:showType]
        }else if(GeneConstants.JOB_RATING_ROLE.equals(roleName) && eiaHrEval.jobRatingType.equals(GeneConstants.EIA_HR_EVAL_EMP)){
            /**
             * 经理给员工打分
             */
            return [staffOrgList:staffOrgList,count:staffOrgList.size(),staffIdList:staffIdList,assessmentMonth:eiaHrEval?.assessmentMonth,jobRatingType:eiaHrEval?.jobRatingType,showType:showType]
        }else if(GeneConstants.JOB_RATING_ROLE.equals(roleName) && eiaHrEval.jobRatingType.equals(GeneConstants.EIA_HR_EVAL_ASS)){
            /**
             * 经理给助理打分
             */
            return [staffOrgList:staffOrgList,count:staffOrgList.size(),staffIdList:staffIdList,assessmentMonth:eiaHrEval?.assessmentMonth,jobRatingType:eiaHrEval?.jobRatingType,showType:showType]
        }
    }
    /**
     * 员工评语保存
     */
    def leaderCommentsSave(params){
        if(params.eiaHrEvalScoreId!="undefined"){
            def eiaHrEvalScore = EiaHrEvalScore.findByIfDelAndId(false,params.long("eiaHrEvalScoreId"))
            eiaHrEvalScore.leaderComments = params.leaderComments
            eiaHrEvalScore.save(flush:true,failOnError:true)
        }
        if(params.eiaHrEvalScoreDetailId!="undefined"){
            def eiaHrEvalScoreDetail = EiaHrEvalScoreDetail.findByIfDelAndId(false,params.long("eiaHrEvalScoreDetailId"))
            eiaHrEvalScoreDetail.leaderComments = params.leaderComments
            eiaHrEvalScoreDetail.save(flush:true,failOnError:true)
        }
        return  true
    }
    /**
     *总经理或管理员点击员工出现该员工详细打分信息
     */
    def eiaHrEvalEmpDetailQueryPage(params,session){
        int page
        int limit
        if (params.page && params.limit) {
            page = params.int('page') - 1
            limit = params.int('limit')
        }
        def eiaHrEvalDataList = EiaHrEvalScoreDetail.createCriteria().list(max: limit, offset: page * limit) {
            /**
             * 查看全部的客户数据
             */
            if (!session?.staff?.funcCode?.contains(FuncConstants.EIA_XZGL_YDKH_VIEWALL)) {
                /**
                 * 查看本部门客户数据
                 */
                if (session?.staff?.funcCode?.contains(FuncConstants.EIA_XZGL_NDKH_VIEWDEPT)) {
                    like("inputDeptCode", "%"+ session.staff.orgCode+ "%")
                }

            }
           eq("staffId",params.long("staffId"))
            eq("ifDel", false)
            order("id", "desc")
        }
        def eiaHrEvalData = []
        eiaHrEvalDataList.each {
            def map = [:]
            map.id = it?.id
            map.orgName = it?.inputDept
            map.staffName = it?.staffName
            map.assessmentMonth = it?.assessmentMonth
            map.workingAttitude = it?.workingAttitude
            map.cultureCognition = it?.cultureCognition
            map.inputUser = it?.inputUser
            map.teamSpirit = it?.teamSpirit
            map.leadership = it?.leadership
            map.proAbility = it?.proAbility
            eiaHrEvalData << map
        }
        def dataMap = [:]
        dataMap.data = eiaHrEvalData
        dataMap.total = eiaHrEvalDataList.totalCount
        return dataMap
    }
    /**
     * 提交，提交后不能编辑
     */
    def empRatingSubmit(params,session){
        def eiaHrEval =  EiaHrEval.findByIdAndIfDel(params.long("eiaHrEvalId"),false)
        def eiaHrEvalScoreDetailList =  EiaHrEvalScoreDetail.findAllByIfDelAndInputDeptIdAndAssessmentMonthAndJobRatingTypeAndInputUserId(false,eiaHrEval?.orgId,eiaHrEval?.assessmentMonth,eiaHrEval?.jobRatingType,session.staff.staffId)
        eiaHrEvalScoreDetailList.each{
            it.ifSubmit = true
        }
        return  true
    }
}
