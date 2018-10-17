layui.use(['jquery', 'layer', 'table'], function(){
    var $ = layui.jquery,
        layer = layui.layer,
        table = layui.table;
    //渲染表格
    table.render({
        id: 'getEiaTaskDataList',
        elem: '#getEiaTaskDataList',
        url: '/eia/eiaTask/getEiaTaskDataList',
        cols: [[
            {fixed: 'left', type: 'numbers', title: '序号', width: '6%', align: "center"},
            {field:'taskNo',width:'15%', title: '任务单号',align: "center"},
            {field:'taskName',width:'25%', title: '任务名称',align: "center"},
            {field:'busiType',width:'20%', title: '业务类型',align: "center"},
            {field:'taskLeaderDept',width:'15%', title: '负责部门',align: "center"},
            {field:'taskState',width:'10%', title: '任务状态',align: "center"},
            {fixed: 'right', title: '操作',width:'10%',align: "center", toolbar: '#mlTool',align: "center"}
        ]],
        page: true,
        even: true,
        limit: 10
    });

    //监听工具条
    table.on('tool(getEiaTaskDataList)', function (obj) {
        var data = obj.data;
        $('#taskId').val(data.id);
        var eiaProjectId = parent.$('#projectId').val();
        if (obj.event === 'eiaEdit') {    //关联任务
            layer.confirm('确定要关联任务吗?', {icon: 3}, function (index) {
                var loadingIndex = layer.load(1, {time: 10*1000,shade: 0.1});
                $.post("../eiaDataMaintainLog/projectToTask", {taskId: data.id,eiaProjectId:eiaProjectId}, function (data) {
                    if (data.code == 0) {
                        layer.msg('关联成功！', {icon: 1, time: 1500,shade: 0.1}, function () {
                            parent.layui.table.reload("eiaProjectList");
                            parent.layui.table.reload("eiaProjectList");
                            parent.layer.closeAll();
                        });
                    } else {
                        if (data.msg) {
                            layer.msg("处理失败！<br/><div style='color:red;'>错误信息：" + data.msg + "</div>", {
                                icon: 2,
                                time: 1500
                            });
                        } else {
                            layer.msg('关联失败！', {icon: 2, time: 1500,shade: 0.1});
                        }
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
            var taskName = $("#taskName").val();
            table.reload('getEiaTaskDataList', {
                where: {
                    taskName: taskName
                }
            });
        }
    }
});