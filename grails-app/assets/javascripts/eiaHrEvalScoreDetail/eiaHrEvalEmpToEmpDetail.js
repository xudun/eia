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
            $(".lessThan10 input").keyup(function(){
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
            $(".lessThan20 input").keyup(function(){
                $(this).val($(this).val().replace(/[^0-9]/g,''));
                if($(this).val()>20){
                    $(this).val("");
                }
            }).bind("paste",function(){  //CTR+V事件处理
                $(this).val($(this).val().replace(/[^0-9]/g,''));
                if($(this).val()>20){
                    $(this).val("");
                }
            }).css("ime-mode", "disabled"); //CSS设置输入法不可用
            $("#orgCode").text(data.orgCode);
            $("#orgName").text(data.orgName);
            $("#assessmentMonth").text(data.assessmentMonth);
            $("#leader").text(data.leader);
            if(data.total>0){
                var titleTop = $(".titleTop");
                titleTop.empty();
                //var mt1 = $(".mt1");
                //mt1.empty();
                var workingAttitude = $("#workingAttitude");
                workingAttitude.empty();
                var teamSpirit = $("#teamSpirit");
                teamSpirit.empty();
                var cultureCognition = $("#cultureCognition");
                cultureCognition.empty();
                var jobRatingList = data.items
                for(var i in jobRatingList) {
                    var jobRatingCore = data.items[i];
                    titleTop.append('<th width="80px">'+jobRatingCore.staffName+'</th>')
                    workingAttitude.append('<td width="80px"><input type="text" name="workingAttitude" disabled value="'+jobRatingCore.workingAttitude+'" /></a></td>')
                    teamSpirit.append('<td width="80px"><input type="text" name="teamSpirit" disabled value="'+jobRatingCore.teamSpirit+'" /></a></td>')
                    cultureCognition.append('<td width="80px"><input type="text" name="cultureCognition" disabled value="'+jobRatingCore.cultureCognition+'" /></a></td>')
                }
            }
        })
    });
})