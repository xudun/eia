/**
 *Created by HSH on 2018/5/30 10:56
 */
layui.use(['jquery', 'layer', 'table', 'element'], function(){
    var $ = layui.jquery,
        layer = layui.layer,
        table = layui.table,
        element = layui.element;
    var eiaContractId = getParamByUrl(window.location.href)["eiaContractId"]
    // Tab切换
    element.on('tab(contractOfferTab)', function(data){
        console.log(data);
        var cur_index = data.index;
        tabAction(cur_index);
    });

    var tableRenderList =[
        {//出账列表配置
            cols:[[
                {fixed: 'left', type: 'numbers', title: '序号',width:'6%',align: "center",templet: "#indexTable"},
                {field:'contractNo',width:'15%', title: '合同编号',align: "center"},
                {field:'contractName',width:'25%', title: '合同名称',align: "center"},
                {field:'invoiceType',width:'15%', title: '费用类型',align: "center"},
                {field:'noteIncomeDate',width:'15%', title: '出账时间',align: "center"},
                {field:'noteIncomeMoney',width:'15%', title: '出账金额(万元)',align: "center"},
                {field:'inputUser',width:'10%', title: '录入人',align: "center"},
                {field:'lastUpdated',width:'15%', title: '出账确认时间',align: "center"},
            ]],
            tableId:"eiaInvoiceOutList",
            url:"/eia/eiaIncomeOut/eiaInvoiceOutQuery?contractId="+eiaContractId,
            toolbar: '#invoiceOutTmp',
            defaultToolbar:['filter', 'print', 'exports']
        },{//进账列表配置
            cols: [[
                {fixed: 'left', type: 'numbers', title: '序号',width:'6%',align: "center",templet: "#indexTable"},
                {field:'contractNo',width:'15%', title: '合同编号',align: "center"},
                {field:'contractName',width:'25%', title: '合同名称',align: "center"},
                {field:'noteIncomeDate',width:'15%', title: '进账时间',align: "center"},
                {field:'noteIncomeMoney',width:'15%', title: '进账金额(万元)',align: "center"},
                {field:'inputUser',width:'10%', title: '录入人',align: "center"},
                {field:'lastUpdated',width:'15%', title: '进账确认时间',align: "center"},
            ]],
            tableId:"eiaInvoiceIncomeList",
            url:"/eia/eiaIncomeOut/eiaInvoiceIncomeQuery?contractId="+eiaContractId,
            toolbar: '#invoiceIncomeTmp',
            defaultToolbar:['filter', 'print', 'exports']
        },{
            //开票列表配置
            cols: [[
                {fixed: 'left', type: 'numbers', title: '序号',width:'6%',align: "center",templet: "#indexTable"},
                {field:'contractNo',width:'15%', title: '合同编号',align: "center"},
                {field:'contractName',width:'25%', title: '合同名称',align: "center"},
                {field:'invoiceType',width:'15%', title: '费用类型',align: "center"},
                {field:'noteIncomeDate',width:'15%', title: '出账时间',align: "center"},
                {field:'noteIncomeMoney',width:'15%', title: '出账金额(万元)',align: "center"},
                {field:'inputUser',width:'10%', title: '录入人',align: "center"},
                {field:'lastUpdated',width:'15%', title: '出账确认时间',align: "center"},
            ]],
            tableId:"eiaInvoiceList",
            url:"/eia/eiaInvoice/eiaInvoiceQuery?contractId="+eiaContractId,
            toolbar: '#eiaInvoiceTmp',
            defaultToolbar:['filter', 'print', 'exports']
        },{//财务信息
            cols: [[
                {field: 'contractNo', title: '合同编号', width: '17%', rowspan: 2, align: "center"} //rowspan即纵向跨越的单元格数
                ,{field: 'contractName', title: '合同名称', width: '17%', rowspan: 2, align: "center"}
                ,{field: 'contractMoney', title: '合同金额(万元', width: '10%', rowspan: 2, align: "center"}
                ,{align: 'center', title: '进账信息', colspan: 4, align: "center"} //colspan即横跨的单元格数，这种情况下不用设置field和width
                ,{align: 'center', title: '出账信息', colspan: 3, align: "center"} //colspan即横跨的单元格数，这种情况下不用设置field和width
            ], [
                {field: 'billMoney', title: '开票金额(万元)', width: '8%', align: "center"}
                ,{field: 'noteIncomeMoney', title: '进账金额(万元)', width: '8%', align: "center"}
                ,{field: 'outstandingAmount', title: '未结清(万元)', width: '8%', align: "center"}
                ,{field: 'ifFinished', title: '是否结清', width: '8%', align: "center"},
                {field: 'expertFee', title: '专家费(万元)', width: '8%', align: "center"}
                ,{field: 'monitorFee', title: '检测费(万元)', width: '8%', align: "center"}
                ,{field: 'otherFee', title: '其他(万元)', width: '8%', align: "center"}
            ]],
            tableId:"contractFinList",
            url:"/eia/eiaInvoice/getInvoiceRevenueFinDataWorkFlow?eiaContractId="+eiaContractId,
            toolbar: '#contractFinTmp',
            defaultToolbar:['filter', 'print', 'exports']
        }
    ]

    var tableRender =function (tableRenderList) {
        for(var index in tableRenderList){
            //渲染表格
            table.render({
                id: tableRenderList[index].tableId,
                elem: '#'+tableRenderList[index].tableId,
                footer:true,
                url:tableRenderList[index].url,
                cols: tableRenderList[index].cols,
                toolbar: tableRenderList[index].toolbar,
                defaultToolbar: tableRenderList[index].defaultToolbar,
                page: false,
                even: true,
                limit: 20
            });
        }
    }

    tableRender(tableRenderList)
});