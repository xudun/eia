layui.use(['jquery', 'layer', 'form','laydate'], function(){
    var $ = layui.jquery,
        form = layui.form,
        layer = layui.layer;
        laydate = layui.laydate;
    var eiaContractId = $('#contractId').val();

    var $offerNo = $('#offerNo'),
        $contactName = $('#contactName'),
        $contractTypeDrop = $('#contractTypeDrop'),
        $contractType = $('#contractType'),
        $contractTypeStr = $('#contractTypeStr'),
        $contractUse = $('#contractUse'),
        $contractMoney = $('#contractMoney'),
        $contractDateStr = $('#contractDateStr'),
        $eiaClientName = $('#eiaClientName'),
        $clientAddress = $('#clientAddress'),
        $contractName = $('#contractName'),
        $reportFees = $('#reportFees'),
        $offerMoney = $('#offerMoney'),
        $enviroMonitoringFee = $('#enviroMonitoringFee'),
        $groundWater = $('#groundWater'),
        $expertFee = $('#expertFee'),
        $otherFee = $('#otherFee'),
        $contactPhone = $('#contactPhone'),
        $taskId = $('#taskId'),
        $province = $('#province');
        $serveFee = $('#serveFee');
        $specialFee = $('#specialFee');
        $ecoDetectFee = $('#ecoDetectFee');
        $preIssCertFee = $('#preIssCertFee');
        $preSurvCertFee = $('#preSurvCertFee');
        $certServeFee = $('#certServeFee');

    //工具函数：
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
    //日期
    laydate.render({
        elem: '#contractDate'
    });
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
    //金额可填写
    $('.fillMoneyBtn').css("display", 'block');
    $('.show-tips').css("display", 'none');
  /*  //生成合同编号
    $.ajax({
        url:"data/createContractNo.json",
        type:"POST",
        data:{},
        dataType: "json",
        success: function (data) {
            $contractNo.val(data.contractNo);
        }
    });*/


    //渲染省份选项
    $.ajax({
        url:'/eia/eiaDomainCode/getTree?domain='+"PROVINCE_CODE",
        type:"POST",
        data:{},
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
    //渲染业务选项
    $.ajax({
        url:"/eia/eiaTask/getEiaTaskName",
        type:"POST",
        data:{},
        dataType: "json",
        disableParent: true,
        success: function (data) {
            for(var i=0; i<data.length; i++){
                var str = "<option value='"+data[i].id+"'>"+data[i].taskName+"</option>";
                $taskId.append(str);
            }
            form.render('select');
        }
    });
    var eiaContractId = $('#contractId').val();
    //回显报价数据
    $.ajax({
        url:"/eia/eiaContract/eiaOfferDataMap?eiaOfferId="+eiaContractId,
        type:"POST",
        data:{},
        dataType: "json",
        success: function (data) {
            console.log(data);
            $offerNo.val(data.data.offerNo);
            $contractName.val(data.data.offerName);
            $contractTypeDrop.val(data.data.contractType);
            $contractTypeStr.val(data.data.contractTypeCode);
            $eiaClientName.val(data.data.eiaClientName);
            $clientAddress.val(data.data.clientAddress);
            $contactPhone.val(data.data.contactPhone);
            $contractMoney.val(data.data.offerMoney);
            $contractDateStr.val(data.data.offerDate.substring(0,10));
            $reportFees.val(data.data.reportFees);
            $enviroMonitoringFee.val(data.data.enviroMonitoringFee);
            $groundWater.val(data.data.groundWater);
            $expertFee.val(data.data.expertFee);
            $otherFee.val(data.data.otherFee);
            $offerMoney.val(data.data.offerMoney);
            $specialFee.val(data.data.specialFee);
            $serveFee.val(data.data.serveFee);
            $ecoDetectFee.val(data.data.ecoDetectFee);
            $preIssCertFee.val(data.data.preIssCertFee);
            $preSurvCertFee.val(data.data.preSurvCertFee);
            $certServeFee.val(data.data.certServeFee);
            selectOptionByVal($contractUse, data.contractUse);
            selectOptionByVal($province, data.data.province);
            selectOptionByVal($taskId,data.data.taskId);
            form.render('select');
        }
    });


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
                $("#contractType").val(JSON.stringify(temp));
                //文件类型有值后，项目金额可填写
                $('.fillMoneyBtn').css("display", 'block');
                $('.show-tips').css("display", 'none');
                $('#offerMoney').val('');
            }
        }
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
            content: 'eiaClientSelect.html',
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
        var valF = parseFloat(value),
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

    //合同金额 按钮
    $('.fillMoneyBtn').click(function () {
        var index = layer.open({
            title: ' ',
            type: 2,
            shade: false,
            maxmin: true,
            skin: 'larry-green',
            area: ['100%', '100%'],
            content: '../eiaContract/eiaOfferFillSelect',
            success: function (layero, index) {
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
    //表单提交
    form.on('submit(save)', function(data){
        var loadingIndex = layer.load(1, {time: 10*1000,shade: 0.1});
        var eiaOfferId = $('#contractId').val();
        var actionUrl = "../eiaContract/eiaContractGene?eiaOfferId="+eiaOfferId;
        $.post(actionUrl, data.field, function (data) {
            if (data.code == 0) {
                layer.msg('保存成功', {icon: 1, time: 1000}, function () {
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.layui.table.reload("eiaOfferList");
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