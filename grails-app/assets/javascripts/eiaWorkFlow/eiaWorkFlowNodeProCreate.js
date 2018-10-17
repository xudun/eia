layui.use(['jquery', 'layer', 'form', 'table'], function(){
    var $ = layui.jquery,
        form = layui.form,
        layer = layui.layer,
        table = layui.table;

    var $processColor = $('#processColor'),
        $processIconName = $('#processIconName');

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

    var eiaWorkFlowId = parent.$('#eiaWorkFlowId').val();
    $('#eiaWorkFlowId').val(eiaWorkFlowId);
    var eiaWorkFlowNodeId = parent.$('#eiaWorkFlowNodeId').val();
    $('#eiaWorkFlowNodeId').val(eiaWorkFlowNodeId);

    //页面类型：0:新建 ； 1：编辑
    var pageType = getParamFromUrl(document.location.href,"pageType");
    if (pageType == 1) {
        var eiaWorkFlowNodeProcessId = parent.$('#eiaWorkFlowNodeProcessId').val();
        $('#eiaWorkFlowNodeProcessId').val(eiaWorkFlowNodeProcessId);
    }

    //设置页面名称
    var $pageTitle = $('.pageTitle');
    pageType == 0 ? $pageTitle.html('新增节点动作') : $pageTitle.html('编辑节点动作');

    //重置按钮显隐
    pageType == 0 ? "" : $('.resetBtn').hide();

    //按钮颜色监听
    $('#processColor').change(function () {
        var cur_val = $(this).val();
        $(this).closest('.layui-form-item').find('.layui-badge-rim').css('background',cur_val);
    });
    //按钮图标监听
    $('#processIconName').change(function () {
        var cur_val = $(this).val();
        var str = "<i class='larry-icon'>"+cur_val+"</i>";
        $(this).closest('.layui-form-item').find('.sq-box').empty().append(str);
    });

    //基本信息回显
    if(pageType == 1){
        $.ajax({
            url:"/eia/eiaWorkFlowNodeProcess/getEiaWorkFlowNodeProcessDataMap?eiaWorkFlowNodeProcessId=" + eiaWorkFlowNodeProcessId,
            type:"POST",
            data:{},
            dataType: "json",
            success: function (result) {
                if (result) {
                    $('#processName').val(result.data.processName);
                    $('#processNum').val(result.data.processNum);
                    $('#processUrl').val(result.data.processUrl);
                    $processColor.val(result.data.processColor);
                    $('#processShowName').val(result.data.processShowName);
                    $('#processCode').val(result.data.processCode);
                    $('#processUrlParams').val(result.data.processUrlParams);
                    $('#processJumpUrl').val(result.data.processJumpUrl);
                    $('#processJumpUrlParams').val(result.data.processJumpUrlParams);
                    $('#processIconName').val(result.data.processIconName);

                    var str = "<i class='larry-icon'>" + data.processIconName + "</i>";
                    $processIconName.closest('.layui-form-item').find('.sq-box').empty().append(str);
                    $processColor.closest('.layui-form-item').find('.layui-badge-rim').css('background', data.processColor);
                }
            }
        });
    }

    //表单提交
    form.on('submit(save)', function(data){
        var loadingIndex = layer.load(1, {time: 10*1000,shade: 0.1});
        console.log(data.field); //当前容器的全部表单字段，名值对形式：{name: value}
        var actionUrl = "../eiaWorkFlowNodeProcess/eiaWorkFlowNodeProcessSave";
        $.post(actionUrl, data.field, function (result) {
            if (result.code == 0) {
                layer.msg('保存成功', {icon: 1, time: 1000,shade: 0.1}, function () {
                    $('#eiaWorkFlowNodeProcessId').val(result.data.id);
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.layer.close(index);
                });
            } else {
                layer.msg('保存失败', {icon: 2, time: 2000,shade: 0.1});
            }
            layer.close(loadingIndex);
        });
        return false;
    });

});