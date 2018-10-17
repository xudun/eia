layui.use(['form', 'table', 'layer', 'laypage', 'element', 'upload'], function () {
    var form = layui.form,
        table = layui.table,
        layer = layui.layer,
        laypage = layui.laypage,
        element = layui.element,
        upload = layui.upload;
    /**更新渲染导航条**/
    element.render();

    /**
     * 环保目标、基准目标、辅助对象、项目配准表格加载问题修复
     */
    element.on('tab(panelRightTab)', function(data){
        table.reload('EPGoalsTable');
        table.reload('datumGoalsTable');
        table.reload('assistObjTable');
    });

    /**右下角layer弹出框**/
    layer.open({
        type: 1
        , title: '编辑项目'
        , area: ['450px', '420px']
        , shade: 0
        , closeBtn: 0
        , maxmin: true
        , offset: 'rb'
        , content: $('#panelRight')
        , min: function (layero) {
            setTimeout(
                function () {
                    $(layero).css({
                        left: "auto",
                        right: "0px"
                    });
                }, 0);
        }
        , success: function (layero, index) {
            var left = $(layero).css('left')
            left = left.substring(0, left.length - 2) - 15 + "px"
            $(layero).css('left', left)
        }
    });


    /**环保目标table加载**/
    var EPGoalsTableIns = table.render({
        id: 'EPGoalsTable',
        elem: '#EPGoalsTable',
        method: 'get',
        cols: [[
            {fixed: 'left',type: 'checkbox'},
            {fixed: 'true',type: 'numbers', title: '序号'},
            {field: 'geoName', width: 155, title: '空间对象名称', align: 'center'},
            {field: 'objType', width: 120, title: '业务类型', align: 'center'},
            {field: 'benchName', width: 120, title: '基准目标', align: 'center'},
            {fixed: 'right', width: 115, title: '操作', align: 'center', toolbar: '#EPGoalsTableBar'}
        ]], data: tempTableList("target"), limit: 1000
    });

    /**环保目标工具条监听**/
    table.on('tool(EPGoalsTable)', function (obj) {
        var data = obj.data;
        if (obj.event === 'EPGoalsDelete') {
            layer.confirm('确定要删除该环保目标吗?', {icon: 3}, function (index) {
                deleteTempDataByCode(data.code);
                var targetList = tempTableList("target");
                targetList.map(function (d) {
                    getMakerByTableCode(d.code).setMap(null);
                    loadingDB(d);
                });
                EPGoals(targetList.length);
                table.reload('EPGoalsTable', {
                    data: targetList
                });
                layer.msg('删除成功！', {icon: 1, time: 1500});
            });
        }
        else if (obj.event === 'EPGoalsLocation') {
            locationByMarker(getMakerByTableCode(data.code));
        } else if (obj.event === 'EPGoalsEdit') {
            dataEdit = {type: "edit", data: tempData.targetMap[data.code]};

            function end() {
                dataEdit = {};
            }

            openEditLayer(end)
        }
    });

    /**环保目标复选框监听**/
    table.on('checkbox(EPGoalsTable)', function (obj) {
        var data = obj.data;
        if (obj.checked) {
            if (obj.type == "all") {
                var checkStatus = table.checkStatus('EPGoalsTable');
                checkStatus.data.map(function (d) {
                    getMakerByTableCode(d.code).setMap(map);
                    getTempDataByCode(d.code).config.ifDisplay = true;
                });
            } else if (obj.type == "one") {
                getMakerByTableCode(data.code).setMap(map);
                getTempDataByCode(data.code).config.ifDisplay = true;
            }
        } else {
            if (obj.type == "all") {
                tempTableList("target").map(function (d) {
                    getMakerByTableCode(d.code).setMap(null);
                    getTempDataByCode(d.code).config.ifDisplay = false;
                })
            } else if (obj.type == "one") {
                getMakerByTableCode(data.code).setMap(null);
                getTempDataByCode(data.code).config.ifDisplay = false;
            }
        }
    });

    /**修改环保目标数目**/
    function EPGoals(num) {
        $("#EPGoals").text(num);
    }

    /**基准目标table加载**/
    var datumGoalsTableIns = table.render({
        id: 'datumGoalsTable',
        elem: '#datumGoalsTable',
        method: 'get',
        cols: [[
            {fixed: 'left',type: 'checkbox'},
            {fixed: 'true',type: 'numbers', title: '序号'},
            {field: 'geoName', width: 155, title: '空间对象名称', align: 'center'},
            {field: 'objType', width: 120, title: '业务类型', align: 'center'},
            {fixed: 'right', width: 115, title: '操作', align: 'center', toolbar: '#datumGoalsTableBar'}
        ]], data: tempTableList("target"), limit: 1000
    });

    /**基准目标工具条监听**/
    table.on('tool(datumGoalsTable)', function (obj) {
        var data = obj.data;
        if (obj.event === 'datumGoalsDelete') {

            layer.confirm('确定要删除该基准目标吗?', {icon: 3}, function (index) {
                deleteTempDataByCode(data.code);
                var targetList = tempTableList("target");
                targetList.map(function (d) {
                    getMakerByTableCode(d.code).setMap(null);
                    loadingDB(d);
                });
                reloadTable();
                layer.msg('删除成功！', {icon: 1, time: 1500});
            });

        } else if (obj.event === 'datumGoalsLocation') {
            locationByMarker(getMakerByTableCode(data.code));
        } else if (obj.event === 'datumGoalsEdit') {
            dataEdit = {type: "edit", data: tempData.benchMap[data.code]};

            function end() {
                dataEdit = {}
            }

            openEditLayer(end)
        }
    });

    /**基准目标复选框监听**/
    table.on('checkbox(datumGoalsTable)', function (obj) {
        var data = obj.data;
        if (obj.checked) {
            if (obj.type == "all") {
                var checkStatus = table.checkStatus('datumGoalsTable');
                checkStatus.data.map(function (d) {
                    getMakerByTableCode(d.code).setMap(map);
                    getTempDataByCode(d.code).config.ifDisplay = true;
                });
            } else if (obj.type == "one") {
                getMakerByTableCode(data.code).setMap(map);
                getTempDataByCode(data.code).config.ifDisplay = true;
            }
        } else {
            if (obj.type == "all") {
                tempTableList("bench").map(function (d) {
                    getMakerByTableCode(d.code).setMap(null);
                    getTempDataByCode(d.code).config.ifDisplay = false;
                })
            } else if (obj.type == "one") {
                getMakerByTableCode(data.code).setMap(null);
                getTempDataByCode(data.code).config.ifDisplay = false;
            }

        }
    });

    /**修改基准目标数目**/
    function datumGoals(num) {
        $("#datumGoals").text(num);
    };

    /**辅助对象table加载**/
    var assistObjTableIns = table.render({
        id: 'assistObjTable',
        elem: '#assistObjTable',
        cols: [[
            {fixed: 'left',type: 'checkbox'},
            {fixed: 'true',type: 'numbers', title: '序号'},
            {field: 'geoName', width: 155, title: '空间对象名称', align: 'center'},
            {field: 'objType', width: 120, title: '业务类型', align: 'center'},
            {fixed: 'right', width: 115, title: '操作', align: 'center', toolbar: '#assistObjTableBar'}
        ]], data: tempTableList("other"), limit: 1000
    });

    /**辅助对象工具条监听**/
    table.on('tool(assistObjTable)', function (obj) {
        var data = obj.data;
        if (obj.event === 'assistObjDelete') {
            layer.confirm('确定要删除该辅助空间对象吗?', {icon: 3}, function (index) {
                deleteTempDataByCode(data.code);
                var otherList = tempTableList("other");
                assistObj(otherList.length);
                otherList.map(loadingDB);
                table.reload('assistObjTable', {
                    data: otherList
                });
                layer.msg('删除成功！', {icon: 1, time: 1500});
            });

        } else if (obj.event === 'assistObjLocation') {
            locationByMarker(getMakerByTableCode(data.code));
        } else if (obj.event === 'assistObjEdit') {
            dataEdit = {type: "edit", data: tempData.otherMap[data.code]};

            function end() {
                dataEdit = {}
            }

            openEditLayer(end)
        }
    });
    /**辅助对象复选框监听**/
    table.on('checkbox(assistObjTable)', function (obj) {
        var data = obj.data;
        if (obj.checked) {
            if (obj.type == "all") {
                var checkStatus = table.checkStatus('assistObjTable');
                checkStatus.data.map(function (d) {
                    getMakerByTableCode(d.code).setMap(map);
                    getTempDataByCode(d.code).config.ifDisplay = true;
                });
            } else if (obj.type == "one") {
                getMakerByTableCode(data.code).setMap(map);
                getTempDataByCode(data.code).config.ifDisplay = true;
            }
        } else {
            if (obj.type == "all") {
                tempTableList("other").map(function (d) {
                    getMakerByTableCode(d.code).setMap(null);
                    getTempDataByCode(d.code).config.ifDisplay = false;
                })
            } else if (obj.type == "one") {
                getMakerByTableCode(data.code).setMap(null);
                getTempDataByCode(data.code).config.ifDisplay = false;
            }

        }
    });

    //修改辅助对象数目
    function assistObj(num) {
        $("#assistObj").text(num);
    }


    reloadTable = function () {
        /**刷新环保目标**/
        var targetList = tempTableList("target");
        table.reload('EPGoalsTable', {
            data: targetList
        });
        EPGoals(targetList.length);
        /**刷新准目标**/
        var benchList = tempTableList("bench");
        table.reload('datumGoalsTable', {
            data: benchList
        });
        datumGoals(benchList.length);
        /**刷新辅助空间对象**/
        var otherList = tempTableList("other");
        table.reload('assistObjTable', {
            data: otherList
        });
        assistObj(otherList.length);

        var matchList = tempTableList("match");

        table.reload('projectNameTable');
    };



    var dataEdit = {};
    /**
     * 绘制结束回调函数
     */
    var dataResult = {};

    function drawEndHandle(geoJSON) {
        dataResult["drawFeatureObj"] = geoJSON;
        $("#confirm").show();
        $("#cancle").show();
    }

    /**绘制开始回调**/
    function drawStartHandle(event) {
    }

    /**搜索结果展示窗口**/
    function openSearchDiv() {
        layer.open({
            type: 1
            , title: ' '
            , area: ['auth', '400px']
            , shade: 0
            , maxmin: true
            , offset: 'lb'
            , content: $('#panel')
            , end: function () {
                $("#panel").css("display", "none");
                var allOverlays = map.getAllOverlays();
                allOverlays.map(function (overlays) {
                    if (overlays.getExtData()._geoJsonProperties && overlays.getExtData()._geoJsonProperties.id) {

                    } else {
                        overlays.setMap(null);
                    }
                })
            }
        });
    }

    /**禁止绘制**/
    function disableDraw() {
        $("#Point").attr("disabled", "true");
        $("#Line").attr("disabled", "true");
        $("#Polygon").attr("disabled", "true");
        $("#Circle").attr("disabled", "true");
    }

    /**解除禁止绘制**/
    function enableDraw() {
        $("#Point").removeAttr("disabled");
        $("#Line").removeAttr("disabled");
        $("#Polygon").removeAttr("disabled");
        $("#Circle").removeAttr("disabled");
    }
    var eiaProjectId = getParamByUrl(window.location.href).eiaProjectId
    ajaxBox(request_url_root + '/eiaProject/getGisGeoProjectMap', {eiaProjectId: eiaProjectId}, function (res) {
        if(res.data){
            tempData = res.data;
            $("#projectName").text("项目名称:"+tempData.projectName);
            var tempGeoList = tempTableList();
            tempGeoList.map(loadingDB);
            map.setFitView();
            reloadTable();
        }else{
            layer.msg("无具体信息",{icon:2,time:2000},function(){
                var index = parent.layer.getFrameIndex(window.name);
                parent.layer.close(index)
            });
        }
    });




})

