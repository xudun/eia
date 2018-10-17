<%@ page contentType="text/html;charset=UTF-8" %>
<html xmlns="http://www.w3.org/1999/html">
<head>
    <title></title>
    <meta name="layout" content="main"/>
    <asset:javascript src="/eiaDomainCode/eiaDomainCodeIndex.js"/>
</head>

<body><div class="layui-fluid larry-wrapper pt0">

    %{--<div class="query-more">--}%
        %{--<i id="open" class="layui-icon" onclick="queryOpen()">&#xe61a;</i>--}%
        %{--<i id="close" class="display-none layui-icon" onclick="queryClose()">&#xe619;</i>--}%
    %{--</div>--}%
    <!--列表-->
    <div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
        <table id="eiaDomainCodeList" lay-filter="eiaDomainCodeList"></table>
    </div>
    <input type="hidden" id="eiaDomainCodeId" />
</div>
<!--列表操作列-->
<script type="text/html" id="eiaDomainCodeListBar">
    <div class="layui-btn-group">
        <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="domainCodeShow" title="查看"><i class="larry-icon">&#xe939;</i></a>
        <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="domainCodeEdit" title="编辑"><i class="larry-icon">&#xe72b;</i></a>
        <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="domainCodeDel" title="删除"><i class="larry-icon">&#xe8d0;</i></a>
    </div>
</script>

<script type="text/html" id="tableTopTmp">
<!--查询及添加框-->
    <div class="layui-inline">
        <div class="layui-input-inline">
            <input type="text" name="search" value="" id="codeDesc" placeholder="编码名称" class="layui-input larry-search-input">
        </div>
        <div class="layui-btn-group top-group">
            <a class="layui-btn search_btn pl12" lay-event="getSelect" data-type="getSelect"><i class="larry-icon">&#xe896;</i> 查询</a>
            <a class="layui-btn layui-bg-pale" lay-event="domainCodeAdd" data-type="domainCodeAdd"><i class="larry-icon">&#xe876;</i> 新增</a>
        </div>
    </div>
    %{--<!--高级查询-->
    <div id="advanced-query" class="display-none">
        <fieldset class="layui-elem-field layui-field-title site-title">
            <legend><a name="methodRender">高级查询</a></legend>
        </fieldset>
        <form class="layui-form">
            <div class="layui-inline">
                <label class="layui-form-label">项目类型</label>
                <div class="layui-input-inline layui-form" lay-filter="projectType">
                    <select name="projectType" id="projectType">
                    </select>
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">签订年月</label>
                <div class="layui-input-inline">
                    <input type="text" name="reportDate" class="layui-input" id="reportDate">
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">所属部门</label>
                <div class="layui-input-inline">
                    <input type="text" name="qInputDept" autocomplete="off" id="qInputDept" class="layui-input">
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">录入人</label>
                <div class="layui-input-inline">
                    <input type="text" name="qInputUser" autocomplete="off" id="qInputUser" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item larry-btn-btn">
                <button class="layui-btn pl12" lay-submit lay-filter="query"><i class="larry-icon">&#xe896;</i> 查询</button>
                <button type="reset" class="layui-btn layui-btn-primary pl12"><i class="larry-icon">&#xe6d7;</i> 重置</button>
            </div>
        </form>
    </div>--}%
</script>
</body>
</html>