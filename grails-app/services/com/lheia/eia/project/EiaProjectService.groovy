package com.lheia.eia.project

import com.lheia.eia.client.EiaClient
import com.lheia.eia.client.EiaClientContacts
import com.lheia.eia.common.FuncConstants
import com.lheia.eia.common.GeneConstants
import com.lheia.eia.common.HttpMesConstants
import com.lheia.eia.common.HttpUrlConstants
import com.lheia.eia.common.WorkFlowConstants
import com.lheia.eia.config.EiaDomainCode
import com.lheia.eia.config.EiaFileUpload
import com.lheia.eia.contract.EiaContract
import com.lheia.eia.lab.EiaLabOffer
import com.lheia.eia.task.EiaTask
import com.lheia.eia.task.EiaTaskAssign
import com.lheia.eia.tools.HttpConnectTools
import com.lheia.eia.tools.JsonHandler
import com.lheia.eia.workflow.EiaWorkFlowBusi
import grails.gorm.transactions.Transactional
import grails.util.Holders

import java.text.SimpleDateFormat

@Transactional
class EiaProjectService {
    def eiaEnvProjectService
    /**
     * 项目信息回显
     */
    def getEiaProjectDataMap(eiaProjectId) {
        def eiaProject = EiaProject.findByIdAndIfDel(eiaProjectId, false)
        def eiaProjectMap = eiaEnvProjectService.convertToMap(eiaProject.properties)
        def fileTypeMap = [:]
        def fileTypeId = EiaDomainCode.findByDomainAndCode(GeneConstants.PROJECT_FILE_TYPE, eiaProject.fileTypeChildCode)?.id
        fileTypeMap.id = fileTypeId
        fileTypeMap.name = eiaProject?.fileTypeChild

        eiaProjectMap.remove('fileType')
        eiaProjectMap.put('fileType', fileTypeMap)
        def eiaTask = EiaTask.findByIfDelAndId(false, eiaProject?.eiaTaskId)
        if (eiaTask) {
            eiaProjectMap.taskName = eiaTask?.taskName
            eiaProjectMap.taskNo = eiaTask?.taskNo
        }
        /**
         * 合同相关项目
         */
        def eiaContract = EiaContract.findByIfDelAndId(false, eiaProject?.eiaContractId)
        if (eiaContract) {
            eiaProjectMap.contractNo = eiaContract?.contractNo
            eiaProjectMap.contractName = eiaContract?.contractName
        }
        return eiaProjectMap
    }
    /**
     * 获取项目信息
     */
    def eiaProjectQueryPage(params, session) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd")
        int page
        int limit
        if (params.page && params.limit) {
            page = params.int('page') - 1
            limit = params.int('limit')
        }
        /** 审批文号 */
        def seaReviewNo = params.seaReviewNo
        def eiaEnvProjectList
        if (seaReviewNo) {
            eiaEnvProjectList = EiaEnvProject.createCriteria().list() {
                /**
                 * 查看全部的项目数据
                 */
                if (!session?.staff?.funcCode?.contains(FuncConstants.EIA_YWCX_XMCX_VIEWALL)) {
                    /**
                     * 查看本部门项目数据
                     */
                    if (session?.staff?.funcCode?.contains(FuncConstants.EIA_YWCX_XMCX_VIEWDEPT)) {
                        like ("inputDeptCode", "%"+ session.staff.orgCode +"%")
                    }
                    /**
                     * 查看本人项目数据
                     */
                    else if (session?.staff?.funcCode?.contains(FuncConstants.EIA_YWCX_XMCX_VIEWSELF)) {
                        eq("inputUserId", Long.valueOf(session.staff.staffId))
                    }
                }
                like("seaReviewNo", "%" + seaReviewNo + "%")
                eq("ifDel", false)
            }
        }
        /** 审批时间 */
        def arcStartDate = params.arcStartDate
        def arcEndDate = params.arcEndDate
        def planItemList
        if (arcStartDate || arcEndDate) {
            planItemList = EiaProjectPlanItem.createCriteria().list() {
                if (arcStartDate) {
                    ge('actEndDate', sdf.parse(arcStartDate))
                }
                if (arcEndDate) {
                    le('actEndDate', sdf.parse(arcEndDate))
                }
                eq("nodesCode", WorkFlowConstants.NODE_CODE_XMGD)
                eq("ifDel", false)
            }
        }
        /** 项目进度 */
        def nodesName = params.nodesName
        /** 是否归档 */
        def ifArc = params.ifArc
        def workFlowProjectIds = []
        if (nodesName || ifArc) {
            def eiaWorkFlowBusiList = EiaWorkFlowBusi.createCriteria().list() {
                /**
                 * 查看全部的项目数据
                 */
                if (!session?.staff?.funcCode?.contains(FuncConstants.EIA_YWCX_XMCX_VIEWALL)) {
                    /**
                     * 查看本部门项目数据
                     */
                    if (session?.staff?.funcCode?.contains(FuncConstants.EIA_YWCX_XMCX_VIEWDEPT)) {
                        like ("inputDeptCode", "%"+ session.staff.orgCode +"%")
                    }
                    /**
                     * 查看本人项目数据
                     */
                    else if (session?.staff?.funcCode?.contains(FuncConstants.EIA_YWCX_XMCX_VIEWSELF)) {
                        eq("inputUserId", Long.valueOf(session.staff.staffId))
                    }
                }
                if (ifArc == "是") {
                    eq("workFlowState", WorkFlowConstants.WORKFLOW_END)
                } else if (ifArc == "否") {
                    ne("workFlowState", WorkFlowConstants.WORKFLOW_END)
                }
                if (nodesName) {
                    eq('nodesName', nodesName)
                }
                eq('tableName', 'EiaProject')
                eq('ifDel', false)
            }
            if (eiaWorkFlowBusiList) {
                workFlowProjectIds = eiaWorkFlowBusiList?.tableNameId
            }
        }
        def eiaProjectList = EiaProject.createCriteria().list(max: limit, offset: page * limit) {
            def projectName = params.projectName
            /** 客户（甲方）名称 */
            def eiaClientName = params.eiaClientName
            if (eiaClientName) {
                like("eiaClientName", "%" + eiaClientName + "%")
            }
            /** 文件类型 */
            def fileTypeChild = params.fileTypeChild
            if (fileTypeChild) {
                like("fileTypeChild", "%" + fileTypeChild + "%")
            }
            /** 建设地点 */
            def buildArea = params.buildArea
            if (buildArea) {
                like("buildArea", "%" + buildArea + "%")
            }
            /** 项目金额 */
            def projectStartMoney = params.projectStartMoney
            if (projectStartMoney) {
                ge("projectMoney", new BigDecimal(projectStartMoney))
            }
            def projectEndMoney = params.projectEndMoney
            if (projectEndMoney) {
                le("projectMoney", new BigDecimal(projectEndMoney))
            }
            /** 审批文号 */
            if (seaReviewNo) {
                if (eiaEnvProjectList) {
                    'in'("id", eiaEnvProjectList?.eiaProjectId)
                } else {
                    eq("id", Long.valueOf(-1))
                }
            }
            /** 审批时间 */
            if (arcStartDate || arcEndDate) {
                'in'("id", planItemList?.eiaProjectId)
                eq("ifArc", true)
            }
            /** 项目进度、是否归档 */
            if (nodesName || ifArc) {
                if (workFlowProjectIds) {
                    'in'("id", workFlowProjectIds)
                } else {
                    eq("id", Long.valueOf(-1))
                }
            }
            if (projectName && !"项目名称、项目编号、项目负责人、录入部门、录入人".equals(projectName)) {
                or {
                    like("projectName", "%" + projectName + "%")
                    like("inputDept", "%" + projectName + "%")
                    like("inputUser", "%" + projectName + "%")
                    like("projectNo", "%" + projectName + "%")
                    like("dutyUser", "%" + projectName + "%")
                }
            } else {
                if (params.eiaTaskId || params.eiaContractId || params.eiaClientId || eiaClientName || fileTypeChild || buildArea || projectStartMoney || projectEndMoney || ifArc || seaReviewNo || arcStartDate || arcEndDate || nodesName) {
                } else {
                    eq("inputUserId", Long.valueOf(session.staff.staffId))
                }
            }
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
            /**
             * 判断是否有任务id，如果有任务id，说明列表为跟任务相关的
             */
            if (params.eiaTaskId) {
                eq("eiaTaskId", params.long("eiaTaskId"))
            }
            /**
             * 该合同相关的项目
             */
            if (params.eiaContractId) {
                eq("eiaContractId", params.long("eiaContractId"))
            }
            /**
             * 客户项目的项目
             */
            if (params.eiaClientId) {
                eq("eiaClientId", params.long("eiaClientId"))
            }
            eq("ifDel", false)
            order("id", "desc")
        }
        def eiaProjectData = []
        eiaProjectList.each {
            def map = [:]
            map.id = it.id
            map.projectMoney = it.projectMoney
            map.projectName = it.projectName
            map.buildArea = it?.buildArea
            map.eiaClientName = it?.eiaClientName
            map.gisProjectId = it.gisProjectId
            map.projectNo = it.projectNo
            map.fileTypeChild = it.fileTypeChild
            map.inputDept = it.inputDept
            map.inputUser = it.inputUser
            map.inputUserId = it.inputUserId
            map.dutyUser = it?.dutyUser
            map.eiaTaskId = it.eiaTaskId
            map.oid = it.oid
            def eiaProjectPlan = EiaProjectPlan.findByEiaProjectIdAndIfDel(it.id, false)
            def eiaLabOffer = EiaLabOffer.findByEiaProjectIdAndIfDel(it.id, false)
            if (eiaLabOffer) {
                map.eiaLabOfferId = eiaLabOffer.id
            } else {
                map.eiaLabOfferId = -1
            }
            def eiaTask = EiaTask.findByIdAndIfDel(it.eiaTaskId, false)
            if (eiaTask) {
                def eiaContract = EiaContract.findByTaskIdAndIfDel(eiaTask.id, false)
                if (eiaContract) {
                    map.oldContractId = eiaContract?.oid
                }
            }
            map.proPlanId = eiaProjectPlan?.id
            map.ifSub = eiaProjectPlan?.ifSub
            map.planMonitor = eiaProjectPlan?.planMonitor
            eiaProjectData << map
        }
        def dataMap = [:]
        dataMap.data = eiaProjectData
        dataMap.total = eiaProjectList.totalCount
        return dataMap
    }
    /**
     * 项目信息编辑
     */
    def eiaProjectUpdate(params, session) {
        if (!params.eiaProjectId) {
            return false
        } else {
            def envList = GeneConstants.ENVLIST
            def eseList = GeneConstants.ESELIST
            def greenList = GeneConstants.GREENLIST
            def eiaProject = EiaProject.findByIdAndIfDel(Long.parseLong(params.eiaProjectId), false)
            params.eiaTaskId = eiaProject.eiaTaskId
            eiaProject.properties = params
            eiaProject.fileType = params.fileTypeDrop
            eiaProject.inputDept = session.staff.orgName
            eiaProject.inputDeptCode = session.staff.orgCode
            eiaProject.inputDeptId = Long.parseLong(session.staff.orgId)
            eiaProject.inputUser = session.staff.staffName
            eiaProject.inputUserId = Long.parseLong(session.staff.staffId)
            if (params.eiaClientId) {
                eiaProject.eiaClientId = Long.parseLong(params.eiaClientId)
            }
            def fileTypeId = params.long('fileTypeDropCode')    //此fileType为子文件类型Id,根据此Id查询父Id,可从json中获取
            def fileTypeChildDomain = EiaDomainCode.findByDomainAndId(GeneConstants.PROJECT_FILE_TYPE, fileTypeId)
            def fileTypeChild = fileTypeChildDomain.codeDesc
            def fileTypeChildCode = fileTypeChildDomain.code
            def fileTypeCode = fileTypeChildDomain.parentCode
            def fileTypeDomain = EiaDomainCode.findByDomainAndCode(GeneConstants.PROJECT_FILE_TYPE, fileTypeCode)
            def fileType = fileTypeDomain.codeDesc
            def projectTypeCode = fileTypeDomain.parentCode
            def projectType = EiaDomainCode.findByDomainAndCode(GeneConstants.PROJECT_FILE_TYPE, projectTypeCode).codeDesc
            eiaProject.fileTypeChild = fileTypeChild
            eiaProject.fileTypeChildCode = fileTypeChildCode
            eiaProject.fileTypeCode = fileTypeCode
            eiaProject.fileType = fileType
            eiaProject.projectTypeCode = projectTypeCode
            eiaProject.projectType = projectType
            eiaProject.competentDept = params.competentDept
            /**
             * 更新成功后更新子表信息
             */
            if (eiaProject.save(flush: true, failOnError: true)) {
                def parentCode = eiaProject.fileTypeCode
                def eiaEnvProject = EiaEnvProject.findByEiaProjectIdAndIfDel(eiaProject.id, false)
                def eiaEneProject = EiaEneProject.findByEiaProjectIdAndIfDel(eiaProject.id, false)
                def eiaGreenProject = EiaGreenProject.findByEiaProjectIdAndIfDel(eiaProject.id, false)

                def saveChildProject = {
                    if (parentCode in envList) {
                        eiaEnvProject = new EiaEnvProject()
                        this.saveEnvProject(eiaEnvProject, params, session, eiaProject.id)
                    } else if (parentCode in eseList) {
                        eiaEneProject = new EiaEneProject()
                        this.saveEneProject(eiaEneProject, params, session, eiaProject.id)
                    } else if (parentCode in greenList) {
                        eiaGreenProject = new EiaGreenProject()
                        this.saveGreenProject(eiaGreenProject, params, session, eiaProject.id)
                    } else {
                        return false
                    }
                }

                if (!eiaEnvProject && !eiaEneProject && !eiaGreenProject) {
                    saveChildProject()
                } else {
                    if (eiaEnvProject && !(parentCode in envList)) {
                        /**———————删除环评项目——————————**/
                        eiaEnvProject.ifDel = true
                        eiaEnvProject.save(flush: true, failOnError: true)
                        saveChildProject()
                    } else if (eiaEnvProject && parentCode in envList) {
                        /**
                         * 更新环保咨询项目
                         */
                        this.saveEnvProject(eiaEnvProject, params, session, eiaProject.id)
                    } else {

                        if (eiaEneProject && !(parentCode in eseList)) {
                            /**————————删除节能评估项目————————**/
                            eiaEneProject?.ifDel = true
                            eiaEneProject.save(flush: true, failOnError: true)
                            saveChildProject()
                        } else if (eiaEneProject && (parentCode in eseList)) {
                            /**
                             * 更新节能评估项目
                             */
                            this.saveEneProject(eiaEneProject, params, session, eiaProject.id)
                        } else {
                            if (eiaGreenProject && !(parentCode in greenList)) {
                                /**————————删除绿色金融项目————————**/
                                eiaGreenProject?.ifDel = true
                                eiaGreenProject.save(flush: true, failOnError: true)
                                saveChildProject()
                            } else if (eiaGreenProject && (parentCode in greenList)) {
                                this.saveGreenProject(eiaGreenProject, params, session, eiaProject.id)
                            }
                        }
                    }
                }
                return eiaProject
            } else {
                return false
            }
        }
    }
    /**
     * 保存环保咨询项目
     */
    def saveEnvProject(EiaEnvProject eiaEnvProject, params, session, Long eiaProjectId) {
        eiaEnvProject.properties = params
        eiaEnvProject.eiaProjectId = eiaProjectId
        def industryTypeDropCode = params.long('industryTypeDropCode')
        def industry = EiaDomainCode.findById(industryTypeDropCode)
        if (industry) {
            eiaEnvProject.industryTypeCode = industry.code
            eiaEnvProject.industryTypeDesc = industry.codeDesc
        }
        def environmentaTypeDropCode = params.long('environmentaTypeDropCode')
        def environment = EiaDomainCode.findById(environmentaTypeDropCode)
        if (environment) {
            eiaEnvProject.environmentaTypeCode = environment.code
            eiaEnvProject.environmentaTypeDesc = environment.codeDesc
        }
        eiaEnvProject.inputDept = session.staff.orgName
        eiaEnvProject.inputDeptCode = session.staff.orgCode
        eiaEnvProject.inputDeptId = Long.parseLong(session.staff.orgId)
        eiaEnvProject.inputUser = session.staff.staffName
        eiaEnvProject.inputUserId = Long.parseLong(session.staff.staffId)
        eiaEnvProject.save(flush: true, failOnError: true)
    }
    /**
     * 保存节能咨询项目
     */
    def saveEneProject(EiaEneProject eiaEneProject, params, session, Long eiaProjectId) {
        /**
         * 更新节能评估项目
         */
        eiaEneProject.properties = params
        eiaEneProject.eiaProjectId = eiaProjectId
        def industryTypeDropCode = params.long('industryTypeDropCode')
        def industry = EiaDomainCode.findById(industryTypeDropCode)
        if (industry) {
            eiaEneProject.industryTypeCode = industry.code
            eiaEneProject.industryTypeDesc = industry.codeDesc
        }
        eiaEneProject.inputDept = session.staff.orgName
        eiaEneProject.inputDeptCode = session.staff.orgCode
        eiaEneProject.inputDeptId = Long.parseLong(session.staff.orgId)
        eiaEneProject.inputUser = session.staff.staffName
        eiaEneProject.inputUserId = Long.parseLong(session.staff.staffId)
        eiaEneProject.save(flush: true, failOnError: true)
    }
    /**
     * 保存绿色金融项目
     */
    def saveGreenProject(EiaGreenProject eiaGreenProject, params, session, Long eiaProjectId) {
        eiaGreenProject.eiaProjectId = eiaProjectId
        eiaGreenProject.properties = params
        eiaGreenProject.inputDept = session.staff.orgName
        eiaGreenProject.inputDeptCode = session.staff.orgCode
        eiaGreenProject.inputDeptId = Long.parseLong(session.staff.orgId)
        eiaGreenProject.inputUser = session.staff.staffName
        eiaGreenProject.inputUserId = Long.parseLong(session.staff.staffId)
        eiaGreenProject.save(flush: true, failOnError: true)
    }
    /**
     * 获取建设性质DomianCode
     */
    def getBuildPropList() {
        def buildPropList = EiaDomainCode.findAllByDomain("建设性质", [sort: "displayOrder", order: "asc"])
        return buildPropList
    }
    /**
     * 园区规划环评开展情况DomainCode
     */
    def getParkEnvCodList() {
        def parkEnvCodList = EiaDomainCode.findAllByDomain("园区规划环评开展情况", [sort: "displayOrder", order: "asc"])
        return parkEnvCodList
    }
    /**
     * 项目删除
     */
    def eiaProjectDel(params) {
        def eiaProject = EiaProject.findByIdAndIfDel(params.long('eiaProjectId'), false)
        //   def eiaWorkFlowBusi =EiaWorkFlowBusi.findAllByTableNameAndTableNameIdAndIfDelAndWorkFlowStateInList()
        if (eiaProject) {
            def eiaWorkFlowBusi = EiaWorkFlowBusi.createCriteria().list {
                eq('tableName', GeneConstants.DOMAIN_EIA_PROJECT)
                eq('tableNameId', eiaProject.id)
                or {
                    eq('workFlowState', WorkFlowConstants.WORKFLOW_START)
                    eq('workFlowState', WorkFlowConstants.WORKFLOW_UNDER_WAY)
                    eq('workFlowState', WorkFlowConstants.WORKFLOW_END)
                }
            }
            if (eiaWorkFlowBusi.size() > 0) {
                return HttpMesConstants.MSG_PRO_FLOW_STARTED
            }
            def projectType = eiaProject.projectType
            eiaProject.ifDel = true
            if (eiaProject.save(flush: true, failOnError: true)) {
                /***恢复内审单**/
                def eiaProjectExplore = EiaProjectExplore.findByEiaProjectIdAndIfDel(eiaProject.id,false)
                eiaProjectExplore.eiaProjectId = 0
                eiaProjectExplore.eiaTaskId = 0
                eiaProjectExplore.gisGeoProjectId = 0
                eiaProjectExplore.save(flush: true, failOnError: true)
                def projectContent
                if (projectType == '环保咨询') {
                    def eiaEnvProject = EiaEnvProject.findByEiaProjectIdAndIfDel(params.long('eiaProjectId'), false)
                    if (eiaEnvProject) {
                        eiaEnvProject?.ifDel = true
                        projectContent = eiaEnvProject?.save(flush: true, failOnError: true)
                    }
                } else if (projectType == '节能咨询') {
                    def eiaEneProject = EiaEneProject.findByEiaProjectIdAndIfDel(params.long('eiaProjectId'), false)
                    if (eiaEneProject) {
                        eiaEneProject.ifDel = true
                        projectContent = eiaEneProject.save(flush: true, failOnError: true)
                    }
                } else {
                    def greenProject = EiaGreenProject.findByEiaProjectIdAndIfDel(params.long('eiaProjectId'), false)
                    if (greenProject) {
                        greenProject.ifDel = true
                        projectContent = greenProject.save(flush: true, failOnError: true)
                    }
                }
                if (projectContent != false) {
                    /**
                     * 删除工作方案及工作方案节点
                     */
                    def eiaProjectPlan = EiaProjectPlan.findByEiaProjectIdAndIfDel(eiaProject.id, false)
                    eiaProjectPlan.ifDel = true
                    eiaProjectPlan.save(flush: true, failOnError: true)
                    def eiaProjectPlanItem = EiaProjectPlanItem.findAllByEiaProjectPlanIdAndIfDel(eiaProjectPlan.id, false)
                    eiaProjectPlanItem.each {
                        it.ifDel = true
                        it.save(flush: true, failOnErro: true)
                    }
                    /**
                     * 向gis系统发送请求，更新是否提交
                     */
                    def gisProjectData = HttpConnectTools.getResponseJson(Holders.getConfig().getProperty('webroots.contextPath.api') + "gis/gisGeoProjectUpdate", [projectId: String.valueOf(eiaProject.gisProjectId)])
                    if (gisProjectData) {
                        def gisProjectMap = JsonHandler.jsonToMap(gisProjectData)
                        if (gisProjectMap.code == HttpMesConstants.CODE_OK) {
                            return true
                        } else {
                            return false
                        }
                    } else {
                        return false
                    }
                }
            } else {
                return false
            }

        } else {
            return false
        }
        return true
    }
    /**
     * 归档打印
     */
    def eiaPrintFileArc(long eiaProjectId) {
        def resMap = [:]
        def eiaProject = EiaProject.findByIdAndIfDel(eiaProjectId, false)
        def eiaEnvProject = EiaEnvProject.findByEiaProjectIdAndIfDel(eiaProjectId, false)
        //def eiaTaskAssign = EiaTaskAssign.findByTaskIdAndIfDelAndTaskAssignRole(eiaProject.eiaTaskId,false,GeneConstants.TASK_ASSIGN_ROLE_TOAST)
        resMap.projectName = eiaProject?.projectName
        resMap.projectNo = eiaProject?.projectNo
        resMap.fileTypeChild = eiaProject?.fileTypeChild
        resMap.inputUser = eiaProject?.inputUser
        resMap.inputDept = eiaProject?.inputDept
        resMap.competentDept = eiaEnvProject?.seaReviewOrg
        resMap.projectLeader = ((eiaProject?.dutyUser) ? (eiaProject?.dutyUser) : resMap.inputUser)//123
        if (eiaProject?.eiaContractId) {
            def htFile = EiaFileUpload.findAllByTableNameAndTableIdAndIfDelAndFileUploadType(GeneConstants.DOMAIN_EIA_CONTRACT, eiaProject?.eiaContractId, false, GeneConstants.CONTRACT_FILT_TYPE_HTSMJ)
            if (htFile) {
                resMap.ht = "存"
            }
            def wwFile = EiaFileUpload.findAllByTableNameAndTableIdAndIfDelAndFileUploadType(GeneConstants.DOMAIN_EIA_CONTRACT, eiaProject?.eiaContractId, false, GeneConstants.CONTRACT_FILT_TYPE_WWHT)
            if (wwFile) {
                resMap.wwht = "存"
            }
        }
        def eiaWorkFlowBusi = EiaWorkFlowBusi.findByTableNameAndTableNameIdAndWorkFlowStateNotEqualAndNodesCode(GeneConstants.DOMAIN_EIA_PROJECT, eiaProjectId, WorkFlowConstants.WORKFLOW_HALT, WorkFlowConstants.NODE_CODE_XMGD)
        if (eiaWorkFlowBusi) {
            resMap.signImagePath = getReport(Long.valueOf(eiaWorkFlowBusi.authCode))
        }
        return resMap
    }
    /**
     * 获取项目、合同、任务信息（项目流程中的项目信息）
     */
    def getProjectDataAll(params) {
        def eiaProjectId = params.long('eiaProjectId')
        def eiaProject = EiaProject.findByIdAndIfDel(eiaProjectId, false)
        def eiaContract = EiaContract.findByIdAndIfDel(eiaProject.eiaContractId, false)
        def eiaTask = EiaTask.findByIdAndIfDel(eiaProject.eiaTaskId, false)
        def eiaClient = EiaClient.findByIdAndIfDel(eiaProject.eiaClientId, false)
        def eiaClientContact = EiaClientContacts.findByEiaClientIdAndIfDel(eiaClient?.id, false)
        def resMap = [:]
        resMap.contractNo = eiaProject?.contractNo
        resMap.contractName = eiaProject?.contractName
        resMap.taskName = eiaTask?.taskName
        resMap.contactName = eiaContract?.contactName
        resMap.contractType = eiaContract?.contractType
        resMap.contractMoney = eiaContract?.contractMoney
        resMap.contractUse = eiaContract?.contractUse
        resMap.contractDate = eiaContract?.contractDate
        resMap.province = eiaContract?.province
        resMap.clientName = eiaClient?.clientName
        resMap.clientAddress = eiaClient?.clientAddress
        resMap.clientContact = eiaClientContact?.contactPhone
        resMap.environmentalFee = eiaProject?.environmentalFee
        resMap.expertFee = eiaProject?.expertFee
        resMap.groundwaterFee = eiaProject?.groundwaterFee
        resMap.otherFee = eiaProject?.otherFee
        resMap.projectComfee = eiaProject?.projectComfee
        return resMap
    }

    /***
     * 可申请资质项目
     */

    def eiaProjectQuery(session) {
        def staffId = Long.valueOf(session.staff.staffId)
        def eiaProjectList = EiaProject.findAllByIfDelAndIfCertAndInputUserId(false, true, staffId)
        def resList = []
        eiaProjectList.each {
            resList << [eiaProjectId: it.id, projectName: it.projectName]
        }

        return resList
    }
    /**
     * 根据项目名称获取项目地址
     */
    def getProjectAddr(Long eiaProjectId) {
        return EiaProject.findById(eiaProjectId, false)
    }
    /**
     * 检测方案获取项目名称list(如果任务有该人员，则可以选择任务下所有项目)
     */
    def getEiaProjectNameList(session) {
        def eiaProjectList
        def eiaTaskList = EiaTask.createCriteria().list() {
            or {
                like("taskAssignUser", "%" + session.staff.staffName + "_" + session.staff.staffId + "%")
                eq("inputUserId", Long.parseLong(session.staff.staffId))
            }
            eq("ifDel", false)
        }
        if (eiaTaskList) {
            eiaProjectList = EiaProject.findAllByEiaTaskIdInListAndIfDel(eiaTaskList?.id, false, [sort: "id", order: "desc"])
        }
        return eiaProjectList
    }
    /**
     * 根据用户id获取签名图片
     * @param userId
     * @return
     */
    def getReport(long userId) {
        def param = [:]
        param.staffId = (userId).toString()
        def staffJson = HttpConnectTools.getResponseJson(HttpUrlConstants.SINGLE_EIA_AUTH_STAFF_INFO, param)
        def signImagePath
        if (staffJson) {
            def staff = JsonHandler.jsonToMap(staffJson).data[0]
            if (staff.signImagePath) {
                signImagePath = GeneConstants.AUTH_FILE_URL_PATH + staff.signImagePath
            }
        }
        return signImagePath
    }
    /**
     * 获取责任运行卡数据
     */
    def getDutyCardDataMap(params) {
        long eiaProjectId = Long.valueOf(params.eiaProjectId)
        def eiaProject = EiaProject.findByIfDelAndId(false, eiaProjectId)
        def eiaContract
        if (eiaProject.eiaContractId) {
            eiaContract = EiaContract.findByIfDelAndId(false, eiaProject.eiaContractId)
        }
        def eiaProjectPlan = EiaProjectPlan.findByIfDelAndEiaProjectId(false, eiaProjectId)
        def eiaProjectPlanItemList
        if (eiaProjectPlan) {
            eiaProjectPlanItemList = EiaProjectPlanItem.findAllByIfDelAndEiaProjectPlanId(false, eiaProjectPlan.id, [sort: "id"])
        }
        def resMap = [:]
        /***
         * projectName 项目名称
         * contractNo 合同号
         * contractDate 合同签订时间
         * completeDate预计完成事件
         * dutyUser 项目负责人
         * projectGroup 项目组成员
         * projectApproval2 二审审核
         * projectApproval3 三审审核
         * record 运行记录
         * approval 审核
         * review 审定
         * */
        resMap.projectName = eiaProject.projectName
        resMap.projectNo = eiaProject?.projectNo
        resMap.contractDate = eiaContract ? eiaContract.contractDate?.format("yyyy-MM-dd") : ""
        resMap.dutyUser = eiaProject.dutyUser ? eiaProject.dutyUser : eiaProject.inputUser
        resMap.projectGroup = eiaProject.inputUser
        StringBuilder stringBuilder = new StringBuilder()
        if (eiaProjectPlanItemList && eiaProjectPlanItemList.size() > 0) {
            def endTime
            /**不需要添加到踏勘记录的节点**/
            def noRecordNodeList = [WorkFlowConstants.NODE_CODE_YSBZ,WorkFlowConstants.NODE_CODE_ESBZ,WorkFlowConstants.NODE_CODE_SSBZ,WorkFlowConstants.NODE_CODE_BPBSB,WorkFlowConstants.NODE_CODE_XMGD]
            eiaProjectPlanItemList.each {
                if (!noRecordNodeList.contains(it.nodesCode) ) {
                    if (WorkFlowConstants.NODE_CODE_ES.equals(it.nodesCode)) {
                        if (it.nodeUserName) {
                            resMap.projectApproval2 = it.nodeUserName
                            resMap.approval = getReport(it.nodeUserId)
                        } else {
                            resMap.projectApproval2 = ""
                            resMap.approval = ""
                        }
                    }
                    if (WorkFlowConstants.NODE_CODE_SS.equals(it.nodesCode)) {
                        if (it.nodeUserName) {
                            resMap.projectApproval3 = it.nodeUserName
                            resMap.review = getReport(it.nodeUserId)
                        } else {
                            resMap.projectApproval3 = ""
                            resMap.review = ""
                        }
                    }
                    if (WorkFlowConstants.NODE_CODE_BPBSB.equals(it.nodesCode)) {
                        if (it.actEndDate) {
                            resMap.completeDate = it.actEndDate?.format("yyyy-MM-dd")
                        } else {
                            resMap.completeDate = ""
                        }
                    }
                    stringBuilder.append("(")
                    stringBuilder.append(it.nodesName)
                    stringBuilder.append("时间：")
                    if (it.actEndDate) {
                        stringBuilder.append(it.actEndDate?.format("yyyy-MM-dd"))
                    } else {
                        stringBuilder.append("(暂无)")
                    }
                    stringBuilder.append(",人员：")
                    if (it.nodeUserName) {
                        stringBuilder.append(it.nodeUserName)
                    } else {
                        stringBuilder.append("(暂无)")
                    }
                    stringBuilder.append(")</br>")

                }
            }

        }
        resMap.record = stringBuilder.toString()

        return resMap
    }

    /**
     * 检查是否有合同
     */
    def checkIfContract(params) {
        if (!params.proPlanId) {
            def eiaWorkFlowBusiId = params.long('eiaWorkFlowBusiId')
            def eiaWorkFlowBusi = EiaWorkFlowBusi.findByIdAndIfDel(eiaWorkFlowBusiId, false)
            def eiaProject = EiaProject.findByIdAndIfDel(eiaWorkFlowBusi.tableNameId, false)
            if (eiaProject) {
                if (eiaProject.eiaContractId) {
                    return eiaProject
                } else {
                    return false
                }
                return eiaProject
            } else {
                return false
            }
        } else {
            def eiaProjectPlan = EiaProjectPlan.findByIdAndIfDel(params.long('proPlanId'), false)
            if (eiaProjectPlan) {
                def eiaProject = EiaProject.findByIdAndIfDel(eiaProjectPlan.eiaProjectId, false)
                if (eiaProject) {
                    if (eiaProject.eiaContractId) {
                        return true
                    } else {
                        return false
                    }
                    return true
                } else {
                    return false
                }
            } else {
                return false
            }
        }
    }
    /**
     * 获取项目所需要的金额的字段
     */
    def getProMoneyProp(params) {
        def projectType
        if (params.eiaProjectId) {
            projectType = EiaProject.findByIdAndIfDel(params.long('eiaProjectId'), false).fileTypeCode
        } else {
            def fileTypeId = params.long('fileTypeId')
            projectType = EiaDomainCode.findById(fileTypeId).parentCode
        }
        if (projectType in GeneConstants.GREENLIST) {
            if (projectType == "GREEN_LZ") {
                return GeneConstants.LZ_MONEY_LIST
            } else if (projectType == "GREEN_LQ") {
                return GeneConstants.LQ_MONEY_LIST
            } else {
                return GeneConstants.GREEN_MONEY_LIST
            }
        } else if (projectType in GeneConstants.ESELIST) {
            return GeneConstants.ESE_MONEY_LIST
        } else if (projectType == 'EPC_HP') {
            return GeneConstants.HP_MONEY_LIST
        } else if (projectType == 'EPC_HY') {
            return GeneConstants.HY_MONEY_LIST
        } else if (projectType == 'EPC_HB') {
            return GeneConstants.HB_MONEY_LIST
        } else if (projectType == 'EPC_YS') {
            return GeneConstants.YS_MONEY_LIST
        } else if (projectType == 'EPC_YA') {
            return GeneConstants.YA_MONEY_LIST
        } else if (projectType == 'EPC_JL') {
            return GeneConstants.JL_MONEY_LIST
        } else if (projectType == 'EPC_GH') {
            return GeneConstants.GH_MONEY_LIST
        } else if (projectType == 'EPC_HH') {
            return GeneConstants.HH_MONEY_LIST
        } else if (projectType == 'EPC_SH') {
            return GeneConstants.SH_MONEY_LIST
        } else if (projectType == 'EPC_XZ') {
            return GeneConstants.XZ_MONEY_LIST
        } else if (projectType == 'EPC_PF') {
            return GeneConstants.PF_MONEY_LIST
        } else if (projectType == 'EPC_PW') {
            return GeneConstants.PW_MONEY_LIST
        } else if (projectType == 'EPC_ST') {
            return GeneConstants.ST_MONEY_LIST
        } else if (projectType == 'EPC_CD') {
            return GeneConstants.CD_MONEY_LIST
        } else if (projectType == 'EPC_QT') {
            return GeneConstants.PF_MONEY_LIST
        }
    }

    def getPropMoneyShow(params) {
        def eiaProject
        def projectType
        if (params.eiaProjectId) {
            eiaProject = EiaProject.findByIdAndIfDel(params.long('eiaProjectId'), false)
            projectType = eiaProject.fileTypeCode
        }
        def dataMap = [:]
        if (projectType in GeneConstants.GREENLIST) {
            if (projectType == "GREEN_LZ") {
                dataMap = eiaEnvProjectService.combNeedMap(eiaProject?.properties, GeneConstants.LZ_MONEY_LIST)
            } else if (projectType == "GREEN_LQ") {
                dataMap = eiaEnvProjectService.combNeedMap(eiaProject?.properties, GeneConstants.LQ_MONEY_LIST)
            } else {
                dataMap = eiaEnvProjectService.combNeedMap(eiaProject?.properties, GeneConstants.GREEN_MONEY_LIST)
            }
        } else if (projectType in GeneConstants.ESELIST) {
            dataMap = eiaEnvProjectService.combNeedMap(eiaProject?.properties, GeneConstants.ESE_MONEY_LIST)
        } else if (projectType == 'EPC_HP') {
            dataMap = eiaEnvProjectService.combNeedMap(eiaProject?.properties, GeneConstants.HP_MONEY_LIST)
        } else if (projectType == 'EPC_HY') {
            dataMap = eiaEnvProjectService.combNeedMap(eiaProject?.properties, GeneConstants.HY_MONEY_LIST)
        } else if (projectType == 'EPC_HB') {
            dataMap = eiaEnvProjectService.combNeedMap(eiaProject?.properties, GeneConstants.HB_MONEY_LIST)
        } else if (projectType == 'EPC_YS') {
            dataMap = eiaEnvProjectService.combNeedMap(eiaProject?.properties, GeneConstants.YS_MONEY_LIST)
        } else if (projectType == 'EPC_YA') {
            dataMap = eiaEnvProjectService.combNeedMap(eiaProject?.properties, GeneConstants.YA_MONEY_LIST)
        } else if (projectType == 'EPC_JL') {
            dataMap = eiaEnvProjectService.combNeedMap(eiaProject?.properties, GeneConstants.JL_MONEY_LIST)
        } else if (projectType == 'EPC_GH') {
            dataMap = eiaEnvProjectService.combNeedMap(eiaProject?.properties, GeneConstants.GH_MONEY_LIST)
        } else if (projectType == 'EPC_HH') {
            dataMap = eiaEnvProjectService.combNeedMap(eiaProject?.properties, GeneConstants.HH_MONEY_LIST)
        } else if (projectType == 'EPC_SH') {
            dataMap = eiaEnvProjectService.combNeedMap(eiaProject?.properties, GeneConstants.SH_MONEY_LIST)
        } else if (projectType == 'EPC_XZ') {
            dataMap = eiaEnvProjectService.combNeedMap(eiaProject?.properties, GeneConstants.XZ_MONEY_LIST)
        } else if (projectType == 'EPC_PF') {
            dataMap = eiaEnvProjectService.combNeedMap(eiaProject?.properties, GeneConstants.PF_MONEY_LIST)
        } else if (projectType == 'EPC_PW') {
            dataMap = eiaEnvProjectService.combNeedMap(eiaProject?.properties, GeneConstants.PW_MONEY_LIST)
        } else if (projectType == 'EPC_ST') {
            dataMap = eiaEnvProjectService.combNeedMap(eiaProject?.properties, GeneConstants.ST_MONEY_LIST)
        } else if (projectType == 'EPC_CD') {
            dataMap = eiaEnvProjectService.combNeedMap(eiaProject?.properties, GeneConstants.CD_MONEY_LIST)
        } else if (projectType == 'EPC_QT') {
            dataMap = eiaEnvProjectService.combNeedMap(eiaProject?.properties, GeneConstants.PF_MONEY_LIST)
        }
        return dataMap
    }
    /**
     *  获取项目负责人列表
     */
    def getProjectDutyUser(params) {
        def eiaProjectId = params.long('eiaProjectId')
        def eiaProject = EiaProject.findByIdAndIfDel(params.long('eiaProjectId'), false)
        if (eiaProject) {
            def eiaTask = EiaTask.findByIdAndIfDel(eiaProject.eiaTaskId, false)
            def dutyUserList = []
            def toastUser = EiaTaskAssign.findAllByTaskIdAndTaskAssignRoleAndIfDel(eiaProject.eiaTaskId, GeneConstants.TASK_ASSIGN_MAIN_EDITOR, false)
            if (toastUser.size() > 0) {
                toastUser.each {
                    def dutyUserMap = [:]
                    dutyUserMap.dutyUser = it.taskAssignUser
                    dutyUserMap.dutyUserId = it.taskAssignUserId
                    dutyUserList << dutyUserMap
                }
            } else {
                dutyUserList << [dutyUser: eiaProject.inputUser, dutyUserId: eiaProject.inputUserId]
            }
            return dutyUserList
        } else {
            return false
        }
    }
}
