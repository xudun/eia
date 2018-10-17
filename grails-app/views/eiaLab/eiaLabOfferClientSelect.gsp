<%@ page contentType="text/html;charset=UTF-8" import="com.lheia.eia.common.FuncConstants" %>
<html>
<head>
    <title></title>
    <meta name="layout" content="main"/>
    <asset:javascript src="/eiaLab/eiaLabOfferClientSelect.js"/>
</head>

<body>
<div class="layui-fluid larry-wrapper pt0">
    <div class="layer-title">
        <fieldset class="layui-elem-field layui-field-title site-title">
            <legend id="title">
                <a name="methodRender">客户列表</a>
            </legend>
        </fieldset>
    </div>
    <table id="eiaClientList" lay-filter="eiaClientList"></table>
    <input type="hidden" id="type">

    <input id="contactPerson" type="hidden">
    <input id="contactPersonMobil" type="hidden">
    <input id="eiaClientId" type="hidden">
</div>
<!--列表操作列-->
<script type="text/html" id="sjClientListBar">
    <div class="layui-btn-group">
        <a class="layui-btn layui-btn-sm" lay-event="sjSelected" title="选择"><i class="larry-icon">&#xe8bc;</i></a>
    </div>
</script>
<!--查询及添加框-->
<script type="text/html" id="tableTopTmp">
<div class="table-top">
    <div class="layui-inline">
        <div class="layui-input-inline">
            <input type="text" name="entName" value="" id="entName" placeholder="客户名称,录入部门,录入人" class="layui-input larry-search-input w200">
        </div>
        <div class="layui-btn-group top-group">
            <a class="layui-btn search_btn pl12" lay-event="getSysSelect"><i class="larry-icon">&#xe896;</i> 查询</a>
            <a class="layui-btn layui-bg-pale pl12" lay-event="clientAdd"><i class="larry-icon">&#xe876;</i> 新增</a>
        </div>
    </div>
</div>
</script>
</body>
</html>