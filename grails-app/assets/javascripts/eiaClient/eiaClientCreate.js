layui.use(['jquery', 'layer', 'table', 'form'], function(){
    var $ = layui.jquery,
        layer = layui.layer,
        table = layui.table,
        form = layui.form;
    var eiaClientId = $('#eiaClientId').val();
    //工具函数在url中获取指定参数值
    var getParamFromUrl = function(url,param){
        if(url.indexOf('?')!==-1){
            var params = url.split("?")[1].split('&');
            for(var i=0; i<params.length; i++){
                if(params[i].indexOf(param) !== -1){
                    return params[i].replace(param+"=","");
                }
            }
            // console.log('该url中无参数'+param);
        }else{
            // console.log('该url中无参数');
        }

    };
    $(function () {
        //页面类型：0:新建 ； 1：编辑
        var pageType = getParamFromUrl(document.location.href,"pageType");

        //设置页面名称
        var $pageTitle = $('.pageTitle');
        pageType == 0 ? $pageTitle.html('新增客户') : $pageTitle.html('编辑客户');

        //重置按钮显隐
        pageType == 0 ? "" : $('.resetBtn').hide();




        //基本信息回显
        if(pageType == 1){
            var eiaClientId = parent.$('#eiaClientId').val();
            $.ajax({
                url:"/eia/eiaClient/getEiaClientDataMap?eiaClientId="+eiaClientId,
                type:"POST",
                data:{},
                dataType: "json",
                success: function (data) {
                    $('#clientName').val(data.data.clientName);
                    $('#clientAddress').val(data.data.clientAddress);
                    $('#clientPostCode').val(data.data.clientPostCode);
                    $('#clientCorporate').val(data.data.clientCorporate);
                    $('#clientFax').val(data.data.clientFax);
                }
            });
        }

        //联系人表格回显
        var tableUrl = "../eiaClient/getEiaClientContactsDataList?eiaClientId="+eiaClientId;
        table.render({
            id: 'contactsList',
            elem: '#contactsList',
            url:tableUrl,
            toolbar: '#tableTopTmp1',
            defaultToolbar:['filter', 'print', 'exports'],
            cols: [[
                {fixed: 'left', title: '序号',width:'7%',align: "center",templet: "#indexTable"},
                {field:'contactName',width:'25%', title: '联系人名称',align: "center"},
                {field:'contactPosition',width:'15%', title: '职务',align: "center"},
                {field:'contactPhone',width:'20%', title: '联系电话',align: "center"},
                {field:'contactEmail',width:'20%', title: '电子邮件',align: "center"},
                {field:'clientFax',width:'15%', title: '传真',align: "center"},
                {fixed: 'right', title: '操作',width:'10%',align: "center", toolbar: '#mlTool',align: "center"}
            ]],
            page: true,
            even: true,
            limit: 10,
            done: function () {
                //新增联系人按钮显隐
                pageType == 0 ? $('.addContactBtn').css('display','none') : $('.noticeTag').css('display','none');
            }
        });


        //财务开户信息
        table.render({
            id: 'eiaInvoiceList',
            elem: '#eiaInvoiceList',
            url:'/eia/eiaClientConfig/invoiceSelectQueryPage?eiaClientId='+eiaClientId+"&invoiceType=1",
            toolbar: '#tableTopTmp2',
            defaultToolbar:['filter', 'print', 'exports'],
            cols: [[
                {fixed: 'left', title: '序号',width:'7%',align: "center",templet: "#indexTable"},
                {field:'clientName',width:'30%', title: '开票单位名称',align: "center"},
                {field:'taxRegCode',width:'15%', title: '税务登记代码',align: "center"},
                {field:'bankName',width:'15%', title: '开户行',align: "center"},
                {field:'bankAccount',width:'15%', title: '开户行户名',align: "center"},
                {field:'addrTel',width:'15%', title: '地址及电话',align: "center"},
                {fixed: 'right', title: '操作',width:'10%',align: "center", toolbar: '#clientTool',align: "center"}
            ]],
            page: true,
            even: true,
            limit: 10,
            done: function () {
                //财务开户信息
                pageType == 0 ? $('.addInvoiceBtn').css('display','none') : $('.noticeCissTag').css('display','none');
            }
        });

        //监听联系人工具条
        table.on('tool(contactsList)', function (obj) {
            var data = obj.data;
            var clientContactsId = data.id;
            $('#clientContactsId').val(clientContactsId);
            if (obj.event === 'eiaEdit') {    //编辑
                pageUrl = '../eiaClient/eiaClientContactsCreate?pageType=1&clientContactsId='+clientContactsId;
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
                        body.find('#clientContactsId').val(data.clientContactsId);
                    },
                    end: function () {
                        table.reload('contactsList');
                        $('#clientContactsId').val("");
                    },
                    min: function () {
                        $(".layui-layer-title").text("修改联系人");
                    },
                    restore: function () {
                        $(".layui-layer-title").text(" ");
                    }
                });
            }
            else if(obj.event === 'eiaDel'){    //删除
                layer.confirm('确定要删除该联系人吗?', {icon: 3}, function (index) {
                    var loadingIndex = layer.load(1, {time: 10*1000,shade: 0.1});
                    $.post("../eiaClient/eiaClientContactsDel", {clientContactsId: data.id}, function (data) {
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

        });
        //监听财务开户信息工具条
        table.on('tool(eiaInvoiceList)', function (obj) {
            var data = obj.data;
            var eiaClientConfigId = data.id;
            $('#eiaClientConfigId').val(eiaClientConfigId);
            if (obj.event === 'eiaEdit') {    //编辑
                pageUrl = '../eiaClientConfig/eiaCliConfigCreate?pageType=1';
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
                        body.find('#clientContactsId').val(data.clientContactsId);
                    },
                    end: function () {
                        table.reload('eiaInvoiceList');
                        $('#clientContactsId').val("");
                    },
                    min: function () {
                        $(".layui-layer-title").text("修改财务开户信息");
                    },
                    restore: function () {
                        $(".layui-layer-title").text(" ");
                    }
                });
            }
            else if(obj.event === 'eiaCissDel'){    //删除
                layer.confirm('确定要删除该开户信息吗?', {icon: 3}, function (index) {
                    var loadingIndex = layer.load(1, {time: 10*1000,shade: 0.1});
                    $.post("../eiaClientConfig/eiaClientConfigDel", {eiaClientConfigId: data.id}, function (data) {
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

        });

       /* //新增联系人按钮
        $('.addContactBtn').click(function () {
            pageUrl = '../eiaClient/eiaClientContactsCreate?pageType=0';
            var index = layer.open({
                title:' ',
                type: 2,
                shade: false,
                maxmin: true,
                skin: 'larry-green',
                area: ['100%', '100%'],
                content: pageUrl,
                success:function () {
                    var body = layer.getChildFrame('body', index);
                },
                end: function () {
                    var eiaClientId = $('#eiaClientId').val();
                    table.reload("contactsList", {
                        url: "/eia/eiaClient/getEiaClientContactsDataList?eiaClientId="+eiaClientId});

                },
                min: function () {
                    $(".layui-layer-title").text("");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        });*/

        //监听头部工具栏事件
        //监听事件
        table.on('toolbar(contactsList)', function(obj){
            switch(obj.event){
                case 'addContactBtn':
                    pageUrl = '../eiaClient/eiaClientContactsCreate?pageType=0';
                    var index = layer.open({
                        title:' ',
                        type: 2,
                        shade: false,
                        maxmin: true,
                        skin: 'larry-green',
                        area: ['100%', '100%'],
                        content: pageUrl,
                        success:function () {
                            var body = layer.getChildFrame('body', index);
                        },
                        end: function () {
                            var eiaClientId = $('#eiaClientId').val();
                            table.reload("contactsList", {
                                url: "/eia/eiaClient/getEiaClientContactsDataList?eiaClientId="+eiaClientId});

                        },
                        min: function () {
                            $(".layui-layer-title").text("");
                        },
                        restore: function () {
                            $(".layui-layer-title").text(" ");
                        }
                    });
                    break;
            };
        });

        table.on('toolbar(eiaInvoiceList)', function(obj){
            switch(obj.event){
                case 'addInvoiceBtn':
                    pageUrl = '../eiaClientConfig/eiaCliConfigCreate?pageType=0';
                    var index = layer.open({
                        title:' ',
                        type: 2,
                        shade: false,
                        maxmin: true,
                        skin: 'larry-green',
                        area: ['100%', '100%'],
                        content: pageUrl,
                        success:function () {
                            var body = layer.getChildFrame('body', index);
                        },
                        end: function () {
                            var eiaClientId = $('#eiaClientId').val();
                            table.reload("eiaInvoiceList", {
                                url: "/eia/eiaClientConfig/invoiceSelectQueryPage?eiaClientId="+eiaClientId+"&invoiceType=1"});
                        },
                        min: function () {
                            $(".layui-layer-title").text("");
                        },
                        restore: function () {
                            $(".layui-layer-title").text(" ");
                        }
                    });
                    break;
            };
        });
        var addContactBtnShow = function(){
            $('.addContactBtn').css('display','inline-block');
            $('.addInvoiceBtn').css('display','inline-block');
            $('.noticeTag').css('display','none');
            $('.noticeCissTag').css('display','none');

        };
        var addContactBtnHide = function(){
            $('.addContactBtn').css('display','none');
            $('.addInvoiceBtn').css('display','none');
            $('.noticeTag').css('display','inline-block');
            $('.noticeCissTag').css('display','inline-block');

        };



        //表单提交
        form.on('submit(save)', function(data){
            var loadingIndex = layer.load(1, {time: 10*1000,shade: 0.1});
            var eiaClientId = $("#eiaClientId").val();
            var actionUrl = "../eiaClient/eiaClientSave?eiaClientId="+eiaClientId;
            $.post(actionUrl, data.field, function (data) {
                if (data.code == 0) {
                    layer.msg('保存成功', {icon: 1, time: 1000,shade: 0.1}, function () {
                        pageType = 1;
                        $("#eiaClientId").val(data.data.id)
                        var eiaClientId = $("#eiaClientId").val();
                        if(eiaClientId || pageType==1){
                            addContactBtnShow();
                        }else {
                            addContactBtnHide();
                        }
                        var index = parent.layer.getFrameIndex(window.name);
                        parent.layui.table.reload("eiaClientList");
                    });
                } else {
                    layer.msg(data.msg, {icon: 2, time: 1000,shade: 0.1});
                }
                layer.close(loadingIndex);
            });
            return false;
        });
        form.verify({
            number_noreq: [
                /^$|\d$/
                ,'只能填写数字'
            ]
        });
    });
});