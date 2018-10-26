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
//设置select指定项选中
var selectOptionByVal = function ($select, val) {
    var opt_arr = $('option',$($select));
    for(var i=0; i<opt_arr.length; i++){
        if($(opt_arr[i]).val() == val){
            $(opt_arr[i]).prop("selected",true);
            break;
        }
    }
};

var inputs = {
    regionName: {  //所属区域
        create: function (val, container) {
            var this_val = val ? val : "",
                val_name = val.name ? val.name : "",
                val_id = val.id ? val.id : "";
            var str = "<div class='layui-form-item'>" +
                "<label class='layui-form-label'><span class='col-f00'>* </span>所属区域</label>" +
                "<div class='layui-input-block'>" +
                "<input type='text' id='regionNameDrop' name='regionNameDrop' class='layui-input' lay-verify='required' readonly value='" + val_name + "'>" +
                "<input type='hidden' id='regionName' name='regionName' lay-verify='required' value='" + JSON.stringify(this_val) + "'>" +
                "</div>" +
                "</div>";
            var $str = $(str).clone();
            $(container).append($str);
            //初始化下拉树 所属区域
            $("#regionNameDrop").dropDownForZ({
                url: '../../data/regionTree.json',
                width: '99%',
                height: '350px',
                disableParent: true,
                selecedSuccess: function (data) {  //选中回调
                    if (!data.isParent) {
                        var temp = {
                            id: data.id,
                            name: data.name
                        };
                        $("#regionName").val(JSON.stringify(temp));
                    }
                }
            });
            $('#regionNameDropCode').val(val_id);
        },
        show: function (val, container) {
            var this_val = val ? val : "";
            var str = "<div class='layui-form-item'>" +
                "<label class='layui-form-label'><span class='col-f00'></span>所属区域</label>" +
                "<div class='layui-input-block check-block' id='regionName'>" + this_val + "</div>" +
                "</div>";
            var $str = $(str).clone();
            $(container).append($str);
        }
    },
    coordinateE: {  //地理坐标 E
        create: function (val, container) {
            var this_val = val ? val : "";
            var str = "<div class='layui-form-item'>" +
                "                    <label class='layui-form-label'><span class='col-f00'></span>地理坐标 E</label>" +
                "                    <div class='layui-input-block'>" +
                "                        <input type='text' id='coordinateE' name='coordinateE' class='layui-input' value='" + this_val + "'>" +
                "                    </div>" +
                "                </div>";
            var $str = $(str).clone();
            $(container).append($str);
        },
        show: function (val, container) {
            var this_val = val ? val : "";
            var str = "<div class='layui-form-item'>" +
                "<label class='layui-form-label'><span class='col-f00'></span>地理坐标 E</label>" +
                "<div class='layui-input-block check-block' id='coordinateE'>" + this_val + "</div>" +
                "</div>";
            var $str = $(str).clone();
            $(container).append($str);
        }
    },
    coordinateN: {  //地理坐标 N
        create: function (val, container) {
            var this_val = val ? val : "";
            var str = "<div class='layui-form-item'>" +
                "                    <label class='layui-form-label'><span class='col-f00'></span>地理坐标 N</label>" +
                "                    <div class='layui-input-block'>" +
                "                        <input type='text' id='coordinateN' name='coordinateN' class='layui-input' value='" + this_val + "'>" +
                "                    </div>" +
                "                </div>";
            var $str = $(str).clone();
            $(container).append($str);
        },
        show: function (val, container) {
            var this_val = val ? val : "";
            var str = "<div class='layui-form-item'>" +
                "<label class='layui-form-label'><span class='col-f00'></span>地理坐标 N</label>" +
                "<div class='layui-input-block check-block' id='coordinateN'>" + this_val + "</div>" +
                "</div>";
            var $str = $(str).clone();
            $(container).append($str);
        }
    },
    handAbility: {  //处理能力
        create: function (val, container) {
            var this_val = val ? val : "";
            var str = "<div class='layui-form-item'>" +
                "                    <label class='layui-form-label'><span class='col-f00'></span>处理能力</label>" +
                "                    <div class='layui-input-block'>" +
                "                        <input type='text' id='handAbility' name='handAbility' class='layui-input' value='" + this_val + "'>" +
                "                    </div>" +
                "                </div>";
            var $str = $(str).clone();
            $(container).append($str);
        },
        show: function (val, container) {
            var this_val = val ? val : "";
            var str = "<div class='layui-form-item'>" +
                "<label class='layui-form-label'><span class='col-f00'></span>处理能力</label>" +
                "<div class='layui-input-block check-block' id='handAbility'>" + this_val + "</div>" +
                "</div>";
            var $str = $(str).clone();
            $(container).append($str);
        }
    },
    treatProcess: {  //处理工艺
        create: function (val, container) {
            var this_val = val ? val : "";
            var str = "<div class='layui-form-item'>" +
                "                    <label class='layui-form-label'><span class='col-f00'></span>处理工艺</label>" +
                "                    <div class='layui-input-block'>" +
                "                        <input type='text' id='treatProcess' name='treatProcess' class='layui-input' value='" + this_val + "'>" +
                "                    </div>" +
                "                </div>";
            var $str = $(str).clone();
            $(container).append($str);
        },
        show: function (val, container) {
            var this_val = val ? val : "";
            var str = "<div class='layui-form-item'>" +
                "<label class='layui-form-label'><span class='col-f00'></span>处理工艺</label>" +
                "<div class='layui-input-block check-block' id='treatProcess'>" + this_val + "</div>" +
                "</div>";
            var $str = $(str).clone();
            $(container).append($str);
        }
    },
    execStandard: {  //执行标准
        create: function (val, container) {
            var this_val = val ? val : "";
            var str = "<div class='layui-form-item'>" +
                "                    <label class='layui-form-label'><span class='col-f00'></span>执行标准</label>" +
                "                    <div class='layui-input-block'>" +
                "                        <input type='text' id='execStandard' name='execStandard' class='layui-input' value='" + this_val + "'>" +
                "                    </div>" +
                "                </div>";
            var $str = $(str).clone();
            $(container).append($str);
        },
        show: function (val, container) {
            var this_val = val ? val : "";
            var str = "<div class='layui-form-item'>" +
                "<label class='layui-form-label'><span class='col-f00'></span>执行标准</label>" +
                "<div class='layui-input-block check-block' id='execStandard'>" + this_val + "</div>" +
                "</div>";
            var $str = $(str).clone();
            $(container).append($str);
        }
    },
    projectMemo: {  //项目基本情况说明
        create: function (val, container) {
            var this_val = val ? val : "";
            var str = "<div class='layui-form-item'>" +
                "                    <label class='layui-form-label'><span class='col-f00'></span>项目基本情况说明</label>" +
                "                    <div class='layui-input-block'>" +
                "                        <textarea name='projectMemo' id='projectMemo' placeholder='请输入内容' class='layui-textarea' value=''>" + this_val + "</textarea>" +
                "                    </div>" +
                "                </div>";
            var $str = $(str).clone();
            $(container).append($str);
        },
        show: function (val, container) {
            var this_val = val ? val : "";
            var str = "<div class='layui-form-item'>" +
                "<label class='layui-form-label'><span class='col-f00'></span>项目基本情况说明</label>" +
                "<div class='layui-input-block check-block' id='projectMemo'>" + this_val + "</div>" +
                "</div>";
            var $str = $(str).clone();
            $(container).append($str);
        }
    },
    eiaUnit: {  //环评单位
        create: function (val, container) {
            var this_val = val ? val : "";
            var str = "<div class='layui-form-item'>" +
                "                    <label class='layui-form-label'><span class='col-f00'></span>环评单位</label>" +
                "                    <div class='layui-input-block'>" +
                "                        <input type='text' id='eiaUnit' name='eiaUnit' class='layui-input' value='" + this_val + "'>" +
                "                    </div>" +
                "                </div>";
            var $str = $(str).clone();
            $(container).append($str);
        },
        show: function (val, container) {
            var this_val = val ? val : "";
            var str = "<div class='layui-form-item'>" +
                "<label class='layui-form-label'><span class='col-f00'></span>环评单位</label>" +
                "<div class='layui-input-block check-block' id='eiaUnit'>" + this_val + "</div>" +
                "</div>";
            var $str = $(str).clone();
            $(container).append($str);
        }
    },
    approveNo: {  //审批文号
        create: function (val, container) {
            var this_val = val ? val : "";
            var str = "<div class='layui-form-item'>" +
                "                    <label class='layui-form-label'><span class='col-f00'></span>审批文号</label>" +
                "                    <div class='layui-input-block'>" +
                "                        <input type='text' id='approveNo' name='approveNo' class='layui-input' value='" + this_val + "'>" +
                "                    </div>" +
                "                </div>";
            var $str = $(str).clone();
            $(container).append($str);
        },
        show: function (val, container) {
            var this_val = val ? val : "";
            var str = "<div class='layui-form-item'>" +
                "<label class='layui-form-label'><span class='col-f00'></span>审批文号</label>" +
                "<div class='layui-input-block check-block' id='approveNo'>" + this_val + "</div>" +
                "</div>";
            var $str = $(str).clone();
            $(container).append($str);
        }
    },
    approveDept: {  //审批部门
        create: function (val, container) {
            var this_val = val ? val : "";
            var str = "<div class='layui-form-item'>" +
                "                    <label class='layui-form-label'><span class='col-f00'></span>审批部门</label>" +
                "                    <div class='layui-input-block'>" +
                "                        <input type='text' id='approveDept' name='approveDept' class='layui-input' value='" + this_val + "'>" +
                "                    </div>" +
                "                </div>";
            var $str = $(str).clone();
            $(container).append($str);
        },
        show: function (val, container) {
            var this_val = val ? val : "";
            var str = "<div class='layui-form-item'>" +
                "<label class='layui-form-label'><span class='col-f00'></span>审批部门</label>" +
                "<div class='layui-input-block check-block' id='approveDept'>" + this_val + "</div>" +
                "</div>";
            var $str = $(str).clone();
            $(container).append($str);
        }
    },
    planType: {//声功能区规划类别
        create: function (val, container) {
            var this_val = val ? val : "";
            var str = "<div class='layui-form-item'>" +
                "                    <label class='layui-form-label'><span class='col-f00'></span>规划类别</label>" +
                "                    <div class='layui-input-block'>" +
                "                        <select name='planType' id='planType' lay-filter='planType'>" +
                "                       <option value=''></option>" +
                "                       <option value='一类声功能区划'>一类声功能区划</option>" +
                "                       <option value='二类声功能区划'>二类声功能区划</option>" +
                "                       <option value='三类声功能区划'>三类声功能区划</option>" +
                "                       <option value='四类声功能区划'>四类声功能区划</option>" +
                "                       </select>" +
                "                    </div>" +
                "                </div>";

            var $str = $(str).clone();
            $(container).append($str);
            selectOptionByVal($("#planType"),this_val);
        },
        show: function (val, container) {
            var this_val = val ? val : "";
            var str = "<div class='layui-form-item'>" +
                "<label class='layui-form-label'><span class='col-f00'></span>规划类别</label>" +
                "<div class='layui-input-block check-block' id='approveDept'>" + this_val + "</div>" +
                "</div>";
            var $str = $(str).clone();
            $(container).append($str);
        }
    }

};