package com.lheia.eia.lab

import com.lheia.eia.common.GeneConstants
import com.lheia.eia.common.HttpMesConstants
import com.lheia.eia.common.HttpUrlConstants
import com.lheia.eia.common.WorkFlowConstants
import com.lheia.eia.config.EiaDomainCode
import com.lheia.eia.config.EiaFileUpload
import com.lheia.eia.project.EiaProject
import com.lheia.eia.tools.HttpConnectTools
import com.lheia.eia.tools.JsonHandler
import grails.converters.JSON

class EiaLabOfferController {

    def eiaLabOfferService
    def eiaLabOfferPlanService
    def eiaProjectService
    def eiaWorkFlowLabOfferService

    def eiaLabOfferIndex() {
        render(view: "/eiaLab/eiaLabOfferIndex.gsp")
    }
    def eiaLabOfferCreate() {
        render(view: "/eiaLab/eiaLabOfferCreate.gsp")
    }
    def eiaLabOfferEdit() {
        def eiaLabOfferId = params.eiaLabOfferId
        def eiaLabOffer = EiaLabOffer.findById(eiaLabOfferId)
        if (eiaLabOffer?.inputUserId == Long.parseLong(session.staff.staffId)) {
            render(view: "/eiaLab/eiaLabOfferEdit.gsp", model: [ifOption: true])
        } else {
            render(view: "/eiaLab/eiaLabOfferEdit.gsp", model: [ifOption: false])
        }
    }
    def eiaLabOfferDetail() {
        if(params.ifModi=="1"){//不能删除
            if (params.fileUploadType) {
                render(view: "/eiaLab/eiaLabOfferDetail.gsp", model: [fileUploadType: params.fileUploadType, ifModi:false])
            } else {
                render(view: "/eiaLab/eiaLabOfferDetail.gsp", model: [ifModi:false])
            }
        } else {
            render(view: "/eiaLab/eiaLabOfferDetail.gsp", model: [ifModi:true])
        }
    }
    def eiaLabContactList() {
        render(view: "/eiaLab/eiaLabContactList.gsp")
    }
    def eiaLabOfferClientSelect() {
        render(view: "/eiaLab/eiaLabOfferClientSelect.gsp")
    }
    def otherFeeTypeSelect() {
        render(view: "/eiaLab/otherFeeTypeSelect.gsp")
    }
    def eiaLabOfferProjectSelect() {
        render(view: "/eiaLab/eiaLabOfferProjectSelect.gsp")
    }

    /**
     * 在检测费用计算器选择项目后新增监测方案
     */
    def eiaLabOfferProjectSave() {
//        /** 判断项目下是否已存在检测方案 */
//        def checkProject = eiaLabOfferService.checkEiaProjectBeUsed(params)
//        if (checkProject == HttpMesConstants.MSG_EIA_LAB_OFFER_PROJECT_REPEAT) {
//            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_EIA_LAB_OFFER_PROJECT_REPEAT] as JSON)
//        } else {
            def eiaLabOffer = eiaLabOfferService.eiaLabOfferProjectSave(params, session)
            if (eiaLabOffer) {
                render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SAVE_OK, data: eiaLabOffer] as JSON)
            } else {
                render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
            }
//        }
    }
    /**
     * 保存检测方案信息
     * @return
     */
    def eiaLabOfferSave() {
//        /** 判断项目下是否已存在检测方案 */
//        def checkProject = eiaLabOfferService.checkEiaProjectBeUsed(params)
//        if (checkProject == HttpMesConstants.MSG_EIA_LAB_OFFER_PROJECT_REPEAT) {
//            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_EIA_LAB_OFFER_PROJECT_REPEAT] as JSON)
//        } else {
            def sjClientId = params.sjClientId
            def wtClientId = params.wtClientId
            /** 保存受检客户联系人 */
            def contactData = [:]
            contactData.contactName = params.sjClientContact
            contactData.contactPhone = params.sjClientPhone
            contactData.eiaClientId = sjClientId
            /** 页面上已经不再填写联系人信息，统一到客户管理填写 */
//            if (eiaClientService.saveEiaClientContact(contactData, session) == HttpMesConstants.MSG_ADD_CONTACT_FALSE) {
//                render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_ADD_CONTACT_FALSE] as JSON)
//                return
//            }
            /**
             * 保存检测方案基本信息
             */
            def eiaLabOffer
            if (!params.eiaLabOfferId) {
                eiaLabOffer = eiaLabOfferService.eiaLabOfferSave(params, session, wtClientId, sjClientId)
            } else {
                eiaLabOffer = eiaLabOfferService.eiaLabOfferUpdate(params)
            }
            if (eiaLabOffer) {
                def responseData = [:]
                def contractInfo
                def param = [:]
                param.labClientId = wtClientId
                def labClientReportJson = HttpConnectTools.getResponseJson(HttpUrlConstants.LAB_CLIENT_REPORT_INFO, param)
                if (labClientReportJson) {
                    def reportJson = JsonHandler.jsonToMap(labClientReportJson).data
                    contractInfo = reportJson
                }
                responseData.eiaLabOfferId = eiaLabOffer.id
                responseData.eiaProjectId = eiaLabOffer.eiaProjectId
                responseData.wtClientId = wtClientId
                responseData.sjClientId = sjClientId
                responseData.ifYxTest = eiaLabOffer.ifYxTest
                responseData.testCompany = eiaLabOffer?.testCompany
                responseData.labClientContractInfoId = contractInfo?.id
                responseData.contractTax = contractInfo?.contractTax
                responseData.contractDiscount = contractInfo?.contractDiscount
                render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SAVE_OK, data: responseData] as JSON)
            } else {
                render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
            }
//        }
    }
    /**
     * 保存现场勘察费用
     * @return
     */
    def eiaLabOfferExploreSave() {
        def eiaLabOffer = eiaLabOfferService.eiaLabOfferExploreSave(params)
        if (eiaLabOffer) {
            render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SAVE_OK, data: eiaLabOffer] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
        }
    }
    /**
     * 保存测试信息
     * @return
     */
    def eiaLabOfferTestInfoSave() {
        def eiaLabOffer = eiaLabOfferService.eiaLabOfferTestInfoSave(params)
        if (eiaLabOffer) {
            render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SAVE_OK, data: eiaLabOffer] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
        }
    }
    /**
     * 删除检测方案信息
     */
    def eiaLabOfferDel() {
        if (eiaLabOfferService.eiaLabOfferDel(params.long('eiaLabOfferId'))) {
            render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_DEL_OK] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DEL_FAIL] as JSON)
        }
    }
    /**
     * 获取检测方案信息
     */
    def getEiaLabOfferDataMap() {
        def eiaLabOffer = eiaLabOfferService.getEiaLabOfferDataMap(params.long('eiaLabOfferId'))
        /** 委托方默认是联合泰泽 */
        def wtClientId
        def param = [:]
        param.busClientId = GeneConstants.TAIZE_BUS_CLIENT_ID_IN_LAB
        def labClientJson = HttpConnectTools.getResponseJson(HttpUrlConstants.LAB_CLIENT_INFO, param)
        if (labClientJson) {
            def labJson = JsonHandler.jsonToMap(labClientJson).data
            wtClientId = labJson.id
        }
        /** 预估检测计划小计费用 */
        def maxSampleFee = eiaLabOfferPlanService.getMaxSampleFee(params)
        if (eiaLabOffer) {
            def reportDate = eiaLabOffer.reportDate
            if (reportDate) {
                reportDate = reportDate.format("yyyy-MM-dd")
            }
            render([code: HttpMesConstants.CODE_OK, data: eiaLabOffer, wtClientId: wtClientId, maxSampleFee: maxSampleFee, reportDate: reportDate] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL, wtClientId: wtClientId] as JSON)
        }
    }
    /**
     * 获取检测方案信息列表
     */
    def getEiaLabOfferDataList() {
        def dataMap = eiaLabOfferService.getEiaLabOfferDataList(params, session)
        if (dataMap) {
            render([code: HttpMesConstants.CODE_OK, count: dataMap.total, data: dataMap.data] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
    /**
     * 检测方案获取项目名称list(如果任务有该人员，则可以选择任务下所有项目)
     */
    def getEiaProjectNameList() {
        def projectNameList = eiaProjectService.getEiaProjectNameList(session)
        if (projectNameList) {
            render([code: HttpMesConstants.CODE_OK, data: projectNameList] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
    /**
     * 根据项目名称获取项目地址
     */
    def getProjectAddr() {
        def project = eiaProjectService.getProjectAddr(params.long('eiaProjectId'))
        if (project) {
            render([code: HttpMesConstants.CODE_OK, data: project] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
    /**
     * 检测方案获取项目类型
     */
    def getOfferProjectType() {
        def param = [:]
        param.domain = GeneConstants.PROJECT_TYPE
        def projectTypeList = HttpConnectTools.getResponseJson(HttpUrlConstants.LAB_DOMAIN_CODE_LIST_INFO, param)
        if (projectTypeList) {
            def projectTypes = []
            def labJson = JsonHandler.jsonToMap(projectTypeList).data
            labJson.each {
                def dataMap = [:]
                dataMap.code = it?.code
                dataMap.codeDesc = it?.codeDesc
                projectTypes << dataMap
            }
            render([code: HttpMesConstants.CODE_OK, data: projectTypes] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
    /**
     * 从LabDomainCode获取检测计划的状态list
     */
    def getOfferStateList() {
        def param = [:]
        param.domain = GeneConstants.OFFER_STATE
        def offerStateList = HttpConnectTools.getResponseJson(HttpUrlConstants.LAB_DOMAIN_CODE_LIST_INFO, param)
        if (offerStateList) {
            def offerStates = []
            def labJson = JsonHandler.jsonToMap(offerStateList).data
            labJson.each {
                def dataMap = [:]
                dataMap.codeDesc = it?.codeDesc
                offerStates << dataMap
            }
            render([code: HttpMesConstants.CODE_OK, data: offerStates] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
    /**
     * 检测方案获取委托检测方list
     */
    def getOfferTestCompany() {
        def companyList = EiaDomainCode.findAllByDomain(GeneConstants.TEST_COMPANY, [sort: "displayOrder", order: "asc"])
        if (companyList) {
            render([code: HttpMesConstants.CODE_OK, data: companyList] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
    /**
     * 检测方案获取lab中联合泰泽下的联系人list
     */
    def getLabClientContactList() {
        def param = [:]
        param.contactInfo = params.contactInfo
        param.labClientId = params.clientId
        param.page = params.page
        param.limit = params.limit
        def contactList = HttpConnectTools.getResponseJson(HttpUrlConstants.LAB_CONTACT_CLIENT_LIST_INFO, param)
        if (contactList) {
            def contacts = []
            def labJson = JsonHandler.jsonToMap(contactList).data.data
            def count = JsonHandler.jsonToMap(contactList).data.total
            labJson.each {
                def dataMap = [:]
                dataMap.contactName = it?.contactName
                dataMap.contactType = it?.contactType
                dataMap.contactPhone = it?.contactPhone
                contacts << dataMap
            }
            render([code: HttpMesConstants.CODE_OK, data: contacts, count: count] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
    /**
     * 复制检测方案信息（包括检测方案里的检测计划信息）
     */
    def copyEiaLabOfferAndEiaLabOfferPlan(Long eiaLabOfferId) {
        def eiaLabOffer = EiaLabOffer.findByIdAndIfDel(eiaLabOfferId, false)
        if (eiaLabOffer) {
            def offer = new EiaLabOffer()
            bindData(offer, eiaLabOffer.properties, [exclude: ['offerCode', 'offerState']])
            offer.offerState = GeneConstants.OFFER_COMPLETE
            offer.pushState = GeneConstants.TAIZE_CREATE_INNER_OFFER
            if (offer.save(flush: true, failOnError: true)) {
                /**
                 * 复制检测方案里的检测计划
                 */
                def eiaLabOfferPlans = EiaLabOfferPlan.findAllByEiaLabOfferIdAndIfDel(eiaLabOfferId, false)
                if (eiaLabOfferPlans) {
                    for (Object eiaLabOfferPlan : eiaLabOfferPlans) {
                        def offerPlan = new EiaLabOfferPlan()
                        bindData(offerPlan, eiaLabOfferPlan.properties, [exclude: ['eiaLabOfferId']])
                        offerPlan.eiaLabOfferId = offer.id
                        offerPlan.save(flush: true, failOnError: true)
                    }
                }
            }
            render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SAVE_OK] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
        }
    }
    /**
     * 修改检测方案状态
     */
    def updateOfferState() {
        if (eiaLabOfferService.updateOfferState(params.long('eiaLabOfferId'), params.offerState)) {
            render([code: HttpMesConstants.CODE_OK] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL] as JSON)
        }
    }
    /**
     * 获取其他费用类型列表
     */
    def getOtherFeeTypeList() {
        def param = [:]
        param.page = params.page
        param.limit = params.limit
        param.otherFeeType = params.otherFeeType
        param.domain = GeneConstants.FEE_TYPE
        def resultJson = HttpConnectTools.getResponseJson(HttpUrlConstants.LAB_OTHER_FEE_LIST_INFO, param)
        if (resultJson) {
            def otherFeeList = []
            def labJson = JsonHandler.jsonToMap(resultJson).data.data
            def count = JsonHandler.jsonToMap(resultJson).data.total
            labJson.each {
                def dataMap = [:]
                dataMap.id = it.id
                dataMap.code = it?.code
                dataMap.fee = it?.fee
                dataMap.LAY_CHECKED = it?.LAY_CHECKED
                otherFeeList << dataMap
            }
            render([code: HttpMesConstants.CODE_OK, data: otherFeeList, count: count] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
    /**
     * 提交检测方案
     */
    def eiaLabOfferSubmit() {
        def eiaLabOfferId = params.long('eiaLabOfferId')
        /** 检查监测方案是否填写完成 */
        def checkResult = eiaLabOfferService.checkEiaLabOfferIfFinish(eiaLabOfferId)
        if (checkResult == HttpMesConstants.MSG_EIA_LAB_OFFER__NEED_FINISH) {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_EIA_LAB_OFFER__NEED_FINISH] as JSON)
        } else {
            def eiaLabOffer = EiaLabOffer.findById(eiaLabOfferId)
            def eiaLabOfferPlanList = EiaLabOfferPlan.findAllByEiaLabOfferIdAndIfDel(eiaLabOfferId, false)
            def eiaFileUploadList = EiaFileUpload.findAllByTableNameAndTableIdAndIfDel(GeneConstants.DOMAIN_EIA_LAB_OFFER, eiaLabOfferId, false)
            if (eiaLabOffer.ifYxTest) {
                def param = [:]
                param.eiaLabOffer = (eiaLabOffer as JSON).toString()
                param.eiaLabOfferPlanList = (eiaLabOfferPlanList as JSON).toString()
                param.eiaFileUploadList = (eiaFileUploadList as JSON).toString()
                param.inputDept = session.staff.orgName
                param.inputDeptId = session.staff.orgId
                param.inputUser = session.staff.staffName
                param.inputUserId = session.staff.staffId
                def resultJson = HttpConnectTools.getResponseJson(HttpUrlConstants.EIA_LAB_OFFER_SUBMIT, param)
                if (resultJson) {
                    def resultCode = JsonHandler.jsonToMap(resultJson).code
                    if (resultCode == HttpMesConstants.CODE_FAIL) {
                        render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SUBMIT_FAIL] as JSON)
                    }
                }
            }
            /** 开启工作流 */
            def workFlowCode = WorkFlowConstants.LAB_OFFER_WORK_FLOW
            if (workFlowCode) {
                def eiaWorkFlowBusi = eiaWorkFlowLabOfferService.startLabOfferWorkFlow(workFlowCode, GeneConstants.DOMAIN_EIA_LAB_OFFER, eiaLabOfferId, WorkFlowConstants.LAB_OFFER_WORKFLOW_START_NODE, session)
                if (eiaWorkFlowBusi) {
                    if (eiaLabOffer && eiaWorkFlowBusi) {
                        /** 更新检测方案的推送状态 */
                        eiaLabOfferService.updatePushState(eiaLabOfferId, GeneConstants.TAIZE_SUBMIT_INNER_OFFER)
                        render([code: HttpMesConstants.CODE_OK, data: eiaLabOffer] as JSON)
                    } else {
                        render([code: HttpMesConstants.CODE_FAIL] as JSON)
                    }
                } else {
                    render([code: HttpMesConstants.CODE_FAIL,msg:HttpMesConstants.MSG_FCONF_NULL] as JSON)
                }
            } else {
                render([code: HttpMesConstants.CODE_FAIL,msg:HttpMesConstants.MSG_CONTRACT_NO_FEE] as JSON)
            }
            render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SUBMIT_OK] as JSON)
        }
    }
    /**
     * 选择项目后，把项目信息更新到Lab报价
     */
    def updateProjectInfo() {
        def dataMap = eiaLabOfferService.eiaLabOfferUpdate(params)
        if (dataMap) {
            /** 更新监测方案后，更新宇相报价 */
            def eiaProjectId = dataMap?.eiaProjectId
            if (eiaProjectId) {
                def eiaProject = EiaProject.findById(Long.valueOf(eiaProjectId))
                def gisProjectId = eiaProject?.gisProjectId
                def eiaTaskId = eiaProject?.eiaTaskId
                def param = [:]
                param.labInnerOfferId = dataMap.id.toString()
                param.eiaTaskId = eiaTaskId.toString()
                param.eiaProjectId = eiaProjectId.toString()
                if (gisProjectId) {
                    param.gisProjectId = gisProjectId.toString()
                }
                param.projectName = dataMap?.projectName
                param.projectAddr = dataMap?.projectAddr
                def resultJson = HttpConnectTools.getResponseJson(HttpUrlConstants.UPDATE_LAB_OFFER_PROJECT_INFO, param)
                if (resultJson) {
                    def resultCode = JsonHandler.jsonToMap(resultJson).code
                    if (resultCode == HttpMesConstants.CODE_FAIL) {
                        render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SUBMIT_FAIL] as JSON)
                    }
                }
                render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SAVE_OK] as JSON)
            } else {
                render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_NOT_CHOOSE_PROJECT] as JSON)
            }
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
        }
    }
}
