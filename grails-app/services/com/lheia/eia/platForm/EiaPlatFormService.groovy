package com.lheia.eia.platForm

import com.lheia.eia.client.EiaClient
import com.lheia.eia.common.GeneConstants
import com.lheia.eia.common.HttpMesConstants
import com.lheia.eia.common.HttpUrlConstants
import com.lheia.eia.common.WorkFlowConstants
import com.lheia.eia.config.EiaDomainCode
import com.lheia.eia.config.EiaFileUpload
import com.lheia.eia.contract.EiaContract
import com.lheia.eia.project.EiaEnvProject
import com.lheia.eia.project.EiaGreenProject
import com.lheia.eia.project.EiaProject
import com.lheia.eia.project.EiaProjectPlan
import com.lheia.eia.project.EiaProjectPlanItem
import com.lheia.eia.task.EiaTask
import com.lheia.eia.tools.HttpConnectTools
import com.lheia.eia.tools.JsonHandler
import grails.gorm.transactions.Transactional
import org.apache.commons.lang.StringUtils
import org.grails.web.json.JSONArray
import org.grails.web.json.JSONObject

import java.lang.reflect.Field
import java.text.SimpleDateFormat


@Transactional
class EiaPlatFormService {

    /**
     * 环评审批保存
     */
    def eiaPubProjectSave(params, session){
        def eiaPubProject =  new EiaPubProject(params)
        eiaPubProject.inputDept = session.staff.orgName
        eiaPubProject.inputDeptCode = session.staff.orgCode
        eiaPubProject.inputDeptId = Long.parseLong(session.staff.orgId)
        eiaPubProject.inputUser = session.staff.staffName
        eiaPubProject.inputUserId = Long.parseLong(session.staff.staffId)
        eiaPubProject.save( flush:true,failOnError:true)
    }
    /**
     * 环评审批公示编辑保存
     */
    def eiaPubProjectUpdate(params){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd")
        def eiaPubProject = EiaPubProject.findById(params.long('eiaPubProjectId'))
        if (params.pubDate) {
            params.pubDate = sdf.parse(params.pubDate)
        }
        if (params.approveDate) {
            params.approveDate = sdf.parse(params.approveDate)
        }
        eiaPubProject.properties = params
        eiaPubProject.regionCode = params.regionNameDropCode
        eiaPubProject.regionName = params.regionNameDrop
        eiaPubProject.regionLevel = EiaDomainCode.findByDomainAndCode(GeneConstants.PROVINCE_CITY, params.regionNameDropCode)?.codeLevel
        eiaPubProject.save( flush:true,failOnError:true)
    }
    /**
     * 环评审批公示详情
     */
    def getEiaPubProjectDataMap(Long eiaPubProjectId, Long eiaDataShareId, session){
        def dataMap = [:]
        def eiaPubProject
        if (eiaPubProjectId) {
            eiaPubProject = EiaPubProject.findById(eiaPubProjectId)
        } else if (eiaDataShareId) {
            def eiaDataShare = EiaDataShare.findById(eiaDataShareId)
            /**
             * 浏览次数
             */
            def viewsCount = 0
            if (eiaDataShare?.viewsCount >= 0) {
                viewsCount = eiaDataShare?.viewsCount + 1
            }
            eiaDataShare.viewsCount = viewsCount
            eiaDataShare.save(flash: true, failOnErynnkror: true)
            eiaPubProject = EiaPubProject.findById(eiaDataShare?.publicProId)
        }
        if (eiaPubProject) {
            dataMap = this.convertToMap(eiaPubProject.properties)
            def regionDomainCode = EiaDomainCode.findByDomainAndCode(GeneConstants.PROVINCE_CITY, eiaPubProject.regionCode)
            if (regionDomainCode) {
                def regionMap = [:]
                regionMap.id = regionDomainCode.code
                regionMap.name = regionDomainCode.codeDesc
                dataMap.remove('regionName')
                dataMap.put('regionName', regionMap)
            }
            def files = []
            def uploadFiles = EiaFileUpload.findAllByTableIdAndTableName(eiaPubProject.id, GeneConstants.EIA_PUB_PROJECT_TABLE_NAME)
            uploadFiles.each {
                def fileMap = [:]
                fileMap.id = it.id
                fileMap.fileName = it.fileName
                fileMap.fileUrl = it?.filePath + it?.saveFileName
                files << fileMap
            }
            dataMap.put("files", files)
        }
        return dataMap
    }
    /**
     * 环评审批公示删除
     */
    def eiaPubProjectDel(Long eiaPubProjectId){
        def publicityProject = EiaPubProject.findById(eiaPubProjectId)
        publicityProject.ifDel = true
        publicityProject.save( flush:true,failOnError:true)
    }
    /**
     * 环评审批公示列表
     * @return
     */
    def eiaPubProjectQuery(params){
        int page
        int limit
        if (params.page && params.limit) {
            page = params.int('page') - 1
            limit = params.int('limit')
        }
        def eiaPubProjectList = EiaPubProject.createCriteria().list(max: limit, offset: page * limit) {
            /** 项目名称 */
            if(params.projectName){
                like('projectName','%' + params.projectName + '%')
            }
            /** 客户所在区域 */
            if(params.regionName){
                def clientIds = EiaClient.findAllByClientAddressAndIfDel(params.regionName, false)?.id
                'in'('id', clientIds)
            }
            eq("ifDel",false)
            order("id","desc")
        }
        def data = []
        eiaPubProjectList.each{
            def map = [:]
            map.id = it.id
            /** 项目名称 */
            map.projectName = it.projectName?:""
            /** 环境影响评价行业类别;行业类型及代码 */
            def environmentaType = EiaDomainCode.findByDomainAndCode('INS_TYPE_CODE',it.environmentaType?:"")
            def industryType = EiaDomainCode.findByDomainAndCode('GNS_TYPE_CODE',it.industryType?:"")
            if (environmentaType) {
                map.environmentaType = environmentaType.codeRemark + " " + environmentaType.codeDesc
            } else {
                map.environmentaType = it.environmentaType?:""
            }
            if (industryType) {
                map.industryType = industryType.codeRemark + " " + industryType.codeDesc
            } else {
                map.industryType = it.industryType?:""
            }
            /** 生产工艺 */
            map.productionEngineer = it.productionEngineer?:""
            /** 产品或功能 */
            map.productFunction =it.productFunction?:""
            /** 建设性质 */
            map.natureConstructio = it.natureConstructio?:""
            /** 审批年度 */
            map.publictyYear = it.publictyYear?:""
            /** 公示日期 */
            map.pubDate = it.pubDate == null ?"-":it.pubDate.format('yyyy-MM-dd')
            /** 审批部门 */
            map.approveDept = it.approveDept?:""
            /** 环评单位 */
            map.eiaUnit = it.eiaUnit?:""
            /** 建设单位 */
            map.developmentOrg = it.developmentOrg?:""
            /** 数据类别 */
            map.dataType = it.dataType?:""
            /** 是否发布 */
            map.ifPub = it.ifPub
            def file = EiaFileUpload.findByTableId(it?.id)
            map.fileName =  file?.fileName?:""
            map.fileId =  file?.id?:""
            map.ifPub =  it.ifPub
            data << map
        }
        def dataMap = [:]
        dataMap.data = data
        dataMap.total = eiaPubProjectList.totalCount
        return dataMap
    }
    /**
     * 审批公示发布
     */
    def eiaPubProjectConfirm(Long eiaPubProjectId) {
        /** 数据共享平台中间表 */
        def eiaPubProject = EiaPubProject.findById(eiaPubProjectId)
        def responseString = HttpConnectTools.getResponseJson(HttpUrlConstants.GIS_GEO_PUB_QUERY,[tableName:GeneConstants.DOMAIN_EIA_PUB_PROJECT, tableNameId:String.valueOf(eiaPubProjectId)])
        if(responseString){
            def resMap = JsonHandler.jsonToMap(responseString)
            if(resMap.code == HttpMesConstants.CODE_OK){
                eiaPubProject?.geographicEast  = resMap.data.east
                eiaPubProject?.geographicNorth = resMap.data.north
                eiaPubProject?.geographicStartEast = resMap.data.startEast
                eiaPubProject?.geographicStartNorth = resMap.data.startNorth
                eiaPubProject?.geographicEndEast = resMap.data.EndEast
                eiaPubProject?.geographicEndNorth = resMap.data.EndNorth
            }else{
                return [code:HttpMesConstants.CODE_FAIL,msg:HttpMesConstants.MSG_NO_GIS]
            }
        }else{
            return [code:HttpMesConstants.CODE_FAIL,msg:HttpMesConstants.MSG_NO_GIS]
        }
        def dataSharePlatform = new EiaDataShare()
        dataSharePlatform.properties = eiaPubProject.properties
        dataSharePlatform.publicProId = eiaPubProjectId
        dataSharePlatform.dataSources = eiaPubProject?.dataType
        dataSharePlatform.projectName = eiaPubProject?.projectName
        dataSharePlatform.publictyYear = eiaPubProject?.publictyYear
        dataSharePlatform.natureConstructio = eiaPubProject?.natureConstructio
        def environmentaTypeC
        def industryTypeC
        def environmentaType = EiaDomainCode.findByDomainAndCode('INS_TYPE_CODE',eiaPubProject?.environmentaType)
        def industryType = EiaDomainCode.findByDomainAndCode('GNS_TYPE_CODE',eiaPubProject?.industryType)
        if (environmentaType) {
            environmentaTypeC = environmentaType.codeRemark + " " + environmentaType.codeDesc
        } else {
            environmentaTypeC = eiaPubProject?.environmentaType
        }
        if (industryType) {
            industryTypeC = industryType.codeRemark + " " + industryType.codeDesc
        } else {
            industryTypeC = eiaPubProject?.industryType
        }
        dataSharePlatform.environmentaType = environmentaTypeC
        dataSharePlatform.industryType = industryTypeC
        dataSharePlatform.productionEngineer = eiaPubProject?.productionEngineer
        dataSharePlatform.productFunction = eiaPubProject?.productFunction
        dataSharePlatform.developmentOrg = eiaPubProject?.developmentOrg
        dataSharePlatform.eiaUnit = eiaPubProject?.eiaUnit
        dataSharePlatform.approveDate = eiaPubProject?.approveDate
        dataSharePlatform.approveDept = eiaPubProject?.approveDept
        dataSharePlatform.regionName = eiaPubProject?.regionName
        dataSharePlatform.regionCode = eiaPubProject?.regionCode
        dataSharePlatform.regionLevel = eiaPubProject?.regionLevel
        dataSharePlatform.projectMemo = eiaPubProject?.projectMemo
        dataSharePlatform.save (flush:true,failOnError:true)
        eiaPubProject.ifPub = true
        eiaPubProject.save (flush:true,failOnError:true)
        return [code:HttpMesConstants.CODE_OK,msg:HttpMesConstants.MSG_SUBMIT_OK]
    }
    /**
     * 规划公示编辑
     */
    def eiaPlanShowUpdate(params) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd")
        def planAnnouncement = EiaPlanShow.findById(params.long('eiaPlanShowId'))
        /** 发布日期 */
        params.pubDate = sdf.parse(params.pubDate)
        planAnnouncement.properties = params
        planAnnouncement.save(flush: true, failOnError: true)
    }
    /**
     * 规划公示详情
     */
    def getEiaPlanShowDataMap(Long eiaPlanShowId){
        def dataMap = [:]
        def eiaPlanShow = EiaPlanShow.findById(eiaPlanShowId)
        dataMap.eiaPlanShow = eiaPlanShow
        def pubDate = eiaPlanShow.pubDate
        if (pubDate) {
            pubDate = pubDate.format("yyyy-MM-dd")
        }
        dataMap.pubDate = pubDate
        def planPictures
        def pictureUrls = []
        if (eiaPlanShow.spiderFileImagesUrl) {
            planPictures = eiaPlanShow.spiderFileImagesUrl.replace(" | ", ",")
            for (int i = 0; i < planPictures.split(",").size(); i++) {
                pictureUrls.add(planPictures.split(",")[i])
            }
        } else {
            planPictures = ""
        }
        dataMap.pictureUrls = pictureUrls
        dataMap.planPictures = planPictures
        dataMap.geographicEast = eiaPlanShow.geographicEast
        dataMap.geographicNorth = eiaPlanShow.geographicNorth
        dataMap.geographicStartEast = eiaPlanShow.geographicStartEast
        dataMap.geographicStartNorth = eiaPlanShow.geographicStartNorth
        dataMap.geographicEndEast = eiaPlanShow.geographicEndEast
        dataMap.geographicEndNorth = eiaPlanShow.geographicEndNorth
        return dataMap
    }
    /**
     * 规划公示删除
     */
    def eiaPlanShowDel(Long eiaPlanShowId) {
        def eiaPlanShow = EiaPlanShow.findById(eiaPlanShowId)
        eiaPlanShow.ifDel = true
        eiaPlanShow.save(flush: true, failOnError: true)
    }
    /**
     * 规划公示列表查询
     */
    def eiaPlanShowQuery(params){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd")
        int page
        int limit
        if (params.page && params.limit) {
            page = params.int('page') - 1
            limit = params.int('limit')
        }
        def eiaPlanShowList = EiaPlanShow.createCriteria().list(max: limit, offset: page * limit) {
            /** 标题 */
            if (params.title) {
                like('title', "%" + params.title + "%")
            }
            eq("ifDel",false)
            order("id","desc")
        }
        def data = []
        eiaPlanShowList.each {
            def map = [:]
            map.id = it.id
            map.spiderFileUrl = it?.spiderFileUrl ?: ""
            if (it.pubDate) {
                map.pubDate = sdf.format(it.pubDate)
            } else {
                map.pubDate = ""
            }
            map.ifFav= it.ifFav
            map.title = it.title
            map.source = it?.source ?: ""
            map.content = it?.content ?: ""
            map.ifFav= it.ifFav
            map.title = it.title ?: ""
            map.source = it?.source ?: ""
            map.content = it?.content ?: ""
            if (it.pubDate) {
                map.pubDate = it.pubDate.format('yyyy-MM-dd')
            } else {
                map.pubDate = ""
            }
            /** 是否发布 */
            map.ifPub = it.ifPub
            data << map
        }
        def dataMap = [:]
        dataMap.data = data
        dataMap.total = eiaPlanShowList.totalCount
        return dataMap
    }
    /**
     * 规划公示-共享查询
     */
    def platFormEiaPlanShowQuery(params){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd")
        def collect
        if (params.searchStringify) {
            def searchStringify = params.searchStringify
            JSONArray array = new JSONArray(searchStringify)
            for (int i=0; i<array.size(); i++) {
                JSONObject obj = array.getJSONObject(i);
                if (obj.collect) {
                    collect = obj.collect
                }
            }
        }
        int page
        int limit
        if (params.page && params.limit) {
            page = params.int('page') - 1
            limit = params.int('limit')
        }
        def eiaPlanShowList = EiaPlanShow.createCriteria().list(max: limit, offset: page * limit) {
            /** 标题、来源机关、公布内容 */
            if(params.dataShareSearchPlanText && params.dataShareSearchPlanText != "标题、来源机关、公布内容"){
                or{
                    like('title', '%' + params.dataShareSearchPlanText + '%')
                    like('source', '%' + params.dataShareSearchPlanText + '%')
                    like('content', '%' + params.dataShareSearchPlanText + '%')
                }
            }
            /** 标题 */
            if (params.title) {
                eq('title', params.title)
            }
            /** 公布日期 */
            if (params.pubDate) {
                eq('pubDate', sdf.parse(params.pubDate))
            }
            /** 收藏个数 */
            if (collect) {
                eq("ifFav",true)
            }
            eq("ifDel",false)
            eq("ifPub",true)
            order("id","desc")
        }
        def data = []
        eiaPlanShowList.each {
            def map = [:]
            map.id = it.id
            map.spiderFileUrl = it?.spiderFileUrl ?: ""
            if (it.pubDate) {
                map.pubDate = sdf.format(it.pubDate)
            } else {
                map.pubDate = ""
            }
            map.ifFav= it.ifFav
            if (params.dataShareSearchPlanText && params.dataShareSearchPlanText != "标题、来源机关、公布内容") {
                /** 标题 */
                if (it?.title.indexOf(params.dataShareSearchPlanText) != -1) {
                    /** 判断要查询的字符长度 */
                    def projectNameInfo = params.dataShareSearchPlanText
                    int projectNameLength = projectNameInfo.length()
                    /** 查询字符位置开始 */
                    int i = it.title.indexOf(params.dataShareSearchPlanText)
                    /** 截取查询名称 */
                    def queryName = it.title.substring(i, i + projectNameLength)
                    /** 截取查询字段之前的名称 */
                    def queryBeforeName = it.title.substring(0, i)
                    /** 截取查询字段之后的名称 */
                    def queryAfterName = it.title.substring(i + projectNameLength)
                    map.title = queryBeforeName + "<font color='red'>" + queryName + "</font>" + queryAfterName
                } else {
                    map.title = it.title
                }
                /** 来源机关 */
                if (params.dataShareSearchPlanText && params.dataShareSearchPlanText != "标题、来源机关、公布内容" && it.source) {
                    if (it?.source.indexOf(params.dataShareSearchPlanText) != -1) {
                        /** 判断要查询的字符长度 */
                        def projectNameInfo = params.dataShareSearchPlanText
                        int projectNameLength = projectNameInfo.length()
                        /** 查询字符位置开始 */
                        int i = it?.source.indexOf(params.dataShareSearchPlanText)
                        /** 截取查询名称 */
                        def queryName = it?.source ?: "".substring(i, i + projectNameLength)
                        /** 截取查询字段之前的名称 */
                        def queryBeforeName = it?.source ?: "".substring(0, i)
                        /** 截取查询字段之后的名称 */
                        def queryAfterName = it?.source ?: "".substring(i + projectNameLength)
                        map.source = queryBeforeName + "<font color='red'>" + queryName + "</font>" + queryAfterName
                    } else {
                        map.source = it?.source ?: ""
                    }
                }else {
                    map.source = it?.source ?: ""
                }
                /** 公布内容 */
                if (params.dataShareSearchPlanText && params.dataShareSearchPlanText != "标题、来源机关、公布内容" && it.content) {
                    if (it?.content.indexOf(params.dataShareSearchPlanText) != -1) {
                        /** 判断要查询的字符长度 */
                        def projectNameInfo = params.dataShareSearchPlanText
                        int projectNameLength = projectNameInfo.length()
                        /** 查询字符位置开始 */
                        int i = it?.content.indexOf(params.dataShareSearchPlanText)
                        /** 截取查询名称 */
                        def queryName = it?.content.substring(i, i + projectNameLength)
                        /** 截取查询字段之前的名称 */
                        def queryBeforeName = it?.content.substring(0, i)
                        /** 截取查询字段之后的名称 */
                        def queryAfterName = it?.content.substring(i + projectNameLength)
                        map.content = queryBeforeName + "<font color='red'>" + queryName + "</font>" + queryAfterName
                    } else {
                        map.content = it?.content ?: ""
                    }
                } else {
                    map.content = it?.content ?: ""
                }
            } else {
                map.title = it.title ?: ""
                map.source = it?.source ?: ""
                map.content = it?.content ?: ""
            }
            map.ifFav= it.ifFav
            if (it.pubDate) {
                map.pubDate = it.pubDate.format('yyyy-MM-dd')
            } else {
                map.pubDate = ""
            }
            /** 是否发布 */
            map.ifPub = it.ifPub
            data << map
        }
        def dataMap = [:]
        dataMap.data = data
        dataMap.total = eiaPlanShowList.totalCount
        return dataMap
    }
    /**
     * 规划公示收藏保存
     */
    def favEiaPlanShowSave(Long eiaPlanShowId, Boolean ifFav) {
        def eiaPlanShow = EiaPlanShow.findById(eiaPlanShowId)
        eiaPlanShow.ifFav = ifFav
        eiaPlanShow.save(flush: true, failOnError: true)
    }
    /**
     * 规划公示发布
     */
    def eiaPlanShowConfirm(Long eiaPubProjectId) {
        def eiaPlanShow = EiaPlanShow.findById(eiaPubProjectId)
//        def responseString = HttpConnectTools.getResponseJson(HttpUrlConstants.GIS_GEO_PUB_QUERY,[tableName:GeneConstants.DOMAIN_EIA_PLAN_SHOW, tableNameId:String.valueOf(eiaPubProjectId)])
//        if(responseString){
//            def resMap = JsonHandler.jsonToMap(responseString)
//            if(resMap.code == HttpMesConstants.CODE_OK){
//                eiaPlanShow?.geographicEast  = resMap.data.east
//                eiaPlanShow?.geographicNorth = resMap.data.north
//                eiaPlanShow?.geographicStartEast = resMap.data.startEast
//                eiaPlanShow?.geographicStartNorth = resMap.data.startNorth
//                eiaPlanShow?.geographicEndEast = resMap.data.EndEast
//                eiaPlanShow?.geographicEndNorth = resMap.data.EndNorth
//            }else{
//                return [code:HttpMesConstants.CODE_FAIL,msg:HttpMesConstants.MSG_NO_GIS]
//            }
//        }else{
//            return [code:HttpMesConstants.CODE_FAIL,msg:HttpMesConstants.MSG_NO_GIS]
//        }
        eiaPlanShow.ifPub = true
        eiaPlanShow.save flush:true,failOnError:true
        return [code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SUBMIT_OK]
    }
    /**
     * 区域概况保存
     */
    def eiaAreaInfoSave(params, session) {
        def areaInfo =  new EiaAreaInfo(params)
        areaInfo.regionCode = params.regionNameDropCode
        areaInfo.planType = params.planType
        areaInfo.regionName = params.regionNameDrop
        areaInfo.regionLevel = EiaDomainCode.findByDomainAndCode(GeneConstants.PROVINCE_CITY, params.regionNameDropCode)?.codeLevel
        areaInfo.inputDept = session.staff.orgName
        areaInfo.inputDeptCode = session.staff.orgCode
        areaInfo.inputDeptId = Long.parseLong(session.staff.orgId)
        areaInfo.inputUser = session.staff.staffName
        areaInfo.inputUserId = Long.parseLong(session.staff.staffId)
        areaInfo.save( flush:true,failOnError:true)
    }
    /**
     * 区域修改
     */
    def eiaAreaInfoUpdate(params) {
        def areaInfo = EiaAreaInfo.findById(params.long('eiaAreaInfoId'))
        areaInfo.properties = params
        areaInfo.regionCode = params.regionNameDropCode
        areaInfo.regionName = params.regionNameDrop
        areaInfo.regionLevel = EiaDomainCode.findByDomainAndCode(GeneConstants.PROVINCE_CITY, params.regionNameDropCode)?.codeLevel
        areaInfo.save( flush:true,failOnError:true)
    }
    /**
     * 区域详情
     */
    def getEiaAreaInfoDataMap(Long eiaAreaInfoId, session) {
        def dataMap = [:]
        if (eiaAreaInfoId) {
            def eiaAreaInfo = EiaAreaInfo.findById(eiaAreaInfoId)
            dataMap = this.convertToMap(eiaAreaInfo.properties)
            def regionDomainCode = EiaDomainCode.findByDomainAndCode(GeneConstants.PROVINCE_CITY, eiaAreaInfo.regionCode)
            if (regionDomainCode) {
                def regionMap = [:]
                regionMap.id = regionDomainCode.code
                regionMap.name = regionDomainCode.codeDesc
                dataMap.remove('regionName')
                dataMap.put('regionName', regionMap)
            }
            def files = []
            def uploadFiles = EiaFileUpload.findAllByTableIdAndTableName(eiaAreaInfoId, GeneConstants.EIA_AREA_INFO_TABLE_NAME)
            uploadFiles.each {
                def fileMap = [:]
                fileMap.id = it.id
                fileMap.fileName = it.fileName
                fileMap.fileUrl = it?.filePath + it?.saveFileName
                files << fileMap
            }
            dataMap.put("files", files)
        }
        return dataMap
    }
    /**
     * 区域概况公示发布
     */
    def eiaAreaInfoConfirm(Long eiaPubProjectId) {
        def eiaAreaInfo = EiaAreaInfo.findById(eiaPubProjectId)
        def responseString = HttpConnectTools.getResponseJson(HttpUrlConstants.GIS_GEO_PUB_QUERY,[tableName:GeneConstants.DOMAIN_EIA_AREA_INFO, tableNameId:String.valueOf(eiaPubProjectId)])
        if(responseString){
            def resMap = JsonHandler.jsonToMap(responseString)
            if(resMap.code == HttpMesConstants.CODE_OK){
                eiaAreaInfo?.geographicEast  = resMap.data.east
                eiaAreaInfo?.geographicNorth = resMap.data.north
                eiaAreaInfo?.geographicStartEast = resMap.data.startEast
                eiaAreaInfo?.geographicStartNorth = resMap.data.startNorth
                eiaAreaInfo?.geographicEndEast = resMap.data.EndEast
                eiaAreaInfo?.geographicEndNorth = resMap.data.EndNorth
            }else{
                return [code:HttpMesConstants.CODE_FAIL,msg:HttpMesConstants.MSG_NO_GIS]
            }
        }else{
            return [code:HttpMesConstants.CODE_FAIL,msg:HttpMesConstants.MSG_NO_GIS]
        }
        eiaAreaInfo.ifPub = true
        eiaAreaInfo.save flush:true,failOnError:true
        return [code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SUBMIT_OK]
    }
    /**
     * 获取不同数据类别下的数据项
     */
    def getEiaAreaInfoByDataType(params) {
        def eiaAreaInfoId = params.long('eiaAreaInfoId')
        def dataType = params.dataType
        def dataMap = [:]
        if (eiaAreaInfoId) {
            def eiaAreaInfo = EiaAreaInfo.findById(eiaAreaInfoId)
            def dataList = []
            if (dataType == "污水处理厂") {
                dataList = GeneConstants.WSCLCLIST
            } else if (dataType == "规划环评") {
                dataList = GeneConstants.GHHPLIST
            }else if(dataType == "声功能区划"){
                dataList = GeneConstants.SGNQHLIST
            }
            dataList.each {
                dataMap.put(it, eiaAreaInfo)
            }
        } else {
            if (dataType == "污水处理厂") {
                dataMap = GeneConstants.WSCLCLIST
            } else if (dataType == "规划环评") {
                dataMap = GeneConstants.GHHPLIST
            }else if(dataType == "声功能区划"){
                dataMap = GeneConstants.SGNQHLIST
            }
        }
        return dataMap
    }
    /**
     * 区域概况删除
     */
    def eiaAreaInfoDel(Long eiaAreaInfoId){
        def areaInfo = EiaAreaInfo.findById(eiaAreaInfoId)
        areaInfo.ifDel = true
        areaInfo.save( flush:true,failOnError:true)
    }
    /**
     * 区域列表
     */
    def eiaAreaInfoQuery(params){
        def dataType
        def collect
        if (params.searchStringify) {
            def searchStringify = params.searchStringify
            JSONArray array = new JSONArray(searchStringify)
            for (int i=0; i<array.size(); i++) {
                JSONObject obj = array.getJSONObject(i)
                if (obj.dataType) {
                    dataType = obj.dataType
                }
                if (obj.collect) {
                    collect = obj.collect
                }
            }
        }
        int page
        int limit
        if (params.page && params.limit) {
            page = params.int('page') - 1
            limit = params.int('limit')
        }
        def eiaAreaInfoList = EiaAreaInfo.createCriteria().list(max: limit, offset: page * limit) {
            /** 共享平台——项目名称 */
            if(params.areaInfoSearch){
                like('projectName', '%' + params.areaInfoSearch + '%')
            }
            /** 项目名称 */
            if(params.projectName){
                like('projectName', '%' + params.projectName + '%')
            }
            /** 数据类别 */
            if(dataType){
                'in' ('dataType', dataType)
            }
            /** 客户所在区域 */
            if(params.regionName){
                eq ('regionName', params.regionName)
            }
            /** 收藏个数 */
            if(collect){
                eq ("ifFav",true)
            }
            eq("ifDel",false)
            order("id","desc")
        }
        def data = []
        eiaAreaInfoList.each{
            def map=[:]
            map.id = it.id
            /**
             * 项目名称
             */
            if(params.areaInfoSearch && params.areaInfoSearch != "项目名称") {
                if (it?.projectName.indexOf(params.areaInfoSearch) != -1) {
                    /** 判断要查询的字符长度 */
                    def projectNameInfo = params.areaInfoSearch
                    int projectNameLength = projectNameInfo.length()
                    /** 查询字符位置开始 */
                    int i = it?.projectName.indexOf(params.areaInfoSearch)
                    /** 截取查询名称 */
                    def queryName = it?.projectName.substring(i, i + projectNameLength)
                    /** 截取查询字段之前的名称 */
                    def queryBeforeName = it?.projectName.substring(0, i)
                    /** 截取查询字段之后的名称 */
                    def queryAfterName = it?.projectName.substring(i + projectNameLength)
                    /** 项目名称 */
                    map.projectName = queryBeforeName + "<font color='red'>" + queryName + "</font>" + queryAfterName
                } else {
                    map.projectName = it?.projectName?:""
                }
            } else {
                map.projectName = it?.projectName?:""
            }
            /** 地理坐标 */
            map.coordinate = "E:" + (it.coordinateE?:"") + " " + "N:" + (it.coordinateN?:"")
            /** 处理能力 */
            map.handAbility = it.handAbility?:""
            /** 处理工艺 */
            map.treatProcess = it?.treatProcess?:""
            /** 执行标准 */
            map.execStandard =it?.execStandard?:""
            /** 环评单位 */
            map.eiaUnit = it.eiaUnit?:""
            /** 审批部门 */
            map.approveDept = it?.approveDept?:""
            /** 审批文号 */
            map.approveNo = it?.approveNo?:""
            /** 所属区域 */
            map.regionName = it?.regionName?:""
            /** 数据类别 */
            map.dataType = it?.dataType?:""
            map.ifFav= it.ifFav
            map.ifPub= it.ifPub
            def fileIdArr
            def eiaFileUploadIds = EiaFileUpload.findAllByTableIdAndTableName(it.id, GeneConstants.EIA_AREA_INFO_TABLE_NAME).id
            def files = []
            if (eiaFileUploadIds) {
                def idStr = StringUtils.strip(eiaFileUploadIds.toString(), "[]")
                fileIdArr = idStr.split(",")
                for (def i : fileIdArr) {
                    def file = EiaFileUpload.findById(i)
                    def fileMap = [:]
                    fileMap.id = file.id
                    fileMap.fileName = file.fileName
                    fileMap.fileUrl = file?.filePath + file?.saveFileName
                    files << fileMap
                }
            }
            map.fileList = files
            data << map
        }
        /** 数据类别 */
        def dataCurrency
        def dataCurrencyList = []
        def domainCode = EiaDomainCode.findAllByDomain(GeneConstants.DATA_CURRENCY)
        domainCode.each{
            dataCurrency = EiaAreaInfo.countByDataTypeAndIfDel(it.codeDesc,false)
            dataCurrencyList.add(it.codeDesc+"("+dataCurrency+")")
        }
        /** 收藏 */
        def ifFavListSize = EiaAreaInfo.countByIfDelAndIfFav(false,true)
        def dataMap = [:]
        dataMap.data = data
        dataMap.total = eiaAreaInfoList.totalCount
        dataMap.dataCurrencyList = dataCurrencyList
        dataMap.ifFavListSize = ifFavListSize
        return dataMap
    }
    /**
     * 区域收藏保存
     */
    def favEiaAreaInfoSave(Long eiaAreaInfoId, Boolean ifFav){
        def eiaAreaInfo = EiaAreaInfo.findById(eiaAreaInfoId)
        eiaAreaInfo.ifFav = ifFav
        eiaAreaInfo.save(flush: true, failOnError: true)
    }
    /**
     * 环评敏感区保存
     */
    def eiaSensAreaSave(params, session){
        def eiaSensArea = new EiaSensArea(params)
        eiaSensArea.regionCode = params.regionNameDropCode
        eiaSensArea.regionName = params.regionNameDrop
        eiaSensArea.regionLevel = EiaDomainCode.findByDomainAndCode(GeneConstants.PROVINCE_CITY, params.regionNameDropCode)?.codeLevel
        eiaSensArea.inputDept = session.staff.orgName
        eiaSensArea.inputDeptCode = session.staff.orgCode
        eiaSensArea.inputDeptId = Long.parseLong(session.staff.orgId)
        eiaSensArea.inputUser = session.staff.staffName
        eiaSensArea.inputUserId = Long.parseLong(session.staff.staffId)
        eiaSensArea.save(flush: true, failOnError: true)
    }
    /**
     * 环评敏感区修改
     */
    def eiaSensAreaUpdate(params){
        def eiaSensArea = EiaSensArea.findById(params.long('eiaSensAreaId'))
        eiaSensArea.properties = params
        eiaSensArea.regionCode = params.regionNameDropCode
        eiaSensArea.regionName = params.regionNameDrop
        eiaSensArea.regionLevel = EiaDomainCode.findByDomainAndCode(GeneConstants.PROVINCE_CITY, params.regionNameDropCode)?.codeLevel
        eiaSensArea.save(flush: true, failOnError: true)
    }
    /**
     * 获取环境敏感区详情
     */
    def getEiaSensAreaDataMap(Long eiaSensAreaId, session){
        def dataMap = [:]
        if (eiaSensAreaId) {
            def eiaSensArea = EiaSensArea.findById(eiaSensAreaId)
            dataMap = this.convertToMap(eiaSensArea.properties)
            def regionDomainCode = EiaDomainCode.findByDomainAndCode(GeneConstants.PROVINCE_CITY, eiaSensArea.regionCode)
            if (regionDomainCode) {
                def regionMap = [:]
                regionMap.id = regionDomainCode.code
                regionMap.name = regionDomainCode.codeDesc
                dataMap.remove('regionName')
                dataMap.put('regionName', regionMap)
            }
            def files = []
            def uploadFiles = EiaFileUpload.findAllByTableIdAndTableName(eiaSensAreaId, GeneConstants.EIA_SENS_AREA_TABLE_NAME)
            uploadFiles.each {
                def fileMap = [:]
                fileMap.id = it.id
                fileMap.fileName = it.fileName
                fileMap.fileUrl = it?.filePath + it?.saveFileName
                files << fileMap
            }
            dataMap.put("files", files)
        }
        return dataMap
    }
    /**
     * 环评敏感区删除
     */
    def eiaSensAreaDel(Long eiaSensAreaId){
        def eiaSensArea = EiaSensArea.findById(eiaSensAreaId)
        eiaSensArea.ifDel = true
        eiaSensArea.save(flush: true, failOnError: true)
    }
    /**
     * 环评敏感区列表
     */
    def eiaSensAreaQuery(params){
        def dataType
        def collect
        if (params.searchStringify) {
            def searchStringify = params.searchStringify
            JSONArray array = new JSONArray(searchStringify)
            for (int i=0; i<array.size(); i++) {
                JSONObject obj = array.getJSONObject(i);
                if (obj.dataType) {
                    dataType = obj.dataType
                }
                if (obj.collect) {
                    collect = obj.collect
                }
            }
        }
        int page
        int limit
        if (params.page && params.limit) {
            page = params.int('page') - 1
            limit = params.int('limit')
        }
        def eiaSensAreaList = EiaSensArea.createCriteria().list(max: limit, offset: page * limit) {
            /** 项目名称 */
            if (params.projectName && params.projectName != "项目名称") {
                like('projectName', '%' + params.projectName + '%')
            }
            /** 共享查询——项目名称 */
            if (params.sensAreaSearch && params.sensAreaSearch != "项目名称") {
                like('projectName', '%' + params.sensAreaSearch + '%')
            }
            /** 数据类别 */
            if (dataType) {
                'in' ('dataType', dataType)
            }
            /** 客户所在区域 */
            if (params.regionName) {
                eq('regionName', params.regionName)
            }
            /** 收藏个数 */
            if (collect) {
                eq ("ifFav",true)
            }
            eq("ifDel",false)
            order("id","desc")
        }
        def data = []
        eiaSensAreaList.each {
            def map = [:]
            map.id = it.id
            /**
             * 项目名称
             */
            if (params.sensAreaSearch && params.sensAreaSearch != "项目名称") {
                if (it?.projectName.indexOf(params.sensAreaSearch) != -1) {
                    /** 判断要查询的字符长度 */
                    def projectNameInfo = params.sensAreaSearch
                    int projectNameLength = projectNameInfo.length()
                    /** 查询字符位置开始 */
                    int i = it?.projectName.indexOf(params.sensAreaSearch)
                    /** 截取查询名称 */
                    def queryName = it?.projectName.substring(i, i + projectNameLength)
                    /** 截取查询字段之前的名称 */
                    def queryBeforeName = it?.projectName.substring(0, i)
                    /** 截取查询字段之后的名称 */
                    def queryAfterName = it?.projectName.substring(i + projectNameLength)
                    /** 项目名称 */
                    map.projectName = queryBeforeName + "<font color='red'>" + queryName + "</font>" + queryAfterName
                } else {
                    map.projectName = it?.projectName?:""
                }
            } else {
                map.projectName = it?.projectName?:""
            }
            /** 所属区域 */
            map.regionName = it?.regionName?:""
            /** 数据类别 */
            map.dataType = it?.dataType?:""
            /** 基本情况简介 */
            map.projectMemo = it?.projectMemo?:""
            map.ifFav= it.ifFav
            map.ifPub= it.ifPub
            def fileIdArr
            def eiaFileUploadIds = EiaFileUpload.findAllByTableIdAndTableName(it.id, GeneConstants.EIA_SENS_AREA_TABLE_NAME).id
            def files = []
            if (eiaFileUploadIds) {
                def idStr = StringUtils.strip(eiaFileUploadIds.toString(), "[]")
                fileIdArr = idStr.split(",")
                for (def i : fileIdArr) {
                    def file = EiaFileUpload.findById(i)
                    def fileMap = [:]
                    fileMap.id = file.id
                    fileMap.fileName = file.fileName
                    fileMap.fileUrl = file?.filePath + file?.saveFileName
                    files << fileMap
                }
            }
            map.fileList = files
            data << map
        }
        def dataMap = [:]
        dataMap.data = data
        dataMap.total = eiaSensAreaList.totalCount
        return dataMap
    }
    /**
     * 环境敏感区收藏保存
     */
    def favEiaSensAreaSave(Long eiaSensAreaId, Boolean ifFav){
        def eiaSensArea = EiaSensArea.findById(eiaSensAreaId)
        eiaSensArea.ifFav = ifFav
        eiaSensArea.save(flush: true, failOnError: true)
    }
    /**
     * 区域概况公示发布
     */
    def eiaSensAreaConfirm(Long eiaPubProjectId) {
        def eiaSensArea = EiaSensArea.findById(eiaPubProjectId)
        def responseString = HttpConnectTools.getResponseJson(HttpUrlConstants.GIS_GEO_PUB_QUERY,[tableName:GeneConstants.DOMAIN_EIA_SENS_AREA, tableNameId:String.valueOf(eiaPubProjectId)])
        if(responseString){
            def resMap = JsonHandler.jsonToMap(responseString)
            if(resMap.code == HttpMesConstants.CODE_OK){
                eiaSensArea?.geographicEast  = resMap.data.east
                eiaSensArea?.geographicNorth = resMap.data.north
                eiaSensArea?.geographicStartEast = resMap.data.startEast
                eiaSensArea?.geographicStartNorth = resMap.data.startNorth
                eiaSensArea?.geographicEndEast = resMap.data.EndEast
                eiaSensArea?.geographicEndNorth = resMap.data.EndNorth
            }else{
                return [code:HttpMesConstants.CODE_FAIL,msg:HttpMesConstants.MSG_NO_GIS]
            }
        }else{
            return [code:HttpMesConstants.CODE_FAIL,msg:HttpMesConstants.MSG_NO_GIS]
        }
        eiaSensArea.ifPub = true
        eiaSensArea.save flush:true,failOnError:true
        return [code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SUBMIT_OK]
    }


    /**
     * 共享平台——审批公示详情
     */
    def eiaProjectDetail(Long eiaDataShareId){
        def dataMap = [:]
        def dataSharePlatform = EiaDataShare.findById(eiaDataShareId)
        def project = EiaProject.findByIdAndIfDel(dataSharePlatform?.publicProId,false)
        dataMap.project = project
        /** 浏览次数 */
        def viewsCount = 0
        if (dataSharePlatform?.viewsCount >= 0) {
            viewsCount = dataSharePlatform?.viewsCount + 1
        }
        dataSharePlatform.viewsCount = viewsCount
        dataSharePlatform.save(flash:true,failOnError:true)
        def envProject = EiaEnvProject.findByEiaProjectIdAndIfDel(project?.id,false)
        def eiaFileUpload = EiaFileUpload.findAllByFileTypeAndFileUploadType(project?.id, "fileArc")
        dataMap.eiaFileUpload = eiaFileUpload
        def fileType = project?.fileTypeChild
        dataMap.fileType = fileType
        def natureConstructio = EiaDomainCode.findByDomainAndCode("NATURE_CONSTRUCTION", envProject?.natureConstructio)?.codeDesc
        dataMap.natureConstructio = natureConstructio
        def industryType = EiaDomainCode.findByDomainAndCode("GNS_TYPE_CODE", envProject?.industryType)?.codeDesc
        dataMap.industryType = industryType
        def environmentaType = EiaDomainCode.findByDomainAndCode("INS_TYPE_CODE", envProject?.environmentaType)?.codeDesc
        dataMap.environmentaType = environmentaType
        return dataMap
    }
    /**
     * 共享平台——审批公示查询
     */
    def eiaDataShareQuery(params) {
        def dataSource
        def year
        def nature
        def collect
        if (params.searchStringify) {
            def searchStringify = params.searchStringify
            JSONArray array = new JSONArray(searchStringify)
            for (int i=0; i<array.size(); i++) {
                JSONObject obj = array.getJSONObject(i)
                if (obj.dataSource) {
                    dataSource = obj.dataSource
                }
                if (obj.year) {
                    year = obj.year
                }
                if (obj.nature) {
                    nature = obj.nature
                }
                if (obj.collect) {
                    collect = obj.collect
                }
            }
        }
        /** 联合赤道 */
        int page
        int limit
        if (params.page && params.limit) {
            page = params.int('page') - 1
            limit = params.int('limit')
        }
        def eiaDataShareList = EiaDataShare.createCriteria().list(max: limit, offset: page * limit) {
            /** 项目名称 */
            if (params.dataShareSearchText && params.dataShareSearchText != "项目名称、环境影响评价行业类型、行业类型及代码、生产工艺、产品或功能") {
                or {
                    like('projectName', '%' + params.dataShareSearchText + '%')
                    like('environmentaType', '%' + params.dataShareSearchText + '%')
                    like('industryType', '%' + params.dataShareSearchText + '%')
                    like('productionEngineer', '%' + params.dataShareSearchText + '%')
                    like('productFunction', '%' + params.dataShareSearchText + '%')
                }
            }
            /** 所在区域 */
            if (params.regionName) {
                eq("regionName", params.regionName)
            }
            /** 审批年度 */
            if (year) {
                'in'("publictyYear", year)
            }
            /** 建设性质 */
            if (nature) {
                'in'("natureConstructio", nature)
            }
            /** 收藏个数 */
            if (collect) {
                eq("ifFav", true)
            }
            /** 数据来源 */
            if (dataSource) {
                if (dataSource.contains( GeneConstants.DATA_SOURCE_LH)) {
                    dataSource.remove(GeneConstants.DATA_SOURCE_LH)
                    dataSource.add(GeneConstants.DATA_SOURCE_CHIDAO)
                    dataSource.add(GeneConstants.DATA_SOURCE_TAIZE)
                }
                'in'("dataSources", dataSource)
            }
            eq("ifDel", false)
            order("id", "desc")
        }
        def data = []
        eiaDataShareList.each {
            def map = [:]
            map.id = it.id
            /**
             * 项目名称
             */
            if (params.dataShareSearchText && params.dataShareSearchText != "项目名称、环境影响评价行业类型、行业类型及代码、生产工艺、产品或功能") {
                if (it.projectName.indexOf(params.dataShareSearchText) != -1) {
                    /** 判断要查询的字符长度 */
                    def projectNameInfo = params.dataShareSearchText
                    int projectNameLength = projectNameInfo.length()
                    /** 查询字符位置开始 */
                    int i = it.projectName.indexOf(params.dataShareSearchText)
                    /** 截取查询名称 */
                    def queryName = it.projectName.substring(i, i + projectNameLength)
                    /** 截取查询字段之前的名称 */
                    def queryBeforeName = it.projectName.substring(0, i)
                    /** 截取查询字段之后的名称 */
                    def queryAfterName = it.projectName.substring(i + projectNameLength)
                    /** 项目名称 */
                    map.projectName = queryBeforeName + "<font color='red'>" + queryName + "</font>" + queryAfterName
                } else {
                    map.projectName = it.projectName
                }
                /**
                 * 环境影响评价行业类型
                 */
                if (EiaDataShare.findAllByEnvironmentaTypeLike('%' + params.dataShareSearchText + '%')) {
                    if (it?.environmentaType) {
                        if (it?.environmentaType?.indexOf(params.dataShareSearchText) != -1) {
                            /** 判断要查询的字符长度 */
                            def projectNameInfo = params.dataShareSearchText
                            int projectNameLength = projectNameInfo.length()
                            /** 查询字符位置开始 */
                            int i = it?.environmentaType.indexOf(params.dataShareSearchText)
                            /** 截取查询名称 */
                            def queryName = it?.environmentaType.substring(i, i + projectNameLength)
                            /** 截取查询字段之前的名称 */
                            def queryBeforeName = it?.environmentaType.substring(0, i)
                            /** 截取查询字段之后的名称 */
                            def queryAfterName = it?.environmentaType.substring(i + projectNameLength)
                            /** 项目名称 */
                            map.environmentaType = queryBeforeName + "<font color='red'>" + queryName + "</font>" + queryAfterName
                        } else {
                            map.environmentaType = it?.environmentaType ?: ""
                        }
                    } else {
                        map.environmentaType = it?.environmentaType ?: ""
                    }
                } else {
                    map.environmentaType = it?.environmentaType ?: ""
                }
                /**
                 * 生产工艺
                 */
                if (EiaDataShare.findAllByProductionEngineerLike('%' + params.dataShareSearchText + '%')) {
                    if (it?.productionEngineer) {
                        if (it?.productionEngineer.indexOf(params.dataShareSearchText) != -1) {
                            /** 判断要查询的字符长度 */
                            def projectNameInfo = params.dataShareSearchText
                            int projectNameLength = projectNameInfo.length()
                            /** 查询字符位置开始 */
                            int i = it?.productionEngineer.indexOf(params.dataShareSearchText)
                            /** 截取查询名称 */
                            def queryName = it?.productionEngineer.substring(i, i + projectNameLength)
                            /** 截取查询字段之前的名称 */
                            def queryBeforeName = it?.productionEngineer.substring(0, i)
                            /** 截取查询字段之后的名称 */
                            def queryAfterName = it?.productionEngineer.substring(i + projectNameLength)
                            /** 项目名称 */
                            map.productionEngineer = queryBeforeName + "<font color='red'>" + queryName + "</font>" + queryAfterName
                        } else {
                            map.productionEngineer = it?.productionEngineer ?: ""
                        }
                    } else {
                        map.productionEngineer = it?.productionEngineer ?: ""
                    }
                } else {
                    map.productionEngineer = it?.productionEngineer ?: ""
                }
                /**
                 * 产品或功能
                 */
                if (EiaDataShare.findAllByProductFunctionLike('%' + params.dataShareSearchText + '%')) {
                    if (it?.productFunction) {
                        if (it?.productFunction.indexOf(params.dataShareSearchText) != -1) {
                            /** 判断要查询的字符长度 */
                            def projectNameInfo = params.dataShareSearchText
                            int projectNameLength = projectNameInfo.length()
                            /** 查询字符位置开始 */
                            int i = it?.productFunction.indexOf(params.dataShareSearchText)
                            /** 截取查询名称 */
                            def queryName = it?.productFunction.substring(i, i + projectNameLength)
                            /** 截取查询字段之前的名称 */
                            def queryBeforeName = it?.productFunction.substring(0, i)
                            /** 截取查询字段之后的名称 */
                            def queryAfterName = it?.productFunction.substring(i + projectNameLength)
                            /** 项目名称 */
                            map.productFunction = queryBeforeName + "<font color='red'>" + queryName + "</font>" + queryAfterName
                        } else {
                            map.productFunction = it?.productFunction ?: ""
                        }
                    } else {
                        map.productFunction = it?.productFunction ?: ""
                    }
                } else {
                    map.productFunction = it?.productFunction ?: ""
                }
                /**
                 * 行业类型及代码
                 */
                if (EiaDataShare.findAllByIndustryTypeLike('%' + params.dataShareSearchText + '%')) {
                    if (it?.industryType) {
                        if (it?.industryType.indexOf(params.dataShareSearchText) != -1) {
                            /** 判断要查询的字符长度 */
                            def projectNameInfo = params.dataShareSearchText
                            int projectNameLength = projectNameInfo.length()
                            /** 查询字符位置开始 */
                            int i = it?.industryType.indexOf(params.dataShareSearchText)
                            /** 截取查询名称 */
                            def queryName = it?.industryType.substring(i, i + projectNameLength)
                            /** 截取查询字段之前的名称 */
                            def queryBeforeName = it?.industryType.substring(0, i)
                            /** 截取查询字段之后的名称 */
                            def queryAfterName = it?.industryType.substring(i + projectNameLength)
                            /** 项目名称 */
                            map.industryType = queryBeforeName + "<font color='red'>" + queryName + "</font>" + queryAfterName
                        } else {
                            map.industryType = it?.industryType ?: ""
                        }
                    } else {
                        map.industryType = it?.industryType ?: ""
                    }
                } else {
                    map.industryType = it?.industryType ?: ""
                }
            } else {
                map.projectName = it.projectName ?: ""
                map.productionEngineer = it?.productionEngineer ?: ""
                map.productFunction = it?.productFunction ?: ""
                /**
                 *  行业类型及代码
                 */
                if (it.dataSources == '联合赤道' || it.dataSources == '联合泰泽') {
                    if (it.environmentaType) {
                        def environmentaType = EiaDomainCode.findByDomainAndCode(GeneConstants.INS_TYPE_CODE, it.environmentaType)
                        map.environmentaType = environmentaType.codeRemark + " " + environmentaType.codeDesc
                    } else {
                        map.environmentaType = ""
                    }
                    if (it.environmentaType) {
                        def industryType = EiaDomainCode.findByDomainAndCode(GeneConstants.GNS_TYPE_CODE, it.environmentaType)
                        if (industryType) {
                            map.industryType = industryType?.codeRemark + " " + industryType?.codeDesc
                        } else {
                            map.industryType = ""
                        }
                    } else {
                        map.industryType = ""
                    }
                } else {
                    map.environmentaType = it?.environmentaType ?: ""
                    map.industryType = it?.industryType ?: ""
                }
            }
            /** 录入人 */
            map.inputUser = it.oneEntry ?: ""
            /** 项目负责人 */
            map.projectLeader = it.projectLeader ?: ""
            /** 建设性质 */
            map.natureConstructio = it?.natureConstructio ?: ""
            /** 审批时间 */
            map.approveDate = it?.approveDate?.format("yyyy-MM-dd") ?: ""
            /** 审批文号 */
            map.approveDept = it?.approveDept ?: ""
            /** 审批部门 */
            map.approveNo = it?.approveNo ?: ""
            /** 浏览次数 */
            map.viewsCount = it?.viewsCount ?: 0
            /** 下载次数 */
            map.downloadsCount = it?.downloadsCount ?: 0
            /** 环评单位 */
            map.eiaUnit = it?.eiaUnit ?: ""
            /** 建设单位 */
            map.developmentOrg = it?.developmentOrg ?: ""
            /** 审批年度 */
            map.publictyYear = it?.publictyYear ?: ""
            /** 数据来源 */
            if (params.dataSourceText) {
                map.dataSource = params.dataSourceText
            } else {
                map.dataSource = it.dataSources
            }
            map.ifFav = it.ifFav
            if(it.dataSources == '审批公示' || it.dataSources == '验收公示') {
                def pubProject = EiaPubProject.findById(EiaDataShare.findById(it.id)?.publicProId)
                def fileIdArr
                def spiderUrlStr
                def spiderFileUrlStr
                def tableId = it.publicProId ?: Long.valueOf(-1)
                def eiaFileUploadIds = EiaFileUpload.findAllByTableIdAndTableName(tableId, GeneConstants.EIA_PUB_PROJECT_TABLE_NAME).id
                def files = []
                if (eiaFileUploadIds) {
                    def idStr = StringUtils.strip(eiaFileUploadIds.toString(), "[]")
                    fileIdArr = idStr.split(",")
                    for (def i : fileIdArr) {
                        def file = EiaFileUpload.findById(i)
                        def fileMap = [:]
                        fileMap.id = file.id
                        fileMap.fileName = file.fileName
                        fileMap.fileUrl = file?.filePath + file?.saveFileName
                        files << fileMap
                    }
                }
                if (pubProject?.spiderFileDownloadUrl) {
                    spiderFileUrlStr = pubProject.spiderFileDownloadUrl
                }
                map.spiderFileUrl = spiderFileUrlStr
                if (pubProject?.spiderFileUrl) {
                    spiderUrlStr = pubProject.spiderFileUrl
                }
                map.spiderUrl = spiderUrlStr
                map.fileList = files
            } else {
                def fileIdArr
                def tableId = it.publicProId
                def eiaFileUploadIds = EiaFileUpload.findAllByTableIdAndTableName(tableId, GeneConstants.DOMAIN_EIA_PROJECT).id
                def files = []
                if (eiaFileUploadIds) {
                    def idStr = StringUtils.strip(eiaFileUploadIds.toString(), "[]")
                    fileIdArr = idStr.split(",")
                    for (def i : fileIdArr) {
                        def file = EiaFileUpload.findById(i)
                        def fileMap = [:]
                        fileMap.id = file.id
                        fileMap.fileName = file.fileName
                        fileMap.fileUrl = file?.filePath + file?.saveFileName
                        files << fileMap
                    }
                }
                map.fileList = files
            }
            data << map
        }
        def dataMap = [:]
        dataMap.data = data
        dataMap.total = eiaDataShareList.totalCount
        return dataMap
    }
    /**
     * 共享平台——审批公示详情（联合泰泽、联合赤道）
     */
    def platFormEiaDataShareDataMap(Long eiaDataShareId) {
        def dataMap = [:]
        def dataShare = EiaDataShare.findById(eiaDataShareId)

        def eiaProject = EiaProject.findByIdAndIfDel(dataShare?.publicProId,false)
        dataMap = this.convertToMap(eiaProject.properties)
        dataMap.putAll(dataShare.properties)
        /**
         * 浏览次数
         */
        def viewsCount = 0
        if(dataShare?.viewsCount>=0){
            viewsCount = dataShare?.viewsCount + 1
        }
        dataShare.viewsCount = viewsCount
        dataShare.save(flash:true,failOnError:true)
        def files = []
        def uploadFiles = EiaFileUpload.findAllByTableIdAndTableName(eiaProject.id, GeneConstants.DOMAIN_EIA_PROJECT)
        uploadFiles.each {
            def fileMap = [:]
            fileMap.id = it.id
            fileMap.fileName = it.fileName
            fileMap.fileUrl = it?.filePath + it?.saveFileName
            files << fileMap
        }
        dataMap.put("files", files)
        def envProject = EiaEnvProject.findByEiaProjectIdAndIfDel(eiaProject?.id,false)
        if (envProject) {
            dataMap.putAll(envProject.properties)
        }
        def greenProject = EiaGreenProject.findByEiaProjectIdAndIfDel(eiaProject?.id,false)
        dataMap.ifIndPolicy = greenProject?.ifIndPolicy
        def fileType = eiaProject?.fileTypeChild
        dataMap.fileType = fileType
        if (dataShare?.approveDate) {
            dataMap.approveDate = dataShare?.approveDate.format("yyyy-MM-dd")
        }
        return dataMap
    }


    /**
     * 共享平台——审批公示详情（联合泰泽、联合赤道）
     */
    def platFormEiaProjectDataMap(Long eiaProjectId) {
        def dataMap = [:]
        def eiaProject = EiaProject.findByIdAndIfDel(eiaProjectId,false)
        dataMap = this.convertToMap(eiaProject.properties)

        def files = []
        def uploadFiles = EiaFileUpload.findAllByTableIdAndTableNameAndFileUploadTypeInList(eiaProject.id, GeneConstants.DOMAIN_EIA_PROJECT,[GeneConstants.SEARCH_ZG,GeneConstants.SEARCH_PFHBA,GeneConstants.SEARCH_XMGDFJ])
        uploadFiles.each {
            def fileMap = [:]
            fileMap.id = it.id
            fileMap.fileName = it.fileName
            fileMap.fileUrl = it?.filePath + it?.saveFileName
            files << fileMap
        }
        dataMap.put("files", files)
        def envProject = EiaEnvProject.findByIdAndIfDel(eiaProject?.id,false)
        def fileType = eiaProject?.fileTypeChild
        if(envProject){
            def natureConstructio = EiaDomainCode.findByDomainAndCode(GeneConstants.NATURE_CONSTRUCTION, envProject?.natureConstructio)
            def industryType = EiaDomainCode.findByDomainAndCode(GeneConstants.GNS_TYPE_CODE, envProject?.industryType)
            def environmentaType = EiaDomainCode.findByDomainAndCode(GeneConstants.INS_TYPE_CODE, envProject?.environmentaType)
            dataMap.envProject = envProject
            dataMap.natureConstructio = natureConstructio
            dataMap.industryType = industryType
            dataMap.environmentaType = environmentaType
        }
        dataMap.fileType = fileType
        return dataMap
    }
    /**
     * 共享平台——审批公示统计数据
     */
    def getEiaDataShareData(){
        def dataMap = [:]
        /**
         * 数据来源list
         */
        def projectEquatorList = []
        /**
         *审批年度
         */
        def projectArchiveList = []
        /**
         *  建设性质
         */
        def envProjectList = []
        /**
         * 赤道环评项目数（只显示内部存档的项目）
         */
        def projectEquatorCount
        /**
         * 联合泰泽
         */
        def projectTatizeCount
        /**
         * 审批公示项目数
         */
        def projectApproveCount
        /**
         * 验收公示项目数
         */
        def projectAcceptCount
        /**
         * 建设性质:新建（迁建）
         */
        def  envProjectXj
        /**
         * 数据来源数
         */
        projectEquatorCount = EiaDataShare.countByIfDelAndDataSourcesInList(false, [GeneConstants.DATA_SOURCE_CHIDAO, GeneConstants.DATA_SOURCE_TAIZE])
        projectApproveCount = EiaDataShare.countByIfDelAndDataSources(false,GeneConstants.DATA_SOURCE_SHENPI)
        projectAcceptCount = EiaDataShare.countByIfDelAndDataSources(false,GeneConstants.DATA_SOURCE_YANSHOU)
        def lhSource = [:]
        lhSource.name = GeneConstants.DATA_SOURCE_LH
        lhSource.value = projectEquatorCount
        projectEquatorList << lhSource
        def shenPiSource = [:]
        shenPiSource.name = GeneConstants.DATA_SOURCE_SHENPI
        shenPiSource.value = projectApproveCount
        projectEquatorList << shenPiSource
        def yanShouSource = [:]
        yanShouSource.name = GeneConstants.DATA_SOURCE_YANSHOU
        yanShouSource.value = projectAcceptCount
        projectEquatorList << yanShouSource
        /**
         * 审批年度
         */
        def domainCodeProjectArchive = EiaDomainCode.findAllByDomain(GeneConstants.PUBLICTY_YEAR)
        domainCodeProjectArchive.each{
            def map = [:]
            def  projectSize= EiaDataShare.countByIfDelAndPublictyYear(false,it.code)
            map.name = it.codeDesc
            map.value = projectSize
            projectArchiveList << map
        }
        /**
         * 建设性质
         */
        def domainCode = EiaDomainCode.findAllByDomain(GeneConstants.NATURE_CONSTRUCTION)
        domainCode.each {
            def map = [:]
            envProjectXj = EiaDataShare.countByNatureConstructioAndIfDel(it.codeDesc, false)
            map.name = it.codeDesc
            map.value = envProjectXj
            envProjectList << map
        }
        /**
         * 收藏
         */
        def ifFavList = []
        def ifFavListSize = EiaDataShare.countByIfDelAndIfFav(false,true)
        def ifFavMap = [:]
        ifFavMap.name = "收藏个数"
        ifFavMap.value = ifFavListSize
        ifFavList << ifFavMap

        dataMap.projectEquatorList = projectEquatorList
        dataMap.projectArchiveList = projectArchiveList
        dataMap.envProjectList = envProjectList
        dataMap.ifFavList = ifFavList
        return dataMap
    }
    /**
     * 共享平台——审批公示收藏保存
     */
    def favEiaDataShareSave(Long eiaDataShareId, Boolean ifFav){
        def eiaDataShare = EiaDataShare.findById(eiaDataShareId)
        eiaDataShare.ifFav = ifFav
        eiaDataShare.save(flush: true, failOnError: true)
    }
    /**
     * 共享平台——规划公示统计数据
     */
    def getEiaPlanShowData() {
        def list = []
        def map = [:]
        def ifFavCount = EiaPlanShow.countByIfFav(true)
        map.name = "收藏个数"
        map.value = ifFavCount
        list << map
        return list
    }
    /**
     * 共享平台——区域概况统计数据
     */
    def getEiaAreaInfoData() {
        def dataTypeList = []
        def eiaDomainCodeList = EiaDomainCode.findAllByDomain(GeneConstants.DATA_CURRENCY)
        if (eiaDomainCodeList) {
            eiaDomainCodeList.each {
                def dataTypeMap = [:]
                def infoCount = EiaAreaInfo.countByDataTypeAndIfDel(it.code, false)
                dataTypeMap.name = it.codeDesc
                dataTypeMap.value = infoCount
                dataTypeList << dataTypeMap
            }
        }
        def collectList = []
        def collectMap = [:]
        def ifFavCount = EiaAreaInfo.countByIfFav(true)
        collectMap.name = "收藏个数"
        collectMap.value = ifFavCount
        collectList << collectMap
        def dataMap = [:]
        dataMap.collectList = collectList
        dataMap.dataTypeList = dataTypeList
        return dataMap
    }
    /**
     * 共享平台——环境敏感区统计数据
     */
    def getEiaSensAreaData() {
        def dataTypeList = []
        def eiaDomainCodeList = EiaDomainCode.findAllByDomain(GeneConstants.SENSITIVE_AREA_TYPE)
        if (eiaDomainCodeList) {
            eiaDomainCodeList.each {
                def dataTypeMap = [:]
                def infoCount = EiaSensArea.countByDataTypeAndIfDel(it.code, false)
                dataTypeMap.name = it.codeDesc
                dataTypeMap.value = infoCount
                dataTypeList << dataTypeMap
            }
        }
        def collectList = []
        def collectMap = [:]
        def ifFavCount = EiaSensArea.countByIfFav(true)
        collectMap.name = "收藏个数"
        collectMap.value = ifFavCount
        collectList << collectMap
        def dataMap = [:]
        dataMap.collectList = collectList
        dataMap.dataTypeList = dataTypeList
        return dataMap
    }
    /**
     * 共享平台下载次数更新
     */
    def updateDataShareDownCount(Long fileId) {
        def eiaFileUpload = EiaFileUpload.findById(fileId)
        def tableId = eiaFileUpload.tableId
        def tableName = eiaFileUpload.tableName
        if (tableName == GeneConstants.EIA_PUB_PROJECT_TABLE_NAME || tableName == GeneConstants.DOMAIN_EIA_PROJECT) {
            def eiaDataShare = EiaDataShare.findByPublicProId(tableId)
            def downloadsCount = eiaDataShare.downloadsCount ?:0
            downloadsCount += 1
            eiaDataShare.downloadsCount = downloadsCount
            eiaDataShare.save(flash: true, failOnError: true)
        }
        return true
    }
    /**
     * 项目归档后，存入共享平台
     */
    def dataShareSaveEiaProject(Long eiaProjectId) {
        def eiaProject = EiaProject.findById(eiaProjectId)
        if (eiaProject) {
            def eiaDataShare = new EiaDataShare()
            eiaDataShare.publicProId = eiaProjectId
            eiaDataShare.dataSources = EiaContract.findById(eiaProject.eiaContractId)?.contractTrust
            Calendar c = Calendar.getInstance()
            def year
            def projectPlan = EiaProjectPlan.findByEiaProjectIdAndIfDel(eiaProjectId, false)
            def arcDate = EiaProjectPlanItem.findByEiaProjectPlanIdAndNodesCode(projectPlan?.id, WorkFlowConstants.NODE_CODE_XMGD)?.actEndDate
            if (arcDate) {
                c.setTime(arcDate)
                year = c.get(Calendar.YEAR)
            }
            eiaDataShare.approveDate = arcDate
            eiaDataShare.publictyYear = year
            def envProject = EiaEnvProject.findByEiaProjectIdAndIfDel(eiaProjectId, false)
            eiaDataShare.approveDept = envProject?.seaReviewOrg
            eiaDataShare.approveNo = envProject?.seaReviewNo
            eiaDataShare.projectName = eiaProject?.projectName
            eiaDataShare.natureConstructio = envProject?.natureConstructio
            if (eiaProject?.dutyUserId) {
                eiaDataShare.projectLeaderId = Long.parseLong(eiaProject?.dutyUserId)
            }
            eiaDataShare.projectLeader = eiaProject?.dutyUser
            def eiaTask = EiaTask.findById(eiaProject?.eiaTaskId)
            eiaDataShare.projectLeaderDeptId = eiaTask?.taskLeaderDeptId
            eiaDataShare.projectLeaderDept = eiaTask?.taskLeaderDept
            eiaDataShare.oneEntry = eiaProject?.inputUser
            eiaDataShare.environmentaType = envProject?.environmentaTypeCode
            eiaDataShare.industryType = envProject?.industryTypeCode
            eiaDataShare.productionEngineer = envProject?.productionEngineer
            eiaDataShare.productFunction = envProject?.productFunction
            eiaDataShare.projectMemo = envProject?.projectMemo
            eiaDataShare.inputUserId = eiaProject?.inputUserId
            eiaDataShare.inputUser = eiaProject?.inputUser
            eiaDataShare.inputDeptId = eiaProject?.inputDeptId
            eiaDataShare.inputDept = eiaProject?.inputDept
            eiaDataShare.inputDeptCode = eiaProject?.inputDeptCode
            eiaDataShare.save(flush:true,failOnError:true)
        }
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
}
