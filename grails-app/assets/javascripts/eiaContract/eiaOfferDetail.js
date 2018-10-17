layui.use(['jquery', 'form'], function(){
    var $ = layui.jquery,
        form = layui.form;

    var eiaOfferId = $('#offerId').val();

    var $dynMoneyContainers = $('.dynMoneyInputs .layui-col-xs6');

    //回显数据
    $.ajax({
        url:"/eia/eiaContract/eiaOfferDataMap?eiaOfferId="+eiaOfferId,
        type:"POST",
        data:{},
        dataType: "json",
        success: function (result) {
            var data = result.data;
            $("#offerNo").text(data.offerNo);
            $("#offerDate").text(result.offerDate);
            $("#offerMoney").find('.cost-num').text(data.offerMoney);
            $("#eiaClientName").text(data.eiaClientName);
            $("#clientAddress").text(data.clientAddress);
            $("#offerName").text(data.offerName);
            $("#contactPhone").text(data.contactPhone);
            $("#contactName").text(data.contactName);
            $("#contractType").text(data.contractType).attr('code',data.contractTypeCode);
            $("#taskName").text(data.taskName);
            $("#province").text(result.province);
            $("#contractUse").text(result.contractUse);
            $("#ownerClientName").text(data.ownerClientName);
            $("#ownerClientAddress").text(data.ownerClientAddress);
            $("#ownerContactName").text(data.ownerContactName);
            $("#ownerContactPhone").text(data.ownerContactPhone);
            $("#contractTrust").text(data.contractTrust);
            $("#projectScale").find('.cost-num').text(data.projectScale);
        }
    });


    //渲染金额子项数据
    $.ajax({
        url:"/eia/eiaContract/getContractMoney",
        type:"POST",
        data:{eiaOfferId: eiaOfferId},
        dataType: "json",
        async: true,
        success: function (result) {
            var data = result.data;
            $dynMoneyContainers.empty();
            var con_index = 0;
            for(var name in data){
                moneyFillInputs[name].show.call(this,data[name], $dynMoneyContainers.eq(con_index++%2));
            }
            if($("#contractType").attr('code') == "CD"){
                $('#enviroMonitoringFee').closest('.layui-form-item').find('.layui-form-label').text('地勘加监测费用');
            }
        }
    });

});