<%@ page import="com.lheia.eia.common.FuncConstants"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>任务列表显示页面</title>
    <meta name="layout" content="main"/>
    <asset:javascript src="/eiaDataMaintainLog/eiaDataMaintainTask.js"/>
</head>
<body>
<div class="layui-fluid larry-wrapper">
    <input type="hidden" id="taskId" value=""/>
    <!--查询及添加框-->
    <blockquote class="layui-elem-quote larry-btn">
        <g:if test="${session.staff.funcCode.contains(FuncConstants.EIA_RWGL_RWCX_QUERY)}">
            <div class="layui-inline">
                <div class="layui-input-inline">
                    <input type="text" name="taskName" value="" id="taskName" placeholder="任务名称、任务单号、录入部门、录入人" class="layui-input larry-search-input">
                </div>
                <div class="layui-btn-group top-group">
                    <a class="layui-btn search_btn pl12" data-type="getSelect"><i class="larry-icon">&#xe939;</i> 查询</a>
                </div>
            </div>
        </g:if>
    </blockquote>

    <!--列表-->
    <div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
        <table id="getEiaTaskDataList" lay-filter="getEiaTaskDataList"></table>
    </div>
</div>
<script type="text/html" id="mlTool">
<div class="layui-btn-group">
    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaEdit" title="关联"><i class="larry-icon">&#xe94a;</i></a>
</div>
</script>
</body>
</html>