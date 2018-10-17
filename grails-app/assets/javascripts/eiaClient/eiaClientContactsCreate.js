layui.use(['jquery', 'form'], function(){
    var $ = layui.jquery,
        form = layui.form;
    var eiaClientId = parent.$('#eiaClientId').val();
    var clientContactsId = parent.$('#clientContactsId').val();
    //工具函数在url中获取指定参数值
    var getParamFromUrl = function(url,param){
        if(url.indexOf('?')!==-1){
            var params = url.split("?")[1].split('&');
            for(var i=0; i<params.length; i++){
                if(params[i].indexOf(param) !== -1){
                    return params[i].replace(param+"=","");
                }
            }
            // console.log('该url中无参数'+param);
        }else{
            // console.log('该url中无参数');
        }

    };

    $(function () {
        //页面类型：0:新建 ； 1：编辑
        var pageType = getParamFromUrl(document.location.href,"pageType");

        //设置页面名称
        var $pageTitle = $('.pageTitle');
        pageType == 0 ? $pageTitle.html('新增联系人') : $pageTitle.html('编辑联系人');

        //重置按钮显隐
        pageType == 0 ? "" : $('.resetBtn').hide();
        //基本信息回显
        if(pageType == 1){
            $.ajax({
                url:"../eiaClient/getEiaClientContactsDataMap?clientContactsId="+clientContactsId,
                type:"POST",
                data:{},
                dataType: "json",
                success: function (data) {
                    $('#contactName').val(data.data.contactName);
                    $('#contactPhone').val(data.data.contactPhone);
                    $('#contactPosition').val(data.data.contactPosition);
                    $('#contactEmail').val(data.data.contactEmail);
                    $('#clientFax').val(data.data.clientFax);
                }
            });
        }

        //表单提交
        form.on('submit(save)', function(data){
            var loadingIndex = layer.load(1, {time: 10*1000,shade: 0.1});
            console.log(data.field); //当前容器的全部表单字段，名值对形式：{name: value}
            var actionUrl = "../eiaClient/eiaClientContactsSave?eiaClientId="+eiaClientId+"&clientContactsId="+clientContactsId;
            $.post(actionUrl, data.field, function (data) {
                if (data.code == 0) {
                    layer.msg('保存成功', {icon: 1, time: 1000}, function () {
                        var index = parent.layer.getFrameIndex(window.name);
                        parent.layui.table.reload("contactsList");
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
});