package com.lheia.eia.config

import com.lheia.eia.common.HttpMesConstants
import grails.gorm.transactions.Transactional

@Transactional
class EiaDomainCodeService {

    /**
     * EiaDomainCode判重
     */
    def checkEiaDomainCode(params) {
        def eiaDomainCodeId = params.long('eiaDomainCodeId')
        def eiaDomainCodeList = EiaDomainCode.findAllByCodeAndDomain(params.code, params.domain)
        if (eiaDomainCodeList.size() > 0) {
            for (Object eiaDomainCode : eiaDomainCodeList) {
                if (eiaDomainCodeId != eiaDomainCode.id) {
                    return HttpMesConstants.MSG_DATA_EXIST
                }
            }
        }
    }
    /**
     * 新增EiaDomainCode
     */
    def eiaDomainCodeSave(params) {
        def domainCode = new EiaDomainCode(params)
        domainCode.parentId = params.long('parentId')
        domainCode.codeLevel = params.int('codeLevel')
        domainCode.displayOrder = params.int('displayOrder')
        domainCode.save(flush: true, failOnError: true)
    }
    /**
     * 修改EiaDomainCode
     */
    def eiaDomainCodeUpdate(params) {
        def domainCode = EiaDomainCode.findById(params.long('eiaDomainCodeId'))
        domainCode.properties = params
        domainCode.parentId = params.long('parentId')
        domainCode.codeLevel = params.int('codeLevel')
        domainCode.displayOrder = params.int('displayOrder')
        domainCode.save(flush: true, failOnError: true)
    }
    /**
     * 删除EiaDomainCode
     */
    def eiaDomainCodeDel(params) {
        def domainCode = EiaDomainCode.findById(params.long('eiaDomainCodeId'))
        domainCode.delete(flush: true, failOnError: true)
    }
    /**
     * 获取EiaDomainCode
     */
    def getEiaDomainCodeDataMap(params) {
        return EiaDomainCode.findById(params.long('eiaDomainCodeId'))
    }

    /**
     * 获取EiaDomainCode列表
     */
    def getEiaDomainCodeList(params) {
        int page
        int limit
        if (params.page && params.limit) {
            page = params.int('page') - 1
            limit = params.int('limit')
        }
        def domainCodeList = EiaDomainCode.createCriteria().list(max: limit, offset: page * limit,sort: "displayOrder", order: "asc") {
            eq("domain", params.domain)
        }
        if (domainCodeList.size() > 0) {
            return domainCodeList
        } else {
            return false
        }
    }
    /**
     * EiaDomainCode分页查询
     */
    def eiaDomainCodeQuery(params) {
        int page
        int limit
        if (params.page && params.limit) {
            page = params.int('page') - 1
            limit = params.int('limit')
        }
        def domainCodeList = EiaDomainCode.createCriteria().list(max: limit, offset: page * limit) {
            def codeDesc = params["key[codeDesc]"]
            if (codeDesc && !"编码名称".equals(codeDesc)) {
                or {
                    like("code","%"+ codeDesc +"%")
                    like("codeDesc","%"+ codeDesc +"%")
                    like("domainDesc","%"+ codeDesc +"%")
                    like("domain","%"+ codeDesc +"%")
                }
            }
            order("displayOrder", "asc")
        }
        def data = []
        domainCodeList.each {
            def map = [:]
            map.id = it.id
            map.code = it?.code
            map.codeDesc = it?.codeDesc
            map.codeLevel = it?.codeLevel
            map.displayOrder = it?.displayOrder
            map.domain = it?.domain
            map.domainDesc = it?.domainDesc
            data << map
        }
        def dataMap = [:]
        dataMap.data = data
        dataMap.total = domainCodeList.totalCount
        return dataMap
    }
    /**
     * 根据类型获取该类型全部信息
     * @param domain
     * @return
     */
    def getCodes(String domain){
        return EiaDomainCode.findAllByDomain(domain,[cache:true,sort:'displayOrder'])
    }
    /**
     * 根据类型返回中文名称
     * @param domain
     * @param code
     * @return
     */
    String translate(String domain,String code){
        def codes=getCodes(domain)
        if(codes){
            return codes.find {it.code==code }?.codeDesc
        }
    }
    /**
     * 根据类型获取该类型（只用于财务出账费用类型）
     * @param domain
     * @return
     */
    def getCodesInvoice(String domain){
        return EiaDomainCode.findAllByDomainAndParentCode(domain,'-1',[cache:true,sort:'displayOrder'])
    }
}
