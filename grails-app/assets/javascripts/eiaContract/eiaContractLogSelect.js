layui.use(['jquery', 'layer', 'table'], function () {
    var $ = layui.jquery,
        layer = layui.layer,
        table = layui.table;

    //渲染表格
    table.render({
        id: 'eiaContractList',
        elem: '#eiaContractList',
        url: '/eia/eiaContract/getEiaContractDataList?ifSub=' + true,
        toolbar: '#tableTopTmp',
        defaultToolbar:['filter', 'print', 'exports'],
        cols: [[
            {fixed: 'left', title: '序号', width: '6%', align: "center", templet: "#indexTable"},
            {field: 'contractNo', width: '15%', title: '合同编号', align: "center"},
            {field: 'contractName', width: '25%', title: '合同名称', align: "center"},
            {field: 'contractType', width: '15%', title: '合同类型', align: "center"},
            {field: 'contractMoney', width: '15%', title: '合同金额（万元）', align: "center"},
            {field: 'contractDate', width: '15%', title: '合同时间', align: "center"},
            {field: 'taskNo', width: '15%', title: '任务编号', align: "center"},
            {field: 'taskName', width: '20%', title: '任务名称', align: "center"},
            {field: 'inputUser', width: '10%', title: '录入人', align: "center"},
            {fixed: 'right', title: '操作', width: '15%', align: "center", toolbar: '#mlTool', align: "center"}
        ]],
        page: true,
        even: true,
        limit: 10
    });

    //监听工具条
    table.on('tool(eiaContractList)', function (obj) {
        var data = obj.data;
        var eiaContractId = data.id;
        $('#eiaContractId').val(eiaContractId);
        $('#eiaTaskId').val(data.eiaTaskId);
        if (obj.event === 'contractEdit') {    //编辑
            var pageUrl = '/eia/eiaContractLog/eiaContractLogCreate?eiaContractId=' + eiaContractId;
            var index = layer.open({
                title: ' ',
                type: 2,
                shade: false,
                maxmin: true,
                skin: 'larry-green',
                area: ['100%', '100%'],
                content: pageUrl,
                success: function (layero, index) {
                    // var body = layer.getChildFrame('body', index);
                    // body.find('#eiaContractId').val(data.id);
                },
                end: function () {
                    // table.reload("eiaContractList");
                    // $('#eiaContractId').val("");
                },
                min: function () {
                    $(".layui-layer-title").text("合同变更");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
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
            var contractName = $("#contractName").val();
            table.reload("eiaContractList", {
                where: {
                    contractName: contractName
                }
            });
        }
    }

    //监听头部工具栏事件
    //监听事件
    table.on('toolbar(eiaContractList)', function(obj){
        switch(obj.event) {
            case 'getSelect':
                var contractName = $("#contractName").val();
                table.reload("eiaContractList", {
                    where: {
                        contractName: contractName
                    }
                });
                break;
        }
    });
});