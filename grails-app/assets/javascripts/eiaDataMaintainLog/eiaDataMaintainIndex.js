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
                {field: 'contractDate', width: '15%', title: '合同时间', align: "center"},
                {field: 'ifShow', width: '15%', title: '是否显示', align: "center"},
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
        ]],
            thTask = [[
                {fixed: 'left', type: 'numbers', title: '序号', width: '6%', align: "center"},
                {field:'taskNo',width:'15%', title: '任务单号',align: "center"},
                {field:'taskName',width:'20%', title: '任务名称',align: "center"},
                {field:'busiType',width:'20%', title: '业务类型',align: "center"},
                {field:'inputDept',width:'15%', title: '录入部门',align: "center"},
                {field:'inputUser',width:'10%', title: '录入人',align: "center"},
                {field:'taskState',width:'10%', title: '任务状态',align: "center"},
                {field:'taskRole',width:'15%', title: '任务角色',align: "center"},
                {fixed: 'right', title: '操作',width:'15%',align: "center", toolbar: '#taskTool',align: "center"}
            ]],
            thProject = [[
                {fixed: 'left', title: '序号', width: '6%', align: "center", templet: "#indexTable"},
                {field: 'projectNo', width: '25%', title: '项目编号', align: "center"},
                {field: 'projectName', width: '35%', title: '项目名称', align: "center"},
                {field: 'fileTypeChild', width: '15%', title: '文件类型', align: "center"},
                {field: 'projectMoney', width: '15%', title: '项目价格', align: "center"},
                {field: 'inputDept', width: '15%', title: '录入部门', align: "center"},
                {field: 'inputUser', width: '15%', title: '录入人', align: "center"},
                {fixed: 'right', title: '操作', width: '20%', align: "center", toolbar: '#projectTool', align: "center"}
            ]],
            thProjectPlan = [[
                {fixed: 'left', title: '序号', width: '6%', align: "center", templet: "#indexTable"},
                {field: 'projectNo', width: '15%', title: '项目编号', align: "center"},
                {field: 'projectName', width: '25%', title: '项目名称', align: "center"},
                {field: 'fileTypeChild', width: '15%', title: '文件类型', align: "center"},
                {field: 'projectMoney', width: '15%', title: '金额(万元)', align: "center"},
                {field: 'inputDept', width: '15%', title: '录入部门', align: "center"},
                {field: 'inputUser', width: '10%', title: '录入人', align: "center"},
                {fixed: 'right', title: '操作', width: '15%', align: "center", toolbar: '#projectPlanTool', align: "center"}
            ]],
            thClient = [[
                {fixed: 'left', title: '序号',width:'7%',align: "center",templet: "#indexTable"},
                {field:'clientName',width:'30%', title: '客户名称',align: "center"},
                {field:'clientAddress',width:'20%', title: '客户地址',align: "center"},
                {field:'clientPostCode',width:'10%', title: '邮政编码',align: "center"},
                {field:'clientCorporate',width:'10%', title: '法人代表',align: "center"},
                {field:'clientFax',width:'10%', title: '传真',align: "center"},
                {field:'inputDept',width:'15%', title: '录入部门',align: "center"},
                {field:'inputUser',width:'10%', title: '录入人',align: "center"},
                {fixed: 'right', title: '操作',width:'15%',align: "center", toolbar: '#clientTool',align: "center"}
            ]],
            thEiaCert= [[
            {fixed: 'left', title: '序号', width: "7%", align: "center", templet: "#indexTable"},
            {field: 'projectNo', Width: '15%', title: '项目编号', align: "center"},
            {field: 'projectName', Width: '15%', title: '项目名称', align: "center"},
            {field: 'inputDept', Width: '27', title: '申请部门', align: "center"},
            {field: 'inputUser', Width: '13%', title: '申请人', align: "center"},
            {fixed: 'right', title: '操作', Width: "5%", align: "center", toolbar: '#eiaCertListTool', align: "center"}
            ]];
        var tableList;
        if(tabType==0){
            tableList = "eiaTaskList";
            url = "/eia/eiaTask/getEiaTaskDataList";
            cols = thTask
            toolbar='#tableTopTmp';

        } else if(tabType==1){
            tableList = "eiaContractList";
            url = "/eia/eiaContract/getEiaContractDataList?ifShow=1";
            cols = thContract
            toolbar='#tableTopTmp2';

        }else if(tabType==2){
            tableList = "eiaOfferList";
            url = "/eia/eiaContract/getEiaOfferDataList";
            cols = thOffer
            toolbar='#tableTopTmp3';

        }else if(tabType==3){
            tableList = "eiaProjectList";
            url = "/eia/eiaProject/getEiaProjectDataList"
            cols = thProject
            toolbar='#tableTopTmp4';

        }else if(tabType==4){
            tableList = "eiaProjectPlanList"
            url = "/eia/eiaProject/getEiaProjectDataList"
            cols = thProjectPlan
            toolbar='#tableTopTmp5';

        }else if(tabType==5){
            tableList = "eiaCertList"
            url = "/eia/eiaCert/eiaCertQuery"
            cols = thEiaCert
            toolbar='#tableTopTmp6';

        }else if(tabType==6){
            tableList = "eiaClientList"
            url = "/eia/eiaClient/getEiaClientDataList"
            cols = thClient;
            toolbar='#tableTopTmp7';
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
            toolbar:toolbar,
            defaultToolbar:['filter', 'print', 'exports'],
            limit: 10,
        });

    };
    tabAction(0);

    //监听报价表格工具条
    table.on('tool(eiaOfferList)', function (obj) {
        var data = obj.data;
        var maintainType = "EiaLabOffer";
        $("#maintainType").val(maintainType)
        $("#offerId").val(data.id);
        if (obj.event === 'eiaEdit') {    //编辑
            var pageUrl = '/eia/eiaDataMaintainLog/eiaDataMaintainSubCreate';
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
        }else if(obj.event === 'eiaDel'){
            layer.confirm('确定要删除报价吗?', {icon: 3}, function (index) {
                var loadingIndex = layer.load(1, {time: 10*1000,shade: 0.1});
                $.post("../eiaDataMaintainLog/maintainDel", {offerId: data.id}, function (data) {
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
    });
    //监听资质表格工具条
    table.on('tool(eiaCertList)', function (obj) {
        var data = obj.data;
        var maintainType = "EiaCert";
        $("#maintainType").val(maintainType)
        $("#eiaCertId").val(data.id);
        if (obj.event === 'eiaEdit') {    //编辑
            var pageUrl = '/eia/eiaDataMaintainLog/eiaDataMaintainSubCreate';
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
                    body.find('#eiaCertId').val(data.id);
                },
                end: function () {
                    // table.reload('eiaOfferList');
                    $('#eiaCertId').val("");
                },
                min: function () {
                    $(".layui-layer-title").text("编辑报价");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        }else if(obj.event === 'eiaDel'){
            layer.confirm('确定要删除资质吗?', {icon: 3}, function (index) {
                var loadingIndex = layer.load(1, {time: 10*1000,shade: 0.1});
                $.post("../eiaDataMaintainLog/maintainDel", {eiaCertId: data.id}, function (data) {
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
    });
    //监听合同表格工具条
    table.on('tool(eiaContractList)', function (obj) {
        var data = obj.data;
        $("#contractId").val(data.id);
        var maintainType = "EiaContract";
        $("#maintainType").val(maintainType)
        if (obj.event === 'eiaEdit') {    //编辑
            var pageUrl = '/eia/eiaDataMaintainLog/eiaDataMaintainSubCreate';
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
        }else if(obj.event === 'eiaDel'){
            layer.confirm('确定要删除合同吗?', {icon: 3}, function (index) {
                var loadingIndex = layer.load(1, {time: 10*1000,shade: 0.1});
                $.post("../eiaDataMaintainLog/maintainDel", {contractId: data.id}, function (data) {
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

        }else if(obj.event === 'eiaShow'){
            layer.confirm('确定该操作吗?', {icon: 3}, function (index) {
                var loadingIndex = layer.load(1, {time: 10*1000,shade: 0.1});
                $.post("../eiaDataMaintainLog/maintainShow", {contractId: data.id}, function (data) {
                    if (data.code == 0) {
                        layer.msg('操作成功！', {icon: 1, time: 1500,shade: 0.1}, function () {
                            table.reload('eiaContractList');
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
    });
    //监听任务表格工具条
    table.on('tool(eiaTaskList)', function (obj) {
        var data = obj.data;
        $("#taskId").val(data.id);
        var maintainType = "EiaTask";
        $("#maintainType").val(maintainType)
        if (obj.event === 'eiaEdit') {    //编辑
            var pageUrl = '/eia/eiaDataMaintainLog/eiaDataMaintainSubCreate';
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
                    body.find('#taskId').val(data.id);
                },
                end: function () {
                    // table.reload('eiaOfferList');
                    $('#taskId').val("");
                },
                min: function () {
                    $(".layui-layer-title").text("编辑合同");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        }else if(obj.event === 'eiaDel'){
            layer.confirm('确定要删除该任务下相关的所有信息吗?', {icon: 3}, function (index) {
                var loadingIndex = layer.load(1, {time: 10*1000,shade: 0.1});
                $.post("../eiaDataMaintainLog/maintainDel", {eiaTaskId: data.id}, function (data) {
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
    });
    //监听项目表格工具条
    table.on('tool(eiaProjectList)', function (obj) {
        var data = obj.data;
        var maintainType = "EiaProject";
        $("#maintainType").val(maintainType)
        $("#projectId").val(data.id);
        if (obj.event === 'eiaEdit') {    //编辑
            var pageUrl = '/eia/eiaDataMaintainLog/eiaDataMaintainSubCreate';
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
                    body.find('#projectId').val(data.id);
                },
                end: function () {
                    // table.reload('eiaOfferList');
                    $('#projectId').val("");
                },
                min: function () {
                    $(".layui-layer-title").text("编辑合同");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        }else if(obj.event === 'eiaDel'){
            layer.confirm('确定要删除该项目吗?', {icon: 3}, function (index) {
                var loadingIndex = layer.load(1, {time: 10*1000,shade: 0.1});
                $.post("../eiaDataMaintainLog/maintainDel", {projectId: data.id}, function (data) {
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

        }else if(obj.event === 'eiaTask'){
            var pageUrl = '../eiaDataMaintainLog/eiaDataMaintainTask';
            var index = layer.open({
                title:' ',
                type: 2,
                shade: false,
                maxmin: true,
                skin: 'larry-green',
                area: ['100%', '100%'],
                content: pageUrl,
                end:function(){
                },
                min:function(){
                    $(".layui-layer-title").text("选择任务");
                },
                restore:function(){
                    $(".layui-layer-title").text(" ");
                }
            });

        }
    });
    //监听工作方案表格工具条
    table.on('tool(eiaProjectPlanList)', function (obj) {
        var data = obj.data;
        $("#projectPlanId").val(data.id);
        var maintainType = "EiaProjectPlan"
        $("#maintainType").val(maintainType)
        if (obj.event === 'eiaEdit') {    //编辑
            var pageUrl = '/eia/eiaDataMaintainLog/eiaDataMaintainSubCreate';
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
                    body.find('#projectPlanId').val(data.id);
                },
                end: function () {
                    // table.reload('eiaOfferList');
                    $('#projectPlanId').val("");
                },
                min: function () {
                    $(".layui-layer-title").text("编辑合同");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        }else if(obj.event === 'eiaDel'){
            layer.confirm('确定要删除工作方案吗?', {icon: 3}, function (index) {
                var loadingIndex = layer.load(1, {time: 10*1000,shade: 0.1});
                $.post("../eiaDataMaintainLog/maintainDel", {projectPlanId: data.id}, function (data) {
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
    });
    //监听企业表格工具条
    table.on('tool(eiaClientList)', function (obj) {
        var data = obj.data;
        $("#clientId").val(data.id);
        var maintainType = "EiaClient"
        $("#maintainType").val(maintainType)
        if (obj.event === 'eiaEdit') {    //编辑
            var pageUrl = '/eia/eiaDataMaintainLog/eiaDataMaintainSubCreate';
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
                    body.find('#clientId').val(data.id);
                },
                end: function () {
                    // table.reload('eiaOfferList');
                    $('#clientId').val("");
                },
                min: function () {
                    $(".layui-layer-title").text("编辑合同");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        }else if(obj.event === 'eiaDel'){
            layer.confirm('确定要删除企业信息吗?', {icon: 3}, function (index) {
                var loadingIndex = layer.load(1, {time: 10*1000,shade: 0.1});
                $.post("../eiaDataMaintainLog/maintainDel", {clientId: data.id}, function (data) {
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
        offerAdd: function () {    //报价数据更新
            var maintainType = "EiaLabOffer"
            pageUrl = '../eiaDataMaintainLog/eiaDataMaintainSubCreate';
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
                    body.find('#maintainType').val(maintainType);
                },
                end: function () {
                    $('#maintainType').val("");
                },
                min: function () {
                    $(".layui-layer-title").text("新增报价");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        },
        taskAdd: function () {    //任务数据维护
            var maintainType = "EiaTask"
            pageUrl = '../eiaDataMaintainLog/eiaDataMaintainSubCreate';
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
                    body.find('#maintainType').val(maintainType);
                },
                end: function () {
                    $('#maintainType').val("");
                },
                min: function () {
                    $(".layui-layer-title").text("新增报价");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        },
        taskSelect: function () {    //合同查询
            var taskName = $("#taskName").val();
            table.reload('eiaTaskList', {
                where: {
                    taskName: taskName
                }
            });
        },
        projectAdd: function () {    //项目数据维护
            var maintainType = "EiaProject"
            pageUrl = '../eiaDataMaintainLog/eiaDataMaintainSubCreate';
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
                    body.find('#maintainType').val(maintainType);
                },
                end: function () {
                    $('#maintainType').val("");
                },
                min: function () {
                    $(".layui-layer-title").text("新增报价");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        },
        projectSelect: function () {    //合同查询
            var projectName = $("#projectName").val();
            table.reload('eiaProjectList', {
                where: {
                    projectName: projectName
                }
            });
        },
        projectPlanAdd: function () {    //工作方案数据维护
            var maintainType = "EiaProjectPlan"
            pageUrl = '../eiaDataMaintainLog/eiaDataMaintainSubCreate';
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
                    body.find('#maintainType').val(maintainType);
                },
                end: function () {
                    $('#maintainType').val("");
                },
                min: function () {
                    $(".layui-layer-title").text("新增报价");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        },
        projectPlanSelect: function () {    //工作方案查询
            var projectName = $("#projectPlanName").val();
            table.reload('eiaProjectPlanList', {
                where: {
                    projectName: projectName
                }
            });
        },
        clientSelect: function () {    //企业查询查询
            var clientName = $("#clientName").val();
            table.reload('eiaClientList', {
                where: {
                    clientName: clientName
                }
            });
        },
        certSelect: function () {    //资质查询查询
            var projectName = $("#projectNameZ").val();
            table.reload('eiaCertList', {
                where: {
                    projectName: projectName
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
        contractAllAdd: function () {    //合同新增
            pageUrl = '../eiaDataMaintainLog/eiaDataMaintainCreate';
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
                    $(".layui-layer-title").text("全部数据维护");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        },
        contractAdd: function () {    //合同数据维护
            var maintainType = "EiaContract"
            pageUrl = '../eiaDataMaintainLog/eiaDataMaintainSubCreate';
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
                    body.find('#maintainType').val(maintainType);
                },
                end: function () {
                    $('#maintainType').val("");
                },
                min: function () {
                    $(".layui-layer-title").text("合同数据维护");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        },
        clientAdd: function () {    //企业数据维护
            var maintainType = "EiaClient"
            pageUrl = '../eiaDataMaintainLog/eiaDataMaintainSubCreate';
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
                    body.find('#maintainType').val(maintainType);
                },
                end: function () {
                    $('#maintainType').val("");
                },
                min: function () {
                    $(".layui-layer-title").text("合同数据维护");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        },
        certAdd: function () {    //资质数据维护
            var maintainType = "EiaCert"
            pageUrl = '../eiaDataMaintainLog/eiaDataMaintainSubCreate';
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
                    body.find('#maintainType').val(maintainType);
                },
                end: function () {
                    $('#maintainType').val("");
                },
                min: function () {
                    $(".layui-layer-title").text("合同数据维护");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        }
    }

    //监听头部工具栏事件
    //任务监听事件
    table.on('toolbar(eiaTaskList)', function(obj){
        switch(obj.event){
            case 'taskSelect':
                var taskName = $("#taskName").val();
                table.reload('eiaTaskList', {
                    where: {
                        taskName: taskName
                    }
                });
                break;
            case 'taskAdd':
                var maintainType = "EiaTask"
                pageUrl = '../eiaDataMaintainLog/eiaDataMaintainSubCreate';
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
                        body.find('#maintainType').val(maintainType);
                    },
                    end: function () {
                        $('#maintainType').val("");
                    },
                    min: function () {
                        $(".layui-layer-title").text("新增报价");
                    },
                    restore: function () {
                        $(".layui-layer-title").text(" ");
                    }
                });
                break;
            case 'contractAllAdd':
                pageUrl = '../eiaDataMaintainLog/eiaDataMaintainCreate';
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
                        $(".layui-layer-title").text("全部数据维护");
                    },
                    restore: function () {
                        $(".layui-layer-title").text(" ");
                    }
                });
                break;
        }
    })
    //合同监听事件
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
                var maintainType = "EiaContract"
                pageUrl = '../eiaDataMaintainLog/eiaDataMaintainSubCreate';
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
                        body.find('#maintainType').val(maintainType);
                    },
                    end: function () {
                        $('#maintainType').val("");
                    },
                    min: function () {
                        $(".layui-layer-title").text("合同数据维护");
                    },
                    restore: function () {
                        $(".layui-layer-title").text(" ");
                    }
                });
        }
    })
    //报价监听事件
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
                var maintainType = "EiaLabOffer"
                pageUrl = '../eiaDataMaintainLog/eiaDataMaintainSubCreate';
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
                        body.find('#maintainType').val(maintainType);
                    },
                    end: function () {
                        $('#maintainType').val("");
                    },
                    min: function () {
                        $(".layui-layer-title").text("新增报价");
                    },
                    restore: function () {
                        $(".layui-layer-title").text(" ");
                    }
                });
                break;
        }
    })

    table.on('toolbar(eiaProjectList)', function(obj){
        switch(obj.event){
            case 'projectSelect':
                var projectName = $("#projectName").val();
                table.reload('eiaProjectList', {
                    where: {
                        projectName: projectName
                    }
                });
                break;
            case 'projectAdd':
                var maintainType = "EiaProject"
                pageUrl = '../eiaDataMaintainLog/eiaDataMaintainSubCreate';
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
                        body.find('#maintainType').val(maintainType);
                    },
                    end: function () {
                        $('#maintainType').val("");
                    },
                    min: function () {
                        $(".layui-layer-title").text("新增报价");
                    },
                    restore: function () {
                        $(".layui-layer-title").text(" ");
                    }
                });
                break;
        }
    })

    table.on('toolbar(eiaProjectPlanList)', function(obj){
        switch(obj.event){
            case 'projectPlanSelect':
                var projectName = $("#projectPlanName").val();
                table.reload('eiaProjectPlanList', {
                    where: {
                        projectName: projectName
                    }
                });
                break;
            case 'projectPlanAdd':
                var maintainType = "EiaProjectPlan"
                pageUrl = '../eiaDataMaintainLog/eiaDataMaintainSubCreate';
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
                        body.find('#maintainType').val(maintainType);
                    },
                    end: function () {
                        $('#maintainType').val("");
                    },
                    min: function () {
                        $(".layui-layer-title").text("新增报价");
                    },
                    restore: function () {
                        $(".layui-layer-title").text(" ");
                    }
                });
                break;
        }
    })

    table.on('toolbar(eiaCertList)', function(obj){
        switch(obj.event){
            case 'certSelect':
                var projectName = $("#projectNameZ").val();
                table.reload('eiaCertList', {
                    where: {
                        projectName: projectName
                    }
                });
                break;
            case 'certAdd':
                var maintainType = "EiaCert"
                pageUrl = '../eiaDataMaintainLog/eiaDataMaintainSubCreate';
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
                        body.find('#maintainType').val(maintainType);
                    },
                    end: function () {
                        $('#maintainType').val("");
                    },
                    min: function () {
                        $(".layui-layer-title").text("合同数据维护");
                    },
                    restore: function () {
                        $(".layui-layer-title").text(" ");
                    }
                });
                break;
        }
    })

    table.on('toolbar(eiaClientList)', function(obj){
        switch(obj.event){
            case 'clientSelect':
                var clientName = $("#clientName").val();
                table.reload('eiaClientList', {
                    where: {
                        clientName: clientName
                    }
                });
                break;
            case 'clientAdd':
                var maintainType = "EiaClient"
                pageUrl = '../eiaDataMaintainLog/eiaDataMaintainSubCreate';
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
                        body.find('#maintainType').val(maintainType);
                    },
                    end: function () {
                        $('#maintainType').val("");
                    },
                    min: function () {
                        $(".layui-layer-title").text("合同数据维护");
                    },
                    restore: function () {
                        $(".layui-layer-title").text(" ");
                    }
                });
                break;
        }
    })
});