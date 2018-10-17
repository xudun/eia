layui.use(['jquery', 'layer', 'table'], function(){
    var $ = layui.jquery,
        layer = layui.layer,
        table = layui.table;

    //渲染表格
    table.render({
        id: 'labTestParamGroupList',
        elem: '#labTestParamGroupList',
        url: '../eiaLabOfferPlan/getLabTestParamGroupDataList',
        method: 'get',
        toolbar: '#tableTopTmp',
        defaultToolbar:['filter', 'print', 'exports'],
        cols: [
            [
                {field: 'groupName', title: '套餐名称', align: 'center', width: '40%', templet: '#paramGroupShow'},
                {field: 'groupFee', title: '套餐费用（元）', align: 'center'},
                {field: 'groupDiscount', title: '批量折扣（%）', align: 'center'},
                {fixed: 'right', title: '操作', width: 90, align: 'center', toolbar: '#labTestParamGroupListTool'}
            ]
        ],
        page: true,
        even: true,
        limit: 10
    });

    //监听工具条
    table.on('tool(labTestParamGroupList)', function (obj) {
        var data = obj.data;
        if(obj.event === 'paramGroupSelected'){    //选择
            parent.$("#labTestParamGroupId").val(data.id);
            parent.$("#groupName").val(data.groupName);
            parent.$("#groupFee").val(data.groupFee);
            var index = parent.layer.getFrameIndex(window.name);
            parent.layer.close(index)
        }
    });
    /**
     * 查看套餐明细
     */
    $(document).on('click', '.paramGroupShow', function () {
        var pageUrl = '../eiaLabOfferPlan/eiaParamGroupDetail';
        var labTestParamGroupId = $(this).attr("id");
        $("#labTestParamGroupId").val(labTestParamGroupId);
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
                body.find('#labTestParamGroupId').val(labTestParamGroupId);
            },
            min: function () {
                $(".layui-layer-title").text("查看检测套餐");
            },
            restore: function () {
                $(".layui-layer-title").text(" ");
            }
        });
    });

    //监听头部工具栏事件
    //监听事件
    table.on('toolbar(labTestParamGroupList)', function(obj){
        switch(obj.event){
            case 'getSelect':
                var groupName = $("#groupName").val();
                table.reload('labTestParamGroupList', {
                    where: {
                        groupName: groupName
                    }, page: {
                        curr: 1
                    }
                });
                break;
        }
    });
});