layui.use(['jquery', 'layer', 'table', 'element'], function(){
    var $ = layui.jquery,
        layer = layui.layer,
        element = layui.element,
        table = layui.table;

    //项目待办列表
    var waitAffairsRender = function(){
        //渲染表格
        table.render({
            id: 'waitAffairsList',
            elem: '#waitAffairsList',
            url:'/eia/eiaWorkFlowBusi/getEiaWorkFlowBusiDataList',
            toolbar: '#waitTmp',
            defaultToolbar:['filter', 'print', 'exports'],
            cols: [[
                {fixed: '', title: '序号',width:'5%',align: "center",templet: "#indexTable"},
                {field: 'workFlowTitle', width: '15%', title: '审批标题', align: "center"},
                {field:'workFlowName',width:'20%', title: '流程名称',align: "center"},
                {field:'nodesName',width:'20%', title: '当前节点名称',align: "center"},
                {field:'inputUser',width:'15%', title: '创建人',align: "center"},
                {field:'dateCreated',width:'15%', title: '创建日期',align: "center"},
                {fixed: '', title: '操作',width:'10%',align: "center", toolbar: '#waitAffairsListTool',align: "center"}
            ]],
            page: true,
            even: true,
            limit: 10,
            where:{tableName:"EiaProject"}
        });

        //获取待办事件数量
        ajaxBox("/eia/eiaWorkFlowBusi/getEiaWorkFlowBusiCount",{authType:"USER_CODE"},function(data){
            $("#waitAffairsListSize").html(data.count)
        });

        //监听工具条
        table.on('tool(waitAffairsList)', function (obj) {
            var data = obj.data;
            if(obj.event === 'eiaCheck'){    //查看
                $('#eiaProjectId').val(data.tableNameId);
                $('#tableNameId').val(data.tableNameId);
                $('#tableName').val("EiaProject");//项目名称
                pageUrl = '/eia/eiaWorkFlow/eiaWorkFlowProject';
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
                        body.find('#eiaProjectId').val(data.tableNameId);
                    },
                    end: function () {
                        $('#tableNameId').val("");
                        $('#tableName').val("");
                        $('#eiaProjectId').val();
                    },
                    min: function () {
                        $(".layui-layer-title").text("项目流程");
                    },
                    restore: function () {
                        $(".layui-layer-title").text(" ");
                    }
                });
            }
        });
    };
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
        }else if (obj.event === 'contractFlow') {    //流程
            pageUrl = '/eia/eiaWorkFlow/eiaWorkFlowProject';
            $('#tableNameId').val(data.id);
            $('#tableName').val("EiaProject")//项目名称
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

    //项目查询列表
    var eiaProjectRender = function(){
        //渲染表格
        table.render({
            id: 'eiaProjectList',
            elem: '#eiaProjectList',
            url: '/eia/eiaProject/getEiaProjectDataList',
            toolbar: '#projectTmp',
            defaultToolbar:['filter', 'print', 'exports'],
            cols: [[
                {fixed: 'left', title: '序号', width: '6%', align: "center", templet: "#indexTable" },
                {field: 'projectNo', width: '25%', title: '项目编号', align: "center" },
                {field: 'projectName', width: '35%', title: '项目名称', align: "center"},
                {field: 'projectMoney', width: '15%', title: '项目价格', align: "center"},
                {fixed: 'right', title: '操作', width: '20%', align: "center", toolbar: '#eiaProjectListTool', align: "center"}
            ]],
            page: true,
            even: true,
            limit: 10
        });

        //监听工具条
        table.on('tool(eiaProjectList)', function (obj) {
            var data = obj.data;
            var eiaProjectId = data.id;
            var gisProjectId = data.gisProjectId;
            $('#eiaProjectId').val(eiaProjectId);
            $('#eiaTaskId').val(data.eiaTaskId);
            $('#gisProjectId').val(data.gisProjectId);
            if (obj.event === 'projectDel') {    //删除
                layer.confirm('确定要删除该项目吗?', {icon: 3}, function (index) {
                    var loadingIndex = layer.load(1, {time: 10*1000,shade: 0.1});
                    $.post("../eiaProject/eiaProjectDel", {eiaProjectId: data.id}, function (data) {
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
            else if (obj.event === 'projectDetail') {    //查看
                pageUrl = '/eia/eiaProject/eiaProjectDetail?eiaProjectId=' + eiaProjectId;
                $("#eiaProjectId").val(data.id);
                $("#tableNameId").val(data.id);
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
                        body.find('').val(data.eiaClientId);
                    },
                    end: function () {
                        table.reload("eiaProjectList");
                        $('#eiaClientId').val("");
                        $('#eiaTaskId').val("");
                    },
                    min: function () {
                        $(".layui-layer-title").text("查看客户");
                    },
                    restore: function () {
                        $(".layui-layer-title").text(" ");
                    }
                });
            }
            else if (obj.event == 'projectEdit') {
                pageUrl = '/eia/eiaProject/eiaProjectCreate?eiaProjectId=' + eiaProjectId + '&pageType=1';
                var index = layer.open({
                    title: ' ',
                    type: 2,
                    shade: false,
                    maxmin: true,
                    skin: 'larry-green',
                    area: ['100%', '100%'],
                    content: pageUrl,
                    success: function (layero, index) {
                        /*        var body = layer.getChildFrame('body', index);
                        body.find('#eiaProjectId').val(data.eiaProjectId);
                    $('#eiaTaskId').val(data.eiaTaskId);
                        body.find('#eiaTaskId').val(data.eiaProjectId);*/
                    },
                    end: function () {
                        table.reload('eiaProjectList');
                        $('#eiaProjectId').val("");
                        $('#eiaTaskId').val("");
                    },
                    min: function () {

                    },
                    restore: function () {

                    }
                });
            } else if (obj.event == 'projectGisShow') {
                pageUrl = '/eia/eiaProject/eiaGisGeoProject?eiaProjectId=' + eiaProjectId;
                var index = layer.open({
                    title: ' ',
                    type: 2,
                    shade: false,
                    maxmin: true,
                    skin: 'larry-green',
                    area: ['100%', '100%'],
                    content: pageUrl,
                    success: function (data) {
                    },
                    end: function () {
                        table.reload('eiaProjectList');
                        $('#eiaProjectId').val("");
                        $('#eiaTaskId').val("");
                    },
                    min: function () {

                    },
                    restore: function () {

                    }
                });

            }
            else if (obj.event === 'projectFlow') {//流程
                actionUrl = '/eia/eiaProjectPlan/checkProjectFlow?eiaProjectId='+eiaProjectId;
                $.post(actionUrl, data.field, function (data) {
                    if (data.code == 0) {
                        pageUrl = '/eia/eiaWorkFlow/eiaWorkFlowProject?tableName=EiaProject&tableName='+eiaProjectId;
                        var index = layer.open({
                            title: ' ',
                            type: 2,
                            shade: false,
                            maxmin: true,
                            skin: 'larry-green',
                            area: ['100%', '100%'],
                            content: pageUrl,
                            before:function(){

                            },
                            success: function (layero, index) {
                            },
                            end: function () {
                                $('#eiaProjectId').val("");
                            },
                            min: function () {
                                $(".layui-layer-title").text("项目流程");
                            },
                            restore: function () {
                                $(".layui-layer-title").text(" ");
                            }
                        });
                    } else {
                        var msg = data.msg
                        layer.msg(data.msg, {icon: 2, time: 2000}, function () {
                        });
                    }
                });

            }
            else if (obj.event === 'certDownload') {
                var loadingIndex = layer.load(1, {time: 10*1000,shade: 0.1});
                $.ajax({
                    url: "/eia/eiaContract/matchProve?eiaProjectId=" + eiaProjectId,
                    type: "POST",
                    data: {},
                    dataType: "json",
                    success: function (data) {
                        if (data.code == 0) {
                            window.location.href = "../eiaContract/exportProCert?eiaProjectId=" + eiaProjectId;
                            layer.msg('正在导出...', {icon: 16, shade: 0.01}, function () {
                                var index = parent.layer.getFrameIndex(window.name);
                                parent.layer.close(index);
                            })
                        } else {
                            layer.msg(data.msg, {icon: 2, time: 1000,shade: 0.1});
                        }
                        layer.close(loadingIndex);
                    }
                });
            }
        });
    };
    // Tab切换
    element.on('tab(proProcessTab)', function(data){
        var cur_index = data.index;
        switch (cur_index){
            case 0:
                waitAffairsRender();
                break;
            case 1:
                eiaProjectRender();
                break;
        }
    });
    waitAffairsRender();

    //监听头部工具栏事件
    //监听事件
    table.on('toolbar(waitAffairsList)', function(obj){
        switch(obj.event){
            case 'getSelect':
                var waitName = $("#waitName").val();
                table.reload('waitAffairsList', {
                    where: {
                        workFlowTitle: waitName,
                        tableName:"EiaProject"
                    }
                });
                break;
        }
    });

    table.on('toolbar(eiaProjectList)', function(obj){
        switch(obj.event){
            case 'getSelect':
                var projectName = $("#projectName").val();
                table.reload('eiaProjectList', {
                    where: {
                        projectName: projectName
                    }
                });
                break;
        }
    });
});