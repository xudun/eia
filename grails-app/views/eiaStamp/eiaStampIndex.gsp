<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>印章申请</title>
    <meta name="layout" content="main"/>
    <asset:javascript src="/eiaStamp/eiaStampIndex.js"/>
    <asset:stylesheet src="/eiaProject/eiaProjectPlanIndex.css"/>
</head>

<body>
<div class="layui-fluid larry-wrapper pt0">

    <!--列表-->
    <div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
        <table id="eiaStampList" lay-filter="eiaStampList"></table>
    </div>
</div>

<input type="hidden" id="eiaStampId" name="eiaStampId">
<input type="hidden" id="tableName" name="tableName">
<input type="hidden" id="tableNameId" name="tableNameId">

<script type="text/html" id="mlTool">
<div class="layui-btn-group">
    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaDetail" title="查看"><i class="larry-icon">&#xe896;</i></a>
    {{#  if(d.ifSub){ }}
    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="stampFlow" title="处理流程"><i class="larry-icon">&#xea21;</i></a>
    {{# } else { }}
    {{# if(d.inputUserId== ${session.staff.staffId}){}}
    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaEdit" title="编辑"><i class="larry-icon">&#xe646;</i></a>
    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaSubmit" title="提交"><i class="larry-icon">&#xe88f;</i></a>
    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="stampDel" title="删除"><i class="larry-icon">&#xe8d0;</i></a>
    {{# }}}
    {{# }}}

</div>
</script>
<!--查询及添加框-->
<script type="text/html" id="tableTopTmp">
<div class="table-top">
    <div class="layui-inline">
        <div class="layui-input-inline">
            <input type="text" name="applicant" value="" id="applicant" placeholder="申请人、录入人、申请部门、申请时间" class="layui-input larry-search-input w300">
        </div>

        <div class="layui-btn-group top-group">
            <a class="layui-btn search_btn pl12" lay-event="getSelect"><i class="larry-icon">&#xe939;</i> 查询</a>
            <a class="layui-btn layui-bg-pale pl12" lay-event="stampAdd"><i class="larry-icon">&#xe987;</i> 新增</a>
        </div>
    </div>
</div>
</script>

<script type="text/html" id="indexTable">
<span>{{d.LAY_TABLE_INDEX + 1}}</span>
</script>
</body>
</html>