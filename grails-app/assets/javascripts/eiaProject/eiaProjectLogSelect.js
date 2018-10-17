layui.use(['jquery', 'layer','form', 'table'], function () {
    var $ = layui.jquery,
        layer = layui.layer,
        form = layui.form,
        table = layui.table;

    //渲染表格
    table.render({
        id: 'eiaProjectList',
        elem: '#eiaProjectList',
        url: '/eia/eiaProject/getEiaProjectDataList',
        toolbar: '#tableTopTmp',
        defaultToolbar:['filter', 'print', 'exports'],
        cols: [[
            {fixed: 'left', title: '序号', width: '6%', align: "center", templet: "#indexTable"},
            {field: 'projectNo', width: '15%', title: '项目编号', align: "center"},
            {field: 'projectName', width: '30%', title: '项目名称', align: "center"},
            {field: 'buildArea', width: '30%', title: '建设地点', align: "center"},
            {field: 'fileTypeChild', width: '15%', title: '文件类型', align: "center"},
            {field: 'projectMoney', width: '15%', title: '项目价格', align: "center"},
            {field: 'inputDept', width: '15%', title: '录入部门', align: "center"},
            {field: 'inputUser', width: '10%', title: '录入人', align: "center"},
            {field: 'dutyUser', width: '10%', title: '项目负责人', align: "center"},
            {fixed: 'right', title: '操作', width: '8%', align: "center", toolbar: '#mlTool', align: "center"}
        ]],
        page: true,
        even: true,
        limit: 10
    });
    //高级查询
    form.on('submit(query)', function () {
        table.reload("eiaProjectList",{
            where : {
                key : eiaProjectKey()
            }, page: {
                curr: 1
            }
        });
        return false;
    });
    //高级查询数据项
    function eiaProjectKey() {
        var fileType = $("#fileType").val();
        var buildArea = $("#buildArea").val();
        var key = {
            fileType: fileType,
            buildArea: buildArea,
        };
        return key;
    }
    //监听工具条
    table.on('tool(eiaProjectList)', function (obj) {
        var data = obj.data;
        var eiaProjectId = data.id;
        $('#eiaProjectId').val(eiaProjectId);
        $('#eiaTaskId').val(data.eiaTaskId);
        if (obj.event === 'projectEdit') {    //编辑
            var pageUrl = '/eia/eiaProjectLog/eiaProjectLogCreate?eiaProjectId=' + eiaProjectId;
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
                    table.reload("eiaProjectList");
                    $('#eiaProjectId').val("");
                },
                min: function () {
                    $(".layui-layer-title").text("编辑项目");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        }
    });

    //监听头部工具栏事件
    //监听事件
    table.on('toolbar(eiaProjectList)', function(obj){
        switch(obj.event){
            case 'getSelect':
                var projectName = $("#projectName").val();
                table.reload("eiaProjectList", {
                    where: {
                        projectName: projectName
                    }
                });
                break;
            case 'highSelect':
                var curState = $(this).attr('state');
                switch (curState){
                    case '0':
                        $(this).attr('state','1');
                        queryOpen();
                        break;
                    case '1':
                        $(this).attr('state','0');
                        queryClose();
                        break;
                }
                break;
        }
    });
});
function queryOpen() {
    $("#advanced-query").removeClass("display-none");
    $("#open").addClass("display-none");
    $("#close").removeClass("display-none");
}
function queryClose() {
    $("#advanced-query").addClass("display-none");
    $("#open").removeClass("display-none");
    $("#close").addClass("display-none");
}