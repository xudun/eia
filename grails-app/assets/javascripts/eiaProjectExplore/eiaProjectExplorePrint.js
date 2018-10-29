layui.use(['jquery', 'form'], function(){
    var $ = layui.jquery,
        form = layui.form;

    $("#eiaProjectExploreId").val(params.eiaProjectExploreId)
    $.post("../eiaProjectExplore/getEiaProjectExploreDataMapDomainCode", {eiaProjectExploreId: params.eiaProjectExploreId}, function (data) {
        var data = data.data;
        for (var key in data) {
            /**下拉树数据渲染**/
            if (key == "environmentaType") {
                $("#environmentaTypeDrop").text(data[key])
            }

            $("#" + key).text(data[key])

            if(key == "otherSense"){
                var eleId = key + "QT"
                if (data[key+"Code"].indexOf("QT")>=0) {
                    $("#"+key).append("   ")
                    $("#"+key).append(data[key+"Info"])
                } else {
                }
            }
        }
        /****立项情况显示*/
        if(data.ifSet){
            if (data.ifSet == "是") {
                $(".setInfo").remove()
            } else if (data.ifSet == "否") {
                $(".approvalType").remove()
            }
        }

        /***其他****/
        if(data.zlnodeimg){
            $('#zlnodei').append('<img style="height: 70px;" src="'+data.zlnodeimg+'">');
        }

        if(data.jlnodeimg){
            $('#jlnodei').append('<img style="height: 70px;" src="'+data.jlnodeimg+'">');
        }
    })



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