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
    projectCode: {  //项目代码
        create: function (val, container) {
            var this_val = val ? val : "";
            var str = "<div class='layui-form-item'>" +
                "<label class='layui-form-label'>项目代码</label>" +
                "<div class='layui-input-block'>" +
                "<input type='text' id='projectCode' name='projectCode' class='layui-input' value='" + this_val + "'>" +
                "</div>" +
                "</div>";
            var $str = $(str).clone();
            $(container).append($str);
        },
        show: function (val, container) {
            var this_val = val ? val : "";
            var str = "<div class='layui-form-item'>" +
                "<label class='layui-form-label'><span class='col-f00'></span>项目代码</label>" +
                "<div class='layui-input-block check-block' id='projectCode'>" + this_val + "</div>" +
                "</div>";
            var $str = $(str).clone();
            $(container).append($str);
        }
    },
    industryType: {  //国民经济行业类型及代码
        create: function (val, container) {
            if (val) {
                var this_val = val ? val : "",
                    val_name = val.name ? val.name : "",
                    val_id = val.id ? val.id : "";
            } else {
                var this_val = "",
                    val_name = "",
                    val_id = "";
            }
            var str = "<div class='layui-form-item'>" +
                "<label class='layui-form-label'><span class='col-f00'>* </span>国民经济行业类型及代码</label>" +
                "<div class='layui-input-block'>" +
                "<input type='text' id='industryTypeDrop' name='industryTypeDrop' class='layui-input' lay-verify='required' autocomplete='off' value='" + val_name + "'>" +
                "<input type='hidden' id='industryType' name='industryType' lay-verify='required' value='" + JSON.stringify(this_val) + "'>" +
                "</div>" +
                "</div>";
            var $str = $(str).clone();
            $(container).append($str);
            //初始化下拉树 国民经济行业类型及代码
            $("#industryTypeDrop").dropDownForZ({
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
                        $("#industryType").val(str);
                        $("#industryTypeDrop").val(str);
                    }
                }
            });
            $('#industryTypeDropCode').val(val_id);

        },
        show: function (val, container) {
            var this_val = val ? val : "";
            var str = "<div class='layui-form-item'>" +
                "<label class='layui-form-label'><span class='col-f00'></span>国民经济行业类型及代码</label>" +
                "<div class='layui-input-block check-block' id='industryType'>" + this_val + "</div>" +
                "</div>";
            var $str = $(str).clone();
            $(container).append($str);
        }
    },
    environmentaType: {  //环境影响评价行业类别
        create: function (val, container) {
            if (val) {
                var this_val = val ? val : "",
                    val_name = val.name ? val.name : "",
                    val_id = val.id ? val.id : "";
            } else {
                var this_val = "",
                    val_name = "",
                    val_id = "";
            }
            var str = "<div class='layui-form-item'>" +
                "<label class='layui-form-label'><span class='col-f00'>* </span>环境影响评价行业类别</label>" +
                "<div class='layui-input-block'>" +
                "<input type='text' id='environmentaTypeDrop' name='environmentaTypeDrop' class='layui-input' lay-verify='required' autocomplete='off' value='" + val_name + "'>" +
                "<input type='hidden' id='environmentaType' name='environmentaType' lay-verify='required' value='" + JSON.stringify(this_val) + "'>" +
                "</div>" +
                "</div>";
            var $str = $(str).clone();
            $(container).append($str);
            //初始化下拉树 环境影响评价行业类别
            $("#environmentaTypeDrop").dropDownForZ({
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
                        $("#environmentaType").val(str);
                        $("#environmentaTypeDrop").val(str);
                        /*    $("#environmentaType").val(JSON.stringify(temp));*/
                    }
                }
            });
            $('#environmentaTypeDropCode').val(val_id);

        },
        show: function (val, container) {
            var this_val = val ? val : "";
            var str = "<div class='layui-form-item'>" +
                "<label class='layui-form-label'><span class='col-f00'></span>环境影响评价行业类别</label>" +
                "<div class='layui-input-block check-block' id='environmentaType'>" + this_val + "</div>" +
                "</div>";
            var $str = $(str).clone();
            $(container).append($str);
        }
    },
    productionEngineer: {  //生产工艺
        create: function (val, container) {
            var this_val = val ? val : "";
            var str = "<div class='layui-form-item'>" +
                "<label class='layui-form-label'><span class='col-f00'>* </span>生产工艺</label>" +
                "<div class='layui-input-block'>" +
                "<input type='text' id='productionEngineer' name='productionEngineer' class='layui-input' lay-verify='required' value='" + this_val + "' placeholder='（1-5个关键词逗号间隔）'>" +
                "</div>" +
                "</div>";
            var $str = $(str).clone();
            $(container).append($str);
        },
        show: function (val, container) {
            var this_val = val ? val : "";
            var str = "<div class='layui-form-item'>" +
                "<label class='layui-form-label'><span class='col-f00'></span>生产工艺</label>" +
                "<div class='layui-input-block check-block' id='productionEngineer'>" + this_val + "</div>" +
                "</div>";
            var $str = $(str).clone();
            $(container).append($str);
        }
    },
    productFunction: {  //产品功能
        create: function (val, container) {
            var this_val = val ? val : "";
            var str = "<div class='layui-form-item'>" +
                "<label class='layui-form-label'><span class='col-f00'>* </span><span class='label-txt'>产品功能</span></label>" +
                "<div class='layui-input-block'>" +
                "<input type='text' id='productFunction' name='productFunction' class='layui-input' lay-verify='required' value='" + this_val + "' placeholder='（1-5个关键词逗号间隔）'>" +
                "</div>" +
                "</div>";
            var $str = $(str).clone();
            $(container).append($str);
        },
        show: function (val, container) {
            var this_val = val ? val : "";
            var str = "<div class='layui-form-item'>" +
                "<label class='layui-form-label'><span class='col-f00'></span><span class='label-txt'>产品功能</span></label>" +
                "<div class='layui-input-block check-block' id='productFunction'>" + this_val + "</div>" +
                "</div>";
            var $str = $(str).clone();
            $(container).append($str);
        }
    },
    parkPlanning: {  //园区规划环评开展情况
        create: function (val, container) {
            var this_val = val ? val : "";
            var str = "<div class='layui-form-item'>" +
                "<label class='layui-form-label'><span class='col-f00'></span>园区规划环评开展情况</label>" +
                "<div class='layui-input-block'>" +
                "<select name='parkPlanning' id='parkPlanning' lay-search>" +
                "</select>" +
                "</div>" +
                "</div>";
            var $str = $(str).clone();
            selectOptionByVal($str, this_val);
            $(container).append($str);
        },
        show: function (val, container) {
            var this_val = val ? val : "";
            var str = "<div class='layui-form-item'>" +
                "<label class='layui-form-label'><span class='col-f00'></span>园区规划环评开展情况</label>" +
                "<div class='layui-input-block check-block' id='parkPlanning'>" + this_val + "</div>" +
                "</div>";
            var $str = $(str).clone();
            $(container).append($str);
        }
    },
    seaFileName: {  //规划环评文件名
        create: function (val, container) {
            var this_val = val ? val : "";
            var str = "<div class='layui-form-item'>" +
                "<label class='layui-form-label'><span class='col-f00'></span>规划环评文件名</label>" +
                "<div class='layui-input-block'>" +
                "<input type='text' id='seaFileName' name='seaFileName' class='layui-input' value='" + this_val + "'>" +
                "</div>" +
                "</div>";
            var $str = $(str).clone();
            $(container).append($str);
        },
        show: function (val, container) {
            var this_val = val ? val : "";
            var str = "<div class='layui-form-item'>" +
                "<label class='layui-form-label'><span class='col-f00'></span>规划环评文件名</label>" +
                "<div class='layui-input-block check-block' id='seaFileName'>" + this_val + "</div>" +
                "</div>";
            var $str = $(str).clone();
            $(container).append($str);
        }
    },
    seaReviewOrg: {  //规划环评审查机关
        create: function (val, container) {


            var this_val = val ? val : "";
            var str = "<div class='layui-form-item'>" +
                "<label class='layui-form-label'><span class='col-f00'></span>规划环评审查机关</label>" +
                "<div class='layui-input-block'>" +
                "<select name='seaReviewOrg' id='seaReviewOrg' lay-search>" +
                "</select>" +
                "</div>" +
                "</div>";
            var $str = $(str).clone();
            $(container).append($str);

            //渲染主管部门select选项
            $.ajax({
                url: '/eia/eiaProject/getCompetentDept',
                type: "POST",
                data: {},
                dataType: "json",
                async: true,
                success: function (data) {
                    var seaReviewOrg = $('#seaReviewOrg');
                    seaReviewOrg.append('<option value="">选择审批部门</option>');
                    for (var name in data) {
                        var str = "<option value='" + data[name].codeDesc + "'>" + data[name].codeDesc + "</option>";
                        $('#seaReviewOrg').append(str);
                    }
                    selectOptionByVal($str, this_val);
                    layui.form.render('select');
                    layui.form.render('radio');
                }
            });
        },
        show: function (val, container) {
            var this_val = val ? val : "";
            var str = "<div class='layui-form-item'>" +
                "<label class='layui-form-label'><span class='col-f00'></span>规划环评审查机关</label>" +
                "<div class='layui-input-block check-block' id='seaReviewOrg'>" + this_val + "</div>" +
                "</div>";
            var $str = $(str).clone();
            $(container).append($str);
        }
    },
    seaReviewNo: {  //规划环评审查意见文号
        create: function (val, container) {
            var this_val = val ? val : "";
            var str = "<div class='layui-form-item'>" +
                "<label class='layui-form-label'><span class='col-f00'></span>规划环评审查意见文号</label>" +
                "<div class='layui-input-block'>" +
                "<input type='text' id='seaReviewNo' name='seaReviewNo' class='layui-input' value='" + this_val + "'>" +
                "</div>" +
                "</div>";
            var $str = $(str).clone();
            $(container).append($str);
        },
        show: function (val, container) {
            var this_val = val ? val : "";
            var str = "<div class='layui-form-item'>" +
                "<label class='layui-form-label'><span class='col-f00'></span>规划环评审查意见文号</label>" +
                "<div class='layui-input-block check-block' id='seaReviewNo'>" + this_val + "</div>" +
                "</div>";
            var $str = $(str).clone();
            $(container).append($str);
        }
    },
    projectInvestMoney: {  //总投资
        create: function (val, container) {
            var this_val = val ? val : 0;
            var str = "<div class='layui-form-item has-unit'>" +
                "<label class='layui-form-label'><span class='col-f00'></span>总投资</label>" +
                "<div class='layui-input-block'>" +
                "<input type='number' id='projectInvestMoney' step='0.0001' name='projectInvestMoney' class='layui-input dotFourInput' value='" + dotFour(this_val) + "'>" +
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
            var str = "<div class='layui-form-item'>" +
                "<label class='layui-form-label'><span class='col-f00'></span>总投资</label>" +
                "<div class='layui-input-block check-block' id='projectInvestMoney'><span class='cost-num'>" + dotFour(this_val) + "</span><span>&nbsp;&nbsp;(万元)</span></div>" +
                "</div>";
            var $str = $(str).clone();
            $(container).append($str);
        }
    },
    contentScale: {  //建设内容、规模
        create: function (val, container) {
            var this_val = val ? val : "";
            var str = "<div class='layui-form-item'>" +
                "<label class='layui-form-label'><span class='col-f00'></span>建设内容、规模</label>" +
                "<div class='layui-input-block'>" +
                "<input type='text' id='contentScale' name='contentScale' class='layui-input' value='" + this_val + "'>" +
                "</div>" +
                "</div>";
            var $str = $(str).clone();
            $(container).append($str);
        },
        show: function (val, container) {
            var this_val = val ? val : "";
            var str = "<div class='layui-form-item'>" +
                "<label class='layui-form-label'><span class='col-f00'></span>建设内容、规模</label>" +
                "<div class='layui-input-block check-block' id='contentScale'>" + this_val + "</div>" +
                "</div>";
            var $str = $(str).clone();
            $(container).append($str);
        }
    },
    natureConstructio: {  //建设性质
        create: function (val, container) {
            var this_val = val ? val : "";
            var str = "<div class='layui-form-item'>" +
                "<label class='layui-form-label'><span class='col-f00'></span>建设性质</label>" +
                "<div class='layui-input-block'>" +
                "<select name='natureConstructio' id='natureConstructio' lay-search>" +
                "</select>" +
                "</div>" +
                "</div>";
            var $str = $(str).clone();
            $(container).append($str);
        },
        show: function (val, container) {
            var this_val = val ? val : "";
            var str = "<div class='layui-form-item'>" +
                "<label class='layui-form-label'><span class='col-f00'></span>建设性质</label>" +
                "<div class='layui-input-block check-block' id='natureConstructio'>" + this_val + "</div>" +
                "</div>";
            var $str = $(str).clone();
            $(container).append($str);
        }
    },
    planArea: {  //规划面积
        create: function (val, container) {
            var this_val = val ? val : 0;
            var str = "<div class='layui-form-item has-unit'>" +
                "<label class='layui-form-label'><span class='col-f00'></span>规划面积</label>" +
                "<div class='layui-input-block'>" +
                "<input type='number' id='planArea' name='planArea' class='layui-input' value='" + this_val + "'>" +
                "</div>" +
                "<div class='tag-right'><span class='tag-unit'>平方米</span></div>" +
                "</div>";
            var $str = $(str).clone();
            $(container).append($str);
        },
        show: function (val, container) {
            var this_val = val ? val : 0;
            var str = "<div class='layui-form-item'>" +
                "<label class='layui-form-label'><span class='col-f00'></span>规划面积</label>" +
                "<div class='layui-input-block check-block' id='natureConstructio'><span class='cost-num'>" + this_val + "</span><span>&nbsp;&nbsp;(平方米)</span></div>" +
                "</div>";
            var $str = $(str).clone();
            $(container).append($str);
        }
    },
    projectMemo: {  //基本情况介绍
        create: function (val, container) {
            var this_val = val ? val : "";
            console.log(this_val);
            var str = "<div class='layui-form-item'>" +
                "<label class='layui-form-label'><span class='col-f00'></span>基本情况介绍</label>" +
                "<div class='layui-input-block'>" +
                " <textarea name='projectMemo' id='projectMemo' placeholder='请输入内容' class='layui-textarea' value=''>" + this_val + "</textarea>" +
                "</div>" +
                "</div>";
            var $str = $(str).clone();
            $(container).append($str);
        },
        show: function (val, container) {
            var this_val = val ? val : "";
            var str = "<div class='layui-form-item'>" +
                "<label class='layui-form-label'><span class='col-f00'></span>基本情况介绍</label>" +
                "<div class='layui-input-block check-block' id='projectMemo'>" + this_val + "</div>" +
                "</div>";
            var $str = $(str).clone();
            $(container).append($str);
        }
    },
    publishYear: {    //发行年限
        create: function (val, container) {
            var this_val = val ? val : 0;
            var str = "<div class='layui-form-item has-unit'>" +
                "<label class='layui-form-label'><span class='col-f00'></span>发行年限</label>" +
                "<div class='layui-input-block'>" +
                "<input type='number' id='publishYear' name='publishYear' class='layui-input integer-input' value='"+integerForce(this_val)+"'>" +
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
            var str = "<div class='layui-form-item'>" +
                "<label class='layui-form-label'><span class='col-f00'></span>发行年限</label>" +
                "<div class='layui-input-block check-block' id='publishYear'><span class='cost-num'>"+integerForce(this_val)+"</span><span>&nbsp;&nbsp;(年)</span></div>" +
                "</div>";
            var $str = $(str).clone();
            $(container).append($str);
        }
    },
    publishMoney: {  //发行总额
        create: function (val, container) {
            var this_val = val ? val : 0;
            var str = "<div class='layui-form-item has-unit'>" +
                "<label class='layui-form-label'><span class='col-f00'></span>发行总额</label>" +
                "<div class='layui-input-block'>" +
                "<input type='number' id='publishMoney'  step='0.0001' name='publishMoney' class='layui-input dotFourInput' value='"+dotFour(this_val)+"'>" +
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
                "<label class='layui-form-label'><span class='col-f00'></span>发行总额</label>" +
                "<div class='layui-input-block check-block' id='publishMoney'><span class='cost-num'>"+dotFour(this_val)+"</span><span>&nbsp;&nbsp;(万元)</span></div>" +
                "</div>";
            var $str = $(str).clone();
            $(container).append($str);
        }
    },
    bondCode: {  //债券代码
        create: function (val, container) {
            var this_val = val ? val : "";
            var str = "<div class='layui-form-item'>" +
                "<label class='layui-form-label'><span class='col-f00'></span>债券代码</label>" +
                "<div class='layui-input-block'>" +
                "<input type='text' id='bondCode' name='bondCode' class='layui-input' value='"+this_val+"'>" +
                "</div>" +
                "</div>";
            var $str = $(str).clone();
            $(container).append($str);
        },
        show: function (val, container) {
            var this_val = val ? val : "";
            var str = "<div class='layui-form-item'>" +
                "<label class='layui-form-label'><span class='col-f00'></span>债券代码</label>" +
                "<div class='layui-input-block check-block' id='bondCode'>"+this_val+"</div>" +
                "</div>";
            var $str = $(str).clone();
            $(container).append($str);
        }
    },
    bondType: {  //债券类型
        create: function (val, container) {
            var this_val = val ? val : "";
            var str = "<div class='layui-form-item'>" +
                "<label class='layui-form-label'><span class='col-f00'></span>债券类型</label>" +
                "<div class='layui-input-block'>" +
                "<input type='text' id='bondType' name='bondType' class='layui-input' value='"+this_val+"'>" +
                "</div>" +
                "</div>";
            var $str = $(str).clone();
            $(container).append($str);
        },
        show: function (val, container) {
            var this_val = val ? val : "";
            var str = "<div class='layui-form-item'>" +
                "<label class='layui-form-label'><span class='col-f00'></span>债券类型</label>" +
                "<div class='layui-input-block check-block' id='bondType'>"+this_val+"</div>" +
                "</div>";
            var $str = $(str).clone();
            $(container).append($str);
        }
    },
    bondName: {  //债券名称
        create: function (val, container) {
            var this_val = val ? val : "";
            var str = "<div class='layui-form-item'>" +
                "<label class='layui-form-label'><span class='col-f00'></span>债券名称</label>" +
                "<div class='layui-input-block'>" +
                "<input type='text' id='bondName' name='bondName' class='layui-input' value='"+this_val+"'>" +
                "</div>" +
                "</div>";
            var $str = $(str).clone();
            $(container).append($str);
        },
        show: function (val, container) {
            var this_val = val ? val : "";
            var str = "<div class='layui-form-item'>" +
                "<label class='layui-form-label'><span class='col-f00'></span>债券名称</label>" +
                "<div class='layui-input-block check-block' id='bondName'>"+this_val+"</div>" +
                "</div>";
            var $str = $(str).clone();
            $(container).append($str);
        }
    },
    ifCompFile: {  //拟投资项目是否具有完备的合规性文件
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
                "<input type='radio' name='ifCompFile' value='true' title='是' "+yes_val+">"+
                "<input type='radio' name='ifCompFile' value='false' title='否' "+no_val+">"+
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
            var str = "<div class='layui-form-item'>" +
                "<label class='layui-form-label'><span class='col-f00'></span>拟投资项目是否具有完备的合规性文件</label>" +
                "<div class='layui-input-block check-block' id='ifCompFile'>"+this_val+"</div>" +
                "</div>";
            var $str = $(str).clone();
            $(container).append($str);
        }
    },
    ifIndPolicy: {  //拟投资项目是否符合产业（行业）政策
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
                "<input type='radio' name='ifIndPolicy' value='true' title='是' "+yes_val+">"+
                "<input type='radio' name='ifIndPolicy' value='false' title='否' "+no_val+">"+
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
            var str = "<div class='layui-form-item'>" +
                "<label class='layui-form-label'><span class='col-f00'></span>拟投资项目是否符合产业（行业）政策</label>" +
                "<div class='layui-input-block check-block' id='ifIndPolicy'>"+this_val+"</div>" +
                "</div>";
            var $str = $(str).clone();
            $(container).append($str);
        }
    },
    ifGuarFund: {  //拟投资项目能否保证资金专款专用
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
                "<input type='radio' name='ifGuarFund' value='true' title='是' "+yes_val+">"+
                "<input type='radio' name='ifGuarFund' value='false' title='否' "+no_val+">"+
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
            var str = "<div class='layui-form-item'>" +
                "<label class='layui-form-label'><span class='col-f00'></span>拟投资项目能否保证资金专款专用</label>" +
                "<div class='layui-input-block check-block' id='ifGuarFund'>"+this_val+"</div>" +
                "</div>";
            var $str = $(str).clone();
            $(container).append($str);
        }
    },
    ifPublishComplete: {  //拟投资项目信息披露制度是否健全
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
                "<input type='radio' name='ifPublishComplete' value='true' title='是' "+yes_val+">"+
                "<input type='radio' name='ifPublishComplete' value='false' title='否' "+no_val+">"+
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
            var str = "<div class='layui-form-item'>" +
                "<label class='layui-form-label'><span class='col-f00'></span>拟投资项目信息披露制度是否健全</label>" +
                "<div class='layui-input-block check-block' id='ifPublishComplete'>"+this_val+"</div>" +
                "</div>";
            var $str = $(str).clone();
            $(container).append($str);
        }
    },
    ifGreenCatalog: {  //拟投资项目是否符合《绿色债券支持项目目录》
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
                "<input type='radio' name='ifGreenCatalog' value='true' title='是' "+yes_val+">"+
                "<input type='radio' name='ifGreenCatalog' value='false' title='否' "+no_val+">"+
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
            var str = "<div class='layui-form-item'>" +
                "<label class='layui-form-label'><span class='col-f00'></span>拟投资项目是否符合《绿色债券支持项目目录》</label>" +
                "<div class='layui-input-block check-block' id='ifGreenCatalog'>"+this_val+"</div>" +
                "</div>";
            var $str = $(str).clone();
            $(container).append($str);
        }
    }

};