layui.use(['jquery', 'form'], function(){
    var $ = layui.jquery,
        form = layui.form;
    var eiaDataShareId = getParamFromUrl(document.location.href,"eiaDataShareId");
    var type = getParamFromUrl(document.location.href,"type");
    //渲染数据
    $.ajax({
        url:"../eiaPlatForm/platFormEiaDataShareDataMap?eiaDataShareId=" + eiaDataShareId+"&type="+type,
        type:"POST",
        data:{},
        dataType: "json",
        success: function (result) {
            console.log(result)
            //普通显示项
            for(var name in result.data){
                if(name !== "files"){
                    if(name == "ifIndPolicy"){
                        if (result.data[name] == true) {
                            $('#'+name).text('是');
                        } else if (result.data[name] == false) {
                            $('#'+name).text('否');
                        }
                    }else{
                        $('#'+name).text(result.data[name]);
                    }
                }
            }

            // //附件
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
    });
    function downloadFile(fileId) {
        if (fileId) {
            window.location.href = "../eiaPlatForm/downloadFile?fileId=" + fileId;
        }
    }
});