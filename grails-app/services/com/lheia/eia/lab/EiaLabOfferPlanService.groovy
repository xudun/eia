package com.lheia.eia.lab

import com.lheia.eia.common.HttpMesConstants
import com.lheia.eia.common.HttpUrlConstants
import com.lheia.eia.tools.HttpConnectTools
import com.lheia.eia.tools.JsonHandler
import grails.gorm.transactions.Transactional
import org.apache.commons.lang.StringUtils

@Transactional
class EiaLabOfferPlanService {

    def eiaLabOfferService

    /**
     * 检测方案检测计划判重
     */
    def checkOfferPlan(params) {
        def eiaLabOfferId = params.long('eiaLabOfferId')
        def eiaLabOfferPlanId = params.long('eiaLabOfferPlanId')
        def labTestBaseId = params.long('labTestBaseId')
        def labTestParamId = params.long('labTestParamId')
        def labTestSchemeId = params.long('labTestSchemeId')
        def memo = params.memo
        if (!memo) {
            memo = null
        }
        def eiaLabOfferPlanList = EiaLabOfferPlan.findAllByEiaLabOfferIdAndLabTestBaseIdAndLabTestParamIdAndLabTestSchemeIdAndIfDel(eiaLabOfferId, labTestBaseId, labTestParamId, labTestSchemeId, false)
        if (eiaLabOfferPlanList.size() > 0) {
            for (Object offerPlan : eiaLabOfferPlanList) {
                def offerPlanMemo = offerPlan?.memo
                if (!offerPlanMemo) {
                    offerPlanMemo = null
                }
                if (eiaLabOfferPlanId != offerPlan.id && memo == offerPlanMemo) {
                    return HttpMesConstants.MSG_DATA_EXIST
                }
            }
        }
    }
    /**
     * 保存检测方案检测计划
     */
    def eiaLabOfferPlanSave(params, session) {
        def eiaLabOfferPlan = new EiaLabOfferPlan(params)
        def eiaLabOfferId = params.long('eiaLabOfferId')
        eiaLabOfferPlan.eiaLabOfferId = eiaLabOfferId
        eiaLabOfferPlan.labClientId = EiaLabOffer.findByIdAndIfDel(eiaLabOfferId, false)?.wtClientId
        /**
         * 获取检测基质、参数、标准，并保存labTestCapId
         */
        def labTestBaseId = params.long('labTestBaseId')
        def labTestParamId = params.long('labTestParamId')
        def baseParam = [:]
        baseParam.labTestBaseId = params.labTestBaseId
        def labTestBase = HttpConnectTools.getResponseJson(HttpUrlConstants.LAB_TEST_BASE_INFO, baseParam)
        if (labTestBase) {
            def baseData = JsonHandler.jsonToMap(labTestBase).data
            eiaLabOfferPlan.labTestBaseId = labTestBaseId
            eiaLabOfferPlan.baseName = baseData.baseName
            eiaLabOfferPlan.baseNameCode = baseData.baseNameCode
        }
        def param = [:]
        param.labTestParamId = params.labTestParamId
        def labTestParam = HttpConnectTools.getResponseJson(HttpUrlConstants.LAB_TEST_PARAM_INFO, param)
        if (labTestParam) {
            def paramData = JsonHandler.jsonToMap(labTestParam).data
            eiaLabOfferPlan.labTestParamId = labTestParamId
            eiaLabOfferPlan.paramNameCn = paramData.paramNameCn
            eiaLabOfferPlan.paramNameEn = paramData.paramNameEn
        }
        /**
         * 检测计划基本信息
         */
        if (params.schemeMoney) {
            eiaLabOfferPlan.schemeMoney = new BigDecimal(params.schemeMoney)
        }
        if (params.discount) {
            eiaLabOfferPlan.discount = new BigDecimal(params.discount)
        } else {
            eiaLabOfferPlan.discount = new BigDecimal(100)
        }
        eiaLabOfferPlan.displayOrder = params.int('displayOrder')
        def pointNum = params.int('pointNum')?:0
        def freqNum = params.int('freqNum')?:0
        def dayNum = params.int('dayNum')?:0
        eiaLabOfferPlan.pointNum = pointNum
        eiaLabOfferPlan.freqNum = freqNum
        eiaLabOfferPlan.dayNum = dayNum
        eiaLabOfferPlan.sampleNum = pointNum * freqNum * dayNum
        eiaLabOfferPlan.inputDept = session.staff.orgName
        eiaLabOfferPlan.inputDeptId = Long.parseLong(session.staff.orgId)
        eiaLabOfferPlan.inputUser = session.staff.staffName
        eiaLabOfferPlan.inputUserId = Long.parseLong(session.staff.staffId)
        eiaLabOfferPlan.inputDeptCode = session.staff.orgCode
        if (eiaLabOfferPlan.save(flush: true, failOnError: true)) {
            def eiaLabOfferPlans = EiaLabOfferPlan.findAllByEiaLabOfferIdAndIfDel(eiaLabOfferId,false)
            def sampleNumTotal = 0
            def sampleFee = 0
            eiaLabOfferPlans.each {
                sampleNumTotal += it?.sampleNum
                def discount = it?.discount
                /** 若是委托检测方，不需要填写检测费用 */
                def schemeMoney = it.schemeMoney ?:0
                if (discount) {
                    sampleFee += it?.sampleNum * schemeMoney * discount/100
                } else {
                    sampleFee += it?.sampleNum * schemeMoney
                }
            }
            /**
             * 更新检测方案表的样品个数，样品费用
             */
            eiaLabOfferService.eiaLabOfferUpdateSampleNum(eiaLabOfferId,sampleNumTotal,sampleFee)
            return eiaLabOfferPlan
        }
    }
    /**
     * 修改检测方案检测计划
     */
    def eiaLabOfferPlanUpdate(params) {
        def eiaLabOfferPlan = EiaLabOfferPlan.findByIdAndIfDel(params.long('eiaLabOfferPlanId'),false)
        eiaLabOfferPlan.properties = params
        /**
         * 获取检测基质、参数、标准，并保存labTestCapId
         */
        def labTestBaseId = params.long('labTestBaseId')
        def labTestParamId = params.long('labTestParamId')
        def baseParam = [:]
        baseParam.labTestBaseId = params.labTestBaseId
        def labTestBase = HttpConnectTools.getResponseJson(HttpUrlConstants.LAB_TEST_BASE_INFO, baseParam)
        if (labTestBase) {
            def baseData = JsonHandler.jsonToMap(labTestBase).data
            eiaLabOfferPlan.labTestBaseId = labTestBaseId
            eiaLabOfferPlan.baseName = baseData.baseName
            eiaLabOfferPlan.baseNameCode = baseData.baseNameCode
        }
        def param = [:]
        param.labTestParamId = params.labTestParamId
        def labTestParam = HttpConnectTools.getResponseJson(HttpUrlConstants.LAB_TEST_PARAM_INFO, param)
        if (labTestParam) {
            def paramData = JsonHandler.jsonToMap(labTestParam).data
            eiaLabOfferPlan.labTestParamId = labTestParamId
            eiaLabOfferPlan.paramNameCn = paramData.paramNameCn
            eiaLabOfferPlan.paramNameEn = paramData.paramNameEn
        }
        /**
         * 检测计划基本信息
         */
        if (params.schemeMoney) {
            eiaLabOfferPlan.schemeMoney = new BigDecimal(params.schemeMoney)
        }
        if (params.discount) {
            eiaLabOfferPlan.discount = new BigDecimal(params.discount)
        } else {
            eiaLabOfferPlan.discount = new BigDecimal(100)
        }
        eiaLabOfferPlan.displayOrder = params.int('displayOrder')
        def pointNum = params.int('pointNum')?:0
        def freqNum = params.int('freqNum')?:0
        def dayNum = params.int('dayNum')?:0
        eiaLabOfferPlan.pointNum = pointNum
        eiaLabOfferPlan.freqNum = freqNum
        eiaLabOfferPlan.dayNum = dayNum
        eiaLabOfferPlan.sampleNum = pointNum * freqNum * dayNum
        if (eiaLabOfferPlan.save(flush: true, failOnError: true)) {
            /**
             * 遍历检测方案检测计划，计算样品个数的总数
             */
            def eiaLabOfferId = eiaLabOfferPlan.eiaLabOfferId
            def eiaLabOfferPlans = EiaLabOfferPlan.findAllByEiaLabOfferIdAndIfDel(eiaLabOfferId,false)
            def sampleNumTotal = 0
            def sampleFee = 0
            eiaLabOfferPlans.each {
                sampleNumTotal += it?.sampleNum
                def discount = it?.discount
                def schemeMoney = it?.schemeMoney ?:0
                if (discount) {
                    sampleFee += it?.sampleNum * schemeMoney * discount/100
                } else {
                    sampleFee += it?.sampleNum * schemeMoney
                }
            }
            /**
             * 更新检测方案表的样品个数，样品费用
             */
            eiaLabOfferService.eiaLabOfferUpdateSampleNum(eiaLabOfferId,sampleNumTotal,sampleFee)
            return eiaLabOfferPlan
        }
    }
    /**
     * 删除检测方案检测计划
     */
    def eiaLabOfferPlanDel(Long eiaLabOfferPlanId) {
        def eiaLabOfferPlan = EiaLabOfferPlan.findByIdAndIfDel(eiaLabOfferPlanId,false)
        eiaLabOfferPlan.ifDel = true
        if (eiaLabOfferPlan.save(flush: true, failOnError: true)) {
            def eiaLabOfferId = eiaLabOfferPlan.eiaLabOfferId
            def eiaLabOfferPlans = EiaLabOfferPlan.findAllByEiaLabOfferIdAndIfDel(eiaLabOfferId,false)
            def sampleNumTotal = 0
            def sampleFee = 0
            eiaLabOfferPlans.each {
                def sampleNum = it?.sampleNum ?: 0
                sampleNumTotal += sampleNum
                def discount = it?.discount
                def schemeMoney = it?.schemeMoney ?:0
                if (discount) {
                    sampleFee += sampleNum * schemeMoney * discount/100
                } else {
                    sampleFee += sampleNum * schemeMoney
                }
            }
            /**
             * 更新检测方案表的样品个数，样品费用
             */
            eiaLabOfferService.eiaLabOfferUpdateSampleNum(eiaLabOfferId,sampleNumTotal,sampleFee)
            return eiaLabOfferPlan
        }
    }
    /**
     * 根据检测方案Id删除全部检测方案检测计划
     */
    def eiaLabOfferPlanDelByEiaLabOfferId(Long eiaLabOfferId) {
        def eiaLabOfferPlans = EiaLabOfferPlan.findAllByEiaLabOfferIdAndIfDel(eiaLabOfferId,false)
        if (eiaLabOfferPlans) {
            eiaLabOfferPlans.each {
                it.ifDel = true
                it.save(flush: true, failOnError: true)
            }
        }
    }
    /**
     * 获取检测方案检测计划详情
     */
    def getEiaLabOfferPlanDataMap(Long eiaLabOfferId, Long eiaLabOfferPlanId) {
        def map = [:]
        def eiaLabOffer = EiaLabOffer.findByIdAndIfDel(eiaLabOfferId, false)
        def ifYxTest = eiaLabOffer?.ifYxTest
        /** 把ifYxTest返回页面进行判断，如果不是宇相检测不需要填写检测方法、费用和折扣 */
        map.ifYxTest = ifYxTest
        def eiaLabOfferPlan = EiaLabOfferPlan.findByIdAndIfDel(eiaLabOfferPlanId,false)
        if (eiaLabOfferPlan) {
            map.eiaLabOfferId = eiaLabOfferPlan.eiaLabOfferId
            map.eiaLabOfferPlanId = eiaLabOfferPlan.id
            map.labTestBaseId = eiaLabOfferPlan.labTestBaseId
            map.labTestParamId = eiaLabOfferPlan.labTestParamId
            map.labTestSchemeId = eiaLabOfferPlan.labTestSchemeId
            map.baseName = eiaLabOfferPlan.baseName
            map.supplierName = eiaLabOfferPlan.supplierName
            map.supplierId = eiaLabOfferPlan.supplierId
            map.paramNameCn = eiaLabOfferPlan.paramNameCn
            map.schemeName = eiaLabOfferPlan.schemeName ?:""
            map.schemeCode = eiaLabOfferPlan.schemeCode ?:""
            map.schemeMoney = eiaLabOfferPlan.schemeMoney
            map.pointNum = eiaLabOfferPlan.pointNum
            map.freqNum = eiaLabOfferPlan.freqNum
            map.dayNum = eiaLabOfferPlan.dayNum
            map.sampleNum = eiaLabOfferPlan.sampleNum
            map.testMethod = eiaLabOfferPlan.testMethod
            map.referenceLimitStandard = eiaLabOfferPlan.referenceLimitStandard
            map.referenceLimit = eiaLabOfferPlan.referenceLimit
            map.discount = eiaLabOfferPlan.discount
            map.ifSub = eiaLabOfferPlan.ifSub
            map.ifGroup = eiaLabOfferPlan.ifGroup
            map.displayOrder = eiaLabOfferPlan?.displayOrder
            map.memo = eiaLabOfferPlan?.memo
            def param = [:]
            param.labTestParamGroupId = (eiaLabOfferPlan.labTestParamGroupId).toString()
            def paramGroupJson = HttpConnectTools.getResponseJson(HttpUrlConstants.LAB_TEST_PARAM_GROUP_INFO, param)
            if (paramGroupJson) {
                def groupData = JsonHandler.jsonToMap(paramGroupJson).data
                if (groupData) {
                    map.groupName = groupData.groupName
                }
            }
            map.maxSchemeMoney = eiaLabOfferPlan.maxSchemeMoney
        } else {
            /** 如果是宇相检测，新增检测计划时带入折扣 */
            if (ifYxTest) {
                map.discount = eiaLabOffer?.contractDiscount
            }
        }
        return map
    }
    /**
     * 获取检测方案检测计划列表
     */
    def getEiaLabOfferPlanDataList(params) {
        def eiaLabOfferId = params.long('eiaLabOfferId')
        int page
        int limit
        if (params.page && params.limit) {
            page = params.int('page') - 1
            limit = params.int('limit')
        }
        def eiaLabOfferPlanList = EiaLabOfferPlan.createCriteria().list(max: limit, offset: page * limit) {
            eq("eiaLabOfferId", eiaLabOfferId)
            eq("ifDel",false)
            order("displayOrder","asc")
        }
        def data = []
        eiaLabOfferPlanList.each {
            def map = [:]
            map.id = it.id
            map.baseName = it.baseName
            map.paramNameCn = it.paramNameCn
            def param = [:]
            param.labTestBaseId = it.labTestBaseId
            param.labTestParamId = it.labTestParamId
            map.sampleNum = it.sampleNum
            map.pointNum = it.pointNum
            map.freqNum = it.freqNum
            map.dayNum = it.dayNum
            def maxSchemeMoney = it.maxSchemeMoney
            map.maxSchemeMoney = maxSchemeMoney
            /**
             * 小计
             */
            def discount = it?.discount
            if (maxSchemeMoney) {
                if (discount) {
                    map.discountFee = maxSchemeMoney * discount / 100
                    map.subTotal = it.sampleNum * maxSchemeMoney * discount / 100
                } else {
                    map.discountFee = maxSchemeMoney
                    map.subTotal = it.sampleNum * maxSchemeMoney
                }
            }

            data.add(map)
        }
        def sampleFee = EiaLabOffer.findByIdAndIfDel(eiaLabOfferId, false)?.sampleFee
        def dataMap = [:]
        dataMap.data = data
        dataMap.total = eiaLabOfferPlanList.totalCount
        dataMap.sampleFee = sampleFee ? sampleFee : 0

        return dataMap
    }
    /**
     * 判断选择不同套餐时，是否包含相同检测计划
     */
    def checkGroup(params) {
        def eiaLabOfferId = params.long('eiaLabOfferId')
        def eiaLabOfferPlans = EiaLabOfferPlan.findAllByEiaLabOfferIdAndIfDel(eiaLabOfferId, false)
        def labTestCapGroups
        def param = [:]
        param.labTestParamGroupId = params.labTestParamGroupId
        def resultJson = HttpConnectTools.getResponseJson(HttpUrlConstants.LAB_TEST_CAP_GROUP_LIST_INFO, param)
        if (resultJson) {
            labTestCapGroups = JsonHandler.jsonToMap(resultJson).data.data
        }
        if (labTestCapGroups && eiaLabOfferPlans) {
            for (Object offerPlan : eiaLabOfferPlans) {
                for (Object capGroup : labTestCapGroups) {
                    def offerPlanMemo = offerPlan?.memo
                    if (!offerPlanMemo) {
                        offerPlanMemo = null
                    }
                    def capGroupMemo = capGroup?.memo
                    if (!capGroupMemo) {
                        capGroupMemo = null
                    }
                    if (offerPlan.labTestBaseId == capGroup.labTestBaseId && offerPlanMemo == capGroupMemo && offerPlan.labTestParamId == capGroup.labTestParamId) {
                        return HttpMesConstants.MSG_OFFER_PLAN_REPEAT
                    }
                }
            }
        }
    }
    /**
     * 检测方案页面选择检测计划套餐，保存检测套餐
     */
    def eiaLabOfferPlanGroupSave(params, session) {
        def eiaLabOfferId = params.long('eiaLabOfferId')
        def eiaLabOffer = EiaLabOffer.findByIdAndIfDel(eiaLabOfferId, false)
        def ifYxTest = eiaLabOffer?.ifYxTest
        def labTestParamGroupId = params.long('labTestParamGroupId')
        def labTestParamGroup
        def param = [:]
        param.labTestParamGroupId = labTestParamGroupId.toString()
        def paramGroupJson = HttpConnectTools.getResponseJson(HttpUrlConstants.LAB_TEST_PARAM_GROUP_INFO, param)
        if (paramGroupJson) {
            def groupData = JsonHandler.jsonToMap(paramGroupJson).data
            if (groupData) {
                labTestParamGroup = groupData
            }
        }
        if (labTestParamGroup) {
            def sampleNumTotal = eiaLabOffer?.sampleNum ?: 0
            def sampleFee = eiaLabOffer?.sampleFee ?:0
            def labTestCapGroups
            def capParam = [:]
            capParam.labTestParamGroupId = params.labTestParamGroupId
            def capGroupJson = HttpConnectTools.getResponseJson(HttpUrlConstants.LAB_TEST_CAP_GROUP_LIST_INFO, capParam)
            if (capGroupJson) {
                def capList = JsonHandler.jsonToMap(capGroupJson).data.data
                if (capList) {
                    labTestCapGroups = capList
                }
            }
            /** 获取套餐内的能力表信息 */
            if (labTestCapGroups) {
                def eiaLabOfferPlans = []
                labTestCapGroups.each {
                    def eiaLabOfferPlan = new EiaLabOfferPlan(params)
                    eiaLabOfferPlan.properties = it
                    def pointNum
                    if (params.pointNum) {
                        pointNum = params.int('pointNum')
                    } else {
                        pointNum = it.pointNum ?:0
                    }
                    def freqNum = it.freqNum ?:0
                    def dayNum = it.dayNum ?:0
                    eiaLabOfferPlan.pointNum = pointNum
                    eiaLabOfferPlan.freqNum = freqNum
                    eiaLabOfferPlan.dayNum = dayNum
                    /** 不是宇相检测的情况下，不保存检测方法和费用 */
                    if (!ifYxTest) {
                        eiaLabOfferPlan.labTestCapId = null
                        eiaLabOfferPlan.labTestSchemeId = null
                        eiaLabOfferPlan.schemeName = null
                        eiaLabOfferPlan.schemeCode = null
                        eiaLabOfferPlan.schemeMoney = null
                        eiaLabOfferPlan.discount = null
                    }
                    def ifSub
                    def ifSubParam = [:]
                    ifSubParam.labTestParamId = (it.labTestParamId).toString()
                    def ifSubJson = HttpConnectTools.getResponseJson(HttpUrlConstants.CHECK_IF_SUB_INFO, ifSubParam)
                    if (ifSubJson != null) {
                        ifSub = JsonHandler.jsonToMap(ifSubJson).data
                    }
                    eiaLabOfferPlan.ifSub = ifSub ?:false
                    eiaLabOfferPlan.ifGroup = true
                    eiaLabOfferPlan.labTestParamGroupId = labTestParamGroupId
                    if (pointNum && freqNum && dayNum) {
                        eiaLabOfferPlan.sampleNum = pointNum * freqNum * dayNum
                    }
                    def maxSchemeMoney = 0
                    def moneyParam = [:]
                    moneyParam.labTestBaseId = (it.labTestBaseId).toString()
                    moneyParam.labTestParamId = (it.labTestParamId).toString()
                    def moneyJson = HttpConnectTools.getResponseJson(HttpUrlConstants.MAX_SCHEME_MONEY_INFO, moneyParam)
                    if (moneyJson) {
                        def money = JsonHandler.jsonToMap(moneyJson).data
                        if (money) {
                            maxSchemeMoney = money
                        }
                    }
                    eiaLabOfferPlan.maxSchemeMoney = maxSchemeMoney
                    def labTestCapParam = [:]
                    labTestCapParam.labTestBaseId = (it.labTestBaseId).toString()
                    labTestCapParam.labTestParamId = (it.labTestParamId).toString()
                    labTestCapParam.labTestSchemeId = (it.labTestSchemeId).toString()
                    def labTestCapJson = HttpConnectTools.getResponseJson(HttpUrlConstants.LAB_TEST_CAP_INFO, labTestCapParam)
                    if (labTestCapJson) {
                        def capData = JsonHandler.jsonToMap(labTestCapJson).data
                        if (capData) {
                            eiaLabOfferPlan.labTestCapId = capData.id
                        }
                    }
                    eiaLabOfferPlan.labClientId = eiaLabOffer?.wtClientId
                    eiaLabOfferPlan.inputDept = session.staff.orgName
                    eiaLabOfferPlan.inputDeptId = Long.parseLong(session.staff.orgId)
                    eiaLabOfferPlan.inputUser = session.staff.staffName
                    eiaLabOfferPlan.inputUserId = Long.parseLong(session.staff.staffId)
                    eiaLabOfferPlan.inputDeptCode = session.staff.orgCode
                    eiaLabOfferPlan.save(flush: true, failOnError: true)
                    eiaLabOfferPlans << eiaLabOfferPlan
                    sampleNumTotal += eiaLabOfferPlan.sampleNum
                    def discount = eiaLabOfferPlan.discount
                    def schemeMoney = eiaLabOfferPlan.schemeMoney
                    if (schemeMoney) {
                        if (discount) {
                            sampleFee += eiaLabOfferPlan.sampleNum * schemeMoney * discount / 100
                        } else {
                            sampleFee += eiaLabOfferPlan.sampleNum * schemeMoney
                        }
                    }
                }
                /**
                 * 更新检测方案表的样品个数，样品费用
                 */
                eiaLabOfferService.eiaLabOfferUpdateSampleNum(eiaLabOfferId, sampleNumTotal, sampleFee)
                return eiaLabOfferPlans
            }
        }
    }
    /**
     * 获取检测方案检测计划套餐
     */
    def getEiaLabOfferPlanGroupDataMap(Long eiaLabOfferId, Long labTestParamGroupId) {
        def param = [:]
        param.labTestParamGroupId = labTestParamGroupId.toString()
        def labTestParamGroup = HttpConnectTools.getResponseJson(HttpUrlConstants.LAB_TEST_PARAM_GROUP_INFO, param)
        if (labTestParamGroup) {
            def groupData = JsonHandler.jsonToMap(labTestParamGroup).data
            if (groupData) {
                def map = [:]
                map.eiaLabOfferId = eiaLabOfferId
                map.labTestParamGroupId = labTestParamGroupId
                map.groupName = groupData.groupName
                map.groupFee = groupData.groupFee
                map.groupDiscount = groupData.groupDiscount
                map.groupPointNum = groupData.groupPointNum
                map.groupFreqNum = groupData.groupFreqNum
                map.groupDayNum = groupData.groupDayNum
                return map
            }
        }
    }
    /**
     * 获取检测分包
     */
    def getTestSub(eiaLabOfferId) {
        def eiaLabOfferPlans = EiaLabOfferPlan.findAllByEiaLabOfferIdAndIfDel(eiaLabOfferId,false)
        def testSubList = []
        eiaLabOfferPlans.each {
            if (it?.ifSub) {
                testSubList.add(it?.paramNameCn)
            }
        }
        return StringUtils.strip(testSubList.unique().toString(),"[]")
    }
    /**
     * 检测计划批量打折
     */
    def eiaLabOfferPlanBatchDiscount(params) {
        def eiaLabOfferId = params.long('eiaLabOfferId')
        def eiaLabOffer = EiaLabOffer.findByIdAndIfDel(eiaLabOfferId, false)
        def discount
        if (params.discount) {
            discount = new BigDecimal(params.discount)
        } else {
            discount = new BigDecimal(100)
        }
        def eiaLabOfferPlanList = EiaLabOfferPlan.findAllByEiaLabOfferIdAndIfDel(eiaLabOfferId, false)
        def sampleFee = 0
        eiaLabOfferPlanList.each {
            it.discount = discount
            if (it?.schemeMoney) {
                sampleFee += it?.sampleNum * it?.schemeMoney * discount / 100
            }
        }
        eiaLabOfferService.eiaLabOfferUpdateSampleNum(eiaLabOfferId, eiaLabOffer?.sampleNum, sampleFee)
        return eiaLabOfferPlanList
    }
    /**
     * 由宇相检测改为委托检测方时，将检测计划的检测标准、检测费用、折扣清空
     */
    def cleanSchemeAndDiscount(Long eiaLabOfferId) {
        def eiaLabOfferPlanList = EiaLabOfferPlan.findAllByEiaLabOfferIdAndIfDel(eiaLabOfferId, false)
        eiaLabOfferPlanList.each {
            it.schemeName = null
            it.schemeCode = null
            it.schemeMoney = null
            it.discount = null
            it.save(flush: true, failOnError: true)
        }
    }
    /**
     * 按照原价计算检测计划小计费用
     */
    def countPlanPreSampleFee(Long eiaLabOfferId) {
        def preSampleFee = 0
        def eiaLabOfferPlans = EiaLabOfferPlan.findAllByEiaLabOfferIdAndIfDel(eiaLabOfferId, false)
        eiaLabOfferPlans.each {
            def schemeMoney = it.schemeMoney ?:0
            if (it.sampleNum) {
                preSampleFee += it.sampleNum * schemeMoney
            } else {
                preSampleFee += it.pointNum * it.freqNum * it.dayNum * schemeMoney
            }
        }
        return preSampleFee
    }
    /**
     * 预估检测计划小计费用
     */
    def getMaxSampleFee(params) {
        def eiaLabOfferId = params.long('eiaLabOfferId')
        def eiaLabOfferPlanList = EiaLabOfferPlan.findAllByEiaLabOfferIdAndIfDel(eiaLabOfferId, false)
        def maxSampleFee = 0
        if (eiaLabOfferPlanList) {
            eiaLabOfferPlanList.each {
                def param = [:]
                param.labTestBaseId= it.labTestBaseId
                param.labTestParamId= it.labTestParamId
                def maxSchemeMoney = it.maxSchemeMoney
                def discount = (it.discount ?:100)/100
                maxSampleFee += maxSchemeMoney * discount * it.sampleNum
            }
        }
        return maxSampleFee
    }
    /**
     * 选择检测套餐后，返回套餐内容为JSON格式
     */
    def getParamGroupDataMap(params) {
        def labTestParamGroupId = params.labTestParamGroupId
        if (labTestParamGroupId) {
            def labTestCapGroupList
            def param = [:]
            param.labTestParamGroupId = params.labTestParamGroupId
            def resultJson = HttpConnectTools.getResponseJson(HttpUrlConstants.LAB_TEST_CAP_GROUP_LIST_INFO, param)
            if (resultJson) {
                labTestCapGroupList = JsonHandler.jsonToMap(resultJson).data.data
            }
            if (labTestCapGroupList) {
                def planList = []
                labTestCapGroupList.each {
                    def map = [:]
                    map.labTestCapId = it.id
                    map.labTestParamGroupId = it.labTestParamGroupId
                    map.labTestBaseId = it.labTestBaseId
                    map.baseName = it.baseName
                    map.labTestParamId = it.labTestParamId
                    map.paramNameCn = it.paramNameCn
                    map.labTestSchemeId = it.labTestSchemeId
                    map.schemeName = it.schemeName
                    map.schemeCode = it.schemeCode
                    map.schemeMoney = it.schemeMoney
                    map.ifCma = it.ifCma
                    map.ifSub = it.ifSub
                    map.memo = it.memo
                    map.displayOrder = it.displayOrder
                    map.referenceLimitStandard = it.referenceLimitStandard
                    map.referenceLimit = it.referenceLimit
                    map.mergeShowCode = it.mergeShowCode
                    map.discount = it.discount
                    def maxSchemeMoney = 0
                    def moneyParam = [:]
                    moneyParam.labTestBaseId = (it.labTestBaseId).toString()
                    moneyParam.labTestParamId = (it.labTestParamId).toString()
                    def moneyJson = HttpConnectTools.getResponseJson(HttpUrlConstants.MAX_SCHEME_MONEY_INFO, moneyParam)
                    if (moneyJson) {
                        maxSchemeMoney = JsonHandler.jsonToMap(moneyJson).data ?:0
                    }
                    map.maxSchemeMoney = maxSchemeMoney
                    def pointNum
                    if (params.pointNum) {
                        pointNum = params.int('pointNum')
                    } else {
                        pointNum = it.pointNum
                    }
                    map.pointNum = pointNum
                    map.dayNum = it.dayNum
                    map.freqNum = it.freqNum
                    def discount = it?.discount
                    if (discount) {
                        map.discountFee = maxSchemeMoney * discount / 100
                        map.subTotal = it.dayNum * pointNum * it.freqNum * maxSchemeMoney * discount / 100
                    } else {
                        map.discountFee = maxSchemeMoney
                        map.subTotal = it.dayNum * pointNum * it.freqNum * maxSchemeMoney
                    }
                    planList << map
                }
                return planList
            }
        }
    }
    /**
     * 获取计算器的内容，批量插入eiaLabOfferPlan
     */
    def batchSaveEiaOfferPlan(List plans, Long eiaLabOfferId, session) {
        def eiaLabOffer = EiaLabOffer.findById(eiaLabOfferId)
        def sampleNumTotal = 0
        def sampleFee = 0
        plans.each {
            def eiaLabOfferPlan = new EiaLabOfferPlan()
            eiaLabOfferPlan.properties = it
            if (it?.labTestParamGroupId) {
                def discount = it.discount
                def schemeMoney = it.schemeMoney
                if (schemeMoney) {
                    if (discount) {
                        sampleFee += it.pointNum * it.dayNum * it.freqNum * schemeMoney * discount / 100
                    } else {
                        sampleFee += it.pointNum * it.dayNum * it.freqNum * schemeMoney
                    }
                }
            }
            def param = [:]
            param.labTestParamGroupId = (it.labTestParamGroupId).toString()
            def paramGroupJson = HttpConnectTools.getResponseJson(HttpUrlConstants.LAB_TEST_PARAM_GROUP_INFO, param)
            if (paramGroupJson) {
                def groupData = JsonHandler.jsonToMap(paramGroupJson).data
                if (groupData) {
                    eiaLabOfferPlan.ifGroup = true
                    eiaLabOfferPlan.labTestParamGroupId = groupData.id
                }
            }
            def capParam = [:]
            capParam.labTestBaseId = (it.labTestBaseId).toString()
            capParam.labTestParamId = (it.labTestParamId).toString()
            capParam.labTestSchemeId = (it.labTestSchemeId).toString()
            def labTestCapJson = HttpConnectTools.getResponseJson(HttpUrlConstants.LAB_TEST_CAP_INFO, capParam)
            if (labTestCapJson) {
                def capData = JsonHandler.jsonToMap(labTestCapJson).data
                if (capData) {
                    eiaLabOfferPlan.labTestCapId = capData.id
                }
            }
            eiaLabOfferPlan.eiaLabOfferId = eiaLabOfferId
            eiaLabOfferPlan.labClientId = eiaLabOffer.wtClientId
            def dayNum = Integer.parseInt((it.dayNum).toString())
            def pointNum = Integer.parseInt((it.pointNum).toString())
            def freqNum = Integer.parseInt((it.freqNum).toString())
            def sampleNum = dayNum * pointNum * freqNum
            eiaLabOfferPlan.sampleNum = sampleNum
            sampleNumTotal += sampleNum
            eiaLabOfferPlan.sampleType = eiaLabOffer.sampleType
            eiaLabOfferPlan.inputDept = session.staff.orgName
            eiaLabOfferPlan.inputDeptId = Long.parseLong(session.staff.orgId)
            eiaLabOfferPlan.inputUser = session.staff.staffName
            eiaLabOfferPlan.inputUserId = Long.parseLong(session.staff.staffId)
            eiaLabOfferPlan.inputDeptCode = session.staff.orgCode
            eiaLabOfferPlan.save(flush: true, failOnError: true)
        }
        /**
         * 更新检测方案表的样品个数，样品费用
         */
        eiaLabOfferService.eiaLabOfferUpdateSampleNum(eiaLabOfferId, sampleNumTotal, sampleFee)
    }
}
