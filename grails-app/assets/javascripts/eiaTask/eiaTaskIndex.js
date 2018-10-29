layui.use(['jquery', 'layer', 'table'], function(){
    var $ = layui.jquery,
        layer = layui.layer,
        table = layui.table;

    var ifAdd = getParamFromUrl(document.location.href, "ifAdd");

    //渲染表格
    table.render({
        id: 'getEiaTaskDataList',
        elem: '#getEiaTaskDataList',
        url: '/eia/eiaTask/getEiaTaskDataList',
        toolbar: '#tableTopTmp',
        defaultToolbar:['filter', 'print', 'exports'],
        cols: [[
            {fixed: 'left', type: 'numbers', title: '序号', width: '6%', align: "center"},
            {field:'taskNo',width:'15%', title: '任务单号',align: "center"},
            {field:'taskName',width:'20%', title: '任务名称',align: "center"},
            {field:'busiType',width:'20%', title: '业务类型',align: "center"},
            {field:'inputDept',width:'15%', title: '录入部门',align: "center"},
            {field:'inputUser',width:'10%', title: '录入人',align: "center"},
            {field:'taskState',width:'10%', title: '任务状态',align: "center"},
            {field:'taskRole',width:'15%', title: '任务角色',align: "center"},
            {fixed: 'right', title: '操作',width:'15%',align: "center", toolbar: '#mlTool',align: "center"}
        ]],
        page: true,
        even: true,
        limit: 10,
        done:function () {
            if(ifAdd == '1'){
                $('.addBtn').trigger('click');
            }
        }
    });

    //监听工具条
    table.on('tool(getEiaTaskDataList)', function (obj) {
        var data = obj.data;
        $('#taskId').val(data.id)
        console.log(data);
        if (obj.event === 'eiaEdit') {    //编辑
            var pageUrl = '../eiaTask/eiaTaskEdit';
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
                    body.find('#taskNo').val(data.taskNo);
                },
                end: function () {
                    table.reload('getEiaTaskDataList');
                    $('#taskNo').val("");
                },
                min: function () {
                    $(".layui-layer-title").text("修改任务");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        }
        else if(obj.event === 'eiaDel'){    //删除
            layer.confirm('确定要删除该任务吗?', {icon: 3}, function (index) {
                var loadingIndex = layer.load(1, {time: 10*1000,shade: 0.1});
                 $.post("../eiaTask/eiaTaskDel", {taskId: data.id}, function (data) {
                     if (data.code == 0) {
                         layer.msg('删除成功！', {icon: 1, time: 1500,shade: 0.1}, function () {
                             obj.del();
                         });
                     } else {
                         if (data.msg) {
                             layer.msg("处理失败！<br/><div style='color:red;'>错误信息：" + data.msg + "</div>", {
                                 icon: 2,
                                 time: 1500
                             });
                         } else {
                             layer.msg('删除失败！', {icon: 2, time: 1500,shade: 0.1});
                         }
                     }
                     layer.close(loadingIndex);
                 });
            });

        }else if(obj.event === 'eiaCheck'){    //分配
            var pageUrl = '../eiaTask/eiaTaskDetail?taskId='+data.id;
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
                    body.find('#taskNo').text(data.taskNo);
                },
                end: function () {
                    table.reload('getEiaTaskDataList');
                    $('#taskNo').val("");
                },
                min: function () {
                    $(".layui-layer-title").text("查看任务");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        }else if(obj.event === 'eiaSubmit'){    //提交
            layer.confirm('确定要提交该任务吗?', {icon: 3}, function (index) {
                var loadingIndex = layer.load(1, {time: 10*1000,shade: 0.1});
                $.post("../eiaTask/eiaTaskSubmit", {taskId: data.id}, function (data) {
                    if (data.code == 0) {
                        layer.msg('提交成功！', {icon: 1, time: 1500,shade: 0.1}, function () {
                            table.reload('getEiaTaskDataList');
                        });
                    } else {
                        if (data.msg) {
                            layer.msg("处理失败！<br/><div style='color:red;'>错误信息：" + data.msg + "</div>", {
                                icon: 2,
                                time: 1500
                            });
                        } else {
                            layer.msg('提交失败！', {icon: 2, time: 1500,shade: 0.1});
                        }
                    }
                    layer.close(loadingIndex);
                });
            },function (index) {
                //取消
            });

        }
    });

    //监听头部工具栏事件
    //监听事件
    table.on('toolbar(getEiaTaskDataList)', function(obj){
        switch(obj.event){
            case 'getSelect':
                var taskName = $("#taskName").val();
                table.reload('getEiaTaskDataList', {
                    where: {
                        taskName: taskName
                    }
                });
                break;
            case 'missionAdd':
                pageUrl = '../eiaTask/eiaTaskCreate';
                var index = layer.open({
                    title:' ',
                    type: 2,
                    shade: false,
                    maxmin: true,
                    skin: 'larry-green',
                    area: ['100%', '100%'],
                    content: pageUrl,
                    success:function () {
                        ifAdd = 0;
                    },
                    end: function () {

                    },
                    min: function () {

                    },
                    restore: function () {

                    }
                });
                break;
        }
    });

});