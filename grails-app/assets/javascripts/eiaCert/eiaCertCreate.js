layui.use(['jquery', 'form', 'laydate'], function () {
    var $ = layui.jquery,
        form = layui.form,
        laydate = layui.laydate;

    //日期
    var date = new Date();
    laydate.render({
        elem: '#applyDate',
        value: date
    });



    //监听 是否申请资质 switch
    form.on('switch(ifApplyCertSwitch)', function (data) {
        console.log(data.elem.checked); //开关是否开启，true或者false
        $('#ifApplyCert').val(data.elem.checked);

    });

    //监听 是否申请资质 switch
    form.on('select(certType)', function (data) {
        if(data.value == "CERT_TYPE_REVIEW"){
            $("#meetDateDiv").html(" <div class=\"layui-form-item\">\n" +
                "                        <label class=\"layui-form-label\"><span class=\"col-f00\">* </span>开会日期</label>\n" +
                "                        <div class=\"layui-input-block\">\n" +
                "                            <input type=\"text\" id=\"meetDate\" name=\"meetDate\" class=\"layui-input\" lay-verify=\"required\">\n" +
                "                        </div>\n" +
                "                    </div>")
            laydate.render({
                elem: '#meetDate',
                value: date
            });
        }else{
            $("#meetDateDiv").empty()
        }

    });

    //项目名称hover提示
    $('.pro-name-item .tip-block i').hover(function () {
        layer.tips('所选项目为三审审批后的', '#tips',{
            area: ['auto', 'auto'],
            tips: [1, '#30b5ff'],
            time: 2000
        });
    }, function () {
        var index = layer.tips();
        layer.close(index);
    });

    //渲染项目名称select选项
    $.ajax({
        url: "/eia/eiaCert/eiaProjectQuery",
        type: "POST",
        data: {},
        dataType: "json",
        async: true,
        disableParent: true,
        success: function (res) {
            var data = res.data
            for (var i = 0; i < data.length; i++) {
                var str = "<option value='" + data[i].eiaProjectId + "'>" + data[i].projectName + "</option>";
                $('#eiaProjectId').append(str);
            }
            form.render('select');
        }
    });

    //重置按钮
    $('.resetBtn').click(function () {
        $('#ifApplyCert').val(false);
    });

    //表单提交
    form.on('submit(save)', function (data) {
        var loadingIndex = layer.load(1, {time: 10*1000,shade: 0.1});
        console.log(data.field); //当前容器的全部表单字段，名值对形式：{name: value}
        ajaxBox("/eia/eiaCert/eiaCertSave", data.field, function (res) {
            if (res.code == 0) {
                layer.msg("提交成功！", {icon: 1, time: 1500,shade: 0.1})
                var index = parent.layer.getFrameIndex(window.name);
                parent.layer.close(index);
            } else {
                layer.msg(res.msg, {icon: 2, time: 1500,shade: 0.1});
            }
            layer.close(loadingIndex);
        });
        //提交成功后关闭自身


        return false;
    })

});