layui.use(['jquery', 'layer', 'form', 'table'], function(){
    var $ = layui.jquery,
        form = layui.form,
        layer = layui.layer,
        table = layui.table;

    var eiaWorkFlowNodeProcessId = parent.$('#eiaWorkFlowNodeProcessId').val();
    var $processColor = $('#processColor'),
        $processIconName = $('#processIconName');

    //基本信息回显
    $.ajax({
        url:"/eia/eiaWorkFlowNodeProcess/getEiaWorkFlowNodeProcessDataMap?eiaWorkFlowNodeProcessId=" + eiaWorkFlowNodeProcessId,
        type:"POST",
        data:{},
        dataType: "json",
        success: function (result) {
            if (result) {
                $('#processName').text(result.data.processName);
                $('#processNum').text(result.data.processNum);
                $('#processUrl').text(result.data.processUrl);
                $processColor.text(result.data.processColor);
                $('#processShowName').text(result.data.processShowName);
                $('#processCode').text(result.data.processCode);
                $('#processUrlParams').text(result.data.processUrlParams);
                $('#processJumpUrl').text(result.data.processJumpUrl);
                $('#processJumpUrlParams').text(result.data.processJumpUrlParams);
                $processIconName.text(result.data.processIconName);

                var str = "<i class='larry-icon'>" + result.data.processIconName + "</i>";
                $processIconName.closest('.layui-input-block').append(str);
                $processColor.next('.layui-badge-rim').css('background', result.data.processColor);
            }
        }
    });
});