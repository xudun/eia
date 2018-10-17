layui.use(['jquery','layer'], function() {
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

        var total;
        //  $.get("/eia/static/js/layuiFrame/data/eiaHrEvalEmpToEmpInput.json",function(data){
        $.post("/eia/eiaHrEvalScoreDetail/eiaHrEvalEmpEdit?eiaHrEvalId="+eiaHrEvalId,function(data){
            total = data.total;
            $("#jobRatingName").val(data.staffIdList);
            $("#staffNameList").val(data.staffNameList);
            var docWidth = document.body.clientWidth;
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
                    if(data.showType){
                        titleTop.append('<th width="80px">'+jobRatingCore.staffName+'</th>')
                        //mt1.append('<th width="80px">'+jobRatingCore.staffName+'</th>')
                        workingAttitude.append('<td width="80px"><input type="text" name="workingAttitude" value="'+jobRatingCore.workingAttitude+'" /></a></td>')
                        teamSpirit.append('<td width="80px"><input type="text" name="teamSpirit" value="'+jobRatingCore.teamSpirit+'" /></a></td>')
                        cultureCognition.append('<td width="80px"><input type="text" name="cultureCognition" value="'+jobRatingCore.cultureCognition+'" /></a></td>')
                    }else{
                        titleTop.append('<th width="80px"><a href="#" class="staffName">'+jobRatingCore.staffName+'</a></th>')
                        //mt1.append('<th width="80px"><a href="#" class="staffName">'+jobRatingCore.staffName+'</a></th>')
                        workingAttitude.append('<td width="80px"><input type="text" name="workingAttitude" value="" /></td>')
                        teamSpirit.append('<td width="80px"><input type="text" name="teamSpirit" value="" /></td>')
                        cultureCognition.append('<td width="80px"><input type="text" name="cultureCognition" value="" /></td>')
                    }

                }
            }
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
        })
    });
})
function clearForm(){
    $("input[name='workingAttitude']").val('')
    $("input[name='teamSpirit']").val('')
    $("input[name='cultureCognition']").val('')
}