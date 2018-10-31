layui.use(['jquery', 'form', 'element'], function(){
    var $ = layui.jquery,
        form = layui.form,
        element = layui.element;

    var $speInpContainers = $('.dynInputs .layui-col-xs6');
    var $dynMoneyContainers = $('.dynMoneyInputs .layui-col-xs6');

    var eiaProjectLogId = parent.$('#eiaProjectLogId').val();
    $('#eiaProjectLogId').val(eiaProjectLogId);

    //渲染固定数据
    $.ajax({
        url: "/eia/eiaProjectLog/getEiaProjectLogDataMap?eiaProjectLogId=" + eiaProjectLogId,
        type:"POST",
        data:{},
        dataType: "json",
        async: true,
        success: function (result) {
            if (result.data.projectName != result.data.projectNameEd) {
                $("#projectName").text(result.data.projectNameEd).css("color", "red");
                $("#projectNameShow").removeClass("display-none");
                $("#projectNameShow").on("mouseenter",function(){
                    layer.tips("修改前内容: " +(result.data.projectName== null?"":result.data.projectName ) + "</br>" + "修改人: " + result.data.logInputUser + "</br>" + "修改日期: " + result.logInputDate, '#projectNameShow', {
                        area: ['auto', 'auto'],
                        tips: [1, '#30b5ff'],
                        time: 2000
                    });
                });
            } else {
                $("#projectName").text(result.data.projectNameEd);
            }
            if (result.data.buildArea != result.data.buildAreaEd) {
                $("#buildArea").text(result.data.buildAreaEd).css("color", "red");
                $("#buildAreaShow").removeClass("display-none");
                $("#buildAreaShow").on("mouseenter",function(){
                    layer.tips("修改前内容: " +(result.data.buildArea== null?"":result.data.buildArea ) + "</br>" + "修改人: " + result.data.logInputUser + "</br>" + "修改日期: " + result.logInputDate, '#buildAreaShow', {
                        area: ['auto', 'auto'],
                        tips: [1, '#30b5ff'],
                        time: 2000
                    });
                });
            } else {
                $("#buildArea").text(result.data.buildAreaEd);
            }
            if (result.data.competentDept != result.data.competentDeptEd) {
                $("#competentDept").text(result.data.competentDeptEd).css("color", "red");
                $("#competentDeptShow").removeClass("display-none");
                $("#competentDeptShow").on("mouseenter",function(){
                    layer.tips("修改前内容: " +(result.data.competentDept== null?"":result.data.competentDept ) + "</br>" + "修改人: " + result.data.logInputUser + "</br>" + "修改日期: " + result.logInputDate, '#competentDeptShow', {
                        area: ['auto', 'auto'],
                        tips: [1, '#30b5ff'],
                        time: 2000
                    });
                });
            } else {
                $("#competentDept").text(result.data.competentDeptEd);
            }
            if (result.data.fileTypeChild.trim() != result.data.fileTypeChildEd.trim()) {
                $("#fileTypeChild").text(result.data.fileTypeChildEd).css("color", "red");
                $("#fileTypehidden").val(result.data.fileTypeChildCodeEd)
                $("#fileTypeChildShow").removeClass("display-none");
                $("#fileTypeChildShow").on("mouseenter",function(){
                    layer.tips("修改前内容: " +(result.data.fileTypeChild== null?"":result.data.fileTypeChild ) + "</br>" + "修改人: " + result.data.logInputUser + "</br>" + "修改日期: " + result.logInputDate, '#fileTypeChildShow', {
                        area: ['auto', 'auto'],
                        tips: [1, '#30b5ff'],
                        time: 2000
                    });
                });
            } else {
                $("#fileTypeChild").text(result.data.fileTypeChildEd);
                $("#fileTypehidden").val(result.data.fileTypeChildCodeEd)
            }
            if (result.data.projectMoney != result.data.projectMoneyEd) {
                $("#projectMoney").text(dotFour(result.data.projectMoneyEd)).css("color", "red");
                $("#projectMoneyShow").removeClass("display-none");
                $("#projectMoneyShow").on("mouseenter",function(){
                    layer.tips("修改前内容: " + dotFour((result.data.projectMoney== null?"":result.data.projectMoney)) + "</span><span>&nbsp;&nbsp;(万元)</span></br>" + "修改人: " + result.data.logInputUser + "</br>" + "修改日期: " + result.logInputDate, '#projectMoneyShow', {
                        area: ['auto', 'auto'],
                        tips: [1, '#30b5ff'],
                        time: 2000
                    });
                });
            } else {
                $("#projectMoney").text(dotFour(result.data.projectMoneyEd));
            }
            if (result.data.projectComfee != result.data.projectComfeeEd) {
                $("#projectComfee").text(dotFour(result.data.projectComfeeEd)).css("color", "red");
                $("#projectComfeeShow").removeClass("display-none");
                $("#projectComfeeShow").on("mouseenter",function(){
                    layer.tips("修改前内容: " + dotFour((result.data.projectComfee== null?"":result.data.projectComfee)) + "</span><span>&nbsp;&nbsp;(万元)</span></br>" + "修改人: " + result.data.logInputUser + "</br>" + "修改日期: " + result.logInputDate, '#projectComfeeShow', {
                        area: ['auto', 'auto'],
                        tips: [1, '#30b5ff'],
                        time: 2000
                    });
                });
            } else {
                $("#projectComfee").text(dotFour(result.data.projectComfeeEd));
            }
            if (result.data.dutyUser != result.data.dutyUserEd) {
                $("#dutyUser").text(result.data.dutyUserEd).css("color", "red");
                $("#dutyUserShow").removeClass("display-none");
                $("#dutyUserShow").on("mouseenter",function(){
                    layer.tips("修改前内容: " +(result.data.dutyUser== null?"":result.data.dutyUser ) + "</br>" + "修改人: " + result.data.logInputUser + "</br>" + "修改日期: " + result.logInputDate, '#dutyUserShow', {
                        area: ['auto', 'auto'],
                        tips: [1, '#30b5ff'],
                        time: 2000
                    });
                });
            } else {
                $("#dutyUser").text(result.data.dutyUserEd);
            }
            if (result.data.groundwaterFee != result.data.groundwaterFeeEd) {
                $("#groundwaterFee").text(dotFour(result.data.groundwaterFeeEd)).css("color", "red");
                $("#groundwaterFeeShow").removeClass("display-none");
                $("#groundwaterFeeShow").on("mouseenter",function(){
                    layer.tips("修改前内容: " + dotFour((result.data.groundwaterFee== null?"":result.data.groundwaterFee)) + "</br>" + "修改人: " + result.data.logInputUser + "</br>" + "修改日期: " + result.logInputDate, '#groundwaterFeeShow', {
                        area: ['auto', 'auto'],
                        tips: [1, '#30b5ff'],
                        time: 2000
                    });
                });
            } else {
                $("#groundwaterFee").text(dotFour(result.data.groundwaterFeeEd));
            }
            if (result.data.otherFee != result.data.otherFeeEd) {
                $("#otherFee").text(dotFour(result.data.otherFeeEd)).css("color", "red");
                $("#otherFeeShow").removeClass("display-none");
                $("#otherFeeShow").on("mouseenter",function(){
                    layer.tips("修改前内容: " + dotFour((result.data.otherFee== null?"":result.data.otherFee)) + "</span><span>&nbsp;&nbsp;(万元)</span></br>" + "修改人: " + result.data.logInputUser + "</br>" + "修改日期: " + result.logInputDate, '#otherFeeShow', {
                        area: ['auto', 'auto'],
                        tips: [1, '#30b5ff'],
                        time: 2000
                    });
                });
            } else {
                $("#otherFee").text(dotFour(result.data.otherFeeEd));
            }
            if (result.data.environmentalFee != result.data.environmentalFeeEd) {
                $("#environmentalFee").text(dotFour(result.data.environmentalFeeEd)).css("color", "red");
                $("#environmentalFeeShow").removeClass("display-none");
                $("#environmentalFeeShow").on("mouseenter",function(){
                    layer.tips("修改前内容: " + dotFour((result.data.environmentalFee== null?"":result.data.environmentalFee)) + "</span><span>&nbsp;&nbsp;(万元)</span></br>" + "修改人: " + result.data.logInputUser + "</br>" + "修改日期: " + result.logInputDate, '#environmentalFeeShow', {
                        area: ['auto', 'auto'],
                        tips: [1, '#30b5ff'],
                        time: 2000
                    });
                });
            } else {
                $("#environmentalFee").text(dotFour(result.data.environmentalFeeEd));
            }
            if (result.data.expertFee != result.data.expertFeeEd) {
                $("#expertFee").text(dotFour(result.data.expertFeeEd)).css("color", "red");
                $("#expertFeeShow").removeClass("display-none");
                $("#expertFeeShow").on("mouseenter",function(){
                    layer.tips("修改前内容: " + dotFour((result.data.expertFee== null?"":result.data.expertFee)) + "</span><span>&nbsp;&nbsp;(万元)</span></br>" + "修改人: " + result.data.logInputUser + "</br>" + "修改日期: " + result.logInputDate, '#expertFeeShow', {
                        area: ['auto', 'auto'],
                        tips: [1, '#30b5ff'],
                        time: 2000
                    });
                });
            } else {
                $("#expertFee").text(dotFour(result.data.expertFeeEd));
            }
            if (result.data.specialFee != result.data.specialFeeEd) {
                $("#specialFee").text(dotFour(result.data.specialFeeEd)).css("color", "red");
                $("#specialFeeShow").removeClass("display-none");
                $("#specialFeeShow").on("mouseenter",function(){
                    layer.tips("修改前内容: " + dotFour((result.data.specialFee== null?"":result.data.specialFee)) + "</span><span>&nbsp;&nbsp;(万元)</span></br>" + "修改人: " + result.data.logInputUser + "</br>" + "修改日期: " + result.logInputDate, '#specialFeeShow', {
                        area: ['auto', 'auto'],
                        tips: [1, '#30b5ff'],
                        time: 2000
                    });
                });
            } else {
                $("#specialFee").text(dotFour(result.data.specialFeeEd));
            }
            if (result.data.detectFee != result.data.detectFeeEd) {
                $("#detectFee").text(dotFour(result.data.detectFeeEd)).css("color", "red");
                $("#detectFeeShow").removeClass("display-none");
                $("#detectFeeShow").on("mouseenter",function(){
                    layer.tips("修改前内容: " + dotFour((result.data.detectFee== null?"":result.data.detectFee)) + "</span><span>&nbsp;&nbsp;(万元)</span></br>" + "修改人: " + result.data.logInputUser + "</br>" + "修改日期: " + result.logInputDate, '#detectFeeShow', {
                        area: ['auto', 'auto'],
                        tips: [1, '#30b5ff'],
                        time: 2000
                    });
                });
            } else {
                $("#detectFee").text(dotFour(result.data.detectFeeEd));
            }
        }
    });

    //渲染可变数据
    $.ajax({
        url: "/eia/eiaProjectLog/getEnvProLogDataMap?eiaProjectLogId=" + eiaProjectLogId,
        type:"POST",
        data:{},
        dataType: "json",
        async: true,
        success: function (data) {
            $speInpContainers.empty();
            var con_index = 0;
            for(var name in data.data){
                inputs[name].show.call(this,data.data[name], $speInpContainers.eq(con_index++%2));
            }


            //产品功能处理
            $.ajax({
                url: "/eia/eiaProjectLog/getEneOrEnvOrGreenLogDataMap?eiaProjectLogId=" + eiaProjectLogId,
                type: "POST",
                data: {},
                dataType: "json",
                async: true,
                success: function (result) {
                    if (result.data.productFunction != result.data.productFunctionEd) {
                        $("#productionEngineerEd", $('#productFunctionEd')).css("color", "red");
                        document.getElementById("productFunctionEd").setAttribute("style","color:red;");
                        $("#productFunctionShow", $('#productFunctionEd')).css("color", "black");
                        $("#productFunctionShow", $('#productFunctionEd')).removeClass("display-none");
                        $("#productFunctionShow", $('#productFunctionEd')).on("mouseenter", function () {
                            layer.tips("修改前内容: " + (result.data.productFunction == null ? "" : result.data.productFunction ) + "</br>" + "修改人: " + result.data.logInputUser + "</br>" + "修改日期: " + result.logInputDate, '#productFunctionShow', {
                                area: ['auto', 'auto'],
                                tips: [1, '#30b5ff'],
                                time: 2000
                            });
                        });
                    }
                }
            });

            //根据下拉树选中的code更改#productFunction的label文字
            var curParentCode = $('#fileTypehidden').val();
            if(curParentCode.indexOf('EPC_GH')!==-1){
                console.log($('#productFunctionEd'));
                $('#productFunctionEd').closest('.layui-form-item').find('.label-txt').text('功能定位');
            }
            form.render('select');
        }
    });

    //渲染金额子项数据
    $.ajax({
        url:"../eiaProjectLog/getProjectLogMoneyShow",
        type:"POST",
        data:{"eiaProjectLogId":eiaProjectLogId},
        dataType: "json",
        async: true,
        success: function (res) {
            var data = res.data;
            console.log("data");
            console.log(data);
            $dynMoneyContainers.empty();
            var con_index = 0;
            for(var name in data){
                moneyFillInputs[name].show.call(this,data[name], $dynMoneyContainers.eq(con_index++%2));
            }
        }
    });
});