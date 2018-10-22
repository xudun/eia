layui.use(['jquery', 'layer', 'table', 'element', 'laydate'], function () {
    var $ = layui.jquery,
        layer = layui.layer,
        element = layui.element,
        laydate = layui.laydate,
        table = layui.table;

    var myDate = new Date(),
        year = myDate.getFullYear(),
        month = myDate.getMonth() + 1,
        day = myDate.getDate(),
        time = myDate.toLocaleTimeString();
    $('#weather').html('您好，现在时间是：' + year + '年' + month + '月' + day + '日 ' + time);

    //四个tab的列表渲染方法
    var renderWorkFlowContractList = function (tabName) {
        table.render({
            id: 'eiaContractList',
            elem: '#eiaContractList',
            url: '/eia/eiaBoard/getEiaWorkFlowBusiDataList?tabName=' + tabName,
            toolbar: '#tableTopTmp1',
            defaultToolbar:['filter', 'print', 'exports'],
            cols: [[{fixed: 'left', title: '序号', width: '6%', align: "center", templet: "#indexTable"},
                {field: 'workFlowTitle', width: '25%', title: '审批标题', align: "center"},
                {field: 'workFlowName', width: '25%', title: '流程名称', align: "center"},
                {field: 'nodesName', width: '15%', title: '当前节点名称', align: "center"},
                {field: 'inputUser', width: '15%', title: '创建人', align: "center"},
                {field: 'dateCreated', width: '14%', title: '创建日期', align: "center"},
                {
                    fixed: 'right',
                    title: '操作',
                    width: '10%',
                    align: "center",
                    toolbar: '#eiaWorkFlowListTool',
                    align: "center"
                }
            ]],
            page: true,
            even: true,
            limit: 10,
            done: function (res, curr, count) {
                $("#eiaContractNums").html(res.count);
                $('#workFlowContractTitle').val($('#proWorkFlowContractTitle').val());
            }
        });
    };
    var renderWorkFlowProjectList = function (tabName) {
        table.render({
            id: 'eiaProjectList',
            elem: '#eiaProjectList',
            url: '/eia/eiaBoard/getEiaWorkFlowBusiDataList?tabName=' + tabName,
            toolbar: '#tableTopTmp3',
            defaultToolbar:['filter', 'print', 'exports'],
            cols: [[{fixed: 'left', title: '序号', width: '6%', align: "center", templet: "#indexTable"},
                {field: 'workFlowTitle', width: '25%', title: '审批标题', align: "center"},
                {field: 'workFlowName', width: '25%', title: '流程名称', align: "center"},
                {field: 'nodesName', width: '15%', title: '当前节点名称', align: "center"},
                {field: 'inputUser', width: '15%', title: '创建人', align: "center"},
                {field: 'dateCreated', width: '14%', title: '创建日期', align: "center"},
                {
                    fixed: 'right',
                    title: '操作',
                    width: '10%',
                    align: "center",
                    toolbar: '#eiaWorkFlowListTool',
                    align: "center"
                }
            ]],
            page: true,
            even: true,
            limit: 10,
            done: function (res, curr, count) {
                $("#eiaProjectNums").html(res.count);
                $('#workFlowProjectTitle').val($('#proWorkFlowProjectTitle').val());
            }
        });
    };
    var renderWorkFlowCertList = function (tabName) {
        table.render({
            id: 'eiaCertList',
            elem: '#eiaCertList',
            toolbar: '#tableTopTmp4',
            url: '/eia/eiaBoard/getEiaWorkFlowBusiDataList?tabName=' + tabName,
            defaultToolbar:['filter', 'print', 'exports'],
            cols: [[{fixed: 'left', title: '序号', width: '6%', align: "center", templet: "#indexTable"},
                {field: 'workFlowTitle', width: '25%', title: '审批标题', align: "center"},
                {field: 'workFlowName', width: '25%', title: '流程名称', align: "center"},
                {field: 'nodesName', width: '15%', title: '当前节点名称', align: "center"},
                {field: 'inputUser', width: '15%', title: '创建人', align: "center"},
                {field: 'dateCreated', width: '14%', title: '创建日期', align: "center"},
                {
                    fixed: 'right',
                    title: '操作',
                    width: '10%',
                    align: "center",
                    toolbar: '#eiaWorkFlowListTool',
                    align: "center"
                }
            ]],
            page: true,
            even: true,
            limit: 10,
            done: function (res, curr, count) {
                $("#eiaCertNums").html(res.count);
                $('#workFlowCertTitle').val($('#proWorkFlowCertTitle').val());
            }
        });
    };
    var renderWorkFlowStampList = function (tabName) {
        table.render({
            id: 'eiaStampList',
            elem: '#eiaStampList',
            url: '/eia/eiaBoard/getEiaWorkFlowBusiDataList?tabName=' + tabName,
            toolbar: '#tableTopTmp5',
            defaultToolbar:['filter', 'print', 'exports'],
            cols: [[{fixed: 'left', title: '序号', width: '6%', align: "center", templet: "#indexTable"},
                {field: 'workFlowTitle', width: '25%', title: '审批标题', align: "center"},
                {field: 'workFlowName', width: '25%', title: '流程名称', align: "center"},
                {field: 'nodesName', width: '15%', title: '当前节点名称', align: "center"},
                {field: 'inputUser', width: '15%', title: '创建人', align: "center"},
                {field: 'dateCreated', width: '14%', title: '创建日期', align: "center"},
                {
                    fixed: 'right',
                    title: '操作',
                    width: '10%',
                    align: "center",
                    toolbar: '#eiaWorkFlowListTool',
                    align: "center"
                }
            ]],
            page: true,
            even: true,
            limit: 10,
            done: function (res, curr, count) {
                $("#eiaStampNums").html(res.count);
                $('#workFlowStampTitle').val($('#proWorkFlowStampTitle').val());
            }
        });
    };
    var renderWorkFlowLabOfferList = function (tabName) {
        table.render({
            id: 'eiaLabOfferList',
            elem: '#eiaLabOfferList',
            url: '/eia/eiaBoard/getEiaWorkFlowBusiDataList?tabName=' + tabName,
            toolbar: '#tableTopTmp2',
            defaultToolbar:['filter', 'print', 'exports'],
            cols: [[{fixed: 'left', title: '序号', width: '6%', align: "center", templet: "#indexTable"},
                {field: 'workFlowTitle', width: '25%', title: '审批标题', align: "center"},
                {field: 'workFlowName', width: '25%', title: '流程名称', align: "center"},
                {field: 'nodesName', width: '15%', title: '当前节点名称', align: "center"},
                {field: 'inputUser', width: '15%', title: '创建人', align: "center"},
                {field: 'dateCreated', width: '14%', title: '创建日期', align: "center"},
                {
                    fixed: 'right',
                    title: '操作',
                    width: '10%',
                    align: "center",
                    toolbar: '#eiaWorkFlowListTool',
                    align: "center"
                }
            ]],
            page: true,
            even: true,
            limit: 10,
            done: function (res, curr, count) {
                $("#eiaLabOfferNums").html(res.count);
                $('#workFlowLabTitle').val($('#proWorkFlowLabTitle').val());
            }
        });
    };
    var renderWorkFlowProjectExploreList = function (tabName) {
        table.render({
            id: 'eiaProjectExploreList',
            elem: '#eiaProjectExploreList',
            url: '/eia/eiaBoard/getEiaWorkFlowBusiDataList?tabName=' + tabName,
            toolbar: '#tableTopTmp6',
            defaultToolbar:['filter', 'print', 'exports'],
            cols: [[{fixed: 'left', title: '序号', width: '6%', align: "center", templet: "#indexTable"},
                {field: 'workFlowTitle', width: '25%', title: '审批标题', align: "center"},
                {field: 'workFlowName', width: '25%', title: '流程名称', align: "center"},
                {field: 'nodesName', width: '15%', title: '当前节点名称', align: "center"},
                {field: 'inputUser', width: '15%', title: '创建人', align: "center"},
                {field: 'dateCreated', width: '14%', title: '创建日期', align: "center"},
                {
                    fixed: 'right',
                    title: '操作',
                    width: '10%',
                    align: "center",
                    toolbar: '#eiaWorkFlowListTool',
                    align: "center"
                }
            ]],
            page: true,
            even: true,
            limit: 10,
            done: function (res, curr, count) {
                $("#eiaProjectExploreNums").html(res.count);
                $('#workFlowProjectTitle').val($('#proWorkFlowProjectTitle').val());
            }
        });
    };
    /* var renderdepAndStaffList = function () {
         table.render({
             id: 'depAndStaffList',
             elem: '#depAndStaffList',
             // url: 'data/depAndStaffListData.json',
             //  url:"/eia/static/js/layuiFrame/data/depAndStaffListData.json",
             url: '/eia/eiaBoard/getTotalMoney?status=root&contractDate=' + contractDate,
             toolbar: '#tableTopTmp7',
             defaultToolbar:['filter', 'print', 'exports'],
             cols: [[
                 // {fixed: 'left', title: '序号',width:"60",align: "center",templet: "#indexTable"},
                 {field: 'orgName', width: "32%", title: '业务部门', event: 'enterDept', align: "center", templet: '#depTp'},
                 {field: 'contractMoneySum', width: "17%", title: '合同总额(万元)', align: "center"},
                 {field: 'contractMoney', width: "17%", title: '已签合同额(万元)', align: "center"},
                 {field: 'offerMoney', width: "17%", title: '已报价金额(万元)', align: "center"},
                 {field: 'projectMoney', width: "17.1%", title: '项目总额(万元)', align: "center"}
             ]],
             done: function (res, curr, count) {
                 if (res.data[0].ifRoot) {
                     $('#depAndStaffList').next().find('.layui-table-main tr').attr('nextDo', 'open').eq(0).addClass('thisRoot');
                 } else {
                     $('#depAndStaffList').next().find('.layui-table-main tr').attr('nextDo', 'open').eq(0).attr('nextDo', 'close');
                 }
             }
         });
     };
     var renderproduceTypeList = function () {
         table.render({
             id: 'produceTypeList',
             elem: '#produceTypeList',
             defaultToolbar:['filter', 'print', 'exports'],
             //     url: '/js/layuiFrame/data/produceTypeListData.json',
             //  url:"/eia/static/js/layuiFrame/data/produceTypeListData.json",
             url: '/eia/eiaBoard/getBusiTypeCountMoney?status=close&contractDate=' + contractBusDate,
             cols: [[
                 {
                     field: 'codeDesc',
                     minWidth: '300',
                     title: '业务类型',
                     event: 'enterType',
                     align: "center",
                     templet: '#proTypeTp'
                 },
                 {field: 'contractMoney', minWidth: '300', title: '合同总额(万元)', align: "center"},
                 {field: 'signMoney', minWidth: '300', title: '已签合同额(万元)', align: "center"},
                 {field: 'offerMoney', minWidth: '300', title: '报价金额(万元)', align: "center"}
             ]],
             done: function (res, curr, count) {
                 console.log("res");
                 console.log(res);
                 if (res.data[0].ifRoot) {
                     $('#produceTypeList').next().find('.layui-table-main tr').attr('nextDo', 'open');
                 } else {
                     $('#produceTypeList').next().find('.layui-table-main tr').attr('nextDo', 'open').eq(0).attr('nextDo', 'close');
                 }

             }
         });
     };
     var rendercissAndStaffList = function () {
         table.render({
             id: 'cissAndStaffList',
             elem: '#cissAndStaffList',
             // url: 'data/depAndStaffListData.json',
             //  url:"/eia/static/js/layuiFrame/data/depAndStaffListData.json",
             url: '/eia/eiaBoard/getEiaInvoice?status=root&contractDate=' + contractDate,
             defaultToolbar:['filter', 'print', 'exports'],
             cols: [[
                 // {fixed: 'left', title: '序号',width:"60",align: "center",templet: "#indexTable"},
                 {field: 'orgName', width: "32%", title: '业务部门', event: 'enterDept', align: "center", templet: '#depTp'},
                 {field: 'contractMoney', width: "17%", title: '已签合同额(万元)', align: "center"},
                 {field: 'billMoney', width: "17%", title: '开票金额(万元)', align: "center"},
                 {field: 'invoiceIncomeSum', width: "17%", title: '进账金额(万元)', align: "center"},
                 {field: 'outstandingAmountSum', width: "17%", title: '未结清(万元)', align: "center"},
                 {field: 'expertFeeSum', width: "17.1%", title: '专家费(万元)', align: "center"},
                 {field: 'monitorFeeSum', width: "17.1%", title: '检测费(万元)', align: "center"},
                 {field: 'otherFeeSum', width: "17.1%", title: '其他(万元)', align: "center"}
             ]],
             done: function (res, curr, count) {
                 if (res.data[0].ifRoot) {
                     $('#cissAndStaffList').next().find('.layui-table-main tr').attr('nextDo', 'open').eq(0).addClass('thisRoot');
                 } else {
                     $('#cissAndStaffList').next().find('.layui-table-main tr').attr('nextDo', 'open').eq(0).attr('nextDo', 'close');
                 }
             }
         });
     };*/
    var renderWorkFlowComList = function () {
        table.render({
            id: 'eiaWorkFlowComList',
            elem: '#eiaWorkFlowComList',
            url: '/eia/eiaBoard/getEiaWorkFlowBusiDataList?workType=1',
            toolbar: '#tableTopTmp6',
            defaultToolbar:['filter', 'print', 'exports'],
            cols: [[{fixed: 'left', title: '序号', width: '6%', align: "center", templet: "#indexTable"},
                {field: 'workFlowTitle', width: '25%', title: '审批标题', align: "center"},
                {field: 'workFlowName', width: '25%', title: '流程名称', align: "center"},
                {field: 'nodesName', width: '15%', title: '当前节点名称', align: "center"},
                {field: 'inputUser', width: '15%', title: '创建人', align: "center"},
                {field: 'dateCreated', width: '14%', title: '创建日期', align: "center"},
                {
                    fixed: 'right',
                    title: '操作',
                    width: '10%',
                    align: "center",
                    toolbar: '#eiaWorkFlowComListTool',
                    align: "center"
                }
            ]],
            page: true,
            even: true,
            limit: 10,
            done: function (res, curr, count) {
                $("#workFlowComBusiNums").html(res.count);
                $('#workFlowComTitle').val($('#proWorkFlowComTitle').val());
            }
        });
    };
    var rendermessageList = function () {
        table.render({
            id: 'messageList',
            elem: '#messageList',
            url: '/js/layuiFrame/data/messageListData.json',
            defaultToolbar:['filter', 'print', 'exports'],
            cols: [[
                {fixed: 'left', title: '序号', width: "60", align: "center", templet: "#indexTable"},
                {field: 'messageBody', minWidth: '300', title: '消息主体', event: 'enterType', align: "center"},
                {field: 'messageSource', minWidth: '200', title: '消息来源', align: "center"},
                {field: 'messageType', minWidth: '200', title: '消息类型', align: "center", templet: '#messageTypeTp'},
                {field: 'messageTime', minWidth: '200', title: '时间', align: "center"},
                {field: 'ifRead', minWidth: '150', title: '是否已读', align: "center", templet: '#ifReadTp'},
                {
                    fixed: 'right',
                    title: '操作',
                    minWidth: "200",
                    align: "center",
                    toolbar: '#messageListTool',
                    align: "center"
                }
            ]],
            page: true,
            even: true,
            limit: 10
        });
    };

    table.on('tool(eiaWorkFlowList)', function (obj) {
        var data = obj.data;
        if (obj.event === 'eiaEdit') {
            ajaxBox("/eia/eiaWorkFlowBusi/checkWorkFlow", {
                tableNameId: data.tableNameId,
                tableName: data.tableName
            }, function (res) {
                if (res.code == 0) {
                    pageUrl = '/eia/eiaWorkFlow/eiaWorkFlowTrans';
                    if (data.workFlowCode == "HALT_CONTRACT_WORK_FLOW") {
                        pageUrl = pageUrl + "?type=halt"
                    }
                    $('#tableNameId').val(data.tableNameId);
                    $('#tableName').val(data.tableName);
                    if (data.tableName == "EiaProject") {
                        pageUrl = '/eia/eiaWorkFlow/eiaWorkFlowProject'
                    }
                    var index = layer.open({
                        title: ' ',
                        type: 2,
                        shade: false,
                        maxmin: true,
                        skin: 'larry-green',
                        area: ['100%', '100%'],
                        content: pageUrl,
                        success: function (layero, index) {
                        },
                        end: function () {
                            $('#tableNameId').val("");
                            $('#tableName').val("");
                        },
                        min: function () {
                            $(".layui-layer-title").text(data.workFlowName);
                        },
                        restore: function () {
                            $(".layui-layer-title").text(" ");
                        }
                    });
                } else {
                    layer.msg(res.msg, {time: 1500, icon: 2, shade: 0.1});
                }
            });

        }
    });
    table.on('tool(eiaWorkFlowComList)', function (obj) {
        var data = obj.data;
        if (obj.event === 'eiaEdit') {
            ajaxBox("/eia/eiaWorkFlowBusi/checkWorkFlow", {
                tableNameId: data.tableNameId,
                tableName: data.tableName
            }, function (res) {
                if (res.code == 0) {
                    pageUrl = '/eia/eiaWorkFlow/eiaWorkFlowTrans';
                    $('#tableNameId').val(data.tableNameId);
                    $('#tableName').val(data.tableName);
                    if (data.tableName == "EiaProject") {
                        pageUrl = '/eia/eiaWorkFlow/eiaWorkFlowProject'
                    }
                    var index = layer.open({
                        title: ' ',
                        type: 2,
                        shade: false,
                        maxmin: true,
                        skin: 'larry-green',
                        area: ['100%', '100%'],
                        content: pageUrl,
                        success: function (layero, index) {
                        },
                        end: function () {
                            $('#tableNameId').val("");
                            $('#tableName').val("");
                        },
                        min: function () {
                            $(".layui-layer-title").text(data.workFlowName);
                        },
                        restore: function () {
                            $(".layui-layer-title").text(" ");
                        }
                    });
                } else {
                    layer.msg(res.msg, {time: 1500, icon: 2, shade: 0.1});
                }
            });

        }
    });
    table.on('tool(eiaContractList)', function (obj) {
        var data = obj.data;
        if (obj.event === 'eiaEdit') {
            ajaxBox("/eia/eiaWorkFlowBusi/checkWorkFlow", {
                tableNameId: data.tableNameId,
                tableName: data.tableName
            }, function (res) {
                if (res.code == 0) {
                    pageUrl = '/eia/eiaWorkFlow/eiaWorkFlowTrans';
                    $('#tableNameId').val(data.tableNameId);
                    $('#tableName').val(data.tableName);
                    if (data.tableName == "EiaProject") {
                        pageUrl = '/eia/eiaWorkFlow/eiaWorkFlowProject'
                    }
                    var index = layer.open({
                        title: ' ',
                        type: 2,
                        shade: false,
                        maxmin: true,
                        skin: 'larry-green',
                        area: ['100%', '100%'],
                        content: pageUrl,
                        success: function (layero, index) {
                        },
                        end: function () {
                            $('#tableNameId').val("");
                            $('#tableName').val("");
                        },
                        min: function () {
                            $(".layui-layer-title").text(data.workFlowName);
                        },
                        restore: function () {
                            $(".layui-layer-title").text(" ");
                        }
                    });
                } else {
                    layer.msg(res.msg, {time: 1500, icon: 2, shade: 0.1});
                }
            });

        }
    });
    table.on('tool(eiaProjectList)', function (obj) {
        var data = obj.data;
        if (obj.event === 'eiaEdit') {
            ajaxBox("/eia/eiaWorkFlowBusi/checkWorkFlow", {
                tableNameId: data.tableNameId,
                tableName: data.tableName
            }, function (res) {
                if (res.code == 0) {
                    pageUrl = '/eia/eiaWorkFlow/eiaWorkFlowTrans';
                    $('#tableNameId').val(data.tableNameId);
                    $('#tableName').val(data.tableName);
                    if (data.tableName == "EiaProject") {
                        pageUrl = '/eia/eiaWorkFlow/eiaWorkFlowProject'
                    }
                    var index = layer.open({
                        title: ' ',
                        type: 2,
                        shade: false,
                        maxmin: true,
                        skin: 'larry-green',
                        area: ['100%', '100%'],
                        content: pageUrl,
                        success: function (layero, index) {
                        },
                        end: function () {
                            $('#tableNameId').val("");
                            $('#tableName').val("");
                        },
                        min: function () {
                            $(".layui-layer-title").text(data.workFlowName);
                        },
                        restore: function () {
                            $(".layui-layer-title").text(" ");
                        }
                    });
                } else {
                    layer.msg(res.msg, {time: 1500, icon: 2, shade: 0.1});
                }
            });

        }
    });
    table.on('tool(eiaCertList)', function (obj) {
        var data = obj.data;
        if (obj.event === 'eiaEdit') {
            ajaxBox("/eia/eiaWorkFlowBusi/checkWorkFlow", {
                tableNameId: data.tableNameId,
                tableName: data.tableName
            }, function (res) {
                if (res.code == 0) {
                    pageUrl = '/eia/eiaWorkFlow/eiaWorkFlowTrans';
                    $('#tableNameId').val(data.tableNameId);
                    $('#tableName').val(data.tableName);
                    if (data.tableName == "EiaProject") {
                        pageUrl = '/eia/eiaWorkFlow/eiaWorkFlowProject'
                    }
                    var index = layer.open({
                        title: ' ',
                        type: 2,
                        shade: false,
                        maxmin: true,
                        skin: 'larry-green',
                        area: ['100%', '100%'],
                        content: pageUrl,
                        success: function (layero, index) {
                        },
                        end: function () {
                            $('#tableNameId').val("");
                            $('#tableName').val("");
                        },
                        min: function () {
                            $(".layui-layer-title").text(data.workFlowName);
                        },
                        restore: function () {
                            $(".layui-layer-title").text(" ");
                        }
                    });
                } else {
                    layer.msg(res.msg, {time: 1500, icon: 2, shade: 0.1});
                }
            });

        }
    });

    table.on('tool(eiaProjectExploreList)', function (obj) {
        var data = obj.data;
        if (obj.event === 'eiaEdit') {
            ajaxBox("/eia/eiaWorkFlowBusi/checkWorkFlow", {
                tableNameId: data.tableNameId,
                tableName: data.tableName
            }, function (res) {
                if (res.code == 0) {
                    pageUrl = '/eia/eiaWorkFlow/eiaWorkFlowTrans';
                    $('#tableNameId').val(data.tableNameId);
                    $('#tableName').val(data.tableName);
                    if (data.tableName == "EiaProject") {
                        pageUrl = '/eia/eiaWorkFlow/eiaWorkFlowProject'
                    }
                    var index = layer.open({
                        title: ' ',
                        type: 2,
                        shade: false,
                        maxmin: true,
                        skin: 'larry-green',
                        area: ['100%', '100%'],
                        content: pageUrl,
                        success: function (layero, index) {
                        },
                        end: function () {
                            $('#tableNameId').val("");
                            $('#tableName').val("");
                        },
                        min: function () {
                            $(".layui-layer-title").text(data.workFlowName);
                        },
                        restore: function () {
                            $(".layui-layer-title").text(" ");
                        }
                    });
                } else {
                    layer.msg(res.msg, {time: 1500, icon: 2, shade: 0.1});
                }
            });

        }
    });

    table.on('tool(eiaStampList)', function (obj) {
        var data = obj.data;
        if (obj.event === 'eiaEdit') {
            ajaxBox("/eia/eiaWorkFlowBusi/checkWorkFlow", {
                tableNameId: data.tableNameId,
                tableName: data.tableName
            }, function (res) {
                if (res.code == 0) {
                    pageUrl = '/eia/eiaWorkFlow/eiaWorkFlowTrans';
                    $('#tableNameId').val(data.tableNameId);
                    $('#tableName').val(data.tableName);
                    if (data.tableName == "EiaProject") {
                        pageUrl = '/eia/eiaWorkFlow/eiaWorkFlowProject'
                    }
                    var index = layer.open({
                        title: ' ',
                        type: 2,
                        shade: false,
                        maxmin: true,
                        skin: 'larry-green',
                        area: ['100%', '100%'],
                        content: pageUrl,
                        success: function (layero, index) {
                        },
                        end: function () {
                            $('#tableNameId').val("");
                            $('#tableName').val("");
                        },
                        min: function () {
                            $(".layui-layer-title").text(data.workFlowName);
                        },
                        restore: function () {
                            $(".layui-layer-title").text(" ");
                        }
                    });
                } else {
                    layer.msg(res.msg, {time: 1500, icon: 2, shade: 0.1});
                }
            });

        }
    });
    table.on('tool(eiaLabOfferList)', function (obj) {
        var data = obj.data;
        if (obj.event === 'eiaEdit') {
            ajaxBox("/eia/eiaWorkFlowBusi/checkWorkFlow", {
                tableNameId: data.tableNameId,
                tableName: data.tableName
            }, function (res) {
                if (res.code == 0) {
                    pageUrl = '/eia/eiaWorkFlow/eiaWorkFlowTrans';
                    $('#tableNameId').val(data.tableNameId);
                    $('#tableName').val(data.tableName);
                    if (data.tableName == "EiaProject") {
                        pageUrl = '/eia/eiaWorkFlow/eiaWorkFlowProject'
                    }
                    var index = layer.open({
                        title: ' ',
                        type: 2,
                        shade: false,
                        maxmin: true,
                        skin: 'larry-green',
                        area: ['100%', '100%'],
                        content: pageUrl,
                        success: function (layero, index) {
                        },
                        end: function () {
                            $('#tableNameId').val("");
                            $('#tableName').val("");
                        },
                        min: function () {
                            $(".layui-layer-title").text(data.workFlowName);
                        },
                        restore: function () {
                            $(".layui-layer-title").text(" ");
                        }
                    });
                } else {
                    layer.msg(res.msg, {time: 1500, icon: 2, shade: 0.1});
                }
            });

        }
    });
    renderWorkFlowContractList("EiaContract");
    //监听 消息提醒表格 工具条
    table.on('tool(messageList)', function (obj) {
        var data = obj.data;
        if (obj.event === 'eiaCheck') {    //查看
            var pageUrl = 'messageDetail.gsp';
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
                    // body.find('#workFlowId').val(data.id);
                },
                end: function () {
                    // $('#workFlowId').val("");
                },
                min: function () {
                    $(".layui-layer-title").text("消息详情");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        }
    });

    // Tab切换
    element.on('tab(panel-tab)', function (data) {
        var cur_index = data.index;
        var tabName = $(data.elem).find('.layui-this').attr("tab-name");
        switch (tabName) {
            case "EiaContract":
                renderWorkFlowContractList(tabName);
                break;
            case "EiaProject":
                renderWorkFlowProjectList(tabName);
                break;
            case "EiaCert":
                renderWorkFlowCertList(tabName);
                break;
            case "EiaStamp":
                renderWorkFlowStampList(tabName);
                break;
            case "EiaLabOffer":
                renderWorkFlowLabOfferList(tabName);
                break;
            case "EiaProjectExplore":
                renderWorkFlowProjectExploreList(tabName);
                break;
            case "workFlowComBusi":
                renderWorkFlowComList();
                break;
            //case 5:
            //    rendermessageList();
            //    break;
        }

    });

    //查询按钮
    $('.larry-btn a.layui-btn').click(function () {
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });
    var active = {
        getSelectWorkFlow: function () {    //查询 待办合同事项//查询
            var workFlowTitle = $("#workFlowContractTitle").val();
            table.reload('eiaContractList', {
                where: {
                    workFlowTitle: workFlowTitle
                }
            });
        },
        getSelectWorkProjectFlow: function () {    //查询 待办项目事项//查询
            var workFlowTitle = $("#workFlowProjectTitle").val();
            table.reload('eiaProjectList', {
                where: {
                    workFlowTitle: workFlowTitle
                }
            });
        },
        getSelectWorkCertFlow: function () {    //查询 待办资质事项//查询
            var workFlowTitle = $("#workFlowCertTitle").val();
            table.reload('eiaCertList', {
                where: {
                    workFlowTitle: workFlowTitle
                }
            });
        },
        getSelectWorkFlowStamp: function () {    //查询 待办印章事项//查询
            var workFlowTitle = $("#workFlowStampTitle").val();
            table.reload('eiaStampList', {
                where: {
                    workFlowTitle: workFlowTitle
                }
            });
        },
    /*    getSelectWorkOfferFlow: function () {    //查询 待办事项//查询
            var workFlowTitle = $("#workFlowOfferTitle").val();
            table.reload('eiaOfferList', {
                where: {
                    workFlowTitle: workFlowTitle
                }
            });
        },
        getSelectWorkContractLogFlow: function () {    //查询 待办合同变更事项//查询
            var workFlowTitle = $("#workFlowContractLogTitle").val();
            table.reload('eiaContractLogList', {
                where: {
                    workFlowTitle: workFlowTitle
                }
            });
        },*/
        getSelectWorkLabFlow: function () {    //查询 待办宇相报价事项//查询
            var workFlowTitle = $("#workFlowLabTitle").val();
            table.reload('eiaLabOfferList', {
                where: {
                    workFlowTitle: workFlowTitle
                }
            });
        },
        getSelectWorkComFlow: function () {    //查询 待办事项//查询
            var workFlowTitle = $("#workFlowComTitle").val();
            table.reload('eiaWorkFlowComList', {
                where: {
                    workFlowTitle: workFlowTitle
                }
            });
        },

        getSelectMessage: function () {    //查询 消息提醒

        }
    };
    var urlType = {
        task: {url: "/eia/eiaTask/eiaTaskIndex", title: "我的任务"},
        contract: {url: "/eia/eiaContract/eiaConOfferIndex", title: "我的合同"},
        project: {url: "/eia/eiaProject/eiaProjectIndex", title: "我的项目"},
        client: {url: "/eia/eiaClient/eiaClientIndex", title: "我的客户"},

    };

    //监听头部工具栏事件
    //监听事件
    table.on('toolbar(eiaContractList)', function(obj){
        switch(obj.event){
            case 'getSelectWorkFlow':
                var workFlowTitle = $("#workFlowContractTitle").val();
                $('#proWorkFlowContractTitle').val(workFlowTitle);
                table.reload('eiaContractList', {
                    where: {
                        workFlowTitle: workFlowTitle
                    }
                });
                break;
        };
    })
    table.on('toolbar(eiaLabOfferList)', function(obj){
        switch(obj.event){
            case 'getSelectWorkLabFlow':
                var workFlowTitle = $("#workFlowLabTitle").val();
                $('#proWorkFlowLabTitle').val(workFlowTitle);
                table.reload('eiaLabOfferList', {
                    where: {
                        workFlowTitle: workFlowTitle
                    }
                });
                break;
        }
    });
    table.on('toolbar(eiaProjectList)', function(obj){
        switch(obj.event){
            case 'getSelectWorkProjectFlow':
                var workFlowTitle = $("#workFlowProjectTitle").val();
                $('#proWorkFlowProjectTitle').val(workFlowTitle);
                table.reload('eiaProjectList', {
                    where: {
                        workFlowTitle: workFlowTitle
                    }
                });
                break
        }
    });
    table.on('toolbar(eiaCertList)', function(obj){
        switch(obj.event){
            case 'getSelectWorkCertFlow':
                var workFlowTitle = $("#workFlowCertTitle").val();
                $('#proWorkFlowCertTitle').val(workFlowTitle);
                table.reload('eiaCertList', {
                    where: {
                        workFlowTitle: workFlowTitle
                    }
                });
                break
        }
    });
    table.on('toolbar(eiaStampList)', function(obj){
        switch(obj.event){
            case 'getSelectWorkFlowStamp':
                var workFlowTitle = $("#workFlowStampTitle").val();
                $('#proWorkFlowStampTitle').val(workFlowTitle);
                table.reload('eiaStampList', {
                    where: {
                        workFlowTitle: workFlowTitle
                    }
                });
                break
        }
    });
    table.on('toolbar(eiaWorkFlowComList)', function(obj){
        switch(obj.event){
            case 'getSelectWorkComFlow':
                var workFlowTitle = $("#workFlowComTitle").val();
                $('#proWorkFlowComTitle').val(workFlowTitle);
                table.reload('eiaWorkFlowComList', {
                    where: {
                        workFlowTitle: workFlowTitle
                    }
                });
                break
        }
    });

    /************************************查询框监听结束****************************************************/

    /****
     * 看板徽章
     */
    /****我的任务***/
    ajaxBox('/eia/eiaBoard/getTaskNums', null, function (res) {
        if (res.code === 0) {
            $("#taskNums").html(res.count)
        }
    });

    /****我的合同***/
    ajaxBox('/eia/eiaBoard/getContractNums', null, function (res) {
        if (res.code === 0) {
            $("#contractNums").html(res.count)
        }
    });

    /****我的项目***/
    ajaxBox('/eia/eiaBoard/getProjectNums', null, function (res) {
        if (res.code === 0) {
            $("#projectNums").html(res.count)
        }
    });

    /****我的客户***/
    ajaxBox('/eia/eiaBoard/getClientNums', null, function (res) {
        if (res.code === 0) {
            $("#clientNums").html(res.count)
        }
    });
    /****已办事项***/
    ajaxBox('/eia/eiaBoard/getEiaWorkFlowBusiDataList?workType=1', null, function (res) {
        if (res.code === 0) {
            $("#workFlowComBusiNums").html(res.count)
        }
    });
    /****项目事项***/
    ajaxBox('/eia/eiaBoard/getEiaWorkFlowBusiDataList?tabName=EiaProject', null, function (res) {
        if (res.code === 0) {
            $("#eiaProjectNums").html(res.count)
        }
    });
    /****内审单事项***/
    ajaxBox('/eia/eiaBoard/getEiaWorkFlowBusiDataList?tabName=EiaProjectExplore', null, function (res) {
        if (res.code === 0) {
            $("#eiaProjectExploreNums").html(res.count)
        }
    });
    /****资质待办***/
    ajaxBox('/eia/eiaBoard/getEiaWorkFlowBusiDataList?tabName=EiaCert', null, function (res) {
        if (res.code === 0) {
            $("#eiaCertNums").html(res.count)
        }
    });
    /****印章待办***/
    ajaxBox('/eia/eiaBoard/getEiaWorkFlowBusiDataList?tabName=EiaStamp', null, function (res) {
        if (res.code === 0) {
            $("#eiaStampNums").html(res.count)
        }
    });
    /****监测待办***/
    ajaxBox('/eia/eiaBoard/getEiaWorkFlowBusiDataList?tabName=EiaLabOffer', null, function (res) {
        if (res.code === 0) {
            $("#eiaLabOfferNums").html(res.count)
        }
    });

});