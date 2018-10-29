package com.lheia.eia.project

import com.lheia.eia.common.FuncConstants
import com.lheia.eia.common.GeneConstants
import com.lheia.eia.common.HttpMesConstants
import com.lheia.eia.common.HttpUrlConstants
import com.lheia.eia.common.WorkFlowConstants
import com.lheia.eia.config.EiaDomainCode
import com.lheia.eia.tools.HttpConnectTools
import com.lheia.eia.tools.JsonHandler
import com.lheia.eia.workflow.EiaWorkFlowBusiLog
import grails.gorm.transactions.Transactional

@Transactional
class EiaProjectExploreService {
    def eiaProjectService
    /**
     * 查询分页
     */
    def eiaProjectExploreQueryPage(params,session) {
        int page
        int limit
        if (params.page && params.limit) {
            page = params.int('page') - 1
            limit = params.int('limit')
        }
        def eiaProjectExploreList = EiaProjectExplore.createCriteria().list(max: limit, offset: page * limit) {
            def projectName = params.projectName
            if (projectName && !"项目名称、项目编号、项目负责人、录入部门、录入人".equals(projectName)) {
                or {
                    like("projectName", "%" + projectName + "%")
                    like("inputDept", "%" + projectName + "%")
                    like("inputUser", "%" + projectName + "%")
                    like("exploreNo", "%" + projectName + "%")
                }
            }
            /**
             * 查看全部的客户数据
             */
            if (!session?.staff?.funcCode?.contains(FuncConstants.EIA_XMGL_XMCS_VIEWALL)) {
                /**
                 * 查看本部门客户数据
                 */
                if (session?.staff?.funcCode?.contains(FuncConstants.EIA_XMGL_XMCS_VIEWDEPT)) {
                    like("inputDeptCode", "%" + session.staff.orgCode + "%")
                }
                /**
                 * 查看本人客户数据
                 */
                else if (session?.staff?.funcCode?.contains(FuncConstants.EIA_XMGL_XMCS_VIEWSELF)) {
                    eq("inputUserId", Long.valueOf(session.staff.staffId))
                }
            }
            eq("ifDel",false)
        }
        def dataMap = [:]
        dataMap.data = eiaProjectExploreList
        dataMap.total = eiaProjectExploreList.totalCount
        return dataMap
    }

    /**
     * 渲染checkbox和select
     */
    def getCheckboxAndSelectValue(){
        def codeList = EiaDomainCode.findAllByParentCode(GeneConstants.DOMAIN_EIA_PROJECT_EXPLORE)
        return codeList
    }

    /**
     * 外审提交
     * @param eiaContractId
     */
    def eiaProjectExploreSub(eiaProjectExploreId) {
        def eiaProjectExplore = EiaProjectExplore.findByIdAndIfDel(eiaProjectExploreId, false)
        eiaProjectExplore.ifSub = true
        eiaProjectExplore.save(flush: true, failOnError: true)
    }

    /***
     * 获取内审单数据
     */
    def getEiaProjectExploreDataMap(params){
        Long eiaProjectExploreId = params.long("eiaProjectExploreId")
        return EiaProjectExplore.findByIdAndIfDel(eiaProjectExploreId,false)
    }
    /***
     * 删除内审单数据
     */
    def eiaProjectExploreDel(params){
        Long eiaProjectExploreId = params.long("eiaProjectExploreId")
        def eiaProjectExplore  = EiaProjectExplore.findByIdAndIfDel(eiaProjectExploreId,false)
        eiaProjectExplore.ifDel = true
        def resMap = JsonHandler.jsonToMap(HttpConnectTools.getResponseJson(HttpUrlConstants.GIS_GEO_PROJECT_EXPLORE_DEL, [eiaProjectExploreId: eiaProjectExploreId]))
        eiaProjectExplore.save(flush: true, failOnError: true)
    }

    /***
     * 获取内审单数据
     */
    def getEiaProjectExploreDataMapDomainCode(params){
        Long eiaProjectExploreId = params.long("eiaProjectExploreId")
        def eiaProjectExplore  = EiaProjectExplore.findByIdAndIfDel(eiaProjectExploreId,false)
        def resMap = [:]
        resMap.putAll(eiaProjectExplore.properties)
        /**替换select编码***/
        eiaProjectExplore.properties.each{
            def eiaDomainCode =  EiaDomainCode.findByParentCodeAndDomainAndCodeAndCodeRemark(GeneConstants.DOMAIN_EIA_PROJECT_EXPLORE,it.key,it.value,"select")
            if(eiaDomainCode){
                resMap[it.key] =eiaDomainCode.codeDesc
            }
        }
        def zlNodeLog = EiaWorkFlowBusiLog.findByTableNameAndTableNameIdAndNodesCodeInList(GeneConstants.DOMAIN_EIA_PROJECT_EXPLORE,eiaProjectExploreId,[WorkFlowConstants.NODE_CODE_ZLSP,WorkFlowConstants.NODE_CODE_FGSFZRSP])
        if(zlNodeLog){
            resMap.zlnodeimg = eiaProjectService.getReport(zlNodeLog.updateUserId)
        }

        def jlNodeLog = EiaWorkFlowBusiLog.findByTableNameAndTableNameIdAndNodesCodeInList(GeneConstants.DOMAIN_EIA_PROJECT_EXPLORE,eiaProjectExploreId,[WorkFlowConstants.NODE_CODE_BMJLSP,WorkFlowConstants.NODE_CODE_FGSDSZSP])
        if(jlNodeLog){
            resMap.jlnodeimg = eiaProjectService.getReport(jlNodeLog.updateUserId)
        }
        return resMap
    }
    /***
     * 保存数据
     */
    def eiaProjectExploreSave(params,session){
        def eiaProjectExplore
        if(params.eiaProjectExploreId){
            Long eiaProjectExploreId = Long.valueOf(params.eiaProjectExploreId)
            eiaProjectExplore = EiaProjectExplore.findByIdAndIfDel(eiaProjectExploreId,false)
        }else{
            eiaProjectExplore = new EiaProjectExplore()
            eiaProjectExplore.eiaTaskId = 0
            eiaProjectExplore.eiaProjectId = 0
            eiaProjectExplore.gisGeoProjectId = 0
            eiaProjectExplore.inputDept = session.staff.orgName
            eiaProjectExplore.inputDeptCode = session.staff.orgCode
            eiaProjectExplore.inputDeptId = Long.parseLong(session.staff.orgId)
            eiaProjectExplore.inputUser = session.staff.staffName
            eiaProjectExplore.inputUserId = Long.parseLong(session.staff.staffId)
        }
        eiaProjectExplore.properties = params
        def environmentaTypeDropCode = params.long('environmentaTypeDropCode')
        def environment = EiaDomainCode.findById(environmentaTypeDropCode)
        if (environment) {
            eiaProjectExplore.environmentaTypeCode = environment.code
            eiaProjectExplore.environmentaTypeDesc = environment.codeDesc
        }

        def fileTypeId = params.long('fileTypeDropCode')    //此fileType为子文件类型Id,根据此Id查询父Id,可从json中获取
        def fileTypeChildDomain = EiaDomainCode.findByDomainAndId(GeneConstants.PROJECT_FILE_TYPE, fileTypeId)
        def fileTypeChild = fileTypeChildDomain.codeDesc
        def fileTypeChildCode = fileTypeChildDomain.code
        def fileTypeCode = fileTypeChildDomain.parentCode
        def fileTypeDomain = EiaDomainCode.findByDomainAndCode(GeneConstants.PROJECT_FILE_TYPE, fileTypeCode)
        def fileType = fileTypeDomain.codeDesc
        eiaProjectExplore.fileTypeChild = fileTypeChild
        eiaProjectExplore.fileTypeChildCode = fileTypeChildCode
        eiaProjectExplore.fileTypeCode = fileTypeCode
        eiaProjectExplore.fileType = fileType

        eiaProjectExplore.save(flush: true, failOnError: true)
        eiaProjectExplore.exploreNo = "E-"+eiaProjectExplore.id
        eiaProjectExplore.save(flush: true, failOnError: true)
    }
}
