layui.use(['jquery', 'layer','form'], function() {
    var $ = layui.jquery,
        form = layui.form,
        layer = layui.layer;



    var jobRatingType  = parent.parent.$("#jobRatingType").val();
    var assessmentMonth  = parent.parent.$("#assessmentMonth").val();
    var orgId  = parent.parent.$("#orgId").val();
    var staffId = parent.$("#staffId").val();
    var eiaHrEvalScoreId = parent.parent.$("#eiaHrEvalScoreId").val();
    var eiaHrEvalScoreDetailId = parent.$("#eiaHrEvalScoreDetailId").val();
    $('.larry-btn a.layui-btn').click(function () {
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });
    var active = {
        //保存领导评语
        save: function () {
            var actionUrl = "/eia/eiaHrEvalScoreDetail/leaderCommentsSave?staffId="+staffId+"&jobRatingType="+jobRatingType+"&assessmentMonth="+assessmentMonth+"&orgId="+orgId+"&eiaHrEvalScoreId="+eiaHrEvalScoreId+"&eiaHrEvalScoreDetailId="+eiaHrEvalScoreDetailId;
            var form = $("#commentForm");
//        if(form.valid()){
            layer.msg('正在保存...', {icon: 16,shade: 0.01});
            var loadingIndex = layer.load(1, {time: 10*1000,shade: 0.1});
            $.post(actionUrl,form.serialize(),function(data){
                if(data.code==0){
                    layer.msg('保存成功！',{icon:1,time:1500,shade: 0.01},function(){
                        parent.layer.closeAll();
                    });
                }else {
                    layer.alert("处理失败！<br/><div style='color:red;'>错误信息：" + data.msg +"</div>",{icon:2,time:1500});
                }
                layer.close(loadingIndex);
            });
//        };
        }
    }

    $.ajax({
        url:"/eia/eiaHrEvalScoreDetail/eiaHrcomment?eiaHrEvalScoreDetailId="+eiaHrEvalScoreDetailId+"&eiaHrEvalScoreId="+eiaHrEvalScoreId,
    //url:"../authMenu/getAuthMenuDataMap?menuId="+menuId+"&funcId="+funcId,
        type:"post",
        cache: false,
        async: false,
        success: function (result) {
            $("#leaderComments").val(result.leaderComments);
        }
    })
})