layui.use(['jquery', 'layer', 'table'], function () {
    var $ = layui.jquery,
        layer = layui.layer,
        table = layui.table;

    var ifAdd = getParamFromUrl(document.location.href, "ifAdd");

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
            var loadingIndex = layer.load(1, {time: 10*1000,shade: 0.1});
            layer.confirm('是否提交合同变更印章审批流程\n?', {icon: 3}, function (index) {
                $.post("../eiaContractLog/eiaContractLogSub", {eiaContractLogId: eiaContractLogId}, function (result) {
                    if (result.code == 0) {
                        table.reload('eiaContractLogList');
                        layer.msg("合同变更流程提交成功！", {icon: 1, time: 1500})
                    }else{
                        layer.msg(result.msg, {icon: 2, time: 2500})
                    }
                    layer.close(loadingIndex);
                });
            }, function (index) {
                //取消
            });
        }else if (obj.event === 'logFlow') {    //流程
            pageUrl = '/eia/eiaWorkFlow/eiaWorkFlowTrans?tableName=EiaContractLog&tableNameId='+data.id;
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
                    $(".layui-layer-title").text("合同变更流程");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        } else if (obj.event === 'contractExport') {    //下载
            var pageUrl = '/eia/eiaContract/eiaContractDownload';
            var index = layer.open({
                title: '合同模板类型',
                type: 2,
                skin: 'demo-class contract-download',
                content: pageUrl,
                area: ['400px', '200px'],
                success: function (layero, index) {
                    var body = layer.getChildFrame('body', index);
                    body.find('#contractId').val(data.eiaContractId);
                },
                end: function () {

                },
                min: function () {
                    $(".layui-layer-title").text("下载合同");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        }
    });

    //监听头部工具栏事件
    //监听事件
    table.on('toolbar(eiaContractLogList)', function(obj){
        switch(obj.event){
            case 'getSelect':
                var contractName = $("#contractName").val();
                table.reload("eiaContractLogList", {
                    where: {
                        contractName: contractName
                    }
                });
                break;
            case 'logAdd':
                var pageUrl = '../eiaContractLog/eiaContractLogSelect';
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