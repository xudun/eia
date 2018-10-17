layui.use(['jquery', 'layer', 'form','laydate'], function(){
    var $ = layui.jquery,
        form = layui.form,
        laydate = layui.laydate,
        layer = layui.layer;
    var eiaInvoiceId = parent.$("#eiaInvoiceId").val();
    //日期
    laydate.render({
        elem: '#estimateDateS'
    });
    //日期
    laydate.render({
        elem: '#billDateS'
    });
    //日期
    laydate.render({
        elem: '#estDateS'
    });
    form.on('radio(invoiceType)', function(data){
        if("增值税专用发票"==data.value){
            $('.verify-item').find('.layui-form-label span').text("* ");
            $('.verify-item').find('.layui-input-block input').attr('lay-verify',"required");

        }else {
            $('.verify-item').find('.layui-form-label span').text("");
            $('.verify-item').find('.layui-input-block input').attr('lay-verify',"");
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
            content: '/eia/eiaClientConfig/eiaCliConfigIndex',
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
    $.ajax({
        url: "../eiaInvoice/getEiaInvoiceDataMap?eiaInvoiceId=" + eiaInvoiceId,
        type:"post",
        cache: false,
        async: false,
        success: function (result) {
            if(result.data){
                if(result.data.invoiceType=="增值税专用发票"){
                    $("#zyfp").attr("checked","checked");
                }else if(result.data.invoiceType=="增值税普通发票"){
                    $("#ptfp").attr("checked","checked");
                }
                if("增值税专用发票"== result.data.invoiceType){
                    $('.verify-item').find('.layui-form-label span').text("* ");
                    $('.verify-item').find('.layui-input-block input').attr('lay-verify',"required");

                }else {
                    $('.verify-item').find('.layui-form-label span').text("");
                    $('.verify-item').find('.layui-input-block input').attr('lay-verify',"");
                }
                if(result.data.realMoney=="已进账"){
                    $("#yjz").attr("checked","checked");
                }else if(result.data.realMoney=="未进账"){
                    $("#wjz").attr("checked","checked");
                }
                if(result.data.billType=="咨询费"){
                    $("#zxf").attr("checked","checked");
                }else if(result.data.realMoney=="其他"){
                    $("#qt").attr("checked","checked");
                }
                if(result.data.billOrg=="联合赤道"){
                    $("#cd").attr("checked","checked");
                }else if(result.data.billOrg=="联合泰泽"){
                    $("#tz").attr("checked","checked");
                }
                form.render('checkbox');
                form.render('radio');
            }
        }
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

    //重置按钮
    $('.resetBtn').click(function () {
        $('#contractTypeStr').val("");
    });

    //表单提交
    form.on('submit(save)', function(data){
        var loadingIndex = layer.load(1, {time: 10*1000,shade: 0.1});
        var actionUrl = "../eiaInvoice/invoiceRevenueUpate";
        $.post(actionUrl, data.field, function (data) {
            if (data.code == 0) {
                layer.msg('保存成功', {icon: 1, time: 1000,shade: 0.1}, function () {
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.layui.table.reload("eiaContractList");
                    parent.layer.close(index)
                    $.post("../eiaInvoice/eiaInvoiceRevNum", {}, function (data) {
                        if (data.code == 0) {
                            document.getElementById('kpqr').innerText=data.data;
                        }
                    });
                    $.post("../eiaInvoice/eiaInvoiceOutNum", {}, function (data) {
                        if (data.code == 0) {
                            document.getElementById('czqr').innerText=data.data
                        }
                    });
                });
            } else {
                layer.msg(data.msg, {icon: 2, time: 1000,shade: 0.1});
            }
            layer.close(loadingIndex);
        });
        return false;
    })

});