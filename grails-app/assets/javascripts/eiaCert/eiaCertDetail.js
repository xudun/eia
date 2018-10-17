layui.use(['jquery', 'form', 'laydate'], function () {
    var $ = layui.jquery,
        form = layui.form,
        laydate = layui.laydate;
    var eiaCertId = parent.$("#eiaCertId").val();
    if(!eiaCertId){
         eiaCertId=$("#eiaCertId").val();
    }
    var certType={"CERT_TYPE_REVIEW":"送审","CERT_TYPE_APPROVAL":"报批"}
    var params = getParamByUrl(location.href)
    $.ajax({
        url: "/eia/eiaCert/getEiaCert",
        type: "POST",
        data: {eiaCertId: params.eiaCertId},
        dataType: "json",
        async: true,
        disableParent: true,
        success: function (res) {
            var data = res.data;
            $('#projectNo').text(data.projectNo);
            $('#projectName').text(data.projectName);
            $('#inputDept').text(data.inputDept);
            $('#inputUser').text(data.inputUser);
            $('#certType').text(certType[data.certType]);
            $('#meetDate').text(data.meetDate);
            $('#applyDate').text(data.applyDate);
            $('#certNum').text(data.certNum);
            if(data.certType == 'CERT_TYPE_REVIEW'){
                $('#meetDate').parent().removeClass("display-none")
            }
            var ifApplyCert = data.ifApplyCert ? "是" : "否";
            var ifSendCert = data.ifSendCert ? "是" : "否";
            var ifReportSeal = data.ifReportSeal ? "是" : "否";

            $('#ifApplyCert').text(ifApplyCert);
            $('#ifSendCert').text(ifSendCert);
            $('#ifReportSeal').text(ifReportSeal);
            $("#CertDown").on("click",function(){
                var loadingIndex = layer.load(1, {time: 10*1000,shade: 0.1});
                $.ajax({
                    url: "/eia/eiaContract/matchProve?eiaProjectId=" + data.eiaProjectId,
                    type: "POST",
                    data: {},
                    dataType: "json",
                    success: function (res) {
                        if (res.code == 0) {
                            window.location.href = "../eiaContract/exportProCert?eiaProjectId=" + data.eiaProjectId;
                            layer.msg('正在导出...', {icon: 16, shade: 0.01}, function () {
                                var index = parent.layer.getFrameIndex(window.name);
                                parent.layer.close(index);
                            })
                        } else {
                            layer.msg(res.msg, {icon: 2, time: 1000,shade: 0.1});
                        }
                        layer.close(loadingIndex);
                    }
                });
            });
            $("#projectCoverDown").on("click",function(){
                var loadingIndex = layer.load(1, {time: 10*1000,shade: 0.1});
                $.ajax({
                    url: "/eia/exportProject/checkProjectType?eiaProjectId=" + data.eiaProjectId,
                    type: "POST",
                    data: {},
                    dataType: "json",
                    success: function (res) {
                        if (res.code == 0) {
                            window.location.href = "../exportProject/exportProject?eiaProjectId=" + data.eiaProjectId;
                            layer.msg('正在导出...', {icon: 16, shade: 0.01}, function () {
                                var index = parent.layer.getFrameIndex(window.name);
                                parent.layer.close(index);
                            })
                        } else {
                            layer.msg(res.msg, {icon: 2, time: 1000,shade: 0.1});
                        }
                        layer.close(loadingIndex);
                    }
                });
            });
            if(data.ifApplyCert){
                $('.cert-num-item').css('display','block');
            }
        }
    });

});