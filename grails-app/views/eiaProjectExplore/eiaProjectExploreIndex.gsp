<%@ page import="com.lheia.eia.common.FuncConstants" contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>项目列表</title>
    <meta name="layout" content="main"/>
    <script type="text/javascript">
        var EXPLORE_DRAW_PATH = "${com.lheia.eia.common.HttpUrlConstants.EXPLORE_DRAW_PATH}"
    </script>
    <asset:javascript src="/eiaProjectExplore/eiaProjectExploreIndex.js"/>
    <asset:stylesheet src="/eiaProjectExplore/eiaProjectExploreIndex.css"/>
</head>

<body>
<div class="layui-fluid larry-wrapper pt0">
    <!--查询及添加框-->
    <input type="hidden" id="eiaProjectId" value=""/>
    <input type="hidden" id="eiaTaskId" value=""/>
    <input type="hidden" id="tableName" value="eiaProject"/>
    <input type="hidden" id="tableNameId" value=""/>
    <input type="hidden" id="eiaLabOfferId" value=""/>
    <input type="hidden" id="proPlanId" value=""/>

    <!--列表-->
    <div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
        <table id="eiaProjectList" lay-filter="eiaProjectList"></table>
    </div>
</div>
<script type="text/html" id="mlTool">
<div class="layui-btn-group">
    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="projectGisShow" title="显示地理信息"><i class="larry-icon">&#xe9b3;</i></a>
    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="projectDetail" title="查看"><i class="larry-icon">&#xe896;</i></a>
    {{# if(!d.ifSub){}}
        {{# if(d.inputUserId== ${session.staff.staffId}){}}
            <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="projectExploreSub" title="提交流程"><i class="larry-icon">&#xe88f;</i></a>
            <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="projectEdit" title="编辑"><i class="larry-icon">&#xe646;</i></a>
            <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="projectDel" title="删除"><i class="larry-icon">&#xe8d0;</i></a>
        {{# }}}
    {{# }else{ }}
            <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="projectExploreFlow" title="查看流程"><i class="larry-icon">&#xea21;</i></a>
    {{# }}}
</div>
</script>
<script type="text/html" id="indexTable">
<span>{{d.LAY_TABLE_INDEX + 1}}</span>
</script>
<script type="text/html" id="tableTopTmp">
<div class="table-top">
    <div class="layui-inline">
        <div class="layui-input-inline">
            <input type="text" name="projectName" value="" id="projectName" placeholder="内审单编号、项目名称、录入部门、录入人" class="layui-input larry-search-input w400">
        </div>
        <div class="layui-btn-group top-group">
            <a class="layui-btn search_btn pl12" lay-event="getSelect"><i class="larry-icon">&#xe939;</i> 查询</a>
            <a class="layui-btn layui-bg-pale pl12" lay-event="projectAdd"><i class="larry-icon">&#xe987;</i> 新增</a>
        </div>
    </div>
</div>
</script>
</body>
</html>