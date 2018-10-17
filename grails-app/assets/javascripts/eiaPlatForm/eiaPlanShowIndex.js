layui.use(['jquery', 'layer', 'table'], function(){
    var $ = layui.jquery,
        layer = layui.layer,
        table = layui.table;

    //渲染表格
    table.render({
        id: 'eiaPlanShowList',
        elem: '#eiaPlanShowList',
        url:'../eiaPlatForm/eiaPlanShowQuery',
        toolbar: '#tableTopTmp',
        defaultToolbar:['filter', 'print', 'exports'],
        cols: [
            [
                {fixed: 'left', title: '序号',width:"60",align: "center",templet: "#indexTable"},
                {field: 'title', width: '52%', title: '标题', align: 'center'},
                {field: 'pubDate', width: '15%', title: '公布日期', align: 'center'},
                {field: 'source', width: '15%', title: '来源机关', align: 'center'},
                {fixed: 'right', title: '操作', width:'15%', align: 'center', toolbar: '#eiaPlanShowListTool'}
            ]
        ],
        page: true,
        even: true,
        limit: 10
    });

    //监听工具条
    table.on('tool(eiaPlanShowList)', function (obj) {
        var data = obj.data;
        var eiaPlanShowId = data.id;
        $('#eiaPlanShowId').val(eiaPlanShowId);
        if(obj.event === 'eiaCheck'){    //查看
            var pageUrl = '../eiaPlatForm/eiaPlanShowDetail';
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
                    $('#eiaPlanShowId').val('');
                },
                min: function () {
                    $(".layui-layer-title").text("规划公示详情");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        }
        else if(obj.event === 'eiaEdit'){  //编辑
            var pageUrl = '../eiaPlatForm/eiaPlanShowEdit';
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
                    table.reload('eiaPlanShowList');
                    $('#eiaPlanShowId').val('');
                },
                min: function () {
                    $(".layui-layer-title").text("修改规划公示");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        }
        else if(obj.event === 'eiaDel'){    //删除
            layer.confirm('确定要删除该规划公示吗?', {icon: 3}, function (index) {
                var loadingIndex = layer.load(1, {time: 10*1000,shade: 0.1});
                $.post("../eiaPlatForm/eiaPlanShowDel", {eiaPlanShowId: data.id}, function (result) {
                    if (result.code == 0) {
                        layer.msg('删除成功！', {icon: 1, time: 1500,shade: 0.1}, function () {
                            table.reload("eiaPlanShowList");
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
        }
        else if(obj.event === 'eiaSubmit'){  //发布
            layer.confirm('确定要发布该规划公示吗?', {icon: 3}, function (index) {
                var loadingIndex = layer.load(1, {time: 10*1000,shade: 0.1});
                $.post("../eiaPlatForm/eiaPlanShowConfirm", {eiaPlanShowId: data.id}, function (result) {
                    if (result.code == 0) {
                        layer.msg('发布成功！', {icon: 1, time: 1500,shade: 0.1}, function () {
                            table.reload("eiaPlanShowList");
                        });
                    } else {
                        if (result.msg) {
                            layer.msg(result.msg, {
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
            var pageUrl = GIS_DRAW_PATH+'?tableName=EiaPlanShow&tableNameId='+data.id;
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
                    table.reload('eiaPubProjectList');
                    $('#eiaPubProjectId').val('');
                },
                min: function () {
                    $(".layui-layer-title").text("修改审批公示信息");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        }
    });

    //监听头部工具栏事件
    //监听事件
    table.on('toolbar(eiaPlanShowList)', function(obj) {
        switch (obj.event) {
            case 'getSelect':
                var title = $("#title").val();
                table.reload('eiaPlanShowList', {
                    where: {
                        title: title
                    }
                });
                break;
        }
    });
});