/**
 * Created by XinXi-001 on 2018/5/7.
 */
layui.use(['jquery', 'form', 'element', 'laydate', "upload"], function () {
    var $ = layui.jquery,
        form = layui.form,
        element = layui.element,
        upload = layui.upload;
    laydate = layui.laydate;

    laydate.render({
        elem: '#exploreDate'
    });
    form.render('radio');

    var eiaProjectId = parent.$('#eiaProjectId').val();
    eiaProjectId = eiaProjectId ? eiaProjectId : getParamFromUrl(document.location.href, "eiaProjectId");

    $.ajax({
        url: "../eiaEnvProject/getEnvProjectDataMap",
        type: "POST",
        data: {"eiaProjectId": eiaProjectId},
        dataType: "json",
        async: true,
        success: function (res) {
            var data = res.data;
            $('#eiaProjectId').val(eiaProjectId);
            $('#projectName').val(data.projectName);
            $('#projectNo').val(data.projectNo);
            $('#fileTypeChild').val(data.fileTypeChild);
            $('#taskAssignUser').val(data.taskAssignUser);
            $('#projectName').val(data.projectName);
            $('#exploreRecord').val(data.exploreRecord);
            $('#eiaEnvProjectId').val(data.eiaEnvProjectId);
            $('#inputUser').val(data.inputUser);
            $('#inputUserId').val(data.inputUserId);
            $('#inputDept').val(data.inputDept);
            $('#inputDeptId').val(data.inputDeptId);
            $('#inputDeptCode').val(data.inputDeptCode);
            $('#exploreDate').val(data.exploreDate);
            $('#dutyUser').val(data.dutyUser);
            $('#dutyUserId').val(data.dutyUserId);
            var staffId = $('#staffId').val();
            if (staffId != data.inputUserId) {
                $('.saveBtn').addClass("display-none");
            }

            if ($('#eiaEnvProjectId').val()) {
                /**
                 * 获取已经上传的图片预览
                 */
                $.ajax({
                    url: "../eiaEnvProject/getExploreImageList",
                    data: {'eiaEnvProjectId': $('#eiaEnvProjectId').val()},
                    type: "post",
                    async: true,
                    success: function (res) {
                        console.log(res)
                        var data = res.data
                        if (data.length > 0) {
                            var uploaded = $("#uploaded")
                            for (var i = 0; i < data.length; i++) {
                                uploaded.append('<img src="' + data[i].path + '" alt="' + data[i].fileName + '" class="layui-upload-img"><span id="img' + data[i].id + '" class="larry-icon deleteBtn cursor-pointer" onclick="delUploadedImg(' + data[i].id + ')">&#xe8d0;</span>')
                            }
                        }
                    }
                });
                $("#selectFile").parent().removeClass("display-none")
                $('.noticeTag').addClass('display-none');
            }

            if (data.ifSensArea == true) {
                $('#isSensArea').attr("checked", "checked");
                $("#ifSensAreaReadonly").val("是")
            } else if (data.ifSensArea == false) {
                $('#notSensArea').attr("checked", "checked");
                $("#ifSensAreaReadonly").val("否")
            }

            if (data.ifCityPlan== true) {
                $('#isCityPlan').attr("checked", "checked");
                $("#ifCityPlanReadonly").val("是")
            }else if(data.ifCityPlan == false){
                $('#notCityPlan').attr("checked", "checked");
                $("#ifCityPlanReadonly").val("否")
            }

            if($("#preview").html() == ""){
                $("#uploadStart").addClass("display-none")
            }
            form.render('radio');

            /** 渲染项目负责人 */
            $.ajax({
                url: "../eiaProject/getProjectDutyUser",
                type: 'POST',
                data: {'eiaProjectId': eiaProjectId},
                success: function (data) {
                    var dutyUserList = $('#dutyUserList');
                    dutyUserList.append('<option value="">选择项目负责人</option>');
                    if (data.data) {
                        if (res.data.dutyUserId) {
                            for (var i = 0; i < data.data.length; i++) {
                                dutyUserList.append('<option value="' + data.data[i].dutyUserId + '">' + data.data[i].dutyUser + '</option>')
                                if (data.data[i].dutyUserId == res.data.dutyUserId) {
                                    dutyUserList.find("option[value = " + res.data.dutyUserId + "]").attr("selected", "selected");
                                }
                            }
                        } else {
                            for (var i = 0; i < data.data.length; i++) {
                                dutyUserList.append('<option value="' + data.data[i].dutyUserId + '">' + data.data[i].dutyUser + '</option>')
                            }
                        }
                    }
                    form.render('select');
                }
            })
        }
    });

    /**
     * 监听项目负责人选中
     */
    form.on('select(dutyUserList)', function (data) {
        $('#dutyUserId').val(data.value)
        $('#dutyUser').val($("#dutyUserList option:selected").text())
    });
    /**
     * 表单提交
     */
    form.on('submit(save)', function (data) {
        var loadingIndex = layer.load(1, {time: 10*1000,shade: 0.1});
        var actionUrl = "../eiaEnvProject/exploreInfoSave";
        $.post(actionUrl, data.field, function (data) {
            if (data.code == 0) {
                layer.msg('保存成功', {icon: 1, time: 1000,shade: 0.1}, function () {
                    $('#eiaEnvProjectId').val(data.data.id);
                    $("#selectFile").parent().removeClass("display-none");
                    $('.noticeTag').addClass('display-none');
                });
            } else {
                layer.msg(data.msg, {icon: 2, time: 2000,shade: 0.1}, function () {
                });
            }
            layer.close(loadingIndex);
        });
        return false;
    })
    /**
     *  现场图片
     */
    var files

    var uploadListIns = upload.render({
        elem: '#selectFile'
        , url: '../eiaEnvProject/uploadExploreImage'
        , multiple: true
        , data: {
            'eiaEnvProjectId': function () {
                return $('#eiaEnvProjectId').val()
            }
        }
        , auto: false
        , bindAction: '#uploadStart'
        , choose: function (obj) {
            //alert($('#eiaEnvProjectId').val())
            files = this.files = obj.pushFile();
            obj.preview(function (index, file, result) {
                $('#preview').append('<img src="' + result + '" alt="' + file.name + '" class="layui-upload-img"><span id="' + index + '" class="larry-icon deleteBtn cursor-pointer">&#xe8d0;</span>')
                $('#' + index).on('click', function () {
                    delete files[index]; //删除对应的文件
                    $(this).prev().remove();
                    $(this).remove();
                    uploadListIns.config.elem.next()[0].value = ''; //清空 input file 值，以免删除后出现同名文件不可选
                    if($("#preview").html() == ""){
                        $("#uploadStart").addClass("display-none")
                    }
                });
            });
            $("#uploadStart").removeClass("display-none")
        }
        , done: function (res) {
            window.location.reload();
        }
    });
});

function delUploadedImg(id) {
    layui.use(['layer'], function () {
        var $ = layui.jquery,
            layer = layui.layer;

        layer.confirm('确定要删除该图片吗?', {icon: 3}, function (index) {
            var loadingIndex = layer.load(1, {time: 10*1000,shade: 0.1});
            $.post("../eiaEnvProject/exploreImageDel", {imgId: id}, function (data) {
                if (data.code == 0) {
                    layer.msg('删除成功！', {icon: 1, time: 1500,shade: 0.1}, function () {
                        $("#img" + id).prev().remove()
                        $("#img" + id).remove()
                    });
                } else {
                    if (data.msg) {
                        layer.msg("处理失败！<br/><div style='color:red;'>错误信息：" + data.msg + "</div>", {
                            icon: 2,
                            time: 1500
                        });
                    } else {
                        layer.msg('删除失败！', {icon: 2, time: 1500,shade: 0.1});
                    }
                }
                layer.close(loadingIndex);
            });
        });
    })
}

