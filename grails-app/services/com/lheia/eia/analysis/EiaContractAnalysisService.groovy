package com.lheia.eia.analysis

import com.lheia.eia.common.FuncConstants
import com.lheia.eia.common.GeneConstants
import com.lheia.eia.common.HttpMesConstants
import com.lheia.eia.common.HttpUrlConstants
import com.lheia.eia.common.WorkFlowConstants
import com.lheia.eia.contract.EiaContract
import com.lheia.eia.contract.EiaOffer
import com.lheia.eia.project.EiaProject
import com.lheia.eia.project.EiaProjectPlanItem
import com.lheia.eia.tools.HttpConnectTools
import com.lheia.eia.tools.JsonHandler
import com.lheia.eia.workflow.EiaWorkFlowBusi
import grails.gorm.transactions.Transactional

import java.math.RoundingMode
import java.text.SimpleDateFormat

@Transactional
class EiaContractAnalysisService {

    /**
     * 合同列表显示信息
     * @param params
     * @param session
     * @return
     */
    def eiaContractQueryPage(params, session) {
        /**
         * 当前年份
         */
        Calendar cale = null;
        cale = Calendar.getInstance();
        int year = cale.get(Calendar.YEAR);
        /** 计时 **/
        long startTime = new Date().time
        def status = params.status
        def contractDate = params.contractDate
        def startDate, endDate
        def sdf = new SimpleDateFormat("yyyy-MM-dd")
        if (params.deptStartDate && params.deptStartDate != 'undefined') {
            startDate = sdf.parse(params.deptStartDate)
        } else {
            startDate = sdf.parse(year + "-01-01")
        }

        if (params.deptEndDate && params.deptEndDate != 'undefined') {
            endDate = sdf.parse(params.deptEndDate)
        } else {
            endDate = sdf.parse(year + "-12-31")
        }
        int page
        int limit
        if (params.page && params.limit) {
            page = params.int('page') - 1
            limit = params.int('limit')
        }
        def contractBusiListFin
        def contractBusiListNf
        /**
         * 合同流程已完成
         */
        if (params.contractBusi == 'end') {
            contractBusiListFin = EiaWorkFlowBusi.createCriteria().list() {

                eq('tableName', 'EiaContract')
                eq('ifDel', false)
                eq('workFlowState', WorkFlowConstants.WORKFLOW_END)
                if (params.inputUserId) {
                    eq('inputUserId', params.long('inputUserId'))
                }
                if (params.inputDeptId) {
                    eq('inputDeptId', params.long('inputDeptId'))
                }
            }.tableNameId
        }

        if (params.contractBusi == 'doing') {
            /**
             * 合同流程未完成
             */
            contractBusiListNf = EiaWorkFlowBusi.createCriteria().list() {
                eq('tableName', 'EiaContract')
                eq('ifDel', false)
                ne('workFlowState', WorkFlowConstants.WORKFLOW_END)
                ne('workFlowState', WorkFlowConstants.WORKFLOW_HALT)
                if (params.inputUserId) {
                    eq('inputUserId', params.long('inputUserId'))
                }
                if (params.inputDeptId) {
                    eq('inputDeptId', params.long('inputDeptId'))
                }
            }.tableNameId
        }

        def eiaContractList = EiaContract.createCriteria().list(max: limit, offset: page * limit) {
            def contractName = params.contractName
            eq('ifDel', false)
            eq('ifShow', true)
            isNotNull("contractNo")
            if (contractName && !"合同名称,合同编号,录入部门,录入人".equals(contractName)) {
                or{
                    like("contractName", "%" + contractName + "%")
                    like("contractNo", "%" + contractName + "%")
                }
            }
            if(params.contractName){

            }
            if (params.inputUserId) {
                eq('inputUserId', params.long('inputUserId'))
            }
            if (params.inputDeptId) {
                eq('inputDeptId', params.long('inputDeptId'))
            }
            if (params.contractBusi == 'end') {
                if (contractBusiListFin) {
                    inList('id', contractBusiListFin)
                }
            }
            if (params.contractBusi == 'doing') {
                if (contractBusiListNf) {
                    inList('id', contractBusiListNf)
                }
            }
            ge('contractDate', startDate)
            le('contractDate', endDate)

            order("id", "desc")
        }

        def eiaContractData = []
        eiaContractList.each {
            def map = [:]
            map.id = it.id
            map.contractName = it.contractName
            map.contractNo = it.contractNo
            map.contractType = it.contractType
            map.taskName = it.taskName
            map.taskNo = it.taskNo
            map.ifSub = it.ifSub
            def ifShow = "否"
            if (it.ifShow) {
                ifShow = "是"
            }
            map.ifShow = ifShow
            SimpleDateFormat formaContractDate = new SimpleDateFormat("yyyy-MM-dd");
            if (it.contractDate) {
                String contractDateCurr = formaContractDate.format(it.contractDate);
                map.contractDate = contractDateCurr
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
            if (it?.reportFees > 0) {
                reportFeeSum = it.reportFees ?: 0
            } else {
                /**
                 * 其他费用
                 */
                if (it.otherFee) {
                    otherFee = it.otherFee ?: 0
                }
                /**
                 * 发行前认证费
                 */
                if (it.preIssCertFee) {
                    preIssCertFee = it.preIssCertFee ?: 0
                }
                /**
                 * 存续期认证费
                 */
                if (it.preSurvCertFee) {
                    preSurvCertFee = it.preSurvCertFee ?: 0
                }
                /**
                 * 认证服务费
                 */
                if (it.certServeFee) {
                    certServeFee = it.certServeFee ?: 0
                }
            }
            /**
             * 计算利润率
             */
            BigDecimal preIssCertFeeSum = new BigDecimal(preIssCertFee ? preIssCertFee : 0);
            BigDecimal preSurvCertFeeSum = new BigDecimal(preSurvCertFee ? preSurvCertFee : 0);
            BigDecimal certServeFeeSum = new BigDecimal(certServeFee ? certServeFee : 0);
            BigDecimal otherFeeSum = new BigDecimal(otherFee ? otherFee : 0);
            serSurPreSum = preIssCertFeeSum + preSurvCertFeeSum + certServeFeeSum + otherFeeSum
            BigDecimal profitMarginMin = serSurPreSum + reportFeeSum
            if (profitMarginMin && it?.contractMoney > 0) {
                map.profitMargin = profitMarginMin.divide(it?.contractMoney, 4, RoundingMode.HALF_UP) ?: 0
            } else {
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
     * 部门金额统计，查询全部
     * @param params
     * @return
     */
    def getTotalMoney(params, session) {
        /**
         * 当前年份
         */
        Calendar cale = null;
        cale = Calendar.getInstance();
        int year = cale.get(Calendar.YEAR);
        /** 计时 **/
        long startTime = new Date().time
        def status = params.status
        def contractDate = params.contractDate
        def startDate, endDate
        def sdf = new SimpleDateFormat("yyyy-MM-dd")
        if (params.deptStartDate) {
            startDate = sdf.parse(params.deptStartDate)
        } else {
            startDate = sdf.parse(year + "-01-01")
        }

        if (params.deptEndDate) {
            endDate = sdf.parse(params.deptEndDate)
        } else {
            endDate = sdf.parse(year + "-12-31")
        }
        def resList = []
        def orgId = String.valueOf(params.id)
        def orgCode = session.staff.orgCode
        def ifRoot = false
        if (!session?.staff?.funcCode?.contains(FuncConstants.EIA_YWCX_HTCX_VIEWALL)) {
            /**
             * 查看本部门合同数据
             */
            if (session?.staff?.funcCode?.contains(FuncConstants.EIA_YWCX_HTCX_VIEWDEPT)) {
                if (status == "root") {
                    orgId = String.valueOf(session?.staff?.orgId)
                    /**
                     * 判断是否为分公司
                     */
                    if (orgCode?.contains("FGS")) {
                        /**
                         * 分公司只能看自己的，不能点击其他的，所以ifRoot为true代表不能往上获取其他数据
                         */
                        status = "close"
                        ifRoot = true
                    } else {
                        status = "open"
                        ifRoot = true
                    }

                } else {
                    if (status == 'close') {
                        ifRoot = true
                    } else {
                        ifRoot = false
                    }
                }

            }
            if (session?.staff?.funcCode?.contains(FuncConstants.EIA_YWCX_XMCX_VIEWSELF)) {
                status = "staff"
            }
        } else {
            if (status == "root") {
                orgId = String.valueOf(GeneConstants.ORG_ROOT)
                status = "open"
            }
        }
        def ifOrg = true
        if (status == "close") {
            def resMap = JsonHandler.jsonToMap(HttpConnectTools.getResponseJson(HttpUrlConstants.EIA_GET_PARENT_ORG, [orgId: orgId]))
            if (resMap.code == HttpMesConstants.CODE_OK) {
                def parentNode = resMap.data
                if (Long.valueOf(parentNode.id) == GeneConstants.ORG_ROOT) {
                    ifRoot = true
                }
                parentNode.ifRoot = ifRoot
//                parentNode.ifRoot = (Long.valueOf(parentNode.id) == GeneConstants.ORG_ROOT)
//                resList << getOrgTotalMoney(parentNode, startDate, endDate)
                resMap = JsonHandler.jsonToMap(HttpConnectTools.getResponseJson(HttpUrlConstants.EIA_GET_CHILD_ORG, [orgId: String.valueOf(parentNode.id)]))
                if (resMap.code == HttpMesConstants.CODE_OK) {
                    def childNodeList = resMap.data
                    def combineList = []
                    combineList << parentNode
                    childNodeList.each {
                        if (it.ifStaff) {
                            ifOrg = false
                        }
                        combineList << it
                    }
                    if (ifOrg) {
                        resList = getOrgTotalMoney(combineList, startDate, endDate)
                    } else {
                        resList = getStaffTotalMoney(combineList, startDate, endDate, parentNode.id)
                    }
                }
            }
        } else if (status == "open") {
            def resMap = JsonHandler.jsonToMap(HttpConnectTools.getResponseJson(HttpUrlConstants.EIA_GET_THIS_ORG, [orgId: orgId]))
            if (resMap.code == HttpMesConstants.CODE_OK) {
                def parentNode = resMap.data
                if (Long.valueOf(parentNode.id) == GeneConstants.ORG_ROOT) {
                    ifRoot = true
                }
                parentNode.ifRoot = ifRoot
//                resList << getOrgTotalMoney(parentNode, startDate, endDate)
                resMap = JsonHandler.jsonToMap(HttpConnectTools.getResponseJson(HttpUrlConstants.EIA_GET_CHILD_ORG, [orgId: String.valueOf(parentNode.id)]))
                if (resMap.code == HttpMesConstants.CODE_OK) {
                    def childNodeList = resMap.data
                    def combineList = []
                    combineList << parentNode
                    childNodeList.each {
                        if (it.ifStaff) {
                            ifOrg = false
                        }
                        combineList << it
                    }
                    if (ifOrg) {
                        resList = getOrgTotalMoney(combineList, startDate, endDate)
                    } else {
                        resList = getStaffTotalMoney(combineList, startDate, endDate, parentNode.id)
                    }

                }
            }
        } else if (status == "staff") {
            ifOrg = false
            def childNodeList
            def parentOrgId
            def resMap = JsonHandler.jsonToMap(HttpConnectTools.getResponseJson(HttpUrlConstants.SINGLE_EIA_AUTH_STAFF_INFO, [staffId: String.valueOf(session.staff.staffId)]))
            if (resMap.code == HttpMesConstants.CODE_OK) {
                resMap.data[0].ifStaff = true
                childNodeList = resMap.data
                parentOrgId = resMap.data[0].orgId
            }
            if (ifOrg) {
                resList = getOrgTotalMoney(childNodeList, startDate, endDate)
            } else {
                resList = getStaffTotalMoney(childNodeList, startDate, endDate, parentOrgId)
            }
        }
        long endTime = new Date().time
        return resList
    }


    /***
     * 返回人员业务总额统计
     * @param parentNode
     * @return
     */
    def getStaffTotalMoney(childNodeList, startDate, endDate, deptId) {
        /**
         * 合同列表统计
         */
        def contractList = EiaContract.createCriteria().list() {
            eq('ifDel', false)
            eq('ifShow', true)
            isNotNull("contractNo")
            if (startDate) {
                ge('contractDate', startDate)
            }
            if (endDate) {
                le('contractDate', endDate)
            }
            eq('inputDeptId', Long.valueOf(deptId))
            projections {
                groupProperty('inputUserId')
                sum('reportFees')
                sum('serveFee')
                sum('preIssCertFee')
                sum('preSurvCertFee')
                sum('certServeFee')
                sum('otherFee')
                sum('contractMoney')
                count()
            }
        }

        /**
         * 合同列表全
         */
        def contractListAll = EiaContract.createCriteria().list() {
            eq('ifDel', false)
            eq('ifShow', true)
            isNotNull("contractNo")
            if (startDate) {
                ge('contractDate', startDate)
            }
            if (endDate) {
                le('contractDate', endDate)
            }
            eq('inputDeptId', Long.valueOf(deptId))
        }.id

        /**
         * 合同流程已完成
         */
        def contractBusiListFin = EiaWorkFlowBusi.createCriteria().list() {
            eq('tableName', 'EiaContract')
            eq('ifDel', false)
            eq('workFlowState', WorkFlowConstants.WORKFLOW_END)
            eq('inputDeptId', Long.valueOf(deptId))
            if (contractListAll) {
                inList('tableNameId', contractListAll)
            }
            projections {
                groupProperty('inputUserId')
                count()
            }
        }
        /**
         * 合同流程未完成
         */
        def contractBusiListNf = EiaWorkFlowBusi.createCriteria().list() {
            eq('tableName', 'EiaContract')
            eq('ifDel', false)
            ne('workFlowState', WorkFlowConstants.WORKFLOW_END)
            ne('workFlowState', WorkFlowConstants.WORKFLOW_HALT)
            eq('inputDeptId', Long.valueOf(deptId))
            if (contractListAll) {
                inList('tableNameId', contractListAll)
            }
            projections {
                groupProperty('inputUserId')

                count()
            }
        }
        /**
         * 报价列表
         */
        def offerMoneyList = EiaOffer.createCriteria().list() {
            eq("ifDel", false)
            if (startDate) {
                ge("offerDate", startDate)
            }
            if (endDate) {
                le("offerDate", endDate)
            }
            eq("offerState", GeneConstants.CONTRACT_STATE_NOT_SIGNED)
            eq('inputDeptId', Long.valueOf(deptId))
            projections {
                groupProperty('inputUserId')

                sum('offerMoney')
            }
        }
        /**
         * 遍历合同list,添加key放入map中,组合成新的list
         */
        def resList = []
        contractList.each { it ->
            def resMap = [:]
            resMap.inputUserId = it[0]
//            resMap.inputDeptId = it[1]
            resMap.reportFees = it[1]
            resMap.serveFee = it[2]
            resMap.preIssCertFee = it[3]
            resMap.preSurvCertFee = it[4]
            resMap.certServeFee = it[5]
            resMap.otherFee = it[6]
            resMap.contractMoney = it[7]
            resMap.contractCount = it[8]
            resList << resMap
        }
        resList.each { it ->
            def signedFlag = false
            contractBusiListFin.each { cf ->
                if (it.inputUserId == cf[0]) {
                    signedFlag = true
                    it.finCount = cf[1]
                }
            }
            if (!signedFlag) {
                it.finCount = 0
            }
            def unsignedFlag = false
            contractBusiListNf.each { cn ->
                if (it.inputUserId == cn[0]) {
                    unsignedFlag = true
                    it.unfinCount = cn[1]
                }
            }
            if (!unsignedFlag) {
                it.unfinCount = 0
            }
            def offerFlag = false
            offerMoneyList.each { ol ->
                if (it.inputUserId == ol[0]) {
                    offerFlag = true
                    it.offerMoney = ol[1] ? ol[1] : BigDecimal.valueOf(0)
                    it.contractMoneySum = it.offerMoney + it.contractMoney
                }
            }
            if (!offerFlag) {
                it.contractMoneySum = it.contractMoney
                it.offerMoney = 0
            }
        }
        def responseList = []
        for (int i = 0; i < childNodeList.size(); i++) {
            def currNode = childNodeList.get(i)
            def responseMap = [:]
            BigDecimal reportFeesS = 0 //进行累加
            BigDecimal serveFeeS = 0
            BigDecimal preIssCertFeeS = 0
            BigDecimal preSurvCertFeeS = 0
            BigDecimal certServeFeeS = 0
            BigDecimal otherFeeS = 0
            BigDecimal contractMoneyS = 0
            BigDecimal contractMoneySumS = 0
            BigDecimal offerMoneyS = 0
            def finCountS = 0
            def unfinCountS = 0
            def contractCountS = 0
            if (currNode.orgBusiName) {
//                def orgBusiNameList = Arrays.asList(currNode.orgBusiName.split(';'))  //这是List<String>
                resList.each {
                    reportFeesS += it.reportFees ? it.reportFees : 0
                    serveFeeS += it.serveFee ? it.serveFee : 0
                    preIssCertFeeS += it.preIssCertFee ? it.preIssCertFee : 0
                    preSurvCertFeeS += it.preSurvCertFee ? it.preSurvCertFee : 0
                    certServeFeeS += it.certServeFee ? it.certServeFee : 0
                    otherFeeS += it.otherFee ? it.otherFee : 0
                    contractMoneyS += it.contractMoney ? it.contractMoney : 0
                    contractMoneySumS += it.contractMoneySum ? it.contractMoneySum : 0
                    offerMoneyS += it.offerMoney ? it.offerMoney : 0
                    finCountS += it.finCount
                    unfinCountS += it.unfinCount
                    contractCountS += it.contractCount

                }
            } else {
                resList.each {
                    if (it.inputUserId == Long.valueOf(currNode.id)) {
                        reportFeesS += it.reportFees ? it.reportFees : 0
                        serveFeeS += it.serveFee ? it.serveFee : 0
                        preIssCertFeeS += it.preIssCertFee ? it.preIssCertFee : 0
                        preSurvCertFeeS += it.preSurvCertFee ? it.preSurvCertFee : 0
                        certServeFeeS += it.certServeFee ? it.certServeFee : 0
                        otherFeeS += it.otherFee ? it.otherFee : 0
                        contractMoneyS += it.contractMoney ? it.contractMoney : 0
                        contractMoneySumS += it.contractMoneySum ? it.contractMoneySum : 0
                        offerMoneyS += it.offerMoney ? it.offerMoney : 0
                        finCountS += it.finCount
                        unfinCountS += it.unfinCount
                        contractCountS += it.contractCount
                    }
                }
            }

            responseMap.contractMoneySum = contractMoneySumS
            responseMap.contractMoney = contractMoneyS
            responseMap.offerMoney = offerMoneyS
            responseMap.finCount = finCountS
            responseMap.unfinCount = unfinCountS
            responseMap.contractCount = contractCountS
            responseMap.parentNodeId = deptId
            responseMap.putAll(currNode)
            /**
             * 计算利润率
             */
            BigDecimal profitMarginMin = reportFeesS + preIssCertFeeS + preSurvCertFeeS + certServeFeeS + otherFeeS
            if (profitMarginMin && contractMoneyS > 0) {
                responseMap.profitMargin = profitMarginMin.divide(contractMoneyS, 4, RoundingMode.HALF_UP) ?: 0
            } else {
                responseMap.profitMargin = new BigDecimal(0);
            }
            responseList << responseMap
        }
        responseList
    }


    /***
     * 返回组织统计总额
     * @param parentNode
     * @return
     */
    def getOrgTotalMoney(childNodeList, startDate, endDate) {
        /**
         * 查询contract表，按机构分组
         */
        def contractList = EiaContract.createCriteria().list() {
            eq('ifDel', false)
            eq('ifShow', true)
            isNotNull("contractNo")
            if (startDate) {
                ge('contractDate', startDate)
            }
            if (endDate) {
                le('contractDate', endDate)
            }
            projections {
                groupProperty('inputDeptId')
                sum('reportFees')
                sum('serveFee')
                sum('preIssCertFee')
                sum('preSurvCertFee')
                sum('certServeFee')
                sum('otherFee')
                sum('contractMoney')
                count()
            }
        }

        /**
         * 合同列表全
         */
        def contractListAll = EiaContract.createCriteria().list() {
            eq('ifDel', false)
            eq('ifShow', true)
            isNotNull("contractNo")
            if (startDate) {
                ge('contractDate', startDate)
            }
            if (endDate) {
                le('contractDate', endDate)
            }
        }.id

        /**
         * 查询流程表已完成的，按机构分组
         */
        def contractBusiListFin = EiaWorkFlowBusi.createCriteria().list() {
            eq('tableName', 'EiaContract')
            eq('ifDel', false)
            eq('workFlowState', WorkFlowConstants.WORKFLOW_END)
            inList('tableNameId', contractListAll)
            projections {
                groupProperty('inputDeptId')
                count()
            }
        }
        /**
         * 查询流程表未完成的，按机构分组
         */
        def contractBusiListNf = EiaWorkFlowBusi.createCriteria().list() {
            eq('tableName', 'EiaContract')
            eq('ifDel', false)
            ne('workFlowState', WorkFlowConstants.WORKFLOW_END)
            ne('workFlowState', WorkFlowConstants.WORKFLOW_HALT)
            inList('tableNameId', contractListAll)

            projections {
                groupProperty('inputDeptId')
                count()
            }
        }
        /**
         * 查询offer表，按机构分组
         */
        def offerMoneyList = EiaOffer.createCriteria().list() {
            eq("ifDel", false)
            if (startDate) {
                ge("offerDate", startDate)
            }
            if (endDate) {
                le("offerDate", endDate)
            }
            eq("offerState", GeneConstants.CONTRACT_STATE_NOT_SIGNED)
            projections {
                groupProperty('inputDeptId')
                sum('offerMoney')
            }
        }
        /**
         * 把合同表的list逐条放入map中，并将map放入list中
         */
        def resList = []
        contractList.each { it ->
            def resMap = [:]
            resMap.inputDeptId = it[0]
            resMap.reportFees = it[1]
            resMap.serveFee = it[2]
            resMap.preIssCertFee = it[3]
            resMap.preSurvCertFee = it[4]
            resMap.certServeFee = it[5]
            resMap.otherFee = it[6]
            resMap.contractMoney = it[7]
            resMap.contractCount = it[8]
            resList << resMap
        }
        /**
         * 根据inputDeptId匹配，把合同完成情况，offer金额都放入新组合的list中
         */
        resList.each { it ->
            def signedFlag = false
            contractBusiListFin.each { cf ->
                if (it.inputDeptId == cf[0]) {
                    signedFlag = true
                    it.finCount = cf[1]
                }
            }
            if (!signedFlag) {
                it.finCount = 0
            }
            def unsignedFlag = false
            contractBusiListNf.each { cn ->
                if (it.inputDeptId == cn[0]) {
                    unsignedFlag = true
                    it.unfinCount = cn[1]
                }
            }
            if (!unsignedFlag) {
                it.unfinCount = 0
            }
            def offerFlag = false
            offerMoneyList.each { ol ->
                if (it.inputDeptId == ol[0]) {
                    offerFlag = true
                    it.offerMoney = ol[1] ? ol[1] : BigDecimal.valueOf(0)
                    it.contractMoneySum = it.offerMoney + it.contractMoney
                }
            }
            if (!offerFlag) {
                it.contractMoneySum = it.contractMoney
                it.offerMoney = 0
            }
        }
        /**————————开始循环传进来的list并累加————————**/
        def responseList = []
        for (int i = 0; i < childNodeList.size(); i++) {
            def responseMap = [:]
            BigDecimal reportFeesS = 0 //进行累加
            BigDecimal serveFeeS = 0
            BigDecimal preIssCertFeeS = 0
            BigDecimal preSurvCertFeeS = 0
            BigDecimal certServeFeeS = 0
            BigDecimal otherFeeS = 0
            BigDecimal contractMoneyS = 0
            BigDecimal contractMoneySumS = 0
            BigDecimal offerMoneyS = 0
            def finCountS = 0
            def unfinCountS = 0
            def contractCountS = 0
            /**
             * 循环结果集，如果childNodeList.get(i).orgBusiName.convertlist包含input_dept_id;则
             * 把这条的值累加起来
             */
            def currNode = childNodeList.get(i)
            def orgBusiNameList = Arrays.asList(currNode.orgBusiName.split(';'))  //这是List<String>
            resList.each {
                if (orgBusiNameList.contains(String.valueOf(it.inputDeptId))) {
                    reportFeesS += it.reportFees ? it.reportFees : 0
                    serveFeeS += it.serveFee ? it.serveFee : 0
                    preIssCertFeeS += it.preIssCertFee ? it.preIssCertFee : 0
                    preSurvCertFeeS += it.preSurvCertFee ? it.preSurvCertFee : 0
                    certServeFeeS += it.certServeFee ? it.certServeFee : 0
                    otherFeeS += it.otherFee ? it.otherFee : 0
                    contractMoneyS += it.contractMoney ? it.contractMoney : 0
                    contractMoneySumS += it.contractMoneySum ? it.contractMoneySum : 0
                    offerMoneyS += it.offerMoney ? it.offerMoney : 0
                    finCountS += it.finCount
                    unfinCountS += it.unfinCount
                    contractCountS += it.contractCount
                }
                responseMap.contractMoneySum = contractMoneySumS
                responseMap.contractMoney = contractMoneyS
                responseMap.offerMoney = offerMoneyS
                responseMap.finCount = finCountS
                responseMap.unfinCount = unfinCountS
                responseMap.contractCount = contractCountS
                responseMap.putAll(currNode)
                /**
                 * 计算利润率
                 */
                BigDecimal profitMarginMin = reportFeesS + preIssCertFeeS + preSurvCertFeeS + certServeFeeS + otherFeeS
                if (profitMarginMin && contractMoneyS > 0) {
                    responseMap.profitMargin = profitMarginMin.divide(contractMoneyS, 4, RoundingMode.HALF_UP) ?: 0
                } else {
                    responseMap.profitMargin = new BigDecimal(0);
                }
            }
            responseList << responseMap
        }
        return responseList
    }


}
