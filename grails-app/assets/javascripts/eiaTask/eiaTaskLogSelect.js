layui.use(['jquery', 'layer', 'table'], function(){
    var $ = layui.jquery,
        layer = layui.layer,
        table = layui.table;
    //渲染表格
    table.render({
        id: 'getEiaTaskDataList',
        elem: '#getEiaTaskDataList',
        url: '/eia/eiaTask/getEiaTaskDataList?taskState=' + '已提交',
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
        console.log(data);
        if (obj.event === 'eiaEdit') {    //编辑
            var pageUrl = '../eiaTaskLog/eiaTaskLogCreate';
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
        }
    });
});