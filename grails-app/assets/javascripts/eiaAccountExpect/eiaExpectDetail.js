layui.use(['jquery', 'form'], function(){
    var $ = layui.jquery,
        form = layui.form;
    var eiaAccountExpectId = parent.$("#eiaAccountExpectId").val()
    //回显数据
    $.ajax({
        url:"/eia/eiaAccountExpect/eiaAccountExpectMap?eiaAccountExpectId="+eiaAccountExpectId,
        type:"POST",
        data:{},
        dataType: "json",
        success: function (data) {
            $("#contractName").text(data.data.contractName);
            $("#contractNo").text(data.data.contractNo);
            $("#expectIncomeMoney").text(data.data.expectIncomeMoney);
            $("#expectInvoiceMoney").text(data.data.expectInvoiceMoney);
            $("#accountState").text(data.data.accountState);
            $("#expertFee").text(data.data.expertFee);
            $("#monitorFee").text(data.data.monitorFee);
            $("#otherFee").text(data.data.otherFee);
            $("#expectPeriod").text(data.data.expectPeriod);


        }
    });

});