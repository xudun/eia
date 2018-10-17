layui.use(['jquery', 'layer'], function() {
    var $ = layui.jquery,
        layer = layui.layer;
    var eiaHrEvalId = parent.$("#eiaHrEvalId").val();
    $(function(){
        var total
        $.post("/eia/eiaHrEvalScoreDetail/eiaHrEvalEmpEdit?eiaHrEvalId="+eiaHrEvalId,function(data){
            total = data.total
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
                    titleTop.append('<th width="80px">'+jobRatingCore.staffName+'</th>')
                    teamSpirit.append('<td width="80px"><input type="text" name="teamSpirit" disabled value="'+jobRatingCore.teamSpirit+'" /></a></td>')
                    leadership.append('<td width="80px"><input type="text" name="leadership" disabled value="'+jobRatingCore.leadership+'" /></a></td>')
                    proAbility.append('<td width="80px"><input type="text" name="proAbility" disabled value="'+jobRatingCore.proAbility+'" /></a></td>')
                }
            }
        })
    });
})