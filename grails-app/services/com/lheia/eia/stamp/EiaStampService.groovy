package com.lheia.eia.stamp

import com.lheia.eia.common.FuncConstants
import com.lheia.eia.common.GeneConstants
import com.lheia.eia.common.HttpMesConstants
import com.lheia.eia.common.HttpUrlConstants
import com.lheia.eia.common.WorkFlowConstants
import com.lheia.eia.config.EiaDomainCode
import com.lheia.eia.contract.EiaOffer
import com.lheia.eia.tools.DateTransTools
import com.lheia.eia.tools.HttpConnectTools
import com.lheia.eia.tools.JsonHandler
import com.lheia.eia.workflow.EiaWorkFlowBusi
import com.lheia.eia.workflow.EiaWorkFlowBusiLog
import grails.gorm.transactions.Transactional

import java.util.regex.Matcher
import java.util.regex.Pattern

@Transactional
class EiaStampService {
    def eiaEnvProjectService
    /**
     * 项目信息保存
     */
    def eiaStampSave(params, session) {
        def eiaStamp

        if (params.eiaStampId) {
            eiaStamp = EiaStamp.findByIdAndIfDel(params.long('eiaStampId'), false)
            eiaStamp.properties = params
            eiaStamp.stampType = params.stampTypeCode
            eiaStamp.appDept = session.staff.inputDept
            eiaStamp.inputUser = session.staff.staffName
            eiaStamp.inputUserId = Long.valueOf(session.staff.staffId)
            eiaStamp.inputDeptCode = session.staff.orgCode
            eiaStamp.inputDept = session.staff.orgName
            eiaStamp.inputDeptId = Long.valueOf(session.staff.orgId)
            eiaStamp.appTime = new Date()
            eiaStamp.applicant = session.staff.staffName
            eiaStamp.save(flush: true, failOnError: true)
        } else {
            eiaStamp = new EiaStamp(params)
            eiaStamp.stampType = params.stampTypeCode
            eiaStamp.appDept = session.staff.inputDept
            eiaStamp.inputUser = session.staff.staffName
            eiaStamp.inputUserId = Long.valueOf(session.staff.staffId)
            eiaStamp.inputDeptCode = session.staff.orgCode
            eiaStamp.inputDept = session.staff.orgName
            eiaStamp.appDept = session.staff.orgName
            eiaStamp.inputDeptId = Long.valueOf(session.staff.orgId)
            eiaStamp.appTime = new Date()
            eiaStamp.applicant = session.staff.staffName
            eiaStamp.save(flush: true, failOnError: true)
        }
    }
    /**
     * 项目信息删除
     */
    def eiaStampDel(params) {
        def eiaStampId = params.long('eiaStampId')
        def eiaStamp = EiaStamp.findByIdAndIfDelAndIfSub(eiaStampId, false, false)
        if (eiaStamp) {
            eiaStamp.ifDel = true
            eiaStamp.save(flush: true, failOnError: true)
        } else {
            return false
        }
    }
    /**
     *  获取申请列表
     */
    def getEiaStampDataList(params, session) {
        int page
        int limit
        if (params.page && params.limit) {
            page = params.int('page') - 1
            limit = params.int('limit')
        }
        def eiaStampList = EiaStamp.createCriteria().list(max: limit, offset: page * limit, sort: "id", order: "desc") {
            def applicant = params.applicant
            if (applicant && !"申请人、录入人、申请部门、申请时间".equals(applicant)) {
                or {
                    like("applicant", "%" + applicant + "%")
                    like("supervisor", "%" + applicant + "%")
                    like('inputDept',"%"+applicant+"%")
                    if(this.ifDateTimeRex(applicant)){
                    between('appTime',DateTransTools.getFormatDateD(applicant+' 00:00:00','yyyy-MM-dd hh:mm:ss') ,DateTransTools.getFormatDateD(applicant+' 23:59:59','yyyy-MM-dd hh:mm:ss'))
                    }
                }
            }
            eq('ifDel', false)
            /**
             * 查看全部的客户数据
             */
            if (!session?.staff?.funcCode?.contains(FuncConstants.EIA_XZGL_YZSH_VIEWALL)) {
                /**
                 * 查看本部门客户数据
                 */
                if (session?.staff?.funcCode?.contains(FuncConstants.EIA_XZGL_YZSH_VIEWDEPT)) {
                    like("inputDeptCode", "%" + session.staff.orgCode + "%")
                }
                /**
                 * 查看本人客户数据
                 */
                else if (session?.staff?.funcCode?.contains(FuncConstants.EIA_XZGL_YZSH_VIEWSELF)) {
                    eq("inputUserId", Long.valueOf(session.staff.staffId))
                }
            }
        }
        def resStampList = []
        eiaStampList.eachWithIndex { it, i ->
            def dataMap = [:]
            if (it.ifTakeOut == true) {
                dataMap.ifTakeOut = '是'
            } else {
                dataMap.ifTakeOut = '否'
            }
            dataMap.stampNum = it.stampNum
            dataMap.applicant = it.applicant
            dataMap.appTime = it.appTime.format('yyyy-MM-dd')
            dataMap.inputDept = it.inputDept
            dataMap.inputUser = it.inputUser
            dataMap.inputUserId = it.inputUserId
            dataMap.ifSub = it.ifSub
            dataMap.id = it.id
            resStampList << dataMap
        }
        def resMap = [:]
        resMap.data = resStampList
        resMap.total = eiaStampList.totalCount
        return resMap
    }
    /**
     * 获取印章申请详情
     */
    def getEiaStampDataMap(params) {
        def eiaStampId = params.long('eiaStampId')
        def eiaStamp = EiaStamp.findByIdAndIfDel(eiaStampId, false)
        if (eiaStamp) {
            return eiaStamp
        } else {
            return false
        }
    }
    /**
     * 印章申请提交
     */
    def eiaStampSub(params) {
        def eiaStamp = EiaStamp.findByIdAndIfDelAndIfSub(params.long('eiaStampId'), false, false)
        if (eiaStamp) {
            eiaStamp.ifSub = true
            eiaStamp.save(flush: true, failOnErro: true)
        } else {
            return false
        }
    }

    /**
     * 报价印章申请审批单
     */
    def eiaStampPrintDataMap(params) {
        def tableName = params.tableName
        def tableNameId = params.long('tableNameId')
        def eiaStampId = tableNameId
        def eiaStamp = EiaStamp.findById(eiaStampId)
        if (eiaStamp) {
            def dataMap = eiaEnvProjectService.convertToMap(eiaStamp.properties)
            if (dataMap.ifTakeOut == 'true') {
                dataMap.ifTakeOut = '是'
            } else {
                dataMap.ifTakeOut = '否'
            }
            dataMap.appTime = dataMap.appTime.format('yyyy-MM-dd')
            /** 经办人，即合同录入人 */
            def inputUser
            def authParam = [:]
            authParam.staffId = String.valueOf(eiaStamp.inputUserId)
            def authJson = HttpConnectTools.getResponseJson(HttpUrlConstants.SINGLE_EIA_AUTH_STAFF_INFO, authParam)
            if (authJson) {
                if (JsonHandler.jsonToMap(authJson).code == HttpMesConstants.CODE_OK) {
                    def staff = JsonHandler.jsonToMap(authJson).data[0]
                    if (staff.signImagePath) {
                        inputUser = GeneConstants.AUTH_FILE_URL_PATH + staff.signImagePath
                    }
                }
            }
            dataMap.put("inputUser", inputUser)
            if (tableNameId) {
                def eiaWorkFlowBusi = EiaWorkFlowBusi.findByTableNameAndTableNameIdAndWorkFlowStateInListAndIfDel(tableName, tableNameId, [WorkFlowConstants.WORKFLOW_UNDER_WAY, WorkFlowConstants.WORKFLOW_START, WorkFlowConstants.WORKFLOW_END], false)
                /** 申请时间 */
                def applyDate = eiaWorkFlowBusi?.dateCreated.format("yyyy-MM-dd")
                dataMap.put("applyDate", applyDate)
                if (eiaWorkFlowBusi) {
                    /** 部门经理 */
                    def bmshBusiLog = EiaWorkFlowBusiLog.findAllByEiaWorkFlowBusiIdAndNodesCodeAndWorkFlowStateAndIfDel(eiaWorkFlowBusi.id, WorkFlowConstants.STAMP_WORK_FLOW_NODE_BMSH, WorkFlowConstants.WORKFLOW_START, false, [sort: "id", order: "desc",])
                    if (bmshBusiLog) {
                        def deptManagerId = bmshBusiLog.get(0)?.updateUserId
                        def deptManager
                        def param = [:]
                        param.staffId = String.valueOf(deptManagerId)
                        def staffJson = HttpConnectTools.getResponseJson(HttpUrlConstants.SINGLE_EIA_AUTH_STAFF_INFO, param)
                        if (staffJson) {
                            if (JsonHandler.jsonToMap(staffJson).code == HttpMesConstants.CODE_OK) {
                                def staff = JsonHandler.jsonToMap(staffJson).data[0]
                                if (staff.signImagePath) {
                                    deptManager = GeneConstants.AUTH_FILE_URL_PATH + staff.signImagePath
                                }
                            }
                        }
                        dataMap.put("deptManager", deptManager)
                    }
                    /** 总经理 */
                    def zjlshBusiLog = EiaWorkFlowBusiLog.findAllByEiaWorkFlowBusiIdAndNodesCodeAndWorkFlowStateAndIfDel(eiaWorkFlowBusi.id, WorkFlowConstants.STAMP_WORK_FLOW_NODE_ZJLSH, WorkFlowConstants.WORKFLOW_UNDER_WAY, false, [sort: "id", order: "desc",])
                    if (zjlshBusiLog) {
                        def topManagerId = zjlshBusiLog.get(0)?.updateUserId
                        def topManager
                        def param = [:]
                        param.staffId = String.valueOf(topManagerId)
                        def staffJson = HttpConnectTools.getResponseJson(HttpUrlConstants.SINGLE_EIA_AUTH_STAFF_INFO, param)
                        if (staffJson) {
                            if (JsonHandler.jsonToMap(staffJson).code == HttpMesConstants.CODE_OK) {
                                def staff = JsonHandler.jsonToMap(staffJson).data[0]
                                if (staff.signImagePath) {
                                    topManager = GeneConstants.AUTH_FILE_URL_PATH + staff.signImagePath
                                }
                            }
                        }
                        dataMap.put("topManager", topManager)
                    }
                    /** 行政经理 */
                    def xzjlBusiLog = EiaWorkFlowBusiLog.findAllByEiaWorkFlowBusiIdAndNodesCodeAndWorkFlowStateAndIfDel(eiaWorkFlowBusi.id, WorkFlowConstants.STAMP_WORK_FLOW_NODE_XZJLSH, WorkFlowConstants.WORKFLOW_UNDER_WAY, false, [sort: "id", order: "desc",])
                    if (xzjlBusiLog) {
                        def topManagerId = xzjlBusiLog.get(0)?.updateUserId
                        def xzManager
                        def param = [:]
                        param.staffId = String.valueOf(topManagerId)
                        def staffJson = HttpConnectTools.getResponseJson(HttpUrlConstants.SINGLE_EIA_AUTH_STAFF_INFO, param)
                        if (staffJson) {
                            if (JsonHandler.jsonToMap(staffJson).code == HttpMesConstants.CODE_OK) {
                                def staff = JsonHandler.jsonToMap(staffJson).data[0]
                                if (staff.signImagePath) {
                                    xzManager = GeneConstants.AUTH_FILE_URL_PATH + staff.signImagePath
                                }
                            }
                        }
                        dataMap.put("xzManager", xzManager)
                    }

                    /** 归档人员 */
                    def archiveBusiLog = EiaWorkFlowBusiLog.findAllByEiaWorkFlowBusiIdAndNodesCodeAndWorkFlowStateAndIfDel(eiaWorkFlowBusi.id, WorkFlowConstants.STAMP_WORK_FLOW_NODE_XZGZ, WorkFlowConstants.WORKFLOW_UNDER_WAY, false, [sort: "id", order: "desc",])
                    if (archiveBusiLog) {
                        def topManagerId = archiveBusiLog.get(0)?.updateUserId
                        def archiver
                        def param = [:]
                        param.staffId = String.valueOf(topManagerId)
                        def staffJson = HttpConnectTools.getResponseJson(HttpUrlConstants.SINGLE_EIA_AUTH_STAFF_INFO, param)
                        if (staffJson) {
                            if (JsonHandler.jsonToMap(staffJson).code == HttpMesConstants.CODE_OK) {
                                def staff = JsonHandler.jsonToMap(staffJson).data[0]
                                if (staff.signImagePath) {
                                    archiver = GeneConstants.AUTH_FILE_URL_PATH + staff.signImagePath
                                }
                            }
                        }
                        dataMap.put("archiver", archiver)
                    }

                    return dataMap
                }
            }
        }
    }

    /**
     * 获取印章公司名称列表
     */
    def getStampCompList() {
        def stampCompList = EiaDomainCode.findAllByDomain('STAMP_COMPANY')
        if (stampCompList) {
            return stampCompList
        } else {
            return false
        }
    }
    def ifDateTimeRex(String date){
        String pattern = "\\d{4}(\\-|\\/|.)\\d{1,2}\\1\\d{1,2}";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(date);
        return m.matches()
    }

}


