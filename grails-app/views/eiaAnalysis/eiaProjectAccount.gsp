<%@ page contentType="text/html;charset=UTF-8" import="com.lheia.eia.common.FuncConstants"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>项目台账统计</title>
    <meta name="layout" content="main"/>
    <asset:stylesheet src="/eiaBoard/eiaTaskBoard.css"/>
    <asset:javascript src="/eiaInvoice/tableAddSubTotalRow.js"/>
    <asset:javascript src="/eiaAnalysis/eiaProjectAccount.js"/>
</head>
<body>
<div class="layui-fluid larry-wrapper pt0">
    <div class="layui-tab layui-tab-brief" lay-filter="contractOfferTab">
        <ul class="layui-tab-title">
            <li class="layui-this">项目台账统计</li>
        </ul>
        <div class="layui-tab-content" style="height: 100px;">
            <!--项目信息-->
            <div class="layui-tab-item layui-show">
                <!--列表-->
                <div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
                    <table id="eiaProjectList" lay-filter="eiaProjectList"></table>
                </div>
            </div>
        </div>
    </div>
</div>
<input type="hidden" id="eiaInvoiceId" name="eiaInvoiceId" value="">
<input type="hidden" id="contractId" name="contractId" value="">
<input type="hidden" id="eiaAccountExpectId" name="eiaAccountExpectId" value="">

%{--<script type="text/html" id="contractTool">
    <div class="layui-btn-group">
        <a class="layui-btn layui-btn-normal layui-btn-sm " lay-event="eiaEdit" title="进账"><i class="larry-icon">&#xe9a6;</i></a>
        <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaCheck" title="详情"><i class="larry-icon">&#xe896;</i></a>
    </div>
</script>--}%


<script type="text/html" id="indexTable">
    <span>{{d.LAY_TABLE_INDEX + 1}}</span>
</script>
<script type="text/html" id="yxTestTp">
{{#  if(d.ifLabTest != "无"){ }}
<a class="depLink">{{d.ifLabTest}}</a>
{{#  } else { }}
<a class="">{{d.ifLabTest}}</a>
{{#  } }}
</script>
<!--查询及添加框-->
<script type="text/html" id="tableTopTmp">
<div class="table-top">
    <legend id="title">
        <a name="methodRender" class="pageTitle"></a>
    </legend>
    <div class="layui-inline">
        <div class="layui-input-inline">
            <input type="text" name="projectName" value="" id="projectName" placeholder="项目名称、项目编号、录入部门、录入人" class="layui-input larry-search-input w300">
        </div>
        <div class="layui-btn-group top-group">
            <a class="layui-btn search_btn pl12" lay-event="getSelect"><i class="larry-icon">&#xe939;</i> 查询</a>
        </div>
    </div>
</div>
</script>
</body>
</html>