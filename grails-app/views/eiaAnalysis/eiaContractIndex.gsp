<%@ page contentType="text/html;charset=UTF-8" import="com.lheia.eia.common.FuncConstants"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>合同</title>
    <meta name="layout" content="main"/>
    <asset:javascript src="/eiaAnalysis/eiaContractIndex.js"/>
</head>

<body>
<div class="layui-fluid larry-wrapper pt0">
    <div class="layui-tab layui-tab-brief" lay-filter="contractOfferTab">
        <ul class="layui-tab-title">
            <li class="layui-this">合同信息</li>
        </ul>
        <div class="layui-tab-content pt0" style="height: 100px;">
            <!--合同信息-->
            <div class="layui-tab-item layui-show">
                <!--列表-->
                <div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
                    <table id="eiaContractList" lay-filter="eiaContractList"></table>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/html" id="contractTool">
<div class="layui-btn-group">
    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaCheck" title="查看"><i class="larry-icon">&#xe896;</i></a>
    {{# if( d.contractNo ){ }}
    {{# if(d.inputUserId== ${session.staff.staffId}){}}
    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="contractExport" title="合同模板导出"><i class="larry-icon">&#xe8cd;</i></a>
    {{# }}}
    {{# }}}
    {{# if(!d.ifSub){}}
    <g:if test="${session.staff.funcCode.contains(FuncConstants.EIA_HGGL_HTCJ_ADDSELF)}">
        {{# if(d.inputUserId== ${session.staff.staffId}){}}
        <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaEdit" title="编辑"><i class="larry-icon">&#xe646;</i></a>
        {{# if(d.inputUserId== ${session.staff.staffId} && !d.contractNo){}}
        <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaDel" title="删除"><i class="larry-icon">&#xe8d0;</i></a>
        {{# }}}
  {{# }}}
    </g:if>
    {{# if(d.inputUserId== ${session.staff.staffId}&& d.contractNo ){ }}
    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="contractSub" title="合同提交"><i class="larry-icon">&#xe88f;</i></a>
    {{# }}}
    {{# }}}
    {{# if(d.ifSub){}}
    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="contractFlow" title="查看流程"><i class="larry-icon">&#xea21;</i></a>
    {{# }}}
</div>
</script>

<script type="text/html" id="indexTable">
<input type="hidden" id="tableNameId" name="tableNameId" value="">
<input type="hidden" id="tableName" name="tableName" value="">
<span>{{d.LAY_TABLE_INDEX + 1}}</span>
</script>

<script type="text/html" id="tableTopTmp">
<!--查询及添加框-->
<g:if test="${session.staff.funcCode.contains(FuncConstants.EIA_HGGL_HTCX_QUERY)}">
    <div class="layui-inline">
        <div class="layui-input-inline">
            <input type="text" name="contractName" value="" id="contractName" placeholder="合同名称、合同编号、录入部门、录入人" class="layui-input larry-search-input w300">
        </div>
    </div>
</g:if>
<div class="layui-btn-group top-group">
    <g:if test="${session.staff.funcCode.contains(FuncConstants.EIA_HGGL_HTCX_QUERY)}">
        <a class="layui-btn search_btn pl12" lay-event="contractSelect" data-type="contractSelect"><i class="larry-icon">&#xe939;</i> 查询</a>
    </g:if>
  %{--  <g:if test="${session.staff.funcCode.contains(FuncConstants.EIA_HGGL_HTCJ_ADDSELF)}">
        <a class="layui-btn layui-bg-pale pl12" lay-event="contractAdd" data-type="contractAdd"><i class="larry-icon">&#xe987;</i> 新增</a>
    </g:if>--}%
</div>
</script>

</body>
</html>