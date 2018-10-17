package com.lheia.eia.config

class EiaWorkDayConfig {

    /**
     * 日期
     */
    Date day
    /**
     * 是否是工作日（工作日：0，休息日：1，节假日：2）
     */
    Integer ifWorkDay
    /**
     * 备注
     */
    String memo

    static mapping = {
        day column: "day", sqlType: "date"
    }

    static constraints = {
        day nullable: true
        ifWorkDay nullable: true
        memo nullable: true
    }
}
