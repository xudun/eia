layui.use(['jquery', 'layer', 'form', 'table'], function(){
    var $ = layui.jquery,
        form = layui.form,
        layer = layui.layer,
        table = layui.table;

    var eiaWorkFlowId = parent.$('#eiaWorkFlowId').val();
    var eiaWorkFlowNodeId = parent.$('#eiaWorkFlowNodeId').val();
    var $nodesIconName = $('#nodesIconName'),
        $nodesColor = $('#nodesColor');

    //基本信息回显
    $.ajax({
        url:"/eia/eiaWorkFlowNode/getEiaWorkFlowNodeDataMap?eiaWorkFlowNodeId=" + eiaWorkFlowNodeId,
        type:"POST",
        data:{},
        dataType: "json",
        success: function (result) {
            if (result) {
                $('#nodesCode').text(result.data.nodesCode);
                $('#nodesName').text(result.data.nodesName);
                $('#nodesAuthCode').text(result.data.nodesAuthCode);
                $('#nodesUrl').text(result.data.nodesUrl);
                $('#nodesTabTitle').text(result.data.nodesTabTitle);
                $nodesColor.text(result.data.nodesColor);
                $('#nodesNum').text(result.data.nodesNum);
                $('#nodesAuthType').text(result.data.nodesAuthType);
                $nodesIconName.text(result.data.nodesIconName);

                var str = "<i class='larry-icon'>" + result.data.nodesIconName + "</i>";
                $nodesIconName.closest('.layui-input-block').append(str);
                $nodesColor.next('.layui-badge-rim').css('background', result.data.nodesColor);
            }
        }
    });

    // var tableUrl = 'data/eiaWorkFlowNodeProcessListData.json';
    //渲染表格
    table.render({
        id: 'eiaWorkFlowNodeProcessList',
        elem: '#eiaWorkFlowNodeProcessList',
        url: '/eia/eiaWorkFlowNodeProcess/getEiaWorkFlowNodeProcessDataList?eiaWorkFlowId=' + eiaWorkFlowId + '&eiaWorkFlowNodeId=' + eiaWorkFlowNodeId,
        toolbar: '#tableTopTmp',
        defaultToolbar:['filter', 'print', 'exports'],
        cols: [[
            {fixed: 'left', title: '序号',width:"7%",align: "center",templet: "#indexTable"},
            {field:'processName',width:'15%', title: '按钮名称',align: "center"},
            {field:'processShowName',width:'15%', title: '按钮别名',align: "center"},
            {field:'processNum',width:'15%', title: '按钮位置序号',align: "center"},
            {field:'processCode',width:'15%', title: '按钮编码',align: "center"},
            {field:'processUrl',width:'15%', title: '按钮URL',align: "center"},
            {field:'processUrlParams',width:'15%', title: '按钮URL参数',align: "center"},
            {field:'processColor',width:'15%', title: '按钮颜色',align: "center", templet: '#colorTp'},
            {field:'processIconName',width:'15%', title: '按钮图标',align: "center", templet: '#iconTp'},
            {fixed: 'right', title: '操作',width:"15%",align: "center", toolbar: '#mlTool',align: "center"}
        ]],
        page: true,
        even: true,
        limit: 10
    });

    //监听工具条
    table.on('tool(eiaWorkFlowNodeProcessList)', function (obj) {
        var data = obj.data;
        $('#eiaWorkFlowNodeProcessId').val(data.id);
        if(obj.event === 'eiaCheck'){    //查看
            pageUrl = '/eia/eiaWorkFlowNodeProcess/eiaWorkFlowNodeProDetail';
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
                    body.find('#eiaWorkFlowNodeProcessId').val(data.id);
                },
                end: function () {
                    $('#eiaWorkFlowNodeProcessId').val("");
                },
                min: function () {
                    $(".layui-layer-title").text("节点动作详情");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        }
    });

});