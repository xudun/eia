package com.lheia.eia.contract

import com.lheia.eia.common.FuncConstants
import com.lheia.eia.common.GeneConstants
import com.lheia.eia.common.HttpMesConstants
import com.lheia.eia.common.HttpUrlConstants
import com.lheia.eia.common.WorkFlowConstants
import com.lheia.eia.config.EiaDomainCode
import com.lheia.eia.project.EiaProject
import com.lheia.eia.task.EiaTask
import com.lheia.eia.tools.DateTransTools
import com.lheia.eia.tools.HttpConnectTools
import com.lheia.eia.tools.JsonHandler
import com.lheia.eia.workflow.EiaWorkFlowBusi
import com.lheia.eia.workflow.EiaWorkFlowBusiLog
import grails.gorm.transactions.Transactional

import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.SimpleDateFormat

@Transactional
class EiaContractService {

    def eiaEnvProjectService

    /**
     * 通过合同id获取合同信息
     */
    def getEiaContractDataMap(eiaContractId) {
        return EiaContract.findByIdAndIfDel(eiaContractId, false)
    }
    /**
     * 合同列表显示信息
     * @param params
     * @param session
     * @return
     */
    def eiaContractQueryPage(params, session) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd")
        int page
        int limit
        if (params.page && params.limit) {
            page = params.int('page') - 1
            limit = params.int('limit')
        }
        /** 合同是否归档 */
        def ifArc = params.ifArc
        def arcContractIds
        if (ifArc) {
            def eiaWorkFlowBusiList = EiaWorkFlowBusi.createCriteria().list() {
                eq('tableName', 'EiaContract')
                if (ifArc == '是') {
                    eq('workFlowState', WorkFlowConstants.WORKFLOW_END)
                } else if (ifArc == '否') {
                    ne('workFlowState', WorkFlowConstants.WORKFLOW_END)
                }
                /**
                 * 查看全部的客户数据
                 */
                if (!session?.staff?.funcCode?.contains(FuncConstants.EIA_YWCX_HTCX_VIEWALL)) {
                    /**
                     * 查看本部门客户数据
                     */
                    if (session?.staff?.funcCode?.contains(FuncConstants.EIA_YWCX_HTCX_VIEWDEPT)) {
                        like ("inputDeptCode", "%"+ session.staff.orgCode +"%")
                    }
                    /**
                     * 查看本人客户数据
                     */
                    else if (session?.staff?.funcCode?.contains(FuncConstants.EIA_YWCX_HTCX_VIEWSELF)) {
                        /**
                         * 如果是暂存的话，不关联合同，所以自己只能看到自己的合同
                         */
                        or{
                            eq("inputUserId", Long.valueOf(session.staff.staffId))
                        }

                    }
                }
                eq('ifDel', false)
            }
            if (eiaWorkFlowBusiList) {
                arcContractIds = eiaWorkFlowBusiList.tableNameId
            }
        }
        def eiaContractList = EiaContract.createCriteria().list(max: limit, offset: page * limit) {
            def contractName = params.contractName
            if (contractName && !"合同名称,合同编号,录入部门,录入人".equals(contractName)) {
                or{
                    like("contractName", "%" + contractName + "%")
                    like("inputDept", "%" + contractName + "%")
                    like("inputUser", "%" + contractName + "%")
                    like("contractNo", "%" + contractName + "%")
                }
            } else {
                if (params.eiaClientId || params.eiaTaskId || params.eiaProjectId) {
                }else {
                    if (params.contractTrust || params.clientName || params.contractType || params.ifArc || params.startDate || params.endDate || params.conStartMoney || params.conEndMoney || params.ifAgency) {
                    } else {
                        or{
                            eq("inputUserId", Long.valueOf(session.staff.staffId))
                            like("taskAssignUser", "%" + session.staff.staffName+"_"+session.staff.staffId + "%")
                        }
                    }
                }
            }
            /** 合同受托方 */
            def contractTrust = params.contractTrust
            if (contractTrust) {
                eq("contractTrust", contractTrust)
            }
            /** 客户（甲方）名称 */
            def clientName = params.clientName
            if (clientName) {
                or {
                    like("eiaClientName", "%" + clientName + "%")
                    like("ownerClientName", "%" + clientName + "%")
                }
            }
            /** 合同类型 */
            def contractType = params.contractType
            if (contractType) {
                like("contractType", "%" + contractType + "%")
            }
            /** 合同是否归档 */
            if (ifArc) {
                if (arcContractIds) {
                    'in'("id", arcContractIds)
                } else {
                    eq("id", Long.valueOf(-1))
                }
            }
            /** 签订日期 */
            def startDate = params.startDate
            def endDate = params.endDate
            if (startDate) {
                ge('contractDate', sdf.parse(startDate))
            }
            if (endDate) {
                le('contractDate', sdf.parse(endDate))
            }
            /** 合同额 */
            def conStartMoney = params.conStartMoney
            if (conStartMoney) {
                ge("contractMoney", new BigDecimal(conStartMoney))
            }
            def conEndMoney = params.conEndMoney
            if (conEndMoney) {
                le("contractMoney", new BigDecimal(conEndMoney))
            }
            /** 是否有中介合同（如果既有客户信息也有甲方信息且两者不是同一单位，视为有中介合同） */
            def ifAgency = params.ifAgency
            if (ifAgency == '是') {
                isNotNull("eiaClientName")
                isNotNull("ownerClientName")
                neProperty("eiaClientName", "ownerClientName")
            } else if (ifAgency == '否') {
                isNull("ownerClientName")
            }
            /**
             * 查看全部的客户数据
             */
            if (!session?.staff?.funcCode?.contains(FuncConstants.EIA_YWCX_HTCX_VIEWALL)) {
                /**
                 * 查看本部门客户数据
                 */
                if (session?.staff?.funcCode?.contains(FuncConstants.EIA_YWCX_HTCX_VIEWDEPT)) {
                    like ("inputDeptCode", "%"+ session.staff.orgCode +"%")
                }
                /**
                 * 查看本人客户数据
                 */
                else if (session?.staff?.funcCode?.contains(FuncConstants.EIA_YWCX_HTCX_VIEWSELF)) {
                    /**
                     * 如果是暂存的话，不关联合同，所以自己只能看到自己的合同
                     */
                    or{
                        eq("inputUserId", Long.valueOf(session.staff.staffId))
                        like("taskAssignUser", "%" + session.staff.staffName+"_"+session.staff.staffId + "%")
                    }

                }
            }
            if (params.boolean('ifSub')) {
                eq("ifSub", true)
            }
            /**
             * 判断是否有任务id，如果有任务id，说明列表为跟任务相关的
             */
            if(params.eiaTaskId){
                eq("taskId", params.long("eiaTaskId"))
            }
            /**
             * 如果有项目id，说明类表示通过项目对应的的合同信息
             */
            if(params.eiaProjectId){
                def eiaProject = EiaProject.findByIfDelAndId(false,params.long("eiaProjectId"))
                if(eiaProject?.eiaContractId){
                    eq("id", eiaProject?.eiaContractId)
                }else {
                    eq("id",Long.valueOf(-1))
                }
            }
            /**
             * 客户项目合同
             */
            if(params.eiaClientId){
                eq("eiaClientId",params.long("eiaClientId"))
            }

            eq("ifDel", false)
            if(params.ifShow){
            }else{
                eq("ifShow", true)
            }
            order("id", "desc")
        }
        def eiaContractData = []
        eiaContractList.each {
            def map = [:]
            map.id = it.id
            map.contractName = it.contractName
            map.contractNo = it.contractNo
            map.eiaClientName = it.eiaClientName
            map.contractType = it.contractType
            map.taskName = it.taskName
            map.taskNo = it.taskNo
            map.ifSub = it.ifSub
            def ifShow = "否"
            if(it.ifShow){
                ifShow = "是"
            }
            map.ifShow = ifShow
            SimpleDateFormat formaContractDate = new SimpleDateFormat("yyyy-MM-dd");
            if (it.contractDate) {
                String contractDate = formaContractDate.format(it.contractDate);
                map.contractDate = contractDate
            }
            map.contractMoney = it.contractMoney
            map.inputDept = it.inputDept
            map.inputUser = it.inputUser
            map.inputUserId = it.inputUserId
            def reportFeeSum = 0
            def serSurPreSum = 0
            def preSurvCertFee = 0
            def preIssCertFee = 0
            def certServeFee = 0
            def otherFee = 0
            /**
             * 项目编制费
             */
            if(it?.reportFees>0){
                reportFeeSum =it.reportFees?:0
            }else{
                /**
                 * 其他费用
                 */
                if(it.otherFee){
                    otherFee =it.otherFee?:0
                }
                /**
                 * 发行前认证费
                 */
                if(it.preIssCertFee){
                    preIssCertFee =it.preIssCertFee?:0
                }
                /**
                 * 存续期认证费
                 */
                if(it.preSurvCertFee){
                    preSurvCertFee =it.preSurvCertFee?:0
                }
                /**
                 * 认证服务费
                 */
                if(it.certServeFee){
                    certServeFee =it.certServeFee?:0
                }
            }
            /**
             * 计算利润率
             */
            BigDecimal preIssCertFeeSum = new BigDecimal(preIssCertFee ? preIssCertFee : 0);
            BigDecimal preSurvCertFeeSum = new BigDecimal(preSurvCertFee ? preSurvCertFee : 0);
            BigDecimal certServeFeeSum = new BigDecimal(certServeFee ? certServeFee : 0);
            BigDecimal otherFeeSum = new BigDecimal(otherFee ? otherFee : 0);
            serSurPreSum =  preIssCertFeeSum+preSurvCertFeeSum+certServeFeeSum+otherFeeSum
            BigDecimal profitMarginMin = serSurPreSum + reportFeeSum
            if(profitMarginMin && it?.contractMoney>0){
                map.profitMargin = profitMarginMin.divide(it?.contractMoney,4, RoundingMode.HALF_UP)?:0
            }else{
                map.profitMargin = new BigDecimal(0);
            }
            eiaContractData << map
        }
        def dataMap = [:]
        dataMap.data = eiaContractData
        dataMap.total = eiaContractList.totalCount
        return dataMap
    }
    /**
     *  获取合同列表（下拉）
     */
    def getEiaContractSelectList(params, session) {
        def eiaTask
        def eiaWorkFlowBusi = EiaWorkFlowBusi.findByIdAndIfDel(params.long("eiaWorkFlowBusiId"),false)
        if(eiaWorkFlowBusi){
            def eiaProject = EiaProject.findByIfDelAndId(false,eiaWorkFlowBusi?.tableNameId)
            if(eiaProject){
                eiaTask = EiaTask.findByIfDelAndId(false,eiaProject?.eiaTaskId)
            }
        }
        def eiaContract
        if(eiaTask){
            eiaContract = EiaContract.findAllByIfDelAndContractStateAndInputDeptIdAndTaskId(false,GeneConstants.CONTRACT_STATE_SIGNED,eiaTask?.inputDeptId,eiaTask?.id)
        }else{
            eiaContract = EiaContract.findAllByInputUserIdAndIfDelAndContractState(Long.parseLong(session.staff.staffId), false,GeneConstants.CONTRACT_STATE_SIGNED)
        }
        if (eiaContract) {
            return eiaContract
        } else {
            return false
        }
    }
    /**
     * 合同信息保存
     */
    def eiaContractSave(params, session) {
        def eiaContract = new EiaContract()
        def contractDate = params.contractDate
        if(contractDate){
            params.contractDate = DateTransTools.getFormatDateD(params.contractDate)
        }
        eiaContract.properties = params
        eiaContract.ifShow = true
        eiaContract.inputDept = session.staff.orgName
        eiaContract.inputDeptCode = session.staff.orgCode
        eiaContract.inputDeptId = Long.parseLong(session.staff.orgId)
        eiaContract.inputUser = session.staff.staffName
        eiaContract.inputUserId = Long.parseLong(session.staff.staffId)
        eiaContract.contractState = GeneConstants.CONTRACT_STATE_SIGNED
        if (params.contractTypeStr) {
            eiaContract.contractType = params.contractTypeDrop
            eiaContract.contractTypeCode = params.contractTypeStr
        }
        def eiaTask = EiaTask.findByIdAndIfDel(params.long("taskId"), false)
        if (eiaTask) {
            eiaContract.taskNo = eiaTask.taskNo
            eiaContract.taskName = eiaTask.taskName
            eiaContract.taskAssignUser = eiaTask?.taskAssignUser
        }
        eiaContract = eiaContract.save(flush: true, failOnError: true)
        if (eiaContract) {
            if (params.ifTempSave) {
                return eiaContract
            } else {
                /**
                 * 生成合同编号
                 */
                def contractNo = this.getContractNo(contractDate, eiaContract.id)
                eiaContract.contractNo = contractNo
                return  eiaContract.save(flush: true, failOnError: true)
            }
        }
    }
    /**
     * 合同信息编辑
     */
    def eiaContractUpdate(params, session) {
        def eiaContract = EiaContract.findByIdAndIfDel(params.long("eiaContractId"), false)
        def  contractDate = params.contractDate
        if(params.contractDate){
            params.contractDate = DateTransTools.getFormatDateD(params.contractDate)
        }
        eiaContract.properties = params
        if (params.contractTypeStr) {
            eiaContract.contractType = params.contractTypeDrop
            eiaContract.contractTypeCode = params.contractTypeStr
        }
        def eiaTask = EiaTask.findByIdAndIfDel(params.long("taskId"), false)
        if (eiaTask) {
            eiaContract.taskNo = eiaTask.taskNo
            eiaContract.taskName = eiaTask.taskName
            eiaContract.taskAssignUser = eiaTask?.taskAssignUser
        }
        eiaContract = eiaContract.save(flush: true, failOnError: true)
        if (params.ifTempSave) {
            return eiaContract
        } else {
            if (eiaContract?.contractNo) {
                return eiaContract
            } else {
                /**
                 * 生成合同编号
                 */
                def contractNo = getContractNo(contractDate, eiaContract.id)
                eiaContract.contractNo = contractNo
                return  eiaContract.save(flush: true, failOnError: true)
            }
        }
    }
    /***
     * 合同信息删除
     */
    def eiaContractDel(eiaContractId) {
        def eiaContract = getEiaContractDataMap(eiaContractId)
        eiaContract.ifDel = true
        eiaContract.save(flush: true, failOnError: true)
    }
    /**
     * 报价中生成合同
     */
    def eiaContractGenerateSave(params, session) {
        def eiaContract = new EiaContract()
        eiaContract.properties = params
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd")
        eiaContract.contractDate = sdf.parse(params.contractDateStr)
        eiaContract.inputDept = session.staff.orgName
        eiaContract.inputDeptCode = session.staff.orgCode
        eiaContract.inputDeptId = Long.parseLong(session.staff.orgId)
        eiaContract.inputUser = session.staff.staffName
        eiaContract.inputUserId = Long.parseLong(session.staff.staffId)
        eiaContract.contractState = GeneConstants.CONTRACT_STATE_SIGNED
        if (params.contractTypeStr) {
            eiaContract.contractType = params.contractTypeDrop
            eiaContract.contractTypeCode = params.contractTypeStr
        }
        def eiaTask = EiaTask.findByIdAndIfDel(params.long("taskId"), false)
        if (eiaTask) {
            eiaContract.taskNo = eiaTask.taskNo
            eiaContract.taskName = eiaTask.taskName
            eiaContract.taskAssignUser = eiaTask?.taskAssignUser
        }
        if (eiaContract.save(flush: true, failOnError: true)) {
            /**
             * 生成合同编号
             */
            def contractNo = this.getContractNo(params.contractDateStr, eiaContract.id)
            eiaContract.contractNo = contractNo
            def eiaOffer = EiaOffer.findByIdAndIfDel(params.eiaOfferId, false)
            eiaOffer.offerState = GeneConstants.CONTRACT_STATE_SIGNED
            eiaContract.save(flush: true, failOnError: true)
        }
    }

    /**—————————————————————生成合同编号—————————————————————**/
    /**
     * 根据年份生成合同编号
     * @return
     */
    def getContractNo(String contractDate, Long eiaContractId) {
        def year = contractDate.split("-")[0]
//        def contracDomain = EiaDomainCode.findByDomainAndCodeDesc(GeneConstants.CONTRACT_NUM,year)
//        def contracNum = Integer.parseInt(contracDomain.code)
//        /**
//         * 更新EiaDomainCode的对应年份合同数量
//         */
//        contracDomain.code = contracNum + 1
//        contracDomain.save(flush: true, failOnError: true)
        /**
         * 格式化合同号
         */
        DecimalFormat df = new DecimalFormat("0000")
        String contracNumFormat = df.format(eiaContractId)
        String contractNo = "C-" + year + "-" + contracNumFormat
        return contractNo
    }

    /**—————————————————————报价功能—————————————————————**/
    /**
     * 通过报价id获取报价信息
     */
    def getEiaOfferDataMap(eiaOffId) {
        return EiaOffer.findByIdAndIfDel(eiaOffId, false)
    }
    /**
     * 报价列表
     */
    def eiaOfferQueryPage(params, session) {
        int page
        int limit
        if (params.page && params.limit) {
            page = params.int('page') - 1
            limit = params.int('limit')
        }
        def eiaOfferList = EiaOffer.createCriteria().list(max: limit, offset: page * limit) {
            def offerName = params.offerName
            if (offerName && !"合同名称、报价编号、录入部门、录入人".equals(offerName)) {
                or{
                    like("offerName", "%" + offerName + "%")
                    like("offerNo", "%" + offerName + "%")
                    like("inputDept", "%" + offerName + "%")
                    like("inputUser", "%" + offerName + "%")
                }
            }else{
                if(params.eiaClientId){
                }else {
                    eq ("inputUserId", Long.valueOf(session.staff.staffId))
                }
            }
            /**
             * 客户相关报价
             */
            if(params.eiaClientId){
                eq("eiaClientId",params.long("eiaClientId"))
            }
            eq("offerState", GeneConstants.CONTRACT_STATE_NOT_SIGNED)
            eq("ifDel", false)
            order("id", "desc")
        }
        def eiaOffeData = []
        eiaOfferList.each {
            def map = [:]
            map.id = it.id
            map.offerName = it.offerName
            map.offerNo = it.offerNo
            map.offerState = it.offerState
            map.contractType = it.contractType
            map.offerMoney = it.offerMoney
            map.taskName = it.taskName
            map.taskNo = it.taskNo
            map.offerMoney = it.offerMoney
            SimpleDateFormat formaOfferDate = new SimpleDateFormat("yyyy-MM-dd");
            map.offerDate = formaOfferDate.format(it.offerDate)
            map.inputDept = it.inputDept
            map.inputUser = it.inputUser
            map.inputUserId = it.inputUserId
            map.ifSub = it.ifSub
            def eiaWorkFlowBusiList = EiaWorkFlowBusi.findAllByTableNameAndTableNameIdAndWorkFlowStateInListAndIfDel(GeneConstants.DOMAIN_EIA_OFFER, it.id, [WorkFlowConstants.WORKFLOW_UNDER_WAY,WorkFlowConstants.WORKFLOW_START], false)
            if (eiaWorkFlowBusiList) {
                map.ifHaveFlow = true
            } else {
                map.ifHaveFlow = false
            }
            /**
             * 判断报价流程是否结束
             */
            def isOfferDel = false
            def eiaWorkFlowBusi = EiaWorkFlowBusi.findByIfDelAndTableNameAndTableNameId(false,GeneConstants.DOMAIN_EIA_OFFER,it.id)
            if(eiaWorkFlowBusi){
                if(eiaWorkFlowBusi.workFlowState.equals(WorkFlowConstants.WORKFLOW_END)){
                    isOfferDel = true
                }
            }
            map.isOfferDel = isOfferDel
            eiaOffeData << map
        }
        def dataMap = [:]
        dataMap.data = eiaOffeData
        dataMap.total = eiaOfferList.totalCount
        return dataMap
    }
    /**
     * 报价信息保存
     */
    def eiaOfferSave(params, session) {
        def eiaOffer = new EiaOffer()
        eiaOffer.properties = params
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd")
        eiaOffer.offerDate = sdf.parse(params.offerDateStr)
        eiaOffer.inputDept = session.staff.orgName
        eiaOffer.inputDeptCode = session.staff.orgCode
        eiaOffer.inputDeptId = Long.parseLong(session.staff.orgId)
        eiaOffer.inputUser = session.staff.staffName
        eiaOffer.inputUserId = Long.parseLong(session.staff.staffId)

        if (params.contractTypeStr) {
            eiaOffer.contractType = params.contractTypeDrop
            eiaOffer.contractTypeCode = params.contractTypeStr
        }
        def eiaTask = EiaTask.findByIdAndIfDel(params.long("taskId"), false)
        if (eiaTask) {
            eiaOffer.taskNo = eiaTask.taskNo
            eiaOffer.taskName = eiaTask.taskName
        }
        eiaOffer.offerState = GeneConstants.CONTRACT_STATE_NOT_SIGNED
        eiaOffer.ifSub = GeneConstants.OFFER_ORIGINAL_STATE
        if (eiaOffer.save(flush: true, failOnError: true)) {
            /**
             * 生成报价编号
             */
            def offerNo = this.getOfferNo(params.offerDateStr, eiaOffer.id)
            eiaOffer.offerNo = offerNo
            eiaOffer.save(flush: true, failOnError: true)
        }
    }
    /**
     * 报价信息编辑
     */
    def eiaOfferUpdate(params, session) {
        def eiaOffer = EiaOffer.findByIdAndIfDel(params.long("eiaOfferId"), false)
        eiaOffer.properties = params
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd")
        eiaOffer.offerDate = sdf.parse(params.offerDateStr)
        if (params.contractTypeStr) {
            eiaOffer.contractType = params.contractTypeDrop
            eiaOffer.contractTypeCode = params.contractTypeStr
        }
        def eiaTask = EiaTask.findByIdAndIfDel(params.long("taskId"), false)
        if (eiaTask) {
            eiaOffer.taskNo = eiaTask.taskNo
            eiaOffer.taskName = eiaTask.taskName
        }
        eiaOffer.save(flush: true, failOnError: true)

    }
    /**
     * 报价信息删除
     */
    def eiaOfferDel(eiaOfferId) {
        def eiaOffer = getEiaOfferDataMap(eiaOfferId)
        eiaOffer.ifDel = true
        eiaOffer.save(flush: true, failOnError: true)
    }
    /**
     * 合同印章申请审批单
     */
    def contractSealApplyApprove(params) {
        def tableName = params.tableName
        def tableNameId = params.long('tableNameId')
        def eiaContractId = tableNameId
        /**
         * 如果是合同变更流程重新取合同ID
         */
        if(tableName == GeneConstants.DOMAIN_EIA_CONTRACT_LOG){
            eiaContractId = EiaContractLog.findByIdAndIfDel(tableNameId,false).eiaContractId
        }
        def eiaContract = EiaContract.findById(eiaContractId)
        if (eiaContract) {
            def dataMap = eiaEnvProjectService.convertToMap(eiaContract.properties)
            /** 经办人，即合同录入人 */
            def inputUser
            def authParam = [:]
            authParam.staffId = String.valueOf(eiaContract.inputUserId)
            def authJson = HttpConnectTools.getResponseJson(HttpUrlConstants.SINGLE_EIA_AUTH_STAFF_INFO, authParam)
            if (authJson) {
                if(JsonHandler.jsonToMap(authJson).code == HttpMesConstants.CODE_OK){
                    def staff = JsonHandler.jsonToMap(authJson).data[0]
                    if (staff.signImagePath) {
                        inputUser = GeneConstants.AUTH_FILE_URL_PATH + staff.signImagePath
                    }
                }
            }
            dataMap.put("inputUser", inputUser)
            if (tableNameId) {
                def eiaWorkFlowBusi = EiaWorkFlowBusi.findByTableNameAndTableNameIdAndWorkFlowStateInListAndIfDel(tableName, tableNameId, [WorkFlowConstants.WORKFLOW_UNDER_WAY,WorkFlowConstants.WORKFLOW_START,WorkFlowConstants.WORKFLOW_END], false)
                /** 申请时间 */
                def applyDate = eiaWorkFlowBusi?.dateCreated.format("yyyy-MM-dd")
                dataMap.put("applyDate", applyDate)
                if (eiaWorkFlowBusi) {
                    /** 合规部审批人 */
                    def hgshBusiLog = EiaWorkFlowBusiLog.findAllByEiaWorkFlowBusiIdAndNodesCodeAndWorkFlowStateAndIfDel(eiaWorkFlowBusi.id, WorkFlowConstants.CERT_WORK_FLOW_NODE_HGSH, WorkFlowConstants.WORKFLOW_UNDER_WAY, false, [sort: "id", order: "desc",])
                    if (hgshBusiLog) {
                        def complianceUserId = hgshBusiLog.get(0)?.updateUserId
                        def complianceUser
                        def param = [:]
                        param.staffId = String.valueOf(complianceUserId)
                        def staffJson = HttpConnectTools.getResponseJson(HttpUrlConstants.SINGLE_EIA_AUTH_STAFF_INFO, param)
                        if (staffJson) {
                            if(JsonHandler.jsonToMap(staffJson).code == HttpMesConstants.CODE_OK){
                                def staff = JsonHandler.jsonToMap(staffJson).data[0]
                                if (staff.signImagePath) {
                                    complianceUser = GeneConstants.AUTH_FILE_URL_PATH + staff.signImagePath
                                }
                            }
                        }
                        dataMap.put("complianceUser", complianceUser)
                    }
                    /** 部门经理 */
                    def bmshBusiLog = EiaWorkFlowBusiLog.findAllByEiaWorkFlowBusiIdAndNodesCodeAndWorkFlowStateAndIfDel(eiaWorkFlowBusi.id, WorkFlowConstants.CONTRACT_WORK_FLOW_NODE_BMSH, WorkFlowConstants.WORKFLOW_UNDER_WAY, false, [sort: "id", order: "desc",])
                    if (bmshBusiLog) {
                        def deptManagerId = bmshBusiLog.get(0)?.updateUserId
                        def deptManager
                        def param = [:]
                        param.staffId = String.valueOf(deptManagerId)
                        def staffJson = HttpConnectTools.getResponseJson(HttpUrlConstants.SINGLE_EIA_AUTH_STAFF_INFO, param)
                        if (staffJson) {
                            if(JsonHandler.jsonToMap(staffJson).code == HttpMesConstants.CODE_OK){
                                def staff = JsonHandler.jsonToMap(staffJson).data[0]
                                if (staff.signImagePath) {
                                    deptManager = GeneConstants.AUTH_FILE_URL_PATH + staff.signImagePath
                                }
                            }
                        }
                        dataMap.put("deptManager", deptManager)
                    }
                    /** 总经理 */
                    def zjlshBusiLog = EiaWorkFlowBusiLog.findAllByEiaWorkFlowBusiIdAndNodesCodeAndWorkFlowStateAndIfDel(eiaWorkFlowBusi.id, WorkFlowConstants.CERT_WORK_FLOW_NODE_ZJLSH, WorkFlowConstants.WORKFLOW_UNDER_WAY, false, [sort: "id", order: "desc",])
                    if (zjlshBusiLog) {
                        def topManagerId = zjlshBusiLog.get(0)?.updateUserId
                        def topManager
                        def param = [:]
                        param.staffId = String.valueOf(topManagerId)
                        def staffJson = HttpConnectTools.getResponseJson(HttpUrlConstants.SINGLE_EIA_AUTH_STAFF_INFO, param)
                        if (staffJson) {
                            if(JsonHandler.jsonToMap(staffJson).code == HttpMesConstants.CODE_OK){
                                def staff = JsonHandler.jsonToMap(staffJson).data[0]
                                if (staff.signImagePath) {
                                    topManager = GeneConstants.AUTH_FILE_URL_PATH + staff.signImagePath
                                }
                            }
                        }
                        dataMap.put("topManager", topManager)
                    }
                    return dataMap
                }
            }
        }
    }

    /**—————————————————————生成报价编号—————————————————————**/
    /**
     * 根据年份生成报价编号
     * @return
     */
    def getOfferNo(String offerDate, Long eiaOfferId) {
        def year = offerDate.split("-")[0]
//        def offerDomain = EiaDomainCode.findByDomainAndCodeDesc(GeneConstants.OFFER_NUM,year)
//        def offerNum = Integer.parseInt(offerDomain.code)
//        /**
//         * 更新EiaDomainCode的对应年份报价数量
//         */
//        offerDomain.code = offerNum + 1
//        offerDomain.save(flush: true, failOnError: true)
        /**
         * 格式化报价号
         */
        DecimalFormat df = new DecimalFormat("0000")
        String offerNumFormat = df.format(eiaOfferId)
        String offerNo = "D-" + year + "-" + offerNumFormat
        return offerNo
    }
    /**
     * 合同提交
     * @param eiaContractId
     */
    def eiaContractSub(eiaContractId) {
        def eiaContract = EiaContract.findByIdAndIfDel(eiaContractId, false)
        eiaContract.ifSub = true
        eiaContract.save(flush: true, failOnError: true)
    }
    /**
     * 报价提交
     * @param eiaOfferId
     */
    def eiaOfferSub(eiaOfferId, ifSub) {
        def eiaOffer = EiaOffer.findByIdAndIfDel(eiaOfferId, false)
        eiaOffer.ifSub = ifSub
        eiaOffer.save(flush: true, failOnError: true)
    }
    /**
     * 根据不同的文件类型获取要填写的金额名称
     */
    def getContractMoneyList(params) {
        String contractTypeCode = params.contractTypeCode
        if (contractTypeCode) {
            def parentCode = EiaDomainCode.findByDomainAndCode(GeneConstants.CONTRACT_TYPE, contractTypeCode)?.parentCode
            if (contractTypeCode.indexOf("ZH") == -1 && contractTypeCode.indexOf("QT") == -1) {
                if (parentCode == "EPC") {
                    if (contractTypeCode == "EPC_HD") {
                        return GeneConstants.CON_HD_MONEY_LIST
                    } else if (contractTypeCode == "EPC_HP") {
                        return GeneConstants.CON_HP_MONEY_LIST
                    } else if (contractTypeCode == "EPC_HY") {
                        return GeneConstants.CON_HY_MONEY_LIST
                    } else if (contractTypeCode == "EPC_HB") {
                        return GeneConstants.CON_HB_MONEY_LIST
                    } else if (contractTypeCode == "EPC_GH") {
                        return GeneConstants.CON_GH_MONEY_LIST
                    } else if (contractTypeCode == "EPC_JC") {
                        return GeneConstants.CON_JC_MONEY_LIST
                    } else if (contractTypeCode == "EPC_DC") {
                        return GeneConstants.CON_DC_MONEY_LIST
                    } else if (contractTypeCode == "EPC_YA") {
                        return GeneConstants.CON_YA_MONEY_LIST
                    } else if (contractTypeCode == "EPC_JL") {
                        return GeneConstants.CON_JL_MONEY_LIST
                    } else if (contractTypeCode == "EPC_HH") {
                        return GeneConstants.CON_HH_MONEY_LIST
                    } else if (contractTypeCode == "EPC_GJ") {
                        return GeneConstants.CON_GJ_MONEY_LIST
                    } else if (contractTypeCode == "EPC_XZ") {
                        return GeneConstants.CON_XZ_MONEY_LIST
                    } else if (contractTypeCode == "EPC_PF") {
                        return GeneConstants.CON_PF_MONEY_LIST
                    } else if (contractTypeCode == "EPC_PW") {
                        return GeneConstants.CON_PW_MONEY_LIST
                    } else if (contractTypeCode == "EPC_CD") {
                        return GeneConstants.CON_CD_MONEY_LIST
                    } else if (contractTypeCode == "EPC_ST") {
                        return GeneConstants.CON_ST_MONEY_LIST
                    }
                } else if (parentCode == "ESE") {
                    if (contractTypeCode == "ESE_JN") {
                        return GeneConstants.CON_JN_MONEY_LIST
                    } else if (contractTypeCode == "ESE_QJ") {
                        return GeneConstants.CON_QJ_MONEY_LIST
                    }
                } else if (parentCode == "GREEN") {
                    if (contractTypeCode == "GREEN_LZ") {
                        return GeneConstants.CON_LZ_MONEY_LIST
                    } else if (contractTypeCode == "GREEN_LQ") {
                        return GeneConstants.CON_LQ_MONEY_LIST
                    } else {
                        return GeneConstants.CON_GREEN_MONEY_LIST
                    }
                }
            } else if (contractTypeCode.indexOf("ZH") != -1) {
                if (contractTypeCode == "EPC_ZH") {
                    return GeneConstants.CON_EPC_ZH_MONEY_LIST
                } else {
                    return GeneConstants.CON_ZH_MONEY_LIST
                }
            } else if (contractTypeCode.indexOf("QT") != -1) {
                return GeneConstants.CON_QT_MONEY_LIST
            }
        }
    }
    /**
     * 根据不同的文件类型获取要填写的金额名称和对应的值
     */
    def getContractMoney(params) {
        def eiaContractId = params.long('eiaContractId')
        def eiaOfferId = params.long('eiaOfferId')
        def eiaContract
        def eiaOffer
        def contractTypeCode
        if (eiaContractId) {
            eiaContract = EiaContract.findById(eiaContractId)
        } else if (eiaOfferId) {
            eiaOffer = EiaOffer.findById(eiaOfferId)
        }
        def properties
        if (eiaContract) {
            properties = eiaContract.properties
            contractTypeCode = eiaContract.contractTypeCode
        } else if (eiaOffer) {
            properties = eiaOffer.properties
            contractTypeCode = eiaOffer.contractTypeCode
        }
        def parentCode = EiaDomainCode.findByDomainAndCode(GeneConstants.CONTRACT_TYPE, contractTypeCode)?.parentCode
        def dataMap
        if (contractTypeCode.indexOf("ZH") == -1 && contractTypeCode.indexOf("QT") == -1) {
            if (parentCode == "EPC") {
                if (contractTypeCode == "EPC_HD") {
                    dataMap = this.combNeedMap(properties, GeneConstants.CON_HD_MONEY_LIST)
                } else if (contractTypeCode == "EPC_HP") {
                    dataMap = this.combNeedMap(properties, GeneConstants.CON_HP_MONEY_LIST)
                } else if (contractTypeCode == "EPC_HY") {
                    dataMap = this.combNeedMap(properties, GeneConstants.CON_HY_MONEY_LIST)
                } else if (contractTypeCode == "EPC_HB") {
                    dataMap = this.combNeedMap(properties, GeneConstants.CON_HB_MONEY_LIST)
                } else if (contractTypeCode == "EPC_GH") {
                    dataMap = this.combNeedMap(properties, GeneConstants.CON_GH_MONEY_LIST)
                } else if (contractTypeCode == "EPC_JC") {
                    dataMap = this.combNeedMap(properties, GeneConstants.CON_JC_MONEY_LIST)
                } else if (contractTypeCode == "EPC_DC") {
                    dataMap = this.combNeedMap(properties, GeneConstants.CON_DC_MONEY_LIST)
                } else if (contractTypeCode == "EPC_YA") {
                    dataMap = this.combNeedMap(properties, GeneConstants.CON_YA_MONEY_LIST)
                } else if (contractTypeCode == "EPC_JL") {
                    dataMap = this.combNeedMap(properties, GeneConstants.CON_JL_MONEY_LIST)
                } else if (contractTypeCode == "EPC_HH") {
                    dataMap = this.combNeedMap(properties, GeneConstants.CON_HH_MONEY_LIST)
                } else if (contractTypeCode == "EPC_GJ") {
                    dataMap = this.combNeedMap(properties, GeneConstants.CON_GJ_MONEY_LIST)
                } else if (contractTypeCode == "EPC_XZ") {
                    dataMap = this.combNeedMap(properties, GeneConstants.CON_XZ_MONEY_LIST)
                } else if (contractTypeCode == "EPC_PF") {
                    dataMap = this.combNeedMap(properties, GeneConstants.CON_PF_MONEY_LIST)
                } else if (contractTypeCode == "EPC_PW") {
                    dataMap = this.combNeedMap(properties, GeneConstants.CON_PW_MONEY_LIST)
                } else if (contractTypeCode == "EPC_CD") {
                    dataMap = this.combNeedMap(properties, GeneConstants.CON_CD_MONEY_LIST)
                } else if (contractTypeCode == "EPC_ST") {
                    dataMap = this.combNeedMap(properties, GeneConstants.CON_ST_MONEY_LIST)
                }
            } else if (parentCode == "ESE") {
                if (contractTypeCode == "ESE_JN") {
                    dataMap = this.combNeedMap(properties, GeneConstants.CON_JN_MONEY_LIST)
                } else if (contractTypeCode == "ESE_QJ") {
                    dataMap = this.combNeedMap(properties, GeneConstants.CON_QJ_MONEY_LIST)
                }
            } else if (parentCode == "GREEN") {
                if (contractTypeCode == "GREEN_LZ") {
                    dataMap = this.combNeedMap(properties, GeneConstants.CON_LZ_MONEY_LIST)
                } else if (contractTypeCode == "GREEN_LQ") {
                    dataMap = this.combNeedMap(properties, GeneConstants.CON_LQ_MONEY_LIST)
                } else {
                    dataMap = this.combNeedMap(properties, GeneConstants.CON_GREEN_MONEY_LIST)
                }
            }
        } else if (contractTypeCode.indexOf("ZH") != -1) {
            if (contractTypeCode == "EPC_ZH") {
                dataMap = this.combNeedMap(properties, GeneConstants.CON_EPC_ZH_MONEY_LIST)
            } else {
                dataMap = this.combNeedMap(properties, GeneConstants.CON_ZH_MONEY_LIST)
            }
        } else if (contractTypeCode.indexOf("QT") != -1) {
            dataMap = this.combNeedMap(properties, GeneConstants.CON_QT_MONEY_LIST)
        }
        return dataMap
    }
    /**
     * 报价生成合同
     */
    def eiaContractGene(params, session) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd")
        def eiaOfferId = params.long('eiaOfferId')
        def eiaOffer = EiaOffer.findById(eiaOfferId)
        def ifSub = eiaOffer?.ifSub
        if (eiaOffer) {
            /** 修改报价状态 */
            eiaOffer.offerState = GeneConstants.CONTRACT_STATE_SIGNED
            eiaOffer.ifSub = ifSub
            eiaOffer.properties = params
            if (params.contractTypeStr) {
                eiaOffer.contractType = params.contractTypeDrop
                eiaOffer.contractTypeCode = params.contractTypeStr
            }
            def eiaTask = EiaTask.findByIdAndIfDel(params.long("taskId"), false)
            if (eiaTask) {
                eiaOffer.taskNo = eiaTask.taskNo
                eiaOffer.taskName = eiaTask.taskName
            }
            eiaOffer.save(flush: true, failOnError: true)
            def eiaContract = new EiaContract()
            eiaOffer.ifSub = null
            eiaContract.properties = eiaOffer.properties
            eiaContract.eiaOfferId = eiaOffer.id
            eiaContract.ifShow = true
            eiaContract.taskId = eiaTask?.id
            eiaContract.taskName = eiaTask?.taskName
            eiaContract.taskNo = eiaTask?.taskNo
            eiaContract.taskAssignUser = eiaTask?.taskAssignUser
            eiaContract.contractName = eiaOffer.offerName
            eiaContract.contractMoney = eiaOffer.offerMoney
            eiaContract.contractDate = DateTransTools.getFormatDateD(params.contractDate)
            eiaContract.ifSub = false
            eiaContract.contractState = GeneConstants.CONTRACT_STATE_SIGNED
            eiaContract.inputDept = session.staff.orgName
            eiaContract.inputDeptCode = session.staff.orgCode
            eiaContract.inputDeptId = Long.parseLong(session.staff.orgId)
            eiaContract.inputUser = session.staff.staffName
            eiaContract.inputUserId = Long.parseLong(session.staff.staffId)
            if (eiaContract.save(flush: true, failOnError: true)) {
                /** 生成合同编号 */
                def contractNo = this.getContractNo(sdf.format(eiaContract.contractDate), eiaContract.id)
                eiaContract.contractNo = contractNo
                eiaContract.save(flush: true, failOnError: true)
            }
        }
    }
    /**
     * 把需要的字段组装成list
     * @param map
     * @param ndProp
     * @return
     */
    def combNeedMap(Object map, List<String> ndProp) {
        def currMap = [:]
        ndProp.each {
            if (map) {
                if (map.getAt(it)) {
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
     * 报价印章申请审批单
     */
    def offerSealApplyApprove(params) {
        def tableName = params.tableName
        def tableNameId = params.long('tableNameId')
        def eiaOfferId = tableNameId
        def eiaOffer = EiaOffer.findById(eiaOfferId)
        if (eiaOffer) {
            def dataMap = eiaEnvProjectService.convertToMap(eiaOffer.properties)
            /** 经办人，即合同录入人 */
            def inputUser
            def authParam = [:]
            authParam.staffId = String.valueOf(eiaOffer.inputUserId)
            def authJson = HttpConnectTools.getResponseJson(HttpUrlConstants.SINGLE_EIA_AUTH_STAFF_INFO, authParam)
            if (authJson) {
                if(JsonHandler.jsonToMap(authJson).code == HttpMesConstants.CODE_OK){
                    def staff = JsonHandler.jsonToMap(authJson).data[0]
                    if (staff.signImagePath) {
                        inputUser = GeneConstants.AUTH_FILE_URL_PATH + staff.signImagePath
                    }
                }
            }
            dataMap.put("inputUser", inputUser)
            if (tableNameId) {
                def eiaWorkFlowBusi = EiaWorkFlowBusi.findByTableNameAndTableNameIdAndWorkFlowStateInListAndIfDel(tableName, tableNameId, [WorkFlowConstants.WORKFLOW_UNDER_WAY,WorkFlowConstants.WORKFLOW_START,WorkFlowConstants.WORKFLOW_END], false)
                /** 申请时间 */
                def applyDate = eiaWorkFlowBusi?.dateCreated.format("yyyy-MM-dd")
                dataMap.put("applyDate", applyDate)
                if (eiaWorkFlowBusi) {
                    /** 部门经理 */
                    def bmshBusiLog = EiaWorkFlowBusiLog.findAllByEiaWorkFlowBusiIdAndNodesCodeAndWorkFlowStateAndIfDel(eiaWorkFlowBusi.id, WorkFlowConstants.CONTRACT_WORK_FLOW_NODE_BMSH, WorkFlowConstants.WORKFLOW_UNDER_WAY, false, [sort: "id", order: "desc",])
                    if (bmshBusiLog) {
                        def deptManagerId = bmshBusiLog.get(0)?.updateUserId
                        def deptManager
                        def param = [:]
                        param.staffId = String.valueOf(deptManagerId)
                        def staffJson = HttpConnectTools.getResponseJson(HttpUrlConstants.SINGLE_EIA_AUTH_STAFF_INFO, param)
                        if (staffJson) {
                            if(JsonHandler.jsonToMap(staffJson).code == HttpMesConstants.CODE_OK){
                                def staff = JsonHandler.jsonToMap(staffJson).data[0]
                                if (staff.signImagePath) {
                                    deptManager = GeneConstants.AUTH_FILE_URL_PATH + staff.signImagePath
                                }
                            }
                        }
                        dataMap.put("deptManager", deptManager)
                    }
                    /** 总经理 */
                    def zjlshBusiLog = EiaWorkFlowBusiLog.findAllByEiaWorkFlowBusiIdAndNodesCodeAndWorkFlowStateAndIfDel(eiaWorkFlowBusi.id, WorkFlowConstants.CERT_WORK_FLOW_NODE_ZJLSH, WorkFlowConstants.WORKFLOW_UNDER_WAY, false, [sort: "id", order: "desc",])
                    if (zjlshBusiLog) {
                        def topManagerId = zjlshBusiLog.get(0)?.updateUserId
                        def topManager
                        def param = [:]
                        param.staffId = String.valueOf(topManagerId)
                        def staffJson = HttpConnectTools.getResponseJson(HttpUrlConstants.SINGLE_EIA_AUTH_STAFF_INFO, param)
                        if (staffJson) {
                            if(JsonHandler.jsonToMap(staffJson).code == HttpMesConstants.CODE_OK){
                                def staff = JsonHandler.jsonToMap(staffJson).data[0]
                                if (staff.signImagePath) {
                                    topManager = GeneConstants.AUTH_FILE_URL_PATH + staff.signImagePath
                                }
                            }
                        }
                        dataMap.put("topManager", topManager)
                    }
                    return dataMap
                }
            }
        }
    }
}
