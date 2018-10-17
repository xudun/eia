<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="layout" content="main"/>
    <title>审批公示</title>
    <script type="text/javascript">
        var GIS_DRAW_PATH = "${com.lheia.eia.common.HttpUrlConstants.GIS_DRAW_PATH}"
    </script>
    <asset:stylesheet src="/eiaPlatForm/eiaPubProject.css"/>
    <asset:javascript src="/eiaPlatForm/eiaPubProjectIndex.js"/>
</head>
<body>
<div class="layui-fluid larry-wrapper pt15">

    <!--列表-->
    <div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
        <table id="eiaPubProjectList" lay-filter="eiaPubProjectList"></table>
    </div>

    <input type="hidden" id="eiaPubProjectId" name="eiaPubProjectId">
</div>

<script type="text/html" id="indexTable">
    <span>{{d.LAY_TABLE_INDEX + 1}}</span>
</script>
<script type="text/html" id="eiaPubProjectListTool">
    <div class="layui-btn-group">
        <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaCheck"><i class="larry-icon">&#xe896;</i></a>
        <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaEdit"><i class="larry-icon">&#xe646;</i></a>
        <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaDel"><i class="larry-icon">&#xe8d0;</i></a>
        {{# if( !d.ifPub ){ }}
            <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaSubmit"><i class="larry-icon">&#xe88f;</i></a>
        {{# } }}
        <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaDraw" title="绘图"><i class="larry-icon">&#xe9b3;</i></a>
    </div>
</script>
<!--查询及添加框-->
<script type="text/html" id="tableTopTmp">
<div class="table-top">
    <div class="layui-inline">
        <div class="layui-input-inline">
            <input type="text" name="projectName" value="" id="projectName" placeholder="项目名称" class="layui-input larry-search-input">
        </div>
    </div>
    <div class="layui-btn-group top-group">
        <a class="layui-btn search_btn pl12" lay-event="getSelect"><i class="larry-icon">&#xe939;</i> 查询</a>
    </div>
</div>
</script>
</body>
</html>