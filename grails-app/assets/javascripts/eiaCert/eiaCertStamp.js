layui.use(['jquery', 'form','layer'], function () {
    var $ = layui.jquery,
        form = layui.form,
        layer = layui.layer;
    var tableNameId = getParamByUrl(window.location.href)["tableNameId"]
    $.ajax({
        url: "/eia/eiaCert/eiaCertStampDataMap",
        type:"post",
        data:{eiaCertId:tableNameId},
        cache: false,
        async: false,
        success: function (res) {
            var data = res.data;
            if(res.code == 0){
                if(data.stampType) { //印章类型
                    $("#print").removeClass("display-none");
                    var stampTypeList = data.stampType.split(",")
                    for (var i = 0; i < stampTypeList.length; i++) {
                        $('input[name="like[' + stampTypeList[i] + ']"]').attr("checked", true);
                    }
                    form.render('checkbox');
                }
                $("#projectName").text(data.projectName);
                $("#fileTypeChild").text(data.fileTypeChild);
                $("#contractNo").text(data.contractNo);
                $("#contractMoney").text(data.contractMoney+"万元");
                $("#otherFee").text(data.otherFee+"万元");
                $("#ifSign").text(data.ifSign?"是":"否");
                $("#dutyCard").text("是");
                $("#enviroMonitoringFee").text(data.enviroMonitoringFee+"万元");
                $("#income").text((data.income?data.income:0));
                if(data.proportion == '暂无数据'){
                    $("#proportion").text(data.proportion);
                }else{
                    $("#proportion").text(Math.round(data.proportion*10000)/100+"%");
                }
                $("#stampNote").val(data.stampNote);
            }else{
                $("#save").addClass("display-none");
                layer.msg(res.msg,{icon:2,time:1500});
            }
        }
    });

    $('.larry-btn a.layui-btn').click(function () {
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });
    var active = {
        //机构考核人员详情
        print: function () {
            var pageUrl = request_url_root + "/eiaCert/eiaCertStampPrint?tableNameId="+tableNameId
            layer.open({
                title: " ",
                type: 2,
                shade: false,
                maxmin: true,
                skin: 'larry-green',
                area: ['100%', '100%'],
                content: pageUrl,
                success: function (layero, index) {
                },
                min: function () {
                    $(".layui-layer-title").text("印章使用申请表");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            })
        },
        save:function () {
            $list = $("input[type=checkbox]:checked");
            var stampType=""
            for(var i=0;i<$list.length;i++){
                stampType=stampType+$list.eq(i).val()+",";
            }
            var stampNote = $("#stampNote").val();
            $.ajax({
                url: "/eia/eiaCert/eiaCertUpdate",
                type:"post",
                data:{eiaCertId:tableNameId,stampNote:stampNote,stampType:stampType},
                cache: false,
                async: false,
                success: function (res) {
                    if(res.code == 0){
                        layer.msg("保存成功！",{icon:1,time:1500});
                    }
                    var data = res.data;
                    if(data.stampType){//印章类型
                        var stampTypeList  = data.stampType.split(",");
                        for(var i=0;i<stampTypeList.length;i++){
                            if(stampTypeList[i]){
                                $('input[name="like['+stampTypeList[i]+']"]').attr("checked",true);
                            }

                        }
                        $("#stampNote").val(data.stampNote);
                        $("#print").removeClass("display-none");
                    }
                }
            });
        }
    }
});