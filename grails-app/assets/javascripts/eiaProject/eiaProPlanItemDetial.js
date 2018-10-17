/**
 * Created by XinXi-001 on 2018/5/7.
 */
layui.use(['jquery', 'form', 'element', 'laydate'], function () {
    var $ = layui.jquery,
        form = layui.form,
        element = layui.element;
    laydate = layui.laydate;
    var eiaProjectId = parent.$('#eiaProjectId').val();
    laydate.render({
        elem: '#planStartDate'
    });
    laydate.render({
        elem: '#planEndDate'
    });

    var proPlanItemId = parent.$('#proPlanItemId').val()
    $("#proPlanItemId").val(proPlanItemId)
    var proPlanId = parent.$('#proPlanId').val()
    $("#proPlanId").val(proPlanId)

    //渲染节点审核人select选项
    $.ajax({
        url: "../eiaProjectPlan/getNodeUsers",
        type: "POST",
        data: {"proPlanId": $("#proPlanId").val(),"proPlanItemId":$('#proPlanItemId').val()},
        dataType: "json",
        async: true,
        success: function (res) {
            var data = res.data
            for (var name in data) {
                var str = "<option value='" + data[name].id + "'>" + data[name].name + "</option>";
                $('#nodeUser').append(str);
            }

            //加载数据
            $.ajax({
                url: "../eiaProjectPlan/getProPlanDataMap?proPlanItemId=" + proPlanItemId,
                type: "POST",
                data: {},
                dataType: "json",
                async: true,
                success: function (result) {
                    if(result.data.nodeUserId){
                        $("#nodeUser").find("option[value = "+result.data.nodeUserId+"]").attr("selected","selected");
                    }
                    $("#nodeUser").val(result.data.nodeUser)
                    $('#nodeUserId').val(result.data.nodeUserId);
                    $('#planStartDate').val(result.data.planStartDate);
                    $('#planEndDate').val(result.data.planEndDate);
                    $('#userNames').val(result.data.userNames);
                    $('#planFinDay').val(result.data.planFinDay);
                    $('#actStartDate').val(result.data.actStartDate);
                    $('#actEndDate').val(result.data.actEndDate);
                    $('#actFinishDay').val(result.data.actFinDay);
                    $('#opinion').val(result.data.opinion);
                    $('#modiContent').val(result.data.modiContent);
                    form.render('select');
                }
            });

            form.render('select');
        }
    });

});

function getOffsetDays(time1, time2) {
    var offsetTime = Math.abs(time1 - time2);
    return Math.floor(offsetTime / (3600 * 24 * 1e3));
}
