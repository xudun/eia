/**
 *Created by HSH on 2018/3/6 14:20
 */


function coor2LLObj(arr) {
    return new AMap.LngLat(arr[0], arr[1]);
}
/**
 * 给线和面geojson添加缓冲区
 * @param geoJSON
 */
function lonLatbuffer(geoJSON, buffer) {
    var geoJSONReader = new jsts.io.GeoJSONReader();
    var geoJSONWriter = new jsts.io.GeoJSONWriter();
    console.log(JSON.stringify(geoJSON));
    if (geoJSON.type == "LineString") {
        geoJSON.coordinates = geoJSON.coordinates.map(wGS84ToGuassian);
    } else if (geoJSON.type == "Polygon") {
        geoJSON.coordinates[0] = geoJSON.coordinates[0].map(wGS84ToGuassian);
    }
    console.log(JSON.stringify(geoJSON));
    var guassianJson = geoJSONWriter.write(geoJSONReader.read(JSON.stringify(geoJSON)).buffer(buffer));
    guassianJson.coordinates[0] = guassianJson.coordinates[0].map(guassianToWGS84);
    console.log(guassianJson);
    return guassianJson;
}

/**
 * 将高德覆盖物对象转化为简化版geoJSON
 * @param geo 高德覆盖物对象
 */
function geoToGeoJSON(geo) {
    var geoJSONObj = new AMap.GeoJSON();
    geoJSONObj.addOverlay(geo);
    return geoJSONObj.toGeoJSON()[0].geometry;
}

/**
 * 将简化版geoJSON转化为高德覆盖物对象 (需要添加属性)
 * @param geoJSON
 * @returns {*}
 */
function geoJSONTOGeo(geoJSON) {
    var geoJSONObj = new AMap.GeoJSON({geoJSON: [{"type": "Feature", "properties": {}, geometry: geoJSON}]});
    return geoJSONObj.getOverlays()[0];
}

/**
 * 高德坐标对象转化为[lon,lat]
 * @param ll
 * @returns {*[]}
 */
function lLObj2Coor(ll) {
    return [ll.getLng(), ll.getLat()];
}

/**
 * 根据坐标定位
 * @param arr
 */
function locationByCoord(arr) {
    map.setZoom(17);
    map.setCenter(coor2LLObj(arr));
    locationOverlays.addOverlay(new AMap.Marker({position: coor2LLObj(arr)}));
}

/**
 * 计算两个坐标方位角
 * @param arr1
 * @param arr2
 * @returns {number}
 */
function angle(arr1, arr2) {
    var x = Math.sin(arr2[0] - arr1[0]) * Math.cos(arr2[1]);
    var y = Math.cos(arr1[1]) * Math.sin(arr2[1]) - Math.sin(arr1[1]) * Math.cos(arr2[1]) * Math.cos(arr2[0] - arr1[0]);
    return Math.atan2(x, y) % 360;
}

/**
 * 计算一个点和覆盖物之间的距离
 * @param coord
 * @param geo
 */
function distanceFromCoorAndGeo(coord, geo) {
    return coord.distance(centerPointByGeo(geo));
}

/**
 * 返回覆盖物的一个点
 * @param geo
 * @returns {*}
 */
function centerPointByGeo(geo) {
    if (geo instanceof AMap.Marker) {
        return geo.getPosition();
    } else if (geo instanceof AMap.Polyline) {
        var coorArr = geo.getPath();
        var sumcoor = coorArr.reduce(function (coor1, coor2) {
            var c1;
            if (coor1 instanceof AMap.LngLat) {
                c1 = lLObj2Coor(coor1);
            } else {
                c1 = coor1;
            }
            var c2 = lLObj2Coor(coor2);
            return [c1[0] + c2[0], c1[1] + c2[1]];
        });
        return coor2LLObj([sumcoor[0] / coorArr.length, sumcoor[1] / coorArr.length]);
    } else if (geo instanceof AMap.Polygon) {
        var coorArr = geo.getPath()[0];
        var sumcoor = coorArr.reduce(function (coor1, coor2) {
            var c1;
            if (coor1 instanceof AMap.LngLat) {
                c1 = lLObj2Coor(coor1);
            } else {
                c1 = coor1;
            }
            var c2 = lLObj2Coor(coor2);
            return [c1[0] + c2[0], c1[1] + c2[1]];
        });
        return coor2LLObj([sumcoor[0] / coorArr.length, sumcoor[1] / coorArr.length])

    } else if (geo instanceof AMap.Circle) {
        return geo.getCenter();
    }
}

/***
 * 返回一个点和geoJSON表述的空间对象的距离
 * @param coord
 * @param geo
 */
function distanceFromCoorAndGeoJSON(coord, geo) {
    if (geo.type == "Point") {
        return coord.distance(coor2LLObj(geo.coordinates));
    } else if (geo.type == "LineString") {
        return coord.distance(geo.coordinates.map(coor2LLObj));
    } else if (geo.type == "Polygon") {
        return coord.distance(geo.coordinates[0].map(coor2LLObj));
    }
}

/**
 * 返回两geoJSON之间的距离
 * @param geo1
 * @param geo2
 */
function distanceFromGeoJSONAndGeoJSON(geo1, geo2) {
    return positionForGeoJSON(geo1).distance(positionForGeoJSON(geo2));
}

/**
 * 根据geoJSON返回一个高德坐标点对象
 * @param geoJSON
 * @returns {*}
 */
function positionForGeoJSON(geoJSON) {
    if (geoJSON.type == "Point") {
        return coor2LLObj(geoJSON.coordinates);
    } else if (geoJSON.type == "LineString") {
        return geoJSON.coordinates.map(coor2LLObj)[0];
    } else if (geoJSON.type == "Polygon") {
        return centerPointByGeo(geoJSONTOGeo(geoJSON))
    }
}

/**
 * 定位
 * @param marker
 */
var locationMarkerList = [];

function locationByMarker(marker) {
    if (marker instanceof AMap.OverlayGroup) {
        marker = marker.getOverlays()[0];
    }
    map.panTo(lLObj2Coor(centerPointByGeo(marker)));
    locationMarkerList = [];
    if (marker instanceof AMap.Marker) {
        marker.setAnimation('AMAP_ANIMATION_DROP');
    }
    locationMarkerList.push(marker);
}

var editorTools;

function startEditOverlay(geo) {
    if (geo.CLASS_NAME == "AMap.Marker") {
        layer.msg("点不支持编辑!");
    } else if (geo.CLASS_NAME == "AMap.Polyline") {
        if (editorTools) {
            editorTools.close();
        }
        editorTools = new AMap.PolyEditor(map, geo);
        editorTools.open();
        editorTools.on("end", function (e) {
            var overlay = e.target;
            var code = overlay.getExtData()._geoJsonProperties.id;
            getTempDataByCode(code).geoJson = JSON.stringify(geoToGeoJSON(e.target));
        });
    } else if (geo.CLASS_NAME == "AMap.Polygon") {
        if (editorTools) {
            editorTools.close();
        }
        editorTools = new AMap.PolyEditor(map, geo);
        editorTools.open();
        editorTools.on("end", function (e) {
            var overlay = e.target;
            var code = overlay.getExtData()._geoJsonProperties.id;
            getTempDataByCode(code).geoJson = JSON.stringify(geoToGeoJSON(e.target));
        });
    } else if (geo.CLASS_NAME == "AMap.Circle") {
        if (editorTools) {
            editorTools.close();
        }
        editorTools = new AMap.CircleEditor(map, geo);
        editorTools.open();
        editorTools.on("end", function (e) {
            var overlay = e.target;
            var code = overlay.getExtData()._geoJsonProperties.id;
            getTempDataByCode(code).geoJson = JSON.stringify({
                type: "Point",
                coordinates: lLObj2Coor(e.target.getCenter())
            });
            getTempDataByCode(code).geoRadius = e.target.getRadius();
        });
    }
}

function endEditOverlay(geo) {
    if (geo.CLASS_NAME == "AMap.Marker") {
        layer.msg("点不支持编辑!");
    } else if (geo.CLASS_NAME == "AMap.Polyline") {
        if (editorTools) {
            editorTools.close();
            editorTools = null;
        } else {
            layer.msg("不在编辑状态!");
        }
    } else if (geo.CLASS_NAME == "AMap.Polygon") {
        if (editorTools) {
            editorTools.close();
            editorTools = null;
        } else {
            layer.msg("不在编辑状态!");
        }
    } else if (geo.CLASS_NAME == "AMap.Circle") {
        if (editorTools) {
            editorTools.close();
            editorTools = null;
        } else {
            layer.msg("不在编辑状态!");
        }
    }
}

function middlePoint(coord1, coord2) {
    return [coord1[0] + coord2[0], coord1[1] + coord2[1]].map(function (num) {
        return num / 2
    });
}

/**计算像素距离**/
function lengthForCoordinate(coord1, coord2) {
    return Math.sqrt(Math.pow(coord1[0] - coord2[0], 2) + Math.pow(coord1[1] - coord2[1], 2));
}

/***
 * 锁定地图
 * 返回层级和中心点
 */
function lockMap() {
    var status = {
        dragEnable: false,//地图是否可通过鼠标拖拽平移，默认为true。此属性可被setStatus/getStatus 方法控制
        zoomEnable: false,//地图是否可缩放，默认值为true。此属性可被setStatus/getStatus 方法控制
        doubleClickZoom: false,//地图是否可通过双击鼠标放大地图，默认为true。此属性可被setStatus/getStatus 方法控制
        /*地图是否可通过键盘控制,默认为true
          方向键控制地图平移，"+"和"-"可以控制地图的缩放，
          Ctrl+“→”顺时针旋转，Ctrl+“←”逆时针旋转。
          此属性可被setStatus/getStatus 方法控制*/
        keyboardEnable: false,
        scrollWheel:false,//地图是否可通过鼠标滚轮缩放浏览，默认为true。此属性可被setStatus/getStatus 方法控制
    }
    map.setStatus(status);
    return {zoom:map.getZoom(),center:map.getCenter()};
}

/***
 * 解除地图锁定
 * @returns {{zoom: *|getZoom, center: ol.Coordinate|*}}
 */
function unlockMap(){
    var status = {
        dragEnable: true,//地图是否可通过鼠标拖拽平移，默认为true。此属性可被setStatus/getStatus 方法控制
        zoomEnable: true,//地图是否可缩放，默认值为true。此属性可被setStatus/getStatus 方法控制
        doubleClickZoom: true,//地图是否可通过双击鼠标放大地图，默认为true。此属性可被setStatus/getStatus 方法控制
        /*地图是否可通过键盘控制,默认为true
          方向键控制地图平移，"+"和"-"可以控制地图的缩放，
          Ctrl+“→”顺时针旋转，Ctrl+“←”逆时针旋转。
          此属性可被setStatus/getStatus 方法控制*/
        keyboardEnable: true,
        scrollWheel:true,//地图是否可通过鼠标滚轮缩放浏览，默认为true。此属性可被setStatus/getStatus 方法控制
    }
    map.setStatus(status);
    return {zoom:map.getZoom(),center:map.getCenter()};
}