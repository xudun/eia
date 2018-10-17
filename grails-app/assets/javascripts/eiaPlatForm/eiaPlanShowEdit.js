layui.use(['jquery', 'layer', 'form', 'laydate'], function(){
    var $ = layui.jquery,
        layer = layui.layer,
        laydate = layui.laydate,
        form = layui.form;
    var eiaPlanShowId = parent.$('#eiaPlanShowId').val();
    $('#eiaPlanShowId').val(eiaPlanShowId);

    //日期
    laydate.render({
        elem: '#pubDate'
    });

    //渲染编辑页数据
    $.ajax({
        url:"../eiaPlatForm/getEiaPlanShowDataMap?eiaPlanShowId=" + eiaPlanShowId,
        type:"POST",
        data:{},
        dataType: "json",
        success: function (result) {
            if (result) {
                // console.log(result.eiaPlanShow);
                $('#title').val(result.eiaPlanShow.title);
                $('#source').val(result.eiaPlanShow.source);
                $('#pubDate').val(result.pubDate);
                $('#spiderFileUrl').attr('href', result.eiaPlanShow.spiderFileUrl);
                $('#content').val(result.eiaPlanShow.content);
                $('#spiderFileImagesUrl')
                    .attr('src', result.eiaPlanShow.spiderFileImagesUrl)
                    .on('click', function () {
                        layer.open({
                            type: 1,
                            title: '规划图片',
                            area: ['800px', '600px'],
                            skin: 'demo-class img-layer',
                            content: "<img class='layer-img-box' src='" + result.eiaPlanShow.spiderFileImagesUrl + "'>"
                        });
                    });
            }
        }
    });


    //表单提交
    form.on('submit(save)', function(data){
        var loadingIndex = layer.load(1, {time: 10*1000,shade: 0.1});
        // console.log(data.field); //当前容器的全部表单字段，名值对形式：{name: value}
        var actionUrl = "../eiaPlatForm/eiaPlanShowSave";
        $.post(actionUrl, data.field, function (data) {
            if (data.code == 0) {
                layer.msg('保存成功', {icon: 1, time: 1000,shade: 0.1}, function () {
                    parent.layui.table.reload("eiaPlanShowList");
                    parent.layer.closeAll();
                });
            } else {
                layer.msg('保存失败', {icon: 2, time: 2000,shade: 0.1}, function () {
                    parent.layer.closeAll();
                });
            }
            layer.close(loadingIndex);
        });
        return false;
    });
});