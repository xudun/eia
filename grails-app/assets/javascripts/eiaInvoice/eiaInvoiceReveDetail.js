layui.use(['jquery', 'form'], function(){
    var $ = layui.jquery,
        form = layui.form;
    var eiaInvoiceId = parent.$('#eiaInvoiceId').val();
    //回显数据
    $.ajax({
        url:"/eia/eiaInvoice/getEiaInvoiceDetails?eiaInvoiceId="+eiaInvoiceId,
        type:"POST",
        data:{},
        dataType: "json",
        success: function (data) {
            $("#contractName").text(data.data.contractName);
            $("#contractMoney").text(data.data.contractMoney);
            $("#contractNo").text(data.data.contractNo);
            $("#billMoney").text(data.data.billMoney);
            $("#billDate").text(data.billDate);
            $("#billOrg").text(data.data.billOrg);
            $("#clientAccountName").text(data.data.clientAccountName);
            $("#taxRegCode").text(data.data.taxRegCode);
            $("#bankName").text(data.data.bankName);
            $("#bankAccount").text(data.data.bankAccount);
            $("#billType").text(data.data.billType);
            $("#invoiceType").text(data.data.invoiceType);
            $("#costTypes").text(data.data.costTypes);
            $("#estDate").text(data.estDate);
            $("#realMoney").text(data.data.realMoney);
            $("#memo").text(data.data.memo);
            $("#addrTel").text(data.data.addrTel);
            $("#billOtherType").text(data.data.billOtherType);

        }
    });

});