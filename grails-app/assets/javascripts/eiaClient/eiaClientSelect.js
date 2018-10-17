layui.use(['jquery', 'layer', 'table'], function(){
    var $ = layui.jquery,
        table = layui.table,
        layer = layui.layer;

    var clientType = getParamFromUrl(document.location.href,"clientType");

    //渲染表格
    table.render({
        id: 'clientList',
        elem: '#clientList',
        url:'/eia/eiaClient/getEiaClientDataList',
        toolbar: '#tableTopTmp',
        defaultToolbar:['filter', 'print', 'exports'],
        cols: [[
            {fixed: 'left', title: '序号',width:'7%',align: "center",templet: "#indexTable"},
            {field:'clientName',width:'30%', title: '客户名称',edit: 'text',align: "center"},
            {field:'clientAddress',width:'20%', title: '客户地址',edit: 'text',align: "center"},
            {field:'clientPostCode',width:'12%', title: '邮政编码',edit: 'text',align: "center"},
            {field:'clientCorporate',width:'12%', title: '法人代表',edit: 'text',align: "center"},
            {field:'clientFax',width:'12%', title: '传真',edit: 'text', align: "center"},
            {fixed: 'right', title: '操作',width:'10%',align: "center", toolbar: '#clientTool',align: "center"}
        ]],
        page: true,
        even: true,
        limit: 10
    });

    //监听工具条
    table.on('tool(clientList)', function (obj) {
        var data = obj.data;
        if(!data.clientName){
            layer.msg('客户名称不能为空，请点击单元格修改或在客户查询中修改!', {icon: 2, time: 3000, shade: 0.1}, function () {
            });
            return
        }else if(!data.clientAddress){
            layer.msg('客户地址不能为空，请点击单元格修改或在客户查询中修改!', {icon: 2, time: 3000, shade: 0.1}, function () {
            });
            return
        }else if(!data.clientCorporate){
            layer.msg('法人代表不能为空，请点击单元格修改或在客户查询中修改!', {icon: 2, time: 3000, shade: 0.1}, function () {
            });
            return
        }else{
            parent.$('#clientId').val(data.id);
            if (obj.event === 'eiaSelectContact') {    //选择联系人
                pageUrl = '/eia/eiaClient/eiaContactSelect?eiaClientId='+data.id+'&clientType='+clientType;
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
                        $('#eiaClientNameSelected').val(data.clientName);
                        $('#clientAddressSelected').val(data.clientAddress);
                        $('#eiaClientId').val(data.id);
                    },
                    end: function () {
                        // table.reload('clientList');
                        // $('#eiaClientId').val("");
                        //关闭层
                        var curIndex = parent.layer.getFrameIndex(window.name);
                        console.log('选择客户层'+curIndex);
                        parent.layer.close(curIndex);
                    },
                    min: function () {
                        $(".layui-layer-title").text("选择联系人");
                    },
                    restore: function () {
                        $(".layui-layer-title").text(" ");
                    }
                });
            }
        }

    });
    layui.use('table', function(){
        var table = layui.table;

        //监听单元格编辑
        table.on('edit(clientList)', function(obj){
            var value = obj.value //得到修改后的值
                ,data = obj.data //得到所在行所有键值s
                ,field = obj.field; //得到字段
            if(field=="clientName"){
                if(value==''){
                    layer.msg('该数据不能为空!', {icon: 2, time: 2000, shade: 0.1}, function () {
                    });
                    return
                }
            }else if(field=="clientAddress"){
                if(value==''){
                    layer.msg('该数据不能为空!', {icon: 2, time: 2000, shade: 0.1}, function () {
                    });
                    return
                }
            }else if(field=="clientCorporate"){
                if(value==''){
                    layer.msg('该数据不能为空!', {icon: 2, time: 2000, shade: 0.1}, function () {
                    });
                    return
                }
            }else if(field=="clientCorporate"){
                if(value==''){
                    layer.msg('该数据不能为空!', {icon: 2, time: 2000, shade: 0.1}, function () {
                    });
                    return
                }
            }
            var param = {}
            param[field] = value
            layer.confirm('确定要修改该信息吗?', {icon: 3}, function (index) {
                $.ajax({
                    url:"/eia/eiaClient/eiaClientUpdate?eiaClientId="+data.id,
                    type:"POST",
                    data:param,
                    dataType: "json",
                    success: function (data) {
                        if (data.code == 0) {
                            layer.msg(data.msg, {icon: 1, time: 1000,shade: 0.1}, function () {});
                            table.reload('contactList');
                        }else if(data.code == -1){
                            layer.msg('客户名称不能重复，请确认!', {icon: 2, time: 2000, shade: 0.1}, function () {
                            });
                            return
                        }else{
                            layer.msg(data.msg, {icon: 2, time: 2000, shade: 0.1}, function () {
                            });
                            table.reload('contactList');
                        }

                    }
                });
            },function (index) {
                //取消
            });
        });
    });
    //查询、新增按钮
    $('.larry-btn a.layui-btn').click(function () {
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });
    var active = {
        getSelect: function () {    //查询
            var clientName = $("#eiaClientName").val();
            table.reload('clientList', {
                where: {
                    clientName: clientName
                }
            });
        },
        clientAdd: function () {     //新增
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
                    table.reload('clientList');
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
                var clientName = $("#eiaClientName").val();
                table.reload('clientList', {
                    where: {
                        clientName: clientName
                    }
                });
                break;
            case 'clientAdd':
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
                        table.reload('clientList');
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