layui.use(['jquery', 'form'], function(){
    var $ = layui.jquery,
        form = layui.form;
    var eiaClientId =  parent.$('#eiaClientId').val();
    var eiaClientName =  parent.$('#clientName').val();;
    $('#clientName').val(eiaClientName)
    $(function () {
        //页面类型：0:新建 ； 1：编辑
        var pageType = getParamFromUrl(document.location.href,"pageType");
        //设置页面名称
        var $pageTitle = $('.pageTitle');
        pageType == 0 ? $pageTitle.html('新增财务开户信息') : $pageTitle.html('编辑财务开户信息');
        var eiaClientConfigId = parent.$('#eiaClientConfigId').val();
        if(pageType==0){
            eiaClientConfigId =''
        }
        //表单提交
        $.ajax({
            url:"../eiaClientConfig/getEiaClientConfigDataMap?eiaClientConfigId="+eiaClientConfigId,
            type:"POST",
            data:{},
            dataType: "json",
            success: function (data) {
                $('#clientName').val(data.data.clientName);
                $('#taxRegCode').val(data.data.taxRegCode);
                $('#bankName').val(data.data.bankName);
                $('#bankAccount').val(data.data.bankAccount);
                $('#addrTel').val(data.data.addrTel);
            }
        });
        form.on('submit(save)', function(data){
            var loadingIndex = layer.load(1, {time: 10*1000,shade: 0.1});
            var actionUrl = "../eiaClientConfig/eiaClientConfigSave?eiaClientId="+eiaClientId+"&eiaClientConfigId="+eiaClientConfigId;
            $.post(actionUrl, data.field, function (data) {
                if (data.code == 0) {
                    layer.msg('保存成功', {icon: 1, time: 1000,shade: 0.1}, function () {
                        var index = parent.layer.getFrameIndex(window.name);
                        //parent.layui.table.reload("eiaInvoiceList");
                        //提交成功后关闭自身
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
    //重置按钮
    $('.resetBtn').click(function () {
        $('#bankAccount').val("");
        $('#addrTel').val("");
        $('#taxRegCode').val("");
        $('#bankName').val("");
    });

    form.verify({
        number_noreq: [
            /^$|\d$/
            ,'只能填写数字111'
        ]
    });
});