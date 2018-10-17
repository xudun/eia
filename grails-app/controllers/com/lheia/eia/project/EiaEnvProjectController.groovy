package com.lheia.eia.project

import com.lheia.eia.common.HttpMesConstants
import grails.converters.JSON

class EiaEnvProjectController {
/**
 * 一级业务:环保咨询
 */
    def eiaEnvProjectService
    /**
     * 保存环保咨询项目信息
     * @return
     */
    def eiaEnvProjectSave() {
        if (params.eiaEnvProjectId && params.eiaProjectId) {
            def eiaEnvPro = eiaEnvProjectService.eiaEnvProjectUpdate(params, session)
            if (eiaEnvPro) {
                render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SAVE_OK, data: eiaProject] as JSON)
            } else {
                render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
            }
        } else {
            def eiaEnvPro = eiaEnvProjectService.eiaEnvProjectSave(params, session)
            if (eiaEnvPro) {
                render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SAVE_OK, data: eiaEnvPro] as JSON)
            } else {
                render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
            }
        }
    }

    def getEnvProDataList() {
        def envProList = eiaEnvProjectService.getEnvProDataList(params)
        if (envProList.size() > 0) {
            render([code: HttpMesConstants.CODE_OK, count: envProList.totalCount, data: envProList.data] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }

    def getEnvProDataMap() {
        def envProDataMap = eiaEnvProjectService.getEnvProDataMap(params)
        if (envProDataMap) {
            render([code: HttpMesConstants.CODE_OK, data: envProDataMap] as JSON)
            //     render(envProDataMap as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, data:[:],msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }

    /**
     * 保存现场勘查的信息
     */
    def exploreInfoSave() {
        def exploreInfo = eiaEnvProjectService.exploreInfoSave(params)
        if (exploreInfo) {
            render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SAVE_OK, data: exploreInfo] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }

    /**
     * 获取现场勘察记录详情
     *
     */
    def getEnvProjectDataMap() {
        def resMap = eiaEnvProjectService.getProjectExploreDataMap(params)
        if (resMap) {
            render([code: HttpMesConstants.CODE_OK, data: resMap] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }

    /**
     * 获取现场勘察记录回显
     *
     */
    def getEnvProjectSaveDataMap() {
        def resMap = eiaEnvProjectService.getProjectExploreSaveDataMap(params)
        if (resMap) {
            render([code: HttpMesConstants.CODE_OK, data: resMap] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }

    /**
     * 图片上传
     */
    def uploadExploreImage() {
        def fileInfo = eiaEnvProjectService.uploadExploreImage(request, session, params)
        if (fileInfo) {
            render([code: HttpMesConstants.CODE_OK, data: HttpMesConstants.MSG_SAVE_OK] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, data: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
        }

    }

    /**
     * 获取项目的现场图片
     */
    def getExploreImageList() {
        def imgList = eiaEnvProjectService.getExploreImageList(params)
        render([code: HttpMesConstants.CODE_OK, data: imgList] as JSON)

    }
    /**
     * 图片删除
     */
    def exploreImageDel() {
        def exploreImage = eiaEnvProjectService.exploreImageDel(params)
        if (exploreImage) {
            render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_DEL_OK] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DEL_FAIL] as JSON)
        }
    }
}
