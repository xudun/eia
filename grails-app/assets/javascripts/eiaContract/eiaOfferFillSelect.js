layui.use(['jquery', 'form'], function () {
    var $ = layui.jquery,
        form = layui.form;

    var $dynMoneyContainers = $('.dynMoneyInputs .layui-col-xs6');

    var contractTypeStr = parent.$("#contractTypeStr").val();
    //渲染动态数据项
    $.ajax({
        url: "../eiaContract/getContractMoneyList",
        type: "POST",
        data: {"contractTypeCode": contractTypeStr},
        dataType: "json",
        async: true,
        success: function (res) {
            var data = res.data;
            $dynMoneyContainers.empty();
            var con_index = 0;
            for (var i = 0; i < data.length; i++) {
                // console.log(data[i]);
                moneyFillInputs[data[i]].create.call(this, "", $dynMoneyContainers.eq(con_index++ % 2));
            }
            //渲染已填数据
            $("#reportFees").val(parent.$("#reportFees").val());
            $("#enviroMonitoringFee").val(parent.$("#enviroMonitoringFee").val());
            $("#expertFee").val(parent.$("#expertFee").val());
            $("#groundWater").val(parent.$("#groundWater").val());
            $("#otherFee").val(parent.$("#otherFee").val());
            $("#specialFee").val(parent.$("#specialFee").val());
            $("#serveFee").val(parent.$("#serveFee").val());
            $("#ecoDetectFee").val(parent.$("#ecoDetectFee").val());
            $("#preIssCertFee").val(parent.$("#preIssCertFee").val());
            $("#preSurvCertFee").val(parent.$("#preSurvCertFee").val());
            $("#certServeFee").val(parent.$("#certServeFee").val());
            if (contractTypeStr == 'CD') {
                $('#enviroMonitoringFee').closest('.layui-form-item').find('.layui-form-label').text('地勘加监测费用');
            }
        }
    });


    //表单提交
    form.on('submit(save)', function (data) {
        console.log(data.field); //当前容器的全部表单字段，名值对形式：{name: value}
        var total = 0;
        for (var name in data.field) {
            total += parseFloat(data.field[name]);
        }
        parent.$("#offerMoney").val(dotFour(total));
        parent.$("#reportFees").val(dotFour(data.field.reportFees));
        parent.$("#groundWater").val(dotFour(data.field.groundWater));
        parent.$("#otherFee").val(dotFour(data.field.otherFee));
        parent.$("#enviroMonitoringFee").val(dotFour(data.field.enviroMonitoringFee));
        parent.$("#expertFee").val(dotFour(data.field.expertFee));
        parent.$("#specialFee").val(dotFour(data.field.specialFee));
        parent.$("#serveFee").val(dotFour(data.field.serveFee));
        parent.$("#ecoDetectFee").val(dotFour(data.field.ecoDetectFee));
        parent.$("#preIssCertFee").val(dotFour(data.field.preIssCertFee));
        parent.$("#preSurvCertFee").val(dotFour(data.field.preSurvCertFee));
        parent.$("#certServeFee").val(dotFour(data.field.certServeFee));
        //提交成功后关闭自身
        var index = parent.layer.getFrameIndex(window.name);
        parent.layer.close(index);

        // return false;
    })
});