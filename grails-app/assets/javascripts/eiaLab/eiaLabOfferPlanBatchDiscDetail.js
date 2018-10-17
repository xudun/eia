layui.use(['layer', 'form'],function(){
    var $ = layui.$,
        layer = layui.layer,
        form = layui.form;
    /**
     * 基本信息form数据加载
     */
    var eiaLabOfferId = parent.$("#eiaLabOfferId").val();
    $.ajax({
        url: "../eiaLabOffer/getEiaLabOfferDataMap?eiaLabOfferId=" + eiaLabOfferId,
        type:"post",
        cache: false,
        async: false,
        success: function (result) {
            if (result.data) {
                $("#eiaLabOfferId").val(result.data.id);
                $("#maxSampleFee").val(result.maxSampleFee);
            }
        }
    });
    /**
     * 基本信息form提交
     */
    form.on('submit(save)',function(data){
        var loadingIndex = layer.load(1, {time: 10*1000,shade: 0.1});
        var actionUrl = "../eiaLabOfferPlan/eiaLabOfferPlanBatchDiscount";
        $.post(actionUrl,data.field,function(result){
            if (result.code == 0) {
                layer.msg('保存成功', {icon: 1, time: 1000,shade: 0.1}, function () {
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.layui.table.reload("eiaLabOfferPlanList");
                    parent.layer.close(index);
                });
            } else {
                layer.msg(result.msg, {icon: 2, time: 1000,shade: 0.1});
            }
            layer.close(loadingIndex);
        });
        return false;
    });
});