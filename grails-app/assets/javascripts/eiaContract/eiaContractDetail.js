layui.use(['jquery', 'form','table'], function(){
    var $ = layui.jquery,
        form = layui.form;
        table = layui.table;
    var eiaContractId = $('#contractId').val();
    var taskId = $('#taskId').val();
    var $dynMoneyContainers = $('.dynMoneyInputs .layui-col-xs6');
    //回显数据
    $.ajax({
        url:"/eia/eiaContract/getEiaContractDetail?eiaContractId=" + eiaContractId,
        type:"POST",
        data:{},
        dataType: "json",
        success: function (result) {
            if(result.code == 0){
                var data = result.data;
                $("#contractNo").text(data.contractNo);
                $("#contractTrust").text(data.contractTrust);
                $("#contractDate").text(result.contractDate);
                $("#contractMoney").find('.cost-num').text(data.contractMoney);
                $("#eiaClientName").text(data.eiaClientName);
                $("#clientAddress").text(data.clientAddress);
                $("#contactName").text(data.contactName);
                $("#contactPhone").text(data.contactPhone);
                $("#contractName").text(data.contractName);
                $("#ownerClientName").text(data.ownerClientName);
                $("#ownerClientAddress").text(data.ownerClientAddress);
                $("#ownerContactName").text(data.ownerContactName);
                $("#ownerContactPhone").text(data.ownerContactPhone);
                $("#contractType").text(data.contractType).attr('code',data.contractTypeCode);
                $("#taskName").text(data.taskName);
                $("#taskNo").text(data.taskNo);
                $("#province").text(result.province);
                $("#contractUse").text(result.contractUse);

                //渲染金额子项数据
                $.ajax({
                    url:"/eia/eiaContract/getContractMoney",
                    type:"POST",
                    data:{eiaContractId: eiaContractId},
                    dataType: "json",
                    async: true,
                    success: function (result) {
                        var data = result.data;
                        console.log(data);
                        $dynMoneyContainers.empty();
                        var con_index = 0;
                        for(var name in data){
                            moneyFillInputs[name].show.call(this,data[name], $dynMoneyContainers.eq(con_index++%2));
                        }
                        if($("#contractType").attr('code') == "CD"){
                            $('#enviroMonitoringFee').closest('.layui-form-item').find('.layui-form-label').text('地勘加监测费用');
                        }
                    }
                });
            }else{

            }
        }
    });
    //渲染项目表格
    if(!eiaContractId){
        eiaContractId =-1;
    }
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

    table.render({
        id: 'eiaProjectList',
        elem: '#eiaProjectList',
        url: '/eia/eiaProject/getEiaProjectDataList?eiaContractId='+eiaContractId,
        toolbar: '#tableTopTmp',
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
    table.render({
        id: 'eiaTaskList',
        elem: '#eiaTaskList',
        url:"../eiaTask/eiaTaskAssignDetailsList?taskId="+taskId,
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
});