layui.use(['jquery', 'layer', 'table', 'element', 'form'], function () {
    var $ = layui.jquery,
        layer = layui.layer,
        table = layui.table,
        element = layui.element,
        form = layui.form;
    // Tab切换
    element.on('tab(contractOfferTab)', function (data) {
        console.log(data);
        var cur_index = data.index;
        tabAction(cur_index);
    });

    //参数： tabType: 0：合同、 1：报价
    var tabAction = function (tabType) {
        var thContract = [[
                {fixed: 'left', title: '序号', width: '6%', align: "center", templet: "#indexTable"},
                {field: 'contractNo', width: '15%', title: '合同编号', align: "center"},
                {field: 'contractName', width: '25%', title: '合同名称', align: "center"},
                {field: 'contractType', width: '15%', title: '合同类型', align: "center"},
                {field: 'contractMoney', width: '15%', title: '合同金额（万元）', align: "center"},
                {field: 'profitMargin', width: '10%', title: '利润率', align: "center"},
                {field: 'contractDate', width: '15%', title: '合同时间', align: "center"},
                {field: 'taskNo', width: '15%', title: '任务编号', align: "center"},
                {field: 'taskName', width: '20%', title: '任务名称', align: "center"},
                {field: 'inputDept', width: '15%', title: '录入部门', align: "center"},
                {field: 'inputUser', width: '10%', title: '录入人', align: "center"},
                {fixed: 'right', title: '操作', width: '20%', align: "center", toolbar: '#contractTool', align: "center"}
            ]],
            thOffer = [[
                {fixed: 'left', title: '序号', width: '6%', align: "center", templet: "#indexTable"},
                {field: 'offerNo', width: '15%', title: '报价编号', align: "center"},
                {field: 'offerName', width: '25%', title: '合同名称', align: "center"},
                {field: 'contractType', width: '15%', title: '合同类型', align: "center"},
                {field: 'offerMoney', width: '15%', title: '报价金额（万元）', align: "center"},
                {field: 'offerDate', width: '15%', title: '报价日期', align: "center"},
                {field: 'inputDept', width: '15%', title: '录入部门', align: "center"},
                {field: 'inputUser', width: '10%', title: '录入人', align: "center"},
                {fixed: 'right', title: '操作', width: '16%', align: "center", toolbar: '#offerTool', align: "center"}
            ]];
        var tableList = tabType == 1 ? "eiaOfferList" : "eiaContractList",
            url = tabType == 1 ? "/eia/eiaContract/getEiaOfferDataList" : "/eia/eiaContract/getEiaContractDataList",
            cols = tabType == 1 ? thOffer : thContract,
            toolbar= tabType == 1 ? '#tableTopTmp2':'#tableTopTmp';


        //渲染表格
        table.render({
            id: tableList,
            elem: '#' + tableList,
            url: url,
            cols: cols,
            toolbar:toolbar,
            defaultToolbar:['filter', 'print', 'exports'],
            page: true,
            even: true,
            limit: 10
        });

    };
    tabAction(0);

    //监听报价表格工具条
    table.on('tool(eiaOfferList)', function (obj) {
        var data = obj.data;
        if (obj.event === 'eiaEdit') {    //编辑
            var pageUrl = '/eia/eiaContract/eiaOfferCreate?pageType=1&eiaOfferId=' + data.id;
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
                    body.find('#offerId').val(data.id);
                },
                end: function () {
                    // table.reload('eiaOfferList');
                    $('#offerId').val("");
                },
                min: function () {
                    $(".layui-layer-title").text("编辑报价");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        }
        else if (obj.event === 'eiaDel') {    //删除
            layer.confirm('确定要删除该报价吗?', {icon: 3}, function (index) {
                var loadingIndex = layer.load(1, {time: 10*1000,shade: 0.1});
                $.post("../eiaContract/eiaOfferDel", {eiaOfferId: data.id}, function (data) {
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
        else if (obj.event === 'eiaCheck') {    //查看
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
                    body.find('#offerId').val(data.id);
                },
                end: function () {
                    $('#offerId').val("");
                },
                min: function () {
                    $(".layui-layer-title").text("查看报价");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        }
        else if (obj.event === 'offerExport') {    //下载
            layer.msg('正在导出...', {icon: 16, shade: 0.01});
            window.location.href = "../exportContract/downloadOffer?eiaOfferId=" + data.id;
        }
        else if (obj.event === 'eiaGenerate') {   //生成合同
            var loadingIndex = layer.load(1, {time: 10*1000,shade: 0.1});
            var pageUrl = '/eia/eiaContract/eiaConGenerate?eiaOfferId=' + data.id;

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
                    // table.reload('eiaOfferList');
                    $('#contractId').val("");
                },
                min: function () {
                    $(".layui-layer-title").text("生成合同");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        }
        else if (obj.event === 'offerSub') {
            var eiaOfferId = data.id;
            var loadingIndex = layer.load(1, {time: 10*1000,shade: 0.1});
            layer.confirm('是否提交报价流程?', {icon: 3}, function (index) {
                $.post("../eiaContract/eiaOfferSub", {eiaOfferId: eiaOfferId}, function (result) {
                    if (result.code == 0) {
                        table.reload('eiaOfferList');
                        layer.msg("报价提交成功！", {icon: 1, time: 1500})
                    } else {
                        layer.msg(result.msg, {icon: 2, time: 1500})
                    }
                    layer.close(loadingIndex);
                });
            }, function (index) {
                // $.post("../eiaContract/updateEiaOfferIfSub", {eiaOfferId: eiaOfferId, ifSub: -1}, function (result) {
                //     if (result.code == 0) {
                //         table.reload('eiaOfferList');
                //         layer.msg(result.msg, {icon: 1, time: 1500})
                //     } else {
                //         layer.msg(result.msg, {icon: 2, time: 1500})
                //     }
                //     layer.close(loadingIndex);
                // });
            });
        }
        else if (obj.event === 'offerFlow') {    //流程
            pageUrl = '/eia/eiaWorkFlow/eiaWorkFlowTrans';
            $('#tableNameId').val(data.id);
            $('#tableName').val("EiaOffer");
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
                    $('#tableNameId').val("");
                    $('#tableName').val("");
                },
                min: function () {
                    $(".layui-layer-title").text("报价流程");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        }
    });
    //监听合同表格工具条
    table.on('tool(eiaContractList)', function (obj) {
        var data = obj.data;
        //$('#eiaContractId').val(data.id)
        if (obj.event === 'eiaEdit') {    //编辑
            var pageUrl = '/eia/eiaContract/eiaContractCreate?pageType=1&eiaContractId=' + data.id;

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
                    // table.reload('eiaOfferList');
                    $('#contractId').val("");
                },
                min: function () {
                    $(".layui-layer-title").text("编辑合同");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        }
        else if (obj.event === 'eiaDel') {    //删除
            layer.confirm('确定要删除该合同吗?', {icon: 3}, function (index) {
                var loadingIndex = layer.load(1, {time: 10*1000,shade: 0.1});
                $.post("../eiaContract/eiaContractDel", {eiaContractId: data.id}, function (data) {
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
        else if (obj.event === 'eiaCheck') {    //查看
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
        else if (obj.event === 'contractExport') {    //下载
            var contractId = data.id;
            $.post("../exportContract/checkProductType", {contractId: contractId},function (result) {
                if (result.data == "noStaff") {
                    layer.msg('员工资料需补充，请联系人力部门更新资料！', {icon: 2, time: 1500});
                } else if (result.data){
                    layer.msg('正在导出...', {icon: 16, shade: 0.01});
                    window.location.href = "../exportContract/downloadContract?contractId=" + contractId;
                } else {
                    layer.msg('尚无对应合同模板，不能导出！', {icon: 2, time: 1500});
                }
            });
        } else if (obj.event === 'contractSub') {
            var eiaContractId = data.id;
            console.log(data)
            var loadingIndex = layer.load(1, {time: 10*1000,shade: 0.1});
            layer.confirm('是否提交合同流程?', {icon: 3}, function (index) {
                $.post("../eiaContract/eiaContractSub", {eiaContractId: eiaContractId}, function (result) {
                    if (result.code == 0) {
                        table.reload('eiaContractList');
                        layer.msg("合同提交成功！", {icon: 1, time: 1500})
                    }else{
                        layer.msg(result.msg, {icon: 2, time: 1500})
                    }
                    layer.close(loadingIndex);
                });
            }, function (index) {
                //取消
            });
        }else if (obj.event === 'contractFlow') {    //流程
            pageUrl = '/eia/eiaWorkFlow/eiaWorkFlowTrans';
            $('#tableNameId').val(data.id);
            $('#tableName').val("EiaContract");
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
                    $('#tableNameId').val("");
                    $('#tableName').val("");
                },
                min: function () {
                    $(".layui-layer-title").text("合同流程");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        }
    });

    //查询、新增按钮（报价、合同）
    $('.larry-btn a.layui-btn').click(function () {
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });
    var active = {
        offerSelect: function () {    //查询
            var offerName = $("#offerName").val();
            table.reload('eiaOfferList', {
                where: {
                    offerName: offerName
                }
            });
        },
        offerAdd: function () {    //报价新增
            pageUrl = '/eia/eiaContract/eiaOfferCreate?pageType=0';
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

                },
                min: function () {
                    $(".layui-layer-title").text("新增报价");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        },
        contractSelect: function () {    //合同查询
            var contractName = $("#contractName").val();
            table.reload('eiaContractList', {
                where: {
                    contractName: contractName
                }
            });
        },
        contractAdd: function () {    //合同新增
            pageUrl = '/eia/eiaContract/eiaContractCreate?pageType=0';
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

                },
                min: function () {
                    $(".layui-layer-title").text("新增合同");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        }
    }

    //监听头部工具栏事件
    //监听事件
    table.on('toolbar(eiaContractList)', function(obj){
        switch(obj.event){
            case 'contractSelect':
                var contractName = $("#contractName").val();
                table.reload('eiaContractList', {
                    where: {
                        contractName: contractName
                    }
                });
                break;
            case 'contractAdd':
                pageUrl = '/eia/eiaContract/eiaContractCreate?pageType=0';
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

                    },
                    min: function () {
                        $(".layui-layer-title").text("新增合同");
                    },
                    restore: function () {
                        $(".layui-layer-title").text(" ");
                    }
                });
                break;
        };
    });

    table.on('toolbar(eiaOfferList)', function(obj){
        switch(obj.event){
            case 'offerSelect':
                var offerName = $("#offerName").val();
                table.reload('eiaOfferList', {
                    where: {
                        offerName: offerName
                    }
                });
                break;
            case 'offerAdd':
                pageUrl = '/eia/eiaContract/eiaOfferCreate?pageType=0';
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

                    },
                    min: function () {
                        $(".layui-layer-title").text("新增报价");
                    },
                    restore: function () {
                        $(".layui-layer-title").text(" ");
                    }
                });
                break;
        };
    });


    //监听复选框   (code值改动两处)
    form.on('checkbox(contractDownload1)', function (data) {
        var ifChecked = data.elem.checked,
            cDValue = ifChecked ? 0 : 1; //code值改动

        $('#contractDownload2').prop('checked', !ifChecked);
        form.render('checkbox');

        $('#contractDownload').val(cDValue);
    });
    form.on('checkbox(contractDownload2)', function (data) {
        var ifChecked = data.elem.checked,
            cDValue = ifChecked ? 1 : 0; //code值改动

        $('#contractDownload1').prop('checked', !ifChecked);
        form.render('checkbox');

        $('#contractDownload').val(cDValue);
    });
});