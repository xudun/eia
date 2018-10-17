layui.use(['jquery', 'layer', 'form'], function() {
    var $ = layui.jquery,
        layer = layui.layer,
        form = layui.form;

    //考核年下拉
    var ratingYearEmp = $("#ratingYearEmp")
    var ratingYearAss = $("#ratingYearAss")
    var date=new Date;
    var year=date.getFullYear();
    for(var i=0;i<5;i++){
        ratingYearEmp.append('<option value="'+(year-i)+'">'+(year-i)+'年</option>');
        ratingYearAss.append('<option value="'+(year-i)+'">'+(year-i)+'年</option>');
    }
    form.render('select');
})
//普通员工导出
function empExport(){
    layui.use('layer', function(){
        var layer = layui.layer;
        layer.confirm("确定要导出所有员工考核信息？",{icon:3}, function(index){
            layer.msg('正在导出...', {icon: 16,shade: 0.01});
            window.location.href = "/eia/eiaHrEvalScore/eiaHrEvalScoreExport";
        });
    })
}
//部门助理导出
function assExport(){
    layui.use('layer', function(){
        var layer = layui.layer;
        layer.confirm("确定要导出所有助理考核信息？",{icon:3}, function(index){
            layer.msg('正在导出...', {icon: 16,shade: 0.01});
            window.location.href = "/eia/eiaHrEvalScore/eiaHrEvalScoreAssExport";
        });
    })
}
//普通员工查询
function getEmpSelect(){
    eiaHrEvalEmpContent();
}
function getWhereEmp(){
    var ratingYearEmp = $('#ratingYearEmp').val();
    var ratingMonthEmp = $('#ratingMonthEmp').val();
    var where = {
        ratingYear:ratingYearEmp,
        ratingMonth:ratingMonthEmp
    }
    return where;
}
function eiaHrEvalEmpContent(){
    var where = getWhereEmp()
    $.post("/eia/eiaHrEvalScore/getEiaHrEvalScoreDataList",{where:where},function(data){
        var cont =$("#eiaHrEvalEmpContent");
        cont.empty();
        if(data.total>0){
            var jobRating = data.jobRatingScoreDeptList
            var jobRatingList = data.items
            var jobRatingMemo = "<tr  style='border-style:none' ><td  colspan='10' style='border-style:none;' ><font color='red'>注:计算公式，员工评分表 =工作绩效（总经理打分）+工作态度（员工互评40%+总经理打分60%），其中员工互评等于去掉最高、最低打分除以人数-2 </font></td></tr>"
            for(var i in jobRating){
                var user  = data.jobRatingScoreDeptList[i];
                var dept  = data.jobRatingScoreDeptListId[i];
                var iconAdd = 'iconAdd'+dept
                var iconMin = 'iconMin'+dept
                cont.append("<tr>"+
                    "<td><i id="+iconAdd+" class='larry-icon display-none' onclick='fold("+dept+")'>&#xe849;</i><i id="+iconMin+" class='larry-icon' onclick='fold("+dept+")'>&#xe8e2;</i>"+user+"</td>"+
                    "<td>"+"-"+"</td>"+
                    "<td>"+"-"+"</td>"+
                    "<td>"+"-"+"</td>"+
                    "<td>"+"-"+"</td>"+
                    "<td>"+"-"+"</td>"+
                    "<td>"+"-"+"</td>"+
                    "<td>"+"-"+"</td>"+
                    "<td>"+"-"+"</td>"+
                    "<td>"+"-"+"</td>"+
                    "</td></tr>");
                for(var j in jobRatingList) {
                    var jobRatingCore = data.items[j];
                    var classDept = 'dept'+dept
                    if (user == (jobRatingCore.inputDept)){
                        cont.append("<tr class="+classDept+">" +
                            "<td>"+(jobRatingCore.staffName)+"</td>"+
                            "<td>"+(jobRatingCore.assessmentMonth)+"</td>" +
                            "<td>"+(jobRatingCore.workExecution==null?"0":jobRatingCore.workExecution)+"</td>" +
                            "<td>"+(jobRatingCore.performance==null?"0":jobRatingCore.performance)+"</td>" +
                            "<td>"+(jobRatingCore.jobSkill==null?"0":jobRatingCore.jobSkill)+"</td>" +
                            "<td>"+(jobRatingCore.workingAttitude==null?"0":jobRatingCore.workingAttitude)+"</td>" +
                            "<td>"+(jobRatingCore.teamSpirit==null?"0":jobRatingCore.teamSpirit)+"</td>" +
                            "<td>"+(jobRatingCore.cultureCognition==null?"0":jobRatingCore.cultureCognition)+"</td>" +
                            "<td>"+(jobRatingCore.finalScore==null?"0":jobRatingCore.finalScore)+"</td>" +
                            "<td style='text-align:contracter;'>"+
                            "<a class='layui-btn layui-btn-normal layui-btn-sm' onclick='editEmpRating("+jobRatingCore.id+")' title='编辑'><i class='larry-icon'>&#xe72b;</i></a>" +
                            "<a class='layui-btn layui-btn-normal layui-btn-sm' onclick='printEmpRating("+jobRatingCore.id+")' title='打印'><i class='larry-icon'>&#xe850;</i></a>" +
                            "</td></tr>");
                    }

                }
            }
            cont.append(jobRatingMemo);
        }else{
            cont.append("<tr><td style='color:green;text-align:center;' colspan='10'>无记录！</td></tr>");
        }
    })
}
eiaHrEvalEmpContent();

//部门助理查询
function getAssSelect(){
    eiaHrEvalAssContent();
}
function getWhereAss(){
    var ratingYearAss = $('#ratingYearAss').val();
    var ratingMonthAss = $('#ratingMonthAss').val();
    var where = {
        ratingYear:ratingYearAss,
        ratingMonth:ratingMonthAss
    }
    return where;
}
function eiaHrEvalAssContent(){
    var where = getWhereAss()
    var staticAss = "ass"
    $.post("/eia/eiaHrEvalScore/getEiaHrEvalScoreDataList?staticAss="+staticAss,{where:where},function(data){
        var cont =$("#eiaHrEvalAssContent");
        cont.empty();
        if(data.total>0){
            var jobRating = data.jobRatingScoreDeptList
            var jobRatingList = data.items
            var jobRatingMemo = "<tr  style='border-style:none' ><td colspan='11' style='border-style:none;' ><font color='red'>注:计算公式，助理评分表 = 去掉最高、最低打分除以人数-2 + 部门总经理评分  </font></td></tr>"
            for(var i in jobRating){
                var user  = data.jobRatingScoreDeptList[i];
                var dept  = data.jobRatingScoreDeptListId[i];
                var iconAdd = 'iconAdd'+dept
                var iconMin = 'iconMin'+dept
                cont.append("<tr>"+
                    "<td><i id="+iconAdd+" class='larry-icon display-none' onclick='fold("+dept+")'>&#xe849;</i><i id="+iconMin+" class='larry-icon' onclick='fold("+dept+")'>&#xe8e2;</i>"+user+"</td>"+
                    "<td>"+"-"+"</td>"+
                    "<td>"+"-"+"</td>"+
                    "<td>"+"-"+"</td>"+
                    "<td>"+"-"+"</td>"+
                    "<td>"+"-"+"</td>"+
                    "<td>"+"-"+"</td>"+
                    "<td>"+"-"+"</td>"+
                    "<td>"+"-"+"</td>"+
                    "<td>"+"-"+"</td>"+
                    "<td>"+"-"+"</td>"+
                    "</td></tr>");
                for(var j in jobRatingList) {
                    var jobRatingCore = data.items[j];
                    var classDept = 'dept'+dept
                    if (user == (jobRatingCore.inputDept)){
                        cont.append("<tr class="+classDept+">" +
                            "<td>"+(jobRatingCore.staffName)+"</td>"+
                            "<td>"+(jobRatingCore.assessmentMonth)+"</td>" +
                            "<td>"+(jobRatingCore.teamSpirit==null?"0":jobRatingCore.teamSpirit)+"</td>" +
                            "<td>"+(jobRatingCore.leadership==null?"0":jobRatingCore.leadership)+"</td>" +
                            "<td>"+(jobRatingCore.proAbility==null?"0":jobRatingCore.proAbility)+"</td>" +
                            "<td>"+(jobRatingCore.workExecution==null?"0":jobRatingCore.workExecution)+"</td>" +
                            "<td>"+(jobRatingCore.performance==null?"0":jobRatingCore.performance)+"</td>" +
                            "<td>"+(jobRatingCore.workingAttitude==null?"0":jobRatingCore.workingAttitude)+"</td>" +
                            "<td>"+(jobRatingCore.leadershipManager==null?"0":jobRatingCore.leadershipManager)+"</td>" +
                            "<td>"+(jobRatingCore.finalScore==null?"0":jobRatingCore.finalScore)+"</td>" +
                            "<td style='text-align:contracter;'>"+
                            "<a class='layui-btn layui-btn-normal layui-btn-sm' onclick='editAssRating("+jobRatingCore.id+")' title='编辑'><i class='larry-icon'>&#xe72b;</i></a>" +
                            "<a class='layui-btn layui-btn-normal layui-btn-sm' onclick='printAssRating("+jobRatingCore.id+")' title='打印'><i class='larry-icon'>&#xe850;</i></a>" +
                            "</td></tr>");
                    }
                }
            }
            cont.append(jobRatingMemo);
        }else{
            cont.append("<tr><td style='color:green;text-align:center;' colspan='11'>无记录！</td></tr>");
        }
    })
}
eiaHrEvalAssContent();

function fold(deptId){
    if($(".dept"+deptId).css("display")=="none"){
        $(".dept"+deptId).removeClass("display-none");
        $("#iconAdd"+deptId).addClass("display-none");
        $("#iconMin"+deptId).removeClass("display-none");
    }else{
        $(".dept"+deptId).addClass("display-none");
        $("#iconAdd"+deptId).removeClass("display-none");
        $("#iconMin"+deptId).addClass("display-none");
    }
}
//普通员工评分编辑页面
function editEmpRating(jobRatingCoreId){
    $('#eiaHrEvalScoreId').val(jobRatingCoreId)
    layui.use('layer', function(){
        var layer = layui.layer;
        var pageUrl = "/eia/eiaHrEvalScore/eiaHrEvalEmpEdit?eiaHrEvalScoreId="+jobRatingCoreId;
        layer.open({
            type : 2,
            title :" ",
            shade: false,
            maxmin: true,
            skin: 'larry-green',
            area: ['100%', '100%'],
            content: pageUrl,
            min: function () {
                $(".layui-layer-title").text("员工考核编辑");
            },
            restore: function () {
                $(".layui-layer-title").text(" ");
            }
        });
    })
}
//部门助理评分编辑页面
function editAssRating(jobRatingCoreId){
    $('#eiaHrEvalScoreId').val(jobRatingCoreId)

    layui.use('layer', function() {
        var layer = layui.layer;
        var pageUrl = "/eia/eiaHrEvalScore/eiaHrEvalAssEdit?eiaHrEvalScoreId="+jobRatingCoreId;
        layer.open({
            type : 2,
            title :" ",
            shade: false,
            maxmin: true,
            skin: 'larry-green',
            area: ['100%', '100%'],
            content: pageUrl,
            min: function () {
                $(".layui-layer-title").text("助理考核编辑");
            },
            restore: function () {
                $(".layui-layer-title").text(" ");
            }
        });
    })
}
//普通员工打印页面
function printEmpRating(jobRatingCoreId){
    var jobRatingCoreId  = jobRatingCoreId;
    layui.use('layer', function(){
        var layer = layui.layer;
        if(jobRatingCoreId){
            var pageUrl = "/eia/eiaHrEvalScore/eiaHrEvalPrintEmp?eiaHrEvalScoreId="+jobRatingCoreId;
            layer.open({
                type : 2,
                title :" ",
                shade: false,
                maxmin: true,
                skin: 'larry-green',
                area: ['100%', '100%'],
                content: pageUrl,
                min: function () {
                    $(".layui-layer-title").text("员工考核表");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        }
    })
}
//部门助理打印页面
function printAssRating(jobRatingCoreId){
    var jobRatingCoreId  = jobRatingCoreId;
    layui.use('layer', function(){
        var layer = layui.layer;
        if(jobRatingCoreId){
            var pageUrl = "/eia/eiaHrEvalScore/eiaHrEvalPrintAss?eiaHrEvalScoreId="+jobRatingCoreId;
            layer.open({
                type : 2,
                title :" ",
                shade: false,
                maxmin: true,
                skin: 'larry-green',
                area: ['100%', '100%'],
                content: pageUrl,
                min: function () {
                    $(".layui-layer-title").text("助理考核表");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        }
    })
}