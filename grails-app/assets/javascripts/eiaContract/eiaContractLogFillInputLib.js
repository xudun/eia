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

var eiaContractLogId = parent.$('#eiaContractLogId').val();
if(!eiaContractLogId){
    eiaContractLogId=getParamByUrl(window.location.href).eiaContractLogId
}
var moneyFillInputs = {
    reportFeesEd: {  //项目编制费
        create: function (val, container) {
            var this_val = val ? val : 0;
            var str = "<div class='layui-form-item has-unit'>" +
                "<label class='layui-form-label'><span class='col-f00'></span>项目编制费</label>" +
                "<div class='layui-input-block'>" +
                    "<input type='number' id='reportFeesEd' step='0.0001' name='reportFeesEd' class='layui-input dotFourInput' value='"+dotFour(this_val)+"'>" +
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
                url: "/eia/eiaContractLog/getEiaContractLogDataMap?eiaContractLogId=" + eiaContractLogId,
                type: "POST",
                data: {},
                dataType: "json",
                success: function (result) {
                    var str = "<div class='layui-form-item'>" +
                        "<label class='layui-form-label'><span class='col-f00'></span>项目编制费</label>" +
                        "<div class='layui-input-block check-block' id='reportFeesEd'><span class='cost-num'>" + dotFour(this_val) + "</span><span>&nbsp;&nbsp;(万元)</span>&nbsp;&nbsp;<i class='larry-icon font20 cursor-pointer display-none' id='reportFeesShow'>&#xe740;</i></div>" +
                        "</div>";
                    var $str = $(str).clone();
                    if (result.data.reportFees == "" || result.data.reportFees == null) {
                        result.data.reportFees = 0;
                    }
                    if (result.data.reportFeesEd == "" || result.data.reportFeesEd == null) {
                        result.data.reportFeesEd = 0;
                    }
                    if (result.data.reportFees != result.data.reportFeesEd) {
                        $("#reportFeesEd", $str).css("color", "red");
                        $("#reportFeesShow", $str).css("color", "black");
                        $("#reportFeesShow", $str).removeClass("display-none");
                        $("#reportFeesShow", $str).on("mouseenter", function () {
                            layer.tips("修改前内容: " + (result.data.reportFees == null ? "" : result.data.reportFees ) + "&nbsp;&nbsp;(万元)</br>" + "修改人: " + result.data.logInputUser + "</br>" + "修改日期: " + result.logInputDate, '#reportFeesShow', {
                                area: ['auto', 'auto'],
                                tips: [1, '#30b5ff'],
                                time: 2000
                            });
                        });
                    } else {
                        $("#reportFees").text(result.data.reportFeesEd);
                    }
                    $(container).append($str);
                }
            });
        }
    },
    enviroMonitoringFeeEd: {  //环境监测费
        create: function (val, container) {
            var this_val = val ? val : 0;
            var str = "<div class='layui-form-item has-unit'>" +
                "<label class='layui-form-label'><span class='col-f00'></span>环境监测费</label>" +
                "<div class='layui-input-block'>" +
                    "<input type='number' id='enviroMonitoringFeeEd' step='0.0001' name='enviroMonitoringFeeEd' class='layui-input dotFourInput' value='"+dotFour(this_val)+"'>" +
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
                url: "/eia/eiaContractLog/getEiaContractLogDataMap?eiaContractLogId=" + eiaContractLogId,
                type: "POST",
                data: {},
                dataType: "json",
                success: function (result) {
                    var str = "<div class='layui-form-item'>" +
                        "<label class='layui-form-label'><span class='col-f00'></span>环境监测费</label>" +
                        "<div class='layui-input-block check-block' id='enviroMonitoringFeeEd'><span class='cost-num'>"+dotFour(this_val)+"</span><span>&nbsp;&nbsp;(万元)</span>&nbsp;&nbsp;<i class='larry-icon font20 cursor-pointer display-none' id='enviroMonitoringFeeShow'>&#xe740;</i></div>" +
                        "</div>";
                    var $str = $(str).clone();
                    if (result.data.enviroMonitoringFee == "" || result.data.enviroMonitoringFee == null) {
                        result.data.enviroMonitoringFee = 0;
                    }
                    if (result.data.enviroMonitoringFeeEd == "" || result.data.enviroMonitoringFeeEd == null) {
                        result.data.enviroMonitoringFeeEd = 0;
                    }
                    if (result.data.enviroMonitoringFee != result.data.enviroMonitoringFeeEd) {
                        $("#enviroMonitoringFeeEd", $str).css("color", "red");
                        $("#enviroMonitoringFeeShow", $str).css("color", "black");
                        $("#enviroMonitoringFeeShow", $str).removeClass("display-none");
                        $("#enviroMonitoringFeeShow", $str).on("mouseenter", function () {
                            layer.tips("修改前内容: " + (result.data.enviroMonitoringFee == null ? "" : result.data.enviroMonitoringFee ) + "&nbsp;&nbsp;(万元)</br>" + "修改人: " + result.data.logInputUser + "</br>" + "修改日期: " + result.logInputDate, '#enviroMonitoringFeeShow', {
                                area: ['auto', 'auto'],
                                tips: [1, '#30b5ff'],
                                time: 2000
                            });
                        });
                    } else {
                        $("#enviroMonitoringFeeEd").text(result.data.enviroMonitoringFeeEd);
                    }
                    $(container).append($str);
                }
            });
        }
    },
    groundWaterEd: {  //地下水专题评价费
        create: function (val, container) {
            var this_val = val ? val : 0;
            var str = "<div class='layui-form-item has-unit'>" +
                "<label class='layui-form-label'><span class='col-f00'></span>地下水专题评价费</label>" +
                "<div class='layui-input-block'>" +
                    "<input type='number' id='groundWater' step='0.0001' name='groundWater' class='layui-input dotFourInput' value='"+dotFour(this_val)+"'>" +
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
                url: "/eia/eiaContractLog/getEiaContractLogDataMap?eiaContractLogId=" + eiaContractLogId,
                type: "POST",
                data: {},
                dataType: "json",
                success: function (result) {
                    var str = "<div class='layui-form-item'>" +
                        "<label class='layui-form-label'><span class='col-f00'></span>地下水专题评价费</label>" +
                        "<div class='layui-input-block check-block' id='groundWaterEd'><span class='cost-num'>"+dotFour(this_val)+"</span><span>&nbsp;&nbsp;(万元)</span>&nbsp;&nbsp;<i class='larry-icon font20 cursor-pointer display-none' id='groundWaterShow'>&#xe740;</i></div>" +
                        "</div>";
                    var $str = $(str).clone();
                    if (result.data.groundWater == "" || result.data.groundWater == null) {
                        result.data.groundWater = 0;
                    }
                    if (result.data.groundWaterEd == "" || result.data.groundWaterEd == null) {
                        result.data.groundWaterEd = 0;
                    }
                    if (result.data.groundWater != result.data.groundWaterEd) {
                        $("#groundWaterEd", $str).css("color", "red");
                        $("#groundWaterShow", $str).css("color", "black");
                        $("#groundWaterShow", $str).removeClass("display-none");
                        $("#groundWaterShow", $str).on("mouseenter", function () {
                            layer.tips("修改前内容: " + (result.data.groundWater == null ? "" : result.data.groundWater ) + "&nbsp;&nbsp;(万元)</br>" + "修改人: " + result.data.logInputUser + "</br>" + "修改日期: " + result.logInputDate, '#groundWaterShow', {
                                area: ['auto', 'auto'],
                                tips: [1, '#30b5ff'],
                                time: 2000
                            });
                        });
                    } else {
                        $("#groundWater").text(result.data.groundWaterEd);
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
                    "<input type='number' id='expertFee'  step='0.0001' name='expertFee' class='layui-input dotFourInput' value='"+dotFour(this_val)+"'>" +
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
                url: "/eia/eiaContractLog/getEiaContractLogDataMap?eiaContractLogId=" + eiaContractLogId,
                type: "POST",
                data: {},
                dataType: "json",
                success: function (result) {
                    var str = "<div class='layui-form-item'>" +
                        "<label class='layui-form-label'><span class='col-f00'></span>专家评审费</label>" +
                        "<div class='layui-input-block check-block' id='expertFeeEd'><span class='cost-num'>"+dotFour(this_val)+"</span><span>&nbsp;&nbsp;(万元)</span>&nbsp;&nbsp;<i class='larry-icon font20 cursor-pointer display-none' id='expertFeeShow'>&#xe740;</i></div>" +
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
                        $("#expertFeeEd").text(result.data.expertFeeEd);
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
                    "<input type='number' id='otherFee' step='0.0001' name='otherFee' class='layui-input dotFourInput' value='"+dotFour(this_val)+"'>" +
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
                url: "/eia/eiaContractLog/getEiaContractLogDataMap?eiaContractLogId=" + eiaContractLogId,
                type: "POST",
                data: {},
                dataType: "json",
                success: function (result) {
                    var str = "<div class='layui-form-item'>" +
                        "<label class='layui-form-label'><span class='col-f00'></span>其他费用</label>" +
                        "<div class='layui-input-block check-block' id='otherFeeEd'><span class='cost-num'>"+dotFour(this_val)+"</span><span>&nbsp;&nbsp;(万元)</span>&nbsp;&nbsp;<i class='larry-icon font20 cursor-pointer display-none' id='otherFeeShow'>&#xe740;</i></div>" +
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
                        $("#otherFeeEd").text(result.data.otherFeeEd);
                    }
                    $(container).append($str);
                }
            });
        }
    },
    specialFeeEd: {  //仪器和劳保用品费
        create: function (val, container) {
            var this_val = val ? val : 0;
            var str = "<div class='layui-form-item has-unit'>" +
                "<label class='layui-form-label'><span class='col-f00'></span>仪器和劳保用品费</label>" +
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
                url: "/eia/eiaContractLog/getEiaContractLogDataMap?eiaContractLogId=" + eiaContractLogId,
                type: "POST",
                data: {},
                dataType: "json",
                success: function (result) {
                    var str = "<div class='layui-form-item'>" +
                        "<label class='layui-form-label'><span class='col-f00'></span>仪器和劳保用品费</label>" +
                        "<div class='layui-input-block check-block' id='specialFeeEd'><span class='cost-num'>"+dotFour(this_val)+"</span><span>&nbsp;&nbsp;(万元)</span>&nbsp;&nbsp;<i class='larry-icon font20 cursor-pointer display-none' id='specialFeeShow'>&#xe740;</i></div>" +
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
                        $("#specialFeeEd").text(result.data.specialFeeEd);
                    }
                    $(container).append($str);
                }
            });
        }
    },
    serveFeeEd: {  //服务费用
        create: function (val, container) {
            var this_val = val ? val : 0;
            var str = "<div class='layui-form-item has-unit'>" +
                "<label class='layui-form-label'><span class='col-f00'></span>服务费用</label>" +
                "<div class='layui-input-block'>" +
                    "<input type='number' id='serveFee' step='0.0001' name='serveFee' class='layui-input dotFourInput' value='"+dotFour(this_val)+"'>" +
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
                url: "/eia/eiaContractLog/getEiaContractLogDataMap?eiaContractLogId=" + eiaContractLogId,
                type: "POST",
                data: {},
                dataType: "json",
                success: function (result) {
                    var str = "<div class='layui-form-item'>" +
                        "<label class='layui-form-label'><span class='col-f00'></span>服务费用</label>" +
                        "<div class='layui-input-block check-block' id='serveFeeEd'><span class='cost-num'>" + dotFour(this_val) + "</span><span>&nbsp;&nbsp;(万元)</span>&nbsp;&nbsp;<i class='larry-icon font20 cursor-pointer display-none' id='serveFeeShow'>&#xe740;</i></div>" +
                        "</div>";
                    var $str = $(str).clone();
                    if (result.data.serveFee == "" || result.data.serveFee == null) {
                        result.data.serveFee = 0;
                    }
                    if (result.data.serveFeeEd == "" || result.data.serveFeeEd == null) {
                        result.data.serveFeeEd = 0;
                    }
                    if (result.data.serveFee != result.data.serveFeeEd) {
                        $("#serveFeeEd", $str).css("color", "red");
                        $("#serveFeeShow", $str).css("color", "black");
                        $("#serveFeeShow", $str).removeClass("display-none");
                        $("#serveFeeShow", $str).on("mouseenter", function () {
                            layer.tips("修改前内容: " + (result.data.serveFee == null ? "" : result.data.serveFee ) + "&nbsp;&nbsp;(万元)</br>" + "修改人: " + result.data.logInputUser + "</br>" + "修改日期: " + result.logInputDate, '#serveFeeShow', {
                                area: ['auto', 'auto'],
                                tips: [1, '#30b5ff'],
                                time: 2000
                            });
                        });
                    } else {
                        $("#serveFeeEd").text(result.data.serveFeeEd);
                    }
                    $(container).append($str);
                }
            });
        }
    },
    ecoDetectFeeEd: {  //生态调查费用
        create: function (val, container) {
            var this_val = val ? val : 0;
            var str = "<div class='layui-form-item has-unit'>" +
                "<label class='layui-form-label'><span class='col-f00'></span>生态调查费用</label>" +
                "<div class='layui-input-block'>" +
                    "<input type='number' id='ecoDetectFee' step='0.0001' name='ecoDetectFee' class='layui-input dotFourInput' value='"+dotFour(this_val)+"'>" +
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
                url: "/eia/eiaContractLog/getEiaContractLogDataMap?eiaContractLogId=" + eiaContractLogId,
                type: "POST",
                data: {},
                dataType: "json",
                success: function (result) {
                    var str = "<div class='layui-form-item'>" +
                        "<label class='layui-form-label'><span class='col-f00'></span>生态调查费用</label>" +
                        "<div class='layui-input-block check-block' id='ecoDetectFeeEd'><span class='cost-num'>" + dotFour(this_val) + "</span><span>&nbsp;&nbsp;(万元)</span>&nbsp;&nbsp;<i class='larry-icon font20 cursor-pointer display-none' id='ecoDetectFeeShow'>&#xe740;</i></div>" +
                        "</div>";
                    var $str = $(str).clone();
                    if (result.data.ecoDetectFee == "" || result.data.ecoDetectFee == null) {
                        result.data.ecoDetectFee = 0;
                    }
                    if (result.data.ecoDetectFeeEd == "" || result.data.ecoDetectFeeEd == null) {
                        result.data.ecoDetectFeeEd = 0;
                    }
                    if (result.data.ecoDetectFee != result.data.ecoDetectFeeEd) {
                        $("#ecoDetectFeeEd", $str).css("color", "red");
                        $("#ecoDetectFeeShow", $str).css("color", "black");
                        $("#ecoDetectFeeShow", $str).removeClass("display-none");
                        $("#ecoDetectFeeShow", $str).on("mouseenter", function () {
                            layer.tips("修改前内容: " + (result.data.ecoDetectFee == null ? "" : result.data.ecoDetectFee ) + "&nbsp;&nbsp;(万元)</br>" + "修改人: " + result.data.logInputUser + "</br>" + "修改日期: " + result.logInputDate, '#ecoDetectFeeShow', {
                                area: ['auto', 'auto'],
                                tips: [1, '#30b5ff'],
                                time: 2000
                            });
                        });
                    } else {
                        $("#ecoDetectFeeEd").text(result.data.ecoDetectFeeEd);
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
                url: "/eia/eiaContractLog/getEiaContractLogDataMap?eiaContractLogId=" + eiaContractLogId,
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
                        $("#preIssCertFeeEd").text(result.data.preIssCertFeeEd);
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
                url: "/eia/eiaContractLog/getEiaContractLogDataMap?eiaContractLogId=" + eiaContractLogId,
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
                        $("#preSurvCertFeeEd").text(result.data.preSurvCertFeeEd);
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
                url: "/eia/eiaContractLog/getEiaContractLogDataMap?eiaContractLogId=" + eiaContractLogId,
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
                        $("#certServeFeeEd").text(result.data.certServeFeeEd);
                    }
                    $(container).append($str);
                }
            });
        }
    }
};