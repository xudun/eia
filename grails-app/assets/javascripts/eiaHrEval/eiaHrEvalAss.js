layui.use(['jquery', 'layer'], function() {
    var $ = layui.jquery,
        layer = layui.layer;

    $(function(){
        var total
        $.get("/eia/static/js/layuiFrame/data/eiaHrEvalDMToAssInput.json",function(data){
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
            var sc=$(document);//得到document文档对象。
            var isFixed = false;
            if(sc.scrollTop()>=0){
                if(!isFixed){
                    top.addClass("fixed-y");
                    top.removeClass("display-none")
                    top.css("width", width+"px");
                    isFixed = true;
                }
            }else if(sc.scrollTop()<0){
                top.removeClass("fixed-y");
                top.addClass("display-none")
                isFixed = false;
            }
            $("#personTable").scroll(function(){
                var left = $("#personTable table").offset().left;
                top.css("left", left + "px");
            });
            $("#orgCode").text(data.orgCode);
            $("#orgName").text(data.orgName);
            $("#assessmentMonth").text(data.assessmentMonth);
            $("#leader").text(data.leader);
            $("#jobRatingType").val(data.jobRatingType);
            if(data.total>0){
                var titleTop = $(".titleTop");
                titleTop.empty();
                var mt1 = $(".mt1");
                mt1.empty();
                var workExecution = $("#workExecution");
                workExecution.empty();
                var performance = $("#performance");
                performance.empty();
                var workingAttitude = $("#workingAttitude");
                workingAttitude.empty();
                var teamSpirit = $("#teamSpirit");
                teamSpirit.empty();
                var leadership = $("#leadership");
                leadership.empty();
                var proAbility = $("#proAbility");
                proAbility.empty();
                var leadershipManager = $("#leadershipManager");
                leadershipManager.empty();
                var finalScore = $("#finalScore");
                finalScore.empty();
                var scoreId = $("#scoreId");
                scoreId.empty();
                var jobRatingList = data.items
                for(var i in jobRatingList) {
                    var jobRatingCore = data.items[i];
                    titleTop.append('<th width="80px"><a href="#" onclick="staffRoting('+jobRatingCore.staffId+')">'+jobRatingCore.staffName+'</th>')
                    mt1.append('<th width="80px"><a href="#" onclick="staffRoting('+jobRatingCore.staffId+')">'+jobRatingCore.staffName+'</th>')
                    workExecution.append('<td width="80px"><input type="text" name="workExecution" disabled value="'+jobRatingCore.workExecution+'" /></td>')
                    performance.append('<td width="80px"><input type="text" name="performance" disabled value="'+jobRatingCore.performance+'" /></td>')
                    workingAttitude.append('<td width="80px"><input type="text" name="workingAttitude" disabled value="'+jobRatingCore.workingAttitude+'" /></td>')
                    teamSpirit.append('<td width="80px"><input type="text" name="teamSpirit" disabled value="'+jobRatingCore.teamSpirit+'" /></td>')
                    leadership.append('<td width="80px"><input type="text" name="leadership" disabled value="'+jobRatingCore.leadership+'" /></td>')
                    proAbility.append('<td width="80px"><input type="text" name="proAbility" disabled value="'+jobRatingCore.proAbility+'" /></td>')
                    leadershipManager.append('<td width="80px"><input type="text" name="leadershipManager" disabled value="'+jobRatingCore.leadershipManager+'" /></td>')
                    finalScore.append('<th width="80px"><input type="text" name="finalScore" disabled value="'+jobRatingCore.finalScore+'" /></th>')
                    scoreId.append('<td><button type="button" class="remarkButton" onclick="assComment('+jobRatingCore.id+')">员工评语</button></td>')
                }
            }
        })
    });
})
function staffRoting(staffId){
    var pageUrl = "/eia/eiaHrEval/eiaHrEvalAssDetail?staffId="+staffId;
    layui.use( 'layer', function() {
        var layer = layui.layer;
        layer.open({
            type : 2,
            title :" ",
            shade: false,
            maxmin: true,
            skin: 'larry-green',
            area: ['100%', '100%'],
            content: pageUrl,
            min: function () {
                $(".layui-layer-title").text("助理详细表");
            },
            restore: function () {
                $(".layui-layer-title").text(" ");
            }
        });
    })
}
function assComment(staffId){
    layui.use( 'layer', function() {
        var layer = layui.layer;
        var jobRatingType  = $("#jobRatingType").val();
        var assessmentMonth  = $("#assessmentMonth").text();
        //$.ajax({
        //    type: 'POST',
        //url: '/jobRating/isJobRatingScore?staffId='+staffId+"&jobRatingType="+jobRatingType+"&assessmentMonth="+assessmentMonth,

        //success: function (data) {
        //    if(data== 'isNotScore'){
        //        layer.msg('请先打分再评语！', {icon:7});
        //        return;
        //    }else{
        var pageUrl = "/eia/eiaHrEval/eiaHrEvalComment?staffId="+staffId+"&jobRatingType="+jobRatingType+"&assessmentMonth="+assessmentMonth
        layer.open({
            type : 2,
            title :" ",
            maxmin: true,
            skin: 'layui-layer-rim',
            area: ['100%', '100%'],
            content: pageUrl,
            min: function () {
                $(".layui-layer-title").text("领导评语");
            },
            restore: function () {
                $(".layui-layer-title").text(" ");
            }
        });
        //}
        //}
        //});
    })
}