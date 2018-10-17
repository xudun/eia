package com.lheia.eia.config

import com.lheia.eia.common.HttpMesConstants
import grails.converters.JSON

class EiaDataMaintainLogController {
    def eiaDataMaintainLogService
    /**
     * 数据维护列表
     */
    def eiaDataMaintainIndex(){

    }
    /**
     * 数据更新全量
     */
    def eiaDataMaintainCreate(){

    }
    /**
     * 数据维护保存
     * @return
     */
    def dataMaintainSave(){

        def dataMaintain = eiaDataMaintainLogService.dataMaintainSave(params,session)
        if (dataMaintain) {
            render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SAVE_OK] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
        }
    }
    /**
     * 数据维护分项
     */
    def eiaDataMaintainSubCreate(){
    }
    /**
     * 数据维护分项保存
     */
    def dataMaintainSubSave(){
        def dataMaintain = eiaDataMaintainLogService.dataMaintainSubSave(params,session)
        if (dataMaintain) {
            render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SAVE_OK] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
        }
    }
    /**
     * 获取已存在的更新人数据
     */
    def getMaintainType(){
        def dataMap = eiaDataMaintainLogService.getMaintainType(params)
        if (dataMap) {
            render([code: HttpMesConstants.CODE_OK, data: dataMap] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
    /**
     * 数据删除
     * @return
     */
    def maintainDel(){
        def dataMap = eiaDataMaintainLogService.maintainDel(params,session)
        if (dataMap) {
            render([code: HttpMesConstants.CODE_OK, data: dataMap] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
    /**
     * 合同不显示
     * @return
     */
    def maintainShow(){
        def dataMap = eiaDataMaintainLogService.maintainShow(params)
        if (dataMap) {
            render([code: HttpMesConstants.CODE_OK, data: dataMap] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
    /**
     * 项目关联任务
     */
    def eiaDataMaintainTask(){
    }
    /**
     * 项目关联任务保存
     */
    def projectToTask(){
        def dataMap = eiaDataMaintainLogService.projectToTask(params)
        if (dataMap) {
            render([code: HttpMesConstants.CODE_OK, data: dataMap] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
}
