/**
 *Created by HSH on 2018/7/3 13:55
 */

layui.use(['jquery', 'layer', 'form', 'element','laydate'], function () {
    var $ = layui.jquery,
        layer = layui.layer,
        form = layui.form,
        laydate = layui.laydate,
        element = layui.element;
    var eiaProjectId = getParamFromUrl(location.href,"eiaProjectId")
    //日期
    laydate.render({
        elem: '#approvalDate'
        ,value:new Date()
    });
    //表单提交
    form.on('submit(save)', function (data) {
        var wordNoStri = encodeURI($("#wordNo").val());
        var actionUrl = '/eia/eiaProject/eiaPrintFileArc?approvalDate='+$("#approvalDate").val()+"&wordNo="+wordNoStri+"&eiaProjectId="+eiaProjectId;
        var index = layer.open({
            title: ' ',
            type: 2,
            shade: false,
            maxmin: true,
            skin: 'larry-green',
            area: ['100%', '100%'],
            content: actionUrl,
            success: function (layero, index) {
                var body = layer.getChildFrame('body', index);
            },
            end: function () {
            },
            min: function () {
                $(".layui-layer-title").text("打印归档");
            },
            restore: function () {
                $(".layui-layer-title").text(" ");
            }
        });
        return false;
    });


});

