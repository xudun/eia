package com.lheia.eia.export

import com.lheia.eia.common.GeneConstants
import grails.converters.JSON
import grails.gorm.transactions.Transactional

import javax.servlet.ServletOutputStream

@Transactional(readOnly = true)
class ExportContractController {

    def exportContractService

    /**
     * 检查合同类型是否符合导出合同模板的类型
     * @param id
     * @return
     */
    def checkProductType(Long contractId){
        def result = exportContractService.matchWrongType(contractId)
        if (result == "unpass") {
            render([data: false] as JSON)
        } else if (result == "noStaff") {
            render([data: "noStaff"] as JSON)
        } else {
            render([data: true] as JSON)
        }
    }

    /**
     * 合同模板导出
     * @param contractId
     */
    def downloadContract(Long contractId){
        File fileContract = null
        InputStream inputStreamContract = null
        ServletOutputStream servletOutputStreamContract = null
        try {
            /**
            * 调用导出word的方法
            */
            fileContract = exportContractService.exportContractToWord(contractId)
            inputStreamContract = new FileInputStream(fileContract)

            response.setCharacterEncoding("UTF-8")
            response.setContentType("application/msword")
            /**
             * 设置浏览器以下载的方式处理该文件
             */
            response.addHeader("Content-Disposition", "attachment;filename=" + fileContract.getName())

            servletOutputStreamContract = response.getOutputStream()
            byte[] buffer = new byte[1024*5]  // 缓冲区
            def bytesToRead = -1
            /**
             * 通过循环将读入的Word文件的内容输出到浏览器中
             */
            while((bytesToRead = inputStreamContract.read(buffer)) != -1) {
                servletOutputStreamContract.write(buffer, 0, bytesToRead)
            }
        }
        finally {
            if (inputStreamContract != null) inputStreamContract.close()
            if (servletOutputStreamContract != null) servletOutputStreamContract.close()
            if (fileContract != null) fileContract.delete()
        }
    }
    /**
     * 报价模板导出
     * @param id
     */
    def downloadOffer(Long eiaOfferId){
        File fileContract = null
        InputStream inputStreamContract = null
        ServletOutputStream servletOutputStreamContract = null
        try {
            /**
             * 调用导出word的方法
             */
            fileContract = exportContractService.exportOfferToWord(eiaOfferId)
            inputStreamContract = new FileInputStream(fileContract)

            response.setCharacterEncoding("UTF-8")
            response.setContentType("application/msword")
            /**
             * 设置浏览器以下载的方式处理该文件
             */
            response.addHeader("Content-Disposition", "attachment;filename=" + fileContract.getName())

            servletOutputStreamContract = response.getOutputStream()
            byte[] buffer = new byte[1024*5]  // 缓冲区
            def bytesToRead = -1
            /**
             * 通过循环将读入的Word文件的内容输出到浏览器中
             */
            while((bytesToRead = inputStreamContract.read(buffer)) != -1) {
                servletOutputStreamContract.write(buffer, 0, bytesToRead)
            }
        }
        finally {
            if (inputStreamContract != null) inputStreamContract.close()
            if (servletOutputStreamContract != null) servletOutputStreamContract.close()
            if (fileContract != null) fileContract.delete()
        }
    }
    /**
     * 合同补充协议、变更、终止协议模板导出
     * @param eiaContractId
     */
    def downloadContractProtocol(Long eiaContractId, String tempType){
        File fileContract = null
        InputStream inputStreamContract = null
        ServletOutputStream servletOutputStreamContract = null
        try {
            /**
             * 调用导出word的方法
             */
            fileContract = exportContractService.exportContractProtocal(eiaContractId, tempType)
            inputStreamContract = new FileInputStream(fileContract)

            response.setCharacterEncoding("UTF-8")
            response.setContentType("application/msword")
            /**
             * 设置浏览器以下载的方式处理该文件
             */
            response.addHeader("Content-Disposition", "attachment;filename=" + fileContract.getName())

            servletOutputStreamContract = response.getOutputStream()
            byte[] buffer = new byte[1024*5]  // 缓冲区
            def bytesToRead = -1
            /**
             * 通过循环将读入的Word文件的内容输出到浏览器中
             */
            while((bytesToRead = inputStreamContract.read(buffer)) != -1) {
                servletOutputStreamContract.write(buffer, 0, bytesToRead)
            }
        }
        finally {
            if (inputStreamContract != null) inputStreamContract.close()
            if (servletOutputStreamContract != null) servletOutputStreamContract.close()
            if (fileContract != null) fileContract.delete()
        }
    }
}
