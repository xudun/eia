layui.use(['layer','table'],function(){
    var $ = layui.$,
        layer = layui.layer,
        table = layui.table;
    /**
     * 基本信息form数据加载
     */
    var labTestParamGroupId = parent.$("#labTestParamGroupId").val();
    $.ajax({
        url: "../eiaLabOfferPlan/getEiaLabOfferPlanGroupDataMap?labTestParamGroupId=" + labTestParamGroupId,
        type:"post",
        cache: false,
        async: false,
        success: function (result) {
            if (result.data) {
                $("#groupName").text(result.data.groupName);
                $("#groupFee").text(result.data.groupFee);
                $("#groupDiscount").text(result.data.groupDiscount);
                $("#groupPointNum").text(result.data.groupPointNum);
                $("#groupFreqNum").text(result.data.groupFreqNum);
                $("#groupDayNum").text(result.data.groupDayNum);
            }
        }
    });
    /**
     * 渲染检测计划及费用明细列表
     */
    var tableIns = table.render({
        id:'labTestCapGroupList',
        elem: '#labTestCapGroupList',
        url: "../eiaLabOfferPlan/getLabTestCapGroupDataList?labTestParamGroupId=" + labTestParamGroupId,
        cellMinWidth: 200,
        toolbar: '#tableTopTmp',
        defaultToolbar:['filter', 'print', 'exports'],
        cols: [
            [
                {field: 'baseName', width: '10%', title: '检测基质',align: 'center', rowspan: 2},
                {field: 'paramNameCn', width: '15%', title: '检测项目',align: 'center', rowspan: 2},
                {field: 'schemeName', width: '40%', title: '检测标准',align: 'center', rowspan: 2},
                {field: 'schemeMoney', width: '12%', title: '测试费用（元）', align: 'center', rowspan: 2},
                {field: 'discountFee', width: '12%', title: '折后价（元）', align: 'center', rowspan: 2},
                {align: 'sampleNum', title: '样品数量（个）',align: 'center', colspan: 3},
                {field: 'subTotal', width: '12%', title: '小计（元）', align: 'center', rowspan: 2}
            ], [
                {field: 'pointNum', width: '8%', title: '点位', align: 'center'},
                {field: 'freqNum', width: '8%', title: '频次', align: 'center'},
                {field: 'dayNum', width: '8%', title: '天数周期', align: 'center'}
            ]
        ],
        page: true,
        limit: 10
    });
});