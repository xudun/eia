package com.lheia.eia.project

import com.lheia.eia.common.HttpMesConstants
import grails.converters.JSON

class EiaProjectExploreController {
    def eiaProjectExploreService
    /**
     * 列表
     */
    def eiaProjectExploreIndex(){}
    /**
     * 创建
     */
    def eiaProjectExploreCreate(){}
    /***
     * 详情页
     */
    def eiaProjectExploreDetail(){}

    def eiaProjectExploreQueryPage(){
        def dataMap =  eiaProjectExploreService.eiaProjectExploreQueryPage(params,session)
        if (dataMap) {
            render([code: HttpMesConstants.CODE_OK, count: dataMap.total, data: dataMap.data] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }


}
