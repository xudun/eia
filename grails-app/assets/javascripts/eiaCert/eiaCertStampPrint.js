layui.use(['layer'], function () {
    var $ = layui.jquery,
        layer = layui.layer;
var tableNameId = getParamByUrl(window.location.href).tableNameId;
    $.ajax({
        url: "/eia/eiaCert/getEiaCertStampPrintDataMap",
        type:"post",
        data:{eiaCertId:tableNameId},
        cache: false,
        async: false,
        success: function (res) {
            var data = res.data
            var stampType = $("#stampType");
            var stampTypeList = data.stampType.split(",")
            var stampMap = {"LHCD":"联合赤道","LHTZ":"联合泰泽","FR":"法人章"}
            for(var i=0;i<stampTypeList.length;i++){
                if(stampTypeList[i]){
                    stampType.append('<span class="mr15">'+stampMap[stampTypeList[i]]+'</span>')
                }
            }
            $("#projectName").text(data.projectName)
            $("#fileTypeChild").text(data.fileTypeChild)
            $("#HGSH").append('<img style="height: 70px;" src="'+data.HGSH+'">')
            $("#stampNote").text(data.stampNote)
            $("#contractNo").text(data.contractNo)
            $("#CWSH").append('<img style="height: 70px;" src="'+data.CWSH+'">')
            $("#ifSign").text(data.ifSign?"是":"否")
            $("#contractMoney").text(data.contractMoney+"万元")
            $("#enviroMonitoringFee").text((data.enviroMonitoringFee?data.enviroMonitoringFee:"0")+"万元")
            $("#otherFee").text((data.otherFee?data.otherFee:"0")+"万元")
            if(data.proportion == '暂无数据'){
                $("#proportion").text(data.proportion);
            }else{
                $("#proportion").text(Math.round(data.proportion*10000)/100+"%");
            }
            $("#ZJLSH").append('<img style="height: 70px;" src="'+data.ZJLSH+'">')
            $("#BMJLSH").append('<img style="height: 70px;" src="'+data.BMJLSH+'">')
            $("#income").text((data.income?data.enviroMonitoringFee:"0")+"万元")
            var currentDate = new Date();
            var year = currentDate.getFullYear()
            var month = currentDate.getMonth()+1
            var date = currentDate.getDate()
            $("#currentDate").append(year+"年"+month+"月"+date+"日")
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