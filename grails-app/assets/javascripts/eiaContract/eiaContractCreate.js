layui.use(['jquery', 'layer', 'form', 'laydate'], function () {
    var $ = layui.jquery,
        form = layui.form,
        laydate = layui.laydate,
        layer = layui.layer;

    var eiaContractId = $('#contractId').val();
    var $contractNo = $('#contractNo'),
        $contactName = $('#contactName'),
        $contractTypeDrop = $('#contractTypeDrop'),
        $contractType = $('#contractType'),
        $contractTypeStr = $('#contractTypeStr'),
        $contractUse = $('#contractUse'),
        $contractMoney = $('#contractMoney'),
        $contractDate = $('#contractDate'),
        $eiaClientName = $('#eiaClientName'),
        $clientAddress = $('#clientAddress'),
        $contractName = $('#contractName'),
        $contactPhone = $('#contactPhone'),
        $taskId = $('#taskId'),
        $province = $('#province'),
        $ownerClientName = $('#ownerClientName'),
        $ownerClientAddress = $('#ownerClientAddress'),
        $ownerContactName = $('#ownerContactName'),
        $ownerContactPhone = $('#ownerContactPhone'),
        $contractTrust = $('#contractTrust');

    //工具函数：
    // 在url中获取指定参数值
    var getParamFromUrl = function (url, param) {
        if (url.indexOf('?') !== -1) {
            var params = url.split("?")[1].split('&');
            for (var i = 0; i < params.length; i++) {
                if (params[i].indexOf(param) !== -1) {
                    return params[i].replace(param + "=", "");
                }
            }
            // console.log('该url中无参数'+param);
        } else {
            // console.log('该url中无参数');
        }
    };
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

    //页面类型：0:新建 ； 1：编辑
    var pageType = getParamFromUrl(document.location.href, "pageType");

    //设置页面名称
    var $pageTitle = $('.pageTitle');
    pageType == 0 ? $pageTitle.html('新增合同') : $pageTitle.html('编辑合同');

    //金额hover提示
    $('.show-tips').hover(function () {
        layer.tips('选择合同类型后即可填写合同金额', '#tips',{
            area: ['auto', 'auto'],
            tips: [1, '#30b5ff'],
            time: 2000
        });
    }, function () {
        var index = layer.tips();
        layer.close(index);
    });

    //重置按钮显隐
    pageType == 0 ? "" : $('.resetBtn').hide();

    //编辑时回显数据
    if (pageType == 1) {
        //金额可填写
        $('.fillMoneyBtn').css("display", 'block');
        $('.show-tips').css("display", 'none');
        var eiaClinetId = $("#eiaClientId").val();
        if (eiaClinetId) {
            $('.selectContactBtn').css("display", 'block');
        } else {
            $('.selectContactBtn').css("display", 'none');
        }
        var ownerClinetId = $("#ownerClientId").val();
        if (ownerClinetId) {
            $('.selectOwnerContactBtn').css("display", 'block');
        } else {
            $('.selectOwnerContactBtn').css("display", 'none');
        }
    } else {
        $('.selectContactBtn').css("display", 'none');
        $('.selectOwnerContactBtn').css("display", 'none');
    }

    var eiaContractId = $('#contractId').val();
    $.ajax({
        url: "/eia/eiaContract/eiaContractDataMap?eiaContractId=" + eiaContractId,
        type: "POST",
        data: {},
        dataType: "json",
        success: function (data) {
            if (data.data) {
                console.log(data);
                $contractNo.val(data.data.contractNo);
                $contractName.val(data.data.contractName);
                $contactName.val(data.data.contactName);
                $contractTypeDrop.val(data.data.contractType);
                $contractTypeStr.val(data.data.contractTypeCode);
                $contractMoney.val(data.data.contractMoney);
                $contractDate.val(data.contractDate)
                $eiaClientName.val(data.data.eiaClientName);
                $clientAddress.val(data.data.clientAddress);
                $contactPhone.val(data.data.contactPhone);
                $ownerClientName.val(data.data.ownerClientName);
                $ownerClientAddress.val(data.data.ownerClientAddress);
                $ownerContactName.val(data.data.ownerContactName);
                $ownerContactPhone.val(data.data.ownerContactPhone);
                $contractTrust.val(data.data.contractTrust);
                var staffId = $('#staffId').val();
                if (staffId != data.data.inputUserId) {
                    $('.saveBtn').addClass("display-none");
                }
                var contractTrust = data.data.contractTrust;
                if (contractTrust == "联合赤道") {
                    $("#lhcd").prop("checked", true);
                    $("#lhtz").prop("checked", false);
                } else if (contractTrust == "联合泰泽") {
                    $("#lhcd").prop("checked", false);
                    $("#lhtz").prop("checked", true);
                }

                $("#reportFees").val(data.data.reportFees);
                $("#enviroMonitoringFee").val(data.data.enviroMonitoringFee);
                $("#expertFee").val(data.data.expertFee);
                $("#groundWater").val(data.data.groundWater);
                $("#otherFee").val(data.data.otherFee);
                $("#specialFee").val(data.data.specialFee);
                $("#serveFee").val(data.data.serveFee);
                $("#ecoDetectFee").val(data.data.ecoDetectFee);
                $("#preIssCertFee").val(data.data.preIssCertFee);
                $("#preSurvCertFee").val(data.data.preSurvCertFee);
                $("#certServeFee").val(data.data.certServeFee);

                selectOptionByVal($contractUse, data.data.contractUse);
                selectOptionByVal($taskId, data.data.taskId);
                selectOptionByVal($province, data.data.province);
                form.render('select');
            }
            //渲染业务选项
            $.ajax({
                url: "/eia/eiaTask/getEiaTaskName",
                type: "POST",
                data: {},
                dataType: "json",
                disableParent: true,
                success: function (names) {
                    var str = "";
                    for (var i = 0; i < names.length; i++) {
                        if (data.data) {
                            if (data.data.taskNo == names[i].name) {
                                str = "<option value='" + names[i].id + "' selected>" + names[i].taskName + "</option>";
                            } else {
                                str = "<option value='" + names[i].id + "'>" + names[i].taskName + "</option>";
                            }
                        } else {
                            str = "<option value='" + names[i].id + "'>" + names[i].taskName + "</option>";
                        }
                        $taskId.append(str);
                    }
                    form.render('select');
                }
            });
            //渲染省份选项
            $.ajax({
                url: '/eia/eiaDomainCode/getTree?domain=' + "PROVINCE_CODE",
                type: "POST",
                data: {},
                dataType: "json",
                success: function (province) {
                    var str = "";
                    for (var i = 0; i < province.length; i++) {
                        if (data.data) {
                            if (data.data.province == province[i].id) {
                                str = "<option value='" + province[i].id + "' selected>" + province[i].name + "</option>";
                            } else {
                                str = "<option value='" + province[i].id + "'>" + province[i].name + "</option>";
                            }
                        } else {
                            str = "<option value='" + province[i].id + "'>" + province[i].name + "</option>";
                        }
                        $province.append(str);
                    }
                    form.render();
                }
            });
            /**
             * 合同用途
             */
            $.ajax({
                url: '/eia/eiaDomainCode/getTree?domain=' + "CONTRACT_USE",
                type: "POST",
                data: {},
                async: true,
                dataType: "json",
                success: function (result) {
                    var str = "";
                    for (var i = 0; i < result.length; i++) {
                        if (data.data) {
                            if (data.data.contractUse == result[i].id) {
                                str = "<option value='" + result[i].id + "' selected>" + result[i].name + "</option>";
                            } else {
                                str = "<option value='" + result[i].id + "'>" + result[i].name + "</option>";
                            }
                        } else {
                            str = "<option value='" + result[i].id + "'>" + result[i].name + "</option>";
                        }
                        $contractUse.append(str);
                    }
                    form.render();
                }
            });
        }
    });

    //下拉树 合同类型
    $("#contractTypeDrop").dropDownForZ({
        url: '/eia/eiaDomainCode/getTree?domain=' + "CONTRACT_TYPE",
        width: '99%',
        height: '350px',
        disableParent: true,
        selecedSuccess: function (data) {
            if (!data.isParent) {
                $("#contractTypeStr").val(data.id);
                var temp = {
                    id: data.id,
                    name: data.name
                };
                $("#contractType").val(JSON.stringify(temp));

                //文件类型有值后，项目金额可填写
                $('.fillMoneyBtn').css("display", 'block');
                $('.show-tips').css("display", 'none');
                $('#contractMoney').val('');
            }
        }
    });

    //日期
    laydate.render({
        elem: '#contractDate'
    });

    //选择客户
    $('.selectUserBtn').click(function () {
        var index = layer.open({
            title: ' ',
            type: 2,
            shade: false,
            maxmin: true,
            skin: 'larry-green',
            area: ['100%', '100%'],
            content: '/eia/eiaClient/eiaClientSelect?clientType=client',
            success: function (layero, index) {

            },
            end: function () {
                var eiaClinetId = $("#eiaClientId").val();
                if (eiaClinetId) {
                    $('.selectContactBtn').css("display", 'block');
                } else {
                    $('.selectContactBtn').css("display", 'none');
                }
            },
            min: function () {

            },
            restore: function () {

            }
        });
    });
    //选择客户联系人
    $('.selectContactBtn').click(function () {
        var eiaClinetId = $("#eiaClientId").val();
        var pageUrl = '/eia/eiaClient/eiaContactSelect?eiaClientId='+ eiaClinetId +'&clientType=client';
        var index = layer.open({
            title: ' ',
            type: 2,
            shade: false,
            maxmin: true,
            skin: 'larry-green',
            area: ['100%', '100%'],
            content: pageUrl,
            success: function (layero, index) {

            },
            end: function () {

            },
            min: function () {

            },
            restore: function () {

            }
        });
    });
    //选择甲方客户
    $('.selectOwnerBtn').click(function () {
        var index = layer.open({
            title: ' ',
            type: 2,
            shade: false,
            maxmin: true,
            skin: 'larry-green',
            area: ['100%', '100%'],
            content: '/eia/eiaClient/eiaClientSelect?clientType=ownerClient',
            success: function (layero, index) {

            },
            end: function () {
                var ownerClientId = $("#ownerClientId").val();
                if (ownerClientId) {
                    $('.selectOwnerContactBtn').css("display", 'block');
                } else {
                    $('.selectOwnerContactBtn').css("display", 'none');
                }
            },
            min: function () {

            },
            restore: function () {

            }
        });
    });
    //选择甲方客户联系人
    $('.selectOwnerContactBtn').click(function () {
        var ownerClientId = $("#ownerClientId").val();
        var pageUrl = '/eia/eiaClient/eiaContactSelect?eiaClientId='+ ownerClientId +'&clientType=ownerClient';
        var index = layer.open({
            title: ' ',
            type: 2,
            shade: false,
            maxmin: true,
            skin: 'larry-green',
            area: ['100%', '100%'],
            content: pageUrl,
            success: function (layero, index) {

            },
            end: function () {

            },
            min: function () {

            },
            restore: function () {

            }
        });
    });

    //金额输入框强制小数点后4位
    var dotFour = function (value) {
        var valF = parseFloat(value),
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
    $('.dotFourInput').blur(function () {
        var val = $(this).val();
        $(this).val(dotFour(val));
    });

    //合同金额 按钮
    $('.fillMoneyBtn').click(function () {
        var index = layer.open({
            title: ' ',
            type: 2,
            shade: false,
            maxmin: true,
            skin: 'larry-green',
            area: ['100%', '100%'],
            content: '../eiaContract/eiaContractFillSelect',
            success: function (layero, index) {
                var body = layer.getChildFrame('body', index);
            },
            end: function () {

            },
            min: function () {
                $(".layui-layer-title").text("合同金额");
            },
            restore: function () {
                $(".layui-layer-title").text(" ");
            }
        });
    });

    //暂存按钮
    $('.tempStoreBtn').click(function () {
        var sendData = {};
        sendData.contractNo = $('#contractNo').val();
        sendData.contractTypeDrop = $('#contractTypeDrop').val();
        sendData.contractTypeStr = $('#contractTypeStr').val();
        sendData.contractTypeDropCode = $('#contractTypeDropCode').val();
        sendData.contractMoney = $('#contractMoney').val();
        sendData.taskId = $('#taskId').val();
        sendData.eiaClientName = $('#eiaClientName').val();
        sendData.contactName = $('#contactName').val();
        sendData.eiaClientId = $('#eiaClientId').val();
        sendData.ownerClientName = $('#ownerClientName').val();
        sendData.ownerContactName = $('#ownerContactName').val();
        sendData.ownerClientId = $('#ownerClientId').val();
        sendData.contractName = $('#contractName').val();
        sendData.contractUse = $('#contractUse').val();
        sendData.contractDate = $('#contractDate').val();
        sendData.province = $('#province').val();
        sendData.clientAddress = $('#clientAddress').val();
        sendData.contactPhone = $('#contactPhone').val();
        sendData.ownerClientAddress = $('#ownerClientAddress').val();
        sendData.ownerContactPhone = $('#ownerContactPhone').val();
        sendData.reportFees = $('#reportFees').val();
        sendData.enviroMonitoringFee = $('#enviroMonitoringFee').val();
        sendData.expertFee = $('#expertFee').val();
        sendData.groundWater = $('#groundWater').val();
        sendData.otherFee = $('#otherFee').val();
        sendData.specialFee = $('#specialFee').val();
        sendData.serveFee = $('#serveFee').val();
        sendData.ecoDetectFee = $('#ecoDetectFee').val();
        sendData.preIssCertFee = $('#preIssCertFee').val();
        sendData.preSurvCertFee = $('#preSurvCertFee').val();
        sendData.certServeFee = $('#certServeFee').val();
        sendData.contractId = $('#contractId').val();
        sendData.ifTempSave = "tempSave";
        sendData.contractTrust = $('#contractTrust').val();

        if (!sendData.contractMoney && sendData.contractMoney !== 0) {
            layer.msg('请录入合同金额！',{icon: 7, time: 1500, shade: 0.1});
            return
        }
        console.log(sendData);
        var loadingIndex = layer.load(1, {time: 10 * 1000, shade: 0.1});
        var actionUrl = "../eiaContract/eiaContractSave?eiaContractId=" + eiaContractId;
        $.ajax({
            url: actionUrl,
            type: "POST",
            data: sendData,
            dataType: "json",
            success: function (data) {
                if (data.code == 0) {
                    layer.msg('暂存成功', {icon: 1, time: 1000}, function () {
                        $('#contractNo').val(data.data.contractNo);
                        $('#contractId').val(data.data.id);
                        pageType = 1;
                        $pageTitle.html('编辑合同');
                        $('.resetBtn').hide();
                        parent.layui.table.reload("eiaContractList");
                        var index = parent.layer.getFrameIndex(window.name);
                        parent.layer.close(index)
                    });
                } else {
                    layer.msg(data.msg, {icon: 2, time: 1000, shade: 0.1});
                }
                layer.close(loadingIndex);

            }
        });
    });

    //合同受托方单选设置
    //监听复选框   (code值改动两处)
    form.on('checkbox(lhcd)', function(data){
        var ifChecked = data.elem.checked,
            cDValue = ifChecked ? 0 : 1; //code值改动
        var setVal = cDValue ? "联合泰泽" : "联合赤道";

        $('#lhtz').prop('checked',!ifChecked);
        form.render('checkbox');

        $('#contractTrust').val(setVal);
    });
    form.on('checkbox(lhtz)', function(data){
        var ifChecked = data.elem.checked,
            cDValue = ifChecked ? 1 : 0; //code值改动
        var setVal = cDValue ? "联合泰泽" : "联合赤道";

        $('#lhcd').prop('checked',!ifChecked);
        form.render('checkbox');

        $('#contractTrust').val(setVal);
    });

    //重置按钮
    $('.resetBtn').click(function () {
        $('#contractType').val("");
        //隐藏项目金额点击按钮
        $('.fillMoneyBtn').css("display", 'none');
        $('.selectContactBtn').css("display", 'none');
        $('.selectOwnerContactBtn').css("display", 'none');
        $('.show-tips').css("display", 'block');
    });

    //表单提交
    form.on('submit(save)', function (data) {
        var ownerClientName = $('#ownerClientName').val();
        if (ownerClientName) {
            var ownerClientId = $('#ownerClientId').val();
            var ownerClientAddress = $('#ownerClientAddress').val();
            var ownerContactName = $('#ownerContactName').val();
            var ownerContactPhone = $('#ownerContactPhone').val();
            if (!ownerClientId || !ownerClientAddress || !ownerContactName || !ownerContactPhone) {
                layer.msg('请完善甲方客户信息', {icon: 2, time: 1500});
                return false
            }
        }
        var loadingIndex = layer.load(1, {time: 10 * 1000, shade: 0.1});
        var eiaContractId = $('#contractId').val();
        var actionUrl = "";
        if (eiaContractId) {
            actionUrl = "../eiaContract/eiaContractSave?eiaContractId=" + eiaContractId;
        } else {
            actionUrl = "../eiaContract/eiaContractSave";
        }
        $.post(actionUrl, data.field, function (data) {
            if (data.code == 0) {
                layer.msg('保存成功', {icon: 1, time: 1000}, function () {
                    $('#contractNo').val(data.data.contractNo);
                    $('#contractId').val(data.data.id);
                    pageType = 1;
                    $pageTitle.html('编辑合同');
                    $('.resetBtn').hide();
                    parent.layui.table.reload("eiaContractList");
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.layer.close(index)
                });
            } else {
                layer.msg(data.msg, {icon: 2, time: 1000, shade: 0.1});
            }
            layer.close(loadingIndex);
        });
        return false;
    })

});