<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>项目流程</title>
    <meta name="layout" content="main"/>
    <asset:javascript src="/eiaProject/eiaProjectProcessIndex.js"/>
    <asset:stylesheet src="/eiaProject/eiaProjectProcessIndex.css"/>
</head>
<body>
<div class="layui-fluid larry-wrapper pt0">

    <!--tab选项卡-->
    <div class="layui-tab layui-tab-brief" lay-filter="proProcessTab">
        <ul class="layui-tab-title">
            <li class="layui-this">项目待办列表<span class="layui-badge" id="waitAffairsListSize">1</span></li>
            <li>项目查询列表</li>
        </ul>
        <div class="layui-tab-content">
            <!--项目待办列表-->
            <div class="layui-tab-item layui-show">
                <!--列表-->
                <div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
                    <table id="waitAffairsList" lay-filter="waitAffairsList"></table>
                </div>
            </div>
            <!--项目查询列表-->
            <div class="layui-tab-item">
                <!--列表-->
                <div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
                    <table id="eiaProjectList" lay-filter="eiaProjectList"></table>
                </div>
            </div>
        </div>
    </div>

</div>
<!--查询及添加框-->
<input type="hidden" id="eiaProjectId" value=""/>
<input type="hidden" id="eiaTaskId" value=""/>
<input type="hidden" id="tableName" value="EiaProject"/>
<input type="hidden" id="tableNameId" value=""/>
<input type="hidden" id="eiaLabOfferId" value=""/>
<script type="text/html" id="indexTable">
<span>{{d.LAY_TABLE_INDEX + 1}}</span>
</script>
<script type="text/html" id="waitAffairsListTool">
    <div class="layui-btn-group">
        <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaCheck" title="查看"><i class="larry-icon">&#xea21;</i></a>
    </div>
</script>
<script type="text/html" id="eiaProjectListTool">
    <div class="layui-btn-group">

        {{# if(d.gisProjectId){}}
        <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="projectGisShow" title="显示地理信息"><i class="larry-icon">&#xea6b;</i></a>
        {{# }}}
        <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="projectDetail" title="查看"><i class="larry-icon">&#xe896;</i></a>
        {{# if(!d.ifSub){}}
            {{# if(d.inputUserId== ${session.staff.staffId}){}}
            <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="projectEdit" title="编辑"><i class="larry-icon">&#xe646;</i></a>
            {{# }}}
        {{# }}}
        %{--    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="projectDel" title="删除"><i class="larry-icon">&#xe8d0;</i></a>--}%
        <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="projectFlow" title="查看流程"><i class="larry-icon">&#xea21;</i></a>
        %{--<a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="certDownload" title="下载"><i class="larry-icon">&#xe8cd;</i></a>--}%
    </div>
</script>
<!--查询及添加框-->
<script type="text/html" id="waitTmp">
<div class="table-top">
    <div class="layui-inline">
        <div class="layui-input-inline">
            <input type="text" name="waitName" value="" id="waitName" placeholder="项目名称、项目编号、录入部门、录入人" class="layui-input larry-search-input w300">
        </div>
        <div class="layui-btn-group top-group">
            <a class="layui-btn search_btn pl12" lay-event="getSelect"><i class="larry-icon">&#xe939;</i> 查询</a>
        </div>
    </div>
</div>
</script>
<!--查询及添加框-->
<script type="text/html" id="projectTmp">
<div class="table-top">
    <div class="layui-inline">
        <div class="layui-input-inline">
            <input type="text" name="projectName" value="" id="projectName" placeholder="项目名称、项目编号、录入部门、录入人" class="layui-input larry-search-input w300">
        </div>
        <div class="layui-btn-group top-group">
            <a class="layui-btn search_btn pl12" lay-event="getSelect"><i class="larry-icon">&#xe939;</i> 查询</a>
        </div>
    </div>
</div>
</script>
</body>
</html>