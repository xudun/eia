<%@ page contentType="text/html;charset=UTF-8" import="com.lheia.eia.common.FuncConstants"%>
<html>
<head>
    <title>客户列表</title>
    <meta name="layout" content="main"/>
    <asset:javascript src="/eiaClient/eiaClientIndex.js"/>
    <style>
        .linktxt{color: #01aaed;}
        .contract-box, .offer-box{display: none;}
    </style>
</head>
<body>
<div class="layui-fluid larry-wrapper pt0">
    <!--查询及添加框-->
    <input type="hidden" id="eiaClientId" value=""/>


    <!--列表-->
    <div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
        <table id="eiaClientList" lay-filter="eiaClientList"></table>
    </div>

    <div class="offer-box">
        <!--列表-->
        <div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
            <table id="eiaOfferList" lay-filter="eiaOfferList"></table>
        </div>
    </div>
    <div class="contract-box">

        <!--列表-->
        <div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
            <table id="eiaContractList" lay-filter="eiaContractList"></table>
        </div>
    </div>

</div>
<script type="text/html" id="mlTool">
    <div class="layui-btn-group">
        <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaCheck" title="查看"><i class="larry-icon">&#xe896;</i></a>
        %{--<g:if test="${session.staff.funcCode.contains(FuncConstants.EIA_YWCX_KHCX_EDIT)}">--}%
            %{--{{# if(d.inputUserId== ${session.staff.staffId}){}}--}%
                <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaEdit" title="编辑"><i class="larry-icon">&#xe646;</i></a>
            %{--{{# }}}--}%
            %{--<a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaDel" title="删除"><i class="larry-icon">&#xe8d0;</i></a>--}%
        %{--</g:if>--}%
    </div>
</script>
<script type="text/html" id="indexTable">
<span>{{d.LAY_TABLE_INDEX + 1}}</span>
</script>
<script type="text/html" id="contractTool">
<div class="layui-btn-group">
    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaCheck" title="查看"><i class="larry-icon">&#xe939;</i></a>
</div>
</script>
<script type="text/html" id="offerTool">
<div class="layui-btn-group">
    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaCheck" title="查看"><i class="larry-icon">&#xe939;</i></a>
</div>
</script>

<script type="text/html" id="contractCount">
    <span class="linktxt">{{d.contractCount}}</span>
</script>
<script type="text/html" id="offerCount">
    <span class="linktxt">{{d.offerCount}}</span>
</script>

<script type="text/html" id="tableTopTmp">
    <div class="layui-inline">
        <div class="layui-input-inline">
            <input type="text" name="clientName" value="" id="clientName" placeholder="客户名称、录入部门、录入人" class="layui-input larry-search-input w300">
        </div>
        <div class="layui-btn-group top-group">
            <a class="layui-btn search_btn pl12" lay-event="getSelect" data-type="getSelect"><i class="larry-icon">&#xe939;</i> 查询</a>
            <g:if test="${session.staff.funcCode.contains(FuncConstants.EIA_YWCX_KHCX_ADD)}">
                <a class="layui-btn layui-bg-pale pl12" lay-event="missionAdd" data-type="missionAdd"><i class="larry-icon">&#xe987;</i> 新增</a>
            </g:if>
        </div>
    </div>
</script>

<script type="text/html" id="tableTopTmp2">
<blockquote class="layui-elem-quote larry-btn">
    报价信息
</blockquote>
</script>

<script type="text/html" id="tableTopTmp3">
<blockquote class="layui-elem-quote larry-btn">
    合同信息
</blockquote>
</script>
</body>
</html>