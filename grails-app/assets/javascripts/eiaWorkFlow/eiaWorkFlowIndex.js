layui.use(['jquery', 'layer', 'table'], function () {
    var $ = layui.jquery,
        layer = layui.layer,
        table = layui.table;

    //渲染表格
    table.render({
        id: 'eiaWorkFlowList',
        elem: '#eiaWorkFlowList',
        url: '/eia/eiaWorkFlow/getEiaWorkFlowDataList',
        toolbar: '#tableTopTmp',
        defaultToolbar:['filter', 'print', 'exports'],
        cols: [[
            {fixed: 'left', title: '序号', width: '6%', align: "center", templet: "#indexTable"},
            {field: 'workFlowCode', width: '30%', title: '流程编码', align: "center"},
            {field: 'workFlowName', width: '30%', title: '流程名称', align: "center"},
            {field: 'workFlowVersion', width: '20%', title: '流程版本', align: "center"},
            {fixed: 'right', title: '操作', width: '14%', align: "center", toolbar: '#mlTool', align: "center"}
        ]],
        page: true,
        even: true,
        limit: 10
    });

    //监听工具条
    table.on('tool(eiaWorkFlowList)', function (obj) {
        var data = obj.data;
        $('#eiaWorkFlowId').val(data.id);
        if (obj.event === 'eiaEdit') {    //编辑
            pageUrl = '/eia/eiaWorkFlow/eiaWorkFlowCreate?pageType=1';
            var index = layer.open({
                title: ' ',
                type: 2,
                shade: false,
                maxmin: true,
                skin: 'larry-green',
                area: ['100%', '100%'],
                content: pageUrl,
                success: function (layero, index) {
                    var body = layer.getChildFrame('body', index);
                    body.find('#eiaWorkFlowId').val(data.id);
                },
                end: function () {
                    $('#eiaWorkFlowId').val("");
                },
                min: function () {
                    $(".layui-layer-title").text("编辑流程");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        }
        else if (obj.event === 'eiaCheck') {    //查看
            pageUrl = 'eiaWorkFlowDetail.html';
            var index = layer.open({
                title: ' ',
                type: 2,
                shade: false,
                maxmin: true,
                skin: 'larry-green',
                area: ['100%', '100%'],
                content: pageUrl,
                success: function (layero, index) {
                    var body = layer.getChildFrame('body', index);
                    body.find('#eiaWorkFlowId').val(data.id);
                },
                end: function () {
                    $('#workFlowId').val("");
                },
                min: function () {
                    $(".layui-layer-title").text("流程详情");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        }
        else if (obj.event === 'eiaDel') {    //删除
            layer.confirm('确定要删除该流程吗?', {icon: 3}, function (index) {
                var loadingIndex = layer.load(1, {time: 10*1000,shade: 0.1});
                $.post("../eiaWorkFlow/eiaWorkFlowDel", {eiaWorkFlowId: data.id}, function (result) {
                    if (result.code == 0) {
                        layer.msg('删除成功！', {icon: 1, time: 1500,shade: 0.1}, function () {
                            table.reload("eiaWorkFlowList");
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
        } else if (obj.event === 'subWorkFlow') {    //删除
            layer.confirm('确定要提交该流程吗?', {icon: 3}, function (index) {
                var loadingIndex = layer.load(1, {time: 10*1000,shade: 0.1});
                $.post("../eiaWorkFlow/submitEiaWorkFlow", {eiaWorkFlowId: data.id}, function (result) {
                    if (result.code == 0) {
                        layer.msg('提交成功！', {icon: 1, time: 1500,shade: 0.1}, function () {
                            layui.table.reload('eiaWorkFlowList')
                        });
                    } else {
                        layer.msg(result.msg, {icon: 2, time: 1500,shade: 0.1});
                    }
                    layer.close(loadingIndex);
                });
            }, function (index) {
                //取消
            });
        }
    });

    //查询、新增按钮
    $('.larry-btn a.layui-btn').click(function () {
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });
    var active = {
        getSelect: function () {    //查询
            var workFlowName = $("#workFlowName").val();
            table.reload('eiaWorkFlowList', {
                where: {
                    workFlowName: workFlowName
                }
            });
        },
        flowAdd: function () {    //新增
            pageUrl = '/eia/eiaWorkFlow/eiaWorkFlowCreate?pageType=0';
            var index = layer.open({
                title: ' ',
                type: 2,
                shade: false,
                maxmin: true,
                skin: 'larry-green',
                area: ['100%', '100%'],
                content: pageUrl,
                success: function (layero, index) {
                    var body = layer.getChildFrame('body', index);
                },
                end: function () {

                },
                min: function () {
                    $(".layui-layer-title").text("新增流程");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        },
        importConfig:function () {
            pageUrl = '/eia/eiaWorkFlow/eiaWorkFlowConfigImport';
            var index = layer.open({
                title: ' ',
                type: 2,
                shade: false,
                maxmin: true,
                skin: 'larry-green',
                area: ['100%', '100%'],
                content: pageUrl,
                success: function (layero, index) {
                    var body = layer.getChildFrame('body', index);
                },
                end: function () {

                },
                min: function () {
                    $(".layui-layer-title").text("导入工作流配置");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        }
    }

    //监听头部工具栏事件
    //监听事件
    table.on('toolbar(eiaWorkFlowList)', function(obj){
        switch(obj.event){
            case 'getSelect':
                var workFlowName = $("#workFlowName").val();
                table.reload('eiaWorkFlowList', {
                    where: {
                        workFlowName: workFlowName
                    }
                });
                break;
            case 'flowAdd':
                var pageUrl = '/eia/eiaWorkFlow/eiaWorkFlowCreate?pageType=0';
                var index = layer.open({
                    title: ' ',
                    type: 2,
                    shade: false,
                    maxmin: true,
                    skin: 'larry-green',
                    area: ['100%', '100%'],
                    content: pageUrl,
                    success: function (layero, index) {
                        var body = layer.getChildFrame('body', index);
                    },
                    end: function () {

                    },
                    min: function () {
                        $(".layui-layer-title").text("新增流程");
                    },
                    restore: function () {
                        $(".layui-layer-title").text(" ");
                    }
                });
                break;
            case 'importConfig':
                var pageUrl = '/eia/eiaWorkFlow/eiaWorkFlowConfigImport';
                var index = layer.open({
                    title: ' ',
                    type: 2,
                    shade: false,
                    maxmin: true,
                    skin: 'larry-green',
                    area: ['100%', '100%'],
                    content: pageUrl,
                    success: function (layero, index) {
                        var body = layer.getChildFrame('body', index);
                    },
                    end: function () {

                    },
                    min: function () {
                        $(".layui-layer-title").text("导入工作流配置");
                    },
                    restore: function () {
                        $(".layui-layer-title").text(" ");
                    }
                });
                break;
        }
    });
});