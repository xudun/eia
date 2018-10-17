layui.use(['jquery', 'layer', 'form'], function(){
    var $ = layui.jquery,
        layer = layui.layer,
        form = layui.form;

    //选择套餐按按钮
    $('#paramGroupSelect').click(function () {
        var pageUrl = '../eiaLabOfferPlan/eiaPlanCalGroupSelect';
        var index = layer.open({
            title:' ',
            type: 2,
            shade: false,
            maxmin: true,
            skin: 'larry-green',
            area: ['100%', '100%'],
            content: pageUrl,
            success:function (layero, index) {
                var body = layer.getChildFrame('body', index);
            },
            end: function () {

            },
            min: function () {
                $(".layui-layer-title").text("新增套餐");
            },
            restore: function () {
                $(".layui-layer-title").text(" ");
            }
        });
    });

    //提交表单
    form.on('submit(save)', function (data) {
        console.log(data.field);
        $.ajax({
            url: "../eiaLabOfferPlan/getParamGroupDataMap?labTestParamGroupId=" + $('#labTestParamGroupId').val() + "&pointNum=" + $('#pointNum').val(),
            type: "get",
            cache: false,
            async: false,
            success: function (result) {
                console.log(result);
                var getArr = result.data;
                var cur_list_data = JSON.parse(parent.$("#eiaPlanCalListData").val());
                cur_list_data = cur_list_data.concat(getArr);
                parent.$("#eiaPlanCalListData").val(JSON.stringify(cur_list_data));

                var count_all_sub = 0;
                for (var i = 0; i < getArr.length; i++) {
                    count_all_sub += parseInt(getArr[i].subTotal);
                }

                //计算主页内的主小计
                var cur_subtotal = parseInt(parent.$("#subtotal").text());
                parent.$("#subtotal").text(cur_subtotal+count_all_sub);

                var index = parent.layer.getFrameIndex(window.name);
                parent.layer.close(index);
            }
        });
        // return false;
    });

});