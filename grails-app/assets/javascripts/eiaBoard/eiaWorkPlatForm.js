layui.use(['jquery', 'layer', 'table', 'element', 'laydate'], function () {
    var $ = layui.jquery,
        layer = layui.layer,
        element = layui.element,
        laydate = layui.laydate,
        table = layui.table;

    //高级查询数据
    var conFilterData = {};
    var proFilterData = {};

    var nameArr = [];
    $('.shortcut-lf li').each(function (index, elem) {
        var curName = $(elem).find('a').data('name');
        nameArr.push(curName);
    });

    $.ajax({
        url: "/eia/eiaBoard/checkStaffClickBtn?nameArr=" + nameArr,
        type: "POST",
        dataType: "json",
        success: function (res) {
            var data = res.data;
            console.log(data);
            for(var name in data){
                $(".shortcut-lf li a[data-name="+name+"]").attr("ifjump",data[name]);
            }
        }
    });

    //快捷方式
    $('.shortcut-group a').off('click').on('click',function(){
        var ifJump = $(this).attr('ifjump');
        if(ifJump=='true'){
            var gp;
            if ($(this).data('group') !== undefined) {
                gp = $(this).data('group');
            } else {
                gp = 'larry-temp';
            }
            var data = {
                href: $(this).data('url'),
                id: $(this).data('id'),
                font: $(this).data('font'),
                icon: $(this).data('icon'),
                group: gp,
                title: $(this).find('cite').text(),
                addType: 'iframe'
            };
            console.log(data);
            try {
                window.top.postMessage(data, auth_forword_jump);
            } catch (error) {
            }
        }else{
            layer.msg('您没有权限');
        }


    });

    /**项目**/
    var projectData = [
        {
            "projectNo": "TM-7777",
            "projectName": "项目一",
            "buildArea": "地点一",
            "fileTypeChild": "文件类型一",
            "projectMoney": "20",
            "inputDept": "部门一",
            "inputUser": "李四",
            "dutyUser": "王伟"
        }
    ];
    //恢复上次查询数据显示
    var regainProQueryShow = function () {
        var proAdvancedState = $('#proAdvancedState').val();
        var proQueryData = JSON.parse($('#proQueryData').val());
        if(proAdvancedState == '1'){ //高级查询
            //打开高级查询面板
            $('.filter-box-pro').attr('state','1').css('display','block');
            $('#proAdvancedState').val(1);
            //赋值
            for(var name in proQueryData){
                switch (name){
                    case 'ifArc':
                        var dataVal = proQueryData[name];
                        if(dataVal){
                            $(".filter-box-pro ."+name+"Filter .filter-item:contains('"+dataVal+"')").addClass('active');
                        }
                        break;
                    case 'arcStartDate':
                    case 'arcEndDate':
                        laydate.render({
                            elem: '#'+name,
                            value: proQueryData[name]
                        });
                        break;
                    case 'fileTypeChild':
                        //下拉树 项目类型
                        $("#fileTypeChild").dropDownForZ({
                            url:'/eia/eiaDomainCode/getTree?domain=' + "PROJECT_FILE_TYPE",
                            width:'99%',
                            height:'350px',
                            disableParent: true,
                            selecedSuccess:function(data){}
                        });
                        $("#fileTypeChild").val(proQueryData[name]);
                        break;
                    default:
                        $('#'+name).val(proQueryData[name]);
                        break;

                }
            }

        }else{//普通查询
            $('#projectName').val(proQueryData.projectName);
        }
    };
    table.render({
        id: 'eiaProjectList',
        elem: '#eiaProjectList',
        url: '/eia/eiaProject/getEiaProjectDataList',
        toolbar: '#proTableTopTmp',
        defaultToolbar:['filter', 'print', 'exports'],
        cols: [[
            {fixed: 'left', title: '序号', width: '6%', align: "center", templet: "#indexTable"},
            {field: 'projectNo', width: '12%', title: '项目编号', align: "center"},
            {field: 'projectName', width: '30%', title: '项目名称', align: "center"},
            {field: 'buildArea', width: '20%', title: '建设地点', align: "center"},
            {field: 'eiaClientName', width: '20%', title: '客户名称', align: "center"},
            {field: 'fileTypeChild', width: '12%', title: '文件类型', align: "center"},
            {field: 'projectMoney', width: '8%', title: '项目金额', align: "center"},
            {field: 'inputDept', width: '12%', title: '录入部门', align: "center"},
            {field: 'inputUser', width: '8%', title: '录入人', align: "center"},
            {field: 'dutyUser', width: '8%', title: '项目负责人', align: "center"},
            {fixed: 'right', title: '操作', width: '20%', align: "center", toolbar: '#mlTool', align: "center"}
        ]],
        page: true,
        even: true,
        limit: 10,
        done: function () {
            regainProQueryShow();
        }
    });
    //获取查询数据以及暂存查询数据
    var getProQueryData = function () {
        proFilterData.projectName = $('#projectName').val();
        proFilterData.eiaClientName = $('#eiaClientName').val();
        proFilterData.buildArea = $('#buildArea').val();
        proFilterData.projectStartMoney = $('#projectStartMoney').val();
        proFilterData.projectEndMoney = $('#projectEndMoney').val();
        proFilterData.fileTypeChild = $('#fileTypeChild').val();
        proFilterData.seaReviewNo = $('#seaReviewNo').val();
        proFilterData.arcStartDate = $('#arcStartDate').val();
        proFilterData.arcEndDate = $('#arcEndDate').val();

        $('.filter-box-pro .filter-ul .filter-li').each(function (index, elem) {
            var curFilter = $(elem).attr('filterName');
            var curVal = $(elem).find('.filter-item.active span').text();
            proFilterData[curFilter] = curVal;

        });
        var proAdvancedState = $('.filter-box-pro').attr('state');
        $('#proQueryData').val(JSON.stringify(proFilterData));
        $('#proAdvancedState').val(proAdvancedState);

        console.log(proFilterData);

    };

    //监听项目表格头部工具栏事件
    table.on('toolbar(eiaProjectList)', function(obj){

        switch(obj.event){
            case 'projectSelect': //查询
                getProQueryData();

                table.reload('eiaProjectList', {
                    where: proFilterData
                });
                break;
            case 'advancedQuery': //高级查询
                var curSate = $('.filter-box-pro').attr('state');
                switch (curSate){
                    case '0': //打开
                        $('.filter-box-pro').attr('state','1').css('display','block');
                        $('#proAdvancedState').val(1);
                        //日期初始化
                        laydate.render({
                            elem: '#arcStartDate'
                        });

                        laydate.render({
                            elem: '#arcEndDate'
                        });
                        //下拉树 项目类型
                        $("#fileTypeChild").dropDownForZ({
                            url:'/eia/eiaDomainCode/getTree?domain=' + "PROJECT_FILE_TYPE",
                            width:'99%',
                            height:'350px',
                            disableParent: true,
                            selecedSuccess:function(data){}
                        });
                        break;
                    case '1': //关闭
                        $('.filter-box-pro').attr('state','0').css('display','none');
                        $('.filter-box-pro input').val('');
                        $('.filter-box-pro .filter-ul .filter-item').removeClass('active');
                        getProQueryData();
                        break;
                }
                break;
            case 'filterItem': //筛选项
                if($(this).hasClass('active')){
                    $(this).removeClass('active');
                }else{
                    $(this).addClass('active').siblings().removeClass('active');
                }
                getProQueryData();
                table.reload('eiaProjectList', {
                    where: proFilterData
                });
                break;
            case 'clearQuery':
                $('#projectName,.filter-box-pro input').val('');
                $('.filter-box-pro .filter-ul .filter-item').removeClass('active');
                getProQueryData();
                break;
        };
    });

    //监听工具条
    table.on('tool(eiaProjectList)', function (obj) {
        var data = obj.data;
        var eiaProjectId = data.id;
        var gisProjectId = data.gisProjectId;
        $('#eiaProjectId').val(eiaProjectId);
        $('#tableNameId').val(eiaProjectId);
        $('#eiaTaskId').val(data.eiaTaskId);
        $('#gisProjectId').val(data.gisProjectId);
        $('#eiaLabOfferId').val(data.eiaLabOfferId);
        $('#proPlanId').val(data.proPlanId);
        if (obj.event === 'projectDetail') {    //查看
            var pageUrl = '/eia/eiaProject/eiaProjectDetail?eiaProjectId=' + eiaProjectId;
            $("#eiaProjectId").val(data.id);
            $("#tableNameId").val(data.id);
            var index = layer.open({
                title: ' ',
                type: 2,
                shade: false,
                maxmin: true,
                skin: 'larry-green',
                area: ['100%', '100%'],
                content: pageUrl,
                success: function (layero, index) {
                    var body = layer.getChildFrame('body', index);
                    body.find('').val(data.eiaClientId);
                },
                end: function () {
                    $('#eiaClientId').val("");
                    $('#eiaTaskId').val("");
                },
                min: function () {
                    $(".layui-layer-title").text("查看客户");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        }
        else if (obj.event == 'projectEdit') {
            var pageUrl = '/eia/eiaProject/eiaProjectCreate?eiaProjectId=' + eiaProjectId + '&pageType=1';
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
                    table.reload('eiaProjectList');
                    $('#eiaProjectId').val("");
                    $('#tableNameId').val("");
                    $('#eiaTaskId').val("");
                    $('#eiaLabOfferId').val("");
                },
                min: function () {

                },
                restore: function () {

                }
            });
        } else if (obj.event == 'projectGisShow') {
            pageUrl = '/eia/eiaProject/eiaGisGeoProject?eiaProjectId=' + eiaProjectId;
            var index = layer.open({
                title: ' ',
                type: 2,
                shade: false,
                maxmin: true,
                skin: 'larry-green',
                area: ['100%', '100%'],
                content: pageUrl,
                success: function (data) {
                },
                end: function () {
                    table.reload('eiaProjectList');
                    $('#eiaProjectId').val("");
                    $('#tableNameId').val("");
                    $('#eiaTaskId').val("");
                    $('#eiaLabOfferId').val("");
                },
                min: function () {

                },
                restore: function () {

                }
            });
        }
        else if (obj.event === 'projectFlow') {//流程
            var actionUrl = '/eia/eiaProjectPlan/checkProjectFlow?eiaProjectId='+eiaProjectId;
            $.post(actionUrl, data.field, function (data) {
                if (data.code == 0) {
                    pageUrl = '/eia/eiaWorkFlow/eiaWorkFlowProject';
                    var index = layer.open({
                        title: ' ',
                        type: 2,
                        shade: false,
                        maxmin: true,
                        skin: 'larry-green',
                        area: ['100%', '100%'],
                        content: pageUrl,
                        before:function(){

                        },
                        success: function (layero, index) {
                        },
                        end: function () {
                            $('#eiaProjectId').val("");
                        },
                        min: function () {
                            $(".layui-layer-title").text("项目流程");
                        },
                        restore: function () {
                            $(".layui-layer-title").text(" ");
                        }
                    });
                } else {
                    var msg = data.msg
                    layer.msg(data.msg, {icon: 2, time: 2000}, function () {
                    });
                }
            });

        }
        else if(obj.event==='changeWorkFlow'){
            pageUrl = '/eia/eiaProject/changeWorkFlow?eiaProjectId='+eiaProjectId;
            var index = layer.open({
                title: ' ',
                type: 2,
                shade: false,
                maxmin: true,
                skin: 'larry-green',
                area: ['50%', '50%'],
                content: pageUrl,
                success: function (data) {
                },
                end: function () {
                    table.reload('eiaProjectList');
                    $('#eiaProjectId').val("");
                    $('#tableNameId').val("");
                    $('#eiaTaskId').val("");
                    $('#eiaLabOfferId').val("");
                    $('#proPlanId').val("");
                },
                min: function () {

                },
                restore: function () {

                }
            });
        }
        else if (obj.event === 'certDownload') {
            var loadingIndex = layer.load(1, {time: 10*1000,shade: 0.1});
            $.ajax({
                url: "/eia/eiaContract/matchProve?eiaProjectId=" + eiaProjectId,
                type: "POST",
                data: {},
                dataType: "json",
                success: function (data) {
                    if (data.code == 0) {
                        window.location.href = "../eiaContract/exportProCert?eiaProjectId=" + eiaProjectId;
                        layer.msg('正在导出...', {icon: 16, shade: 0.01}, function () {
                            var index = parent.layer.getFrameIndex(window.name);
                            parent.layer.close(index);
                        })
                    } else {
                        layer.msg(data.msg, {icon: 2, time: 1000,shade: 0.1});
                    }
                    layer.close(loadingIndex);
                }
            });
        }
        else if (obj.event === 'projectCoverDown') {     //封皮下载
            var loadingIndex = layer.load(1, {time: 10*1000,shade: 0.1});
            $.ajax({
                url: "/eia/exportProject/checkProjectType?eiaProjectId=" + eiaProjectId,
                type: "POST",
                data: {},
                dataType: "json",
                success: function (res) {
                    if (res.code == 0) {
                        window.location.href = "../exportProject/exportProject?eiaProjectId=" + eiaProjectId;
                        layer.msg('正在导出...', {icon: 16, shade: 0.01}, function () {
                            var index = parent.layer.getFrameIndex(window.name);
                            parent.layer.close(index);
                        })
                    } else {
                        layer.msg(res.msg, {icon: 2, time: 1000,shade: 0.1});
                    }
                    layer.close(loadingIndex);
                }
            });
        }
    });


    /**合同**/
        //恢复上次查询数据显示
    var regainConQueryShow = function () {
            var conAdvancedState = $('#conAdvancedState').val();
            var conQueryData = JSON.parse($('#conQueryData').val());
            if(conAdvancedState == '1'){ //高级查询
                //打开高级查询面板
                $('.filter-box-con').attr('state','1').css('display','block');
                $('#conAdvancedState').val(1);
                //赋值
                for(var name in conQueryData){
                    switch (name){
                        case 'contractTrust':
                        case 'ifAgency':
                        case 'ifArc':
                            var dataVal = conQueryData[name];
                            if(dataVal){
                                $(".filter-box-con ."+name+"Filter .filter-item:contains('"+dataVal+"')").addClass('active');
                            }
                            break;
                        case 'startDate':
                        case 'endDate':
                            laydate.render({
                                elem: '#'+name,
                                value: conQueryData[name]
                            });
                            break;
                        case 'contractType':
                            //下拉树 合同类型
                            $("#contractType").dropDownForZ({
                                url:'/eia/eiaDomainCode/getTree?domain=' + "CONTRACT_TYPE",
                                width:'99%',
                                height:'350px',
                                disableParent: true,
                                selecedSuccess:function(data){}
                            });
                            $("#contractType").val(conQueryData[name]);
                            break;
                        default:
                            $('#'+name).val(conQueryData[name]);
                            break;

                    }
                }

            }else{//普通查询
                $('#contractName').val(conQueryData.contractName);
            }
        };
    var contractData = [
        {
            "contractNo": "TL-1",
            "contractName": "合同一",
            "eiaClientName": "客户一",
            "contractType": "合同类型一",
            "contractMoney": "100",
            "profitMargin": "10%",
            "contractDate": "2011-1-1",
            "taskNo": "11111",
            "taskName": "任务一",
            "inputDept": "部门一",
            "inputUser": "张一"
        },
        {
            "contractNo": "TL-2",
            "contractName": "合同二",
            "eiaClientName": "客户二",
            "contractType": "合同类型二",
            "contractMoney": "200",
            "profitMargin": "20%",
            "contractDate": "2012-2-2",
            "taskNo": "2222",
            "taskName": "任务二",
            "inputDept": "部门二",
            "inputUser": "张二"
        }
    ];
    table.render({
        id: 'eiaContractList',
        elem: '#eiaContractList',
        url: '/eia/eiaContract/getEiaContractDataList',
        toolbar: '#conTableTopTmp',
        defaultToolbar:['filter', 'print', 'exports'],
        cols: [[
            {fixed: 'left', title: '序号', width: '6%', align: "center", templet: "#indexTable"},
            {field: 'contractNo', width: '12%', title: '合同编号', align: "center"},
            {field: 'contractName', width: '25%', title: '合同名称', align: "center"},
            {field: 'eiaClientName', width: '20%', title: '客户名称', align: "center"},
            {field: 'contractType', width: '15%', title: '合同类型', align: "center"},
            {field: 'contractMoney', width: '15%', title: '合同金额（万元）', align: "center"},
            {field: 'profitMargin', width: '10%', title: '利润率', align: "center"},
            {field: 'contractDate', width: '15%', title: '合同时间', align: "center"},
            {field: 'taskNo', width: '15%', title: '任务编号', align: "center"},
            {field: 'taskName', width: '20%', title: '任务名称', align: "center"},
            {field: 'inputDept', width: '15%', title: '录入部门', align: "center"},
            {field: 'inputUser', width: '10%', title: '录入人', align: "center"},
            {fixed: 'right', title: '操作', width: '20%', align: "center", toolbar: '#contractTool', align: "center"}
        ]],
        page: true,
        even: true,
        limit: 10,
        done: function (res, curr, count) {
            regainConQueryShow();
        }
    });
    //监听合同表格工具条
    table.on('tool(eiaContractList)', function (obj) {
        var data = obj.data;
        //$('#eiaContractId').val(data.id)
        if (obj.event === 'eiaEdit') {    //编辑
            var pageUrl = '/eia/eiaContract/eiaContractCreate?pageType=1&eiaContractId=' + data.id;

            var index = layer.open({
                title: ' ',
                type: 2,
                shade: false,
                maxmin: true,
                skin: 'larry-green',
                area: ['100%', '100%'],
                content: pageUrl,
                success: function (layero, index) {
                    var body = layer.getChildFrame('body', index);
                    body.find('#contractId').val(data.id);
                },
                end: function () {
                    // table.reload('eiaOfferList');
                    $('#contractId').val("");
                },
                min: function () {
                    $(".layui-layer-title").text("编辑合同");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        }
        else if (obj.event === 'eiaDel') {    //删除
            layer.confirm('确定要删除该合同吗?', {icon: 3}, function (index) {
                var loadingIndex = layer.load(1, {time: 10*1000,shade: 0.1});
                $.post("../eiaContract/eiaContractDel", {eiaContractId: data.id}, function (data) {
                    if (data.code == 0) {
                        layer.msg('删除成功！', {icon: 1, time: 1500,shade: 0.1}, function () {
                            obj.del();
                        });
                    } else {
                        if (data.msg) {
                            layer.msg("处理失败！<br/><div style='color:red;'>错误信息：" + data.msg + "</div>", {
                                icon: 2,
                                time: 1500
                            });
                        } else {
                            layer.msg('删除失败！', {icon: 2, time: 1500,shade: 0.1});
                        }
                    }
                    layer.close(loadingIndex);
                });
            }, function (index) {
                //取消
            });


        }
        else if (obj.event === 'eiaCheck') {    //查看
            var pageUrl = '/eia/eiaContract/eiaContractDetail?eiaContractId=' + data.id;
            var index = layer.open({
                title: ' ',
                type: 2,
                shade: false,
                maxmin: true,
                skin: 'larry-green',
                area: ['100%', '100%'],
                content: pageUrl,
                success: function (layero, index) {
                    var body = layer.getChildFrame('body', index);
                    body.find('#contractId').val(data.id);
                },
                end: function () {
                    $('#offerId').val("");
                },
                min: function () {
                    $(".layui-layer-title").text("查看合同");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        }
        else if (obj.event === 'contractExport') {    //下载
            var contractId = data.id;
            $.post("../exportContract/checkProductType", {contractId: contractId},function (result) {
                if (result.data == "noStaff") {
                    layer.msg('员工资料需补充，请联系人力部门更新资料！', {icon: 2, time: 1500});
                } else if (result.data){
                    layer.msg('正在导出...', {icon: 16, shade: 0.01});
                    window.location.href = "../exportContract/downloadContract?contractId=" + contractId;
                } else {
                    layer.msg('尚无对应合同模板，不能导出！', {icon: 2, time: 1500});
                }
            });
        } else if (obj.event === 'contractSub') {
            var eiaContractId = data.id;
            console.log(data)
            var loadingIndex = layer.load(1, {time: 10*1000,shade: 0.1});
            layer.confirm('是否提交合同流程?', {icon: 3}, function (index) {
                $.post("../eiaContract/eiaContractSub", {eiaContractId: eiaContractId}, function (result) {
                    if (result.code == 0) {
                        table.reload('eiaContractList');
                        layer.msg("合同提交成功！", {icon: 1, time: 1500})
                    }else{
                        layer.msg(result.msg, {icon: 2, time: 1500})
                    }
                    layer.close(loadingIndex);
                });
            }, function (index) {
                //取消
            });
        }else if (obj.event === 'contractFlow') {    //流程
            pageUrl = '/eia/eiaWorkFlow/eiaWorkFlowTrans';
            $('#tableNameId').val(data.id);
            $('#tableName').val("EiaContract");
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
                    $('#tableNameId').val("");
                    $('#tableName').val("");
                },
                min: function () {
                    $(".layui-layer-title").text("合同流程");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        }
    });
    //获取查询数据以及暂存查询数据
    var getConQueryData = function () {
        conFilterData.contractName = $('#contractName').val();
        conFilterData.clientName = $('#clientName').val();
        conFilterData.contractType = $('#contractType').val();
        conFilterData.conStartMoney = $('#conStartMoney').val();
        conFilterData.conEndMoney = $('#conEndMoney').val();
        conFilterData.startDate = $('#startDate').val();
        conFilterData.endDate = $('#endDate').val();

        $('.filter-box-con .filter-ul .filter-li').each(function (index, elem) {
            var curFilter = $(elem).attr('filterName');
            var curVal = $(elem).find('.filter-item.active span').text();
            conFilterData[curFilter] = curVal;

        });
        var conAdvancedState = $('.filter-box-con').attr('state');
        $('#conQueryData').val(JSON.stringify(conFilterData));
        $('#conAdvancedState').val(conAdvancedState);

        console.log(conFilterData);
    };
    //监听头部工具栏事件
    //监听事件
    table.on('toolbar(eiaContractList)', function(obj){
        switch(obj.event){
            case 'contractSelect':
                getConQueryData();
                table.reload('eiaContractList', {
                    where: conFilterData
                });
                break;
            case 'contractAdd':
                var pageUrl = '/eia/eiaContract/eiaContractCreate?pageType=0';
                var index = layer.open({
                    title: ' ',
                    type: 2,
                    shade: false,
                    maxmin: true,
                    skin: 'larry-green',
                    area: ['100%', '100%'],
                    content: pageUrl,
                    success: function (layero, index) {
                        var body = layer.getChildFrame('body', index);
                    },
                    end: function () {

                    },
                    min: function () {
                        $(".layui-layer-title").text("新增合同");
                    },
                    restore: function () {
                        $(".layui-layer-title").text(" ");
                    }
                });
                break;
            case 'highSelect':
                var curSate = $('.filter-box-con').attr('state');
                switch (curSate){
                    case '0': //打开
                        $('.filter-box-con').attr('state','1').css('display','block');
                        $('#conAdvancedState').val(1);
                        laydate.render({
                            elem: '#startDate'
                        });

                        laydate.render({
                            elem: '#endDate'
                        });
                        //下拉树 合同类型
                        $("#contractType").dropDownForZ({
                            url:'/eia/eiaDomainCode/getTree?domain=' + "CONTRACT_TYPE",
                            width:'99%',
                            height:'350px',
                            disableParent: true,
                            selecedSuccess:function(data){}
                        });
                        break;
                        break;
                    case '1': //关闭
                        $('.filter-box-con').attr('state','0').css('display','none');
                        $('.filter-box-con input').val('');
                        $('.filter-box-con .filter-ul .filter-item').removeClass('active');
                        getConQueryData();
                        break;
                }
                break;
            case 'filterItem': //筛选项
                if($(this).hasClass('active')){
                    $(this).removeClass('active');
                }else{
                    $(this).addClass('active').siblings().removeClass('active');
                }
                getConQueryData();
                table.reload('eiaContractList', {
                    where: conFilterData
                });
                break;
            case 'clearQuery':
                $('#contractName,.filter-box-con input').val('');
                $('.filter-box-con .filter-ul .filter-item').removeClass('active');
                getConQueryData();

                break;
        }
    });
    /****内审单待办***/
    ajaxBox('/eia/eiaBoard/getEiaWorkFlowBusiDataList?tabName=EiaProjectExplore', null, function (res) {
        if (res.code === 0) {
            $("#eiaProjectExploreNums").html(res.count)
        }
    });
    /****合同报价待办***/
    ajaxBox('/eia/eiaBoard/getEiaWorkFlowBusiDataList?tabName=EiaContract', null, function (res) {
        if (res.code === 0) {
            $("#eiaContractNums").html(res.count)
        }
    });
    /****监测待办***/
    ajaxBox('/eia/eiaBoard/getEiaWorkFlowBusiDataList?tabName=EiaLabOffer', null, function (res) {
        if (res.code === 0) {
            $("#eiaLabOfferNums").html(res.count)
        }
    });
    /****项目待办***/
    ajaxBox('/eia/eiaBoard/getEiaWorkFlowBusiDataList?tabName=EiaProject', null, function (res) {
        if (res.code === 0) {
            $("#eiaProjectNums").html(res.count)
        }
    });
    /****资质待办***/
    ajaxBox('/eia/eiaBoard/getEiaWorkFlowBusiDataList?tabName=EiaCert', null, function (res) {
        if (res.code === 0) {
            $("#eiaCertNums").html(res.count)
        }
    });
    /****印章待办***/
    ajaxBox('/eia/eiaBoard/getEiaWorkFlowBusiDataList?tabName=EiaStamp', null, function (res) {
        if (res.code === 0) {
            $("#eiaStampNums").html(res.count)
        }
    });
    /****已办事项***/
    ajaxBox('/eia/eiaBoard/getEiaWorkFlowBusiDataList?workType=1', null, function (res) {
        if (res.code === 0) {
            $("#workFlowComBusiNums").html(res.count)
        }
    });
});