layui.use(['jquery', 'table'], function(){
    var $ = layui.jquery,
        table = layui.table;

    var eiaClientId = $('#eiaClientId').val();
    var clientType = getParamFromUrl(document.location.href,"clientType");

    //渲染联系人表格
    table.render({
        id: 'contactList',
        elem: '#contactList',
        url:'/eia/eiaClient/getEiaClientContactsDataList?eiaClientId='+eiaClientId,
        toolbar: '#tableTopTmp',
        defaultToolbar:['filter', 'print', 'exports'],
        cols: [[
            {fixed: 'left', title: '序号',width:'6%',align: "center",templet: "#indexTable"},
            {field:'contactName',width:'24.1%', title: '联系人名称', edit: 'text',align: "center"},
            {field:'contactPosition',width:'20%',  edit: 'text',title: '职务',align: "center"},
            {field:'contactPhone',width:'20%', title: '联系电话',edit: 'text', align: "center"},
            {field:'contactEmail',width:'20%', title: '电子邮件',edit: 'text', align: "center"},
            {fixed: 'right', title: '操作',width:'10%',align: "center", toolbar: '#clientTool',align: "center"}
        ]],
        page: true,
        even: true,
        limit: 10
    });

    //监听工具条
    table.on('tool(contactList)', function (obj) {
        var data = obj.data;
        if (obj.event === 'eiaAddContact') {    //选择联系人
            //父父层赋值
            if(!data.contactName){
                layer.msg('联系人名称不能为空，请点击单元格修改或在客户查询中修改', {icon: 2, time: 3000, shade: 0.1}, function () {
                });
                return
            }else if(!data.contactPhone){
                layer.msg('联系电话不能为空，请点击单元格修改或在客户查询中修改', {icon: 2, time: 2000, shade: 0.1}, function () {
                });
                return
            }else if(!data.contactPosition){
                layer.msg('职务不能为空，请点击单元格修改或在客户查询中修改', {icon: 2, time: 2000, shade: 0.1}, function () {
                });
                return
            }else if(!data.contactEmail){
                layer.msg('电子邮件不能为空，请点击单元格修改或在客户查询中修改', {icon: 2, time: 2000, shade: 0.1}, function () {
                });
                return
            }
            if (clientType == 'client') {
                parent.$("#contactName").val(data.contactName);
                parent.$("#contactPhone").val(data.contactPhone);
            } else if (clientType == "ownerClient") {
                parent.$("#ownerContactName").val(data.contactName);
                parent.$("#ownerContactPhone").val(data.contactPhone);
            }
            //关闭层
            var curIndex = parent.layer.getFrameIndex(window.name);
            parent.layer.close(curIndex);
        }else if(obj.event === 'eiaDelContact'){
            layer.confirm('确定要删除该信息吗?', {icon: 3}, function (index) {
                $.ajax({
                    url:"/eia/eiaClient/eiaClientContactsDel?clientContactsId="+data.id,
                    type:"POST",
                    data:{},
                    dataType: "json",
                    success: function (data) {
                        layer.msg('删除成功！', {icon: 1, time: 1500,shade: 0.1}, function () {
                            obj.del();
                        });
                    }
                });
            },function (index) {
                //取消
            });
        }

        $.ajax({
            url:"/eia/eiaClient/clientContactDel?clientContactId="+data.id,
            type:"POST",
            data:param,
            dataType: "json",
            success: function (data) {
                table.reload('contactList');
            }
        });
    });
    layui.use('table', function(){
        var table = layui.table;

        //监听单元格编辑
        table.on('edit(contactList)', function(obj){
            var value = obj.value //得到修改后的值
                ,data = obj.data //得到所在行所有键值s
                ,field = obj.field; //得到字段
            if(field=="contactName"){
                if(value==''){
                    layer.msg('该数据不能为空!', {icon: 2, time: 2000, shade: 0.1}, function () {
                    });
                    return
                }
            }else if(field=="contactPhone"){
                if(value==''){
                    layer.msg('该数据不能为空!', {icon: 2, time: 2000, shade: 0.1}, function () {
                    });
                    return
                }
            }else if(field=="contactPosition"){
                if(value==''){
                    layer.msg('该数据不能为空!', {icon: 2, time: 2000, shade: 0.1}, function () {
                    });
                    return
                }
            }else if(field=="contactEmail"){
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
                    url:"/eia/eiaClient/clientContactUpdate?clientContactId="+data.id+"&eiaClientId="+eiaClientId,
                    type:"POST",
                    data:param,
                    dataType: "json",
                    success: function (data) {
                        if (data.code == 0) {
                            layer.msg(data.msg, {icon: 1, time: 1000,shade: 0.1}, function () {});
                            table.reload('contactList');
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
    //新增按钮
    $('.larry-btn a.layui-btn').click(function () {
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });
    var active = {
        contactAdd: function () {    //新增
            $.ajax({
                url:"/eia/eiaClient/clientContactAddUpdate?eiaClientId="+eiaClientId,
                type:"POST",
                data:{},
                dataType: "json",
                success: function (data) {
                    table.reload('contactList');
                }
            });
        }
    }

    //监听头部工具栏事件
    //监听事件
    table.on('toolbar(contactList)', function(obj){
        switch(obj.event){
            case 'contactAdd':
                $.ajax({
                    url:"/eia/eiaClient/clientContactAddUpdate?eiaClientId="+eiaClientId,
                    type:"POST",
                    data:{},
                    dataType: "json",
                    success: function (data) {
                        table.reload('contactList');
                    }
                });
                break;
        };
    });
});