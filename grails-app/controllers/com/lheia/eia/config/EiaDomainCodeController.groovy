package com.lheia.eia.config

import com.lheia.eia.common.HttpMesConstants
import grails.converters.JSON

class EiaDomainCodeController {

    def eiaDomainCodeService

    def eiaDomainCodeIndex() {
    }
    def eiaDomainCodeDetail() {}
    def eiaDomainCodeShow() {}

    /**
     * 新增EiaDomainCode
     */
    def eiaDomainCodeSave() {
        /** EiaDomainCode判重 */
        def checkResult = eiaDomainCodeService.checkEiaDomainCode(params)
        if (checkResult == HttpMesConstants.MSG_DATA_EXIST) {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_EXIST] as JSON)
        } else {
            if (!params.eiaDomainCodeId) {
                if (eiaDomainCodeService.eiaDomainCodeSave(params)) {
                    render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SAVE_OK] as JSON)
                } else {
                    render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
                }
            } else {
                if (eiaDomainCodeService.eiaDomainCodeUpdate(params)) {
                    render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SAVE_OK] as JSON)
                } else {
                    render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
                }
            }
        }
    }
    /**
     * 修改EiaDomainCode
     */
    def eiaDomainCodeUpdate() {
        if (eiaDomainCodeService.eiaDomainCodeUpdate(params)) {
            render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_SAVE_OK] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_SAVE_FAIL] as JSON)
        }
    }
    /**
     * 删除EiaDomainCode
     */
    def eiaDomainCodeDel() {
        try {
            eiaDomainCodeService.eiaDomainCodeDel(params)
            render([code: HttpMesConstants.CODE_OK, msg: HttpMesConstants.MSG_DEL_OK] as JSON)
        } catch (Exception e) {
            e.printStackTrace()
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DEL_FAIL] as JSON)
        }
    }
    /**
     * 获取EiaDomainCode
     */
    def getEiaDomainCodeDataMap() {
        def dataMap = eiaDomainCodeService.getEiaDomainCodeDataMap(params)
        if (dataMap) {
            render([code: HttpMesConstants.CODE_OK, data: dataMap] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
    /**
     * 获取EiaDomainCode列表
     */
    def getEiaDomainCodeList() {
        def clientDomainList = eiaDomainCodeService.getEiaDomainCodeList(params)
        if (clientDomainList) {
            render([code: HttpMesConstants.CODE_OK, data: clientDomainList] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
    /**
     * EiaDomainCode分页查询
     */
    def eiaDomainCodeQuery() {
        def dataMap = eiaDomainCodeService.eiaDomainCodeQuery(params)
        if (dataMap) {
            render([code: HttpMesConstants.CODE_OK, count: dataMap.total, data: dataMap.data] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
        }
    }
    /**
     * 获取domainCode中的树形结构
     * @return
     */
    def getTree(){
        def codes = eiaDomainCodeService.getCodes(params.domain)
        boolean closed = false
        if(params.closed) closed = params.boolean('closed')
        def nodesMap = [:]
        def treeList = []
        def createNode = {code ->
            def node = nodesMap[code]
            if(!node){
                node = [:]
                node.children = []
                node.attributes = [:]
                nodesMap[code] = node

            }
            return node
        }
        codes.each {
            def node = createNode(it.code)
            node.id = it.code
            node.name = it.codeDesc
            node.attributes.parentCode = it.parentCode
            if(it.parentCode){
                def pNode = createNode(it.parentCode)
                pNode.children << node
                if(closed)
                    pNode.state = 'closed'
            }else{
                treeList << node
            }
        }
        render (treeList as JSON)
    }
    /**
     * 获取domainCode中的树形结构(只用于财务出账费用类型)
     * @return
     */
    def getTreeInvoice(){
        def codes = eiaDomainCodeService.getCodesInvoice(params.domain)
        boolean closed = false
        if(params.closed) closed = params.boolean('closed')
        def nodesMap = [:]
        def treeList = []
        def createNode = {code ->
            def node = nodesMap[code]
            if(!node){
                node = [:]
                node.children = []
                node.attributes = [:]
                nodesMap[code] = node

            }
            return node
        }
        codes.each {
            def node = createNode(it.code)
            node.id = it.code
            node.name = it.codeDesc
            treeList << node
        }
        render (treeList as JSON)
    }

}
