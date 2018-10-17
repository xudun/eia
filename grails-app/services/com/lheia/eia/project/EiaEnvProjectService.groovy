package com.lheia.eia.project

import com.lheia.eia.common.GeneConstants
import com.lheia.eia.config.EiaFileUpload
import com.lheia.eia.task.EiaTask
import com.lheia.eia.task.EiaTaskAssign
import com.lheia.eia.tools.DateTransTools
import grails.gorm.transactions.Transactional


@Transactional
class EiaEnvProjectService {
    def eiaFileUploadService
    def eiaWorkFlowService

    /**
     * 保存环保咨询项目信息,和eiaProject一起保存，此方法暂时禁用
     * @param params
     * @param session
     */
    def eiaEnvProjectSave(params, session) {
        def eiaEnvProject = new EiaEnvProject(params)
        eiaEnvProject.eiaProjectId = params.long('eiaProjectId')
        eiaEnvProject.inputDept = session.staff.orgName
        eiaEnvProject.inputDeptCode = session.staff.orgCode
        eiaEnvProject.inputDeptId = Long.parseLong(session.staff.orgId)
        eiaEnvProject.inputUser = session.staff.staffName
        eiaEnvProject.inputUserId = Long.parseLong(session.staff.staffId)
        eiaEnvProject.save(flush: true, failOnError: true)
    }
/**
 * 修改环保咨询项目信息
 * @param params
 * @param session
 * @return
 */
    def eiaEnvProjectUpdate(params, session) {
        def eiaEnvProject = EiaEnvProject.findByIdAndIfDel(params.long('eiaEnvProjectId'), false)
        eiaEnvProject.properties = params
        eiaEnvProject.inputDept = session.staff.orgName
        eiaEnvProject.inputDeptCode = session.staff.orgCode
        eiaEnvProject.inputDeptId = Long.parseLong(session.staff.orgId)
        eiaEnvProject.inputUser = session.staff.staffName
        eiaEnvProject.inputUserId = Long.parseLong(session.staff.staffId)
        eiaEnvProject.save(flush: true, failOnError: true)
    }
/**
 * 获取环保咨询项目List
 * @param params
 */
    def getEnvProDataList(params) {
        int page
        int limit
        if (params.page && params.limit) {
            page = params.int('page') - 1
            limit = params.int('limit')
        }
        def envProList = EiaEnvProject.createCriteria().list(max: limit, offset: page * limit) {
            def projectName = params.projectName
            if (projectName && !"项目名称".equals(projectName)) {
                like("projectName", "%" + projectName + "%")
            }
            eq("ifDel", false)
            order("id", "desc")
        }
        def resultDataMap = [:]
        resultDataMap.data = envProList
        resultDataMap.count = envProList.totalCount
        return resultDataMap
    }
    /**
     * 获取环保咨询项目详情
     * @param params
     */
    def getProjectExploreDataMap(params) {
        def eiaProjectId = params.long('eiaProjectId')
        def eiaProject = EiaProject.findByIdAndIfDel(eiaProjectId, false)
        def signInputUserId = eiaProject.inputUserId
        def eiaTask = EiaTask.findByIdAndIfDel(eiaProject.eiaTaskId, false)
        //def eiaTaskAssign = EiaTaskAssign.findByTaskAssignRoleAndTaskIdAndIfDel(GeneConstants.TASK_ASSIGN_ROLE_TOAST, eiaTask.id, false)
        def resMap = [:]
        resMap.projectName = eiaProject.projectName
        resMap.projectNo = eiaProject.projectNo
        resMap.inputUser = eiaProject.inputUser
        resMap.inputUserId = eiaProject.inputUserId
        resMap.inputDept = eiaProject.inputDept
        resMap.inputDeptId = eiaProject.inputDeptId
        resMap.inputDeptCode = eiaProject.inputDeptCode
        resMap.buildArea = eiaProject.buildArea
        resMap.fileTypeChild = eiaProject.fileTypeChild
        resMap.sign = eiaWorkFlowService.getReport(signInputUserId)
        eiaProject.dutyUser ? (resMap.taskAssignUser = eiaProject.dutyUser) : (resMap.taskAssignUser = eiaProject.inputUser)
        eiaProject.dutyUser ? (resMap.dutyUser = eiaProject.dutyUser) : (resMap.dutyUser = eiaProject.inputUser)
        eiaProject.dutyUserId ? (resMap.dutyUserId = eiaProject.dutyUserId) : (resMap.dutyUserId = eiaProject.inputUserId)
        def eiaEnvProject = EiaEnvProject.findByEiaProjectIdAndIfDel(eiaProjectId, false)
        if (eiaEnvProject) {
            if (eiaEnvProject.exploreDate) {
                resMap.exploreDate = eiaEnvProject.exploreDate.format('yyyy-MM-dd')
            }
            resMap.eiaEnvProjectId = eiaEnvProject.id
            resMap.ifSensArea = eiaEnvProject.ifSensArea
            resMap.ifCityPlan = eiaEnvProject.ifCityPlan
            resMap.exploreRecord = eiaEnvProject.exploreRecord
        }
        return resMap
    }

    /**
     * 获取环保咨询项目详情
     * @param params
     */
    def getProjectExploreSaveDataMap(params) {
        def eiaProjectId = params.long('eiaProjectId')
        def resMap = [:]
        def eiaEnvProject = EiaEnvProject.findByEiaProjectIdAndIfDel(eiaProjectId, false)
        def eiaProject = EiaProject.findByIdAndIfDel(eiaProjectId,false)
        if (eiaEnvProject) {
            if (eiaEnvProject.exploreDate) {
                resMap.exploreDate = eiaEnvProject.exploreDate.format('yyyy-MM-dd')
            }
            resMap.eiaEnvProjectId = eiaEnvProject.id
            resMap.ifSensArea = eiaEnvProject.ifSensArea
            resMap.ifCityPlan = eiaEnvProject.ifCityPlan
            resMap.buildArea = eiaProject.buildArea
            resMap.exploreRecord = eiaEnvProject.exploreRecord
        }
        return resMap
    }
    /**
     * 现场勘察信息保存
     */
    def exploreInfoSave(params) {
        def eiaProject = EiaProject.findByIdAndIfDel(params.long('eiaProjectId'),false)
        eiaProject.dutyUser = params.dutyUser
        eiaProject.dutyUserId = params.long('dutyUserId')
        if(params.buildArea){
            eiaProject.buildArea = params.buildArea
        }
        eiaProject.save(flush: true, failOnError: true)
        if (params.eiaEnvProjectId) {
            params.exploreDate = DateTransTools.getFormatDateD(params.exploreDate)
            def eiaEnvProject = EiaEnvProject.findByIdAndIfDel(params.long('eiaEnvProjectId'), false)
            eiaEnvProject.inputUser = params.inputUser
            if (params.ifSensArea == 'true') {
                eiaEnvProject.ifSensArea = true
            } else if (params.ifSensArea == 'false') {
                eiaEnvProject.ifSensArea = false
            }
            if (params.ifCityPlan == 'true') {
                eiaEnvProject.ifCityPlan = true
            } else if (params.ifCityPlan == 'false') {
                eiaEnvProject.ifCityPlan = false
            }
            eiaEnvProject.exploreDate = params.exploreDate
            eiaEnvProject.exploreRecord = params.exploreRecord
            eiaEnvProject.save(flush: true, failOnErro: true)
        } else {
            def eiaEnvProject = new EiaEnvProject()
            params.exploreDate = DateTransTools.getFormatDateD(params.exploreDate)
            eiaEnvProject.eiaProjectId = params.long('eiaProjectId')
            eiaEnvProject.inputUser = params.inputUser
            eiaEnvProject.inputUserId = params.long('inputUserId')
            eiaEnvProject.inputDept = params.inputDept
            eiaEnvProject.inputDeptId = params.long('inputDeptId')
            eiaEnvProject.inputDeptCode = params.inputDeptCode
            if (params.ifSensArea == 'true') {
                eiaEnvProject.ifSensArea = true
            } else if (params.ifSensArea == 'false') {
                eiaEnvProject.ifSensArea = false
            }
            if (params.ifCityPlan == 'true') {
                eiaEnvProject.ifCityPlan = true
            } else if (params.ifCityPlan == 'false') {
                eiaEnvProject.ifCityPlan = false
            }
            eiaEnvProject.exploreDate = params.exploreDate
            eiaEnvProject.exploreRecord = params.exploreRecord
            eiaEnvProject.save(flush: true, failOnErro: true)
        }
    }
    /**
     * 获取项目子表的数据,无论字表是否存在，都返回map
     * @param params
     */
    def getEnvProDataMap(params) {
        def eiaEnv
        def eiaEne
        def eiaGreen
        if (!params.eiaProjectId) {
            return false
        } else {
            eiaEnv = EiaEnvProject.findByEiaProjectIdAndIfDel(params.long('eiaProjectId'), false)
            if (!eiaEnv) {
                eiaEne = EiaEneProject.findByEiaProjectIdAndIfDel(params.long('eiaProjectId'), false)
                if (!eiaEne) {
                    eiaGreen = EiaGreenProject.findByEiaProjectIdAndIfDel(params.long('eiaProjectId'), false)
                }
            }
        }
        def dataMap
        def fileTypeCode = EiaProject.findById(params.long('eiaProjectId')).fileTypeCode
        if (fileTypeCode == "EPC_HB") {
            dataMap = this.combNeedMap(eiaEnv?.properties, GeneConstants.HBPROPLIST)
        } else if (fileTypeCode == "EPC_YS") {
            dataMap = this.combNeedMap(eiaEnv?.properties, GeneConstants.YSPROPLIST)
        } else if (fileTypeCode == "EPC_YA") {
            dataMap = this.combNeedMap(eiaEnv?.properties, GeneConstants.YAPROPLIST)
        } else if (fileTypeCode == "EPC_JL") {
            dataMap = this.combNeedMap(eiaEnv?.properties, GeneConstants.JLPROPLIST)
        } else if (fileTypeCode == "EPC_GH") {
            dataMap = this.combNeedMap(eiaEnv?.properties, GeneConstants.GHPROPLIST)
        } else if (fileTypeCode == "EPC_HH") {
            dataMap = this.combNeedMap(eiaEnv?.properties, GeneConstants.HHPROPLIST)
        } else if (fileTypeCode == "EPC_SH") {
            dataMap = this.combNeedMap(eiaEnv?.properties, GeneConstants.SHPROPLIST)
        } else if (fileTypeCode == "EPC_XZ") {
            dataMap = this.combNeedMap(eiaEnv?.properties, GeneConstants.XZPROPLIST)
        } else if (fileTypeCode == "EPC_PF") {
            dataMap = this.combNeedMap(eiaEnv?.properties, GeneConstants.PFPROPLIST)
        } else if (fileTypeCode == "EPC_CD") {
            dataMap = this.combNeedMap(eiaEnv?.properties, GeneConstants.CDPROPLIST)
        } else if (fileTypeCode == "EPC_ST") {
            dataMap = this.combNeedMap(eiaEnv?.properties, GeneConstants.STPROPLIST)
        } else if (fileTypeCode == "EPC_HP") {
            dataMap = this.combNeedMap(eiaEnv?.properties, GeneConstants.HPPROPLIST)
        } else if (fileTypeCode == "EPC_HY") {
            dataMap = this.combNeedMap(eiaEnv?.properties, GeneConstants.HYPROPLIST)
        } else if (fileTypeCode == "ESE_LZ") {
            dataMap = this.combNeedMap(eiaEne?.properties, GeneConstants.ESELZPROPLIST)
        } else if (GeneConstants.GREENLIST.contains(fileTypeCode)) {
            dataMap = this.combNeedMap(eiaGreen?.properties, GeneConstants.GREENPROPLIST)
        }
        return dataMap
    }

    /**
     * 删除环保咨询项目
     * @param params
     * @param sssion
     * @return
     */
    def envProDel(params, sssion) {
        if (!params.eiaEnvProjectId) {
            return false
        } else {
            def envProId = params.long('eiaEnvProjectId')
            def eiaEnv = EiaEnvProject.findByIdAndIfDel(envProId, false)
            eiaEnv.ifDel = false
            eiaEnv.save(flush: true, failOnError: true)
        }
    }
    /**
     * 把需要的字段组装成新的map
     * @param map
     * @param ndProp
     * @return
     */
    def combNeedMap(Object map, List<String> ndProp) {
        def currMap = [:]
        ndProp.each {
            if (map) {
                if (map.getAt(it) != null) {
                    currMap.put(it, map.getAt(it))
                } else {
                    currMap.put(it, null)
                }
            } else {
                currMap.put(it, null)
            }
        }
        return currMap
    }
    /**
     * 转换为map
     * @param map
     * @return
     */
    def convertToMap(Object map) {
        def currMap = [:]

        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            currMap.put(entry.getKey(), entry.getValue())
        }
        return currMap
    }
    /**
     *  上传图片
     */
    def uploadExploreImage(request, session, params) {
        def fileInfo = eiaFileUploadService.upload(request, session, GeneConstants.DOMAIN_EIA_ENV_PROJECT, params.long('eiaEnvProjectId'), GeneConstants.UPLOADTYPE_EXPLORE_IMAGE, "")
        if (fileInfo) {
            return true
        } else {
            return false
        }
    }
    /**
     * 获取现场勘察图片列表
     */
    def getExploreImageList(params) {
        def exploreFiles = EiaFileUpload.findAllByTableNameAndTableIdAndIfDel(GeneConstants.DOMAIN_EIA_ENV_PROJECT, params.long('eiaEnvProjectId'), false)
        def imgList = []
        exploreFiles.each {
            def fileInfo = [:]
            fileInfo.path = GeneConstants.EIA_FILE_URL_PATH + it.filePath + "/" + it.saveFileName
            fileInfo.fileName = it.fileName
            fileInfo.id = it.id
            imgList << fileInfo
        }
        return imgList
    }

    /**
     *  删除已经上传的现场图片
     */
    def exploreImageDel(params) {
        def eiaFileId = params.long('imgId')
        def eiaFileUpload = EiaFileUpload.findByIdAndIfDel(eiaFileId, false)
        if (eiaFileUploadService.delete(eiaFileId)) {
            eiaFileUpload.save(flush: true, failOnError: true)
        } else {
            return false
        }
    }

}
