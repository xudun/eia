<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>新增流程</title>
    <meta name="layout" content="main"/>
    <asset:stylesheet src="/eiaWorkFlow/eiaWorkFlow.css"/>
    <asset:javascript src="/eiaWorkFlow/eiaWorkFlowCreate.js"/>
</head>
<body class="pb68">
<div class="layui-fluid larry-wrapper pt0">
    <div class="layer-title">
        <fieldset class="layui-elem-field layui-field-title site-title">
            <legend id="title">
                <a name="methodRender" class="pageTitle">新增流程</a>
            </legend>
        </fieldset>
    </div>

    <form class="layui-form">

        <blockquote class="layui-elem-quote larry-btn fixed-footer">
            <div class="layui-inline pl12">
                <div class="layui-btn-group top-group">
                    <button class="layui-btn pl12" lay-submit="" lay-filter="save"><i class="larry-icon">&#xe9d1;</i> 保存</button>
                    <button type="reset" class="layui-btn layui-btn-primary resetBtn pl12"><i class="larry-icon">&#xe69a;</i> 重置</button>
                </div>
            </div>
        </blockquote>

        <div class="layui-row mt15">
            <div class="layui-col-xs6">
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">* </span>流程编码</label>
                    <div class="layui-input-block">
                        <input type="text" id="workFlowCode" name="workFlowCode" class="layui-input" lay-verify="required" value="">
                    </div>
                </div>
            </div>
            <div class="layui-col-xs6">
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">* </span>流程名称</label>
                    <div class="layui-input-block">
                        <input type="text" id="workFlowName" name="workFlowName" class="layui-input" lay-verify="required">
                    </div>
                </div>
            </div>
        </div>

        <div class="layui-row mt15">
            <div class="layui-col-xs6">
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">* </span>流程版本号</label>
                    <div class="layui-input-block">
                        <input type="text" id="workFlowVersion" name="workFlowVersion" class="layui-input" lay-verify="intnumber" value="">
                    </div>
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
        <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaEdit" title="编辑"><i class="larry-icon">&#xe646;</i></a>
        <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaDel" title="删除"><i class="larry-icon">&#xe8d0;</i></a>
    </div>
</script>
<script type="text/html" id="iconTp">
    <i class="larry-icon table-cell-icon">{{d.nodesIconName}}</i>
</script>
<script type="text/html" id="colorTp">
    <span class="layui-badge-rim" style="background:{{d.nodesColor}}"></span>
    <span class="layui-badge-rim">{{d.nodesColor}}</span>
</script>
<script type="text/html" id="tableTopTmp">
<div class="table-top">
    流程节点列表
    <div class="layui-inline pl12">
        <div class="layui-btn-group top-group">
            <a class="layui-btn layui-bg-pale addFlowNodeBtn" lay-event="addFlowNodeBtn"><i class="larry-icon">&#xe81c;</i> 新增</a>
            <a class="layui-btn layui-bg-red noticeTag"><i class="larry-icon">&#xe740;</i> 保存流程基本信息后即可添加流程节点</a>
        </div>
    </div>
</div>
</script>
</body>
</html>