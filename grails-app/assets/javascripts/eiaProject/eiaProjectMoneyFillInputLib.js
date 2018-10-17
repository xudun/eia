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

var moneyFillInputs = {
    projectComfee: {  //项目编制费
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
            var str = "<div class='layui-form-item'>" +
                "<label class='layui-form-label'><span class='col-f00'></span>项目编制费</label>" +
                "<div class='layui-input-block check-block' id='projectComfee'><span class='cost-num'>"+dotFour(this_val)+"</span><span>&nbsp;&nbsp;(万元)</span></div>" +
                "</div>";
            var $str = $(str).clone();
            $(container).append($str);
        }
    },
    environmentalFee: {  //环境监测费
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
            var str = "<div class='layui-form-item'>" +
                "<label class='layui-form-label'><span class='col-f00'></span>环境监测费</label>" +
                "<div class='layui-input-block check-block' id='environmentalFee'><span class='cost-num'>"+dotFour(this_val)+"</span><span>&nbsp;&nbsp;(万元)</span></div>" +
                "</div>";
            var $str = $(str).clone();
            $(container).append($str);
        }
    },
    groundwaterFee: {  //地下水专题评价费
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
            var str = "<div class='layui-form-item'>" +
                "<label class='layui-form-label'><span class='col-f00'></span>地下水专题评价费</label>" +
                "<div class='layui-input-block check-block' id='groundwaterFee'><span class='cost-num'>"+dotFour(this_val)+"</span><span>&nbsp;&nbsp;(万元)</span></div>" +
                "</div>";
            var $str = $(str).clone();
            $(container).append($str);
        }
    },
    expertFee: {  //专家评审费
        create: function (val, container) {
            var this_val = val ? val : 0;
            var str = "<div class='layui-form-item has-unit'>" +
                "<label class='layui-form-label'><span class='col-f00'></span>专家评审费</label>" +
                "<div class='layui-input-block'>" +
                    "<input type='number' id='expertFee' step='0.0001' name='expertFee' class='layui-input dotFourInput' value='"+dotFour(this_val)+"'>" +
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
            var str = "<div class='layui-form-item'>" +
                "<label class='layui-form-label'><span class='col-f00'></span>专家评审费</label>" +
                "<div class='layui-input-block check-block' id='expertFee'><span class='cost-num'>"+dotFour(this_val)+"</span><span>&nbsp;&nbsp;(万元)</span></div>" +
                "</div>";
            var $str = $(str).clone();
            $(container).append($str);
        }
    },
    otherFee: {  //其他费用
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
            var str = "<div class='layui-form-item'>" +
                "<label class='layui-form-label'><span class='col-f00'></span>其他费用</label>" +
                "<div class='layui-input-block check-block' id='otherFee'><span class='cost-num'>"+dotFour(this_val)+"</span><span>&nbsp;&nbsp;(万元)</span></div>" +
                "</div>";
            var $str = $(str).clone();
            $(container).append($str);
        }
    },
    specialFee: {  //仪器和劳保用品
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
            var str = "<div class='layui-form-item'>" +
                "<label class='layui-form-label'><span class='col-f00'></span>仪器和劳保用品</label>" +
                "<div class='layui-input-block check-block' id='specialFee'><span class='cost-num'>"+dotFour(this_val)+"</span><span>&nbsp;&nbsp;(万元)</span></div>" +
                "</div>";
            var $str = $(str).clone();
            $(container).append($str);
        }
    },
    detectFee: {  //项目调查费
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
            var str = "<div class='layui-form-item'>" +
                "<label class='layui-form-label'><span class='col-f00'></span>项目调查费</label>" +
                "<div class='layui-input-block check-block' id='detectFee'><span class='cost-num'>"+dotFour(this_val)+"</span><span>&nbsp;&nbsp;(万元)</span></div>" +
                "</div>";
            var $str = $(str).clone();
            $(container).append($str);
        }
    },
    projectMoney: {  //项目金额
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
            var str = "<div class='layui-form-item'>" +
                "<label class='layui-form-label'><span class='col-f00'></span>项目金额</label>" +
                "<div class='layui-input-block check-block' id='projectMoney'><span class='cost-num'>"+dotFour(this_val)+"</span><span>&nbsp;&nbsp;(万元)</span></div>" +
                "</div>";
            var $str = $(str).clone();
            $(container).append($str);
        }
    },
    preIssCertFee: {  //发行前认证费
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
            var str = "<div class='layui-form-item'>" +
                "<label class='layui-form-label'><span class='col-f00'></span>发行前认证费</label>" +
                "<div class='layui-input-block check-block' id='preIssCertFee'><span class='cost-num'>"+dotFour(this_val)+"</span><span>&nbsp;&nbsp;(万元)</span></div>" +
                "</div>";
            var $str = $(str).clone();
            $(container).append($str);
        }
    },
    preSurvCertFee: {  //存续期认证费
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
            var str = "<div class='layui-form-item'>" +
                "<label class='layui-form-label'><span class='col-f00'></span>存续期认证费</label>" +
                "<div class='layui-input-block check-block' id='preSurvCertFee'><span class='cost-num'>"+dotFour(this_val)+"</span><span>&nbsp;&nbsp;(万元)</span></div>" +
                "</div>";
            var $str = $(str).clone();
            $(container).append($str);
        }
    },
    certServeFee: {  //认证服务费
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
            var str = "<div class='layui-form-item'>" +
                "<label class='layui-form-label'><span class='col-f00'></span>认证服务费</label>" +
                "<div class='layui-input-block check-block' id='certServeFee'><span class='cost-num'>"+dotFour(this_val)+"</span><span>&nbsp;&nbsp;(万元)</span></div>" +
                "</div>";
            var $str = $(str).clone();
            $(container).append($str);
        }
    }

};