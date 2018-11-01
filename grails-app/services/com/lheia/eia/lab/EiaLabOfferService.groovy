package com.lheia.eia.lab

import com.lheia.eia.client.EiaClient
import com.lheia.eia.common.FuncConstants
import com.lheia.eia.common.GeneConstants
import com.lheia.eia.common.HttpMesConstants
import com.lheia.eia.common.HttpUrlConstants
import com.lheia.eia.common.WorkFlowConstants
import com.lheia.eia.project.EiaProject
import com.lheia.eia.tools.HttpConnectTools
import com.lheia.eia.tools.JsonHandler
import com.lheia.eia.workflow.EiaWorkFlowBusi
import grails.gorm.transactions.Transactional
import org.grails.web.json.JSONArray
import org.grails.web.json.JSONObject

import java.lang.reflect.Field
import java.text.SimpleDateFormat

@Transactional
class EiaLabOfferService {

    def eiaLabOfferPlanService

    /**
     * 判断项目下是否已存在检测方案
     */
    def checkEiaProjectBeUsed(params) {
        def eiaProjectId = params.long('eiaProjectId')
        def eiaLabOfferId = params.long('eiaLabOfferId')
        def eiaLabOffer = EiaLabOffer.findByEiaProjectIdAndIfDel(eiaProjectId, false)
        if (eiaLabOffer) {
            if (eiaLabOffer.id != eiaLabOfferId) {
                return HttpMesConstants.MSG_EIA_LAB_OFFER_PROJECT_REPEAT
            }
        }
    }
    /**
     * 在检测费用计算器选择项目后新增监测方案
     */
    def eiaLabOfferProjectSave(params, session) {
        def eiaLabOffer = new EiaLabOffer(params)
        def eiaProjectId = params.long('eiaProjectId')
        eiaLabOffer.eiaProjectId = eiaProjectId
        def eiaProject = EiaProject.findById(eiaProjectId)
        eiaLabOffer.projectName = eiaProject.projectName
        def eiaTaskId = eiaProject.eiaTaskId
        eiaLabOffer.eiaTaskId = eiaTaskId
        def wtClientId
        def clientParam = [:]
        clientParam.busClientId = GeneConstants.TAIZE_BUS_CLIENT_ID_IN_LAB
        def labClientJson = HttpConnectTools.getResponseJson(HttpUrlConstants.LAB_CLIENT_INFO, clientParam)
        if (labClientJson) {
            def labClient = JsonHandler.jsonToMap(labClientJson).data
            if (labClient) {
                wtClientId = labClient.id
                eiaLabOffer.wtClientId = wtClientId
                eiaLabOffer.wtClientName = labClient.clientNameCn
                eiaLabOffer.wtClientAddr = labClient.clientAddrCn
            }
        }
        /**
         * 报告信息
         */
        def param = [:]
        param.labClientId = wtClientId.toString()
        def labClientReportJson = HttpConnectTools.getResponseJson(HttpUrlConstants.LAB_CLIENT_REPORT_INFO, param)
        if (labClientReportJson) {
            def reportJson = JsonHandler.jsonToMap(labClientReportJson).data
            eiaLabOffer.properties = reportJson
        }
        /**
         * 合同信息
         */
        def labClientContractInfoJson = HttpConnectTools.getResponseJson(HttpUrlConstants.LAB_CLIENT_CONTRACT_INFO, param)
        if (labClientContractInfoJson) {
            def contractInfoJson = JsonHandler.jsonToMap(labClientContractInfoJson).data
            eiaLabOffer.properties = contractInfoJson
            eiaLabOffer.defReportTemp = contractInfoJson.reportTemp
            eiaLabOffer.labClientContractInfoId = contractInfoJson.id
        }
        /**
         * 财务信息
         */
        def labClientFinanceInfoJson = HttpConnectTools.getResponseJson(HttpUrlConstants.LAB_CLIENT_FINANCE_INFO, param)
        if (labClientFinanceInfoJson) {
            def financeInfoJson = JsonHandler.jsonToMap(labClientFinanceInfoJson).data
            eiaLabOffer.properties = financeInfoJson
            eiaLabOffer.labClientFinanceInfoId = financeInfoJson.id
        }
        eiaLabOffer.ifYxTest = true
        eiaLabOffer.offerState = GeneConstants.OFFER_COMPLETE
        eiaLabOffer.pushState = GeneConstants.TAIZE_CREATE_INNER_OFFER
        eiaLabOffer.inputDept = session.staff.orgName
        eiaLabOffer.inputDeptId = Long.parseLong(session.staff.orgId)
        eiaLabOffer.inputUser = session.staff.staffName
        eiaLabOffer.inputUserId = Long.parseLong(session.staff.staffId)
        eiaLabOffer.inputDeptCode = session.staff.orgCode
        eiaLabOffer.ifTestComplete = false
        if (eiaLabOffer.save(flush: true, failOnError: true)) {
            JSONArray planList = new JSONArray(params.planList)
            if (planList) {
                def plans = []
                for (int i = 0; i < planList.length(); i++) {
                    JSONObject obj = planList.getJSONObject(i)
                    plans << obj
                }
                /** 获取计算器的内容批量插入eiaLabOfferPlan */
                eiaLabOfferPlanService.batchSaveEiaOfferPlan(plans, eiaLabOffer.id, session)
            }
        }
        return eiaLabOffer
    }
    /**
     * 保存检测方案信息
     */
    def eiaLabOfferSave(params, session, wtClientId, sjClientId) {
        def eiaLabOffer = new EiaLabOffer(params)
        def eiaProjectId = params.long('eiaProjectId')
        if (eiaProjectId) {
            eiaLabOffer.eiaProjectId = eiaProjectId
            def eiaProject = EiaProject.findById(eiaProjectId)
            eiaLabOffer.gisProjectId = eiaProject?.gisProjectId
            eiaLabOffer.projectName = eiaProject.projectName
            def eiaTaskId = eiaProject.eiaTaskId
            eiaLabOffer.eiaTaskId = eiaTaskId
        }
        if (wtClientId != "") {
            eiaLabOffer.wtClientId = Long.parseLong(wtClientId)
        }
        if (sjClientId != "") {
            eiaLabOffer.sjClientId = Long.parseLong(sjClientId)
        }
        eiaLabOffer.ifYxTest = params.ifYxTest ?:false
        if (params.ifYxTest) {
            eiaLabOffer.projectName = null
            eiaLabOffer.projectAddr = null
            eiaLabOffer.testCompany = null
        }
        eiaLabOffer.inputDept = session.staff.orgName
        eiaLabOffer.inputDeptId = Long.parseLong(session.staff.orgId)
        eiaLabOffer.inputUser = session.staff.staffName
        eiaLabOffer.inputUserId = Long.parseLong(session.staff.staffId)
        eiaLabOffer.inputDeptCode = session.staff.orgCode
        eiaLabOffer.ifTestComplete = false
        /**
         * 报告信息
         */
        def param = [:]
        param.labClientId = wtClientId
        def labClientReportJson = HttpConnectTools.getResponseJson(HttpUrlConstants.LAB_CLIENT_REPORT_INFO, param)
        if (labClientReportJson) {
            def reportJson = JsonHandler.jsonToMap(labClientReportJson).data
            if (reportJson) {
                eiaLabOffer.properties = reportJson
            }
        }
        /**
         * 合同信息
         */
        def labClientContractInfoJson = HttpConnectTools.getResponseJson(HttpUrlConstants.LAB_CLIENT_CONTRACT_INFO, param)
        if (labClientContractInfoJson) {
            def contractInfoJson = JsonHandler.jsonToMap(labClientContractInfoJson).data
            if (contractInfoJson) {
                eiaLabOffer.properties = contractInfoJson
                eiaLabOffer.contractDiscount = new BigDecimal(100)
                eiaLabOffer.defReportTemp = contractInfoJson.reportTemp
                eiaLabOffer.labClientContractInfoId = contractInfoJson.id
            }
        }
        /**
         * 财务信息
         */
        def labClientFinanceInfoJson = HttpConnectTools.getResponseJson(HttpUrlConstants.LAB_CLIENT_FINANCE_INFO, param)
        if (labClientFinanceInfoJson) {
            def financeInfoJson = JsonHandler.jsonToMap(labClientFinanceInfoJson).data
            if (financeInfoJson) {
                eiaLabOffer.properties = financeInfoJson
                eiaLabOffer.labClientFinanceInfoId = financeInfoJson.id
            }
        }
        if (eiaLabOffer.save(flush: true, failOnError: true)) {
            eiaLabOffer.offerState = GeneConstants.OFFER_COMPLETE
            eiaLabOffer.pushState = GeneConstants.TAIZE_CREATE_INNER_OFFER
            eiaLabOffer.save(flush: true, failOnError: true)
        }
    }
    /**
     * 保存现场勘察费用
     */
    def eiaLabOfferExploreSave(params) {
        def eiaLabOfferId = params.long('eiaLabOfferId')
        def eiaLabOffer = EiaLabOffer.findByIdAndIfDel(eiaLabOfferId, false)
        eiaLabOffer.properties = params
        if (params.exploreFee) {
            eiaLabOffer.exploreFee = new BigDecimal(params.exploreFee)
        } else {
            eiaLabOffer.exploreFee = null
        }
        if (params.trafficFee) {
            eiaLabOffer.trafficFee = new BigDecimal(params.trafficFee)
        } else {
            eiaLabOffer.trafficFee = null
        }
        if (params.hotelFee) {
            eiaLabOffer.hotelFee = new BigDecimal(params.hotelFee)
        } else {
            eiaLabOffer.hotelFee = null
        }
        if (params.countFee) {
            eiaLabOffer.countFee = new BigDecimal(params.countFee)
        } else {
            eiaLabOffer.countFee = null
        }
        if (params.countFeeTax) {
            eiaLabOffer.countFeeTax = new BigDecimal(params.countFeeTax).setScale(2,BigDecimal.ROUND_DOWN)
        } else {
            eiaLabOffer.countFeeTax = null
        }
        if (params.contractTax) {
            eiaLabOffer.contractTax = new BigDecimal(params.contractTax)
        } else {
            eiaLabOffer.contractTax = null
        }
        if (params.contractDiscount) {
            eiaLabOffer.contractDiscount = new BigDecimal(params.contractDiscount)
        } else {
            eiaLabOffer.contractDiscount = new BigDecimal(100)
        }
        eiaLabOffer.explorePeopleNum = params.int('explorePeopleNum')
        eiaLabOffer.exploreDayNum = params.int('exploreDayNum')
        eiaLabOffer.travelDayNum = params.int('travelDayNum')
        eiaLabOffer.roomNum = params.int('roomNum')
        eiaLabOffer.liveDayNum = params.int('liveDayNum')
        if (params.exploreFeeTotal) {
            eiaLabOffer.exploreFeeTotal = new BigDecimal(params.exploreFeeTotal)
        } else {
            eiaLabOffer.exploreFeeTotal = null
        }
        if (params.trafficFeeTotal) {
            eiaLabOffer.trafficFeeTotal = new BigDecimal(params.trafficFeeTotal)
        } else {
            eiaLabOffer.trafficFeeTotal = null
        }
        if (params.hotelFeeTotal) {
            eiaLabOffer.hotelFeeTotal = new BigDecimal(params.hotelFeeTotal)
        } else {
            eiaLabOffer.hotelFeeTotal = null
        }
        if (params.workOutFee) {
            eiaLabOffer.workOutFee = new BigDecimal(params.workOutFee)
        } else {
            eiaLabOffer.workOutFee = null
        }
        if (params.expertFee) {
            eiaLabOffer.expertFee = new BigDecimal(params.expertFee)
        } else {
            eiaLabOffer.expertFee = null
        }
        if (params.sampleFee) {
            eiaLabOffer.sampleFee = new BigDecimal(params.sampleFee)
        } else {
            eiaLabOffer.expertFee = null
        }
        if (params.otherFee) {
            eiaLabOffer.otherFee = new BigDecimal(params.otherFee)
        } else {
            eiaLabOffer.otherFee = null
        }
        eiaLabOffer.save(flush: true, failOnError: true)
    }
    /**
     * 修改检测方案基本信息
     */
    def eiaLabOfferUpdate(params) {
        def eiaLabOffer = EiaLabOffer.findByIdAndIfDel(params.long('eiaLabOfferId'), false)
        eiaLabOffer.properties = params
        def eiaProjectId = params.long('eiaProjectId')
        if (eiaProjectId) {
            eiaLabOffer.eiaProjectId = eiaProjectId
            def eiaProject = EiaProject.findById(eiaProjectId)
            eiaLabOffer.gisProjectId = eiaProject?.gisProjectId
            eiaLabOffer.projectName = eiaProject.projectName
            def eiaTaskId = eiaProject.eiaTaskId
            eiaLabOffer.eiaTaskId = eiaTaskId
        } else {
            if (params.ifYxTest) {
                eiaLabOffer.gisProjectId = null
                eiaLabOffer.projectName = null
                eiaLabOffer.projectAddr = null
                eiaLabOffer.testCompany = null
            }
        }
        eiaLabOffer.wtClientId = params.long('wtClientId')
        eiaLabOffer.sjClientId = params.long('sjClientId')
        eiaLabOffer.ifYxTest = params.ifYxTest ?:false
        if (eiaLabOffer.save(flush: true, failOnError: true)) {
            def eiaLabOfferList = EiaLabOfferPlan.findAllByEiaLabOfferIdAndIfDel(eiaLabOffer.id, false)
            if (eiaLabOfferList) {
                /** 若只更新基本信息，该方法暂时无用 */
//                eiaLabOfferUpdateSampleNum(eiaLabOffer.id, eiaLabOffer.sampleNum, eiaLabOffer.sampleFee)
                /**
                 * 由宇相检测改为委托检测方时,将检测计划的检测标准、检测费用、折扣清空
                 */
                if (!params.ifYxTest) {
                    eiaLabOfferPlanService.cleanSchemeAndDiscount(eiaLabOffer.id)
                }
            }
            return eiaLabOffer
        }
    }
    /**
     * 删除检测方案信息
     */
    def eiaLabOfferDel(Long eiaLabOfferId) {
        /**
         * 根据eiaLabOfferId删除检测方案检测计划
         */
        eiaLabOfferPlanService.eiaLabOfferPlanDelByEiaLabOfferId(eiaLabOfferId)
        /**
         * 删除检测方案信息
         */
        def eiaLabOffer = EiaLabOffer.findByIdAndIfDel(eiaLabOfferId, false)
        eiaLabOffer.ifDel = true
        eiaLabOffer.save(flush: true, failOnError: true)
    }
    /**
     * 获取检测方案信息详情
     */
    def getEiaLabOfferDataMap(Long eiaLabOfferId) {
        return EiaLabOffer.findByIdAndIfDel(eiaLabOfferId, false)
    }
    /**
     * 获取检测方案信息列表
     */
    def getEiaLabOfferDataList(params, session) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd")
        int page
        int limit
        if (params.page && params.limit) {
            page = params.int('page') - 1
            limit = params.int('limit')
        }
        def eiaLabOfferList = EiaLabOffer.createCriteria().list(max: limit, offset: page * limit) {
            def projectName = params["key[projectName]"]
            if (projectName && !"项目名称、受检单位、录入部门、录入人、报价编号".equals(projectName)) {
                or{
                    like("projectName", "%" + projectName + "%")
                    like("sjClientName", "%" + projectName + "%")
                    like("inputDept", "%" + projectName + "%")
                    like("inputUser", "%" + projectName + "%")
                    like("offerCode", "%" + projectName + "%")
                }
            }
            def projectType = params["key[projectType]"]
            if (projectType && !"请选择项目类型".equals(projectType)) {
                like("projectType", projectType)
            }
            def offerState = params["key[offerState]"]
            if (offerState && !"请选择检测方案状态".equals(offerState)) {
                like("offerState", offerState)
            }
            /**
             * 按出具日期年月查询
             */
            def reportDate = params["key[reportDate]"]
            if (reportDate) {
                def reportDateBegin = reportDate + "-01"
                def reportDateEnd = reportDate + "-31"
                ge("reportDate", sdf.parse(reportDateBegin))
                le("reportDate", sdf.parse(reportDateEnd))
            }
            def inputDept = params["key[inputDept]"]
            if (inputDept) {
                like("inputDept", "%" + inputDept + "%")
            }
            def inputUser = params["key[inputUser]"]
            if (inputUser) {
                like("inputUser", "%" + inputUser + "%")
            }
            /**
             * 是否宇相检测
             */
            def ifYxTestChar = params["key[ifYxTestChar]"]
            if(ifYxTestChar){
                if(ifYxTestChar.equals("1")){
                    eq("ifYxTest",true)
                }else{
                    eq("ifYxTest",false)
                }
            }
            if (params.eiaProjectId) {
                eq("eiaProjectId", params.long('eiaProjectId'))
            }
            /**
             * 经理查看检测方案数据权限
             */
            if (!session?.staff?.funcCode?.contains(FuncConstants.EIA_XMGL_JCFA_VIEWALL)) {
                /**
                 * 主管查看检测方案数据权限
                 */
                if (session?.staff?.funcCode?.contains(FuncConstants.EIA_XMGL_JCFA_VIEWDEPT)) {
                    like ("inputDeptCode", '%'+ session.staff.orgCode +'%')
                }
                /**
                 * 员工查看检测方案数据权限
                 */
                else if (session?.staff?.funcCode?.contains(FuncConstants.EIA_XMGL_JCFA_VIEWSELF)) {
                    eq("inputUserId", Long.valueOf(session.staff.staffId))
                }
            }
            eq("ifDel", false)
            order("id", "desc")
        }
        def data = []
        eiaLabOfferList.each {
            def map = [:]
            map.id = it.id
            map.offerCode = it?.offerCode
            map.projectName = it.projectName
            map.wtClientId = it.wtClientId
            map.wtClientName = it.wtClientName
            map.sjClientId = it.sjClientId
            map.sjClientName = it.sjClientName
            map.projectType = it.projectType
            map.sampleType = it.sampleType
            map.offerState = it?.offerState
            map.pushState = it?.pushState
            map.ifYxTest =  it?.ifYxTest
            def ifYxTestChar = "否"
            if(it?.ifYxTest){
                ifYxTestChar = "是"
            }
            map.ifYxTestChar =  ifYxTestChar
            if(it?.busiFee>0){
                map.countFeeTax =  it?.busiFee
            }else{
                map.countFeeTax =  it.countFeeTax?:0
            }
            map.inputUserId = it?.inputUserId
            def eiaLabOfferPlan = EiaLabOfferPlan.findAllByEiaLabOfferIdAndIfDel(it.id, false)
            if (eiaLabOfferPlan) {
                map.ifHaveOfferPlan = 1
            } else {
                map.ifHaveOfferPlan = 0
            }
            def eiaWorkFlowBusi = EiaWorkFlowBusi.findByWorkFlowStateNotEqualAndTableNameIdAndTableNameAndIfDel(WorkFlowConstants.WORKFLOW_HALT, it.id, GeneConstants.DOMAIN_EIA_LAB_OFFER, false)
            if (eiaWorkFlowBusi) {
                map.ifHaveFlow = true
                map.workFlowState = eiaWorkFlowBusi?.nodesName
            } else {
                map.ifHaveFlow = false
                map.workFlowState = "无"
            }
            data << map
        }
        def dataMap = [:]
        dataMap.data = data
        dataMap.total = eiaLabOfferList.totalCount
        return dataMap
    }
    /**
     * 更新检测方案表样品数量，费用
     */
    def eiaLabOfferUpdateSampleNum(Long eiaLabOfferId, int sampleNum, def sampleFee) {
        def eiaLabOffer = EiaLabOffer.findByIdAndIfDel(eiaLabOfferId, false)
        def exploreFeeTotal = eiaLabOffer.exploreFeeTotal ?: 0
        def trafficFeeTotal = eiaLabOffer.trafficFeeTotal ?: 0
        def hotelFeeTotal = eiaLabOffer.hotelFeeTotal ?: 0
        def workOutFee = eiaLabOffer.workOutFee ?: 0
        def expertFee = eiaLabOffer.expertFee ?: 0
        def otherFee = eiaLabOffer.otherFee ?: 0
        def contractDiscount = eiaLabOffer.contractDiscount ?: 100
        /** 税费从EiaLabOffer表取，若没有值按6%算 */
        def contractTax = eiaLabOffer.contractTax
        def countFee = exploreFeeTotal + trafficFeeTotal + hotelFeeTotal + sampleFee + workOutFee + expertFee + otherFee
        eiaLabOffer.countFee = (int) countFee
        /** 更新优惠前价格、优惠前含税价格 */
        def preSampleFee = eiaLabOfferPlanService.countPlanPreSampleFee(eiaLabOffer.id)
        def prePrefFee = (int) (exploreFeeTotal/(contractDiscount/100) + trafficFeeTotal/(contractDiscount/100) + hotelFeeTotal/(contractDiscount/100) + preSampleFee + workOutFee + expertFee + otherFee)
        eiaLabOffer.prePrefFee = prePrefFee
        if (contractTax) {
            eiaLabOffer.countFeeTax = (int) (countFee * (1 + contractTax/100))
            eiaLabOffer.prePrefFeeTax = (int) (prePrefFee * (1 + contractTax/100))
        } else {
            eiaLabOffer.countFeeTax = (int) countFee
            eiaLabOffer.prePrefFeeTax = (int) prePrefFee
        }
        eiaLabOffer.sampleNum = sampleNum
        eiaLabOffer.sampleFee = sampleFee
        eiaLabOffer.save(flush: true, failOnError: true)
    }
    /**
     * 变更检测方案状态
     */
    def updateOfferState(eiaLabOfferId, offerState) {
        def eiaLabOffer = EiaLabOffer.findByIdAndIfDel(eiaLabOfferId,false)
        if (eiaLabOffer) {
            eiaLabOffer.offerState = offerState
            eiaLabOffer.save(flush: true, failOnError: true)
        }
    }
    /**
     * 变更检测方案推送状态
     */
    def updatePushState(eiaLabOfferId, pushState) {
        def eiaLabOffer = EiaLabOffer.findByIdAndIfDel(eiaLabOfferId,false)
        if (eiaLabOffer) {
            eiaLabOffer.pushState = pushState
            eiaLabOffer.save(flush: true, failOnError: true)
        }
    }
    /**
     * 检查监测方案是否填写完成
     */
    def checkEiaLabOfferIfFinish(eiaLabOfferId) {
        def eiaLabOffer = EiaLabOffer.findById(eiaLabOfferId)
        if (eiaLabOffer) {
            def sjClientId = eiaLabOffer.sjClientId
            if (!sjClientId) {
                return HttpMesConstants.MSG_EIA_LAB_OFFER__NEED_FINISH
            }
        }
    }
    /**
     * 删除监测方案信息部分信息（用于监测流程中止后）
     */
    def eiaLabOfferDelPartData(Long eiaLabOfferId) {
        /**
         * 根据eiaLabOfferId删除监测方案检测计划
         */
        eiaLabOfferPlanService.eiaLabOfferPlanDelByEiaLabOfferId(eiaLabOfferId)
        /**
         * 只保留监测方案基本信息和附件
         */
        def eiaLabOffer = EiaLabOffer.findByIdAndIfDel(eiaLabOfferId, false)
        def sjClientId = EiaClient.findByClientNameAndIfDel(eiaLabOffer.sjClientName, false)?.id
        def map = [:]
        for (Field labOfferField : eiaLabOffer.getClass().getDeclaredFields()) {
            labOfferField.setAccessible(true)
            def name = labOfferField.getName()
            if (WorkFlowConstants.LAB_OFFER_NEED_DATA.indexOf(name) != -1) {
                map.put(name, labOfferField.get(eiaLabOffer))
            } else {
                map.put(name, null)
            }
        }
        eiaLabOffer.properties = map
        eiaLabOffer.sjClientId = sjClientId
        eiaLabOffer.contractDiscount = new BigDecimal(100)
        eiaLabOffer.pushState = GeneConstants.TAIZE_CREATE_INNER_OFFER
        eiaLabOffer.save(flush: true, failOnError: true)
    }
}