layui.use(['jquery', 'layer', 'table'], function () {
    var $ = layui.jquery,
        layer = layui.layer,
        table = layui.table;

    var tabName = "EiaContract";

    var renderWorkFlowContractList = function (tabName) {
        table.render({
            id: 'eiaContractList',
            elem: '#eiaContractList',
            url: '/eia/eiaBoard/getEiaWorkFlowBusiDataList?tabName=' + tabName,
            toolbar: '#tableTopTmp',
            defaultToolbar:['filter', 'print', 'exports'],
            cols: [
                [
                    {fixed: 'left', title: '序号', width: '6%', align: "center", templet: "#indexTable"},
                    {field: 'workFlowTitle', width: '30.3%', title: '审批标题', align: "center"},
                    {field: 'workFlowName', width: '15%', title: '流程名称', align: "center"},
                    {field: 'nodesName', width: '15%', title: '当前节点名称', align: "center"},
                    {field: 'inputUser', width: '10%', title: '创建人', align: "center"},
                    {field: 'dateCreated', width: '14%', title: '创建日期', align: "center"},
                    {fixed: 'right', title: '操作', width: '10%', align: "center", toolbar: '#eiaWorkFlowListTool', align: "center"}
                ]
            ],
            page: true,
            even: true,
            limit: 10,
            done: function (res, curr, count) {
                $("#eiaContractNums").html(res.count);
                $('#workFlowContractTitle').val($('#proWorkFlowContractTitle').val());
            }
        });
    };
    renderWorkFlowContractList("EiaContract");

    table.on('tool(eiaContractList)', function (obj) {
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
                        pageUrl = '/eia/eiaWorkFlow/eiaWorkFlowProject'
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
    table.on('toolbar(eiaContractList)', function(obj){
        switch(obj.event){
            case 'getSelectWorkFlow':
                var workFlowTitle = $("#workFlowContractTitle").val();
                $('#proWorkFlowContractTitle').val(workFlowTitle);
                table.reload('eiaContractList', {
                    where: {
                        workFlowTitle: workFlowTitle
                    }
                });
                break;
        }
    });
});