package com.lheia.eia.contract

import com.lheia.eia.common.FuncConstants
import com.lheia.eia.common.GeneConstants
import com.lheia.eia.common.WorkFlowConstants
import com.lheia.eia.config.EiaDomainCode
import com.lheia.eia.workflow.EiaWorkFlowBusi
import grails.gorm.transactions.Transactional

import java.text.SimpleDateFormat

@Transactional
class EiaContractLogService {

    def eiaContractService

    /**
     * 合同变更保存
     */
    def eiaContractLogSave(eiaContractLogId, params, session) {
        /** 修改合同信息 */
        def eiaContract = eiaContractService.eiaContractUpdate(params, session)
        if (eiaContract) {
            this.copyProjectAfter(eiaContract.id, eiaContractLogId, session)
        }
    }

    /**
     * 获取合同变更详情
     */
    def getEiaContractLogDataMap(eiaContractLogId) {
        def dataMap = [:]
        def eiaContractLog = EiaContractLog.findById(eiaContractLogId)
        dataMap = this.convertToMap(eiaContractLog.properties)
        def province = EiaDomainCode.findByDomainAndCode(GeneConstants.PROVINCE_CODE, eiaContractLog.province).codeDesc
        def provinceEd = EiaDomainCode.findByDomainAndCode(GeneConstants.PROVINCE_CODE, eiaContractLog.provinceEd).codeDesc
        dataMap.remove("province")
        dataMap.remove("provinceEd")
        dataMap.put("province", province)
        dataMap.put("provinceEd", provinceEd)
        def contractUse = EiaDomainCode.findByDomainAndCode(GeneConstants.CONTRACT_USE, eiaContractLog.contractUse)?.codeDesc
        def contractUseEd = EiaDomainCode.findByDomainAndCode(GeneConstants.CONTRACT_USE, eiaContractLog.contractUseEd)?.codeDesc
        dataMap.remove("contractUse")
        dataMap.remove("contractUseEd")
        dataMap.put("contractUse", contractUse)
        dataMap.put("contractUseEd", contractUseEd)
        return dataMap
    }
    /**
     * 转换为map
     * @param obj
     */
    def convertToMap(Object obj) {
        def currMap = [:]

        for (Map.Entry<Integer, Integer> entry : obj.entrySet()) {
            currMap.put(entry.getKey(), entry.getValue())
        }
        return currMap
    }
    /**
     * 合同变更列表
     */
    def eiaContractLogQuery(params, session) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd")
        int page
        int limit
        if (params.page && params.limit) {
            page = params.int('page') - 1
            limit = params.int('limit')
        }
        def eiaContractLogList = EiaContractLog.createCriteria().list(max: limit, offset: page * limit) {
            /**
             * 数据权限编码所在位置
             */
            /**
             * 查看全部的客户数据
             */
            if (!session?.staff?.funcCode?.contains(FuncConstants.EIA_YWCX_XMCX_VIEWALL)) {
                /**
                 * 查看本部门客户数据
                 */
                if (session?.staff?.funcCode?.contains(FuncConstants.EIA_YWCX_HTCX_VIEWDEPT)) {
                    like("inputDeptCode", "%" + session.staff.orgCode + "%")
                }
                /**
                 * 查看本人客户数据
                 */
                else if (session?.staff?.funcCode?.contains(FuncConstants.EIA_YWCX_HTCX_VIEWSELF)) {
                    eq("inputUserId", Long.valueOf(session.staff.staffId))
                }
            }

            if (params.contractName && !"合同名称、合同编号、录入部门、录入人".equals(params.contractName)) {
                or {
                    like("contractNameEd", "%" + params.contractName + "%")
                    like("contractNo", "%" + params.contractName + "%")
                    like("inputDept", "%" + params.contractName + "%")
                    like("inputUser", "%" + params.contractName + "%")
                }
            }
            eq("ifDel", false)
            eq("ifHalt", params.ifHalt ? true : false)
            order("id", "desc")
        }
        def data = []
        eiaContractLogList.each {
            def map = [:]
            map.id = it.id
            map.eiaContractId = it?.eiaContractId
            map.contractNo = it?.contractNo
            map.contractName = it?.contractNameEd
            map.contractType = it?.contractTypeEd
            map.contractMoney = it?.contractMoneyEd
            if (it?.contractDateEd) {
                map.contractDate = sdf.format(it?.contractDateEd)
            }
            map.taskNo = it?.taskNoEd
            map.taskName = it?.taskNameEd
            map.inputDept = it?.inputDept
            map.inputUser = it?.inputUser
            map.ifSub = it?.ifSub
            map.ifHaltEnd = it?.ifHaltEnd
            map.inputUserId = it?.inputUserId
            data << map
        }
        def dataMap = [:]
        dataMap.data = data
        dataMap.total = eiaContractLogList.totalCount
        return dataMap
    }
    /**
     * 保存合同变更后信息
     */
    def copyProjectAfter(eiaContractId, eiaContractLogId, session) {
        def eiaContract = EiaContract.findById(eiaContractId)
        def eiaContractLog = EiaContractLog.findById(eiaContractLogId)
        if (eiaContract && eiaContractLog) {
            eiaContractLog.taskIdEd = eiaContract.taskId
            eiaContractLog.taskNoEd = eiaContract.taskNo
            eiaContractLog.taskNameEd = eiaContract.taskName
            eiaContractLog.eiaClientIdEd = eiaContract.eiaClientId
            eiaContractLog.eiaClientNameEd = eiaContract.eiaClientName
            eiaContractLog.clientAddressEd = eiaContract.clientAddress
            eiaContractLog.contactNameEd = eiaContract.contactName
            eiaContractLog.contactPhoneEd = eiaContract.contactPhone
            eiaContractLog.ownerClientIdEd = eiaContract.ownerClientId
            eiaContractLog.ownerClientNameEd = eiaContract.ownerClientName
            eiaContractLog.ownerClientAddressEd = eiaContract.ownerClientAddress
            eiaContractLog.ownerContactNameEd = eiaContract.ownerContactName
            eiaContractLog.ownerContactPhoneEd = eiaContract.ownerContactPhone
            eiaContractLog.contractNameEd = eiaContract.contractName
            eiaContractLog.contractTypeEd = eiaContract.contractType
            eiaContractLog.contractTypeCodeEd = eiaContract.contractTypeCode
            eiaContractLog.contractUseEd = eiaContract.contractUse
            eiaContractLog.contractMoneyEd = eiaContract.contractMoney
            eiaContractLog.provinceEd = eiaContract.province
            eiaContractLog.reportFeesEd = eiaContract.reportFees
            eiaContractLog.enviroMonitoringFeeEd = eiaContract.enviroMonitoringFee
            eiaContractLog.expertFeeEd = eiaContract.expertFee
            eiaContractLog.groundWaterEd = eiaContract.groundWater
            eiaContractLog.contractDateEd = eiaContract.contractDate
            eiaContractLog.otherFeeEd = eiaContract.otherFee
            eiaContractLog.specialFeeEd = eiaContract.specialFee
            eiaContractLog.serveFeeEd = eiaContract.serveFee
            eiaContractLog.ecoDetectFeeEd = eiaContract.ecoDetectFee
            eiaContractLog.preIssCertFeeEd = eiaContract.preIssCertFee
            eiaContractLog.preSurvCertFeeEd = eiaContract.preSurvCertFee
            eiaContractLog.certServeFeeEd = eiaContract.certServeFee
            eiaContractLog.contractTrustEd = eiaContract.contractTrust
            /** 日志录入时间、人员 */
            eiaContractLog.logInputDept = session.staff.orgName
            eiaContractLog.logInputDeptId = Long.parseLong(session.staff.orgId)
            eiaContractLog.logInputDeptCode = session.staff.orgCode
            eiaContractLog.logInputUser = session.staff.staffName
            eiaContractLog.logInputUserId = Long.parseLong(session.staff.staffId)
            return eiaContractLog.save(flush: true, failOnError: true)
        }
    }
    /**
     * 根据不同的文件类型获取要填写的金额名称和对应的值
     */
    def getContractLogMoney(params) {
        def eiaContractLogId = params.long('eiaContractLogId')
        def eiaContractLog
        def contractTypeCode
        if (eiaContractLogId) {
            eiaContractLog = EiaContractLog.findById(eiaContractLogId)
        }
        def properties
        if (eiaContractLog) {
            properties = eiaContractLog.properties
            contractTypeCode = eiaContractLog.contractTypeCodeEd
        }
        def parentCode = EiaDomainCode.findByDomainAndCode(GeneConstants.CONTRACT_TYPE, contractTypeCode)?.parentCode
        def dataMap
        if (contractTypeCode.indexOf("ZH") == -1 && contractTypeCode.indexOf("QT") == -1) {
            if (parentCode == "EPC") {
                if (contractTypeCode == "EPC_HD") {
                    dataMap = this.combNeedMap(properties, GeneConstants.CON_LOG_HD_MONEY_LIST)
                } else if (contractTypeCode == "EPC_HP") {
                    dataMap = this.combNeedMap(properties, GeneConstants.CON_LOG_HP_MONEY_LIST)
                } else if (contractTypeCode == "EPC_HY") {
                    dataMap = this.combNeedMap(properties, GeneConstants.CON_LOG_HY_MONEY_LIST)
                } else if (contractTypeCode == "EPC_HB") {
                    dataMap = this.combNeedMap(properties, GeneConstants.CON_LOG_HB_MONEY_LIST)
                } else if (contractTypeCode == "EPC_GH") {
                    dataMap = this.combNeedMap(properties, GeneConstants.CON_LOG_GH_MONEY_LIST)
                } else if (contractTypeCode == "EPC_JC") {
                    dataMap = this.combNeedMap(properties, GeneConstants.CON_LOG_JC_MONEY_LIST)
                } else if (contractTypeCode == "EPC_DC") {
                    dataMap = this.combNeedMap(properties, GeneConstants.CON_LOG_DC_MONEY_LIST)
                } else if (contractTypeCode == "EPC_YA") {
                    dataMap = this.combNeedMap(properties, GeneConstants.CON_LOG_YA_MONEY_LIST)
                } else if (contractTypeCode == "EPC_JL") {
                    dataMap = this.combNeedMap(properties, GeneConstants.CON_LOG_JL_MONEY_LIST)
                } else if (contractTypeCode == "EPC_HH") {
                    dataMap = this.combNeedMap(properties, GeneConstants.CON_LOG_HH_MONEY_LIST)
                } else if (contractTypeCode == "EPC_GJ") {
                    dataMap = this.combNeedMap(properties, GeneConstants.CON_LOG_GJ_MONEY_LIST)
                } else if (contractTypeCode == "EPC_XZ") {
                    dataMap = this.combNeedMap(properties, GeneConstants.CON_LOG_XZ_MONEY_LIST)
                } else if (contractTypeCode == "EPC_PF") {
                    dataMap = this.combNeedMap(properties, GeneConstants.CON_LOG_PF_MONEY_LIST)
                } else if (contractTypeCode == "EPC_PW") {
                    dataMap = this.combNeedMap(properties, GeneConstants.CON_LOG_PW_MONEY_LIST)
                } else if (contractTypeCode == "EPC_CD") {
                    dataMap = this.combNeedMap(properties, GeneConstants.CON_LOG_CD_MONEY_LIST)
                } else if (contractTypeCode == "EPC_ST") {
                    dataMap = this.combNeedMap(properties, GeneConstants.CON_LOG_ST_MONEY_LIST)
                } else if (contractTypeCode == "ESE_JN") {
                    dataMap = this.combNeedMap(properties, GeneConstants.CON_LOG_JN_MONEY_LIST)
                } else if (contractTypeCode == "ESE_QJ") {
                    dataMap = this.combNeedMap(properties, GeneConstants.CON_LOG_QJ_MONEY_LIST)
                }
            }
         /*   else if (parentCode == "ESE") {
                if (contractTypeCode == "ESE_JN") {
                    dataMap = this.combNeedMap(properties, GeneConstants.CON_LOG_JN_MONEY_LIST)
                } else if (contractTypeCode == "ESE_QJ") {
                    dataMap = this.combNeedMap(properties, GeneConstants.CON_LOG_QJ_MONEY_LIST)
                }
            } */
            else if (parentCode == "GREEN") {
                if (contractTypeCode == "GREEN_LZ") {
                    dataMap = this.combNeedMap(properties, GeneConstants.CON_LOG_LZ_MONEY_LIST)
                } else if (contractTypeCode == "GREEN_LQ") {
                    dataMap = this.combNeedMap(properties, GeneConstants.CON_LOG_LQ_MONEY_LIST)
                } else {
                    dataMap = this.combNeedMap(properties, GeneConstants.CON_LOG_GREEN_MONEY_LIST)
                }
            }
        } else if (contractTypeCode.indexOf("ZH") != -1) {
            if (contractTypeCode == "EPC_ZH") {
                dataMap = this.combNeedMap(properties, GeneConstants.CON_LOG_EPC_ZH_MONEY_LIST)
            } else {
                dataMap = this.combNeedMap(properties, GeneConstants.CON_LOG_ZH_MONEY_LIST)
            }
        } else if (contractTypeCode.indexOf("QT") != -1) {
            dataMap = this.combNeedMap(properties, GeneConstants.CON_LOG_QT_MONEY_LIST)
        }
        return dataMap
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

    /***
     * 改变合同变更流程标志位
     */
    def eiaContractLogSub(long eiaContractLogId) {
        def eiaContractLog = EiaContractLog.findByIdAndIfDel(eiaContractLogId, false)
        eiaContractLog?.ifSub = true
        eiaContractLog?.save(flush: true, failOnError: true)
    }

    /*************合同中止************/
    /***
     * 删除合同和合同流程
     * @param eiaContractId
     * @return
     */
    def contractWorkFlowHalt(long eiaContractId) {
        /**
         * 删除合同
         */
        eiaContractService.eiaContractDel(eiaContractId)
        /***
         * 删除合同流程
         */
        def eiaContractBusi = EiaWorkFlowBusi.findByTableNameAndTableNameIdAndWorkFlowStateNotEqual(GeneConstants.DOMAIN_EIA_CONTRACT, eiaContractId, WorkFlowConstants.WORKFLOW_HALT)
        eiaContractBusi.workFlowState = WorkFlowConstants.WORKFLOW_HALT
        eiaContractBusi.ifDel = true
        eiaContractBusi.save(flush: true, failOnError: true)

        /****
         * 删除合同变更
         */
        def eiaContractLogList = EiaContractLog.findAllByIfDelAndEiaContractIdAndIfHalt(false, eiaContractId, false)
        eiaContractLogList.each {
            def eiaContractLogBusiList = EiaWorkFlowBusi.findByTableNameAndTableNameIdAndWorkFlowStateNotEqual(GeneConstants.DOMAIN_EIA_CONTRACT_LOG, it.id, WorkFlowConstants.WORKFLOW_HALT)
            eiaContractLogBusiList.each {
                it.workFlowState = WorkFlowConstants.WORKFLOW_HALT
                it.ifDel = true
                it.save(flush: true, failOnError: true)
            }
            it.ifDel = true
            it.save(flush: true, failOnError: true)
        }
    }
    /**
     * 合同终止删除
     */
    def eiaContractLogDel(contractId) {
        def eiaContractLog = EiaContractLog.findByIfDelAndId(false, contractId)
        eiaContractLog.ifDel = true
        eiaContractLog.save(flush: true, failOnError: true)
    }
}
