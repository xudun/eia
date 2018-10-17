layui.use(['layer', 'form', 'table'], function () {
    var $ = layui.$,
        layer = layui.layer,
        form = layui.form,
        table = layui.table;
    form.render();

    var eiaLabOfferId = parent.$("#eiaLabOfferId").val();
    var sampleType = "采样";

    /**
     * 文件上传
     */
    var tableName = "EiaLabOffer";
    var tableId = eiaLabOfferId ? eiaLabOfferId : -1;
    $("#tableName").val(tableName);
    $("#tableId").val(tableId);
    /**
     * 渲染文件上传列表
     */
    table.render({
        id: 'eiaFileUploadList',
        elem: '#eiaFileUploadList',
        url:'/eia/eiaFileUpload/getEiaFileUploadDataList?tableName=' + tableName + '&tableId=' + tableId,
        toolbar: '#tableTopTmp',
        defaultToolbar:['filter', 'print', 'exports'],
        cols: [[
            {type: 'numbers', title: '序号', align: "center"},
            {field:'fileName', title: '原文件名',align: "center"},
            {field:'inputUser', title: '上传人',align: "center"},
            {field:'uploadDate', title: '上传时间',align: "center"},
            {field:'fileSize', title: '大小',align: "center"},
            {field:'fileUploadType', title: '文件类型',align: "center"},
            {fixed: 'right', title: '操作' ,align: "center", toolbar: '#eiaFileUploadListBar',align: "center"}
        ]],
        page: true,
        even: true,
        limit: 10,
        done: function () {
            if (eiaLabOfferId) {
                $('.fileAddBtn').removeClass("display-none");
                $('.noticeTag').addClass("display-none");
            }
        }
    });

    /**
     * 基本信息form数据加载
     */
    $.ajax({
        url: "../eiaLabOffer/getEiaLabOfferDataMap?eiaLabOfferId=" + eiaLabOfferId,
        type: "post",
        cache: false,
        async: false,
        success: function (result) {
            $.ajax({
                url: "../eiaLabOffer/getOfferProjectType",
                type: 'get',
                success: function (types) {
                    var projectType = $('#projectType');
                    projectType.append('<option value="">请选择项目类型</option>');
                    for (var i = 0; i < types.data.length; i++) {
                        if (result.data) {
                            if (result.data.projectType == types.data[i].codeDesc) {
                                projectType.append('<option value="' + types.data[i].code + '" selected>' + types.data[i].codeDesc + '</option>')
                            } else {
                                projectType.append('<option value="' + types.data[i].code + '">' + types.data[i].codeDesc + '</option>')
                            }
                        } else {
                            projectType.append('<option value="' + types.data[i].code + '">' + types.data[i].codeDesc + '</option>')
                        }
                    }
                    form.render('select', 'projectType');
                }
            });
            $.ajax({
                url: "../eiaLabOffer/getEiaProjectNameList",
                type: 'get',
                success: function (names) {
                    var projectName = $('#projectName');
                    projectName.append('<option value="">请选择项目名称</option>');
                    for (var i = 0; i < names.data.length; i++) {
                        if (result.data) {
                            if (result.data.projectName == names.data[i].projectName) {
                                projectName.append('<option value="' + names.data[i].id + '" selected>' + names.data[i].projectName + '</option>')
                            } else {
                                projectName.append('<option value="' + names.data[i].id + '">' + names.data[i].projectName + '</option>')
                            }
                        } else {
                            projectName.append('<option value="' + names.data[i].id + '">' + names.data[i].projectName + '</option>')
                        }
                    }
                    form.render('select', 'projectName');
                }
            });
            $.ajax({
                url: "../eiaLabOffer/getOfferTestCompany",
                type: 'get',
                success: function (names) {
                    var testCompany = $('#testCompany');
                    testCompany.append('<option value="">请选择委托检测方</option>');
                    if (names.data) {
                        for (var i = 0; i < names.data.length; i++) {
                            if (result.data) {
                                if (result.data.testCompany == names.data[i].code) {
                                    testCompany.append('<option value="' + names.data[i].code + '" selected>' + names.data[i].code + '</option>')
                                } else {
                                    testCompany.append('<option value="' + names.data[i].code + '">' + names.data[i].code + '</option>')
                                }
                            } else {
                                testCompany.append('<option value="' + names.data[i].code + '">' + names.data[i].code + '</option>')
                            }
                        }
                        form.render('select', 'testCompany');
                    }
                }
            });
            if (result.data) {
                $('#editTitle').tmpl().appendTo('#title');
                $("#projectName").val(result.data.projectName);
                $("#projectAddr").val(result.data.projectAddr);
                $("#wtClientName").val(result.data.wtClientName);
                $("#wtClientContact").val(result.data.wtClientContact);
                $("#sjClientName").val(result.data.sjClientName);
                $("#sjClientContact").val(result.data.sjClientContact);
                $("#wtClientAddr").val(result.data.wtClientAddr);
                $("#wtClientPhone").val(result.data.wtClientPhone);
                $("#sjClientId").val(result.data.sjClientId);
                $("#sjClientAddr").val(result.data.sjClientAddr);
                $("#sjClientPhone").val(result.data.sjClientPhone);
                $("#eiaLabOfferId").val(result.data.id);
                $("#eiaProjectId").val(result.data.eiaProjectId);
                $("#labClientContractInfoId").val(result.data.labClientContractInfoId);
                $("#labClientFinanceInfoId").val(result.data.labClientFinanceInfoId);
                if (result.data.contractTax) {
                    $("#contractTax").val(result.data.contractTax);
                }
                $("#contractDiscount").val(result.data.contractDiscount);
                if (result.data.ifYxTest == true) {
                    $("#ifYxTest").attr("checked", "checked");
                    document.getElementById("ifShowTestCompany").style.display = "none";
                    $(".showProjectName").addClass("display-none");
                    $(".showProjectAddr").addClass("display-none");
                    $(".discountBtn").removeClass("display-none");
                    $("#projectName").removeAttr("lay-verify", "required");
                    $("#projectAddr").removeAttr("lay-verify", "required");
                    $("#testCompany").removeAttr("lay-verify", "required");
                } else {
                    document.getElementById("ifShowTestCompany").style.display = "";
                    $(".showProjectName").removeClass("display-none");
                    $(".showProjectAddr").removeClass("display-none");
                    $("#projectName").attr("lay-verify", "required");
                    $("#projectAddr").attr("lay-verify", "required");
                    $("#testCompany").attr("lay-verify", "required");
                }
                $("#ifYxTest").val(result.data.ifYxTest);
                $('.addPlanBtn').removeClass("display-none");
                $('.addGroupBtn').removeClass("display-none");
                $('.feeSaveBtn').removeClass("display-none");
                $('.quitBtn').removeClass("display-none");
                $('.fileAddBtn').removeClass("display-none");
                $('.noticeTag').addClass("display-none");
                form.render('checkbox');
            } else {
                $('#addTitle').tmpl().appendTo('#title');
                $("#ifYxTest").attr("checked", "checked");
                $("#projectName").removeAttr("lay-verify", "required");
                $("#projectAddr").removeAttr("lay-verify", "required");
                $("#testCompany").removeAttr("lay-verify", "required");
                $('.noticeTag').removeClass("display-none");
                form.render('checkbox');
            }
            $("#wtClientId").val(result.wtClientId);
        }
    });

    /**
     * 渲染检测计划及费用明细列表
     */
    var tableIns = table.render({
        id: 'eiaLabOfferPlanList',
        elem: '#eiaLabOfferPlanList',
        url: "../eiaLabOfferPlan/getEiaLabOfferPlanDataList?eiaLabOfferId=" + eiaLabOfferId,
        cellMinWidth: 200,
        cols: [
            [
                {field: 'baseName', width: '10%', title: '检测基质', align: 'center', rowspan: 2},
                {field: 'paramNameCn', width: '15%', title: '检测项目', align: 'center', rowspan: 2},
                // {field: 'schemeName', width: '40%', title: '检测标准', align: 'center', rowspan: 2},
                // {field: 'schemeMoney', width: '12%', title: '测试费用（元）', align: 'center', rowspan: 2},
                {field: 'maxSchemeMoney', width: '15%', title: '预估最高费用（元）', align: 'center', rowspan: 2},
                // {field: 'discountFee', width: '12%', title: '折后价（元）', align: 'center', rowspan: 2},
                {field: 'discountFee', width: '16%', title: '最高费用折后价（元）', align: 'center', rowspan: 2},
                {align: 'sampleNum', title: '样品数量（个）', align: 'center', colspan: 3},
                {field: 'subTotal', width: '12%', title: '小计（元）', align: 'center', rowspan: 2},
                {fixed: 'right', title: '操作', width: '12%', rowspan: 2, align: 'center', toolbar: '#eiaLabOfferPlanListBar', fixed: "right"
                }
            ], [
                {field: 'pointNum', width: '8%', title: '点位', align: 'center'},
                {field: 'freqNum', width: '8%', title: '频次', align: 'center'},
                {field: 'dayNum', width: '8%', title: '天数周期', align: 'center'}
            ]
        ],
        page: true,
        limit: 10,
        done: function (result) {
            $("#eiaLabOfferPlanCount").val(result.count);
        }
    });
    /**
     * 监听工具条
     */
    table.on('tool(eiaLabOfferPlanList)', function (obj) {
        var data = obj.data;
        if (obj.event === 'planShow') {
            $("#eiaLabOfferPlanId").val(data.id);
            var pageUrl = '../eiaLabOfferPlan/eiaLabOfferPlanDetail';
            var winWidth = document.documentElement.clientWidth;
            var area = ['70%', '75%'];
            if (winWidth < 1300) {
                area = ['100%', '100%'];
            }
            layer.open({
                title: " ",
                type: 2,
                maxmin: true,
                skin: 'larry-green',
                area: area,
                content: pageUrl,
                end: function () {
                    table.reload('eiaLabOfferPlanList');
                    $("#eiaLabOfferPlanId").val("");
                },
                min: function () {
                    $(".layui-layer-title").text("检测计划");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        } else if (obj.event === 'planEdit') {
            $("#eiaLabOfferPlanId").val(data.id);
            var pageUrl = '../eiaLabOfferPlan/eiaLabOfferPlanCreate';
            var winWidth = document.documentElement.clientWidth;
            var area = ['70%', '75%'];
            if (winWidth < 1300) {
                area = ['100%', '100%'];
            }
            layer.open({
                title: " ",
                type: 2,
                maxmin: true,
                skin: 'larry-green',
                area: area,
                content: pageUrl,
                end: function () {
                    table.reload('eiaLabOfferPlanList');
                    $("#eiaLabOfferPlanId").val("");
                },
                min: function () {
                    $(".layui-layer-title").text("修改检测计划");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        } else if (obj.event === 'planDel') {
            layer.confirm('确定要删除该条检测计划吗?', {icon: 3}, function (index) {
                var loadingIndex = layer.load(1, {time: 10*1000,shade: 0.1});
                $.post("../eiaLabOfferPlan/eiaLabOfferPlanDel", {eiaLabOfferPlanId: data.id}, function (data) {
                    if (data.code == 0) {
                        layer.msg('删除成功！', {icon: 1, time: 1500,shade: 0.1}, function () {
                            parent.layui.table.reload("eiaLabOfferList");
                            table.reload("eiaLabOfferPlanList");
                            countFee();
                        });
                    } else {
                        if (data.msg) {
                            layer.msg("处理失败！<br/><div style='color:red;'>错误信息：" + data.msg + "</div>", {
                                icon: 2,
                                time: 1500
                            });
                        } else {
                            layer.msg('删除失败！', {icon: 2, time: 1500,shade: 0.1});
                        }
                    }
                    layer.close(loadingIndex);
                });
            });
        }
    });
    /**
     * 选择委托单位联系人
     */
    $("#wtContactSelect").click(function () {
        var wtClientId = $("#wtClientId").val();
        $("#clientId").val(wtClientId);
        var pageUrl = "../eiaLabOffer/eiaLabContactList";
        var winWidth = document.documentElement.clientWidth;
        var area = ['85%', '80%'];
        if (winWidth < 1300) {
            area = ['100%', '100%'];
        }
        var entLayer = layer.open({
            title: " ",
            type: 2,
            maxmin: true,
            skin: 'larry-green',
            area: area,
            content: pageUrl,
            end: function () {
                $("#type").val("");
            },
            min: function () {
                $(".layui-layer-title").text("选择委托单位联系人");
            },
            restore: function () {
                $(".layui-layer-title").text(" ");
            }
        });
    });
    /**
     * 选择受检单位名称
     */
    $("#sjClientSelect").click(function () {
        var pageUrl = "../eiaLabOffer/eiaLabOfferClientSelect";
        var winWidth = document.documentElement.clientWidth;
        var area = ['85%', '80%'];
        if (winWidth < 1300) {
            area = ['100%', '100%'];
        }
        $("#type").val("sjClientSelect");
        var entLayer = layer.open({
            title: " ",
            type: 2,
            //shade: false,
            maxmin: true,
            skin: 'larry-green',
            area: area,
            content: pageUrl,
            end: function () {
                $("#type").val("");
            },
            min: function () {
                $(".layui-layer-title").text("选择受检（单位）名称");
            },
            restore: function () {
                $(".layui-layer-title").text(" ");
            }
        });
    })
    /**
     * 选择其他费用
     */
    $("#otherFeeTypeSel").click(function () {
        var otherFeeType = $("#otherFeeType").val();
        var countFee = $("#countFee").val();
        var otherFee = $("#otherFee").val();
        if (otherFee == "") {
            otherFee = 0;
        }
        var pageUrl = "../eiaLabOffer/otherFeeTypeSelect";
        var winWidth = document.documentElement.clientWidth;
        var area = ['85%', '80%'];
        if (winWidth < 1300) {
            area = ['100%', '100%'];
        }
        var entLayer = layer.open({
            title: " ",
            type: 2,
            //shade: false,
            maxmin: true,
            skin: 'larry-green',
            area: area,
            content: pageUrl,
            success: function (layero, index) {
                var body = layer.getChildFrame('body', index);
                body.find('#otherFeeType').val(otherFeeType);
                body.find('#countFee').val(countFee);
                body.find('#otherFee').val(otherFee);
            },
            restore: function () {
                $(".layui-layer-title").text(" ");
            }
        });
    });
    /**
     * 基本信息form提交
     */
    form.on('submit(baseInfoSave)', function (data) {
        var loadingIndex = layer.load(1, {time: 10*1000,shade: 0.1});
        var actionUrl = "../eiaLabOffer/eiaLabOfferSave?sampleType=" + "采样";
        $.post(actionUrl, data.field, function (result) {
            if (result.code == 0) {
                layer.msg('保存成功', {icon: 1, time: 1000,shade: 0.1});
                $("#eiaLabOfferId").val(result.data.eiaLabOfferId);
                $("#tableId").val(result.data.eiaLabOfferId);
                eiaLabOfferId = result.data.eiaLabOfferId;
                if (result.data.eiaLabOfferId) {
                    $('.addPlanBtn').removeClass("display-none");
                    $('.addGroupBtn').removeClass("display-none");
                    $('.feeSaveBtn').removeClass("display-none");
                    $('.quitBtn').removeClass("display-none");
                    $('.fileAddBtn').removeClass("display-none");
                    $('.noticeTag').addClass("display-none");
                }
                if (result.data.ifYxTest == true) {
                    $('.discountBtn').removeClass("display-none")
                } else {
                    $('.discountBtn').addClass("display-none")
                }
                $("#wtClientId").val(result.data.wtClientId);
                $("#sjClientId").val(result.data.sjClientId);
                $("#testCompany").val(result.data.testCompany);
                if (result.data.contractTax) {
                    $("#contractTax").val(result.data.contractTax);
                }
                if (result.data.contractDiscount) {
                    $("#contractDiscount").val(result.data.contractDiscount);
                } else {
                    $("#contractDiscount").val(parseFloat(100));
                }
                $("#labClientContractInfoId").val(result.data.labClientContractInfoId);
                table.reload("eiaLabOfferPlanList");

                form.render('radio');
                form.render('select', 'testCompany');
            } else {
                layer.msg(result.msg, {icon: 2, time: 1000,shade: 0.1});
            }
            layer.close(loadingIndex);
        });
        return false;
    });
    /**
     * 保存并退出
     */
    form.on('submit(saveAndQuit)',function(data){
        var loadingIndex = layer.load(1, {time: 10*1000,shade: 0.1});
        var actionUrl = "../eiaLabOffer/eiaLabOfferSave?sampleType=" + "采样";
        $.post(actionUrl,data.field,function(result){
            if (result.code == 0) {
                layer.msg('保存成功', {icon: 1, time: 1000,shade: 0.1}, function () {
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.layui.table.reload("eiaLabOfferList");
                    parent.layer.close(index);
                });
            } else {
                layer.msg(result.msg, {icon: 2, time: 1000,shade: 0.1});
            }
            layer.close(loadingIndex);
        });
    });
    /**
     * 查询按钮、新增
     */
    $('.larry-btn a.layui-btn').on('click', function () {
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });
    var active = {
        planAdd: function () {
            var eiaLabOfferId = $('#eiaLabOfferId').val();
            if (eiaLabOfferId == "") {
                layer.msg('请先保存基本信息', {icon: 2, time: 1500})
                return false
            }
            var pageUrl = '../eiaLabOfferPlan/eiaLabOfferPlanCreate';
            var winWidth = document.documentElement.clientWidth;
            var area = ['70%', '75%'];
            if (winWidth < 1300) {
                area = ['100%', '100%'];
            }
            layer.open({
                title: " ",
                type: 2,
                maxmin: true,
                skin: 'larry-green',
                area: area,
                content: pageUrl,
                success: function (layero, index) {
                    var body = layer.getChildFrame('body', index);
                    body.find('#eiaLabOfferId').val(eiaLabOfferId);
                    body.find('#sampleType').val(sampleType);
                },
                end: function () {
                    table.reload('eiaLabOfferPlanList', {
                        url: "../eiaLabOfferPlan/getEiaLabOfferPlanDataList?eiaLabOfferId=" + eiaLabOfferId
                    });
                },
                min: function () {
                    $(".layui-layer-title").text("新增检测计划");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        },
        planGroupAdd: function () {
            var eiaLabOfferId = $('#eiaLabOfferId').val();
            if (eiaLabOfferId == "") {
                layer.msg('请先保存基本信息', {icon: 2, time: 1500})
                return false
            }
            var pageUrl = '../eiaLabOfferPlan/eiaLabOfferPlanGroupCreate';
            var winWidth = document.documentElement.clientWidth;
            var area = ['70%', '75%'];
            if (winWidth < 1300) {
                area = ['100%', '100%'];
            }
            layer.open({
                title: " ",
                type: 2,
                maxmin: true,
                skin: 'larry-green',
                area: area,
                content: pageUrl,
                success: function (layero, index) {
                    var body = layer.getChildFrame('body', index);
                    body.find('#eiaLabOfferId').val(eiaLabOfferId);
                    body.find('#sampleType').val(sampleType);
                },
                end: function () {
                    table.reload('eiaLabOfferPlanList', {
                        url: "../eiaLabOfferPlan/getEiaLabOfferPlanDataList?eiaLabOfferId=" + eiaLabOfferId
                    });
                },
                min: function () {
                    $(".layui-layer-title").text("新增检测计划套餐");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        },
        batchDiscount: function () {
            var eiaLabOfferId = $('#eiaLabOfferId').val();
            if (eiaLabOfferId == "") {
                layer.msg('请先保存基本信息', {icon: 2, time: 1500})
                return false
            }
            var eiaLabOfferPlanCount = $('#eiaLabOfferPlanCount').val();
            if (eiaLabOfferPlanCount <= 0) {
                layer.msg('请先添加检测计划', {icon: 2, time: 1500})
                return false
            }
            var pageUrl = '../eiaLabOfferPlan/eiaLabOfferPlanBatchDiscDetail';
            var winWidth = document.documentElement.clientWidth;
            var area = ['70%', '75%'];
            if (winWidth < 1300) {
                area = ['100%', '100%'];
            }
            layer.open({
                title: " ",
                type: 2,
                maxmin: true,
                skin: 'larry-green',
                area: area,
                content: pageUrl,
                success: function (layero, index) {
                    var body = layer.getChildFrame('body', index);
                    body.find('#eiaLabOfferId').val(eiaLabOfferId);
                },
                end: function () {
                    table.reload('eiaLabOfferPlanList', {
                        url: "../eiaLabOfferPlan/getEiaLabOfferPlanDataList?eiaLabOfferId=" + eiaLabOfferId
                    });
                },
                min: function () {
                    $(".layui-layer-title").text("批量打折");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        },
    };
    /**
     * 监听项目名称选择
     */
    form.on('select(projectNameSelect)', function (data) {
        $('#eiaProjectId').val(data.value);
        $.ajax({
            url: "../eiaLabOffer/getProjectAddr",
            type:"POST",
            data:{"eiaProjectId":data.value},
            dataType: "json",
            async: true,
            success: function (result) {
                $('#projectAddr').val(result.data.buildArea);
            }
        });
    });
    /**
     * 现场勘察费用form提交
     */
    form.on('submit(exploreSave)', function (data) {
        var loadingIndex = layer.load(1, {time: 10*1000,shade: 0.1});
        var eiaLabOfferId = $('#eiaLabOfferId').val();
        var wtClientId = $("#wtClientId").val();
        var labClientContractInfoId = $("#labClientContractInfoId").val();
        if (eiaLabOfferId == "") {
            layer.msg('请先保存基本信息', {icon: 2, time: 1500});
            return false
        }
        var actionUrl = "../eiaLabOffer/eiaLabOfferExploreSave?eiaLabOfferId=" + eiaLabOfferId;
        data.field['wtClientId'] = wtClientId;
        data.field['labClientContractInfoId'] = labClientContractInfoId;
        $.post(actionUrl, data.field, function (result) {
            if (result.code == 0) {
                layer.msg('保存成功', {icon: 1, time: 1000,shade: 0.1});
                $("#labClientContractInfoId").val(result.data.labClientContractInfoId);
                parent.layui.table.reload("eiaLabOfferList");
            } else {
                layer.msg(result.msg, {icon: 2, time: 1000,shade: 0.1});
            }
            layer.close(loadingIndex);
        });
        return false;
    });
    /**
     * 保存并退出
     */
    form.on('submit(feeSaveQuit)',function(data){
        var eiaLabOfferId = $('#eiaLabOfferId').val();
        var wtClientId = $("#wtClientId").val();
        var labClientContractInfoId = $("#labClientContractInfoId").val();
        if (eiaLabOfferId == "") {
            layer.msg('请先保存基本信息', {icon: 2, time: 1500});
            return false
        }
        var loadingIndex = layer.load(1, {time: 10*1000,shade: 0.1});
        var actionUrl = "../eiaLabOffer/eiaLabOfferExploreSave?eiaLabOfferId=" + eiaLabOfferId;
        data.field['wtClientId'] = wtClientId;
        data.field['labClientContractInfoId'] = labClientContractInfoId;
        $.post(actionUrl,data.field,function(result){
            if (result.code == 0) {
                layer.msg('保存成功', {icon: 1, time: 1000,shade: 0.1}, function () {
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.layui.table.reload("eiaLabOfferList");
                    parent.layer.close(index);
                });
            } else {
                layer.msg(result.msg, {icon: 2, time: 1000,shade: 0.1});
            }
            layer.close(loadingIndex);
        });
    });
    /**
     * 现场勘察费用form数据加载
     */
    $.ajax({
        url: "../eiaLabOffer/getEiaLabOfferDataMap?eiaLabOfferId=" + eiaLabOfferId,
        type: "post",
        cache: false,
        async: false,
        success: function (result) {
            if (result.data) {
                $("#exploreFee").val(result.data.exploreFee);
                $("#hotelFee").val(result.data.hotelFee);
                $("#trafficFee").val(result.data.trafficFee);
                $("#countFee").val(result.data.countFee);
                $("#countFeeTax").val(result.data.countFeeTax);
                $("#sampleFee").val(result.data.sampleFee);
                $("#explorePeopleNum").val(result.data.explorePeopleNum);
                $("#exploreDayNum").val(result.data.exploreDayNum);
                $("#travelDayNum").val(result.data.travelDayNum);
                $("#roomNum").val(result.data.roomNum);
                $("#liveDayNum").val(result.data.liveDayNum);
                $("#workOutFee").val(result.data.workOutFee);
                $("#expertFee").val(result.data.expertFee);
                $("#exploreFeeTotal").val(result.data.exploreFeeTotal);
                $("#trafficFeeTotal").val(result.data.trafficFeeTotal);
                $("#hotelFeeTotal").val(result.data.hotelFeeTotal);
                $("#otherFeeType").val(result.data.otherFeeType);
                $("#otherFee").val(result.data.otherFee);
                $("#prePrefFee").val(result.data.prePrefFee);
                $("#prePrefFeeTax").val(result.data.prePrefFeeTax);
            }
        }
    });
    $("#exploreFee").keyup(function () {
        countExploreFeeTotal();
        countFee();
    });
    $("#explorePeopleNum").keyup(function () {
        countExploreFeeTotal();
        countFee();
    });
    $("#exploreDayNum").keyup(function () {
        countExploreFeeTotal();
        countFee();
    });
    $("#exploreFeeTotal").keyup(function () {
        countFee();
    });
    $("#hotelFee").keyup(function () {
        countHotelFeeTotal();
        countFee();
    });
    $("#roomNum").keyup(function () {
        countHotelFeeTotal();
        countFee();
    });
    $("#liveDayNum").keyup(function () {
        countHotelFeeTotal();
        countFee();
    });
    $("#hotelFeeTotal").keyup(function () {
        countFee();
    });
    $("#trafficFee").keyup(function () {
        countTrafficFeeTotal();
        countFee();
    });
    $("#travelDayNum").keyup(function () {
        countTrafficFeeTotal();
        countFee();
    });
    $("#trafficFeeTotal").keyup(function () {
        countFee();
    });
    $("#contractTax").keyup(function () {
        countFee();
    });
    $("#workOutFee").keyup(function () {
        countFee();
    });
    $("#expertFee").keyup(function () {
        countFee();
    });
    $("#otherFee").keyup(function () {
        countFee();
    });
    $("#contractDiscount").keyup(function () {
        countExploreFeeTotal();
        countTrafficFeeTotal();
        countHotelFeeTotal();
        countFee();
    });
    function countExploreFeeTotal() {
        var contractDiscount = $("#contractDiscount").val();
        var exploreFee = $("#exploreFee").val();
        var explorePeopleNum = $("#explorePeopleNum").val();
        var exploreDayNum = $("#exploreDayNum").val();
        if (!contractDiscount)
            contractDiscount = 100;
        if (exploreFee == "")
            exploreFee = 0;
        if (explorePeopleNum == "")
            explorePeopleNum = 0;
        if (exploreDayNum == "")
            exploreDayNum = 0;
        if (exploreFee && explorePeopleNum && exploreDayNum)
            var exploreFeeTotal = (parseFloat(exploreFee) * parseFloat(explorePeopleNum) * parseFloat(exploreDayNum) * contractDiscount / 100).toFixed(2);
        $("#exploreFeeTotal").val(exploreFeeTotal);
    }

    function countTrafficFeeTotal() {
        var contractDiscount = $("#contractDiscount").val();
        var trafficFee = $("#trafficFee").val();
        var travelDayNum = $("#travelDayNum").val();
        if (!contractDiscount)
            contractDiscount = 100;
        if (trafficFee == "")
            trafficFee = 0;
        if (travelDayNum == "")
            travelDayNum = 0;
        if (trafficFee && travelDayNum)
            var trafficFeeTotal = (parseFloat(trafficFee) * parseFloat(travelDayNum) * contractDiscount / 100).toFixed(2);
        $("#trafficFeeTotal").val(trafficFeeTotal);
    }

    function countHotelFeeTotal() {
        var contractDiscount = $("#contractDiscount").val();
        var hotelFee = $("#hotelFee").val();
        var roomNum = $("#roomNum").val();
        var liveDayNum = $("#liveDayNum").val();
        if (!contractDiscount)
            contractDiscount = 100;
        if (hotelFee == "")
            hotelFee = 0;
        if (roomNum == "")
            roomNum = 0;
        if (liveDayNum == "")
            liveDayNum = 0;
        if (hotelFee && roomNum && liveDayNum)
            var hotelFeeTotal = (parseFloat(hotelFee) * parseFloat(roomNum) * parseFloat(liveDayNum) * contractDiscount / 100).toFixed(2);
        $("#hotelFeeTotal").val(hotelFeeTotal);
    }

    function countFee() {
        var exploreFeeTotal = $("#exploreFeeTotal").val();
        var hotelFeeTotal = $("#hotelFeeTotal").val();
        var trafficFeeTotal = $("#trafficFeeTotal").val();
        var sampleFee = $("#sampleFee").val();
        var contractTax = $("#contractTax").val();
        var workOutFee = $("#workOutFee").val();
        var expertFee = $("#expertFee").val();
        var otherFee = $("#otherFee").val();
        var preSampleFee = $("#preSampleFee").val();
        var contractDiscount = $("#contractDiscount").val();
        if (exploreFeeTotal == "")
            exploreFeeTotal = 0;
        if (hotelFeeTotal == "")
            hotelFeeTotal = 0;
        if (trafficFeeTotal == "")
            trafficFeeTotal = 0;
        if (contractTax == "")
            contractTax = 0;
        if (workOutFee == "")
            workOutFee = 0;
        if (expertFee == "")
            expertFee = 0;
        if (otherFee == "")
            otherFee = 0;
        if (sampleFee == "")
            sampleFee = 0;
        if (preSampleFee == "")
            preSampleFee = 0;
        if (contractDiscount == "")
            contractDiscount = 100;
        var prePrefFee = parseInt((parseFloat(exploreFeeTotal / (contractDiscount / 100)) + parseFloat(hotelFeeTotal / (contractDiscount / 100)) + parseFloat(trafficFeeTotal / (contractDiscount / 100)) + parseFloat(preSampleFee) + parseFloat(workOutFee) + parseFloat(expertFee) + parseFloat(otherFee)).toFixed(2));
        var prePrefFeeTax = parseInt(parseFloat(prePrefFee * (1 + contractTax / 100)).toFixed(2));
        var countFee = parseInt((parseFloat(exploreFeeTotal) + parseFloat(hotelFeeTotal) + parseFloat(trafficFeeTotal) + parseFloat(sampleFee) + parseFloat(workOutFee) + parseFloat(expertFee) + parseFloat(otherFee)).toFixed(2));
        var countFeeTax = parseInt(parseFloat(countFee * (1 + contractTax / 100)).toFixed(2));
        $("#prePrefFee").val(prePrefFee);
        $("#prePrefFeeTax").val(prePrefFeeTax);
        $("#countFeeTax").val(countFeeTax);
        $("#countFee").val(countFee);
    }

    /**
     * 是否宇相检测switch开关监听
     */
    form.on('switch(ifYxTest)', function (data) {
        if (data.elem.checked) {
            document.getElementById("ifShowTestCompany").style.display = "none";
            $(".showProjectName").addClass("display-none");
            $(".showProjectAddr").addClass("display-none");
            $("#eiaProjectId").val('');
            $("#projectName").val('');
            $("#projectAddr").val('');
            $("#projectName").removeAttr("lay-verify", "required");
            $("#projectAddr").removeAttr("lay-verify", "required");
            $("#testCompany").removeAttr("lay-verify", "required");
        } else {
            document.getElementById("ifShowTestCompany").style.display = "";
            $(".showProjectName").removeClass("display-none");
            $(".showProjectAddr").removeClass("display-none");
            $("#projectName").attr("lay-verify", "required");
            $("#projectAddr").attr("lay-verify", "required");
            $("#testCompany").attr("lay-verify", "required");
            form.render();
        }
    });

    /**
     * 监听工具条
     */
    table.on('tool(eiaFileUploadList)', function (obj) {
        var data = obj.data;
        if(obj.event === 'eiaFileUploadDel'){    //删除
            layer.confirm('确定要删除该附件吗?', {icon: 3}, function (index) {
                var loadingIndex = layer.load(1, {time: 10*1000,shade: 0.1});
                $.post("/eia/eiaFileUpload/eiaFileUploadDelete", {eiaFileUploadId:data.id},function (data) {
                    if (data.code == 0) {
                        layer.msg('删除成功！', {icon: 1, time: 1500,shade: 0.1}, function () {
                            obj.del();
                        });
                    } else {
                        if (data.msg) {
                            layer.msg("处理失败！<br/><div style='color:red;'>错误信息：" + data.msg + "</div>", {
                                icon: 2,
                                time: 1500
                            });
                        } else {
                            layer.msg('删除失败！', {icon: 2, time: 1500,shade: 0.1});
                        }
                    }
                    layer.close(loadingIndex);
                });
            });
        }else if(obj.event === 'eiaFileUploadDownload'){
            window.location.href =  request_url_root+"/eiaFileUpload/eiaFileUploadDownload?eiaFileUploadId=" + data.id
        }else if(obj.event === 'eiaFileUploadDetail'){
            var pageUrl = request_url_root+'/eiaFileUpload/eiaFileUploadDetail?eiaFileUploadId='+data.id;
            var index = layer.open({
                title:' ',
                type: 2,
                shade: false,
                maxmin: true,
                skin: 'larry-green',
                area: ['100%', '100%'],
                content: pageUrl,
                min: function () {
                    $(".layui-layer-title").text("文件上传查看");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        }
    });

    //监听头部工具栏事件
    //监听事件
    table.on('toolbar(eiaFileUploadList)', function(obj){
        switch(obj.event){
            case 'eiaFileUploadAdd':
                tableId = $("#tableId").val();
                var pageUrl = request_url_root+'/eiaFileUpload/eiaFileUploadCreate';
                var index = layer.open({
                    title:' ',
                    type: 2,
                    shade: false,
                    maxmin: true,
                    skin: 'larry-green',
                    area: ['100%', '100%'],
                    content: pageUrl,
                    end: function () {
                        table.reload('eiaFileUploadList', {url: "/eia/eiaFileUpload/getEiaFileUploadDataList?tableName=" + tableName + "&tableId=" + tableId});
                    },
                    min: function () {
                        $(".layui-layer-title").text("文件上传");
                    },
                    restore: function () {
                        $(".layui-layer-title").text(" ");
                    }
                });
                break;
        }
    });
});