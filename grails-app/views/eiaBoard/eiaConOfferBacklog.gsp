<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>合同报价待办</title>
    <meta name="layout" content="main"/>
    <asset:stylesheet src="/eiaBoard/eiaTaskBoard.css"/>
    <asset:javascript src="/eiaBoard/eiaConOfferBacklog.js"/>
</head>

<body>
<div class="layui-fluid larry-wrapper pt0">

    <input type="hidden" id="tableNameId" name="tableNameId" value="">
    <input type="hidden" id="tableName" name="tableName" value="">

    <!--列表-->
    <div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
        <table id="eiaContractList" lay-filter="eiaContractList"></table>
        <input type="hidden" id="proWorkFlowContractTitle" value="">
    </div>
</div>

<script type="text/html" id="indexTable">
<span>{{d.LAY_TABLE_INDEX + 1}}</span>
</script>
<script type="text/html" id="eiaWorkFlowListTool">
<div class="layui-btn-group">
    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaEdit"><i class="larry-icon">&#xea21;</i></a>
</div>
<!--合同页签的查询框-->
</script>
    <script type="text/html" id="tableTopTmp">
        <div class="layui-inline">
            <div class="layui-input-inline">
                <input type="text" name="workFlowName" value="" id="workFlowContractTitle" placeholder="审批标题、创建人" class="layui-input larry-search-input">
            </div>
        </div>
        <div class="layui-btn-group top-group">
        <a class="layui-btn search_btn pl12" lay-event="getSelectWorkFlow"><i class="larry-icon">&#xe939;</i> 查询</a>
    </div>
</script>
</body>
</html>