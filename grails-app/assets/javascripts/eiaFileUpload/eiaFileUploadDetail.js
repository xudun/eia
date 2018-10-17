layui.use(['jquery', 'layer','form'], function() {
    var $ = layui.jquery,
        form = layui.form,
        layer = layui.layer;

    var eiaFileUploadId = getParamByUrl(location.href).eiaFileUploadId;
    //渲染固定数据
    $.ajax({
        url:"/eia/eiaFileUpload/getEiaFileUploadDataMap",
        type:"post",
        data:{eiaFileUploadId:eiaFileUploadId},
        cache: false,
        async: false,
        success: function (res) {
            var data = res.data
            $("#fileUploadType").text(data.fileUploadType);
            $('#fileName').text(data.fileName);
            $('#fileNote').text(data.fileNote);
            form.render();
        }
    });
    $('#fileName').click(function(){
        window.location.href = "../eiaFileUpload/eiaFileUploadDownload?eiaFileUploadId="+eiaFileUploadId
    })
})