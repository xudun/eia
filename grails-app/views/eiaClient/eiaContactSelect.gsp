
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>选择联系人</title>
    <meta name="layout" content="main"/>
    <asset:javascript src="/eiaClient/eiaContactSelect.js"/></head>

<body>
<div class="layui-fluid larry-wrapper pt0">
    <div class="layer-title">
        <fieldset class="layui-elem-field layui-field-title site-title">
            <legend id="title">
                <a name="methodRender" class="pageTitle">选择联系人</a>
            </legend>
        </fieldset>
    </div>

    <!--列表-->
    <div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
        <table id="contactList" lay-filter="contactList"></table>
    </div>
</div>

<!--客户id-->
<input type="hidden" id="eiaClientId" name="eiaClientId" value="${eiaClientId}">

<script type="text/html" id="clientTool">
    <div class="layui-btn-group">
        <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaDelContact" title="删除"><i class="larry-icon">&#xe8d0;</i></a>
        <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaAddContact" title="选择"><i class="larry-icon">&#xe88f;</i></a>
    </div>
</script>
<script type="text/html" id="indexTable">
<span>{{d.LAY_TABLE_INDEX + 1}}</span>
</script>

<script type="text/html" id="tableTopTmp">
    <div class="layui-inline pl12">
        <div class="layui-btn-group top-group">
            <a class="layui-btn tempStoreBtn" lay-event="contactAdd" data-type="contactAdd"><i class="larry-icon">&#xe987;</i> 新增</a>
        </div>
    </div>
</script>
</body>
</html>