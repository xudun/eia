layui.use(['jquery', 'layer', 'table', 'element'], function(){
    var $ = layui.jquery,
        layer = layui.layer,
        table = layui.table,
        element = layui.element;
    // Tab切换
    element.on('tab(contractOfferTab)', function(data){
        console.log(data);
        var cur_index = data.index;
        tabAction(cur_index);
    });
    $.post("../eiaInvoice/eiaInvoiceRevCissNum", {}, function (data) {
        if (data.code == 0) {
            document.getElementById('kpqr').innerText=data.data;
        }else{
            document.getElementById('kpqr').innerText=0;
        }
    });
    $.post("../eiaInvoice/eiaInvoiceOutCissNum", {}, function (data) {
        if (data.code == 0) {
            document.getElementById('czqr').innerText=data.data
        }else{
            document.getElementById('czqr').innerText=0;
        }
    });
    $.post("../eiaAccountExpect/eiaExpectCissNum", {}, function (data) {
        if (data.code == 0) {
            document.getElementById('czyj').innerText=data.data
        }else{
            document.getElementById('czyj').innerText=0;
        }
    });
    //参数
    var tabAction = function(tabType){
        var thExpect = [[
            {fixed: 'left', title: '序号',width:'6%',align: "center",templet: "#indexTable"},
            {field:'contractNo',width:'15%', title: '合同编号',align: "center"},
            {field:'contractName',width:'25%', title: '合同名称',align: "center"},
            {field:'expectInvoiceMoney',width:'15%', title: '预计开票金额(万元)',align: "center"},
            {field:'expectIncomeMoney',width:'15%', title: '预计收款金额(万元)',align: "center"},
            {field:'expertFee',width:'15%', title: '专家费(万元)',align: "center"},
            {field:'monitorFee',width:'15%', title: '检测费(万元)',align: "center"},
            {field:'otherFee',width:'15%', title: '其他费(万元)',align: "center"},
            {field:'expectPeriod',width:'15%', title: '预计期次',align: "center"},
            {field:'inputDept',width:'15%', title: '录入部门',align: "center"},
            {field:'inputUser',width:'15%', title: '录入人',align: "center"},
            {fixed: 'right', title: '操作',width:'15%',align: "center", toolbar: '#expectTool',align: "center"}
        ]],
            thInvoiceOut = [[
                {fixed: 'left', title: '序号',width:'6%',align: "center",templet: "#indexTable"},
                {field:'contractNo',width:'15%', title: '合同编号',align: "center"},
                {field:'contractName',width:'25%', title: '合同名称',align: "center"},
                {field:'contractMoney',width:'15%', title: '合同金额(万元)',align: "center"},
                {field:'incomeMoney',width:'15%', title: '实际收款情况(万元)',align: "center"},
                {field:'accountState',width:'15%', title: '当前状态',align: "center"},
                {field:'costTypes',width:'15%', title: '费用类型',align: "center"},
                {field:'noteIncomeDate',width:'15%', title: '出账时间',align: "center"},
                {field:'noteIncomeMoney',width:'15%', title: '出账金额(万元)',align: "center"},
                {field:'inputDept',width:'15%', title: '录入部门',align: "center"},
                {field:'inputUser',width:'15%', title: '录入人',align: "center"},
                {fixed: 'right', title: '操作',width:'15%',align: "center", toolbar: '#invoiceOutTool',align: "center"}
            ]],
            thInvoice = [[
                {fixed: 'left', title: '序号',width:'6%',align: "center",templet: "#indexTable"},
                {field:'contractNo',width:'15%', title: '合同编号',align: "center"},
                {field:'contractName',width:'25%', title: '合同名称',align: "center"},
                {field:'accountState',width:'15%', title: '当前状态',align: "center"},
                {field:'billDate',width:'15%', title: '申请开票时间',align: "center"},
                {field:'billMoney',width:'15%', title: '开票金额(万元)',align: "center"},
                {field:'inputDept',width:'15%', title: '录入部门',align: "center"},
                {field:'inputUser',width:'15%', title: '录入人',align: "center"},
                {fixed: 'right', title: '操作',width:'15%',align: "center", toolbar: '#invoiceTool',align: "center"}
            ]],
            thContract = [[
                {fixed: 'left', title: '序号',width:'6%',align: "center",templet: "#indexTable", rowspan: 2},
                {field: 'contractNo', title: '合同编号', width: '15%', rowspan: 2, align: "center"} //rowspan即纵向跨越的单元格数
                ,{field: 'contractName', title: '合同名称', width: '25%', rowspan: 2, align: "center"}
                ,{field: 'contractMoney', title: '合同金额(万元)', width: '15%', rowspan: 2, align: "center"}
                ,{align: 'center', title: '进账信息', colspan: 4, align: "center"} //colspan即横跨的单元格数，这种情况下不用设置field和width
                ,{align: 'center', title: '出账信息', colspan: 3, align: "center"} //colspan即横跨的单元格数，这种情况下不用设置field和width
                ,{field: 'profitMargin', title: '利润率', width: '15%', rowspan: 2, align: "center"}
                ,{fixed: 'right', title: '操作',width:'15%',align: "center", toolbar: '#contractTool',align: "center"}

            ], [
                {field: 'billMoney', title: '开票金额(万元)', width: '15%', align: "center"}
                ,{field: 'noteIncomeMoney', title: '进账金额(万元)', width: '15%', align: "center"}
                ,{field: 'outstandingAmount', title: '未结清(万元)', width: '15%', align: "center"}
                ,{field: 'ifFinished', title: '是否结清', width: '15%', align: "center"},
                {field: 'expertFee', title: '专家费(万元)', width: '15%', align: "center"}
                ,{field: 'monitorFee', title: '检测费(万元)', width: '15%', align: "center"}
                ,{field: 'otherFee', title: '其他(万元)', width: '15%', align: "center"}
            ]];
        //底部汇总数据格式
        var tableList
        if(tabType==0){
            tableList = "eiaContractList"
            url = "/eia/eiaInvoice/getInvoiceRevenueFinData"
            cols = thContract;
        } else if(tabType==1){
            tableList = "eiaInvoiceList"
            url = "/eia/eiaInvoice/eiaInvoiceFinQuery?accountState=1"
            cols = thInvoice;
        }else if(tabType==2){
            tableList = "eiaInvoiceOutList"
            url = "/eia/eiaIncomeOut/eiaInvoiceOutFinQuery?accountState=1"
            cols = thInvoiceOut;
        }else if(tabType==3){
            tableList = "eiaAccountExpectList"
            url = "/eia/eiaAccountExpect/eiaExpectQuery?accountState=1"
            cols = thExpect;
        }
        //渲染表格
        table.render({
            id: tableList,
            elem: '#'+tableList,
            footer:true,
            url:url,
            cols: cols,
            page: true,
            even: true,
            limit: 10,
            done: function (res, curr, count) {
                console.log('res:');
                console.log(res);
                console.log("this");
                console.log(this);
                //添加小计，总计
                var count_fields = ['contractMoney','billMoney','billMoney1','noteIncomeMoney','outstandingAmount','expertFee','monitorFee','otherFee'];
                //加入小计
                tableAddSubTotalRow(this,res,count_fields);
            }
        });

    };
    tabAction(0);

    //监听开票表格工具条
    table.on('tool(eiaInvoiceList)', function (obj) {
        var data = obj.data;
        $('#eiaInvoiceId').val(data.id)
        if (obj.event === 'eiaEdit') {    //编辑
            pageUrl = '/eia/eiaIncomeOut/eiaInvoiceIncomeCreate';
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
                    body.find('#offerId').val(data.id);
                },
                end: function () {
                    table.reload('eiaInvoiceList');
                    $('#offerId').val("");
                },
                min: function () {
                    $(".layui-layer-title").text("编辑开票信息");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        }
        else if(obj.event === 'eiaCheck'){    //查看
            var eiaInvoiceId = data.id;
            var pageUrl = '../eiaInvoice/eiaInvoiceReveDetail?eiaInvoiceId='+eiaInvoiceId;
            var index = layer.open({
                title:' ',
                type: 2,
                shade: false,
                maxmin: true,
                skin: 'larry-green',
                area: ['100%', '100%'],
                content: pageUrl,
                success:function (layero, index) {
                },
                end: function () {
                },
                min: function () {
                    $(".layui-layer-title").text("查看开票");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        }
        else if(obj.event === 'eiaSubmit'){    //下载
            layer.confirm('确定要确认该开票信息吗?', {icon: 3}, function (index) {
                var loadingIndex = layer.load(1, {time: 10*1000,shade: 0.1});
                $.post("../eiaInvoice/eiaInvoiceConfirm", {eiaInvoiceId: data.id}, function (data) {
                    if (data.code == 0) {
                        layer.msg('提交成功！', {icon: 1, time: 1500,shade: 0.1}, function () {
                            $.post("../eiaInvoice/eiaInvoiceRevCissNum", {}, function (data) {
                                if (data.code == 0) {
                                    document.getElementById('kpqr').innerText=data.data;
                                }else{
                                    document.getElementById('kpqr').innerText=0;
                                }
                            });
                            table.reload('eiaInvoiceList');

                        });
                    } else {
                        if (data.msg) {
                            layer.msg("处理失败！<br/><div style='color:red;'>错误信息：" + data.msg + "</div>", {
                                icon: 2,
                                time: 1500
                            });
                        } else {
                            layer.msg('提交失败！', {icon: 2, time: 1500,shade: 0.1});
                        }
                    }
                    layer.close(loadingIndex);

                });
            },function (index) {
                //取消
            });

        }
        else if(obj.event === 'eiaBack'){   //开票信息驳回
            layer.confirm('确定要驳回该开票信息吗?', {icon: 3}, function (index) {
                var loadingIndex = layer.load(1, {time: 10*1000,shade: 0.1});
                $.post("../eiaInvoice/eiaInvoiceRevenueBack", {eiaInvoiceId: data.id}, function (data) {
                    if (data.code == 0) {
                        layer.msg('提交成功！', {icon: 1, time: 1500,shade: 0.1}, function () {
                            $.post("../eiaInvoice/eiaInvoiceRevCissNum", {}, function (data) {
                                if (data.code == 0) {
                                    document.getElementById('kpqr').innerText=data.data;
                                }else{
                                    document.getElementById('kpqr').innerText=0
                                }
                            });
                            table.reload('eiaInvoiceList');
                        });
                    } else {
                        if (data.msg) {
                            layer.msg("处理失败！<br/><div style='color:red;'>错误信息：" + data.msg + "</div>", {
                                icon: 2,
                                time: 1500
                            });
                        } else {
                            layer.msg('提交失败！', {icon: 2, time: 1500,shade: 0.1});
                        }
                    }
                    layer.close(loadingIndex);
                });
            },function (index) {
                //取消
            });
        }
    });
    //监听合同表格工具条
    table.on('tool(eiaContractList)', function (obj) {
        var data = obj.data;
        if (obj.event === 'eiaEdit') {    //编辑
            var pageUrl = '/eia/eiaIncomeOut/eiaInvoiceIncomeCreate?contractId='+data.id;
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
                    body.find('#contractId').val(data.id);
                },
                end: function () {
                    table.reload('eiaOfferList');
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
        else if(obj.event === 'eiaCheck'){    //查看
            $("#contractId").val(data.id)
            var pageUrl = '/eia/eiaInvoice/eiaInvoiceList?contractId='+data.id;
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
                    body.find('#contractId').val(data.contractId);
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
    //监听出账表格工具条
    table.on('tool(eiaInvoiceOutList)', function (obj) {
        var data = obj.data;
        $('#eiaIncomeOutId').val(data.id)
        if (obj.event === 'eiaEdit') {    //编辑
            var pageUrl = '/eia/eiaIncomeOut/eiaInvoiceOutEdit?eiaIncomeOutId='+data.id;
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
                    body.find('#offerId').val(data.id);
                },
                end: function () {
                    table.reload('eiaInvoiceList');
                    $('#offerId').val("");
                },
                min: function () {
                    $(".layui-layer-title").text("编辑开票信息");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        }
        else if(obj.event === 'eiaSubmit'){    //提交
            layer.confirm('确定要确认该信息吗?', {icon: 3}, function (index) {
                var loadingIndex = layer.load(1, {time: 10*1000,shade: 0.1});
                $.post("../eiaIncomeOut/eiaInvoiceOutConfirm", {eiaIncomeOutId: data.id}, function (data) {
                    if (data.code == 0) {
                        layer.msg('提交成功！', {icon: 1, time: 1500,shade: 0.1}, function () {
                            $.post("../eiaInvoice/eiaInvoiceRevCissNum", {}, function (data) {
                                if (data.code == 0) {
                                    document.getElementById('kpqr').innerText=data.data;
                                }
                            });
                            $.post("../eiaInvoice/eiaInvoiceOutCissNum", {}, function (data) {
                                if (data.code == 0) {
                                    document.getElementById('czqr').innerText=data.data
                                }
                            });
                            table.reload('eiaInvoiceOutList');
                        });
                    } else {
                        if (data.msg) {
                            layer.msg("处理失败！<br/><div style='color:red;'>错误信息：" + data.msg + "</div>", {
                                icon: 2,
                                time: 1500
                            });
                        } else {
                            layer.msg('提交失败！', {icon: 2, time: 1500,shade: 0.1});
                        }
                    }
                    layer.close(loadingIndex);
                });
            },function (index) {
                //取消
            });

        }
        else if(obj.event === 'eiaBack'){   //出账信息驳回
            layer.confirm('确定要驳回该出账信息吗?', {icon: 3}, function (index) {
                var loadingIndex = layer.load(1, {time: 10*1000,shade: 0.1});
                $.post("../eiaIncomeOut/eiaInvoiceOutBack", {eiaIncomeOutId: data.id}, function (data) {
                    if (data.code == 0) {
                        layer.msg('提交成功！', {icon: 1, time: 1500,shade: 0.1}, function () {
                            $.post("../eiaInvoice/eiaInvoiceOutCissNum", {}, function (data) {
                                if (data.code == 0) {
                                    document.getElementById('czqr').innerText=data.data
                                }else{
                                    document.getElementById('czqr').innerText=0;
                                }
                            });
                            table.reload('eiaInvoiceOutList');
                        });
                    } else {
                        if (data.msg) {
                            layer.msg("处理失败！<br/><div style='color:red;'>错误信息：" + data.msg + "</div>", {
                                icon: 2,
                                time: 1500
                            });
                        } else {
                            layer.msg('提交失败！', {icon: 2, time: 1500,shade: 0.1});
                        }
                    }
                    layer.close(loadingIndex);
                });
            },function (index) {
                //取消
            });
        }
    });
    //监听预计财务信息表格工具条
    table.on('tool(eiaAccountExpectList)', function (obj) {
        var data = obj.data;
        $('#eiaAccountExpectId').val(data.id)
        if (obj.event === 'eiaEdit') {    //编辑
            var pageUrl = '/eia/eiaIncomeOut/eiaInvoiceOutEdit?eiaIncomeOutId='+data.id;
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
                    body.find('#offerId').val(data.id);
                },
                end: function () {
                    table.reload('eiaInvoiceList');
                    $('#offerId').val("");
                },
                min: function () {
                    $(".layui-layer-title").text("编辑开票信息");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        }
        else if(obj.event === 'eiaSubmit'){    //提交
            layer.confirm('确定要确认该信息吗?', {icon: 3}, function (index) {
                var loadingIndex = layer.load(1, {time: 10*1000,shade: 0.1});
                $.post("../eiaAccountExpect/eiaAccountExpectConfirm", {eiaAccountExpectId: data.id}, function (data) {
                    if (data.code == 0) {
                        layer.msg('提交成功！', {icon: 1, time: 1500,shade: 0.1}, function () {
                            $.post("../eiaAccountExpect/eiaExpectCissNum", {}, function (data) {
                                if (data.code == 0) {
                                    document.getElementById('czyj').innerText=data.data
                                }else{
                                    document.getElementById('czyj').innerText=0;
                                }
                            });
                            table.reload('eiaAccountExpectList');
                        });
                    } else {
                        if (data.msg) {
                            layer.msg("处理失败！<br/><div style='color:red;'>错误信息：" + data.msg + "</div>", {
                                icon: 2,
                                time: 1500
                            });
                        } else {
                            layer.msg('提交失败！', {icon: 2, time: 1500,shade: 0.1});
                        }
                    }
                    layer.close(loadingIndex);
                });
            },function (index) {
                //取消
            });

        }
        else if(obj.event === 'eiaBack'){   //预计信息驳回
            layer.confirm('确定要驳回该信息吗?', {icon: 3}, function (index) {
                var loadingIndex = layer.load(1, {time: 10*1000,shade: 0.1});
                $.post("../eiaAccountExpect/eiaAccountExpectBack", {eiaAccountExpectId: data.id}, function (data) {
                    if (data.code == 0) {
                        layer.msg('提交成功！', {icon: 1, time: 1500,shade: 0.1}, function () {
                            $.post("../eiaAccountExpect/eiaExpectCissNum", {}, function (data) {
                                if (data.code == 0) {
                                    document.getElementById('czyj').innerText=data.data
                                }else{
                                    document.getElementById('czyj').innerText=0;
                                }
                            });
                            table.reload('eiaAccountExpectList');
                        });
                    } else {
                        if (data.msg) {
                            layer.msg("处理失败！<br/><div style='color:red;'>错误信息：" + data.msg + "</div>", {
                                icon: 2,
                                time: 1500
                            });
                        } else {
                            layer.msg('提交失败！', {icon: 2, time: 1500,shade: 0.1});
                        }
                    }
                    layer.close(loadingIndex);
                });
            },function (index) {
                //取消
            });
        }
    });
    //查询、新增按钮（报价、合同）
    $('.larry-btn a.layui-btn').click(function () {
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });
    var active = {
        revSelect: function () {    //查询
            var contractName = $("#contractNameK").val();
            table.reload('eiaInvoiceList', {
                where: {
                    contractName: contractName
                }
            });
        },
        contractSelect: function () {    //合同查询
            var contractName = $("#contractNameC").val();
            table.reload('eiaContractList', {
                where: {
                    contractName: contractName
                }
            });
        },
        incomeSelect: function () {    //开票查询
            var contractName = $("#contractNameZ").val();
            table.reload('eiaInvoiceOutList', {
                where: {
                    contractName: contractName
                }
            });
        },
        expectSelect: function () {    //开票查询
            var contractName = $("#contractNameY").val();
            table.reload('eiaAccountExpectList', {
                where: {
                    contractName: contractName
                }
            });
        },
        contractAdd: function () {    //进账新增
            pageUrl = '/eia/eiaIncomeOut/eiaInvoiceIncomeCreate';
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

});