package com.lheia.eia.config

import com.lheia.eia.cert.EiaCert
import com.lheia.eia.client.EiaClient
import com.lheia.eia.client.EiaClientContacts
import com.lheia.eia.common.GeneConstants
import com.lheia.eia.contract.EiaContract
import com.lheia.eia.contract.EiaContractLog
import com.lheia.eia.contract.EiaOffer
import com.lheia.eia.finance.EiaIncomeOut
import com.lheia.eia.finance.EiaInvoice
import com.lheia.eia.lab.EiaLabOffer
import com.lheia.eia.project.EiaEneProject
import com.lheia.eia.project.EiaEneProjectLog
import com.lheia.eia.project.EiaEnvProject
import com.lheia.eia.project.EiaEnvProjectLog
import com.lheia.eia.project.EiaGreenProject
import com.lheia.eia.project.EiaGreenProjectLog
import com.lheia.eia.project.EiaProject
import com.lheia.eia.project.EiaProjectLog
import com.lheia.eia.project.EiaProjectPlan
import com.lheia.eia.project.EiaProjectPlanItem
import com.lheia.eia.task.EiaTask
import com.lheia.eia.task.EiaTaskAssign
import com.lheia.eia.task.EiaTaskAssignLog
import com.lheia.eia.task.EiaTaskLog
import com.lheia.eia.workflow.EiaWorkFlowBusi
import com.lheia.eia.workflow.EiaWorkFlowBusiLog
import grails.gorm.transactions.Transactional
import org.grails.web.json.JSONArray
import org.grails.web.json.JSONObject

@Transactional
class EiaDataMaintainLogService {

    /**
     * 数据维护
     */
    def dataMaintainSave(params,session){
        /**
         * 当前人员
         */
        def agoInfo = params.taskRoleToast
        /**
         * 变更后人员名称
         */
        def afterInfo = params.taskRoleExam
        /**
         * 当前人员
         */
        JSONArray agoInfos = new JSONArray(agoInfo)
        /**
         * 变更前部门信息
         */
        def agoOrgId
        def agoStaffName
        def agoStaffId
        /**
         * 变更后部门信息
         */
        def afterOrgId
        def afterOrgName
        def afterOrgCode
        def afterStaffName
        def afterStaffId
        for (int i = 0; i < agoInfos.length(); i++) {
            JSONObject agoInfosUser = agoInfos.getJSONObject(i);
            agoOrgId = agoInfosUser.getString("orgId")
            agoStaffName = agoInfosUser.getString("staffName")
            agoStaffId = agoInfosUser.getString("staffId")
        }
        /***
         * 变更后人员信息
         */
        JSONArray afterInfos = new JSONArray(afterInfo)
        for (int i = 0; i < afterInfos.length(); i++) {
            JSONObject afterInfosUser = afterInfos.getJSONObject(i);
            afterOrgId = afterInfosUser.getString("orgId")
            afterOrgName = afterInfosUser.getString("orgName")
            afterOrgCode = afterInfosUser.getString("orgCode")
            afterStaffName = afterInfosUser.getString("staffName")
            afterStaffId = afterInfosUser.getString("staffId")
        }
        /**
         * 客户信息变更
         */
        def eiaDataMaintainList = []
        def eiaClientList = []
        def eiaTaskList = []
        def eiaOfferList = []
        def eiaContractList = []
        def eiaCertList = []
        def eiaProjectList = []
        def eiaProjectPlanList = []
        def eiaWorkFlowBusiList = []
        def eiaWorkFlowBusiLogList = []
        def eiaClient = EiaClient.findAllByIfDelAndInputUserIdAndInputDeptId(false, Long.valueOf(agoStaffId),Long.valueOf(agoOrgId))
        eiaClient.each{
            def eiaClientMap = [:]
            eiaClientMap["tableName"] = GeneConstants.DOMAIN_EIA_CLIENT
            eiaClientMap["tableNameId"] = it.id
            eiaClientMap["afterInputDept"] = afterOrgName
            eiaClientMap["afterInputDeptId"] = Long.valueOf(afterOrgId)
            eiaClientMap["afterInputDeptCode"] = afterOrgCode
            eiaClientMap["afterInputUser"] = afterStaffName
            eiaClientMap["afterInputUserId"]= Long.valueOf(afterStaffId)
            eiaClientMap["agoInputDept"] = it.inputDept
            eiaClientMap["agoInputDeptId"] = it.inputDeptId
            eiaClientMap["agoInputDeptCode"] = it.inputDeptCode
            eiaClientMap["agoInputUser"] = it.inputUser
            eiaClientMap["agoInputUserId"]= it.inputUserId
            eiaClientList.add(eiaClientMap)
            /**
             * 更新数据
             */
            it.inputDept = afterOrgName
            it.inputDeptId = Long.valueOf(afterOrgId)
            it.inputDeptCode = afterOrgCode
            it.inputUser = afterStaffName
            it.inputUserId = Long.valueOf(afterStaffId)
            def eiaClientContacts = EiaClientContacts.findAllByIfDelAndEiaClientId(false,it.id)
            eiaClientContacts.each{
                it.inputDept = afterOrgName
                it.inputDeptId = Long.valueOf(afterOrgId)
                it.inputDeptCode = afterOrgCode
                it.inputUser = afterStaffName
                it.inputUserId = Long.valueOf(afterStaffId)
            }
        }
        /**
         * 变更任务信息
         */

        def eiaTask = EiaTask.findAllByIfDelAndInputUserIdAndInputDeptId(false, Long.valueOf(agoStaffId),Long.valueOf(agoOrgId))
        eiaTask.each{
            def eiaTaskMap = [:]
            eiaTaskMap["tableName"] = GeneConstants.DOMAIN_EIA_TASK
            eiaTaskMap["tableNameId"] = it.id
            eiaTaskMap["afterInputDept"] = afterOrgName
            eiaTaskMap["afterInputDeptId"] = Long.valueOf(afterOrgId)
            eiaTaskMap["afterInputDeptCode"] = afterOrgCode
            eiaTaskMap["afterInputUser"] = afterStaffName
            eiaTaskMap["afterInputUserId"]= Long.valueOf(afterStaffId)
            eiaTaskMap["agoInputDept"] = it.inputDept
            eiaTaskMap["agoInputDeptId"] = it.inputDeptId
            eiaTaskMap["agoInputDeptCode"] = it.inputDeptCode
            eiaTaskMap["agoInputUser"] = it.inputUser
            eiaTaskMap["agoInputUserId"]= it.inputUserId
            eiaTaskList.add(eiaTaskMap)
            it.inputDept = afterOrgName
            it.inputDeptId = Long.valueOf(afterOrgId)
            it.inputDeptCode = afterOrgCode
            it.inputUser = afterStaffName
            it.inputUserId = Long.valueOf(afterStaffId)
            /**
             * 人员分配编号
             */
            def eiaTaskAssignMaintain = EiaTaskAssign.findAllByIfDelAndTaskAssignUserIdAndTaskAssignDeptId(false,Long.valueOf(agoStaffId),Long.valueOf(agoOrgId))
            eiaTaskAssignMaintain.each{
                it.taskAssignDept = afterOrgName
                it.taskAssignDeptId = Long.valueOf(afterOrgId)
                it.taskAssignUser = afterStaffName
                it.taskAssignUserId = Long.valueOf(afterStaffId)
                /**
                 * 任务分派录入人
                 */
                String taskAssignUserInfo = ""
                def eiaTaskAssign = EiaTaskAssign.findAllByIfDelAndTaskId(false,it.id)
                eiaTaskAssign.each{
                    it.inputDept = afterOrgName
                    it.inputDeptId = Long.valueOf(afterOrgId)
                    it.inputUser = afterStaffName
                    it.inputUserId = Long.valueOf(afterStaffId)
                    if(it.taskAssignUserId == Long.valueOf(afterStaffId)){
                        it.taskAssignUser = afterStaffName
                        it.taskAssignUserId = Long.valueOf(afterStaffId)
                    }
                    taskAssignUserInfo += it.taskAssignUser + "_" + it.taskAssignUserId + ','
                }
            }

            /**
             * 更新任务中的分配人员
             */
            def taskAssignUserInfo = ""
            def eiaTaskAssign = EiaTaskAssign.findAllByIfDelAndTaskId(false,it.id)
            eiaTaskAssign.each{
                taskAssignUserInfo += it.taskAssignUser + "_" + it.taskAssignUserId + ','
            }
            /**
             * 变更任务分配人
             */
            def eiaTaskInfo = EiaTask.findByIfDelAndId(false,it.id)
            if(taskAssignUserInfo.isEmpty()){
                eiaTaskInfo.taskAssignUser = afterStaffName + "_" + afterStaffId
            }else{
                eiaTaskInfo.taskAssignUser = taskAssignUserInfo.substring(0, taskAssignUserInfo.length() - 1)
            }
            eiaTaskInfo.save(flush: true, failOnError: true)
        }
        /**
         * 更新报价信息
         */
        def eiaOffer = EiaOffer.findAllByIfDelAndInputUserIdAndInputDeptId(false,Long.valueOf(agoStaffId),Long.valueOf(agoOrgId))
        eiaOffer.each{
            def eiaOfferMap = [:]
            eiaOfferMap["tableName"] = GeneConstants.DOMAIN_EIA_OFFER
            eiaOfferMap["tableNameId"] = it.id
            eiaOfferMap["afterInputDept"] = afterOrgName
            eiaOfferMap["afterInputDeptId"] = Long.valueOf(afterOrgId)
            eiaOfferMap["afterInputDeptCode"] = afterOrgCode
            eiaOfferMap["afterInputUser"] = afterStaffName
            eiaOfferMap["afterInputUserId"]= Long.valueOf(afterStaffId)
            eiaOfferMap["agoInputDept"] = it.inputDept
            eiaOfferMap["agoInputDeptId"] = it.inputDeptId
            eiaOfferMap["agoInputDeptCode"] = it.inputDeptCode
            eiaOfferMap["agoInputUser"] = it.inputUser
            eiaOfferMap["agoInputUserId"]= it.inputUserId
            eiaOfferList.add(eiaOfferMap)
            it.inputDept = afterOrgName
            it.inputDeptId = Long.valueOf(afterOrgId)
            it.inputDeptCode = afterOrgCode
            it.inputUser = afterStaffName
            it.inputUserId = Long.valueOf(afterStaffId)
        }
        /**
         * 更新合同信息
         */
        def eiaContract = EiaContract.findAllByIfDelAndInputUserIdAndInputDeptId(false,Long.valueOf(agoStaffId),Long.valueOf(agoOrgId))
        eiaContract.each{
            def eiaContractMap = [:]
            eiaContractMap["tableName"] = GeneConstants.DOMAIN_EIA_CONTRACT
            eiaContractMap["tableNameId"] = it.id
            eiaContractMap["afterInputDept"] = afterOrgName
            eiaContractMap["afterInputDeptId"] = Long.valueOf(afterOrgId)
            eiaContractMap["afterInputDeptCode"] = afterOrgCode
            eiaContractMap["afterInputUser"] = afterStaffName
            eiaContractMap["afterInputUserId"]= Long.valueOf(afterStaffId)
            eiaContractMap["agoInputDept"] = it.inputDept
            eiaContractMap["agoInputDeptId"] = it.inputDeptId
            eiaContractMap["agoInputDeptCode"] = it.inputDeptCode
            eiaContractMap["agoInputUser"] = it.inputUser
            eiaContractMap["agoInputUserId"]= it.inputUserId
            eiaContractList.add(eiaContractMap)
            it.inputDept = afterOrgName
            it.inputDeptId = Long.valueOf(afterOrgId)
            it.inputDeptCode = afterOrgCode
            it.inputUser = afterStaffName
            it.inputUserId = Long.valueOf(afterStaffId)

            /**
             * 更新财务数据，根据contractId
             */
            def eiaInvoice= EiaInvoice.findAllByIfDelAndContractId(false,it?.id)
            eiaInvoice.each{
                it.inputDept = afterOrgName
                it.inputDeptId = Long.valueOf(afterOrgId)
                it.inputDeptCode = afterOrgCode
                it.inputUser = afterStaffName
                it.inputUserId = Long.valueOf(afterStaffId)
            }
            def eiaIncomeOut= EiaIncomeOut.findAllByIfDelAndContractId(false,it?.id)
            eiaIncomeOut.each{
                it.inputDept = afterOrgName
                it.inputDeptId = Long.valueOf(afterOrgId)
                it.inputDeptCode = afterOrgCode
                it.inputUser = afterStaffName
                it.inputUserId = Long.valueOf(afterStaffId)
            }
        }
        /**
         * 更新资质信息
         */
        def eiaCert = EiaCert.findAllByIfDelAndInputUserIdAndInputDeptId(false,Long.valueOf(agoStaffId),Long.valueOf(agoOrgId))
        eiaCert.each{
            def eiaCertMap = [:]
            eiaCertMap["tableName"] = GeneConstants.DOMAIN_EIA_CERT
            eiaCertMap["tableNameId"] = it.id
            eiaCertMap["afterInputDept"] = afterOrgName
            eiaCertMap["afterInputDeptId"] = Long.valueOf(afterOrgId)
            eiaCertMap["afterInputDeptCode"] = afterOrgCode
            eiaCertMap["afterInputUser"] = afterStaffName
            eiaCertMap["afterInputUserId"]= Long.valueOf(afterStaffId)
            eiaCertMap["agoInputDept"] = it.inputDept
            eiaCertMap["agoInputDeptId"] = it.inputDeptId
            eiaCertMap["agoInputDeptCode"] = it.inputDeptCode
            eiaCertMap["agoInputUser"] = it.inputUser
            eiaCertMap["agoInputUserId"]= it.inputUserId
            eiaCertList.add(eiaCertMap)
            it.inputDept = afterOrgName
            it.inputDeptId = Long.valueOf(afterOrgId)
            it.inputDeptCode = afterOrgCode
            it.inputUser = afterStaffName
            it.inputUserId = Long.valueOf(afterStaffId)
            /**
             * 跟资质有关的流程变更，根据tableId和tableName进行项目变更
             */
            def eiaWorkFlowBusi = EiaWorkFlowBusi.findAllByIfDelAndTableNameAndTableNameId(false,GeneConstants.DOMAIN_EIA_CERT,it.id)
            eiaWorkFlowBusi.each{
                def eiaWorkFlowBusiMap = [:]
                eiaWorkFlowBusiMap["tableName"] = GeneConstants.DOMAIN_EIA_WLOW_BUSI
                eiaWorkFlowBusiMap["tableNameId"] = it.id
                eiaWorkFlowBusiMap["afterInputDept"] = afterOrgName
                eiaWorkFlowBusiMap["afterInputDeptId"] = Long.valueOf(afterOrgId)
                eiaWorkFlowBusiMap["afterInputDeptCode"] = afterOrgCode
                eiaWorkFlowBusiMap["afterInputUser"] = afterStaffName
                eiaWorkFlowBusiMap["afterInputUserId"]= Long.valueOf(afterStaffId)
                eiaWorkFlowBusiMap["agoInputDept"] = it.inputDept
                eiaWorkFlowBusiMap["agoInputDeptId"] = it.inputDeptId
                eiaWorkFlowBusiMap["agoInputDeptCode"] = it.inputDeptCode
                eiaWorkFlowBusiMap["agoInputUser"] = it.inputUser
                eiaWorkFlowBusiMap["agoInputUserId"]= it.inputUserId
                eiaWorkFlowBusiList.add(eiaWorkFlowBusiMap)
                it.inputDept = afterOrgName
                it.inputDeptId = Long.valueOf(afterOrgId)
                it.inputDeptCode = afterOrgCode
                it.inputUser = afterStaffName
                it.inputUserId = Long.valueOf(afterStaffId)
            }
            /**
             * 更新流程信息(参与人，更加updateDeptId和updateUserId更新)
             */
            def eiaWorkFlowBusiUpdate = EiaWorkFlowBusi.findAllByIfDelAndUpdateUserIdAndUpdateDeptIdAndTableNameAndTableNameId(false,Long.valueOf(agoStaffId),Long.valueOf(agoOrgId),GeneConstants.DOMAIN_EIA_CERT,it.id)
            eiaWorkFlowBusiUpdate.each{
                it.updateDept = afterOrgName
                it.updateDeptId = Long.valueOf(afterOrgId)
                it.updateDeptCode = afterOrgCode
                it.updateUser = afterStaffName
                it.updateUserId = Long.valueOf(afterStaffId)
            }
            /**
             * 更新流程信息(审核人)
             */
            def eiaWorkFlowBusiAuth = EiaWorkFlowBusi.findAllByIfDelAndAuthCodeAndTableNameAndTableNameId(false,Long.valueOf(agoStaffId),GeneConstants.DOMAIN_EIA_CERT,it.id)
            eiaWorkFlowBusiAuth.each{
                it.authName = afterOrgName
                it.authCode = Long.valueOf(afterStaffId)
            }
            /**
             * 流程日志表
             */
            def eiaWorkFlowBusiLog = EiaWorkFlowBusiLog.findAllByIfDelAndTableNameAndTableNameId(false,GeneConstants.DOMAIN_EIA_CERT,it.id)
            eiaWorkFlowBusiLog.each{
                def eiaWorkFlowBusiLogMap = [:]
                eiaWorkFlowBusiLogMap["tableName"] = GeneConstants.DOMAIN_EIA_WLOW_BUSI_LOG
                eiaWorkFlowBusiLogMap["tableNameId"] = it.id
                eiaWorkFlowBusiLogMap["afterInputDept"] = afterOrgName
                eiaWorkFlowBusiLogMap["afterInputDeptId"] = Long.valueOf(afterOrgId)
                eiaWorkFlowBusiLogMap["afterInputDeptCode"] = afterOrgCode
                eiaWorkFlowBusiLogMap["afterInputUser"] = afterStaffName
                eiaWorkFlowBusiLogMap["afterInputUserId"]= Long.valueOf(afterStaffId)
                eiaWorkFlowBusiLogMap["agoInputDept"] = it.inputDept
                eiaWorkFlowBusiLogMap["agoInputDeptId"] = it.inputDeptId
                eiaWorkFlowBusiLogMap["agoInputDeptCode"] = it.updateDeptCode
                eiaWorkFlowBusiLogMap["agoInputUser"] = it.inputUser
                eiaWorkFlowBusiLogMap["agoInputUserId"]= it.inputUserId
                eiaWorkFlowBusiLogList.add(eiaWorkFlowBusiLogMap)
                it.inputDept = afterOrgName
                it.inputDeptId = Long.valueOf(afterOrgId)
                it.inputUser = afterStaffName
                it.inputUserId = Long.valueOf(afterStaffId)
            }
            /**流程日志表
             * 更新流程信息(参与人，更加updateDeptId和updateUserId更新)
             */
            def eiaWorkFlowBusiLogUpdate = EiaWorkFlowBusiLog.findAllByIfDelAndUpdateUserIdAndUpdateDeptIdAndTableNameAndTableNameId(false,Long.valueOf(agoStaffId),Long.valueOf(agoOrgId),GeneConstants.DOMAIN_EIA_CERT,it.id)
            eiaWorkFlowBusiLogUpdate.each{
                it.updateDept = afterOrgName
                it.updateDeptId = Long.valueOf(afterOrgId)
                it.updateDeptCode = afterOrgCode
                it.updateUser = afterStaffName
                it.updateUserId = Long.valueOf(afterStaffId)
            }
            /**
             * 更新流程信息(审核人)
             */
            def eiaWorkFlowBusiLogAuth = EiaWorkFlowBusiLog.findAllByIfDelAndAuthCodeAndTableNameAndTableNameId(false,Long.valueOf(agoStaffId),GeneConstants.DOMAIN_EIA_CERT,it.id)
            eiaWorkFlowBusiLogAuth.each{
                it.authName = afterOrgName
                it.authCode = Long.valueOf(afterStaffId)
            }
        }
        /**
         * 更新项目信息
         */
        def eiaProject = EiaProject.findAllByIfDelAndInputUserIdAndInputDeptId(false,Long.valueOf(agoStaffId),Long.valueOf(agoOrgId))
        eiaProject.each{
            def eiaProjectMap = [:]
            eiaProjectMap["tableName"] = GeneConstants.DOMAIN_EIA_PROJECT
            eiaProjectMap["tableNameId"] = it.id
            eiaProjectMap["afterInputDept"] = afterOrgName
            eiaProjectMap["afterInputDeptId"] = Long.valueOf(afterOrgId)
            eiaProjectMap["afterInputDeptCode"] = afterOrgCode
            eiaProjectMap["afterInputUser"] = afterStaffName
            eiaProjectMap["afterInputUserId"]= Long.valueOf(afterStaffId)
            eiaProjectMap["agoInputDept"] = it.inputDept
            eiaProjectMap["agoInputDeptId"] = it.inputDeptId
            eiaProjectMap["agoInputDeptCode"] = it.inputDeptCode
            eiaProjectMap["agoInputUser"] = it.inputUser
            eiaProjectMap["agoInputUserId"]= it.inputUserId
            eiaProjectList.add(eiaProjectMap)
            it.inputDept = afterOrgName
            it.inputDeptId = Long.valueOf(afterOrgId)
            it.inputDeptCode = afterOrgCode
            it.inputUser = afterStaffName
            it.inputUserId = Long.valueOf(afterStaffId)
            /**
             * 更新项目子表信息
             */
            def eiaEnvProject = EiaEnvProject.findAllByIfDelAndEiaProjectId(false,it.id)
            eiaEnvProject.each{
                it.inputDept = afterOrgName
                it.inputDeptId = Long.valueOf(afterOrgId)
                it.inputDeptCode = afterOrgCode
                it.inputUser = afterStaffName
                it.inputUserId = Long.valueOf(afterStaffId)
            }
            def eiaEneProject = EiaEneProject.findAllByIfDelAndEiaProjectId(false,it.id)
            eiaEneProject.each{
                it.inputDept = afterOrgName
                it.inputDeptId = Long.valueOf(afterOrgId)
                it.inputDeptCode = afterOrgCode
                it.inputUser = afterStaffName
                it.inputUserId = Long.valueOf(afterStaffId)
            }
            /**
             * 绿色金融
             */
            def eiaGreenProject = EiaGreenProject.findAllByIfDelAndId(false,it.id)
            eiaGreenProject.each{
                it.inputDept = afterOrgName
                it.inputDeptId = Long.valueOf(afterOrgId)
                it.inputDeptCode = afterOrgCode
                it.inputUser = afterStaffName
                it.inputUserId = Long.valueOf(afterStaffId)
            }
        }
        /**
         * 更新工作方案信息
         */
        def eiaProjectPlan = EiaProjectPlan.findAllByIfDelAndInputUserIdAndInputDeptId(false,Long.valueOf(agoStaffId),Long.valueOf(agoOrgId))
        eiaProjectPlan.each{
            def eiaProjectPlanMap = [:]
            eiaProjectPlanMap["tableName"] = GeneConstants.DOMAIN_EIA_PROJECT_PLAN
            eiaProjectPlanMap["tableNameId"] = it.id
            eiaProjectPlanMap["afterInputDept"] = afterOrgName
            eiaProjectPlanMap["afterInputDeptId"] = Long.valueOf(afterOrgId)
            eiaProjectPlanMap["afterInputDeptCode"] = afterOrgCode
            eiaProjectPlanMap["afterInputUser"] = afterStaffName
            eiaProjectPlanMap["afterInputUserId"]= Long.valueOf(afterStaffId)
            eiaProjectPlanMap["agoInputDept"] = it.inputDept
            eiaProjectPlanMap["agoInputDeptId"] = it.inputDeptId
            eiaProjectPlanMap["agoInputDeptCode"] = it.inputDeptCode
            eiaProjectPlanMap["agoInputUser"] = it.inputUser
            eiaProjectPlanMap["agoInputUserId"]= it.inputUserId
            eiaProjectPlanList.add(eiaProjectPlanMap)
            it.inputDept = afterOrgName
            it.inputDeptId = Long.valueOf(afterOrgId)
            it.inputDeptCode = afterOrgCode
            it.inputUser = afterStaffName
            it.inputUserId = Long.valueOf(afterStaffId)
            /**
             * 更新工作方案审核表信息(审核节点有关的)
             */
            def eiaProjectPlanItem = EiaProjectPlanItem.findAllByIfDelAndEiaProjectPlanIdAndNodeUserId(false,it.id,agoStaffId)
            eiaProjectPlanItem.each{
                it.nodeUserId =  Long.valueOf(afterStaffId)
                it.nodeUserName = afterStaffName
            }
            /**
             * 工作方案节点人员名称
             */
            def eiaProjectPlanItemUser = EiaProjectPlanItem.findAllByIfDelAndUserNamesAndEiaProjectPlanId(false,agoStaffName,it.id)
            eiaProjectPlanItemUser.each{
                it.userNames = afterStaffName
            }
        }
        /**
         * 更新流程信息
         */
        def eiaWorkFlowBusi = EiaWorkFlowBusi.findAllByIfDelAndInputUserIdAndInputDeptId(false,Long.valueOf(agoStaffId),Long.valueOf(agoOrgId))
        eiaWorkFlowBusi.each{
            def eiaWorkFlowBusiMap = [:]
            eiaWorkFlowBusiMap["tableName"] = GeneConstants.DOMAIN_EIA_WLOW_BUSI
            eiaWorkFlowBusiMap["tableNameId"] = it.id
            eiaWorkFlowBusiMap["afterInputDept"] = afterOrgName
            eiaWorkFlowBusiMap["afterInputDeptId"] = Long.valueOf(afterOrgId)
            eiaWorkFlowBusiMap["afterInputDeptCode"] = afterOrgCode
            eiaWorkFlowBusiMap["afterInputUser"] = afterStaffName
            eiaWorkFlowBusiMap["afterInputUserId"]= Long.valueOf(afterStaffId)
            eiaWorkFlowBusiMap["agoInputDept"] = it.inputDept
            eiaWorkFlowBusiMap["agoInputDeptId"] = it.inputDeptId
            eiaWorkFlowBusiMap["agoInputDeptCode"] = it.inputDeptCode
            eiaWorkFlowBusiMap["agoInputUser"] = it.inputUser
            eiaWorkFlowBusiMap["agoInputUserId"]= it.inputUserId
            eiaWorkFlowBusiList.add(eiaWorkFlowBusiMap)
            it.inputDept = afterOrgName
            it.inputDeptId = Long.valueOf(afterOrgId)
            it.inputDeptCode = afterOrgCode
            it.inputUser = afterStaffName
            it.inputUserId = Long.valueOf(afterStaffId)
        }
        /**
         * 更新流程信息(参与人，更加updateDeptId和updateUserId更新)
         */
        def eiaWorkFlowBusiUpdate = EiaWorkFlowBusi.findAllByIfDelAndUpdateUserIdAndUpdateDeptId(false,Long.valueOf(agoStaffId),Long.valueOf(agoOrgId))
        eiaWorkFlowBusiUpdate.each{
            it.updateDept = afterOrgName
            it.updateDeptId = Long.valueOf(afterOrgId)
            it.updateDeptCode = afterOrgCode
            it.updateUser = afterStaffName
            it.updateUserId = Long.valueOf(afterStaffId)
        }
        /**
         * 更新流程信息(审核人)
         */
        def eiaWorkFlowBusiAuth = EiaWorkFlowBusi.findAllByIfDelAndAuthCode(false,Long.valueOf(agoStaffId))
        eiaWorkFlowBusiAuth.each{
            it.authName = afterOrgName
            it.authCode = Long.valueOf(afterStaffId)
        }
        /**
         * 流程日志表
         */
        def eiaWorkFlowBusiLog = EiaWorkFlowBusiLog.findAllByIfDelAndInputUserIdAndInputDeptId(false,Long.valueOf(agoStaffId),Long.valueOf(agoOrgId))
        eiaWorkFlowBusiLog.each{
            def eiaWorkFlowBusiLogMap = [:]
            eiaWorkFlowBusiLogMap["tableName"] = GeneConstants.DOMAIN_EIA_WLOW_BUSI_LOG
            eiaWorkFlowBusiLogMap["tableNameId"] = it.id
            eiaWorkFlowBusiLogMap["afterInputDept"] = afterOrgName
            eiaWorkFlowBusiLogMap["afterInputDeptId"] = Long.valueOf(afterOrgId)
            eiaWorkFlowBusiLogMap["afterInputDeptCode"] = afterOrgCode
            eiaWorkFlowBusiLogMap["afterInputUser"] = afterStaffName
            eiaWorkFlowBusiLogMap["afterInputUserId"]= Long.valueOf(afterStaffId)
            eiaWorkFlowBusiLogMap["agoInputDept"] = it.inputDept
            eiaWorkFlowBusiLogMap["agoInputDeptId"] = it.inputDeptId
            eiaWorkFlowBusiLogMap["agoInputDeptCode"] = it.updateDeptCode
            eiaWorkFlowBusiLogMap["agoInputUser"] = it.inputUser
            eiaWorkFlowBusiLogMap["agoInputUserId"]= it.inputUserId
            eiaWorkFlowBusiLogList.add(eiaWorkFlowBusiLogMap)
            it.inputDept = afterOrgName
            it.inputDeptId = Long.valueOf(afterOrgId)
            it.inputUser = afterStaffName
            it.inputUserId = Long.valueOf(afterStaffId)
        }
        /**流程日志表
         * 更新流程信息(参与人，更加updateDeptId和updateUserId更新)
         */
        def eiaWorkFlowBusiLogUpdate = EiaWorkFlowBusiLog.findAllByIfDelAndUpdateUserIdAndUpdateDeptId(false,Long.valueOf(agoStaffId),Long.valueOf(agoOrgId))
        eiaWorkFlowBusiLogUpdate.each{
            it.updateDept = afterOrgName
            it.updateDeptId = Long.valueOf(afterOrgId)
            it.updateDeptCode = afterOrgCode
            it.updateUser = afterStaffName
            it.updateUserId = Long.valueOf(afterStaffId)
        }
        /**
         * 更新流程信息(审核人)
         */
        def eiaWorkFlowBusiLogAuth = EiaWorkFlowBusiLog.findAllByIfDelAndAuthCode(false,Long.valueOf(agoStaffId))
        eiaWorkFlowBusiLogAuth.each{
            it.authName = afterOrgName
            it.authCode = Long.valueOf(afterStaffId)
        }
        /**
         * 统一保存数据维护日志表
         */
        eiaDataMaintainList.add(eiaClientList)
        eiaDataMaintainList.add(eiaTaskList)
        eiaDataMaintainList.add(eiaOfferList)
        eiaDataMaintainList.add(eiaContractList)
        eiaDataMaintainList.add(eiaCertList)
        eiaDataMaintainList.add(eiaProjectList)
        eiaDataMaintainList.add(eiaProjectPlanList)
        eiaDataMaintainList.add(eiaWorkFlowBusiList)
        eiaDataMaintainList.add(eiaWorkFlowBusiLogList)
        for(int j=0;j<eiaDataMaintainList.size();j++){
            eiaDataMaintainLongSave(eiaDataMaintainList.get(j),session)
        }
        return  true
    }
    /**
     * 分项目数据更新（合同，报价，任务，项目，工作方案，企业，流程）
     */
    def dataMaintainSubSave(params,session){
        /**
         * 当前人员
         */
        def agoInfo = params.taskRoleToast
        /**
         * 变更后人员名称
         */
        def afterInfo = params.taskRoleExam
        /**
         * 维护类型
         */
        def maintainType =  params.maintainType
        /**
         * 当前人员
         */
        JSONArray agoInfos = new JSONArray(agoInfo)
        /**
         * 变更前部门信息
         */
        def agoOrgId
        def agoStaffName
        def agoStaffId
        /**
         * 变更后部门信息
         */
        def afterOrgId
        def afterOrgName
        def afterOrgCode
        def afterStaffName
        def afterStaffId
        for (int i = 0; i < agoInfos.length(); i++) {
            JSONObject agoInfosUser = agoInfos.getJSONObject(i);
            agoOrgId = agoInfosUser.getString("orgId")
            agoStaffName = agoInfosUser.getString("staffName")
            agoStaffId = agoInfosUser.getString("staffId")
        }
        /***
         * 变更后人员信息
         */
        JSONArray afterInfos = new JSONArray(afterInfo)
        for (int i = 0; i < afterInfos.length(); i++) {
            JSONObject afterInfosUser = afterInfos.getJSONObject(i);
            afterOrgId = afterInfosUser.getString("orgId")
            afterOrgName = afterInfosUser.getString("orgName")
            afterOrgCode = afterInfosUser.getString("orgCode")
            afterStaffName = afterInfosUser.getString("staffName")
            afterStaffId = afterInfosUser.getString("staffId")
        }
        def eiaDataMaintainList = []
        def eiaClientList = []
        def eiaTaskList = []
        def eiaOfferList = []
        def eiaContractList = []
        def eiaCertList = []
        def eiaProjectList = []
        def eiaProjectPlanList = []
        def eiaWorkFlowBusiList = []
        def eiaWorkFlowBusiLogList = []
        /**
         * 变更企业信息(根据不同的类型判断)
         */
        if(params.clientId || GeneConstants.DOMAIN_EIA_CLIENT.equals(maintainType)){
            def eiaClient
            if(params.clientId){
                eiaClient = EiaClient.findAllByIfDelAndId(false,params.long("clientId"))
            }else{
                eiaClient = EiaClient.findAllByIfDelAndInputUserIdAndInputDeptId(false, Long.valueOf(agoStaffId),Long.valueOf(agoOrgId))
            }
            eiaClient.each{
                def eiaClientMap = [:]
                eiaClientMap["tableName"] = GeneConstants.DOMAIN_EIA_CLIENT
                eiaClientMap["tableNameId"] = it.id
                eiaClientMap["afterInputDept"] = afterOrgName
                eiaClientMap["afterInputDeptId"] = Long.valueOf(afterOrgId)
                eiaClientMap["afterInputDeptCode"] = afterOrgCode
                eiaClientMap["afterInputUser"] = afterStaffName
                eiaClientMap["afterInputUserId"]= Long.valueOf(afterStaffId)
                eiaClientMap["agoInputDept"] = it.inputDept
                eiaClientMap["agoInputDeptId"] = it.inputDeptId
                eiaClientMap["agoInputDeptCode"] = it.inputDeptCode
                eiaClientMap["agoInputUser"] = it.inputUser
                eiaClientMap["agoInputUserId"]= it.inputUserId
                eiaClientList.add(eiaClientMap)
                it.inputDept = afterOrgName
                it.inputDeptId = Long.valueOf(afterOrgId)
                it.inputDeptCode = afterOrgCode
                it.inputUser = afterStaffName
                it.inputUserId = Long.valueOf(afterStaffId)
                def eiaClientContacts = EiaClientContacts.findAllByIfDelAndEiaClientId(false,it.id)
                eiaClientContacts.each{
                    it.inputDept = afterOrgName
                    it.inputDeptId = Long.valueOf(afterOrgId)
                    it.inputDeptCode = afterOrgCode
                    it.inputUser = afterStaffName
                    it.inputUserId = Long.valueOf(afterStaffId)
                }
            }
        }
        /**
         * 变更任务信息
         */
        if(params.taskId || GeneConstants.DOMAIN_EIA_TASK.equals(maintainType)){
            def eiaTask
            if(params.taskId){
                eiaTask = EiaTask.findAllByIfDelAndId(false,params.long("taskId"))
            }else{
                eiaTask = EiaTask.findAllByIfDelAndInputUserIdAndInputDeptId(false, Long.valueOf(agoStaffId),Long.valueOf(agoOrgId))
            }
            eiaTask.each{
                def eiaTaskMap = [:]
                eiaTaskMap["tableName"] = GeneConstants.DOMAIN_EIA_TASK
                eiaTaskMap["tableNameId"] = it.id
                eiaTaskMap["afterInputDept"] = afterOrgName
                eiaTaskMap["afterInputDeptId"] = Long.valueOf(afterOrgId)
                eiaTaskMap["afterInputDeptCode"] = afterOrgCode
                eiaTaskMap["afterInputUser"] = afterStaffName
                eiaTaskMap["afterInputUserId"]= Long.valueOf(afterStaffId)
                eiaTaskMap["agoInputDept"] = it.inputDept
                eiaTaskMap["agoInputDeptId"] = it.inputDeptId
                eiaTaskMap["agoInputDeptCode"] = it.inputDeptCode
                eiaTaskMap["agoInputUser"] = it.inputUser
                eiaTaskMap["agoInputUserId"]= it.inputUserId
                eiaTaskList.add(eiaTaskMap)
                it.inputDept = afterOrgName
                it.inputDeptId = Long.valueOf(afterOrgId)
                it.inputDeptCode = afterOrgCode
                it.inputUser = afterStaffName
                it.inputUserId = Long.valueOf(afterStaffId)
                /**
                 * 人员分配编号
                 */
                def eiaTaskAssignMaintain = EiaTaskAssign.findAllByIfDelAndTaskAssignUserIdAndTaskAssignDeptIdAndTaskId(false,Long.valueOf(agoStaffId),Long.valueOf(agoOrgId),it.id)
                eiaTaskAssignMaintain.each{
                    it.taskAssignDept = afterOrgName
                    it.taskAssignDeptId = Long.valueOf(afterOrgId)
                    it.taskAssignUser = afterStaffName
                    it.taskAssignUserId = Long.valueOf(afterStaffId)
                    /**
                     * 任务分派录入人
                     */
                    String taskAssignUserInfo = ""
                    def eiaTaskAssign = EiaTaskAssign.findAllByIfDelAndTaskId(false,it.id)
                    eiaTaskAssign.each{
                        it.inputDept = afterOrgName
                        it.inputDeptId = Long.valueOf(afterOrgId)
                        it.inputUser = afterStaffName
                        it.inputUserId = Long.valueOf(afterStaffId)
                        if(it.taskAssignUserId == Long.valueOf(afterStaffId)){
                            it.taskAssignUser = afterStaffName
                            it.taskAssignUserId = Long.valueOf(afterStaffId)
                        }
                        taskAssignUserInfo += it.taskAssignUser + "_" + it.taskAssignUserId + ','
                    }
                }
                /**
                 * 更新任务中的分配人员
                 */
                def taskAssignUserInfo = ""
                def eiaTaskAssign = EiaTaskAssign.findAllByIfDelAndTaskId(false,it.id)
                eiaTaskAssign.each{
                    taskAssignUserInfo += it.taskAssignUser + "_" + it.taskAssignUserId + ','
                }
                /**
                 * 变更任务分配人
                 */
                def eiaTaskInfo = EiaTask.findByIfDelAndId(false,it.id)
                if(taskAssignUserInfo.isEmpty()){
                    eiaTaskInfo.taskAssignUser = afterStaffName + "_" + afterStaffId
                }else{
                    eiaTaskInfo.taskAssignUser = taskAssignUserInfo.substring(0, taskAssignUserInfo.length() - 1)
                }
                eiaTaskInfo.save(flush: true, failOnError: true)
                /**
                 * 更新跟任务相关的所有信息
                 */
                def eiaOffer = EiaOffer.findAllByIfDelAndTaskId(false,it?.id)
                eiaOffer.each{
                    def eiaOfferMap = [:]
                    eiaOfferMap["tableName"] = GeneConstants.DOMAIN_EIA_OFFER
                    eiaOfferMap["tableNameId"] = it.id
                    eiaOfferMap["afterInputDept"] = afterOrgName
                    eiaOfferMap["afterInputDeptId"] = Long.valueOf(afterOrgId)
                    eiaOfferMap["afterInputDeptCode"] = afterOrgCode
                    eiaOfferMap["afterInputUser"] = afterStaffName
                    eiaOfferMap["afterInputUserId"]= Long.valueOf(afterStaffId)
                    eiaOfferMap["agoInputDept"] = it.inputDept
                    eiaOfferMap["agoInputDeptId"] = it.inputDeptId
                    eiaOfferMap["agoInputDeptCode"] = it.inputDeptCode
                    eiaOfferMap["agoInputUser"] = it.inputUser
                    eiaOfferMap["agoInputUserId"]= it.inputUserId
                    eiaOfferList.add(eiaOfferMap)
                    it.inputDept = afterOrgName
                    it.inputDeptId = Long.valueOf(afterOrgId)
                    it.inputDeptCode = afterOrgCode
                    it.inputUser = afterStaffName
                    it.inputUserId = Long.valueOf(afterStaffId)
                    it.inputDept = afterOrgName
                    it.inputDeptId = Long.valueOf(afterOrgId)
                    it.inputDeptCode = afterOrgCode
                    it.inputUser = afterStaffName
                    it.inputUserId = Long.valueOf(afterStaffId)
                }
                /**
                 * 监测方案
                 */
                def eiaProjectPlan = EiaProjectPlan.findAllByIfDelAndEiaTaskId(false,it.id)
                eiaProjectPlan.each{
                    def eiaProjectPlanMap = [:]
                    eiaProjectPlanMap["tableName"] = GeneConstants.DOMAIN_EIA_PROJECT_PLAN
                    eiaProjectPlanMap["tableNameId"] = it.id
                    eiaProjectPlanMap["afterInputDept"] = afterOrgName
                    eiaProjectPlanMap["afterInputDeptId"] = Long.valueOf(afterOrgId)
                    eiaProjectPlanMap["afterInputDeptCode"] = afterOrgCode
                    eiaProjectPlanMap["afterInputUser"] = afterStaffName
                    eiaProjectPlanMap["afterInputUserId"]= Long.valueOf(afterStaffId)
                    eiaProjectPlanMap["agoInputDept"] = it.inputDept
                    eiaProjectPlanMap["agoInputDeptId"] = it.inputDeptId
                    eiaProjectPlanMap["agoInputDeptCode"] = it.inputDeptCode
                    eiaProjectPlanMap["agoInputUser"] = it.inputUser
                    eiaProjectPlanMap["agoInputUserId"]= it.inputUserId
                    eiaProjectPlanList.add(eiaProjectPlanMap)
                    it.inputDept = afterOrgName
                    it.inputDeptId = Long.valueOf(afterOrgId)
                    it.inputDeptCode = afterOrgCode
                    it.inputUser = afterStaffName
                    it.inputUserId = Long.valueOf(afterStaffId)
                    /**
                     * 更新工作方案审核表信息(审核节点有关的)
                     */
                    def eiaProjectPlanItem = EiaProjectPlanItem.findAllByIfDelAndEiaProjectPlanIdAndNodeUserId(false,it.id,agoStaffId)
                    eiaProjectPlanItem.each{
                        it.nodeUserId =  Long.valueOf(afterStaffId)
                        it.nodeUserName = afterStaffName
                    }
                    /**
                     * 工作方案节点人员名称
                     */
                    def eiaProjectPlanItemUser = EiaProjectPlanItem.findAllByIfDelAndUserNamesAndEiaProjectPlanId(false,agoStaffName,it.id)
                    eiaProjectPlanItemUser.each{
                        it.userNames = afterStaffName
                    }
                }
                /**
                 * 合同变更
                 */
                def eiaContract = EiaContract.findAllByIfDelAndTaskId(false,it.id)
                eiaContract.each{
                    def eiaContractMap = [:]
                    eiaContractMap["tableName"] = GeneConstants.DOMAIN_EIA_CONTRACT
                    eiaContractMap["tableNameId"] = it.id
                    eiaContractMap["afterInputDept"] = afterOrgName
                    eiaContractMap["afterInputDeptId"] = Long.valueOf(afterOrgId)
                    eiaContractMap["afterInputDeptCode"] = afterOrgCode
                    eiaContractMap["afterInputUser"] = afterStaffName
                    eiaContractMap["afterInputUserId"]= Long.valueOf(afterStaffId)
                    eiaContractMap["agoInputDept"] = it.inputDept
                    eiaContractMap["agoInputDeptId"] = it.inputDeptId
                    eiaContractMap["agoInputDeptCode"] = it.inputDeptCode
                    eiaContractMap["agoInputUser"] = it.inputUser
                    eiaContractMap["agoInputUserId"]= it.inputUserId
                    eiaContractList.add(eiaContractMap)
                    it.inputDept = afterOrgName
                    it.inputDeptId = Long.valueOf(afterOrgId)
                    it.inputDeptCode = afterOrgCode
                    it.inputUser = afterStaffName
                    it.inputUserId = Long.valueOf(afterStaffId)

                    /**
                     * 更新财务数据，根据contractId
                     */
                    def eiaInvoice= EiaInvoice.findAllByIfDelAndContractId(false,it?.id)
                    eiaInvoice.each{
                        it.inputDept = afterOrgName
                        it.inputDeptId = Long.valueOf(afterOrgId)
                        it.inputDeptCode = afterOrgCode
                        it.inputUser = afterStaffName
                        it.inputUserId = Long.valueOf(afterStaffId)
                    }
                    def eiaIncomeOut= EiaIncomeOut.findAllByIfDelAndContractId(false,it?.id)
                    eiaIncomeOut.each{
                        it.inputDept = afterOrgName
                        it.inputDeptId = Long.valueOf(afterOrgId)
                        it.inputDeptCode = afterOrgCode
                        it.inputUser = afterStaffName
                        it.inputUserId = Long.valueOf(afterStaffId)
                    }
                    /**
                     * 更新流程信息(获取跟他有关的审核信息)
                     * 根据tableName和tableNameId更新
                     */
                    def eiaWorkFlowBusi = EiaWorkFlowBusi.findAllByIfDelAndTableNameAndTableNameId(false,GeneConstants.DOMAIN_EIA_CONTRACT,it.id)
                    eiaWorkFlowBusi.each{
                        def eiaWorkFlowBusiMap = [:]
                        eiaWorkFlowBusiMap["tableName"] = GeneConstants.DOMAIN_EIA_WLOW_BUSI
                        eiaWorkFlowBusiMap["tableNameId"] = it.id
                        eiaWorkFlowBusiMap["afterInputDept"] = afterOrgName
                        eiaWorkFlowBusiMap["afterInputDeptId"] = Long.valueOf(afterOrgId)
                        eiaWorkFlowBusiMap["afterInputDeptCode"] = afterOrgCode
                        eiaWorkFlowBusiMap["afterInputUser"] = afterStaffName
                        eiaWorkFlowBusiMap["afterInputUserId"]= Long.valueOf(afterStaffId)
                        eiaWorkFlowBusiMap["agoInputDept"] = it.inputDept
                        eiaWorkFlowBusiMap["agoInputDeptId"] = it.inputDeptId
                        eiaWorkFlowBusiMap["agoInputDeptCode"] = it.inputDeptCode
                        eiaWorkFlowBusiMap["agoInputUser"] = it.inputUser
                        eiaWorkFlowBusiMap["agoInputUserId"]= it.inputUserId
                        eiaWorkFlowBusiList.add(eiaWorkFlowBusiMap)
                        it.inputDept = afterOrgName
                        it.inputDeptId = Long.valueOf(afterOrgId)
                        it.inputDeptCode = afterOrgCode
                        it.inputUser = afterStaffName
                        it.inputUserId = Long.valueOf(afterStaffId)
                    }
                    /**
                     * 更新流程信息(参与人，更加updateDeptId和updateUserId更新)
                     */
                    def eiaWorkFlowBusiUpdate = EiaWorkFlowBusi.findAllByIfDelAndUpdateUserIdAndUpdateDeptIdAndTableNameAndTableNameId(false,Long.valueOf(agoStaffId),Long.valueOf(agoOrgId),GeneConstants.DOMAIN_EIA_CONTRACT,it.id)
                    eiaWorkFlowBusiUpdate.each{
                        it.updateDept = afterOrgName
                        it.updateDeptId = Long.valueOf(afterOrgId)
                        it.updateDeptCode = afterOrgCode
                        it.updateUser = afterStaffName
                        it.updateUserId = Long.valueOf(afterStaffId)
                    }
                    /**
                     * 更新流程信息(审核人)
                     */
                    def eiaWorkFlowBusiAuth = EiaWorkFlowBusi.findAllByIfDelAndAuthCodeAndTableNameAndTableNameId(false,Long.valueOf(agoStaffId),GeneConstants.DOMAIN_EIA_CONTRACT,it.id)
                    eiaWorkFlowBusiAuth.each{
                        it.authName = afterOrgName
                        it.authCode = Long.valueOf(afterStaffId)
                    }
                    /**
                     * 流程日志表
                     */
                    def eiaWorkFlowBusiLog = EiaWorkFlowBusiLog.findAllByIfDelAndTableNameAndTableNameId(false,GeneConstants.DOMAIN_EIA_CONTRACT,it.id)
                    eiaWorkFlowBusiLog.each{
                        def eiaWorkFlowBusiLogMap = [:]
                        eiaWorkFlowBusiLogMap["tableName"] = GeneConstants.DOMAIN_EIA_WLOW_BUSI_LOG
                        eiaWorkFlowBusiLogMap["tableNameId"] = it.id
                        eiaWorkFlowBusiLogMap["afterInputDept"] = afterOrgName
                        eiaWorkFlowBusiLogMap["afterInputDeptId"] = Long.valueOf(afterOrgId)
                        eiaWorkFlowBusiLogMap["afterInputDeptCode"] = afterOrgCode
                        eiaWorkFlowBusiLogMap["afterInputUser"] = afterStaffName
                        eiaWorkFlowBusiLogMap["afterInputUserId"]= Long.valueOf(afterStaffId)
                        eiaWorkFlowBusiLogMap["agoInputDept"] = it.inputDept
                        eiaWorkFlowBusiLogMap["agoInputDeptId"] = it.inputDeptId
                        eiaWorkFlowBusiLogMap["agoInputDeptCode"] = it.updateDeptCode
                        eiaWorkFlowBusiLogMap["agoInputUser"] = it.inputUser
                        eiaWorkFlowBusiLogMap["agoInputUserId"]= it.inputUserId
                        eiaWorkFlowBusiLogList.add(eiaWorkFlowBusiLogMap)
                        it.inputDept = afterOrgName
                        it.inputDeptId = Long.valueOf(afterOrgId)
                        it.inputUser = afterStaffName
                        it.inputUserId = Long.valueOf(afterStaffId)
                    }
                    /**流程日志表
                     * 更新流程信息(参与人，更加updateDeptId和updateUserId更新)
                     */
                    def eiaWorkFlowBusiLogUpdate = EiaWorkFlowBusiLog.findAllByIfDelAndUpdateUserIdAndUpdateDeptIdAndTableNameAndTableNameId(false,Long.valueOf(agoStaffId),Long.valueOf(agoOrgId),GeneConstants.DOMAIN_EIA_CONTRACT,it.id)
                    eiaWorkFlowBusiLogUpdate.each{
                        it.updateDept = afterOrgName
                        it.updateDeptId = Long.valueOf(afterOrgId)
                        it.updateDeptCode = afterOrgCode
                        it.updateUser = afterStaffName
                        it.updateUserId = Long.valueOf(afterStaffId)
                    }
                    /**
                     * 更新流程信息(审核人)
                     */
                    def eiaWorkFlowBusiLogAuth = EiaWorkFlowBusiLog.findAllByIfDelAndAuthCodeAndTableNameAndTableNameId(false,Long.valueOf(agoStaffId),GeneConstants.DOMAIN_EIA_CONTRACT,it.id)
                    eiaWorkFlowBusiLogAuth.each{
                        it.authName = afterOrgName
                        it.authCode = Long.valueOf(afterStaffId)
                    }
                }
                /**
                 *项目信息维护
                 */
                def eiaProject = EiaProject.findAllByIfDelAndEiaTaskId(false,it?.id)
                eiaProject.each{
                    def eiaProjectMap = [:]
                    eiaProjectMap["tableName"] = GeneConstants.DOMAIN_EIA_PROJECT
                    eiaProjectMap["tableNameId"] = it.id
                    eiaProjectMap["afterInputDept"] = afterOrgName
                    eiaProjectMap["afterInputDeptId"] = Long.valueOf(afterOrgId)
                    eiaProjectMap["afterInputDeptCode"] = afterOrgCode
                    eiaProjectMap["afterInputUser"] = afterStaffName
                    eiaProjectMap["afterInputUserId"]= Long.valueOf(afterStaffId)
                    eiaProjectMap["agoInputDept"] = it.inputDept
                    eiaProjectMap["agoInputDeptId"] = it.inputDeptId
                    eiaProjectMap["agoInputDeptCode"] = it.inputDeptCode
                    eiaProjectMap["agoInputUser"] = it.inputUser
                    eiaProjectMap["agoInputUserId"]= it.inputUserId
                    eiaProjectList.add(eiaProjectMap)
                    it.inputDept = afterOrgName
                    it.inputDeptId = Long.valueOf(afterOrgId)
                    it.inputDeptCode = afterOrgCode
                    it.inputUser = afterStaffName
                    it.inputUserId = Long.valueOf(afterStaffId)
                    /**
                     * 更新项目子表信息
                     */
                    def eiaEnvProject = EiaEnvProject.findAllByIfDelAndEiaProjectId(false,it.id)
                    eiaEnvProject.each{
                        it.inputDept = afterOrgName
                        it.inputDeptId = Long.valueOf(afterOrgId)
                        it.inputDeptCode = afterOrgCode
                        it.inputUser = afterStaffName
                        it.inputUserId = Long.valueOf(afterStaffId)
                    }
                    def eiaEneProject = EiaEneProject.findAllByIfDelAndEiaProjectId(false,it.id)
                    eiaEneProject.each{
                        it.inputDept = afterOrgName
                        it.inputDeptId = Long.valueOf(afterOrgId)
                        it.inputDeptCode = afterOrgCode
                        it.inputUser = afterStaffName
                        it.inputUserId = Long.valueOf(afterStaffId)
                    }
                    /**
                     * 绿色金融
                     */
                    def eiaGreenProject = EiaGreenProject.findAllByIfDelAndId(false,it.id)
                    eiaGreenProject.each{
                        it.inputDept = afterOrgName
                        it.inputDeptId = Long.valueOf(afterOrgId)
                        it.inputDeptCode = afterOrgCode
                        it.inputUser = afterStaffName
                        it.inputUserId = Long.valueOf(afterStaffId)
                    }
                    /**
                     * 跟项目有关的流程变更，根据tableId和tableName进行项目变更
                     */
                    def eiaWorkFlowBusi = EiaWorkFlowBusi.findAllByIfDelAndTableNameAndTableNameId(false,GeneConstants.DOMAIN_EIA_PROJECT,it.id)
                    eiaWorkFlowBusi.each{
                        def eiaWorkFlowBusiMap = [:]
                        eiaWorkFlowBusiMap["tableName"] = GeneConstants.DOMAIN_EIA_WLOW_BUSI
                        eiaWorkFlowBusiMap["tableNameId"] = it.id
                        eiaWorkFlowBusiMap["afterInputDept"] = afterOrgName
                        eiaWorkFlowBusiMap["afterInputDeptId"] = Long.valueOf(afterOrgId)
                        eiaWorkFlowBusiMap["afterInputDeptCode"] = afterOrgCode
                        eiaWorkFlowBusiMap["afterInputUser"] = afterStaffName
                        eiaWorkFlowBusiMap["afterInputUserId"]= Long.valueOf(afterStaffId)
                        eiaWorkFlowBusiMap["agoInputDept"] = it.inputDept
                        eiaWorkFlowBusiMap["agoInputDeptId"] = it.inputDeptId
                        eiaWorkFlowBusiMap["agoInputDeptCode"] = it.inputDeptCode
                        eiaWorkFlowBusiMap["agoInputUser"] = it.inputUser
                        eiaWorkFlowBusiMap["agoInputUserId"]= it.inputUserId
                        eiaWorkFlowBusiList.add(eiaWorkFlowBusiMap)
                        it.inputDept = afterOrgName
                        it.inputDeptId = Long.valueOf(afterOrgId)
                        it.inputDeptCode = afterOrgCode
                        it.inputUser = afterStaffName
                        it.inputUserId = Long.valueOf(afterStaffId)
                    }
                    /**
                     * 更新流程信息(参与人，更加updateDeptId和updateUserId更新)
                     */
                    def eiaWorkFlowBusiUpdate = EiaWorkFlowBusi.findAllByIfDelAndUpdateUserIdAndUpdateDeptIdAndTableNameAndTableNameId(false,Long.valueOf(agoStaffId),Long.valueOf(agoOrgId),GeneConstants.DOMAIN_EIA_PROJECT,it.id)
                    eiaWorkFlowBusiUpdate.each{
                        it.updateDept = afterOrgName
                        it.updateDeptId = Long.valueOf(afterOrgId)
                        it.updateDeptCode = afterOrgCode
                        it.updateUser = afterStaffName
                        it.updateUserId = Long.valueOf(afterStaffId)
                    }
                    /**
                     * 更新流程信息(审核人)
                     */
                    def eiaWorkFlowBusiAuth = EiaWorkFlowBusi.findAllByIfDelAndAuthCodeAndTableNameAndTableNameId(false,Long.valueOf(agoStaffId),GeneConstants.DOMAIN_EIA_PROJECT,it.id)
                    eiaWorkFlowBusiAuth.each{
                        it.authName = afterOrgName
                        it.authCode = Long.valueOf(afterStaffId)
                    }
                    /**
                     * 流程日志表
                     */
                    def eiaWorkFlowBusiLog = EiaWorkFlowBusiLog.findAllByIfDelAndTableNameAndTableNameId(false,GeneConstants.DOMAIN_EIA_PROJECT,it.id)
                    eiaWorkFlowBusiLog.each{
                        def eiaWorkFlowBusiLogMap = [:]
                        eiaWorkFlowBusiLogMap["tableName"] = GeneConstants.DOMAIN_EIA_WLOW_BUSI_LOG
                        eiaWorkFlowBusiLogMap["tableNameId"] = it.id
                        eiaWorkFlowBusiLogMap["afterInputDept"] = afterOrgName
                        eiaWorkFlowBusiLogMap["afterInputDeptId"] = Long.valueOf(afterOrgId)
                        eiaWorkFlowBusiLogMap["afterInputDeptCode"] = afterOrgCode
                        eiaWorkFlowBusiLogMap["afterInputUser"] = afterStaffName
                        eiaWorkFlowBusiLogMap["afterInputUserId"]= Long.valueOf(afterStaffId)
                        eiaWorkFlowBusiLogMap["agoInputDept"] = it.inputDept
                        eiaWorkFlowBusiLogMap["agoInputDeptId"] = it.inputDeptId
                        eiaWorkFlowBusiLogMap["agoInputDeptCode"] = it.updateDeptCode
                        eiaWorkFlowBusiLogMap["agoInputUser"] = it.inputUser
                        eiaWorkFlowBusiLogMap["agoInputUserId"]= it.inputUserId
                        eiaWorkFlowBusiLogList.add(eiaWorkFlowBusiLogMap)
                        it.inputDept = afterOrgName
                        it.inputDeptId = Long.valueOf(afterOrgId)
                        it.inputUser = afterStaffName
                        it.inputUserId = Long.valueOf(afterStaffId)
                    }
                    /**流程日志表
                     * 更新流程信息(参与人，更加updateDeptId和updateUserId更新)
                     */
                    def eiaWorkFlowBusiLogUpdate = EiaWorkFlowBusiLog.findAllByIfDelAndUpdateUserIdAndUpdateDeptIdAndTableNameAndTableNameId(false,Long.valueOf(agoStaffId),Long.valueOf(agoOrgId),GeneConstants.DOMAIN_EIA_PROJECT,it.id)
                    eiaWorkFlowBusiLogUpdate.each{
                        it.updateDept = afterOrgName
                        it.updateDeptId = Long.valueOf(afterOrgId)
                        it.updateDeptCode = afterOrgCode
                        it.updateUser = afterStaffName
                        it.updateUserId = Long.valueOf(afterStaffId)
                    }
                    /**
                     * 更新流程信息(审核人)
                     */
                    def eiaWorkFlowBusiLogAuth = EiaWorkFlowBusiLog.findAllByIfDelAndAuthCodeAndTableNameAndTableNameId(false,Long.valueOf(agoStaffId),GeneConstants.DOMAIN_EIA_PROJECT,it.id)
                    eiaWorkFlowBusiLogAuth.each{
                        it.authName = afterOrgName
                        it.authCode = Long.valueOf(afterStaffId)
                    }
                }
                /**
                 *资质维护
                 */
                def eiaCert = EiaCert.findAllByIfDelAndEiaTaskId(false,it?.id)
                eiaCert.each{
                    def eiaCertMap = [:]
                    eiaCertMap["tableName"] = GeneConstants.DOMAIN_EIA_CERT
                    eiaCertMap["tableNameId"] = it.id
                    eiaCertMap["afterInputDept"] = afterOrgName
                    eiaCertMap["afterInputDeptId"] = Long.valueOf(afterOrgId)
                    eiaCertMap["afterInputDeptCode"] = afterOrgCode
                    eiaCertMap["afterInputUser"] = afterStaffName
                    eiaCertMap["afterInputUserId"]= Long.valueOf(afterStaffId)
                    eiaCertMap["agoInputDept"] = it.inputDept
                    eiaCertMap["agoInputDeptId"] = it.inputDeptId
                    eiaCertMap["agoInputDeptCode"] = it.inputDeptCode
                    eiaCertMap["agoInputUser"] = it.inputUser
                    eiaCertMap["agoInputUserId"]= it.inputUserId
                    eiaCertList.add(eiaCertMap)
                    it.inputDept = afterOrgName
                    it.inputDeptId = Long.valueOf(afterOrgId)
                    it.inputDeptCode = afterOrgCode
                    it.inputUser = afterStaffName
                    it.inputUserId = Long.valueOf(afterStaffId)
                    /**
                     * 跟资质有关的流程变更，根据tableId和tableName进行项目变更
                     */
                    def eiaWorkFlowBusi = EiaWorkFlowBusi.findAllByIfDelAndTableNameAndTableNameId(false,GeneConstants.DOMAIN_EIA_CERT,it.id)
                    eiaWorkFlowBusi.each{
                        def eiaWorkFlowBusiMap = [:]
                        eiaWorkFlowBusiMap["tableName"] = GeneConstants.DOMAIN_EIA_WLOW_BUSI
                        eiaWorkFlowBusiMap["tableNameId"] = it.id
                        eiaWorkFlowBusiMap["afterInputDept"] = afterOrgName
                        eiaWorkFlowBusiMap["afterInputDeptId"] = Long.valueOf(afterOrgId)
                        eiaWorkFlowBusiMap["afterInputDeptCode"] = afterOrgCode
                        eiaWorkFlowBusiMap["afterInputUser"] = afterStaffName
                        eiaWorkFlowBusiMap["afterInputUserId"]= Long.valueOf(afterStaffId)
                        eiaWorkFlowBusiMap["agoInputDept"] = it.inputDept
                        eiaWorkFlowBusiMap["agoInputDeptId"] = it.inputDeptId
                        eiaWorkFlowBusiMap["agoInputDeptCode"] = it.inputDeptCode
                        eiaWorkFlowBusiMap["agoInputUser"] = it.inputUser
                        eiaWorkFlowBusiMap["agoInputUserId"]= it.inputUserId
                        eiaWorkFlowBusiList.add(eiaWorkFlowBusiMap)
                        it.inputDept = afterOrgName
                        it.inputDeptId = Long.valueOf(afterOrgId)
                        it.inputDeptCode = afterOrgCode
                        it.inputUser = afterStaffName
                        it.inputUserId = Long.valueOf(afterStaffId)
                    }
                    /**
                     * 更新流程信息(参与人，更加updateDeptId和updateUserId更新)
                     */
                    def eiaWorkFlowBusiUpdate = EiaWorkFlowBusi.findAllByIfDelAndUpdateUserIdAndUpdateDeptIdAndTableNameAndTableNameId(false,Long.valueOf(agoStaffId),Long.valueOf(agoOrgId),GeneConstants.DOMAIN_EIA_CERT,it.id)
                    eiaWorkFlowBusiUpdate.each{
                        it.updateDept = afterOrgName
                        it.updateDeptId = Long.valueOf(afterOrgId)
                        it.updateDeptCode = afterOrgCode
                        it.updateUser = afterStaffName
                        it.updateUserId = Long.valueOf(afterStaffId)
                    }
                    /**
                     * 更新流程信息(审核人)
                     */
                    def eiaWorkFlowBusiAuth = EiaWorkFlowBusi.findAllByIfDelAndAuthCodeAndTableNameAndTableNameId(false,Long.valueOf(agoStaffId),GeneConstants.DOMAIN_EIA_CERT,it.id)
                    eiaWorkFlowBusiAuth.each{
                        it.authName = afterOrgName
                        it.authCode = Long.valueOf(afterStaffId)
                    }
                    /**
                     * 流程日志表
                     */
                    def eiaWorkFlowBusiLog = EiaWorkFlowBusiLog.findAllByIfDelAndTableNameAndTableNameId(false,GeneConstants.DOMAIN_EIA_CERT,it.id)
                    eiaWorkFlowBusiLog.each{
                        def eiaWorkFlowBusiLogMap = [:]
                        eiaWorkFlowBusiLogMap["tableName"] = GeneConstants.DOMAIN_EIA_WLOW_BUSI_LOG
                        eiaWorkFlowBusiLogMap["tableNameId"] = it.id
                        eiaWorkFlowBusiLogMap["afterInputDept"] = afterOrgName
                        eiaWorkFlowBusiLogMap["afterInputDeptId"] = Long.valueOf(afterOrgId)
                        eiaWorkFlowBusiLogMap["afterInputDeptCode"] = afterOrgCode
                        eiaWorkFlowBusiLogMap["afterInputUser"] = afterStaffName
                        eiaWorkFlowBusiLogMap["afterInputUserId"]= Long.valueOf(afterStaffId)
                        eiaWorkFlowBusiLogMap["agoInputDept"] = it.inputDept
                        eiaWorkFlowBusiLogMap["agoInputDeptId"] = it.inputDeptId
                        eiaWorkFlowBusiLogMap["agoInputDeptCode"] = it.updateDeptCode
                        eiaWorkFlowBusiLogMap["agoInputUser"] = it.inputUser
                        eiaWorkFlowBusiLogMap["agoInputUserId"]= it.inputUserId
                        eiaWorkFlowBusiLogList.add(eiaWorkFlowBusiLogMap)
                        it.inputDept = afterOrgName
                        it.inputDeptId = Long.valueOf(afterOrgId)
                        it.inputUser = afterStaffName
                        it.inputUserId = Long.valueOf(afterStaffId)
                    }
                    /**流程日志表
                     * 更新流程信息(参与人，更加updateDeptId和updateUserId更新)
                     */
                    def eiaWorkFlowBusiLogUpdate = EiaWorkFlowBusiLog.findAllByIfDelAndUpdateUserIdAndUpdateDeptIdAndTableNameAndTableNameId(false,Long.valueOf(agoStaffId),Long.valueOf(agoOrgId),GeneConstants.DOMAIN_EIA_CERT,it.id)
                    eiaWorkFlowBusiLogUpdate.each{
                        it.updateDept = afterOrgName
                        it.updateDeptId = Long.valueOf(afterOrgId)
                        it.updateDeptCode = afterOrgCode
                        it.updateUser = afterStaffName
                        it.updateUserId = Long.valueOf(afterStaffId)
                    }
                    /**
                     * 更新流程信息(审核人)
                     */
                    def eiaWorkFlowBusiLogAuth = EiaWorkFlowBusiLog.findAllByIfDelAndAuthCodeAndTableNameAndTableNameId(false,Long.valueOf(agoStaffId),GeneConstants.DOMAIN_EIA_CERT,it.id)
                    eiaWorkFlowBusiLogAuth.each{
                        it.authName = afterOrgName
                        it.authCode = Long.valueOf(afterStaffId)
                    }
                }
            }
        }
        /**
         * 变更报价信息
         */
        if(params.offerId || GeneConstants.DOMAIN_EIA_LAB_OFFER.equals(maintainType)){
            def eiaOffer
            if(params.offerId){
                eiaOffer = EiaOffer.findAllByIfDelAndId(false,params.long("offerId"))
            }else {
                eiaOffer = EiaOffer.findAllByIfDelAndInputUserIdAndInputDeptId(false,Long.valueOf(agoStaffId),Long.valueOf(agoOrgId))
            }
            eiaOffer.each{
                def eiaOfferMap = [:]
                eiaOfferMap["tableName"] = GeneConstants.DOMAIN_EIA_OFFER
                eiaOfferMap["tableNameId"] = it.id
                eiaOfferMap["afterInputDept"] = afterOrgName
                eiaOfferMap["afterInputDeptId"] = Long.valueOf(afterOrgId)
                eiaOfferMap["afterInputDeptCode"] = afterOrgCode
                eiaOfferMap["afterInputUser"] = afterStaffName
                eiaOfferMap["afterInputUserId"]= Long.valueOf(afterStaffId)
                eiaOfferMap["agoInputDept"] = it.inputDept
                eiaOfferMap["agoInputDeptId"] = it.inputDeptId
                eiaOfferMap["agoInputDeptCode"] = it.inputDeptCode
                eiaOfferMap["agoInputUser"] = it.inputUser
                eiaOfferMap["agoInputUserId"]= it.inputUserId
                eiaOfferList.add(eiaOfferMap)
                it.inputDept = afterOrgName
                it.inputDeptId = Long.valueOf(afterOrgId)
                it.inputDeptCode = afterOrgCode
                it.inputUser = afterStaffName
                it.inputUserId = Long.valueOf(afterStaffId)
                it.inputDept = afterOrgName
                it.inputDeptId = Long.valueOf(afterOrgId)
                it.inputDeptCode = afterOrgCode
                it.inputUser = afterStaffName
                it.inputUserId = Long.valueOf(afterStaffId)
            }
        }
        /**
         * 监测方案信息
         */
        if(params.projectPlanId || GeneConstants.DOMAIN_EIA_PROJECT_PLAN.equals(maintainType)){
            def eiaProjectPlan
            if(params.projectPlanId){
                eiaProjectPlan = EiaProjectPlan.findAllByIfDelAndEiaProjectId(false,params.long("projectPlanId"))
            }else {
                eiaProjectPlan = EiaProjectPlan.findAllByIfDelAndInputUserIdAndInputDeptId(false,Long.valueOf(agoStaffId),Long.valueOf(agoOrgId))
            }
            eiaProjectPlan.each{
                def eiaProjectPlanMap = [:]
                eiaProjectPlanMap["tableName"] = GeneConstants.DOMAIN_EIA_PROJECT_PLAN
                eiaProjectPlanMap["tableNameId"] = it.id
                eiaProjectPlanMap["afterInputDept"] = afterOrgName
                eiaProjectPlanMap["afterInputDeptId"] = Long.valueOf(afterOrgId)
                eiaProjectPlanMap["afterInputDeptCode"] = afterOrgCode
                eiaProjectPlanMap["afterInputUser"] = afterStaffName
                eiaProjectPlanMap["afterInputUserId"]= Long.valueOf(afterStaffId)
                eiaProjectPlanMap["agoInputDept"] = it.inputDept
                eiaProjectPlanMap["agoInputDeptId"] = it.inputDeptId
                eiaProjectPlanMap["agoInputDeptCode"] = it.inputDeptCode
                eiaProjectPlanMap["agoInputUser"] = it.inputUser
                eiaProjectPlanMap["agoInputUserId"]= it.inputUserId
                eiaProjectPlanList.add(eiaProjectPlanMap)
                it.inputDept = afterOrgName
                it.inputDeptId = Long.valueOf(afterOrgId)
                it.inputDeptCode = afterOrgCode
                it.inputUser = afterStaffName
                it.inputUserId = Long.valueOf(afterStaffId)
                /**
                 * 更新工作方案审核表信息(审核节点有关的)
                 */
                def eiaProjectPlanItem = EiaProjectPlanItem.findAllByIfDelAndEiaProjectPlanIdAndNodeUserId(false,it.id,agoStaffId)
                eiaProjectPlanItem.each{
                    it.nodeUserId =  Long.valueOf(afterStaffId)
                    it.nodeUserName = afterStaffName
                }
                /**
                 * 工作方案节点人员名称
                 */
                def eiaProjectPlanItemUser = EiaProjectPlanItem.findAllByIfDelAndUserNamesAndEiaProjectPlanId(false,agoStaffName,it.id)
                eiaProjectPlanItemUser.each{
                    it.userNames = afterStaffName
                }
            }
        }
        /**
         * 变更合同信息
         */
        if(params.contractId ||  GeneConstants.DOMAIN_EIA_CONTRACT.equals(maintainType)){
            def eiaContract
            if(params.contractId){
                eiaContract = EiaContract.findAllByIfDelAndId(false,params.long("contractId"))
            }else{
                eiaContract = EiaContract.findAllByIfDelAndInputUserIdAndInputDeptId(false,Long.valueOf(agoStaffId),Long.valueOf(agoOrgId))
            }
            eiaContract.each{
                def eiaContractMap = [:]
                eiaContractMap["tableName"] = GeneConstants.DOMAIN_EIA_CONTRACT
                eiaContractMap["tableNameId"] = it.id
                eiaContractMap["afterInputDept"] = afterOrgName
                eiaContractMap["afterInputDeptId"] = Long.valueOf(afterOrgId)
                eiaContractMap["afterInputDeptCode"] = afterOrgCode
                eiaContractMap["afterInputUser"] = afterStaffName
                eiaContractMap["afterInputUserId"]= Long.valueOf(afterStaffId)
                eiaContractMap["agoInputDept"] = it.inputDept
                eiaContractMap["agoInputDeptId"] = it.inputDeptId
                eiaContractMap["agoInputDeptCode"] = it.inputDeptCode
                eiaContractMap["agoInputUser"] = it.inputUser
                eiaContractMap["agoInputUserId"]= it.inputUserId
                eiaContractList.add(eiaContractMap)
                it.inputDept = afterOrgName
                it.inputDeptId = Long.valueOf(afterOrgId)
                it.inputDeptCode = afterOrgCode
                it.inputUser = afterStaffName
                it.inputUserId = Long.valueOf(afterStaffId)
                /**
                 * 更新财务数据，根据contractId
                 */
                def eiaInvoice= EiaInvoice.findAllByIfDelAndContractId(false,it?.id)
                eiaInvoice.each{
                    it.inputDept = afterOrgName
                    it.inputDeptId = Long.valueOf(afterOrgId)
                    it.inputDeptCode = afterOrgCode
                    it.inputUser = afterStaffName
                    it.inputUserId = Long.valueOf(afterStaffId)
                }
                def eiaIncomeOut= EiaIncomeOut.findAllByIfDelAndContractId(false,it?.id)
                eiaIncomeOut.each{
                    it.inputDept = afterOrgName
                    it.inputDeptId = Long.valueOf(afterOrgId)
                    it.inputDeptCode = afterOrgCode
                    it.inputUser = afterStaffName
                    it.inputUserId = Long.valueOf(afterStaffId)
                }
                /**
                 * 更新流程信息(获取跟他有关的审核信息)
                 * 根据tableName和tableNameId更新
                 */
                def eiaWorkFlowBusi = EiaWorkFlowBusi.findAllByIfDelAndTableNameAndTableNameId(false,GeneConstants.DOMAIN_EIA_CONTRACT,it.id)
                eiaWorkFlowBusi.each{
                    def eiaWorkFlowBusiMap = [:]
                    eiaWorkFlowBusiMap["tableName"] = GeneConstants.DOMAIN_EIA_WLOW_BUSI
                    eiaWorkFlowBusiMap["tableNameId"] = it.id
                    eiaWorkFlowBusiMap["afterInputDept"] = afterOrgName
                    eiaWorkFlowBusiMap["afterInputDeptId"] = Long.valueOf(afterOrgId)
                    eiaWorkFlowBusiMap["afterInputDeptCode"] = afterOrgCode
                    eiaWorkFlowBusiMap["afterInputUser"] = afterStaffName
                    eiaWorkFlowBusiMap["afterInputUserId"]= Long.valueOf(afterStaffId)
                    eiaWorkFlowBusiMap["agoInputDept"] = it.inputDept
                    eiaWorkFlowBusiMap["agoInputDeptId"] = it.inputDeptId
                    eiaWorkFlowBusiMap["agoInputDeptCode"] = it.inputDeptCode
                    eiaWorkFlowBusiMap["agoInputUser"] = it.inputUser
                    eiaWorkFlowBusiMap["agoInputUserId"]= it.inputUserId
                    eiaWorkFlowBusiList.add(eiaWorkFlowBusiMap)
                    it.inputDept = afterOrgName
                    it.inputDeptId = Long.valueOf(afterOrgId)
                    it.inputDeptCode = afterOrgCode
                    it.inputUser = afterStaffName
                    it.inputUserId = Long.valueOf(afterStaffId)
                }
                /**
                 * 更新流程信息(参与人，更加updateDeptId和updateUserId更新)
                 */
                def eiaWorkFlowBusiUpdate = EiaWorkFlowBusi.findAllByIfDelAndUpdateUserIdAndUpdateDeptIdAndTableNameAndTableNameId(false,Long.valueOf(agoStaffId),Long.valueOf(agoOrgId),GeneConstants.DOMAIN_EIA_CONTRACT,it.id)
                eiaWorkFlowBusiUpdate.each{
                    it.updateDept = afterOrgName
                    it.updateDeptId = Long.valueOf(afterOrgId)
                    it.updateDeptCode = afterOrgCode
                    it.updateUser = afterStaffName
                    it.updateUserId = Long.valueOf(afterStaffId)
                }
                /**
                 * 更新流程信息(审核人)
                 */
                def eiaWorkFlowBusiAuth = EiaWorkFlowBusi.findAllByIfDelAndAuthCodeAndTableNameAndTableNameId(false,Long.valueOf(agoStaffId),GeneConstants.DOMAIN_EIA_CONTRACT,it.id)
                eiaWorkFlowBusiAuth.each{
                    it.authName = afterOrgName
                    it.authCode = Long.valueOf(afterStaffId)
                }
                /**
                 * 流程日志表
                 */
                def eiaWorkFlowBusiLog = EiaWorkFlowBusiLog.findAllByIfDelAndTableNameAndTableNameId(false,GeneConstants.DOMAIN_EIA_CONTRACT,it.id)
                eiaWorkFlowBusiLog.each{
                    def eiaWorkFlowBusiLogMap = [:]
                    eiaWorkFlowBusiLogMap["tableName"] = GeneConstants.DOMAIN_EIA_WLOW_BUSI_LOG
                    eiaWorkFlowBusiLogMap["tableNameId"] = it.id
                    eiaWorkFlowBusiLogMap["afterInputDept"] = afterOrgName
                    eiaWorkFlowBusiLogMap["afterInputDeptId"] = Long.valueOf(afterOrgId)
                    eiaWorkFlowBusiLogMap["afterInputDeptCode"] = afterOrgCode
                    eiaWorkFlowBusiLogMap["afterInputUser"] = afterStaffName
                    eiaWorkFlowBusiLogMap["afterInputUserId"]= Long.valueOf(afterStaffId)
                    eiaWorkFlowBusiLogMap["agoInputDept"] = it.inputDept
                    eiaWorkFlowBusiLogMap["agoInputDeptId"] = it.inputDeptId
                    eiaWorkFlowBusiLogMap["agoInputDeptCode"] = it.updateDeptCode
                    eiaWorkFlowBusiLogMap["agoInputUser"] = it.inputUser
                    eiaWorkFlowBusiLogMap["agoInputUserId"]= it.inputUserId
                    eiaWorkFlowBusiLogList.add(eiaWorkFlowBusiLogMap)
                    it.inputDept = afterOrgName
                    it.inputDeptId = Long.valueOf(afterOrgId)
                    it.inputUser = afterStaffName
                    it.inputUserId = Long.valueOf(afterStaffId)
                }
                /**流程日志表
                 * 更新流程信息(参与人，更加updateDeptId和updateUserId更新)
                 */
                def eiaWorkFlowBusiLogUpdate = EiaWorkFlowBusiLog.findAllByIfDelAndUpdateUserIdAndUpdateDeptIdAndTableNameAndTableNameId(false,Long.valueOf(agoStaffId),Long.valueOf(agoOrgId),GeneConstants.DOMAIN_EIA_CONTRACT,it.id)
                eiaWorkFlowBusiLogUpdate.each{
                    it.updateDept = afterOrgName
                    it.updateDeptId = Long.valueOf(afterOrgId)
                    it.updateDeptCode = afterOrgCode
                    it.updateUser = afterStaffName
                    it.updateUserId = Long.valueOf(afterStaffId)
                }
                /**
                 * 更新流程信息(审核人)
                 */
                def eiaWorkFlowBusiLogAuth = EiaWorkFlowBusiLog.findAllByIfDelAndAuthCodeAndTableNameAndTableNameId(false,Long.valueOf(agoStaffId),GeneConstants.DOMAIN_EIA_CONTRACT,it.id)
                eiaWorkFlowBusiLogAuth.each{
                    it.authName = afterOrgName
                    it.authCode = Long.valueOf(afterStaffId)
                }
            }
        }
        /**
         * 变更项目信息
         */
        if(params.projectId || GeneConstants.DOMAIN_EIA_PROJECT.equals(maintainType)){
            /**
             * 更新项目信息
             */
            def eiaProject
            if(params.projectId){
                eiaProject = EiaProject.findAllByIfDelAndId(false,params.long("projectId"))
            }else{
                eiaProject = EiaProject.findAllByIfDelAndInputUserIdAndInputDeptId(false,Long.valueOf(agoStaffId),Long.valueOf(agoOrgId))
            }
            eiaProject.each{
                def eiaProjectMap = [:]
                eiaProjectMap["tableName"] = GeneConstants.DOMAIN_EIA_PROJECT
                eiaProjectMap["tableNameId"] = it.id
                eiaProjectMap["afterInputDept"] = afterOrgName
                eiaProjectMap["afterInputDeptId"] = Long.valueOf(afterOrgId)
                eiaProjectMap["afterInputDeptCode"] = afterOrgCode
                eiaProjectMap["afterInputUser"] = afterStaffName
                eiaProjectMap["afterInputUserId"]= Long.valueOf(afterStaffId)
                eiaProjectMap["agoInputDept"] = it.inputDept
                eiaProjectMap["agoInputDeptId"] = it.inputDeptId
                eiaProjectMap["agoInputDeptCode"] = it.inputDeptCode
                eiaProjectMap["agoInputUser"] = it.inputUser
                eiaProjectMap["agoInputUserId"]= it.inputUserId
                eiaProjectList.add(eiaProjectMap)
                it.inputDept = afterOrgName
                it.inputDeptId = Long.valueOf(afterOrgId)
                it.inputDeptCode = afterOrgCode
                it.inputUser = afterStaffName
                it.inputUserId = Long.valueOf(afterStaffId)
                /**
                 * 更新项目子表信息
                 */
                def eiaEnvProject = EiaEnvProject.findAllByIfDelAndEiaProjectId(false,it.id)
                eiaEnvProject.each{
                    it.inputDept = afterOrgName
                    it.inputDeptId = Long.valueOf(afterOrgId)
                    it.inputDeptCode = afterOrgCode
                    it.inputUser = afterStaffName
                    it.inputUserId = Long.valueOf(afterStaffId)
                }
                def eiaEneProject = EiaEneProject.findAllByIfDelAndEiaProjectId(false,it.id)
                eiaEneProject.each{
                    it.inputDept = afterOrgName
                    it.inputDeptId = Long.valueOf(afterOrgId)
                    it.inputDeptCode = afterOrgCode
                    it.inputUser = afterStaffName
                    it.inputUserId = Long.valueOf(afterStaffId)
                }
                /**
                 * 绿色金融
                 */
                def eiaGreenProject = EiaGreenProject.findAllByIfDelAndId(false,it.id)
                eiaGreenProject.each{
                    it.inputDept = afterOrgName
                    it.inputDeptId = Long.valueOf(afterOrgId)
                    it.inputDeptCode = afterOrgCode
                    it.inputUser = afterStaffName
                    it.inputUserId = Long.valueOf(afterStaffId)
                }
                /**
                 * 跟项目有关的流程变更，根据tableId和tableName进行项目变更
                 */
                def eiaWorkFlowBusi = EiaWorkFlowBusi.findAllByIfDelAndTableNameAndTableNameId(false,GeneConstants.DOMAIN_EIA_PROJECT,it.id)
                eiaWorkFlowBusi.each{
                    def eiaWorkFlowBusiMap = [:]
                    eiaWorkFlowBusiMap["tableName"] = GeneConstants.DOMAIN_EIA_WLOW_BUSI
                    eiaWorkFlowBusiMap["tableNameId"] = it.id
                    eiaWorkFlowBusiMap["afterInputDept"] = afterOrgName
                    eiaWorkFlowBusiMap["afterInputDeptId"] = Long.valueOf(afterOrgId)
                    eiaWorkFlowBusiMap["afterInputDeptCode"] = afterOrgCode
                    eiaWorkFlowBusiMap["afterInputUser"] = afterStaffName
                    eiaWorkFlowBusiMap["afterInputUserId"]= Long.valueOf(afterStaffId)
                    eiaWorkFlowBusiMap["agoInputDept"] = it.inputDept
                    eiaWorkFlowBusiMap["agoInputDeptId"] = it.inputDeptId
                    eiaWorkFlowBusiMap["agoInputDeptCode"] = it.inputDeptCode
                    eiaWorkFlowBusiMap["agoInputUser"] = it.inputUser
                    eiaWorkFlowBusiMap["agoInputUserId"]= it.inputUserId
                    eiaWorkFlowBusiList.add(eiaWorkFlowBusiMap)
                    it.inputDept = afterOrgName
                    it.inputDeptId = Long.valueOf(afterOrgId)
                    it.inputDeptCode = afterOrgCode
                    it.inputUser = afterStaffName
                    it.inputUserId = Long.valueOf(afterStaffId)
                }
                /**
                 * 更新流程信息(参与人，更加updateDeptId和updateUserId更新)
                 */
                def eiaWorkFlowBusiUpdate = EiaWorkFlowBusi.findAllByIfDelAndUpdateUserIdAndUpdateDeptIdAndTableNameAndTableNameId(false,Long.valueOf(agoStaffId),Long.valueOf(agoOrgId),GeneConstants.DOMAIN_EIA_PROJECT,it.id)
                eiaWorkFlowBusiUpdate.each{
                    it.updateDept = afterOrgName
                    it.updateDeptId = Long.valueOf(afterOrgId)
                    it.updateDeptCode = afterOrgCode
                    it.updateUser = afterStaffName
                    it.updateUserId = Long.valueOf(afterStaffId)
                }
                /**
                 * 更新流程信息(审核人)
                 */
                def eiaWorkFlowBusiAuth = EiaWorkFlowBusi.findAllByIfDelAndAuthCodeAndTableNameAndTableNameId(false,Long.valueOf(agoStaffId),GeneConstants.DOMAIN_EIA_PROJECT,it.id)
                eiaWorkFlowBusiAuth.each{
                    it.authName = afterOrgName
                    it.authCode = Long.valueOf(afterStaffId)
                }
                /**
                 * 流程日志表
                 */
                def eiaWorkFlowBusiLog = EiaWorkFlowBusiLog.findAllByIfDelAndTableNameAndTableNameId(false,GeneConstants.DOMAIN_EIA_PROJECT,it.id)
                eiaWorkFlowBusiLog.each{
                    def eiaWorkFlowBusiLogMap = [:]
                    eiaWorkFlowBusiLogMap["tableName"] = GeneConstants.DOMAIN_EIA_WLOW_BUSI_LOG
                    eiaWorkFlowBusiLogMap["tableNameId"] = it.id
                    eiaWorkFlowBusiLogMap["afterInputDept"] = afterOrgName
                    eiaWorkFlowBusiLogMap["afterInputDeptId"] = Long.valueOf(afterOrgId)
                    eiaWorkFlowBusiLogMap["afterInputDeptCode"] = afterOrgCode
                    eiaWorkFlowBusiLogMap["afterInputUser"] = afterStaffName
                    eiaWorkFlowBusiLogMap["afterInputUserId"]= Long.valueOf(afterStaffId)
                    eiaWorkFlowBusiLogMap["agoInputDept"] = it.inputDept
                    eiaWorkFlowBusiLogMap["agoInputDeptId"] = it.inputDeptId
                    eiaWorkFlowBusiLogMap["agoInputDeptCode"] = it.updateDeptCode
                    eiaWorkFlowBusiLogMap["agoInputUser"] = it.inputUser
                    eiaWorkFlowBusiLogMap["agoInputUserId"]= it.inputUserId
                    eiaWorkFlowBusiLogList.add(eiaWorkFlowBusiLogMap)
                    it.inputDept = afterOrgName
                    it.inputDeptId = Long.valueOf(afterOrgId)
                    it.inputUser = afterStaffName
                    it.inputUserId = Long.valueOf(afterStaffId)
                }
                /**流程日志表
                 * 更新流程信息(参与人，更加updateDeptId和updateUserId更新)
                 */
                def eiaWorkFlowBusiLogUpdate = EiaWorkFlowBusiLog.findAllByIfDelAndUpdateUserIdAndUpdateDeptIdAndTableNameAndTableNameId(false,Long.valueOf(agoStaffId),Long.valueOf(agoOrgId),GeneConstants.DOMAIN_EIA_PROJECT,it.id)
                eiaWorkFlowBusiLogUpdate.each{
                    it.updateDept = afterOrgName
                    it.updateDeptId = Long.valueOf(afterOrgId)
                    it.updateDeptCode = afterOrgCode
                    it.updateUser = afterStaffName
                    it.updateUserId = Long.valueOf(afterStaffId)
                }
                /**
                 * 更新流程信息(审核人)
                 */
                def eiaWorkFlowBusiLogAuth = EiaWorkFlowBusiLog.findAllByIfDelAndAuthCodeAndTableNameAndTableNameId(false,Long.valueOf(agoStaffId),GeneConstants.DOMAIN_EIA_PROJECT,it.id)
                eiaWorkFlowBusiLogAuth.each{
                    it.authName = afterOrgName
                    it.authCode = Long.valueOf(afterStaffId)
                }
            }
        }
        /**
         * 更新资质信息
         */
        def eiaCert
        if(params.eiaCertId || GeneConstants.DOMAIN_EIA_CERT.equals(maintainType)){
            /**
             * 更新项目信息
             */
            if(params.eiaCertId){
                eiaCert = EiaCert.findAllByIfDelAndId(false,params.long("eiaCertId"))
            }else{
                eiaCert = EiaCert.findAllByIfDelAndInputUserIdAndInputDeptId(false,Long.valueOf(agoStaffId),Long.valueOf(agoOrgId))
            }
        }
        eiaCert.each{
            def eiaCertMap = [:]
            eiaCertMap["tableName"] = GeneConstants.DOMAIN_EIA_CERT
            eiaCertMap["tableNameId"] = it.id
            eiaCertMap["afterInputDept"] = afterOrgName
            eiaCertMap["afterInputDeptId"] = Long.valueOf(afterOrgId)
            eiaCertMap["afterInputDeptCode"] = afterOrgCode
            eiaCertMap["afterInputUser"] = afterStaffName
            eiaCertMap["afterInputUserId"]= Long.valueOf(afterStaffId)
            eiaCertMap["agoInputDept"] = it.inputDept
            eiaCertMap["agoInputDeptId"] = it.inputDeptId
            eiaCertMap["agoInputDeptCode"] = it.inputDeptCode
            eiaCertMap["agoInputUser"] = it.inputUser
            eiaCertMap["agoInputUserId"]= it.inputUserId
            eiaCertList.add(eiaCertMap)
            it.inputDept = afterOrgName
            it.inputDeptId = Long.valueOf(afterOrgId)
            it.inputDeptCode = afterOrgCode
            it.inputUser = afterStaffName
            it.inputUserId = Long.valueOf(afterStaffId)
            /**
             * 跟资质有关的流程变更，根据tableId和tableName进行项目变更
             */
            def eiaWorkFlowBusi = EiaWorkFlowBusi.findAllByIfDelAndTableNameAndTableNameId(false,GeneConstants.DOMAIN_EIA_CERT,it.id)
            eiaWorkFlowBusi.each{
                def eiaWorkFlowBusiMap = [:]
                eiaWorkFlowBusiMap["tableName"] = GeneConstants.DOMAIN_EIA_WLOW_BUSI
                eiaWorkFlowBusiMap["tableNameId"] = it.id
                eiaWorkFlowBusiMap["afterInputDept"] = afterOrgName
                eiaWorkFlowBusiMap["afterInputDeptId"] = Long.valueOf(afterOrgId)
                eiaWorkFlowBusiMap["afterInputDeptCode"] = afterOrgCode
                eiaWorkFlowBusiMap["afterInputUser"] = afterStaffName
                eiaWorkFlowBusiMap["afterInputUserId"]= Long.valueOf(afterStaffId)
                eiaWorkFlowBusiMap["agoInputDept"] = it.inputDept
                eiaWorkFlowBusiMap["agoInputDeptId"] = it.inputDeptId
                eiaWorkFlowBusiMap["agoInputDeptCode"] = it.inputDeptCode
                eiaWorkFlowBusiMap["agoInputUser"] = it.inputUser
                eiaWorkFlowBusiMap["agoInputUserId"]= it.inputUserId
                eiaWorkFlowBusiList.add(eiaWorkFlowBusiMap)
                it.inputDept = afterOrgName
                it.inputDeptId = Long.valueOf(afterOrgId)
                it.inputDeptCode = afterOrgCode
                it.inputUser = afterStaffName
                it.inputUserId = Long.valueOf(afterStaffId)
            }
            /**
             * 更新流程信息(参与人，更加updateDeptId和updateUserId更新)
             */
            def eiaWorkFlowBusiUpdate = EiaWorkFlowBusi.findAllByIfDelAndUpdateUserIdAndUpdateDeptIdAndTableNameAndTableNameId(false,Long.valueOf(agoStaffId),Long.valueOf(agoOrgId),GeneConstants.DOMAIN_EIA_CERT,it.id)
            eiaWorkFlowBusiUpdate.each{
                it.updateDept = afterOrgName
                it.updateDeptId = Long.valueOf(afterOrgId)
                it.updateDeptCode = afterOrgCode
                it.updateUser = afterStaffName
                it.updateUserId = Long.valueOf(afterStaffId)
            }
            /**
             * 更新流程信息(审核人)
             */
            def eiaWorkFlowBusiAuth = EiaWorkFlowBusi.findAllByIfDelAndAuthCodeAndTableNameAndTableNameId(false,Long.valueOf(agoStaffId),GeneConstants.DOMAIN_EIA_CERT,it.id)
            eiaWorkFlowBusiAuth.each{
                it.authName = afterOrgName
                it.authCode = Long.valueOf(afterStaffId)
            }
            /**
             * 流程日志表
             */
            def eiaWorkFlowBusiLog = EiaWorkFlowBusiLog.findAllByIfDelAndTableNameAndTableNameId(false,GeneConstants.DOMAIN_EIA_CERT,it.id)
            eiaWorkFlowBusiLog.each{
                def eiaWorkFlowBusiLogMap = [:]
                eiaWorkFlowBusiLogMap["tableName"] = GeneConstants.DOMAIN_EIA_WLOW_BUSI_LOG
                eiaWorkFlowBusiLogMap["tableNameId"] = it.id
                eiaWorkFlowBusiLogMap["afterInputDept"] = afterOrgName
                eiaWorkFlowBusiLogMap["afterInputDeptId"] = Long.valueOf(afterOrgId)
                eiaWorkFlowBusiLogMap["afterInputDeptCode"] = afterOrgCode
                eiaWorkFlowBusiLogMap["afterInputUser"] = afterStaffName
                eiaWorkFlowBusiLogMap["afterInputUserId"]= Long.valueOf(afterStaffId)
                eiaWorkFlowBusiLogMap["agoInputDept"] = it.inputDept
                eiaWorkFlowBusiLogMap["agoInputDeptId"] = it.inputDeptId
                eiaWorkFlowBusiLogMap["agoInputDeptCode"] = it.updateDeptCode
                eiaWorkFlowBusiLogMap["agoInputUser"] = it.inputUser
                eiaWorkFlowBusiLogMap["agoInputUserId"]= it.inputUserId
                eiaWorkFlowBusiLogList.add(eiaWorkFlowBusiLogMap)
                it.inputDept = afterOrgName
                it.inputDeptId = Long.valueOf(afterOrgId)
                it.inputUser = afterStaffName
                it.inputUserId = Long.valueOf(afterStaffId)
            }
            /**流程日志表
             * 更新流程信息(参与人，更加updateDeptId和updateUserId更新)
             */
            def eiaWorkFlowBusiLogUpdate = EiaWorkFlowBusiLog.findAllByIfDelAndUpdateUserIdAndUpdateDeptIdAndTableNameAndTableNameId(false,Long.valueOf(agoStaffId),Long.valueOf(agoOrgId),GeneConstants.DOMAIN_EIA_CERT,it.id)
            eiaWorkFlowBusiLogUpdate.each{
                it.updateDept = afterOrgName
                it.updateDeptId = Long.valueOf(afterOrgId)
                it.updateDeptCode = afterOrgCode
                it.updateUser = afterStaffName
                it.updateUserId = Long.valueOf(afterStaffId)
            }
            /**
             * 更新流程信息(审核人)
             */
            def eiaWorkFlowBusiLogAuth = EiaWorkFlowBusiLog.findAllByIfDelAndAuthCodeAndTableNameAndTableNameId(false,Long.valueOf(agoStaffId),GeneConstants.DOMAIN_EIA_CERT,it.id)
            eiaWorkFlowBusiLogAuth.each{
                it.authName = afterOrgName
                it.authCode = Long.valueOf(afterStaffId)
            }
        }


        /**
         * 统一保存数据维护日志表
         */
        eiaDataMaintainList.add(eiaClientList)
        eiaDataMaintainList.add(eiaTaskList)
        eiaDataMaintainList.add(eiaOfferList)
        eiaDataMaintainList.add(eiaContractList)
        eiaDataMaintainList.add(eiaCertList)
        eiaDataMaintainList.add(eiaProjectList)
        eiaDataMaintainList.add(eiaProjectPlanList)
        eiaDataMaintainList.add(eiaWorkFlowBusiList)
        eiaDataMaintainList.add(eiaWorkFlowBusiLogList)
        for(int j=0;j<eiaDataMaintainList.size();j++){
            eiaDataMaintainLongSave(eiaDataMaintainList.get(j),session)
        }
        return  true
    }
    /**
     * 日志表保存
     * @param eiaDataMaintainList
     */
    def eiaDataMaintainLongSave(eiaDataMaintainList,session){
        for (int i = 0; i < eiaDataMaintainList.size(); i++) {
            def  afterInfosUser = eiaDataMaintainList.get(i);
            def eiaDataMaintainLog = new EiaDataMaintainLog()
            eiaDataMaintainLog.tableNameId = afterInfosUser?.tableNameId
            eiaDataMaintainLog.tableName = afterInfosUser?.tableName
            eiaDataMaintainLog.afterInputDept = afterInfosUser?.afterInputDept
            eiaDataMaintainLog.afterInputDeptId = afterInfosUser?.afterInputDeptId
            eiaDataMaintainLog.afterInputDeptCode = afterInfosUser?.afterInputDeptCode
            eiaDataMaintainLog.afterInputUser = afterInfosUser?.afterInputUser
            eiaDataMaintainLog.afterInputUserId = afterInfosUser?.afterInputUserId
            eiaDataMaintainLog.agoInputDept = afterInfosUser?.agoInputDept
            eiaDataMaintainLog.agoInputDeptId = afterInfosUser?.agoInputDeptId
            eiaDataMaintainLog.agoInputDeptCode = afterInfosUser?.agoInputDeptCode
            eiaDataMaintainLog.agoInputUser = afterInfosUser?.agoInputUser
            eiaDataMaintainLog.agoInputUserId = afterInfosUser?.agoInputUserId
            eiaDataMaintainLog.inputDept = session.staff.orgName
            eiaDataMaintainLog.inputDeptCode = session.staff.orgCode
            eiaDataMaintainLog.inputDeptId = Long.parseLong(session.staff.orgId)
            eiaDataMaintainLog.inputUser = session.staff.staffName
            eiaDataMaintainLog.inputUserId = Long.parseLong(session.staff.staffId)
            eiaDataMaintainLog.save(flush: true, failOnError: true)
        }
    }
    /**
     * 获取已存在的更新人数据
     */
    def getMaintainType(params){
        def maintainType
        if(params.contractId){
            maintainType = EiaContract.findByIfDelAndId(false,params.long("contractId"))
        }else if(params.offerId){
            maintainType = EiaOffer.findByIfDelAndId(false,params.long("offerId"))
        }else if(params.taskId){
            maintainType = EiaTask.findByIfDelAndId(false,params.long("taskId"))
        }else if(params.projectId){
            maintainType = EiaProject.findByIfDelAndId(false,params.long("projectId"))
        }else if(params.clientId){
            maintainType = EiaClient.findByIfDelAndId(false,params.long("clientId"))
        }else if(params.projectPlanId){
            maintainType = EiaProjectPlan.findByIfDelAndEiaProjectId(false,params.long("projectPlanId"))
        }else if(params.eiaCertId){
            maintainType = EiaCert.findByIfDelAndId(false,params.long("eiaCertId"))
        }
        return  maintainType
    }
    /**
     * 数据删除
     */
    def maintainDel(params,session){
        def eiaDataMaintainList = []
        def eiaClientList = []
        def eiaTaskList = []
        def eiaOfferList = []
        def eiaContractList = []
        def eiaCertList = []
        def eiaProjectList = []
        def eiaProjectPlanList = []
        def eiaWorkFlowBusiList = []
        def eiaWorkFlowBusiLogList = []
        if(params.eiaTaskId){
            def eiaTask = EiaTask.findByIfDelAndId(false,params.long("eiaTaskId"))
            def eiaTaskMap = [:]
            eiaTaskMap["tableName"] = GeneConstants.DOMAIN_EIA_TASK
            eiaTaskMap["tableNameId"] = eiaTask.id
            eiaTaskMap["agoInputDept"] = eiaTask.inputDept
            eiaTaskMap["agoInputDeptId"] = eiaTask.inputDeptId
            eiaTaskMap["agoInputDeptCode"] = eiaTask.inputDeptCode
            eiaTaskMap["agoInputUser"] = eiaTask.inputUser
            eiaTaskMap["agoInputUserId"]= eiaTask.inputUserId
            eiaTaskList.add(eiaTaskMap)
            def eiaTaskAssign = EiaTaskAssign.findAllByIfDelAndTaskId(false,eiaTask?.id)
            eiaTaskAssign.each{
                it.ifDel = true
            }
            def eiaTaskAssignLog = EiaTaskAssignLog.findAllByIfDelAndTaskId(false,eiaTask?.id)
            eiaTaskAssignLog.each{
                it.ifDel = true
            }
            def eiaTaskLog = EiaTaskLog.findAllByIfDelAndEiaTaskId(false,eiaTask?.id)
            eiaTaskLog.each{
                it.ifDel = true
            }
            /**
             * 报价
             */
            def eiaOffer = EiaOffer.findAllByIfDelAndTaskId(false,eiaTask?.id)
            eiaOffer.each{
                def eiaOfferMap = [:]
                eiaOfferMap["tableName"] = GeneConstants.DOMAIN_EIA_OFFER
                eiaOfferMap["tableNameId"] = it.id
                eiaOfferMap["agoInputDept"] = it.inputDept
                eiaOfferMap["agoInputDeptId"] = it.inputDeptId
                eiaOfferMap["agoInputDeptCode"] = it.inputDeptCode
                eiaOfferMap["agoInputUser"] = it.inputUser
                eiaOfferMap["agoInputUserId"]= it.inputUserId
                eiaOfferList.add(eiaOfferMap)
                /**
                 * 关于报价流程
                 */
                def eiaWorkFlowBusi = EiaWorkFlowBusi.findAllByIfDelAndTableNameAndTableNameId(false,GeneConstants.DOMAIN_EIA_OFFER,it?.id)
                if(eiaWorkFlowBusi){
                    eiaWorkFlowBusi.each{
                        def eiaWorkFlowBusiLog = EiaWorkFlowBusiLog.findAllByIfDelAndEiaWorkFlowBusiIdAndTableName(false,it?.id,GeneConstants.DOMAIN_EIA_OFFER)
                        eiaWorkFlowBusiLog.each{
                            it.ifDel = true
                        }
                        def eiaWorkFlowBusiMap = [:]
                        eiaWorkFlowBusiMap["tableName"] = GeneConstants.DOMAIN_EIA_WLOW_BUSI
                        eiaWorkFlowBusiMap["tableNameId"] = it.id
                        eiaWorkFlowBusiMap["agoInputDept"] = it.inputDept
                        eiaWorkFlowBusiMap["agoInputDeptId"] = it.inputDeptId
                        eiaWorkFlowBusiMap["agoInputDeptCode"] = it.inputDeptCode
                        eiaWorkFlowBusiMap["agoInputUser"] = it.inputUser
                        eiaWorkFlowBusiMap["agoInputUserId"]= it.inputUserId
                        eiaWorkFlowBusiList.add(eiaWorkFlowBusiMap)
                        it.ifDel = true
                    }
                    it.ifDel = true
                }
            }
            /**
             * 合同信息
             */
            def eiaContract = EiaContract.findAllByIfDelAndTaskId(false,eiaTask?.id)
            eiaContract.each{
                def eiaContractMap = [:]
                eiaContractMap["tableName"] = GeneConstants.DOMAIN_EIA_CONTRACT
                eiaContractMap["tableNameId"] = it.id
                eiaContractMap["agoInputDept"] = it.inputDept
                eiaContractMap["agoInputDeptId"] = it.inputDeptId
                eiaContractMap["agoInputDeptCode"] = it.inputDeptCode
                eiaContractMap["agoInputUser"] = it.inputUser
                eiaContractMap["agoInputUserId"]= it.inputUserId
                eiaContractList.add(eiaContractMap)
                /**
                 * 合同变更表
                 */
                def eiaContractLog = EiaContractLog.findAllByIfDelAndEiaContractId(false,it?.id)
                eiaContractLog.each{
                    it.ifDel = true
                }
                /**
                 * 开票信息
                 */
                def eiaInvoice = EiaInvoice.findAllByIfDelAndContractId(false,it?.id)
                eiaInvoice.each{
                    it.ifDel = true
                }
                /**
                 * 进出帐信息
                 */
                def eiaIncomeOut = EiaIncomeOut.findAllByIfDelAndContractId(false,it?.id)
                eiaIncomeOut.each{
                    it.ifDel = true
                }
                /**
                 * 关于合同流程
                 */
                def eiaWorkFlowBusi = EiaWorkFlowBusi.findAllByIfDelAndTableNameAndTableNameId(false,GeneConstants.DOMAIN_EIA_CONTRACT,it?.id)
                eiaWorkFlowBusi.each{
                    def eiaWorkFlowBusiLog = EiaWorkFlowBusiLog.findAllByIfDelAndEiaWorkFlowBusiIdAndTableName(false,it?.id,GeneConstants.DOMAIN_EIA_CONTRACT)
                    eiaWorkFlowBusiLog.each{
                        it.ifDel = true
                    }
                    def eiaWorkFlowBusiMap = [:]
                    eiaWorkFlowBusiMap["tableName"] = GeneConstants.DOMAIN_EIA_WLOW_BUSI
                    eiaWorkFlowBusiMap["tableNameId"] = it.id
                    eiaWorkFlowBusiMap["agoInputDept"] = it.inputDept
                    eiaWorkFlowBusiMap["agoInputDeptId"] = it.inputDeptId
                    eiaWorkFlowBusiMap["agoInputDeptCode"] = it.inputDeptCode
                    eiaWorkFlowBusiMap["agoInputUser"] = it.inputUser
                    eiaWorkFlowBusiMap["agoInputUserId"]= it.inputUserId
                    eiaWorkFlowBusiList.add(eiaWorkFlowBusiMap)
                    it.ifDel = true
                }
                it.ifDel = true

            }
            /**
             * 项目
             */
            def eiaProject = EiaProject.findAllByIfDelAndEiaTaskId(false,eiaTask?.id)
            eiaProject.each{
                /**
                 * 项目子表
                 */
                def eiaEnvProject = EiaEnvProject.findAllByIfDelAndEiaProjectId(false,it?.id)
                eiaEnvProject.each{
                    def eiaEnvProjectLog = EiaEnvProjectLog.findAllByIfDelAndEiaEnvProjectId(false,it?.id)
                    eiaEnvProjectLog.each{
                        it.ifDel = true
                    }
                    it.ifDel = true
                }

                def eiaEneProject = EiaEneProject.findAllByIfDelAndEiaProjectId(false,it?.id)
                eiaEneProject.each{
                    def eiaEneProjectLog = EiaEneProjectLog.findAllByIfDelAndEiaEneProjectId(false,it?.id)
                    eiaEneProjectLog.each{
                        it.ifDel = true
                    }
                    it.ifDel = true
                }

                def eiaGreenProject = EiaGreenProject.findAllByIfDelAndEiaProjectId(false,it?.id)
                eiaGreenProject.each{
                    def eiaGreenProjectLog = EiaGreenProjectLog.findAllByIfDelAndEiaGreenProjectId(false,it?.id)
                    eiaGreenProjectLog.each{
                        it.ifDel = true
                    }
                    it.ifDel = true
                }
                def eiaProjectLog = EiaProjectLog.findAllByIfDelAndEiaProjectId(false,it?.id)
                eiaProjectLog.each{
                    it.ifDel = true
                }
                /**
                 * 关于项目流程
                 */
                def eiaWorkFlowBusi = EiaWorkFlowBusi.findAllByIfDelAndTableNameAndTableNameId(false,GeneConstants.DOMAIN_EIA_PROJECT,it?.id)
                eiaWorkFlowBusi.each{
                    def eiaWorkFlowBusiLog = EiaWorkFlowBusiLog.findAllByIfDelAndEiaWorkFlowBusiIdAndTableName(false,it?.id,GeneConstants.DOMAIN_EIA_PROJECT)
                    eiaWorkFlowBusiLog.each{
                        it.ifDel = true
                    }
                    def eiaWorkFlowBusiMap = [:]
                    eiaWorkFlowBusiMap["tableName"] = GeneConstants.DOMAIN_EIA_WLOW_BUSI
                    eiaWorkFlowBusiMap["tableNameId"] = it.id
                    eiaWorkFlowBusiMap["agoInputDept"] = it.inputDept
                    eiaWorkFlowBusiMap["agoInputDeptId"] = it.inputDeptId
                    eiaWorkFlowBusiMap["agoInputDeptCode"] = it.inputDeptCode
                    eiaWorkFlowBusiMap["agoInputUser"] = it.inputUser
                    eiaWorkFlowBusiMap["agoInputUserId"]= it.inputUserId
                    eiaWorkFlowBusiList.add(eiaWorkFlowBusiMap)
                    it.ifDel = true
                }
                def eiaProjectMap = [:]
                eiaProjectMap["tableName"] = GeneConstants.DOMAIN_EIA_PROJECT
                eiaProjectMap["tableNameId"] = it.id
                eiaProjectMap["agoInputDept"] = it.inputDept
                eiaProjectMap["agoInputDeptId"] = it.inputDeptId
                eiaProjectMap["agoInputDeptCode"] = it.inputDeptCode
                eiaProjectMap["agoInputUser"] = it.inputUser
                eiaProjectMap["agoInputUserId"]= it.inputUserId
                eiaProjectList.add(eiaProjectMap)
                it.ifDel = true

            }

            /**
             * 工作方案
             */
            def eiaProjectPlan = EiaProjectPlan.findAllByIfDelAndEiaTaskId(false,eiaTask?.id)
            eiaProjectPlan.each{
                def eiaProjectPlanItem = EiaProjectPlanItem.findAllByIfDelAndEiaProjectPlanId(false,it?.id)
                eiaProjectPlanItem.each{
                    it.ifDel = true
                }
                def eiaProjectPlanMap = [:]
                eiaProjectPlanMap["tableName"] = GeneConstants.DOMAIN_EIA_PROJECT_PLAN
                eiaProjectPlanMap["tableNameId"] = it.id
                eiaProjectPlanMap["agoInputDept"] = it.inputDept
                eiaProjectPlanMap["agoInputDeptId"] = it.inputDeptId
                eiaProjectPlanMap["agoInputDeptCode"] = it.inputDeptCode
                eiaProjectPlanMap["agoInputUser"] = it.inputUser
                eiaProjectPlanMap["agoInputUserId"]= it.inputUserId
                eiaProjectPlanList.add(eiaProjectPlanMap)
                it.ifDel = true
            }
            /**
             * 资质报告
             */
            def eiaCert = EiaCert.findAllByIfDelAndEiaTaskId(false,eiaTask?.id)
            eiaCert.each{
                /**
                 * 关于资质流程
                 */
                def eiaWorkFlowBusi = EiaWorkFlowBusi.findAllByIfDelAndTableNameAndTableNameId(false,GeneConstants.DOMAIN_EIA_CERT,it?.id)
                eiaWorkFlowBusi.each{
                    def eiaWorkFlowBusiLog = EiaWorkFlowBusiLog.findAllByIfDelAndEiaWorkFlowBusiIdAndTableName(false,it?.id,GeneConstants.DOMAIN_EIA_CERT)
                    eiaWorkFlowBusiLog.each{
                        it.ifDel = true
                    }
                    def eiaWorkFlowBusiMap = [:]
                    eiaWorkFlowBusiMap["tableName"] = GeneConstants.DOMAIN_EIA_WLOW_BUSI
                    eiaWorkFlowBusiMap["tableNameId"] = it.id
                    eiaWorkFlowBusiMap["agoInputDept"] = it.inputDept
                    eiaWorkFlowBusiMap["agoInputDeptId"] = it.inputDeptId
                    eiaWorkFlowBusiMap["agoInputDeptCode"] = it.inputDeptCode
                    eiaWorkFlowBusiMap["agoInputUser"] = it.inputUser
                    eiaWorkFlowBusiMap["agoInputUserId"]= it.inputUserId
                    eiaWorkFlowBusiList.add(eiaWorkFlowBusiMap)
                    it.ifDel = true
                }
                def eiaCertMap = [:]
                eiaCertMap["tableName"] = GeneConstants.DOMAIN_EIA_CERT
                eiaCertMap["tableNameId"] = it.id
                eiaCertMap["agoInputDept"] = it.inputDept
                eiaCertMap["agoInputDeptId"] = it.inputDeptId
                eiaCertMap["agoInputDeptCode"] = it.inputDeptCode
                eiaCertMap["agoInputUser"] = it.inputUser
                eiaCertMap["agoInputUserId"]= it.inputUserId
                eiaCertList.add(eiaCertMap)
                it.ifDel = true
            }
            eiaTask.ifDel = true
            eiaTask.save(flush: true, failOnError: true)
        }else if(params.contractId){
            def eiaContract = EiaContract.findByIfDelAndId(false,params.long("contractId"))
            if(eiaContract){
                def eiaContractMap = [:]
                eiaContractMap["tableName"] = GeneConstants.DOMAIN_EIA_CONTRACT
                eiaContractMap["tableNameId"] = eiaContract.id
                eiaContractMap["agoInputDept"] = eiaContract.inputDept
                eiaContractMap["agoInputDeptId"] = eiaContract.inputDeptId
                eiaContractMap["agoInputDeptCode"] = eiaContract.inputDeptCode
                eiaContractMap["agoInputUser"] = eiaContract.inputUser
                eiaContractMap["agoInputUserId"]= eiaContract.inputUserId
                eiaContractList.add(eiaContractMap)
                def eiaContractLog = EiaContractLog.findAllByIfDelAndEiaContractId(false,eiaContract?.id)
                eiaContractLog.each{
                    it.ifDel = true
                }
                /**
                 * 开票信息
                 */
                def eiaInvoice = EiaInvoice.findAllByIfDelAndContractId(false,eiaContract?.id)
                eiaInvoice.each{
                    it.ifDel = true
                }
                /**
                 * 进出帐信息
                 */
                def eiaIncomeOut = EiaIncomeOut.findAllByIfDelAndContractId(false,eiaContract?.id)
                eiaIncomeOut.each{
                    it.ifDel = true
                }
                /**
                 * 关于合同流程
                 */
                def eiaWorkFlowBusi = EiaWorkFlowBusi.findAllByIfDelAndTableNameAndTableNameId(false,GeneConstants.DOMAIN_EIA_CONTRACT,eiaContract?.id)
                eiaWorkFlowBusi.each{
                    def eiaWorkFlowBusiLog = EiaWorkFlowBusiLog.findAllByIfDelAndEiaWorkFlowBusiIdAndTableName(false,it?.id,GeneConstants.DOMAIN_EIA_CONTRACT)
                    eiaWorkFlowBusiLog.each{
                        it.ifDel = true
                    }
                    def eiaWorkFlowBusiMap = [:]
                    eiaWorkFlowBusiMap["tableName"] = GeneConstants.DOMAIN_EIA_WLOW_BUSI
                    eiaWorkFlowBusiMap["tableNameId"] = it.id
                    eiaWorkFlowBusiMap["agoInputDept"] = it.inputDept
                    eiaWorkFlowBusiMap["agoInputDeptId"] = it.inputDeptId
                    eiaWorkFlowBusiMap["agoInputDeptCode"] = it.inputDeptCode
                    eiaWorkFlowBusiMap["agoInputUser"] = it.inputUser
                    eiaWorkFlowBusiMap["agoInputUserId"]= it.inputUserId
                    eiaWorkFlowBusiList.add(eiaWorkFlowBusiMap)
                    it.ifDel = true
                }
                eiaContract.ifDel = true
                eiaContract.save(flush: true, failOnError: true)
            }
        }else if(params.offerId){
            def eiaOffer = EiaOffer.findByIfDelAndId(false,params.long("offerId"))
            if(eiaOffer){
                def eiaOfferMap = [:]
                eiaOfferMap["tableName"] = GeneConstants.DOMAIN_EIA_OFFER
                eiaOfferMap["tableNameId"] = eiaOffer.id
                eiaOfferMap["agoInputDept"] = eiaOffer.inputDept
                eiaOfferMap["agoInputDeptId"] = eiaOffer.inputDeptId
                eiaOfferMap["agoInputDeptCode"] = eiaOffer.inputDeptCode
                eiaOfferMap["agoInputUser"] = eiaOffer.inputUser
                eiaOfferMap["agoInputUserId"]= eiaOffer.inputUserId
                eiaOfferList.add(eiaOfferMap)
                eiaOffer.ifDel = true
                eiaOffer.save(flush: true, failOnError: true)
            }
            def eiaWorkFlowBusi = EiaWorkFlowBusi.findAllByIfDelAndTableNameAndTableNameId(false,GeneConstants.DOMAIN_EIA_OFFER,eiaOffer?.id)
            if(eiaWorkFlowBusi){
                eiaWorkFlowBusi.each{
                    def eiaWorkFlowBusiLog = EiaWorkFlowBusiLog.findAllByIfDelAndEiaWorkFlowBusiIdAndTableName(false,eiaOffer?.id,GeneConstants.DOMAIN_EIA_OFFER)
                    eiaWorkFlowBusiLog.each{
                        it.ifDel = true
                    }
                    def eiaWorkFlowBusiMap = [:]
                    eiaWorkFlowBusiMap["tableName"] = GeneConstants.DOMAIN_EIA_WLOW_BUSI
                    eiaWorkFlowBusiMap["tableNameId"] = it.id
                    eiaWorkFlowBusiMap["agoInputDept"] = it.inputDept
                    eiaWorkFlowBusiMap["agoInputDeptId"] = it.inputDeptId
                    eiaWorkFlowBusiMap["agoInputDeptCode"] = it.inputDeptCode
                    eiaWorkFlowBusiMap["agoInputUser"] = it.inputUser
                    eiaWorkFlowBusiMap["agoInputUserId"]= it.inputUserId
                    eiaWorkFlowBusiList.add(eiaWorkFlowBusiMap)
                    it.ifDel = true
                }
            }

        }else if(params.projectId){
            def eiaProject = EiaProject.findByIfDelAndId(false,params.long("projectId"))
            if(eiaProject){
                def eiaProjectMap = [:]
                eiaProjectMap["tableName"] = GeneConstants.DOMAIN_EIA_PROJECT
                eiaProjectMap["tableNameId"] = eiaProject.id
                eiaProjectMap["agoInputDept"] = eiaProject.inputDept
                eiaProjectMap["agoInputDeptId"] = eiaProject.inputDeptId
                eiaProjectMap["agoInputDeptCode"] = eiaProject.inputDeptCode
                eiaProjectMap["agoInputUser"] = eiaProject.inputUser
                eiaProjectMap["agoInputUserId"]= eiaProject.inputUserId
                eiaProjectList.add(eiaProjectMap)
                /**
                 * 项目子表
                 */
                def eiaEnvProject = EiaEnvProject.findAllByIfDelAndEiaProjectId(false,eiaProject?.id)
                eiaEnvProject.each{
                    def eiaEnvProjectLog = EiaEnvProjectLog.findAllByIfDelAndEiaEnvProjectId(false,it?.id)
                    eiaEnvProjectLog.each{
                        it.ifDel = true
                    }
                    it.ifDel = true
                }

                def eiaEneProject = EiaEneProject.findAllByIfDelAndEiaProjectId(false,eiaProject?.id)
                eiaEneProject.each{
                    def eiaEneProjectLog = EiaEneProjectLog.findAllByIfDelAndEiaEneProjectId(false,it?.id)
                    eiaEneProjectLog.each{
                        it.ifDel = true
                    }
                    it.ifDel = true
                }

                def eiaGreenProject = EiaGreenProject.findAllByIfDelAndEiaProjectId(false,eiaProject?.id)
                eiaGreenProject.each{
                    def eiaGreenProjectLog = EiaGreenProjectLog.findAllByIfDelAndEiaGreenProjectId(false,it?.id)
                    eiaGreenProjectLog.each{
                        it.ifDel = true
                    }
                    it.ifDel = true
                }
                def eiaProjectLog = EiaProjectLog.findAllByIfDelAndEiaProjectId(false,eiaProject?.id)
                eiaProjectLog.each{
                    it.ifDel = true
                }
                /**
                 * 关于项目流程
                 */
                def eiaWorkFlowBusi = EiaWorkFlowBusi.findAllByIfDelAndTableNameAndTableNameId(false,GeneConstants.DOMAIN_EIA_PROJECT,eiaProject?.id)
                eiaWorkFlowBusi.each{
                    def eiaWorkFlowBusiLog = EiaWorkFlowBusiLog.findAllByIfDelAndEiaWorkFlowBusiIdAndTableName(false,it?.id,GeneConstants.DOMAIN_EIA_PROJECT)
                    eiaWorkFlowBusiLog.each{
                        it.ifDel = true
                    }
                    def eiaWorkFlowBusiMap = [:]
                    eiaWorkFlowBusiMap["tableName"] = GeneConstants.DOMAIN_EIA_WLOW_BUSI
                    eiaWorkFlowBusiMap["tableNameId"] = it.id
                    eiaWorkFlowBusiMap["agoInputDept"] = it.inputDept
                    eiaWorkFlowBusiMap["agoInputDeptId"] = it.inputDeptId
                    eiaWorkFlowBusiMap["agoInputDeptCode"] = it.inputDeptCode
                    eiaWorkFlowBusiMap["agoInputUser"] = it.inputUser
                    eiaWorkFlowBusiMap["agoInputUserId"]= it.inputUserId
                    eiaWorkFlowBusiList.add(eiaWorkFlowBusiMap)
                    it.ifDel = true
                }
                eiaProject.ifDel = true
                eiaProject.save(flush: true, failOnError: true)
            }

        }else if(params.clientId){
            def eiaClient = EiaClient.findByIfDelAndId(false,params.long("clientId"))
            if(eiaClient){
                def eiaClientMap = [:]
                eiaClientMap["tableName"] = GeneConstants.DOMAIN_EIA_WLOW_BUSI
                eiaClientMap["tableNameId"] = eiaClient.id
                eiaClientMap["agoInputDept"] = eiaClient.inputDept
                eiaClientMap["agoInputDeptId"] = eiaClient.inputDeptId
                eiaClientMap["agoInputDeptCode"] = eiaClient.inputDeptCode
                eiaClientMap["agoInputUser"] = eiaClient.inputUser
                eiaClientMap["agoInputUserId"]= eiaClient.inputUserId
                eiaClientList.add(eiaClientMap)
                eiaClient.ifDel = true
                eiaClient.save(flush: true, failOnError: true)
            }
        }else if(params.projectPlanId){
            def eiaProjectPlan = EiaProjectPlan.findByIfDelAndEiaProjectId(false,params.long("projectPlanId"))
            if(eiaProjectPlan){
                def eiaProjectPlanMap = [:]
                eiaProjectPlanMap["tableName"] = GeneConstants.DOMAIN_EIA_PROJECT_PLAN
                eiaProjectPlanMap["tableNameId"] = eiaProjectPlan.id
                eiaProjectPlanMap["agoInputDept"] = eiaProjectPlan.inputDept
                eiaProjectPlanMap["agoInputDeptId"] = eiaProjectPlan.inputDeptId
                eiaProjectPlanMap["agoInputDeptCode"] = eiaProjectPlan.inputDeptCode
                eiaProjectPlanMap["agoInputUser"] = eiaProjectPlan.inputUser
                eiaProjectPlanMap["agoInputUserId"]= eiaProjectPlan.inputUserId
                eiaProjectPlanList.add(eiaProjectPlanMap)
                def eiaProjectPlanItem = EiaProjectPlanItem.findAllByIfDelAndEiaProjectPlanId(false,eiaProjectPlan?.id)
                eiaProjectPlanItem.each{
                    it.ifDel = true
                }
                eiaProjectPlan.ifDel = true
                eiaProjectPlan.save(flush: true, failOnError: true)
            }

        }else if(params.eiaCertId){
            def eiaCert = EiaCert.findByIfDelAndId(false,params.long("eiaCertId"))
            if(eiaCert){
                def eiaCertMap = [:]
                eiaCertMap["tableName"] = GeneConstants.DOMAIN_EIA_CERT
                eiaCertMap["tableNameId"] = eiaCert.id
                eiaCertMap["agoInputDept"] = eiaCert.inputDept
                eiaCertMap["agoInputDeptId"] = eiaCert.inputDeptId
                eiaCertMap["agoInputDeptCode"] = eiaCert.inputDeptCode
                eiaCertMap["agoInputUser"] = eiaCert.inputUser
                eiaCertMap["agoInputUserId"]= eiaCert.inputUserId
                eiaCertList.add(eiaCertMap)
                /**
                 * 关于资质流程
                 */
                def eiaWorkFlowBusi = EiaWorkFlowBusi.findAllByIfDelAndTableNameAndTableNameId(false,GeneConstants.DOMAIN_EIA_CERT,eiaCert?.id)
                eiaWorkFlowBusi.each{
                    def eiaWorkFlowBusiLog = EiaWorkFlowBusiLog.findAllByIfDelAndEiaWorkFlowBusiIdAndTableName(false,it?.id,GeneConstants.DOMAIN_EIA_CERT)
                    eiaWorkFlowBusiLog.each{
                        it.ifDel = true
                    }
                    def eiaWorkFlowBusiMap = [:]
                    eiaWorkFlowBusiMap["tableName"] = GeneConstants.DOMAIN_EIA_WLOW_BUSI
                    eiaWorkFlowBusiMap["tableNameId"] = it.id
                    eiaWorkFlowBusiMap["agoInputDept"] = it.inputDept
                    eiaWorkFlowBusiMap["agoInputDeptId"] = it.inputDeptId
                    eiaWorkFlowBusiMap["agoInputDeptCode"] = it.inputDeptCode
                    eiaWorkFlowBusiMap["agoInputUser"] = it.inputUser
                    eiaWorkFlowBusiMap["agoInputUserId"]= it.inputUserId
                    eiaWorkFlowBusiList.add(eiaWorkFlowBusiMap)
                }
                eiaCert.ifDel = true
                eiaCert.save(flush: true, failOnError: true)
            }

        }
        /**
         * 统一保存删除信息日志表
         */
        eiaDataMaintainList.add(eiaClientList)
        eiaDataMaintainList.add(eiaTaskList)
        eiaDataMaintainList.add(eiaOfferList)
        eiaDataMaintainList.add(eiaContractList)
        eiaDataMaintainList.add(eiaCertList)
        eiaDataMaintainList.add(eiaProjectList)
        eiaDataMaintainList.add(eiaProjectPlanList)
        eiaDataMaintainList.add(eiaWorkFlowBusiList)
        eiaDataMaintainList.add(eiaWorkFlowBusiLogList)
        for(int j=0;j<eiaDataMaintainList.size();j++){
            eiaDataMaintainLongSave(eiaDataMaintainList.get(j),session)
        }
        return  true
    }
    /**
     * 合同是否显示
     */
    def maintainShow(params){
        def eiaContract = EiaContract.findByIfDelAndId(false,params.long("contractId"))
        if(eiaContract.ifShow){
            eiaContract.ifShow = false
        }else{
            eiaContract.ifShow = true
        }
        eiaContract.save(flush: true, failOnError: true)
    }
    /**
     * 项目关联任务
     */
    def projectToTask(params){
        def eiaTask = EiaTask.findByIfDelAndId(false,params.long("taskId"))
        def eiaProject = EiaProject.findByIfDelAndId(false,params.long("eiaProjectId"))
        def eiaContract = EiaContract.findByIfDelAndTaskId(false,params.long("taskId"))
        if(eiaProject){
            eiaProject.eiaTaskId = eiaTask?.id
            eiaProject.eiaContractId = eiaContract?.id
            eiaProject.contractName = eiaContract?.contractName
            eiaProject.contractNo = eiaContract?.contractNo
            eiaProject.save(flush: true, failOnError: true)
            def eiaProjectPlan = EiaProjectPlan.findByIfDelAndEiaProjectId(false,eiaProject?.id)
            if(eiaProjectPlan){
                eiaProjectPlan.eiaTaskId = eiaTask?.id
                eiaProjectPlan.taskName = eiaTask?.taskName
                eiaProjectPlan.save(flush: true, failOnError: true)
            }
            def eiaCert  = EiaCert.findByIfDelAndEiaProjectId(false,eiaProject?.id)
            if(eiaCert){
                eiaCert.eiaTaskId = eiaTask?.id
                eiaCert.save(flush: true, failOnError: true)
            }
            def eiaLabOffer = EiaLabOffer.findByIfDelAndEiaProjectId(false,eiaProject?.id)
            if(eiaLabOffer){
                eiaLabOffer.eiaTaskId = eiaTask?.id
                eiaLabOffer.save(flush: true, failOnError: true)
            }
        }
        return  true
    }
}
