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
}
