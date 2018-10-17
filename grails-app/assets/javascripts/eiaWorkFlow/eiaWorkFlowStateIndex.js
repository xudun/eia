/**
 *Created by HSH on 2018/7/6 15:27
 */

layui.use(['jquery', 'layer', 'table', 'element'], function () {
    var $ = layui.jquery,
        layer = layui.layer,
        table = layui.table,
        element = layui.element;
    var params= getParamByUrl(window.location.href);
    var tableName = params.utilTableName;
    var tableNameId ;
    if(tableName == "EiaProject"){
        tableNameId = params.eiaProjectId
    }else if(tableName == "EiaContract"){
        tableNameId = params.eiaContractId
    }else if(tableName == "EiaCert"){
        tableNameId = params.eiaCertId
    }
    /**流程状态:**/
        //渲染表格
    var renderFlowTable = function () {
            table.render({
                id: 'flowStateList',
                elem: '#flowStateList',
                where: {tableName: tableName, tableNameId: tableNameId},
                url: request_url_root + "/eiaWorkFlowBusiLog/getEiaWorkFlowBusiLogDataList",
                toolbar: ' ',
                defaultToolbar:['filter', 'print', 'exports'],
                cols: [[
                    {fixed: 'left', title: '序号', width: "6%", align: "center", templet: "#indexTable"},
                    {field: 'workFlowName', width: '30%', title: '流程名称', align: "center"},
                    {field: 'nodesName', width: "20%", title: '节点名称', align: "center"},
                    {field: 'inputUser', width: '12%', title: '操作人', align: "center"},
                    {field: 'processName', width: '12%', title: '操作状态', align: "center"},
                    {field: 'approvalDate', width: '20%', title: '操作时间', align: "center"}
                ]],
                page: true,
                even: true,
                limit: 10
            });
        };
    renderFlowTable();
});