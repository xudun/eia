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
            //机构考核人员详情
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
                            parent.eiaHrEvalEmpContent();

                        });
                    }else {
                        layer.alert("处理失败！<br/><div style='color:red;'>错误信息：" + data.msg +"</div>",{icon:2,time:1500});
                    }
                    layer.close(loadingIndex);
                });
            }
        }
        var total
        $.ajax({
           // url:"/eia/static/js/layuiFrame/data/eiaHrEvalDMToAssInput.json",
            url:"/eia/eiaHrEvalScoreDetail/eiaHrEvalEmpEdit?eiaHrEvalId="+eiaHrEvalId,
            //url:"../authMenu/getAuthMenuDataMap?menuId="+menuId+"&funcId="+funcId,
            type:"get",
            cache: false,
            async: false,
            success: function (data) {
                total = data.total;
                $("#jobRatingName").val(data.staffIdList);
                $("#staffNameList").val(data.staffNameList);
                var docWidth = document.body.clientWidth
                if(docWidth<=973){
                    $("#personTable").css("width",docWidth-651+"px");
                }else{
                    $("#personTable").css("width",docWidth-841+"px");
                }
                var width = total*81
                $("#rightTitle").css("width",width+"px")
                var top=$(".titleTop"); //得到导航对象
                var sc=$(document);//得到document文档对象。
                var isFixed = false;
                //sc.scroll(function(){
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
                //})
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
                    var leadership = $("#leadership");
                    leadership.empty();
                    var scoreId = $("#scoreId");
                    scoreId.empty();
                    var jobRatingList = data.items
                    for(var i in jobRatingList) {
                        var jobRatingCore = data.items[i];
                        if(data.showType){
                            titleTop.append('<th width="80px"><a href="#" onclick="staffRoting('+jobRatingCore.staffId+')">'+jobRatingCore.staffName+'</th>')
                            mt1.append('<th width="80px"><a href="#" onclick="staffRoting('+jobRatingCore.staffId+')">'+jobRatingCore.staffName+'</th>')
                            workExecution.append('<td width="80px"><input type="text" name="workExecution" value="'+jobRatingCore.workExecution+'" /></td>')
                            performance.append('<td width="80px"><input type="text" name="performance" value="'+jobRatingCore.performance+'" /></td>')
                            workingAttitude.append('<td width="80px"><input type="text" name="workingAttitude" value="'+jobRatingCore.workingAttitude+'" /></td>')
                            leadership.append('<td width="80px"><input type="text" name="leadership" value="'+jobRatingCore.leadership+'" /></td>')
                        }else{
                            titleTop.append('<th width="80px"><a href="#" onclick="staffRoting('+jobRatingCore.id+')">'+jobRatingCore.staffName+'</th>')
                            mt1.append('<th width="80px"><a href="#" onclick="staffRoting('+jobRatingCore.id+')">'+jobRatingCore.staffName+'</th>')
                            workExecution.append('<td width="80px"><input type="text" name="workExecution" value="" /></td>')
                            performance.append('<td width="80px"><input type="text" name="performance" value="" /></td>')
                            workingAttitude.append('<td width="80px"><input type="text" name="workingAttitude" value="" /></td>')
                            leadership.append('<td width="80px"><input type="text" name="leadership" value="" /></td>')
                        }
                        scoreId.append('<td><button type="button" class="remarkButton" onclick="assComment('+jobRatingCore.id+')">员工评语</button></td>')
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
            }
        });
    });
})
function staffRoting(staffId){
    var pageUrl = "/eia/eiaHrEvalScoreDetail/eiaHrEvalAssDetail?staffId="+staffId;
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
function assComment(eiaHrEvalScoreDetailId){
    layui.use( 'layer', function() {
        var layer = layui.layer;
        var jobRatingType = parent.$("#jobRatingType").val();
        var assessmentMonth = parent.$("#assessmentMonth").val();
        $("#eiaHrEvalScoreDetailId").val(eiaHrEvalScoreDetailId);
        $.ajax({
            type: 'POST',
            url: '/eia/eiaHrEvalScoreDetail/isEiaHrEvalScore?eiaHrEvalScoreDetailId='+eiaHrEvalScoreDetailId,
            success: function (data) {
                if(data== 'isNotScore'){
                    layer.msg('请先打分再评语！', {icon:7});
                    return;
                }else{
                    var pageUrl = "/eia/eiaHrEvalScoreDetail/eiaHrEvalComment?eiaHrEvalScoreDetailId="+eiaHrEvalScoreDetailId
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
function clearForm(){
    $("input[name='workExecution']").val('')
    $("input[name='performance']").val('')
    $("input[name='workingAttitude']").val('')
    $("input[name='leadership']").val('')
}