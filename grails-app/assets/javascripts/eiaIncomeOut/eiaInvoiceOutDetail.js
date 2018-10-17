layui.use(['jquery', 'form'], function(){
    var $ = layui.jquery,
        form = layui.form;
    var eiaIncomeOutId = parent.$('#eiaIncomeOutId').val();
    //回显数据
    $.ajax({
        url:"/eia/eiaIncomeOut/eiaIncomeOutDataMap?eiaIncomeOutId="+eiaIncomeOutId,
        type:"POST",
        data:{},
        dataType: "json",
        success: function (data) {
            $("#contractName").text(data.data.contractName);
            $("#contractNo").text(data.data.contractNo);
            $("#costTypes").text(data.data.costTypes);
            $("#noteIncomeMoney").text(data.data.noteIncomeMoney);
            $("#accountState").text(data.data.accountState);
            $("#noteIncomeDate").text(data.noteIncomeDate);
            $("#thereNoBill").text(data.data.thereNoBill);


        }
    });

});