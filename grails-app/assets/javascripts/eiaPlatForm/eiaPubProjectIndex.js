layui.use(['jquery', 'layer', 'table'], function(){
    var $ = layui.jquery,
        layer = layui.layer,
        table = layui.table;

    //渲染表格
    table.render({
        id: 'eiaPubProjectList',
        elem: '#eiaPubProjectList',
        url:'../eiaPlatForm/eiaPubProjectQuery',
        toolbar: '#tableTopTmp',
        defaultToolbar:['filter', 'print', 'exports'],
        cols: [
            [
                {fixed: 'left', title: '序号',width:'60',align: "center",templet: "#indexTable"},
                {field: 'projectName', width: '30%', title: '项目名称', align: 'center'},
                {field: 'pubDate', width: '15%', title: '公示日期', align: 'center'},
                {field: 'eiaUnit', width: '30%', title: '环评单位', align: 'center'},
                {field: 'dataType', width: '15%', title: '公示类型', align: 'center'},
                {fixed: 'right', title: '操作', width:'15%', align: 'center', toolbar: '#eiaPubProjectListTool'}
            ]
        ],
        page: true,
        even: true,
        limit: 10
    });

    //监听工具条
    table.on('tool(eiaPubProjectList)', function (obj) {
        var data = obj.data;
        var eiaPubProjectId = data.id;
        $('#eiaPubProjectId').val(eiaPubProjectId);
        if(obj.event === 'eiaCheck'){    //查看
            var pageUrl = '../eiaPlatForm/eiaPubProjectDetail?eiaPubProjectId='+eiaPubProjectId;
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
                    $('#eiaPubProjectId').val('');
                },
                min: function () {
                    $(".layui-layer-title").text("审批公示详情");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        }
        else if(obj.event === 'eiaEdit'){  //编辑
            var pageUrl = '../eiaPlatForm/eiaPubProjectEdit?eiaPubProjectId='+eiaPubProjectId;
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
        else if(obj.event === 'eiaDel'){    //删除
            layer.confirm('确定要删除该审批公示吗?', {icon: 3}, function (index) {
                var loadingIndex = layer.load(1, {time: 10*1000,shade: 0.1});
                $.post("../eiaPlatForm/eiaPubProjectDel", {eiaPubProjectId: data.id}, function (result) {
                    if (result.code == 0) {
                        layer.msg('删除成功！', {icon: 1, time: 1500,shade: 0.1}, function () {
                            table.reload("eiaPubProjectList");
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
            layer.confirm('确定要发布该审批公示吗?', {icon: 3}, function (index) {
                var loadingIndex = layer.load(1, {time: 10*1000,shade: 0.1});
                $.post("../eiaPlatForm/eiaPubProjectConfirm", {eiaPubProjectId: data.id}, function (result) {
                    if (result.code == 0) {
                        layer.msg('发布成功！', {icon: 1, time: 1500,shade: 0.1}, function () {
                            table.reload("eiaPubProjectList");
                        });
                    } else {
                        if (result.msg) {
                            layer.msg( result.msg , {
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
            var pageUrl = GIS_DRAW_PATH+'?tableName=EiaPubProject&tableNameId='+data.id;
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
    table.on('toolbar(eiaPubProjectList)', function(obj){
        switch(obj.event){
            case 'getSelect':
                var projectName = $("#projectName").val();
                table.reload('eiaPubProjectList', {
                    where: {
                        projectName: projectName
                    }
                });
                break;
        }
    });
});