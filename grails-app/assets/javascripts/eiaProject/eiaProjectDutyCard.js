//表单数据
var eiaProjectId = $("#eiaProjectId").val();
$.ajax({
    url: "/eia/eiaProject/getDutyCardDataMap",
    data:{eiaProjectId:eiaProjectId},
    type:"get",
    async: true,
    success: function (res) {
            var data = res.data
        $('#contractNo').text(data.contractNo);
        $('#projectNo').text(data.projectNo);
        $('#projectName').text(data.projectName);
        $('#contractDate').text(data.contractDate);
        $('#completeDate').text(data.completeDate);
        $('#dutyUser').text(data.dutyUser);
        $('#projectGroup').text(data.projectGroup);
        $('#projectApproval2').text(data.projectApproval2);
        $('#projectApproval3').text(data.projectApproval3);
        $('#record').html(data.record);
        $('#approval').append('<img style="height: 70px;" src="'+data.approval+'">');
        $('#review').append('<img style="height: 70px;" src="'+data.review+'">');
    }
});