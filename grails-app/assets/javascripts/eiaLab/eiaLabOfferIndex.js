layui.use(['jquery', 'form', 'layer', 'table', 'element', 'laydate'], function () {
    var $ = layui.jquery,
        layer = layui.layer,
        form = layui.form,
        table = layui.table,
        element = layui.element,
        laydate = layui.laydate;

    var eiaProjectId = getParamFromUrl(document.location.href, "eiaProjectId");
    if (!eiaProjectId) {
        eiaProjectId = "";
    }

    var queryInfoList = function () {
        $.ajax({
            url: "../eiaLabOffer/getOfferProjectType",
            type: 'get',
            success: function (data) {
                if (data.data) {
                    var projectType = $('#projectType');
                    projectType.append('<option value="">请选择项目类型</option>');
                    for (var i = 0; i < data.data.length; i++) {
                        projectType.append('<option value="' + data.data[i].code + '">' + data.data[i].codeDesc + '</option>')
                    }
                    form.render('select', 'projectType');
                }
            }
        });
        $.ajax({
            url: "../eiaLabOffer/getOfferStateList",
            type: 'get',
            success: function (data) {
                if (data.data) {
                    var offerState = $('#offerState');
                    offerState.append('<option value="">请选择报价状态</option>');
                    for (var i = 0; i < data.data.length; i++) {
                        offerState.append('<option value="' + data.data[i].code + '">' + data.data[i].codeDesc + '</option>')
                    }
                    form.render('select', 'offerState');
                }
            }
        });
    };

    //渲染表格
    var tableIns = table.render({
        id: 'eiaLabOfferList',
        elem: '#eiaLabOfferList',
        url: '/eia/eiaLabOffer/getEiaLabOfferDataList?eiaProjectId=' + eiaProjectId,
        cellMinWidth: 200,
        toolbar: '#tableTopTmp',
        defaultToolbar:['filter', 'print', 'exports'],
        cols: [
            [
                {type: 'numbers', title: '序号'},
                {field: 'projectName', width: '20%', title: '项目名称', align: 'center'},
                {field: 'sjClientName', width: '18%', title: '受检单位名称', align: 'center', templet: '#sjClientShow'},
                {field: 'countFeeTax', width: '10%', title: '费用总计(元)', align: 'center'},
                {field: 'ifYxTestChar', width: '8%', title: '是否宇相检测', align: 'center'},
                {field: 'projectType', width: '8%', title: '项目类型', align: 'center'},
                {field: 'sampleType', width: '8%', title: '样品类型', align: 'center'},
                {field: 'workFlowState', width: '8%', title: '流程状态', align: 'center'},
                {fixed: 'right', title: '操作', width: '18%', align: 'center', toolbar: '#eiaLabOfferListBar', fixed: "right"}
            ]
        ],
        page: true,
        even: true,
        limit: 10
    });
    //高级查询数据项
    function eiaLabOfferGetKey() {
        var projectName = $("#projectName").val();
        var projectType = $("#projectType").val();
        var inputDept = $("#qInputDept").val();
        var inputUser = $("#qInputUser").val();
        var offerState = $("#offerState").val();
        var ifYxTestChar = $("#ifYxTestChar").val();
        var key = {
            projectName: projectName,
            projectType: projectType,
            inputDept: inputDept,
            inputUser: inputUser,
            offerState: offerState,
            ifYxTestChar: ifYxTestChar
        };
        return key;
    }
    //高级查询
    form.on('submit(query)', function () {
        table.reload("eiaLabOfferList",{
            where : {
                key : eiaLabOfferGetKey()
            }, page: {
                curr: 1
            }
        });
        return false;
    });
    //监听工具条
    table.on('tool(eiaLabOfferList)', function (obj) {
        var data = obj.data;
        if (obj.event === 'offerShow') {
            var pageUrl = "../eiaLabOffer/eiaLabOfferDetail";
            $("#eiaLabOfferId").val(data.id);
            var index = layer.open({
                title: " ",
                type: 2,
                shade: false,
                maxmin: true,
                skin: 'larry-green',
                area: ['100%', '100%'],
                content: pageUrl,
                end: function () {
                    table.reload('eiaLabOfferList');
                    $("#eiaLabOfferId").val("");
                },
                min: function () {
                    $(".layui-layer-title").text("查看检测方案");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        } else if (obj.event === 'offerEdit') {
            var pageUrl = "../eiaLabOffer/eiaLabOfferCreate";
            $("#eiaLabOfferId").val(data.id);
            var index = layer.open({
                title: " ",
                type: 2,
                shade: false,
                maxmin: true,
                skin: 'larry-green',
                area: ['100%', '100%'],
                content: pageUrl,
                end: function () {
                    table.reload('eiaLabOfferList');
                    $("#eiaLabOfferId").val("");
                },
                min: function () {
                    $(".layui-layer-title").text("修改检测方案");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        } else if (obj.event === 'offerDel') {
            layer.confirm('确定要删除该检测方案吗?', {icon: 3}, function (index) {
                var loadingIndex = layer.load(1, {time: 10*1000,shade: 0.1});
                $.post("../eiaLabOffer/eiaLabOfferDel", {eiaLabOfferId: data.id}, function (data) {
                    if (data.code == 0) {
                        layer.msg('删除成功！', {icon: 1, time: 1500,shade: 0.1}, function () {
                            table.reload("eiaLabOfferList");
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
            });
        } else if (obj.event === 'offerSubmit') {
            layer.confirm('确定要提交该检测方案吗?', {icon: 3}, function (index) {
                var loadingIndex = layer.load(1, {time: 10*1000,shade: 0.1});
                $.post("../eiaLabOffer/eiaLabOfferSubmit", {eiaLabOfferId: data.id}, function (result) {
                    if (result.code == 0) {
                        layer.msg('提交成功！', {icon: 1, time: 1500,shade: 0.1}, function () {
                            table.reload("eiaLabOfferList");
                        });
                    } else {
                        if (result.msg) {
                            layer.msg("提交失败！<br/><div style='color:red;'>错误信息：" + result.msg + "</div>", {icon: 2, time: 1500});
                        } else {
                            layer.msg('提交失败！', {icon: 2, time: 1500,shade: 0.1});
                        }
                    }
                    layer.close(loadingIndex);
                });
            });
        } else if (obj.event === 'copyOffer') {
            layer.confirm('确定要复制该检测方案吗?', {icon: 3}, function (index) {
                var loadingIndex = layer.load(1, {time: 10*1000,shade: 0.1});
                $.post("../eiaLabOffer/copyEiaLabOfferAndEiaLabOfferPlan", {eiaLabOfferId: data.id}, function (data) {
                    if (data.code == 0) {
                        layer.msg('复制成功！', {icon: 1, time: 1500,shade: 0.1}, function () {
                            table.reload("eiaLabOfferList");
                        });
                    } else {
                        if (data.msg) {
                            layer.msg("处理失败！<br/><div style='color:red;'>错误信息：" + data.msg + "</div>", {
                                icon: 2,
                                time: 1500
                            });
                        } else {
                            layer.msg('复制失败！', {icon: 2, time: 1500,shade: 0.1});
                        }
                    }
                    layer.close(loadingIndex);
                });
            });
        } else if (obj.event === 'offerStop') {
            layer.confirm('确定要中止该检测方案吗?', {icon: 3}, function (index) {
                $.post("../eiaLabOffer/updateOfferState", {eiaLabOfferId: data.id, offerState: "报价作废"}, function (data) {
                    if (data.code == 0) {
                        layer.msg('报价已作废！', {icon: 1, time: 1500}, function () {
                            table.reload("eiaLabOfferList");
                        });
                    } else {
                        if (data.msg) {
                            layer.msg("处理失败！<br/><div style='color:red;'>错误信息：" + data.msg + "</div>", {
                                icon: 2,
                                time: 1500
                            });
                        } else {
                            layer.msg('处理失败！', {icon: 2, time: 1500});
                        }
                    }
                });
            });
        } else if (obj.event === 'recoveryOffer') {
            layer.confirm('确定要恢复该检测方案吗?', {icon: 3}, function (index) {
                var loadingIndex = layer.load(1, {time: 10*1000,shade: 0.1});
                $.post("../eiaLabOffer/updateOfferState", {eiaLabOfferId: data.id, offerState: "生成报价"}, function (data) {
                    if (data.code == 0) {
                        layer.msg('报价恢复成功！', {icon: 1, time: 1500,shade: 0.1}, function () {
                            table.reload("eiaLabOfferList");
                        });
                    } else {
                        if (data.msg) {
                            layer.msg("处理失败！<br/><div style='color:red;'>错误信息：" + data.msg + "</div>", {
                                icon: 2,
                                time: 1500
                            });
                        } else {
                            layer.msg('处理失败！', {icon: 2, time: 1500,shade: 0.1});
                        }
                    }
                    layer.close(loadingIndex);
                });
            });
        } else if (obj.event === 'offerFlow') {    //查看流程
            pageUrl = '/eia/eiaWorkFlow/eiaWorkFlowTrans';
            $('#tableNameId').val(data.id);
            $('#tableName').val("EiaLabOffer");
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
                    $(".layui-layer-title").text("监测方案流程");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        }
    });
    $(document).on('click', '.wtClientShow', function () {
        var pageUrl = '../labClient/labClientShow';
        var clientId = $(this).attr("id");
        $('#clientId').val($(this).attr("id"));
        var index = layer.open({
            title: " ",
            type: 2,
            shade: false,
            maxmin: true,
            skin: 'larry-green',
            area: ['100%', '100%'],
            content: pageUrl,
            success: function (layero, index) {
                var body = layer.getChildFrame('body', index);
                body.find('#clientId').val(clientId);
            },
            end: function () {
                // table.reload('labClientList');
            },
            min: function () {
                $(".layui-layer-title").text("查看企业信息");
            },
            restore: function () {
                $(".layui-layer-title").text(" ");
            }
        });
    });
    $(document).on('click', '.sjClientShow', function () {
        var pageUrl = '../labClient/labClientShow';
        var clientId = $(this).attr("id");
        $('#clientId').val($(this).attr("id"))
        var index = layer.open({
            title: " ",
            type: 2,
            shade: false,
            maxmin: true,
            skin: 'larry-green',
            area: ['100%', '100%'],
            content: pageUrl,
            success: function (layero, index) {
                var body = layer.getChildFrame('body', index);
                body.find('#clientId').val(clientId);
            },
            end: function () {
                // table.reload('labClientList');
            },
            min: function () {
                $(".layui-layer-title").text("查看企业信息");
            },
            restore: function () {
                $(".layui-layer-title").text(" ");
            }
        });
    });
    laydate.render({
        elem: '#reportDate'
        , type: 'month'
    });

    //监听头部工具栏事件
    //监听事件
    table.on('toolbar(eiaLabOfferList)', function(obj){
        switch(obj.event){
            case 'getSelect':
                table.reload('eiaLabOfferList', {
                    where: {
                        key: eiaLabOfferGetKey()
                    }, page: {
                        curr: 1
                    }
                });
                break;
            case 'offerAdd':
                var pageUrl = "../eiaLabOffer/eiaLabOfferCreate";
                var index = layer.open({
                    title: " ",
                    type: 2,
                    shade: false,
                    maxmin: true,
                    skin: 'larry-green',
                    area: ['100%', '100%'],
                    content: pageUrl,
                    end: function () {
                        table.reload('eiaLabOfferList');
                        $("#eiaLabOfferId").val("");
                    },
                    min: function () {
                        $(".layui-layer-title").text("新增检测方案");
                    },
                    restore: function () {
                        $(".layui-layer-title").text(" ");
                    }
                });
                break;
            case 'planCal':
                var pageUrl = "../eiaLabOfferPlan/eiaPlanCalList";
                var index = layer.open({
                    title: " ",
                    type: 2,
                    shade: false,
                    maxmin: true,
                    skin: 'larry-green',
                    area: ['100%', '100%'],
                    content: pageUrl,
                    end: function () {
                        var eiaLabOfferId = $('#eiaLabOfferId').val();
                        if (eiaLabOfferId) {
                            var pageUrl = '../eiaLabOffer/eiaLabOfferCreate';
                            var index = layer.open({
                                title: ' ',
                                type: 2,
                                shade: false,
                                maxmin: true,
                                skin: 'larry-green',
                                area: ['100%', '100%'],
                                content: pageUrl,
                                end: function () {
                                    table.reload('eiaLabOfferList');
                                    $("#eiaLabOfferId").val("");
                                }
                            });
                        }
                    },
                    min: function () {
                        $(".layui-layer-title").text("检测费用计算器");
                    },
                    restore: function () {
                        $(".layui-layer-title").text(" ");
                    }
                });
                break;
            case 'highSelect':
                var curState = $(this).attr('state');
                switch (curState){
                    case '0':
                        $(this).attr('state','1');
                        queryInfoList();
                        queryOpen();
                        break;
                    case '1':
                        $(this).attr('state','0');
                        queryClose();
                        break;
                }
                break;
        }
    });
});
function queryOpen() {
    $("#advanced-query").removeClass("display-none");
    $("#open").addClass("display-none");
    $("#close").removeClass("display-none");
}
function queryClose() {
    $("#advanced-query").addClass("display-none");
    $("#open").removeClass("display-none");
    $("#close").addClass("display-none");
}