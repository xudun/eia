layui.use(['jquery', 'layer', 'form','laydate'], function(){
    var $ = layui.jquery,
        form = layui.form,
        laydate = layui.laydate,
        layer = layui.layer;
    //日期
    var contractId = parent.$('#contractId').val();
    $('#contractId').val(contractId);
    var eiaAccountExpectId = parent.$("#eiaAccountExpectId").val()
    laydate.render({
        elem: '#expectPeriod',
        type: 'month',
        change: function(value, date, endDate){
        show: true ,//直接显示
        console.log(value); //得到日期生成的值，如：2017-08-18
        console.log(date); //得到日期时间对象：{year: 2017, month: 8, date: 18, hours: 0, minutes: 0, seconds: 0}
        console.log(endDate); //得结束的日期时间对象，开启范围选择（range: true）才会返回。对象成员同上。
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
    $(function () {

//表单提交
        $.ajax({
            url:"../eiaAccountExpect/eiaAccountExpectMap?eiaAccountExpectId="+eiaAccountExpectId,
            type:"POST",
            data:{},
            dataType: "json",
            success: function (data) {
                var date = new Date();
                var year = date.getFullYear();

                var month = date.getMonth()+1;
                if(month>9){
                    month = month ;
                }else{
                    month = "0"+month;
                }
                var yearMonth = year +"-"+month;
                if(eiaAccountExpectId){
                    $('#expectPeriod').val(data.data.expectPeriod);
                }else{
                    $('#expectPeriod').val(yearMonth);
                }
                $('#expectInvoiceMoney').val(data.data.expectInvoiceMoney);
                $('#expertFee').val(data.data.expertFee);
                $('#monitorFee').val(data.data.monitorFee);
                $('#otherFee').val(data.data.otherFee);
                $('#expectIncomeMoney').val(data.data.expectIncomeMoney);
            }
        });

        //表单提交
        form.on('submit(save)', function(data){
            var actionUrl = "../eiaAccountExpect/eiaAccountExpectSave?eiaAccountExpectId="+eiaAccountExpectId;
            var loadingIndex = layer.load(1, {time: 10*1000,shade: 0.1});
            $.post(actionUrl, data.field, function (data) {
                if (data.code == 0) {
                    layer.msg('保存成功', {icon: 1, time: 1000,shade: 0.1}, function () {
                        $.post("../eiaAccountExpect/eiaExpectNum", {}, function (data) {
                            if (data.code == 0) {
                                parent.document.getElementById('czyj').innerText=data.data
                            }else{
                                parent.document.getElementById('czyj').innerText=0;
                            }
                        });
                        var index = parent.layer.getFrameIndex(window.name);
                        parent.layui.table.reload("eiaContractList");
                        parent.layer.close(index)
                    });
                } else if(data.code == -1){
                    layer.msg('当月预计已存在,不能操作！', {icon:7});

                    return;
                }else {
                    layer.msg(data.msg, {icon: 2, time: 1000,shade: 0.1});
                }
                layer.close(loadingIndex);
            });
            return false;
        })
    });


});