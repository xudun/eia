layui.use(['layer', 'form', 'table'], function () {
    var $ = layui.$,
        layer = layui.layer,
        form = layui.form,
        $form = $('form');
    var eiaLabOfferId = parent.$("#eiaLabOfferId").val();
    var eiaLabOfferPlanId = parent.$("#eiaLabOfferPlanId").val();
    $("#eiaLabOfferId").val(eiaLabOfferId);
    $.ajax({
        url: "../eiaLabOfferPlan/getEiaLabOfferPlanDataMap?eiaLabOfferId="+eiaLabOfferId+"&eiaLabOfferPlanId="+eiaLabOfferPlanId,
        type: "post",
        cache: false,
        async: false,
        success: function (result) {
            if (result.data.eiaLabOfferPlanId) {
                $('#editTitle').tmpl().appendTo('#title');
                $("#labTestBaseId").val(result.data.labTestBaseId);
                $("#labTestParamId").val(result.data.labTestParamId);
                $("#labTestSchemeId").val(result.data.labTestSchemeId);
                $("#schemeMoney").val(result.data.schemeMoney);
                $("#pointNum").val(result.data.pointNum);
                $("#freqNum").val(result.data.freqNum);
                $("#dayNum").val(result.data.dayNum);
                $("#referenceLimitStandard").val(result.data.referenceLimitStandard);
                $("#referenceLimit").val(result.data.referenceLimit);
                $("#labOfferId").val(result.data.labOfferId);
                $("#eiaLabOfferPlanId").val(result.data.eiaLabOfferPlanId);
                $("#supplierName").val(result.data.supplierName);
                $("#supplierId").val(result.data.supplierId);
                $("#displayOrder").val(result.data.displayOrder);
                $("#memo").val(result.data.memo);
                $("#maxSchemeMoney").val(result.data.maxSchemeMoney);
            } else {
                $('#addTitle').tmpl().appendTo('#title');
            }
            $("#discount").val(result.data.discount);
            if (result.data.ifYxTest) {
                $("#ifShowScheme").removeClass("display-none");
                $("#ifShowMoney").removeClass("display-none");
                $("#ifShowDiscount").removeClass("display-none");
            } else {
                $("#schemeName").removeAttr("lay-verify", "required");
                $("#schemeMoney").removeAttr("lay-verify", "required");
                $("#discount").removeAttr("lay-verify", "required");
            }
            /**
             * 下拉联动
             */
            $.ajax({
                url: "../eiaLabOfferPlan/getBaseList",
                type: "get",
                cache: false,
                async: false,
                success: function (data) {
                    var baseName = $('#baseName');
                    baseName.append('<option value="">请选择检测基质</option>');
                    for (var i = 0; i < data.data.length; i++) {
                        if (result.data.eiaLabOfferPlanId) {
                            if (result.data.labTestBaseId == data.data[i].val) {
                                baseName.append('<option value="' + data.data[i].val + '" selected>' + data.data[i].baseName + '</option>')
                                $.ajax({
                                    // url: "/eiaLab/static/js/layuiFrame/data/paramNameCn.json",
                                    url: "../eiaLabOfferPlan/getParamList?labTestBaseId="+data.data[i].val,
                                    type: "get",
                                    cache: false,
                                    async: false,
                                    success: function (data) {
                                        var paramNameCn = $('#paramNameCn');
                                        for (var i = 0; i < data.data.length; i++) {
                                            if (result.data.labTestParamId == data.data[i].val) {
                                                paramNameCn.append('<option value="' + data.data[i].val + '" selected>' + data.data[i].paramNameCn + '</option>')

                                            }else{
                                                paramNameCn.append('<option value="' + data.data[i].val + '">' + data.data[i].paramNameCn + '</option>')
                                            }
                                        }
                                        form.render('select', 'paramNameCn');
                                    }
                                });
                            } else {
                                baseName.append('<option value="' + data.data[i].val + '">' + data.data[i].baseName + '</option>')
                            }
                        } else {
                            baseName.append('<option value="' + data.data[i].val + '">' + data.data[i].baseName + '</option>')
                        }
                    }
                    form.render('select', 'baseName');
                }
            });
            form.on('select(baseName)', function(data){
                $("#paramNameCn").html("");
                $("#maxSchemeMoney").val("");
                form.render('select');
                var labTestBaseId = data.value;
                if (!labTestBaseId) {
                    return
                }
                $("#labTestBaseId").val(labTestBaseId);
                $.ajax({
                    url: "../eiaLabOfferPlan/getParamList?labTestBaseId="+labTestBaseId,
                    type: "get",
                    cache: false,
                    async: false,
                    success: function (paramData) {
                        var paramNameCn = $('#paramNameCn');
                        paramNameCn.append('<option value="">请选择检测项目</option>');
                        for (var i = 0; i < paramData.data.length; i++) {
                            paramNameCn.append('<option value="' + paramData.data[i].val + '">' + paramData.data[i].paramNameCn + '</option>')
                        }
                        form.render('select', 'paramNameCn');
                    }
                });
            });
            form.on('select(paramNameCn)', function(data){
                $("#schemeName").html("");
                $("#schemeMoney").val("");
                form.render('select', 'schemeName');
                var labTestBaseId = $("#labTestBaseId").val();
                var labTestParamId = data.value;
                $("#labTestParamId").val(labTestParamId);
                $.ajax({
                    url: "../eiaLabOfferPlan/checkIfSub?labTestParamId=" + labTestParamId,
                    type: "get",
                    cache: false,
                    async: false,
                    success: function (result) {
                        var ifSub = result.data;
                        if (ifSub == true) {
                            $("#ifSub").prop("checked", true);
                            document.getElementById("ifShowSupplier").style.display="";
                            $("#supplierName").attr("lay-verify", "required");
                        } else {
                            $("#ifSub").prop('checked',false);
                            document.getElementById("ifShowSupplier").style.display="none";
                            $("#supplierName").removeAttr("lay-verify", "required");
                        }
                        form.render("checkbox");
                    }
                });
                $.ajax({
                    url: "../eiaLabOfferPlan/getMaxSchemeMoney?labTestParamId="+labTestParamId+"&labTestBaseId="+labTestBaseId,
                    type: "get",
                    cache: false,
                    async: false,
                    success: function (result) {
                        $('#maxSchemeMoney').val(result.data);
                    }
                });
            });
        }
    });
    //提交表单
    form.on('submit(save)', function (data) {
        var loadingIndex = layer.load(1, {time: 10*1000,shade: 0.1});
        var actionUrl = "../eiaLabOfferPlan/eiaLabOfferPlanSave?sampleType="+"采样";
        $.post(actionUrl, data.field, function (data) {
            if (data.code == 0) {
                layer.msg('保存成功', {icon: 1, time: 1000,shade: 0.1}, function () {
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.parent.layui.table.reload("eiaLabOfferList");
                    parent.layui.table.reload("eiaLabOfferPlanList");
                    parent.layer.close(index);
                });
            } else {
                layer.msg(data.msg, {icon: 2, time: 1000,shade: 0.1});
            }
            layer.close(loadingIndex);
        });
        return false;
    });
    /**
     * 重置表单
     */
    $("#reset").click(function () {
        $("#paramNameCn").val("");
        $("#labTestParamId").val("");
        $("#baseName").val("");
        $("#labTestBaseId").val("");
        $("#maxSchemeMoney").val("");
        $("#pointNum").val("");
        $("#dayNum").val("");
        $("#freqNum").val("");
        $("#discount").val("");
        $("#referenceLimitStandard").val("");
        $("#referenceLimit").val("");
        $("#displayOrder").val("");
        $("#memo").val("");
        form.render();
    });
});