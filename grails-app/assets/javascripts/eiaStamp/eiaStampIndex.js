layui.use(['jquery', 'layer', 'table'], function () {
    var $ = layui.jquery,
        layer = layui.layer,
        table = layui.table;

    //渲染表格
    table.render({
        id: 'eiaStampList',
        elem: '#eiaStampList',
        url: '/eia/eiaStamp/getEiaStampDataList',
        toolbar: '#tableTopTmp',
        defaultToolbar: ['filter', 'print', 'exports'],
        cols: [[
            {fixed: 'left', title: '序号', width: '5%', align: "center", templet: "#indexTable"},
            {field: 'applicant', width: '10%', title: '申请人', align: "center"},
            {field: 'stampNum', width: '15%', title: '用章次数', align: "center"},
            {field: 'ifTakeOut', width: '15%', title: '是否外带', align: "center"},
            {field: 'appTime', width: '15%', title: '申请时间', align: "center"},
            {field: 'inputDept', width: '15%', title: '录入部门', align: "center"},
            {field: 'inputUser', width: '10%', title: '录入人', align: "center"},
            {fixed: 'right', title: '操作', width: '15%', align: "center", toolbar: '#mlTool', align: "center"}
        ]],
        page: true,
        even: true,
        limit: 10
    });

    //监听工具条
    table.on('tool(eiaStampList)', function (obj) {
        var data = obj.data;
        var eiaStampId = data.id;
        $('#eiaStampId').val(eiaStampId);
        if (obj.event === 'eiaEdit') {
            pageUrl = '/eia/eiaStamp/eiaStampEdit';
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
                    $('#eiaStampId').val("");
                },
                min: function () {
                    $(".layui-layer-title").text("项目流程");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        }
        else if (obj.event === 'eiaDetail') {
            pageUrl = '/eia/eiaStamp/eiaStampDetail';
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
                    $('#eiaStampId').val("");
                },
                min: function () {
                    $(".layui-layer-title").text("项目流程");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        }
        else if (obj.event === 'stampFlow') {
            ajaxBox("/eia/eiaWorkFlowBusi/checkWorkFlow", {
                tableNameId: data.id,
                tableName: "EiaStamp"
            }, function (res) {
                if (res.code == 0) {
                    pageUrl = '/eia/eiaWorkFlow/eiaWorkFlowTrans';
                    $('#tableNameId').val(data.id);
                    $('#tableName').val("EiaStamp");
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
                            $('#eiaStampId').val("");
                            $('#tableNameId').val("");
                            $('#tableName').val("");
                        },
                        min: function () {
                            $(".layui-layer-title").text("资质申请流程");
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

        else if (obj.event === 'eiaSubmit') {
            layer.confirm('确定要提交该资质申请吗?', {icon: 3}, function (index) {
                var eiaStampId = data.id;
                var loadingIndex = layer.load(1, {time: 10 * 1000, shade: 0.1});
                $.post("../eiaStamp/eiaStampSub", {eiaStampId: eiaStampId}, function (result) {
                    if (result.code == 0) {
                        table.reload('eiaStampList');
                        layer.msg("印章申请提交成功！", {icon: 1, time: 1500, shade: 0.1})
                    } else {
                        layer.msg(result.msg, {icon: 2, time: 1500, shade: 0.1})
                    }
                    layer.close(loadingIndex);
                });
            }, function (index) {
                //取消
            });
        } else if (obj.event === 'stampDel') {    //删除
            layer.confirm('确定要删除该印章申请吗?', {icon: 3}, function (index) {
                var loadingIndex = layer.load(1, {time: 10 * 1000, shade: 0.1});
                var eiaStampId = data.id;
                $.post("../eiaStamp/eiaStampDel", {eiaStampId: eiaStampId}, function (result) {
                    if (result.code == 0) {
                        table.reload('eiaStampList');
                        layer.msg("删除成功！", {icon: 1, time: 1500, shade: 0.1})
                        $('#eiaStampId').val('')
                    } else {
                        table.reload('eiaStampList');
                        layer.msg("删除失败！", {icon: 2, time: 1500, shade: 0.1})
                    }
                    layer.close(loadingIndex);
                });
            }, function (index) {
                $('#eiaStampId').val('')
                //取消
            });
        }
    });

    //监听头部工具栏事件
    //监听事件
    table.on('toolbar(eiaStampList)', function (obj) {
        switch (obj.event) {
            case 'getSelect':
                var applicant = $("#applicant").val();
                table.reload('eiaStampList', {
                    where: {
                        applicant: applicant
                    }
                });
                break;
            case 'stampAdd':
                var pageUrl = '/eia/eiaStamp/eiaStampEdit';
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
                        table.reload("eiaStampList");
                        $('#eiaStampId').val('')
                    },
                    min: function () {
                        $(".layui-layer-title").text("新增印章申请");
                    },
                    restore: function () {
                        $(".layui-layer-title").text(" ");
                    }
                });
                break;
        }
    });
});