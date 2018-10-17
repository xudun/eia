layui.use(['jquery', 'table'], function(){
    var $ = layui.jquery,
        table = layui.table;
    var taskId = parent.$('#taskId').val();
    //渲染基本数据
    $.ajax({
        url:"../eiaTask/eiaTaskDataMap?taskId="+taskId,
        type:"POST",
        data:{},
        dataType: "json",
        success: function (data) {
            $('#taskName').text(data.data.taskName);
            $('#taskLeaderDept').text(data.data.taskLeaderDept);
            $('#busiType').text(data.data.busiType);
        }
    });

    //渲染人员表格
    table.render({
        id: 'taskAssignUserList',
        elem: '#taskAssignUserList',
        url:"../eiaTask/eiaTaskAssignDetailsList?taskId="+taskId,
        toolbar: '#taskAssignUserTmp',
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
    //渲染人员表格
    table.render({
        id: 'eiaContractList',
        elem: '#eiaContractList',
        url: '/eia/eiaContract/getEiaContractDataList?eiaTaskId='+taskId,
        toolbar: '#contractTmp',
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
    //渲染项目表格
    if(!taskId){
        taskId =-1;
    }
    table.render({
        id: 'eiaProjectList',
        elem: '#eiaProjectList',
        url: '/eia/eiaProject/getEiaProjectDataList?eiaTaskId='+taskId,
        toolbar: '#projectTmp',
        defaultToolbar:['filter', 'print', 'exports'],
        cols: [[
            {fixed: 'left', title: '序号',width:'5%',align: "center",templet: "#indexTable"},
            {field: 'projectNo', width: '15%', title: '项目编号', align: "center"},
            {field: 'projectName', width: '30%', title: '项目名称', align: "center"},
            {field: 'buildArea', width: '30%', title: '建设地点', align: "center"},
            {field: 'fileTypeChild', width: '15%', title: '文件类型', align: "center"},
            {field: 'projectMoney', width: '15%', title: '项目价格', align: "center"},
            {field: 'inputDept', width: '15%', title: '录入部门', align: "center"},
            {field: 'inputUser', width: '10%', title: '录入人', align: "center"},
            {field: 'dutyUser', width: '10%', title: '项目负责人', align: "center"},
            {fixed: 'right', title: '操作', width: '10%', align: "center", toolbar: '#projectTool', align: "center"}
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
    //监听项目表格工具条
    table.on('tool(eiaProjectList)', function (obj) {
        var data = obj.data;
        //$('#eiaContractId').val(data.id)
        if (obj.event === 'eiaCheck') {    //编辑
            pageUrl = '/eia/eiaProject/eiaProjectDetail?eiaProjectId=' + data.id;
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
                    table.reload("eiaProjectList");
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
    });
});