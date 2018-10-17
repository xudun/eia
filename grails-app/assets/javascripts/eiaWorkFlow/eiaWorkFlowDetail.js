layui.use(['jquery', 'layer', 'form', 'table'], function(){
    var $ = layui.jquery,
        form = layui.form,
        layer = layui.layer,
        table = layui.table;

    var eiaWorkFlowId = parent.$('#eiaWorkFlowId').val();
    $('#eiaWorkFlowId').val(eiaWorkFlowId);
    //基本信息回显

    $.ajax({
        url:"/eia/eiaWorkFlow/getEiaWorkFlowDataMap?eiaWorkFlowId=" + eiaWorkFlowId,
        type:"POST",
        data:{},
        dataType: "json",
        success: function (result) {
            $('#workFlowCode').text(result.data.workFlowCode);
            $('#workFlowName').text(result.data.workFlowName);
            $('#workFlowVersion').text(result.data.workFlowVersion);
        }
    });


    var tableUrl = 'data/eiaWorkFlowNodeListData.json';
    //渲染表格
    table.render({
        id: 'eiaWorkFlowNodeList',
        elem: '#eiaWorkFlowNodeList',
        url:'/eia/eiaWorkFlowNode/getEiaWorkFlowNodeDataList?eiaWorkFlowId=' + eiaWorkFlowId,
        toolbar: '#tableTopTmp',
        defaultToolbar:['filter', 'print', 'exports'],
        cols: [[
            {fixed: 'left', title: '序号',width: '6%',align: "center",templet: "#indexTable"},
            {field:'nodesCode',width:'15%', title: '流程节点编码',align: "center"},
            {field:'nodesNum',width:'12%', title: '流程节点序号',align: "center"},
            {field:'nodesName',width:'15%', title: '流程节点名称',align: "center"},
            {field:'nodesAuthType',width:'17%', title: '流程节点权限控制类型',align: "center"},
            {field:'nodesIconName',width:'12%', title: '流程节点图标',align: "center",templet: '#iconTp'},
            {field:'nodesAuthCode',width:'15%', title: '节点权限编码',align: "center"},
            // {field:'nodesColor',width:'15%', title: '流程节点颜色',align: "center", templet: '#colorTp'},
            {fixed: 'right', title: '操作',width:"15%",align: "center", toolbar: '#mlTool',align: "center"}
        ]],
        page: true,
        even: true,
        limit: 10
    });

    //监听工具条
    table.on('tool(eiaWorkFlowNodeList)', function (obj) {
        var data = obj.data;
        $('#eiaWorkFlowNodeId').val(data.id);
        if(obj.event === 'eiaCheck'){    //查看
            pageUrl = '/eia/eiaWorkFlowNode/eiaWorkFlowNodeDetail';
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
                    body.find('#eiaWorkFlowNodeId').val(data.id);
                },
                end: function () {
                    $('#eiaWorkFlowNodeId').val("");
                },
                min: function () {
                    $(".layui-layer-title").text("流程节点详情");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        }
    });

});