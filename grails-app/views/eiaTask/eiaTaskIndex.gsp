<%@ page import="com.lheia.eia.common.FuncConstants"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>任务列表显示页面</title>
    <meta name="layout" content="main"/>
    <asset:javascript src="/eiaTask/eiaTaskIndex.js"/>
</head>
<body>
<div class="layui-fluid larry-wrapper pt0">
    <input type="hidden" id="taskId" value=""/>

    <!--列表-->
    <div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
        <table id="getEiaTaskDataList" lay-filter="getEiaTaskDataList"></table>
    </div>
</div>
<script type="text/html" id="mlTool">
<g:if test="${session.staff.funcCode.contains(FuncConstants.EIA_RWGL_RWCX_QUERY)}">
    <div class="layui-btn-group">
        <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaCheck" title="查看"><i class="larry-icon">&#xe939;</i></a>
        {{# if( d.taskState == '新建' ){ }}
            {{# if(d.inputUserId== ${session.staff.staffId}){}}
                <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaEdit" title="编辑"><i class="larry-icon">&#xe72b;</i></a>
                <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaDel" title="删除"><i class="larry-icon">&#xe8d0;</i></a>
                <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaSubmit" title="提交"><i class="larry-icon">&#xe88f;</i></a>
            {{# }}}
        {{# } }}
    </div>
</g:if>
</script>
<!--查询及添加框-->
<script type="text/html" id="tableTopTmp">
<div class="table-top">
    <div class="layui-inline">
        <g:if test="${session.staff.funcCode.contains(FuncConstants.EIA_RWGL_RWCX_QUERY)}">
            <div class="layui-input-inline">
                <input type="text" name="taskName" value="" id="taskName" placeholder="任务名称、任务单号、录入部门、录入人" class="layui-input larry-search-input w300">
            </div>
        </g:if>
        <div class="layui-btn-group top-group">
            <g:if test="${session.staff.funcCode.contains(FuncConstants.EIA_RWGL_RWCX_QUERY)}">
                <a class="layui-btn search_btn pl12" lay-event="getSelect"><i class="larry-icon">&#xe939;</i> 查询</a>
            </g:if>
            <g:if test="${session.staff.funcCode.contains(FuncConstants.EIA_RWGL_RWCJ_ADD)}">
                <a class="layui-btn layui-bg-pale pl12 addBtn" lay-event="missionAdd"><i class="larry-icon">&#xe987;</i> 新增</a>
            </g:if>
        </div>
    </div>
</div>
</script>
</body>
</html>