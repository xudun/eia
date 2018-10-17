layui.use(['jquery', 'table'], function() {
    var $ = layui.jquery,
        table = layui.table;
    var staffId = $("#staffId").val();
    //渲染表格
    table.render({
        id: 'eiaHrEvalDetailList',
        elem: '#eiaHrEvalDetailList',
        url:'/eia/eiaHrEvalScoreDetail/getEiaHrEvalEmpDetailData?staffId='+staffId,
        toolbar: ' ',
        defaultToolbar:['filter', 'print', 'exports'],
        cols: [[
            {type:'numbers', title: '序号'},
            {field:'staffName', title: '姓名',align: "center"},
            {field:'assessmentMonth', title: '考核月份',align: "center"},
            {field:'orgName', title: '所属部门',align: "center"},
            {field:'teamSpirit', title: '团队精神',align: "center"},
            {field:'leadership', title: '领导能力',align: "center"},
            {field:'proAbility', title: '专业能力',align: "center"},
            {field:'inputUser', title: '打分人',align: "center"}
        ]],
        page: true,
        even: true,
        limit: 10
    });
});