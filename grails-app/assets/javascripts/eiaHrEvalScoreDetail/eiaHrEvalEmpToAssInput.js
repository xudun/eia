layui.use(['jquery', 'layer'], function() {
    var $ = layui.jquery,
        layer = layui.layer;


    var eiaHrEvalId = parent.$("#eiaHrEvalId").val();
    $(function(){
        $('.larry-btn a.layui-btn').click(function () {
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });
        var active = {
            //员工月度考核表保存
            save: function () {
                var actionUrl = "/eia/eiaHrEvalScoreDetail/eiaHrEvalScoreDetailSave?eiaHrEvalId="+eiaHrEvalId;
                var form = $("#perTableForm");
                for(var i=0;i<$("#perTable input").length;i++){
                    if($("#perTable input").eq(i).val()==""){
                        layer.msg('请全部评分后再保存！', {icon:7});
                        return;
                    }
                }
                layer.msg('正在保存...', {icon: 16,shade: 0.01});
                var form = $("#perTableForm")
                var loadingIndex = layer.load(1, {time: 10*1000,shade: 0.1});
                $.post(actionUrl,form.serialize(),function(data){
                    if(data.code==0){
                        layer.msg('保存成功！',{icon:1,time:1500,shade: 0.1},function(){
                            parent.layer.closeAll();

                        });
                    }else {
                        layer.alert("处理失败！<br/><div style='color:red;'>错误信息：" + data.msg +"</div>",{icon:2,time:1500});
                    }
                    layer.close(loadingIndex);
                });
            }
        }
        var total
        $.post("/eia/eiaHrEvalScoreDetail/eiaHrEvalEmpEdit?eiaHrEvalId="+eiaHrEvalId,function(data){
            total = data.total;
            $("#jobRatingName").val(data.staffIdList);
            $("#staffNameList").val(data.staffNameList);
            var docWidth = document.body.clientWidth
            if(docWidth<=973){
                $("#personTable").css("width",docWidth-651+"px");
            }else{
                $("#personTable").css("width",docWidth-841+"px");
            }
            var width = total*81;
            $("#rightTitle").css("width",width+"px")
            var top=$(".titleTop"); //得到导航对象
            //var sc=$(document);//得到document文档对象。
            $("#personTable").scroll(function(){
                var left = $("#personTable table").offset().left;
                top.css("left", left + "px");
            });
            $("#orgCode").text(data.orgCode);
            $("#orgName").text(data.orgName);
            $("#assessmentMonth").text(data.assessmentMonth);
            $("#leader").text(data.leader);
            if(data.total>0){
                var titleTop = $(".titleTop");
                titleTop.empty();
                var teamSpirit = $("#teamSpirit");
                teamSpirit.empty();
                var leadership = $("#leadership");
                leadership.empty();
                var proAbility = $("#proAbility");
                proAbility.empty();
                var jobRatingList = data.items
                for(var i in jobRatingList) {
                    var jobRatingCore = data.items[i];
                    if(data.showType){
                        titleTop.append('<th width="80px">'+jobRatingCore.staffName+'</th>')
                        teamSpirit.append('<td width="80px"><input type="text" name="teamSpirit" value="'+jobRatingCore.teamSpirit+'" /></td>')
                        leadership.append('<td width="80px"><input type="text" name="leadership" value="'+jobRatingCore.leadership+'" /></td>')
                        proAbility.append('<td width="80px"><input type="text" name="proAbility" value="'+jobRatingCore.proAbility+'" /></td>')
                    }else{
                        titleTop.append('<th width="80px">'+jobRatingCore.staffName+'</th>')
                        teamSpirit.append('<td width="80px"><input type="text" name="teamSpirit" value="" /></td>')
                        leadership.append('<td width="80px"><input type="text" name="leadership" value="" /></td>')
                        proAbility.append('<td width="80px"><input type="text" name="proAbility" value="" /></td>')
                    }

                }
            }
            $("#perTable input").keyup(function(){
                $(this).val($(this).val().replace(/[^0-9]/g,''));
                if($(this).val()>10){
                    $(this).val("");
                }
            }).bind("paste",function(){  //CTR+V事件处理
                $(this).val($(this).val().replace(/[^0-9]/g,''));
                if($(this).val()>10){
                    $(this).val("");
                }
            }).css("ime-mode", "disabled"); //CSS设置输入法不可用
        })
    });
})
function clearForm(){
    $("input[name='teamSpirit']").val('')
    $("input[name='leadership']").val('')
    $("input[name='proAbility']").val('')
}