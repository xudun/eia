package com.lheia.eia.project

import grails.gorm.transactions.Transactional

import java.text.DecimalFormat

@Transactional
/**
 * 生成项目编号
 */
class GenProjectNoService {
    static scope = 'singleton'
    /**
     * 根据年份生成报价编号
     * @return
     */
    def getProjectNo(String ProjectDate, Long eiaProjectId) {
        def year = ProjectDate.split("-")[0]
        /**
         * 格式化报价号
         */
        DecimalFormat df = new DecimalFormat("0000")
        String projectNumFormat = df.format(eiaProjectId)
        String projectNo = "P-" + year + "-" +projectNumFormat
        return projectNo
    }
}
