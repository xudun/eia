layui.use(['jquery', 'form'], function(){
    var $ = layui.jquery,
        form = layui.form;

    var $speInpContainers = $('.dynInputs .layui-col-xs6');
    var eiaAreaInfoId = parent.$('#eiaAreaInfoId').val();
    if (!eiaAreaInfoId) {
        eiaAreaInfoId = getParamFromUrl(document.location.href,"eiaAreaInfoId");
    }
    $('#eiaAreaInfoId').val(eiaAreaInfoId);

    $.ajax({
        url:"../eiaPlatForm/getEiaAreaInfoDataMap?eiaAreaInfoId=" + eiaAreaInfoId,
        type:"POST",
        data:{},
        dataType: "json",
        success: function (result) {
            if (result.data) {
                $('#projectName').text(result.data.projectName);
                $('#regionName').text(result.data.regionName.name);
                $('#dataType').text(result.data.dataType);
                $('#geographicEast').text(result.data.geographicEast);
                $('#geographicStartEast').text(result.data.geographicStartEast);
                $('#geographicEndEast').text(result.data.geographicEndEast);
                $('#geographicNorth').text(result.data.geographicNorth);
                $('#geographicStartNorth').text(result.data.geographicStartNorth);
                $('#geographicEndNorth').text(result.data.geographicEndNorth);
                var filesArr = result.data.files;
                for (var i = 0; i < filesArr.length; i++) {
                    var str = " <p class='file-p'><a class='file-text' fileId='"+filesArr[i].id+"'>" + filesArr[i].fileName + "</a></p>";
                    var $str = $(str).on('click',function () {
                        var fileId = $('a',$(this)).attr('fileId');
                        downloadFile(fileId);
                    });
                    $('#files').append($str);
                }

                //渲染动态数据
                $.ajax({
                    url:"../eiaPlatForm/getEiaAreaInfoByDataType?eiaAreaInfoId=" + eiaAreaInfoId,
                    type: "POST",
                    data: {"dataType": result.data.dataType},
                    dataType: "json",
                    async: true,
                    success: function (inpResult) {
                        $speInpContainers.empty();
                        var con_index = 0;
                        for (var name in inpResult.data) {
                            inputs[name].show.call(this, result.data[name], $speInpContainers.eq(con_index++ % 2));
                        }
                    }
                });
            }
        }
    });
    function downloadFile(fileId) {
        if (fileId) {
            window.location.href = "../eiaPlatForm/downloadFile?fileId=" + fileId;
        }
    }
});