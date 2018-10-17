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
            {fixed: 'right', title: '操作', width: '10%', align: "center", toolbar: '#mlTool', align: "center"}
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
        if (obj.event === 'contractAdd') {
            $.post("../eiaContractLog/eiaContractHaltCheck", {eiaContractId:eiaContractId}, function (res) {
                if(res.code == 0){
                    layer.msg(res.msg, {icon: 2, time: 1000});
                }else if(res.code == -1){
                    //添加合同中止流程
                    //直接中止合同流程
                    var loadingIndex = layer.load(1, {time: 1000,shade: 0.1});
                    var actionUrl = "../eiaContractLog/eiaContractHaltSave";
                    layer.confirm('是否提交合同中止印章审批流程?', {icon: 3}, function (index) {
                        $.post(actionUrl, {eiaContractId:eiaContractId,ifDirectly:false}, function (data) {
                            if (data.code == 0) {
                                layer.msg('提交成功', {icon: 1, time: 1000}, function () {
                                    parent.layui.table.reload("eiaContractLogList");
                                    parent.layer.closeAll()
                                });
                            } else {
                                layer.msg(data.msg, {icon: 2, time: 1000,shade: 0.1});
                            }

                        });
                    }, function (index) {
                        //取消
                    });
                }
            })

        }
       /* else if(obj.event === 'contractHalt'){
            $.post("../eiaContractLog/eiaContractHaltCheck", {eiaContractId:eiaContractId}, function (res) {
                if(res.code == 0){
                    layer.msg(res.msg, {icon: 2, time: 1000});
                }else if(res.code == -1){
                    //直接中止合同流程
                    var loadingIndex = layer.load(1, {time: 1000,shade: 0.1});
                    var actionUrl = "../eiaContractLog/eiaContractHaltSave";
                    layer.confirm('是否直接中止合同?', {icon: 3}, function (index) {
                        $.post(actionUrl, {eiaContractId:eiaContractId,ifDirectly:true}, function (data) {
                            if (data.code == 0) {
                                layer.msg('中止成功', {icon: 1, time: 1000}, function () {
                                    parent.layui.table.reload("eiaContractLogList");
                                    parent.layer.closeAll()
                                });
                            } else {
                                layer.msg(data.msg, {icon: 2, time: 1000,shade: 0.1});
                            }
                            layer.close(loadingIndex);
                        });
                    }, function (index) {
                        //取消
                    });
                }
            })
        }*/
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
        switch(obj.event){
            case 'getSelect':
                var contractName = $("#contractName").val();
                table.reload("eiaContractList", {
                    where: {
                        contractName: contractName
                    }
                });
                break;
        };
    });
});