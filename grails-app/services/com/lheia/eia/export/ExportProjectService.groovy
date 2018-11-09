package com.lheia.eia.export

import com.lheia.eia.cert.EiaCert
import com.lheia.eia.client.EiaClient
import com.lheia.eia.client.EiaClientContacts
import com.lheia.eia.common.GeneConstants
import com.lheia.eia.common.HttpMesConstants
import com.lheia.eia.common.HttpUrlConstants
import com.lheia.eia.config.EiaDomainCode
import com.lheia.eia.contract.EiaContract
import com.lheia.eia.project.EiaProject
import com.lheia.eia.tools.DateTransTools
import com.lheia.eia.tools.QRCodeTools
import com.lheia.eia.tools.RandomCodeTools
import freemarker.template.Configuration
import freemarker.template.Template
import grails.gorm.transactions.Transactional
import grails.util.Holders

import java.lang.reflect.Field
import java.text.SimpleDateFormat

@Transactional
class ExportProjectService {
    /**
     * 判断文件类型是否有对应的项目封皮
     */
    def checkProjectType(Long eiaProjectId){
        def eiaProject = EiaProject.findById(eiaProjectId)
        if (eiaProject) {
            def fileTypeChildCode = eiaProject.fileTypeChildCode
            if ((GeneConstants.MATCH_EXPORT_FILE_TYPE_CODE_LIST).indexOf(fileTypeChildCode) != -1) {
                return true
            } else {
                return HttpMesConstants.MSG_NO_MATCH_COVER
            }
        } else {
            return HttpMesConstants.MSG_NO_MATCH_COVER
        }
    }
    /**
     * 项目环评报告导出
     */
    def exportProject(Long eiaProjectId,params) {
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
            def fileTypeCode = eiaProject.fileTypeChildCode
            def exportType
            def exportTypeDoc
            /**
             * 环评报告表封皮
             */
            if ((GeneConstants.EXPORT_PROVE_FILE_TYPE_CODE_FROM_LIST).indexOf(fileTypeCode) != -1) {
                exportType = "EPC_HY_P.ftl"
                exportTypeDoc = "eiaReportHy.doc"
                /**
                 * 环评报告书封皮
                 */
            } else if((GeneConstants.EXPORT_PROVE_FILE_TYPE_CODE_BOOK_LIST).indexOf(fileTypeCode) != -1){
                exportType = "EPC_HP_P.ftl"
                exportTypeDoc = "eiaReportHp.doc"
            } else if((GeneConstants.EXPORT_PROVE_FILE_TYPE_CODE_HB_LIST).indexOf(fileTypeCode) != -1){
                exportType = "EPC_HB_P.ftl"
                exportTypeDoc = "eiaReportHb.doc"
            } else if((GeneConstants.EXPORT_PROVE_FILE_TYPE_CODE_YS_JC).indexOf(fileTypeCode) != -1){
                exportType = "EPC_YS_JC_P.ftl"
                exportTypeDoc = "eiaReportJc.doc"
            } else if((GeneConstants.EXPORT_PROVE_FILE_TYPE_CODE_YS_DC).indexOf(fileTypeCode) != -1){
                exportType = "EPC_YS_DC_P.ftl"
                exportTypeDoc = "eiaReportDc.doc"
            }else if((GeneConstants.EXPORT_PROVE_FILE_TYPE_CODE_GH_LIST).indexOf(fileTypeCode) != -1){
                exportType = "EPC_GH_P.ftl"
                exportTypeDoc = "eiaReportGh.doc"
            }else if((GeneConstants.EXPORT_PROVE_FILE_TYPE_CODE_XZ_LIST).indexOf(fileTypeCode) != -1){
                exportType = "EPC_XZ_P.ftl"
                exportTypeDoc = "eiaReportXz.doc"
            }else if((GeneConstants.EXPORT_PROVE_FILE_TYPE_CODE_ST_LIST).indexOf(fileTypeCode) != -1){
                exportType = "EPC_ST_P.ftl"
                exportTypeDoc = "eiaReportSt.doc"
            }else if((GeneConstants.EXPORT_PROVE_FILE_TYPE_CODE_YA_LIST).indexOf(fileTypeCode) != -1){
                exportType = "EPC_YA_P.ftl"
                exportTypeDoc = "eiaReportYa.doc"
            }else if((GeneConstants.EXPORT_PROVE_FILE_TYPE_CODE_PF_LIST).indexOf(fileTypeCode) != -1){
                exportType = "EPC_PF_P.ftl"
                exportTypeDoc = "eiaReportPf.doc"
            }else if((GeneConstants.EXPORT_PROVE_FILE_TYPE_CODE_LZ_LIST).indexOf(fileTypeCode) != -1){
                exportType = "EPC_LZ_NPBG_P.ftl"
                exportTypeDoc = "eiaReportNpbg.doc"
            }else if((GeneConstants.EXPORT_PROVE_FILE_TYPE_CODE_QJSCSH_LIST).indexOf(fileTypeCode) != -1){
                exportType = "EPC_LZ_QJSCSH_P.ftl"
                exportTypeDoc = "eiaReportQjscsh.doc"
            }else if((GeneConstants.EXPORT_PROVE_FILE_TYPE_CODE_QT_LIST).indexOf(fileTypeCode) != -1){
                exportType = "EPC_HB_P.ftl"
                exportTypeDoc = "eiaReportQt.doc"
            }else{
                exportType = "EPC_HP_P.ftl"
                exportTypeDoc = "eiaReportBook.doc"
            }
            template = cfg.getTemplate(exportType, "UTF-8")
            /**
             * 得到要显示目的地的输出流
             */
            String newFileName =(Holders.getConfig().getProperty("webroots.tempFilePath")+eiaProject.projectNo+exportTypeDoc)
            File file = new File(newFileName)
            Writer writer = new OutputStreamWriter(new FileOutputStream(file), "UTF-8")
            /**
             * 把数据写入到相应的输出流
             */
            def map = [:]
            def qrTools =  new QRCodeTools()
            if(!eiaProject.randomCode){
                def randomCode = RandomCodeTools.randomString(15)
                eiaProject.randomCode = randomCode
                eiaProject.save(flush: true, failOnError: true)
            }
            def urlStr = HttpUrlConstants.QRCODE_ADDR  + eiaProject.randomCode
            def imgStr = qrTools.makeQrCode(urlStr, "jpg", 200, 200)       //宽和高要与模板中的图片保持一致
            map.put("imageString", imgStr)
            def yearMonth
            Calendar now = Calendar.getInstance();
            if ((GeneConstants.EXPORT_PROVE_FILE_TYPE_CODE_FROM_LIST).indexOf(fileTypeCode) != -1) {
                map.put("projectName", eiaProject.projectName)
                yearMonth  = now.get(Calendar.YEAR)+"年"+(now.get(Calendar.MONTH) + 1)+"月"
                /**
                 * 报告书
                 */
            } else if((GeneConstants.EXPORT_PROVE_FILE_TYPE_CODE_BOOK_LIST).indexOf(fileTypeCode) != -1){
                if(eiaProject.projectName.length()>10){
                    map.put("projectName", eiaProject.projectName.substring(0,10))
                    map.put("projectNameS", eiaProject.projectName.substring(10))
                }else{
                    map.put("projectName", eiaProject.projectName)
                    map.put("projectNameS","")
                }
                yearMonth  = now.get(Calendar.YEAR)+"-"+(now.get(Calendar.MONTH) + 1)
                if(yearMonth){
                    yearMonth = DateTransTools.getUpperDate(yearMonth)
                }
                /**
                 * 现状,生态论证
                 */
            }else if((GeneConstants.EXPORT_PROVE_FILE_TYPE_CODE_XZ_LIST).indexOf(fileTypeCode) != -1 || (GeneConstants.EXPORT_PROVE_FILE_TYPE_CODE_ST_LIST).indexOf(fileTypeCode) != -1
                    || (GeneConstants.EXPORT_PROVE_FILE_TYPE_CODE_YA_LIST).indexOf(fileTypeCode) != -1  || (GeneConstants.EXPORT_PROVE_FILE_TYPE_CODE_PF_LIST).indexOf(fileTypeCode) != -1
                    || (GeneConstants.EXPORT_PROVE_FILE_TYPE_CODE_QJSCSH_LIST).indexOf(fileTypeCode) != -1){
                if(eiaProject.projectName.length()>10){
                    map.put("projectName", eiaProject.projectName.substring(0,10))
                    map.put("projectNameS", eiaProject.projectName.substring(10))
                }else{
                    map.put("projectName", eiaProject.projectName)
                    map.put("projectNameS","")
                }
                yearMonth  = now.get(Calendar.YEAR)+"年"+(now.get(Calendar.MONTH) + 1)+"月"
            }else{
                if(eiaProject.projectName.length()>10){
                    map.put("projectName", eiaProject.projectName.substring(0,10))
                    map.put("projectNameS", eiaProject.projectName.substring(10))
                }else{
                    map.put("projectName", eiaProject.projectName)
                    map.put("projectNameS","")
                }
                yearMonth  = now.get(Calendar.YEAR)+"-"+(now.get(Calendar.MONTH) + 1)
                if(yearMonth){
                    yearMonth = DateTransTools.getUpperDate(yearMonth)
                }
            }
            def client = EiaClient.findById(eiaProject?.eiaClientId)
            for (Field clientField : client.getClass().getDeclaredFields()) {
                clientField.setAccessible(true)
                if (!clientField.get(client)) {
                    map.put(clientField.getName(), "")
                } else {
                    map.put(clientField.getName(), clientField.get(client))
                }
            }
            def eiaContract
            def clientName = " "
            def contractTrust = EiaDomainCode.findByCodeAndDomain(GeneConstants.CHIDAO_NAME, GeneConstants.TRUSTEE_CLIENT)?.codeDesc
            if(eiaProject.eiaContractId){
                eiaContract = EiaContract.findByIdAndIfDel(eiaProject.eiaContractId,false)
                /**
                 * 如果甲方已存在，取甲方的客户信息
                 */
                if(eiaContract.ownerClientName){
                    clientName = eiaContract.ownerClientName
                }else{
                    clientName = eiaContract.eiaClientName
                }
                if (GeneConstants.CONTRACT_TRUST_CHIDAO != eiaContract?.contractTrust) {
                    contractTrust = EiaDomainCode.findByCodeAndDomain(GeneConstants.TAIZE_NAME, GeneConstants.TRUSTEE_CLIENT)?.codeDesc
                }
            }else{
                clientName = eiaProject.eiaClientName
            }
            map.put("contractTrust", contractTrust)
            if(clientName){
                map.put("clientName", clientName)
            }else{
                map.put("clientName", "")
            }
            if(eiaProject.projectNo){
                map.put("projectNo", eiaProject.projectNo)
            }else{
                map.put("projectNo", "")
            }
            if(yearMonth){
                map.put("compileDate", yearMonth)
            }else{
                map.put("compileDate", "")
            }
            def eiaCertList = EiaCert.findAllByEiaProjectIdAndIfDel(eiaProject?.id,false, [sort: "id", order: "desc"])
            if(eiaCertList){
                def certType = eiaCertList[0]?.certType
                if(GeneConstants.CERT_TYPE_APPROVAL.equals(certType)){
                    map.put("certType", "报批稿")
                }else{
                    map.put("certType", "送审稿")
                }
            }else {
                map.put("certType", "")
            }
            def contact = EiaClientContacts.findByEiaClientIdAndIfDel(eiaProject?.eiaClientId, false)
            if (contact) {
                map.put("clientTel", contact?.contactPhone)
            } else {
                map.put("clientTel", '')
            }
            map.put("dutyUser", eiaProject?.dutyUser)
            map.put("inputUser", eiaProject?.inputUser)
            map.put("entName", GeneConstants.EIA_LHCIS_ORG)
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月")
            map.put("date", sdf.format(new Date()))
            map.put("fileTypeChild", eiaProject?.fileTypeChild)
            template.process(map, writer)
            /**
             * 刷输出流,并关闭
             */
            writer.close()
            return file
        }
    }
}
