layui.use(['layer', 'upload', 'form'], function () {
    var $ = layui.$,
        layer = layui.layer,
        upload = layui.upload,
        form = layui.form;
    var tableName = 'EiaStamp'
    var tableId = parent.$("#eiaStampId").val();
    var $fileUploadType = 'EiaStampFile'
    ajaxBox("/eia/eiaFileUpload/getFileTypeList",{tableName:tableName},function (res) {
        if(res.code == 0){
            var data = res.data
            data.map(function(d){
                $fileUploadType.append("<option value='"+d.code+"'>"+d.codeDesc+"</option>")
            });
            form.render('select');
        }else{
            // layer.msg(res.msg,{icon:1,time:1500});
        }
    });

    upload.render({
        elem: '#eiaFileUpload'
        , url: '/eia/eiaFileUpload/eiaFileUploadSave'
        , accept: 'file'
        , auto: false
        , exts: 'zip|rar|txt|xlsx|xls|docx|doc|pdf|jpg|jpeg|png|gif'
        , bindAction: '#save'
        , data: {
            tableName: tableName, tableId: tableId, fileUploadType: function () {
                return $("#fileUploadType").val()
            }, fileNote: function () {
                return $("#fileNote").val()
            }
        }
        , choose: function (obj) {
            obj.preview(function (index, file, result) {
                $("#fileName").text(file.name)
            });
        }
        ,before: function(obj){ //obj参数包含的信息，跟 choose回调完全一致，可参见上文。
            $(".shade").removeClass("display-none")
            layer.load(1)
        }
        , error: function () {
            //请求异常回调
        }, done: function (res, index, upload) {
            if (res.code == 0) {
                $(".shade").addClass("display-none")
                layer.msg("上传成功！", {icon: 1, time: 1500}, function () {
                    var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                    parent.layer.close(index); //再执行关闭
                });
            }
        }
    });
    /*表单验证*/
    form.on('submit(save)', function (data) {
        return false
    });
});