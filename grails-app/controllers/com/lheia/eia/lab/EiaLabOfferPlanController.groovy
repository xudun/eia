package com.lheia.eia.lab

import com.lheia.eia.common.HttpMesConstants
import com.lheia.eia.common.HttpUrlConstants
import com.lheia.eia.tools.HttpConnectTools
import com.lheia.eia.tools.JsonHandler
import grails.converters.JSON

class EiaLabOfferPlanController {
    
    def eiaLabOfferPlanService

    def eiaLabOfferPlanCreate() {
        render(view: "/eiaLab/eiaLabOfferPlanCreate.gsp")
    }
    def eiaLabOfferPlanDetail() {
        render(view: "/eiaLab/eiaLabOfferPlanDetail.gsp")
    }
    def eiaLabOfferPlanGroupCreate() {
        render(view: "/eiaLab/eiaLabOfferPlanGroupCreate.gsp")
    }
    def eiaParamGroupSelect() {
        render(view: "/eiaLab/eiaParamGroupSelect.gsp")
    }
    def eiaParamGroupDetail() {
        render(view: "/eiaLab/eiaParamGroupDetail.gsp")
    }
    def eiaLabOfferPlanBatchDiscDetail() {
        render(view: "/eiaLab/eiaLabOfferPlanBatchDiscDetail.gsp")
    }
    def eiaPlanCalList() {
        render(view: "/eiaLab/eiaPlanCalList.gsp")
    }
    def eiaPlanCalCreate() {
        render(view: "/eiaLab/eiaPlanCalCreate.gsp")
    }
    def eiaPlanCalGroupCreate() {
        render(view: "/eiaLab/eiaPlanCalGroupCreate.gsp")
    }
    def eiaPlanCalGroupSelect() {
        render(view: "/eiaLab/eiaPlanCalGroupSelect.gsp")
    }

    /**
     * 保存检测计划
     * @return
     */
    def eiaLabOfferPlanSave() {
        /** 检测计划判重 */
        def checkResult = eiaLabOfferPlanService.checkOfferPlan(params)
        if (checkResult == HttpMesConstants.MSG_DATA_EXIST) {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_EXIST] as JSON)
        } else {
            if (!params.eiaLabOfferPlanId) {
                if (eiaLabOfferPlanService.eiaLabOfferPlanSave(params, session)) {
                    render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SAVE_OK] as JSON)
                } else {
                    render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
                }
            } else {
                if (eiaLabOfferPlanService.eiaLabOfferPlanUpdate(params)) {
                    render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SAVE_OK] as JSON)
                } else {
                    render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
                }
            }
        }
    }
    /**
     * 删除检测计划
     */
    def eiaLabOfferPlanDel() {
        if (eiaLabOfferPlanService.eiaLabOfferPlanDel(params.long('eiaLabOfferPlanId'))) {
            render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_DEL_OK] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DEL_FAIL] as JSON)
        }
    }
    /**
     * 获取检测计划
     */
    def getEiaLabOfferPlanDataMap() {
        def dataMap = eiaLabOfferPlanService.getEiaLabOfferPlanDataMap(params.long('eiaLabOfferId'), params.long('eiaLabOfferPlanId'))
        if (dataMap) {
            render([code: HttpMesConstants.CODE_OK, data: dataMap] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
    /**
     * 获取检测计划列表
     */
    def getEiaLabOfferPlanDataList() {
        def dataMap = eiaLabOfferPlanService.getEiaLabOfferPlanDataList(params)
        if (dataMap) {
            render([code: HttpMesConstants.CODE_OK, count: dataMap.total, data: dataMap.data, sampleFee: dataMap.sampleFee] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
    /**
     * 保存检测计划套餐
     */
    def eiaLabOfferPlanGroupSave() {
        /** 判断选择不同套餐时，是否包含相同检测计划 */
        def checkResult = eiaLabOfferPlanService.checkGroup(params)
        if (checkResult == HttpMesConstants.MSG_OFFER_PLAN_REPEAT) {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_OFFER_PLAN_REPEAT] as JSON)
        } else {
            if (eiaLabOfferPlanService.eiaLabOfferPlanGroupSave(params, session)) {
                render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SAVE_OK] as JSON)
            } else {
                render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
            }
        }
    }
    /**
     * 获取检测计划套餐
     */
    def getEiaLabOfferPlanGroupDataMap() {
        def data = eiaLabOfferPlanService.getEiaLabOfferPlanGroupDataMap(params.long('eiaLabOfferId'), params.long('labTestParamGroupId'))
        if (data) {
            render([code: HttpMesConstants.CODE_OK, data: data] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
    /**
     * 检测计划批量打折
     */
    def eiaLabOfferPlanBatchDiscount() {
        def data = eiaLabOfferPlanService.eiaLabOfferPlanBatchDiscount(params)
        if (data) {
            render([code: HttpMesConstants.CODE_OK] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
    /**
     * 三级下拉获取检测基质list
     */
    def getBaseList() {
        def param = [:]
        def baseList = HttpConnectTools.getResponseJson(HttpUrlConstants.LAB_TEST_BASE_LIST_INFO, param)
        if (baseList) {
            def data = JsonHandler.jsonToMap(baseList).data
            render([code: HttpMesConstants.CODE_OK, data: data] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
    /**
     * 三级下拉获取检测项目list
     */
    def getParamList() {
        def param = [:]
        param.labTestBaseId = params.labTestBaseId
        def paramList = HttpConnectTools.getResponseJson(HttpUrlConstants.LAB_TEST_PARAM_LIST_INFO, param)
        if (paramList) {
            def data = JsonHandler.jsonToMap(paramList).data
            render([code: HttpMesConstants.CODE_OK, data: data] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
    /**
     * 获取选择的检测项目下，最高的检测费用
     */
    def getMaxSchemeMoney() {
        def param = [:]
        param.labTestBaseId = params.labTestBaseId
        param.labTestParamId = params.labTestParamId
        def maxSchemeMoney = HttpConnectTools.getResponseJson(HttpUrlConstants.MAX_SCHEME_MONEY_INFO, param)
        if (maxSchemeMoney) {
            def data = JsonHandler.jsonToMap(maxSchemeMoney).data
            render([code: HttpMesConstants.CODE_OK, data: data] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
    /**
     * 检查是否是分包的检测项目
     */
    def checkIfSub() {
        def param = [:]
        param.labTestParamId = params.labTestParamId
        def ifSub = HttpConnectTools.getResponseJson(HttpUrlConstants.CHECK_IF_SUB_INFO, param)
        if (ifSub != null) {
            def data = JsonHandler.jsonToMap(ifSub).data
            render([code: HttpMesConstants.CODE_OK, data: data] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
    /**
     * 检测计划套餐分页查询
     */
    def getLabTestParamGroupDataList() {
        def param = [:]
        param.page = params.page
        param.limit = params.limit
        param.groupName = params.groupName
        def resultJson = HttpConnectTools.getResponseJson(HttpUrlConstants.LAB_TEST_PARAM_GROUP_LIST_INFO, param)
        if (resultJson) {
            def groupList = []
            def labJson = JsonHandler.jsonToMap(resultJson).data.data
            def count = JsonHandler.jsonToMap(resultJson).data.total
            labJson.each {
                def dataMap = [:]
                dataMap.id = it.id
                dataMap.groupName = it?.groupName
                dataMap.groupFee = it?.groupFee
                dataMap.groupDiscount = it?.groupDiscount
                groupList << dataMap
            }
            render([code: HttpMesConstants.CODE_OK, data: groupList, count: count] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
    /**
     * 检测计划套餐分页查询
     */
    def getLabTestCapGroupDataList() {
        def param = [:]
        param.page = params.page
        param.limit = params.limit
        param.labTestParamGroupId = params.labTestParamGroupId
        def resultJson = HttpConnectTools.getResponseJson(HttpUrlConstants.LAB_TEST_CAP_GROUP_LIST_INFO, param)
        if (resultJson) {
            def groupList = []
            def labJson = JsonHandler.jsonToMap(resultJson).data.data
            def count = JsonHandler.jsonToMap(resultJson).data.total
            labJson.each {
                def map = [:]
                map.baseName = it.baseName
                map.paramNameCn = it.paramNameCn
                map.schemeName = it.schemeName + " " + it?.schemeCode
                map.schemeMoney = it.schemeMoney
                map.discount = it?.discount
                map.pointNum = it?.pointNum
                map.dayNum = it?.dayNum
                map.freqNum = it?.freqNum
                /**
                 * 小计
                 */
                def discount = it?.discount
                def pointNum = it.pointNum ?:0
                def dayNum = it.dayNum ?:0
                def freqNum = it.freqNum ?:0
                if (discount) {
                    map.discountFee = it.schemeMoney * discount/100
                    map.subTotal = pointNum * dayNum * freqNum * it.schemeMoney * discount/100
                } else {
                    map.discountFee = it.schemeMoney
                    map.subTotal = pointNum * dayNum * freqNum * it.schemeMoney
                }
                groupList << map
            }
            render([code: HttpMesConstants.CODE_OK, data: groupList, count: count] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
    /**
     * 选择检测套餐后，返回套餐内容为JSON格式
     */
    def getParamGroupDataMap() {
        def dataMap = eiaLabOfferPlanService.getParamGroupDataMap(params)
        if (dataMap) {
            render([code: HttpMesConstants.CODE_OK, data: dataMap] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
}
