layui.use(['jquery', 'layer', 'table'], function(){
    var $ = layui.jquery,
        layer = layui.layer,
        table = layui.table;

    var listData = [];
    $('#eiaPlanCalListData').val(JSON.stringify(listData));

    //渲染表格
    table.render({
        id: 'eiaPlanCalList',
        elem: '#eiaPlanCalList',
        // url:'data/eiaPlanCalList.json',
        // cellMinWidth: 0,
        cols: [
            [
                {field: 'baseName', width: '10%', title: '检测基质', align: 'center', rowspan: 2},
                {field: 'paramNameCn', width: '15%', title: '检测项目', align: 'center', rowspan: 2},
                {field: 'maxSchemeMoney', width: '15%', title: '预估最高费用（元）', align: 'center', rowspan: 2},
                {field: 'discountFee', width: '16%', title: '最高费用折后价（元）', align: 'center', rowspan: 2},
                {align: 'sampleNum', title: '样品数量（个）', align: 'center', colspan: 3},
                {field: 'subTotal', width: '12%', title: '小计（元）', align: 'center', rowspan: 2},
                {fixed: 'right', title: '操作', width: '12%', rowspan: 2, align: 'center', toolbar: '#eiaPlanCalListTool', fixed: "right"
                }
            ], [
                {field: 'pointNum', width: '8%', title: '点位', align: 'center'},
                {field: 'freqNum', width: '8%', title: '频次', align: 'center'},
                {field: 'dayNum', width: '8%', title: '天数周期', align: 'center'}
            ]
        ],
        page: true,
        limit: 10
    });

    //监听工具条
    table.on('tool(eiaPlanCalList)', function (obj) {
        var data = obj.data;
        if(obj.event === 'planDel'){    //删除
            layer.confirm('确定要删除该检测计划吗?', {icon: 3}, function (index) {
                //确定
                var curListData = JSON.parse($('#eiaPlanCalListData').val());
                var deleteIndex = $(obj.tr[1]).index();
                curListData.splice(deleteIndex,1);
                $('#eiaPlanCalListData').val(JSON.stringify(curListData));
                obj.del();
                //计算主页内的主小计
                var cur_subtotal = parseInt($("#subtotal").text()),
                    this_subtotal = parseInt(data.subTotal);
                $("#subtotal").text(cur_subtotal-this_subtotal);

                layer.msg('删除成功', {icon: 1});
                if(!curListData.length){
                    var str = "<div class='layui-none'>无数据</div>";
                    $('.layui-table-box .layui-table-body').append(str);
                }
            },function (index) {
                //取消
            });
        }
    });

    //新增按钮
    $('.larry-btn a.layui-btn').click(function () {
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });
    var active = {
        planAdd: function () {    //新增项目
            var pageUrl = '../eiaLabOfferPlan/eiaPlanCalCreate';
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
                },
                end: function () {
                    var curListData = JSON.parse($('#eiaPlanCalListData').val());
                    table.reload('eiaPlanCalList', {
                        data: curListData,
                        url: ""
                    });
                },
                min: function () {
                    $(".layui-layer-title").text("新增项目");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        },
        planGroupAdd: function () {    //新增套餐
            var pageUrl = '../eiaLabOfferPlan/eiaPlanCalGroupCreate';
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
                },
                end: function () {
                    var curListData = JSON.parse($('#eiaPlanCalListData').val());
                    table.reload('eiaPlanCalList', {
                        data: curListData,
                        url: ""
                    });
                },
                min: function () {
                    $(".layui-layer-title").text("新增套餐");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        },
        createLabOffer: function () {    //新增监测方案
            var pageUrl = '../eiaLabOffer/eiaLabOfferProjectSelect';
            var index = layer.open({
                title:' ',
                type: 2,
                shade: false,
                maxmin: true,
                skin: 'larry-green',
                area: ['80%', '80%'],
                content: pageUrl,
                success:function (layero, index) {
                    var body = layer.getChildFrame('body', index);
                },
                end: function () {

                },
                min: function () {
                    $(".layui-layer-title").text("新增套餐");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        }
    }
});