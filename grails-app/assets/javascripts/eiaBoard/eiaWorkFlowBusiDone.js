layui.use(['jquery', 'layer', 'table'], function () {
    var $ = layui.jquery,
        layer = layui.layer,
        table = layui.table;

    var renderWorkFlowComList = function () {
        table.render({
            id: 'eiaWorkFlowComList',
            elem: '#eiaWorkFlowComList',
            url: '/eia/eiaBoard/getEiaWorkFlowBusiDataList?workType=1',
            toolbar: '#tableTopTmp',
            defaultToolbar:['filter', 'print', 'exports'],
            cols: [
                [
                    {fixed: 'left', title: '序号', width: '6%', align: "center", templet: "#indexTable"},
                    {field: 'workFlowTitle', width: '30.3%', title: '审批标题', align: "center"},
                    {field: 'workFlowName', width: '20%', title: '流程名称', align: "center"},
                    {field: 'nodesName', width: '10%', title: '当前节点名称', align: "center"},
                    {field: 'inputUser', width: '10%', title: '创建人', align: "center"},
                    {field: 'dateCreated', width: '14%', title: '创建日期', align: "center"},
                    {fixed: 'right', title: '操作', width: '10%', align: "center", toolbar: '#eiaWorkFlowListTool', align: "center"}
                ]
            ],
            page: true,
            even: true,
            limit: 10,
            done: function (res, curr, count) {
                $("#workFlowComBusiNums").html(res.count);
                $('#workFlowComTitle').val($('#proWorkFlowComTitle').val());
            }
        });
    };
    renderWorkFlowComList();

    table.on('tool(eiaWorkFlowComList)', function (obj) {
        var data = obj.data;
        if (obj.event === 'eiaEdit') {
            ajaxBox("/eia/eiaWorkFlowBusi/checkWorkFlow", {
                tableNameId: data.tableNameId,
                tableName: data.tableName
            }, function (res) {
                if (res.code == 0) {
                    var pageUrl = '/eia/eiaWorkFlow/eiaWorkFlowTrans';
                    $('#tableNameId').val(data.tableNameId);
                    $('#tableName').val(data.tableName);
                    if (data.tableName == "EiaProject") {
                        var pageUrl = '/eia/eiaWorkFlow/eiaWorkFlowProject'
                    }
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
                            $(".layui-layer-title").text(data.workFlowName);
                        },
                        restore: function () {
                            $(".layui-layer-title").text(" ");
                        }
                    });
                } else {
                    layer.msg(res.msg, {time: 1500, icon: 2, shade: 0.1});
                }
            });
        }
    });

    //监听头部工具栏事件
    //监听事件
    table.on('toolbar(eiaWorkFlowComList)', function(obj){
        switch(obj.event){
            case 'getSelectWorkComFlow':
                var workFlowTitle = $("#workFlowComTitle").val();
                $('#proWorkFlowComTitle').val(workFlowTitle);
                table.reload('eiaWorkFlowComList', {
                    where: {
                        workFlowTitle: workFlowTitle
                    }
                });
                break;
        }
    });
});