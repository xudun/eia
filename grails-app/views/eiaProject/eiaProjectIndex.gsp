<%@ page import="com.lheia.eia.common.FuncConstants" contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>项目列表</title>
    <meta name="layout" content="main"/>
    <asset:javascript src="/eiaProject/eiaProjectIndex.js"/>
    <asset:stylesheet src="/eiaProject/eiaProjectIndex.css"/>
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
    {{# if(d.gisProjectId){}}
        <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="projectGisShow" title="显示地理信息"><i class="larry-icon">&#xea6b;</i></a>
    {{# }}}
    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="projectDetail" title="查看"><i class="larry-icon">&#xe896;</i></a>
    {{# if(!d.ifSub){}}
        {{# if(d.inputUserId== ${session.staff.staffId}){}}
            <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="projectEdit" title="编辑"><i class="larry-icon">&#xe646;</i></a>
            <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="projectDel" title="删除"><i class="larry-icon">&#xe8d0;</i></a>
        {{# }}}
    {{# }}}
    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="projectFlow" title="查看流程"><i class="larry-icon">&#xea21;</i></a>
    <g:if test="${session.staff.funcCode.contains(FuncConstants.EIA_HGGL_ZZBG_CJZZ)}">
        <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="projectCoverDown" title="封皮下载"><i class="larry-icon">&#xe81b;</i></a>
        <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="certDownload" title="资质下载"><i class="larry-icon">&#xe8cd;</i></a>
    </g:if>
    <g:else>
        {{# if(d.inputUserId== ${session.staff.staffId}){}}
        <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="projectCoverDown" title="封皮下载"><i class="larry-icon">&#xe81b;</i></a>
        {{# }}}
    </g:else>
    <g:if test="${session.staff.staffCode=='s_admin'}">
        <a class="layui-btn layui-btn-warm layui-btn-sm" lay-event="changeWorkFlow" title="干预流程"><i class="larry-icon">&#xec8d;</i></a>
    </g:if>
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
        <div class="layui-btn-group top-group">
            <a class="layui-btn search_btn pl12" lay-event="getSelect"><i class="larry-icon">&#xe939;</i> 查询</a>
            <a class="layui-btn search_btn pl12" lay-event="highSelect" state="0"><i class="larry-icon">&#xe939;</i> 高级查询</a>
        </div>
    </div>
    %{--   <div class="layui-inline">
           <a class="layui-btn layui-bg-pale pl12" data-type="projectAdd"><i class="larry-icon">&#xe987;</i> 新增</a>
       </div>--}%
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