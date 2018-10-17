layui.use(['jquery', 'form'], function(){
    var $ = layui.jquery,
        form = layui.form;

    var eiaPubProjectId = getParamFromUrl(document.location.href,"eiaPubProjectId");
    var eiaDataShareId = getParamFromUrl(document.location.href,"eiaDataShareId");
    var pageUrl = "../eiaPlatForm/getEiaPubProjectDataMap?"
    if(eiaPubProjectId){
        pageUrl = pageUrl + "eiaPubProjectId=" + eiaPubProjectId+"&"
    }

    if(eiaDataShareId){
        pageUrl = pageUrl + "eiaDataShareId=" + eiaDataShareId+"&"
    }
    //渲染编辑页数据
    $.ajax({
        url:"../eiaPlatForm/getEiaPubProjectDataMap?eiaPubProjectId=" + eiaPubProjectId + "&eiaDataShareId=" + eiaDataShareId,
        type:"POST",
        data:{},
        dataType: "json",
        success: function (result) {
            if (result.data) {
                console.log(result.data);
                $('#projectName').text(result.data.projectName);
                $('#approveDept').text(result.data.approveDept);
                if (result.data.industryType) {
                    $('#industryTypeDrop').text(result.data.industryType.name);
                }
                $('#industryType').text(result.data.industryType);
                $('#productFunction').text(result.data.productFunction);
                $('#eiaUnit').text(result.data.eiaUnit);
                $('#geographicEast').text(result.data.geographicEast);
                $('#geographicStartEast').text(result.data.geographicStartEast);
                $('#geographicEndEast').text(result.data.geographicEndEast);
                if (result.data.regionName) {
                    $('#regionNameDrop').text(result.data.regionName.name);
                    $('#regionName').text(result.data.regionName.name);
                }
                $('#productionEngineer').text(result.data.productionEngineer);
                if (result.data.environmentaType) {
                    $('#environmentaTypeDrop').text(result.data.environmentaType.name);
                }
                $('#environmentaType').text(result.data.environmentaType);
                $('#developmentOrg').text(result.data.developmentOrg);
                $('#geographicNorth').text(result.data.geographicNorth);
                $('#geographicStartNorth').text(result.data.geographicStartNorth);
                $('#geographicEndNorth').text(result.data.geographicEndNorth);
                $('#projectMemo').text(result.data.projectMemo);
                $('#projectLoc').text(result.data.projectLoc);
                $('#pubDate').text(result.pubDate);
                $('#natureConstructio').text(result.data.natureConstructio);
                $('#publictyYear').text(result.data.publictyYear);
                $('#dataType').text(result.data.dataType);

                $('#spiderFileDownloadUrl').attr('href', result.data.spiderFileDownloadUrl);
                $('#spiderFileUrl').attr('href', result.data.spiderFileUrl);

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