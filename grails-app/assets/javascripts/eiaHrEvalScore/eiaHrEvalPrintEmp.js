layui.use(['jquery', 'layer', 'table'], function() {
    var $ = layui.jquery,
        layer = layui.layer;

    $('.larry-btn a.layui-btn').click(function () {
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });
    var eiaHrEvalScoreId = $('#eiaHrEvalScoreId').val();
    var eiaHrEvalId = $('#eiaHrEvalId').val();
    var active = {
        printReport: function () {
            window.print();
        }
    }
    $.ajax({
        //   url:"/eia/static/js/layuiFrame/data/empEdit.json",
        url:"/eia/eiaHrEvalScore/eiaHrEvalPrintEmpList?eiaHrEvalScoreId="+eiaHrEvalScoreId+"&eiaHrEvalId="+eiaHrEvalId,
        type:"get",
        cache: false,
        async: false,
        success: function (result) {
            $("#orgCode").text(result.orgCode);
            $("#orgName").text(result.orgName);
            $("#assessmentMonth").text(result.assessmentMonth);
            $("#leader").text(result.leader);
            $("#roleName").text(result.roleName);
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
            $("#finalScore").val(result.finalScore);
            $("#leaderComments").val(result.leaderComments);
        }
    });
})