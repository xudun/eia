/**
 * Created by XinXi-001 on 2018/5/7.
 */
layui.use(['jquery', 'form', 'element', 'laydate'], function () {
    var $ = layui.jquery,
        form = layui.form,
        element = layui.element,
        laydate = layui.laydate;

    var eiaProjectId = parent.$('#eiaProjectId').val();

    var proPlanItemId = parent.$('#proPlanItemId').val();
    $("#proPlanItemId").val(proPlanItemId);
    var proPlanId = parent.$('#proPlanId').val();
    $("#proPlanId").val(proPlanId);


    //加载节点列表

    $.ajax({
        url: "../eiaProjectPlan/getProItems",
        type: "POST",
        data: {"proPlanId": $("#proPlanId").val()},
        dataType: "json",
        async: true,
        success: function (res) {
            var data = res.data;
            for (var name in data) {
                var str = "<option value='" + data[name].id + "'>" + data[name].nodesName + "</option>";
                $('#nodesCode').append(str);
            }
            form.render('select');
        }
    });

    //监听任务名称select
    form.on('select(nodesCodeSelect)', function (data) {
        $('#addUser').remove();
        var curVal = data.value;
        console.log(curVal);
        $('#proPlanItemId').val(curVal);
        //渲染节点审核人select选项
        $.ajax({
            url: "../eiaProjectPlan/getNodeUsers",
            type: "POST",
            data: {"proPlanId": $("#proPlanId").val(),"proPlanItemId":curVal},
            dataType: "json",
            async: true,
            success: function (res) {
                var data = res.data;
                for (var name in data) {
                    var str = "<option id='addUser' value='" + data[name].id + "'>" + data[name].name + "</option>";
                    $('#nodeUser').append(str);
                }
                form.render('select');
            }
        });
    });


    form.on('submit(save)', function (data) {

        data.field['nodeUserName'] =$('#nodeUser option:selected').text();//选中的文本
        data.field['proPlanId'] = $('#proPlanId').val()
        data.field['proPlanItemId'] = $('#proPlanItemId').val()
        data.field['nodeUser'] = $('#nodeUser').val()
        data.field['eiaProjectId'] = eiaProjectId
        console.log(data.field); //当前容器的全部表单字段，名值对形式：{name: value}
        var loadingIndex = layer.load(1, {time: 10*1000,shade: 0.1});
        var actionUrl = "../eiaProjectPlan/changeWorkFlowNode";
        $.post(actionUrl, data.field, function (data) {
            if (data.code == 0) {
                layer.msg('保存成功', {icon: 1, time: 1000,shade: 0.1}, function () {
                    parent.layer.closeAll();
                });
            } else {
                layer.msg(data.msg, {icon: 2, time: 2000,shade: 0.1}, function () {
                });
            }
            layer.close(loadingIndex);
        });
        return false;
    })
});

