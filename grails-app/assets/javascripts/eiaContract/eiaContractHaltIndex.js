layui.use(['jquery', 'layer', 'table'], function () {
    var $ = layui.jquery,
        layer = layui.layer,
        table = layui.table;

    //渲染表格
    table.render({
        id: 'eiaContractLogList',
        elem: '#eiaContractLogList',
        url: '/eia/eiaContractLog/eiaContractLogQuery',
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
            {field: 'inputDept', width: '15%', title: '录入部门', align: "center"},
            {field: 'inputUser', width: '10%', title: '录入人', align: "center"},
            {fixed: 'right', title: '操作', width: '15%', align: "center", toolbar: '#mlTool', align: "center"}
        ]],
        where: {
            ifHalt: true
        },
        page: true,
        even: true,
        limit: 10
    });

    //监听工具条
    table.on('tool(eiaContractLogList)', function (obj) {
        var data = obj.data;
        var eiaContractLogId = data.id;
        $('#eiaContractLogId').val(eiaContractLogId);
        if (obj.event === 'logDetail') {    //显示
            var pageUrl = '/eia/eiaContractLog/eiaContractLogDetail?eiaContractLogId=' + eiaContractLogId;
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
                    table.reload("eiaContractLogList");
                    $('#eiaContractLogId').val("");
                },
                min: function () {
                    $(".layui-layer-title").text("合同变更详情");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        }else if (obj.event === 'logSub') {
            var eiaContractLogId = data.id;
            console.log(data)
            var loadingIndex = layer.load(1, {time: 1000,shade: 0.1});
            layer.confirm('是否提交合同变更流程?', {icon: 3}, function (index) {
                $.post("../eiaContractLog/eiaContractHaltSub", {eiaContractLogId: eiaContractLogId}, function (result) {
                    if (result.code == 0) {
                        table.reload('eiaContractLogList');
                        layer.msg("合同中止流程提交成功！", {icon: 1, time: 1500})
                    }else{
                        layer.msg(result.msg, {icon: 2, time: 2500})
                    }
                    layer.close(loadingIndex);
                });
            });
        }else if (obj.event === 'logFlow') {    //流程
            pageUrl = '/eia/eiaWorkFlow/eiaWorkFlowTrans?tableName=EiaContractLog&type=halt&tableNameId='+data.id;
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
                },
                min: function () {
                    $(".layui-layer-title").text("合同中止流程");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        }else if (obj.event === 'contractExport') {  //中止模板导出
            var contractId = data.id;
            var tempType = "contractStopTmp";
            layer.msg('正在导出...', {icon: 16, shade: 0.01});
            window.location.href = "../exportContract/downloadContractProtocol?eiaContractId=" + contractId+"&tempType="+ tempType;
        }else if (obj.event === 'eiaDel') {  //删除
            var contractId = data.id;
            layer.confirm('确定要删除该合同吗?', {icon: 3}, function (index) {
                var loadingIndex = layer.load(1, {time: 10*1000,shade: 0.1});
                $.post("../eiaContractLog/eiaContractLogDel", {contractId: contractId}, function (data) {
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
            var contractName = $("#contractName").val();
            table.reload("eiaContractLogList", {
                where: {
                    contractName: contractName,
                    ifHalt:true
                }
            });
        },
        logAdd: function () {    //新增
            var pageUrl = '../eiaContractLog/eiaContractHaltSelect';
            var index = layer.open({
                title:' ',
                type: 2,
                shade: false,
                maxmin: true,
                skin: 'larry-green',
                area: ['100%', '100%'],
                content: pageUrl,
                end:function(){
                },
                min:function(){
                    $(".layui-layer-title").text("选择合同");
                },
                restore:function(){
                    $(".layui-layer-title").text(" ");
                }
            });
        }
    }


    //监听头部工具栏事件
    //监听事件
    table.on('toolbar(eiaContractLogList)', function(obj){
        switch(obj.event){
            case 'getSelect':
                var contractName = $("#contractName").val();
                table.reload("eiaContractLogList", {
                    where: {
                        contractName: contractName,
                        ifHalt:true
                    }
                });
                break;
            case 'logAdd':
                var pageUrl = '../eiaContractLog/eiaContractHaltSelect';
                var index = layer.open({
                    title:' ',
                    type: 2,
                    shade: false,
                    maxmin: true,
                    skin: 'larry-green',
                    area: ['100%', '100%'],
                    content: pageUrl,
                    end:function(){
                    },
                    min:function(){
                        $(".layui-layer-title").text("选择合同");
                    },
                    restore:function(){
                        $(".layui-layer-title").text(" ");
                    }
                });
                break;
        };
    });
});