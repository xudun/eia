layui.use(['jquery', 'layer'], function() {
    var $ = layui.jquery,
        layer = layui.layer;
    var eiaHrEvalId = parent.$("#eiaHrEvalId").val();
    $(function(){
        var total
        $.post("/eia/eiaHrEvalScore/getEiaHrEvalEmpDataDetailList?eiaHrEvalId="+eiaHrEvalId,function(data){
            total = data.total
            var docWidth = document.body.clientWidth
            if(docWidth<=973){
                $("#personTable").css("width",docWidth-650+"px");
            }else{
                $("#personTable").css("width",docWidth-840+"px");
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
                var jobSkill = $("#jobSkill");
                jobSkill.empty();
                var workingAttitude = $("#workingAttitude");
                workingAttitude.empty();
                var teamSpirit = $("#teamSpirit");
                teamSpirit.empty();
                var cultureCognition = $("#cultureCognition");
                cultureCognition.empty();
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
                    jobSkill.append('<td width="80px"><input type="text" name="jobSkill" disabled value="'+jobRatingCore.jobSkill+'" /></td>')
                    workingAttitude.append('<td width="80px"><input type="text" name="workingAttitude" disabled value="'+jobRatingCore.workingAttitude+'" /></td>')
                    teamSpirit.append('<td width="80px"><input type="text" name="teamSpirit" disabled value="'+jobRatingCore.teamSpirit+'" /></td>')
                    cultureCognition.append('<td width="80px"><input type="text" name="cultureCognition" disabled value="'+jobRatingCore.cultureCognition+'" /></td>')
                    finalScore.append('<th width="80px"><input type="text" name="finalScore" disabled value="'+jobRatingCore.finalScore+'" /></th>')
                    scoreId.append('<td><button type="button" class="remarkButton" onclick="empComment('+jobRatingCore.id+')">员工评语</button></td>')
                }
            }
        })
    });
})
function staffRoting(staffId){
    var pageUrl = "/eia/eiaHrEvalScoreDetail/eiaHrEvalEmpDetail?staffId="+staffId;
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
                $(".layui-layer-title").text("打分详细表");
            },
            restore: function () {
                $(".layui-layer-title").text(" ");
            }
        });
    })
}
function empComment(staffId){
    layui.use( 'layer', function() {
        var layer = layui.layer;
        var jobRatingType = parent.$("#jobRatingType").val();
        var assessmentMonth = parent.$("#assessmentMonth").val();
        var eiaHrEvalScoreId = parent.$("#eiaHrEvalScoreId").val();
        $("#eiaHrEvalScoreId").val(staffId);
        $.ajax({
            type: 'POST',
            url: '/eia/eiaHrEvalScoreDetail/isEiaHrEvalScore?eiaHrEvalScoreId='+eiaHrEvalScoreId,
            success: function (data) {
                if(data== 'isNotScore'){
                    layer.msg('请先打分再评语！', {icon:7});
                    return;
                }else{
                    var pageUrl = "/eia/eiaHrEvalScoreDetail/eiaHrEvalCommentDetail?eiaHrEvalScoreId="+eiaHrEvalScoreId
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
                }
            }
        });
    })
}