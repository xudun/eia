layui.use(['jquery', 'layer', 'table'], function () {
    var $ = layui.jquery,
        layer = layui.layer,
        table = layui.table;
    var proPlanId = parent.$('#proPlanId').val();
    var eiaProjectId = parent.$('#eiaProjectId').val();
    var oid= parent.$('oid').val();
    $('#proPlanId').val(proPlanId);
    $('#eiaProjectId').val(eiaProjectId);
    $('#oid').val(oid)
    //渲染表格
    table.render({
        id: 'eiaProPlanItemList',
        elem: '#eiaProPlanItemList',
        url: '/eia/eiaProjectPlan/getProjectNodeList?proPlanId=' + $('#proPlanId').val(),
        toolbar: ' ',
        defaultToolbar:['filter', 'print', 'exports'],
        cols: [[
            {fixed: 'left', title: '序号', width: "6%", align: "center", templet: "#indexTable"},
            {field: 'nodesName', width: '15%', title: '项目流程', align: "center"},
            {field: 'nodeUserName', width: '15%', title: '审批人', align: "center"},
            {field: 'planStartDate', width: '15%', title: '预计开始日期', align: "center"},
            {field: 'planEndDate', width: "15%", title: '预计完成日期', align: "center"},
            {field: 'planFinDay', width: "15%", title: '预计完成天数', align: "center"},
            {field: 'actStartDate', width: "15%", title: '实际开始日期', align: "center"},
            {field: 'actEndDate', width: "15%", title: '实际完成日期', align: "center"},
            {field: 'actFinDay', width: "15%", title: '实际完成天数', align: "center"},
            {fixed: 'right', title: '操作', width: '15%', align: "center", toolbar: '#mlTool', align: "center"}
        ]],
        page: true,
        even: true,
        limit: 10
    });

    //监听工具条
    table.on('tool(eiaProPlanItemList)', function (obj) {
        var data = obj.data
        $('#proPlanItemId').val(data.id)
        if (obj.event === 'itemDetail') {
            pageUrl = '/eia/eiaProject/eiaProPlanItemDetail';
            var index = layer.open({
                title: ' ',
                type: 2,
                shade: [0.8, '#393D49'],
                maxmin: true,
                skin: 'larry-green',
                area: ['80%', '80%'],
                content: pageUrl,
                success: function (layero, index) {
                },
                end: function () {
                    $('#eiaProjectId').val("");
                    $('#proPlanItemId').val("");
                },
                min: function () {
                    $(".layui-layer-title").text("项目流程");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        }
        else if (obj.event === 'planItemDel') {    //删除
            layer.confirm('确定要删除该节点吗?', {icon: 3}, function (index) {
                var loadingIndex = layer.load(1, {time: 10*1000,shade: 0.1});
                $.post("../eiaProjectPlan/proPlanItemDel", {proPlanItemId: data.id}, function (data) {
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
            },function (index) {
                //取消
            });
        }
    });
});