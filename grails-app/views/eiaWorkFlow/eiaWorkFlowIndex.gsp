<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>流程配置</title>
    <meta name="layout" content="main"/>
    <asset:stylesheet src="/eiaWorkFlow/eiaWorkFlow.css"/>
    <asset:javascript src="/eiaWorkFlow/eiaWorkFlowIndex.js"/>
</head>
<body>
<div class="layui-fluid larry-wrapper pt0">

    <!--列表-->
    <div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
        <table id="eiaWorkFlowList" lay-filter="eiaWorkFlowList"></table>
    </div>

    <input type="hidden" id="eiaWorkFlowId" name="eiaWorkFlowId">
</div>


<script type="text/html" id="indexTable">
    <span>{{d.LAY_TABLE_INDEX + 1}}</span>
</script>
<script type="text/html" id="mlTool">
    <div class="layui-btn-group">
        <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaCheck" title="查看"><i class="larry-icon">&#xe896;</i></a>
        <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaEdit" title="编辑"><i class="larry-icon">&#xe646;</i></a>
        <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaDel" title="删除"><i class="larry-icon">&#xe8d0;</i></a>
        <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="subWorkFlow" title="提交"><i class="larry-icon">&#xe88f;</i></a>
    </div>
</script>
<!--查询及添加框-->
<script type="text/html" id="tableTopTmp">
<div class="table-top">
    <div class="layui-inline">
        <div class="layui-input-inline">
            <input type="text" name="workFlowName" value="" id="workFlowName" placeholder="流程名称" class="layui-input larry-search-input">
        </div>
    </div>
    <div class="layui-btn-group top-group">
        <a class="layui-btn search_btn pl12" lay-event="getSelect"><i class="larry-icon">&#xe939;</i> 查询</a>
        <a class="layui-btn layui-bg-pale pl12" lay-event="flowAdd"><i class="larry-icon">&#xe987;</i> 新增</a>
        <a class="layui-btn layui-bg-pale pl12" lay-event="importConfig"><i class="larry-icon">&#xe812;</i> 导入工作流</a>
    </div>
</div>
</script>
</body>
</html>