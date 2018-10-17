layui.use(['jquery', 'layer', 'table'], function(){
    var $ = layui.jquery,
        layer = layui.layer,
        table = layui.table;

    //渲染表格
    table.render({
        id: 'eiaSensAreaList',
        elem: '#eiaSensAreaList',
        url:'../eiaPlatForm/eiaSensAreaQuery',
        toolbar: '#tableTopTmp',
        defaultToolbar:['filter', 'print', 'exports'],
        cols: [
            [
                {fixed: 'left', title: '序号',width:'59',align: "center",templet: "#indexTable"},
                {field: 'projectName', width: '55%', title: '名称', align: 'center'},
                {field: 'dataType', width: '15%', title: '数据类别', align: 'center'},
                {field: 'regionName', width: '15%', title: '所属区域', align: 'center'},
                {fixed: 'right', title: '操作', width:'16%', align: 'center', toolbar: '#eiaSensAreaListTool'}
            ]
        ],
        page: true,
        even: true,
        limit: 10
    });

    //监听工具条
    table.on('tool(eiaSensAreaList)', function (obj) {
        var data = obj.data;
        var eiaSensAreaId = data.id;
        $('#eiaSensAreaId').val(eiaSensAreaId);
        if(obj.event === 'eiaCheck'){    //查看
            var pageUrl = '../eiaPlatForm/eiaSensAreaDetail';
            var index = layer.open({
                title:' ',
                type: 2,
                shade: false,
                maxmin: true,
                skin: 'larry-green',
                area: ['100%', '100%'],
                content: pageUrl,
                success:function (layero, index) {
                    var body = layer.getChildFrame('body', index);
                },
                end: function () {
                    $('#eiaSensAreaId').val('');
                },
                min: function () {
                    $(".layui-layer-title").text("环境敏感区详情");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        }
        else if(obj.event === 'eiaEdit'){  //编辑
            var pageUrl = '../eiaPlatForm/eiaSensAreaCreate?pageType=1';
            var index = layer.open({
                title:' ',
                type: 2,
                shade: false,
                maxmin: true,
                skin: 'larry-green',
                area: ['100%', '100%'],
                content: pageUrl,
                success:function (layero, index) {
                    var body = layer.getChildFrame('body', index);
                },
                end: function () {
                    table.reload('eiaSensAreaList');
                    $('#eiaSensAreaId').val('');
                },
                min: function () {
                    $(".layui-layer-title").text("编辑环境敏感区");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        }
        else if(obj.event === 'eiaDel'){    //删除
            layer.confirm('确定要删除该环境敏感区吗?', {icon: 3}, function (index) {
                var loadingIndex = layer.load(1, {time: 10*1000,shade: 0.1});
                $.post("../eiaPlatForm/eiaSensAreaDel", {eiaSensAreaId: data.id}, function (result) {
                    if (result.code == 0) {
                        layer.msg('删除成功！', {icon: 1, time: 1500,shade: 0.1}, function () {
                            table.reload("eiaSensAreaList");
                        });
                    } else {
                        if (result.msg) {
                            layer.msg("处理失败！<br/><div style='color:red;'>错误信息：" + result.msg + "</div>", {
                                icon: 2,
                                time: 1500
                            });
                        } else {
                            layer.msg('删除失败！', {icon: 2, time: 1500,shade: 0.1});
                        }
                    }
                    layer.close(loadingIndex);
                });
            }, function (index) {
                //取消
            });
        }else if(obj.event === 'eiaSubmit'){  //发布
            layer.confirm('确定要发布该环境敏感区吗?', {icon: 3}, function (index) {
                var loadingIndex = layer.load(1, {time: 10*1000,shade: 0.1});
                $.post("../eiaPlatForm/eiaSensAreaConfirm", {eiaSensAreaId: data.id}, function (result) {
                    if (result.code == 0) {
                        layer.msg('发布成功！', {icon: 1, time: 1500,shade: 0.1}, function () {
                            table.reload("eiaSensAreaList");
                        });
                    } else {
                        if (result.msg) {
                            layer.msg(result.msg , {
                                icon: 2,
                                time: 1500
                            });
                        } else {
                            layer.msg('发布失败！', {icon: 2, time: 1500,shade: 0.1});
                        }
                    }
                    layer.close(loadingIndex);
                });
            }, function (index) {
                //取消
            });
        }else if(obj.event === 'eiaDraw'){
            var pageUrl = GIS_DRAW_PATH+'?tableName=EiaSensArea&tableNameId='+data.id;
            var index = layer.open({
                title:' ',
                type: 2,
                shade: false,
                maxmin: true,
                skin: 'larry-green',
                area: ['100%', '100%'],
                content: pageUrl,
                success:function (layero, index) {
                    var body = layer.getChildFrame('body', index);
                },
                end: function () {
                    table.reload('eiaSensAreaList');
                    $('#eiaPubProjectId').val('');
                },
                min: function () {
                    $(".layui-layer-title").text("修改环境敏感区信息");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        }
    });

    //监听头部工具栏事件
    //监听事件
    table.on('toolbar(eiaSensAreaList)', function(obj){
        switch(obj.event){
            case 'getSelect':
                var projectName = $("#projectName").val();
                table.reload('eiaSensAreaList', {
                    where: {
                        projectName: projectName
                    }
                });
                break;
            case 'projectAdd':
                var pageUrl = '/eia/eiaPlatForm/eiaSensAreaCreate?pageType=0';
                var index = layer.open({
                    title:' ',
                    type: 2,
                    shade: false,
                    maxmin: true,
                    skin: 'larry-green',
                    area: ['100%', '100%'],
                    content: pageUrl,
                    success:function (layero, index) {
                        var body = layer.getChildFrame('body', index);
                    },
                    end: function () {
                        table.reload('eiaSensAreaList');
                        $('#eiaSensAreaId').val('');
                    },
                    min: function () {
                        $(".layui-layer-title").text("新增环境敏感区");
                    },
                    restore: function () {
                        $(".layui-layer-title").text(" ");
                    }
                });
                break;
        }
    });
});