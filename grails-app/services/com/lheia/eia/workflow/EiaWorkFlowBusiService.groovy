package com.lheia.eia.workflow

import com.lheia.eia.cert.EiaCert
import com.lheia.eia.common.FuncConstants
import com.lheia.eia.common.GeneConstants
import com.lheia.eia.common.WorkFlowConstants
import com.lheia.eia.contract.EiaContractLog
import com.lheia.eia.project.EiaProjectPlan
import com.lheia.eia.project.EiaProjectPlanItem
import com.lheia.eia.tools.DateTransTools
import com.lheia.eia.tools.JsonHandler
import grails.gorm.transactions.Transactional

@Transactional
class EiaWorkFlowBusiService {
    /**
     * 流程步骤数据
     * @param params
     * @return
     */
    def getWorkFlowNodeDataList(params) {
        def eiaWorkFlowBusi = EiaWorkFlowBusi.createCriteria().get() {
            eq('tableName', params.tableName)
            eq('tableNameId', Long.parseLong(params.tableNameId))
            ne('workFlowState', WorkFlowConstants.WORKFLOW_HALT)
            eq("ifDel",false)
        }
        def workFlowJsonMap = JsonHandler.jsonToMap(eiaWorkFlowBusi.workFlowJson)
        List workFlowNodeList = workFlowJsonMap.workFlowNode
        Integer nodeIndex = workFlowJsonMap.workFlowNodeDic[eiaWorkFlowBusi.nodesCode]
        List resList = []
        def nodesTab = [] //当前节点的tab信息
        def currNodeCode = eiaWorkFlowBusi.nodesCode
        for (int i = 0; i < workFlowNodeList.size(); i++) {
            def workFlowNode = workFlowNodeList.get(i)
            def resMap = [:]
            resMap.id = i
            resMap.flowName = workFlowNode.nodesName
            resMap.nodesCode = workFlowNode.nodesCode
            resMap.nodesColor = workFlowNode.nodesColor
            resMap.ifIcon = workFlowNode.ifIcon
            def nodesUrlList = workFlowNode.nodesUrl.split(",")
            def tabTitleList = workFlowNode.nodesTabTitle.split(",")
            /**
             * 如果此节点为流程当前节点，则取其tabUrl和Name
             */
            if (workFlowNode.nodesCode == currNodeCode) {
                for (int j = 0; j < nodesUrlList.size(); j++) {
                    def nodeTab = [:]
                    def url
                    /**
                     * 判断资质流程tab显示，根据工作方案中判断是否显示
                     */
                    def eiaProjectPlanItem
                    def eiaCertInfo = EiaCert.findByIdAndIfDel(Long.valueOf(params.tableNameId), false)
                    def eiaProjectPlan = EiaProjectPlan.findByIfDelAndEiaProjectId(false,eiaCertInfo?.eiaProjectId)
                    if(eiaProjectPlan){
                        eiaProjectPlanItem = EiaProjectPlanItem.findAllByIfDelAndEiaProjectPlanId(false,eiaProjectPlan?.id)?.nodesCode
                    }
                    /***
                     * 如果为资质流程
                     */
                    if (eiaWorkFlowBusi.workFlowCode.contains("CERT_WORK_FLOW")) {
                        def eiaCert = EiaCert.findByIdAndIfDel(Long.valueOf(params.tableNameId), false)
                        def tableNameId = params.tableNameId
                        if (eiaCert.parentEiaCertId) {
                            tableNameId = eiaCert.parentEiaCertId
                        }
                        if(nodesUrlList[j].contains("reportType")){
                            /**
                             * 判断reportType，如果有reportType参数为YS,ES,SS(一审，二审，三审)
                             */
                            String getSignInfo = nodesUrlList[j].substring(nodesUrlList[j].indexOf("=") + 1).replace("&", "")
                            if(getSignInfo){
                                /**
                                 * 判断工作方案中是否有YS,ES,SS(一审，二审，三审)，如果有则显示标签，没有则不显示
                                 */
                                if(eiaProjectPlanItem.contains(getSignInfo)){
                                    nodeTab.nodesUrl = nodesUrlList[j]
                                    if(((String)nodesUrlList[j]).indexOf("?")==-1){
                                        url = nodesUrlList[j]+"?"
                                    }else{
                                        url = nodesUrlList[j]+"&"
                                    }
                                    nodeTab.nodesTabTitle = tabTitleList[j]
                                    nodeTab.nodesUrl = url + "eiaContractId=" + (eiaCert.eiaContractId?eiaCert.eiaContractId:"") + "&eiaProjectId=" + (eiaCert.eiaProjectId?eiaCert.eiaProjectId:"") + "&eiaCertId=" + params.tableNameId + "&tableName=EiaCert&tableNameId=" + tableNameId
                                }
                            }else {
                                /**
                                 * 除一审，二审，三审以为其他的标签，不判断工作方案是否存在
                                 */
                                nodeTab.nodesUrl = nodesUrlList[j]
                                if(((String)nodesUrlList[j]).indexOf("?")==-1){
                                    url = nodesUrlList[j]+"?"
                                }else{
                                    url = nodesUrlList[j]+"&"
                                }
                                nodeTab.nodesTabTitle = tabTitleList[j]
                                nodeTab.nodesUrl = url + "eiaContractId=" + (eiaCert.eiaContractId?eiaCert.eiaContractId:"") + "&eiaProjectId=" + (eiaCert.eiaProjectId?eiaCert.eiaProjectId:"") + "&eiaCertId=" + params.tableNameId + "&tableName=EiaCert&tableNameId=" + tableNameId
                            }
                        }else{
                            nodeTab.nodesUrl = nodesUrlList[j]
                            if(((String)nodesUrlList[j]).indexOf("?")==-1){
                                url = nodesUrlList[j]+"?"
                            }else{
                                url = nodesUrlList[j]+"&"
                            }
                            nodeTab.nodesTabTitle = tabTitleList[j]
                            nodeTab.nodesUrl = url + "eiaContractId=" + (eiaCert.eiaContractId?eiaCert.eiaContractId:"") + "&eiaProjectId=" + (eiaCert.eiaProjectId?eiaCert.eiaProjectId:"") + "&eiaCertId=" + params.tableNameId + "&tableName=EiaCert&tableNameId=" + tableNameId
                        }
                    }else{
                        nodeTab.nodesTabTitle = tabTitleList[j]
                        nodeTab.nodesUrl = nodesUrlList[j]
                        if(((String)nodesUrlList[j]).indexOf("?")==-1){
                            url = nodesUrlList[j]+"?"
                        }else{
                            url = nodesUrlList[j]+"&"
                        }
                    }
                    if (eiaWorkFlowBusi.tableName == GeneConstants.DOMAIN_EIA_PROJECT) {

                        //节点url增加参数eiaProjectID
                        nodeTab.nodesUrl = url+ "eiaProjectId=" + Long.valueOf(params.tableNameId) + "&tableName=" + eiaWorkFlowBusi.tableName + "&tableNameId=" + eiaWorkFlowBusi.tableNameId

                    }
                    if (eiaWorkFlowBusi.tableName == GeneConstants.DOMAIN_EIA_CONTRACT) {
                        //节点url增加参数eiaProjectID
                        nodeTab.nodesUrl = url+ "eiaContractId=" + Long.valueOf(params.tableNameId) + "&tableName=" + eiaWorkFlowBusi.tableName + "&tableNameId=" + eiaWorkFlowBusi.tableNameId + "&pageType=1"
                    }

                    if (eiaWorkFlowBusi.tableName == GeneConstants.DOMAIN_EIA_CONTRACT_LOG) {
                        def eiaContractLog = EiaContractLog.findByIdAndIfDel(Long.valueOf(params.tableNameId),false)
                        //节点url增加参数eiaProjectID
                        nodeTab.nodesUrl = url + "eiaContractLogId=" + Long.valueOf(params.tableNameId) + "&tableName=" + eiaWorkFlowBusi.tableName + "&tableNameId=" + eiaWorkFlowBusi.tableNameId + "&pageType=1" +"&eiaContractId=" + eiaContractLog.eiaContractId
                    }
                    if (eiaWorkFlowBusi.tableName == GeneConstants.DOMAIN_EIA_OFFER) {
                        //节点url增加参数eiaOfferId
                        nodeTab.nodesUrl = url+ "eiaOfferId=" + Long.valueOf(params.tableNameId) + "&tableName=" + eiaWorkFlowBusi.tableName + "&tableNameId=" + eiaWorkFlowBusi.tableNameId + "&pageType=1"
                    }
                    if (eiaWorkFlowBusi.tableName == GeneConstants.DOMAIN_EIA_LAB_OFFER) {
                        //节点url增加参数eiaLabOfferId
                        nodeTab.nodesUrl = url+ "eiaLabOfferId=" + Long.valueOf(params.tableNameId) + "&tableName=" + eiaWorkFlowBusi.tableName + "&tableNameId=" + eiaWorkFlowBusi.tableNameId + "&pageType=1"
                    }
                    if (eiaWorkFlowBusi.tableName == GeneConstants.DOMAIN_EIA_STAMP) {
                        //节点url增加参数eiaLabOfferId
                        nodeTab.nodesUrl = url+ "eiaStampId=" + Long.valueOf(params.tableNameId) + "&tableName=" + eiaWorkFlowBusi.tableName + "&tableNameId=" + eiaWorkFlowBusi.tableNameId + "&pageType=1"
                    }
                    if(eiaWorkFlowBusi.tableName == GeneConstants.DOMAIN_EIA_PROJECT_EXPLORE){
                        //节点url增加参数eiaLabOfferId
                        nodeTab.nodesUrl = url+ "eiaProjectExploreId=" + Long.valueOf(params.tableNameId) + "&tableName=" + eiaWorkFlowBusi.tableName + "&tableNameId=" + eiaWorkFlowBusi.tableNameId + "&pageType=edit"
                    }
                    if(nodeTab.isEmpty()){
                    }else {
                        nodesTab << nodeTab
                    }

                }
            }
            resMap.flowIcon = workFlowNode.nodesIconName
            resMap.active = Boolean.valueOf(i <= nodeIndex)//流程图标是否不为灰色
            resList << resMap
        }
        def respnoseMap = [:]
        respnoseMap.nodesTab = nodesTab
        respnoseMap.nodesList = resList
        respnoseMap.currNodeCode = currNodeCode
        return respnoseMap
    }

    /**
     * 获取按钮配置
     * 根据权限查询按钮
     * @param params
     * @param session
     * @return
     */
    def getWorkFlowNodeProcessDataList(params, session) {
        def eiaWorkFlowBusi
        // eiaWorkFlowBusi = EiaWorkFlowBusi.findByTableNameAndTableNameIdAndWorkFlowState(params.tableName, Long.valueOf(params.tableNameId), WorkFlowConstants.WORKFLOW_START)
        eiaWorkFlowBusi = EiaWorkFlowBusi.createCriteria().get() {
            eq('tableName', params.tableName)
            eq('tableNameId', Long.parseLong(params.tableNameId))
            eq("ifDel",false)
            /*不为结束或者终止的情况*/
            not { 'in'('workFlowState', [WorkFlowConstants.WORKFLOW_HALT, WorkFlowConstants.WORKFLOW_END]) }
        }
        Boolean hasAuth = false
        if (eiaWorkFlowBusi) {
            /**
             * 根据权限编码判断
             */
            Long version = Long.valueOf(eiaWorkFlowBusi.version)
            if (WorkFlowConstants.AUTH_TYPE_AUTH_CODE.equals(eiaWorkFlowBusi.authType)) {
                Set functionCode = new HashSet(Arrays.asList(session.staff.funcCode.split(",")))
                String funcCode = session.staff.funcCode
                hasAuth = functionCode.contains(eiaWorkFlowBusi.authCode)
                /***** 监测方案流程里，带有TZFKQR（泰泽付款确认）的有操作权限，带有YXFKQR（宇相付款确认）的有只读权限 *****/
                for(String code in functionCode){
                    if(code.contains("YXFKQR")){
                        hasAuth = false
                        break
                    }
                }
                if(eiaWorkFlowBusi.authCode.contains("SELF")){
                    /***
                     * 如果权限编码中存在只看自己的情况对比当前登录用户和录入人是否一致
                     */
                    hasAuth = (session.staff.staffId == String.valueOf(eiaWorkFlowBusi.inputUserId))
                }
                /**
                 * 根据用户Id判断
                 */
            } else if (WorkFlowConstants.AUTH_TYPE_USER_CODE.equals(eiaWorkFlowBusi.authType)) {
                def authName = session.staff.staffName
                def authCode = Long.valueOf(session.staff.staffId)
                //Java中的equals方法对应Groovy中的== , 而Java中的==（判断是否引用同一对象）对应Groovy中的is方法
                hasAuth = (authCode.toString() == eiaWorkFlowBusi.authCode)
            }
            /**
             * 有权限
             */
            if (hasAuth) {
                def workFlowJsonMap = JsonHandler.jsonToMap(eiaWorkFlowBusi.workFlowJson)
                List workFlowNodeList = workFlowJsonMap.workFlowNode
                Integer nodeIndex = workFlowJsonMap.workFlowNodeDic[eiaWorkFlowBusi.nodesCode]
                def workFlowNode = workFlowNodeList.get(nodeIndex)
                def resList = []
                if (eiaWorkFlowBusi.workFlowState == WorkFlowConstants.WORKFLOW_END) {
                    workFlowNode.nodeProcessMap.each {
                        def resMap = [:]
                        resMap.processCode = it.value.processCode
                        resMap.eiaWorkFlowBusiId = eiaWorkFlowBusi.id
                        resList << resMap
                    }
                } else {
                    workFlowNode.nodeProcessMap.each {
                        def resMap = [:]
                        resMap.btnName = it.value.processShowName
                        resMap.processCode = it.value.processCode
                        resMap.eiaWorkFlowBusiId = eiaWorkFlowBusi.id
                        resMap.btnIcon = it.value.processIconName
                        resMap.btnColor = it.value.processColor
                        resMap.actionUrl = it.value.processUrl
                        resMap.processUrlParams = it.value.processUrlParams
                        resMap.processJumpUrlParams = it.value.processJumpUrlParams
                        resMap.processJumpUrl = it.value.processJumpUrl
                        resMap.currNodesName = workFlowNode.nodesName
                        resMap.version = version
                        resList << resMap
                    }
                }

                return resList
            } else {
                return false
            }
        } else {
            return false
        }
    }
    /**
     * 检查工作流是否可以进入查看页
     */
    def checkWorkFlow(String tableName, Long tableNameId) {
        def eiaWorkFlowBusi = EiaWorkFlowBusi.createCriteria().get() {
            eq('tableName', tableName)
            eq('tableNameId', tableNameId)
            ne('workFlowState', WorkFlowConstants.WORKFLOW_HALT)
            eq("ifDel",false)
        }
        if (eiaWorkFlowBusi && eiaWorkFlowBusi.workFlowState != WorkFlowConstants.WORKFLOW_HALT) {
            return eiaWorkFlowBusi
        } else {
            return false
        }
    }
    /***
     * 获取代办流程
     * @param params
     * @param session
     * @return
     */
    def getEiaWorkFlowBusiDataList(params, session) {
        def staffId = session.staff.staffId
        int page
        int limit
        if (params.page && params.limit) {
            page = Integer.valueOf(params.page) - 1
            limit = Integer.valueOf(params.limit)
        }
        def busiList
        if (params.tableName == GeneConstants.DOMAIN_EIA_PROJECT) {

            busiList = EiaWorkFlowBusi.createCriteria().list(max: limit, offset: page * limit, sort: "lastUpdated", order: "desc") {
                and {
                    if (params.workFlowTitle) {
                        or{
                            like("workFlowTitle", "%" + params.workFlowTitle + "%")
                            like("inputUser", "%" + params.workFlowTitle + "%")
                        }

                    }
                    eq("ifDel",false)
                    eq("authCode", String.valueOf(staffId))
                    'in'("workFlowState", [WorkFlowConstants.WORKFLOW_START, WorkFlowConstants.WORKFLOW_UNDER_WAY])
                }
            }
        } else if (params.tableName == GeneConstants.DOMAIN_EIA_CONTRACT) {
            Set functionCode = new HashSet(Arrays.asList(session.staff.funcCode.split(",")))
            def keyword = params.contractName
            busiList = EiaWorkFlowBusi.createCriteria().list(max: limit, offset: page * limit, sort: "lastUpdated", order: "desc") {
                or {
                    if (functionCode.contains(FuncConstants.EIA_HGGL_HTCJ_ADDSELF)) {
                        functionCode.remove(FuncConstants.EIA_HGGL_HTCJ_ADDSELF)
                        and {/**如果为合同申请节点**/
                            eq("authCode", FuncConstants.EIA_HGGL_HTCJ_ADDSELF)
                            eq("inputUserId", Long.valueOf(staffId))
                        }
                    }

                    and {
                        'in'("authCode", functionCode)
                        /**
                         * 查看全部的客户数据
                         */
                        if (!session?.staff?.funcCode?.contains(FuncConstants.EIA_YWKB_VIEWALL)) {
                            /*
                         * 查看本部门客户数据
                         */
                            if (session?.staff?.funcCode?.contains(FuncConstants.EIA_YWKB_VIEWDEPT)) {
                                like("inputDeptCode", "%" + session.staff.orgCode + "%")
                            }
                            /*
                          * 查看本人客户数据
                          */
                            else if (session?.staff?.funcCode?.contains(FuncConstants.EIA_YWKB_VIEWSELF)) {
                                eq("inputUserId", Long.valueOf(session.staff.staffId))
                            }
                        }
                    }
                }
                eq("tableName", params.tabName)
                eq("ifDel",false)
                'in'("workFlowState", [WorkFlowConstants.WORKFLOW_START, WorkFlowConstants.WORKFLOW_UNDER_WAY])
                if (keyword && !"合同名称,合同编号,录入部门,录入人".equals(keyword)) {
                    or{
                        like("workFlowTitle", "%" + keyword + "%")
                        like("inputDept", "%" + keyword + "%")
                        like("inputUser", "%" + keyword + "%")
                    }
                }
            }
        }else if(params.tabName =='EiaStamp'){
            Set functionCode = new HashSet(Arrays.asList(session.staff.funcCode.split(",")))
            if(functionCode.contains(FuncConstants.EIA_HGGL_YZSH_XZJLSH)){
                busiList = EiaWorkFlowBusi.createCriteria().list(max: limit, offset: page * limit, sort: "lastUpdated", order: "desc") {
                    or{
                        functionCode.remove(FuncConstants.EIA_XZGL_YZSH_VIEWALL)
                        and {/**如果为部门审核节点**/
                            eq("authCode",'EIA_HGGL_YZSH_BMSH')
                            like("inputDeptCode", "%" + session.staff.orgCode + "%")
                        }
                        and {
                            'in'("authCode", functionCode)
                            ne('authCode','EIA_HGGL_YZSH_BMSH')
                            /**
                             * 查看全部的客户数据
                             */
                            if (!session?.staff?.funcCode?.contains('EIA_XZGL_YZSH_VIEWALL')) {
                                /*
                             * 查看本部门客户数据
                             */
                                if (session?.staff?.funcCode?.contains('EIA_XZGL_YZSH_VIEWDEPT')) {
                                    like("inputDeptCode", "%" + session.staff.orgCode + "%")
                                }
                                /*
                              * 查看本人客户数据
                              */
                                else if (session?.staff?.funcCode?.contains('EIA_XZGL_YZSH_VIEWSELF')) {
                                    eq("inputUserId", Long.valueOf(session.staff.staffId))
                                }
                            }
                        }
                    }
                    eq("tableName", params.tabName)
                    eq("ifDel",false)
                    'in'("workFlowState", [WorkFlowConstants.WORKFLOW_START, WorkFlowConstants.WORKFLOW_UNDER_WAY])
                }
            }else{
                busiList = EiaWorkFlowBusi.createCriteria().list(max: limit, offset: page * limit, sort: "lastUpdated", order: "desc") {
                    and {
                        'in'("authCode", functionCode)
                        /**
                         * 查看全部的客户数据
                         */
                        if (!session?.staff?.funcCode?.contains('EIA_XZGL_YZSH_VIEWALL')) {
                            /*
                         * 查看本部门客户数据
                         */
                            if (session?.staff?.funcCode?.contains('EIA_XZGL_YZSH_VIEWDEPT')) {
                                like("inputDeptCode", "%" + session.staff.orgCode + "%")
                            }
                            /*
                          * 查看本人客户数据
                          */
                            else if (session?.staff?.funcCode?.contains('EIA_XZGL_YZSH_VIEWSELF')) {
                                eq("inputUserId", Long.valueOf(session.staff.staffId))
                            }
                        }
                    }
                    eq("tableName", params.tabName)
                    eq("ifDel",false)
                    'in'("workFlowState", [WorkFlowConstants.WORKFLOW_START, WorkFlowConstants.WORKFLOW_UNDER_WAY])

                }
            }
        }

        else if (params.authType == "ALL") {
            /**
             * 查看已办事项
             */
            if(params.workType){
                /**
                 * 判断是否为已办项目workType有值为已办项目
                 */
                def eiaWorkFlowBusiLogList = EiaWorkFlowBusiLog.createCriteria().list() {
                    eq("updateUserId", Long.valueOf(staffId))
                    eq("ifDel", false)
                }
                busiList = EiaWorkFlowBusi.createCriteria().list(max: limit, offset: page * limit, sort: "lastUpdated", order: "desc") {
                    if(eiaWorkFlowBusiLogList){
                        ArrayList eiaWorkFlowBusiIds = eiaWorkFlowBusiLogList.eiaWorkFlowBusiId
                        if(eiaWorkFlowBusiIds){
                            'in'("id",eiaWorkFlowBusiIds.unique())
                        }
                    }
                    else {
                        eq("inputUserId",Long.valueOf(-1))
                    }
                    if (params.workFlowTitle) {
                        or{
                            like("workFlowTitle", "%" + params.workFlowTitle + "%")
                            like("inputUser", "%" + params.workFlowTitle + "%")
                        }
                    }
                    eq("workFlowState",WorkFlowConstants.WORKFLOW_UNDER_WAY)
                    eq("ifDel", false)
                }
            }
            else{
                /***
                 查询所有代办事项
                 */
                Set functionCode = new HashSet(Arrays.asList(session.staff.funcCode.split(",")))
                Set selfFunctionCode = new HashSet()
                Set queryCode = new HashSet()
                functionCode.each{
                    if (it.contains("SELF")) {
                        selfFunctionCode.add(it)
                    } else if(it.contains("YXFKQR")) {
                        queryCode.add(it.substring(0,it.indexOf("YXFKQR")) + "TZFKQR")
                    } else {
                        queryCode.add(it)
                    }
                }
                busiList = EiaWorkFlowBusi.createCriteria().list(max: limit, offset: page * limit, sort: "lastUpdated", order: "desc") {
                    or {
                        selfFunctionCode.each{
                            def selfCode = it
                            and{
                                eq("authCode", selfCode)
                                eq("inputUserId", Long.valueOf(staffId))
                            }
                        }

//                        if (functionCode.contains(FuncConstants.EIA_HGGL_HTCJ_ADDSELF)) {
//                            functionCode.remove(FuncConstants.EIA_HGGL_HTCJ_ADDSELF)
//                            and {/**如果为合同申请节点**/
//                                eq("authCode", FuncConstants.EIA_HGGL_HTCJ_ADDSELF)
//                                eq("inputUserId", Long.valueOf(staffId))
//                            }
//                        }
//                        /**如果为资质申请节点**/
//                        if (functionCode.contains(FuncConstants.EIA_HGGL_ZZBG_ADDSELF)) {
//                            functionCode.remove(FuncConstants.EIA_HGGL_ZZBG_ADDSELF)
//                            and {
//                                eq("authCode", FuncConstants.EIA_HGGL_ZZBG_ADDSELF)
//                                eq("inputUserId", Long.valueOf(staffId))
//                            }
//                        }
//                        /**如果为生成订单节点**/
//                        if (functionCode.contains(FuncConstants.EIA_XMGL_JCFA_SCDDSELF)) {
//                            functionCode.remove(FuncConstants.EIA_XMGL_JCFA_SCDDSELF)
//                            and {
//                                eq("authCode", FuncConstants.EIA_XMGL_JCFA_SCDDSELF)
//                                eq("inputUserId", Long.valueOf(staffId))
//                            }
//                        }
//                        /**如果为监测进行节点**/
//                        if (functionCode.contains(FuncConstants.EIA_XMGL_JCFA_JCWCSELF)) {
//                            functionCode.remove(FuncConstants.EIA_XMGL_JCFA_JCWCSELF)
//                            and {
//                                eq("authCode", FuncConstants.EIA_XMGL_JCFA_JCWCSELF)
//                                eq("inputUserId", Long.valueOf(staffId))
//                            }
//                        }
                        and {
                            'in'("authCode", queryCode)
                            /**
                             * 查看全部的客户数据
                             */
                            if (!session?.staff?.funcCode?.contains(FuncConstants.EIA_YWKB_VIEWALL)) {
                                /*
                                 * 查看本部门客户数据
                                 */
                                if (session?.staff?.funcCode?.contains(FuncConstants.EIA_YWKB_VIEWDEPT)) {
                                    like("inputDeptCode", "%" + session.staff.orgCode + "%")
                                }
                                /*
                                  * 查看本人客户数据
                                  */
                                else if (session?.staff?.funcCode?.contains(FuncConstants.EIA_YWKB_VIEWSELF)) {
                                    eq("inputUserId", Long.valueOf(session.staff.staffId))
                                }
                            }
                        }
                        and {
                            eq("authCode", String.valueOf(staffId))
                            'in'("workFlowState", [WorkFlowConstants.WORKFLOW_START, WorkFlowConstants.WORKFLOW_UNDER_WAY])
                        }
                    }
                    if (params.workFlowTitle) {
                        or{
                            like("workFlowTitle", "%" + params.workFlowTitle + "%")
                            like("inputUser", "%" + params.workFlowTitle + "%")
                        }
                    }
                    /**
                     * 业务看板中每个标签
                     */
                    if(GeneConstants.DOMAIN_EIA_CONTRACT.equals(params.tabName)){
                        'in'("tableName", [GeneConstants.DOMAIN_EIA_CONTRACT,GeneConstants.DOMAIN_EIA_CONTRACT_LOG,GeneConstants.DOMAIN_EIA_OFFER])
                    }else{
                        if(params.tabName){
                            eq("tableName", params.tabName)
                        }
                    }
                    'in'("workFlowState", [WorkFlowConstants.WORKFLOW_START, WorkFlowConstants.WORKFLOW_UNDER_WAY])
                    eq("ifDel", false)
                }
            }
        }
        if (!busiList) {
            def resMap = [:]
            resMap.data = []
            resMap.count = 0
            return resMap
        } else {
            def resList = []
            busiList.each {
                def resMap = [:]
                resMap.putAll(it.properties)
                resMap.dateCreated = DateTransTools.getFormatDateS(it.dateCreated)
                resList << resMap
            }
            def resMap = [:]
            resMap.data = resList
            resMap.count = busiList.totalCount
            return resMap
        }
    }

    def getEiaWorkFlowBusiCount(params, session) {
        if (params.authType == "USER_CODE") {
            def staffId = session.staff.staffId
            return EiaWorkFlowBusi.countByAuthCodeAndWorkFlowStateAndIfDel(String.valueOf(staffId), WorkFlowConstants.WORKFLOW_START,false)
        } else if (params.authType == "AUTH_CODE") {
            List functionCode = session.staff.funcCode.split(",")
            return EiaWorkFlowBusi.countByAuthCodeInListAndWorkFlowStateAndIfDel(functionCode, WorkFlowConstants.WORKFLOW_START,false)
        }
    }

    def getEiaWorkFlowBusiLogDataList(params, session) {
        def staffId = session.staff.staffId
        def busiList = EiaWorkFlowBusiLog.findAllByAuthCodeAndIfDel(Long.valueOf(staffId),false)
        return busiList
    }


}
