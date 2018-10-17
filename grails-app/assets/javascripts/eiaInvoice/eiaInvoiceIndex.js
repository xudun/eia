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
    $.post("../eiaInvoice/eiaInvoiceRevNum", {}, function (data) {
        if (data.code == 0) {
            document.getElementById('kpqr').innerText=data.data;
        }else{
            document.getElementById('kpqr').innerText=0;
        }
    });
    $.post("../eiaInvoice/eiaInvoiceAlreadyRevNum", {}, function (data) {
        if (data.code == 0) {
            document.getElementById('czyqr').innerText=data.data;
        }else{
            document.getElementById('czyqr').innerText=0;
        }
    });
    $.post("../eiaInvoice/eiaInvoiceOutNum", {}, function (data) {
        if (data.code == 0) {
            document.getElementById('czqr').innerText=data.data
        }else{
            document.getElementById('czqr').innerText=0;
        }
    });
    $.post("../eiaAccountExpect/eiaExpectNum", {}, function (data) {
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
            {field:'accountState',width:'15%', title: '当前状态',align: "center"},
            {field:'inputDept',width:'15%', title: '录入部门',align: "center"},
            {field:'inputUser',width:'15%', title: '录入人',align: "center"},
            {fixed: 'right', title: '操作',width:'15%',align: "center", toolbar: '#expectTool',align: "center"}
        ]],
         thAlreadyInvoice = [[
            {fixed: 'left', title: '序号',width:'6%',align: "center",templet: "#indexTable"},
            {field:'contractNo',width:'15%', title: '合同编号',align: "center"},
            {field:'contractName',width:'25%', title: '合同名称',align: "center"},
            {field:'accountState',width:'15%', title: '当前状态',align: "center"},
            {field:'billDate',width:'15%', title: '申请开票时间',align: "center"},
            {field:'billMoney',width:'15%', title: '开票金额(万元)',align: "center"},
            {field:'inputDept',width:'15%', title: '录入部门',align: "center"},
            {field:'inputUser',width:'15%', title: '录入人',align: "center"},
            {fixed: 'right', title: '操作',width:'15%',align: "center", toolbar: '#invoiceAlreadyTool',align: "center"}
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
            url = "/eia/eiaInvoice/eiaInvoiceFinQuery"
            cols = thInvoice;
        }else if(tabType==2){
            tableList = "eiaInvoiceOutList"
            url = "/eia/eiaIncomeOut/eiaInvoiceOutFinQuery"
            cols = thInvoiceOut;
        }else if(tabType==3){
            tableList = "eiaInvoiceAlreadyList"
            url = "/eia/eiaInvoice/eiaInvoiceAlreadyFinQuery"
            cols = thAlreadyInvoice;
        }else if(tabType==4){
            tableList = "eiaExpectList"
            url = "/eia/eiaAccountExpect/eiaExpectQuery"
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
            done: function (res, curr, count){
                //添加小计，总计
                var count_fields = ['contractMoney','billMoney','billMoney1','noteIncomeMoney','outstandingAmount','expertFee','monitorFee','otherFee'];
                //加入小计
                if(tabType==0){
                    tableAddSubTotalRow(this,res,count_fields);
                }
            }
        });

    };
    tabAction(0);

    //监听合同表格工具条
    table.on('tool(eiaInvoiceList)', function (obj) {
        var data = obj.data;
        $('#eiaInvoiceId').val(data.id)
        if (obj.event === 'eiaEdit') {    //编辑
            var pageUrl = '/eia/eiaInvoice/eiaInvoiceReveEdit?eiaInvoiceId='+data.id;
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
        else if(obj.event === 'eiaDel'){    //删除
            layer.confirm('确定要删除该开票吗?', {icon: 3}, function (index) {
                var loadingIndex = layer.load(1, {time: 10*1000,shade: 0.1});
                $.post("../eiaInvoice/eiaInvoiceDel", {eiaInvoiceId: data.id}, function (data) {
                    if (data.code == 0) {
                        layer.msg('删除成功！', {icon: 1, time: 1500,shade: 0.1}, function () {
                            $.post("../eiaInvoice/eiaInvoiceRevNum", {}, function (data) {
                                if (data.code == 0) {
                                    document.getElementById('kpqr').innerText=data.data;
                                }else{
                                    document.getElementById('kpqr').innerText=0;
                                }
                            });
                            $.post("../eiaInvoice/eiaInvoiceOutNum", {}, function (data) {
                                if (data.code == 0) {
                                    document.getElementById('czqr').innerText=data.data
                                }else{
                                    document.getElementById('kpqr').innerText=0;
                                }
                            });
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
            layer.confirm('确定要提交该开票信息吗?', {icon: 3}, function (index) {
                var loadingIndex = layer.load(1, {time: 10*1000,shade: 0.1});
                $.post("../eiaInvoice/eiaInvoiceSubmit", {eiaInvoiceId: data.id}, function (data) {
                    if (data.code == 0) {
                        layer.msg('提交成功！', {icon: 1, time: 1500,shade: 0.1}, function () {
                            $.post("../eiaInvoice/eiaInvoiceRevNum", {}, function (data) {
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
        else if(obj.event === 'eiaPrint'){   //打印开票
            var eiaInvoiceId = data.id;
            var pageUrl = '../eiaInvoice/eiaInvoiceRevePrint?eiaInvoiceId='+eiaInvoiceId;
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
                    body.find('#offerId').val(data.offerId);
                },
                end: function () {
                    $('#offerId').val("");
                },
                min: function () {
                    $(".layui-layer-title").text("查看开票");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        }
    });
    //监听开票表格工具条
    table.on('tool(eiaContractList)', function (obj) {
        var data = obj.data;
        var contractId = data.id
       if (obj.event === 'eiaEdit') {    //编辑
           $.ajax({
               type: 'POST',
               url: '/eia/eiaInvoice/isAuditEiaInvoice?pageType=1&contractId='+contractId,
               success: function (data) {
                   if(data.code==-2){
                       layer.msg('存在未被财务审核的开票信息,不能操作！', {icon:7});
                       return;
                   }else{
                       var pageUrl = '/eia/eiaInvoice/eiaInvoiceReveCreate?pageType=1&contractId='+contractId;
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
                               body.find('#contractId').val(contractId);
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
                    $(".layui-layer-title").text("查看信息");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        }
        else if(obj.event === 'eiaOut'){
           var contractId = data.id;
           $.ajax({
               type: 'POST',
               url: '/eia/eiaInvoice/isAuditEiaInvoiceOut?pageType=1&contractId='+contractId,
               success: function (data) {
                   if(data.code==-2){
                       layer.msg('存在未被财务审核的出账信息,不能操作！', {icon:7});
                       return;
                   }else{
                       var pageUrl = '/eia/eiaIncomeOut/eiaInvoiceOutCreate?contractId='+contractId;
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
                               body.find('#contractId').val(contractId);
                           },
                           end: function () {
                               table.reload('eiaInvoiceList');
                               $('#contractId').val("");
                           },
                           min: function () {
                               $(".layui-layer-title").text("编辑报价");
                           },
                           restore: function () {
                               $(".layui-layer-title").text(" ");
                           }
                       });
                   }
               }
           });
        }else if(obj.event === 'eiaExpect'){
           $("#contractId").val(data.id)
           var pageUrl = '/eia/eiaAccountExpect/eiaExpectCreate';
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
                   table.reload('eiaInvoiceList');
                   $('#contractId').val("");
                   $('#eiaAccountExpectId').val("");
               },
               min: function () {
                   $(".layui-layer-title").text("编辑开票信息");
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
        else if(obj.event === 'eiaDel'){    //删除
            layer.confirm('确定要删除该出账吗?', {icon: 3}, function (index) {
                var loadingIndex = layer.load(1, {time: 10*1000,shade: 0.1});
                $.post("../eiaIncomeOut/eiaIncomeOutDel", {eiaIncomeOutId: data.id}, function (data) {
                    if (data.code == 0) {
                        layer.msg('删除成功！', {icon: 1, time: 1500,shade: 0.1}, function () {
                            $.post("../eiaInvoice/eiaInvoiceOutNum", {}, function (data) {
                                if (data.code == 0) {
                                    document.getElementById('czqr').innerText=data.data
                                }else{
                                    document.getElementById('czqr').innerText=0;
                                }
                            });
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
            var eiaInvoiceId = data.id;
            var pageUrl = '../eiaIncomeOut/eiaInvoiceOutDetail?eiaIncomeOutId='+data.id;
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
                    $(".layui-layer-title").text("查看出账");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        }
        else if(obj.event === 'eiaSubmit'){    //提交
            layer.confirm('确定要提交该出账信息吗?', {icon: 3}, function (index) {
                var loadingIndex = layer.load(1, {time: 10*1000,shade: 0.1});
                $.post("../eiaIncomeOut/eiaIncomeOutSubmit", {eiaIncomeOutId: data.id}, function (data) {
                    if (data.code == 0) {
                        layer.msg('提交成功！', {icon: 1, time: 1500,shade: 0.1}, function () {
                            $.post("../eiaInvoice/eiaInvoiceOutNum", {}, function (data) {
                                if (data.code == 0) {
                                    document.getElementById('czqr').innerText=data.data;
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
        else if(obj.event === 'eiaPrint'){   //打印开票
            var eiaInvoiceId = data.id;
            var pageUrl = '../eiaInvoice/eiaInvoiceRevePrint?eiaInvoiceId='+eiaInvoiceId;
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
                    body.find('#offerId').val(data.offerId);
                },
                end: function () {
                    $('#offerId').val("");
                },
                min: function () {
                    $(".layui-layer-title").text("查看开票");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        }
    });
    //监听预计表格工具条
    table.on('tool(eiaExpectList)', function (obj) {
        var data = obj.data;
        $("#eiaAccountExpectId").val(data.id)
        if (obj.event === 'eiaEdit') {    //编辑
            var pageUrl = '/eia/eiaAccountExpect/eiaExpectCreate?eiaAccountExpectId='+data.id;
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
                    body.find('#eiaAccountExpectId').val(data.id);
                },
                end: function () {
                    table.reload('eiaExpectList');
                },
                min: function () {
                    $(".layui-layer-title").text("编辑预计信息");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        }
        else if(obj.event === 'eiaDel'){    //删除
            layer.confirm('确定要删除该财务预计信息吗?', {icon: 3}, function (index) {
                var loadingIndex = layer.load(1, {time: 10*1000,shade: 0.1});
                $.post("../eiaAccountExpect/eiaAccountExpectDel", {eiaAccountExpectId: data.id}, function (data) {
                    if (data.code == 0) {
                        layer.msg('删除成功！', {icon: 1, time: 1500,shade: 0.1}, function () {
                            $.post("../eiaAccountExpect/eiaExpectNum", {}, function (data) {
                                if (data.code == 0) {
                                    document.getElementById('czyj').innerText=data.data
                                }else{
                                    document.getElementById('czyj').innerText=0;
                                }
                            });
                            obj.del();
                            table.reload('eiaExpectList');
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
            var pageUrl = '../eiaAccountExpect/eiaExpectDetail?eiaAccountExpectId='+data.id;
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
                    $(".layui-layer-title").text("查看出账");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        }
        else if(obj.event === 'eiaSubmit'){    //提交
            layer.confirm('确定要提交该财务预计信息吗?', {icon: 3}, function (index) {
                var loadingIndex = layer.load(1, {time: 10*1000,shade: 0.1});
                $.post("../eiaAccountExpect/eiaExpectSubmit", {eiaAccountExpectId: data.id}, function (data) {
                    if (data.code == 0) {
                        layer.msg('提交成功！', {icon: 1, time: 1500,shade: 0.1}, function () {
                            $.post("../eiaAccountExpect/eiaExpectNum", {}, function (data) {
                                if (data.code == 0) {
                                    document.getElementById('czyj').innerText=data.data
                                }else{
                                    document.getElementById('czyj').innerText=0;
                                }
                            });
                            table.reload('eiaExpectList');
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
    //监听已确认表格工具条
    table.on('tool(eiaInvoiceAlreadyList)', function (obj) {
        var data = obj.data;
        $('#eiaInvoiceId').val(data.id)
        if (obj.event === 'eiaCheck') {
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
        else if(obj.event === 'eiaPrint'){   //打印开票
            var eiaInvoiceId = data.id;
            var pageUrl = '../eiaInvoice/eiaInvoiceRevePrint?eiaInvoiceId='+eiaInvoiceId;
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
                    body.find('#offerId').val(data.offerId);
                },
                end: function () {
                    $('#offerId').val("");
                },
                min: function () {
                    $(".layui-layer-title").text("查看开票");
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
        incomeSelect: function () {    //合同查询
            var contractName = $("#contractNameZ").val();
            table.reload('eiaInvoiceOutList', {
                where: {
                    contractName: contractName
                }
            });
        },
        revAlreadySelect: function () {    //合同查询
            var contractName = $("#contractNameA").val();
            table.reload('eiaInvoiceAlreadyList', {
                where: {
                    contractName: contractName
                }
            });
        },
        eiaExpectSelect: function () {    //合同查询
            var contractName = $("#contractNameY").val();
            table.reload('eiaExpectList', {
                where: {
                    contractName: contractName
                }
            });
        },

        contractAdd: function () {    //合同新增
            pageUrl = '/eia/eiaInvoice/invoiceRevenueCreate';
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