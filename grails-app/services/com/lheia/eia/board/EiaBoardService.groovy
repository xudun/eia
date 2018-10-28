package com.lheia.eia.board

import com.lheia.eia.client.EiaClient
import com.lheia.eia.common.FuncConstants
import com.lheia.eia.common.GeneConstants
import com.lheia.eia.common.HttpMesConstants
import com.lheia.eia.common.HttpUrlConstants
import com.lheia.eia.common.WorkFlowConstants
import com.lheia.eia.config.EiaDomainCode
import com.lheia.eia.contract.EiaContract
import com.lheia.eia.contract.EiaOffer
import com.lheia.eia.finance.EiaAccountExpect
import com.lheia.eia.finance.EiaIncomeOut
import com.lheia.eia.finance.EiaInvoice
import com.lheia.eia.project.EiaProject
import com.lheia.eia.project.EiaProjectPlanItem
import com.lheia.eia.task.EiaTask
import com.lheia.eia.tools.HttpConnectTools
import com.lheia.eia.tools.JsonHandler
import com.lheia.eia.workflow.EiaWorkFlowBusi
import grails.gorm.transactions.Transactional
import groovy.sql.Sql

import java.math.RoundingMode
import java.text.SimpleDateFormat

@Transactional
class EiaBoardService {
    def dataSource
    /**
     * 部门统计
     */
    def getDeptCount() {
        /*获取组织架构*/
        def param = [:]
        def eiaOrgStaffInfo = JsonHandler.jsonToMap(HttpConnectTools.getResponseJson(HttpUrlConstants.EIA_AUTH_STAFF_INFO, param))[0]
        this.deptLv = 0
        getStaffCountMoney(eiaOrgStaffInfo)
        getDeptCountMoney(eiaOrgStaffInfo)
        return eiaOrgStaffInfo
    }
    private def deptLv = 0

    /**
     * 人员金额统计
     * @param eiaOrgStaffInfo
     * @return
     */
    def getStaffCountMoney(eiaOrgStaffInfo) {
        if (eiaOrgStaffInfo.children.size() > 0 && !eiaOrgStaffInfo.staffName) {
            eiaOrgStaffInfo.children.each {
                getStaffCountMoney(it)
            }
        } else {
            /**查询合同总额**/
            def contractMoney = EiaContract.findAllByInputUserIdAndIfDel(Long.valueOf(eiaOrgStaffInfo.id), false).contractMoney.sum()
            eiaOrgStaffInfo.contractMoney = contractMoney ? contractMoney : 0
            /**查询项目总额**/
            def projectMoney = EiaProject.findAllByInputUserIdAndIfDel(Long.valueOf(eiaOrgStaffInfo.id), false).projectMoney.sum()
            eiaOrgStaffInfo.projectMoney = projectMoney ? projectMoney : 0
            /**查询报价总额**/
            def offerMoney = EiaOffer.findAllByInputUserIdAndIfDel(Long.valueOf(eiaOrgStaffInfo.id), false).offerMoney.sum()
            eiaOrgStaffInfo.offerMoney = offerMoney ? offerMoney : 0
        }
    }

    /**
     * 部门金额计算
     */
    def getDeptCountMoney(eiaOrgStaffInfo) {
        if (eiaOrgStaffInfo.children.size() > 0) {
            for (def i = 0; i < eiaOrgStaffInfo.children.size(); i++) {
                if (eiaOrgStaffInfo.children[i].contractMoney == null) {
                    getDeptCountMoney(eiaOrgStaffInfo.children[i]);
                }
            }
            eiaOrgStaffInfo.contractMoney = BigDecimal.valueOf(0)
            eiaOrgStaffInfo.projectMoney = BigDecimal.valueOf(0)
            eiaOrgStaffInfo.offerMoney = BigDecimal.valueOf(0)
            eiaOrgStaffInfo.children.each {
                eiaOrgStaffInfo.contractMoney += it.contractMoney
                eiaOrgStaffInfo.projectMoney += it.projectMoney
                eiaOrgStaffInfo.offerMoney += it.offerMoney
            }
        } else {
            eiaOrgStaffInfo.contractMoney = BigDecimal.valueOf(0)
            eiaOrgStaffInfo.projectMoney = BigDecimal.valueOf(0)
            eiaOrgStaffInfo.offerMoney = BigDecimal.valueOf(0)
        }
    }

    /**
     * 任务列表数据显示
     */
    def eiaTaskQuery(session) {
        def eiaTaskList = EiaTask.createCriteria().list() {
            eq("inputUserId", Long.valueOf(session.staff.staffId))
            eq("ifDel", false)
            order("id", "desc")
        }
        def dataMap = [:]
        dataMap.total = eiaTaskList.size()
        return dataMap
    }

    /**
     * 合同列表显示信息
     * @param params
     * @param session
     * @return
     */
    def eiaContractQueryPage(session) {
        def eiaContractList = EiaContract.createCriteria().list() {
            eq("inputUserId", Long.valueOf(session.staff.staffId))
            eq("ifDel", false)
            order("id", "desc")
        }
        def dataMap = [:]
        dataMap.total = eiaContractList.size()
        return dataMap
    }
    /**
     * 获取项目信息
     */
    def eiaProjectQueryPage(session) {
        def eiaProjectList = EiaProject.createCriteria().list() {
            eq("inputUserId", Long.valueOf(session.staff.staffId))
            eq("ifDel", false)
            order("id", "desc")
        }
        def dataMap = [:]
        dataMap.total = eiaProjectList.size()
        return dataMap
    }

    /**
     * 客户信息列表
     */
    def eiaClientQueryPage(session) {
        def eiaClientList = EiaClient.createCriteria().list() {
            and {
                eq("ifDel", false)
                eq("inputUserId", Long.parseLong(session?.staff.staffId))
            }
            order("id", "desc")
        }
        def dataMap = [:]
        dataMap.total = eiaClientList.size()
        return dataMap
    }




    /**
     * 父节点
     * @param nodeCode
     * @param startDate
     * @param endDate
     * @return
     */
    def getParentContractTotal(session,nodeCode, startDate, endDate, inputDept) {
        def resMap = [:]
        resMap.putAll(nodeCode.properties)
        def childCodeList = EiaDomainCode.findAllByParentCodeAndDomain(resMap.code, GeneConstants.CONTRACT_TYPE).code

        if (childCodeList) {
            def signMoney = EiaContract.createCriteria().list() {
                eq("ifDel", false)
                eq("ifShow", true)
                isNotNull("contractNo")
                if (startDate) {
                    ge("contractDate", startDate)
                }
                if (endDate) {
                    le("contractDate", endDate)
                }
                if (inputDept) {
                    like("inputDept", "%" + inputDept + "%")
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
                'in'("contractTypeCode", childCodeList)
                isNotNull("contractMoney")
            }.contractMoney.sum()
            resMap.signMoney = signMoney ? signMoney : BigDecimal.valueOf(0)
            def offerMoney = EiaOffer.createCriteria().list() {
                eq("ifDel", false)
                if (startDate) {
                    ge("offerDate", startDate)
                }
                if (endDate) {
                    le("offerDate", endDate)
                }
                if (inputDept) {
                    like("inputDept", "%" + inputDept + "%")
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
                eq("offerState", GeneConstants.CONTRACT_STATE_NOT_SIGNED)
                'in'("contractTypeCode", childCodeList)
                isNotNull("offerMoney")
            }.offerMoney.sum()
            resMap.offerMoney = offerMoney ? offerMoney : BigDecimal.valueOf(0)
            resMap.contractMoney = resMap.offerMoney + resMap.signMoney
        }
        resMap.ifParent = true
        return resMap
    }
    /**
     * 获取文件类型合同金额统计
     * @param nodeCode
     * @param startDate
     * @param endDate
     * @return
     */
    def getChildContractTotal(session,nodeCode, startDate, endDate, inputDept) {
        def resMap = [:]
        resMap.putAll(nodeCode.properties)
        def signMoney = EiaContract.createCriteria().list() {
            eq("ifDel", false)
            eq("ifShow", true)
            isNotNull("contractNo")
            if (startDate) {
                ge("contractDate", startDate)
            }
            if (endDate) {
                le("contractDate", endDate)
            }
            if (inputDept) {
                like("inputDept", "%" + inputDept + "%")
            }
            eq("contractTypeCode", nodeCode.code)
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
            isNotNull("contractMoney")
        }.contractMoney.sum()
        resMap.signMoney = signMoney ? signMoney : BigDecimal.valueOf(0)

        def offerMoney = EiaOffer.createCriteria().list() {
            eq("ifDel", false)
            if (startDate) {
                ge("offerDate", startDate)
            }
            if (endDate) {
                le("offerDate", endDate)
            }
            if (inputDept) {
                like("inputDept", "%" + inputDept + "%")
            }
            eq("offerState", GeneConstants.CONTRACT_STATE_NOT_SIGNED)
            eq("contractTypeCode", nodeCode.code)
            isNotNull("offerMoney")
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
        }.offerMoney.sum()
        resMap.offerMoney = offerMoney ? offerMoney : BigDecimal.valueOf(0)
        resMap.contractMoney = resMap.offerMoney + resMap.signMoney
        resMap.ifParent = false
        return resMap
    }

    /**
     * 业务类型金额统计
     * @param params
     * @return
     */
    def getBusiTypeTotalMoney(session,params) {
        /**
         * 当前年份
         */
        Calendar cale = null;
        cale = Calendar.getInstance();
        int year = cale.get(Calendar.YEAR);
        def status = params.status
        def contractDate = params.contractDate
        def startDate, endDate
        def sdf = new SimpleDateFormat("yyyy-MM-dd")
        def inputDept = params.inputDept
        if (params.busiStartDate) {
            startDate = sdf.parse(params.busiStartDate)
        } else {
            startDate = sdf.parse(year + "-01-01")
        }
        if (params.busiEndDate) {
            endDate = sdf.parse(params.busiEndDate)
        } else {
            endDate = sdf.parse(year + "-12-31")
        }
        def resList = []
        if (status == "close") {
            def nodeList = EiaDomainCode.findAllByParentCodeIsNullAndDomain(GeneConstants.CONTRACT_TYPE)
            def totalNode = [:]
            totalNode.codeDesc = "总计"
            totalNode.ifRoot = true
            totalNode.offerMoney = BigDecimal.valueOf(0)
            totalNode.contractMoney = BigDecimal.valueOf(0)
            totalNode.signMoney = BigDecimal.valueOf(0)
            resList << totalNode
            nodeList.each {
                def node = getParentContractTotal(session,it, startDate, endDate, inputDept)
                totalNode.offerMoney += node.offerMoney
                totalNode.contractMoney += node.contractMoney
                totalNode.signMoney += node.signMoney
                resList << node
            }
        } else if (status == "open") {
            def code = params.code
            def parentNode = EiaDomainCode.findByCodeAndDomain(code, GeneConstants.CONTRACT_TYPE)
            parentNode = getParentContractTotal(session,parentNode, startDate, endDate, inputDept)
            resList << parentNode
            def nodeList = EiaDomainCode.findAllByParentCodeAndDomain(code, GeneConstants.CONTRACT_TYPE)
            nodeList.each {
                def node = getChildContractTotal(session,it, startDate, endDate, inputDept)
                resList << node
            }
        }
        return resList
    }
    /**
     * 部门财务进出帐统计
     * @param params
     * @return
     */
    def getEiaInvoice(params) {
        /**
         * 当前年份
         */
        Calendar cale = null;
        cale = Calendar.getInstance();
        int year = cale.get(Calendar.YEAR);
        /**
         * 计时 **/
        long startTime = new Date().time
        def status = params.status
        def contractDate = params.contractDate
        def startDate, endDate
        def sdf = new SimpleDateFormat("yyyy-MM-dd")
        if (params.cissStartDate) {
            startDate = sdf.parse(params.cissStartDate)
        } else {
            startDate = sdf.parse(year + "-01-01")
        }
        if (params.cissEndDate) {
            endDate = sdf.parse(params.cissEndDate)
        } else {
            endDate = sdf.parse(year + "-12-31")
        }
        def resList = []
        def orgId = String.valueOf(params.id)
        if (status == "root") {
            orgId = String.valueOf(GeneConstants.ORG_ROOT)
            status = "open"
        }
        if (status == "close") {
            def resMap = JsonHandler.jsonToMap(HttpConnectTools.getResponseJson(HttpUrlConstants.EIA_GET_PARENT_ORG, [orgId: orgId]))
            if (resMap.code == HttpMesConstants.CODE_OK) {
                def parentNode = resMap.data
                parentNode.ifRoot = (Long.valueOf(parentNode.id) == GeneConstants.ORG_ROOT)
                resList << getOrgEiaInvoice(parentNode, startDate, endDate)
                resMap = JsonHandler.jsonToMap(HttpConnectTools.getResponseJson(HttpUrlConstants.EIA_GET_CHILD_ORG, [orgId: String.valueOf(parentNode.id)]))
                if (resMap.code == HttpMesConstants.CODE_OK) {
                    def childNodeList = resMap.data
                    childNodeList.each {
                        if (it.ifStaff) {
                            resList << getStaffEiaInvoice(it, startDate, endDate, parentNode.id)
                        } else {
                            resList << getOrgEiaInvoice(it, startDate, endDate)
                        }
                    }
                }
            }
        } else if (status == "open") {
            def resMap = JsonHandler.jsonToMap(HttpConnectTools.getResponseJson(HttpUrlConstants.EIA_GET_THIS_ORG, [orgId: orgId]))
            if (resMap.code == HttpMesConstants.CODE_OK) {
                def parentNode = resMap.data
                parentNode.ifRoot = (Long.valueOf(parentNode.id) == GeneConstants.ORG_ROOT)
                resList << getOrgEiaInvoice(parentNode, startDate, endDate)
                resMap = JsonHandler.jsonToMap(HttpConnectTools.getResponseJson(HttpUrlConstants.EIA_GET_CHILD_ORG, [orgId: String.valueOf(parentNode.id)]))
                if (resMap.code == HttpMesConstants.CODE_OK) {
                    def childNodeList = resMap.data
                    childNodeList.each {
                        if (it.ifStaff) {
                            resList << getStaffEiaInvoice(it, startDate, endDate, parentNode.id)
                        } else {
                            resList << getOrgEiaInvoice(it, startDate, endDate)
                        }
                    }
                }
            }
        }
        long endTime = new Date().time
        return resList
    }

    /***
     * 返回人员财务进出帐名字统计
     * @param parentNode
     * @return
     */
    def getStaffEiaInvoice(parentNode, startDate, endDate, deptId) {
        /**
         * 合同金额
         */
        def eiaContract = EiaContract.createCriteria().list() {
            eq("ifDel", false)
            eq("ifShow", true)
            isNotNull("contractNo")
            if (startDate) {
                ge("contractDate", startDate)
            }
            if (endDate) {
                le("contractDate", endDate)
            }
            eq("inputUserId", Long.valueOf(parentNode.id))
            eq("inputDeptId", Long.valueOf(deptId))
        }
        parentNode.contractMoney = eiaContract?.contractMoney.sum() ? eiaContract?.contractMoney.sum() : BigDecimal.valueOf(0)
        /**
         * 开票金额
         */
        def billMoney = EiaInvoice.createCriteria().list() {
            eq("ifDel", false)
            if (startDate) {
                ge("billDate", startDate)
            }
            if (endDate) {
                le("billDate", endDate)
            }
            eq("inputUserId", Long.valueOf(parentNode.id))
            eq("inputDeptId", Long.valueOf(deptId))
            eq("accountState", GeneConstants.INVOICE_CONFIRM_YES)
        }.billMoney.sum()
        parentNode.billMoney = billMoney ? billMoney : BigDecimal.valueOf(0)
        /**
         * 进账金额
         */
        def eiaContractIncome = EiaContract.createCriteria().list() {
            eq("ifDel", false)
            eq("ifShow", true)
            isNotNull("contractNo")
            eq("inputUserId", Long.valueOf(parentNode.id))
            eq("inputDeptId", Long.valueOf(deptId))
        }
        def invoiceIncomeSum = EiaIncomeOut.createCriteria().list() {
            eq("ifDel", false)
            if (startDate) {
                ge("noteIncomeDate", startDate)
            }
            if (endDate) {
                le("noteIncomeDate", endDate)
            }
            eq("costTypes", GeneConstants.INVOICE_TYPE_INCOME)
            eq("accountState", GeneConstants.INVOICE_CONFIRM_YES)
            if (eiaContractIncome) {
                'in'("contractId", eiaContractIncome?.id)
            } else {
                'in'("contractId", Long.valueOf(-1))
            }
        }.noteIncomeMoney.sum()
        parentNode.invoiceIncomeSum = invoiceIncomeSum ? invoiceIncomeSum : BigDecimal.valueOf(0)
        /**
         * 专家费
         */
        def expertFeeSum = EiaIncomeOut.createCriteria().list() {
            eq("ifDel", false)
            if (startDate) {
                ge("noteIncomeDate", startDate)
            }
            if (endDate) {
                le("noteIncomeDate", endDate)
            }
            eq("accountState", GeneConstants.INVOICE_CONFIRM_YES)
            eq("costTypes", GeneConstants.INVOICE_ECPERT_FEES)
            eq("inputUserId", Long.valueOf(parentNode.id))
            eq("inputDeptId", Long.valueOf(deptId))
        }.noteIncomeMoney.sum()
        parentNode.expertFeeSum = expertFeeSum ? expertFeeSum : BigDecimal.valueOf(0)
        /**
         * 监测费
         */
        def monitorFeeSum = EiaIncomeOut.createCriteria().list() {
            eq("ifDel", false)
            if (startDate) {
                ge("noteIncomeDate", startDate)
            }
            if (endDate) {
                le("noteIncomeDate", endDate)
            }
            eq("accountState", GeneConstants.INVOICE_CONFIRM_YES)
            eq("costTypes", GeneConstants.INVOICE_MONITORING_FEES)
            eq("inputUserId", Long.valueOf(parentNode.id))
            eq("inputDeptId", Long.valueOf(deptId))
        }.noteIncomeMoney.sum()
        parentNode.monitorFeeSum = monitorFeeSum ? monitorFeeSum : BigDecimal.valueOf(0)
        /**
         * 其他
         */
        def otherFeeSum = EiaIncomeOut.createCriteria().list() {
            eq("ifDel", false)
            if (startDate) {
                ge("noteIncomeDate", startDate)
            }
            if (endDate) {
                le("noteIncomeDate", endDate)
            }
            eq("accountState", GeneConstants.INVOICE_CONFIRM_YES)
            eq("costTypes", GeneConstants.INVOICE_OTHER_FEES)
            eq("inputUserId", Long.valueOf(parentNode.id))
            eq("inputDeptId", Long.valueOf(deptId))
        }.noteIncomeMoney.sum()
        parentNode.otherFeeSum = otherFeeSum ? otherFeeSum : BigDecimal.valueOf(0)
        def outstandingAmountSum = (eiaContract?.contractMoney.sum() ? eiaContract?.contractMoney.sum() : BigDecimal.valueOf(0)) - (invoiceIncomeSum ? invoiceIncomeSum : BigDecimal.valueOf(0))
        parentNode.outstandingAmountSum = outstandingAmountSum ? outstandingAmountSum : BigDecimal.valueOf(0)
        parentNode.ifOpen = false
        return parentNode
    }

    /***
     * 返回组织进出帐明细
     * @param parentNode
     * @return
     */
    def getOrgEiaInvoice(parentNode, startDate, endDate) {
        def eiaContract = EiaContract.createCriteria().list() {
            eq("ifDel", false)
            eq("ifShow", true)
            isNotNull("contractNo")
            if (startDate) {
                ge("contractDate", startDate)
            }
            if (endDate) {
                le("contractDate", endDate)
            }
            like("inputDeptCode", "%" + parentNode.orgCode + "%")
        }
        parentNode.contractMoney = eiaContract?.contractMoney.sum() ? eiaContract?.contractMoney.sum() : BigDecimal.valueOf(0)
        /**
         * 开票金额
         */
        def billMoney = EiaInvoice.createCriteria().list() {
            eq("ifDel", false)
            if (startDate) {
                ge("billDate", startDate)
            }

            if (endDate) {
                le("billDate", endDate)
            }
            eq("accountState", GeneConstants.INVOICE_CONFIRM_YES)
            like("inputDeptCode", "%" + parentNode.orgCode + "%")
        }.billMoney.sum()
        parentNode.billMoney = billMoney ? billMoney : BigDecimal.valueOf(0)
        /**
         * 进账金额
         */
        def eiaContractIncome = EiaContract.createCriteria().list() {
            eq("ifDel", false)
            eq("ifShow", true)
            isNotNull("contractNo")
            like("inputDeptCode", "%" + parentNode.orgCode + "%")
        }
        def invoiceIncomeSum = EiaIncomeOut.createCriteria().list() {
            eq("ifDel", false)
            if (startDate) {
                ge("noteIncomeDate", startDate)
            }
            if (endDate) {
                le("noteIncomeDate", endDate)
            }
            eq("accountState", GeneConstants.INVOICE_CONFIRM_YES)
            eq("costTypes", GeneConstants.INVOICE_TYPE_INCOME)
            if (eiaContractIncome) {
                'in'("contractId", eiaContractIncome?.id)
            } else {
                'in'("contractId", Long.valueOf(-1))
            }

        }.noteIncomeMoney.sum()
        parentNode.invoiceIncomeSum = invoiceIncomeSum ? invoiceIncomeSum : BigDecimal.valueOf(0)
        /**
         * 专家费
         */
        def expertFeeSum = EiaIncomeOut.createCriteria().list() {
            eq("ifDel", false)
            if (startDate) {
                ge("noteIncomeDate", startDate)
            }
            if (endDate) {
                le("noteIncomeDate", endDate)
            }
            eq("accountState", GeneConstants.INVOICE_CONFIRM_YES)
            eq("costTypes", GeneConstants.INVOICE_ECPERT_FEES)
            like("inputDeptCode", "%" + parentNode.orgCode + "%")
        }.noteIncomeMoney.sum()
        parentNode.expertFeeSum = expertFeeSum ? expertFeeSum : BigDecimal.valueOf(0)

        /**
         * 监测费
         */
        def monitorFeeSum = EiaIncomeOut.createCriteria().list() {
            eq("ifDel", false)
            eq("costTypes", GeneConstants.INVOICE_MONITORING_FEES)
            if (startDate) {
                ge("noteIncomeDate", startDate)
            }
            if (endDate) {
                le("noteIncomeDate", endDate)
            }
            eq("accountState", GeneConstants.INVOICE_CONFIRM_YES)
            like("inputDeptCode", "%" + parentNode.orgCode + "%")
        }.noteIncomeMoney.sum()
        parentNode.monitorFeeSum = monitorFeeSum ? monitorFeeSum : BigDecimal.valueOf(0)
        /**
         * 其他
         */
        def otherFeeSum = EiaIncomeOut.createCriteria().list() {
            eq("ifDel", false)
            eq("costTypes", GeneConstants.INVOICE_OTHER_FEES)
            if (startDate) {
                ge("noteIncomeDate", startDate)
            }
            if (endDate) {
                le("noteIncomeDate", endDate)
            }
            eq("accountState", GeneConstants.INVOICE_CONFIRM_YES)
            like("inputDeptCode", "%" + parentNode.orgCode + "%")
        }.noteIncomeMoney.sum()
        parentNode.otherFeeSum = otherFeeSum ? otherFeeSum : BigDecimal.valueOf(0)
        def outstandingAmountSum = (eiaContract?.contractMoney.sum() ? eiaContract?.contractMoney.sum() : BigDecimal.valueOf(0)) - (invoiceIncomeSum ? invoiceIncomeSum : BigDecimal.valueOf(0))
        parentNode.outstandingAmountSum = outstandingAmountSum ? outstandingAmountSum : BigDecimal.valueOf(0)
        return parentNode
    }
    /**
     * 开票未进账
     */
    def getEiaInvoiceIncome(params, session) {
        def sdf = new SimpleDateFormat("yyyy-MM-dd")
        int page
        int limit
        if (params.page && params.limit) {
            page = params.int('page') - 1
            limit = params.int('limit')
        }
        def invoiceProjectList = []
        def sql = "select A.noteIncomeMoney,B.contract_id as contractId,B.billMoney from (select sum(bill_Money)  as billMoney , contract_id from eia_invoice where account_state='已确认' and if_del=false group by contract_id ) B left  join(select sum(note_income_money)  as noteIncomeMoney, contract_id from eia_income_out where  cost_types='进账' and if_del=false AND account_state='已确认' group by contract_id) A on B.contract_id = A.contract_id";
        def sql_dataSource = new Sql(dataSource)
        sql_dataSource.eachRow(sql) {
            if (it.billMoney > it.noteIncomeMoney) {
                invoiceProjectList << it.contractId
            }
        }
        def eiaContractList = EiaContract.createCriteria().list(max: limit, offset: page * limit) {
            if (params.contractName && params.contractName != "合同名称、合同编号、录入部门、录入人") {
                or {
                    like('contractNo', '%' + params.contractName + '%')
                    like('contractName', '%' + params.contractName + '%')
                    like("inputDept", "%" + params.contractName + "%")
                    like("inputUser", "%" + params.contractName + "%")
                }
            } else {
                if (session?.staff?.funcCode?.contains(FuncConstants.EIA_CWGL_YWRYZWGL_VIEWDEPT)) {
                    eq("inputUserId", Long.valueOf(session.staff.staffId))
                }
            }
            /**
             * 查看全部的客户数据
             */
            if (!session?.staff?.funcCode?.contains(FuncConstants.EIA_CWGL_CWRYZWGL_VIEWALL)) {
                /**
                 * 财务查看本部门财务数据
                 */
                if (session?.staff?.funcCode?.contains(FuncConstants.EIA_CWGL_CWRYZWGL_VIEWDEPT)) {
                    like("inputDeptCode", "%" + session.staff.orgCode + "%")
                }
                /**
                 * 业务查看本部门财务数据
                 */
                else if (session?.staff?.funcCode?.contains(FuncConstants.EIA_CWGL_YWRYZWGL_VIEWDEPT)) {
                    like("inputDeptCode", "%" + session.staff.orgCode + "%")
                }
                /**
                 * 财务数据
                 */
                else if (session?.staff?.funcCode?.contains(FuncConstants.EIA_CWGL_YWRYZWGL_VIEWSELF)) {
                    eq("inputUserId", Long.valueOf(session.staff.staffId))
                } else {
                    eq("id", Long.valueOf(-1))
                }
            }
            if (invoiceProjectList) {
                'in'("id", invoiceProjectList)
            } else {
                eq("id", Long.valueOf(-1))
            }
            eq("ifDel", false)
            eq("ifShow", true)
            isNotNull("contractNo")
            order("id", "desc")
        }
        def eiaContractDataInfo = []
        eiaContractList.each {
            def map = [:]
            map.id = it.id
            map.contractName = it.contractName
            map.contractNo = it.contractNo
            map.contractMoney = it.contractMoney
            map.inputUser = it.inputUser
            map.inputUserId = it.inputUserId
            def eiaInvoice = EiaInvoice.findAllByContractIdAndIfDelAndAccountState(it.id, false, GeneConstants.INVOICE_CONFIRM_YES)
            /**
             * 开票金额
             */
            def eiaInvoiceSum = 0
            eiaInvoice.each {
                eiaInvoiceSum += it.billMoney
                if (it.billDate) {
                    map.billDate = it.billDate?.format("yyyy-MM-dd")
                    Date day = new Date();
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    def incomeDay = df.format(day)
                    String billDate = it.billDate?.format("yyyy-MM-dd")
                    //算两个日期间隔多少天
                    if (billDate) {
                        Date dateCalendar = df.parse(incomeDay);
                        Date billCalendarDate = df.parse(billDate);
                        int incomeDays = (int) ((dateCalendar.getTime() - billCalendarDate.getTime()) / (1000 * 3600 * 24));
                        map.incomeDay = incomeDays
                    }
                }
            }
            /**
             * 进账金额
             */
            def invoiceIncomeSum = 0
            def invoiceIncome = EiaIncomeOut.findAllByContractIdAndIfDelAndInvoiceTypeAndAccountState(it.id, false, GeneConstants.INVOICE_TYPE_INCOME, GeneConstants.INVOICE_CONFIRM_YES)
            invoiceIncome.each {
                invoiceIncomeSum += it.noteIncomeMoney
            }
            map.outstandingAmount = eiaInvoiceSum - invoiceIncomeSum
            map.billMoney = eiaInvoiceSum
            map.noteIncomeMoney = invoiceIncomeSum
            map.inputUserId = it.inputUserId
            map.inputDept = it.inputDept
            eiaContractDataInfo << map
        }
        /**
         * 统计合同金额(总计)
         */
        def eiaContractInfoList = EiaContract.createCriteria().list() {
            if (params.contractName && params.contractName != "合同名称、合同编号、录入部门、录入人") {
                or {
                    like('contractNo', '%' + params.contractName + '%')
                    like('contractName', '%' + params.contractName + '%')
                    like("inputDept", "%" + params.contractName + "%")
                    like("inputUser", "%" + params.contractName + "%")
                }
            } else {
                if (session?.staff?.funcCode?.contains(FuncConstants.EIA_CWGL_YWRYZWGL_VIEWDEPT)) {
                    eq("inputUserId", Long.valueOf(session.staff.staffId))
                }
            }
            /**
             * 查看全部的客户数据
             */
            if (!session?.staff?.funcCode?.contains(FuncConstants.EIA_CWGL_CWRYZWGL_VIEWALL)) {
                /**
                 * 财务查看本部门财务数据
                 */
                if (session?.staff?.funcCode?.contains(FuncConstants.EIA_CWGL_CWRYZWGL_VIEWDEPT)) {
                    like("inputDeptCode", "%" + session.staff.orgCode + "%")
                }
                /**
                 * 业务查看本部门财务数据
                 */
                else if (session?.staff?.funcCode?.contains(FuncConstants.EIA_CWGL_YWRYZWGL_VIEWDEPT)) {
                    like("inputDeptCode", "%" + session.staff.orgCode + "%")
                }
                /**
                 * 财务数据
                 */
                else if (session?.staff?.funcCode?.contains(FuncConstants.EIA_CWGL_YWRYZWGL_VIEWSELF)) {
                    eq("inputUserId", Long.valueOf(session.staff.staffId))
                } else {
                    eq("id", Long.valueOf(-1))
                }
            }
            if (invoiceProjectList) {
                'in'("id", invoiceProjectList)
            } else {
                eq("id", Long.valueOf(-1))
            }
            eq("ifDel", false)
            eq("ifShow", true)
            isNotNull("contractNo")
            order("id", "desc")
        }
        def eiaContractDataInfoList = []
        def map = [:]
        /**
         * 合同金额
         */
        def contractMoneySum = 0
        contractMoneySum += eiaContractInfoList*.contractMoney.sum() ?: 0
        map.contractMoney = contractMoneySum
        /**
         * 开票金额
         */
        def eiaInvoiceSum = 0
        if (eiaContractInfoList.size() > 0) {
            eiaInvoiceSum = EiaInvoice.findAllByContractIdInListAndIfDelAndAccountState(eiaContractInfoList?.id, false, GeneConstants.INVOICE_CONFIRM_YES)*.billMoney.sum() ?: 0
        }
        /**
         * 进账金额
         */
        def invoiceIncomeSum = 0
        if (eiaContractInfoList.size() > 0) {
            invoiceIncomeSum = EiaIncomeOut.findAllByContractIdInListAndIfDelAndInvoiceTypeAndAccountState(eiaContractInfoList?.id, false, GeneConstants.INVOICE_TYPE_INCOME, GeneConstants.INVOICE_CONFIRM_YES)*.noteIncomeMoney.sum() ?: 0
        }
        map.outstandingAmount = eiaInvoiceSum - invoiceIncomeSum
        map.billMoney = eiaInvoiceSum
        map.noteIncomeMoney = invoiceIncomeSum
        eiaContractDataInfoList << map
        def dataMap = [:]
        dataMap.data = eiaContractDataInfo
        dataMap.total = eiaContractList?.totalCount
        dataMap.totalDetail = eiaContractDataInfoList
        return dataMap
    }
    /**
     * 财务预计信息统计
     * @param params
     * @return
     */
    def getEiaAccountExpect(params, session) {
        /**
         * 当前年份
         */
        Calendar cale = null;
        cale = Calendar.getInstance();
        int year = cale.get(Calendar.YEAR);
        /**
         * 计时 **/
        long startTime = new Date().time
        def status = params.status
        def contractDate = params.contractDate
        Calendar calendar = Calendar.getInstance();
        def expectPeriodMonth
        def startDate
        def endDate
        def isStartDate = false
        def isEndDate = false
        if (params.expectStartPeriod) {
            startDate = params.expectStartPeriod
            isStartDate = true
        } else {
            int month = calendar.get(Calendar.MONTH)
            if (month + 1 > 9) {
                expectPeriodMonth = month + 1
            } else {
                expectPeriodMonth = "0" + (month + 1)
            }
            startDate = calendar.get(Calendar.YEAR) + "-" + expectPeriodMonth
        }
        if (params.expectEndPeriod) {
            endDate = params.expectEndPeriod
            isEndDate = true
        } else {
            int month = calendar.get(Calendar.MONTH)
            if (month + 1 > 9) {
                expectPeriodMonth = month + 1
            } else {
                expectPeriodMonth = "0" + (month + 1)
            }
            endDate = calendar.get(Calendar.YEAR) + "-" + expectPeriodMonth
        }
        def orgId = String.valueOf(params.id)
        def resList = []
        def ifRoot = false
        def orgCode = session.staff.orgCode
        if (!session?.staff?.funcCode?.contains(FuncConstants.EIA_CWGL_CWRYZWGL_VIEWALL)) {
            /**
             * 查看本部门财务数据
             */
            if (session?.staff?.funcCode?.contains(FuncConstants.EIA_CWGL_CWRYZWGL_VIEWDEPT)) {
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
        } else {
            if (status == "root") {
                orgId = String.valueOf(GeneConstants.ORG_ROOT)
                status = "open"
            }
        }
        /**
         * 获取财务预计信息
         */
        def eiaDomainCode = EiaDomainCode.findAllByDomain(GeneConstants.EXPECT_ACCOUNT)
        def expectOrgId
        if (eiaDomainCode) {
            expectOrgId = eiaDomainCode?.code
        }
        if (status == "close") {
            def resMap = JsonHandler.jsonToMap(HttpConnectTools.getResponseJson(HttpUrlConstants.EIA_EXCEPT_PARENT_ORG, [orgId: orgId, expectOrgId: String.valueOf(expectOrgId)]))
            if (resMap.code == HttpMesConstants.CODE_OK) {
                def parentNode = resMap.data
                if (Long.valueOf(parentNode.id) == GeneConstants.ORG_ROOT) {
                    ifRoot = true
                }
                parentNode.ifRoot = ifRoot
                resList << getEiaOrgExpectMoney(parentNode, startDate, endDate, isStartDate, isEndDate)
                resMap = JsonHandler.jsonToMap(HttpConnectTools.getResponseJson(HttpUrlConstants.EIA_EXPECT_CHILD_ORG, [orgId: String.valueOf(parentNode.id), expectOrgId: String.valueOf(expectOrgId)]))
                if (resMap.code == HttpMesConstants.CODE_OK) {
                    def childNodeList = resMap.data
                    childNodeList.each {
                        if (it.ifStaff) {
                            resList << getEiaStaffExpectMoney(it, startDate, endDate, parentNode.id, isStartDate, isEndDate)
                        } else {
                            resList << getEiaOrgExpectMoney(it, startDate, endDate, isStartDate, isEndDate)
                        }
                    }
                }
            }
        } else if (status == "open") {
            def resMap = JsonHandler.jsonToMap(HttpConnectTools.getResponseJson(HttpUrlConstants.EIA_GET_THIS_ORG, [orgId: orgId, expectOrgId: String.valueOf(expectOrgId)]))
            if (resMap.code == HttpMesConstants.CODE_OK) {
                def parentNode = resMap.data
                if (Long.valueOf(parentNode.id) == GeneConstants.ORG_ROOT) {
                    ifRoot = true
                }
                parentNode.ifRoot = ifRoot
                resList << getEiaOrgExpectMoney(parentNode, startDate, endDate, isStartDate, isEndDate)
                resMap = JsonHandler.jsonToMap(HttpConnectTools.getResponseJson(HttpUrlConstants.EIA_EXPECT_CHILD_ORG, [orgId: String.valueOf(parentNode.id), expectOrgId: String.valueOf(expectOrgId)]))
                if (resMap.code == HttpMesConstants.CODE_OK) {
                    def childNodeList = resMap.data
                    childNodeList.each {
                        if (it.ifStaff) {
                            resList << getEiaStaffExpectMoney(it, startDate, endDate, parentNode.id, isStartDate, isEndDate)
                        } else {
                            resList << getEiaOrgExpectMoney(it, startDate, endDate, isStartDate, isEndDate)
                        }
                    }
                }
            }
        }
        long endTime = new Date().time
        return resList
    }
    /**
     * 财务预计信息人员详情
     */
    def getEiaStaffExpectMoney(parentNode, startDate, endDate, deptId, isStartDate, isEndDate) {
        /**
         * 财务预计
         */
        def eiaAccountExpectList = EiaAccountExpect.createCriteria().list() {
            eq("ifDel", false)
            eq("accountState", GeneConstants.INVOICE_CONFIRM_YES)
            between("expectPeriod", startDate, endDate)
            eq("inputUserId", Long.valueOf(parentNode.id))
            eq("inputDeptId", Long.valueOf(deptId))
        }
        def noteIncomeStartDate, noteIncomeEndDate
        def sdf = new SimpleDateFormat("yyyy-MM-dd")
        noteIncomeStartDate = sdf.parse(startDate + "-01")
        noteIncomeEndDate = sdf.parse(endDate + "-31")
        def eiaContractIncome = EiaContract.createCriteria().list() {
            eq("ifDel", false)
            eq("ifShow", true)
            isNotNull("contractNo")
            eq("inputUserId", Long.valueOf(parentNode.id))
            eq("inputDeptId", Long.valueOf(deptId))
        }
        /**
         * 已回款信息
         */
        def eiaIncomeOutList = EiaIncomeOut.createCriteria().list() {
            eq("ifDel", false)
            eq("accountState", GeneConstants.INVOICE_CONFIRM_YES)
            eq("costTypes", GeneConstants.INVOICE_TYPE_INCOME)
            if (eiaContractIncome) {
                'in'("contractId", eiaContractIncome?.id)
            } else {
                'in'("contractId", Long.valueOf(-1))
            }
            if (isStartDate) {
                if (noteIncomeStartDate) {
                    ge("noteIncomeDate", noteIncomeStartDate)
                }
            }
            if (isEndDate) {
                if (noteIncomeEndDate) {
                    le("noteIncomeDate", noteIncomeEndDate)
                }
            }

        }
        /**
         * 已开票信息
         */
        def eiaInvoiceList = EiaInvoice.createCriteria().list() {
            eq("ifDel", false)
            eq("accountState", GeneConstants.INVOICE_CONFIRM_YES)
            eq("inputUserId", Long.valueOf(parentNode.id))
            eq("inputDeptId", Long.valueOf(deptId))
            if (noteIncomeStartDate) {
                ge("billDate", noteIncomeStartDate)
            }
            if (noteIncomeEndDate) {
                le("billDate", noteIncomeEndDate)
            }
        }
        /**
         * 已开票金额
         */
        if (eiaInvoiceList?.billMoney) {
            parentNode.expectInvoiceAlread = eiaInvoiceList.billMoney.sum() ?: 0
        } else {
            parentNode.expectInvoiceAlread = 0
        }
        /**
         * 已回款
         */
        if (eiaIncomeOutList?.noteIncomeMoney) {
            parentNode.incomeMoney = eiaIncomeOutList.noteIncomeMoney.sum() ?: 0
        } else {
            parentNode.incomeMoney = 0
        }
        /**
         * 本月预计开票
         */
        if (eiaAccountExpectList?.expectInvoiceMoney) {
            parentNode.expectInvoiceMoney = eiaAccountExpectList.expectInvoiceMoney.sum() ?: 0
        } else {
            parentNode.expectInvoiceMoney = 0
        }
        /**
         * 本期预计收款
         */
        if (eiaAccountExpectList?.expectIncomeMoney) {
            parentNode.expectIncomeMoney = eiaAccountExpectList.expectIncomeMoney.sum() ?: 0
        } else {
            parentNode.expectIncomeMoney = 0
        }
        /**
         * 预计检测费(万元)
         */
        if (eiaAccountExpectList?.expectIncomeMoney) {
            parentNode.monitorFee = eiaAccountExpectList.monitorFee.sum() ?: 0
        } else {
            parentNode.monitorFee = 0
        }
        /**
         * 预计专家费(万元)
         */
        if (eiaAccountExpectList?.expertFee) {
            parentNode.expertFee = eiaAccountExpectList.expertFee.sum() ?: 0
        } else {
            parentNode.expertFee = 0
        }
        /**
         * 预计其他(万元)
         */
        if (eiaAccountExpectList?.otherFee) {
            parentNode.otherFee = eiaAccountExpectList.otherFee.sum() ?: 0
        } else {
            parentNode.otherFee = 0
        }

        /**
         * 截止上月累计发生业务成本
         */
        /**
         * 检测费
         */
        def monitorFeeSum = EiaIncomeOut.createCriteria().list() {
            eq("costTypes", GeneConstants.INVOICE_MONITORING_FEES)
            eq("accountState", GeneConstants.INVOICE_CONFIRM_YES)
            eq("ifDel", false)
            if (noteIncomeStartDate) {
                ge("noteIncomeDate", noteIncomeStartDate)
            }
            if (noteIncomeEndDate) {
                le("noteIncomeDate", noteIncomeEndDate)
            }
            eq("inputUserId", Long.valueOf(parentNode.id))
            eq("inputDeptId", Long.valueOf(deptId))
        }.noteIncomeMoney.sum()
        parentNode.monitorFeeSum = monitorFeeSum ?: 0
        /**
         * 专家费(万元)
         */
        def expertFeeSum = EiaIncomeOut.createCriteria().list() {
            eq("costTypes", GeneConstants.INVOICE_ECPERT_FEES)
            eq("accountState", GeneConstants.INVOICE_CONFIRM_YES)
            eq("ifDel", false)
            if (noteIncomeStartDate) {
                ge("noteIncomeDate", noteIncomeStartDate)
            }
            if (noteIncomeEndDate) {
                le("noteIncomeDate", noteIncomeEndDate)
            }
            eq("inputUserId", Long.valueOf(parentNode.id))
            eq("inputDeptId", Long.valueOf(deptId))
        }.noteIncomeMoney.sum()
        parentNode.expertFeeSum = expertFeeSum ?: 0
        /**
         * 其他
         */
        def otherFeeSum = EiaIncomeOut.createCriteria().list() {
            eq("costTypes", GeneConstants.INVOICE_OTHER_FEES)
            eq("accountState", GeneConstants.INVOICE_CONFIRM_YES)
            eq("ifDel", false)
            if (noteIncomeStartDate) {
                ge("noteIncomeDate", noteIncomeStartDate)
            }
            if (noteIncomeEndDate) {
                le("noteIncomeDate", noteIncomeEndDate)
            }
            eq("inputUserId", Long.valueOf(parentNode.id))
            eq("inputDeptId", Long.valueOf(deptId))
        }.noteIncomeMoney.sum()
        parentNode.otherFeeSum = otherFeeSum ?: 0
        parentNode.ifOpen = false
        return parentNode
    }
    /**
     * 财务预计信息部门详情
     */
    def getEiaOrgExpectMoney(parentNode, startDate, endDate, isStartDate, isEndDate) {
        def eiaDomainCode = EiaDomainCode.findByDomain(GeneConstants.EXPECT_ACCOUNT)
        /**
         * 财务预计
         */
        def eiaAccountExpectList = EiaAccountExpect.createCriteria().list() {
            eq("ifDel", false)
            eq("accountState", GeneConstants.INVOICE_CONFIRM_YES)
            between("expectPeriod", startDate, endDate)
            if (parentNode.id == GeneConstants.ORG_ROOT) {
                like("inputDeptCode", "%" + eiaDomainCode?.codeRemark + "%")
            } else {
                like("inputDeptCode", "%" + parentNode.orgCode + "%")
            }
        }
        def noteIncomeStartDate, noteIncomeEndDate
        def sdf = new SimpleDateFormat("yyyy-MM-dd")
        noteIncomeStartDate = sdf.parse(startDate + "-01")
        noteIncomeEndDate = sdf.parse(endDate + "-31")
        def eiaContractIncome = EiaContract.createCriteria().list() {
            eq("ifDel", false)
            eq("ifShow", true)
            isNotNull("contractNo")
            if (parentNode.id == GeneConstants.ORG_ROOT) {
                like("inputDeptCode", "%" + eiaDomainCode?.codeRemark + "%")
            } else {
                like("inputDeptCode", "%" + parentNode.orgCode + "%")
            }
        }
        /**
         * 已回款信息
         */
        def eiaIncomeOutList = EiaIncomeOut.createCriteria().list() {
            eq("ifDel", false)
            eq("accountState", GeneConstants.INVOICE_CONFIRM_YES)
            eq("costTypes", GeneConstants.INVOICE_TYPE_INCOME)
            if (eiaContractIncome) {
                'in'("contractId", eiaContractIncome?.id)
            } else {
                'in'("contractId", Long.valueOf(-1))
            }
            if (isStartDate) {
                if (noteIncomeStartDate) {
                    ge("noteIncomeDate", noteIncomeStartDate)
                }
            }
            if (isEndDate) {
                if (noteIncomeStartDate) {
                    le("noteIncomeDate", noteIncomeEndDate)
                }
            }

        }
        /**
         * 已开票信息
         */
        def eiaInvoiceList = EiaInvoice.createCriteria().list() {
            eq("ifDel", false)
            eq("accountState", GeneConstants.INVOICE_CONFIRM_YES)
            if (parentNode.id == GeneConstants.ORG_ROOT) {
                like("inputDeptCode", "%" + eiaDomainCode?.codeRemark + "%")
            } else {
                like("inputDeptCode", "%" + parentNode.orgCode + "%")
            }
            if (isStartDate) {
                if (noteIncomeStartDate) {
                    ge("billDate", noteIncomeStartDate)
                }
            }
            if (isEndDate) {
                if (noteIncomeStartDate) {
                    le("billDate", noteIncomeEndDate)
                }
            }

        }
        /**
         * 已开票金额
         */
        if (eiaInvoiceList?.billMoney) {
            parentNode.expectInvoiceAlread = eiaInvoiceList.billMoney.sum() ?: 0
        } else {
            parentNode.expectInvoiceAlread = 0
        }
        /**
         * 已回款
         */
        if (eiaIncomeOutList?.noteIncomeMoney) {
            parentNode.incomeMoney = eiaIncomeOutList.noteIncomeMoney.sum() ?: 0
        } else {
            parentNode.incomeMoney = 0
        }
        /**
         * 本月预计开票
         */
        if (eiaAccountExpectList?.expectInvoiceMoney) {
            parentNode.expectInvoiceMoney = eiaAccountExpectList.expectInvoiceMoney.sum() ?: 0
        } else {
            parentNode.expectInvoiceMoney = 0
        }
        /**
         * 本期预计收款
         */
        if (eiaAccountExpectList?.expectIncomeMoney) {
            parentNode.expectIncomeMoney = eiaAccountExpectList.expectIncomeMoney.sum() ?: 0
        } else {
            parentNode.expectIncomeMoney = 0
        }
        /**
         * 预计检测费(万元)
         */
        if (eiaAccountExpectList?.expectIncomeMoney) {
            parentNode.monitorFee = eiaAccountExpectList.monitorFee.sum() ?: 0
        } else {
            parentNode.monitorFee = 0
        }
        /**
         * 预计专家费(万元)
         */
        if (eiaAccountExpectList?.expertFee) {
            parentNode.expertFee = eiaAccountExpectList.expertFee.sum() ?: 0
        } else {
            parentNode.expertFee = 0
        }
        /**
         * 预计其他(万元)
         */
        if (eiaAccountExpectList?.otherFee) {
            parentNode.otherFee = eiaAccountExpectList.otherFee.sum() ?: 0
        } else {
            parentNode.otherFee = 0
        }

        /**
         * 截止上月累计发生业务成本
         */
        /**
         * 检测费
         */
        def monitorFeeSum = EiaIncomeOut.createCriteria().list() {
            eq("costTypes", GeneConstants.INVOICE_MONITORING_FEES)
            eq("accountState", GeneConstants.INVOICE_CONFIRM_YES)
            eq("ifDel", false)
            if (isStartDate) {
                if (noteIncomeStartDate) {
                    ge("noteIncomeDate", noteIncomeStartDate)
                }
            }
            if (isEndDate) {
                if (noteIncomeEndDate) {
                    le("noteIncomeDate", noteIncomeEndDate)
                }
            }
            if (parentNode.id == GeneConstants.ORG_ROOT) {
                like("inputDeptCode", "%" + eiaDomainCode?.codeRemark + "%")
            } else {
                like("inputDeptCode", "%" + parentNode.orgCode + "%")
            }
        }.noteIncomeMoney.sum()
        parentNode.monitorFeeSum = monitorFeeSum ?: 0
        /**
         * 专家费(万元)
         */
        def expertFeeSum = EiaIncomeOut.createCriteria().list() {
            eq("costTypes", GeneConstants.INVOICE_ECPERT_FEES)
            eq("accountState", GeneConstants.INVOICE_CONFIRM_YES)
            eq("ifDel", false)
            if (isStartDate) {
                if (noteIncomeStartDate) {
                    ge("noteIncomeDate", noteIncomeStartDate)
                }
            }
            if (isEndDate) {
                if (noteIncomeEndDate) {
                    le("noteIncomeDate", noteIncomeEndDate)
                }
            }
            if (parentNode.id == GeneConstants.ORG_ROOT) {
                like("inputDeptCode", "%" + eiaDomainCode?.codeRemark + "%")
            } else {
                like("inputDeptCode", "%" + parentNode.orgCode + "%")
            }
        }.noteIncomeMoney.sum()
        parentNode.expertFeeSum = expertFeeSum ?: 0
        /**
         * 其他
         */
        def otherFeeSum = EiaIncomeOut.createCriteria().list() {
            eq("costTypes", GeneConstants.INVOICE_OTHER_FEES)
            eq("accountState", GeneConstants.INVOICE_CONFIRM_YES)
            eq("ifDel", false)
            if (isStartDate) {
                if (noteIncomeStartDate) {
                    ge("noteIncomeDate", noteIncomeStartDate)
                }
            }
            if (isEndDate) {
                if (noteIncomeEndDate) {
                    le("noteIncomeDate", noteIncomeEndDate)
                }
            }
            if (parentNode.id == GeneConstants.ORG_ROOT) {
                like("inputDeptCode", "%" + eiaDomainCode?.codeRemark + "%")
            } else {
                like("inputDeptCode", "%" + parentNode.orgCode + "%")
            }
        }.noteIncomeMoney.sum()
        parentNode.otherFeeSum = otherFeeSum ?: 0
        return parentNode
    }
    /**
     * 判断当前人员是否有工作台按钮权限
     */
    def checkStaffClickBtn(params, session) {
        def funcCode = session?.staff?.funcCode
        def nameArr = params.nameArr
        def nameList = nameArr.split(",").toList()
        def map = [:]
        if (nameList) {
            nameList.each {
                /** funcCode包含的权限与页面按钮相比较，有该权限，则按钮可以点击 */
                if (funcCode.indexOf(it) != -1) {
                    map.put(it, true)
                } else {
                    /** 报价新增的权限与合同新增权限相同，所以单独判断 */
                    if (it == "EIA_HGGL_BJCJ_ADD" && funcCode.indexOf("EIA_HGGL_HTCJ_ADD") != -1) {
                        map.put(it, true)
                    } else {
                        map.put(it, false)
                    }
                }
            }
        }
        return map
    }
}
