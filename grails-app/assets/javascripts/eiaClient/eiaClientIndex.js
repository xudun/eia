layui.use(['jquery', 'layer', 'table'], function(){
    var $ = layui.jquery,
        layer = layui.layer,
        table = layui.table;

    //渲染表格
    table.render({
        id: 'eiaClientList',
        elem: '#eiaClientList',
        url:'/eia/eiaClient/getEiaClientDataList',
        toolbar: '#tableTopTmp',
        defaultToolbar:['filter', 'print', 'exports'],
        cols: [[
            {fixed: 'left', title: '序号',width:'7%',align: "center",templet: "#indexTable"},
            {field:'clientName',width:'20%', title: '客户名称',align: "center"},
            {field:'clientAddress',width:'20%', title: '客户地址',align: "center"},
            {field:'contractCount',width:'10%', title: '已签合同（份）',align: "center",event: 'setSignContract',templet:'#contractCount'},
            {field:'offerCount',width:'10%', title: '已报价（份）',align: "center",event: 'setSignOffer',templet:'#offerCount'},
            {field:'inputDept',width:'15%', title: '录入部门',align: "center"},
            {field:'inputUser',width:'10%', title: '录入人',align: "center"},
            {field:'clientPostCode',width:'10%', title: '邮政编码',align: "center"},
            {field:'clientCorporate',width:'10%', title: '法人代表',align: "center"},
            {field:'clientFax',width:'10%', title: '传真',align: "center"},
            {fixed: 'right', title: '操作',width:'15%',align: "center", toolbar: '#mlTool',align: "center"}
        ]],
        page: true,
        even: true,
        limit: 10
    });

    //监听工具条
    table.on('tool(eiaClientList)', function (obj) {
        var data = obj.data;
        var eiaClientId = data.id;
        $('#eiaClientId').val(eiaClientId);
        if (obj.event === 'eiaEdit') {    //编辑
            pageUrl = '/eia/eiaClient/eiaClientCreate?pageType=1&eiaClientId='+eiaClientId;
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
                    body.find('#eiaClientId').val(data.id);
                },
                end: function () {
                    table.reload('eiaClientList');
                    $('#eiaClientId').val("");
                },
                min: function () {
                    $(".layui-layer-title").text("编辑客户");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        }
        else if(obj.event === 'eiaDel'){    //删除
            layer.confirm('确定要删除该客户吗?', {icon: 3}, function (index) {
                var loadingIndex = layer.load(1, {time: 10*1000,shade: 0.1});
                $.post("../eiaClient/eiaClientDel", {eiaClientId: data.id}, function (data) {
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
        else if(obj.event === 'eiaCheck'){    //查看
            pageUrl = '/eia/eiaClient/eiaClientDetail?eiaClientId='+eiaClientId;
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
                    body.find('#eiaClientId').val(data.eiaClientId);
                },
                end: function () {
                    table.reload('eiaClientList');
                    $('#eiaClientId').val("");
                },
                min: function () {
                    $(".layui-layer-title").text("查看客户");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        }else if(obj.event=='setSignOffer'){
            $('.offer-box').css('display','block');
            $('.contract-box').css('display','none');
            table.render({
                id: 'eiaOfferList',
                elem: '#eiaOfferList',
                url: '/eia/eiaContract/getEiaOfferDataList?eiaClientId='+eiaClientId,
                toolbar: '#tableTopTmp2',
                defaultToolbar:['filter', 'print', 'exports'],
                cols: [[
                    {fixed: 'left', title: '序号', width: '6%', align: "center", templet: "#indexTable"},
                    {field: 'offerNo', width: '15%', title: '报价编号', align: "center"},
                    {field: 'offerName', width: '25%', title: '合同名称', align: "center"},
                    {field: 'contractType', width: '15%', title: '合同类型', align: "center"},
                    {field: 'offerMoney', width: '15%', title: '报价金额（万元）', align: "center"},
                    {field: 'offerDate', width: '15%', title: '报价日期', align: "center"},
                    {field: 'inputDept', width: '15%', title: '录入部门', align: "center"},
                    {field: 'inputUser', width: '10%', title: '录入人', align: "center"},
                    {fixed: 'right', title: '操作', width: '10%', align: "center", toolbar: '#offerTool', align: "center"}
                ]],
                page: true,
                even: true,
                limit: 10
            });
        }else if(obj.event=='setSignContract'){
            $('.contract-box').css('display','block');
            $('.offer-box').css('display','none');
            //渲染合同表格
            table.render({
                id: 'eiaContractList',
                elem: '#eiaContractList',
                url: '/eia/eiaContract/getEiaContractDataList?eiaClientId='+eiaClientId,
                toolbar: '#tableTopTmp3',
                defaultToolbar:['filter', 'print', 'exports'],
                cols: [[
                    {fixed: 'left', title: '序号',width:'5%',align: "center",templet: "#indexTable"},
                    {field: 'contractNo', width: '15%', title: '合同编号', align: "center"},
                    {field: 'contractName', width: '25%', title: '合同名称', align: "center"},
                    {field: 'contractType', width: '15%', title: '合同类型', align: "center"},
                    {field: 'contractMoney', width: '15%', title: '合同金额（万元）', align: "center"},
                    {field: 'contractDate', width: '15%', title: '合同时间', align: "center"},
                    {field: 'taskNo', width: '15%', title: '任务编号', align: "center"},
                    {field: 'taskName', width: '20%', title: '任务名称', align: "center"},
                    {field: 'inputDept', width: '15%', title: '录入部门', align: "center"},
                    {field: 'inputUser', width: '10%', title: '录入人', align: "center"},
                    {fixed: 'right', title: '操作', width: '10%', align: "center", toolbar: '#contractTool', align: "center"}
                ]],
                page: true,
                even: true,
                limit: 10
            });
        }
    });
//监听合同表格工具条
    table.on('tool(eiaContractList)', function (obj) {
        var data = obj.data;
        //$('#eiaContractId').val(data.id)
        if (obj.event === 'eiaCheck') {    //编辑
            var pageUrl = '/eia/eiaContract/eiaContractDetail?eiaContractId=' + data.id;
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
                    body.find('#contractId').val(data.id);
                },
                end: function () {
                    $('#offerId').val("");
                },
                min: function () {
                    $(".layui-layer-title").text("查看合同");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        }
    });
    //监听报价表格工具条
    table.on('tool(eiaOfferList)', function (obj) {
        var data = obj.data;
        //$('#eiaContractId').val(data.id)
        if (obj.event === 'eiaCheck') {    //编辑
            var pageUrl = '/eia/eiaContract/eiaOfferDetail?eiaOfferId=' + data.id;
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
                    body.find('#contractId').val(data.id);
                },
                end: function () {
                    $('#offerId').val("");
                },
                min: function () {
                    $(".layui-layer-title").text("查看合同");
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
            var clientName = $("#clientName").val();
            table.reload('eiaClientList', {
                where: {
                    clientName: clientName
                }
            });
        },
        missionAdd: function () {    //新增
            pageUrl = '/eia/eiaClient/eiaClientCreate?pageType=0';
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
                },
                end: function () {

                },
                min: function () {

                },
                restore: function () {

                }
            });
        }
    }

    //监听头部工具栏事件
    //监听事件
    table.on('toolbar(eiaClientList)', function(obj){
        switch(obj.event){
            case 'getSelect':
                var clientName = $("#clientName").val();
                table.reload('eiaClientList', {
                    where: {
                        clientName: clientName
                    }
                });
                break;
            case 'missionAdd':
                pageUrl = '/eia/eiaClient/eiaClientCreate?pageType=0';
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
                    },
                    end: function () {

                    },
                    min: function () {

                    },
                    restore: function () {

                    }
                });
                break;
        };
    });
});