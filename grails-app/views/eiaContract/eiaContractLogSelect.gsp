<%@ page contentType="text/html;charset=UTF-8" import="com.lheia.eia.common.FuncConstants"%>
<html>
<head>
    <title>合同列表</title>
    <meta name="layout" content="main"/>
    <asset:javascript src="/eiaContract/eiaContractLogSelect.js"/>
</head>
<body>
<div class="layui-fluid larry-wrapper">
    <!--查询及添加框-->
    <input type="hidden" id="eiaProjectId" value=""/>
    <input type="hidden" id="eiaTaskId" value=""/>


    <!--列表-->
    <div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
        <table id="eiaContractList" lay-filter="eiaContractList"></table>
    </div>

    <input type="hidden" id="eiaContractId" name="eiaContractId">
</div>
<script type="text/html" id="mlTool">
    <div class="layui-btn-group">
        <g:if test="${session.staff.funcCode.contains(FuncConstants.EIA_HGGL_HTBG_ADD)}">
            {{# if(d.inputUserId== ${session.staff.staffId}){}}
                <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="contractEdit" title="编辑"><i class="larry-icon">&#xe646;</i></a>
            {{# }}}
        </g:if>
    </div>
</script>
<script type="text/html" id="indexTable">
<span>{{d.LAY_TABLE_INDEX + 1}}</span>
</script>

<script type="text/html" id="tableTopTmp">
    <div class="layui-inline">
        <div class="layui-input-inline">
            <input type="text" name="contractName" value="" id="contractName" placeholder="合同名称" class="layui-input larry-search-input w300">
        </div>
        <div class="layui-btn-group top-group">
            <a class="layui-btn search_btn pl12" lay-event="getSelect" data-type="getSelect"><i class="larry-icon">&#xe939;</i> 查询</a>
        </div>
    </div>
</script>
</body>
</html>