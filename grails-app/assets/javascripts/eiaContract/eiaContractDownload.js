layui.use(['jquery','form'], function(){
    var $ = layui.jquery,
        form = layui.form;

    //合同受托方单选设置
    //监听复选框   (code值改动两处)
    form.on('checkbox(contractSupply)', function(data){
        var ifChecked = data.elem.checked,
            cDValue = ifChecked ? "contractSupplyTmp" : ""; //code值改动

        if(ifChecked){
            $('#contractChange').prop('checked',!ifChecked);
            $('#contractStop').prop('checked',!ifChecked);
        }
        form.render('checkbox');
        $('#tempType').val(cDValue);
    });
    form.on('checkbox(contractChange)', function(data){
        var ifChecked = data.elem.checked,
            cDValue = ifChecked ? "contractChangeTmp" : ""; //code值改动

        if(ifChecked){
            $('#contractSupply').prop('checked',!ifChecked);
            $('#contractStop').prop('checked',!ifChecked);
        }
        form.render('checkbox');
        $('#tempType').val(cDValue);
    });
    form.on('checkbox(contractStop)', function(data){
        var ifChecked = data.elem.checked,
            cDValue = ifChecked ? "contractStopTmp" : ""; //code值改动

        if(ifChecked){
            $('#contractSupply').prop('checked',!ifChecked);
            $('#contractChange').prop('checked',!ifChecked);
        }
        form.render('checkbox');
        $('#tempType').val(cDValue);
    });


    //表单提交
    form.on('submit(save)', function(data){
        var loadingIndex = layer.load(1, {time: 10*1000,shade: 0.1});
        var contractId = $('#contractId').val();
        window.location.href = "../exportContract/downloadContractProtocol?eiaContractId=" + contractId+"&tempType="+ $('#tempType').val();
        layer.msg('正在导出...', {icon: 16, shade: 0.01},function(){
            var index = parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
        });
        layer.close(loadingIndex);
    })
});