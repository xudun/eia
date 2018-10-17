var eiaProjectLogId = parent.$('#eiaProjectLogId').val();
//设置select指定项选中
var selectOptionByVal = function ($select, val) {
    var opt_arr = $('option', $($select));
    for (var i = 0; i < opt_arr.length; i++) {
        if ($(opt_arr[i]).val() == val) {
            $(opt_arr[i]).prop("selected", true);
            break;
        }
    }
};
//金额输入框强制小数点后4位
var dotFour = function (value) {
    var valF = value ? parseFloat(value) : parseFloat(0),
        valR = Math.round(valF * 10000) / 10000,
        valS = valR.toString(),
        rs = valS.indexOf('.');
    if (rs < 0) {
        rs = valS.length;
        valS += '.';
    }
    while (valS.length <= rs + 4) {
        valS += '0';
    }
    return valS;
};
//年限输入框强制整数
var integerForce = function (value) {
    var val = value;
    var re = /^\d*$/;
    if(!re.test(val)){
        val = 0;
    }
    return val;
};

var inputs = {
    projectCodeEd: {  //项目代码
        create: function (val, container) {
            var this_val = val ? val : "";
            var str = "<div class='layui-form-item'>" +
                "<label class='layui-form-label'><span class='col-f00'>* </span>项目代码</label>" +
                "<div class='layui-input-block'>" +
                "<input type='text' id='projectCodeEd' name='projectCodeEd' class='layui-input' lay-verify='required' value='" + this_val + "'>" +
                "</div>" +
                "</div>";
            var $str = $(str).clone();
            $(container).append($str);
        },
        show: function (val, container) {
            var this_val = val ? val : "";
            $.ajax({
                url: "/eia/eiaProjectLog/getEneOrEnvOrGreenLogDataMap?eiaProjectLogId=" + eiaProjectLogId,
                type: "POST",
                data: {},
                dataType: "json",
                async: true,
                success: function (result) {
                    var str = "<div class='layui-form-item'>" +
                        "<label class='layui-form-label'><span class='col-f00'></span>项目代码</label>" +
                        "<div class='layui-input-block check-block' id='projectCodeEd'>" + this_val + " &nbsp;<i class='larry-icon font20 cursor-pointer display-none' id='projectCodeShow'>&#xe740;</i></div>" +
                        "</div>";
                    var $str = $(str).clone();
                    if (result.data.projectCode != result.data.projectCodeEd) {
                        $("#projectCodeEd", $str).css("color", "red");
                        $("#projectCodeShow", $str).css("color", "black");
                        $("#projectCodeShow", $str).removeClass("display-none");
                        $("#projectCodeShow", $str).on("mouseenter", function () {
                            layer.tips("修改前内容: " + (result.data.projectCode == null ? "" : result.data.projectCode ) + "</br>" + "修改人: " + result.data.logInputUser + "</br>" + "修改日期: " + result.logInputDate, '#projectCodeShow', {
                                area: ['auto', 'auto'],
                                tips: [1, '#30b5ff'],
                                time: 2000
                            });
                        });
                    }
                    $(container).append($str);
                }
            });
        }
    },
    industryTypeEd: {  //国民经济行业类型及代码
        create: function (val, container) {
            var this_val = val ? val : "",
                val_name = val.name ? val.name : "",
                val_id = val.id ? val.id : "";
            var str = "<div class='layui-form-item'>" +
                "<label class='layui-form-label'><span class='col-f00'>* </span>国民经济行业类型及代码</label>" +
                "<div class='layui-input-block'>" +
                "<input type='text' id='industryTypeDropEd' name='industryTypeDropEd' class='layui-input' lay-verify='required' autocomplete='off' value='" + val_name + "'>" +
                "<input type='hidden' id='industryTypeEd' name='industryTypeEd' lay-verify='required' value='" + JSON.stringify(this_val) + "'>" +
                "</div>" +
                "</div>";
            var $str = $(str).clone();
            $(container).append($str);
            //初始化下拉树 国民经济行业类型及代码
            $("#industryTypeDropEd").dropDownForZ({
                url: '/eia/eiaProject/getTreeByDomain?domain=GNS_TYPE_CODE',
                width: '99%',
                height: '350px',
                disableParent: true,
                ifSearch:true,
                selecedSuccess: function (data) {  //选中回调
                    if (!data.isParent) {
                        /*    var temp = {
                         id: data.id,
                         name: data.name
                         };*/
                        var str = data.attributes.mark + " " + data.name;
                        // $("#industryType").val(JSON.stringify(temp));
                        $("#industryTypeEd").val(str);
                        $("#industryTypeDropEd").val(str);
                    }
                }
            });
            $('#industryTypeDropCodeEd').val(val_id);
        },
        show: function (val, container) {
            var this_val = val ? val : "";
            $.ajax({
                url: "/eia/eiaProjectLog/getEneOrEnvOrGreenLogDataMap?eiaProjectLogId=" + eiaProjectLogId,
                type: "POST",
                data: {},
                dataType: "json",
                async: true,
                success: function (result) {
                    var str = "<div class='layui-form-item'>" +
                        "<label class='layui-form-label'><span class='col-f00'></span>国民经济行业类型及代码</label>" +
                        "<div class='layui-input-block check-block' id='industryTypeEd'>" + this_val + " &nbsp;<i class='larry-icon font20 cursor-pointer display-none' id='industryTypeShow'>&#xe740;</i></div>" +
                        "</div>";
                    var $str = $(str).clone();
                    if (result.data.industryType != result.data.industryTypeEd) {
                        $("#industryTypeEd", $str).css("color", "red");
                        $("#industryTypeShow", $str).css("color", "black");
                        $("#industryTypeShow", $str).removeClass("display-none");
                        $("#industryTypeShow", $str).on("mouseenter", function () {
                            layer.tips("修改前内容: " + (result.data.industryType == null ? "" : result.data.industryType ) + "</br>" + "修改人: " + result.data.logInputUser + "</br>" + "修改日期: " + result.logInputDate, '#industryTypeShow', {
                                area: ['auto', 'auto'],
                                tips: [1, '#30b5ff'],
                                time: 2000
                            });
                        });
                    }
                    $(container).append($str);
                }
            });
        }
    },
    environmentaTypeEd: {  //环境影响评价行业类别
        create: function (val, container) {
            var this_val = val ? val : "",
                val_name = val.name ? val.name : "",
                val_id = val.id ? val.id : "";
            var str = "<div class='layui-form-item'>" +
                "<label class='layui-form-label'><span class='col-f00'>* </span>环境影响评价行业类别</label>" +
                "<div class='layui-input-block'>" +
                "<input type='text' id='environmentaTypeDropEd' name='environmentaTypeDropEd' class='layui-input' lay-verify='required' autocomplete='off' value='" + val_name + "'>" +
                "<input type='hidden' id='environmentaTypeEd' name='environmentaTypeEd' lay-verify='required' value='" + JSON.stringify(this_val) + "'>" +
                "</div>" +
                "</div>";
            var $str = $(str).clone();
            $(container).append($str);
            //初始化下拉树 环境影响评价行业类别
            $("#environmentaTypeDropEd").dropDownForZ({
                url: '/eia/eiaProject/getTreeByDomain?domain=INS_TYPE_CODE',
                width: '99%',
                height: '350px',
                disableParent: true,
                ifSearch:true,
                selecedSuccess: function (data) {  //选中回调
                    if (!data.isParent) {
                        /*         var temp = {
                         id: data.id,
                         name: data.name
                         };*/
                        var str = data.attributes.mark + " " + data.name;
                        // $("#industryType").val(JSON.stringify(temp));
                        $("#environmentaTypeEd").val(str);
                        $("#environmentaTypeDropEd").val(str);
                        /*    $("#environmentaType").val(JSON.stringify(temp));*/
                    }
                }
            });
            $('#environmentaTypeDropCodeEd').val(val_id);
        },
        show: function (val, container) {
            var this_val = val ? val : "";
            $.ajax({
                url: "/eia/eiaProjectLog/getEneOrEnvOrGreenLogDataMap?eiaProjectLogId=" + eiaProjectLogId,
                type: "POST",
                data: {},
                dataType: "json",
                async: true,
                success: function (result) {
                    var str = "<div class='layui-form-item'>" +
                        "<label class='layui-form-label'><span class='col-f00'></span>环境影响评价行业类别</label>" +
                        "<div class='layui-input-block check-block' id='environmentaTypeEd'>" + this_val + " &nbsp;<i class='larry-icon font20 cursor-pointer display-none' id='environmentaTypeShow'>&#xe740;</i></div>" +
                        "</div>";
                    var $str = $(str).clone();
                    if (result.data.environmentaType != result.data.environmentaTypeEd) {
                        $("#environmentaTypeEd", $str).css("color", "red");
                        $("#environmentaTypeShow", $str).css("color", "black");
                        $("#environmentaTypeShow", $str).removeClass("display-none");
                        $("#environmentaTypeShow", $str).on("mouseenter", function () {
                            layer.tips("修改前内容: " + (result.data.environmentaType == null ? "" : result.data.environmentaType ) + "</br>" + "修改人: " + result.data.logInputUser + "</br>" + "修改日期: " + result.logInputDate, '#environmentaTypeShow', {
                                area: ['auto', 'auto'],
                                tips: [1, '#30b5ff'],
                                time: 2000
                            });
                        });
                    }
                    $(container).append($str);
                }
            });
        }
    },
    productionEngineerEd: {  //生产工艺
        create: function (val, container) {
            var this_val = val ? val : "";
            var str = "<div class='layui-form-item'>" +
                "<label class='layui-form-label'><span class='col-f00'>* </span>生产工艺</label>" +
                "<div class='layui-input-block'>" +
                "<input type='text' id='productionEngineerEd' name='productionEngineerEd' class='layui-input' lay-verify='required' value='" + this_val + "' placeholder='（1-5个关键词逗号间隔）'>" +
                "</div>" +
                "</div>";
            var $str = $(str).clone();
            $(container).append($str);
        },
        show: function (val, container) {
            var this_val = val ? val : "";
            $.ajax({
                url: "/eia/eiaProjectLog/getEneOrEnvOrGreenLogDataMap?eiaProjectLogId=" + eiaProjectLogId,
                type: "POST",
                data: {},
                dataType: "json",
                async: true,
                success: function (result) {
                    var str = "<div class='layui-form-item'>" +
                        "<label class='layui-form-label'><span class='col-f00'></span>生产工艺</label>" +
                        "<div class='layui-input-block check-block' id='productionEngineerEd'>" + this_val + " &nbsp;<i class='larry-icon font20 cursor-pointer display-none' id='productionEngineerShow'>&#xe740;</i></div>" +
                        "</div>";
                    var $str = $(str).clone();
                    if (result.data.productionEngineer != result.data.productionEngineerEd) {
                        $("#productionEngineerEd", $str).css("color", "red");
                        $("#productionEngineerShow", $str).css("color", "black");
                        $("#productionEngineerShow", $str).removeClass("display-none");
                        $("#productionEngineerShow", $str).on("mouseenter", function () {
                            layer.tips("修改前内容: " + (result.data.productionEngineer == null ? "" : result.data.productionEngineer ) + "</br>" + "修改人: " + result.data.logInputUser + "</br>" + "修改日期: " + result.logInputDate, '#productionEngineerShow', {
                                area: ['auto', 'auto'],
                                tips: [1, '#30b5ff'],
                                time: 2000
                            });
                        });
                    }
                    $(container).append($str);
                }
            });

        }
    },
    productFunctionEd: {  //产品功能
        create: function (val, container) {
            var this_val = val ? val : "";
            var str = "<div class='layui-form-item'>" +
                "<label class='layui-form-label'><span class='col-f00'>* </span>产品功能</label>" +
                "<div class='layui-input-block'>" +
                "<input type='text' id='productFunctionEd' name='productFunctionEd' class='layui-input' lay-verify='required' value='" + this_val + "' placeholder='（1-5个关键词逗号间隔）'>" +
                "</div>" +
                "</div>";
            var $str = $(str).clone();
            $(container).append($str);
        },
        show: function (val, container) {
            var this_val = val ? val : "";
            $.ajax({
                url: "/eia/eiaProjectLog/getEneOrEnvOrGreenLogDataMap?eiaProjectLogId=" + eiaProjectLogId,
                type: "POST",
                data: {},
                dataType: "json",
                async: true,
                success: function (result) {
                    var str = "<div class='layui-form-item'>" +
                        "<label class='layui-form-label'><span class='col-f00'></span>产品功能</label>" +
                        "<div class='layui-input-block check-block' id='productFunctionEd'>" + this_val + " &nbsp;<i class='larry-icon font20 cursor-pointer display-none' id='productFunctionShow'>&#xe740;</i></div>" +
                        "</div>";
                    var $str = $(str).clone();
                    if (result.data.productFunction != result.data.productFunctionEd) {
                        $("#productFunctionEd", $str).css("color", "red");
                        $("#productFunctionShow", $str).css("color", "black");
                        $("#productFunctionShow", $str).removeClass("display-none");
                        $("#productFunctionShow", $str).on("mouseenter", function () {
                            layer.tips("修改前内容: " + (result.data.productFunction == null ? "" : result.data.productFunction ) + "</br>" + "修改人: " + result.data.logInputUser + "</br>" + "修改日期: " + result.logInputDate, '#productFunctionShow', {
                                area: ['auto', 'auto'],
                                tips: [1, '#30b5ff'],
                                time: 2000
                            });
                        });
                    }
                    $(container).append($str);
                }
            });
        }
    },
    productFunction: {  //产品功能
        create: function (val, container) {
            var this_val = val ? val : "";
            var str = "<div class='layui-form-item'>" +
                "<label class='layui-form-label'><span class='col-f00'>* </span>产品功能</label>" +
                "<div class='layui-input-block'>" +
                "<input type='text' id='productFunction' name='productFunction' class='layui-input' lay-verify='required' value='" + this_val + "' placeholder='（1-5个关键词逗号间隔）'>" +
                "<i class='larry-icon font20 cursor-pointer display-none' id='productFunctionShow'>&#xe740;</i>" +
                "</div>" +
                "</div>";
            var $str = $(str).clone();
            $(container).append($str);
        },
        show: function (val, container) {
            var this_val = val ? val : "";
            var str = "<div class='layui-form-item'>" +
                "<label class='layui-form-label'><span class='col-f00'></span>产品功能</label>" +
                "<div class='layui-input-block check-block' id='productFunction'>" + this_val + "</div>" +
                "</div>";
            var $str = $(str).clone();
            $(container).append($str);
        }
    },
    parkPlanningEd: {  //园区规划环评开展情况
        create: function (val, container) {
            var this_val = val ? val : "";
            var str = "<div class='layui-form-item'>" +
                "<label class='layui-form-label'><span class='col-f00'></span>园区规划环评开展情况</label>" +
                "<div class='layui-input-block'>" +
                "<select name='parkPlanningEd' id='parkPlanningEd' lay-search>" +
                "</select>" +
                "</div>" +
                "</div>";
            var $str = $(str).clone();
            selectOptionByVal($str, this_val);
            $(container).append($str);
        },
        show: function (val, container) {
            var this_val = val ? val : "";
            $.ajax({
                url: "/eia/eiaProjectLog/getEneOrEnvOrGreenLogDataMap?eiaProjectLogId=" + eiaProjectLogId,
                type: "POST",
                data: {},
                dataType: "json",
                async: true,
                success: function (result) {
                    var str = "<div class='layui-form-item'>" +
                        "<label class='layui-form-label'><span class='col-f00'></span>园区规划环评开展情况</label>" +
                        "<div class='layui-input-block check-block' id='parkPlanningEd'>" + this_val + " &nbsp;<i class='larry-icon font20 cursor-pointer display-none' id='parkPlanningShow'>&#xe740;</i></div>" +
                        "</div>";
                    var $str = $(str).clone();
                    if (result.data.parkPlanning != result.data.parkPlanningEd) {
                        $("#parkPlanningEd", $str).css("color", "red");
                        $("#parkPlanningShow", $str).css("color", "black");
                        $("#parkPlanningShow", $str).removeClass("display-none");
                        $("#parkPlanningShow", $str).on("mouseenter", function () {
                            layer.tips("修改前内容: " + (result.data.parkPlanning == null ? "" : result.data.parkPlanning ) + "</br>" + "修改人: " + result.data.logInputUser + "</br>" + "修改日期: " + result.logInputDate, '#parkPlanningShow', {
                                area: ['auto', 'auto'],
                                tips: [1, '#30b5ff'],
                                time: 2000
                            });
                        });
                    }
                    $(container).append($str);
                }
            });
        }
    },
    seaFileNameEd: {  //规划环评文件名
        create: function (val, container) {
            var this_val = val ? val : "";
            var str = "<div class='layui-form-item'>" +
                "<label class='layui-form-label'><span class='col-f00'></span>规划环评文件名</label>" +
                "<div class='layui-input-block'>" +
                "<input type='text' id='seaFileNameEd' name='seaFileNameEd' class='layui-input' value='" + this_val + "'>" +
                "</div>" +
                "</div>";
            var $str = $(str).clone();
            $(container).append($str);
        },
        show: function (val, container) {
            var this_val = val ? val : "";
            $.ajax({
                url: "/eia/eiaProjectLog/getEneOrEnvOrGreenLogDataMap?eiaProjectLogId=" + eiaProjectLogId,
                type: "POST",
                data: {},
                dataType: "json",
                async: true,
                success: function (result) {
                    var str = "<div class='layui-form-item'>" +
                        "<label class='layui-form-label'><span class='col-f00'></span>规划环评文件名</label>" +
                        "<div class='layui-input-block check-block' id='seaFileNameEd'>" + this_val + " &nbsp;<i class='larry-icon font20 cursor-pointer display-none' id='seaFileNameShow'>&#xe740;</i></div>" +
                        "</div>";
                    var $str = $(str).clone();
                    if (result.data.seaFileName != result.data.seaFileNameEd) {
                        $("#seaFileNameEd", $str).css("color", "red");
                        $("#seaFileNameShow", $str).css("color", "black");
                        $("#seaFileNameShow", $str).removeClass("display-none");
                        $("#seaFileNameShow", $str).on("mouseenter", function () {
                            layer.tips("修改前内容: " + (result.data.seaFileName == null ? "" : result.data.seaFileName ) + "</br>" + "修改人: " + result.data.logInputUser + "</br>" + "修改日期: " + result.logInputDate, '#seaFileNameShow', {
                                area: ['auto', 'auto'],
                                tips: [1, '#30b5ff'],
                                time: 2000
                            });
                        });
                    }
                    $(container).append($str);
                }
            });
        }
    },
    seaReviewOrgEd: {  //规划环评审查机关
        create: function (val, container) {
            var this_val = val ? val : "";
            var str = "<div class='layui-form-item'>" +
                "<label class='layui-form-label'><span class='col-f00'></span>规划环评审查机关</label>" +
                "<div class='layui-input-block'>" +
                "<input type='text' id='seaReviewOrgEd' name='seaReviewOrgEd' class='layui-input' value='" + this_val + "'>" +
                "</div>" +
                "</div>";
            var $str = $(str).clone();
            $(container).append($str);
        },
        show: function (val, container) {
            var this_val = val ? val : "";
            $.ajax({
                url: "/eia/eiaProjectLog/getEneOrEnvOrGreenLogDataMap?eiaProjectLogId=" + eiaProjectLogId,
                type: "POST",
                data: {},
                dataType: "json",
                async: true,
                success: function (result) {
                    var str = "<div class='layui-form-item'>" +
                        "<label class='layui-form-label'><span class='col-f00'></span>规划环评审查机关</label>" +
                        "<div class='layui-input-block check-block' id='seaReviewOrgEd'>" + this_val + " &nbsp;<i class='larry-icon font20 cursor-pointer display-none' id='seaReviewOrgShow'>&#xe740;</i></div>" +
                        "</div>";
                    var $str = $(str).clone();
                    if (result.data.seaReviewOrg != result.data.seaReviewOrgEd) {
                        $("#seaReviewOrgEd", $str).css("color", "red");
                        $("#seaReviewOrgShow", $str).css("color", "black");
                        $("#seaReviewOrgShow", $str).removeClass("display-none");
                        $("#seaReviewOrgShow", $str).on("mouseenter", function () {
                            layer.tips("修改前内容: " + (result.data.seaReviewOrg == null ? "" : result.data.seaReviewOrg ) + "</br>" + "修改人: " + result.data.logInputUser + "</br>" + "修改日期: " + result.logInputDate, '#seaReviewOrgShow', {
                                area: ['auto', 'auto'],
                                tips: [1, '#30b5ff'],
                                time: 2000
                            });
                        });
                    }
                    $(container).append($str);
                }
            });
        }
    },
    seaReviewNoEd: {  //规划环评审查意见文号
        create: function (val, container) {
            var this_val = val ? val : "";
            var str = "<div class='layui-form-item'>" +
                "<label class='layui-form-label'><span class='col-f00'></span>规划环评审查意见文号</label>" +
                "<div class='layui-input-block'>" +
                "<input type='text' id='seaReviewNoEd' name='seaReviewNoEd' class='layui-input' value='" + this_val + "'>" +
                "</div>" +
                "</div>";
            var $str = $(str).clone();
            $(container).append($str);
        },
        show: function (val, container) {
            var this_val = val ? val : "";
            $.ajax({
                url: "/eia/eiaProjectLog/getEneOrEnvOrGreenLogDataMap?eiaProjectLogId=" + eiaProjectLogId,
                type: "POST",
                data: {},
                dataType: "json",
                async: true,
                success: function (result) {
                    var str = "<div class='layui-form-item'>" +
                        "<label class='layui-form-label'><span class='col-f00'></span>规划环评审查意见文号</label>" +
                        "<div class='layui-input-block check-block' id='seaReviewNoEd'>" + this_val + " &nbsp;<i class='larry-icon font20 cursor-pointer display-none' id='seaReviewNoShow'>&#xe740;</i></div>" +
                        "</div>";
                    var $str = $(str).clone();
                    if (result.data.seaReviewNo != result.data.seaReviewNoEd) {
                        $("#seaReviewNoEd", $str).css("color", "red");
                        $("#seaReviewNoShow", $str).css("color", "black");
                        $("#seaReviewNoShow", $str).removeClass("display-none");
                        $("#seaReviewNoShow", $str).on("mouseenter", function () {
                            layer.tips("修改前内容: " + (result.data.seaReviewNo == null ? "" : result.data.seaReviewNo ) + "</br>" + "修改人: " + result.data.logInputUser + "</br>" + "修改日期: " + result.logInputDate, '#seaReviewNoShow', {
                                area: ['auto', 'auto'],
                                tips: [1, '#30b5ff'],
                                time: 2000
                            });
                        });
                    }
                    $(container).append($str);
                }
            });
        }
    },
    projectInvestMoneyEd: {  //总投资
        create: function (val, container) {
            var this_val = val ? val : 0;
            var str = "<div class='layui-form-item has-unit'>" +
                "<label class='layui-form-label'><span class='col-f00'></span>总投资</label>" +
                "<div class='layui-input-block'>" +
                "<input type='number' id='projectInvestMoneyEd' step='0.0001' name='projectInvestMoneyEd' class='layui-input dotFourInput' value='" + dotFour(this_val) + "'>" +
                "</div>" +
                "<div class='tag-right'><span class='tag-unit'>万元</span></div>" +
                "</div>";
            var $str = $(str).clone();
            $('.dotFourInput', $str).blur(function () {
                var val = $(this).val();
                $(this).val(dotFour(val));
            });
            $(container).append($str);
        },
        show: function (val, container) {
            var this_val = val ? val : 0;
            $.ajax({
                url: "/eia/eiaProjectLog/getEneOrEnvOrGreenLogDataMap?eiaProjectLogId=" + eiaProjectLogId,
                type: "POST",
                data: {},
                dataType: "json",
                async: true,
                success: function (result) {
                    var str = "<div class='layui-form-item'>" +
                        "<label class='layui-form-label'><span class='col-f00'></span>总投资</label>" +
                        "<div class='layui-input-block check-block' id='projectInvestMoneyEd'><span class='cost-num'>" + dotFour(this_val) + "</span><span>&nbsp;&nbsp;(万元)</span>&nbsp;<i class='larry-icon font20 cursor-pointer display-none' id='projectInvestMoneyShow'>&#xe740;</i></div>" +
                        "</div>";
                    var $str = $(str).clone();
                    if (result.data.projectInvestMoney == null) {
                        result.data.projectInvestMoney = 0.0000;
                    }
                    if (result.data.projectInvestMoney != result.data.projectInvestMoneyEd) {
                        $("#projectInvestMoneyEd", $str).css("color", "red");
                        $("#projectInvestMoneyShow", $str).css("color", "black");
                        $("#projectInvestMoneyShow", $str).removeClass("display-none");
                        $("#projectInvestMoneyShow", $str).on("mouseenter", function () {
                            layer.tips("修改前内容: " + dotFour(result.data.projectInvestMoney == null ? "" : result.data.projectInvestMoney ) + "</span><span>&nbsp;&nbsp;(万元)</span></br>" + "修改人: " + result.data.logInputUser + "</br>" + "修改日期: " + result.logInputDate, '#projectInvestMoneyShow', {
                                area: ['auto', 'auto'],
                                tips: [1, '#30b5ff'],
                                time: 2000
                            });
                        });
                    }
                    $(container).append($str);
                }
            });
        }
    },
    contentScaleEd: {  //建设内容、规模
        create: function (val, container) {
            var this_val = val ? val : "";
            var str = "<div class='layui-form-item'>" +
                "<label class='layui-form-label'><span class='col-f00'></span>建设内容、规模</label>" +
                "<div class='layui-input-block'>" +
                "<input type='text' id='contentScaleEd' name='contentScaleEd' class='layui-input' value='" + this_val + "'>" +
                "</div>" +
                "</div>";
            var $str = $(str).clone();
            $(container).append($str);
        },
        show: function (val, container) {
            var this_val = val ? val : "";
            $.ajax({
                url: "/eia/eiaProjectLog/getEneOrEnvOrGreenLogDataMap?eiaProjectLogId=" + eiaProjectLogId,
                type: "POST",
                data: {},
                dataType: "json",
                async: true,
                success: function (result) {
                    var str = "<div class='layui-form-item'>" +
                        "<label class='layui-form-label'><span class='col-f00'></span>建设内容、规模</label>" +
                        "<div class='layui-input-block check-block' id='contentScaleEd'>" + this_val + " &nbsp;<i class='larry-icon font20 cursor-pointer display-none' id='contentScaleShow'>&#xe740;</i></div>" +
                        "</div>";
                    var $str = $(str).clone();
                    if (result.data.contentScale != result.data.contentScaleEd) {
                        $("#contentScaleEd", $str).css("color", "red");
                        $("#contentScaleShow", $str).css("color", "black");
                        $("#contentScaleShow", $str).removeClass("display-none");
                        $("#contentScaleShow", $str).on("mouseenter", function () {
                            layer.tips("修改前内容: " + (result.data.contentScale == null ? "" : result.data.contentScale ) + "</br>" + "修改人: " + result.data.logInputUser + "</br>" + "修改日期: " + result.logInputDate, '#contentScaleShow', {
                                area: ['auto', 'auto'],
                                tips: [1, '#30b5ff'],
                                time: 2000
                            });
                        });
                    }
                    $(container).append($str);
                }
            });
        }
    },
    natureConstructioEd: {  //建设性质
        create: function (val, container) {
            var this_val = val ? val : "";
            var str = "<div class='layui-form-item'>" +
                "<label class='layui-form-label'><span class='col-f00'></span>建设性质</label>" +
                "<div class='layui-input-block'>" +
                "<select name='natureConstructioEd' id='natureConstructioEd' lay-search>" +
                "</select>" +
                "</div>" +
                "</div>";
            var $str = $(str).clone();
            $(container).append($str);
        },
        show: function (val, container) {
            var this_val = val ? val : "";
            $.ajax({
                url: "/eia/eiaProjectLog/getEneOrEnvOrGreenLogDataMap?eiaProjectLogId=" + eiaProjectLogId,
                type: "POST",
                data: {},
                dataType: "json",
                async: true,
                success: function (result) {
                    var str = "<div class='layui-form-item'>" +
                        "<label class='layui-form-label'><span class='col-f00'></span>建设性质</label>" +
                        "<div class='layui-input-block check-block' id='natureConstructioEd'>" + this_val + " &nbsp;<i class='larry-icon font20 cursor-pointer display-none' id='natureConstructioShow'>&#xe740;</i></div>" +
                        "</div>";
                    var $str = $(str).clone();
                    if (result.data.natureConstructio != result.data.natureConstructioEd) {
                        $("#natureConstructioEd", $str).css("color", "red");
                        $("#natureConstructioShow", $str).css("color", "black");
                        $("#natureConstructioShow", $str).removeClass("display-none");
                        $("#natureConstructioShow", $str).on("mouseenter", function () {
                            layer.tips("修改前内容: " + (result.data.natureConstructio == null ? "" : result.data.natureConstructio ) + "</br>" + "修改人: " + result.data.logInputUser + "</br>" + "修改日期: " + result.logInputDate, '#natureConstructioShow', {
                                area: ['auto', 'auto'],
                                tips: [1, '#30b5ff'],
                                time: 2000
                            });
                        });
                    }
                    $(container).append($str);
                }
            });
        }
    },
    planAreaEd: {  //规划面积
        create: function (val, container) {
            var this_val = val ? val : 0;
            var str = "<div class='layui-form-item has-unit'>" +
                "<label class='layui-form-label'><span class='col-f00'></span>规划面积</label>" +
                "<div class='layui-input-block'>" +
                "<input type='number' id='planAreaEd' name='planAreaEd' class='layui-input' value='" + this_val + "'>" +
                "</div>" +
                "<div class='tag-right'><span class='tag-unit'>平方米</span></div>" +
                "</div>";
            var $str = $(str).clone();
            $(container).append($str);
        },
        show: function (val, container) {
            var this_val = val ? val : 0;
            $.ajax({
                url: "/eia/eiaProjectLog/getEneOrEnvOrGreenLogDataMap?eiaProjectLogId=" + eiaProjectLogId,
                type: "POST",
                data: {},
                dataType: "json",
                async: true,
                success: function (result) {
                    var str = "<div class='layui-form-item'>" +
                        "<label class='layui-form-label'><span class='col-f00'></span>规划面积</label>" +
                        "<div class='layui-input-block check-block' id='planAreaEd'><span class='cost-num'>" + this_val + "</span><span>&nbsp;&nbsp;(平方米)</span>&nbsp;<i class='larry-icon font20 cursor-pointer display-none' id='planAreaShow'>&#xe740;</i></div>" +
                        "</div>";
                    var $str = $(str).clone();
                    if (result.data.planArea != result.data.planAreaEd) {
                        $("#planAreaEd", $str).css("color", "red");
                        $("#planAreaShow", $str).css("color", "black");
                        $("#planAreaShow", $str).removeClass("display-none");
                        $("#planAreaShow", $str).on("mouseenter", function () {
                            layer.tips("修改前内容: " + (result.data.planArea == null ? "" : result.data.planArea ) + "</br>" + "修改人: " + result.data.logInputUser + "</br>" + "修改日期: " + result.logInputDate, '#planAreaShow', {
                                area: ['auto', 'auto'],
                                tips: [1, '#30b5ff'],
                                time: 2000
                            });
                        });
                    }
                    $(container).append($str);
                }
            });
        }
    },
    projectMemoEd: {  //基本情况介绍
        create: function (val, container) {
            var this_val = val ? val : "";
            console.log(this_val);
            var str = "<div class='layui-form-item'>" +
                "<label class='layui-form-label'><span class='col-f00'></span>基本情况介绍</label>" +
                "<div class='layui-input-block'>" +
                " <textarea name='projectMemoEd' id='projectMemoEd' placeholder='请输入内容' class='layui-textarea' value=''>" + this_val + "</textarea>" +
                "</div>" +
                "</div>";
            var $str = $(str).clone();
            $(container).append($str);
        },
        show: function (val, container) {
            var this_val = val ? val : "";
            $.ajax({
                url: "/eia/eiaProjectLog/getEneOrEnvOrGreenLogDataMap?eiaProjectLogId=" + eiaProjectLogId,
                type: "POST",
                data: {},
                dataType: "json",
                async: true,
                success: function (result) {
                    var str = "<div class='layui-form-item'>" +
                        "<label class='layui-form-label'><span class='col-f00'></span>基本情况介绍</label>" +
                        "<div class='layui-input-block check-block' id='projectMemoEd'>" + this_val + " &nbsp;<i class='larry-icon font20 cursor-pointer display-none' id='projectMemoShow'>&#xe740;</i></div>" +
                        "</div>";
                    var $str = $(str).clone();
                    if (result.data.projectMemo != result.data.projectMemoEd) {
                        $("#projectMemoEd", $str).css("color", "red");
                        $("#projectMemoShow", $str).css("color", "black");
                        $("#projectMemoShow", $str).removeClass("display-none");
                        $("#projectMemoShow", $str).on("mouseenter", function () {
                            layer.tips("修改前内容: " + (result.data.projectMemo == null ? "" : result.data.projectMemo ) + "</br>" + "修改人: " + result.data.logInputUser + "</br>" + "修改日期: " + result.logInputDate, '#projectMemoShow', {
                                area: ['auto', 'auto'],
                                tips: [1, '#30b5ff'],
                                time: 2000
                            });
                        });
                    }
                    $(container).append($str);
                }
            });
        }
    },
    publishYearEd: {    //发行年限
        create: function (val, container) {
            var this_val = val ? val : 0;
            var str = "<div class='layui-form-item has-unit'>" +
                "<label class='layui-form-label'><span class='col-f00'></span>发行年限</label>" +
                "<div class='layui-input-block'>" +
                "<input type='number' id='publishYearEd' name='publishYearEd' class='layui-input integer-input' value='"+integerForce(this_val)+"'>" +
                "</div>" +
                "<div class='tag-right'><span class='tag-unit'>年</span></div>"+
                "</div>";
            var $str = $(str).clone();
            $('.integer-input',$str).blur(function () {
                var val = $(this).val();
                $(this).val(integerForce(val));
            });
            $(container).append($str);
        },
        show: function (val, container) {
            var this_val = val ? val : 0;
            $.ajax({
                url: "/eia/eiaProjectLog/getEneOrEnvOrGreenLogDataMap?eiaProjectLogId=" + eiaProjectLogId,
                type: "POST",
                data: {},
                dataType: "json",
                async: true,
                success: function (result) {
                    var str = "<div class='layui-form-item'>" +
                        "<label class='layui-form-label'><span class='col-f00'></span>发行年限</label>" +
                        "<div class='layui-input-block check-block' id='publishYearEd'><span class='cost-num'>"+integerForce(this_val)+"</span><span>&nbsp;&nbsp;(年)</span>&nbsp;<i class='larry-icon font20 cursor-pointer display-none' id='publishYearShow'>&#xe740;</i></div>" +
                        "</div>";
                    var $str = $(str).clone();
                    if (result.data.publishYear != result.data.publishYearEd) {
                        $("#publishYearEd", $str).css("color", "red");
                        $("#publishYearShow", $str).css("color", "black");
                        $("#publishYearShow", $str).removeClass("display-none");
                        $("#publishYearShow", $str).on("mouseenter", function () {
                            layer.tips("修改前内容: " + (result.data.publishYear == null ? "" : result.data.publishYear ) + "</br>" + "修改人: " + result.data.logInputUser + "</br>" + "修改日期: " + result.logInputDate, '#publishYearShow', {
                                area: ['auto', 'auto'],
                                tips: [1, '#30b5ff'],
                                time: 2000
                            });
                        });
                    }
                    $(container).append($str);
                }
            });
        }
    },
    publishMoneyEd: {  //发行总额
        create: function (val, container) {
            var this_val = val ? val : 0;
            var str = "<div class='layui-form-item has-unit'>" +
                "<label class='layui-form-label'><span class='col-f00'></span>发行总额</label>" +
                "<div class='layui-input-block'>" +
                "<input type='number' id='publishMoneyEd'  step='0.0001' name='publishMoneyEd' class='layui-input dotFourInput' value='"+dotFour(this_val)+"'>" +
                "</div>" +
                "<div class='tag-right'><span class='tag-unit'>万元</span></div>"+
                "</div>";
            var $str = $(str).clone();
            $('.dotFourInput',$str).blur(function () {
                var val = $(this).val();
                $(this).val(dotFour(val));
            });
            $(container).append($str);
        },
        show: function (val, container) {
            var this_val = val ? val : 0;
            $.ajax({
                url: "/eia/eiaProjectLog/getEneOrEnvOrGreenLogDataMap?eiaProjectLogId=" + eiaProjectLogId,
                type: "POST",
                data: {},
                dataType: "json",
                async: true,
                success: function (result) {
                    var str = "<div class='layui-form-item'>" +
                        "<label class='layui-form-label'><span class='col-f00'></span>发行总额</label>" +
                        "<div class='layui-input-block check-block' id='publishMoneyEd'><span class='cost-num'>"+dotFour(this_val)+"</span><span>&nbsp;&nbsp;(万元)</span>&nbsp;<i class='larry-icon font20 cursor-pointer display-none' id='publishMoneyShow'>&#xe740;</i></div>" +
                        "</div>";
                    var $str = $(str).clone();
                    if (result.data.publishMoney != result.data.publishMoneyEd) {
                        $("#publishMoneyEd", $str).css("color", "red");
                        $("#publishMoneyShow", $str).css("color", "black");
                        $("#publishMoneyShow", $str).removeClass("display-none");
                        $("#publishMoneyShow", $str).on("mouseenter", function () {
                            layer.tips("修改前内容: " + (result.data.publishMoney == null ? "" : result.data.publishMoney ) + "</br>" + "修改人: " + result.data.logInputUser + "</br>" + "修改日期: " + result.logInputDate, '#publishMoneyShow', {
                                area: ['auto', 'auto'],
                                tips: [1, '#30b5ff'],
                                time: 2000
                            });
                        });
                    }
                    $(container).append($str);
                }
            });
        }
    },
    bondCodeEd: {  //债券代码
        create: function (val, container) {
            var this_val = val ? val : "";
            var str = "<div class='layui-form-item'>" +
                "<label class='layui-form-label'><span class='col-f00'></span>债券代码</label>" +
                "<div class='layui-input-block'>" +
                "<input type='text' id='bondCodeEd' name='bondCodeEd' class='layui-input' value='"+this_val+"'>" +
                "</div>" +
                "</div>";
            var $str = $(str).clone();
            $(container).append($str);
        },
        show: function (val, container) {
            var this_val = val ? val : "";
            $.ajax({
                url: "/eia/eiaProjectLog/getEneOrEnvOrGreenLogDataMap?eiaProjectLogId=" + eiaProjectLogId,
                type: "POST",
                data: {},
                dataType: "json",
                async: true,
                success: function (result) {
                    var str = "<div class='layui-form-item'>" +
                        "<label class='layui-form-label'><span class='col-f00'></span>债券代码</label>" +
                        "<div class='layui-input-block check-block' id='bondCodeEd'>"+this_val+" &nbsp;<i class='larry-icon font20 cursor-pointer display-none' id='bondCodeShow'>&#xe740;</i></div>" +
                        "</div>";
                    var $str = $(str).clone();
                    if (result.data.bondCode != result.data.bondCodeEd) {
                        $("#bondCodeEd", $str).css("color", "red");
                        $("#bondCodeShow", $str).css("color", "black");
                        $("#bondCodeShow", $str).removeClass("display-none");
                        $("#bondCodeShow", $str).on("mouseenter", function () {
                            layer.tips("修改前内容: " + (result.data.bondCode == null ? "" : result.data.bondCode ) + "</br>" + "修改人: " + result.data.logInputUser + "</br>" + "修改日期: " + result.logInputDate, '#bondCodeShow', {
                                area: ['auto', 'auto'],
                                tips: [1, '#30b5ff'],
                                time: 2000
                            });
                        });
                    }
                    $(container).append($str);
                }
            });
        }
    },
    bondTypeEd: {  //债券类型
        create: function (val, container) {
            var this_val = val ? val : "";
            var str = "<div class='layui-form-item'>" +
                "<label class='layui-form-label'><span class='col-f00'></span>债券类型</label>" +
                "<div class='layui-input-block'>" +
                "<input type='text' id='bondTypeEd' name='bondTypeEd' class='layui-input' value='"+this_val+"'>" +
                "</div>" +
                "</div>";
            var $str = $(str).clone();
            $(container).append($str);
        },
        show: function (val, container) {
            var this_val = val ? val : "";
            $.ajax({
                url: "/eia/eiaProjectLog/getEneOrEnvOrGreenLogDataMap?eiaProjectLogId=" + eiaProjectLogId,
                type: "POST",
                data: {},
                dataType: "json",
                async: true,
                success: function (result) {
                    var str = "<div class='layui-form-item'>" +
                        "<label class='layui-form-label'><span class='col-f00'></span>债券类型</label>" +
                        "<div class='layui-input-block check-block' id='bondTypeEd'>"+this_val+"&nbsp;<i class='larry-icon font20 cursor-pointer display-none' id='bondTypeShow'>&#xe740;</i></div>" +
                        "</div>";
                    var $str = $(str).clone();
                    if (result.data.bondType != result.data.bondTypeEd) {
                        $("#bondTypeEd", $str).css("color", "red");
                        $("#bondTypeShow", $str).css("color", "black");
                        $("#bondTypeShow", $str).removeClass("display-none");
                        $("#bondTypeShow", $str).on("mouseenter", function () {
                            layer.tips("修改前内容: " + (result.data.bondType == null ? "" : result.data.bondType ) + "</br>" + "修改人: " + result.data.logInputUser + "</br>" + "修改日期: " + result.logInputDate, '#bondTypeShow', {
                                area: ['auto', 'auto'],
                                tips: [1, '#30b5ff'],
                                time: 2000
                            });
                        });
                    }
                    $(container).append($str);
                }
            });
        }
    },
    bondNameEd: {  //债券名称
        create: function (val, container) {
            var this_val = val ? val : "";
            var str = "<div class='layui-form-item'>" +
                "<label class='layui-form-label'><span class='col-f00'></span>债券名称</label>" +
                "<div class='layui-input-block'>" +
                "<input type='text' id='bondNameEd' name='bondNameEd' class='layui-input' value='"+this_val+"'>" +
                "</div>" +
                "</div>";
            var $str = $(str).clone();
            $(container).append($str);
        },
        show: function (val, container) {
            var this_val = val ? val : "";
            $.ajax({
                url: "/eia/eiaProjectLog/getEneOrEnvOrGreenLogDataMap?eiaProjectLogId=" + eiaProjectLogId,
                type: "POST",
                data: {},
                dataType: "json",
                async: true,
                success: function (result) {
                    var str = "<div class='layui-form-item'>" +
                        "<label class='layui-form-label'><span class='col-f00'></span>债券名称</label>" +
                        "<div class='layui-input-block check-block' id='bondNameEd'>"+this_val+"&nbsp;<i class='larry-icon font20 cursor-pointer display-none' id='bondNameShow'>&#xe740;</i></div>" +
                        "</div>";
                    var $str = $(str).clone();
                    if (result.data.bondName != result.data.bondNameEd) {
                        $("#bondNameEd", $str).css("color", "red");
                        $("#bondNameShow", $str).css("color", "black");
                        $("#bondNameShow", $str).removeClass("display-none");
                        $("#bondNameShow", $str).on("mouseenter", function () {
                            layer.tips("修改前内容: " + (result.data.bondName == null ? "" : result.data.bondName ) + "</br>" + "修改人: " + result.data.logInputUser + "</br>" + "修改日期: " + result.logInputDate, '#bondNameShow', {
                                area: ['auto', 'auto'],
                                tips: [1, '#30b5ff'],
                                time: 2000
                            });
                        });
                    }
                    $(container).append($str);
                }
            });
        }
    },
    ifCompFileEd: {  //拟投资项目是否具有完备的合规性文件
        create: function (val, container) {
            var yes_val =""
            var no_val =""
            if(val ==true){
                yes_val="checked=''"
            }else if(val==false){
                no_val = "checked=''";
            }
            var str = "<div class='layui-form-item'>" +
                "<label class='layui-form-label'><span class='col-f00'></span>拟投资项目是否具有完备的合规性文件</label>" +
                "<div class='layui-input-block'>" +
                "<input type='radio' name='ifCompFileEd' value='true' title='是' "+yes_val+">"+
                "<input type='radio' name='ifCompFileEd' value='false' title='否' "+no_val+">"+
                "</div>" +
                "</div>";
            var $str = $(str).clone();
            $(container).append($str);
        },
        show: function (val, container) {
            if(val!=null){
                var this_val = val ? "是" : "否";
            }
            else{
                var this_val = ""
            }
            $.ajax({
                url: "/eia/eiaProjectLog/getEneOrEnvOrGreenLogDataMap?eiaProjectLogId=" + eiaProjectLogId,
                type: "POST",
                data: {},
                dataType: "json",
                async: true,
                success: function (result) {
                    var str = "<div class='layui-form-item'>" +
                        "<label class='layui-form-label'><span class='col-f00'></span>拟投资项目是否具有完备的合规性文件</label>" +
                        "<div class='layui-input-block check-block' id='ifCompFileEd'>"+this_val+"&nbsp;<i class='larry-icon font20 cursor-pointer display-none' id='ifCompFileShow'>&#xe740;</i></div>" +
                        "</div>";
                    var $str = $(str).clone();
                    if (result.data.ifCompFile != result.data.ifCompFileEd) {
                        $("#ifCompFileEd", $str).css("color", "red");
                        $("#ifCompFileShow", $str).css("color", "black");
                        $("#ifCompFileShow", $str).removeClass("display-none");
                        $("#ifCompFileShow", $str).on("mouseenter", function () {
                            layer.tips("修改前内容: " + (result.data.ifCompFile == null ? "" : result.data.ifCompFile ) + "</br>" + "修改人: " + result.data.logInputUser + "</br>" + "修改日期: " + result.logInputDate, '#ifCompFileShow', {
                                area: ['auto', 'auto'],
                                tips: [1, '#30b5ff'],
                                time: 2000
                            });
                        });
                    }
                    $(container).append($str);
                }
            });
        }
    },
    ifIndPolicyEd: {  //拟投资项目是否符合产业（行业）政策
        create: function (val, container) {
            var yes_val =""
            var no_val =""
            if(val ==true){
                yes_val="checked=''"
            }else if(val==false){
                no_val = "checked=''";
            }
            var str = "<div class='layui-form-item'>" +
                "<label class='layui-form-label'><span class='col-f00'></span>拟投资项目是否符合产业（行业）政策</label>" +
                "<div class='layui-input-block'>" +
                "<input type='radio' name='ifIndPolicyEd' value='true' title='是' "+yes_val+">"+
                "<input type='radio' name='ifIndPolicyEd' value='false' title='否' "+no_val+">"+
                "</div>" +
                "</div>";
            var $str = $(str).clone();
            $(container).append($str);
        },
        show: function (val, container) {
            if(val!=null){
                var this_val = val ? "是" : "否";
            }
            else{
                var this_val = ""
            }
            $.ajax({
                url: "/eia/eiaProjectLog/getEneOrEnvOrGreenLogDataMap?eiaProjectLogId=" + eiaProjectLogId,
                type: "POST",
                data: {},
                dataType: "json",
                async: true,
                success: function (result) {
                    var str = "<div class='layui-form-item'>" +
                        "<label class='layui-form-label'><span class='col-f00'></span>拟投资项目是否符合产业（行业）政策</label>" +
                        "<div class='layui-input-block check-block' id='ifIndPolicyEd'>"+this_val+"&nbsp;<i class='larry-icon font20 cursor-pointer display-none' id='ifIndPolicyShow'>&#xe740;</i></div>" +
                        "</div>";
                    var $str = $(str).clone();
                    if (result.data.ifIndPolicy != result.data.ifIndPolicyEd) {
                        $("#ifIndPolicyEd", $str).css("color", "red");
                        $("#ifIndPolicyShow", $str).css("color", "black");
                        $("#ifIndPolicyShow", $str).removeClass("display-none");
                        $("#ifIndPolicyShow", $str).on("mouseenter", function () {
                            layer.tips("修改前内容: " + (result.data.ifIndPolicy == null ? "" : result.data.ifIndPolicy ) + "</br>" + "修改人: " + result.data.logInputUser + "</br>" + "修改日期: " + result.logInputDate, '#ifIndPolicyShow', {
                                area: ['auto', 'auto'],
                                tips: [1, '#30b5ff'],
                                time: 2000
                            });
                        });
                    }
                    $(container).append($str);
                }
            });
        }
    },
    ifGuarFundEd: {  //拟投资项目能否保证资金专款专用
        create: function (val, container) {
            var yes_val =""
            var no_val =""
            if(val ==true){
                yes_val="checked=''"
            }else if(val==false){
                no_val = "checked=''";
            }
            var str = "<div class='layui-form-item'>" +
                "<label class='layui-form-label'><span class='col-f00'></span>拟投资项目能否保证资金专款专用</label>" +
                "<div class='layui-input-block'>" +
                "<input type='radio' name='ifGuarFundEd' value='true' title='是' "+yes_val+">"+
                "<input type='radio' name='ifGuarFundEd' value='false' title='否' "+no_val+">"+
                "</div>" +
                "</div>";
            var $str = $(str).clone();
            $(container).append($str);
        },
        show: function (val, container) {
            if(val!=null){
                var this_val = val ? "是" : "否";
            }
            else{
                var this_val = ""
            }
            $.ajax({
                url: "/eia/eiaProjectLog/getEneOrEnvOrGreenLogDataMap?eiaProjectLogId=" + eiaProjectLogId,
                type: "POST",
                data: {},
                dataType: "json",
                async: true,
                success: function (result) {
                    var str = "<div class='layui-form-item'>" +
                        "<label class='layui-form-label'><span class='col-f00'></span>拟投资项目能否保证资金专款专用</label>" +
                        "<div class='layui-input-block check-block' id='ifGuarFundEd'>"+this_val+"&nbsp;<i class='larry-icon font20 cursor-pointer display-none' id='ifGuarFundShow'>&#xe740;</i></div>" +
                        "</div>";
                    var $str = $(str).clone();
                    if (result.data.ifGuarFund != result.data.ifGuarFundEd) {
                        $("#ifGuarFundEd", $str).css("color", "red");
                        $("#ifGuarFundShow", $str).css("color", "black");
                        $("#ifGuarFundShow", $str).removeClass("display-none");
                        $("#ifGuarFundShow", $str).on("mouseenter", function () {
                            layer.tips("修改前内容: " + (result.data.ifGuarFund == null ? "" : result.data.ifGuarFund ) + "</br>" + "修改人: " + result.data.logInputUser + "</br>" + "修改日期: " + result.logInputDate, '#ifGuarFundShow', {
                                area: ['auto', 'auto'],
                                tips: [1, '#30b5ff'],
                                time: 2000
                            });
                        });
                    }
                    $(container).append($str);
                }
            });
        }
    },
    ifPublishCompleteEd: {  //拟投资项目信息披露制度是否健全
        create: function (val, container) {
            var yes_val =""
            var no_val =""
            if(val ==true){
                yes_val="checked=''"
            }else if(val==false){
                no_val = "checked=''";
            }
            var str = "<div class='layui-form-item'>" +
                "<label class='layui-form-label'><span class='col-f00'></span>拟投资项目信息披露制度是否健全</label>" +
                "<div class='layui-input-block'>" +
                "<input type='radio' name='ifPublishCompleteEd' value='true' title='是' "+yes_val+">"+
                "<input type='radio' name='ifPublishCompleteEd' value='false' title='否' "+no_val+">"+
                "</div>" +
                "</div>";
            var $str = $(str).clone();
            $(container).append($str);
        },
        show: function (val, container) {
            if(val!=null){
                var this_val = val ? "是" : "否";
            }
            else{
                var this_val = ""
            }
            $.ajax({
                url: "/eia/eiaProjectLog/getEneOrEnvOrGreenLogDataMap?eiaProjectLogId=" + eiaProjectLogId,
                type: "POST",
                data: {},
                dataType: "json",
                async: true,
                success: function (result) {
                    var str = "<div class='layui-form-item'>" +
                        "<label class='layui-form-label'><span class='col-f00'></span>拟投资项目信息披露制度是否健全</label>" +
                        "<div class='layui-input-block check-block' id='ifPublishCompleteEd'>"+this_val+"&nbsp;<i class='larry-icon font20 cursor-pointer display-none' id='ifPublishCompleteShow'>&#xe740;</i></div>" +
                        "</div>";
                    var $str = $(str).clone();
                    if (result.data.ifPublishComplete != result.data.ifPublishCompleteEd) {
                        $("#ifPublishCompleteEd", $str).css("color", "red");
                        $("#ifPublishCompleteShow", $str).css("color", "black");
                        $("#ifPublishCompleteShow", $str).removeClass("display-none");
                        $("#ifPublishCompleteShow", $str).on("mouseenter", function () {
                            layer.tips("修改前内容: " + (result.data.ifPublishComplete == null ? "" : result.data.ifPublishComplete ) + "</br>" + "修改人: " + result.data.logInputUser + "</br>" + "修改日期: " + result.logInputDate, '#ifPublishCompleteShow', {
                                area: ['auto', 'auto'],
                                tips: [1, '#30b5ff'],
                                time: 2000
                            });
                        });
                    }
                    $(container).append($str);
                }
            });
        }
    },
    ifGreenCatalogEd: {  //拟投资项目是否符合《绿色债券支持项目目录》
        create: function (val, container) {
            var yes_val =""
            var no_val =""
            if(val ==true){
                yes_val="checked=''"
            }else if(val==false){
                no_val = "checked=''";
            }
            var str = "<div class='layui-form-item'>" +
                "<label class='layui-form-label'><span class='col-f00'></span>拟投资项目是否符合《绿色债券支持项目目录》</label>" +
                "<div class='layui-input-block'>" +
                "<input type='radio' name='ifGreenCatalogEd' value='true' title='是' "+yes_val+">"+
                "<input type='radio' name='ifGreenCatalogEd' value='false' title='否' "+no_val+">"+
                "</div>" +
                "</div>";
            var $str = $(str).clone();
            $(container).append($str);
        },
        show: function (val, container) {
            if(val!=null){
                var this_val = val ? "是" : "否";
            }
            else{
                var this_val = ""
            }
            $.ajax({
                url: "/eia/eiaProjectLog/getEneOrEnvOrGreenLogDataMap?eiaProjectLogId=" + eiaProjectLogId,
                type: "POST",
                data: {},
                dataType: "json",
                async: true,
                success: function (result) {
                    var str = "<div class='layui-form-item'>" +
                        "<label class='layui-form-label'><span class='col-f00'></span>拟投资项目是否符合《绿色债券支持项目目录》</label>" +
                        "<div class='layui-input-block check-block' id='ifGreenCatalogEd'>"+this_val+"&nbsp;<i class='larry-icon font20 cursor-pointer display-none' id='ifGreenCatalogShow'>&#xe740;</i></div>" +
                        "</div>";
                    var $str = $(str).clone();
                    if (result.data.ifGreenCatalog != result.data.ifGreenCatalogEd) {
                        $("#ifGreenCatalogEd", $str).css("color", "red");
                        $("#ifGreenCatalogShow", $str).css("color", "black");
                        $("#ifGreenCatalogShow", $str).removeClass("display-none");
                        $("#ifGreenCatalogShow", $str).on("mouseenter", function () {
                            layer.tips("修改前内容: " + (result.data.ifGreenCatalog == null ? "" : result.data.ifGreenCatalog ) + "</br>" + "修改人: " + result.data.logInputUser + "</br>" + "修改日期: " + result.logInputDate, '#ifGreenCatalogShow', {
                                area: ['auto', 'auto'],
                                tips: [1, '#30b5ff'],
                                time: 2000
                            });
                        });
                    }
                    $(container).append($str);
                }
            });
        }
    },
};