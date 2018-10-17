<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title></title>
    <meta name="layout" content="main"/>
    <style>
    .search-layer .layui-table-cell{
        height: auto;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: normal;
        box-sizing: border-box;
    }
    .search-layer .layui-table-view{
        margin: 0;
    }
    .searchTpInfo{
        text-align: left;
        width: 100%;
        word-wrap: break-word;
    　　word-break: break-all;
        line-height: 20px;
        font-size: 13px;
        color: #999;
    }
    .searchTpInfo .firstLine{
        color: #565656;
        font-size: 14px;
        margin-bottom: 5px;
    }
    .bg-f2f {
        background-color: #f2f2f2 !important;
    }
    </style>
</head>
<body>
<asset:stylesheet src="eiaGisGeo/gisGeoIndex.css"/>

<script type="text/javascript" src='https://a.amap.com/jsapi_demos/static/resource/capitals.js'></script>
<div id="map" class="map layui-fluid larry-wrapper">
    <div id="searchKeywords" class="layui-inline layui-form larry-btn">
        %{--搜索框--}%
        <div class="layui-input-inline">
            <input type="text" value="" id="keywords" placeholder="请输入地名" class="layui-input larry-search-input">
        </div>
        <a class="layui-btn search_btn pl12" data-type="getSelect"><i class="larry-icon">&#xe896;</i> 搜索</a>
        %{--搜索结果展示--}%
        <div class="searchResult w600 font12">
            <table id="searchResult" lay-filter="searchResult">
            </table>
        </div>
    </div>
    <div id="right-nav">
        <ul class="layui-nav layui-bg-cyan larry-btn">
            <li class="layui-nav-item"><a id="fullScreen" class="layui-btn bg-f2f" data-type="fullScreen"><i class="larry-icon">&#xe726;</i> 全屏</a></li>
            <li class="layui-nav-item display-none"><a id="exitFullScreen" class="layui-btn bg-f2f" data-type="exitFullScreen"><i class="larry-icon">&#xe7a1;</i> 退出全屏</a></li>
            <li class="layui-nav-item">
                <a href="javascript:;"><i class="larry-icon">&#xe78c;</i> 测量工具</a>
                <dl class="layui-nav-child">
                    <dd><a class="layui-btn" data-type="distance">测量距离</a></dd>
                    <dd><a class="layui-btn" data-type="area">测量面积</a></dd>
                </dl>
            </li>
            <li class="layui-nav-item">
                <a href="javascript:;">&nbsp;&nbsp;<i class="larry-icon">&#xe9b3;</i> 图形绘制</a>
                <dl class="layui-nav-child">
                    <dd><a id="Point" class="layui-btn" data-type="point">点</a></dd>
                    <dd><a id="line" class="layui-btn" data-type="line">线</a></dd>
                </dl>
            </li>
            <button id="confirm" class="layui-btn layui-btn-primary layui-btn-sm ml15 font12" style="display: none;">
                %{--<i class="layui-icon">&#x1005;</i>--}%
                确认
            </button>
            <button id="cancle" class="layui-btn layui-btn-primary layui-btn-sm font12" style="display: none;">
                %{--<i class="layui-icon">&#x1007;</i>--}%
                取消
            </button>
        </ul>
    </div>
</div>
<div class='layui-fluid larry-wrapper pt0 mt0' id="searchListLeft">

    <div class='layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12'>
        <table id='searchList' lay-filter='searchList'></table>
    </div>
</div>
<script type="text/html" id="searchListTool">
<div class="layui-btn-group">
    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="location"><i class="larry-icon">&#xea6b;</i></a>
    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="check"><i class="larry-icon">&#xe896;</i></a>
    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="draw" title="绘图"><i class="larry-icon">&#xe9b3;</i></a>
</div>
</script>
<!--列表操作列-->
<script type="text/html" id="searchResultBar">
<a class="layui-btn layui-btn-xs" lay-event="location" title="定位"><i class="larry-icon">&#xea6b;</i></a>
</script>
<script type="text/html" id="searchTp">
<div class="searchTpInfo">
    <div class="firstLine">
        <span>{{d.LAY_TABLE_INDEX + 1}}</span>.
        <span>{{d.name}}</span>
    </div>
    <p >{{d.rootType}}</p>
    <div>{{d.parentType}}</div>
    <div>{{d.type?"":d.type}}</div>
</div>
</script>
<asset:javascript src="eiaGisGeo/aMap/baseMap.js"/>
<asset:javascript src="eiaGisGeo/aMap/tools.js"/>
<asset:javascript src="eiaGisGeo/aMap/multiLayer.js"/>
<asset:javascript src="eiaGisGeo/aMap/draw.js"/>
<asset:javascript src="eiaGisGeo/aMap/loadingDB.js"/>
<asset:javascript src="eiaGisGeo/eiaMapDraw.js"/>
<asset:stylesheet src="gisTools/toolBar.css"/>
</body>
</html>