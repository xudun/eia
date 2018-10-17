<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title></title>
    <meta name="layout" content="main"/>

</head>
<body>
<asset:stylesheet src="eiaGisGeo/gisGeoIndex.css"/>
<div id="map" class="map layui-fluid larry-wrapper">
</div>

<!--搜索周边列表操作列-->
<script type="text/html" id="aroundSearchResultBar">
    <div class="layui-btn-group">
        <a class="layui-btn layui-btn-sm" lay-event="aroundSearchEdit" title="编辑"><i class="larry-icon">&#xe93c;</i></a>
    </div>
</script>
<!--环保目标列表操作列-->
<script type="text/html" id="EPGoalsTableBar">
    <div class="layui-btn-group">
        <a class="layui-btn layui-btn-sm" lay-event="EPGoalsLocation" title="定位"><i class="larry-icon">&#xea6b;</i></a>
    </div>
</script>
<!--基准目标列表操作列-->
<script type="text/html" id="datumGoalsTableBar">
    <div class="layui-btn-group">
        <a class="layui-btn layui-btn-sm" lay-event="datumGoalsLocation" title="定位"><i class="larry-icon">&#xea6b;</i></a>
    </div>
</script>
<!--辅助对象列表操作列-->
<script type="text/html" id="assistObjTableBar">
    <div class="layui-btn-group">
        <a class="layui-btn layui-btn-sm" lay-event="assistObjLocation" title="定位"><i class="larry-icon">&#xea6b;</i></a>
    </div>
</script>



<asset:javascript src="eiaGisGeo/aMap/baseMap.js"/>
<asset:javascript src="eiaGisGeo/aMap/tools.js"/>
<asset:javascript src="eiaGisGeo/aMap/multiLayer.js"/>
<asset:javascript src="eiaGisGeo/aMap/draw.js"/>
<asset:javascript src="eiaGisGeo/aMap/loadingDB.js"/>
<asset:javascript src="eiaGisGeo/aMap/drawCacheData.js"/>
<asset:javascript src="eiaGisGeo/gisGeoIndex.js"/>

<div id="panelRight" class="table-sm">
    <div class="layui-inline">
        <blockquote class="layui-elem-quote w395" id="projectName">

        </blockquote>
        %{--<div class="layui-input-inline w530" id="projectName">--}%
        %{--</div>--}%
    </div>
    <div class="layui-tab layui-tab-brief" lay-filter="">
        <ul class="layui-tab-title">
            <li class="layui-this">环保目标<span id="EPGoals" class="layui-badge">0</span></li>
            <li>基准目标<span id="datumGoals" class="layui-badge">0</span></li>
            <li>辅助对象<span id="assistObj" class="layui-badge">0</span></li>
        </ul>
        <div class="layui-tab-content" style="height: auto;">
            <div class="layui-tab-item layui-show">
                <table class="layui-hide" id="EPGoalsTable" lay-filter="EPGoalsTable"></table>
            </div>
            <div class="layui-tab-item">
                <table class="layui-hide" id="datumGoalsTable" lay-filter="datumGoalsTable"></table>
            </div>
            <div class="layui-tab-item">
                <table class="layui-hide" id="assistObjTable" lay-filter="assistObjTable"></table>
            </div>
        </div>
    </div>
</div>
</body>
</html>