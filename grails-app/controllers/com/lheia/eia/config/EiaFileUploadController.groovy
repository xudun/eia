package com.lheia.eia.config

import com.lheia.eia.common.HttpMesConstants
import com.lheia.eia.project.EiaProjectPlan
import com.lheia.eia.project.EiaProjectPlanItem
import grails.converters.JSON

class EiaFileUploadController {
    def eiaFileUploadService
    /**
     *文件列表
     */
    def eiaFileUploadIndex() {
        def eiaProjectPlanItem
        def eiaProjectPlan = EiaProjectPlan.findByIfDelAndEiaProjectId(false,params.long("eiaProjectId"))
        def itemMap = [:]
        if(eiaProjectPlan){
            eiaProjectPlanItem = EiaProjectPlanItem.findAllByIfDelAndEiaProjectPlanId(false,eiaProjectPlan?.id)
            eiaProjectPlanItem.each {
                itemMap.put(it.nodesCode,it.nodesName)
            }
        }
        if(params.ifModi=="1"){//不能删除
            if (params.fileUploadType) {
                [fileUploadType: params.fileUploadType, ifModi:false,itemMap:itemMap]
            } else {
                [ifModi:false,itemMap:itemMap]
            }
        } else {
            [ifModi:true,itemMap:itemMap]
        }
    }
    /**
     * 文件上传
     */
    def eiaFileUploadCreate(){}
    /**
     * 文件上传，没有文件类型
     */
    def eiaFileUploadCreateNoType(){}
    /**
     * 查看详情
     */
    def eiaFileUploadDetail(){}
    /***
     * 列表查看页
     */
    def eiaFileUploadShow(){}
    /***
     * 上传文件表单
     * @return
     */
    def eiaFileUploadSave(){
        def eiaFileUpload = eiaFileUploadService.upload(request, session, params.tableName, Long.valueOf(params.tableId), params.fileUploadType, params.fileNote)
        if (eiaFileUpload){
            render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_UPLOAD_OK] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_UPLOAD_FAIL] as JSON)
        }
    }
    /***
     * 根据tableName获取文件类型
     * @param tableName
     */
    def getFileTypeList(){
        def codeList = eiaFileUploadService.getFileTypeList(params.tableName)
        if(codeList&&codeList.size()>0){
            render([code: HttpMesConstants.CODE_OK,data: codeList] as JSON)
        }else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }

    }
    /***
     * 删除
     * @return
     */
    def eiaFileUploadDelete() {
        Long eiaFileUploadId = Long.valueOf(params.eiaFileUploadId)
        def eiaFileUpload = eiaFileUploadService.delete(eiaFileUploadId)
        if (eiaFileUpload) {
            render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_DEL_OK] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DEL_FAIL] as JSON)
        }
    }

    /***
     * 下载
     */
    def eiaFileUploadDownload() {
        Long eiaFileUploadId = Long.valueOf(params.eiaFileUploadId)
        eiaFileUploadService.download(request, response, eiaFileUploadId)
    }
    /**
     *文件预览
     */
    def viewCreditReport (){
        Long eiaFileUploadId = Long.valueOf(params.eiaFileUploadId)
        eiaFileUploadService.downloadView(request, response, eiaFileUploadId)
    }
    /**
     * 获取文件列表数据
     * @return
     */
    def getEiaFileUploadDataList(){
        def dataMap = eiaFileUploadService.getEiaFileUploadDataList(params)
        if (dataMap) {
            render([code: HttpMesConstants.CODE_OK, count: dataMap.total, data: dataMap.data] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }


    def getEiaFileUploadDataMap(){
        def dataMap = eiaFileUploadService.getEiaFileUploadDataMap(params)
        if(dataMap) {
            render([code: HttpMesConstants.CODE_OK, data: dataMap] as JSON)
        }else{
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }

}
