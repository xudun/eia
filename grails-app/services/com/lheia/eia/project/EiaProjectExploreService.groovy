package com.lheia.eia.project

import com.lheia.eia.common.GeneConstants
import com.lheia.eia.config.EiaDomainCode
import grails.gorm.transactions.Transactional

@Transactional
class EiaProjectExploreService {
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

    /***
     * 获取内审单数据
     */
    def getEiaProjectExploreDataMap(params){
        Long eiaProjectExploreId = params.long("eiaProjectExploreId")
        return EiaProjectExplore.findByIdAndIfDel(eiaProjectExploreId,false)
    }
    /***
     * 保存数据
     */
    def eiaProjectExploreSave(params,session){
        def eiaProjectExplore
        if(params.eiaProjectExploreId){
            Long eiaProjectExploreId = Long.valueOf(params.eiaProjectExploreId)
            eiaProjectExplore = EiaProjectExplore.findByIdAndIfDel(eiaProjectExploreId)
        }else{
            eiaProjectExplore = new EiaProjectExplore()
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
        eiaProjectExplore.save(flush: true, failOnError: true)
        eiaProjectExplore.exploreNo = "E"+eiaProjectExplore.id
        eiaProjectExplore.save(flush: true, failOnError: true)
    }
}
