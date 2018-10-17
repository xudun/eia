/**
 * Created by XinXi-001 on 2018/5/7.
 */
layui.use(['jquery', 'form', 'element', 'laydate'], function () {
    var $ = layui.jquery,
        form = layui.form,
        element = layui.element,
        laydate = layui.laydate;

    var hideTimeArr = ['现场踏勘', '现场勘查', '现场访谈', '编写监测', '一审', '二审', '三审', '编写报告', '内部审查', '周报月报', '总结报告', '审核', '审定', '现场访谈', '其它','初稿完成'];
    var xckxArr = ['现场踏勘', '现场勘查', '现场访谈'];
    var eiaProjectId = parent.$('#eiaProjectId').val();
    laydate.render({
        elem: '#planStartDate'
    });
    laydate.render({
        elem: '#planEndDate'
    });
    laydate.render({
        elem: '#actStartDate'
    });
    laydate.render({
        elem: '#actEndDate'
    });
    laydate.render({
        elem: '#exploreDate'
    });
    var proPlanItemId = parent.$('#proPlanItemId').val();
    $("#proPlanItemId").val(proPlanItemId);
    var proPlanId = parent.$('#proPlanId').val();
    $("#proPlanId").val(proPlanId);
    var oid = parent.$('#oid').val()
    $("#oid").val(oid);
    var nodesName = decodeURI(getParamFromUrl(document.location.href, "nodesName"));
    $('.showTitle').text(nodesName);
    if (hideTimeArr.indexOf(nodesName) < 0) {
        $('.canHideItem').find('.layui-form-label span').text("");
        $('.canHideItem').find('.layui-input-block input').attr("lay-verify", "");
    }

    var xckcFlag = false;
    if (xckxArr.indexOf(nodesName) >= 0 && parent.parent.$('#oid').val()) {
        xckcFlag = true;
        $.ajax({
            url: "../eiaEnvProject/getEnvProjectSaveDataMap",
            type: "POST",
            data: {"eiaProjectId": eiaProjectId},
            dataType: "json",
            async: true,
            success: function (res) {
                var data = res.data;
                $('#eiaProjectId').val(eiaProjectId);
                $('#exploreRecord').val(data.exploreRecord);
                $('#eiaEnvProjectId').val(data.eiaEnvProjectId);
                $('#inputUser').val(data.inputUser);
                $('#inputUserId').val(data.inputUserId);
                $('#inputDept').val(data.inputDept);
                $('#inputDeptId').val(data.inputDeptId);
                $('#inputDeptCode').val(data.inputDeptCode);
                $('#exploreDate').val(data.exploreDate);
                $('#buildArea').val(data.buildArea);
                if (data.ifSensArea == true) {
                    $('#isSensArea').attr("checked", "checked");
                    $("#ifSensAreaReadonly").val("是")
                } else if (data.ifSensArea == false) {
                    $('#notSensArea').attr("checked", "checked");
                    $("#ifSensAreaReadonly").val("否")
                }
                if (data.ifCityPlan == true) {
                    $('#isCityPlan').attr("checked", "checked");
                    $("#ifCityPlanReadonly").val("是")
                } else if (data.ifCityPlan == false) {
                    $('#notCityPlan').attr("checked", "checked");
                    $("#ifCityPlanReadonly").val("否")
                }
                if ($("#preview").html() == "") {
                    $("#uploadStart").addClass("display-none")
                }
                form.render('radio');
            }
        });
    }


    if (!parent.parent.$('#oid').val()) {
        /***新项目**/
        $('.oldItem').remove();
    } else {
        if (xckxArr.indexOf(nodesName) < 0) {
            /***老项目**/
            $('.xckcItem').remove();
        }
        // $('.noRequired').find('.layui-form-label span').text("");
        // $('.noRequired').find('.layui-input-block input').attr("lay-verify", "");
        $('.noRequired').remove();
    }
    //下拉树 相关工作人员
    $("#userNamesDept").dropDownForZ({
        url: "../eiaProjectPlan/getNodeUsersTree?proPlanId=" + proPlanId + "&proPlanItemId=" + proPlanItemId,
        width: '99%',
        height: '350px',
        mode: "multi",
        selectedNodes: function (data) {
            var userNames = [];
            for (var i = 0; i < data.length; i++) {
                var temp = {};
                temp.name = data[i].name;
                temp.id = data[i].id;
                userNames.push(temp);
            }
            $('#userNames').val(JSON.stringify(userNames));
        }
    });

    //渲染节点审核人select选项
    $.ajax({
        url: "../eiaProjectPlan/getNodeUsers",
        type: "POST",
        data: {"proPlanId": $("#proPlanId").val(), "proPlanItemId": $('#proPlanItemId').val()},
        dataType: "json",
        async: true,
        success: function (res) {
            var data = res.data;
            for (var name in data) {
                var str = "<option value='" + data[name].id + "'>" + data[name].name + "</option>";
                $('#nodeUser').append(str);
            }

            //加载数据
            $.ajax({
                url: "../eiaProjectPlan/getProPlanDataMap?proPlanItemId=" + proPlanItemId,
                type: "POST",
                data: {'showTitle': nodesName, "proPlanId": $("#proPlanId").val()},
                dataType: "json",
                async: true,
                success: function (result) {
                    if (result.data.nodeUserId) {
                        $("#nodeUser").find("option[value = " + result.data.nodeUserId + "]").attr("selected", "selected");
                    }
                    $('#nodeUserId').val(result.data.nodeUserId);
                    $('#planStartDate').val(result.data.planStartDate);
                    $('#planEndDate').val(result.data.planEndDate);
                    $('#actStartDate').val(result.data.actStartDate);
                    $('#actEndDate').val(result.data.actEndDate);
                    $('#opinion').val(result.data.opinion);
                    $('#modiContent').val(result.data.modiContent);
                    form.render('select');
                }
            });

            form.render('select');
        }
    });

    //回显 相关工作人员下拉树 数据
    $.ajax({
        url: "../eiaProjectPlan/getItemUserNames",
        type: "POST",
        data: {"proPlanId": $("#proPlanId").val(), "proPlanItemId": $('#proPlanItemId').val()},
        dataType: "json",
        success: function (res) {
            if (res.code == 0) {
                var data = res.data
                var showName = "",
                    showCode = "";
                for (var i = 0; i < data.length; i++) {
                    showName += data[i].name + ",";
                    showCode += data[i].id + ",";
                }
                showName = showName.substring(0, showName.length - 1);
                showCode = showCode.substring(0, showCode.length - 1);
                $('#userNamesDept').val(showName);
                $('#userNamesDeptCode').val(showCode);

                $('#userNames').val(JSON.stringify(data));
            } else {

            }
        }
    });


    form.on('submit(save)', function (d) {
        d.field['nodeUserName'] = $('#nodeUser option:selected').text();//选中的文本
        d.field['proPlanId'] = $('#proPlanId').val();
        d.field['proPlanItemId'] = $('#proPlanItemId').val();
        d.field['nodeUser'] = $('#nodeUser').val();
        d.field['eiaEnvProjectId'] = $('#eiaEnvProjectId').val();
        d.field['showTitle'] = nodesName;
        console.log(d.field); //当前容器的全部表单字段，名值对形式：{name: value}
        var loadingIndex = layer.load(1, {time: 10 * 1000, shade: 0.1});
        if (oid != null && oid != '') {
            var actionUrl = "../eiaProjectPlan/eiaProjectPlanItemUpdate";
        } else {
            var actionUrl = '../eiaProjectPlan/eiaProjectPlanItemUpdateNew'
        }
        $.post(actionUrl, d.field, function (data) {
            if (data.code == 0) {
                if (xckcFlag) {
                    var actionUrl = "../eiaEnvProject/exploreInfoSave";
                    $.post(actionUrl, d.field, function (data) {
                        if (data.code == 0) {
                            layer.msg('保存成功', {icon: 1, time: 1000, shade: 0.1}, function () {
                                parent.layui.table.reload("eiaProPlanItemList");
                                parent.layer.closeAll();
                            });
                        } else {
                            layer.msg(data.msg, {icon: 2, time: 2000, shade: 0.1}, function () {
                            });
                        }
                    });
                } else {
                    layer.msg('保存成功', {icon: 1, time: 1000, shade: 0.1}, function () {
                        parent.layui.table.reload("eiaProPlanItemList");
                        parent.layer.closeAll();
                    });
                }
            } else {
                layer.msg(data.msg, {icon: 2, time: 2000, shade: 0.1}, function () {
                });
            }
            layer.close(loadingIndex);
        });
        return false;
    })
});

function getOffsetDays(time1, time2) {
    var offsetTime = Math.abs(time1 - time2);
    return Math.floor(offsetTime / (3600 * 24 * 1e3));
}
