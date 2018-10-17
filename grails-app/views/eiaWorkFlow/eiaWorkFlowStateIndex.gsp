<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>流程配置</title>
    <meta name="layout" content="main"/>
    <asset:stylesheet src="/eiaWorkFlow/eiaWorkFlow.css"/>
    <asset:javascript src="/eiaWorkFlow/eiaWorkFlowStateIndex.js"/>
    <style>
        .layui-table-view {
            margin: 0px 0 25px;
        }
    </style>
</head>

<body>
<div class="layui-fluid larry-wrapper pt0 pb0">
    <div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12 flow-state-box">
        <table id="flowStateList" lay-filter="flowStateList"></table>
    </div>
    <input type="hidden" id="tableName" name="tableName">
    <input type="hidden" id="tableNameId" name="tableNameId">
</div>
<script type="text/html" id="indexTable">
<span>{{d.LAY_TABLE_INDEX + 1}}</span>
</script>
</body>
</html>