package com.lheia.eia.platForm

class EiaPlanShow {

    /**
     * 标题
     */
    String title
    /**
     * 公布日期
     */
    Date pubDate
    /**
     * 来源机关
     */
    String source
    /**
     * 源文件地址
     */
    String spiderFileUrl
    /**
     * 爬虫_pk
     */
    String spiderFilePk
    /**
     * 公布内容
     */
    String content
    /**
     * 规划图片
     */
    String spiderFileImagesUrl
    /**
     * 是否收藏
     */
    Boolean ifFav = false
    /**
     * 是否发布
     */
    Boolean ifPub = false
    /**
     * 是否删除
     */
    Boolean ifDel = false
    /**
     * 录人部门ID
     */
    Long inputDeptId
    /**
     * 录入部门
     */
    String inputDept
    /**
     * 录入部门Code
     */
    String inputDeptCode
    /**
     * 录入人ID
     */
    Long inputUserId
    /**
     * 录入人
     */
    String inputUser
    /**
     * 建设地点中心坐标E
     */
    String  geographicEast
    /**
     * 建设地点中心坐标N
     */
    String  geographicNorth
    /**
     * 建设地点线性坐标起点E
     */
    String  geographicStartEast
    /**
     * 建设地点线性坐标起点N
     */
    String geographicStartNorth
    /**
     * 建设地点线性坐标终点E
     */
    String  geographicEndEast
    /**
     * 建设地点线性坐标终点E
     */
    String geographicEndNorth

    static mapping = {
        content column: "content", sqlType: "LONGTEXT"
        spiderFileImagesUrl column: "spider_file_images_url", sqlType: "LONGTEXT"
        pubDate column: "pub_date", sqlType: "Date"
    }

    static constraints = {
        pubDate nullable:true
        source nullable: true
        title nullable: true
        spiderFileUrl nullable: true
        spiderFilePk nullable: true
        content nullable:true
        spiderFileImagesUrl nullable: true
        inputDeptId nullable:true
        inputDept nullable:true
        inputDeptCode nullable:true
        inputUserId nullable:true
        inputUser nullable:true
        geographicEast nullable:true
        geographicNorth nullable:true
        geographicStartEast nullable:true
        geographicStartNorth nullable:true
        geographicEndEast nullable:true
        geographicEndNorth nullable:true
        ifDel nullable:true
    }
}
