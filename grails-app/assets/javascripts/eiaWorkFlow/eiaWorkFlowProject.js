layui.use(['jquery', 'layer', 'table', 'element'], function () {
    var $ = layui.jquery,
        layer = layui.layer,
        table = layui.table,
        element = layui.element;


    var $flowLastTitle = $('.flow-tab-title .last-title'),
        $flowLastItem = $('.flow-tab-content .last-tab-item');

    // Tab切换
    element.on('tab(flowTab)', function (data) {
        var cur_index = data.index;
        if (cur_index) {
            renderFlowTable();
            var $thisIabIframe = $('.flow-tab-content .layui-tab-item').eq(cur_index).find('iframe');
            $thisIabIframe.attr('src', $thisIabIframe.attr('src'));

        }
    });
    var eiaProjectId = parent.$('#eiaProjectId').val();
    if (!eiaProjectId) {
        eiaProjectId = parent.$('#tableNameId').val();
    }
    eiaProjectId = eiaProjectId ? eiaProjectId : getParamFromUrl(document.location.href, "eiaProjectId");
    var currNodesCode;//当前节点編碼
    $('#eiaProjectId').val(eiaProjectId)
    //渲染流程步骤
    //需要tableName和tableNameId两个参数
    $.ajax({
        //url:"/eia/static/js/layuiFrame/data/flowStepsData.json",
        url: request_url_root + "/eiaWorkFlowBusi/getWorkFlowNodeDataList",
        type: "GET",
        data: {tableName: 'EiaProject', tableNameId: $('#eiaProjectId').val()},
        dataType: "json",
        async: true,
        success: function (res) {
            var data = res.data;
            var nodesList = data.nodesList,
                nodesTab = data.nodesTab;
            currNodesCode = data.currNodeCode;
            var stepNum = nodesList.length, col = "";
            nodesList.map(function (d) {
                if (!d.ifIcon) {
                    stepNum = stepNum - 1
                }
            });
            switch (stepNum) {
                case 2:
                    col = "layui-col-xs6 layui-col-sm6 layui-col-md6 layui-col-lg6";
                    break;
                case 3:
                    col = "layui-col-xs6 layui-col-sm4 layui-col-md4 layui-col-lg4";
                    break;
                case 4:
                    col = "layui-col-xs6 layui-col-sm4 layui-col-md3 layui-col-lg3";
                    break;
                case 5:
                    col = "layui-col-xs6 layui-col-sm4 layui-col-md3 layui-col-lg2-4";
                    break;
                case 6:
                    col = "layui-col-xs6 layui-col-sm4 layui-col-md3 layui-col-lg2";
                    break;
                case 7:
                    col = "layui-col-xs6 layui-col-sm4 layui-col-md3 layui-col-lg1-7";
                    break;
                case 8:
                    col = "layui-col-xs6 layui-col-sm4 layui-col-md3 layui-col-lg2 layui-col-lg1-5";
                    break;
                case 9:
                    col = "layui-col-xs6 layui-col-sm4 layui-col-md3 layui-col-lg2 layui-col-lg1-3";
                    break;
                default:
                    col = "layui-col-xs6 layui-col-sm4 layui-col-md3 layui-col-lg2 layui-col-lg1-3";
                    break;
            }

            for (var i = 0; i < nodesList.length; i++) {
                if (nodesList[i].ifIcon == true) {
                    var ifactive = nodesList[i].active ? "active larry-ico-rotate" : "",
                        bgColor = nodesList[i].active ? nodesList[i].nodesColor : "";
                    var str = "<div class='col-box " + col + "'>" +
                        "            <section class='flow-block " + ifactive + " '>" +
                        "                <a class=''>" +
                        "                    <div class='larry-ico' style='background:" + bgColor + "'>" +
                        "                        <i class='larry-icon'>" + nodesList[i].flowIcon + "</i>" +
                        "                    </div>" +
                        "                    <div class='larry-value larry-bg-gray'>" +
                        "                        <span>" + nodesList[i].flowName + "</span>" +
                        "                    </div>" +
                        "                </a>" +
                        "            </section>" +
                        "        </div>";

                    $('.proj-flow-steps').append(str);
                }
            }

            //后台传来的nodesTab未定义或为空数组时
            if (!nodesTab || !nodesTab.length) {
                $flowLastTitle.addClass('layui-this');
                $flowLastItem.addClass('layui-show');
            }
            for (var i = 0; i < nodesTab.length; i++) {
                var ifShowTitle = i ? "" : "layui-this";
                var tabTitleStr = "<li class='" + ifShowTitle + "'>" + nodesTab[i].nodesTabTitle + "</li>";
                $flowLastTitle.before(tabTitleStr);

                var ifShowItem = i ? "" : "layui-show";
                var tabItemStr = "<div class='layui-tab-item " + ifShowItem + "'>" +
                    "                <iframe src='" + request_url_root + nodesTab[i].nodesUrl + "' frameborder='0'></iframe>" +
                    "            </div>"
                $flowLastItem.before(tabItemStr);
            }
        }
    });

    //渲染按钮部分
    //需要tableName和tableNameId两个参数
    $.ajax({
        //url:"/eia/static/js/layuiFrame/data/flowBtnsData.json",
        url: request_url_root + "/eiaWorkFlowBusi/getWorkFlowNodeProcessDataList",
        type: "GET",
        data: {tableName: 'EiaProject', tableNameId: $('#eiaProjectId').val()},
        dataType: "json",
        async: true,
        success: function (res) {
            var data = res.data;
            for (var i = 0; i < data.length; i++) {
                if (data[i].btnName) {
                    // var str = "<div class='layui-inline' btnName='" + data[i].btnName + "' currNodesName='"+data[i].currNodesName+"' actionUrl = '" + data[i].actionUrl + "' eiaWorkFlowBusiId='" + data[i].eiaWorkFlowBusiId + "' processCode='" + data[i].processCode + "' processUrlParams='" + data[i].processUrlParams + "' processJumpUrl='" + data[i].processJumpUrl + "' processJumpUrlParams='" + data[i].processJumpUrlParams + "' >" +
                    //     "            <a class='layui-btn layui-btn-sm pl12' data-type='missionAdd'><i class='larry-icon'>" + data[i].btnIcon + "</i> " + data[i].btnName + "</a>" +
                    //     "        </div>";
                    var str = "<a class='layui-btn layui-btn-sm pl12' data-type='missionAdd' btnName='" + data[i].btnName + "' currNodesName='" + data[i].currNodesName + "' actionUrl = '" + data[i].actionUrl + "' eiaWorkFlowBusiId='" + data[i].eiaWorkFlowBusiId + "' processCode='" + data[i].processCode + "' processUrlParams='" + data[i].processUrlParams + "' processJumpUrl='" + data[i].processJumpUrl + "' processJumpUrlParams='" + data[i].processJumpUrlParams + "' version='" + data[i].version + "'><i class='larry-icon'>" + data[i].btnIcon + "</i> " + data[i].btnName + "</a>";

                    var $str = $(str)
                        .on('click', function () {
                            var actionURL = $(this).attr('actionUrl'),
                                actionName = $(this).attr('btnName'),
                                eiaWorkFlowBusiId = $(this).attr('eiaWorkFlowBusiId'),
                                processCode = $(this).attr('processCode'),
                                processUrlParams = $(this).attr('processUrlParams'),
                                /**————加跳转URL和确认URL的字段——————**/
                                processUrl = $(this).attr('processUrl'),
                                processJumpUrl = $(this).attr('processJumpUrl'),
                                processJumpUrlParams = $(this).attr('processJumpUrlParams'),
                                currNodesName = $(this).attr('currNodesName'),
                                version = $(this).attr('version');

                            pageUrl = request_url_root + processJumpUrl + "?actionURL=" + actionURL
                                + "&eiaWorkFlowBusiId=" + eiaWorkFlowBusiId
                                + "&processCode=" + processCode
                                + "&processUrlParams=" + processUrlParams
                                + "&nodesCode=" + currNodesCode
                                + "&currNodesName=" + currNodesName
                                + "&version=" + version;
                            /**
                             * 这里加确认节点的方法
                             */
                            $.ajax({
                                url: request_url_root + "/eiaWorkFlowProject/checkNodeExist",
                                type: "POST",
                                data: {
                                    "processCode": processCode,
                                    "eiaWorkFlowBusiId": eiaWorkFlowBusiId,
                                    "nodesCode": currNodesCode,
                                    "processUrlParams": processUrlParams,//
                                },
                                dataType: "json",
                                async: true,
                                success: function (res) {
                                    if (res.code == 0) {
                                        layer.open({
                                            title: ' ',
                                            type: 2,
                                            skin: 'larry-green',
                                            content: pageUrl,
                                            area: ['800px', '600px'],
                                            success: function (layero, index) {
                                                var body = layer.getChildFrame('body', index);
                                                // body.find('#contractId').val(data.id);
                                            },
                                            end: function () {
                                                // $('#offerId').val("");
                                            },
                                            min: function () {
                                                $(".layui-layer-title").text("流程确认");
                                            },
                                            restore: function () {
                                                $(".layui-layer-title").text(" ");
                                            }
                                        });
                                    } else {
                                        layer.msg(res.msg, {icon: 2});
                                    }

                                }
                            })
                        });
                    $str.css('background-color', data[i].btnColor);
                    $('.larry-btn-box').append($str);
                }
            }
        }
    });

    /**项目信息:**/


    // 下方Tab切换
    element.on('tab(flowFormTab)', function (data) {
        var cur_index = data.index;
    });
    //渲染流转意见
    $.ajax({
        // url:"/eia/static/js/layuiFrame/data/optionFlowData.json",
        url: request_url_root + "/eiaWorkFlowBusiLog/getEiaWorkFlowBusiLogDataList",
        //需要tableName和tableNameId两个参数
        data: {tableName: 'EiaProject', tableNameId: $('#eiaProjectId').val()},
        dataType: "json",
        async: true,
        success: function (res) {
            var data = res.data;
            for (var i = 0; i < data.length; i++) {
                var signImgStr = data[i].signImg ? "<img src='" + data[i].signImg + "'/>" : "";
                var str = "<li>" +
                    "                                    <div class='layui-row'>" +
                    "                                        <div class='layui-col-xs3'>" +
                    "                                            <div class='opl-l'>" +
                    "                                                <div class='opl-inner opl-inner-lf pt5'>" +
                    "                                                    <i class='larry-icon'>&#xe7ac;</i>" +
                    "                                                </div>" +
                    "                                                <div class='opl-inner ml15'>" +
                    "                                                    <p class='opl-txt1'>" + data[i].inputUser + "</p>" +
                    "                                                    <p class='opl-txt2'>" + data[i].inputDept + "</p>" +
                    "                                                </div>" +
                    "                                            </div>" +
                    "                                        </div>" +
                    "                                        <div class='layui-col-xs4'>" +
                    "                                            <div class='opl-l'>" +
                    "                                                <div class='opl-inner'>" +
                    "                                                    <p class='opl-txt2'>流转意见：</p>" +
                    "                                                    <p class='opl-txt1'>" + data[i].opinion + "</p>" +
                    "                                                </div>" +
                    "                                            </div>" +
                    "                                        </div>" +
                    "                                        <div class='layui-col-xs2'>" +
                    "                                            <div class='sign-img'>" + signImgStr + "</div>" +
                    "                                        </div>" +
                    "                                        <div class='layui-col-xs3'>" +
                    "                                            <div class='opl-l opl-l-res'>" +
                    "                                                <div class='opl-inner opl-inner-res'>" +
                    "                                                    <p class='opl-txt2'>" + data[i].approvalDate + "</p>" +
                    "                                                    <p class='opl-txt1'>[" + data[i].nodesName + "/" + data[i].processName + "]</p>" +
                    "                                                </div>" +
                    "                                            </div>" +
                    "                                        </div>" +
                    "                                    </div>" +
                    "                                    <hr>" +
                    "                                </li>";
                $('#optionFlow').append(str);
            }
        }
    });
    //渲染与我相关
    $.ajax({
        //url:"/eia/static/js/layuiFrame/data/optionAboutMeData.json",
        url: request_url_root + "/eiaWorkFlowBusiLog/getEiaWorkFlowBusiLogAboutMeDataList",
        //需要tableName和tableNameId两个参数
        data: {tableName: 'EiaProject', tableNameId: $('#eiaProjectId').val()},
        dataType: "json",
        async: true,
        success: function (res) {
            var data = res.data;
            for (var i = 0; i < data.length; i++) {
                var signImgStr = data[i].signImg ? "<img src='" + data[i].signImg + "'/>" : "";
                var str = "<li>" +
                    "                                    <div class='layui-row'>" +
                    "                                        <div class='layui-col-xs3'>" +
                    "                                            <div class='opl-l'>" +
                    "                                                <div class='opl-inner opl-inner-lf pt5'>" +
                    "                                                    <i class='larry-icon'>&#xe7ac;</i>" +
                    "                                                </div>" +
                    "                                                <div class='opl-inner ml15'>" +
                    "                                                    <p class='opl-txt1'>" + data[i].inputUser + "</p>" +
                    "                                                    <p class='opl-txt2'>" + data[i].inputDept + "</p>" +
                    "                                                </div>" +
                    "                                            </div>" +
                    "                                        </div>" +
                    "                                        <div class='layui-col-xs6'>" +
                    "                                            <div class='opl-l'>" +
                    "                                                <div class='opl-inner'>" +
                    "                                                    <p class='opl-txt2'>流转意见：</p>" +
                    "                                                    <p class='opl-txt1'>" + data[i].opinion + "</p>" +
                    "                                                </div>" +
                    "                                            </div>" +
                    "                                        </div>" +
                    "                                        <div class='layui-col-xs2'>" +
                    "                                            <div class='sign-img'>" + signImgStr + "</div>" +
                    "                                        </div>" +
                    "                                        <div class='layui-col-xs3'>" +
                    "                                            <div class='opl-l opl-l-res'>" +
                    "                                                <div class='opl-inner opl-inner-res'>" +
                    "                                                    <p class='opl-txt2'>" + data[i].approvalDate + "</p>" +
                    "                                                    <p class='opl-txt1'>[" + data[i].nodesName + "/" + data[i].processName + "]</p>" +
                    "                                                </div>" +
                    "                                            </div>" +
                    "                                        </div>" +
                    "                                    </div>" +
                    "                                    <hr>" +
                    "                                </li>";
                $('#optionAboutMe').append(str);
            }
        }
    });


    /**流程状态:**/
        //渲染表格
    var renderFlowTable = function () {
            table.render({
                id: 'flowStateList',
                elem: '#flowStateList',
                where: {tableName: 'EiaProject', tableNameId: $('#eiaProjectId').val()},
                url: request_url_root + "/eiaWorkFlowBusiLog/getEiaWorkFlowBusiLogDataList",
                toolbar: ' ',
                defaultToolbar:['filter', 'print', 'exports'],
                cols: [[
                    {fixed: 'left', title: '序号', width: "6%", align: "center", templet: "#indexTable"},
                    {field: 'workFlowName', width: '19%', title: '流程名称', align: "center"},
                    {field: 'nodesName', width: "20%", title: '节点名称', align: "center"},
                    {field: 'inputUser', width: '12%', title: '操作人', align: "center"},
                    {field: 'receiver', width: '12%', title: '接收人', align: "center"},
                    {field: 'processName', width: '12%', title: '操作状态', align: "center"},
                    {field: 'approvalDate', width: '20%', title: '操作时间', align: "center"}
                ]],
                page: true,
                even: true,
                limit: 10
            });
        };
});