layui.use(['jquery', 'form', 'layer', 'laydate'], function () {
    var $ = layui.jquery,
        form = layui.form,
        layer = layui.layer,
        laydate = layui.laydate;

    var nowDate
    /**
     * 获取当前时间
     */
    var getNowDate = new function () {
        var date = new Date();
        var sign1 = "-";
        var year = date.getFullYear() // 年
        var month = date.getMonth() + 1; // 月
        var day = date.getDate(); // 日

        // 给一位数数据前面加 “0”
        if (month >= 1 && month <= 9) {
            month = "0" + month;
        }
        if (day >= 0 && day <= 9) {
            day = "0" + day;
        }
        var currentdate = year + sign1 + month + sign1 + day;
        nowDate = currentdate
        return
    }


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

    var ajaxUrl = getParamFromUrl(document.location.href, "actionURL"),
        processCode = getParamFromUrl(document.location.href, "processCode"),
        eiaWorkFlowBusiId = getParamFromUrl(document.location.href, "eiaWorkFlowBusiId"),
        processUrlParams = getParamFromUrl(document.location.href, "processUrlParams"),
        nodesCode = getParamFromUrl(document.location.href, "nodesCode"),
        currNodesName = getParamFromUrl(document.location.href, "currNodesName"),
        version = getParamFromUrl(document.location.href, "version");
    $("#currNodesName").text(decodeURI(currNodesName));


    //渲染节点审核人select选项
    $.ajax({
        url: "../eiaProjectPlan/getNodeUsers",
        type: "POST",
        data: {"eiaWorkFlowBusiId": eiaWorkFlowBusiId, "nodesCode": processUrlParams},
        dataType: "json",
        async: true,
        success: function (res) {
            var data = res.data;
            for (var name in data) {
                var str = "<option value='" + data[name].id + "'>" + data[name].name + "</option>";
                $('#nodeUser').append(str);
            }

            //加载数据
            $.ajax({
                url: "../eiaProjectPlan/getProPlanDataMap",
                type: "POST",
                data: {"eiaWorkFlowBusiId": eiaWorkFlowBusiId, "nodesCode": processUrlParams},
                dataType: "json",
                async: true,
                success: function (result) {
                    if (result.data.nodeUserId) {
                        $("#nodeUser").find("option[value = " + result.data.nodeUserId + "]").attr("selected", "selected");
                    }
                    $('#nodeUserId').val(result.data.nodeUserId);
                    form.render('select');
                }
            });

            //加载数据
            $.ajax({
                url: "../eiaProjectPlan/getProPlanDataMap",
                type: "POST",
                data: {"eiaWorkFlowBusiId": eiaWorkFlowBusiId, "nodesCode": nodesCode},//当前节点的
                dataType: "json",
                async: true,
                success: function (result) {
                    if (result.data.actStartDate) {
                        $('#actStartDate').val(result.data.actStartDate);
                    }
                    if (result.data.actEndDate) {
                        $('#actEndDate').val(result.data.actEndDate);
                    }
                    form.render('select');
                }
            });
            form.render('select');
        }
    });

    //下拉树 相关工作人员
    $("#userNamesDept").dropDownForZ({
        url: "../eiaProjectPlan/getNodeUsersTree?eiaWorkFlowBusiId=" + eiaWorkFlowBusiId + "&nodesCode=" + nodesCode,
        width: '99%',
        height: '350px',
        mode: "multi",
        selectedNodes: function (data) {
            var userNames = [];
            for (var i = 0; i < data.length; i++) {
                var temp = {};
                temp.name = data[i].name;
                temp.id = data[i].id;
                userNames.push(temp);
            }
            $('#userNames').val(JSON.stringify(userNames));
        }
    });

    //回显 相关工作人员下拉树 数据
    $.ajax({
        url: "../eiaProjectPlan/getItemUserNames",
        type: "POST",
        data: {"eiaWorkFlowBusiId": eiaWorkFlowBusiId, "nodesCode": nodesCode},
        dataType: "json",
        success: function (res) {
            if (res.code == 0) {
                var data = res.data
                var showName = "",
                    showCode = "";
                for (var i = 0; i < data.length; i++) {
                    showName += data[i].name + ",";
                    showCode += data[i].id + ",";
                }
                showName = showName.substring(0, showName.length - 1);
                showCode = showCode.substring(0, showCode.length - 1);
                $('#userNamesDept').val(showName);
                $('#userNamesDeptCode').val(showCode);

                $('#userNames').val(JSON.stringify(data));
            } else {

            }

        }
    });


    /**
     * 表单回显
     */
    //渲染客户select选项
    $.ajax({
        url: "/eia/eiaWorkFlowProject/getNodeOpinion",
        type: "POST",
        data: {
            nodesCode: nodesCode,
            processUrlParams: processUrlParams,
            eiaWorkFlowBusiId: eiaWorkFlowBusiId

        },
        dataType: "json",
        async: true,
        success: function (res) {
            if (res.data) {
                $('#opinion').val(res.data.opinion);
                $('#modiContent').val(res.data.modiContent);
            }
        }
    })

    //表单提交
    form.on('submit(save)', function (data) {
        var loadingIndex = layer.load(1, {time: 10 * 1000, shade: 0.1});
        $.ajax({
            url: ".." + ajaxUrl,   //需要替换，actionUrl前要加"/"
            type: "POST",
            data: {
                "opinion": data.field.opinion,
                "modiContent": data.field.modiContent,
                "processCode": processCode,
                "eiaWorkFlowBusiId": eiaWorkFlowBusiId,
                "processUrlParams": processUrlParams,
                "nodesCode": nodesCode,
                "nodesUsers": $('#userNames').val(),
                "nodeUserId": $('#nodeUser').val(), //这个是id
                "nodesCode": nodesCode,
                "version": version,
                'nodeUserName': $('#nodeUser option:selected').text(),//选中的文本
                'version': version
            },
            dataType: "json",
            async: true,
            success: function (data) {
                if (data.code == 0) {
                    layer.msg(data.msg, {icon: 1, time: 1500}, function () {
                        if (parent.parent.layer) {
                            parent.parent.location.reload();
                        }
                        parent.parent.layer.closeAll();
                    });
                } else {
                    layer.msg(data.msg, {icon: 2});
                }
            },
            complete: function () {
                layer.close(loadingIndex);
            }
        });

        return false;
    })


});