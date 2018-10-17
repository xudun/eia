layui.use(['jquery', 'layer'], function() {
    var $ = layui.jquery,
        layer = layui.layer;

    $('.larry-btn a.layui-btn').click(function () {
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });
    var eiaHrEvalScoreId = $('#eiaHrEvalScoreId').val();

    var active = {
        //机构考核人员详情
        save: function () {
            var actionUrl = "/eia/eiaHrEvalScore/eiaHrEvalScoreSave";
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
                        parent.eiaHrEvalEmpContent();
                        parent.layer.closeAll();
                    });
                }else {
                    layer.alert("处理失败！<br/><div style='color:red;'>错误信息：" + data.msg +"</div>",{icon:2,time:1500});
                }
                layer.close(loadingIndex);
            });
        }
    }

    $.ajax({
    //   url:"/eia/static/js/layuiFrame/data/empEdit.json",
        url:"/eia/eiaHrEvalScore/eiaHrEvalEmpList?eiaHrEvalScoreId="+eiaHrEvalScoreId,
        type:"post",
        cache: false,
        async: false,
        success: function (result) {
            $("#orgCode").text(result.orgCode);
            $("#orgName").text(result.orgName);
            $("#assessmentMonth").text(result.assessmentMonth);
            $("#leader").text(result.leader);
            $("#jobRatingScoreId").val(result.jobRatingScoreId);
            $(".staffName").text(result.staffName);
            $("#workExecution").val(result.workExecution);
            $("#performance").val(result.performance);
            $("#jobSkill").val(result.jobSkill);
            $("#workingAttitude").val(result.workingAttitude);
            $("#teamSpirit").val(result.teamSpirit);
            $("#cultureCognition").val(result.cultureCognition);
            $("#staffId").val(result.staffId);
            $("#jobRatingType").val(result.jobRatingType);
            $("#orgId").val(result.orgId);
        }
    });

    $("#empComment").click(function(){
        var staffId = $("#staffId").val();
        var jobRatingType = $("#jobRatingType").val();
        var assessmentMonth = $("#assessmentMonth").text();
        var orgId  = $("#orgId").val();
        var pageUrl = "/eia/eiaHrEval/eiaHrEvalComment?staffId="+staffId+"&jobRatingType="+jobRatingType+"&assessmentMonth="+assessmentMonth+"&orgId="+orgId
        layer.open({
            type : 2,
            title :" ",
            maxmin: true,
            skin: 'larry-green',
            area: ['100%', '100%'],
            content: pageUrl,
            min: function () {
                $(".layui-layer-title").text("领导评语");
            },
            restore: function () {
                $(".layui-layer-title").text(" ");
            }
        });
    })

    $(".staffName").click(function(){
        var staffId = $("#staffId").val();
        var title = "打分详细表";
        var pageUrl = "/eia/eiaHrEval/eiaHrEvalEmpDetail?staffId="+staffId;
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

    $(function(){
        var docWidth = document.body.clientWidth
        if(docWidth<=973){
            $("#personTable").css("width",docWidth-651+"px");
        }else{
            $("#personTable").css("width",docWidth-841+"px");
        }
        var width = 82;
        $("#rightTitle").css("width",width+"px")
        var top=$(".titleTop"); //得到导航对象
        var sc=$(document);//得到document文档对象。
        var isFixed = false;
        //console.log(sc);
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
        var col = $(this).parent().index();
        var row = $(this).parent().parent().index();

        var rows_num = $("#perTable tr").length;
        var sum = 0;
        for(var i = 0 ; i < rows_num-2; i++){
            if($("#perTable tr").eq(i).find("td").eq(col).has("input").length){
                sum += parseInt($("#perTable tr").eq(i).find("td").eq(col).find("input").val());
            }
        }
        if(sum>=90 && sum<=100){
            $("#perTable tr").eq(rows_num-2).find("th").eq(col).find("input").val("优秀："+sum);
        }else if(sum>=80 && sum<90){
            $("#perTable tr").eq(rows_num-2).find("th").eq(col).find("input").val("良好："+sum);
        }else if(sum>=70 && sum<80){
            $("#perTable tr").eq(rows_num-2).find("th").eq(col).find("input").val("合格："+sum);
        }else if(sum<70){
            $("#perTable tr").eq(rows_num-2).find("th").eq(col).find("input").val("不合格："+sum);
        }else{
            $("#perTable tr").eq(rows_num-2).find("th").eq(col).find("input").val("数据错误");
        }
    });
})
function clearForm(){
    $("input[name='workExecution']").val('')
    $("input[name='performance']").val('')
    $("input[name='jobSkill']").val('')
    $("input[name='workingAttitude']").val('')
    $("input[name='teamSpirit']").val('')
    $("input[name='cultureCognition']").val('')
    $("input[name='leadership']").val('')
    $("input[name='leadershipManager']").val('')
    $("input[name='proAbility']").val('')

}