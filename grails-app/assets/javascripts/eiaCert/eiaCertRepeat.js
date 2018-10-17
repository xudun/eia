layui.use(['jquery', 'form', 'laydate'], function(){
    var $ = layui.jquery,
        form = layui.form,
        laydate = layui.laydate;

    //日期
    var date = new Date();
    laydate.render({
        elem: '#applyDate',
    });
    laydate.render({
        elem: '#meetDate',
    });
    var eiaCertId = getParamByUrl(window.location.href)["eiaCertId"];
    //项目名称hover提示
    $('.pro-name-item .tip-block i').hover(function () {
        layer.tips('所选项目为三审审批后的', '#tips',{
            area: ['auto', 'auto'],
            tips: [1, '#30b5ff'],
            time: 2000
        });
    },function () {
        var index = layer.tips();
        layer.close(index);
    });

    var certType={"CERT_TYPE_REVIEW":"送审","CERT_TYPE_APPROVAL":"报批"}

    //渲染显示数据
    $.ajax({
        url:"/eia/eiaCert/getEiaCert",
        type:"POST",
        data:{eiaCertId:eiaCertId},
        dataType: "json",
        async: true,
        disableParent: true,
        success: function (res) {
            var data = res.data;
            $('#projectName').text(data.projectName);
            $('#certType').text(certType[data.certType]);
            var ifApplyCert = data.ifApplyCert ? "是" : "否";
            $('#ifApplyCert').text(ifApplyCert);
            $('#meetDate').val(data.meetDate);
            $('#applyDate').val(data.applyDate);
            if(data.certType == 'CERT_TYPE_REVIEW'){
                $('#meetDateDiv').removeClass("display-none")
            }else{
                $('.verify-item').find('.layui-input-block input').attr('lay-verify',"");
            }
        }
    });

    //表单提交
    form.on('submit(save)', function(data){
        var loadingIndex = layer.load(1, {time: 10*1000,shade: 0.1});
        console.log(data.field); //当前容器的全部表单字段，名值对形式：{name: value}
        data.field.parentEiaCertId = eiaCertId;
        $.ajax({
            url:"/eia/eiaCert/eiaCertRepeatSub",
            type:"POST",
            data:data.field,
            dataType: "json",
            async: true,
            disableParent: true,
            success: function (data) {
                layer.msg("提交成功！",{icon:1,time:1500,shade: 0.1});
                //提交成功后关闭自身
                var index = parent.layer.getFrameIndex(window.name);
                parent.layer.close(index);
                parent.location.reload();
            },
            complete: function () {
                layer.close(loadingIndex);
            }
        });


        return false;
    })

});