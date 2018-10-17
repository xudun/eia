<%@ page import="com.lheia.eia.common.FuncConstants"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>项目列表显示页面</title>
    <meta name="layout" content="main"/>
    <asset:javascript src="/eiaProject/eiaProjectLogIndex.js"/>
    <asset:stylesheet src="/eiaProject/eiaProjectIndex.css"/>
</head>
<body>
<div class="layui-fluid larry-wrapper pt0">
    <!--查询及添加框-->
    <input type="hidden" id="eiaProjectLogId" value=""/>

    <!--列表-->
    <div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
        <table id="eiaProjectLogList" lay-filter="eiaProjectLogList"></table>
    </div>
</div>
<script type="text/html" id="mlTool">
    <div class="layui-btn-group">
        <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="logDetail" title="查看"><i class="larry-icon">&#xe896;</i></a>
    </div>
</script>
<script type="text/html" id="indexTable">
<span>{{d.LAY_TABLE_INDEX + 1}}</span>
</script>
<script type="text/html" id="tableTopTmp">
<div class="table-top">
    <div class="layui-inline">
        <div class="layui-input-inline">
            <input type="text" name="projectName" value="" id="projectName" placeholder="项目名称、项目编号、项目负责人、录入部门、录入人" class="layui-input larry-search-input w400">
        </div>
    </div>
    <div class="layui-btn-group top-group">
        <a class="layui-btn search_btn pl12" lay-event="getSelect"><i class="larry-icon">&#xe939;</i> 查询</a>
        <a class="layui-btn search_btn pl12" lay-event="highSelect" state="0"><i class="larry-icon">&#xe939;</i> 高级查询</a>
        <g:if test="${session.staff.funcCode.contains(FuncConstants.EIA_XMGL_XMBG_ADD)}">
            <a class="layui-btn layui-bg-pale pl12" lay-event="logAdd"><i class="larry-icon">&#xe987;</i> 新增变更</a>
        </g:if>
    </div>
    <!--高级查询-->
    <div id="advanced-query" class="display-none">
        <fieldset class="layui-elem-field layui-field-title site-title">
            <legend><a name="methodRender">高级查询</a></legend>
        </fieldset>
        <form class="layui-form">
            <div class="layui-inline">
                <label class="layui-form-label">文件类型</label>
                <div class="layui-input-inline">
                    <input type="text" name="fileType" autocomplete="off" id="fileType" class="layui-input">
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">建设地点</label>
                <div class="layui-input-inline">
                    <input type="text" name="buildArea" autocomplete="off" id="buildArea" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item larry-btn-btn">
                <button class="layui-btn pl12" lay-submit lay-filter="query"><i class="larry-icon">&#xe896;</i> 查询</button>
                <button type="reset" class="layui-btn layui-btn-primary pl12"><i class="larry-icon">&#xe6d7;</i> 重置</button>
            </div>
        </form>
    </div>
</div>
</script>
</body>
</html>