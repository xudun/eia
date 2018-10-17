package com.lheia.eia.export

import com.lheia.eia.cert.EiaCert
import com.lheia.eia.client.EiaClient
import com.lheia.eia.client.EiaClientContacts
import com.lheia.eia.common.GeneConstants
import com.lheia.eia.common.HttpMesConstants
import com.lheia.eia.common.HttpUrlConstants
import com.lheia.eia.config.EiaDomainCode
import com.lheia.eia.contract.EiaContract
import com.lheia.eia.contract.EiaContractLog
import com.lheia.eia.contract.EiaOffer
import com.lheia.eia.project.EiaProject
import com.lheia.eia.tools.DateTransTools
import com.lheia.eia.tools.HttpConnectTools
import com.lheia.eia.tools.JsonHandler
import com.lheia.eia.tools.QRCodeTools
import com.lheia.eia.tools.RandomCodeTools
import freemarker.template.Configuration
import freemarker.template.Template
import grails.gorm.transactions.Transactional
import grails.util.Holders

import java.lang.reflect.Field
import java.text.DecimalFormat
import java.text.SimpleDateFormat

@Transactional
class ExportContractService {

    /**
     * 匹配合同模板
     * @param id
     * @return
     */
    def matchWrongType(Long contractId) {
        def contract = EiaContract.findById(contractId)
        def contractTypeCode = contract.contractTypeCode
        def wrongCodeTotal = "EPC_JL,EPC_HH,EPC_GJ,EPC_CD,EPC_ZH,EPC_QT,ESE_QJ,ESE_ZH,ESE_QT,GREEN_SC,GREEN_ZR,GREEN_TH,GREEN_FP,GREEN_ZH,GREEN_QT"
        def wrongCode = wrongCodeTotal.split(",")
        def result = "pass"
        for (int i = 0; i < wrongCode.size(); i++) {
            if (contractTypeCode.equals(wrongCode[i])) {
                result = "unpass"
            }
        }
        def param = [:]
        param.staffId = (contract.inputUserId).toString()
        def staffJson = HttpConnectTools.getResponseJson(HttpUrlConstants.SINGLE_EIA_AUTH_STAFF_INFO, param)
        if (staffJson) {
            def staff = JsonHandler.jsonToMap(staffJson).data
            if (!staff) {
                result = "noStaff"
            }
        }
        return result
    }

    /**
     * 合同模板导出
     * @param contractId
     * @return
     */
    def exportContractToWord(contractId) {
        def contract = EiaContract.findById(contractId)
        def client = EiaClient.findById(contract.eiaClientId)
        def contact = EiaClientContacts.findByContactNameAndContactPhone(contract.contactName, contract.contactPhone)
        if (!contact) {
            def contactList = EiaClientContacts.findAllByEiaClientIdAndIfDel(contract.eiaClientId, false)
            if (contactList) {
                contact = contactList.get(0)
            }
        }
        /**
         * 创建freemarker配置实例
         */
        Configuration cfg = new Configuration()
        /**
         * 设置字符集
         */
        cfg.setDefaultEncoding("UTF-8");
        /**
         * 加载freemarker文件夹 Holders.getConfig()获得ftl模板所在路径
         */
        cfg.setDirectoryForTemplateLoading(new File(Holders.getConfig().getProperty("webroots.exportContractFtlPath")))
        /**
         * 创建数据模型
         */
        Map<String, Object> map = new HashMap<>()
        /**
         * 添加数据
         */
        for (Field contractField : contract.getClass().getDeclaredFields()) {
            contractField.setAccessible(true)
            if (!contractField.get(contract)) {
                map.put(contractField.getName(), "")
            } else {
                map.put(contractField.getName(), contractField.get(contract))
            }
            if ("contractDate".equals(contractField.getName())) {
                def dateFormat = new SimpleDateFormat("yyyy年M月")
                def year = new SimpleDateFormat("yyyy")
                map.put(contractField.getName(), dateFormat.format(contractField.get(contract)))
                map.put("year", year.format(contractField.get(contract)))
            }
            if ("province".equals(contractField.getName())) {
                def dictionary = EiaDomainCode.findByCode(contractField.get(contract))
                map.put(contractField.getName(), dictionary.getCodeDesc())
            }
            if ("contractMoney".equals(contractField.getName())) {
                /** 获取合同金额，将金额格式由万元转为元 */
                def contractMoney = contractField.get(contract).toString()
                def moneyStr = contractMoney.substring(0, contractMoney.lastIndexOf(".")) + contractMoney.substring(contractMoney.lastIndexOf(".") + 1)
                /** 金额的每3位用","隔开 */
                DecimalFormat df = new DecimalFormat(",###")
                def money = df.format(Double.parseDouble(moneyStr))
                /** 将合同金额转为大写 */
                def transformMoney = DateTransTools.transformMoney(Double.parseDouble(moneyStr))
                map.put(contractField.getName(), money + "元" + " " + "(大写:" + transformMoney + ")")
            }
        }
        for (Field clientField : client.getClass().getDeclaredFields()) {
            clientField.setAccessible(true)
            if (!clientField.get(client)) {
                map.put(clientField.getName(), "")
            } else {
                map.put(clientField.getName(), clientField.get(client))
            }
        }
        if (contact) {
            for (Field contactField : contact.getClass().getDeclaredFields()) {
                contactField.setAccessible(true)
                if (!contactField.get(contact)) {
                    map.put(contactField.getName(), "")
                } else {
                    map.put(contactField.getName(), contactField.get(contact))
                }
            }
        } else {
            map.put("contactEmail", "")
        }
        def param = [:]
        param.staffId = (contract.inputUserId).toString()
        def staffJson = HttpConnectTools.getResponseJson(HttpUrlConstants.SINGLE_EIA_AUTH_STAFF_INFO, param)
        if (staffJson) {
            def staff = JsonHandler.jsonToMap(staffJson).data[0]
            if (staff.mailBox) {
                def mailBox = staff.mailBox
                map.put("mailbox", mailBox)
            } else {
                map.put("mailbox", "")
            }
            if (staff.cellphone) {
                def cellphone = staff.cellphone
                map.put("cellphone", cellphone)
            } else {
                map.put("cellphone", "")
            }
        } else {
            map.put("mailbox", "")
        }
        if (contract.inputUser) {
            map.put("inputUser", contract.inputUser)
        } else {
            map.put("inputUser", "")
        }
        if (client.clientName) {
            map.put("clientName", client.clientName)
        } else {
            map.put("clientName", "")
        }
        if (contract.contactName) {
            map.put("contactName", contract.contactName)
        } else {
            map.put("contactName", "")
        }
        if (client.clientPostCode) {
            map.put("clientPostCode", client.clientPostCode)
        } else {
            map.put("clientPostCode", "")
        }
        if (client.clientFax) {
            map.put("clientFax", client.clientFax)
        } else {
            map.put("clientFax", "")
        }
        /** 判断是泰泽还是赤道进行导出操作，决定了受托方和开户信息 */
        def contractTrust = contract?.contractTrust
        def trusteeBank
        def trusteeAddr
        def trusteeAccount
        def trusteeAccName
        if (GeneConstants.CONTRACT_TRUST_CHIDAO == contractTrust) {
            def trusteeClient = EiaDomainCode.findByCodeAndDomain(GeneConstants.CHIDAO_NAME, GeneConstants.TRUSTEE_CLIENT)?.codeDesc
            if (trusteeClient) {
                map.put("trusteeClient", trusteeClient)
            } else {
                map.put("trusteeClient", "")
            }
            trusteeBank = EiaDomainCode.findByCodeAndDomain(GeneConstants.CHIDAO_BANK, GeneConstants.TRUSTEE_BANK)?.codeDesc
            if (trusteeBank) {
                map.put("trusteeBank", trusteeBank)
            } else {
                map.put("trusteeBank", "")
            }
            trusteeAddr = EiaDomainCode.findByCodeAndDomain(GeneConstants.CHIDAO_ADDR, GeneConstants.TRUSTEE_ADDR)?.codeDesc
            if (trusteeAddr) {
                map.put("trusteeAddr", trusteeAddr)
            } else {
                map.put("trusteeAddr", "")
            }
            trusteeAccount = EiaDomainCode.findByCodeAndDomain(GeneConstants.CHIDAO_ACCOUNT, GeneConstants.TRUSTEE_ACCOUNT)?.codeDesc
            if (trusteeAccount) {
                map.put("trusteeAccount", trusteeAccount)
            } else {
                map.put("trusteeAccount", "")
            }
            trusteeAccName = EiaDomainCode.findByCodeAndDomain(GeneConstants.CHIDAO_ACC_NAME, GeneConstants.TRUSTEE_ACC_NAME)?.codeDesc
            if (trusteeAccName) {
                map.put("trusteeAccName", trusteeAccName)
            } else {
                map.put("trusteeAccName", "")
            }
        } else {
            def trusteeClient = EiaDomainCode.findByCodeAndDomain(GeneConstants.TAIZE_NAME, GeneConstants.TRUSTEE_CLIENT)?.codeDesc
            if (trusteeClient) {
                map.put("trusteeClient", trusteeClient)
            } else {
                map.put("trusteeClient", "")
            }
            trusteeBank = EiaDomainCode.findByCodeAndDomain(GeneConstants.TAIZE_BANK, GeneConstants.TRUSTEE_BANK)?.codeDesc
            if (trusteeBank) {
                map.put("trusteeBank", trusteeBank)
            } else {
                map.put("trusteeBank", "")
            }
            trusteeAddr = EiaDomainCode.findByCodeAndDomain(GeneConstants.TAIZE_ADDR, GeneConstants.TRUSTEE_ADDR)?.codeDesc
            if (trusteeAddr) {
                map.put("trusteeAddr", trusteeAddr)
            } else {
                map.put("trusteeAddr", "")
            }
            trusteeAccount = EiaDomainCode.findByCodeAndDomain(GeneConstants.TAIZE_ACCOUNT, GeneConstants.TRUSTEE_ACCOUNT)?.codeDesc
            if (trusteeAccount) {
                map.put("trusteeAccount", trusteeAccount)
            } else {
                map.put("trusteeAccount", "")
            }
            trusteeAccName = EiaDomainCode.findByCodeAndDomain(GeneConstants.TAIZE_ACC_NAME, GeneConstants.TRUSTEE_ACC_NAME)?.codeDesc
            if (trusteeAccName) {
                map.put("trusteeAccName", trusteeAccName)
            } else {
                map.put("trusteeAccName", "")
            }
        }
        map.put("trusteeFax", "022-58356864")
        map.put("contractAddress", "天津市")
        /**
         * 加载模板文件
         */
        Template template = cfg.getTemplate(contract.contractTypeCode + ".ftl", "UTF-8")
        /**
         * 得到要显示目的地的输出流
         */
        File file = new File(Holders.getConfig().getProperty("webroots.tempFilePath") + contract.contractTypeCode + ".doc")
        Writer writer = new OutputStreamWriter(new FileOutputStream(file), "UTF-8")
        /**
         * 把数据写入到相应的输出流
         */
        template.process(map, writer)
        /**
         * 刷输出流,并关闭
         */
        writer.close()
        return file
    }
    /**
     * 检查项目的文件类型是否有对应的资质模板
     * @param eiaProjectId
     * @return
     */
    def matchProve(Long eiaProjectId) {
        def eiaProject = EiaProject.findById(eiaProjectId)
        def fileTypeCode = eiaProject.fileTypeCode
        if ((GeneConstants.ALL_EXPORT_PROVE_FILE_TYPE_CODE_LIST).indexOf(fileTypeCode) == -1) {
            return HttpMesConstants.MSG_NO_MATCH_PROVE
        }
    }
    /**
     * 项目资格证书导出
     */
    def exportProCert(Long eiaProjectId) {
        def eiaProject = EiaProject.findByIdAndIfDel(eiaProjectId, false)
        if (!eiaProject) {
            return false
        } else {
            /**
             * 创建freemarker配置实例
             */
            Configuration cfg = new Configuration()
            /**
             * 设置字符集
             */
            cfg.setDefaultEncoding("UTF-8");
            /**
             * 加载freemarker文件夹 Holders.getConfig()获得ftl模板所在路径
             */
            cfg.setDirectoryForTemplateLoading(new File(Holders.getConfig().getProperty("webroots.exportContractFtlPath")))
            /**
             * 加载模板文件
             */
            Template template
            def fileTypeCode = eiaProject.fileTypeCode
            if ((GeneConstants.EXPORT_PROVE_FILE_TYPE_CODE_LIST).indexOf(fileTypeCode) != -1) {
                template = cfg.getTemplate("prove.ftl", "UTF-8")
            } else {
                template = cfg.getTemplate("certificate.ftl", "UTF-8")
            }
            /**
             * 得到要显示目的地的输出流
             */
            String newFileName =(Holders.getConfig().getProperty("webroots.tempFilePath")+eiaProject.projectNo+"_certificate.doc")
            File file = new File(newFileName)
            Writer writer = new OutputStreamWriter(new FileOutputStream(file), "UTF-8")
            /**
             * 把数据写入到相应的输出流
             */
            def map = [:]
            def qrTools =  new QRCodeTools()
            if(!eiaProject.randomCode){

                def randomCode = RandomCodeTools.randomString(11)
                eiaProject.randomCode = randomCode
                eiaProject.save(flush: true, failOnError: true)
            }
            def urlStr = HttpUrlConstants.QRCODE_ADDR  + eiaProject.randomCode
            def imgStr = qrTools.makeQrCode(urlStr, "jpg", 200, 200)       //宽和高要与模板中的图片保持一致
            map.put("imageString", imgStr)
            map.put("projectName", eiaProject.projectName)
            if (fileTypeCode == "EPC_HP") {
                map.put("fileType", "环境影响报告表")
            } else if (fileTypeCode == "EPC_HY") {
                map.put("fileType", "环境影响报告书")
            } else {
                map.put("fileType", eiaProject.fileType)
            }
            map.put("projectNo", eiaProject.projectNo)
            map.put("fileTypeChild", eiaProject.fileTypeChild)
            def eiaCert = EiaCert.findByEiaProjectIdAndIfDel(eiaProject?.id,false, [sort: "id", order:  "desc"])
            if(eiaCert?.certType){
                if(GeneConstants.CERT_TYPE_APPROVAL.equals(eiaCert?.certType)){
                    map.put("certType", "报批")
                }else{
                    map.put("certType", "送审")
                }
            }else {
                map.put("certType", "")
            }
            template.process(map, writer)
            /**
             * 刷输出流,并关闭
             */
            writer.close()
            return file
        }
    }
    /**
     * 报价模板导出
     */
    def exportOfferToWord(Long eiaOfferId){
        def eiaOffer = EiaOffer.findById(eiaOfferId)
        /**
         * 获取企业对象
         */
        def eiaClient = EiaClient.findById(eiaOffer.eiaClientId)
        def userInfo
        def param = [:]
        param.staffId = (eiaOffer.inputUserId).toString()
        def staffJson = HttpConnectTools.getResponseJson(HttpUrlConstants.SINGLE_EIA_AUTH_STAFF_INFO, param)
        if (staffJson) {
            def staff = JsonHandler.jsonToMap(staffJson).data[0]
            if (staff) {
                userInfo = staff
            }
        }
        /**
         * 创建freemarker配置实例
         */
        Configuration cfg = new Configuration()
        /**
         * 设置字符集
         */
        cfg.setDefaultEncoding("UTF-8")
        /**
         * 加载freemarker文件夹 Holders.getConfig()获得ftl模板所在路径
         */
        cfg.setDirectoryForTemplateLoading(new File(Holders.getConfig().getProperty("webroots.exportContractFtlPath")))
        /**
         * 创建数据模型
         */
        Map<String,Object> map = new HashMap<>()
        /**
         * 添加数据
         */
        if (eiaClient) {
            for (Field eiaClientField : eiaClient.getClass().getDeclaredFields()) {
                eiaClientField.setAccessible(true)
                if (eiaClientField.get(eiaClient) == null) {
                    map.put(eiaClientField.getName(), "")
                } else {
                    map.put(eiaClientField.getName(), eiaClientField.get(eiaClient))
                }
            }
        }
        /**
         * 客户名称
         */
        if (!eiaOffer.eiaClientName) {
            map.put("clientName", "")
        } else {
            map.put("clientName", eiaOffer.eiaClientName)
        }
        /**
         * 合同名称
         */
        if (!eiaOffer?.offerName) {
            map.put("offerName", "")
        } else {
            map.put("offerName", eiaOffer?.offerName)
        }
        /**
         * 报价金额
         */
        if (eiaOffer.offerMoney != null && eiaOffer.offerMoney != "") {
            map.put("offerMoney", eiaOffer.offerMoney)
        } else {
            map.put("offerMoney", "0")
        }
        /**
         * 项目规模
         */
        if (eiaOffer.projectScale != null && eiaOffer.projectScale != "") {
            map.put("projectScale", eiaOffer.projectScale)
        } else {
            map.put("projectScale", "0")
        }
        /**
         * 报告编制费(万元)
         */
        if (eiaOffer?.reportFees != null && eiaOffer?.reportFees != "") {
            map.put("reportFees", eiaOffer.reportFees)
        } else {
            map.put("reportFees", "0")
        }
        /**
         * 环境监测费(万元)
         */
        if (eiaOffer?.enviroMonitoringFee != null && eiaOffer?.enviroMonitoringFee != "") {
            map.put("enviroMonitoringFee", eiaOffer?.enviroMonitoringFee)
        } else {
            map.put("enviroMonitoringFee", "0")
        }
        /**
         * 专家评审费(万元)
         */
        if (eiaOffer?.expertFee != null && eiaOffer?.expertFee != "") {
            map.put("expertFee", eiaOffer?.expertFee)
        } else {
            map.put("expertFee", "0")
        }
        /**
         * 地下水专题评价费
         */
        if (eiaOffer?.groundWater != null && eiaOffer?.groundWater != "") {
            map.put("groundWater", eiaOffer?.groundWater)
        } else {
            map.put("groundWater", "0")
        }
        /**
         * 合计
         */
        def reportFees = eiaOffer.reportFees ?:0
        def expertFee = eiaOffer.expertFee ?:0
        def enviroMonitoringFee = eiaOffer.enviroMonitoringFee ?:0
        def groundWater = eiaOffer.groundWater ?:0
        def totalMoney = (reportFees + expertFee + enviroMonitoringFee + groundWater) ?:0.0000
        map.put("totalMoney", totalMoney)

        /** 获取合同金额，将金额格式由万元转为元 */
        def moneyStr = totalMoney.toString().substring(0,totalMoney.toString().lastIndexOf(".")) + totalMoney.toString().substring(totalMoney.toString().lastIndexOf(".")+1)
        /** 金额的每3位用","隔开 */
        DecimalFormat df = new DecimalFormat(",###")
        def money = df.format(Double.parseDouble(moneyStr))
        /** 将合同金额转为大写 */
        def transformMoney = DateTransTools.transformMoney(Double.parseDouble(moneyStr))
        map.put("totalMoneyYuan", money + "元" + " " + "(大写:" + transformMoney + ")")
        def eiaContract = EiaContract.findByEiaOfferIdAndIfDel(eiaOffer.id, false)
        if (eiaContract) {
            def project = EiaProject.findByEiaContractIdAndIfDel(eiaContract?.id, false)
            if (!project?.projectName) {
                map.put("projectName", eiaOffer?.offerName)
            } else {
                map.put("projectName", project?.projectName)
            }
        } else {
            map.put("projectName", eiaOffer?.offerName)
        }
        /**
         * 项目联系人
         */
        if (!eiaOffer?.inputUser) {
            map.put("inputUser", "")
        } else {
            map.put("inputUser", eiaOffer?.inputUser)
        }
        if (!userInfo?.cellphone) {
            map.put("cellphone", "")
        } else {
            map.put("cellphone", userInfo?.cellphone)
        }
        Date currentTime = new Date()
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd")
        String dateString = formatter.format(currentTime)
        map.put("dateString",dateString)
        /**
         * 加载模板文件
         */
        def contractTrust = eiaOffer?.contractTrust
        def orgType = 'lhTzOffer'
        if (GeneConstants.CONTRACT_TRUST_CHIDAO == contractTrust) {
            orgType = 'lhCdOffer'
        }
        Template template = cfg.getTemplate(orgType + ".ftl","UTF-8")
        /**
         * 得到要显示目的地的输出流
         */
        File file = new File(Holders.getConfig().getProperty("webroots.tempFilePath") + orgType + ".doc")
        Writer writer = new OutputStreamWriter(new FileOutputStream(file),"UTF-8")
        /**
         * 把数据写入到相应的输出流
         */
        template.process(map,writer)
        /**
         * 刷输出流,并关闭
         */
        writer.close()
        return file
    }
    /**
     * 合同补充协议、变更、终止协议模板导出
     * @param eiaContractId
     * @return
     */
    def exportContractProtocal(eiaContractId, tempType) {
        def contract = EiaContract.findById(eiaContractId)
        def client = EiaClient.findById(contract.eiaClientId)
        def contact = EiaClientContacts.findByContactNameAndContactPhone(contract.contactName, contract.contactPhone)
        if (!contact) {
            def contactList = EiaClientContacts.findAllByEiaClientIdAndIfDel(contract.eiaClientId, false)
            if (contactList) {
                contact = contactList.get(0)
            }
        }
        /**
         * 创建freemarker配置实例
         */
        Configuration cfg = new Configuration()
        /**
         * 设置字符集
         */
        cfg.setDefaultEncoding("UTF-8");
        /**
         * 加载freemarker文件夹 Holders.getConfig()获得ftl模板所在路径
         */
        cfg.setDirectoryForTemplateLoading(new File(Holders.getConfig().getProperty("webroots.exportContractFtlPath")))
        /**
         * 创建数据模型
         */
        Map<String, Object> map = new HashMap<>()
        /**
         * 添加数据
         */
        for (Field contractField : contract.getClass().getDeclaredFields()) {
            contractField.setAccessible(true)
            if (!contractField.get(contract)) {
                map.put(contractField.getName(), "")
            } else {
                map.put(contractField.getName(), contractField.get(contract))
            }
            if ("contractDate".equals(contractField.getName())) {
                def dateFormat = new SimpleDateFormat("yyyy年MM月")
                map.put(contractField.getName(), dateFormat.format(contractField.get(contract)))
            }
            if ("province".equals(contractField.getName())) {
                def dictionary = EiaDomainCode.findByCode(contractField.get(contract))
                map.put(contractField.getName(), dictionary.getCodeDesc())
            }
            if ("contractMoney".equals(contractField.getName())) {
                /** 获取合同金额，将金额格式由万元转为元 */
                def contractMoney
                def eiaContractLogList = EiaContractLog.findAllByEiaContractIdAndIfDel(eiaContractId, false, [sort: "id", order: "asc"])
                if (eiaContractLogList) {
                    contractMoney = eiaContractLogList.get(0)?.contractMoney.toString()
                } else {
                    contractMoney = contractField.get(contract).toString()
                }
                def moneyStr = contractMoney.substring(0, contractMoney.lastIndexOf(".")) + contractMoney.substring(contractMoney.lastIndexOf(".") + 1)
                /** 金额的每3位用","隔开 */
                DecimalFormat df = new DecimalFormat(",###")
                def money = df.format(Double.parseDouble(moneyStr))
                /** 将合同金额转为大写 */
                def transformMoney = DateTransTools.transformMoney(Double.parseDouble(moneyStr))
                map.put("contractMoneyStr", money + "元" + " " + "(大写:" + transformMoney + ")")
            }
        }
        for (Field clientField : client.getClass().getDeclaredFields()) {
            clientField.setAccessible(true)
            if (!clientField.get(client)) {
                map.put(clientField.getName(), "")
            } else {
                map.put(clientField.getName(), clientField.get(client))
            }
        }
        if (contact) {
            for (Field contactField : contact.getClass().getDeclaredFields()) {
                contactField.setAccessible(true)
                if (!contactField.get(contact)) {
                    map.put(contactField.getName(), "")
                } else {
                    map.put(contactField.getName(), contactField.get(contact))
                }
            }
        } else {
            map.put("contactEmail", "")
        }
        def param = [:]
        param.staffId = (contract.inputUserId).toString()
        def staffJson = HttpConnectTools.getResponseJson(HttpUrlConstants.SINGLE_EIA_AUTH_STAFF_INFO, param)
        if (staffJson) {
            def staff = JsonHandler.jsonToMap(staffJson).data[0]
            if (staff.mailBox) {
                def mailBox = staff.mailBox
                map.put("mailbox", mailBox)
            } else {
                map.put("mailbox", "")
            }
            if (staff.cellphone) {
                def cellphone = staff.cellphone
                map.put("cellphone", cellphone)
            } else {
                map.put("cellphone", "")
            }
        } else {
            map.put("mailbox", "")
            map.put("cellphone", "")
        }
        if (contract.inputUser) {
            map.put("inputUser", contract.inputUser)
        } else {
            map.put("inputUser", "")
        }
        if (contract.eiaClientName) {
            map.put("clientName", contract.eiaClientName)
        } else {
            map.put("clientName", "")
        }
        if (contract.contactName) {
            map.put("contactName", contract.contactName)
        } else {
            map.put("contactName", "")
        }
        if (client) {
            if (client.clientFax) {
                map.put("clientFax", client.clientFax)
            } else {
                map.put("clientFax", "")
            }
        } else {
            map.put("clientFax", "")
            map.put("clientCorporate", "")
        }
        /** 判断是泰泽还是赤道进行导出操作，决定了受托方名称 */
        def contractTrust = contract?.contractTrust
        if (GeneConstants.CONTRACT_TRUST_CHIDAO == contractTrust) {
            def trusteeClient = EiaDomainCode.findByCodeAndDomain(GeneConstants.CHIDAO_NAME, GeneConstants.TRUSTEE_CLIENT)?.codeDesc
            if (trusteeClient) {
                map.put("trusteeClient", trusteeClient)
            } else {
                map.put("trusteeClient", "")
            }
        } else {
            def trusteeClient = EiaDomainCode.findByCodeAndDomain(GeneConstants.TAIZE_NAME, GeneConstants.TRUSTEE_CLIENT)?.codeDesc
            if (trusteeClient) {
                map.put("trusteeClient", trusteeClient)
            } else {
                map.put("trusteeClient", "")
            }
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月")
        map.put("nowDate", sdf.format(new Date()))
        /**
         * 加载模板文件
         */
        Template template = cfg.getTemplate(tempType + ".ftl", "UTF-8")
        /**
         * 得到要显示目的地的输出流
         */
        File file = new File(Holders.getConfig().getProperty("webroots.tempFilePath") + tempType + ".doc")
        Writer writer = new OutputStreamWriter(new FileOutputStream(file), "UTF-8")
        /**
         * 把数据写入到相应的输出流
         */
        template.process(map, writer)
        /**
         * 刷输出流,并关闭
         */
        writer.close()
        return file
    }
}
