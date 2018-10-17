package com.lheia.eia.config

class EiaSpiderLog {

    /**
     * 执行开日时间
     */
    Date startTime
    /**
     * 执行结束时间
     */
    Date endTime
    /**
     * 更新记录数
     */
    Integer recordNo
    /**
     * 同步日期
     */
    Date syncDate
    /**
     * 同步数据表
     */
    String tableName

    Date dateCreated
    Date lastUpdated

    static mapping = {
        syncDate column: "sync_date", sqlType: "date"
    }

    static constraints = {
        startTime nullable:true
        endTime nullable:true
        recordNo nullable:true
        syncDate nullable:true
        tableName nullable:true
    }

}
