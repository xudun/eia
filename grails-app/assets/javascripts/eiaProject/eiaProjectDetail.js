layui.use(['jquery', 'form', 'element','table'], function(){
    var $ = layui.jquery,
        form = layui.form,
        element = layui.element;
        table = layui.table;
    var $speInpContainers = $('.dynInputs .layui-col-xs6');
    var $dynMoneyContainers = $('.dynMoneyInputs .layui-col-xs6');

    var eiaProjectId = parent.$('#eiaProjectId').val();
    var eiaTaskId = parent.$('#eiaTaskId').val();
    if(!eiaProjectId){
        eiaProjectId = params.eiaProjectId
    }
    //渲染固定数据
    $.ajax({
        url: "/eia/eiaProject/getEiaProjectDataMap?eiaProjectId=" + eiaProjectId,
        type:"POST",
        data:{},
        dataType: "json",
        async: true,
        success: function (data) {
            $('#projectName').text(data.data.projectName);
            if(!eiaTaskId){
                eiaTaskId = data.data.eiaTaskId
            }
            $('#buildArea').text(data.data.buildArea);
            $('#competentDept').text(data.data.competentDept);
            $('#fileType').text(data.data.fileType.name);
            $('#fileTypehidden').val(JSON.stringify(data.data.fileType));
            $('#dutyUser').text(data.data.dutyUser);
            $('#taskName').text(data.data.taskName);
            $('#taskNo').text(data.data.taskNo);
            $('#contractName').text(data.data.contractName);
            $('#coordEast').text(data.data.coordEast);
            $('#contractNo').text(data.data.contractNo);
            $('#coordStartEast').text(data.data.coordStartEast);
            $('#coordEndEast').text(data.data.coordEndEast);
            $('#coordNorth').text(data.data.coordNorth);
            $('#coordStartNorth').text(data.data.coordStartNorth);
            $('#coordEndNorth').text(data.data.coordEndNorth);
            $('#projectMoney').find('.cost-num').text(dotFour(data.data.projectMoney));
            $.ajax({
                url:"../eiaTask/eiaTaskDataMap?taskId="+eiaTaskId,
                type:"POST",
                data:{},
                dataType: "json",
                success: function (data) {
                    $('#taskName').text(data.data.taskName);
                    $('#taskLeaderDept').text(data.data.taskLeaderDept);
                    $('#busiType').text(data.data.busiType);


                    //根据下拉树选中的code更改#productFunction的label文字
                    var curParentCode = JSON.parse($('#fileTypehidden').val()).code;
                    if(curParentCode.indexOf('EPC_GH')!==-1){
                        $('#productFunction').closest('.layui-form-item').find('.label-txt').text('功能定位');
                    }
                }
            });
            table.render({
                id: 'eiaTaskList',
                elem: '#eiaTaskList',
                url:"../eiaTask/eiaTaskAssignDetailsList?taskId="+eiaTaskId,
                toolbar: '#tableTaskTmp',
                defaultToolbar:['filter', 'print', 'exports'],
                cols: [[
                    {fixed: 'left', title: '序号',width:'5%',align: "center",templet: "#indexTable"},
                    {field:'taskAssignDept',width:'35%', title: '人员所属部门',align: "center"},
                    {field:'taskAssignRole',width:'35%', title: '所属任务角色',align: "center"},
                    {field:'taskAssignUser',width:'30%', title: '人员名称',align: "center"}
                ]],
                page: true,
                even: true,
                limit: 10
            });
        }
    });

    //渲染可变数据
    $.ajax({
        url: "/eia/eiaEnvProject/getEnvProDataMap?eiaProjectId=" + eiaProjectId,
        type:"POST",
        data:{},
        dataType: "json",
        async: true,
        success: function (data) {
            $speInpContainers.empty();
            var con_index = 0;
            for(var name in data.data){
                if(inputs[name]){
                    inputs[name].show.call(this,data.data[name], $speInpContainers.eq(con_index++%2));
                }
            }
            form.render('select');
        }
    });

    //渲染金额子项数据
    $.ajax({
        url:"../eiaProject/getPropMoneyShow",
        type:"POST",
        data:{"eiaProjectId":eiaProjectId},
        dataType: "json",
        async: true,
        success: function (res) {
            var data = res.data
            $dynMoneyContainers.empty();
            var con_index = 0;
            for(var name in data){
                moneyFillInputs[name].show.call(this,data[name], $dynMoneyContainers.eq(con_index++%2));
            }
        }
    });
    //监听合同表格工具条
    table.on('tool(eiaContractList)', function (obj) {
        var data = obj.data;
        //$('#eiaContractId').val(data.id)
        if (obj.event === 'eiaCheck') {    //编辑
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
    });


    //渲染人员表格
    table.render({
        id: 'eiaContractList',
        elem: '#eiaContractList',
        url: '/eia/eiaContract/getEiaContractDataList?eiaProjectId='+eiaProjectId,
        toolbar: '#tableContractTmp',
        defaultToolbar:['filter', 'print', 'exports'],
        cols: [[
            {fixed: 'left', title: '序号',width:'5%',align: "center",templet: "#indexTable"},
            {field: 'contractNo', width: '15%', title: '合同编号', align: "center"},
            {field: 'contractName', width: '25%', title: '合同名称', align: "center"},
            {field: 'contractType', width: '15%', title: '合同类型', align: "center"},
            {field: 'contractMoney', width: '15%', title: '合同金额（万元）', align: "center"},
            {field: 'contractDate', width: '15%', title: '合同时间', align: "center"},
            {field: 'taskNo', width: '15%', title: '任务编号', align: "center"},
            {field: 'taskName', width: '20%', title: '任务名称', align: "center"},
            {field: 'inputDept', width: '15%', title: '录入部门', align: "center"},
            {field: 'inputUser', width: '10%', title: '录入人', align: "center"},
            {fixed: 'right', title: '操作', width: '10%', align: "center", toolbar: '#contractTool', align: "center"}
        ]],
        page: true,
        even: true,
        limit: 10
    });
    //监听合同表格工具条
    table.on('tool(eiaContractList)', function (obj) {
        var data = obj.data;
        //$('#eiaContractId').val(data.id)
        if (obj.event === 'eiaCheck') {    //编辑
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
    });


});