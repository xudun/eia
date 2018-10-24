layui.use(['jquery', 'layer', 'table'], function(){
    var $ = layui.jquery,
        table = layui.table,
        layer = layui.layer;

    //渲染表格
    table.render({
        id: 'clientList',
        elem: '#clientList',
        url:'/eia/eiaClientConfig/invoiceSelectQueryPage',
        toolbar: '#tableTopTmp',
        defaultToolbar:['filter', 'print', 'exports'],
        cols: [[
            {fixed: 'left', title: '序号',width:'7%',align: "center",templet: "#indexTable"},
            {field:'clientName',width:'30%', title: '开票单位名称',align: "center"},
            {field:'taxRegCode',width:'15%', title: '税务登记代码',align: "center"},
            {field:'bankName',width:'15%', title: '开户行户名',align: "center"},
            {field:'bankAccount',width:'15%', title: '开户行账号',align: "center"},
            {field:'addrTel',width:'15%', title: '地址及电话',align: "center"},
            {fixed: 'right', title: '操作',width:'10%',align: "center", toolbar: '#clientTool',align: "center"}
        ]],
        page: true,
        even: true,
        limit: 10
    });

    //监听工具条
    table.on('tool(clientList)', function (obj) {
        var data = obj.data;
        //父层赋值
        parent.$("#clientName").val(data.clientName);
        parent.$("#clientConfigId").val(data.id);
        parent.$("#taxRegCode").val(data.taxRegCode);
        parent.$("#bankName").val(data.bankName);
        parent.$("#bankAccount").val(data.bankAccount);
        parent.$("#addrTel").val(data.addrTel);

        //关闭层
        var curIndex = parent.layer.getFrameIndex(window.name);
        console.log('选择联系人层'+curIndex);
        parent.layer.close(curIndex);
    });

    //查询、新增按钮
    $('.larry-btn a.layui-btn').click(function () {
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });
    var active = {
        getSelect: function () {    //查询
            var eiaClientName = $("#eiaClientName").val();
            table.reload('clientList', {
                where: {
                    eiaClientName: eiaClientName
                }
            });
        },
        clientConfigAdd: function () {    //新增
            pageUrl = '/eia/eiaClientConfig/eiaCliConfigCreate';
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
    table.on('toolbar(clientList)', function(obj){
        switch(obj.event){
            case 'getSelect':
                var eiaClientName = $("#eiaClientName").val();
                table.reload('clientList', {
                    where: {
                        eiaClientName: eiaClientName
                    }
                });
                break;
            case 'clientConfigAdd':
                pageUrl = '/eia/eiaClientConfig/eiaCliConfigCreate';
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