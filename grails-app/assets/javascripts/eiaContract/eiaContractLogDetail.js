layui.use(['jquery', 'form'], function(){
    var $ = layui.jquery,
        form = layui.form;
    var eiaContractLogId = parent.$('#eiaContractLogId').val();
    if(!eiaContractLogId){
        eiaContractLogId=getParamByUrl(window.location.href).eiaContractLogId
    }
    $('#eiaContractLogId').val(eiaContractLogId);

    var $dynMoneyContainers = $('.dynMoneyInputs .layui-col-xs6');

    //回显数据
    $.ajax({
        url:"/eia/eiaContractLog/getEiaContractLogDataMap?eiaContractLogId="+eiaContractLogId,
        type:"POST",
        data:{},
        dataType: "json",
        success: function (result) {
            console.log(result);
            $("#contractNo").text(result.data.contractNo);
            if (result.contractDate != result.contractDateEd) {
                $("#contractDate").text(result.contractDateEd).css("color", "red");
                $("#contractDateShow").removeClass("display-none");
                $("#contractDateShow").on("mouseenter",function(){
                    layer.tips("修改前内容: " +(result.contractDate== null?"":result.contractDate ) + "</br>" + "修改人: " + result.data.logInputUser + "</br>" + "修改日期: " + result.logInputDate, '#contractDateShow', {
                        area: ['auto', 'auto'],
                        tips: [1, '#30b5ff'],
                        time: 2000
                    });
                });
            } else {
                $("#contractDate").text(result.contractDateEd);
            }
            if (result.data.contractMoney != result.data.contractMoneyEd) {
                $("#contractMoney").text(result.data.contractMoneyEd).css("color", "red");
                $("#contractMoneyShow").removeClass("display-none");
                $("#contractMoneyShow").on("mouseenter",function(){
                    layer.tips("修改前内容: " +(result.data.contractMoney== null?"":result.data.contractMoney ) + "&nbsp;&nbsp;(万元)</br>" + "修改人: " + result.data.logInputUser + "</br>" + "修改日期: " + result.logInputDate, '#contractMoneyShow', {
                        area: ['auto', 'auto'],
                        tips: [1, '#30b5ff'],
                        time: 2000
                    });
                });
            } else {
                $("#contractMoney").text(result.data.contractMoneyEd);
            }
            if (result.data.eiaClientName != result.data.eiaClientNameEd) {
                $("#eiaClientName").text(result.data.eiaClientNameEd).css("color", "red");
                $("#eiaClientNameShow").removeClass("display-none");
                $("#eiaClientNameShow").on("mouseenter",function(){
                    layer.tips("修改前内容: " +(result.data.eiaClientName== null?"":result.data.eiaClientName ) + "</br>" + "修改人: " + result.data.logInputUser + "</br>" + "修改日期: " + result.logInputDate, '#eiaClientNameShow', {
                        area: ['auto', 'auto'],
                        tips: [1, '#30b5ff'],
                        time: 2000
                    });
                });
            } else {
                $("#eiaClientName").text(result.data.eiaClientNameEd);
            }
            if (result.data.clientAddress != result.data.clientAddressEd) {
                $("#clientAddress").text(result.data.clientAddressEd).css("color", "red");
                $("#clientAddressShow").removeClass("display-none");
                $("#clientAddressShow").on("mouseenter",function(){
                    layer.tips("修改前内容: " +(result.data.clientAddress== null?"":result.data.clientAddress ) + "</br>" + "修改人: " + result.data.logInputUser + "</br>" + "修改日期: " + result.logInputDate, '#clientAddressShow', {
                        area: ['auto', 'auto'],
                        tips: [1, '#30b5ff'],
                        time: 2000
                    });
                });
            } else {
                $("#clientAddress").text(result.data.clientAddressEd);
            }
            if (result.data.contactName != result.data.contactNameEd) {
                $("#contactName").text(result.data.contactNameEd).css("color", "red");
                $("#contactNameShow").removeClass("display-none");
                $("#contactNameShow").on("mouseenter",function(){
                    layer.tips("修改前内容: " +(result.data.contactName== null?"":result.data.contactName ) + "</br>" + "修改人: " + result.data.logInputUser + "</br>" + "修改日期: " + result.logInputDate, '#contactNameShow', {
                        area: ['auto', 'auto'],
                        tips: [1, '#30b5ff'],
                        time: 2000
                    });
                });
            } else {
                $("#contactName").text(result.data.contactNameEd);
            }
            if (result.data.contactPhone != result.data.contactPhoneEd) {
                $("#contactPhone").text(result.data.contactPhoneEd).css("color", "red");
                $("#contactPhoneShow").removeClass("display-none");
                $("#contactPhoneShow").on("mouseenter",function(){
                    layer.tips("修改前内容: " +(result.data.contactPhone== null?"":result.data.contactPhone ) + "</br>" + "修改人: " + result.data.logInputUser + "</br>" + "修改日期: " + result.logInputDate, '#contactPhoneShow', {
                        area: ['auto', 'auto'],
                        tips: [1, '#30b5ff'],
                        time: 2000
                    });
                });
            } else {
                $("#contactPhone").text(result.data.contactPhoneEd);
            }
            if (result.data.contractName != result.data.contractNameEd) {
                $("#contractName").text(result.data.contractNameEd).css("color", "red");
                $("#contractNameShow").removeClass("display-none");
                $("#contractNameShow").on("mouseenter",function(){
                    layer.tips("修改前内容: " +(result.data.contractName== null?"":result.data.contractName ) + "</br>" + "修改人: " + result.data.logInputUser + "</br>" + "修改日期: " + result.logInputDate, '#contractNameShow', {
                        area: ['auto', 'auto'],
                        tips: [1, '#30b5ff'],
                        time: 2000
                    });
                });
            } else {
                $("#contractName").text(result.data.contractNameEd);
            }
            if (result.data.reportFees != result.data.reportFeesEd) {
                $("#reportFees").text(result.data.reportFeesEd).css("color", "red");
                $("#reportFeesShow").removeClass("display-none");
                $("#reportFeesShow").on("mouseenter",function(){
                    layer.tips("修改前内容: " +(result.data.reportFees== null?"":result.data.reportFees ) + "</br>" + "修改人: " + result.data.logInputUser + "</br>" + "修改日期: " + result.logInputDate, '#reportFeesShow', {
                        area: ['auto', 'auto'],
                        tips: [1, '#30b5ff'],
                        time: 2000
                    });
                });
            } else {
                $("#reportFees").text(result.data.reportFeesEd);
            }
            if (result.data.enviroMonitoringFee != result.data.enviroMonitoringFeeEd) {
                $("#enviroMonitoringFee").text(result.data.enviroMonitoringFeeEd).css("color", "red");
                $("#enviroMonitoringFeeShow").removeClass("display-none");
                $("#enviroMonitoringFeeShow").on("mouseenter",function(){
                    layer.tips("修改前内容: " +(result.data.enviroMonitoringFee== null?"":result.data.enviroMonitoringFee ) + "</br>" + "修改人: " + result.data.logInputUser + "</br>" + "修改日期: " + result.logInputDate, '#enviroMonitoringFeeShow', {
                        area: ['auto', 'auto'],
                        tips: [1, '#30b5ff'],
                        time: 2000
                    });
                });
            } else {
                $("#enviroMonitoringFee").text(result.data.enviroMonitoringFeeEd);
            }
            if (result.data.groundWater != result.data.groundWaterEd) {
                $("#groundWater").text(result.data.groundWaterEd).css("color", "red");
                $("#groundWaterShow").removeClass("display-none");
                $("#groundWaterShow").on("mouseenter",function(){
                    layer.tips("修改前内容: " +(result.data.groundWater== null?"":result.data.groundWater ) + "</br>" + "修改人: " + result.data.logInputUser + "</br>" + "修改日期: " + result.logInputDate, '#groundWaterShow', {
                        area: ['auto', 'auto'],
                        tips: [1, '#30b5ff'],
                        time: 2000
                    });
                });
            } else {
                $("#groundWater").text(result.data.groundWaterEd);
            }
            if (result.data.expertFee != result.data.expertFeeEd) {
                $("#expertFee").text(result.data.expertFeeEd).css("color", "red");
                $("#expertFeeShow").removeClass("display-none");
                $("#expertFeeShow").on("mouseenter",function(){
                    layer.tips("修改前内容: " +(result.data.expertFee== null?"":result.data.expertFee ) + "</br>" + "修改人: " + result.data.logInputUser + "</br>" + "修改日期: " + result.logInputDate, '#expertFeeShow', {
                        area: ['auto', 'auto'],
                        tips: [1, '#30b5ff'],
                        time: 2000
                    });
                });
            } else {
                $("#expertFee").text(result.data.expertFeeEd);
            }
            if (result.data.otherFee != result.data.otherFeeEd) {
                $("#otherFee").text(result.data.otherFeeEd).css("color", "red");
                $("#otherFeeShow").removeClass("display-none");
                $("#otherFeeShow").on("mouseenter",function(){
                    layer.tips("修改前内容: " +(result.data.otherFee== null?"":result.data.otherFee ) + "</br>" + "修改人: " + result.data.logInputUser + "</br>" + "修改日期: " + result.logInputDate, '#otherFeeShow', {
                        area: ['auto', 'auto'],
                        tips: [1, '#30b5ff'],
                        time: 2000
                    });
                });
            } else {
                $("#otherFee").text(result.data.otherFeeEd);
            }
            if (result.data.contractType != result.data.contractTypeEd) {
                $("#contractType").text(result.data.contractTypeEd).attr('code',result.data.contractTypeCodeEd).css("color", "red");
                $("#contractTypeShow").removeClass("display-none");
                $("#contractTypeShow").on("mouseenter",function(){
                    layer.tips("修改前内容: " +(result.data.contractType== null?"":result.data.contractType ) + "</br>" + "修改人: " + result.data.logInputUser + "</br>" + "修改日期: " + result.logInputDate, '#contractTypeShow', {
                        area: ['auto', 'auto'],
                        tips: [1, '#30b5ff'],
                        time: 2000
                    });
                });
            } else {
                $("#contractType").text(result.data.contractTypeEd).attr('code',result.data.contractTypeCodeEd);
            }
            if (result.data.taskName != result.data.taskNameEd) {
                $("#taskName").text(result.data.taskNameEd).css("color", "red");
                $("#taskNameShow").removeClass("display-none");
                $("#taskNameShow").on("mouseenter",function(){
                    layer.tips("修改前内容: " +(result.data.taskName== null?"":result.data.taskName ) + "</br>" + "修改人: " + result.data.logInputUser + "</br>" + "修改日期: " + result.logInputDate, '#taskNameShow', {
                        area: ['auto', 'auto'],
                        tips: [1, '#30b5ff'],
                        time: 2000
                    });
                });
            } else {
                $("#taskName").text(result.data.taskNameEd);
            }
            if (result.data.province != result.data.provinceEd) {
                $("#province").text(result.data.provinceEd).css("color", "red");
                $("#provinceShow").removeClass("display-none");
                $("#provinceShow").on("mouseenter",function(){
                    layer.tips("修改前内容: " +(result.data.province== null?"":result.data.province ) + "</br>" + "修改人: " + result.data.logInputUser + "</br>" + "修改日期: " + result.logInputDate, '#provinceShow', {
                        area: ['auto', 'auto'],
                        tips: [1, '#30b5ff'],
                        time: 2000
                    });
                });
            } else {
                $("#province").text(result.data.provinceEd);
            }
            if (result.data.contractUse != result.data.contractUseEd) {
                $("#contractUse").text(result.data.contractUseEd).css("color", "red");
                $("#contractUseShow").removeClass("display-none");
                $("#contractUseShow").on("mouseenter",function(){
                    layer.tips("修改前内容: " +(result.data.contractUse== null?"":result.data.contractUse ) + "</br>" + "修改人: " + result.data.logInputUser + "</br>" + "修改日期: " + result.logInputDate, '#contractUseShow', {
                        area: ['auto', 'auto'],
                        tips: [1, '#30b5ff'],
                        time: 2000
                    });
                });
            } else {
                $("#contractUse").text(result.data.contractUseEd);
            }
            if (result.data.ownerClientName != result.data.ownerClientNameEd) {
                $("#ownerClientName").text(result.data.ownerClientNameEd).css("color", "red");
                $("#ownerClientNameShow").removeClass("display-none");
                $("#ownerClientNameShow").on("mouseenter",function(){
                    layer.tips("修改前内容: " +(result.data.ownerClientName== null?"":result.data.ownerClientName ) + "</br>" + "修改人: " + result.data.logInputUser + "</br>" + "修改日期: " + result.logInputDate, '#ownerClientNameShow', {
                        area: ['auto', 'auto'],
                        tips: [1, '#30b5ff'],
                        time: 2000
                    });
                });
            } else {
                $("#ownerClientName").text(result.data.ownerClientNameEd);
            }
            if (result.data.ownerClientAddress != result.data.ownerClientAddressEd) {
                $("#ownerClientAddress").text(result.data.ownerClientAddressEd).css("color", "red");
                $("#ownerClientAddressShow").removeClass("display-none");
                $("#ownerClientAddressShow").on("mouseenter",function(){
                    layer.tips("修改前内容: " +(result.data.ownerClientAddress== null?"":result.data.ownerClientAddress ) + "</br>" + "修改人: " + result.data.logInputUser + "</br>" + "修改日期: " + result.logInputDate, '#ownerClientAddressShow', {
                        area: ['auto', 'auto'],
                        tips: [1, '#30b5ff'],
                        time: 2000
                    });
                });
            } else {
                $("#ownerClientAddress").text(result.data.ownerClientAddressEd);
            }
            if (result.data.ownerContactName != result.data.ownerContactNameEd) {
                $("#ownerContactName").text(result.data.ownerContactNameEd).css("color", "red");
                $("#ownerContactNameShow").removeClass("display-none");
                $("#ownerContactNameShow").on("mouseenter",function(){
                    layer.tips("修改前内容: " +(result.data.ownerContactName== null?"":result.data.ownerContactName ) + "</br>" + "修改人: " + result.data.logInputUser + "</br>" + "修改日期: " + result.logInputDate, '#ownerContactNameShow', {
                        area: ['auto', 'auto'],
                        tips: [1, '#30b5ff'],
                        time: 2000
                    });
                });
            } else {
                $("#ownerContactName").text(result.data.ownerContactNameEd);
            }
            if (result.data.ownerContactPhone != result.data.ownerContactPhoneEd) {
                $("#ownerContactPhone").text(result.data.ownerContactPhoneEd).css("color", "red");
                $("#ownerContactPhoneShow").removeClass("display-none");
                $("#ownerContactPhoneShow").on("mouseenter",function(){
                    layer.tips("修改前内容: " +(result.data.ownerContactPhone== null?"":result.data.ownerContactPhone ) + "</br>" + "修改人: " + result.data.logInputUser + "</br>" + "修改日期: " + result.logInputDate, '#ownerContactPhoneShow', {
                        area: ['auto', 'auto'],
                        tips: [1, '#30b5ff'],
                        time: 2000
                    });
                });
            } else {
                $("#ownerContactPhone").text(result.data.ownerContactPhoneEd);
            }
            if (result.data.contractTrust != result.data.contractTrustEd) {
                $("#contractTrust").text(result.data.contractTrustEd).css("color", "red");
                $("#contractTrustShow").removeClass("display-none");
                $("#contractTrustShow").on("mouseenter",function(){
                    layer.tips("修改前内容: " +(result.data.contractTrust== null?"":result.data.contractTrust ) + "</br>" + "修改人: " + result.data.logInputUser + "</br>" + "修改日期: " + result.logInputDate, '#contractTrustShow', {
                        area: ['auto', 'auto'],
                        tips: [1, '#30b5ff'],
                        time: 2000
                    });
                });
            } else {
                $("#contractTrust").text(result.data.contractTrustEd);
            }
        }
    });

    //渲染金额子项数据
    $.ajax({
        url:"/eia/eiaContractLog/getContractLogMoney",
        type:"POST",
        data:{eiaContractLogId: eiaContractLogId},
        dataType: "json",
        async: true,
        success: function (result) {
            var data = result.data;
            $dynMoneyContainers.empty();
            var con_index = 0;
            for(var name in data){
                moneyFillInputs[name].show.call(this,data[name], $dynMoneyContainers.eq(con_index++%2));
            }
            if ($("#contractType").attr('code') == "CD") {
                $('#enviroMonitoringFeeEd').closest('.layui-form-item').find('.layui-form-label').text('地勘加监测费用');
            }
        }
    });

});