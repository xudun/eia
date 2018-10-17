layui.use(['jquery', 'layer', 'form','laydate'], function(){
    var $ = layui.jquery,
        form = layui.form,
        laydate = layui.laydate,
        layer = layui.layer;
    var $costTypes = $('#costTypes'),
        $thereNoBill = $('#thereNoBill')
    //日期
    laydate.render({
        elem: '#noteIncomeDateS'
    });
    /**
     * 费用类型
     */
    $.ajax({
        url:'/eia/eiaDomainCode/getTreeInvoice?domain='+"INVOICE",
        type:"POST",
        data:{},
        async: true,
        dataType: "json",
        success: function (data) {
            for(var i=0; i<data.length; i++){
                var str = "<option value='"+data[i].id+"'>"+data[i].name+"</option>";
                $costTypes.append(str);
            }
            form.render();
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
        $('#costTypes').val("");
    });

    //表单提交
    form.on('submit(save)', function(data){
        var actionUrl = "../eiaIncomeOut/eiaIncomeOutSave";
        var loadingIndex = layer.load(1, {time: 10*1000,shade: 0.1});
        $.post(actionUrl, data.field, function (data) {
            if (data.code == 0) {
                layer.msg('保存成功', {icon: 1, time: 1000,shade: 0.1}, function () {
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
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.layui.table.reload("eiaContractList");
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