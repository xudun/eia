layui.use(['layer', 'form', 'table'], function () {
    var $ = layui.$,
        layer = layui.layer,
        form = layui.form,
        table = layui.table;
    form.render();

    $.ajax({
        url: "../eiaLabOffer/getEiaProjectNameList",
        type: 'get',
        success: function (names) {
            var projectName = $('#projectName');
            projectName.append('<option value="">请选择项目名称</option>');
            for (var i = 0; i < names.data.length; i++) {
                projectName.append('<option value="' + names.data[i].id + '">' + names.data[i].projectName + '</option>')
            }
            form.render('select', 'projectName');
        }
    });

    /**
     * 基本信息form提交
     */
    form.on('submit(save)', function (data) {
        var loadingIndex = layer.load(1, {time: 10*1000,shade: 0.1});
        var planList = parent.$('#eiaPlanCalListData').val();
        data.field.planList = planList;
        var actionUrl = "../eiaLabOffer/eiaLabOfferProjectSave";
        $.post(actionUrl, data.field, function (result) {
            if (result.code == 0) {
                layer.msg('保存成功', {icon: 1, time: 1000,shade: 0.1}, function () {
                    parent.parent.$("#eiaLabOfferId").val(result.data.id);
                    parent.parent.layer.closeAll();
                });
            } else {
                layer.msg(result.msg, {icon: 2, time: 1000,shade: 0.1});
            }
            layer.close(loadingIndex);
        });
        return false;
    });
    /**
     * 保存并退出
     */
    form.on('submit(saveAndQuit)',function(data){
        var loadingIndex = layer.load(1, {time: 10*1000,shade: 0.1});
        var actionUrl = "../eiaLabOffer/eiaLabOfferSave?sampleType=" + "采样";
        $.post(actionUrl,data.field,function(result){
            if (result.code == 0) {
                layer.msg('保存成功', {icon: 1, time: 1000,shade: 0.1}, function () {
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.layui.table.reload("eiaLabOfferList");
                    parent.layer.close(index);
                });
            } else {
                layer.msg(result.msg, {icon: 2, time: 1000,shade: 0.1});
            }
            layer.close(loadingIndex);
        });
    });
    /**
     * 监听项目名称选择
     */
    form.on('select(projectNameSelect)', function (data) {
        $('#eiaProjectId').val(data.value);
        $.ajax({
            url: "../eiaLabOffer/getProjectAddr",
            type:"POST",
            data:{"eiaProjectId":data.value},
            dataType: "json",
            async: true,
            success: function (result) {
                $('#projectAddr').val(result.data.buildArea);
            }
        });
    });
});