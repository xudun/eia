<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="layout" content="main"/>
    <title>检测计划计算器列表</title>
    <asset:stylesheet src="/eiaLab/eiaPlanCal.css"/>
    <asset:javascript src="/eiaLab/eiaPlanCalList.js"/>
</head>
<body>
<div class="layui-fluid larry-wrapper pt15">
    <blockquote class="layui-elem-quote larry-btn cal-quote">
        检测计划及费用明细
        <div class="layui-inline pl12">
            <div class="layui-btn-group top-group">
                <a class="layui-btn layui-bg-pale" data-type="planAdd"><i class="larry-icon">&#xe876;</i> 新增项目</a>
                <a class="layui-btn layui-bg-pale" data-type="planGroupAdd"><i class="larry-icon">&#xe876;</i> 新增套餐</a>
                <a class="layui-btn layui-bg-pale" data-type="createLabOffer"><i class="larry-icon">&#xe876;</i> 新增监测方案</a>
            </div>
        </div>
        <div class="layui-btn total-txt layui-btn-danger">总计：<span id="subtotal">0</span> 元</div>
    </blockquote>

    <!--列表-->
    <div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
        <table id="eiaPlanCalList" lay-filter="eiaPlanCalList"></table>
    </div>

    <input type="hidden" id="eiaLabOfferId" name="eiaLabOfferId">
</div>

<!--列表数据-->
<input type="hidden" name="eiaPlanCalListData" id="eiaPlanCalListData" value="">

<script type="text/html" id="indexTable">
    <span>{{d.LAY_TABLE_INDEX + 1}}</span>
</script>
<script type="text/html" id="eiaPlanCalListTool">
    <div class="layui-btn-group">
        <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="planDel" title="删除"><i class="larry-icon">&#xe8d0;</i></a>
    </div>
</script>
</body>
</html>