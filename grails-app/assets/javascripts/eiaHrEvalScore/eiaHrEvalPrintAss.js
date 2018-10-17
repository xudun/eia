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
    var eiaHrEvalScoreId = $('#eiaHrEvalScoreId').val();
    var eiaHrEvalId = $('#eiaHrEvalId').val();
    $.get("/eia/eiaHrEvalScore/eiaHrEvalAssList?eiaHrEvalScoreId="+eiaHrEvalScoreId+"&eiaHrEvalId="+eiaHrEvalId,function(data){
        $(".staffName").text(data.staffName)
        $("#leaderComments").val(data.leaderComments)
        $("#orgCode").text(data.orgCode)
        $("#orgName").text(data.orgName)
        $("#assessmentMonth").text(data.assessmentMonth)
        $("#roleName").text(data.roleName)
        $("#leader").text(data.leader)
        $("#teamSpirit").val(data.teamSpirit)
        $("#leadership").val(data.leadership)
        $("#proAbility").val(data.proAbility)
        $("#workExecution").val(data.workExecution)
        $("#performance").val(data.performance)
        $("#workingAttitude").val(data.workingAttitude)
        $("#leadershipManager").val(data.leadershipManager)
        $("#finalScore").val(data.finalScore)

    })
})