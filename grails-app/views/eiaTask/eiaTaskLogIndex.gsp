<%@ page import="com.lheia.eia.common.FuncConstants"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>任务变更列表显示页面</title>
    <meta name="layout" content="main"/>
    <asset:javascript src="/eiaTask/eiaTaskLogIndex.js"/>
</head>
<body>
<div class="layui-fluid larry-wrapper pt0">
    <input type="hidden" id="eiaTaskLogId" value=""/>

    <!--列表-->
    <div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
        <table id="getEiaTaskLogDataList" lay-filter="getEiaTaskLogDataList"></table>
    </div>
</div>
<script type="text/html" id="mlTool">
    <div class="layui-btn-group">
        <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaCheck" title="查看"><i class="larry-icon">&#xe939;</i></a>
    </div>
</script>
<!--查询及添加框-->
<script type="text/html" id="tableTopTmp">
<div class="table-top">
    <div class="layui-inline">
        <div class="layui-input-inline">
            <input type="text" name="taskName" value="" id="taskName" placeholder="任务名称、任务单号、录入部门、录入人" class="layui-input larry-search-input w300">
        </div>
    </div>
    <div class="layui-btn-group top-group">
        <a class="layui-btn search_btn pl12" lay-event="getSelect"><i class="larry-icon">&#xe939;</i> 查询</a>
        <g:if test="${session.staff.funcCode.contains(FuncConstants.EIA_RWGL_RWBG_ADD)}">
            <a class="layui-btn layui-bg-pale pl12" lay-event="logAdd"><i class="larry-icon">&#xe987;</i> 新增变更</a>
        </g:if>
    </div>
</div>
</script>
</body>
</html>