layui.use(['jquery', 'form'], function(){
    var $ = layui.jquery,
        form = layui.form;

    var params = getParamByUrl(window.location.href);
    console.log(params)
    //回显数据
    $.ajax({
        url:"/eia/eiaStamp/eiaStampPrintDataMap" ,
        type:"POST",
        data:params,
        dataType: "json",
        success: function (result) {
            var data = result.data;
            console.log(data);
            $("#inputDept").text(data.inputDept);
            $("#inputUser img").attr('src', data.inputUser);
            $("#appTime").text(data.appTime);
            $("#stampNum").text(data.stampNum);
            $("#appReason").text(data.appReason);
            $("#ifTakeOut").text(data.ifTakeOut);
            $("#supervisor").text(data.supervisor);
            $("#stampType").text(data.stampType);
            $("#stampCompany").text(data.stampCompany);
            $("#userName img").attr('src', data.inputUser);
            $("#complianceUser img").attr('src', data.complianceUser);
            $("#deptManager img").attr('src', data.deptManager);
            $("#topManager img").attr('src', data.topManager);
            $("#xzManager img").attr('src', data.xzManager);
            $("#archiver img").attr('src', data.archiver);
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