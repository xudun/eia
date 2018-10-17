package com.lheia.eia.contract

import com.lheia.eia.common.GeneConstants
import com.lheia.eia.common.HttpMesConstants
import com.lheia.eia.common.WorkFlowConstants
import com.lheia.eia.workflow.EiaWorkFlowBusi
import com.lheia.eia.workflow.EiaWorkFlowBusiLog
import grails.converters.JSON

class EiaContractLogController {

    def eiaContractLogService
    def eiaContractService
    def eiaWorkFlowContractService
    def eiaWorkFlowContractLogService

    def eiaContractLogIndex() {
        render(view: "/eiaContract/eiaContractLogIndex.gsp")
    }

    def eiaContractLogSelect() {
        render(view: "/eiaContract/eiaContractLogSelect.gsp")
    }

    def eiaContractLogCreate() {
        render(view: "/eiaContract/eiaContractLogCreate.gsp")
    }

    def eiaContractLogDetail() {
        render(view: "/eiaContract/eiaContractLogDetail.gsp")
    }

    /*******************合同终止页面**********************/
    def eiaContractHaltDetail() {
        render(view: "/eiaContract/eiaContractHaltDetail.gsp")
    }

    def eiaContractHaltIndex() {
        render(view: "/eiaContract/eiaContractHaltIndex.gsp")
    }

    def eiaContractHaltSelect() {
        render(view: "/eiaContract/eiaContractHaltSelect.gsp")
    }
    /**
     * 合同变更列表
     */
    def eiaContractLogQuery() {
        def dataMap = eiaContractLogService.eiaContractLogQuery(params, session)
        if (dataMap) {
            render([code: HttpMesConstants.CODE_OK, count: dataMap.total, data: dataMap.data] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
    /**
     * 合同变更信息保存
     */
    def eiaContractLogSave() {
        def eiaContractId = params.long("eiaContractId")
        /** 先保存变更前的合同信息 */
        def eiaContractLog = this.copyContractBefore(eiaContractId)
        if (eiaContractLog) {
            /** 修改合同信息，然后保存变更后的合同信息 */
            if (eiaContractLogService.eiaContractLogSave(eiaContractLog.id, params, session)) {
                render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SAVE_OK, data: eiaContractLog] as JSON)
            } else {
                render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
            }
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
        }
    }
    /**
     * 合同变更信息回显
     */
    def getEiaContractLogDataMap() {
        def dataMap = eiaContractLogService.getEiaContractLogDataMap(params.long("eiaContractLogId"))
        if (dataMap) {
            render([code: HttpMesConstants.CODE_OK, data: dataMap, contractDate: dataMap.contractDate.format("yyyy-MM-dd"), contractDateEd: dataMap.contractDateEd.format("yyyy-MM-dd"), logInputDate: dataMap.dateCreated.format("yyyy-MM-dd HH:ss:mm")] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
    /**
     * 保存合同变更前信息
     */
    def copyContractBefore(Long eiaContractId) {
        def eiaContract = EiaContract.findById(eiaContractId)
        if (eiaContract) {
            def eiaContractLog = new EiaContractLog()
            bindData(eiaContractLog, eiaContract.properties)
            eiaContractLog.ifSub = false
            eiaContractLog.ifHalt = false
            eiaContractLog.eiaContractId = eiaContractId
            eiaContractLog.save(flush: true, failOnError: true)
        }
    }
    /**
     * 根据不同的文件类型获取要填写的金额名称和对应的值
     * @return
     */
    def getContractLogMoney() {
        def moneyList = eiaContractLogService.getContractLogMoney(params)
        if (moneyList) {
            render([code: HttpMesConstants.CODE_OK, data: moneyList] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }

    /***
     * 提交合同并开启工作流
     * @return
     */
    def eiaContractLogSub() {
        long eiaContractLogId = Long.valueOf(params.eiaContractLogId)
        def eiaContractLog = EiaContractLog.findByIdAndIfDel(eiaContractLogId, false)
        if (eiaContractLog.ifSub) {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_CERT_FLOW_SEC_SUB] as JSON)
            return
        }
        /****
         * 判断是否正常完成合同流程
         */
        def eiaContractBusi = EiaWorkFlowBusi.findByTableNameAndTableNameIdAndWorkFlowState(GeneConstants.DOMAIN_EIA_CONTRACT, eiaContractLog.eiaContractId, WorkFlowConstants.WORKFLOW_END)
        if (!eiaContractBusi) {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_CON_FLOW_NO_END] as JSON)
            return
        }
        /****
         * 判断是否存在未完成的合同变更流程
         */
        def eiaContracLogIdList = EiaContractLog.findAllByIfDelAndEiaContractIdAndIfHalt(false, eiaContractLog.eiaContractId,false).id
        def eiaContractLogBusi = EiaWorkFlowBusi.findAllByTableNameAndTableNameIdInListAndWorkFlowStateInList(GeneConstants.DOMAIN_EIA_CONTRACT_LOG, eiaContracLogIdList, [WorkFlowConstants.WORKFLOW_START, WorkFlowConstants.WORKFLOW_UNDER_WAY])
        if (eiaContractLogBusi) {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_CON_FLOW_HAVE_DOING] as JSON)
            return
        }

        def workFlowCode = eiaWorkFlowContractService.getContractWorkFlowCode(eiaContractLog.eiaContractId)
        if (workFlowCode) {
            def eiaWorkFlowBusi = eiaWorkFlowContractLogService.startContractLogWorkFlow("CHANGE_" + workFlowCode, GeneConstants.DOMAIN_EIA_CONTRACT_LOG, eiaContractLogId, WorkFlowConstants.CHANGE_CONTRACT_WORKFLOW_START_NODE, session)
            if (eiaWorkFlowBusi) {
                eiaContractLog = eiaContractLogService.eiaContractLogSub(eiaContractLogId)
                if (eiaContractLog && eiaWorkFlowBusi) {
                    render([code: HttpMesConstants.CODE_OK, data: eiaContractLog] as JSON)
                } else {
                    render([code: HttpMesConstants.CODE_FAIL] as JSON)
                }
            } else {
                render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_FCONF_NULL] as JSON)
            }
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_CONTRACT_NO_FEE] as JSON)
        }
    }
    /**********************合同中止***************************/
    def eiaContractHaltCheck(){
        def eiaContractId = params.long("eiaContractId")
        def eiaContractLog = EiaContractLog.findByIfDelAndIfHaltAndEiaContractId(false,true,eiaContractId)
        if(eiaContractLog){
            render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_CON_FLOW_HALT_CHECK, data: eiaContractLog] as JSON)
        }else{
            render([code: HttpMesConstants.CODE_FAIL, data: eiaContractLog] as JSON)
        }
    }
    /**
     * 合同中止信息保存
     */
    def eiaContractHaltSave() {
        def eiaContractId = params.long("eiaContractId")
        /** 先保存变更前的合同信息 */
        def eiaContractLog = this.copyContractBefore(eiaContractId)
        if (eiaContractLog) {
            eiaContractLog.ifHalt = true
            if (Boolean.valueOf(params.ifDirectly)) {
                eiaContractLog.ifHaltEnd = true
                eiaContractLogService.contractWorkFlowHalt(eiaContractId)
            }
            eiaContractLog.save(flush: true, failOnError: true)
            if (eiaContractLogService.copyProjectAfter(eiaContractId, eiaContractLog.id, session)) {
                render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SAVE_OK, data: eiaContractLog] as JSON)
            } else {
                render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
            }
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
        }
    }

    /***
     * 提交合同中止并开启工作流
     * @return
     */
    def eiaContractHaltSub() {
        long eiaContractLogId = Long.valueOf(params.eiaContractLogId)
        def eiaContractLog = EiaContractLog.findByIdAndIfDel(eiaContractLogId, false)
        if (eiaContractLog.ifSub) {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_CERT_FLOW_SEC_SUB] as JSON)
            return
        }
        def eiaWorkFlowBusi = eiaWorkFlowContractLogService.startContractLogWorkFlow(WorkFlowConstants.HALT_CONTRACT_WORKFLOW, GeneConstants.DOMAIN_EIA_CONTRACT_LOG, eiaContractLogId, WorkFlowConstants.CHANGE_CONTRACT_WORKFLOW_START_NODE, session)
        if (eiaWorkFlowBusi) {
            eiaContractLog = eiaContractLogService.eiaContractLogSub(eiaContractLogId)
            if (eiaContractLog && eiaWorkFlowBusi) {
                render([code: HttpMesConstants.CODE_OK, data: eiaContractLog] as JSON)
            } else {
                render([code: HttpMesConstants.CODE_FAIL] as JSON)
            }
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_FCONF_NULL] as JSON)
        }

    }

    /**
     * 合同终止删除
     */
    def eiaContractLogDel() {
        def eiaOffer = eiaContractLogService.eiaContractLogDel(params.long("contractId"))
        if (eiaOffer) {
            render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_DEL_OK, data: eiaOffer] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DEL_FAIL] as JSON)
        }
    }
}
