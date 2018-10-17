/**
 *Created by HSH on 2018/3/5 18:05
 * 地图定义
 */

/**地图定义**/
var map = new AMap.Map('map', {
    resizeEnable: true,
    expandZoomRange:true,
    zooms:[0,20]
});
/**缩放插件 参考http://lbs.amap.com/api/javascript-api/reference/map-control**/
map.plugin(["AMap.ToolBar"], function() {
    map.addControl(new AMap.ToolBar());
});

if(location.href.indexOf('&guide=1')!==-1){
    map.setStatus({scrollWheel:false});
}

map.setDefaultCursor("DefaultCursor");

map.plugin(["AMap.Scale"],function(){
    var controlBar = new AMap.Scale();
    map.addControl(controlBar);
});
// map.on("click",function(e){console.log(e.pixel)})

function eventClickMap(ev,i){
    var marker = new AMap.Marker({
        map: parent.map,
        position:ev.lnglat ,
        icon: new AMap.Icon({
            size: new AMap.Size(40, 40),  /**图标大小**/
            image: "/gis/assets/location.png",
            imageOffset: new AMap.Pixel(-5, 0),
            imageSize: new AMap.Size(30, 30)
        })
    });
    matchOverlaysMap["mapRegister" + i] = marker;

}