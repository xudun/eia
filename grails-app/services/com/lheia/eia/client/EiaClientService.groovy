package com.lheia.eia.client

import com.lheia.eia.common.GeneConstants
import com.lheia.eia.common.HttpMesConstants
import com.lheia.eia.contract.EiaContract
import com.lheia.eia.contract.EiaOffer
import grails.gorm.transactions.Transactional

@Transactional
class EiaClientService {
    /**
     * 通过客户id获取客户信息
     */
    def getEiaClientDataMap(eiaClientId) {
        return EiaClient.findByIdAndIfDel(eiaClientId,false)
    }
    /**
     *通过客户id获取客户联系人信息
     */
    def getEiaClientContactsDataMap(clientContactsId){
        return EiaClientContacts.findByIdAndIfDel(clientContactsId,false)
    }
    /**
     * 客户信息列表
     */
    def eiaClientQueryPage(params, session) {
        int page
        int limit
        if (params.page && params.limit) {
            page = params.int('page') - 1
            limit = params.int('limit')
        }
        def eiaClientList = EiaClient.createCriteria().list(max: limit, offset: page * limit) {
            def clientName = params.clientName
            if (clientName && !"客户名称,录入部门,录入人".equals(clientName)) {
                or{
                    like("clientName", "%" + clientName + "%")
                    like("inputDept", "%" + clientName + "%")
                    like("inputUser", "%" + clientName + "%")
                }
            }
            eq("ifDel", false)
            order("id", "desc")
        }
        def eiaClientData = []
        eiaClientList.each {
            def map = [:]
            map.id = it.id
            map.clientName = it.clientName
            map.clientAddress = it.clientAddress
            map.clientPostCode = it.clientPostCode
            map.clientCorporate = it.clientCorporate
            map.clientFax = it.clientFax
            map.clientNameCn = it.clientName
            map.clientAddrCn = it.clientAddress
            map.inputDept = it.inputDept
            map.inputUser = it.inputUser
            map.inputUserId = it.inputUserId
            /** 找客户的联系人，该联系人的信息必须是填写完全的 */
            def clientContact = EiaClientContacts.findByEiaClientIdAndIfDelAndContactNameIsNotNullAndContactPhoneIsNotNullAndContactPositionIsNotNullAndContactEmailIsNotNull(it.id, false, [sort: "id", order: "desc"])
            if (clientContact) {
                map.contactName = clientContact?.contactName
                map.contactPhone = clientContact?.contactPhone
            }
            def offerCount = EiaOffer.countByEiaClientIdAndIfDelAndOfferState(it.id,false,GeneConstants.CONTRACT_STATE_NOT_SIGNED)
            map.offerCount = offerCount
            def contractCount = EiaContract.countByEiaClientIdAndIfDel(it.id, false)
            map.contractCount = contractCount
            eiaClientData << map
        }
        def dataMap = [:]
        dataMap.data = eiaClientData
        dataMap.total = eiaClientList.totalCount
        return dataMap
    }
    /**
     * 客户联系人列表
     */
    def eiaClientContactsQueryPage(params,session){
        int page
        int limit
        if (params.page && params.limit) {
            page = params.int('page') - 1
            limit = params.int('limit')
        }
        def eiaClientContactsList = EiaClientContacts.createCriteria().list(max: limit, offset: page * limit) {
            def contactInfo = params.contactInfo
            if (contactInfo) {
                or {
                    like("contactName", "%" + contactInfo + "%")
                    like("contactPhone", "%" + contactInfo + "%")
                }
            }
            eq("eiaClientId", params.long("eiaClientId"))
            eq("ifDel", false)
            order("id", "desc")
        }
        def eiaClientContactsData = []
        eiaClientContactsList.each {
            def map = [:]
            map.id = it.id
            map.contactName = it.contactName
            map.contactPosition = it.contactPosition
            map.contactPhone = it.contactPhone
            map.contactEmail = it.contactEmail
            map.clientFax = it.clientFax
            map.inputDept = it.inputDept
            map.inputUser = it.inputUser
            eiaClientContactsData << map
        }
        def dataMap = [:]
        dataMap.data = eiaClientContactsData
        dataMap.total = eiaClientContactsList.totalCount
        return dataMap
    }
    /**
     * 客户信息新增保存
     */
    def eiaClientSave(params,session){
        /**
         * 判断客户是否存在
         */
        def eiaClientExist = eiaClientSaveExist(params.clientName)
        if(eiaClientExist){
            return HttpMesConstants.MSG_ADDCLIENT_FALSE
        }else{
            def eiaClient = new EiaClient()
            eiaClient.properties = params
            eiaClient.inputDept = session.staff.orgName
            eiaClient.inputDeptId = Long.parseLong(session.staff.orgId)
            eiaClient.inputDeptCode = session.staff.orgCode
            eiaClient.inputUser = session.staff.staffName
            eiaClient.inputUserId = Long.parseLong(session.staff.staffId)
            eiaClient.save(flush: true, failOnError: true)
        }

    }
    /**
     * 客户信息编辑保存
     */
    def eiaClientUpdate(params,session){
        /**
         * 判断客户是否存在
         */
        def eiaClient
        def eiaClientExist = eiaClientUpdateExist(params)
        if(eiaClientExist){
            return HttpMesConstants.MSG_ADDCLIENT_FALSE
        }else{
            eiaClient = EiaClient.findByIdAndIfDel(params.long("eiaClientId"),false)
            eiaClient.properties = params
            eiaClient.save(flush: true, failOnError: true)
            /**
             * 修改开户信息中的客户名称
             */
            def eiaClientConfig = EiaClientConfig.findAllByEiaClientIdAndIfDel(eiaClient?.id,false)
            if(eiaClientConfig){
                eiaClientConfig.each{
                    it.clientName = eiaClient?.clientName
                    it.save(flush: true, failOnError: true)
                }
            }
        }
        return eiaClient
    }
    /**
     * 新增客户联系人保存
     */
    def eiaClientContactsSave(params,session){
        /**
         * 判断客户联系人是否存在
         */
        def eiaClientExist = eiaClientContactsSaveExist(params.eiaClientId,params.contactPhone)
        if(eiaClientExist){
            return HttpMesConstants.MSG_ADD_CONTACT_FALSE
        }else{
            def eiaClientContacts = new EiaClientContacts()
            eiaClientContacts.properties = params
            if(params.eiaClientId){
                eiaClientContacts.eiaClientId = Long.parseLong(params.eiaClientId)
            }
            eiaClientContacts.inputDept = session.staff.orgName
            eiaClientContacts.inputDeptCode = session.staff.orgCode
            eiaClientContacts.inputDeptId = Long.parseLong(session.staff.orgId)
            eiaClientContacts.inputUser = session.staff.staffName
            eiaClientContacts.inputUserId = Long.parseLong(session.staff.staffId)
            eiaClientContacts.save(flush: true, failOnError: true)
        }
    }
    /**
     * 新增客户联系人修改
     */
    def eiaClientContactsUpdate(params,session){
        /**
         * 判断客户联系人是否存在
         */
        def eiaClientExist = eiaClientContactsUpdateExist(params)
        if(eiaClientExist){
            return HttpMesConstants.MSG_ADD_CONTACT_FALSE
        }else{
            def eiaClientContacts =EiaClientContacts.findByIdAndIfDel(params.long("clientContactsId"),false)
            eiaClientContacts.properties = params
            eiaClientContacts.save(flush: true, failOnError: true)
        }
    }
    /**
     * 客户信息删除
     */
    def eiaClientDel(params){
        def eiaClient = getEiaClientDataMap(params.long("eiaClientId"))
        eiaClient.ifDel = true
        eiaClient.save(flush: true, failOnError: true)
        /**
         * 删除对应联系人信息
         */
        def eiaClientContacts = EiaClientContacts.findAllByIfDelAndEiaClientId(false,params.long("eiaClientId"))
        if(eiaClientContacts){
            eiaClientContacts.each{
                it.ifDel = true
            }
        }

        /**
         * 删除对应开户信息
         */
        def eiaClientConfig = EiaClientConfig.findAllByIfDelAndEiaClientId(false,params.long("eiaClientId"))
        if(eiaClientConfig){
            eiaClientConfig.each{
                it.ifDel = true
            }
        }

        return true
    }
    /**
     * 删除客户联系人信息
     */
    def eiaClientContactsDel(clientContactsId){
        def eiaClientContacts = getEiaClientContactsDataMap(clientContactsId)
        eiaClientContacts.ifDel = true
        eiaClientContacts.save(flush: true, failOnError: true)
    }
    /**
     * 保存报价和合同中新增客户信息
     */
    def eiaOfferClientSave(params,session){
        /**
         * 判断客户是否存在
         */
        def eiaClient
        def eiaClientExist = eiaClientSaveExist(params.clientName)
        if(eiaClientExist){
            return HttpMesConstants.MSG_ADDCLIENT_FALSE
        }else{
            eiaClient = new EiaClient()
            eiaClient.properties = params
            eiaClient.inputDept = session.staff.orgName
            eiaClient.inputDeptCode = session.staff.orgCode
            eiaClient.inputDeptId = Long.parseLong(session.staff.orgId)
            eiaClient.inputUser = session.staff.staffName
            eiaClient.inputUserId = Long.parseLong(session.staff.staffId)
            eiaClient.save(flush: true, failOnError: true)
        }
        if(params.contactName){
            /**
             * 判断客户联系人是否存在
             */
            eiaClientExist = eiaClientContactsSaveExist(eiaClient.id,params.contactPhone)
            if(eiaClientExist){
                return HttpMesConstants.MSG_ADDCLIENT_FALSE
            }else{
                def eiaClientContacts = new EiaClientContacts()
                eiaClientContacts.properties = params
                eiaClientContacts.eiaClientId = eiaClient.id
                eiaClientContacts.inputDept = session.staff.orgName
                eiaClientContacts.inputDeptCode = session.staff.orgCode
                eiaClientContacts.inputDeptId = Long.parseLong(session.staff.orgId)
                eiaClientContacts.inputUser = session.staff.staffName
                eiaClientContacts.inputUserId = Long.parseLong(session.staff.staffId)
                eiaClientContacts.save(flush: true, failOnError: true)
            }
        }
    }
    /**
     * 判断客户信息是否已存在（根据客户名称）
     */
    def eiaClientSaveExist(clientName){
        def eiaClientList = EiaClient.findAllByIfDelAndClientName(false,clientName)
        /**
         * 判断客户信息是否存在
         */
        if(eiaClientList.size()>0){
            return true
        }else{
            return false
        }
    }
    /**
     * 判断客户信息是否已存在（根据客户名称）
     */
    def eiaClientUpdateExist(params){
        def eiaClientList = EiaClient.findAllByIfDelAndClientNameAndIdNotEqual(false,params.clientName,params.eiaClientId)
        /**
         * 判断客户信息是否存在
         */
        if(eiaClientList.size()>0){
            return true
        }else{
            return false
        }
    }
    /**
     * 判断客户联系人信息是否已存在新增（根据企业id（企业信息），联系电话）
     */
    def eiaClientContactsSaveExist(eiaClientId,contactPhone){
        def eiaClientContactsList = EiaClientContacts.findAllByIfDelAndEiaClientIdAndContactPhone(false,eiaClientId,contactPhone)
        /**
         * 判断客户联系人信息是否存在
         */
        if(eiaClientContactsList.size()>0){
            return true
        }else{
            return false
        }
    }
    /**
     * 判断客户联系人信息是否已存在修改（根据企业id（企业信息），联系电话）
     */
    def eiaClientContactsUpdateExist(params){
        def eiaClientContactsList = EiaClientContacts.findAllByIfDelAndEiaClientIdAndContactPhoneAndContactNameAndIdNotEqual(false,params.eiaClientId,params.contactPhone,params.contactName,params.clientContactsId)
        /**
         * 判断客户联系人信息是否存在
         */
        if(eiaClientContactsList.size()>0){
            return true
        }else{
            return false
        }
    }
    /**
     * 保存在检测方案页面填写的受检客户联系人
     */
    def saveEiaClientContact(params, session) {
        def eiaClientId = Long.parseLong(params.eiaClientId)
        def contact = EiaClientContacts.findByContactPhoneAndIfDel(params.contactPhone, false)
        if (contact) {
            if (contact.eiaClientId != eiaClientId) {
                return HttpMesConstants.MSG_ADD_CONTACT_FALSE
            }
        } else {
            contact = new EiaClientContacts()
        }
        contact.properties = params
        contact.eiaClientId = eiaClientId
        contact.inputDept = session.staff.orgName
        contact.inputDeptCode = session.staff.orgCode
        contact.inputDeptId = Long.parseLong(session.staff.orgId)
        contact.inputUser = session.staff.staffName
        contact.inputUserId = Long.parseLong(session.staff.staffId)
        contact.save(flush: true, failOnError: true)
    }
    /**
     * 修改联系人信息
     */
    def clientContactUpdate(params,session){
        def clientContactId = params.long("clientContactId")
        if(params.contactPhone){
            def eiaClientExist = eiaClientContactsUpdate(params)
            if(eiaClientExist){
                return HttpMesConstants.MSG_ADD_CONTACT_FALSE
            }else{
                def eiaClientContacts = EiaClientContacts.findByIfDelAndId(false,clientContactId)
                if(eiaClientContacts){
                    eiaClientContacts.properties = params
                    eiaClientContacts.save(flush: true, failOnError: true)
                }
            }
        }else{
            def eiaClientContacts = EiaClientContacts.findByIfDelAndId(false,clientContactId)
            if(eiaClientContacts){
                eiaClientContacts.properties = params
                eiaClientContacts.save(flush: true, failOnError: true)
            }
        }

    }
    /**
     * 新增联系人
     */
    def clientContactAddUpdate(params,session){
        def eiaClientExist = eiaClientContactsUpdate(params)
        if(eiaClientExist){
            return HttpMesConstants.MSG_ADD_CONTACT_FALSE
        }else{
            def eiaClientContacts = new EiaClientContacts()
            eiaClientContacts.properties = params
            if(params.eiaClientId){
                eiaClientContacts.eiaClientId = Long.parseLong(params.eiaClientId)
            }
            eiaClientContacts.inputDept = session.staff.orgName
            eiaClientContacts.inputDeptCode = session.staff.orgCode
            eiaClientContacts.inputDeptId = Long.parseLong(session.staff.orgId)
            eiaClientContacts.inputUser = session.staff.staffName
            eiaClientContacts.inputUserId = Long.parseLong(session.staff.staffId)
            eiaClientContacts.save(flush: true, failOnError: true)
        }

    }
    /**
     * 删除联系人名称为空
     */
    def clientContactDel(){
        def eiaClientContacts = EiaClientContacts.findAllByIfDelAndContactNameIsNull(false)
        if(eiaClientContacts.size()>0){
            eiaClientContacts.each{
                it.delete(flush: true, failOnError: true)
            }
        }
        return true
    }
    /**
     * 判断客户电话信息
     * */
    def eiaClientContactsUpdate(params){
        def eiaClientContactsList = EiaClientContacts.findAllByIfDelAndEiaClientIdAndContactPhone(false,params.eiaClientId,params.contactPhone)
        /**
         * 判断客户联系人信息是否存在
         */
        if(eiaClientContactsList.size()>0){
            return true
        }else{
            return false
        }
    }
}
