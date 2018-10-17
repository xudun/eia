<%@ page import="com.lheia.eia.common.FuncConstants"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>工作方案</title>
    <meta name="layout" content="main"/>
    <asset:javascript src="/eiaProject/eiaProjectPlanIndex.js"/>
    <asset:stylesheet src="/eiaProject/eiaProjectPlanIndex.css"/>
    <style>
        .monitor-red{color: #FF5722;}
        .monitor-green{color: #5FB878;}
        .monitor-yellow{color: #FFB800;}
    </style>
</head>
<body>
<div class="layui-fluid larry-wrapper pt0">

    <!--列表-->
    <div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
        <table id="eiaProjectList" lay-filter="eiaProjectList"></table>
    </div>
</div>

<input type="hidden" id="eiaProjectId" value=""/>
<input type="hidden" id="proPlanId" value=""/>
<input type="hidden" id="oid" value=""/>
<input type="hidden" id="oldContractId" value=""/>

<script type="text/html" id="indexTable">
<span>{{d.LAY_TABLE_INDEX + 1}}</span>
</script>
<script type="text/html" id="mlTool">
    <div class="layui-btn-group">
        <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaDetail" title="查看"><i class="larry-icon">&#xe896;</i></a>
        {{# if(d.inputUserId==${session.staff.staffId}){}}
        {{# if(!d.ifSub){}}
       <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaEdit" title="编辑"><i class="larry-icon">&#xe646;</i></a>
        <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaSubmit" title="提交"><i class="larry-icon">&#xe88f;</i></a>
        {{# }}}

        {{# if(d.ifSub && d.oid){}}
            {{# if(d.inputUserId== ${session.staff.staffId}){}}
                <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaEdit" title="编辑"><i class="larry-icon">&#xe646;</i></a>
            {{# }}}
        {{# }}}
        {{# }}}
        {{# if(!d.ifSub){}}
        <g:if test="${session.staff.staffCode=='s_admin'}">
        <a class="layui-btn layui-btn-warm layui-btn-sm" lay-event="eiaSubmitAdmin" title="提交"><i class="larry-icon">&#xe88f;</i></a>
        </g:if>
        {{# }}}
    </div>
</script>
<script type="text/html" id="planMonitorTmp">
    {{#  if(d.planMonitor == "没有问题"){ }}
        <span class="monitor-green">{{d.planMonitor}}</span>
    {{#  } }}
    {{#  if(d.planMonitor == "小于七天"){ }}
    <span class="monitor-yellow">{{d.planMonitor}}</span>
    {{#  } }}
    {{#  if(d.planMonitor == "过期"){ }}
    <span class="monitor-red">{{d.planMonitor}}</span>
    {{#  } }}
</script>
<!--查询及添加框-->
<script type="text/html" id="tableTopTmp">
<div class="table-top">
    <div class="layui-inline">
        <div class="layui-input-inline">
            <input type="text" name="projectName" value="" id="projectName" placeholder="项目名称、项目编号、项目负责人、录入部门、录入人" class="layui-input larry-search-input w400">
        </div>
        <div class="layui-btn-group top-group">
            <a class="layui-btn search_btn pl12" lay-event="getSelect"><i class="larry-icon">&#xe939;</i> 查询</a>
        </div>
    </div>
</div>
</script>
</body>
</html>