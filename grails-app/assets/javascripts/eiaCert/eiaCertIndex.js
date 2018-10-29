layui.use(['jquery', 'layer', 'table'], function () {
    var $ = layui.jquery,
        layer = layui.layer,
        table = layui.table;

    var ifAdd = getParamFromUrl(document.location.href, "ifAdd");

    //渲染表格
    table.render({
        id: 'eiaProjectList',
        elem: '#eiaProjectList',
        url: '/eia/eiaCert/eiaCertQuery',
        toolbar: '#tableTopTmp',
        defaultToolbar:['filter', 'print', 'exports'],
        cols: [[
            {fixed: 'left', title: '序号', width: "7%", align: "center", templet: "#indexTable"},
            {field: 'projectNo', Width: '15%', title: '项目编号', align: "center"},
            {field: 'projectName', Width: '15%', title: '项目名称', align: "center"},
            {field: 'inputDept', Width: '27', title: '申请部门', align: "center"},
            {field: 'inputUser', Width: '13%', title: '申请人', align: "center"},
            {field: 'ifApplyCert', Width: '6%', title: '是否申请资质', align: "center", templet: '#ifApplyCertTp'},
            {field: 'certType', Width: '5%', title: '资质申请类型', align: "center", templet: '#certType'},
            {field: 'status', Width: '4%', title: '流程状态', align: "center"},
            {fixed: 'right', title: '操作', Width: "5%", align: "center", toolbar: '#eiaProjectListTool', align: "center"}
        ]],
        page: true,
        even: true,
        limit: 10,
        done:function () {
            if(ifAdd == '1'){
                $('.addBtn').trigger('click');
            }
        }
    });

    //监听工具条
    table.on('tool(eiaProjectList)', function (obj) {
        var data = obj.data;
        if (obj.event === 'eiaCheck') {    //查看

            $('#eiaCertId').val(data.id);
            pageUrl = '/eia/eiaCert/eiaCertDetail?eiaCertId=' + data.id;
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
                    $('#eiaCertId').val("");
                },
                min: function () {
                    $(".layui-layer-title").text("资质详情");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        }
        else if (obj.event === 'eiaDel') {    //删除
            layer.confirm('确定要删除该资质吗?', {icon: 3}, function (index) {
                var loadingIndex = layer.load(1, {time: 10 * 1000, shade: 0.1});
                var eiaCertId = data.id;
                $.post("../eiaCert/eiaCertDel", {eiaCertId: eiaCertId}, function (result) {
                    if (result.code == 0) {
                        table.reload('eiaProjectList');
                        layer.msg("删除成功！", {icon: 1, time: 1500, shade: 0.1})
                    } else {

                    }
                    layer.close(loadingIndex);
                });
            }, function (index) {
                //取消
            });
        }
        else if (obj.event === 'certSub') {
            layer.confirm('确定要提交该资质申请吗?', {icon: 3}, function (index) {
                var eiaCertId = data.id;
                var loadingIndex = layer.load(1, {time: 10 * 1000, shade: 0.1});
                $.post("../eiaCert/eiaCertSub", {eiaCertId: eiaCertId}, function (result) {
                    if (result.code == 0) {
                        table.reload('eiaProjectList');
                        layer.msg("资质申请提交成功！", {icon: 1, time: 1500, shade: 0.1})
                    } else {
                        layer.msg(result.msg, {icon: 2, time: 1500, shade: 0.1})
                    }
                    layer.close(loadingIndex);
                });
            }, function (index) {
                //取消
            });
        }
        else if (obj.event === 'certFlow') {
            ajaxBox("/eia/eiaWorkFlowBusi/checkWorkFlow", {
                tableNameId: data.id,
                tableName: "EiaCert"
            }, function (res) {
                if (res.code == 0) {
                    pageUrl = '/eia/eiaWorkFlow/eiaWorkFlowTrans';
                    $('#tableNameId').val(data.id);
                    $('#tableName').val("EiaCert");
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
                            $(".layui-layer-title").text("资质申请流程");
                        },
                        restore: function () {
                            $(".layui-layer-title").text(" ");
                        }
                    });
                }else{
                    layer.msg(res.msg,{time: 1500,shade: 0.1});
                }
            });
        } else if (obj.event === 'repeatSub') {
            //再次申请
            layer.confirm('确定要启动重复申请流程吗?', {icon: 3}, function (index) {
                var pageUrl = '/eia/eiaCert/eiaCertRepeat?eiaCertId=' + data.id;
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
                        $('#projectId').val("");
                    },
                    min: function () {
                        $(".layui-layer-title").text("再次申请");
                    },
                    restore: function () {
                        $(".layui-layer-title").text(" ");
                    }
                });

            });
        }
    });

    //监听头部工具栏事件
    //监听事件
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
            case 'certAdd':
                var pageUrl = '/eia/eiaCert/eiaCertCreate';
                var index = layer.open({
                    title: ' ',
                    type: 2,
                    shade: false,
                    maxmin: true,
                    skin: 'larry-green',
                    area: ['100%', '100%'],
                    content: pageUrl,
                    success: function (layero, index) {
                        ifAdd = 0;
                    },
                    end: function () {
                        table.reload("eiaProjectList");
                    },
                    min: function () {
                        $(".layui-layer-title").text("新增资质");
                    },
                    restore: function () {
                        $(".layui-layer-title").text(" ");
                    }
                });
                break;
        };
    });
});