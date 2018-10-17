/**
 *Created by HSH on 2018/3/5 19:20
 * 绘制覆盖物
 */
/**点编码**/
var POINT_CODE = 'Point';
/**线编码**/
var LINESTRING_CODE = 'LineString';
/**多边形编码**/
var POLYGON_CODE = 'Polygon';
/**圆编码**/
var CIRCLE_CODE = 'Circle';
/**绘制覆盖物列表**/
var drawOverlay = [];

/**
 * 绘制生命周期控制
 * @param geoType 绘制类型参考上述类型编码
 * @param drawStartHandle
 * @param drawEndHandle
 * @param inputId
 */

var tools
function drawEvent(geoType, drawStartHandle, drawEndHandle, inputId) {
    locationOverlays.clearOverlays();
    map.plugin(["AMap.MouseTool"], function () {
        map.setDefaultCursor("crosshair");
        if(tools){
            tools.close();
        }
         tools = new AMap.MouseTool(map);
        switch (geoType) {
            case POINT_CODE:
                tools.marker({zIndex: 999});
                break;
            case LINESTRING_CODE:
                tools.polyline({zIndex: 999});
                break;
            case POLYGON_CODE:
                tools.polygon({zIndex: 999,fillOpacity:0.3});
                break;
            case CIRCLE_CODE:
                tools.marker({zIndex: 999});
                break;
         }
        AMap.event.addListener(tools, 'draw', function (e) { //添加事件
            tools.close();
            var geoJSON = new AMap.GeoJSON();
            drawOverlay.push(e.obj);
            e.obj.setExtData({_geoJsonProperties: {"geoType": geoType}});
            geoJSON.addOverlay(e.obj);

            drawEndHandle(geoJSON);
            map.setDefaultCursor("default");
        });
    });
}
/**移除所有绘制的覆盖物**/
function removeDrawOverlay() {
    map.remove(drawOverlay);
    dataResult={};
}


/**绘制测量工具**/
var ruler;
var measureArea;
/**引入测量插件**/
map.plugin(["AMap.RangingTool","AMap.MouseTool"], function() {
    ruler = new AMap.RangingTool(map,{d:1});
    AMap.event.addListener(ruler, "end", function (e) {
        /**绘制结束关闭控件**/
        ruler.turnOff();
    });
    measureArea=new AMap.MouseTool(map);
    AMap.event.addListener(measureArea, "draw", function (e) {
        menuMark["measureArea"]=e.obj
        e.obj.on("rightclick",function(event){
            menuMeasure.open(map,event.lnglat);
        });
        measureArea.close();
    });
});

/**注册清除面积测量覆盖物**/
var menuMeasure=new AMap.ContextMenu();
menuMeasure.addItem("<i class='larry-icon'>&#xe8d0;</i> 清除",function(){
    measureArea.close(true);
    menuMark["measureArea"].setMap(null);
    menuMark={};
});
var menuMark={};

