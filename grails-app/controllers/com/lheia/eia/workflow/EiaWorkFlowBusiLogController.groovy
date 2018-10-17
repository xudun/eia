package com.lheia.eia.workflow

import com.lheia.eia.common.HttpMesConstants
import grails.converters.JSON

class EiaWorkFlowBusiLogController {
    def eiaWorkFlowBusiLogService
    /***
     * 流转意见数据
     */
    def getEiaWorkFlowBusiLogDataList() {
        def dataMap = eiaWorkFlowBusiLogService.getEiaWorkFlowBusiLogDataList(params)
        render([code: HttpMesConstants.CODE_OK, count: dataMap.total, data: dataMap.data] as JSON)
    }
    /***
     * 获取与我相关数据
     * @return
     */
    def getEiaWorkFlowBusiLogAboutMeDataList() {
        def paramMap = [:]
        paramMap.tableName = params.tableName
        paramMap.tableNameId = params.tableNameId
        paramMap.staffId = session.staff.staffId
        def dataMap = eiaWorkFlowBusiLogService.getEiaWorkFlowBusiLogDataList(paramMap)
        render([code: HttpMesConstants.CODE_OK, count: dataMap.total, data: dataMap.data] as JSON)
    }

}
