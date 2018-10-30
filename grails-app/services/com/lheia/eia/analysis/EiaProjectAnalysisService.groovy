package com.lheia.eia.analysis

import com.lheia.eia.common.FuncConstants
import com.lheia.eia.common.GeneConstants
import com.lheia.eia.common.HttpMesConstants
import com.lheia.eia.common.HttpUrlConstants
import com.lheia.eia.common.WorkFlowConstants
import com.lheia.eia.contract.EiaContract
import com.lheia.eia.finance.EiaIncomeOut
import com.lheia.eia.finance.EiaInvoice
import com.lheia.eia.lab.EiaLabOffer
import com.lheia.eia.project.EiaProject
import com.lheia.eia.project.EiaProjectPlan
import com.lheia.eia.project.EiaProjectPlanItem
import com.lheia.eia.task.EiaTask
import com.lheia.eia.tools.HttpConnectTools
import com.lheia.eia.tools.JsonHandler
import com.lheia.eia.workflow.EiaWorkFlowBusi
import grails.gorm.transactions.Transactional

import java.math.RoundingMode
import java.text.SimpleDateFormat

@Transactional
class EiaProjectAnalysisService {

    /**
     * 获取项目列表
     */
    def eiaProjectQuery(params) {
        Calendar cale = null
        cale = Calendar.getInstance()
        int year = cale.get(Calendar.YEAR)
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd")
        int page
        int limit
        if (params.page && params.limit) {
            page = params.int('page') - 1
            limit = params.int('limit')
        }
        def startDate
        def endDate
        if (params.startDate) {
            startDate = sdf.parse(params.startDate)
        } else {
            startDate = sdf.parse(year + "-01-01 00:00:00")
        }
        if (params.endDate) {
            endDate = sdf.parse(params.endDate)
        } else {
            endDate = sdf.parse(year + "-12-31 23:59:59")
        }
        def eiaProjectIds = []
        /** 统计进行中、未归档、已归档的项目 */
        if (params.viewType == 'projectDoing' || params.viewType == 'projectUnComp' || params.viewType == 'projectComp') {
            def workFlowBusiList = EiaWorkFlowBusi.createCriteria().list() {
                if (startDate) {
                    ge("dateCreated", startDate)
                }
                if (endDate) {
                    le("dateCreated", endDate)
                }
                /** 进行中的项目查询条件 */
                if (params.viewType == 'projectDoing') {
                    ne("nodesCode", WorkFlowConstants.NODE_CODE_BPBSB)
                    'in'("workFlowState", [WorkFlowConstants.WORKFLOW_START, 'doing'])
                }
                /** 未归档的项目查询条件 */
                else if (params.viewType == 'projectUnComp') {
                    eq("nodesCode", WorkFlowConstants.NODE_CODE_BPBSB)
                    'in'("workFlowState", [WorkFlowConstants.WORKFLOW_START, 'doing'])
                }
                /** 已归档的项目查询条件 */
                else if (params.viewType == 'projectComp') {
                    eq("workFlowState", WorkFlowConstants.WORKFLOW_END)
                }
                eq("inputDeptId", Long.parseLong(params.inputDeptId))
                eq("inputUserId", Long.parseLong(params.inputUserId))
                eq("tableName", GeneConstants.DOMAIN_EIA_PROJECT)
                eq("ifDel", false)
            }
            eiaProjectIds = workFlowBusiList.tableNameId
        }
        /** 统计一审、二审、三审的项目 */
        else if (params.viewType == 'ysProject' || params.viewType == 'esProject' || params.viewType == 'ssProject'||params.viewType == 'nsProject') {
            def planItemList = EiaProjectPlanItem.createCriteria().list() {
                if (startDate) {
                    ge("dateCreated", startDate)
                }
                if (endDate) {
                    le("dateCreated", endDate)
                }
                /** 查询已审批的一审项目 */
                if (params.viewType == 'ysProject') {
                    eq("nodesCode", WorkFlowConstants.NODE_CODE_YS)
                }
                /** 查询已审批的二审项目 */
                else if (params.viewType == 'esProject') {
                    eq("nodesCode", WorkFlowConstants.NODE_CODE_ES)
                }
                /** 查询已审批的三审项目 */
                else if (params.viewType == 'ssProject') {
                    or{
                        eq("nodesName", WorkFlowConstants.NODE_NAME_SS)
                        eq("nodesName", WorkFlowConstants.NODE_NAME_SD)
                    }
                }
                /** 查询已审批的内审项目 */
                else if (params.viewType == 'nsProject') {
                    eq("nodesName", WorkFlowConstants.NODE_NAME_NBSC)
                }

                eq("nodeDeptId", Long.parseLong(params.inputDeptId))
                eq("nodeUserId", Long.parseLong(params.inputUserId))
                eq("ifDel", false)
            }
            eiaProjectIds = planItemList.eiaProjectId
        }
        def eiaProjectList = []
        if (eiaProjectIds) {
            eiaProjectList = EiaProject.createCriteria().list(max: limit, offset: page * limit) {
                'in'("id", eiaProjectIds)
                if (params.projectName && params.projectName != "项目名称、项目编号") {
                    or {
                        like('projectNo', '%' + params.projectName + '%')
                        like('projectName', '%' + params.projectName + '%')
                    }
                }
                eq("ifDel", false)
                order("id", "desc")
            }
        }
        def eiaProjectData = []
        eiaProjectList.each {
            def map = [:]
            map.id = it.id
            map.projectMoney = it.projectMoney
            map.projectName = it.projectName
            map.buildArea = it?.buildArea
            map.gisProjectId = it.gisProjectId
            map.projectNo = it.projectNo
            map.fileTypeChild = it.fileTypeChild
            map.inputDept = it.inputDept
            map.inputUser = it.inputUser
            map.inputUserId = it.inputUserId
            map.dutyUser = it?.dutyUser
            map.eiaTaskId = it.eiaTaskId
            map.oid = it.oid
            def eiaProjectPlan = EiaProjectPlan.findByEiaProjectIdAndIfDel(it.id, false)
            def eiaLabOffer = EiaLabOffer.findByEiaProjectIdAndIfDel(it.id, false)
            if (eiaLabOffer) {
                map.eiaLabOfferId = eiaLabOffer.id
            } else {
                map.eiaLabOfferId = -1
            }
            def eiaTask = EiaTask.findByIdAndIfDel(it.eiaTaskId, false)
            if (eiaTask) {
                def eiaContract = EiaContract.findByTaskIdAndIfDel(eiaTask.id, false)
                if (eiaContract) {
                    map.oldContractId = eiaContract?.oid
                }
            }
            map.proPlanId = eiaProjectPlan?.id
            map.ifSub = eiaProjectPlan?.ifSub
            map.planMonitor = eiaProjectPlan?.planMonitor
            eiaProjectData << map
        }
        def dataMap = [:]
        dataMap.data = eiaProjectData
        dataMap.total = eiaProjectList.totalCount
        return dataMap
    }

    /**
     * 人员项目统计
     * @param params
     * @return
     */
    def getTotalProjectCount(params, session) {
        /** 当前年份 */
        Calendar cale = null
        cale = Calendar.getInstance()
        int year = cale.get(Calendar.YEAR)
        def startDate
        def endDate
        def sdf = new SimpleDateFormat("yyyy-MM-dd")
        if (params.startDate) {
            startDate = sdf.parse(params.startDate)
        } else {
            startDate = sdf.parse(year + "-01-01 00:00:00")
        }
        if (params.endDate) {
            endDate = sdf.parse(params.endDate)
        } else {
            endDate = sdf.parse(year + "-12-31 23:59:59")
        }
        def status = params.status
        def resList = []
        def orgId = String.valueOf(params.id)
        def ifRoot = false
        def orgCode = session?.staff?.orgCode
        if (!session?.staff?.funcCode?.contains(FuncConstants.EIA_YWCX_XMCX_VIEWALL)) {
            /**
             * 查看本部门项目数据
             */
            if (session?.staff?.funcCode?.contains(FuncConstants.EIA_YWCX_XMCX_VIEWDEPT)) {
                if (status == "root") {
                    orgId = String.valueOf(session?.staff?.orgId)
                    /**
                     * 判断是否为分公司
                     */
                    if (orgCode?.contains("FGS")) {
                        /**
                         * 分公司只能看自己的，不能点击其他的，所以ifRoot为true代表不能往上获取其他数据
                         */
                        status = "close"
                        ifRoot = true
                    } else {
                        status = "open"
                        ifRoot = true
                    }
                } else {
                    if (status == 'close') {
                        ifRoot = true
                    } else {
                        ifRoot = false
                    }
                }
            }
            if (session?.staff?.funcCode?.contains(FuncConstants.EIA_YWCX_XMCX_VIEWSELF)) {
                status = "staff"
            }
        } else {
            if (status == "root") {
                orgId = String.valueOf(GeneConstants.ORG_ROOT)
                status = "open"
            }
        }
        def orgOrStaffList = []
        /** staff所在部门id */
        def parentOrgId
        def ifOrg = true
        if (status == "close") {
            def resMap = JsonHandler.jsonToMap(HttpConnectTools.getResponseJson(HttpUrlConstants.EIA_GET_PARENT_ORG, [orgId: orgId]))
            if (resMap.code == HttpMesConstants.CODE_OK) {
                def parentNode = resMap.data
                if (Long.valueOf(parentNode.id) == GeneConstants.ORG_ROOT) {
                    ifRoot = true
                }
                parentNode.ifRoot = ifRoot
                orgOrStaffList.add(parentNode)
                resMap = JsonHandler.jsonToMap(HttpConnectTools.getResponseJson(HttpUrlConstants.EIA_GET_CHILD_ORG, [orgId: String.valueOf(parentNode.id)]))
                if (resMap.code == HttpMesConstants.CODE_OK) {
                    def childNodeList = resMap.data
                    childNodeList.each {
                        orgOrStaffList.add(it)
                        if (it.ifStaff) {
                            ifOrg = false
                        }
                    }
                }
            }
        } else if (status == "open") {
            def resMap = JsonHandler.jsonToMap(HttpConnectTools.getResponseJson(HttpUrlConstants.EIA_GET_THIS_ORG, [orgId: orgId]))
            if (resMap.code == HttpMesConstants.CODE_OK) {
                def parentNode = resMap.data
                if (Long.valueOf(parentNode.id) == GeneConstants.ORG_ROOT) {
                    ifRoot = true
                }
                parentNode.ifRoot = ifRoot
                orgOrStaffList.add(parentNode)
                parentOrgId = parentNode.id
                resMap = JsonHandler.jsonToMap(HttpConnectTools.getResponseJson(HttpUrlConstants.EIA_GET_CHILD_ORG, [orgId: String.valueOf(parentNode.id)]))
                if (resMap.code == HttpMesConstants.CODE_OK) {
                    def childNodeList = resMap.data
                    childNodeList.each {
                        it.parentOrgId = parentOrgId
                        orgOrStaffList.add(it)
                        if (it.ifStaff) {
                            ifOrg = false
                        }
                    }
                }
            }
        } else if (status == "staff") {
            ifOrg = false
            def resMap = JsonHandler.jsonToMap(HttpConnectTools.getResponseJson(HttpUrlConstants.SINGLE_EIA_AUTH_STAFF_INFO, [staffId: String.valueOf(session.staff.staffId)]))
            if (resMap.code == HttpMesConstants.CODE_OK) {
                parentOrgId = resMap.data[0].orgId
                resMap.data[0].ifStaff = true
                resMap.data[0].parentOrgId = parentOrgId
                orgOrStaffList = resMap.data
            }
        }
        if (orgOrStaffList) {
            orgOrStaffList.each {
                def map = [:]
                map.putAll(it)
                /** 项目数量 */
                map.projectNum = 0
                /** 进行中项目数量（项目流程未完成且不是报批版上报的节点） */
                map.projectDoing = 0
                /** 进行中项目id */
                map.projectDoingIdList = []
                /** 未归档项目数量（项目流程未完成且到报批版上报的节点） */
                map.projectUnComp = 0
                /** 未归档项目id */
                map.projectUnCompIdList = []
                /** 已归档项目数量（项目流程end） */
                map.projectComp = 0
                /** 已归档项目id */
                map.projectCompIdList = []
                /** 一审数量 */
                map.ysNum = 0
                /** 二审数量 */
                map.esNum = 0
                /** 三审数量 */
                map.ssNum = 0
                /** 内审数量**/
                map.nsNum = 0
                /** 一审项目id */
                map.ysIdList = []
                /** 二审项目id */
                map.esIdList = []
                /** 三审项目id */
                map.ssIdList = []
                /** 内审项目id */
                map.nsIdList = []

                resList << map
            }
        }
        if (ifOrg) {
            resList = getOrgTotalProject(resList, startDate, endDate)
        } else {
            resList = getStaffTotalProject(resList, parentOrgId, startDate, endDate)
        }
        return resList
    }

    /***
     * 获取组织机构的项目数量
     * @param parentNode
     * @return
     */
    def getOrgTotalProject(orgList, startDate, endDate) {
        /** 统计项目数量 */
        def projectList = EiaProject.createCriteria().list() {
            if (startDate) {
                ge("dateCreated", startDate)
            }
            if (endDate) {
                le("dateCreated", endDate)
            }
            eq("ifDel", false)
            projections {
                count()
                groupProperty('inputDeptId')
            }
        }
        if (projectList) {
            orgList.each { it ->
                projectList.each { project ->
                    if (project[1] && it.orgBusiName.contains(project[1].toString())) {
                        it.projectNum += project[0]
                    }
                }
            }
        }
        /** 统计进行中的项目数量 */
        def projectDoingList = EiaWorkFlowBusi.createCriteria().list() {
            if (startDate) {
                ge("dateCreated", startDate)
            }
            if (endDate) {
                le("dateCreated", endDate)
            }
            eq("tableName", GeneConstants.DOMAIN_EIA_PROJECT)
            ne("nodesCode", WorkFlowConstants.NODE_CODE_BPBSB)
            'in'("workFlowState", [WorkFlowConstants.WORKFLOW_START, 'doing'])
            eq("ifDel", false)
            projections {
                count()
                groupProperty('inputDeptId')
            }
        }
        if (projectDoingList) {
            orgList.each { it ->
                projectDoingList.each { project ->
                    if (it.orgBusiName.contains(project[1].toString())) {
                        it.projectDoing += project[0]
                    }
                }
            }
        }
        /** 统计未归档的项目数量 */
        def projectUnCompList = EiaWorkFlowBusi.createCriteria().list() {
            if (startDate) {
                ge("dateCreated", startDate)
            }
            if (endDate) {
                le("dateCreated", endDate)
            }
            eq("tableName", GeneConstants.DOMAIN_EIA_PROJECT)
            eq("nodesCode", WorkFlowConstants.NODE_CODE_BPBSB)
            'in'("workFlowState", [WorkFlowConstants.WORKFLOW_START, 'doing'])
            eq("ifDel", false)
            projections {
                count()
                groupProperty('inputDeptId')
            }
        }
        if (projectUnCompList) {
            orgList.each { it ->
                projectUnCompList.each { project ->
                    if (it.orgBusiName.contains(project[1].toString())) {
                        it.projectUnComp += project[0]
                    }
                }
            }
        }
        /** 统计已归档的项目数量 */
        def projectCompList = EiaWorkFlowBusi.createCriteria().list() {
            if (startDate) {
                ge("dateCreated", startDate)
            }
            if (endDate) {
                le("dateCreated", endDate)
            }
            eq("tableName", GeneConstants.DOMAIN_EIA_PROJECT)
            eq("workFlowState", WorkFlowConstants.WORKFLOW_END)
            eq("ifDel", false)
            projections {
                count()
                groupProperty('inputDeptId')
            }
        }
        if (projectCompList) {
            orgList.each { it ->
                projectCompList.each { project ->
                    if (it.orgBusiName.contains(project[1].toString())) {
                        it.projectComp += project[0]
                    }
                }
            }
        }
        /** 从工作方案查看一审节点审批人是自己的项目 */
        def ysPlanItemList = EiaProjectPlanItem.createCriteria().list() {
            if (startDate) {
                ge("dateCreated", startDate)
            }
            if (endDate) {
                le("dateCreated", endDate)
            }
            eq("nodesCode", WorkFlowConstants.NODE_CODE_YS)
            eq("ifDel", false)
            projections {
                count()
                groupProperty('nodeDeptId')
            }
        }
        if (ysPlanItemList) {
            orgList.each { it ->
                ysPlanItemList.each { item ->
                    /** 统计一审的项目的数量和id */
                    if (item[1] && it.orgBusiName.contains(item[1].toString())) {
                        it.ysNum += item[0]
                    }
                }
            }
        }
        /** 从工作方案查看二审节点审批人是自己的项目 */
        def esPlanItemList = EiaProjectPlanItem.createCriteria().list() {
            if (startDate) {
                ge("dateCreated", startDate)
            }
            if (endDate) {
                le("dateCreated", endDate)
            }
            eq("nodesCode", WorkFlowConstants.NODE_CODE_ES)
            eq("ifDel", false)
            projections {
                count()
                groupProperty('nodeDeptId')
            }
        }
        if (esPlanItemList) {
            orgList.each { it ->
                esPlanItemList.each { item ->
                    /** 统计二审的项目的数量和id */
                    if (item[1] && it.orgBusiName.contains(item[1].toString())) {
                        it.esNum += item[0]
                    }
                }
            }
        }
        /** 从工作方案查看三审节点审批人是自己的项目 */
        def ssPlanItemList = EiaProjectPlanItem.createCriteria().list() {
            if (startDate) {
                ge("dateCreated", startDate)
            }
            if (endDate) {
                le("dateCreated", endDate)
            }
            or{
                eq("nodesName", WorkFlowConstants.NODE_NAME_SD)
                eq("nodesName", WorkFlowConstants.NODE_NAME_SS)
            }
            eq("ifDel", false)
            projections {
                count()
                groupProperty('nodeDeptId')
            }
        }
        if (ssPlanItemList) {
            orgList.each { it ->
                ssPlanItemList.each { item ->
                    /** 统计三审的项目的数量和id */
                    if (item[1] && it.orgBusiName.contains(item[1].toString())) {
                        it.ssNum += item[0]
                    }
                }
            }
        }

        /** 从工作方案查看内审节点审批人是自己的项目 */
        def nsPlanItemList = EiaProjectPlanItem.createCriteria().list() {
            if (startDate) {
                ge("dateCreated", startDate)
            }
            if (endDate) {
                le("dateCreated", endDate)
            }
            eq("nodesName", WorkFlowConstants.NODE_NAME_NBSC)
            eq("ifDel", false)
            projections {
                count()
                groupProperty('nodeDeptId')
            }
        }
        if (nsPlanItemList) {
            orgList.each { it ->
                nsPlanItemList.each { item ->
                    /** 统计三审的项目的数量和id */
                    if (item[1] && it.orgBusiName.contains(item[1].toString())) {
                        it.nsNum += item[0]
                    }
                }
            }
        }
        return orgList
    }

    /**
     * 获取人员的项目数量
     * @param parentNode
     * @return
     */
    def getStaffTotalProject(orgList, parentOrgId, startDate, endDate) {
        /** 统计项目数量 */
        def projectList = EiaProject.createCriteria().list() {
            if (startDate) {
                ge("dateCreated", startDate)
            }
            if (endDate) {
                le("dateCreated", endDate)
            }
            if (parentOrgId) {
                eq("inputDeptId", Long.parseLong(parentOrgId.toString()))
            }
            eq("ifDel", false)
            projections {
                count()
                groupProperty('inputUserId')
            }
        }
        if (projectList) {
            orgList.each { it ->
                projectList.each { project ->
                    if (it.ifStaff) {
                        if (project[1] == it.id) {
                            it.projectNum += project[0]
                        }
                    } else {
                        it.projectNum += project[0]
                    }
                }
            }
        }
        /** 统计进行中的项目数量 */
        def projectDoingList = EiaWorkFlowBusi.createCriteria().list() {
            if (startDate) {
                ge("dateCreated", startDate)
            }
            if (endDate) {
                le("dateCreated", endDate)
            }
            eq("inputDeptId", Long.parseLong(parentOrgId.toString()))
            eq("tableName", GeneConstants.DOMAIN_EIA_PROJECT)
            ne("nodesCode", WorkFlowConstants.NODE_CODE_BPBSB)
            'in'("workFlowState", [WorkFlowConstants.WORKFLOW_START, 'doing'])
            eq("ifDel", false)
            projections {
                count()
                groupProperty('inputUserId')
            }
        }
        if (projectDoingList) {
            orgList.each { it ->
                projectDoingList.each { project ->
                    /** 员工的情况下，比对userId，获取进行中项目数量和项目id */
                    if (it.ifStaff) {
                        if (project[1] == it.id) {
                            it.projectDoing += project[0]
                        }
                    }
                    /** 机构的情况下，直接获取进行中项目数量 */
                    else {
                        it.projectDoing += project[0]
                    }
                }
            }
        }
        /** 统计未归档的项目数量 */
        def projectUnCompList = EiaWorkFlowBusi.createCriteria().list() {
            if (startDate) {
                ge("dateCreated", startDate)
            }
            if (endDate) {
                le("dateCreated", endDate)
            }
            eq("inputDeptId", Long.parseLong(parentOrgId.toString()))
            eq("tableName", GeneConstants.DOMAIN_EIA_PROJECT)
            eq("nodesCode", WorkFlowConstants.NODE_CODE_BPBSB)
            'in'("workFlowState", [WorkFlowConstants.WORKFLOW_START, 'doing'])
            eq("ifDel", false)
            projections {
                count()
                groupProperty('inputUserId')
            }
        }
        if (projectUnCompList) {
            orgList.each { it ->
                projectUnCompList.each { project ->
                    /** 员工的情况下，比对userId，获取未归档项目数量和项目id */
                    if (it.ifStaff) {
                        if (project[1] == it.id) {
                            it.projectUnComp += project[0]
                        }
                    }
                    /** 机构的情况下，直接获取未归档项目数量 */
                    else {
                        it.projectUnComp += project[0]
                    }
                }
            }
        }
        /** 统计已完成的项目数量 */
        def projectCompList = EiaWorkFlowBusi.createCriteria().list() {
            if (startDate) {
                ge("dateCreated", startDate)
            }
            if (endDate) {
                le("dateCreated", endDate)
            }
            eq("inputDeptId", Long.parseLong(parentOrgId.toString()))
            eq("tableName", GeneConstants.DOMAIN_EIA_PROJECT)
            eq("workFlowState", WorkFlowConstants.WORKFLOW_END)
            eq("ifDel", false)
            projections {
                count()
                groupProperty('inputUserId')
            }
        }
        if (projectCompList) {
            orgList.each { it ->
                projectCompList.each { project ->
                    /** 员工的情况下，比对userId，获取已完成项目数量和项目id */
                    if (it.ifStaff) {
                        if (project[1] == it.id) {
                            it.projectComp += project[0]
                        }
                    }
                    /** 机构的情况下，直接获取已完成项目数量 */
                    else {
                        it.projectComp += project[0]
                    }
                }
            }
        }
        /** 从工作方案查看一审节点审批人是自己的项目 */
        def ysPlanItemList = EiaProjectPlanItem.createCriteria().list() {
            if (startDate) {
                ge("dateCreated", startDate)
            }
            if (endDate) {
                le("dateCreated", endDate)
            }
            eq("nodeDeptId", Long.parseLong(parentOrgId.toString()))
            eq("nodesCode", WorkFlowConstants.NODE_CODE_YS)
            eq("ifDel", false)
            projections {
                count()
                groupProperty('nodeUserId')
            }
        }
        if (ysPlanItemList) {
            orgList.each { it ->
                ysPlanItemList.each { item ->
                    /** 统计一审的项目的数量和id */
                    if (it.ifStaff) {
                        if (item[1] == it.id) {
                            it.ysNum += item[0]
                        }
                    } else {
                        it.ysNum += item[0]
                    }
                }
            }
        }
        /** 从工作方案查看二审节点审批人是自己的项目 */
        def esPlanItemList = EiaProjectPlanItem.createCriteria().list() {
            if (startDate) {
                ge("dateCreated", startDate)
            }
            if (endDate) {
                le("dateCreated", endDate)
            }
            eq("nodeDeptId", Long.parseLong(parentOrgId.toString()))
            eq("nodesCode", WorkFlowConstants.NODE_CODE_ES)
            eq("ifDel", false)
            projections {
                count()
                groupProperty('nodeUserId')
            }
        }
        if (esPlanItemList) {
            orgList.each { it ->
                esPlanItemList.each { item ->
                    /** 统计二审的项目的数量和id */
                    if (it.ifStaff) {
                        if (item[1] == it.id) {
                            it.esNum += item[0]
                        }
                    } else {
                        it.esNum += item[0]
                    }
                }
            }
        }
        /** 从工作方案查看三审节点审批人是自己的项目 */
        def ssPlanItemList = EiaProjectPlanItem.createCriteria().list() {
            if (startDate) {
                ge("dateCreated", startDate)
            }
            if (endDate) {
                le("dateCreated", endDate)
            }
            eq("nodeDeptId", Long.parseLong(parentOrgId.toString()))
            /**三审和审定放在一起**/
            or {
                eq("nodesName", WorkFlowConstants.NODE_NAME_SS)
                eq("nodesName", WorkFlowConstants.NODE_NAME_SD)
            }
            eq("ifDel", false)
            projections {
                count()
                groupProperty('nodeUserId')
            }
        }

        if (ssPlanItemList) {
            orgList.each { it ->
                ssPlanItemList.each { item ->
                    /** 统计三审的项目的数量和id */
                    if (it.ifStaff) {
                        if (item[1] == it.id) {
                            it.ssNum += item[0]
                        }
                    } else {
                        it.ssNum += item[0]
                    }
                }
            }
        }
        /** 从工作方案查看三审节点审批人是自己的项目 */
        def nsPlanItemList = EiaProjectPlanItem.createCriteria().list() {
            if (startDate) {
                ge("dateCreated", startDate)
            }
            if (endDate) {
                le("dateCreated", endDate)
            }
            eq("nodeDeptId", Long.parseLong(parentOrgId.toString()))
            /**内部审查**/
            eq("nodesName", WorkFlowConstants.NODE_NAME_NBSC)
            eq("ifDel", false)
            projections {
                count()
                groupProperty('nodeUserId')
            }
        }
        if (nsPlanItemList) {
            orgList.each { it ->
                nsPlanItemList.each { item ->
                    /** 统计内审的项目的数量和id */
                    if (it.ifStaff) {
                        if (item[1] == it.id) {
                            it.nsNum += item[0]
                        }
                    } else {
                        it.nsNum += item[0]
                    }
                }
            }
        }
        return orgList
    }

    /**
     * 获取项目台账列表
     */
    def getProjectAccountDataList(params, session) {
        int page
        int limit
        if (params.page && params.limit) {
            page = params.int('page') - 1
            limit = params.int('limit')
        }
        def proAccList = EiaProject.createCriteria().list(max: limit, offset: page * limit) {
            if (params.projectName && params.projectName != "项目名称、项目编号、录入部门、录入人") {
                or {
                    like('projectNo', '%' + params.projectName + '%')
                    like('projectName', '%' + params.projectName + '%')
                    like("inputDept", "%" + params.projectName + "%")
                    like("inputUser", "%" + params.projectName + "%")
                }
            }
            if (params.inputUserId) {
                eq('inputUserId', params.long('inputUserId'))
            }
            if (params.inputDeptId) {
                eq('inputDeptId', params.long('inputDeptId'))
            }
            /**
             * 查看全部的客户数据
             */
            if (!session?.staff?.funcCode?.contains(FuncConstants.EIA_YWCX_XMCX_VIEWALL)) {
                /**
                 * 查看本部门客户数据
                 */
                if (session?.staff?.funcCode?.contains(FuncConstants.EIA_YWCX_XMCX_VIEWDEPT)) {
                    like("inputDeptCode", "%" + session.staff.orgCode + "%")
                }
                /**
                 * 查看本人客户数据
                 */
                else if (session?.staff?.funcCode?.contains(FuncConstants.EIA_YWCX_XMCX_VIEWSELF)) {
                    eq("inputUserId", Long.valueOf(session.staff.staffId))
                }
            }
            eq("ifDel", false)
            order("id", "desc")
        }
        def resList = []
        proAccList.each {
            def combineMap = [:]
            def ifLabTest = "无"
            def yxTestList = EiaLabOffer.findAllByEiaProjectIdAndIfYxTestAndIfDel(it.id, true, false)
            if (yxTestList) {
                ifLabTest = "宇相监测"
            }
            def notYxTestList = EiaLabOffer.findAllByEiaProjectIdAndIfYxTestAndIfDel(it.id, false, false)
            if (notYxTestList) {
                ifLabTest = "非宇相监测"
            }
            if (it.eiaContractId) {
                def eiaContract = EiaContract.findByIdAndIfDel(it.eiaContractId, false)
                if (eiaContract && eiaContract.contractState == GeneConstants.CONTRACT_STATE_SIGNED) {
                    def eiaInvoice = EiaInvoice.findAllByContractIdAndIfDelAndAccountState(it.eiaContractId, false, GeneConstants.INVOICE_CONFIRM_YES)
                    /**
                     * 开票金额
                     */
                    def eiaInvoiceSum = 0
                    eiaInvoice.each {
                        eiaInvoiceSum += it.billMoney
                    }
                    /**
                     * 进账金额
                     */
                    def invoiceIncomeSum = 0
                    def invoiceIncome = EiaIncomeOut.findAllByContractIdAndIfDelAndInvoiceTypeAndAccountState(it.eiaContractId, false, GeneConstants.INVOICE_TYPE_INCOME, GeneConstants.INVOICE_CONFIRM_YES)
                    invoiceIncome.each {
                        invoiceIncomeSum += it.noteIncomeMoney
                    }
                    /**
                     * 出账信息(
                     * 专家费(万元))
                     */
                    def expertFeeSum = 0
                    def invoiceOut = EiaIncomeOut.findAllByContractIdAndIfDelAndInvoiceTypeAndCostTypesAndAccountState(it.id, false, GeneConstants.INVOICE_TYPE_OUT, GeneConstants.INVOICE_ECPERT_FEES, GeneConstants.INVOICE_CONFIRM_YES)
                    invoiceOut.each {
                        expertFeeSum += it.noteIncomeMoney
                    }
                    /**
                     * 出账信息(
                     * 监测费(万元))
                     */
                    def monitorFeeSum = 0
                    def monitorFee = EiaIncomeOut.findAllByContractIdAndIfDelAndInvoiceTypeAndCostTypesAndAccountState(it.id, false, GeneConstants.INVOICE_TYPE_OUT, GeneConstants.INVOICE_MONITORING_FEES, GeneConstants.INVOICE_CONFIRM_YES)
                    monitorFee.each {
                        monitorFeeSum += it.noteIncomeMoney
                    }
                    /**
                     * 出账信息(
                     * 其他((万元))
                     */
                    def otherFeeSum = 0
                    def otherFee = EiaIncomeOut.findAllByContractIdAndIfDelAndInvoiceTypeAndCostTypesAndAccountState(it.id, false, GeneConstants.INVOICE_TYPE_OUT, GeneConstants.INVOICE_OTHER_FEES, GeneConstants.INVOICE_CONFIRM_YES)
                    otherFee.each {
                        otherFeeSum += it.noteIncomeMoney
                    }

                    combineMap.billMoney = eiaInvoiceSum
                    combineMap.noteIncomeMoney = invoiceIncomeSum
                    combineMap.expertFee = expertFeeSum
                    combineMap.monitorFee = monitorFeeSum
                    combineMap.otherFee = otherFeeSum

                    def reportFeeSum = 0
                    def serSurPreSum = 0
                    def preSurvCertFee = 0
                    def preIssCertFee = 0
                    def certServeFee = 0
                    def otherContractFee = 0
                    /**
                     * 项目编制费
                     */
                    if (eiaContract?.reportFees > 0) {
                        reportFeeSum = eiaContract.reportFees ?: 0
                    } else {
                        /**
                         * 其他费用
                         */
                        if (eiaContract.otherFee) {
                            otherContractFee = eiaContract.otherFee ?: 0
                        }
                        /**
                         * 发行前认证费
                         */
                        if (eiaContract.preIssCertFee) {
                            preIssCertFee = eiaContract.preIssCertFee ?: 0
                        }
                        /**
                         * 存续期认证费
                         */
                        if (eiaContract.preSurvCertFee) {
                            preSurvCertFee = eiaContract.preSurvCertFee ?: 0
                        }
                        /**
                         * 认证服务费
                         */
                        if (eiaContract.certServeFee) {
                            certServeFee = eiaContract.certServeFee ?: 0
                        }
                    }
                    /**
                     * 计算利润率
                     */
                    BigDecimal preIssCertFeeSum = new BigDecimal(preIssCertFee ? preIssCertFee : 0);
                    BigDecimal preSurvCertFeeSum = new BigDecimal(preSurvCertFee ? preSurvCertFee : 0);
                    BigDecimal certServeFeeSum = new BigDecimal(certServeFee ? certServeFee : 0);
                    BigDecimal otherFeeContractSum = new BigDecimal(otherContractFee ? otherContractFee : 0);
                    serSurPreSum = preIssCertFeeSum + preSurvCertFeeSum + certServeFeeSum + otherFeeContractSum
                    BigDecimal profitMarginMin = serSurPreSum + reportFeeSum
                    if (profitMarginMin > 0 && eiaContract.contractMoney > 0) {
                        combineMap.profitMargin = profitMarginMin.divide(eiaContract.contractMoney ?: 0, 4, RoundingMode.HALF_UP) ?: 0
                    } else {
                        combineMap.profitMargin = new BigDecimal(0);
                    }
                } else {
                    combineMap.billMoney = '-'
                    combineMap.noteIncomeMoney = '-'
                    combineMap.expertFee = '-'
                    combineMap.monitorFee = '-'
                    combineMap.otherFee = '-'
                    combineMap.profitMargin = '-'
                }
            }
            combineMap.id = it.id
            combineMap.ifLabTest = ifLabTest
            def workFlowProject = EiaWorkFlowBusi.findByTableNameAndTableNameIdAndifDel('EiaProject', it.id, false)
            if (workFlowProject && workFlowProject.workFlowState != WorkFlowConstants.WORKFLOW_HALT) {
                combineMap.nodesName = workFlowProject.nodesName
            } else {
                combineMap.nodesName = '未开始'
            }
            combineMap.projectName = it.projectName
            combineMap.projectNo = it.projectNo
            resList << combineMap
        }
        def resMap = [:]
        def totalDetail =[]
        resMap.data = resList
        resMap.totalDetail = totalDetail
        resMap.count = proAccList.totalCount
        return resMap
    }
}
