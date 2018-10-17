<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>客户列表</title>
    <meta name="layout" content="main"/>
    <asset:javascript src="/eiaClient/eiaClientSelect.js"/>
</head>

<body>
<div class="layui-fluid larry-wrapper pt0">
    <div class="layer-title pt0">
        <fieldset class="layui-elem-field layui-field-title site-title">
            <legend id="title">
                <a name="methodRender" class="pageTitle">选择客户</a>
            </legend>
        </fieldset>
    </div>

    <!--列表-->
    <div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12 pt0">
        <table id="clientList" lay-filter="clientList"></table>
    </div>
</div>

<!--选中的客户数据-->
<input type="hidden" id="eiaClientNameSelected" name="eiaClientNameSelected" value="">
<input type="hidden" id="clientAddressSelected" name="clientAddressSelected" value="">
<input type="hidden" id="eiaClientId" name="eiaClientId" value="">

<script type="text/html" id="clientTool">
<div class="layui-btn-group pt0">
    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaSelectContact" title="选择联系人"><i class="larry-icon">&#xe88f;</i></a>
</div>
</script>
<script type="text/html" id="indexTable">
<span>{{d.LAY_TABLE_INDEX + 1}}</span>
</script>

<script type="text/html" id="tableTopTmp">
<!--查询及添加框-->
    <div class="layui-inline pt0">
        <div class="layui-input-inline">
            <input type="text" name="eiaClientName" value="" id="eiaClientName" placeholder="客户名称" class="layui-input larry-search-input">
        </div>
    </div>

    <div class="layui-btn-group top-group pt0">
        <a class="layui-btn search_btn pl12" lay-event="getSelect" data-type="getSelect"><i class="larry-icon">&#xe939;</i> 查询</a>
        <a class="layui-btn layui-bg-pale pl12" lay-event="clientAdd" data-type="clientAdd"><i class="larry-icon">&#xe987;</i> 新增</a>
    </div>
</script>
</body>
</html>