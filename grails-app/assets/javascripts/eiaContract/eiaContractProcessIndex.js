layui.use(['jquery', 'layer', 'table', 'element'], function () {
    var $ = layui.jquery,
        layer = layui.layer,
        element = layui.element,
        table = layui.table;

    //项目待办列表
    var waitAffairsRender = function () {
        //渲染表格
        table.render({
            id: 'waitAffairsList',
            elem: '#waitAffairsList',
            url: '/eia/eiaWorkFlowBusi/getEiaWorkFlowBusiDataList',
            toolbar: '#tableTopTmp',
            defaultToolbar: ['filter', 'print', 'exports'],
            cols: [[
                {fixed: 'left', title: '序号', width: '5%', align: "center", templet: "#indexTable"},
                {field: 'workFlowTitle', width: '10%', title: '审批标题', align: "center"},
                {field: 'workFlowName', width: '20%', title: '流程名称', align: "center"},
                {field: 'nodesName', width: '20%', title: '当前节点名称', align: "center"},
                {field: 'inputUser', width: '15%', title: '创建人', align: "center"},
                {field: 'dateCreated', width: '20%', title: '创建日期', align: "center"},
                {fixed: 'right', title: '操作', width: '10%', align: "center", toolbar: '#waitAffairsListTool', align: "center"}
            ]],
            page: true,
            even: true,
            limit: 10,
            where: {tableName: "EiaContract"},
            done: function (res, curr, count) {
                //如果是异步请求数据方式，res即为你接口返回的信息。
                //如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度
                $("#waitAffairsListSize").html(res.count ? res.count : 0);
                //得到当前页码
                console.log(curr);

                //得到数据总量
                console.log(count);
            }
        });

        //监听工具条
        table.on('tool(waitAffairsList)', function (obj) {
            var data = obj.data;
            if (obj.event === 'eiaCheck') {    //查看
                pageUrl = '/eia/eiaWorkFlow/eiaWorkFlowTrans';
                $('#tableNameId').val(data.tableNameId);
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
                        $('#tableNameId').val();
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
    };


    //合同查询列表
    var eiaContractRender = function () {

        //渲染表格
        table.render({
            id: "eiaContractList",
            elem: '#eiaContractList',
            url: '/eia/eiaContract/getEiaContractDataList',
            toolbar: '#tableTopTmp2',
            defaultToolbar: ['filter', 'print', 'exports'],
            cols: [[
                {fixed: 'left', title: '序号', width: '6%', align: "center", templet: "#indexTable"},
                {field: 'contractNo', width: '15%', title: '合同编号', align: "center"},
                {field: 'contractName', width: '25%', title: '合同名称', align: "center"},
                {field: 'contractType', width: '15%', title: '合同类型', align: "center"},
                {field: 'contractMoney', width: '15%', title: '合同金额（万元）', align: "center"},
                {field: 'contractDate', width: '15%', title: '合同时间', align: "center"},
                {field: 'taskNo', width: '15%', title: '任务编号', align: "center"},
                {field: 'taskName', width: '20%', title: '任务名称', align: "center"},
                {field: 'inputUser', width: '10%', title: '录入人', align: "center"},
                {fixed: 'right', title: '操作', width: '15%', align: "center", toolbar: '#contractTool', align: "center"}
            ]],
            page: true,
            even: true,
            limit: 10
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
                    var loadingIndex = layer.load(1, {time: 10 * 1000, shade: 0.1});
                    $.post("../eiaContract/eiaContractDel", {eiaContractId: data.id}, function (data) {
                        if (data.code == 0) {
                            layer.msg('删除成功！', {icon: 1, time: 1500, shade: 0.1}, function () {
                                obj.del();
                            });
                        } else {
                            if (data.msg) {
                                layer.msg("处理失败！<br/><div style='color:red;'>错误信息：" + data.msg + "</div>", {
                                    icon: 2,
                                    time: 1500
                                });
                            } else {
                                layer.msg('删除失败！', {icon: 2, time: 1500, shade: 0.1});
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
                // var contractId = data.id;
                // $.post("../exportContract/checkProductType", {contractId: contractId},function (result) {
                //     if (result.data == "noStaff") {
                //         layer.msg('员工资料需补充，请联系人力部门更新资料！', {icon: 2, time: 1500});
                //     } else if (result.data){
                //         layer.msg('正在导出...', {icon: 16, shade: 0.01});
                //         window.location.href = "../exportContract/downloadContract?contractId=" + contractId;
                //     } else {
                //         layer.msg('尚无对应合同模板，不能导出！', {icon: 2, time: 1500});
                //     }
                // });
                var pageUrl = '/eia/eiaContract/eiaContractDownload';
                var index = layer.open({
                    title: '合同受托方',
                    type: 2,
                    skin: 'demo-class contract-download',
                    content: pageUrl,
                    success: function (layero, index) {
                        var body = layer.getChildFrame('body', index);
                        body.find('#contractId').val(data.id);
                    },
                    end: function () {
                        // $('#offerId').val("");
                    },
                    min: function () {
                        $(".layui-layer-title").text("下载合同");
                    },
                    restore: function () {
                        $(".layui-layer-title").text(" ");
                    }
                });
            } else if (obj.event === 'contractSub') {
                var eiaContractId = data.id;
                layer.confirm('确定要提交合同审核吗?', {icon: 3}, function (index) {
                    $.post("../eiaContract/eiaContractSub", {eiaContractId: eiaContractId}, function (result) {
                        if (result.code == 0) {
                            table.reload('eiaContractList');
                            layer.msg("合同审核提交成功", {icon: 1, time: 1500})
                        } else {
                            layer.msg(result.msg, {icon: 1, time: 1500})
                        }
                    });
                }, function (index) {
                });
            } else if (obj.event === 'contractFlow') {
                layer.msg(res.msg, {time: 1500, shade: 0.1});
                ajaxBox("/eia/eiaWorkFlowBusi/checkWorkFlow", {tableNameId: data.id, tableName: "EiaContract"}, function (res) {
                    if (res.code == 0) {
                        //流程
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
                    } else {
                        layer.msg(res.msg, {time: 1500, shade: 0.1});
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
            getSelect: function () {    //查询
                var contractName = $("#contractName").val();
                table.reload('waitAffairsList', {
                    where: {
                        tableName: "EiaContract",
                        contractName: contractName
                    }
                });
                table.reload('eiaContractList', {
                    where: {
                        contractName: contractName
                    }
                });
            }
        }

        table.on('toolbar(waitAffairsList)', function (obj) {
            switch (obj.event) {
                case 'getSelect':
                    var contractName = $("#contractName").val();
                    table.reload('waitAffairsList', {
                        where: {
                            tableName: "EiaContract",
                            contractName: contractName
                        }
                    });
            }
        });

        table.on('toolbar(eiaContractList)', function (obj) {
            switch (obj.event) {
                case 'getSelect':
                    var contractName = $("#contractName2").val();
                    table.reload('eiaContractList', {
                        where: {
                            contractName: contractName
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
                    $.post("../eiaContract/eiaContractDel", {eiaContractId: data.id}, function (data) {
                        if (data.code == 0) {
                            layer.msg('删除成功！', {icon: 1, time: 1500}, function () {
                                obj.del();
                            });
                        } else {
                            if (data.msg) {
                                layer.msg("处理失败！<br/><div style='color:red;'>错误信息：" + data.msg + "</div>", {
                                    icon: 2,
                                    time: 1500
                                });
                            } else {
                                layer.msg('删除失败！', {icon: 2, time: 1500});
                            }
                        }
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
                // var contractId = data.id;
                // $.post("../exportContract/checkProductType", {contractId: contractId},function (result) {
                //     if (result.data == "noStaff") {
                //         layer.msg('员工资料需补充，请联系人力部门更新资料！', {icon: 2, time: 1500});
                //     } else if (result.data){
                //         layer.msg('正在导出...', {icon: 16, shade: 0.01});
                //         window.location.href = "../exportContract/downloadContract?contractId=" + contractId;
                //     } else {
                //         layer.msg('尚无对应合同模板，不能导出！', {icon: 2, time: 1500});
                //     }
                // });
                var pageUrl = '/eia/eiaContract/eiaContractDownload';
                var index = layer.open({
                    title: '合同受托方',
                    type: 2,
                    skin: 'demo-class contract-download',
                    content: pageUrl,
                    success: function (layero, index) {
                        var body = layer.getChildFrame('body', index);
                        body.find('#contractId').val(data.id);
                    },
                    end: function () {
                        // $('#offerId').val("");
                    },
                    min: function () {
                        $(".layui-layer-title").text("下载合同");
                    },
                    restore: function () {
                        $(".layui-layer-title").text(" ");
                    }
                });
            } else if (obj.event === 'contractSub') {
                var eiaContractId = data.id;
                layer.confirm('确定要提交合同审核吗?', {icon: 3}, function (index) {
                    $.post("../eiaContract/eiaContractSub", {eiaContractId: eiaContractId}, function (result) {
                        if (result.code == 0) {
                            table.reload('eiaContractList');
                            layer.msg("合同审核提交成功", {icon: 1, time: 1500})
                        } else {
                            table.reload('eiaContractList');
                            layer.msg(result.msg, {icon: 1, time: 1500})
                        }
                    });
                }, function (index) {

                });

            } else if (obj.event === 'contractFlow') {    //流程
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
    };


    // Tab切换
    element.on('tab(proProcessTab)', function (data) {
        var cur_index = data.index;
        switch (cur_index) {
            case 0:
                waitAffairsRender();
                break;
            case 1:
                eiaContractRender();
                break;
        }
    });
    waitAffairsRender();

});