package com.lheia.eia.project

import com.lheia.eia.common.GeneConstants
import com.lheia.eia.common.HttpMesConstants
import com.lheia.eia.common.WorkFlowConstants
import grails.converters.JSON

class EiaProjectExploreController {
    def eiaProjectExploreService
    def eiaWorkFlowProjectExploreService
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
    /***
     * 打印页
     */
    def eiaProjectExplorePrint(){}
    /**
     * 地图显示页
     * @return
     */
    def eiaMapDetail(){}

    def eiaProjectExploreQueryPage(){
        def dataMap =  eiaProjectExploreService.eiaProjectExploreQueryPage(params,session)
        if (dataMap) {
            render([code: HttpMesConstants.CODE_OK, count: dataMap.total, data: dataMap.data] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
    /*****/
    def getEiaProjectExploreDataMap(){
        def dataMap =  eiaProjectExploreService.getEiaProjectExploreDataMap(params)
        if (dataMap) {
            render([code: HttpMesConstants.CODE_OK,  data: dataMap] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
    /***select转码**/
    def getEiaProjectExploreDataMapDomainCode(){
        def dataMap =  eiaProjectExploreService.getEiaProjectExploreDataMapDomainCode(params)
        if (dataMap) {
            render([code: HttpMesConstants.CODE_OK,  data: dataMap] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }

    /**删除***/
    def eiaProjectExploreDel(){
        def dataMap =  eiaProjectExploreService.eiaProjectExploreDel(params)
        if (dataMap) {
            render([code: HttpMesConstants.CODE_OK,  data: dataMap] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DEL_FAIL] as JSON)
        }
    }
    /***
     * 保存
     * @return
     */
    def eiaProjectExploreSave(){
        def dataMap =  eiaProjectExploreService.eiaProjectExploreSave(params,session)
        if (dataMap) {
            render([code: HttpMesConstants.CODE_OK,  data: dataMap] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }

    /**
     * 渲染checkbox和select
     */
    def getCheckboxAndSelectValue(){
        def codeList = eiaProjectExploreService.getCheckboxAndSelectValue()
        if (codeList) {
            render([code: HttpMesConstants.CODE_OK, data: codeList] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }

    /***
     * 提交内审单并开启工作流
     * @return
     */
    def eiaProjectExploreSub() {
        eiaWorkFlowProjectExploreService.getWorkFlowCode(session)
        long eiaProjectExploreId = Long.valueOf(params.eiaProjectExploreId)
        def eiaProjectExplore = EiaProjectExplore.findByIdAndIfDel(eiaProjectExploreId,false)
        if(eiaProjectExplore.ifSub){
            render([code: HttpMesConstants.CODE_FAIL,msg:HttpMesConstants.MSG_FCONF_NULL] as JSON)
        }else{
            def workFlowCode = eiaWorkFlowProjectExploreService.getWorkFlowCode(session)
                def eiaWorkFlowBusi = eiaWorkFlowProjectExploreService.startProjectExploreWorkFlow(workFlowCode, GeneConstants.DOMAIN_EIA_PROJECT_EXPLORE, eiaProjectExploreId, WorkFlowConstants.PROJECT_EXPLORE_WORK_FLOW_START_NODE,session)
                if(eiaWorkFlowBusi){
                    eiaProjectExplore = eiaProjectExploreService.eiaProjectExploreSub(eiaProjectExploreId)
                    if (eiaProjectExplore && eiaWorkFlowBusi) {
                        render([code: HttpMesConstants.CODE_OK, data: eiaProjectExplore] as JSON)
                    } else {
                        render([code: HttpMesConstants.CODE_FAIL] as JSON)
                    }
                }else{
                    render([code: HttpMesConstants.CODE_FAIL,msg:HttpMesConstants.MSG_FCONF_NULL] as JSON)
                }
        }
    }


}
