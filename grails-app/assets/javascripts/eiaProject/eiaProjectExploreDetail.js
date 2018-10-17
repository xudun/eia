layui.use(['jquery', 'form'], function(){
    var $ = layui.jquery,
        form = layui.form;

    var eiaProjectId = parent.$('#eiaProjectId').val();
    eiaProjectId = eiaProjectId ? eiaProjectId : getParamFromUrl(document.location.href, "eiaProjectId");

    $.ajax({
        url: "../eiaEnvProject/getEnvProjectDataMap",
        // url: "data/projectExploreDetail.json",
        type: "POST",
        data: {"eiaProjectId": eiaProjectId},
        dataType: "json",
        async: true,
        success: function (res) {
            var data = res.data;
            $('#eiaProjectId').val(eiaProjectId);
            $('#projectName').text(data.projectName);
            $('#projectNo').text(data.projectNo);
            $('#fileTypeChild').text(data.fileTypeChild);
            $('#taskAssignUser').text(data.taskAssignUser);
            $('#exploreRecord').text(data.exploreRecord);
            $('#eiaEnvProjectId').val(data.eiaEnvProjectId);
            $('#inputUser').text(data.inputUser);
            $('#exploreDate').text(data.exploreDate);
            if(data.ifSensArea !=null){$("#ifSensArea").text(data.ifSensArea ? "是" : "否");}
            if(data.ifCityPlan!=null){$("#isCityPlan").text(data.ifCityPlan ? "是" : "否");}

            if ($('#eiaEnvProjectId').val()) {
                /**
                 * 获取已经上传的图片预览
                 */
                $.ajax({
                    url: "../eiaEnvProject/getExploreImageList",
                    // url: "data/imgs.json",
                    data: {'eiaEnvProjectId': $('#eiaEnvProjectId').val()},
                    type: "post",
                    async: true,
                    success: function (res) {
                        console.log(res);
                        var data = res.data;
                        if (data.length > 0) {
                            var uploaded = $("#uploaded");
                            for (var i = 0; i < data.length; i++) {
                                uploaded.append('<img src="' + data[i].path + '" alt="' + data[i].fileName + '" class="layui-upload-img">')
                            }
                        }
                    }
                });
            }


        }
    });

});