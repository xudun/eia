layui.use(['layer'], function () {
    var $ = layui.$,
        layer = layui.layer;
    var eiaDomainCodeId = parent.$("#eiaDomainCodeId").val();
    $.ajax({
        url:"../eiaDomainCode/getEiaDomainCodeDataMap?eiaDomainCodeId="+eiaDomainCodeId,
        type: "get",
        cache: false,
        async: false,
        success: function (result) {
            $("#code").text(result.data.code);
            $("#codeDesc").text(result.data.codeDesc);
            $("#domain").text(result.data.domain);
            $("#domainDesc").text(result.data.domainDesc);
            $("#displayOrder").text(result.data.displayOrder);
            $("#codeLevel").text(result.data.codeLevel);
            $("#codeRemark").text(result.data.codeRemark);
            $("#parentId").text(result.data.parentId);
            $("#parentCode").text(result.data.parentCode);
        }
    });
});