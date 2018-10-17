/**
 * 加载数据到地图上
 *Created by HSH on 2018/3/6 10:54
 */
var menu;

/***
 * 加载数据
 * @param geoData
 */
function loadingDB(geoData) {
    var marker;
    if(dBOverlaysMap[geoData.code]){
        dBOverlaysMap[geoData.code].setMap(null);
    }
    // if(geoData.code){
    //     console.log(geoData.code);
    // }
    var geoJson = JSON.parse(geoData.geoJson);
    if (geoData.geoRadius) { //绘制圆
        var center = geoJson.coordinates;
        var marker = new AMap.Circle({
            center: new AMap.LngLat(center[0], center[1]),
            radius: geoData.geoRadius * 1,
            strokeStyle: geoData.config.strokeStyle ? geoData.config.strokeStyle : "dashed",
            strokeDasharray: [10, 10],
            strokeColor: geoData.config.strokeColor ? "#"+geoData.config.strokeColor : "blue",
            strokeWeight:geoData.config.strokeWeight*1 ? geoData.config.strokeWeight*1 : 3,
            strokeOpacity:geoData.config.strokeOpacity/100 ? geoData.config.strokeOpacity/100 : 1,
            fillOpacity: geoData.config.fillOpacity/100 ? geoData.config.fillOpacity/100 : 0,
            fillColor:  geoData.config.fillColor ? "#"+geoData.config.fillColor : "blue",
            zIndex:geoData.config.zIndex*1 ? geoData.config.zIndex*1 : 0
        });
        marker.setExtData({type: "DBOverlays", _geoJsonProperties: {id: geoData.code,"geoType": "Circle",geoName:geoData.geoName}});

        dBOverlaysMap["" + geoData.code] = marker;
        if(geoData.config.ifDisplay){
            dBOverlays.addOverlay(marker);
        }

        /**渲染空间对象名称**/
        if (geoData.config.ifDisplayName) {
            displayGeoName(dBOverlaysMap[geoData.code], geoData.geoName, geoData.code,geoData.config.textPos,geoData.config.ifDisplay);
        }
    } else if (geoJson.type == "Point") {
        /**绘制点**/
        /**如果有贴图的话优先显示贴图**/
        if (geoData.config.picType) {
            var marker = new AMap.Marker({
                map: map,
                position: coor2LLObj(geoJson.coordinates),
                icon: new AMap.Icon({
                    size: new AMap.Size(40, 40),  /**图标大小**/
                    image: "/gis/assets/monitorIcon/" + geoData.config.picType + ".png",
                    imageOffset: new AMap.Pixel(0, 0),
                    imageSize: new AMap.Size(30, 30)
                }),
                zIndex:geoData.config.zIndex*1 ? geoData.config.zIndex*1 : 0
            });
            marker.setExtData({type: "DBOverlays", _geoJsonProperties: {id: geoData.code,"geoType": "Point",geoName:geoData.geoName}});
            dBOverlaysMap["" + geoData.code] = marker;
            if(geoData.config.ifDisplay){
                dBOverlays.addOverlay(marker);
            }
            /**渲染空间对象名称**/
            if (geoData.config.ifDisplayName) {
                displayGeoName(dBOverlaysMap["" + geoData.code], geoData.geoName, geoData.code,geoData.config.textPos,geoData.config.ifDisplay);
            }
        } else {
            AMapUI.loadUI(['overlay/SimpleMarker'], function (SimpleMarker) {
                var marker = new SimpleMarker({
                    iconLabel: geoData.benchIndex,
                    iconTheme: 'numv2',
                    iconStyle: geoData.code.split("")[0] == "b" ? 'red' : 'lightblue',
                    position: coor2LLObj(geoJson.coordinates)
                });
                marker.setExtData({type: "DBOverlays", _geoJsonProperties: {id: geoData.code,"geoType": "Point",geoName:geoData.geoName}});
                dBOverlaysMap["" + geoData.code] = marker;
                if(geoData.config.ifDisplay){
                    dBOverlays.addOverlay(marker);
                }
                /**渲染空间对象名称**/
                if (geoData.config.ifDisplayName) {
                    displayGeoName(marker, geoData.geoName, geoData.code,geoData.config.textPos,geoData.config.ifDisplay);
                }
            });
        }
    } else {
        /**绘制线和面**/
        var featureObj = [];
        featureObj[0] = {};
        featureObj[0].geometry = JSON.parse(geoData.geoJson);
        featureObj[0].type = "Feature";
        featureObj[0].properties = {id: geoData.code,"geoType": featureObj[0].geometry.type,geoName:geoData.geoName};
        var tempGeoJSON = new AMap.GeoJSON({geoJSON: featureObj});
        marker = tempGeoJSON.getOverlays()[0];
        marker.setOptions({
            strokeStyle: geoData.config.strokeStyle ? geoData.config.strokeStyle : "solid",
            strokeColor: geoData.config.strokeColor ? "#"+geoData.config.strokeColor : "blue",
            strokeOpacity: geoData.config.strokeOpacity*1 / 100 ? geoData.config.strokeOpacity*1 / 100 : 1,
            strokeWeight:geoData.config.strokeWeight*1  ? geoData.config.strokeWeight*1 : 3,
            fillOpacity: geoData.config.fillOpacity / 100 ? geoData.config.fillOpacity / 100 : 0,
            fillColor: geoData.config.fillColor ? "#"+geoData.config.fillColor : "blue",
            zIndex:geoData.config.zIndex*1 ? geoData.config.zIndex*1 : 0
        });
        dBOverlaysMap["" + geoData.code] = marker;
        if(geoData.config.ifDisplay){
            dBOverlays.addOverlay(marker);
        }
        /**渲染空间对象名称**/
        if (geoData.config.ifDisplayName) {
            displayGeoName(dBOverlaysMap["" + geoData.code], geoData.geoName, geoData.code, geoData.config.textPos,geoData.config.ifDisplay);
        }
    }

}


/**加载数据库空间对象*/
function loadDB(result) {
    if (result.data) {
        for (var i = 0; i < result.data.length; i++) {
            loadingDB(result.data[i]);
        }
    }
}

/**显示空间对象名称**/
function displayGeoName(geo, name, id,textPos,ifDisPlay) {
    var textMarker = new AMap.Text({
        text: name,
        offset: new AMap.Pixel(-2, 10),
        position: textPos?new AMap.LngLat(textPos.split(",")[0]*1,textPos.split(",")[1]*1):centerPointByGeo(geo),
        zIndex:100000,
        style: {
            opacity: 0.6
        }
    });
    textMarker.setExtData({type: "DBTextOverlays", _geoJsonProperties: {id: id,"geoType": "Text",geoName:name}});
    if(ifDisPlay){
        textMarker.setMap(map);
    }


    textOverlaysMap[id + ""] = textMarker;
}
function loadDbImg(matchData){
    var ws=new AMap.LngLat(matchData.southwestPoint.split(",")[0]*1,matchData.southwestPoint.split(",")[1]*1);
    var en=new AMap.LngLat(matchData.northeastPoint.split(",")[0]*1,matchData.northeastPoint.split(",")[1]*1);
    loadImg(ws,en,matchData.code);
    imageLayerMap[matchData.code].setOpacity(matchData.opacity*1/100)
}


function loadImg(ws,en,code){
    var imageLayer=new AMap.ImageLayer({
        bounds:new AMap.Bounds(ws,en),
        url:request_url_root + tempData.matchMap[code].fileSrc + "/" + tempData.matchMap[code].fileName,
        visible:true,
        map:map,
        zIndex:9999,
        zooms:[0,20]
    });
    if(imageLayerMap[code]){
        imageLayerMap[code].setMap(null);
    }
    imageLayerMap[code]=imageLayer;

}
