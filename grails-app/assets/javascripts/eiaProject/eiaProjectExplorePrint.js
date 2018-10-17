layui.use(['layer'], function () {
    var $ = layui.jquery,
        layer = layui.layer;
    var eiaProjectId = parent.$('#eiaProjectId').val();
    eiaProjectId = eiaProjectId ? eiaProjectId : getParamFromUrl(document.location.href, "eiaProjectId");

    $.ajax({
        url: "../eiaEnvProject/getEnvProjectDataMap",
        type: "post",
        data: {'eiaProjectId':eiaProjectId},
        cache: false,
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
            $('#exploreDate').text(data.exploreDate?data.exploreDate:"无");
            $('#buildArea').text(data.buildArea);
            $("#ifSensArea").text(data.ifSensArea ? "是" : "否");
            $("#isCityPlan").text(data.ifCityPlan ? "是" : "否");
            $("#sign").append('<img height="70px" src="'+data.sign+'">');
            /**
             * 获取已经上传的图片预览
             */
            $.ajax({
                url: "../eiaEnvProject/getExploreImageList",
                data: {'eiaEnvProjectId': $('#eiaEnvProjectId').val()},
                type: "post",
                async: true,
                success: function (res) {
                    var data = res.data;
                    if (data.length > 0) {
                        for (var i = 0; i < data.length; i++) {
                            $("#uploadImg").append('<div class="float-left" style="width: 200px;height: 200px"><img id="sceneImg' + i + '" src="' + data[i].path + '" alt="' + data[i].fileName + '"></div>')
                            var img = document.getElementById("sceneImg" + i);
                            img.onload = function () {
                                for (var p = 0; p < data.length; p++) {
                                    var img = document.getElementById("sceneImg" + p);
                                    var naturalW = img.naturalWidth;
                                    var naturalH = img.naturalHeight   //图片真实高度
                                    if (naturalW > 200 && naturalH <= 200) {
                                        $("#sceneImg" + p).css("width", "200px")
                                    } else if (naturalW <= 200 && naturalH > 200) {
                                        $("#sceneImg" + p).css("height", "200px")
                                    } else if (naturalW > 200 && naturalH > 200) {
                                        if ((naturalW - 200) > (naturalH - 200)) {
                                            $("#sceneImg" + p).css("width", "200px");
                                        } else {
                                            $("#sceneImg" + p).css("height", "200px");
                                        }
                                    }
                                }
                            };

                        }
                    }
                }
            });


        }
    });

    function imgWidth() {
        var img = document.getElementsByClassName('sceneImg')[0]
        var nWidth = img.naturalWidth     //图片真实宽度
        var nHeight = img.naturalHeight   //图片真实高度
        alert(nWidth + "-----" + nHeight)
    }

    $('.larry-btn a.layui-btn').click(function () {
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });
    var active = {
        print: function () {
            $("#print").addClass("display-none")
            window.print();
            $("#print").removeClass("display-none")
        }
    }

});