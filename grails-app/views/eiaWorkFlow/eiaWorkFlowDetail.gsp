<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>流程详情</title>
    <meta name="layout" content="main"/>
    <asset:stylesheet src="/eiaWorkFlow/eiaWorkFlow.css"/>
    <asset:javascript src="/eiaWorkFlow/eiaWorkFlowDetail.js"/>
</head>
<body>
<div class="layui-fluid larry-wrapper pt0">
    <div class="layer-title">
        <fieldset class="layui-elem-field layui-field-title site-title">
            <legend id="title">
                <a name="methodRender" class="pageTitle">流程详情</a>
            </legend>
        </fieldset>
    </div>

    <form class="layui-form">
        <blockquote class="layui-elem-quote larry-btn">
            流程基本信息
        </blockquote>
        <div class="layui-row mt15">
            <div class="layui-col-xs6">
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">* </span>流程编码</label>
                    <div class="layui-input-block check-block" id="workFlowCode"></div>
                </div>
            </div>
            <div class="layui-col-xs6">
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">* </span>流程名称</label>
                    <div class="layui-input-block check-block" id="workFlowName"></div>
                </div>
            </div>
        </div>

        <div class="layui-row mt15">
            <div class="layui-col-xs6">
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">* </span>流程版本</label>
                    <div class="layui-input-block check-block" id="workFlowVersion"></div>
                </div>
            </div>
        </div>

        <!--流程节点列表-->
        <div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
            <table id="eiaWorkFlowNodeList" lay-filter="eiaWorkFlowNodeList"></table>
        </div>

        <!--流程id-->
        <input type="hidden" id="eiaWorkFlowId" name="eiaWorkFlowId">
        <input type="hidden" id="eiaWorkFlowNodeId" name="eiaWorkFlowNodeId">
    </form>
</div>

<script type="text/html" id="indexTable">
    <span>{{d.LAY_TABLE_INDEX + 1}}</span>
</script>
<script type="text/html" id="mlTool">
    <div class="layui-btn-group">
        <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaCheck" title="查看"><i class="larry-icon">&#xe896;</i></a>
    </div>
</script>
<script type="text/html" id="iconTp">
    <i class="larry-icon table-cell-icon">{{d.nodesIconName}}</i>
</script>
<script type="text/html" id="colorTp">
    <!--<a href="/detail/{{d.id}}" class="layui-table-link">{{d.title}}</a>-->
    <span class="layui-badge-rim" style="background:{{d.nodesColor}}"></span>
    <span class="layui-badge-rim">{{d.nodesColor}}</span>
</script>
<script type="text/html" id="tableTopTmp">
<div class="table-top">
    流程节点列表
</div>
</script>
</body>
</html>