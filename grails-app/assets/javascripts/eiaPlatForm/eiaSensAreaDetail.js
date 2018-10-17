layui.use(['jquery', 'form'], function(){
    var $ = layui.jquery,
        form = layui.form;
    var eiaSensAreaId = parent.$('#eiaSensAreaId').val();
    if (!eiaSensAreaId) {
        eiaSensAreaId = getParamFromUrl(document.location.href,"eiaSensAreaId");
    }
    $('#eiaSensAreaId').val(eiaSensAreaId);

    $.ajax({
        url:"../eiaPlatForm/getEiaSensAreaDataMap?eiaSensAreaId=" + eiaSensAreaId,
        type:"POST",
        data:{},
        dataType: "json",
        success: function (result) {
            if (result.data) {
                $('#projectName').text(result.data.projectName);
                $('#regionName').text(result.data.regionName.name);
                $('#dataType').text(result.data.dataType);
                $('#projectMemo').text(result.data.projectMemo);
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
            }
        }
    });
    function downloadFile(fileId) {
        if (fileId) {
            window.location.href = "../eiaPlatForm/downloadFile?fileId=" + fileId;
        }
    }
});