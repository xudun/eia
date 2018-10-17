layui.use(['jquery', 'form'], function(){
    var $ = layui.jquery,
        form = layui.form;

    var $dynMoneyContainers = $('.dynMoneyInputs .layui-col-xs6');

    var fileTypeId = JSON.parse(parent.$("#fileType").val()).id;
    //渲染动态数据项
    $.ajax({
        url:"../eiaProject/getProMoneyProp",
        type:"POST",
        data:{"fileTypeId":fileTypeId},
        dataType: "json",
        async: true,
        success: function (res) {
            var data = res.data
            $dynMoneyContainers.empty();
            var con_index = 0;
            for(var i=0; i<data.length; i++){
                // console.log(data[i]);
                moneyFillInputs[data[i]].create.call(this,"", $dynMoneyContainers.eq(con_index++%2));
            }
            //渲染已填数据
            $("#projectComfee").val(parent.$("#projectComfee").val());
            $("#groundwaterFee").val(parent.$("#groundwaterFee").val());
            $("#otherFee").val(parent.$("#otherFee").val());
            $("#environmentalFee").val(parent.$("#environmentalFee").val());
            $("#expertFee").val(parent.$("#expertFee").val());
            $("#specialFee").val(parent.$("#specialFee").val());
            $("#detectFee").val(parent.$("#detectFee").val());
            $("#projectMoney").val(parent.$("#projectMoney").val());
            $("#preIssCertFee").val(parent.$("#preIssCertFee").val());
            $("#preSurvCertFee").val(parent.$("#preSurvCertFee").val());
            $("#certServeFee").val(parent.$("#certServeFee").val());
        }
    });


    //表单提交
    form.on('submit(save)', function(data){
        console.log(data.field); //当前容器的全部表单字段，名值对形式：{name: value}
        var total = 0;
        for(var name in data.field){
            total += parseFloat(data.field[name]);
        }
        parent.$("#projectMoney").val(dotFour(total));
        parent.$("#projectComfee").val(dotFour(data.field.projectComfee));
        parent.$("#groundwaterFee").val(dotFour(data.field.groundwaterFee));
        parent.$("#otherFee").val(dotFour(data.field.otherFee));
        parent.$("#environmentalFee").val(dotFour(data.field.environmentalFee));
        parent.$("#expertFee").val(dotFour(data.field.expertFee));
        parent.$("#specialFee").val(dotFour(data.field.specialFee));
        parent.$("#detectFee").val(dotFour(data.field.detectFee));
        parent.$("#preIssCertFee").val(dotFour(data.field.preIssCertFee));
        parent.$("#preSurvCertFee").val(dotFour(data.field.preSurvCertFee));
        parent.$("#certServeFee").val(dotFour(data.field.certServeFee));

        //提交成功后关闭自身
        var index = parent.layer.getFrameIndex(window.name);
        parent.layer.close(index);

        // return false;
    })
});