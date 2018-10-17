package com.lheia.eia.cert

import com.lheia.eia.common.GeneConstants
import com.lheia.eia.common.HttpMesConstants
import com.lheia.eia.common.WorkFlowConstants
import com.lheia.eia.contract.EiaContract
import com.lheia.eia.finance.EiaIncomeOut
import com.lheia.eia.project.EiaProject
import grails.converters.JSON

class EiaCertController {
    /**
     * 资质申请
     */
    def eiaCertIndex() {}
    /**
     * 新增资质
     */
    def eiaCertCreate() {}
    /**
     * 查看资质
     */
    def eiaCertDetail() {
        [eiaCertId: params.eiaCertId]
    }
    /**
     * 查看资质
     */
    def eiaCertEdit() {
        [eiaCertId: params.eiaCertId]
    }
    /**
     * 资质下载
     */
    def eiaCertDown() {
        [eiaCertId: params.eiaCertId]
    }

    def eiaCertRepeat() {
        [eiaCertId: params.eiaCertId]
    }
    /**
     * 盖章说明
     */
    def eiaCertStamp() {}
    /**
     * 盖章说明
     */
    def eiaCertStampPrint() {}
    def eiaProjectService
    def eiaCertService
    def eiaWorkFlowCertService

    def eiaProjectQuery() {
        render([code: HttpMesConstants.CODE_OK, data: eiaProjectService.eiaProjectQuery(session)] as JSON)
    }

    /***
     * 保存资质
     * @return
     */
    def eiaCertSave() {
        if(eiaCertService.checkCert(params)){
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_CERT_FLOW_REPEAT] as JSON)
        }else{
            def resMap = eiaCertService.eiaCertSave(params, session)
            if (resMap) {
                render(resMap as JSON)
            } else {
                render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
            }
        }
    }
    /**
     * 资质列表
     * @return
     */
    def eiaCertQuery() {
        def dataMap = eiaCertService.eiaCertQuery(params, session)
        render([code: HttpMesConstants.CODE_OK, count: dataMap.totalCount, data: dataMap.data] as JSON)
    }

    /**
     * 查看详情
     */
    def getEiaCert() {
        def eiaCert = eiaCertService.getEiaCertMap(params)

        if (eiaCert) {
            render([code: HttpMesConstants.CODE_OK, data: eiaCert] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL] as JSON)
        }
    }

    /**
     * 重复提交
     * @return
     */
    def eiaCertRepeatSub() {
        def eiaCert = eiaCertService.eiaCertRepeatSub(params)
        if (eiaCert) {
            render([code: HttpMesConstants.CODE_OK, data: eiaCert] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL] as JSON)
        }
    }

    /***
     * 提交合同并开启工作流
     * @return
     */
    def eiaCertSub() {
        long eiaCertId = Long.valueOf(params.eiaCertId)
        def eiaCert = EiaCert.findByIdAndIfDel(eiaCertId,false)
        if(eiaCert.ifSub){
            render([code: HttpMesConstants.CODE_FAIL,msg:HttpMesConstants.MSG_CERT_FLOW_SEC_SUB] as JSON)
        }else{
            def workFlowCode
            def startNodeCode
            if (eiaCert.ifApplyCert && eiaCert.certType == GeneConstants.CERT_TYPE_REVIEW) {
                /**出资质送审流程**/
                /**资质申请—出具资质—报告上传—打印报告**/
                workFlowCode = WorkFlowConstants.CERT_WORK_FLOW_CZZSSLC
                startNodeCode = WorkFlowConstants.CERT_WORK_FLOW_NODE_ZZSQ
            } else if (eiaCert.ifApplyCert && eiaCert.certType == GeneConstants.CERT_TYPE_APPROVAL) {
                /**出资质报批流程**/
                /**资质申请—出具资质—报告上传—合规审核—财务审核—打印报告
                 **/
                workFlowCode = WorkFlowConstants.CERT_WORK_FLOW_CZZBPLC
                startNodeCode = WorkFlowConstants.CERT_WORK_FLOW_NODE_ZZSQ
            } else if (!eiaCert.ifApplyCert && eiaCert.certType == GeneConstants.CERT_TYPE_REVIEW) {
                /**无资质送审流程
                 报告上传—打印报告**/
                workFlowCode = WorkFlowConstants.CERT_WORK_FLOW_WZZSSLC
                startNodeCode = WorkFlowConstants.CERT_WORK_FLOW_NODE_BGSC
            } else if (!eiaCert.ifApplyCert && eiaCert.certType == GeneConstants.CERT_TYPE_APPROVAL) {
                /**
                 * 无资质报批流程
                 报告上传—合规审核—财务审核—打印报告
                 */
                workFlowCode = WorkFlowConstants.CERT_WORK_FLOW_WZZBPLC
                startNodeCode = WorkFlowConstants.CERT_WORK_FLOW_NODE_BGSC
            }

            /**如果为重复流程**/
            if (eiaCert.parentEiaCertId) {
                workFlowCode = workFlowCode + "_REPEAT"
                /**如果为无资质的重复申请初始节点为打印报告
                 * 有资质的重复申请初始节点与初次申请相同
                 * ***/
                if(!eiaCert.ifApplyCert){
                    startNodeCode = WorkFlowConstants.CERT_WORK_FLOW_NODE_DYBG
                }
            }
            def eiaWorkFlowBusi = eiaWorkFlowCertService.startCertWorkFlow(workFlowCode, GeneConstants.DOMAIN_EIA_CERT, eiaCertId, startNodeCode, session)
            if (eiaCert && eiaWorkFlowBusi) {
                eiaCertService.eiaCertSub(eiaCertId)
                render([code: HttpMesConstants.CODE_OK, data: eiaCert] as JSON)
            } else {
                render([code: HttpMesConstants.CODE_FAIL,msg:HttpMesConstants.MSG_FCONF_NULL] as JSON)
            }
        }
    }

    def eiaCertStampDataMap(){
        long eiaCertId = Long.valueOf(params.eiaCertId)
        def eiaCert = EiaCert.findById(eiaCertId)
        if(eiaCert){
            def resMap =[:]
            if(eiaCert.eiaContractId&&eiaCert.eiaProjectId){
                def eiaContract = EiaContract.findByIfDelAndId(false,eiaCert.eiaContractId)
                def eiaProject = EiaProject.findByIdAndIfDel(eiaCert.eiaProjectId,false)
                resMap.income = EiaIncomeOut.findAllByCostTypesAndContractId(GeneConstants.INVOICE_TYPE_INCOME,Long.valueOf(eiaCert.eiaContractId)).noteIncomeMoney.sum()
                resMap.proportion = (eiaContract.contractMoney&&resMap.income)?(resMap.income/eiaContract.contractMoney):"暂无数据"
                resMap.stampNote = eiaCert.stampNote
                resMap.stampType = eiaCert.stampType
                resMap.projectName = eiaCert.projectName
                resMap.fileTypeChild =eiaProject.fileTypeChild
                resMap.contractNo = eiaContract.contractNo
                resMap.contractMoney = eiaContract.contractMoney
                resMap.otherFee = eiaContract.otherFee
                resMap.enviroMonitoringFee = eiaContract.enviroMonitoringFee?eiaContract.enviroMonitoringFee:0
                render([code: HttpMesConstants.CODE_OK, data: resMap] as JSON)
            }else{
                render([code: HttpMesConstants.CODE_FAIL,msg:HttpMesConstants.MSG_CERT_NO_CON] as JSON)
            }
        }else{
            render([code: HttpMesConstants.CODE_FAIL,msg:HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
    /**
     * 保存资质印章申请单印章类型和特殊说明
     */
    def eiaCertUpdate() {
    def eiaCert = eiaCertService.eiaCertUpdate(params)
        if(eiaCert){
            render([code: HttpMesConstants.CODE_OK, data: eiaCert,msg:HttpMesConstants.MSG_SAVE_OK] as JSON)
        }else{
            render([code: HttpMesConstants.CODE_FAIL,msg:HttpMesConstants.MSG_SAVE_FAIL] as JSON)
        }
    }
    /**
     * 资质删除
     * @return
     */
    def eiaCertDel(){
        def eiaCert = eiaCertService.eiaCertDel(params)
        if(eiaCert){
            render([code: HttpMesConstants.CODE_OK, data: eiaCert,msg:HttpMesConstants.MSG_DEL_OK] as JSON)
        }else{
            render([code: HttpMesConstants.CODE_FAIL,msg:HttpMesConstants.MSG_DEL_FAIL] as JSON)
        }
    }

    def getEiaCertStampPrintDataMap(){
        long eiaCertId = Long.valueOf(params.eiaCertId)
        def nodeList =[WorkFlowConstants.CERT_WORK_FLOW_NODE_HGSH,WorkFlowConstants.CERT_WORK_FLOW_NODE_CWSH,WorkFlowConstants.CERT_WORK_FLOW_NODE_BMJLSH,WorkFlowConstants.CERT_WORK_FLOW_NODE_ZJLSH]
        def resMap =eiaWorkFlowCertService.getReportList(eiaCertId,nodeList);
        def eiaCert = EiaCert.findById(eiaCertId)
        if(eiaCert){
            def eiaContract = EiaContract.findByIfDelAndId(false,eiaCert.eiaContractId)
            def eiaProject = EiaProject.findByIdAndIfDel(eiaCert.eiaProjectId,false)
            resMap.income = EiaIncomeOut.findAllByCostTypesAndContractId(GeneConstants.INVOICE_TYPE_INCOME,Long.valueOf(eiaCert.eiaContractId)).noteIncomeMoney.sum()
            resMap.proportion = (eiaContract.contractMoney&&resMap.income)?(resMap.income/eiaContract.contractMoney):"暂无数据"
            resMap.stampNote = eiaCert.stampNote
            resMap.stampType = eiaCert.stampType
            resMap.projectName = eiaCert.projectName
            resMap.fileTypeChild =eiaProject.fileTypeChild
            resMap.contractNo = eiaContract.contractNo
            resMap.contractMoney = eiaContract.contractMoney
            resMap.enviroMonitoringFee = eiaContract.enviroMonitoringFee?eiaContract.enviroMonitoringFee:0
            resMap.otherFee = eiaContract.otherFee
            render([code: HttpMesConstants.CODE_OK, data: resMap] as JSON)
        }else{
            render([code: HttpMesConstants.CODE_FAIL] as JSON)
        }
    }
}
