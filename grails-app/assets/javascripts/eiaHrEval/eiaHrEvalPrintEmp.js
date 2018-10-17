layui.use(['jquery', 'layer', 'table'], function() {
    var $ = layui.jquery,
        layer = layui.layer;

    $('.larry-btn a.layui-btn').click(function () {
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });
    var active = {
        printReport: function () {
            window.print();
        }
    }

    $.get("/eia/static/js/layuiFrame/data/eiaHrEvalPrintEmp.json",function(data){
        $(".staffName").text(data.staffName)
        $("#orgCode").text(data.orgCode)
        $("#orgName").text(data.orgName)
        $("#assessmentMonth").text(data.assessmentMonth)
        $("#roleName").text(data.roleName)
        $("#leader").text(data.leader)
        $("#workExecution").val(data.workExecution)
        $("#performance").val(data.performance)
        $("#jobSkill").val(data.jobSkill)
        $("#workingAttitude").val(data.workingAttitude)
        $("#teamSpirit").val(data.teamSpirit)
        $("#cultureCognition").val(data.cultureCognition)
        $("#finalScore").val(data.finalScore)
        $("#leaderComments").text(data.leaderComments)
        //if(data.total>0){
        //    var staffName = $("#staffName");
        //    var workExecution = $("#workExecution");
        //    var performance = $("#performance");
        //    var jobSkill = $("#jobSkill");
        //    var workingAttitude = $("#workingAttitude");
        //    var teamSpirit = $("#teamSpirit");
        //    var cultureCognition = $("#cultureCognition");
        //    var finalScore = $("#finalScore");
        //    staffName.empty();
        //    workExecution.empty();
        //    performance.empty();
        //    jobSkill.empty();
        //    workingAttitude.empty();
        //    teamSpirit.empty();
        //    cultureCognition.empty();
        //    finalScore.empty();
        //    var jobRatingList = data.items
        //    for(var i in jobRatingList) {
        //        var jobRatingCore = data.items[i];
        //        staffName.append("<th width='80px'>"+jobRatingCore.staffName+"</th>");
        //        workExecution.append("<td width='80px'><input type='text' name='workExecution' disabled value="+jobRatingCore.workExecution+" /></td>");
        //        performance.append("<td width='80px'><input type='text' name='performance' disabled value="+jobRatingCore.performance+" /></td>");
        //        jobSkill.append("<td width='80px'><input type='text' name='jobSkill' disabled value="+jobRatingCore.jobSkill+" /></td>");
        //        workingAttitude.append("<td width='80px'><input type='text' name='workingAttitude' disabled value="+jobRatingCore.workingAttitude+" /></td>");
        //        teamSpirit.append("<td width='80px'><input type='text' name='teamSpirit' disabled value="+jobRatingCore.teamSpirit+" /></td>");
        //        cultureCognition.append("<td width='80px'><input type='text' name='cultureCognition' disabled value="+jobRatingCore.cultureCognition+" /></td>");
        //        finalScore.append("<th width='80px'><input type='text' name='finalScore' disabled value="+jobRatingCore.finalScore+" /></th>");
        //    }
        //}else{
        //    //cont.append("<tr><td style='color:green;text-align:center;' colspan='10'>无记录！</td></tr>");
        //}
    })
})