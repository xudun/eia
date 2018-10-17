layui.use(['jquery', 'layer', 'table', 'element'], function () {
    var $ = layui.jquery,
        layer = layui.layer,
        table = layui.table,
        element = layui.element;

    var inputUserId = getParamFromUrl(document.location.href, "inputUserId");
    if (!inputUserId) {
        inputUserId = "";
    }
    var inputDeptId = getParamFromUrl(document.location.href, "inputDeptId");
    if (!inputDeptId) {
        inputDeptId = "";
    }

    //渲染表格
    table.render({
        id: 'eiaProjectList',
        elem: '#eiaProjectList',
        footer: true,
        url: '/eia/eiaAnalysis/getProjectAccountDataList?inputUserId=' + inputUserId + '&inputDeptId=' + inputDeptId,
        toolbar: '#tableTopTmp',
        defaultToolbar: ['filter', 'print', 'exports'],
        cols: [[
            {fixed: 'left', title: '序号', width: '6%', align: "center", templet: "#indexTable", rowspan: 2},
            {field: 'projectName', title: '项目名称', width: '25%', rowspan: 2, align: "center"},
            {field: 'projectNo', title: '项目编号', width: '15%', rowspan: 2, align: "center"}, //rowspan即纵向跨越的单元格数
            {field: 'nodesName', title: '项目进度', width: '10%', rowspan: 2, align: "center"},
            {field: 'ifLabTest', title: '监测详情', width: '10%', rowspan: 2, align: "center", templet: '#yxTestTp', event: 'showLabOfferList'},
            {align: 'center', title: '进账信息', colspan: 2, align: "center"}, //colspan即横跨的单元格数，这种情况下不用设置field和width
            {align: 'center', title: '出账信息', colspan: 3, align: "center"},
            {field: 'profitMargin', title: '合同利润率', width: '8%', rowspan: 2, align: "center"},
            // {fixed: 'right', title: '操作', width: '15%', align: "center", toolbar: '#contractTool', align: "center"}
        ], [
            {field: 'billMoney', title: '开票金额(万元)', width: '8%', align: "center"},
            {field: 'noteIncomeMoney', title: '进账金额(万元)', width: '8%', align: "center"},
            {field: 'expertFee', title: '专家费(万元)', width: '8%', align: "center"},
            {field: 'monitorFee', title: '检测费(万元)', width: '8%', align: "center"},
            {field: 'otherFee', title: '其他(万元)', width: '8%', align: "center"}
        ]],
        page: true,
        even: true,
        limit: 10,
        done: function (res, curr, count) {
      /*      console.log('res:');
            console.log(res);
            console.log("this");
            console.log(this);
                 //添加小计，总计
                 var count_fields = [ 'billMoney', 'noteIncomeMoney', 'expertFee', 'monitorFee', 'otherFee'];
                 //加入小计
                 tableAddSubTotalRow(this, res, count_fields);*/
        }
    });


    //监听合同表格工具条
    table.on('tool(eiaProjectList)', function (obj) {
        var data = obj.data;
        if (obj.event === 'showLabOfferList') {    //查看监测方案列表
            var pageUrl = '/eia/eiaLabOffer/eiaLabOfferIndex?eiaProjectId=' + data.id;
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
                },
                min: function () {
                    $(".layui-layer-title").text("监测方案列表");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        }
    });

    //查询、新增按钮（报价、合同）
    $('.larry-btn a.layui-btn').click(function () {
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });
    var active = {
        contractAdd: function () {    //进账新增
            pageUrl = '/eia/eiaIncomeOut/eiaInvoiceIncomeCreate';
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

                },
                min: function () {

                },
                restore: function () {

                }
            });
        }
    }

    //监听头部工具栏事件
    //监听事件
    table.on('toolbar(eiaProjectList)', function (obj) {
        switch (obj.event) {
            case 'getSelect':
                var projectName = $("#projectName").val();
                table.reload('eiaProjectList', {
                    where: {
                        projectName: projectName
                    }
                });
                break;
        }
    });

});