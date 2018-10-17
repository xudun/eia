layui.use(['layer', 'form', 'table'], function () {
    var $ = layui.$,
        layer = layui.layer,
        form = layui.form,
        $form = $('form');
    var eiaLabOfferId = parent.$("#eiaLabOfferId").val();
    $("#eiaLabOfferId").val(eiaLabOfferId);
    var labTestParamGroupId = parent.$("#labTestParamGroupId").val();
    $.ajax({
        url: "../eiaLabOfferPlan/getEiaLabOfferPlanGroupDataMap?eiaLabOfferId=" + eiaLabOfferId + "&labTestParamGroupId=" + labTestParamGroupId,
        type: "post",
        cache: false,
        async: false,
        success: function (result) {
            if (result.data) {
                $("#groupName").val(result.data.groupName);
                $("#groupFee").val(result.data.groupFee);
                $("#labTestParamGroupId").val(labTestParamGroupId);
                $("#freqNum").val(result.data.freqNum);
                $("#dayNum").val(result.data.dayNum);
            }
        }
    });
    /**
     * 选择检测套餐名称
     */
    $("#paramGroupSelect").click(function(){
        var pageUrl = "../eiaLabOfferPlan/eiaParamGroupSelect";
        var winWidth = document.documentElement.clientWidth;
        var area = ['85%', '80%'];
        if (winWidth < 1300) {
            area = ['100%', '100%'];
        }
        var entLayer = layer.open({
            title: " ",
            type: 2,
            //shade: false,
            maxmin: true,
            skin:'larry-green',
            area: area,
            content: pageUrl,
            end:function(){
                $("#type").val("");
            },
            min:function(){
                $(".layui-layer-title").text("选择套餐名称");
            },
            restore:function(){
                $(".layui-layer-title").text(" ");
            }
        });
    });
    //提交表单
    form.on('submit(save)', function (data) {
        var loadingIndex = layer.load(1, {time: 10*1000,shade: 0.1});
        var actionUrl = "../eiaLabOfferPlan/eiaLabOfferPlanGroupSave?sampleType="+"采样";
        $.post(actionUrl, data.field, function (data) {
            if (data.code == 0) {
                layer.msg('保存成功', {icon: 1, time: 1000,shade: 0.1} ,function () {
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.parent.layui.table.reload("eiaLabOfferList");
                    parent.layui.table.reload("eiaLabOfferPlanList");
                    parent.layer.close(index);
                });
            } else {
                layer.msg(data.msg, {icon: 2, time: 1000,shade: 0.1});
            }
            layer.close(loadingIndex);
        });
        return false;
    });
});