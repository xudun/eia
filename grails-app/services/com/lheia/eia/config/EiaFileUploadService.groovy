package com.lheia.eia.config

import com.lheia.eia.common.GeneConstants
import com.lheia.eia.tools.DateTransTools
import grails.gorm.transactions.Transactional
import grails.util.Holders
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import java.text.SimpleDateFormat

@Transactional
class EiaFileUploadService {

    def getSaveFileName(String fileType){
        Date date = new Date()
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss")
        return sdf.format(date) + new Random().nextInt(9999) + "." + fileType
    }

    /***
     * 根据tableName获取文件类型
     * @param tableName
     */
    def getFileTypeList(String tableName){
       def codeList =  EiaDomainCode.findAllByDomainAndCodeRemark(GeneConstants.FILE_UPLOAD_TYPE, tableName,[sort: "displayOrder", order: "asc",])
        return codeList
    }

    /**
     * 文件上传
     * @param request ：HttpServletRequest
     * @param tableName : 文件所属的表名，用于文件路径分层
     * @param fileUploadType ：文件上传类型(名片、文件，等等)
     * @param grTableId ：文件所属的表中的记录的Id
     * @return
     */
    def upload(HttpServletRequest request, session, String tableName, Long tableId, String fileUploadType,String fileNote) {
        def file = request.getFile("file")//表单中type="file"的input的name属性值,Layui默认为file
        if (file == null) {
            return false
        }
        String fileName = file.getOriginalFilename()
        /**************************************获取文件类型**********************************************/
        def fileType = fileName.substring(fileName.lastIndexOf(".") + 1)
        String saveFileName = getSaveFileName(fileType)
        /**
         * 保存fileUpload表
         */
        def eiaFileUpload = new EiaFileUpload()
        eiaFileUpload.tableName = tableName
        eiaFileUpload.fileName = fileName
        eiaFileUpload.saveFileName = saveFileName
        eiaFileUpload.fileUploadType = fileUploadType
        eiaFileUpload.fileType = fileType
        eiaFileUpload.tableId = tableId
        eiaFileUpload.fileNote = fileNote
        eiaFileUpload.inputUser = session?.staff?.staffName
        eiaFileUpload.inputUserId = Long.parseLong(session?.staff?.staffId)
        eiaFileUpload.inputDept = session?.staff?.orgName
        eiaFileUpload.inputDeptCode = session?.staff?.orgCode
        eiaFileUpload.inputDeptId = Long.parseLong(session?.staff?.orgId)
        if (eiaFileUpload.save(flush: true, failOnError: true)) {
            /***
             * 文件路径：配置文件filePath/表名/表中Id/文件上传类型/文件Id/文件名.文件类型
             */
            String pathString = tableName + "/" + tableId + "/"  + fileUploadType + "/"  + eiaFileUpload.id
            File dirTemp = new File(Holders.getConfig().getProperty("webroots.filePath") + pathString)
            if (!dirTemp.exists()) {
                dirTemp.mkdirs()
            }
            def path = pathString + "/" + saveFileName;
            def targetFile = new File(Holders.getConfig().getProperty("webroots.filePath") + path)
            file.transferTo(targetFile)

            /*********************************文件写入完毕后保存表中的filePath字段******************************/
            eiaFileUpload.filePath = pathString
            eiaFileUpload.fileSize= targetFile.length()
            eiaFileUpload.save(flush: true, failOnError: true)
            if (targetFile.exists()) {
                def fileInfo = [:]
                fileInfo.filePath = eiaFileUpload.filePath
                fileInfo.fileId = eiaFileUpload.id
                fileInfo.fileName = eiaFileUpload.fileName
                fileInfo.fileSaveName =eiaFileUpload.saveFileName
                return fileInfo
            } else {
                return false
            }
        }

    }


    def delete(Long id) {
        def eiaFileUpload = EiaFileUpload.findByIdAndIfDel(id,false)
        String pathString = Holders.getConfig().getProperty("webroots.filePath")+eiaFileUpload?.filePath + "/" + eiaFileUpload?.saveFileName
        File file = new File(pathString)
        try {
            eiaFileUpload.ifDel = true
            eiaFileUpload.save(flush: true, failOnError: true)
            // 判断文件是否存在
            if (!file.exists()) {
                println("未找到文件信息")
                return false
            } else {
                if (file.delete()) {
                    File dir = new File(eiaFileUpload.filePath)
                    dir.delete()
                    println("删除成功")
                    return true
                } else {
                    return false
                }
            }
        } catch (e) {
            return false
        }
    }
    /**
     * 文件下载
     * @param request
     * @param response
     * @param params ：fileId
     * @return
     */
    def download(HttpServletRequest request, HttpServletResponse response, fileId) {
        def file = EiaFileUpload.findById(fileId)
        response.setContentType("application/octet-stream")
        String downLoadFileName = java.net.URLEncoder.encode(file.fileName, "UTF-8")
        String UserAgent = request.getHeader("USER-AGENT").toLowerCase();
        if (UserAgent.indexOf("firefox") >= 0) {
            downLoadFileName = new String(file.fileName.getBytes("UTF-8"), "iso-8859-1")
        }
        response.setHeader("Content-Disposition", "attachment;filename=\"" + downLoadFileName + "\"")
        def out = response.outputStream
        InputStream inputStream
        /** 原系统的附件没有存文件大小，根据是否存在文件大小来判断是否是原系统的文件 */
        if (file.fileSize) {
            inputStream = new FileInputStream(Holders.getConfig().getProperty("webroots.filePath")+file.filePath + "/" + file.saveFileName)
        } else {
            inputStream = new FileInputStream(Holders.getConfig().getProperty("webroots.oldFilePath")+file.filePath + "/" + file.saveFileName)
        }
        byte[] buffer = new byte[1024]
        int i = -1
        while ((i = inputStream.read(buffer)) != -1) {
            out.write(buffer, 0, i)
        }
        out.flush()
        out.close()
        inputStream.close()
    }

/**
 * 文件预览不直接打开
 * @param request
 * @param response
 * @param fileId
 * @return
 */
    def downloadView(HttpServletRequest request, HttpServletResponse response, fileId) {
        def file = EiaFileUpload.findById(fileId)
        response.setContentType("application/octet-stream")
        String downLoadFileName = java.net.URLEncoder.encode(file.fileName, "UTF-8")
        String UserAgent = request.getHeader("USER-AGENT").toLowerCase();
        if (UserAgent.indexOf("firefox") >= 0) {
            downLoadFileName = new String(file.fileName.getBytes("UTF-8"), "iso-8859-1")
        }
        response.reset();
        if("pdf".equals(file?.fileType)){

        }else{
            response.setContentType("application/x-msword");
            response.setHeader("Content-Disposition", "inline;filename=\"" + downLoadFileName + "\"")
        }
        def out = response.outputStream
        InputStream inputStream
        /** 原系统的附件没有存文件大小，根据是否存在文件大小来判断是否是原系统的文件 */
        if (file.fileSize>=0) {
            inputStream = new FileInputStream(Holders.getConfig().getProperty("webroots.filePath")+file.filePath + "/" + file.saveFileName)
        } else {
            inputStream = new FileInputStream(Holders.getConfig().getProperty("webroots.oldFilePath")+file.filePath + "/" + file.saveFileName)
        }
        byte[] buffer = new byte[1024]
        int i = -1
        while ((i = inputStream.read(buffer)) != -1) {
            out.write(buffer, 0, i)
        }
        out.flush()
        out.close()
        inputStream.close()
    }
    def getEiaFileUploadDataList(params){
        int page
        int limit
        if (params.page && params.limit) {
            page = params.int('page') - 1
            limit = params.int('limit')
        }
        def eiaFileUploadList = EiaFileUpload.createCriteria().list(max: limit, offset: page * limit) {
            /**
             * 查看全部的客户数据
             */
            eq("tableName",params.tableName)
            if (params.tableId) {
                eq("tableId",Long.valueOf(params.tableId))
            } else {
                eq("tableId",Long.valueOf(-1))
            }
            if (params.fileUploadType) {
                eq("fileUploadType", params.fileUploadType)
            }
            eq("ifDel", false)
            order("id", "desc")
            //不需要替换权限编码
        }
        def data = []
        eiaFileUploadList.each{
            def resMap = [:]
            resMap.putAll(it.properties)
            if (it.fileSize) {
                resMap.fileSize = getFileSize(Double.valueOf(it.fileSize))
            }
            resMap.uploadDate = DateTransTools.getFormatDateS(it.lastUpdated)
            resMap.id = it.id
            def domainCode = EiaDomainCode.findByDomainAndCode(GeneConstants.FILE_UPLOAD_TYPE, it.fileUploadType)
            if (domainCode) {
                resMap.fileUploadType = domainCode?.codeDesc
            }
            data << resMap
        }
        def dataMap = [:]
        dataMap.data = data
        dataMap.total = eiaFileUploadList?.totalCount
        return dataMap
    }

    def getEiaFileUploadDataMap(params){
        def dataMap = [:]
        Long eiaFileUploadId = Long.valueOf(params.eiaFileUploadId)
        def eiaFileUpload = EiaFileUpload.findByIfDelAndId(false,eiaFileUploadId)
        dataMap = this.convertToMap(eiaFileUpload.properties)
        def fileType = EiaDomainCode.findByDomainAndCode(GeneConstants.FILE_UPLOAD_TYPE, eiaFileUpload.fileUploadType)?.codeDesc
        dataMap.remove("fileUploadType")
        dataMap.put("fileUploadType", fileType)
        return dataMap
    }
    /***
     * 获取文件大小
     * @param num
     * @return
     */
    def getFileSize(double num){
        def unitList = ["B","KB","MB","GB","TB"]
        int index=0
        while (num>1024){
            num = num/1024
            index++
        }
        return String.format("%.2f",num)+unitList[index]
    }
    /**
     * 修改文件为只读属性
     */
    def UpdateFileReadOnly(String tableName,Long tableNameId){
        def fileList = EiaFileUpload.findAllByTableNameAndTableIdAndIfDel(tableName,tableNameId,false)
        fileList.each {
            it.ifReadOnly = false
            it.save(flush: true, failOnError: true)
        }
    }

    /**
     * 转换为map
     * @param obj
     */
    def convertToMap(Object obj) {
        def currMap = [:]

        for (Map.Entry<Integer, Integer> entry : obj.entrySet()) {
            currMap.put(entry.getKey(), entry.getValue())
        }
        return currMap
    }
}
