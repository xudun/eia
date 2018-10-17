layui.use(['jquery', 'layer', 'table', 'element'], function(){
    var $ = layui.jquery,
        layer = layui.layer,
        table = layui.table,
        element = layui.element;
    var contractId = parent.$("#contractId").val();
    // Tab切换
    element.on('tab(contractOfferTab)', function(data){
        console.log(data);
        var cur_index = data.index;
        tabAction(cur_index);
    });
    //参数
    var tabAction = function(tabType){
        var thExpect = [[
            {fixed: 'left', title: '序号',width:'6%',align: "center",templet: "#indexTable", rowspan: 2},
            {field: 'contractNo', title: '合同编号', width: '15%', rowspan: 2, align: "center"}, //rowspan即纵向跨越的单元格数,
            {field: 'contractName', title: '合同名称', width: '25%', rowspan: 2, align: "center"},
            {field: 'contractMoney', title: '合同金额(万元)', width: '10%', rowspan: 2, align: "center"},
            {field: 'contractMoney', title: '已开票数', width: '10%', rowspan: 2, align: "center"},
            {field: 'expectInvoiceMoney', title: '本月预计开票', width: '10%', rowspan: 2, align: "center"},
            {field: 'incomeMoney', title: '已回款', width: '15%', rowspan: 2, align: "center"},
            {field: 'expectIncomeMoney', title: '本期预计收款', width: '10%', rowspan: 2, align: "center"},
            {align: 'center', title: '截止上月累计发生业务成本', colspan: 3, align: "center"}, //colspan即横跨的单元格数，这种情况下不用设置field和width
            {align: 'center', title: '本期计划业务成本支出', colspan: 3, align: "center"} //colspan即横跨的单元格数，这种情况下不用设置field和width

        ], [
            {field: 'monitorFeeSum', title: '检测费(万元)', width: '10%', align: "center"},
            {field: 'expertFeeSum', title: '专家费(万元)', width: '10%', align: "center"},
            {field: 'otherFeeSum', title: '其他(万元)', width: '10%', align: "center"},
            {field: 'expertFee', title: '检测费(万元)', width: '10%', align: "center"},
            {field: 'monitorFee', title: '专家费(万元)', width: '10%', align: "center"},
            {field: 'otherFee', title: '其他(万元)', width: '10%', align: "center"}
        ]],
         thInvoiceOut = [[
                {fixed: 'left', title: '序号',width:'6%',align: "center",templet: "#indexTable"},
                {field:'contractNo',width:'15%', title: '合同编号',align: "center"},
                {field:'contractName',width:'25%', title: '合同名称',align: "center"},
                {field:'costTypes',width:'15%', title: '费用类型',align: "center"},
                {field:'noteIncomeDate',width:'15%', title: '出账时间',align: "center"},
                {field:'noteIncomeMoney',width:'15%', title: '出账金额(万元)',align: "center"},
                {field:'lastUpdated',width:'15%', title: '出账确认时间',align: "center"},
                {field:'inputDept',width:'15%', title: '录入部门',align: "center"},
                {field:'inputUser',width:'15%', title: '录入人',align: "center"},
            ]],
            thInvoice = [[
                {fixed: 'left', title: '序号',width:'6%',align: "center",templet: "#indexTable"},
                {field:'contractNo',width:'15%', title: '合同编号',align: "center"},
                {field:'contractName',width:'25%', title: '合同名称',align: "center"},
                {field:'noteIncomeDate',width:'15%', title: '进账时间',align: "center"},
                {field:'noteIncomeMoney',width:'15%', title: '进账金额(万元)',align: "center"},
                {field:'lastUpdated',width:'15%', title: '进账确认时间',align: "center"},
                {field:'inputDept',width:'15%', title: '录入部门',align: "center"},
                {field:'inputUser',width:'15%', title: '录入人',align: "center"},
            ]],
            thContract = [[
                {fixed: 'left', title: '序号',width:'6%',align: "center",templet: "#indexTable"},
                {field:'contractNo',width:'15%', title: '合同编号',align: "center"},
                {field:'contractName',width:'25%', title: '合同名称',align: "center"},
                {field:'accountState',width:'15%', title: '当前状态',align: "center"},
                {field:'billDate',width:'15%', title: '申请开票时间',align: "center"},
                {field:'billMoney',width:'15%', title: '开票金额(万元)',align: "center"},
                {field:'inputDept',width:'15%', title: '录入部门',align: "center"},
                {field:'inputUser',width:'15%', title: '录入人',align: "center"},
                {fixed: 'right', title: '操作',width:'15%',align: "center", toolbar: '#invoiceTool',align: "center"}
            ]];
        //底部汇总数据格式
        var tableList
        if(tabType==0){
            tableList = "eiaContractList"
            url = "/eia/eiaInvoice/eiaInvoiceQuery?contractId="+contractId
            cols = thContract;
        } else if(tabType==1){
            tableList = "eiaInvoiceList"
            url = "/eia/eiaIncomeOut/eiaInvoiceIncomeQuery?contractId="+contractId
            cols = thInvoice;
        }else if(tabType==2){
            tableList = "eiaInvoiceOutList"
            url = "/eia/eiaIncomeOut/eiaInvoiceOutQuery?contractId="+contractId
            cols = thInvoiceOut;
        }else if(tabType==3){
            tableList = "eiaAccountExpectList"
            url = "/eia/eiaAccountExpect/getEiaAccountExpectData?contractId="+contractId
            cols = thExpect;
        }

        //渲染表格
        table.render({
            id: tableList,
            elem: '#'+tableList,
            footer:true,
            url:url,
            cols: cols,
            page: true,
            even: true,
            limit: 10
        });

    };
    tabAction(0);
    //监听开票表格工具条
    table.on('tool(eiaContractList)', function (obj) {
        var data = obj.data;
        if(obj.event === 'eiaCheck'){    //查看
            var eiaInvoiceId = data.id;
            $('#eiaInvoiceId').val(eiaInvoiceId)
            var pageUrl = '../eiaInvoice/eiaInvoiceReveDetail?eiaInvoiceId='+eiaInvoiceId;
            var index = layer.open({
                title:' ',
                type: 2,
                shade: false,
                maxmin: true,
                skin: 'larry-green',
                area: ['100%', '100%'],
                content: pageUrl,
                success:function (layero, index) {
                },
                end: function () {
                },
                min: function () {
                    $(".layui-layer-title").text("查看开票");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        }
    });
});