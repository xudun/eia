package com.lheia.eia.project

import com.lheia.eia.client.EiaClient
import com.lheia.eia.common.GeneConstants
import com.lheia.eia.common.HttpMesConstants
import com.lheia.eia.common.HttpUrlConstants
import com.lheia.eia.common.WorkFlowConstants
import com.lheia.eia.config.EiaDomainCode
import com.lheia.eia.task.EiaTask
import com.lheia.eia.tools.AMapApiTools
import com.lheia.eia.tools.HttpConnectTools
import com.lheia.eia.tools.JsonHandler
import com.lheia.eia.workflow.EiaWorkFlowBusi
import com.lheia.eia.workflow.EiaWorkFlowBusiLog
import grails.converters.JSON

/**
 * 项目信息
 */
class EiaProjectController {
    def eiaProjectService
    def eiaDomainCodeService

    def eiaProjectIndex() {}

    def eiaGisGeoProject() {}

    def eiaProjectDetail() {
        [pageType: params.pageType]
    }

    def eiaProjectShow() {
        def eiaProject = EiaProject.findByRandomCodeAndIfDel(params.qrcode, false)
        [projectName: eiaProject.projectName, fileType: eiaProject.fileTypeChild, projectNo: eiaProject.projectNo, buildArea: eiaProject.buildArea]
    }

    def eiaProjectFillSelect() {}
    /**
     * 工作方案列表页面
     */
    def eiaProjectPlanIndex() {}
    /**
     * 工作方案流程编辑页面
     */
    def eiaProjectPlanEdit() {}
    /**
     * 工作方案流程查看页面
     */
    def eiaProjectPlanDetail() {}

    /**
     * 项目工作流页面
     */
    def eiaProjectProcessIndex() {}
    /**
     * 工作方案节点编辑页面
     */
    def eiaProPlanItemEdit() {}
    /**
     * 责任运行卡
     */
    def eiaProjectDutyCard() {
        [eiaProjectId: params.eiaProjectId]
    }
    /**
     * 节点详情页面
     */
    def eiaProPlanItemDetail() {}
    /**
     * 现场踏勘详情页面
     */
    def eiaProjectExploreEdit() {}
    /**
     * 现场踏勘详情页面
     */
    def eiaProjectExploreDetail() {}
    /**
     * 现场踏勘详情打印
     */
    def eiaProjectExplorePrint() {}
    /**
     * 人工干预流程(admin用)
     */
    def changeWorkFlow() {}

    /**
     * 根据不同文件类型返回不同页面元素
     */
    def eiaEnvProjectDetail() {
        def fileId = params.long('fileType')
        def fileType = EiaDomainCode.findById(fileId)?.parentCode
        def dataList
        if (fileType == "EPC_HP") {
            dataList = GeneConstants.HPPROPLIST
        } else if (fileType == "EPC_HY") {
            dataList = GeneConstants.HYPROPLIST
        } else if (fileType == "EPC_HB") {
            dataList = GeneConstants.HBPROPLIST
        } else if (fileType == "EPC_YS") {
            dataList = GeneConstants.YSPROPLIST
        } else if (fileType == "EPC_YA") {
            dataList = GeneConstants.YAPROPLIST
        } else if (fileType == "EPC_JL") {
            dataList = GeneConstants.JLPROPLIST
        } else if (fileType == "EPC_GH") {
            dataList = GeneConstants.GHPROPLIST
        } else if (fileType == "EPC_HH") {
            dataList = GeneConstants.HHPROPLIST
        } else if (fileType == "EPC_SH") {
            dataList = GeneConstants.SHPROPLIST
        } else if (fileType == "EPC_XZ") {
            dataList = GeneConstants.XZPROPLIST
        } else if (fileType == "EPC_PF") {
            dataList = GeneConstants.PFPROPLIST
        } else if (fileType == 'EPC_PW') {
            dataList = GeneConstants.PWPROPLIST
        } else if (fileType == "EPC_CD") {
            dataList = GeneConstants.CDPROPLIST
        } else if (fileType == "ESE_LZ") {
            dataList = GeneConstants.ESELZPROPLIST
        } else if (GeneConstants.GREENLIST.contains(fileType)) {
            dataList = GeneConstants.GREENPROPLIST
        }
        render([code: HttpMesConstants.CODE_OK, data: dataList] as JSON)
    }
    /**
     * 获取项目数据
     */
    def getEiaProjectDataList() {
        def dataMap = eiaProjectService.eiaProjectQueryPage(params, session)
        if (dataMap) {
            render([code: HttpMesConstants.CODE_OK, count: dataMap.total, data: dataMap.data] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
    /**
     * 归档打印
     */
    def eiaFileArc() {}
    /**
     * 归档打印
     */
    def eiaPrintFileArc() {
        def resMap = [:]
        resMap = eiaProjectService.eiaPrintFileArc(Long.valueOf(params.eiaProjectId))
        [resMap: resMap, wordNo: params.wordNo, approvalDate: params.approvalDate]
    }
    /**
     * 项目信息新增
     */
    def eiaProjectCreate() {
        /**
         * 获取文件类型，code值，把code值传到前台，用于界面类型判断
         */
        [fileType: params.fileType]
    }
    /**
     * 项目信息保存
     */
    def eiaProjectSave() {
        if (params.long("eiaProjectId")) {
            def eiaProject = eiaProjectService.eiaProjectUpdate(params, session)
            if (eiaProject) {
                render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SAVE_OK, data: eiaProject] as JSON)
            } else {
                render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
            }
        } else {
            def eiaProject = eiaProjectService.eiaProjectSave(params, session)
            if (eiaProject) {
                render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SAVE_OK] as JSON)
            } else {
                render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
            }
        }
    }
    /**
     * 项目信息回显
     */
    def getEiaProjectDataMap() {
        def eiaProject = [:]
        eiaProject = eiaProjectService.getEiaProjectDataMap(params.long("eiaProjectId"))
        if (eiaProject) {
            render([code: HttpMesConstants.CODE_OK, data: eiaProject] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
    /**
     * 获取建设性质列表
     */
    def getBuildPropList() {
        def buildPropList = eiaProjectService.getBuildPropList()
        if (buildPropList.size() > 0) {
            render([code: HttpMesConstants.CODE_OK, data: buildPropList] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
    /**
     * 获取园区规划环评开展情况Domain
     */
    def getParkEnvCodList() {
        def parkEnvCodList = eiaProjectService.getParkEnvCodList()
        if (parkEnvCodList.size() > 0) {
            render([code: HttpMesConstants.CODE_OK, data: parkEnvCodList] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
    /**
     * 获取任务列表
     * eiaTaskController中的最后一个方法
     */
    def getTaskList() {
        def eiaTask = EiaTask.findAllByIfDel(false, [sort: "lastUpdated", order: "desc",])
        def taskResult = []
        eiaTask.each {
            def taskMap = [:]
            taskMap.id = it.id
            taskMap.name = it.taskNo
            taskMap.taskName = it.taskName + " (" + it.taskNo + ")"
            taskResult.add(taskMap)
        }
        render(taskResult as JSON);
    }

    def getClientIdByTaskId() {
        def eiaTask = EiaTask.findByIdAndIfDel(params.long('taskId'), false)
        def clientId = eiaTask?.inputUserId
        render([code: HttpMesConstants.CODE_OK, data: ["clientId": clientId]] as JSON)
    }

    /**
     * 获取文件类型列表
     */
    def getFileType() {
        def fileTypeList = EiaDomainCode.findAllByDomainAndCodeLevel(GeneConstants.PROJECT_FILE_TYPE, 3)
        render([code: HttpMesConstants.CODE_OK, data: fileTypeList] as JSON)
    }
    /**
     * 获取文件类型列表树
     */
    def getFileTypeTree() {
        def fileTypeList = EiaDomainCode.findAllByDomain(GeneConstants.PROJECT_FILE_TYPE)
    }

    /**
     * 获取树Domain中的树
     */
    def getTreeByDomain() {
        def codes = eiaDomainCodeService.getCodes(params.domain)
        def nodesMap = [:]
        def treeList = []
        def createNode = { code ->
            def node = nodesMap[code]
            if (!node) {
                node = [:]
                node.children = []
                node.attributes = [:]
                nodesMap[code] = node
            }
            return node
        }
        codes.each {
            def node = createNode(it.code)
            node.code = it.code
            node.name = it.codeDesc
            node.id = it.id
            node.attributes.levels = it.codeLevel
            node.attributes.mark = it.codeRemark
            if (it.parentCode) {
                def pNode = createNode(it.parentCode)
                pNode.children << node
            } else {
                treeList << node
            }
        }
        render(treeList as JSON)
    }

    /**
     * 获取主管部门
     */
    def getCompetentDept() {
        def competentDeptList = EiaDomainCode.findAllByDomainAndCodeLevel(GeneConstants.CHECK_ORG, 2)
        render(competentDeptList as JSON)
    }

    /**
     * 根据taskId获取相应的文件类型树
     */
    def getFileTreeByTaskId() {
        def eiaTask
        if (params.eiaTaskId == "undefined") {
            def eiaProjectId = params.long("eiaProjectId")
            def eiaProject = EiaProject.findByIdAndIfDel(eiaProjectId, false)
            eiaTask = EiaTask.findByIdAndIfDel(eiaProject.eiaTaskId, false)
        } else {
            eiaTask = EiaTask.findByIdAndIfDel(params.long('eiaTaskId'), false)
        }
        def projectType = eiaTask.busiType
        List<String> busiTypeList = projectType.split(",")
        def fileTypeList = EiaDomainCode.findAllByDomainAndCodeDescInList(GeneConstants.PROJECT_FILE_TYPE, busiTypeList)
        def codes = eiaDomainCodeService.getCodes(GeneConstants.PROJECT_FILE_TYPE)
        def codeList = []
        /*  fileTypeList.each { it ->
              codes.each { code ->
                  if (code.code.indexOf(it.code) != -1)
                      codeList << code
              }
          }*/
        codeList.addAll(fileTypeList)
        def seclevelList = EiaDomainCode.findAllByDomainAndParentCodeInListAndCodeLevel('PROJECT_FILE_TYPE', fileTypeList.code, 2)
        codeList.addAll(seclevelList)
        def thirdLevelList = EiaDomainCode.findAllByDomainAndParentCodeInListAndCodeLevel('PROJECT_FILE_TYPE', seclevelList.code, 3)
        codeList.addAll(thirdLevelList)
        def nodesMap = [:]
        def treeList = []
        def createNode = { code ->
            def node = nodesMap[code]
            if (!node) {
                node = [:]
                node.children = []
                node.attributes = [:]
                nodesMap[code] = node
            }
            return node
        }
        codeList.each {
            def node = createNode(it.code)
            node.code = it.code
            node.name = it.codeDesc
            node.id = it.id
            node.attributes.levels = it.codeLevel
            node.attributes.mark = it.codeRemark
            if (it.parentCode) {
                def pNode = createNode(it.parentCode)
                pNode.children << node
            } else {
                treeList << node
            }
        }
        render(treeList as JSON)
    }

    /**
     * 获取全部文件类型
     */
    /**
     * 根据taskId获取相应的文件类型树
     */
    def getFileTree() {
        def fileTypeList = EiaDomainCode.findAllByDomain(GeneConstants.PROJECT_FILE_TYPE)
        def codes = eiaDomainCodeService.getCodes(GeneConstants.PROJECT_FILE_TYPE)
        def codeList = []
        /*  fileTypeList.each { it ->
              codes.each { code ->
                  if (code.code.indexOf(it.code) != -1)
                      codeList << code
              }
          }*/
        codeList.addAll(fileTypeList)
        def seclevelList = EiaDomainCode.findAllByDomainAndParentCodeInListAndCodeLevel('PROJECT_FILE_TYPE', fileTypeList.code, 2)
        codeList.addAll(seclevelList)
        def thirdLevelList = EiaDomainCode.findAllByDomainAndParentCodeInListAndCodeLevel('PROJECT_FILE_TYPE', seclevelList.code, 3)
        codeList.addAll(thirdLevelList)
        def nodesMap = [:]
        def treeList = []
        def createNode = { code ->
            def node = nodesMap[code]
            if (!node) {
                node = [:]
                node.children = []
                node.attributes = [:]
                nodesMap[code] = node
            }
            return node
        }
        codeList.each {
            def node = createNode(it.code)
            node.code = it.code
            node.name = it.codeDesc
            node.id = it.id
            node.attributes.levels = it.codeLevel
            node.attributes.mark = it.codeRemark
            if (it.parentCode) {
                def pNode = createNode(it.parentCode)
                pNode.children << node
            } else {
                treeList << node
            }
        }
        render(treeList as JSON)
    }
    /**
     * 删除项目
     */
    def eiaProjectDel() {
        def eiaPro = eiaProjectService.eiaProjectDel(params)
        if (eiaPro && eiaPro != HttpMesConstants.MSG_PRO_FLOW_STARTED) {
            render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_DEL_OK] as JSON)
        } else if (eiaPro == HttpMesConstants.MSG_PRO_FLOW_STARTED) {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_PRO_FLOW_STARTED] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DEL_FAIL] as JSON)
        }
    }

    //地图绘制获取点和线信息
    def eiaMapDraw() {}
    /**
     * 关键检索地名
     * @param parmas
     * @return
     *
     */
    def getSearchKeywordsDataList(params) {
        def resList = []
        String keywords = String.valueOf(params.keywords).replaceAll("\\s+", "")
        def dataList = []
        def data = AMapApiTools.getSearchKeywordsDataList(keywords, "", Integer.valueOf(params.page), Integer.valueOf(params.limit))
        dataList.addAll(data.pois)
        dataList.each {
            def resMap = [:]
            resMap.name = it.name
            resMap.address = it.pname + it.cityname + it.adname + it.address
            resMap.location = it.location
            resList << resMap
        }
        render([code: HttpMesConstants.CODE_OK, count: data.count, data: resList] as JSON)
    }

    def getGisGeoProjectMap() {
        Long eiaProjectId = Long.valueOf(params.eiaProjectId)
        def eiaProject = EiaProject.findByIdAndIfDel(eiaProjectId, false)
        if (eiaProject.gisProjectId) {
            def gisProjectMap = HttpConnectTools.getResponseJson(HttpUrlConstants.GET_GIS_PROJECT_MAP, [projectId: String.valueOf(eiaProject.gisProjectId)])
            if (gisProjectMap) {
                def projectMap = JsonHandler.jsonToMap(gisProjectMap).data
                render([code: HttpMesConstants.CODE_OK, data: projectMap] as JSON)
            } else {
                render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
            }
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
    /**
     *
     */
    def getProjectDataAll() {
        def projectData = eiaProjectService.getProjectDataAll(params)
        if (projectData) {
            render([code: HttpMesConstants.CODE_OK, data: projectData] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }

    /***
     *
     * 获取责任运行卡数据
     */
    def getDutyCardDataMap() {
        def dutyCardDataMap = eiaProjectService.getDutyCardDataMap(params)
        render([code: HttpMesConstants.CODE_OK, data: dutyCardDataMap] as JSON)
    }
    /**
     *  查询项目是否关联合同
     */
    def checkIfContract() {
        def ifContract = eiaProjectService.checkIfContract(params)
        if (ifContract) {
            render([code: HttpMesConstants.CODE_OK, data: ifContract] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }

    /**
     * 一审、二审、三审审核单
     * @param eiaProjectId
     * @param reportType
     * @return
     */
    def eiaProjectReport(Long eiaProjectId, String reportType) {
        def eiaProject = EiaProject.findById(eiaProjectId)
        def eiaClient = EiaClient.findById(eiaProject.eiaClientId)
        def eiaProjectPlan = EiaProjectPlan.findByEiaProjectIdAndIfDel(eiaProjectId, false)
        def planItem
        def workFlowNode
        def modiContent
        if (reportType == WorkFlowConstants.NODE_CODE_YS) {
            /** 一审审批内容 */
            if (eiaProjectPlan) {
                planItem = EiaProjectPlanItem.findByEiaProjectPlanIdAndIfDelAndNodesCode(eiaProjectPlan.id, false, WorkFlowConstants.NODE_CODE_YS)
                workFlowNode = EiaWorkFlowBusiLog.findByTableNameAndTableNameIdAndNodesCode(
                        GeneConstants.DOMAIN_EIA_PROJECT, eiaProjectId, WorkFlowConstants.NODE_CODE_YSBZ)
            }
            modiContent = EiaProjectPlanItem.findByEiaProjectPlanIdAndIfDelAndNodesCode(eiaProjectPlan.id, false, WorkFlowConstants.NODE_CODE_YSBZ)?.modiContent
        } else if (reportType == WorkFlowConstants.NODE_CODE_ES) {
            /** 三审审批内容 */
            if (eiaProjectPlan) {
                planItem = EiaProjectPlanItem.findByEiaProjectPlanIdAndIfDelAndNodesCode(eiaProjectPlan.id, false, WorkFlowConstants.NODE_CODE_ES)
                workFlowNode = EiaWorkFlowBusiLog.findByTableNameAndTableNameIdAndNodesCode(
                        GeneConstants.DOMAIN_EIA_PROJECT, eiaProjectId, WorkFlowConstants.NODE_CODE_ESBZ)
            }
            modiContent = EiaProjectPlanItem.findByEiaProjectPlanIdAndIfDelAndNodesCode(eiaProjectPlan.id, false, WorkFlowConstants.NODE_CODE_ESBZ)?.modiContent
        } else if (reportType == WorkFlowConstants.NODE_CODE_SS) {
            /** 三审审批内容 */
            if (eiaProjectPlan) {
                planItem = EiaProjectPlanItem.findByEiaProjectPlanIdAndIfDelAndNodesCode(eiaProjectPlan.id, false, WorkFlowConstants.NODE_CODE_SS)
                workFlowNode = EiaWorkFlowBusiLog.findByTableNameAndTableNameIdAndNodesCode(
                        GeneConstants.DOMAIN_EIA_PROJECT, eiaProjectId, WorkFlowConstants.NODE_CODE_SSBZ)
            }
            modiContent = EiaProjectPlanItem.findByEiaProjectPlanIdAndIfDelAndNodesCode(eiaProjectPlan.id, false, WorkFlowConstants.NODE_CODE_SSBZ)?.modiContent
        }
        def busiLog
        def workFlowBusi = EiaWorkFlowBusi.findByWorkFlowStateNotEqualAndTableNameAndTableNameId(WorkFlowConstants.WORKFLOW_HALT, GeneConstants.DOMAIN_EIA_PROJECT, eiaProjectId)
        if (workFlowBusi) {
            busiLog = EiaWorkFlowBusiLog.findByEiaWorkFlowBusiIdAndIfDel(workFlowBusi.id, false)
        }
        def signImagePath
        def param = [:]
        param.staffId = String.valueOf(planItem?.nodeUserId)
        def staffJson = HttpConnectTools.getResponseJson(HttpUrlConstants.SINGLE_EIA_AUTH_STAFF_INFO, param)
        if (staffJson) {
            if (JsonHandler.jsonToMap(staffJson).code == HttpMesConstants.CODE_OK) {
                def staff = JsonHandler.jsonToMap(staffJson).data[0]
                if (staff.signImagePath) {
                    signImagePath = GeneConstants.AUTH_FILE_URL_PATH + staff.signImagePath
                }
            }
        }
        [reportType: reportType, project: eiaProject, eiaClient: eiaClient, planItem: planItem, modiContent: modiContent, busiLog: busiLog, signImagePath: signImagePath, workFlowNode: workFlowNode]
    }
    /**
     * 一审、二审、三审审核单打印
     * @param eiaProjectId
     * @param reportType
     * @return
     */
    def eiaPrintReport(Long eiaProjectId, String reportType) {
        def eiaProject = EiaProject.findById(eiaProjectId)
        def eiaClient = EiaClient.findById(eiaProject.eiaClientId)
        def eiaProjectPlan = EiaProjectPlan.findByEiaProjectIdAndIfDel(eiaProjectId, false)
        def planItem
        def workFlowNode
        def modiContent
        if (reportType == WorkFlowConstants.NODE_CODE_YS) {
            /** 一审审批内容 */
            if (eiaProjectPlan) {
                planItem = EiaProjectPlanItem.findByEiaProjectPlanIdAndIfDelAndNodesCode(eiaProjectPlan.id, false, WorkFlowConstants.NODE_CODE_YS)
                workFlowNode = EiaWorkFlowBusiLog.findByTableNameAndTableNameIdAndNodesCode(
                        GeneConstants.DOMAIN_EIA_PROJECT, eiaProjectId, WorkFlowConstants.NODE_CODE_YSBZ)
            }
            modiContent = EiaProjectPlanItem.findByEiaProjectPlanIdAndIfDelAndNodesCode(eiaProjectPlan.id, false, WorkFlowConstants.NODE_CODE_YSBZ)?.modiContent
            if (!modiContent) {
                modiContent = EiaProjectPlanItem.findByEiaProjectPlanIdAndIfDelAndNodesCode(eiaProjectPlan.id, false, WorkFlowConstants.NODE_CODE_YS)?.modiContent
            }
        } else if (reportType == WorkFlowConstants.NODE_CODE_ES) {
            /** 三审审批内容 */
            if (eiaProjectPlan) {
                planItem = EiaProjectPlanItem.findByEiaProjectPlanIdAndIfDelAndNodesCode(eiaProjectPlan.id, false, WorkFlowConstants.NODE_CODE_ES)
                workFlowNode = EiaWorkFlowBusiLog.findByTableNameAndTableNameIdAndNodesCode(
                        GeneConstants.DOMAIN_EIA_PROJECT, eiaProjectId, WorkFlowConstants.NODE_CODE_ESBZ)
            }
            modiContent = EiaProjectPlanItem.findByEiaProjectPlanIdAndIfDelAndNodesCode(eiaProjectPlan.id, false, WorkFlowConstants.NODE_CODE_ESBZ)?.modiContent
            if (!modiContent) {
                modiContent = EiaProjectPlanItem.findByEiaProjectPlanIdAndIfDelAndNodesCode(eiaProjectPlan.id, false, WorkFlowConstants.NODE_CODE_ES)?.modiContent
            }
        } else if (reportType == WorkFlowConstants.NODE_CODE_SS) {
            /** 三审审批内容 */
            if (eiaProjectPlan) {
                planItem = EiaProjectPlanItem.findByEiaProjectPlanIdAndIfDelAndNodesCode(eiaProjectPlan.id, false, WorkFlowConstants.NODE_CODE_SS)
                workFlowNode = EiaWorkFlowBusiLog.findByTableNameAndTableNameIdAndNodesCode(
                        GeneConstants.DOMAIN_EIA_PROJECT, eiaProjectId, WorkFlowConstants.NODE_CODE_SSBZ)
            }
            modiContent = EiaProjectPlanItem.findByEiaProjectPlanIdAndIfDelAndNodesCode(eiaProjectPlan.id, false, WorkFlowConstants.NODE_CODE_SSBZ)?.modiContent
            if (!modiContent) {
                modiContent = EiaProjectPlanItem.findByEiaProjectPlanIdAndIfDelAndNodesCode(eiaProjectPlan.id, false, WorkFlowConstants.NODE_CODE_SS)?.modiContent
            }
        }
        def busiLog
        def workFlowBusi = EiaWorkFlowBusi.findByWorkFlowStateNotEqualAndTableNameAndTableNameId(WorkFlowConstants.WORKFLOW_HALT, GeneConstants.DOMAIN_EIA_PROJECT, eiaProjectId)
        if (workFlowBusi) {
            busiLog = EiaWorkFlowBusiLog.findByEiaWorkFlowBusiIdAndIfDel(workFlowBusi.id, false)
        }
        def signImagePath
        def param = [:]
        param.staffId = String.valueOf(planItem?.nodeUserId)
        def staffJson = HttpConnectTools.getResponseJson(HttpUrlConstants.SINGLE_EIA_AUTH_STAFF_INFO, param)
        if (staffJson) {
            if (JsonHandler.jsonToMap(staffJson).code == HttpMesConstants.CODE_OK) {
                def staff = JsonHandler.jsonToMap(staffJson).data[0]
                if (staff.signImagePath) {
                    signImagePath = GeneConstants.AUTH_FILE_URL_PATH + staff.signImagePath
                }
            }
        }
        [reportType: reportType, project: eiaProject, eiaClient: eiaClient, planItem: planItem, modiContent: modiContent, busiLog: busiLog, signImagePath: signImagePath, workFlowNode: workFlowNode]
    }
    /**
     * 责任运行卡
     * @return
     */
    def eiaPrintDutyCard() {
        def dutyCardDataMap = eiaProjectService.getDutyCardDataMap(params)
        [dutyCardDataMap: dutyCardDataMap]
    }

    /**
     * 获取项目所需要的金额的字段
     */
    def getProMoneyProp() {
        def proMoneyList = eiaProjectService.getProMoneyProp(params)
        render([success: HttpMesConstants.CODE_OK, data: proMoneyList] as JSON)
    }
    /**
     * 获取项目所需要的金额的字段
     */
    def getPropMoneyShow() {
        def proMoneyMap = eiaProjectService.getPropMoneyShow(params)
        render([success: HttpMesConstants.CODE_OK, data: proMoneyMap] as JSON)
    }
    /**
     * 获取项目负责人
     */
    def getProjectDutyUser() {
        def proDutyUser = eiaProjectService.getProjectDutyUser(params)
        render([code: HttpMesConstants.CODE_OK, data: proDutyUser] as JSON)
    }
}
