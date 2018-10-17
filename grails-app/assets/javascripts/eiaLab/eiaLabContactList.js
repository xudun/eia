layui.use(['layer', 'form', 'table'], function () {
    var $ = layui.$,
        layer = layui.layer,
        table = layui.table;
    /**
     * 渲染联系人列表
     */
    var wtClientId = parent.$('#wtClientId').val();
    var eiaClientId = parent.$('#eiaClientId').val();
    var pageUrl;
    if (wtClientId) {
        pageUrl =  '../eiaLabOffer/getLabClientContactList?clientId=' + wtClientId;
    } else {
        pageUrl = '../eiaClient/getEiaClientContactsDataList?eiaClientId=' + eiaClientId;
    }
    table.render({
        id: 'eiaLabContactList',
        elem: '#eiaLabContactList',
        url: pageUrl,
        toolbar: '#tableTopTmp',
        defaultToolbar:['filter', 'print', 'exports'],
        cols: [
            [
                {field: 'contactName', title: '联系人', width: '49%', align: 'center', rowspan: 2},
                {field: 'contactPhone', title: '联系电话', width: '45%', align: 'center', rowspan: 2},
                {fixed: 'right', title: '操作', width: 90, rowspan: 2, align: 'center', toolbar: '#eiaLabContactListBar', fixed: "right"}
            ]
        ],
        page: true,
        even: true,
        limit: 10
    });
    /**
     * 监听工具条
     */
    table.on('tool(eiaLabContactList)', function (obj) {
        var data = obj.data;
        if (wtClientId) {
            parent.$("#wtClientContact").val(data.contactName);
            parent.$("#wtClientPhone").val(data.contactPhone);
        } else {
            parent.$("#contactPerson").val(data.contactName);
            parent.$("#contactPersonMobil").val(data.contactPhone);
        }
        var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
        parent.layer.close(index); //再执行关闭
    });

    //监听头部工具栏事件
    //监听事件
    table.on('toolbar(eiaLabContactList)', function(obj){
        switch(obj.event){
            case 'contactSelect':
                var contactInfo = $("#contactInfo").val();
                table.reload('eiaLabContactList', {
                    where: {
                        contactInfo: contactInfo
                    }, page: {
                        curr: 1
                    }
                });
                break;
        }
    });
});