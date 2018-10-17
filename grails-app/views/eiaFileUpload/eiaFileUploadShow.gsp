<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title></title>
    <meta name="layout" content="main"/>
    <asset:javascript src="/eiaFileUpload/eiaFileUploadIndex.js"/>
</head>

<body>
<div class="layui-fluid larry-wrapper">
    <!--列表-->
    <div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
        <table id="eiaFileUploadList" lay-filter="eiaFileUploadList"></table>
    </div>
</div>
<input type="hidden" id="tableName" value=""/>
<input type="hidden" id="tableId" value=""/>
<script type="text/html" id="eiaFileUploadListBar">
<div class="layui-btn-group">
    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaFileUploadDetail" title="查看"><i class="larry-icon">&#xe939;</i></a>
    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaFileUploadDownload" title="下载"><i class="larry-icon">&#xe8cd;</i></a>
</div>
</script>

<script type="text/html" id="tableTopTmp">
<blockquote class="layui-elem-quote larry-btn">
    文件上传
</blockquote>
</script>
</body>
</html>