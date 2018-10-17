layui.use(['jquery', 'layer', 'table'], function(){
    var $ = layui.jquery,
        layer = layui.layer,
        table = layui.table;

    //渲染表格
    table.render({
        id: 'eiaAreaInfoList',
        elem: '#eiaAreaInfoList',
        url:'../eiaPlatForm/eiaAreaInfoQuery',
        toolbar: '#tableTopTmp',
        defaultToolbar:['filter', 'print', 'exports'],
        cols: [
            [
                {fixed: 'left', title: '序号',width:'58',align: "center",templet: "#indexTable"},
                {field: 'projectName', width: '50%', title: '区域/企业名称', align: 'center'},
                {field: 'dataType', width: '19%', title: '数据类别', align: 'center'},
                {field: 'regionName', width: '19%', title: '所属区域', align: 'center'},
                {fixed: 'right', title: '操作', width:'14%', align: 'center', toolbar: '#eiaAreaInfoListTool'}
            ]
        ],
        page: true,
        even: true,
        limit: 10
    });

    //监听工具条
    table.on('tool(eiaAreaInfoList)', function (obj) {
        var data = obj.data;
        var eiaAreaInfoId = data.id;
        $('#eiaAreaInfoId').val(eiaAreaInfoId);
        if(obj.event === 'eiaCheck'){    //查看
            var pageUrl = '../eiaPlatForm/eiaAreaInfoDetail';
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
                    $('#eiaAreaInfoId').val('');
                },
                min: function () {
                    $(".layui-layer-title").text("企业信息详情");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        }
        else if(obj.event === 'eiaEdit'){  //编辑
            var pageUrl = '../eiaPlatForm/eiaAreaInfoCreate?pageType=1';
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
                    table.reload('eiaAreaInfoList');
                    $('#eiaAreaInfoId').val('');
                },
                min: function () {
                    $(".layui-layer-title").text("修改区域/企业信息");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        }
        else if(obj.event === 'eiaDel'){    //删除
            layer.confirm('确定要删除该区域/企业信息吗?', {icon: 3}, function (index) {
                var loadingIndex = layer.load(1, {time: 10*1000,shade: 0.1});
                $.post("../eiaPlatForm/eiaAreaInfoDel", {eiaAreaInfoId: data.id}, function (result) {
                    if (result.code == 0) {
                        layer.msg('删除成功！', {icon: 1, time: 1500,shade: 0.1}, function () {
                            table.reload("eiaAreaInfoList");
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
            layer.confirm('确定要发布该区域/企业信息吗?', {icon: 3}, function (index) {
                var loadingIndex = layer.load(1, {time: 10*1000,shade: 0.1});
                $.post("../eiaPlatForm/eiaAreaInfoConfirm", {eiaPlanShowId: data.id}, function (result) {
                    if (result.code == 0) {
                        layer.msg('发布成功！', {icon: 1, time: 1500,shade: 0.1}, function () {
                            table.reload("eiaAreaInfoList");
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
            var pageUrl = GIS_DRAW_PATH+'?tableName=EiaAreaInfo&tableNameId='+data.id;
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
                    table.reload('eiaAreaInfoList');
                    $('#eiaPubProjectId').val('');
                },
                min: function () {
                    $(".layui-layer-title").text("修改区域规划信息");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        }
    });

    //监听头部工具栏事件
    //监听事件
    table.on('toolbar(eiaAreaInfoList)', function(obj){
        switch(obj.event){
            case 'getSelect':
                var projectName = $("#projectName").val();
                table.reload('eiaAreaInfoList', {
                    where: {
                        projectName: projectName
                    }
                });
                break;
            case 'projectAdd':
                var pageUrl = '../eiaPlatForm/eiaAreaInfoCreate?pageType=0';
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
                        table.reload('eiaAreaInfoList');
                        $('#eiaAreaInfoId').val('');
                    },
                    min: function () {
                        $(".layui-layer-title").text("新增区域/企业信息");
                    },
                    restore: function () {
                        $(".layui-layer-title").text(" ");
                    }
                });
                break;
        }
    });
});