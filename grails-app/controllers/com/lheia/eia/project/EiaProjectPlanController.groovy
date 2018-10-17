package com.lheia.eia.project

import com.lheia.eia.common.GeneConstants
import com.lheia.eia.common.HttpMesConstants
import grails.converters.JSON

class EiaProjectPlanController {
    def eiaProjectPlanService
    def eiaWorkFlowBusiService
    /**
     * 工作方案保存
     */
    def eiaProjectPlanSave() {
        if (params.eiaProjectPlanId) {
            def proPlan = eiaProjectPlanService.eiaProjectPlanUpdate(params)
            if (proPlan) {
                render([code: HttpMesConstants.CODE_OK, data: proPlan] as JSON)
            } else {
                render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
            }
        } else {
            def proPlan = eiaProjectPlanService.eiaProjectPlanSave(params)
            if (proPlan) {
                render([code: HttpMesConstants.CODE_OK, data: proPlan] as JSON)
            } else {
                render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
            }
        }
    }
    /**
     * 工作方案节点更新
     */
    def eiaProjectPlanItemUpdate() {
        if (!params.proPlanItemId) {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        } else {
            def proPlanItem = eiaProjectPlanService.eiaProjectPlanItemUpdate(params)
            if (proPlanItem == HttpMesConstants.MES_DATE_ERROR) {
                render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MES_DATE_ERROR] as JSON)
            } else if (proPlanItem && proPlanItem != HttpMesConstants.MES_DATE_ERROR) {
                render([code: HttpMesConstants.CODE_OK, data: proPlanItem] as JSON)
            } else {
                render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
            }
        }
    }
    /**
     * 工作方案节点更新新
     */
    def eiaProjectPlanItemUpdateNew() {
        def proPlanItem = eiaProjectPlanService.eiaProjectPlanItemUpdateNew(params)
        if (proPlanItem == HttpMesConstants.MES_DATE_ERROR) {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MES_DATE_ERROR] as JSON)
        } else if (proPlanItem && proPlanItem != HttpMesConstants.MES_DATE_ERROR) {
            render([code: HttpMesConstants.CODE_OK, data: proPlanItem] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
        }
    }
    /**
     * 工作方案节点删除
     */
    def proPlanItemDel() {
        if (!params.proPlanItemId) {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        } else {
            def proPlanItem = eiaProjectPlanService.proPlanItemDel(params)
            if (proPlanItem) {
                render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_DEL_OK] as JSON)

            } else {
                render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DEL_FAIL] as JSON)
            }
        }
    }

    /**
     * 获取工作方案节点列表
     */
    def getProjectNodeList() {
        def proPlanItemList = eiaProjectPlanService.getProjectNodeList(params)
        if (proPlanItemList) {
            render([code: HttpMesConstants.CODE_OK, data: proPlanItemList.data, count: proPlanItemList.total] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }

    /**
     * 获取工作方案节点列表（新项目）
     */
    def getProjectNodeListNew() {
        def proPlanItemList = eiaProjectPlanService.getProjectNodeListNew(params)
        if (proPlanItemList) {
            render([code: HttpMesConstants.CODE_OK, data: proPlanItemList.data, count: proPlanItemList.total] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }

    /**
     * 获取工作方案节点详情
     */
    def getProPlanDataMap() {
        def proPlanData = eiaProjectPlanService.getPlanItemDataMap(params)
        if (proPlanData) {
            render([code: HttpMesConstants.CODE_OK, data: proPlanData] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, data: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
    /**
     * 保存工作方案到工作流
     */
    def proPlanToWorkFlow() {
        def proWorkFlow = eiaProjectPlanService.proPlanToWorkFlow(params, session)
        if (proWorkFlow == HttpMesConstants.MSG_SAVE_OK) {
            render([code: HttpMesConstants.CODE_OK] as JSON)
        } else if (proWorkFlow == HttpMesConstants.MSG_FCONF_NULL) {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_FCONF_NULL] as JSON)
        } else if (proWorkFlow == HttpMesConstants.MSG_APP_NULL) {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_APP_NULL] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
        }
    }
    /**
     * 保存工作方案到工作流(管理员专用)
     */
    def makeProjectWorkFlow() {
        def proWorkFlow = eiaProjectPlanService.makeProjectWorkFlow(params, session)
        if (proWorkFlow == HttpMesConstants.MSG_SAVE_OK) {
            render([code: HttpMesConstants.CODE_OK] as JSON)
        } else if (proWorkFlow == HttpMesConstants.MSG_FCONF_NULL) {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_FCONF_NULL] as JSON)
        } else if (proWorkFlow == HttpMesConstants.MSG_APP_NULL) {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_APP_NULL] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
        }
    }
    /**
     * 获取节点审核人员列表
     */
    def getNodeUsers() {
        def nodeUsers = eiaProjectPlanService.getNodeUsers(params)
        if (nodeUsers) {
            render([code: HttpMesConstants.CODE_OK, data: nodeUsers] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
    /**
     * 检查项目是否启动工作流
     */
    def checkProjectFlow() {
        def proFlow = eiaWorkFlowBusiService.checkWorkFlow(GeneConstants.DOMAIN_EIA_PROJECT, params.long('eiaProjectId'))
        if (proFlow) {
            render([code: HttpMesConstants.CODE_OK] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_PRO_FLOW_NULL] as JSON)
        }
    }
    /**
     * 节点相关工作人员下拉树
     */
    def getNodeUsersTree() {
        def nodeUserList = eiaProjectPlanService.getNodeUsersTree(params)
        if (nodeUserList.size() > 0) {
            render nodeUserList as JSON
            // render([code: HttpMesConstants.CODE_OK, data: nodeUserList] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DEL_OK])
        }
    }
    /**
     * 相关工作人员回显
     */
    def getItemUserNames() {
        def userNames = eiaProjectPlanService.getItemUserNames(params)
        if (userNames) {
            render([code: HttpMesConstants.CODE_OK, data: userNames] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
    /**
     * 获取节点列表（干预流程用）
     */
    def getProItems() {
        def proItems = eiaProjectPlanService.getProItems(params)
        if (proItems) {
            render([code: HttpMesConstants.CODE_OK, data: proItems] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
    /**
     * 变更流程当前节点
     */
    def changeWorkFlowNode() {
        def proItems = eiaProjectPlanService.changeWorkFlowNode(params)
        if (proItems) {
            render([code: HttpMesConstants.CODE_OK, data: proItems] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
}
