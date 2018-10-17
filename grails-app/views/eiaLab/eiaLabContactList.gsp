<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title></title>
    <meta name="layout" content="main"/>
    <asset:javascript src="/eiaLab/eiaLabContactList.js"/>
</head>

<body>
<div class="layui-fluid larry-wrapper pt0">
    <div class="layer-title">
        <fieldset class="layui-elem-field layui-field-title site-title">
            <legend id="title">
                <a name="methodRender">联系人列表</a>
            </legend>
        </fieldset>
    </div>

    <table id="eiaLabContactList" lay-filter="eiaLabContactList"></table>
</div>
<input type="hidden" id="clientId" name="clientId" class="layui-input">
<!--列表操作列-->
<script type="text/html" id="eiaLabContactListBar">
    <div class="layui-btn-group">
        <a class="layui-btn layui-btn-sm" lay-event="clientSelected" title="选择"><i class="larry-icon">&#xe8bc;</i></a>
    </div>
</script>
<!--查询及添加框-->
<script type="text/html" id="tableTopTmp">
<div class="table-top">
    <div class="layui-inline">
        <div class="layui-input-inline">
            <input type="text" name="contactInfo" value="" id="contactInfo" placeholder="联系人姓名、电话" class="layui-input larry-search-input w150">
        </div>
        <div class="layui-btn-group top-group">
            <a class="layui-btn search_btn pl12" lay-event="contactSelect"><i class="larry-icon">&#xe896;</i> 查询</a>
        </div>
    </div>
</div>
</script>
</body>
</html>