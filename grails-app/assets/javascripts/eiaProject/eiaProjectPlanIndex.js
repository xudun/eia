layui.use(['jquery', 'layer', 'table'], function () {
    var $ = layui.jquery,
        layer = layui.layer,
        table = layui.table;

    //渲染表格
    table.render({
        id: 'eiaProjectList',
        elem: '#eiaProjectList',
        url: '/eia/eiaProject/getEiaProjectDataList',
        toolbar: '#tableTopTmp',
        defaultToolbar:['filter', 'print', 'exports'],
        cols: [[
            {fixed: 'left', title: '序号', width: '6%', align: "center", templet: "#indexTable"},
            {field: 'projectNo', width: '15%', title: '项目编号', align: "center"},
            {field: 'projectName', width: '25%', title: '项目名称', align: "center"},
            {field: 'fileTypeChild', width: '15%', title: '文件类型', align: "center"},
            {field: 'planMonitor', width: '15%', title: '监控预警', align: "center", templet: "#planMonitorTmp"},
            {field: 'inputDept', width: '15%', title: '录入部门', align: "center"},
            {field: 'inputUser', width: '10%', title: '录入人', align: "center"},
            {field: 'dutyUser', width: '10%', title: '项目负责人', align: "center"},
            {fixed: 'right', title: '操作', width: '15%', align: "center", toolbar: '#mlTool', align: "center"}
        ]],
        page: true,
        even: true,
        limit: 10
    });

    //监听工具条
    table.on('tool(eiaProjectList)', function (obj) {

        var data = obj.data;
        var proPlanId = data.proPlanId;
        var eiaProjectId = data.id;
        var oid = data.oid;
        var oldContractId = data.oldContractId;
        $('#proPlanId').val(proPlanId);
        $('#eiaProjectId').val(eiaProjectId);
        $('#oid').val(oid);
        $('#oldContractId').val(oldContractId)
        if (obj.event === 'eiaEdit') {
            pageUrl = '/eia/eiaProject/eiaProjectPlanEdit';
            var index = layer.open({
                title: ' ',
                type: 2,
                shade: false,
                maxmin: true,
                skin: 'larry-green',
                area: ['100%', '100%'],
                content: pageUrl,
                success: function (layero, index) {
                },
                end: function () {
                    $('#eiaProjectId').val("");
                    $('#proPlanId').val("");
                    $('#oid').val("")
                    $('#oldContractId').val("")
                },
                min: function () {
                    $(".layui-layer-title").text("项目流程");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        }
        else if (obj.event === 'eiaDuty') {    //责任运行卡

        }
        else if (obj.event === 'eiaDetail') {    //查看
            pageUrl = '/eia/eiaProject/eiaProjectPlanDetail';
            var index = layer.open({
                title: ' ',
                type: 2,
                shade: false,
                maxmin: true,
                skin: 'larry-green',
                area: ['100%', '100%'],
                content: pageUrl,
                success: function (layero, index) {
                },
                end: function () {
                    $('#eiaProjectId').val("");
                    $('#proPlanId').val("");
                    $('#oid').val("")
                    $('#oldContractId').val("")
                },
                min: function () {
                    $(".layui-layer-title").text("项目流程");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        }
        else if (obj.event === 'eiaSubmit') {    //提交
            actionUrl = '/eia/eiaProjectPlan/proPlanToWorkFlow?proPlanId=' + proPlanId + '&eiaProjectId=' + eiaProjectId;
            layer.confirm('是否提交工作方案?', {icon: 3}, function (index) {
                var loadingIndex = layer.load(1, {time: 10*1000,shade: 0.1});
                $.post(actionUrl, data.field, function (data) {
                    if (data.code == 0) {
                        layer.msg('提交成功', {icon: 1, time: 1000,shade: 0.1}, function () {
                            layui.table.reload("eiaProjectList");
                        });
                    } else {
                        var msg = data.msg
                        layer.msg(data.msg, {icon: 2, time: 2000,shade: 0.1}, function () {
                            parent.layer.closeAll();
                        });
                    }
                    layer.close(loadingIndex);
                });
            }, function (index) {
                //取消
            });
        }
        else if (obj.event === 'eiaSubmitAdmin') {    //提交
            actionUrl = '/eia/eiaProjectPlan/makeProjectWorkFlow?proPlanId=' + proPlanId + '&eiaProjectId=' + eiaProjectId;
            layer.confirm('是否提交工作方案?', {icon: 3}, function (index) {
                var loadingIndex = layer.load(1, {time: 10*1000,shade: 0.1});
                $.post(actionUrl, data.field, function (data) {
                    if (data.code == 0) {
                        layer.msg('提交成功', {icon: 1, time: 1000,shade: 0.1}, function () {
                            layui.table.reload("eiaProjectList");
                        });
                    } else {
                        var msg = data.msg
                        layer.msg(data.msg, {icon: 2, time: 2000,shade: 0.1}, function () {
                            parent.layer.closeAll();
                        });
                    }
                    layer.close(loadingIndex);
                });
            }, function (index) {
                //取消
            });
        }
    });

    //监听头部工具栏事件
    //监听事件
    table.on('toolbar(eiaProjectList)', function(obj){
        switch(obj.event){
            case 'getSelect':
                var projectName = $("#projectName").val();
                table.reload("eiaProjectList", {
                    where: {
                        projectName: projectName
                    }
                });
                break;
        }
    });
});