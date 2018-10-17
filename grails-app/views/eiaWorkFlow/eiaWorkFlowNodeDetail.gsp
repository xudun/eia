<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>流程节点详情</title>
    <meta name="layout" content="main"/>
    <asset:stylesheet src="/eiaWorkFlow/eiaWorkFlow.css"/>
    <asset:javascript src="/eiaWorkFlow/eiaWorkFlowNodeDetail.js"/>
</head>
<body>
<div class="layui-fluid larry-wrapper pt0">
    <div class="layer-title">
        <fieldset class="layui-elem-field layui-field-title site-title">
            <legend id="title">
                <a name="methodRender" class="pageTitle">流程节点详情</a>
            </legend>
        </fieldset>
    </div>

    <form class="layui-form flow-node-form">
        <blockquote class="layui-elem-quote larry-btn">
            流程节点信息
        </blockquote>
        <div class="layui-row mt15">
            <div class="layui-col-xs6">
                <div class="layui-form-item">
                    <label class="layui-form-label">流程节点编码</label>
                    <div class="layui-input-block check-block" id="nodesCode"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">流程节点名称</label>
                    <div class="layui-input-block check-block" id="nodesName"></div>
                </div>
                <div class="layui-form-item has-sq">
                    <label class="layui-form-label">流程节点图标</label>
                    <div class="layui-input-block check-block">
                        <span class="check-value" id="nodesIconName"></span>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">节点tab页名称</label>
                    <div class="layui-input-block check-block" id="nodesTabTitle"></div>
                </div>
                <div class="layui-form-item has-sq">
                    <label class="layui-form-label">流程节点颜色</label>
                    <div class="layui-input-block check-block">
                        <span class="check-value" id="nodesColor"></span>
                        <span class="layui-badge-rim"></span>
                    </div>
                </div>
            </div>
            <div class="layui-col-xs6">
                <div class="layui-form-item">
                    <label class="layui-form-label">流程节点序号</label>
                    <div class="layui-input-block check-block" id="nodesNum"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">流程节点权限控制类型</label>
                    <div class="layui-input-block check-block" id="nodesAuthType"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">流程节点Url</label>
                    <div class="layui-input-block check-block" id="nodesUrl"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">节点权限编码</label>
                    <div class="layui-input-block check-block" id="nodesAuthCode"></div>
                </div>
            </div>
        </div>

        <!--节点动作列表-->
        <div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
            <table id="eiaWorkFlowNodeProcessList" lay-filter="eiaWorkFlowNodeProcessList"></table>
        </div>

        <!--流程节点id-->
        <input type="hidden" id="eiaWorkFlowNodeId" name="eiaWorkFlowNodeId">
        <input type="hidden" id="eiaWorkFlowNodeProcessId" name="eiaWorkFlowNodeProcessId">
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
    <i class="larry-icon table-cell-icon">{{d.processIconName}}</i>
</script>
<script type="text/html" id="colorTp">
    <span class="layui-badge-rim" style="background:{{d.processColor}}"></span>
    <span class="layui-badge-rim">{{d.processColor}}</span>
</script>
<script type="text/html" id="tableTopTmp">
<div class="table-top">
    节点动作列表
</div>
</script>
</body>
</html>