layui.use(['layer', 'form', 'table'], function () {
    var $ = layui.$,
        layer = layui.layer;
    var eiaLabOfferId = parent.$("#eiaLabOfferId").val();
    var eiaLabOfferPlanId = parent.$("#eiaLabOfferPlanId").val();
    $.ajax({
        url:"../eiaLabOfferPlan/getEiaLabOfferPlanDataMap?eiaLabOfferId="+eiaLabOfferId+"&eiaLabOfferPlanId="+eiaLabOfferPlanId,
        type: "get",
        cache: false,
        async: false,
        success: function (result) {
            $("#paramNameCn").text(result.data.paramNameCn);
            $("#baseName").text(result.data.baseName);
            $("#pointNum").text(result.data.pointNum);
            $("#freqNum").text(result.data.freqNum);
            $("#dayNum").text(result.data.dayNum);
            $("#referenceLimitStandard").text(result.data.referenceLimitStandard);
            $("#referenceLimit").text(result.data.referenceLimit);
            $("#discount").text(result.data.discount);
            $("#displayOrder").text(result.data.displayOrder);
            $("#memo").text(result.data.memo);
            $("#maxSchemeMoney").text(result.data.maxSchemeMoney);
            if (result.data.ifGroup) {
                $("#ifGroup").text("是");
            } else {
                $("#ifGroup").text("否");
            }
            if (result.data.groupName) {
                $("#groupName").text(result.data.groupName);
            } else {
                $("#groupName").text("无");
            }
            if (result.data.ifYxTest) {
                $("#ifShowScheme").removeClass("display-none");
                $("#ifShowMoney").removeClass("display-none");
                $("#ifShowDiscount").removeClass("display-none");
            }
        }
    });
});