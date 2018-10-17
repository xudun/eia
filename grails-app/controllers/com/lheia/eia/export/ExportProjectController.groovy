package com.lheia.eia.export

import com.lheia.eia.common.HttpMesConstants
import grails.converters.JSON

import javax.servlet.ServletOutputStream

class ExportProjectController {
    def exportProjectService
    /**
     * 判断文件类型是否有对应的项目封皮
     * @return
     */
    def checkProjectType() {
        def result = exportProjectService.checkProjectType(params.long('eiaProjectId'))
        if (result != HttpMesConstants.MSG_NO_MATCH_COVER) {
            render([code: HttpMesConstants.CODE_OK] as JSON)
        } else {
            render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_NO_MATCH_COVER] as JSON)
        }
    }
    /**
     * 导出项目信息
     */
    def exportProject(){
        File fileProject = null
        InputStream inputStreamContract = null
        ServletOutputStream servletOutputStreamContract = null
        try {
            /**
             * 调用导出word的方法
             */
            fileProject = exportProjectService.exportProject(Long.valueOf(params.eiaProjectId),params)
            if (!fileProject) {
                render([code: HttpMesConstants.CODE_FAIL, msg: HttpMesConstants.MSG_DATA_NULL] as JSON)
            }
            inputStreamContract = new FileInputStream(fileProject)

            response.setCharacterEncoding("UTF-8")
            response.setContentType("application/msword")
            /**
             * 设置浏览器以下载的方式处理该文件
             */
            response.addHeader("Content-Disposition", "attachment;filename=" + fileProject.getName())

            servletOutputStreamContract = response.getOutputStream()
            byte[] buffer = new byte[1024 * 5]  // 缓冲区
            def bytesToRead = -1
            /**
             * 通过循环将读入的Word文件的内容输出到浏览器中
             */
            while ((bytesToRead = inputStreamContract.read(buffer)) != -1) {
                servletOutputStreamContract.write(buffer, 0, bytesToRead)
            }
        }
        finally {
            if (inputStreamContract != null) inputStreamContract.close()
            if (servletOutputStreamContract != null) servletOutputStreamContract.close()
            if (fileProject != null) fileProject.delete()
        }
    }
}
