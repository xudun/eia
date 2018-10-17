//金额输入框强制小数点后4位
var dotFour = function(value){
    var valF = value ? parseFloat(value) : parseFloat(0),
        valR = Math.round(valF*10000)/10000,
        valS = valR.toString(),
        rs = valS.indexOf('.');
    if(rs<0){
        rs = valS.length;
        valS += '.';
    }
    while (valS.length <= rs + 4){
        valS += '0';
    }
    return valS;
};

var eiaProjectLogId = parent.$('#eiaProjectLogId').val();

var moneyFillInputs = {
    projectComfeeEd: {  //项目编制费
        create: function (val, container) {
            var this_val = val ? val : 0;
            var str = "<div class='layui-form-item has-unit'>" +
                "<label class='layui-form-label'><span class='col-f00'></span>项目编制费</label>" +
                "<div class='layui-input-block'>" +
                    "<input type='number' id='projectComfee' step='0.0001' name='projectComfee' class='layui-input dotFourInput' value='"+dotFour(this_val)+"'>" +
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
                url: "/eia/eiaProjectLog/getEiaProjectLogDataMap?eiaProjectLogId=" + eiaProjectLogId,
                type: "POST",
                data: {},
                dataType: "json",
                success: function (result) {
                    var str = "<div class='layui-form-item'>" +
                        "<label class='layui-form-label'><span class='col-f00'></span>项目编制费</label>" +
                        "<div class='layui-input-block check-block' id='projectComfeeEd'><span class='cost-num'>" + dotFour(this_val) + "</span><span>&nbsp;&nbsp;(万元)</span>&nbsp;&nbsp;<i class='larry-icon font20 cursor-pointer display-none' id='projectComfeeShow'>&#xe740;</i></div>" +
                        "</div>";
                    var $str = $(str).clone();
                    if (result.data.projectComfee == "" || result.data.projectComfee == null) {
                        result.data.projectComfee = 0;
                    }
                    if (result.data.projectComfeeEd == "" || result.data.projectComfeeEd == null) {
                        result.data.projectComfeeEd = 0;
                    }
                    if (result.data.projectComfee != result.data.projectComfeeEd) {
                        $("#projectComfeeEd", $str).css("color", "red");
                        $("#projectComfeeShow", $str).css("color", "black");
                        $("#projectComfeeShow", $str).removeClass("display-none");
                        $("#projectComfeeShow", $str).on("mouseenter", function () {
                            layer.tips("修改前内容: " + (result.data.projectComfee == null ? "" : result.data.projectComfee ) + "&nbsp;&nbsp;(万元)</br>" + "修改人: " + result.data.logInputUser + "</br>" + "修改日期: " + result.logInputDate, '#projectComfeeShow', {
                                area: ['auto', 'auto'],
                                tips: [1, '#30b5ff'],
                                time: 2000
                            });
                        });
                    } else {
                        $("#projectComfee").text(result.data.projectComfeeEd);
                    }
                    $(container).append($str);
                }
            });
        }
    },
    environmentalFeeEd: {  //环境监测费
        create: function (val, container) {
            var this_val = val ? val : 0;
            var str = "<div class='layui-form-item has-unit'>" +
                "<label class='layui-form-label'><span class='col-f00'></span>环境监测费</label>" +
                "<div class='layui-input-block'>" +
                    "<input type='number' id='environmentalFee' step='0.0001' name='environmentalFee' class='layui-input dotFourInput' value='"+dotFour(this_val)+"'>" +
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
                url: "/eia/eiaProjectLog/getEiaProjectLogDataMap?eiaProjectLogId=" + eiaProjectLogId,
                type: "POST",
                data: {},
                dataType: "json",
                success: function (result) {
                    var str = "<div class='layui-form-item'>" +
                        "<label class='layui-form-label'><span class='col-f00'></span>环境监测费</label>" +
                        "<div class='layui-input-block check-block' id='environmentalFeeEd'><span class='cost-num'>" + dotFour(this_val) + "</span><span>&nbsp;&nbsp;(万元)</span>&nbsp;&nbsp;<i class='larry-icon font20 cursor-pointer display-none' id='environmentalFeeShow'>&#xe740;</i></div>" +
                        "</div>";
                    var $str = $(str).clone();
                    if (result.data.environmentalFee == "" || result.data.environmentalFee == null) {
                        result.data.environmentalFee = 0;
                    }
                    if (result.data.environmentalFeeEd == "" || result.data.environmentalFeeEd == null) {
                        result.data.environmentalFeeEd = 0;
                    }
                    if (result.data.environmentalFee != result.data.environmentalFeeEd) {
                        $("#environmentalFeeEd", $str).css("color", "red");
                        $("#environmentalFeeShow", $str).css("color", "black");
                        $("#environmentalFeeShow", $str).removeClass("display-none");
                        $("#environmentalFeeShow", $str).on("mouseenter", function () {
                            layer.tips("修改前内容: " + (result.data.environmentalFee == null ? "" : result.data.environmentalFee ) + "&nbsp;&nbsp;(万元)</br>" + "修改人: " + result.data.logInputUser + "</br>" + "修改日期: " + result.logInputDate, '#environmentalFeeShow', {
                                area: ['auto', 'auto'],
                                tips: [1, '#30b5ff'],
                                time: 2000
                            });
                        });
                    } else {
                        $("#environmentalFee").text(result.data.environmentalFeeEd);
                    }
                    $(container).append($str);
                }
            });
        }
    },
    groundwaterFeeEd: {  //地下水专题评价费
        create: function (val, container) {
            var this_val = val ? val : 0;
            var str = "<div class='layui-form-item has-unit'>" +
                "<label class='layui-form-label'><span class='col-f00'></span>地下水专题评价费</label>" +
                "<div class='layui-input-block'>" +
                    "<input type='number' id='groundwaterFee' step='0.0001' name='groundwaterFee' class='layui-input dotFourInput' value='"+dotFour(this_val)+"'>" +
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
                url: "/eia/eiaProjectLog/getEiaProjectLogDataMap?eiaProjectLogId=" + eiaProjectLogId,
                type: "POST",
                data: {},
                dataType: "json",
                success: function (result) {
                    var str = "<div class='layui-form-item'>" +
                        "<label class='layui-form-label'><span class='col-f00'></span>地下水专题评价费</label>" +
                        "<div class='layui-input-block check-block' id='groundwaterFeeEd'><span class='cost-num'>" + dotFour(this_val) + "</span><span>&nbsp;&nbsp;(万元)</span>&nbsp;&nbsp;<i class='larry-icon font20 cursor-pointer display-none' id='groundwaterFeeShow'>&#xe740;</i></div>" +
                        "</div>";
                    var $str = $(str).clone();
                    if (result.data.groundwaterFee == "" || result.data.groundwaterFee == null) {
                        result.data.groundwaterFee = 0;
                    }
                    if (result.data.groundwaterFeeEd == "" || result.data.groundwaterFeeEd == null) {
                        result.data.groundwaterFeeEd = 0;
                    }
                    if (result.data.groundwaterFee != result.data.groundwaterFeeEd) {
                        $("#groundwaterFeeEd", $str).css("color", "red");
                        $("#groundwaterFeeShow", $str).css("color", "black");
                        $("#groundwaterFeeShow", $str).removeClass("display-none");
                        $("#groundwaterFeeShow", $str).on("mouseenter", function () {
                            layer.tips("修改前内容: " + (result.data.groundwaterFee == null ? "" : result.data.groundwaterFee ) + "&nbsp;&nbsp;(万元)</br>" + "修改人: " + result.data.logInputUser + "</br>" + "修改日期: " + result.logInputDate, '#groundwaterFeeShow', {
                                area: ['auto', 'auto'],
                                tips: [1, '#30b5ff'],
                                time: 2000
                            });
                        });
                    } else {
                        $("#groundwaterFee").text(result.data.groundwaterFeeEd);
                    }
                    $(container).append($str);
                }
            });
        }
    },
    expertFeeEd: {  //专家评审费
        create: function (val, container) {
            var this_val = val ? val : 0;
            var str = "<div class='layui-form-item has-unit'>" +
                "<label class='layui-form-label'><span class='col-f00'></span>专家评审费</label>" +
                "<div class='layui-input-block'>" +
                    "<input type='number' id='expertFee' step='0.0001' name='expertFeeEd' class='layui-input dotFourInput' value='"+dotFour(this_val)+"'>" +
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
                url: "/eia/eiaProjectLog/getEiaProjectLogDataMap?eiaProjectLogId=" + eiaProjectLogId,
                type: "POST",
                data: {},
                dataType: "json",
                success: function (result) {
                    var str = "<div class='layui-form-item'>" +
                        "<label class='layui-form-label'><span class='col-f00'></span>专家评审费</label>" +
                        "<div class='layui-input-block check-block' id='expertFeeEd'><span class='cost-num'>" + dotFour(this_val) + "</span><span>&nbsp;&nbsp;(万元)</span>&nbsp;&nbsp;<i class='larry-icon font20 cursor-pointer display-none' id='expertFeeShow'>&#xe740;</i></div>" +
                        "</div>";
                    var $str = $(str).clone();
                    if (result.data.expertFee == "" || result.data.expertFee == null) {
                        result.data.expertFee = 0;
                    }
                    if (result.data.expertFeeEd == "" || result.data.expertFeeEd == null) {
                        result.data.expertFeeEd = 0;
                    }
                    if (result.data.expertFee != result.data.expertFeeEd) {
                        $("#expertFeeEd", $str).css("color", "red");
                        $("#expertFeeShow", $str).css("color", "black");
                        $("#expertFeeShow", $str).removeClass("display-none");
                        $("#expertFeeShow", $str).on("mouseenter", function () {
                            layer.tips("修改前内容: " + (result.data.expertFee == null ? "" : result.data.expertFee ) + "&nbsp;&nbsp;(万元)</br>" + "修改人: " + result.data.logInputUser + "</br>" + "修改日期: " + result.logInputDate, '#expertFeeShow', {
                                area: ['auto', 'auto'],
                                tips: [1, '#30b5ff'],
                                time: 2000
                            });
                        });
                    } else {
                        $("#expertFee").text(result.data.expertFeeEd);
                    }
                    $(container).append($str);
                }
            });
        }
    },
    otherFeeEd: {  //其他费用
        create: function (val, container) {
            var this_val = val ? val : 0;
            var str = "<div class='layui-form-item has-unit'>" +
                "<label class='layui-form-label'><span class='col-f00'></span>其他费用</label>" +
                "<div class='layui-input-block'>" +
                    "<input type='number' id='otherFee' step='0.0001' name='otherFeeEd' class='layui-input dotFourInput' value='"+dotFour(this_val)+"'>" +
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
                url: "/eia/eiaProjectLog/getEiaProjectLogDataMap?eiaProjectLogId=" + eiaProjectLogId,
                type: "POST",
                data: {},
                dataType: "json",
                success: function (result) {
                    var str = "<div class='layui-form-item'>" +
                        "<label class='layui-form-label'><span class='col-f00'></span>其他费用</label>" +
                        "<div class='layui-input-block check-block' id='otherFeeEd'><span class='cost-num'>" + dotFour(this_val) + "</span><span>&nbsp;&nbsp;(万元)</span>&nbsp;&nbsp;<i class='larry-icon font20 cursor-pointer display-none' id='otherFeeShow'>&#xe740;</i></div>" +
                        "</div>";
                    var $str = $(str).clone();
                    if (result.data.otherFee == "" || result.data.otherFee == null) {
                        result.data.otherFee = 0;
                    }
                    if (result.data.otherFeeEd == "" || result.data.otherFeeEd == null) {
                        result.data.otherFeeEd = 0;
                    }
                    if (result.data.otherFee != result.data.otherFeeEd) {
                        $("#otherFeeEd", $str).css("color", "red");
                        $("#otherFeeShow", $str).css("color", "black");
                        $("#otherFeeShow", $str).removeClass("display-none");
                        $("#otherFeeShow", $str).on("mouseenter", function () {
                            layer.tips("修改前内容: " + (result.data.otherFee == null ? "" : result.data.otherFee ) + "&nbsp;&nbsp;(万元)</br>" + "修改人: " + result.data.logInputUser + "</br>" + "修改日期: " + result.logInputDate, '#otherFeeShow', {
                                area: ['auto', 'auto'],
                                tips: [1, '#30b5ff'],
                                time: 2000
                            });
                        });
                    } else {
                        $("#otherFee").text(result.data.otherFeeEd);
                    }
                    $(container).append($str);
                }
            });
        }
    },
    specialFeeEd: {  //仪器和劳保用品
        create: function (val, container) {
            var this_val = val ? val : 0;
            var str = "<div class='layui-form-item has-unit'>" +
                "<label class='layui-form-label'><span class='col-f00'></span>仪器和劳保用品</label>" +
                "<div class='layui-input-block'>" +
                    "<input type='number' id='specialFee' step='0.0001' name='specialFee' class='layui-input dotFourInput' value='"+dotFour(this_val)+"'>" +
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
                url: "/eia/eiaProjectLog/getEiaProjectLogDataMap?eiaProjectLogId=" + eiaProjectLogId,
                type: "POST",
                data: {},
                dataType: "json",
                success: function (result) {
                    var str = "<div class='layui-form-item'>" +
                        "<label class='layui-form-label'><span class='col-f00'></span>仪器和劳保用品</label>" +
                        "<div class='layui-input-block check-block' id='specialFeeEd'><span class='cost-num'>" + dotFour(this_val) + "</span><span>&nbsp;&nbsp;(万元)</span>&nbsp;&nbsp;<i class='larry-icon font20 cursor-pointer display-none' id='specialFeeShow'>&#xe740;</i></div>" +
                        "</div>";
                    var $str = $(str).clone();
                    if (result.data.specialFee == "" || result.data.specialFee == null) {
                        result.data.specialFee = 0;
                    }
                    if (result.data.specialFeeEd == "" || result.data.specialFeeEd == null) {
                        result.data.specialFeeEd = 0;
                    }
                    if (result.data.specialFee != result.data.specialFeeEd) {
                        $("#specialFeeEd", $str).css("color", "red");
                        $("#specialFeeShow", $str).css("color", "black");
                        $("#specialFeeShow", $str).removeClass("display-none");
                        $("#specialFeeShow", $str).on("mouseenter", function () {
                            layer.tips("修改前内容: " + (result.data.specialFee == null ? "" : result.data.specialFee ) + "&nbsp;&nbsp;(万元)</br>" + "修改人: " + result.data.logInputUser + "</br>" + "修改日期: " + result.logInputDate, '#specialFeeShow', {
                                area: ['auto', 'auto'],
                                tips: [1, '#30b5ff'],
                                time: 2000
                            });
                        });
                    } else {
                        $("#specialFee").text(result.data.specialFeeEd);
                    }
                    $(container).append($str);
                }
            });
        }
    },
    detectFeeEd: {  //项目调查费
        create: function (val, container) {
            var this_val = val ? val : 0;
            var str = "<div class='layui-form-item has-unit'>" +
                "<label class='layui-form-label'><span class='col-f00'></span>项目调查费</label>" +
                "<div class='layui-input-block'>" +
                    "<input type='number' id='detectFee' step='0.0001' name='detectFee' class='layui-input dotFourInput' value='"+dotFour(this_val)+"'>" +
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
                url: "/eia/eiaProjectLog/getEiaProjectLogDataMap?eiaProjectLogId=" + eiaProjectLogId,
                type: "POST",
                data: {},
                dataType: "json",
                success: function (result) {
                    var str = "<div class='layui-form-item'>" +
                        "<label class='layui-form-label'><span class='col-f00'></span>项目调查费</label>" +
                        "<div class='layui-input-block check-block' id='detectFeeEd'><span class='cost-num'>" + dotFour(this_val) + "</span><span>&nbsp;&nbsp;(万元)</span>&nbsp;&nbsp;<i class='larry-icon font20 cursor-pointer display-none' id='detectFeeShow'>&#xe740;</i></div>" +
                        "</div>";
                    var $str = $(str).clone();
                    if (result.data.detectFee == "" || result.data.detectFee == null) {
                        result.data.detectFee = 0;
                    }
                    if (result.data.detectFeeEd == "" || result.data.detectFeeEd == null) {
                        result.data.detectFeeEd = 0;
                    }
                    if (result.data.detectFee != result.data.detectFeeEd) {
                        $("#detectFeeEd", $str).css("color", "red");
                        $("#detectFeeShow", $str).css("color", "black");
                        $("#detectFeeShow", $str).removeClass("display-none");
                        $("#detectFeeShow", $str).on("mouseenter", function () {
                            layer.tips("修改前内容: " + (result.data.detectFee == null ? "" : result.data.detectFee ) + "&nbsp;&nbsp;(万元)</br>" + "修改人: " + result.data.logInputUser + "</br>" + "修改日期: " + result.logInputDate, '#detectFeeShow', {
                                area: ['auto', 'auto'],
                                tips: [1, '#30b5ff'],
                                time: 2000
                            });
                        });
                    } else {
                        $("#detectFee").text(result.data.detectFeeEd);
                    }
                    $(container).append($str);
                }
            });
        }
    },
    projectMoneyEd: {  //项目金额
        create: function (val, container) {
            var this_val = val ? val : 0;
            var str = "<div class='layui-form-item has-unit'>" +
                "<label class='layui-form-label'><span class='col-f00'></span>项目金额</label>" +
                "<div class='layui-input-block'>" +
                    "<input type='number' id='projectMoney' step='0.0001' name='projectMoney' class='layui-input dotFourInput' value='"+dotFour(this_val)+"'>" +
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
                url: "/eia/eiaProjectLog/getEiaProjectLogDataMap?eiaProjectLogId=" + eiaProjectLogId,
                type: "POST",
                data: {},
                dataType: "json",
                success: function (result) {
                    var str = "<div class='layui-form-item'>" +
                        "<label class='layui-form-label'><span class='col-f00'></span>项目金额</label>" +
                        "<div class='layui-input-block check-block' id='projectMoneyEd'><span class='cost-num'>" + dotFour(this_val) + "</span><span>&nbsp;&nbsp;(万元)</span>&nbsp;&nbsp;<i class='larry-icon font20 cursor-pointer display-none' id='projectMoneyShow'>&#xe740;</i></div>" +
                        "</div>";
                    var $str = $(str).clone();
                    if (result.data.projectMoney == "" || result.data.projectMoney == null) {
                        result.data.projectMoney = 0;
                    }
                    if (result.data.projectMoneyEd == "" || result.data.projectMoneyEd == null) {
                        result.data.projectMoneyEd = 0;
                    }
                    if (result.data.projectMoney != result.data.projectMoneyEd) {
                        $("#projectMoneyEd", $str).css("color", "red");
                        $("#projectMoneyShow", $str).css("color", "black");
                        $("#projectMoneyShow", $str).removeClass("display-none");
                        $("#projectMoneyShow", $str).on("mouseenter", function () {
                            layer.tips("修改前内容: " + (result.data.projectMoney == null ? "" : result.data.projectMoney ) + "&nbsp;&nbsp;(万元)</br>" + "修改人: " + result.data.logInputUser + "</br>" + "修改日期: " + result.logInputDate, '#projectMoneyShow', {
                                area: ['auto', 'auto'],
                                tips: [1, '#30b5ff'],
                                time: 2000
                            });
                        });
                    } else {
                        $("#projectMoney").text(result.data.projectMoneyEd);
                    }
                    $(container).append($str);
                }
            });
        }
    },
    preIssCertFeeEd: {  //发行前认证费
        create: function (val, container) {
            var this_val = val ? val : 0;
            var str = "<div class='layui-form-item has-unit'>" +
                "<label class='layui-form-label'><span class='col-f00'></span>发行前认证费</label>" +
                "<div class='layui-input-block'>" +
                    "<input type='number' id='preIssCertFee' step='0.0001' name='preIssCertFee' class='layui-input dotFourInput' value='"+dotFour(this_val)+"'>" +
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
                url: "/eia/eiaProjectLog/getEiaProjectLogDataMap?eiaProjectLogId=" + eiaProjectLogId,
                type: "POST",
                data: {},
                dataType: "json",
                success: function (result) {
                    var str = "<div class='layui-form-item'>" +
                        "<label class='layui-form-label'><span class='col-f00'></span>发行前认证费</label>" +
                        "<div class='layui-input-block check-block' id='preIssCertFeeEd'><span class='cost-num'>" + dotFour(this_val) + "</span><span>&nbsp;&nbsp;(万元)</span>&nbsp;&nbsp;<i class='larry-icon font20 cursor-pointer display-none' id='preIssCertFeeShow'>&#xe740;</i></div>" +
                        "</div>";
                    var $str = $(str).clone();
                    if (result.data.preIssCertFee == "" || result.data.preIssCertFee == null) {
                        result.data.preIssCertFee = 0;
                    }
                    if (result.data.preIssCertFeeEd == "" || result.data.preIssCertFeeEd == null) {
                        result.data.preIssCertFeeEd = 0;
                    }
                    if (result.data.preIssCertFee != result.data.preIssCertFeeEd) {
                        $("#preIssCertFeeEd", $str).css("color", "red");
                        $("#preIssCertFeeShow", $str).css("color", "black");
                        $("#preIssCertFeeShow", $str).removeClass("display-none");
                        $("#preIssCertFeeShow", $str).on("mouseenter", function () {
                            layer.tips("修改前内容: " + (result.data.preIssCertFee == null ? "" : result.data.preIssCertFee ) + "&nbsp;&nbsp;(万元)</br>" + "修改人: " + result.data.logInputUser + "</br>" + "修改日期: " + result.logInputDate, '#preIssCertFeeShow', {
                                area: ['auto', 'auto'],
                                tips: [1, '#30b5ff'],
                                time: 2000
                            });
                        });
                    } else {
                        $("#preIssCertFee").text(result.data.preIssCertFeeEd);
                    }
                    $(container).append($str);
                }
            });
        }
    },
    preSurvCertFeeEd: {  //存续期认证费
        create: function (val, container) {
            var this_val = val ? val : 0;
            var str = "<div class='layui-form-item has-unit'>" +
                "<label class='layui-form-label'><span class='col-f00'></span>存续期认证费</label>" +
                "<div class='layui-input-block'>" +
                    "<input type='number' id='preSurvCertFee' step='0.0001' name='preSurvCertFee' class='layui-input dotFourInput' value='"+dotFour(this_val)+"'>" +
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
                url: "/eia/eiaProjectLog/getEiaProjectLogDataMap?eiaProjectLogId=" + eiaProjectLogId,
                type: "POST",
                data: {},
                dataType: "json",
                success: function (result) {
                    var str = "<div class='layui-form-item'>" +
                        "<label class='layui-form-label'><span class='col-f00'></span>存续期认证费</label>" +
                        "<div class='layui-input-block check-block' id='preSurvCertFeeEd'><span class='cost-num'>" + dotFour(this_val) + "</span><span>&nbsp;&nbsp;(万元)</span>&nbsp;&nbsp;<i class='larry-icon font20 cursor-pointer display-none' id='preSurvCertFeeShow'>&#xe740;</i></div>" +
                        "</div>";
                    var $str = $(str).clone();
                    if (result.data.preSurvCertFee == "" || result.data.preSurvCertFee == null) {
                        result.data.preSurvCertFee = 0;
                    }
                    if (result.data.preSurvCertFeeEd == "" || result.data.preSurvCertFeeEd == null) {
                        result.data.preSurvCertFeeEd = 0;
                    }
                    if (result.data.preSurvCertFee != result.data.preSurvCertFeeEd) {
                        $("#preSurvCertFeeEd", $str).css("color", "red");
                        $("#preSurvCertFeeShow", $str).css("color", "black");
                        $("#preSurvCertFeeShow", $str).removeClass("display-none");
                        $("#preSurvCertFeeShow", $str).on("mouseenter", function () {
                            layer.tips("修改前内容: " + (result.data.preSurvCertFee == null ? "" : result.data.preSurvCertFee ) + "&nbsp;&nbsp;(万元)</br>" + "修改人: " + result.data.logInputUser + "</br>" + "修改日期: " + result.logInputDate, '#preSurvCertFeeShow', {
                                area: ['auto', 'auto'],
                                tips: [1, '#30b5ff'],
                                time: 2000
                            });
                        });
                    } else {
                        $("#preSurvCertFee").text(result.data.preSurvCertFeeEd);
                    }
                    $(container).append($str);
                }
            });
        }
    },
    certServeFeeEd: {  //认证服务费
        create: function (val, container) {
            var this_val = val ? val : 0;
            var str = "<div class='layui-form-item has-unit'>" +
                "<label class='layui-form-label'><span class='col-f00'></span>认证服务费</label>" +
                "<div class='layui-input-block'>" +
                    "<input type='number' id='certServeFee' step='0.0001' name='certServeFee' class='layui-input dotFourInput' value='"+dotFour(this_val)+"'>" +
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
                url: "/eia/eiaProjectLog/getEiaProjectLogDataMap?eiaProjectLogId=" + eiaProjectLogId,
                type: "POST",
                data: {},
                dataType: "json",
                success: function (result) {
                    var str = "<div class='layui-form-item'>" +
                        "<label class='layui-form-label'><span class='col-f00'></span>认证服务费</label>" +
                        "<div class='layui-input-block check-block' id='certServeFeeEd'><span class='cost-num'>" + dotFour(this_val) + "</span><span>&nbsp;&nbsp;(万元)</span>&nbsp;&nbsp;<i class='larry-icon font20 cursor-pointer display-none' id='certServeFeeShow'>&#xe740;</i></div>" +
                        "</div>";
                    var $str = $(str).clone();
                    if (result.data.certServeFee == "" || result.data.certServeFee == null) {
                        result.data.certServeFee = 0;
                    }
                    if (result.data.certServeFeeEd == "" || result.data.certServeFeeEd == null) {
                        result.data.certServeFeeEd = 0;
                    }
                    if (result.data.certServeFee != result.data.certServeFeeEd) {
                        $("#certServeFeeEd", $str).css("color", "red");
                        $("#certServeFeeShow", $str).css("color", "black");
                        $("#certServeFeeShow", $str).removeClass("display-none");
                        $("#certServeFeeShow", $str).on("mouseenter", function () {
                            layer.tips("修改前内容: " + (result.data.certServeFee == null ? "" : result.data.certServeFee ) + "&nbsp;&nbsp;(万元)</br>" + "修改人: " + result.data.logInputUser + "</br>" + "修改日期: " + result.logInputDate, '#certServeFeeShow', {
                                area: ['auto', 'auto'],
                                tips: [1, '#30b5ff'],
                                time: 2000
                            });
                        });
                    } else {
                        $("#certServeFee").text(result.data.certServeFeeEd);
                    }
                    $(container).append($str);
                }
            });
        }
    },
};