layui.use(['jquery', 'layer', 'table', 'laydate', 'element'], function () {
    var $ = layui.jquery,
        layer = layui.layer,
        table = layui.table,
        laydate = layui.laydate,
        element = layui.element;

    var contractDate = $("#contractDate").val();
    var contractBusDate = $("#contractBusDate").val();
    var ifShowConFin = $("#ifShowConFin").val();
    var ifShowProTypeFin = $("#ifShowProTypeFin").val();
    if (!ifShowConFin && ifShowProTypeFin) {
        $(".conFin").removeClass("layui-this");
        $(".proTypeFin").addClass("layui-this");
        $(".showConFinList").removeClass("layui-show");
        $(".showProTypeFinList").addClass("layui-show");
    }

    // Tab切换
    element.on('tab(contractTab)', function (data) {
        var cur_index = data.index;
        var tabName = $(data.elem).find('.layui-this').attr("tab-name");
        switch (tabName) {
            case "conFin":
                renderConFinList();
                break;
            case "proTypeFin":
                renderProTypeFinList();
                break;
        }
    });

    var renderConFinList = function () {
        table.render({
            id: 'conFinList',
            elem: '#conFinList',
            url: '/eia/eiaAnalysis/getTotalMoney?status=root&contractDate=' + contractDate,
            toolbar: '#confinaTmp',
            defaultToolbar: ['filter', 'print', 'exports'],
            cols: [[
                {field: 'orgName', width: "21%", title: '业务部门', event: 'enterDept', align: "center", templet: '#depTp', rowspan: 2},
                {field: 'contractCount', width: "10%", title: '合同个数', event: 'showContractList', templet: '#contractTp', align: "center", rowspan: 2},
                {align: 'center', title: '合同进度', colspan: 2, align: "center"},
                {field: 'contractMoneySum', width: "15%", title: '合同总额(万元)', align: "center", rowspan: 2},
                {field: 'contractMoney', width: "15%", title: '已签合同额(万元)', align: "center", rowspan: 2},
                {field: 'offerMoney', width: "15%", title: '已报价金额(万元)', align: "center", rowspan: 2},
                {field: 'profitMargin', width: "10%", title: '利润率', align: "center", rowspan: 2}
            ], [
                {field: 'unfinCount', width: "7%", title: '未归档', templet: '#conUnFinTp', event: 'showUnFinConList', align: "center"},
                {field: 'finCount', width: "7%", title: '已归档', templet: '#conFinTp', event: 'showFinConList', align: "center"},
            ]],
            done: function (res, curr, count) {
                laydate.render({
                    elem: '#deptStartDate'
                });
                laydate.render({
                    elem: '#deptEndDate'
                });
                if (res.data[0].ifRoot) {
                    $('#conFinList').next().find('.layui-table-main tr').attr('nextDo', 'open').eq(0).addClass('thisRoot');
                } else {
                    $('#conFinList').next().find('.layui-table-main tr').attr('nextDo', 'open').eq(0).attr('nextDo', 'close');
                }
                $('#deptStartDate').val($('#dasStartTime').val());
                $('#deptEndDate').val($('#dasEndTime').val());
            }
        });
    };


    var renderProTypeFinList = function () {
        table.render({
            id: 'proTypeFinList',
            elem: '#proTypeFinList',
            toolbar: '#proTypeFinTmp',
            defaultToolbar: ['filter', 'print', 'exports'],
            url: '/eia/eiaAnalysis/getBusiTypeCountMoney?status=close&contractDate=' + contractBusDate,
            cols: [[
                {field: 'codeDesc', minWidth: '300', title: '业务类型', event: 'enterType', align: "center", templet: '#proTypeTp'},
                {field: 'contractMoney', minWidth: '300', title: '合同总额(万元)', align: "center"},
                {field: 'signMoney', minWidth: '300', title: '已签合同额(万元)', align: "center"},
                {field: 'offerMoney', minWidth: '300', title: '报价金额(万元)', align: "center"}
            ]],
            done: function (res, curr, count) {
                laydate.render({
                    elem: '#busiStartDate'
                });
                laydate.render({
                    elem: '#busiEndDate'
                });
                if (res.data[0].ifRoot) {
                    $('#proTypeFinList').next().find('.layui-table-main tr').attr('nextDo', 'open');
                } else {
                    $('#proTypeFinList').next().find('.layui-table-main tr').attr('nextDo', 'open').eq(0).attr('nextDo', 'close');
                }
                $('#busiStartDate').val($('#proStartTime').val());
                $('#busiEndDate').val($('#proEndTime').val());
                $('#inputDept').val($('#proOrgName').val());
            }
        });
    };
    if (!ifShowConFin && ifShowProTypeFin) {
        renderProTypeFinList();
    } else {
        renderConFinList();
    }

    //监听部门业务统计表格单元格（部门）事件   （openState：0：未展开；1：展开）
    table.on('tool(conFinList)', function (obj) {
        var data = obj.data;
        var busiStartDate = $('#busiStartDate').val()
        var busiEndDate = $('#busiEndDate').val()
        var inputUserId = data.id;
        var inputOrgId = data.parentNodeId
        var tr = obj.tr; //获得当前行 tr 的DOM对象
        if (obj.event === 'enterDept') {
            if (!data.ifStaff && !data.ifRoot) {
                var nextDo = $(tr).attr('nextdo');
                var depId = data.id;
                var deptStartDate = $("#deptStartDate").val();
                var deptEndDate = $("#deptEndDate").val();
                //刷新表格数据，改变参数
                layui.table.reload("conFinList", {
                    url: '/eia/eiaAnalysis/getTotalMoney?id=' + depId + "&status=" + nextDo + "&deptStartDate=" + deptStartDate + "&deptEndDate=" + deptEndDate
                });
            }
        }
        else if (obj.event == 'showContractList') {
            if (data.ifStaff == true) {
                var pageUrl = '/eia/eiaAnalysis/eiaContractIndex?inputUserId=' + inputUserId + '&inputDeptId=' + inputOrgId + '&deptStartDate=' + deptStartDate + '&deptEndDate=' + deptEndDate;
                var index = layer.open({
                    title: " ",
                    type: 2,
                    shade: false,
                    maxmin: true,
                    skin: 'larry-green',
                    area: ['100%', '100%'],
                    content: pageUrl,
                    success: function (layero, index) {
                    },
                    min: function () {
                        $(".layui-layer-title").text("合同列表");
                    },
                    restore: function () {
                        $(".layui-layer-title").text(" ");
                    }
                });
            }
        }
        else if (obj.event == 'showFinConList') {
            if (data.ifStaff == true) {
                var pageUrl = '/eia/eiaAnalysis/eiaContractIndex?contractBusi=end&inputUserId=' + inputUserId + '&inputDeptId=' + inputOrgId + '&deptStartDate=' + busiStartDate + '&deptEndDate=' + busiEndDate;
                var index = layer.open({
                    title: " ",
                    type: 2,
                    shade: false,
                    maxmin: true,
                    skin: 'larry-green',
                    area: ['100%', '100%'],
                    content: pageUrl,
                    success: function (layero, index) {
                    },
                    min: function () {
                        $(".layui-layer-title").text("合同列表");
                    },
                    restore: function () {
                        $(".layui-layer-title").text(" ");
                    }
                });
            }
        }
        else if (obj.event == 'showUnFinConList') {
            if (data.ifStaff == true) {
                var pageUrl = '/eia/eiaAnalysis/eiaContractIndex?contractBusi=doing&inputUserId=' + inputUserId + '&inputDeptId=' + inputOrgId + '&deptStartDate=' + deptStartDate + '&deptEndDate=' + deptEndDate;
                var index = layer.open({
                    title: " ",
                    type: 2,
                    shade: false,
                    maxmin: true,
                    skin: 'larry-green',
                    area: ['100%', '100%'],
                    content: pageUrl,
                    success: function (layero, index) {
                    },
                    min: function () {
                        $(".layui-layer-title").text("合同列表");
                    },
                    restore: function () {
                        $(".layui-layer-title").text(" ");
                    }
                });
            }
        }
    });

    //监听业务类型统计表格单元格（业务类型）事件   （openState：0：未展开；1：展开）
    table.on('tool(proTypeFinList)', function (obj) {
        var data = obj.data;
        var tr = obj.tr; //获得当前行 tr 的DOM对象
        if (obj.event === 'enterType') {
            if (data.ifParent && !data.ifRoot) {
                var nextDo = $(tr).attr('nextdo');
                var code = data.code;
                var busiStartDate = $("#busiStartDate").val();
                var busiEndDate = $("#busiEndDate").val();
                var inputDept = $("#inputDept").val();
                //刷新表格数据，改变参数
                layui.table.reload("proTypeFinList", {
                    url: '/eia/eiaAnalysis/getBusiTypeCountMoney?status=' + nextDo + "&code=" + code + "&busiStartDate=" + busiStartDate + "&busiEndDate=" + busiEndDate + "&inputDept=" + inputDept
                });
            }
        }
    });

    //监听头部工具栏事件
    //监听事件
    table.on('toolbar(conFinList)', function (obj) {
        switch (obj.event) {
            case 'getSelect':
                var deptStartDate = $("#deptStartDate").val();
                var deptEndDate = $("#deptEndDate").val();
                $('#dasStartTime').val(deptStartDate);
                $('#dasEndTime').val(deptEndDate);
                //刷新表格数据，改变参数
                layui.table.reload("conFinList", {
                    url: '/eia/eiaAnalysis/getTotalMoney?status=root&deptStartDate=' + deptStartDate + "&deptEndDate=" + deptEndDate
                });
                break
        }
    });

    //监听头部工具栏事件
    //监听事件
    table.on('toolbar(proTypeFinList)', function (obj) {
        switch (obj.event) {
            case 'getSelect':
                var busiStartDate = $("#busiStartDate").val();
                var busiEndDate = $("#busiEndDate").val();
                var inputDept = $("#inputDept").val();
                $('#proStartTime').val(busiStartDate);
                $('#proEndTime').val(busiEndDate);
                $('#proOrgName').val(inputDept);
                table.reload('proTypeFinList', {
                    url: '/eia/eiaAnalysis/getBusiTypeCountMoney?status=close&busiStartDate=' + busiStartDate + "&busiEndDate=" + busiEndDate
                    + "&inputDept=" + inputDept
                });
                break
        }
    })
});