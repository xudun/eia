package com.lheia.eia.cert

import com.lheia.eia.common.FuncConstants
import com.lheia.eia.common.GeneConstants
import com.lheia.eia.common.HttpMesConstants
import com.lheia.eia.common.WorkFlowConstants
import com.lheia.eia.contract.EiaContract
import com.lheia.eia.project.EiaProject
import com.lheia.eia.tools.DateTransTools
import com.lheia.eia.workflow.EiaWorkFlowBusi
import grails.gorm.transactions.Transactional

@Transactional
class EiaCertService {
    /**
     * 保存申请资质
     * @param params
     * @param session
     * @return
     */
    def eiaCertSave(params, session) {
        def eiaProjectId = Long.valueOf(params.eiaProjectId)
        def eiaProject = EiaProject.findByIfDelAndId(false, eiaProjectId)
        if (params.certType == GeneConstants.CERT_TYPE_APPROVAL && !eiaProject.eiaContractId) {
            return [code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_CERT_APPROVAL_NO_CON]
        }
        def eiaCert = new EiaCert()
        eiaCert.properties = eiaProject.properties
        eiaCert.eiaProjectId = eiaProjectId
        eiaCert.applyDate = DateTransTools.getFormatDateD(params.applyDate)
        if(params.meetDate){
            eiaCert.meetDate = DateTransTools.getFormatDateD(params.meetDate)
        }
        eiaCert.ifApplyCert = Boolean.valueOf(params.ifApplyCert)
        eiaCert.certType = params.certType
        eiaCert.certNum = Integer.valueOf(params.certNum)
        if (session.staff) {
            eiaCert.inputDept = session.staff.orgName
            eiaCert.inputDeptId = Long.parseLong(session.staff.orgId)
            eiaCert.inputUser = session.staff.staffName
            eiaCert.inputUserId = Long.parseLong(session.staff.staffId)
            eiaCert.inputDeptCode = session.staff.orgCode
        }
        if(eiaCert.eiaContractId){
            def contractTrust = EiaContract.findByIdAndIfDel(eiaCert.eiaContractId,false).contractTrust
            StringBuilder stampType = new StringBuilder()
            if(contractTrust == GeneConstants.CONTRACT_TRUST_LHTZ){
                stampType.append("LHTZ,")
            }
            if(contractTrust == GeneConstants.CONTRACT_TRUST_LHCD){
                stampType.append("LHCD,")
            }
            eiaCert.stampType = stampType.toString()
        }
        eiaCert = eiaCert.save(flush: true, failOnError: true)
        if (eiaCert) {
            return [code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SAVE_OK, data: eiaCert]
        } else {
            return [code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL]
        }

    }

    def checkCert(params) {
        def eiaCert = EiaCert.findByEiaProjectIdAndCertTypeAndIfApplyCertAndIfDel(Long.valueOf(params.eiaProjectId), params.certType, Boolean.valueOf(params.ifApplyCert), false)
        return eiaCert
    }
    /**
     * 重复提交
     * @return
     */
    def eiaCertRepeatSub(params) {
        long parentEiaCertId = Long.valueOf(params.parentEiaCertId)
        def parentEiaCert = EiaCert.findById(parentEiaCertId);
        def eiaCert = new EiaCert()
        eiaCert.properties = parentEiaCert.properties
        eiaCert.parentEiaCertId = parentEiaCertId
        eiaCert.applyDate = DateTransTools.getFormatDateD(params.applyDate)
        if(params.meetDate){
            eiaCert.meetDate = DateTransTools.getFormatDateD(params.meetDate)
        }
        eiaCert.ifSub = false
        eiaCert.ifEnd = false
        eiaCert.certNum = Integer.valueOf(params.certNum)
        eiaCert.save(flush: true, failOnError: true)
    }

    /**
     * 申请资质列表查询
     */
    def eiaCertQuery(params, session) {
        int page
        int limit
        if (params.page && params.limit) {
            page = params.int('page') - 1
            limit = params.int('limit')
        }
        def eiaCertList = EiaCert.createCriteria().list(max: limit, offset: page * limit) {
            /**
             * 查看全部的机构数据
             */
            if (!session?.staff?.funcCode?.contains(FuncConstants.EIA_YWCX_HTCX_VIEWALL)) {
                /**
                 * 查看本部门数据
                 */
                if (session?.staff?.funcCode?.contains(FuncConstants.EIA_YWCX_HTCX_VIEWDEPT)) {
                    like("inputDeptCode", '%' + session.staff.orgCode + '%')
                }
                /**
                 * 查看本人数据
                 */
                else if (session?.staff?.funcCode?.contains(FuncConstants.EIA_YWCX_HTCX_VIEWSELF)) {
                    eq("inputUserId", Long.valueOf(session.staff.staffId))
                }
            }
            eq("ifDel", false)
            if (params.projectName) {
                or {
                    like("projectName", '%' + params.projectName + "%")
                    like("projectNo", '%' + params.projectName + "%")
                    like("inputDept", '%' + params.projectName + "%")
                    like("inputUser", '%' + params.projectName + "%")
                }
            }
            order("id", "desc")
        }
        def resList = []
        def dataMap = [:]

        eiaCertList.each{
            def resMap = [:]
            resMap.putAll(it.properties)
            resMap.id = it.id
            def eiaWorkFlowBusi = EiaWorkFlowBusi.findByIfDelAndTableNameAndTableNameIdAndWorkFlowStateNotEqual(false,GeneConstants.DOMAIN_EIA_CERT,it.id,WorkFlowConstants.WORKFLOW_HALT)
            resMap.status = ((eiaWorkFlowBusi?.nodesName) ? (eiaWorkFlowBusi?.nodesName) :"流程未启动")
            resList << resMap
        }
        dataMap.totalCount = eiaCertList.totalCount
        dataMap.data = resList
       return dataMap

    }
    /**
     * 查看详情
     * @param params
     * @return
     */
    def getEiaCertMap(params) {
        def eiaCertId = Long.valueOf(params.eiaCertId)
        def eiaCert = EiaCert.findByIfDelAndId(false, eiaCertId)
        def dataMap = [:]
        dataMap.putAll(eiaCert.properties)
        dataMap.applyDate = dataMap.applyDate?.format("yyyy-MM-dd")
        dataMap.meetDate = dataMap.meetDate?.format("yyyy-MM-dd")
        return dataMap
    }
    /**
     * 提交资质申请
     */
    def eiaCertSub(Long eiaContractId) {
        def eiaCert = EiaCert.findByIfDelAndId(false, eiaContractId)
        eiaCert.ifSub = true
        eiaCert.save(flush: true, failOnError: true)
    }
    /**
     * 保存资质印章申请单印章类型和特殊说明
     */
    def eiaCertUpdate(params) {
        long eiaCertId = Long.valueOf(params.eiaCertId)
        def eiaCert = EiaCert.findByIdAndIfDel(eiaCertId, false)
        if(params.stampType){
            eiaCert.stampType = params.stampType
        }
        if(params.stampNote){
            eiaCert.stampNote = params.stampNote
        }
        if(params.certNum){
            eiaCert.certNum = Integer.valueOf(params.certNum)
        }
        if(params.applyDate){
            eiaCert.applyDate = DateTransTools.getFormatDateD(params.applyDate)
        }
        if(params.meetDate){
            eiaCert.meetDate = DateTransTools.getFormatDateD(params.meetDate)
        }
        eiaCert.save(flush: true, failOnError: true)
    }

    /**
     * 资质删除
     * @return
     */
    def eiaCertDel(params) {
        long eiaCertId = Long.valueOf(params.eiaCertId)
        def eiaCert = EiaCert.findByIdAndIfDel(eiaCertId, false)
        eiaCert.ifDel = true
        eiaCert.save(flush: true, failOnError: true)
    }
}
