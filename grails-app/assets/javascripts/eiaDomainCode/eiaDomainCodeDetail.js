layui.use(['layer', 'form','table','laydate'],function(){
    var $ = layui.$,
        layer = layui.layer,
        form = layui.form,
        table = layui.table;
    /**
     * 基本信息form数据加载
     */
    var eiaDomainCodeId = parent.$("#eiaDomainCodeId").val();
    $.ajax({
        url: "../eiaDomainCode/getEiaDomainCodeDataMap?eiaDomainCodeId=" + eiaDomainCodeId,
        type:"get",
        cache: false,
        async: false,
        success: function (result) {
            if (result.data) {
                $('#editTitle').tmpl().appendTo('#title');
                $("#eiaDomainCodeId").val(result.data.id);
                $("#code").val(result.data.code);
                $("#codeDesc").val(result.data.codeDesc);
                $("#domain").val(result.data.domain);
                $("#domainDesc").val(result.data.domainDesc);
                $("#displayOrder").val(result.data.displayOrder);
                $("#codeLevel").val(result.data.codeLevel);
                $("#codeRemark").val(result.data.codeRemark);
                $("#parentId").val(result.data.parentId);
                $("#parentCode").val(result.data.parentCode);
            } else {
                $('#addTitle').tmpl().appendTo('#title');
            }
        }
    });
    /**
     * 基本信息form提交
     */
    form.on('submit(save)',function(data){
        var loadingIndex = layer.load(1, {time: 10*1000,shade: 0.1});
        var actionUrl = "../eiaDomainCode/eiaDomainCodeSave";
        $.post(actionUrl,data.field,function(data){
            if (data.code == 0) {
                layer.msg('保存成功', {icon: 1, time: 1000,shade: 0.1}, function () {
                    parent.layui.table.reload("eiaDomainCodeList");
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.layer.close(index);
                });
            } else {
                layer.msg(data.msg, {icon: 2, time: 1000,shade: 0.1});
            }
            layer.close(loadingIndex);
        });
        return false;
    });
});