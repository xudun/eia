<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>项目进度</title>
    <meta name="layout" content="main"/>
    <asset:javascript src="/eiaProject/eiaProjectPlanEdit.js"/>
    <asset:stylesheet src="/eiaProject/eiaProjectPlanIndex.css"/>
</head>

<body>
<div class="layui-fluid larry-wrapper pt0">
    <div class="layer-title">
        <fieldset class="layui-elem-field layui-field-title site-title">
            <legend>
                <a name="methodRender" class="pageTitle">项目进度列表</a>
            </legend>
        </fieldset>
    </div>

    <!--列表-->
    <div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
        <table id="eiaProPlanItemList" lay-filter="eiaProPlanItemList"></table>
    </div>
</div>

<!--项目id-->
<input type="hidden" id="eiaProjectId" name="eiaProjectId" value="">
<input type="hidden" id="proPlanItemId" name="proPlanItemId" value="">
<input type="hidden" id="proPlanId" name="proPlanId" value="">
<input type="hidden" id="oid" name="oid" value="">

<script type="text/html" id="indexTable">
<span>{{d.LAY_TABLE_INDEX + 1}}</span>
</script>
<script type="text/html" id="mlTool">
    <div class="layui-btn-group">
        <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="planItemEdit" title="编辑"><i class="larry-icon">&#xe646;</i></a>
        %{--<a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="planItemDel" title="删除"><i class="larry-icon">&#xe8d0;</i></a>--}%
    </div>
</script>
</body>
</html>