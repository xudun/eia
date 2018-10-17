<%@ page contentType="text/html;charset=UTF-8" import="com.lheia.eia.common.FuncConstants"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>合同报价</title>
    <meta name="layout" content="main"/>
    <asset:javascript src="/eiaDataMaintainLog/eiaDataMaintainIndex.js"/>
</head>

<body>
<input type="hidden" id="contractId" name="contractId" value="">
<input type="hidden" id="offerId" name="offerId" value="">
<input type="hidden" id="taskId" name="taskId" value="">
<input type="hidden" id="projectId" name="projectId" value="">
<input type="hidden" id="clientId" name="clientId" value="">
<input type="hidden" id="projectPlanId" name="projectPlanId" value="">
<input type="hidden" id="eiaCertId" name="eiaCertId" value="">
<input type="hidden" id="maintainType" name="maintainType" value="">
<div class="layui-fluid larry-wrapper pt0">
    <div class="layui-tab layui-tab-brief" lay-filter="contractOfferTab">
        <ul class="layui-tab-title">
            <li class="layui-this">任务数据维护 </li>
            <li >合同数据维护</li>
            <li>报价数据维护</li>
            <li>项目数据维护</li>
            <li>工作方案数据维护</li>
            <li>资质报告数据维护</li>
            <li>企业数据维护</li>
        </ul>
        <div class="layui-tab-content pt0" style="height: 100px;">
            <!--任务信息-->
            <div class="layui-tab-item layui-show">
                <!--列表-->
                <div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
                    <table id="eiaTaskList" lay-filter="eiaTaskList"></table>
                </div>
            </div>
            <!--合同信息-->
            <div class="layui-tab-item ">
                <!--列表-->
                <div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
                    <table id="eiaContractList" lay-filter="eiaContractList"></table>
                </div>

            </div>
            <!--报价信息-->
            <div class="layui-tab-item ">
                <!--列表-->
                <div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
                    <table id="eiaOfferList" lay-filter="eiaOfferList"></table>
                </div>
            </div>
            <!--项目信息-->
            <div class="layui-tab-item ">
                <!--列表-->
                <div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
                    <table id="eiaProjectList" lay-filter="eiaProjectList"></table>
                </div>
            </div>
            <!--工作方案-->
            <div class="layui-tab-item ">
                <!--列表-->
                <div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
                    <table id="eiaProjectPlanList" lay-filter="eiaProjectPlanList"></table>
                </div>
            </div>
            <!--资质信息-->
            <div class="layui-tab-item ">
                <!--列表-->
                <div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
                    <table id="eiaCertList" lay-filter="eiaCertList"></table>
                </div>
            </div>
            <!--企业信息-->
            <div class="layui-tab-item ">
                <!--列表-->
                <div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
                    <table id="eiaClientList" lay-filter="eiaClientList"></table>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/html" id="contractTool">
<div class="layui-btn-group">
    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaEdit" title="合同单笔数据维护"><i class="larry-icon">&#xe646;</i></a>
    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaDel" title="删除"><i class="larry-icon">&#xe8d0;</i></a>
    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaShow" title="是否显示"><i class="larry-icon">&#xe778;</i></a>
</div>
</script>
<script type="text/html" id="offerTool">
<div class="layui-btn-group">
    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaEdit" title="报价单笔数据维护"><i class="larry-icon">&#xe646;</i></a>
    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaDel" title="删除"><i class="larry-icon">&#xe8d0;</i></a>

</div>
</script>
<script type="text/html" id="taskTool">
<div class="layui-btn-group">
    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaEdit" title="任务单笔数据维护"><i class="larry-icon">&#xe646;</i></a>
    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaDel" title="删除"><i class="larry-icon">&#xe8d0;</i></a>
</div>
</script>
<script type="text/html" id="projectTool">
<div class="layui-btn-group">
    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaEdit" title="项目单笔数据维护"><i class="larry-icon">&#xe646;</i></a>
    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaDel" title="删除"><i class="larry-icon">&#xe8d0;</i></a>
    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaTask" title="关联任务"><i class="larry-icon">&#xe94a;</i></a>

</div>
</script>
<script type="text/html" id="projectPlanTool">
<div class="layui-btn-group">
    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaEdit" title="工作方案单笔数据维护"><i class="larry-icon">&#xe646;</i></a>
    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaDel" title="删除"><i class="larry-icon">&#xe8d0;</i></a>
</div>
</script>
<script type="text/html" id="eiaCertListTool">
<div class="layui-btn-group">
    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaEdit" title="资质单笔数据维护"><i class="larry-icon">&#xe646;</i></a>
    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaDel" title="删除"><i class="larry-icon">&#xe8d0;</i></a>

</div>
</script>
<script type="text/html" id="clientTool">
<div class="layui-btn-group">
    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaEdit" title="企业单笔数据维护"><i class="larry-icon">&#xe646;</i></a>
    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaDel" title="删除"><i class="larry-icon">&#xe8d0;</i></a>

</div>
</script>
<script type="text/html" id="indexTable">
<input type="hidden" id="tableNameId" name="tableNameId" value="">
<input type="hidden" id="tableName" name="tableName" value="">
<span>{{d.LAY_TABLE_INDEX + 1}}</span>
</script>
%{--任务查询及添加框--}%
<script type="text/html" id="tableTopTmp">
    <div class="layui-inline">
        <div class="layui-input-inline">
            <input type="text" name="taskName" value="" id="taskName" placeholder="任务名称、任务编号、录入部门、录入人" class="layui-input larry-search-input w300">
        </div>
    </div>
    <div class="layui-btn-group top-group">
        <a class="layui-btn search_btn pl12" lay-event="taskSelect" data-type="taskSelect"><i class="larry-icon">&#xe939;</i> 查询</a>
        <a class="layui-btn layui-bg-pale pl12" lay-event='taskAdd' data-type="taskAdd"><i class="larry-icon">&#xe987;</i> 任务多笔数据维护</a>
        <a class="layui-btn layui-bg-pale pl12" lay-event="contractAllAdd" data-type="contractAllAdd"><i class="larry-icon">&#xe987;</i> 全量数据维护</a>
    </div>
</script>
%{--合同查询及添加框--}%
<script type="text/html" id="tableTopTmp2">
    <div class="layui-inline">
        <div class="layui-input-inline">
            <input type="text" name="contractName" value="" id="contractName" placeholder="合同名称、合同编号、录入部门、录入人" class="layui-input larry-search-input w300">
        </div>
    </div>
    <div class="layui-btn-group top-group">
        <a class="layui-btn search_btn pl12" lay-event="contractSelect" data-type="contractSelect"><i class="larry-icon">&#xe939;</i> 查询</a>
        <a class="layui-btn layui-bg-pale pl12" lay-event="contractAdd" data-type="contractAdd"><i class="larry-icon">&#xe987;</i> 合同多笔数据维护</a>

    </div>
</script>
%{--报价查询及添加框--}%
<script type="text/html" id="tableTopTmp3">
    <div class="layui-inline">
        <div class="layui-input-inline">
            <input type="text" name="offerName" value="" id="offerName" placeholder="合同名称、报价编号、录入部门、录入人" class="layui-input larry-search-input w300">
        </div>
    </div>
    <div class="layui-btn-group top-group">
        <a class="layui-btn search_btn pl12" lay-event="offerSelect" data-type="offerSelect"><i class="larry-icon">&#xe939;</i> 查询</a>
        <a class="layui-btn layui-bg-pale pl12" lay-event="offerAdd" data-type="offerAdd"><i class="larry-icon">&#xe987;</i> 报价多笔数据维护</a>
    </div>
</script>
%{--项目查询及添加框--}%
<script type="text/html" id="tableTopTmp4">
    <div class="layui-inline">
        <div class="layui-input-inline">
            <input type="text" name="projectName" value="" id="projectName" placeholder="项目名称、项目编号、录入部门、录入人" class="layui-input larry-search-input w300">
        </div>
    </div>
    <div class="layui-btn-group top-group">
        <a class="layui-btn search_btn pl12" lay-event="projectSelect" data-type="projectSelect"><i class="larry-icon">&#xe939;</i> 查询</a>
        <a class="layui-btn layui-bg-pale pl12" lay-event="projectAdd" data-type="projectAdd"><i class="larry-icon">&#xe987;</i> 项目多笔数据维护</a>
    </div>
</script>

%{--工作方案询及添加框--}%
<script type="text/html" id="tableTopTmp5">
    <div class="layui-inline">
        <div class="layui-input-inline">
            <input type="text" name="projectPlanName" value="" id="projectPlanName" placeholder="项目名称、项目编号、录入部门、录入人" class="layui-input larry-search-input w300">
        </div>
    </div>
    <div class="layui-btn-group top-group">
        <a class="layui-btn search_btn pl12" lay-event="projectPlanSelect" data-type="projectPlanSelect"><i class="larry-icon">&#xe939;</i> 查询</a>
        <a class="layui-btn layui-bg-pale pl12" lay-event="projectPlanAdd" data-type="projectPlanAdd"><i class="larry-icon">&#xe987;</i> 工作方案多笔数据维护</a>
    </div>
</script>
%{--资质查询及添加框--}%
<script type="text/html" id="tableTopTmp6">
        <div class="layui-inline">
            <div class="layui-input-inline">
                <input type="text" name="projectNameZ" value="" id="projectNameZ" placeholder="项目名称、项目编号、申请部门、申请人" class="layui-input larry-search-input w300">
            </div>
        </div>
        <div class="layui-btn-group top-group">
            <a class="layui-btn search_btn pl12" lay-event="certSelect" data-type="certSelect"><i class="larry-icon">&#xe939;</i> 查询</a>
            <a class="layui-btn layui-bg-pale pl12" lay-event="certAdd" data-type="certAdd"><i class="larry-icon">&#xe987;</i> 资质多笔数据维护</a>
        </div>
</script>
%{--企业查询框--}%
<script type="text/html" id="tableTopTmp7">
        <div class="layui-inline">
            <div class="layui-input-inline">
                <input type="text" name="clientName" value="" id="clientName" placeholder="企业名称、录入部门、录入人" class="layui-input larry-search-input w300">
            </div>
        </div>
        <div class="layui-btn-group top-group">
            <a class="layui-btn search_btn pl12" lay-event="clientSelect" data-type="clientSelect"><i class="larry-icon">&#xe939;</i> 查询</a>
            <a class="layui-btn layui-bg-pale pl12" lay-event="clientAdd" data-type="clientAdd"><i class="larry-icon">&#xe987;</i> 企业多笔数据维护</a>
        </div>
</script>
</body>
</html>