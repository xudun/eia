layui.use(['jquery', 'form', 'layer', 'laydate'], function () {
    var $ = layui.jquery,
        form = layui.form,
        layer = layui.layer,
        laydate = layui.laydate;

/*    //日期
    laydate.render({
        elem: '#approvalDate'
    });*/


    //工具函数：
    // 在url中获取指定参数值
    var getParamFromUrl = function (url, param) {
        if (url.indexOf('?') !== -1) {
            var params = url.split("?")[1].split('&');
            for (var i = 0; i < params.length; i++) {
                if (params[i].indexOf(param) !== -1) {
                    return params[i].replace(param + "=", "");
                }
            }
            // console.log('该url中无参数'+param);
        } else {
            // console.log('该url中无参数');
        }
    };

    var ajaxUrl = getParamFromUrl(document.location.href, "actionURL"),
        processCode = getParamFromUrl(document.location.href, "processCode"),
        eiaWorkFlowBusiId = getParamFromUrl(document.location.href, "eiaWorkFlowBusiId"),
        processUrlParams = getParamFromUrl(document.location.href, "processUrlParams"),
        nodesCode = getParamFromUrl(document.location.href,"nodesCode"),
        currNodesName = getParamFromUrl(document.location.href,"currNodesName"),
        version = getParamFromUrl(document.location.href,"version");
        $("#currNodesName").text(decodeURI(currNodesName));

    //表单提交
    form.on('submit(save)', function (data) {
        console.log(data.field); //当前容器的全部表单字段，名值对形式：{name: value}
        $.ajax({
            url: "../" + ajaxUrl,   //需要替换，actionUrl前要加"/"
            type: "POST",
            data: {
                "opinion": data.field.opinion,
                "processCode": processCode,
                "eiaWorkFlowBusiId": eiaWorkFlowBusiId,
                "processUrlParams": processUrlParams,
                "nodesCode":nodesCode,
                "version":version,
                "ifOpinion":"no"
            },
            dataType: "json",
            async: true,
            success: function (data) {
                if (data.code == 0) {
                        layer.msg(data.msg, {icon: 1, time: 1500}, function () {
                            if(parent.parent.layer){
                                parent.parent.location.reload();
                            }
                            parent.parent.layer.closeAll();
                        });
                } else {
                    layer.msg(data.msg, {icon: 2});
                }
            }
        });
        return false;
    })
});