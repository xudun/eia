layui.use(['layer', 'form', 'table'], function () {
    var $ = layui.$,
        layer = layui.layer,
        form = layui.form,
        table = layui.table;
    form.render();

    var eiaLabOfferId = parent.$('#eiaLabOfferId').val();
    eiaLabOfferId = eiaLabOfferId ? eiaLabOfferId : getParamFromUrl(document.location.href, "eiaLabOfferId");

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
            if (result.data) {
                $('#editTitle').tmpl().appendTo('#title');
                $("#projectType").val(result.data.projectType);
                $("#projectName").val(result.data.projectName);
                if (result.data.eiaProjectId) {
                    $("#projectAddr").val(result.data.projectAddr);
                }
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
                if (result.data.ifYxTest) {
                    $("#ifYxTestStr").val("是");
                } else {
                    $("#ifYxTestStr").val("否");
                }
                $("#ifYxTest").val(result.data.ifYxTest);
                $('.addPlanBtn').removeClass("display-none");
                $('.addGroupBtn').removeClass("display-none");
                $('.feeSaveBtn').removeClass("display-none");
                $('.quitBtn').removeClass("display-none");
                $('.fileAddBtn').removeClass("display-none");
                $('.noticeTag').addClass("display-none");
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
        toolbar: '#planListTmp',
        defaultToolbar:['filter', 'print', 'exports'],
        cols: [
            [
                {field: 'baseName', width: '15%', title: '检测基质', align: 'center', rowspan: 2},
                {field: 'paramNameCn', width: '20%', title: '检测项目', align: 'center', rowspan: 2},
                {field: 'maxSchemeMoney', width: '15%', title: '预估最高费用（元）', align: 'center', rowspan: 2},
                {field: 'discountFee', width: '16%', title: '最高费用折后价（元）', align: 'center', rowspan: 2},
                {align: 'sampleNum', title: '样品数量（个）', align: 'center', colspan: 3},
                {field: 'subTotal', width: '12%', title: '小计（元）', align: 'center', rowspan: 2}
            ], [
                {field: 'pointNum', width: '8%', title: '点位', align: 'center'},
                {field: 'freqNum', width: '8%', title: '频次', align: 'center'},
                {field: 'dayNum', width: '8%', title: '天数周期', align: 'center'}
            ]
        ],
        page: true,
        limit: 10,
        done: function (result) {
            if (result.sampleFee) {
                $("#planSubTotal").text(result.sampleFee);
            } else {
                $("#planSubTotal").text(0);
            }
        }
    });

    /**
     * 基本信息form提交
     */
    form.on('submit(baseInfoSave)', function (data) {
        var loadingIndex = layer.load(1, {time: 10*1000,shade: 0.1});
        var actionUrl = "../eiaLabOffer/updateProjectInfo?sampleType=" + "采样";
        $.post(actionUrl, data.field, function (result) {
            if (result.code == 0) {
                layer.msg('保存成功', {icon: 1, time: 1000,shade: 0.1});
            } else {
                layer.msg(result.msg, {icon: 2, time: 1000,shade: 0.1});
            }
            layer.close(loadingIndex);
        });
        return false;
    });

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
                var exploreSubTotalDisc = result.data.exploreSubTotalDisc;
                if (!exploreSubTotalDisc) {
                    exploreSubTotalDisc = 100
                }
                var otherFeeTotalDisc = result.data.otherFeeTotalDisc;
                if (!otherFeeTotalDisc) {
                    otherFeeTotalDisc = 100
                }
                $("#exploreSubTotalDisc").val(exploreSubTotalDisc);
                $("#otherFeeTotalDisc").val(otherFeeTotalDisc);
                var exploreSubTotal = (result.data.exploreFeeTotal + result.data.hotelFeeTotal + result.data.trafficFeeTotal) * exploreSubTotalDisc/100;
                var otherFeeTotal = (result.data.otherFee + result.data.workOutFee + result.data.expertFee) * otherFeeTotalDisc/100;
                $("#exploreSubTotal").text(exploreSubTotal);
                $("#otherFeeTotal").text(otherFeeTotal);
            }
        }
    });

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
        limit: 10
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
});