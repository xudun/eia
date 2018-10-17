layui.use(['jquery', 'common', 'layer', 'table'], function () {
    var $ = layui.jquery,
        layer = layui.layer,
        table = layui.table;
    //渲染表格
    var tableIns = table.render({
        id: 'eiaDomainCodeList',
        elem: '#eiaDomainCodeList',
        url: '/eia/eiaDomainCode/eiaDomainCodeQuery',
        toolbar: '#tableTopTmp',
        defaultToolbar:['filter', 'print', 'exports'],
        cellMinWidth: 200,
        cols: [
            [
                {fixed: 'left', type: 'numbers', title: '序号', width: '6%', align: "center"},
                {field: 'code', width: '20%', title: '编码', align: 'center'},
                {field: 'codeDesc', title: '编码描述', width: '20%',  align: 'center'},
                {field: 'domain', title: '编码类型', width: '15%', align: 'center'},
                {field: 'domainDesc', title: '编码类型描述', width: '15%', align: 'center'},
                {field: 'codeLevel', title: '编码级别', width: '10%', align: 'center'},
                {field: 'displayOrder', title: '显示顺序', width: '10%', align: 'center'},
                {fixed: 'right', title: '操作', width: '15%', align: 'center', toolbar: '#eiaDomainCodeListBar', fixed: "right"}
            ]
        ],
        page: true,
        even: true,
        limit: 10
    });
    //监听工具条
    table.on('tool(eiaDomainCodeList)', function (obj) {
        var data = obj.data;
        if (obj.event === 'domainCodeShow') {
            $("#eiaDomainCodeId").val(data.id);
            var pageUrl = "../eiaDomainCode/eiaDomainCodeShow";
            var index = layer.open({
                title: " ",
                type: 2,
                shade: false,
                maxmin: true,
                skin: 'larry-green',
                area: ['100%', '100%'],
                content: pageUrl,
                min: function () {
                    $(".layui-layer-title").text("查看系统配置");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        } else if (obj.event == "domainCodeEdit"){
            var pageUrl = "../eiaDomainCode/eiaDomainCodeDetail";
            $("#eiaDomainCodeId").val(data.id);
            var index = layer.open({
                title: " ",
                type: 2,
                shade: false,
                maxmin: true,
                skin: 'larry-green',
                area: ['100%', '100%'],
                content: pageUrl,
                end: function () {
                    table.reload('eiaDomainCodeList');
                    $("#eiaDomainCodeId").val("");
                },
                min: function () {
                    $(".layui-layer-title").text("系统配置修改");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        } else if (obj.event === 'domainCodeDel') {
            layer.confirm('确定要删除该系统配置吗?', {icon: 3}, function (index) {
                var loadingIndex = layer.load(1, {time: 10*1000,shade: 0.1});
                $.post("../eiaDomainCode/eiaDomainCodeDel", {eiaDomainCodeId: data.id}, function (data) {
                    if (data.code == 0) {
                        layer.msg('删除成功！', {icon: 1, time: 1500,shade: 0.1}, function () {
                            table.reload("eiaDomainCodeList");
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
        }
    });
    //查询按钮
    $('.larry-btn a.layui-btn').on('click', function () {
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });
    var active = {
        getSelect: function () {
            var codeDesc = $("#codeDesc").val();
            table.reload('eiaDomainCodeList', {
                where: {
                    key: {
                        codeDesc : codeDesc
                    }
                }, page: {
                    curr: 1
                }
            });
        },
        domainCodeAdd: function () {
            var pageUrl = '../eiaDomainCode/eiaDomainCodeDetail';
            var winWidth = document.documentElement.clientWidth;
            var area = ['70%', '75%'];
            if (winWidth < 1300) {
                area = ['100%', '100%'];
            }
            layer.open({
                title: " ",
                type: 2,
                maxmin: true,
                skin: 'larry-green',
                area: area,
                content: pageUrl,
                end: function () {
                    table.reload('eiaDomainCodeList');
                    $("#labDomainCodeId").val("");
                },
                min: function () {
                    $(".layui-layer-title").text("新增系统配置");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        }
    };

    //监听头部工具栏事件
    //监听事件
    table.on('toolbar(eiaDomainCodeList)', function(obj) {
        switch (obj.event) {
            case 'getSelect':
                var codeDesc = $("#codeDesc").val();
                table.reload('eiaDomainCodeList', {
                    where: {
                        key: {
                            codeDesc : codeDesc
                        }
                    }, page: {
                        curr: 1
                    }
                });
                break;
            case 'domainCodeAdd':
                var pageUrl = '../eiaDomainCode/eiaDomainCodeDetail';
                var winWidth = document.documentElement.clientWidth;
                var area = ['70%', '75%'];
                if (winWidth < 1300) {
                    area = ['100%', '100%'];
                }
                layer.open({
                    title: " ",
                    type: 2,
                    maxmin: true,
                    skin: 'larry-green',
                    area: area,
                    content: pageUrl,
                    end: function () {
                        table.reload('eiaDomainCodeList');
                        $("#labDomainCodeId").val("");
                    },
                    min: function () {
                        $(".layui-layer-title").text("新增系统配置");
                    },
                    restore: function () {
                        $(".layui-layer-title").text(" ");
                    }
                });
                break;
        }
    })
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
