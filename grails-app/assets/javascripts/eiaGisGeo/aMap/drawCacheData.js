/**
 *Created by HSH on 2018/3/15 17:01
 */

/**页面数据定义**/
var tempData = {};
tempData.benchMap = {};
tempData.otherMap = {};
tempData.targetMap = {};
tempData.matchMap={};

/**
 * 判断是否出现环保目标选项
 */
function hasTargetChoose() {
    return tempData.benchList.length;
}

/**
 * 返回基准对象下拉json
 * @returns {any[]}
 */
function selectBenchData() {
    var benchCodeList = [];
    for (var key in tempData.benchMap) {
        var benchCodeMap = {};
        benchCodeMap.code = tempData.benchMap[key].geoName;
        benchCodeMap.codeDesc = tempData.benchMap[key].geoName;
        benchCodeMap.index = tempData.benchMap[key].index;
        benchCodeList.push(benchCodeMap);
    }
    return benchCodeList;
}

/***
 * 基准目标列表构建
 * @returns {Array}
 */
function benchTableList() {
    var benchList = [];
    for (var k in tempData.benchMap) {
        var benchTableMap = {};
        benchTableMap.LAY_CHECKED = tempData.benchMap[k].config.ifDisplay;
        benchTableMap.geoId = tempData.benchMap[k].geoId;
        benchTableMap.geoRadius = tempData.benchMap[k].geoRadius;
        benchTableMap.picType = tempData.benchMap[k].picType;
        benchTableMap.benchId = tempData.benchMap[k].benchId;
        benchTableMap.code = tempData.benchMap[k].code;
        benchTableMap.config = tempData.benchMap[k].config;
        benchTableMap.geoName = tempData.benchMap[k].geoName;
        benchTableMap.geoJson = tempData.benchMap[k].geoJson;
        benchTableMap.objType = "基准空间对象";
        benchList.push(benchTableMap);
    }
    return benchList
}

/**
 * 环保目标里列表构建
 * @returns {Array}
 */
function targetTableList() {
    var targetList = [];
    for (var k in tempData.targetMap) {
        var targetTableMap = {};
        targetTableMap.LAY_CHECKED = tempData.targetMap[k].config.ifDisplay;
        targetTableMap.geoId = tempData.targetMap[k].geoId;
        targetTableMap.geoRadius = tempData.targetMap[k].geoRadius;
        targetTableMap.targetId = tempData.targetMap[k].targetId;
        targetTableMap.picType = tempData.targetMap[k].picType;
        targetTableMap.code = tempData.targetMap[k].code;
        targetTableMap.geoName = tempData.targetMap[k].geoName;
        targetTableMap.geoJson = tempData.targetMap[k].geoJson;
        targetTableMap.config = tempData.targetMap[k].config;
        targetTableMap.benchIndex = tempData.targetMap[k].benchIndex;
        targetTableMap.benchName = tempData.targetMap[k].benchCode ? tempData.benchMap[tempData.targetMap[k].benchCode].geoName : "";
        targetTableMap.benchCode = tempData.targetMap[k].benchCode;
        targetTableMap.benchId = tempData.targetMap[k].benchId;
        targetTableMap.objType = "环保目标";
        targetList.push(targetTableMap);
    }
    return targetList;
}

/**
 * 辅助空间对象列表构建
 * @returns {Array}
 */
function otherTableList() {
    var otherList = [];
    for (var k in tempData.otherMap) {
        var otherTableMap = {};
        otherTableMap.LAY_CHECKED =tempData.otherMap[k].config.ifDisplay
        otherTableMap.geoId = tempData.otherMap[k].geoId;
        otherTableMap.code = tempData.otherMap[k].code;
        otherTableMap.picType = tempData.otherMap[k].picType;
        otherTableMap.geoRadius = tempData.otherMap[k].geoRadius;
        otherTableMap.geoName = tempData.otherMap[k].geoName;
        otherTableMap.config = tempData.otherMap[k].config;
        otherTableMap.geoJson = tempData.otherMap[k].geoJson;
        otherTableMap.objType = "辅助空间对象";
        otherList.push(otherTableMap);
    }
    return otherList;
}
/**
 * 图片配准对象列表构建
 * @returns {Array}
 */
function matchTableList() {
    var matchList = [];
    for (var k in tempData.matchMap) {
        var matchTableMap = {};
        matchTableMap.matchId = tempData.matchMap[k].matchId;
        matchTableMap.opacity = tempData.matchMap[k].opacity;
        matchTableMap.southwestPoint = tempData.matchMap[k].southwestPoint;
        matchTableMap.northeastPoint = tempData.matchMap[k].northeastPoint;
        matchTableMap.fileSize = (tempData.matchMap[k].fileSize*1 / 1024).toFixed(1) + 'kb';
        matchTableMap.fileName = tempData.matchMap[k].fileName;
        matchTableMap.uploadTime = tempData.matchMap[k].uploadTime;
        matchTableMap.isMatch=tempData.matchMap[k].southwestPoint?"已配准":"未配准";
        matchTableMap.code=tempData.matchMap[k].code;
        matchTableMap.status="已上传";
        matchTableMap.objType = "配准图片";
        matchTableMap.LAY_CHECKED=false;
        matchList.push(matchTableMap);
    }
    return matchList;
}

/***
 * 列表类型分发
 * @param type 参数为空的话返回全部
 * @returns {*}
 */
function tempTableList(type) {
    switch (type) {
        case "bench":
            return benchTableList();
        case "other":
            return otherTableList();
        case "target":
            return targetTableList();
        case "match":
            return matchTableList();
        default:
            return benchTableList().concat(targetTableList(), otherTableList());
    }
}

/**
 * 根据code获取临时数据
 */
function getTempDataByCode(code) {
    var typeAndIndex = code.split("");
    if (typeAndIndex[0] == "t") {//环保目标
        return tempData.targetMap[code];
    } else if (typeAndIndex[0] == "a") {
        return tempData.otherMap[code];
    } else if (typeAndIndex[0] == "b") {
        return tempData.benchMap[code];
    }
}

/**根据code删除临时数据**/
function deleteTempDataByCode(code) {
    var typeAndIndex = code.split("");
    if(typeAndIndex[0]=="m"){
        delete tempData.matchMap[code];
        return;
    }
    getMakerByTableCode(code).setMap(null);
    delete textOverlaysMap[code];
    delete dBOverlaysMap[code];
    if (typeAndIndex[0] == "t") {//环保目标
        var benchName = tempData.targetMap[code].benchName;
        for (var k in tempData.benchMap) {
            if (tempData.benchMap[k].geoName == benchName) {
                var index = tempData.benchMap[k].targetCodeList.indexOf(code);
                if (index > -1) {
                    tempData.benchMap[k].targetCodeList.splice(index, 1);
                }
                /**重新给序号排序**/
                tempData.benchMap[k].targetCodeList.map(function (t) {
                    tempData.targetMap[t].benchIndex = tempData.benchMap[k].targetCodeList.indexOf(t) + 1;
                });
            }
        }
        if (tempData.targetMap[code].targetId) {
            ajaxBox(request_url_root + "/gisGeoProject/gisGeoProjectTargetDelete", {
                projectId: tempData.projectId,
                geoId: tempData.targetMap[code].geoId
            }, function () {
                console.log("删除环保目标");
            }, "false");
        }
        delete tempData.targetMap[code];
    } else if (typeAndIndex[0] == "a") {
        if (typeAndIndex[1] == "s") {
            ajaxBox(request_url_root + "/gisGeoProject/gisGeoProjectOtherDelete", {
                projectId: tempData.projectId,
                geoId: tempData.otherMap[code].geoId
            }, function () {
                console.log("删除辅助空间对象");
            }, "false");
        }
        delete tempData.otherMap[code];
    } else if (typeAndIndex[0] == "b") {
        for (var i in tempData.benchMap[code].targetCodeList) {
            var targetCode = tempData.benchMap[code].targetCodeList[i];
            tempData.targetMap[targetCode].benchName = null;
            tempData.targetMap[targetCode].benchId = null;
            tempData.targetMap[targetCode].benchIndex = null;
            tempData.targetMap[targetCode].benchCode = null;
        }
        if (tempData.benchMap[code].benchId) {
            ajaxBox(request_url_root + "/gisGeoProject/gisGeoProjectBenchDelete", {
                projectId: tempData.projectId,
                geoId: tempData.benchMap[code].geoId
            }, function () {
                console.log("删除基准目标");
            }, "false");
        }
        delete tempData.benchMap[code];
    }
}

/**
 *
 * 清空临时数据
 */
function clearTempData() {
    tempData.benchMap = {};
    tempData.otherMap = {};
    tempData.targetMap = {};
    tempData.projectName = null;
}

/**
 * 根据code返回缓存的覆盖物对象
 * @param code
 * @returns {AMap.OverlayGroup}
 */
function getMakerByTableCode(code) {
    var overlayGroup = new AMap.OverlayGroup([dBOverlaysMap[code]]);
    if (textOverlaysMap[code]) {
        overlayGroup.addOverlay(textOverlaysMap[code]);
    }
    return overlayGroup;
}