layui.use(['jquery', 'layer', 'table', 'element', 'laydate'], function () {
    var $ = layui.jquery,
        layer = layui.layer,
        table = layui.table,
        element = layui.element,
        laydate = layui.laydate;

    var ifShowDeptProjCount = $("#ifShowDeptProjCount").val();
    var ifShowProjectFinance = $("#ifShowProjectFinance").val();
    if (!ifShowDeptProjCount && ifShowProjectFinance) {
        $(".projectCount").removeClass("layui-this");
        $(".projectFinance").addClass("layui-this");
        $(".showProjectCountList").removeClass("layui-show");
        $(".showProjectFinanceList").addClass("layui-show");
    }

    // Tab切换
    element.on('tab(projectTab)', function (data) {
        var cur_index = data.index;
        var tabName = $(data.elem).find('.layui-this').attr("tab-name");
        switch (tabName) {
            case "projectCount":
                renderProjectCountList();
                break;
            case "projectFinance":
                renderProjectFinanceList();
                break;
        }
    });

    var renderProjectCountList = function () {
        table.render({
            id: 'projectCountList',
            elem: '#projectCountList',
            url: '/eia/eiaAnalysis/getTotalProjectCount?status=root',
            toolbar: '#projectCountTmp',
            defaultToolbar:['filter', 'print', 'exports'],
            cols: [
                [
                    {field: 'orgName', width: "27.1%", title: '业务部门', event: 'enterDept', align: "center", rowspan: 2, templet: '#depTp'},
                    {field: 'projectNum', width: "10%", title: '项目个数', event: 'showProjAccList', align: "center", rowspan: 2, templet: '#projAccTp'},
                    {title: '项目进度', align: "center", colspan: 3},
                    {title: '项目审批', align: "center", colspan: 4}
                ],[
                    {field: 'projectDoing', width: "9%", title: '进行中', align: "center", event: 'showProjDoingList', templet: '#projDoingTp'},
                    {field: 'projectUnComp', width: "9%", title: '未归档', align: "center", event: 'showProjUnCompList', templet: '#projUnCompTp'},
                    {field: 'projectComp', width: "9%", title: '已归档', align: "center", event: 'showProjCompList', templet: '#projCompTp'},
                    {field: 'ysNum', width: "9%", title: '一审', align: "center", event: 'showYsProList', templet: '#ysProjTp'},
                    {field: 'esNum', width: "9%", title: '二审/审核', align: "center", event: 'showEsProList', templet: '#esProjTp'},
                    {field: 'ssNum', width: "9%", title: '三审/审定', align: "center", event: 'showSsProList', templet: '#ssProjTp'},
                    {field: 'nsNum', width: "9%", title: '内部审查', align: "center", event: 'showNsProList', templet: '#nsProjTp'}
                ]
            ],
            done: function (res, curr, count) {
                laydate.render({
                    elem: '#startDate'
                });
                laydate.render({
                    elem: '#endDate'
                });
                if (res.data[0].ifRoot) {
                    $('#projectCountList').next().find('.layui-table-main tr').attr('nextDo', 'open').eq(0).addClass('thisRoot');
                } else {
                    $('#projectCountList').next().find('.layui-table-main tr').attr('nextDo', 'open').eq(0).attr('nextDo', 'close');
                }
                $('#startDate').val($('#startTime').val());
                $('#endDate').val($('#endTime').val());
            }
        });
    };

    var renderProjectFinanceList = function () {
        table.render({
            id: 'projectFinanceList',
            elem: '#projectFinanceList',
            footer: true,
            url: '/eia/eiaAnalysis/getProjectAccountDataList',
            toolbar: '#financeTmp',
            defaultToolbar: ['filter', 'print', 'exports'],
            cols: [[
                {fixed: 'left', title: '序号', width: '4%', align: "center", templet: "#indexTable", rowspan: 2},
                {field: 'projectName', title: '项目名称', width: '25%', rowspan: 2, align: "center"},
                {field: 'projectNo', title: '项目编号', width: '13.3%', rowspan: 2, align: "center"},
                {field: 'nodesName', title: '项目进度', width: '10%', rowspan: 2, align: "center"},
                {align: 'center', title: '进账信息', colspan: 2, align: "center"},
                {align: 'center', title: '出账信息', colspan: 3, align: "center"},
                {field: 'profitMargin', title: '合同利润率', width: '8%', rowspan: 2, align: "center"}
            ], [
                {field: 'billMoney', title: '开票金额(万元)', width: '8%', align: "center"},
                {field: 'noteIncomeMoney', title: '进账金额(万元)', width: '8%', align: "center"},
                {field: 'expertFee', title: '专家费(万元)', width: '8%', align: "center"},
                {field: 'monitorFee', title: '检测费(万元)', width: '8%', align: "center"},
                {field: 'otherFee', title: '其他(万元)', width: '8%', align: "center"}
            ]],
            page: true,
            even: true,
            limit: 10
        });
    };

    if (!ifShowDeptProjCount && ifShowProjectFinance) {
        renderProjectFinanceList();
    } else {
        renderProjectCountList();
    }

    table.on('tool(projectCountList)', function (obj) {
        var data = obj.data;
        var inputUserId = data.id;
        var inputDeptId = data.parentOrgId;
        var startDate = $("#startDate").val();
        var endDate = $("#endDate").val();
        var tr = obj.tr; //获得当前行 tr 的DOM对象
        if (obj.event === 'enterDept') {
            if (!data.ifStaff && !data.ifRoot) {
                var nextDo = $(tr).attr('nextdo');
                var depId = data.id;
                var startDate = $("#startDate").val();
                var endDate = $("#endDate").val();
                //刷新表格数据，改变参数
                layui.table.reload("projectCountList", {
                    url: '/eia/eiaAnalysis/getTotalProjectCount?id=' + depId + "&status=" + nextDo + "&startDate=" + startDate + "&endDate=" + endDate
                });
            }
        } else if (obj.event == 'showProjAccList') {
            if (data.ifStaff == true && data.projectNum > 0) {
                var pageUrl = '/eia/eiaAnalysis/eiaProjectAccount?inputUserId=' + inputUserId + '&inputDeptId=' + inputDeptId;
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
                        $(".layui-layer-title").text("项目台账列表");
                    },
                    restore: function () {
                        $(".layui-layer-title").text(" ");
                    }
                });
            }
        } else if (obj.event == 'showYsProList') {
            if (data.ifStaff == true && data.ysNum > 0) {
                var pageUrl = '/eia/eiaAnalysis/eiaProjectList?startDate=' + startDate + '&endDate=' + endDate + '&inputUserId=' + inputUserId + '&inputDeptId=' + inputDeptId + '&viewType=ysProject';
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
                        $(".layui-layer-title").text("一审项目列表");
                    },
                    restore: function () {
                        $(".layui-layer-title").text(" ");
                    }
                });
            }
        } else if (obj.event == 'showEsProList') {
            if (data.ifStaff == true && data.esNum > 0) {
                var pageUrl = '/eia/eiaAnalysis/eiaProjectList?startDate=' + startDate + '&endDate=' + endDate + '&inputUserId=' + inputUserId + '&inputDeptId=' + inputDeptId + '&viewType=esProject';
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
                        $(".layui-layer-title").text("二审项目列表");
                    },
                    restore: function () {
                        $(".layui-layer-title").text(" ");
                    }
                });
            }
        } else if (obj.event == 'showSsProList') {
            if (data.ifStaff == true && data.ssNum > 0) {
                var pageUrl = '/eia/eiaAnalysis/eiaProjectList?startDate=' + startDate + '&endDate=' + endDate + '&inputUserId=' + inputUserId + '&inputDeptId=' + inputDeptId + '&viewType=ssProject';
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
                        $(".layui-layer-title").text("三审项目列表");
                    },
                    restore: function () {
                        $(".layui-layer-title").text(" ");
                    }
                });
            }
        }else if (obj.event == 'showNsProList') {
            if (data.ifStaff == true && data.ssNum > 0) {
                var pageUrl = '/eia/eiaAnalysis/eiaProjectList?startDate=' + startDate + '&endDate=' + endDate + '&inputUserId=' + inputUserId + '&inputDeptId=' + inputDeptId + '&viewType=nsProject';
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
                        $(".layui-layer-title").text("内审项目列表");
                    },
                    restore: function () {
                        $(".layui-layer-title").text(" ");
                    }
                });
            }
        }
        else if (obj.event == 'showProjDoingList') {
            if (data.ifStaff == true && data.projectDoing > 0) {
                var pageUrl = '/eia/eiaAnalysis/eiaProjectList?startDate=' + startDate + '&endDate=' + endDate + '&inputUserId=' + inputUserId + '&inputDeptId=' + inputDeptId + '&viewType=projectDoing';
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
                        $(".layui-layer-title").text("进行中项目列表");
                    },
                    restore: function () {
                        $(".layui-layer-title").text(" ");
                    }
                });
            }
        } else if (obj.event == 'showProjUnCompList') {
            if (data.ifStaff == true && data.projectUnComp > 0) {
                var pageUrl = '/eia/eiaAnalysis/eiaProjectList?startDate=' + startDate + '&endDate=' + endDate + '&inputUserId=' + inputUserId + '&inputDeptId=' + inputDeptId + '&viewType=projectUnComp';
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
                        $(".layui-layer-title").text("未归档项目列表");
                    },
                    restore: function () {
                        $(".layui-layer-title").text(" ");
                    }
                });
            }
        } else if (obj.event == 'showProjCompList') {
            if (data.ifStaff == true && data.projectComp > 0) {
                var pageUrl = '/eia/eiaAnalysis/eiaProjectList?startDate=' + startDate + '&endDate=' + endDate + '&inputUserId=' + inputUserId + '&inputDeptId=' + inputDeptId + '&viewType=projectComp';
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
                        $(".layui-layer-title").text("未归档项目列表");
                    },
                    restore: function () {
                        $(".layui-layer-title").text(" ");
                    }
                });
            }
        }
    });

    //监听头部工具栏事件
    //监听事件
    table.on('toolbar(projectCountList)', function(obj){
        switch(obj.event){
            case 'getSelect':
                var startDate = $("#startDate").val();
                var endDate = $("#endDate").val();
                $('#startTime').val(startDate);
                $('#endTime').val(endDate);
                layui.table.reload("projectCountList", {
                    url: "/eia/eiaAnalysis/getTotalProjectCount?status=root&startDate=" + startDate + "&endDate=" + endDate
                });
                break;
        }
    });

    //监听头部工具栏事件
    //监听事件
    table.on('toolbar(projectFinanceList)', function (obj) {
        switch (obj.event) {
            case 'getSelect':
                var projectName = $("#projectName").val();
                table.reload('projectFinanceList', {
                    where: {
                        projectName: projectName
                    }
                });
                break;
        }
    });
});