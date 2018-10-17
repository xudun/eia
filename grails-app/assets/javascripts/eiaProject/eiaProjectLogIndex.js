layui.use(['jquery', 'layer','form', 'table'], function () {
    var $ = layui.jquery,
        layer = layui.layer,
        form = layui.form,
        table = layui.table;

    //渲染表格
    table.render({
        id: 'eiaProjectLogList',
        elem: '#eiaProjectLogList',
        url: '/eia/eiaProjectLog/getEiaProjectLogDataList',
        toolbar: '#tableTopTmp',
        defaultToolbar:['filter', 'print', 'exports'],
        cols: [[
            {fixed: 'left', title: '序号', width: '6%', align: "center", templet: "#indexTable"},
            {field: 'projectNo', width: '15%', title: '项目编号', align: "center"},
            {field: 'projectNameEd', width: '30%', title: '项目名称', align: "center"},
            {field: 'buildAreaEd', width: '30%', title: '建设地点', align: "center"},
            {field: 'fileTypeChild', width: '15%', title: '文件类型', align: "center"},
            {field: 'projectMoney', width: '15%', title: '金额(万元)', align: "center"},
            {field: 'inputDept', width: '15%', title: '录入部门', align: "center"},
            {field: 'inputUser', width: '10%', title: '录入人', align: "center"},
            {field: 'dutyUserEd', width: '10%', title: '项目负责人', align: "center"},
            {fixed: 'right', title: '操作', width: '10%', align: "center", toolbar: '#mlTool', align: "center"}
        ]],
        page: true,
        even: true,
        limit: 10
    });
    //高级查询
    form.on('submit(query)', function () {
        table.reload("eiaProjectLogList",{
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
    table.on('tool(eiaProjectLogList)', function (obj) {
        var data = obj.data;
        var eiaProjectLogId = data.id;
        $('#eiaProjectLogId').val(eiaProjectLogId);
        if (obj.event === 'logDetail') {    //显示
            var pageUrl = '/eia/eiaProjectLog/eiaProjectLogDetail?eiaProjectLogId=' + eiaProjectLogId;
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
                    table.reload("eiaProjectLogList");
                    $('#eiaProjectLogId').val("");
                },
                min: function () {
                    $(".layui-layer-title").text("项目变更详情");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        }
    });

    //监听头部工具栏事件
    //监听事件
    table.on('toolbar(eiaProjectLogList)', function(obj){
        switch(obj.event){
            case 'getSelect':
                var projectName = $("#projectName").val();
                table.reload("eiaProjectLogList", {
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
            case 'logAdd':
                var pageUrl = '../eiaProjectLog/eiaProjectLogSelect';
                var index = layer.open({
                    title:' ',
                    type: 2,
                    shade: false,
                    maxmin: true,
                    skin: 'larry-green',
                    area: ['100%', '100%'],
                    content: pageUrl,
                    end:function(){
                    },
                    min:function(){
                        $(".layui-layer-title").text("选择项目");
                    },
                    restore:function(){
                        $(".layui-layer-title").text(" ");
                    }
                });
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