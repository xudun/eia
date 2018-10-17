layui.use(['jquery', 'table'], function(){
    var $ = layui.jquery,
        table = layui.table;
    var eiaTaskLogId = parent.$('#eiaTaskLogId').val();
    //渲染基本数据
    $.ajax({
        url:"../eiaTaskLog/eiaTaskLogDataMap?eiaTaskLogId="+eiaTaskLogId,
        type:"POST",
        data:{},
        dataType: "json",
        success: function (result) {
            $("#taskNo").text(result.data.taskNo);
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
            if (result.data.taskLeaderDept != result.data.taskLeaderDeptEd) {
                $("#taskLeaderDept").text(result.data.taskLeaderDeptEd).css("color", "red");
                $("#taskLeaderDeptShow").removeClass("display-none");
                $("#taskLeaderDeptShow").on("mouseenter",function(){
                    layer.tips("修改前内容: " +(result.data.taskLeaderDept== null?"":result.data.taskLeaderDept ) + "</br>" + "修改人: " + result.data.logInputUser + "</br>" + "修改日期: " + result.logInputDate, '#taskLeaderDeptShow', {
                        area: ['auto', 'auto'],
                        tips: [1, '#30b5ff'],
                        time: 2000
                    });
                });
            } else {
                $("#taskLeaderDept").text(result.data.taskLeaderDeptEd);
            }
            if (result.data.busiType != result.data.busiTypeEd) {
                $("#busiType").text(result.data.busiTypeEd).css("color", "red");
                $("#busiTypeShow").removeClass("display-none");
                $("#busiTypeShow").on("mouseenter",function(){
                    layer.tips("修改前内容: " +(result.data.busiType== null?"":result.data.busiType ) + "</br>" + "修改人: " + result.data.logInputUser + "</br>" + "修改日期: " + result.logInputDate, '#busiTypeShow', {
                        area: ['auto', 'auto'],
                        tips: [1, '#30b5ff'],
                        time: 2000
                    });
                });
            } else {
                $("#busiType").text(result.data.busiTypeEd);
            }
        }
    });

    //渲染人员表格
    table.render({
        id: 'eiaTaskAssignLogList',
        elem: '#eiaTaskAssignLogList',
        url:"../eiaTaskLog/eiaTaskAssignLogDetailsList?eiaTaskLogId="+eiaTaskLogId,
        toolbar: '#taskAssignUserTmp',
        defaultToolbar:['filter', 'print', 'exports'],
        cols: [[
            {field:'state', width:'25%', title: '状态', align: "center", templet: '#stateTpl'},
            {field:'taskAssignDept', width:'25%', title: '人员所属部门', align: "center", templet: '#deptTpl'},
            {field:'taskAssignRole', width:'25%', title: '所属任务角色', align: "center", templet: '#roleTpl'},
            {field:'taskAssignUser', width:'25%', title: '人员名称', align: "center", templet: '#userTpl'}
        ]],
        page: true,
        even: true,
        limit: 10
    });
    function riskContentTips(lhCrWorkId,riskStatus){
        alert(lhCrWorkId)
    }
});