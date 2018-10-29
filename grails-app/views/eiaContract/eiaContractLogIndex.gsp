<%@ page import="com.lheia.eia.common.FuncConstants"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>合同列表显示页面</title>
    <meta name="layout" content="main"/>
    <asset:stylesheet src="/eiaContract/eiaContractDownload.css"/>
    <asset:javascript src="/eiaContract/eiaContractLogIndex.js"/>
</head>
<body>
<div class="layui-fluid larry-wrapper pt0">
    <input type="hidden" id="eiaContractLogId" name="eiaContractLogId">


    <!--列表-->
    <div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
        <table id="eiaContractLogList" lay-filter="eiaContractLogList"></table>
    </div>
</div>
<script type="text/html" id="mlTool">
    <div class="layui-btn-group">
        <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="logDetail" title="查看"><i class="larry-icon">&#xe896;</i></a>
        {{# if(d.inputUserId == ${session.staff.staffId}){}}
            <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="contractExport" title="补充、变更协议模板导出"><i class="larry-icon">&#xe8cd;</i></a>
        {{# }}}
        {{# if(d.inputUserId== ${session.staff.staffId} && !d.ifSub ){ }}
        <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="logSub" title="合同变更提交"><i class="larry-icon">&#xe88f;</i></a>
        {{# }}}
        {{# if(d.ifSub){}}
        <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="logFlow" title="查看流程"><i class="larry-icon">&#xea21;</i></a>
        {{# }}}
    </div>
</script>
<script type="text/html" id="indexTable">
<span>{{d.LAY_TABLE_INDEX + 1}}</span>
</script>
<script type="text/html" id="tableTopTmp">
<!--查询及添加框-->
<blockquote class="layui-elem-quote larry-btn">
    <div class="layui-inline">
        <div class="layui-input-inline">
            <input type="text" name="contractName" value="" id="contractName" placeholder="合同名称、合同编号、录入部门、录入人" class="layui-input larry-search-input w300">
        </div>
    </div>
    <div class="layui-btn-group top-group">
        <g:if test="${session.staff.funcCode.contains(FuncConstants.EIA_HGGL_HTBG_QUERY)}">
            <a class="layui-btn search_btn pl12" lay-event="getSelect"><i class="larry-icon">&#xe939;</i> 查询</a>
        </g:if>
        <g:if test="${session.staff.funcCode.contains(FuncConstants.EIA_HGGL_HTBG_ADD)}">
            <a class="layui-btn layui-bg-pale pl12 addBtn" lay-event="logAdd"><i class="larry-icon">&#xe987;</i> 新增变更</a>
        </g:if>
    </div>
</blockquote>
</script>
</body>
</html>