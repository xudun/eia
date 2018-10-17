layui.use(['jquery', 'layer', 'form','laydate'], function(){
    var $ = layui.jquery,
        form = layui.form,
        laydate = layui.laydate,
        layer = layui.layer;

    var $offerNo = $('#offerNo'),
        $offerDateStr = $('#offerDateStr'),
        $offerMoney = $('#offerMoney'),
        $eiaClientName = $('#eiaClientName'),
        $clientAddress = $('#clientAddress'),
        $offerName = $('#offerName'),
        $contactName = $('#contactName'),
        $contractTypeDrop = $('#contractTypeDrop'),
        $contactPhone = $('#contactPhone'),
        $contractTypeStr = $('#contractTypeStr'),
        $taskId = $('#taskId'),
        $province = $('#province'),
        $contractUse = $('#contractUse'),
        $ownerClientName = $('#ownerClientName'),
        $ownerClientAddress = $('#ownerClientAddress'),
        $ownerContactName = $('#ownerContactName'),
        $ownerContactPhone = $('#ownerContactPhone'),
        $projectScale = $('#projectScale'),
        $contractTrust = $('#contractTrust');

    //工具函数：
    // 在url中获取指定参数值
    var getParamFromUrl = function(url,param){
        if(url.indexOf('?')!==-1){
            var params = url.split("?")[1].split('&');
            for(var i=0; i<params.length; i++){
                if(params[i].indexOf(param) !== -1){
                    return params[i].replace(param+"=","");
                }
            }
            // console.log('该url中无参数'+param);
        }else{
            // console.log('该url中无参数');
        }
    };
    //设置select指定项选中
    var selectOptionByVal = function ($select, val) {
        var opt_arr = $('option',$($select));
        for(var i=0; i<opt_arr.length; i++){
            if($(opt_arr[i]).val() == val){
                $(opt_arr[i]).prop("selected",true);
                break;
            }
        }
    };

    //页面类型：0:新建 ； 1：编辑
    var pageType = getParamFromUrl(document.location.href,"pageType");
    console.log('页面类型：'+pageType);

    //设置页面名称
    var $pageTitle = $('.pageTitle');
    pageType == 0 ? $pageTitle.html('新增报价') : $pageTitle.html('编辑报价');

    //金额hover提示
    $('.show-tips').hover(function () {
        layer.tips('选择合同类型后即可填写报价金额', '#tips',{
            area: ['auto', 'auto'],
            tips: [1, '#30b5ff'],
            time: 2000
        });
    },function () {
        var index = layer.tips();
        layer.close(index);
    });

    //重置按钮显隐
    pageType == 0 ? "" : $('.resetBtn').hide();

    //渲染省份选项
    $.ajax({
        url:'/eia/eiaDomainCode/getTree?domain='+"PROVINCE_CODE",
        type:"POST",
        data:{},
        async: true,
        dataType: "json",
        success: function (data) {
            for(var i=0; i<data.length; i++){
                var str = "<option value='"+data[i].id+"'>"+data[i].name+"</option>";
                $province.append(str);
            }
            form.render();
        }
    });
    /**
     * 合同用途
     */
    $.ajax({
        url:'/eia/eiaDomainCode/getTree?domain='+"CONTRACT_USE",
        type:"POST",
        data:{},
        async: true,
        dataType: "json",
        success: function (data) {
            for(var i=0; i<data.length; i++){
                var str = "<option value='"+data[i].id+"'>"+data[i].name+"</option>";
                $contractUse.append(str);
            }
            form.render();
        }
    });

    //渲染任务选项
    $.ajax({
        url:"/eia/eiaTask/getEiaTaskName",
        type:"POST",
        data:{},
        dataType: "json",
        async: true,
        disableParent: true,
        success: function (data) {
            for(var i=0; i<data.length; i++){
                var str = "<option value='"+data[i].id+"'>"+data[i].taskName+"</option>";
                $taskId.append(str);
            }
            form.render('select');
        }
    });

    //编辑时回显数据
    if(pageType == 1){
        //金额可填写
        $('.fillMoneyBtn').css("display",'block');
        $('.show-tips').css("display",'none');
        var eiaOfferId = $('#offerId').val();
        $.ajax({
            url:"/eia/eiaContract/eiaOfferDataMap?eiaOfferId="+eiaOfferId,
            type:"POST",
            data:{},
            dataType: "json",
            async: true,
            success: function (data) {
                console.log(data);

                $taskId.val(data.data.taskName);
                $offerNo.val(data.data.offerNo);
                var offerDate = data.offerDate;
                $offerDateStr.val(offerDate);
                $offerMoney.val(data.data.offerMoney);
                $eiaClientName.val(data.data.eiaClientName);
                $clientAddress.val(data.data.clientAddress);
                $offerName.val(data.data.offerName);
                $contactPhone.val(data.data.contactPhone);
                $contactName.val(data.data.contactName);
                $contractTypeDrop.val(data.data.contractType);
                $contractTypeStr.val(data.data.contractTypeCode);
                $ownerClientName.val(data.data.ownerClientName);
                $ownerClientAddress.val(data.data.ownerClientAddress);
                $ownerContactName.val(data.data.ownerContactName);
                $ownerContactPhone.val(data.data.ownerContactPhone);
                $projectScale.val(data.data.projectScale);
                $contractTrust.val(data.data.contractTrust);
                var contractTrust = data.data.contractTrust;
                if (contractTrust == "联合赤道") {
                    $("#lhcd").prop("checked", true);
                    $("#lhtz").prop("checked", false);
                } else if (contractTrust == "联合泰泽") {
                    console.log(2222);
                    $("#lhcd").prop("checked", false);
                    $("#lhtz").prop("checked", true);
                }

                $("#reportFees").val(data.data.reportFees);
                $("#enviroMonitoringFee").val(data.data.enviroMonitoringFee);
                $("#expertFee").val(data.data.expertFee);
                $("#groundWater").val(data.data.groundWater);
                $("#otherFee").val(data.data.otherFee);
                $("#specialFee").val(data.data.specialFee);
                $("#serveFee").val(data.data.serveFee);
                $("#ecoDetectFee").val(data.data.ecoDetectFee);
                $("#preIssCertFee").val(data.data.preIssCertFee);
                $("#preSurvCertFee").val(data.data.preSurvCertFee);
                $("#certServeFee").val(data.data.certServeFee);
                selectOptionByVal($taskId, data.data.taskId);
                selectOptionByVal($province, data.data.province);
                selectOptionByVal($contractUse, data.data.contractUse);
                form.render();
                //渲染业务选项
                $.ajax({
                    url: "/eia/eiaTask/getEiaTaskName",
                    type: "POST",
                    data: {},
                    dataType: "json",
                    disableParent: true,
                    success: function (names) {
                        var str = "";
                        for (var i = 0; i < names.length; i++) {
                            if (data.data) {
                                if (data.data.taskNo == names[i].name) {
                                    str = "<option value='" + names[i].id + "' selected>" + names[i].taskName + "</option>";
                                } else {
                                    str = "<option value='" + names[i].id + "'>" + names[i].taskName + "</option>";
                                }
                            } else {
                                str = "<option value='" + names[i].id + "'>" + names[i].taskName + "</option>";
                            }
                            $taskId.append(str);
                        }
                        form.render('select');
                    }
                });
            }
        });
    }

    //下拉树 合同类型
    $("#contractTypeDrop").dropDownForZ({
        url:'/eia/eiaDomainCode/getTree?domain='+"CONTRACT_TYPE",
        width:'99%',
        height:'350px',
        disableParent: true,
        selecedSuccess:function(data){
            if(!data.isParent){
                $("#contractTypeStr").val(data.id);
                var temp = {
                    id: data.id,
                    name: data.name
                };

                //文件类型有值后，项目金额可填写
                $('.fillMoneyBtn').css("display",'block');
                $('.show-tips').css("display",'none');
                $('#offerMoney').val('');
            }

        }
    });

    //日期
    laydate.render({
        elem: '#offerDateStr'
    });

    //选择客户
    $('.selectUserBtn').click(function () {
        var index = layer.open({
            title:' ',
            type: 2,
            shade: false,
            maxmin: true,
            skin: 'larry-green',
            area: ['100%', '100%'],
            content: '/eia/eiaClient/eiaClientSelect?clientType=client',
            success:function (layero, index) {
                var body = layer.getChildFrame('body', index);
            },
            end: function () {

            },
            min: function () {

            },
            restore: function () {

            }
        });
    });
    //选择甲方客户
    $('.selectOwnerBtn').click(function () {
        var index = layer.open({
            title:' ',
            type: 2,
            shade: false,
            maxmin: true,
            skin: 'larry-green',
            area: ['100%', '100%'],
            content: '/eia/eiaClient/eiaClientSelect?clientType=ownerClient',
            success:function (layero, index) {
                var body = layer.getChildFrame('body', index);
            },
            end: function () {

            },
            min: function () {

            },
            restore: function () {

            }
        });
    });

    //金额输入框强制小数点后4位
    var dotFour = function(value){
        var valF = value ? parseFloat(value) : parseFloat(0),
            valR = Math.round(valF*10000)/10000,
            valS = valR.toString(),
            rs = valS.indexOf('.');
        if(rs<0){
            rs = valS.length;
            valS += '.';
        }
        while (valS.length <= rs + 4){
            valS += '0';
        }
        return valS;
    };
    $('.dotFourInput').blur(function () {
        var val = $(this).val();
        $(this).val(dotFour(val));
    });

    //报价金额 按钮
    $('.fillMoneyBtn').click(function () {
        var index = layer.open({
            title:' ',
            type: 2,
            shade: false,
            maxmin: true,
            skin: 'larry-green',
            area: ['100%', '100%'],
            content: '../eiaContract/eiaOfferFillSelect',
            success:function (layero, index) {
                var body = layer.getChildFrame('body', index);
            },
            end: function () {

            },
            min: function () {
                $(".layui-layer-title").text("报价金额");
            },
            restore: function () {
                $(".layui-layer-title").text(" ");
            }
        });
    });

    //合同受托方单选设置
    //监听复选框   (code值改动两处)
    form.on('checkbox(lhcd)', function(data){
        var ifChecked = data.elem.checked,
            cDValue = ifChecked ? 0 : 1; //code值改动
        var setVal = cDValue ? "联合泰泽" : "联合赤道";

        $('#lhtz').prop('checked',!ifChecked);
        form.render('checkbox');

        $('#contractTrust').val(setVal);
    });
    form.on('checkbox(lhtz)', function(data){
        var ifChecked = data.elem.checked,
            cDValue = ifChecked ? 1 : 0; //code值改动
        var setVal = cDValue ? "联合泰泽" : "联合赤道";

        $('#lhcd').prop('checked',!ifChecked);
        form.render('checkbox');

        $('#contractTrust').val(setVal);
    });

    //重置按钮
    $('.resetBtn').click(function () {
        $('#contractTypeStr').val("");
        //隐藏项目金额点击按钮
        $('.fillMoneyBtn').css("display",'none');
        $('.show-tips').css("display",'block');
    });

    //表单提交
    form.on('submit(save)', function(data){
        var ownerClientName = $('#ownerClientName').val();
        if (ownerClientName) {
            var ownerClientId = $('#ownerClientId').val();
            var ownerClientAddress = $('#ownerClientAddress').val();
            var ownerContactName = $('#ownerContactName').val();
            var ownerContactPhone = $('#ownerContactPhone').val();
            if (!ownerClientId || !ownerClientAddress || !ownerContactName || !ownerContactPhone) {
                layer.msg('请完善甲方客户信息', {icon: 2, time: 1500});
                return false
            }
        }
        var loadingIndex = layer.load(1, {time: 10*1000,shade: 0.1});
        var eiaOfferId = $('#offerId').val();
        var actionUrl = "../eiaContract/eiaOfferSave?eiaOfferId="+eiaOfferId;
        $.post(actionUrl, data.field, function (result) {
            if (result.code == 0) {
                layer.msg('保存成功', {icon: 1, time: 1000,shade: 0.1}, function () {
                    $('#offerNo').val(result.data.offerNo);
                    $('#offerId').val(result.data.id);
                    pageType = 1;
                    $pageTitle.html('编辑报价');
                    $('.resetBtn').hide();
                    parent.layui.table.reload("eiaOfferList");
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.layer.close(index)
                });
            } else {
                layer.msg(data.msg, {icon: 2, time: 1000,shade: 0.1});
            }
            layer.close(loadingIndex);
        });
        return false;
    })

});