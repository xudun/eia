layui.use(['jquery', 'form', 'layer', 'laydate'], function () {
    var $ = layui.jquery,
        form = layui.form,
        layer = layui.layer;



    //表单提交
    form.on('submit(save)', function (data) {
        console.log(data.field); //当前容器的全部表单字段，名值对形式：{name: value}
        var workFlowConfig = data.field.workFlowConfig
        try {
            JSON.parse(workFlowConfig)
        }catch (e){
            layer.msg("JSON格式有误无法序列化！",{icon:2,time:1500});
            return false
        }
        var loadingIndex = layer.load(1, {time: 10*1000,shade: 0.1});
        $.ajax({
            url: "/eia/eiaWorkFlow/workFlowConfigJsonParse",   //需要替换，actionUrl前要加"/"
            type: "POST",
            data: {
                workFlowConfig:data.field.workFlowConfig
            },
            async: true,
            success: function (data) {
                if (data.code == 0) {
                    if(data.msg =='导入成功'){
                        layer.msg(data.msg, {icon: 1, time: 1000,shade: 0.1}, function () {
                            parent.parent.layer.closeAll();
                        });
                    }
                    else{
                        layer.msg('导入成功', {icon: 1, time: 1000,shade: 0.1}, function () {
                            var index = parent.layer.getFrameIndex(window.name);
                            parent.location.reload();
                            parent.layer.close(index);
                        });
                    }
                } else {
                    layer.msg(data.msg, {icon: 2,shade: 0.1});
                }
            },
            complete: function () {
                layer.close(loadingIndex);
            }
        });

        return false;
    })


});