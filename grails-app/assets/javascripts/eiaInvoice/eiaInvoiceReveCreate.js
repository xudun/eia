layui.use(['jquery', 'layer', 'form','laydate'], function(){
    var $ = layui.jquery,
        form = layui.form,
        laydate = layui.laydate,
        layer = layui.layer;

    //日期
    laydate.render({
        elem: '#estimateDateS'
    });
    //日期
    laydate.render({
        elem: '#billDateS'
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
        var actionUrl = "../eiaInvoice/invoiceRevenueSave";
        $.post(actionUrl, data.field, function (data) {
            if (data.code == 0) {
                layer.msg('保存成功', {icon: 1, time: 1000,shade: 0.1}, function () {
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.layui.table.reload("eiaContractList");
                    parent.layer.close(index);
                    $.post("../eiaInvoice/eiaInvoiceRevNum", {}, function (data) {
                        if (data.code == 0) {
                            parent.document.getElementById('kpqr').innerText=data.data;
                        }
                    });
                    $.post("../eiaInvoice/eiaInvoiceOutNum", {}, function (data) {
                        if (data.code == 0) {
                            parent.document.getElementById('czqr').innerText=data.data
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