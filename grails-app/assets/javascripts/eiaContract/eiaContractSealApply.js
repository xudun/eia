layui.use(['jquery', 'form'], function(){
    var $ = layui.jquery,
        form = layui.form;

    var params = getParamByUrl(window.location.href);

    //回显数据
    $.ajax({
        url:"/eia/eiaContract/contractSealApplyApprove" ,
        type:"POST",
        data:params,
        dataType: "json",
        success: function (result) {
            var data = result.data;
            console.log(data);
            $("#inputDept").text(data.inputDept);
            $("#inputUser img").attr('src', data.inputUser);
            $("#applyDate").text(data.applyDate);
            $("#contractTrust").text(data.contractTrust);
            $("#contractNo").text(data.contractNo);
            $("#contractName").text(data.contractName);
            $("#contractType").text(data.contractType);
            $("#contractMoney").text(data.contractMoney);
            $("#reportFees").text(data.reportFees);
            $("#enviroMonitoringFee").text(data.enviroMonitoringFee);
            $("#groundWater").text(data.groundWater);
            $("#expertFee").text(data.expertFee);
            $("#otherFee").text(data.otherFee);
            $("#userName img").attr('src', data.inputUser);
            $("#complianceUser img").attr('src', data.complianceUser);
            $("#deptManager img").attr('src', data.deptManager);
            $("#topManager img").attr('src', data.topManager);
        }
    });
    $('.larry-btn a.layui-btn').click(function () {
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });
    var active = {
        print: function () {
            $("#print").addClass("display-none")
            window.print();
            $("#print").removeClass("display-none")
        }
    }
});