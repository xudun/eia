layui.use(['layer', 'form', 'table'], function () {
    var $ = layui.$,
        layer = layui.layer,
        table = layui.table;

    var type = parent.$("#type").val();
    $(function () {
        var eiaClientId = $("#eiaClientId").val();
        if (type == "sjClientSelect") {
            /**
             * 渲染企业列表
             */
            var tableIns = table.render({
                id: 'eiaClientList',
                elem: '#eiaClientList',
                url: '../eiaClient/getEiaClientDataList',
                toolbar: '#tableTopTmp',
                defaultToolbar:['filter', 'print', 'exports'],
                cols: [
                    [
                        {field: 'clientNameCn', title: '企业名称', width: '50%', align: 'center'},
                        {field: 'clientAddrCn', title: '地址', width: '50%', align: 'center'},
                        {fixed: 'right', title: '操作', width: 90, align: 'center', toolbar: '#sjClientListBar'}
                    ]
                ],
                page: true,
                even: true,
                limit: 10
            });
        }
    });
    /**
     * 监听工具条
     */
    table.on('tool(eiaClientList)', function (obj) {
        var data = obj.data;
        $('#eiaClientId').val(data.id);
        if (obj.event === 'sjSelected') {
            var pageUrl = "../eiaLabOffer/eiaLabContactList";
            var sjLayer = layer.open({
                title: " ",
                type: 2,
                //shade: false,
                maxmin: true,
                skin: 'larry-green',
                area: ['100%', '100%'],
                content: pageUrl,
                success: function (layero, index) {
                    var body = layer.getChildFrame('body', index);
                    body.find('#eiaClientId').val(data.id);
                },
                end: function (layero, index) {
                    //var body = layer.getChildFrame('body', index);
                    //body.find('#type').val("clientEnt");
                    parent.$("#sjClientName").val(data.clientNameCn);
                    parent.$("#sjClientAddr").val(data.clientAddrCn);
                    parent.$("#sjClientId").val(data.id);
                    parent.$("#sjClientSource").val("LAB");
                    parent.$("#sjClientContact").val($("#contactPerson").val());
                    parent.$("#sjClientPhone").val($("#contactPersonMobil").val());
                    var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                    //alert(index+"==1");
                    parent.layer.close(index); //再执行关闭
                },
                min: function () {
                    $(".layui-layer-title").text("选择联系人信息");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        }
    });

    //监听头部工具栏事件
    //监听事件
    table.on('toolbar(eiaClientList)', function(obj){
        switch(obj.event){
            case 'getSysSelect':
                var clientName = $("#entName").val();
                table.reload('eiaClientList', {
                    where: {
                        clientName: clientName
                    }
                });
                break;
            case 'clientAdd':
                var pageUrl = '../eiaClient/eiaClientCreate?pageType=0';
                layer.open({
                    title: " ",
                    type: 2,
                    maxmin: true,
                    skin: 'larry-green',
                    area: ['100%', '100%'],
                    content: pageUrl,
                    success: function (layero, index) {
                        var body = layer.getChildFrame('body', index);
                        if (type == "wtClientSelect") {
                            body.find('#contactType').val("现场联系人");
                        } else if (type == "sjClientSelect") {
                            body.find('#contactType').val("业务联系人");
                        }
                        //body.find('#labOfferId').val(labOfferId);
                        //body.find('#labOfferPlanId').val(data.id);
                        //body.find('#sampleType').val(sampleType);
                    },
                    end: function () {
                        table.reload('eiaClientList');
                    },
                    min: function () {
                        $(".layui-layer-title").text("检测计划");
                    },
                    restore: function () {
                        $(".layui-layer-title").text(" ");
                    }
                });
                break;
        }
    });
});