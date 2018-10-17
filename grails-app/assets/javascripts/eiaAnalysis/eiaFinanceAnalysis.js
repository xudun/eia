layui.use(['jquery', 'layer', 'table', 'laydate', 'element'], function () {
    var $ = layui.jquery,
        layer = layui.layer,
        table = layui.table,
        laydate = layui.laydate,
        element = layui.element;

    var contractDate = $("#contractDate").val();
    var ifShowCissAndStaff = $("#ifShowCissAndStaff").val();
    if (!ifShowCissAndStaff) {
        $(".cissAndStaff").removeClass("layui-this");
        $(".invoiceIncome").addClass("layui-this");
        $(".showCissAndStaffList").removeClass("layui-show");
        $(".showEiaInvoiceList").addClass("layui-show");
    }

    // Tab切换
    element.on('tab(financeTab)', function (data) {
        var cur_index = data.index;
        var tabName = $(data.elem).find('.layui-this').attr("tab-name");
        switch (tabName) {
            case "cissAndStaff":
                rendercissAndStaffList();
                break;
            case "invoiceIncome":
                renderInvoiceList();
                break;
            case "eiaAccountExpect":
                renderExceptList();
                break;
        }
    });

    var rendercissAndStaffList = function () {
        table.render({
            id: 'cissAndStaffList',
            elem: '#cissAndStaffList',
            toolbar: '#cissTmp',
            defaultToolbar:['filter', 'print', 'exports'],
            url: '/eia/eiaAnalysis/getEiaInvoice?status=root&contractDate=' + contractDate,
            cols: [[
                {field: 'orgName', width: "22.9%", title: '业务部门', event: 'enterDept', align: "center", templet: '#depTp'},
                {field: 'contractMoney', width: "11%", title: '已签合同额(万元)', align: "center"},
                {field: 'billMoney', width: "11%", title: '开票金额(万元)', align: "center"},
                {field: 'invoiceIncomeSum', width: "11%", title: '进账金额(万元)', align: "center"},
                {field: 'outstandingAmountSum', width: "11%", title: '未结清(万元)', align: "center"},
                {field: 'expertFeeSum', width: "11.1%", title: '专家费(万元)', align: "center"},
                {field: 'monitorFeeSum', width: "11.1%", title: '检测费(万元)', align: "center"},
                {field: 'otherFeeSum', width: "11.1%", title: '其他(万元)', align: "center"}
            ]],
            done: function (res, curr, count) {
                laydate.render({
                    elem: '#cissStartDate'
                });
                laydate.render({
                    elem: '#cissEndDate'
                });
                if (res.data[0].ifRoot) {
                    $('#cissAndStaffList').next().find('.layui-table-main tr').attr('nextDo', 'open').eq(0).addClass('thisRoot');
                } else {
                    $('#cissAndStaffList').next().find('.layui-table-main tr').attr('nextDo', 'open').eq(0).attr('nextDo', 'close');
                }
                $('#cissStartDate').val($('#cissStartTime').val());
                $('#cissEndDate').val($('#cissEndTime').val());
            }
        });
    };

    var renderInvoiceList = function () {
        table.render({
            id: 'eiaInvoiceList',
            elem: '#eiaInvoiceList',
            url: '/eia/eiaAnalysis/getEiaInvoiceIncomeDataList',
            toolbar: '#invoiceTmp',
            defaultToolbar:['filter', 'print', 'exports'],
            cols: [[{fixed: 'left', title: '序号', width: '4%', align: "center", templet: "#indexTable"},
                {field: 'contractNo', width: '10%', title: '合同编号', align: "center"},
                {field: 'contractName', width: '23%', title: '合同名称', align: "center"},
                {field: 'contractMoney', width: '8%', title: '合同金额(万元)', align: "center"},
                {field: 'billMoney', width: '8%', title: '开票金额(万元)', align: "center"},
                {field: 'billDate', width: '8%', title: '开票日期', align: "center"},
                {field: 'noteIncomeMoney', width: '8%', title: '进账金额(万元)', align: "center"},
                {field: 'outstandingAmount', width: '9%', title: '未进账金额(万元)', align: "center"},
                {field: 'inputDept', width: '11%', title: '录入部门', align: "center"},
                {field: 'inputUser', width: '5%', title: '录入人', align: "center"},
                {field: 'incomeDay', width: '6.4%', title: '未进账天数', align: "center"}
            ]],
            page: true,
            even: true,
            footer: true,
            limit: 10,
            done: function (res, curr, count) {
                $("#invoiceNums").html(res.count);
                //添加小计，总计
                var count_fields = ['contractMoney', 'billMoney', 'noteIncomeMoney', 'outstandingAmount'];
                //加入小计
                tableAddSubTotalRow(this, res, count_fields);
                $('#invoiceTitle').val($('#invoiceOrgName').val());
            }

        });
    };

    var renderExceptList = function () {
        table.render({
            id: 'eiaExpectList',
            elem: '#eiaExpectList',
            toolbar: '#expectTmp',
            url: '/eia/eiaAnalysis/getEiaAccountExpectDataList?status=root&contractDate=' + contractDate,
            defaultToolbar:['filter', 'print', 'exports'],
            cols:  [
                [
                    {field: 'orgName', width: "17.4%", title: '业务部门', event: 'enterDept', rowspan: 2, align: "center", templet: '#depTp'},
                    {field: 'expectInvoiceAlread', title: '已开票金额(万元)', width: '9%', rowspan: 2, align: "center"},
                    {field: 'expectInvoiceMoney', title: '本月预计开票', width: '9%', rowspan: 2, align: "center"},
                    {field: 'incomeMoney', title: '已回款', width: '8%', rowspan: 2, align: "center"},
                    {field: 'expectIncomeMoney', title: '本期预计收款', width: '9%', rowspan: 2, align: "center"},
                    {align: 'center', title: '累计发生业务成本', colspan: 3, align: "center"},
                    {align: 'center', title: '计划业务成本支出', colspan: 3, align: "center"}
                ], [
                    {field: 'monitorFeeSum', title: '检测费(万元)', width: '8%', align: "center"},
                    {field: 'expertFeeSum', title: '专家费(万元)', width: '8%', align: "center"},
                    {field: 'otherFeeSum', title: '其他(万元)', width: '8%', align: "center"},
                    {field: 'monitorFee', title: '检测费(万元)', width: '8%', align: "center"},
                    {field: 'expertFee', title: '专家费(万元)', width: '8%', align: "center"},
                    {field: 'otherFee', title: '其他(万元)', width: '8%', align: "center"}
                ]
            ],
            done: function (res, curr, count) {
                laydate.render({
                    elem: '#expectStartPeriod',
                    type: 'month'
                });
                laydate.render({
                    elem: '#expectEndPeriod',
                    type: 'month'
                });
                if (res.data[0].ifRoot) {
                    $('#eiaExpectList').next().find('.layui-table-main tr').attr('nextDo', 'open').eq(0).addClass('thisRoot');
                } else {
                    $('#eiaExpectList').next().find('.layui-table-main tr').attr('nextDo', 'open').eq(0).attr('nextDo', 'close');
                }
                $('#expectStartPeriod').val($('#proExpectStartPeriod').val());
                $('#expectEndPeriod').val($('#proExpectEndPeriod').val());
            }
        });
    };

    if (!ifShowCissAndStaff) {
        renderInvoiceList();
    } else {
        rendercissAndStaffList();
    }

    /****开票未进账***/
    ajaxBox('/eia/eiaAnalysis/getEiaInvoiceIncomeDataList', null, function (res) {
        if (res.code === 0) {
            $("#invoiceNums").html(res.count)
        }
    });

    //监听部门财务进出帐统计表格单元格（部门）事件   （openState：0：未展开；1：展开）
    table.on('tool(cissAndStaffList)', function (obj) {
        var data = obj.data;
        var tr = obj.tr; //获得当前行 tr 的DOM对象
        if (obj.event === 'enterDept') {
            if (!data.ifStaff && !data.ifRoot) {
                var nextDo = $(tr).attr('nextdo');
                var depId = data.id;
                var cissStartDate = $("#cissStartDate").val();
                var cissEndDate = $("#cissEndDate").val();
                //刷新表格数据，改变参数
                layui.table.reload("cissAndStaffList", {
                    url: '/eia/eiaAnalysis/getEiaInvoice?id=' + depId + "&status=" + nextDo + "&cissStartDate=" + cissStartDate + "&cissEndDate=" + cissEndDate
                });
            }
        }
    });

    //监听部门财务预计表格单元格（部门）事件   （openState：0：未展开；1：展开）
    table.on('tool(eiaExpectList)', function (obj) {
        var data = obj.data;
        var tr = obj.tr; //获得当前行 tr 的DOM对象
        if (obj.event === 'enterDept') {
            if (!data.ifStaff && !data.ifRoot) {
                var nextDo = $(tr).attr('nextdo');
                var depId = data.id;
                //刷新表格数据，改变参数
                layui.table.reload("eiaExpectList", {
                    url: '/eia/eiaAnalysis/getEiaAccountExpectDataList?id=' + depId + "&status=" + nextDo
                });
            }
        }
    });

    //监听头部工具栏事件
    //监听事件
    table.on('toolbar(cissAndStaffList)', function(obj){
        switch(obj.event){
            case 'getSelect':
                var cissStartDate = $("#cissStartDate").val();
                var cissEndDate = $("#cissEndDate").val();
                $('#cissStartTime').val(cissStartDate);
                $('#cissEndTime').val(cissEndDate);
                //刷新表格数据，改变参数
                layui.table.reload("cissAndStaffList", {
                    url: '/eia/eiaAnalysis/getEiaInvoice?status=root&cissStartDate=' + cissStartDate + "&cissEndDate=" + cissEndDate
                });
                break
        }
    });

    //监听头部工具栏事件
    //监听事件
    table.on('toolbar(eiaInvoiceList)', function(obj){
        switch(obj.event){
            case 'getSelect':
                var contractName = $("#invoiceTitle").val();
                $('#invoiceOrgName').val(contractName);
                table.reload('eiaInvoiceList', {
                    where: {
                        contractName: contractName
                    }
                });
                break
        }
    });

    //监听头部工具栏事件
    //监听事件
    table.on('toolbar(eiaExpectList)', function(obj){
        switch(obj.event){
            case 'getSelect':
                var expectStartPeriod = $("#expectStartPeriod").val();
                var expectEndPeriod = $("#expectEndPeriod").val();
                $('#proExpectStartPeriod').val(expectStartPeriod);
                $('#proExpectEndPeriod').val(expectEndPeriod);
                table.reload('eiaExpectList', {
                    where: {
                        expectStartPeriod: expectStartPeriod,
                        expectEndPeriod: expectEndPeriod
                    }
                });
                break
        }
    });
});