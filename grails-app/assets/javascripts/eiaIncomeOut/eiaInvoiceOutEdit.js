layui.use(['jquery', 'layer', 'form','laydate'], function(){
    var $ = layui.jquery,
        form = layui.form,
        laydate = layui.laydate,
        layer = layui.layer;
    var $costTypes = $('#costTypes'),
        $thereNoBill = $('#thereNoBill')
        $noteIncomeMoney = $('#noteIncomeMoney')

    //日期
    laydate.render({
        elem: '#noteIncomeDateS'
    });
    /**
     * 有无票据
     */
    $.ajax({
        url:'/eia/eiaDomainCode/getTree?domain='+"THERE_NO_BILL",
        type:"POST",
        data:{},
        async: true,
        dataType: "json",
        success: function (data) {
            for(var i=0; i<data.length; i++){
                var str = "<option value='"+data[i].id+"'>"+data[i].name+"</option>";
                $thereNoBill.append(str);
            }
            form.render();
        }
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
    var eiaIncomeOutId = parent.$('#eiaIncomeOutId').val();
    $.ajax({
        url:"/eia/eiaIncomeOut/eiaIncomeOutDataMap?eiaIncomeOutId="+eiaIncomeOutId,
        type:"POST",
        data:{},
        dataType: "json",
        async: true,
        success: function (data) {
            $costTypes.val(data.data.costTypes);
            $thereNoBill.val(data.data.thereNoBill);
            form.render('select');
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
        $('#noteIncomeMoney').val("");
        $('#noteIncomeDateS').val("");
        $('#memo').val("");
    });

    //表单提交
    form.on('submit(save)', function(data){
        var loadingIndex = layer.load(1, {time: 10*1000,shade: 0.1});
        var actionUrl = "../eiaIncomeOut/eiaInvoiceIncomeUpdate?eiaIncomeOutId="+eiaIncomeOutId;
        $.post(actionUrl, data.field, function (data) {
            if (data.code == 0) {
                layer.msg('保存成功', {icon: 1, time: 1000,shade: 0.1}, function () {
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