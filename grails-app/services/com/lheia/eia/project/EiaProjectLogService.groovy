package com.lheia.eia.project

import com.lheia.eia.common.FuncConstants
import com.lheia.eia.common.GeneConstants
import com.lheia.eia.config.EiaDomainCode
import grails.gorm.transactions.Transactional

@Transactional
class EiaProjectLogService {

    def eiaProjectService

    /**
     * 项目变更保存
     */
    def eiaProjectLogSave(eiaProjectLogId, params, session) {
        /** 修改项目信息 */
        def eiaProject = eiaProjectService.eiaProjectUpdate(params, session)
        if (eiaProject) {
            this.copyProjectAfter(eiaProject.id, eiaProjectLogId, session)
        }
    }
    /**
     * 获取项目变更详情
     */
    def getEiaProjectLogDataMap(eiaProjectLogId) {
        return EiaProjectLog.findById(eiaProjectLogId)
    }
    /**
     * 获取环保咨询项目信息
     * @param params
     */
    def getEnvProLogDataMap(params) {
        def eiaEnvLog
        def eiaEneLog
        def eiaGreenLog
        if (!params.eiaProjectLogId) {
            return false
        } else {
            eiaEnvLog = EiaEnvProjectLog.findByEiaProjectLogIdAndIfDel(params.long('eiaProjectLogId'), false)
            if (!eiaEnvLog) {
                eiaEneLog = EiaEneProjectLog.findByEiaProjectLogIdAndIfDel(params.long('eiaProjectLogId'), false)
                if (!eiaEneLog) {
                    eiaGreenLog = EiaGreenProjectLog.findByEiaProjectLogIdAndIfDel(params.long('eiaProjectLogId'), false)
                }
            }
        }
        def dataMap
        def fileTypeCodeEd = EiaProjectLog.findById(params.long('eiaProjectLogId')).fileTypeCodeEd
        if (fileTypeCodeEd == "EPC_HB") {
            dataMap = this.combNeedMap(eiaEnvLog.properties, GeneConstants.HBPROPLOGLIST)
        } else if (fileTypeCodeEd == "EPC_YS") {
            dataMap = this.combNeedMap(eiaEnvLog.properties, GeneConstants.YSPROPLOGLIST)
        } else if (fileTypeCodeEd == "EPC_YA") {
            dataMap = this.combNeedMap(eiaEnvLog.properties, GeneConstants.YAPROPLOGLIST)
        } else if (fileTypeCodeEd == "EPC_JL") {
            dataMap = this.combNeedMap(eiaEnvLog.properties, GeneConstants.JLPROPLOGLIST)
        } else if (fileTypeCodeEd == "EPC_GH") {
            dataMap = this.combNeedMap(eiaEnvLog.properties, GeneConstants.GHPROPLOGLIST)
        } else if (fileTypeCodeEd == "EPC_HH") {
            dataMap = this.combNeedMap(eiaEnvLog.properties, GeneConstants.HHPROPLOGLIST)
        } else if (fileTypeCodeEd == "EPC_SH") {
            dataMap = this.combNeedMap(eiaEnvLog.properties, GeneConstants.SHPROPLOGLIST)
        } else if (fileTypeCodeEd == "EPC_XZ") {
            dataMap = this.combNeedMap(eiaEnvLog.properties, GeneConstants.XZPROPLOGLIST)
        } else if (fileTypeCodeEd == "EPC_PF") {
            dataMap = this.combNeedMap(eiaEnvLog.properties, GeneConstants.PFPROPLOGLIST)
        } else if (fileTypeCodeEd == "EPC_CD") {
            dataMap = this.combNeedMap(eiaEnvLog.properties, GeneConstants.CDPROPLOGLIST)
        } else if (fileTypeCodeEd == "EPC_ST") {
            dataMap = this.combNeedMap(eiaEnvLog.properties, GeneConstants.STPROPLOGLIST)
        } else if (fileTypeCodeEd == "EPC_HP") {
            dataMap = this.combNeedMap(eiaEnvLog?.properties, GeneConstants.HPPROPLOGLIST)
        } else if (fileTypeCodeEd == "EPC_HY") {
            dataMap = this.combNeedMap(eiaEnvLog.properties, GeneConstants.HYPROPLOGLIST)
        } else if (fileTypeCodeEd == "ESE_LZ") {
            dataMap = this.combNeedMap(eiaEneLog.properties, GeneConstants.ESELZPROPLOGLIST)
        } else if (GeneConstants.GREENLIST.contains(fileTypeCodeEd)) {
            dataMap = this.combNeedMap(eiaGreenLog.properties, GeneConstants.GREENPROPLOGLIST)
        }
        return dataMap
    }
    /**
     * 获取项目子表信息
     */
    def getEneOrEnvOrGreenLogDataMap(eiaProjectLogId) {
        def eiaEnvProjectLog = EiaEnvProjectLog.findByEiaProjectLogIdAndIfDel(eiaProjectLogId, false)
        def eiaEneProjectLog = EiaEneProjectLog.findByEiaProjectLogIdAndIfDel(eiaProjectLogId, false)
        def eiaGreenProjectLog = EiaGreenProjectLog.findByEiaProjectLogIdAndIfDel(eiaProjectLogId, false)
        if (eiaEnvProjectLog) {
            return eiaEnvProjectLog
        } else if (eiaEneProjectLog) {
            return eiaEneProjectLog
        } else if (eiaGreenProjectLog) {
            return eiaGreenProjectLog
        } else {
            return false
        }
    }
    /**
     * 把需要的字段组装成新的map
     * @param map
     * @param ndProp
     * @return
     */
    def combNeedMap(Object map, List<String> ndProp) {
        def currMap = [:]
        ndProp.each {
            currMap.put(it, map.getAt(it))
        }
        return currMap
    }
    /**
     * 项目变更列表
     */
    def eiaProjectLogQueryPage(params, session) {
        int page
        int limit
        if (params.page && params.limit) {
            page = params.int('page') - 1
            limit = params.int('limit')
        }
        def eiaProjectLogList = EiaProjectLog.createCriteria().list(max: limit, offset: page * limit) {
            def projectName = params.projectName
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
                    like("inputDeptCode", "%"+ session.staff.orgCode +"%")
                }
                /**
                 * 查看本人客户数据
                 */
                else if (session?.staff?.funcCode?.contains(FuncConstants.EIA_YWCX_HTCX_VIEWSELF)) {
                    eq ("inputUserId", Long.valueOf(session.staff.staffId))
                }
            }

            if (projectName && !"项目名称、项目编号、项目负责人、录入部门、录入人".equals(projectName)) {
                or{
                    like("projectNameEd", "%" + projectName + "%")
                    like("projectNo", "%" + projectName + "%")
                    like("inputDept", "%" + projectName + "%")
                    like("inputUser", "%" + projectName + "%")
                    like("dutyUserEd", "%" + projectName + "%")
                }
            }
            def fileType =params["key[fileType]"]
            if(fileType){
                like("fileTypeChildEd", "%" + fileType + "%")
            }
            def buildArea =params["key[buildArea]"]
            if(buildArea){
                like("buildAreaEd", "%" + buildArea + "%")
            }
            eq("ifDel", false)
            order("id", "desc")
        }
        def eiaProjectLogData = []
        eiaProjectLogList.each {
            def map = [:]
            map.id = it.id
            map.fileTypeChild = it?.fileTypeChildEd
            map.projectMoney = it?.projectMoneyEd
            map.projectNameEd = it?.projectNameEd
            map.buildAreaEd = it?.buildAreaEd
            map.gisProjectId = it?.gisProjectId
            map.projectNo = it?.projectNo
            map.fileType = it?.fileTypeEd
            map.inputDept = it?.inputDept
            map.inputUser = it?.inputUser
            map.dutyUserEd = it?.dutyUserEd
            map.eiaTaskId = it?.eiaTaskId
            eiaProjectLogData << map
        }
        def dataMap = [:]
        dataMap.data = eiaProjectLogData
        dataMap.total = eiaProjectLogList.totalCount
        return dataMap
    }
    /**
     * 保存项目变更后信息
     */
    def copyProjectAfter(eiaProjectId, eiaProjectLogId, session) {
        def eiaProject = EiaProject.findById(eiaProjectId)
        def eiaProjectLog = EiaProjectLog.findById(eiaProjectLogId)
        if (eiaProject && eiaProjectLog) {
            eiaProjectLog.projectNameEd = eiaProject.projectName
            eiaProjectLog.fileTypeEd = eiaProject.fileType
            eiaProjectLog.fileTypeCodeEd = eiaProject.fileTypeCode
            eiaProjectLog.fileTypeChildEd = eiaProject.fileTypeChild
            eiaProjectLog.fileTypeChildCodeEd = eiaProject.fileTypeChildCode
            eiaProjectLog.projectMoneyEd = eiaProject.projectMoney
            eiaProjectLog.eiaClientIdEd = eiaProject.eiaClientId
            eiaProjectLog.eiaClientNameEd = eiaProject.eiaClientName
            eiaProjectLog.eiaContractIdEd = eiaProject.eiaContractId
            eiaProjectLog.contractNameEd = eiaProject.contractName
            eiaProjectLog.contractNoEd = eiaProject.contractNo
            eiaProjectLog.projectComfeeEd = eiaProject.projectComfee
            eiaProjectLog.environmentalFeeEd = eiaProject.environmentalFee
            eiaProjectLog.groundwaterFeeEd = eiaProject.groundwaterFee
            eiaProjectLog.expertFeeEd = eiaProject.expertFee
            eiaProjectLog.otherFeeEd = eiaProject.otherFee
            eiaProjectLog.specialFeeEd = eiaProject.specialFee
            eiaProjectLog.detectFeeEd = eiaProject.detectFee
            eiaProjectLog.preIssCertFeeEd = eiaProject.preIssCertFee
            eiaProjectLog.preSurvCertFeeEd = eiaProject.preSurvCertFee
            eiaProjectLog.certServeFeeEd = eiaProject.certServeFee
            eiaProjectLog.projectTypeEd = eiaProject.projectType
            eiaProjectLog.competentDeptEd = eiaProject.competentDept
            eiaProjectLog.buildAreaEd = eiaProject.buildArea
            eiaProjectLog.coordEastEd = eiaProject.coordEast
            eiaProjectLog.coordNorthEd = eiaProject.coordNorth
            eiaProjectLog.coordStartNorthEd = eiaProject.coordStartNorth
            eiaProjectLog.coordStartEastEd = eiaProject.coordStartEast
            eiaProjectLog.coordEndEastEd = eiaProject.coordEndEast
            eiaProjectLog.coordEndNorthEd = eiaProject.coordEndNorth
            eiaProjectLog.dutyUserEd = eiaProject.dutyUser
            eiaProjectLog.dutyUserIdEd = eiaProject.dutyUserId
            /** 日志录入时间、人员 */
            eiaProjectLog.logInputDept = session.staff.orgName
            eiaProjectLog.logInputDeptId = Long.parseLong(session.staff.orgId)
            eiaProjectLog.logInputUser = session.staff.staffName
            eiaProjectLog.logInputUserId = Long.parseLong(session.staff.staffId)
            if (eiaProjectLog.save(flush: true, failOnError: true)) {
                /** 更新项目子表信息 */
                def envList = GeneConstants.ENVLIST
                def eseList = GeneConstants.ESELIST
                def greenList = GeneConstants.GREENLIST
                def parentCode = EiaDomainCode.findByDomainAndCode(GeneConstants.PROJECT_FILE_TYPE, eiaProjectLog.fileTypeChildCodeEd)?.parentCode
                if (parentCode in envList) {
                    def eiaEnvProject = EiaEnvProject.findByEiaProjectIdAndIfDel(eiaProject.id, false)
                    def eiaEnvProjectLog = EiaEnvProjectLog.findByEiaProjectIdAndEiaProjectLogId(eiaProject.id, eiaProjectLog.id)
                    /** 若环保咨询变更日志不存在，说明之前的文件类型不是环评，需要把项目之前对应的子表删掉 */
                    if (!eiaEnvProjectLog) {
                        def eiaEneProjectLog = EiaEneProjectLog.findByEiaProjectIdAndEiaProjectLogId(eiaProject.id, eiaProjectLog.id)
                        if (eiaEneProjectLog) {
                            eiaEneProjectLog.ifDel = true
                            eiaEneProjectLog.save(flush: true, failOnError: true)
                        } else {
                            def eiaGreenProjectLog = EiaGreenProjectLog.findByEiaProjectIdAndEiaProjectLogId(eiaProject.id, eiaProjectLog.id)
                            eiaGreenProjectLog.ifDel = true
                            eiaGreenProjectLog.save(flush: true, failOnError: true)
                        }
                        eiaEnvProjectLog = new EiaEnvProjectLog()
                    }
                    eiaEnvProjectLog.eiaProjectId = eiaProject.id
                    eiaEnvProjectLog.eiaProjectLogId = eiaProjectLog.id
                    eiaEnvProjectLog.eiaEnvProjectIdEd = eiaEnvProject.id
                    eiaEnvProjectLog.natureConstructioEd = eiaEnvProject.natureConstructio
                    eiaEnvProjectLog.projectCodeEd = eiaEnvProject.projectCode
                    eiaEnvProjectLog.industryTypeEd = eiaEnvProject.industryType
                    eiaEnvProjectLog.industryTypeCodeEd = eiaEnvProject.industryTypeCode
                    eiaEnvProjectLog.industryTypeDescEd = eiaEnvProject.industryTypeDesc
                    eiaEnvProjectLog.environmentaTypeEd = eiaEnvProject.environmentaType
                    eiaEnvProjectLog.environmentaTypeCodeEd = eiaEnvProject.environmentaTypeCode
                    eiaEnvProjectLog.environmentaTypeDescEd = eiaEnvProject.environmentaTypeDesc
                    eiaEnvProjectLog.productionEngineerEd = eiaEnvProject.productionEngineer
                    eiaEnvProjectLog.productFunctionEd = eiaEnvProject.productFunction
                    eiaEnvProjectLog.parkPlanningEd = eiaEnvProject.parkPlanning
                    eiaEnvProjectLog.projectInvestMoneyEd = eiaEnvProject.projectInvestMoney
                    eiaEnvProjectLog.seaFileNameEd = eiaEnvProject.seaFileName
                    eiaEnvProjectLog.seaReviewOrgEd = eiaEnvProject.seaReviewOrg
                    eiaEnvProjectLog.seaReviewNoEd = eiaEnvProject.seaReviewNo
                    eiaEnvProjectLog.contentScaleEd = eiaEnvProject.contentScale
                    eiaEnvProjectLog.planAreaEd = eiaEnvProject.planArea
                    eiaEnvProjectLog.projectMemoEd = eiaEnvProject.projectMemo
                    eiaEnvProjectLog.ifSensAreaEd = eiaEnvProject.ifSensArea
                    eiaEnvProjectLog.ifCityPlanEd = eiaEnvProject.ifCityPlan
                    eiaEnvProjectLog.exploreDateEd = eiaEnvProject.exploreDate
                    eiaEnvProjectLog.exploreRecordEd = eiaEnvProject.exploreRecord
                    eiaEnvProjectLog.ifDel = false
                    eiaEnvProjectLog.inputDept = session.staff.orgName
                    eiaEnvProjectLog.inputDeptCode = session.staff.orgCode
                    eiaEnvProjectLog.inputDeptId = Long.parseLong(session.staff.orgId)
                    eiaEnvProjectLog.inputUser = session.staff.staffName
                    eiaEnvProjectLog.inputUserId = Long.parseLong(session.staff.staffId)
                    /** 日志录入时间、人员 */
                    eiaEnvProjectLog.logInputDept = session.staff.orgName
                    eiaEnvProjectLog.logInputDeptCode = session.staff.orgCode
                    eiaEnvProjectLog.logInputDeptId = Long.parseLong(session.staff.orgId)
                    eiaEnvProjectLog.logInputUser = session.staff.staffName
                    eiaEnvProjectLog.logInputUserId = Long.parseLong(session.staff.staffId)
                    eiaEnvProjectLog.save(flush: true, failOnError: true)
                } else if (parentCode in eseList) {
                    def eiaEneProject = EiaEneProject.findByEiaProjectIdAndIfDel(eiaProject.id, false)
                    def eiaEneProjectLog = EiaEneProjectLog.findByEiaProjectIdAndEiaProjectLogId(eiaProject.id, eiaProjectLog.id)
                    /** 若节能评估变更日志不存在，说明之前的文件类型不是能评，需要把项目之前对应的子表删掉 */
                    if (!eiaEneProjectLog) {
                        def eiaEnvProjectLog = EiaEnvProjectLog.findByEiaProjectIdAndEiaProjectLogId(eiaProject.id, eiaProjectLog.id)
                        if (eiaEnvProjectLog) {
                            eiaEnvProjectLog.ifDel = true
                            eiaEnvProjectLog.save(flush: true, failOnError: true)
                        } else {
                            def eiaGreenProjectLog = EiaGreenProjectLog.findByEiaProjectIdAndEiaProjectLogId(eiaProject.id, eiaProjectLog.id)
                            eiaGreenProjectLog.ifDel = true
                            eiaGreenProjectLog.save(flush: true, failOnError: true)
                        }
                        eiaEneProjectLog = new EiaEneProjectLog()
                    }
                    eiaEneProjectLog.eiaProjectId = eiaProject.id
                    eiaEneProjectLog.eiaProjectLogId = eiaProjectLog.id
                    eiaEneProjectLog.eiaEneProjectIdEd = eiaEneProject.id
                    eiaEneProjectLog.industryTypeEd = eiaEneProject.industryType
                    eiaEneProjectLog.industryTypeCodeEd = eiaEneProject.industryTypeCode
                    eiaEneProjectLog.industryTypeDescEd = eiaEneProject.industryTypeDesc
                    eiaEneProjectLog.contentScaleEd = eiaEneProject.contentScale
                    eiaEneProjectLog.ifDel = false
                    eiaEneProjectLog.inputDept = session.staff.orgName
                    eiaEneProjectLog.inputDeptCode = session.staff.orgCode
                    eiaEneProjectLog.inputDeptId = Long.parseLong(session.staff.orgId)
                    eiaEneProjectLog.inputUser = session.staff.staffName
                    eiaEneProjectLog.inputUserId = Long.parseLong(session.staff.staffId)
                    /** 日志录入时间、人员 */
                    eiaEneProjectLog.logInputDept = session.staff.orgName
                    eiaEneProjectLog.logInputDeptCode = session.staff.orgCode
                    eiaEneProjectLog.logInputDeptId = Long.parseLong(session.staff.orgId)
                    eiaEneProjectLog.logInputUser = session.staff.staffName
                    eiaEneProjectLog.logInputUserId = Long.parseLong(session.staff.staffId)
                    eiaEneProjectLog.save(flush: true, failOnError: true)
                } else if (parentCode in greenList) {
                    def eiaGreenProject = EiaGreenProject.findByEiaProjectIdAndIfDel(eiaProject.id, false)
                    def eiaGreenProjectLog = EiaGreenProjectLog.findByEiaProjectIdAndEiaProjectLogId(eiaProject.id, eiaProjectLog.id)
                    /** 若绿色金融变更日志不存在，说明之前的文件类型不是绿金，需要把项目之前对应的子表删掉 */
                    if (!eiaGreenProjectLog) {
                        def eiaEnvProjectLog = EiaEnvProjectLog.findByEiaProjectIdAndEiaProjectLogId(eiaProject.id, eiaProjectLog.id)
                        if (eiaEnvProjectLog) {
                            eiaEnvProjectLog.ifDel = true
                            eiaEnvProjectLog.save(flush: true, failOnError: true)
                        } else {
                            def eiaEneProjectLog = EiaEneProjectLog.findByEiaProjectIdAndEiaProjectLogId(eiaProject.id, eiaProjectLog.id)
                            eiaEneProjectLog.ifDel = true
                            eiaEneProjectLog.save(flush: true, failOnError: true)
                        }
                        eiaGreenProjectLog = new EiaGreenProjectLog()
                    }
                    eiaGreenProjectLog.eiaProjectId = eiaProject.id
                    eiaGreenProjectLog.eiaProjectLogId = eiaProjectLog.id
                    eiaGreenProjectLog.eiaGreenProjectIdEd = eiaGreenProject.id
                    eiaGreenProjectLog.publishMoneyEd = eiaGreenProject.publishMoney
                    eiaGreenProjectLog.publishYearEd = eiaGreenProject.publishYear
                    eiaGreenProjectLog.bondCodeEd = eiaGreenProject.bondCode
                    eiaGreenProjectLog.bondTypeEd = eiaGreenProject.bondType
                    eiaGreenProjectLog.bondNameEd = eiaGreenProject.bondName
                    eiaGreenProjectLog.ifCompFileEd = eiaGreenProject.ifCompFile
                    eiaGreenProjectLog.ifPublishCompleteEd = eiaGreenProject.ifPublishComplete
                    eiaGreenProjectLog.ifIndPolicyEd = eiaGreenProject.ifIndPolicy
                    eiaGreenProjectLog.ifGreenCatalogEd = eiaGreenProject.ifGreenCatalog
                    eiaGreenProjectLog.ifGuarFundEd = eiaGreenProject.ifGuarFund
                    eiaGreenProjectLog.ifDel = false
                    eiaGreenProjectLog.inputDept = session.staff.orgName
                    eiaGreenProjectLog.inputDeptCode = session.staff.orgCode
                    eiaGreenProjectLog.inputDeptId = Long.parseLong(session.staff.orgId)
                    eiaGreenProjectLog.inputUser = session.staff.staffName
                    eiaGreenProjectLog.inputUserId = Long.parseLong(session.staff.staffId)
                    /** 日志录入时间、人员 */
                    eiaGreenProjectLog.logInputDept = session.staff.orgName
                    eiaGreenProjectLog.logInputDeptCode = session.staff.orgCode
                    eiaGreenProjectLog.logInputDeptId = Long.parseLong(session.staff.orgId)
                    eiaGreenProjectLog.logInputUser = session.staff.staffName
                    eiaGreenProjectLog.logInputUserId = Long.parseLong(session.staff.staffId)
                    eiaGreenProjectLog.save(flush: true, failOnError: true)
                }
            }
            return eiaProjectLog
        }
    }
    /**
     * 获取项目所需要的金额的字段
     */
    def getProjectLogMoneyShow(params) {
        def eiaProjectLog
        def projectType
        if (params.eiaProjectLogId) {
            eiaProjectLog = EiaProjectLog.findByIdAndIfDel(params.long('eiaProjectLogId'), false)
            projectType = eiaProjectLog.fileTypeCodeEd
        }
        def dataMap = [:]
        if (projectType in GeneConstants.GREENLIST) {
            if (projectType == "GREEN_LZ") {
                dataMap = this.combNeedMap(eiaProjectLog?.properties, GeneConstants.PRO_LOG_LZ_MONEY_LIST)
            } else if (projectType == "GREEN_LQ") {
                dataMap = this.combNeedMap(eiaProjectLog?.properties, GeneConstants.PRO_LOG_LQ_MONEY_LIST)
            } else {
                dataMap = this.combNeedMap(eiaProjectLog?.properties, GeneConstants.PRO_LOG_GREEN_MONEY_LIST)
            }
        } else if (projectType in GeneConstants.ESELIST) {
            dataMap = this.combNeedMap(eiaProjectLog?.properties, GeneConstants.PRO_LOG_ESE_MONEY_LIST)
        } else if (projectType == 'EPC_HP') {
            dataMap = this.combNeedMap(eiaProjectLog?.properties, GeneConstants.PRO_LOG_HP_MONEY_LIST)
        } else if (projectType == 'EPC_HY') {
            dataMap = this.combNeedMap(eiaProjectLog?.properties, GeneConstants.PRO_LOG_HY_MONEY_LIST)
        } else if (projectType == 'EPC_HB') {
            dataMap = this.combNeedMap(eiaProjectLog?.properties, GeneConstants.PRO_LOG_HB_MONEY_LIST)
        } else if (projectType == 'EPC_YS') {
            dataMap = this.combNeedMap(eiaProjectLog?.properties, GeneConstants.PRO_LOG_YS_MONEY_LIST)
        } else if (projectType == 'EPC_YA') {
            dataMap = this.combNeedMap(eiaProjectLog?.properties, GeneConstants.PRO_LOG_YA_MONEY_LIST)
        } else if (projectType == 'EPC_JL') {
            dataMap = this.combNeedMap(eiaProjectLog?.properties, GeneConstants.PRO_LOG_JL_MONEY_LIST)
        } else if (projectType == 'EPC_GH') {
            dataMap = this.combNeedMap(eiaProjectLog?.properties, GeneConstants.PRO_LOG_GH_MONEY_LIST)
        } else if (projectType == 'EPC_HH') {
            dataMap = this.combNeedMap(eiaProjectLog?.properties, GeneConstants.PRO_LOG_HH_MONEY_LIST)
        } else if (projectType == 'EPC_SH') {
            dataMap = this.combNeedMap(eiaProjectLog?.properties, GeneConstants.PRO_LOG_SH_MONEY_LIST)
        } else if (projectType == 'EPC_XZ') {
            dataMap = this.combNeedMap(eiaProjectLog?.properties, GeneConstants.PRO_LOG_XZ_MONEY_LIST)
        } else if (projectType == 'EPC_PF') {
            dataMap = this.combNeedMap(eiaProjectLog?.properties, GeneConstants.PRO_LOG_PF_MONEY_LIST)
        } else if (projectType == 'EPC_ST') {
            dataMap = this.combNeedMap(eiaProjectLog?.properties, GeneConstants.PRO_LOG_ST_MONEY_LIST)
        } else if (projectType == 'EPC_CD') {
            dataMap = this.combNeedMap(eiaProjectLog?.properties, GeneConstants.PRO_LOG_CD_MONEY_LIST)
        }
        return  dataMap
    }
}
