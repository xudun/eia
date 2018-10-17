layui.use(['layer','table'],function(){
    var $ = layui.$,
        layer = layui.layer,
        table = layui.table;
    /**
     * 基本信息form数据加载
     */
    var eiaLabOfferId = parent.$("#eiaLabOfferId").val();
    eiaLabOfferId = eiaLabOfferId ? eiaLabOfferId : getParamFromUrl(document.location.href,"eiaLabOfferId");
    $.ajax({
        url: "../eiaLabOffer/getEiaLabOfferDataMap?eiaLabOfferId=" + eiaLabOfferId,
        type:"post",
        cache: false,
        async: false,
        success: function (result) {
            if(result.data){
                $("#offerCode").text(result.data.offerCode);
                $("#projectType").text(result.data.projectType);
                $("#wtClientName").text(result.data.wtClientName);
                $("#wtClientContact").text(result.data.wtClientContact);
                $("#sjClientName").text(result.data.sjClientName);
                $("#sjClientContact").text(result.data.sjClientContact);
                $("#wtClientAddr").text(result.data.wtClientAddr);
                $("#wtClientPhone").text(result.data.wtClientPhone);
                $("#sjClientAddr").text(result.data.sjClientAddr);
                $("#sjClientPhone").text(result.data.sjClientPhone);
                $("#projectName").text(result.data.projectName);
                $("#projectAddr").text(result.data.projectAddr);
                $("#reportDate").text(result.reportDate);
                if (result.data.ifYxTest) {
                    $("#ifYxTest").text("是");
                } else {
                    $("#ifYxTest").text("否");
                    document.getElementById("ifShowTestCompany").style.display = "";
                    $("#testCompany").text(result.data.testCompany);
                }
            }
        }
    });
    /**
     * 渲染检测计划及费用明细列表
     */
    var tableIns = table.render({
        id:'eiaLabOfferPlanList',
        elem: '#eiaLabOfferPlanList',
        url: "../eiaLabOfferPlan/getEiaLabOfferPlanDataList?eiaLabOfferId=" + eiaLabOfferId,
        cellMinWidth: 200,
        toolbar: '#planListTmp',
        defaultToolbar:['filter', 'print', 'exports'],
        cols: [
            [
                {field: 'baseName', width: '10%', title: '检测基质',align: 'center', rowspan: 2},
                {field: 'paramNameCn', width: '15%', title: '检测项目',align: 'center', rowspan: 2},
                // {field: 'schemeName', width: '40%', title: '检测标准',align: 'center', rowspan: 2},
                // {field: 'schemeMoney', width: '12%', title: '测试费用（元）', align: 'center', rowspan: 2},
                {field: 'maxSchemeMoney', width: '20%', title: '预估最高费用（元）', align: 'center', rowspan: 2},
                // {field: 'discountFee', width: '12%', title: '折后价（元）', align: 'center', rowspan: 2},
                {field: 'discountFee', width: '20%', title: '最高费用折后价（元）', align: 'center', rowspan: 2},
                {align: 'sampleNum', title: '样品数量（个）',align: 'center', colspan: 3},
                {field: 'subTotal', width: '12%', title: '小计（元）', align: 'center', rowspan: 2}
            ], [
                {field: 'pointNum', width: '8%', title: '点位', align: 'center'},
                {field: 'freqNum', width: '8%', title: '频次', align: 'center'},
                {field: 'dayNum', width: '8%', title: '天数周期', align: 'center'}
            ]
        ],
        page: true,
        limit: 10,
        done: function(result){
            if (result.sampleFee) {
                $("#planSubTotal").text(result.sampleFee);
            } else {
                $("#planSubTotal").text(0);
            }
        }
    });
    /**
     * 现场勘察费用form数据加载
     */
    $.ajax({
        url: "../eiaLabOffer/getEiaLabOfferDataMap?eiaLabOfferId=" + eiaLabOfferId,
        type:"post",
        cache: false,
        async: false,
        success: function (result) {
            if(result.data){
                $("#exploreFee").text(result.data.exploreFee);
                $("#hotelFee").text(result.data.hotelFee);
                $("#trafficFee").text(result.data.trafficFee);
                $("#countFee").text(result.data.countFee);
                $("#sampleFee").text(result.data.sampleFee);
                $("#contractTax").text(result.data.contractTax);
                $("#contractDiscount").text(result.data.contractDiscount);
                $("#countFeeTax").text(result.data.countFeeTax);
                $("#explorePeopleNum").text(result.data.explorePeopleNum);
                $("#exploreDayNum").text(result.data.exploreDayNum);
                $("#travelDayNum").text(result.data.travelDayNum);
                $("#roomNum").text(result.data.roomNum);
                $("#liveDayNum").text(result.data.liveDayNum);
                $("#workOutFee").text(result.data.workOutFee);
                $("#expertFee").text(result.data.expertFee);
                $("#exploreFeeTotal").text(result.data.exploreFeeTotal);
                $("#trafficFeeTotal").text(result.data.trafficFeeTotal);
                $("#hotelFeeTotal").text(result.data.hotelFeeTotal);
                $("#otherFeeType").text(result.data.otherFeeType);
                $("#otherFee").text(result.data.otherFee);
                $("#prePrefFee").text(result.data.prePrefFee);
                $("#prePrefFeeTax").text(result.data.prePrefFeeTax);
                $("#busiFee").text(result.data.busiFee);
                var exploreSubTotalDisc = result.data.exploreSubTotalDisc;
                if (!exploreSubTotalDisc) {
                    exploreSubTotalDisc = 100
                }
                var otherFeeTotalDisc = result.data.otherFeeTotalDisc;
                if (!otherFeeTotalDisc) {
                    otherFeeTotalDisc = 100
                }
                $("#exploreSubTotalDisc").text(exploreSubTotalDisc);
                $("#otherFeeTotalDisc").text(otherFeeTotalDisc);

                var exploreSubTotal = (result.data.exploreFeeTotal + result.data.hotelFeeTotal + result.data.trafficFeeTotal) * exploreSubTotalDisc/100;
                var otherFeeTotal = (result.data.otherFee + result.data.workOutFee + result.data.expertFee) * otherFeeTotalDisc/100;
                $("#exploreSubTotal").text(exploreSubTotal);
                $("#otherFeeTotal").text(otherFeeTotal);
                if (result.data.busiFee) {
                    $("#countFeeTotal").text(result.data.busiFee);
                } else {
                    $("#countFeeTotal").text(result.data.countFeeTax);
                }
            }
        }
    });
    /**
     * 测试信息确认form数据加载
     */
    $.ajax({
        url: "../eiaLabOffer/getEiaLabOfferDataMap?eiaLabOfferId=" + eiaLabOfferId,
        type:"post",
        cache: false,
        async: false,
        success: function (result) {
            if(result.data){
                $("#testPeriod").text(result.data.testPeriod);
                $("#reportLanguage").text(result.data.reportLanguage);
                if (result.data.ifLimit) {
                    $("#ifLimit").text("是");
                } else if (result.data.ifLimit == false) {
                    $("#ifLimit").text("否");
                }
                if (result.data.ifCma) {
                    $("#ifCma").text("是");
                } else if (result.data.ifCma == false) {
                    $("#ifCma").text("否");
                }
                if (result.data.ifFlueGasConvConcn) {
                    $("#ifFlueGasConvConcn").text("是");
                } else if (result.data.ifFlueGasConvConcn == false) {
                    $("#ifFlueGasConvConcn").text("否");
                }
                if (result.data.ifFlueGasEmisRate) {
                    $("#ifFlueGasEmisRate").text("是");
                } else if (result.data.ifFlueGasEmisRate == false) {
                    $("#ifFlueGasEmisRate").text("否");
                }
                $("#testDayNum").text(result.data.testDayNum);
                if (!result.data.testDayNum) {
                    $("#testDayNumStr").css("display", "none");
                }
                $("#reportTemp").text(result.data.reportTemp);
                $("#testMethod").text(result.data.testMethod);
                $("#remainDeal").text(result.data.remainDeal);
                $("#ticketType").text(result.data.ticketType);
                $("#testSubType").text(result.data.testSubType);
                $("#testSub").text(result.data.testSub);
                $("#invoiceComp").text(result.data.invoiceComp);
                $("#clientAddr").text(result.data.clientAddr);
                $("#taxCode").text(result.data.taxCode);
                $("#openAccountBank").text(result.data.openAccountBank);
                $("#bankAccount").text(result.data.bankAccount);
                $("#clientPhone").text(result.data.clientPhone);
                if (result.data.remainDeal == "其他") {
                    $("#remainDealMemo").text(result.data.remainDealMemo);
                }
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
        } else if(obj.event === 'eiaFileUploadDownload'){
            window.location.href =  request_url_root+"/eiaFileUpload/eiaFileUploadDownload?eiaFileUploadId=" + data.id
        }
    });
});