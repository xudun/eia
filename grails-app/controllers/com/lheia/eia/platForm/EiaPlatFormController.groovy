package com.lheia.eia.platForm

import com.lheia.eia.common.GeneConstants
import com.lheia.eia.common.HttpMesConstants
import com.lheia.eia.config.EiaDomainCode
import grails.converters.JSON

class EiaPlatFormController {

    def eiaPlatFormService
    def eiaFileUploadService

    /**
     * 审批公示列表页
     */
    def eiaPubProjectIndex() {}
    /**
     * 审批公示编辑页
     */
    def eiaPubProjectEdit() {}
    /**
     * 审批公示详情页
     */
    def eiaPubProjectDetail() {}
    /**
     * 规划公示列表页
     */
    def eiaPlanShowIndex() {}
    /**
     * 规划公示编辑页
     */
    def eiaPlanShowEdit() {}
    /**
     * 规划公示详情页
     */
    def eiaPlanShowDetail() {}
    /**
     * 区域概况列表页
     */
    def eiaAreaInfoIndex() {}
    /**
     * 区域概况新增/编辑页
     */
    def eiaAreaInfoCreate() {}
    /**
     * 区域概况详情页
     */
    def eiaAreaInfoDetail() {}
    /**
     * 环境敏感区列表页
     */
    def eiaSensAreaIndex() {}
    /**
     * 环境敏感区新增/编辑页
     */
    def eiaSensAreaCreate() {}
    /**
     * 环境敏感区详情页
     */
    def eiaSensAreaDetail() {}
    /**
     * 共享查询列表页
     */
    def eiaDataShareIndex() {}
    /**
     * 共享查询——环评项目详情页
     */
    def eiaDataShareDetail() {}

    /**
     * 环评审批保存
     * @return
     */
    def eiaPubProjectSave() {
        if (!params.long("eiaPubProjectId")) {
            def dataMap = eiaPlatFormService.eiaPubProjectSave(params, session)
            if (dataMap) {
                render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SAVE_OK, data: dataMap] as JSON)
            } else {
                render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
            }
        } else {
            def dataMap = eiaPlatFormService.eiaPubProjectUpdate(params)
            if (dataMap) {
                render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SAVE_OK, data: dataMap] as JSON)
            } else {
                render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
            }
        }
    }
    /**
     * 环评审批公示详情
     * @return
     */
    def getEiaPubProjectDataMap() {
        def dataMap = eiaPlatFormService.getEiaPubProjectDataMap(params.long('eiaPubProjectId'), params.long('eiaDataShareId'), session)
        if (dataMap) {
            def pubDate = dataMap.pubDate
            if (pubDate) {
                pubDate = pubDate.format("yyyy-MM-dd")
            }
            render([code: HttpMesConstants.CODE_OK, data: dataMap, pubDate: pubDate] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
    /**
     * 环评审批公示删除
     * @return
     */
    def eiaPubProjectDel() {
        if (eiaPlatFormService.eiaPubProjectDel(params.long('eiaPubProjectId'))) {
            render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_DEL_OK] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DEL_FAIL] as JSON)
        }
    }
    /**
     * 环评审批公示列表
     * @return
     */
    def eiaPubProjectQuery() {
        def dataMap = eiaPlatFormService.eiaPubProjectQuery(params)
        if (dataMap) {
            render([code: HttpMesConstants.CODE_OK, count: dataMap.total, data: dataMap.data] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
    /**
     * 审批公示发布
     * @return
     */
    def eiaPubProjectConfirm(){
        long eiaPubProjectId = Long.valueOf(params.eiaPubProjectId)
        def dataMap = eiaPlatFormService.eiaPubProjectConfirm(params.long('eiaPubProjectId'))
        if (dataMap) {
            render(dataMap as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SUBMIT_FAIL] as JSON)
        }
    }
    /**
     * 规划公示编辑
     * @return
     */
    def eiaPlanShowSave() {
        def dataMap = eiaPlatFormService.eiaPlanShowUpdate(params)
        if (dataMap) {
            render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SAVE_OK, data: dataMap] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
        }
    }
    /**
     * 规划公示详情
     * @return
     */
    def getEiaPlanShowDataMap() {
        def dataMap = eiaPlatFormService.getEiaPlanShowDataMap(params.long('eiaPlanShowId'))
        if (dataMap) {
            render([code: HttpMesConstants.CODE_OK, eiaPlanShow: dataMap.eiaPlanShow, pubDate: dataMap.pubDate, pictureUrls: dataMap.pictureUrls, planPictures: dataMap.planPictures] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
    /**
     * 规划公示删除
     * @return
     */
    def eiaPlanShowDel() {
        def dataMap = eiaPlatFormService.eiaPlanShowDel(params.long('eiaPlanShowId'))
        if (dataMap) {
            render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_DEL_OK] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DEL_FAIL] as JSON)
        }
    }
    /**
     * 规划公示列表查询
     * @return
     */
    def eiaPlanShowQuery() {
        def dataMap = eiaPlatFormService.eiaPlanShowQuery(params)
        if (dataMap) {
            render([code: HttpMesConstants.CODE_OK, count: dataMap.total, data: dataMap.data] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
    /**
     * 规划公示收藏保存
     * @return
     */
    def favEiaPlanShowSave() {
        def dataMap = eiaPlatFormService.favEiaPlanShowSave(params.long('eiaPlanShowId'), params.boolean('ifFav'))
        if (dataMap) {
            render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SAVE_OK] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
        }
    }
    /**
     * 规划公示发布
     * @return
     */
    def eiaPlanShowConfirm() {
        def dataMap = eiaPlatFormService.eiaPlanShowConfirm(params.long('eiaPlanShowId'))
        if (dataMap) {
            render(dataMap as JSON)
        }  else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SUBMIT_FAIL] as JSON)
        }
    }
    /**
     * 区域保存
     * @return
     */
    def eiaAreaInfoSave() {
        if (!params.long("eiaAreaInfoId")) {
            def dataMap = eiaPlatFormService.eiaAreaInfoSave(params, session)
            if (dataMap) {
                render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SAVE_OK, data: dataMap] as JSON)
            } else {
                render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
            }
        } else {
            def dataMap = eiaPlatFormService.eiaAreaInfoUpdate(params)
            if (dataMap) {
                render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SAVE_OK, data: dataMap] as JSON)
            } else {
                render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
            }
        }
    }
    /**
     * 获取不同数据类别下的数据项
     * @return
     */
    def getEiaAreaInfoByDataType() {
        def dataMap = eiaPlatFormService.getEiaAreaInfoByDataType(params)
        if (dataMap) {
            render([code: HttpMesConstants.CODE_OK, data: dataMap] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
    /**
     * 区域划分详情
     * @return
     */
    def getEiaAreaInfoDataMap() {
        def dataMap = eiaPlatFormService.getEiaAreaInfoDataMap(params.long('eiaAreaInfoId'), session)
        if (dataMap) {
            render([code: HttpMesConstants.CODE_OK, data: dataMap] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
    /**
     * 区域概况删除
     * @return
     */
    def eiaAreaInfoDel() {
        def dataMap = eiaPlatFormService.eiaAreaInfoDel(params.long('eiaAreaInfoId'))
        if (dataMap) {
            render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_DEL_OK] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DEL_FAIL] as JSON)
        }
    }
    /**
     * 区域列表
     * @return
     */
    def eiaAreaInfoQuery() {
        def dataMap = eiaPlatFormService.eiaAreaInfoQuery(params)
        if (dataMap) {
            render([code: HttpMesConstants.CODE_OK, count: dataMap.total, data: dataMap.data, dataCurrencyList: dataMap.dataCurrencyList, ifFavListSize: dataMap.ifFavListSize] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
    /**
     * 区域收藏保存
     * @return
     */
    def favEiaAreaInfoSave() {
        if (eiaPlatFormService.favEiaAreaInfoSave(params.long('eiaAreaInfoId'), params.boolean('ifFav'))) {
            render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SAVE_OK] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
        }
    }

    /**
     * 审批公示发布
     * @return
     */
    def eiaAreaInfoConfirm(){
        long eiaPubProjectId = Long.valueOf(params.eiaPlanShowId)
        def dataMap = eiaPlatFormService.eiaAreaInfoConfirm(eiaPubProjectId)
        if (dataMap) {
            render(dataMap as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SUBMIT_FAIL] as JSON)
        }
    }
    /**
     * 环评敏感区保存
     * @return
     */
    def eiaSensAreaSave() {
        if (!params.long("eiaSensAreaId")) {
            def dataMap = eiaPlatFormService.eiaSensAreaSave(params, session)
            if (dataMap) {
                render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SAVE_OK, data: dataMap] as JSON)
            } else {
                render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
            }
        } else {
            def dataMap = eiaPlatFormService.eiaSensAreaUpdate(params)
            if (dataMap) {
                render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SAVE_OK, data: dataMap] as JSON)
            } else {
                render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
            }
        }
    }
    /**
     * 获取环境敏感区详情
     * @return
     */
    def getEiaSensAreaDataMap(){
        def dataMap = eiaPlatFormService.getEiaSensAreaDataMap(params.long("eiaSensAreaId"), session)
        if (dataMap) {
            render([code: HttpMesConstants.CODE_OK, data: dataMap] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
    /**
     * 环评敏感区删除
     * @return
     */
    def eiaSensAreaDel(){
        if (eiaPlatFormService.eiaSensAreaDel(params.long('eiaSensAreaId'))) {
            render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_DEL_OK] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DEL_FAIL] as JSON)
        }
    }
    /**
     * 环评敏感区列表
     * @return
     */
    def eiaSensAreaQuery(){
        def dataMap = eiaPlatFormService.eiaSensAreaQuery(params)
        if (dataMap) {
            render([code: HttpMesConstants.CODE_OK, count: dataMap.total, data: dataMap.data] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
    /**
     * 环境敏感区收藏保存
     * @return
     */
    def favEiaSensAreaSave(){
        def dataMap = eiaPlatFormService.favEiaSensAreaSave(params.long('eiaSensAreaId'), params.boolean('ifFav'))
        if (dataMap) {
            render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SAVE_OK] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
        }
    }
    /**
     * 审批公示发布
     * @return
     */
    def eiaSensAreaConfirm(){
        long eiaPubProjectId = Long.valueOf(params.eiaSensAreaId)
        def dataMap = eiaPlatFormService.eiaSensAreaConfirm(eiaPubProjectId)
        if (dataMap) {
            render(dataMap as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SUBMIT_FAIL] as JSON)
        }
    }
    /**
     * 共享平台——审批公示统计数据
     * @return
     */
    def getEiaDataShareData(){
        def dataMap = eiaPlatFormService.getEiaDataShareData()
        if (dataMap) {
            render([code: HttpMesConstants.CODE_OK, dataSource: dataMap.projectEquatorList, nature: dataMap.envProjectList, year: dataMap.projectArchiveList, collect: dataMap.ifFavList] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
    /**
     * 共享平台——审批公示查询
     * @return
     */
    def eiaDataShareQuery() {
        def dataMap = eiaPlatFormService.eiaDataShareQuery(params)
        if (dataMap) {
            render([code: HttpMesConstants.CODE_OK, count: dataMap.total, data: dataMap.data] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
    /**
     * 共享平台——审批公示详情（联合泰泽、联合赤道）
     * @return
     */
    def platFormEiaDataShareDataMap() {
        def type = params.type
        def dataMap
        if(type == GeneConstants.DOMAIN_EIA_PROJECT){
            dataMap = eiaPlatFormService.platFormEiaProjectDataMap(params.long('eiaDataShareId'))
        }else{
            dataMap = eiaPlatFormService.platFormEiaDataShareDataMap(params.long('eiaDataShareId'))
        }
        if (dataMap) {
            render([code: HttpMesConstants.CODE_OK, data: dataMap] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
    /**
     * 共享平台——审批公示收藏保存
     * @return
     */
    def favEiaDataShareSave(){
        def dataMap = eiaPlatFormService.favEiaDataShareSave(params.long('eiaDataShareId'), params.boolean('ifFav'))
        if (dataMap) {
            render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SAVE_OK] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
        }
    }
    /**
     * 共享平台——规划公示统计数据
     * @return
     */
    def getEiaPlanShowData() {
        def dataMap = eiaPlatFormService.getEiaPlanShowData()
        if (dataMap) {
            render([code: HttpMesConstants.CODE_OK, collect: dataMap] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
    /**
     * 共享平台——规划公示查询
     * @return
     */
    def platFormEiaPlanShowQuery() {
        def dataMap = eiaPlatFormService.platFormEiaPlanShowQuery(params)
        if (dataMap) {
            render([code: HttpMesConstants.CODE_OK, count: dataMap.total, data: dataMap.data] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
    /**
     * 共享平台——区域概况统计数据
     * @return
     */
    def getEiaAreaInfoData() {
        def dataMap = eiaPlatFormService.getEiaAreaInfoData()
        if (dataMap) {
            render([code: HttpMesConstants.CODE_OK, dataType: dataMap.dataTypeList, collect: dataMap.collectList] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
    /**
     * 共享平台——环境敏感区统计数据
     * @return
     */
    def getEiaSensAreaData() {
        def dataMap = eiaPlatFormService.getEiaSensAreaData()
        if (dataMap) {
            render([code: HttpMesConstants.CODE_OK, dataType: dataMap.dataTypeList, collect: dataMap.collectList] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
    /**
     * 共享平台——审批公示详情
     * @return
     */
    def eiaProjectDetail(){
        def dataMap = eiaPlatFormService.eiaProjectDetail(params.long('eiaDataShareId'))
        if (dataMap) {
            render([code: HttpMesConstants.CODE_OK, data: dataMap] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
    /**
     * 共享平台——附件下载
     * @return
     */
    def downloadFile() {
        def fileId = params.long('fileId')
        if (fileId) {
            /**
             * 判断IP
             * */
            String ip = request.getHeader("X-Forwarder-For")
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP")
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP")
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_REAL_IP")
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr()
            }
            /**
             * 内网ip：172.16；192.168 本机ip：0:0:0:0:0:0:0:1 如果通过反向代理登录(一般为外网访问),则禁止下载，否则可以下载
             */
            def domainCode = EiaDomainCode.findAllByDomain(GeneConstants.FILE_DOWN_IPACCESS)?.code
            if (ip.contains("172.16") || ip.contains("0:0:0:0:0:0:0:1") || domainCode.contains(ip)) {
                if (eiaPlatFormService.updateDataShareDownCount(fileId)) {
                    eiaFileUploadService.download(request, response, fileId)
                }
            } else {
                render (view:"../ipError.gsp")
                return
            }
        }
    }
}
