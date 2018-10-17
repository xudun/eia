package com.lheia.eia.tools


/**
 * 高德api工具类
 * author：HSH
 * time:10:50
 **/
class AMapApiTools {

    /**
     * api key
     */
    static String KEY = "f5e69fca8ee63cc711b80f5b3d2e4467"
    /**
     * 搜索限定城市
     */
    static String CITY = "tianjin"
    /**
     * 每页结果
     */
    static String OFFSET = 10
    /**
     * 关键字搜索url
     */
    static String SEARCH_KEYWORDS_URL = "http://restapi.amap.com/v3/place/text"
    /**
     * 附近搜索url
     */
    static String SEARCH_AROUND_URL = "http://restapi.amap.com/v3/place/around"
    /**
     * 多边形搜索url
     */
    static String SEARCH_POLYGON_URL = "http://restapi.amap.com/v3/place/polygon"
    /**
     * 获取POI详情url
     */
    static String GET_POI_DETAIL = "https://www.amap.com/detail/get/detail"
    /**
     * 坐标转换
     */
    static String COORD_CONVERT = "https://restapi.amap.com/v3/assistant/coordinate/convert"
    /***
     * 行政区域规划查询
     */
    static String AREA_ADMIN_QUERY = "https://restapi.amap.com/v3/config/district"
    /**
     * 搜索限定城市（省级）
     */
    static List CITY_LIMIT_LIST = ["天津市","云南省","湖南省","山东省","河北省"]
    /***
     *
     */
    static String AREA_LV_DISTRICT = "district"
    /**
     * 行政区划分
     */
    /***
     * 获取POI详情
     * @param id
     * @return
     */
    static getPoiDetail(String id){
        def params = [:]
        def url = GET_POI_DETAIL
        params.id = id
        return  JsonHandler.jsonToMap(HttpConnectTools.getResponseJson(url, params, "GET"))
    }
    /**
     * 转换坐标系
     */
    static coordAllConvert(coordinates){
            def coordinateList = []
            def countForty = 40
            //40个坐标为一组进行转换提高效率
            StringBuilder strCoor = new StringBuilder()
            coordinates.each {
                strCoor.append(it.getLongitude())
                strCoor.append(",")
                strCoor.append(it.getLatitude())
                strCoor.append("%7C")
                countForty--
               if(countForty <= 0){
                   coordinateList.addAll(AMapApiTools.coordConvert(strCoor.toString()))
                   countForty=40
                   strCoor = new StringBuilder()
               }
        }
        if(strCoor.length()>0){
            coordinateList.addAll(AMapApiTools.coordConvert(strCoor.toString()))
        }
        return coordinateList
    }


    /**
     * 关键检索地名
     * @param keywords
     * @return
     */
    static getSearchKeywordsDataList(String keywords,String city,int page,int offset) {
        def params = [:]
        def url = SEARCH_KEYWORDS_URL
        params.key = KEY
        params.offset = offset
        params.page = page
        params.keywords = keywords
        params.children = 0
        return  JsonHandler.jsonToMap(HttpConnectTools.getResponseJson(url, params, "GET"))
    }
    /**
     * 附近搜索
     * @param type
     * @param center
     * @param radius
     * @return
     */
    static getSearchAroundDataList(String types, String center, String radius) {
        def params = [:]
        def url = SEARCH_AROUND_URL
        params.key = KEY
        params.location = center
        //params.city = CITY
        params.types = types
        params.offset = OFFSET
        params.radius = radius
        params.citylimit = true
        return readAllPageFromPagingApi(url, params)
    }

    static getSearchPolygonDataList(String types, String polygonCoords) {
        def params = [:]
        def url = SEARCH_POLYGON_URL
        params.key = KEY
        params.polygon = polygonCoords
        params.offset = OFFSET
        //params.city = CITY
        params.types = types
        params.citylimit = true
        return readAllPageFromPagingApi(url, params)
    }
    /**
     * 从带分页api中读取所有数据
     * @param url
     * @param params
     */
    static readAllPageFromPagingApi(String url, Map params) {
        int count=Integer.valueOf(JsonHandler.jsonToMap(HttpConnectTools.getResponseJson(url, params, "GET")).count)
        int pageNum=0;
        if(count%Integer.valueOf(OFFSET)==0){
             pageNum=count/Integer.valueOf(OFFSET)
        }else{
            pageNum=count/Integer.valueOf(OFFSET)+1
        }
        def dataList=[]
        (1..pageNum).each ({
            params.page=it
            dataList.addAll(JsonHandler.jsonToMap(HttpConnectTools.getResponseJson(url, params, "GET")).pois)
        })
        return dataList;
    }

    /***
     * 返回省级单位的行政区划
     */
    static adminAreaForProvince(String provinceName){
        def params = [:]
        def url = AREA_ADMIN_QUERY
        params.keywords = provinceName
        params.subdistrict = "1"
        params.key = KEY
        def dataMap = JsonHandler.jsonToMap(HttpConnectTools.getResponseJson(url, params,"GET")).districts[0]
        def resList = []
        dataMap.districts.each{
            if(it.level == AREA_LV_DISTRICT){
                it.coordinates = adminAreaNameToCoordinateArr(it.name)
                it.features = []
                resList << it
            }
        }
        return resList
    }
    /***
     * 返回当前行政区划的边界坐标数组
     */
    static adminAreaNameToCoordinateArr(String provinceName){
        def params = [:]
        def url = AREA_ADMIN_QUERY
        params.keywords = provinceName
        params.subdistrict = "0"
        params.extensions = "all"
        params.key = KEY
        def dataMap =JsonHandler.jsonToMap( HttpConnectTools.getResponseJson(url, params,"GET"))
        for(def i =0 ;i<dataMap?.districts?.size();i++){
            if(dataMap?.districts[i].level == AREA_LV_DISTRICT){
                return dataMap?.districts[0]?.polyline
            }
        }
    }
}