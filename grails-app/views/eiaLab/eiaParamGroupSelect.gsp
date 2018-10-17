<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title></title>
    <meta name="layout" content="main"/>
    <asset:javascript src="/eiaLab/eiaParamGroupSelect.js"/>
</head>
<body>
<div class="layui-fluid larry-wrapper pt0">
    <div class="layer-title">
        <fieldset class="layui-elem-field layui-field-title site-title">
            <legend id="title">
                <a name="methodRender">检测套餐列表</a>
            </legend>
        </fieldset>
    </div>

    <table id="labTestParamGroupList" lay-filter="labTestParamGroupList"></table>
    <input type="hidden" id="labTestParamGroupId">
</div>
<!--列表操作列-->
<script type="text/html" id="labTestParamGroupListBar">
    <div class="layui-btn-group">
        <a class="layui-btn layui-btn-sm" lay-event="paramGroupSelected" title="选择"><i class="larry-icon">&#xe8bc;</i></a>
    </div>
</script>
<script type="text/html" id="paramGroupShow">
<a href="javascript:void(0);" class="layui-table-link paramGroupShow" id="{{d.id}}">{{ d.groupName }}</a>
</script>
<!--查询及添加框-->
<script type="text/html" id="tableTopTmp">
<div class="table-top">
    <div class="layui-inline">
        <div class="layui-input-inline">
            <input type="text" name="groupName" value="" id="groupName" placeholder="套餐名称" class="layui-input larry-search-input">
        </div>
        <div class="layui-btn-group top-group">
            <a class="layui-btn search_btn pl12" lay-event="getSelect"><i class="larry-icon">&#xe896;</i> 查询</a>
        </div>
    </div>
</div>
</script>
</body>
</html>