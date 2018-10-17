/**
 *Created by HSH on 2018/3/5 10:32
 */
var mapLayer = {};
/**定义谷歌地图源**/
mapLayer["google"] = new AMap.TileLayer({
    // 图块取图地址
    tileUrl: 'http://www.google.cn/maps/vt/pb=!1m4!1m3!1i[z]!2i[x]!3i[y]!' +
    '2m3!1e0!2sm!3i380072576!3m8!2szh-CN!3scn!5e1105!12m4!1e68!2m2!1sset!2sRo' +
    'admap!4e0!5m1!1e0'
    //zIndex: 100
});
/**定义谷歌卫星地图源**/
mapLayer["googleSatellite"] = new AMap.TileLayer({
    // 图块取图地址
    //tileUrl: 'http://www.google.cn/maps/vt?lyrs=s@189&gl=cn&x=[x]&y=[y]&z=[z]',
    tileUrl: 'http://mt2.google.cn/vt/lyrs=y&hl=zh-CN&gl=CN&src=app&x=[x]&y=[y]&z=[z]&s=G',
    zIndex: 100
});
/**定义谷歌卫星（无标注）地图源**/
mapLayer["googleSatelliteNoMark"] = new AMap.TileLayer({
    // 图块取图地址
    //tileUrl: 'http://www.google.cn/maps/vt?lyrs=s@189&gl=cn&x=[x]&y=[y]&z=[z]',
    tileUrl: 'http://www.google.cn/maps/vt?lyrs=s@189&gl=cn&x=[x]&y=[y]&z=[z]',
    zIndex: 100
});


mapLayer["graticule"] = new AMap.TileLayer.Flexible({
    cacheSize: 30,
    zIndex: 200,
    createTile: function (x, y, z, success, fail) {
        var c = document.createElement('canvas');
        var lnglat = map.pixelToLngLat(new AMap.Pixel((x) * 256, (y) * 256))
        c.width = c.height = 256;
        var cxt = c.getContext("2d");
        cxt.font = "15px Verdana";
        cxt.fillStyle = "#ff0000";
        cxt.strokeStyle = "#ff0000";
        cxt.strokeRect(0, 0, 256, 256)
        cxt.fillText('(' + [Math.round(lnglat.getLng()*100)/100, Math.round(lnglat.getLat()*100)/100, z].join(',') + ')', 10, 30)
        success(c)//通知API切片创建完成
    }
});

/***
 * 切换地图
 * @param layername
 */
function displayLayer(layername) {
    for (var key in mapLayer) {
        if (layername == key) {
            mapLayer[key].setMap(map)
        } else {
            mapLayer[key].setMap(null);
        }
    }
}

/**设置默认地图**/
displayLayer("googleSatellite");
/**定义数据库层架加载覆盖物容器**/
var dBOverlays = new AMap.OverlayGroup();
dBOverlays.setMap(map);
var dBOverlaysMap = {};

/**清理容器中所有覆盖物**/
function clearDBOverlays() {
    Object.keys(dBOverlaysMap).map(function (olKey) {
        dBOverlaysMap[olKey].setMap(null);
    });
    dBOverlaysMap = {};
}

function hideDboverlays() {
    Object.keys(dBOverlaysMap).map(function (olKey) {
        dBOverlaysMap[olKey].setMap(null);
    });
    Object.keys(textOverlaysMap).map(function (olKey) {
        textOverlaysMap[olKey].setMap(null);
    });
}

function showDboverlays() {
    Object.keys(dBOverlaysMap).map(function (olKey) {
        if (getTempDataByCode(olKey).config.ifDisplay) {
            dBOverlaysMap[olKey].setMap(map);
        }
    });
    Object.keys(textOverlaysMap).map(function (olKey) {
        if (getTempDataByCode(olKey).config.ifDisplay) {
            textOverlaysMap[olKey].setMap(map);
        }
    });
}

/**文字覆盖物容器**/
var textOverlaysMap = {};
/**属性弹窗容器**/
var propInfoWindow = {}

/**清理所有文字覆盖物**/
function clearTextOverlays() {
    Object.keys(textOverlaysMap).map(function (olKey) {
        textOverlaysMap[olKey].setMap(null);
    });
    textOverlaysMap = {};
}

var matchOverlaysMap = {};
/**定位覆盖物容器**/
var locationOverlays = new AMap.OverlayGroup();
locationOverlays.setMap(map);
var imageLayerMap = {};
/**图层控制控件**/
AMapUI.loadUI(['control/BasicControl'], function (BasicControl) {
    var layerCtrl2 = new BasicControl.LayerSwitcher({
        // theme: 'dark',
        //自定义基础图层
        baseLayers: [{
            id: 'google',
            name: '谷歌地图',
            layer: mapLayer["google"]
        }, {
            enable: true,
            id: 'googleSatellite',
            name: '谷歌卫星地图',
            layer: mapLayer["googleSatellite"]
        }, {
            id: 'googleSatelliteNoMark',
            name: '谷歌卫星地图(无标注)',
            layer: mapLayer["googleSatelliteNoMark"]
        }, {
            id: 'aMap',
            name: '高德地图',
            layer: new AMap.TileLayer()
        }, {
            id: 'aMap',
            name: '高德卫星地图',
            layer: new AMap.TileLayer.Satellite()
        }
        ], overlayLayers: [{
            enable: false,
            id: 'traffic',
            name: '路况图',
            layer: new AMap.TileLayer.Traffic()
        }, {
            enable: false,
            id: 'roadNet',
            name: '路网图',
            layer: new AMap.TileLayer.RoadNet()
        }, {
            enable: false,
            id: 'graticule',
            name: '经纬网',
            layer: mapLayer["graticule"]
        }]
    });
    map.addControl(layerCtrl2);
});