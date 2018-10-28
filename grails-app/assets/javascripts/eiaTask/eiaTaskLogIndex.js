layui.use(['jquery', 'layer', 'table'], function(){
    var $ = layui.jquery,
        layer = layui.layer,
        table = layui.table;

    var ifAdd = getParamFromUrl(document.location.href, "ifAdd");

    //渲染表格
    table.render({
        id: 'getEiaTaskLogDataList',
        elem: '#getEiaTaskLogDataList',
        url: '/eia/eiaTaskLog/getEiaTaskLogDataList',
        toolbar: '#tableTopTmp',
        defaultToolbar:['filter', 'print', 'exports'],
        cols: [[
            {fixed: 'left', type: 'numbers', title: '序号', width: '6%', align: "center"},
            {field:'taskNo',width:'15%', title: '任务单号',align: "center"},
            {field:'taskName',width:'25%', title: '任务名称',align: "center"},
            {field:'busiType',width:'20%', title: '业务类型',align: "center"},
            {field:'inputDept',width:'15%', title: '录入部门',align: "center"},
            {field:'inputUser',width:'10%', title: '录入人',align: "center"},
            {field:'taskState',width:'10%', title: '任务状态',align: "center"},
            {fixed: 'right', title: '操作',width:'10%',align: "center", toolbar: '#mlTool',align: "center"}
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
    table.on('tool(getEiaTaskLogDataList)', function (obj) {
        var data = obj.data;
        $('#eiaTaskLogId').val(data.id);
        if (obj.event === 'eiaCheck') {
            var pageUrl = '../eiaTaskLog/eiaTaskLogDetail';
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
                    table.reload('getEiaTaskLogDataList');
                    $('#taskNo').val("");
                },
                min: function () {
                    $(".layui-layer-title").text("查看任务");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        }
    });

    //监听头部工具栏事件
    //监听事件
    table.on('toolbar(getEiaTaskLogDataList)', function(obj){
        switch(obj.event){
            case 'getSelect':
                var taskName = $("#taskName").val();
                table.reload('getEiaTaskLogDataList', {
                    where: {
                        taskName: taskName
                    }
                });
                break;
            case 'logAdd':
                var pageUrl = '../eiaTaskLog/eiaTaskLogSelect';
                var index = layer.open({
                    title:' ',
                    type: 2,
                    shade: false,
                    maxmin: true,
                    skin: 'larry-green',
                    area: ['100%', '100%'],
                    content: pageUrl,
                    success: function () {
                        ifAdd = 0;
                    },
                    end:function(){
                    },
                    min:function(){
                        $(".layui-layer-title").text("选择任务");
                    },
                    restore:function(){
                        $(".layui-layer-title").text(" ");
                    }
                });
                break;
        }
    });
});