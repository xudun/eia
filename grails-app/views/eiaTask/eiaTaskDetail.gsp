<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>任务信息查看</title>
    <meta name="layout" content="main"/>
    <asset:javascript src="/eiaTask/eiaTaskDetail.js"/>
</head>
<body>
<div class="layui-fluid larry-wrapper pt0">
    <div class="layer-title">
        <fieldset class="layui-elem-field layui-field-title site-title">
            <legend id="title">
                <a name="methodRender">查看任务</a>
            </legend>
        </fieldset>
    </div>

    <blockquote class="layui-elem-quote larry-btn">
        任务基本信息
    </blockquote>
    <div class="layui-row mt15">
        <div class="layui-col-xs6">
            <div class="layui-form-item">
                <label class="layui-form-label">任务单号</label>
                <div class="layui-input-block check-block" id="taskNo"></div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">业务类型</label>
                <div class="layui-input-block check-block" id="busiType"></div>
            </div>
        </div>

        <div class="layui-col-xs6">
            <div class="layui-form-item">
                <label class="layui-form-label">任务名称</label>
                <div class="layui-input-block check-block" id="taskName"></div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">负责部门</label>
                <div class="layui-input-block check-block" id="taskLeaderDept"></div>
            </div>
        </div>
    </div>

    <!--列表-->
    <div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
        <table id="taskAssignUserList" lay-filter="taskAssignUserList"></table>
    </div>

    <!--列表-->
    <div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
        <table id="eiaContractList" lay-filter="eiaContractList"></table>
    </div>

    <!--列表-->
    <div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
        <table id="eiaProjectList" lay-filter="eiaProjectList"></table>
    </div>
</div>
<input type="hidden" id="eiaProjectId" value=""/>
<input type="hidden" id="tableNameId" value=""/>
<script type="text/html" id="contractTool">
<div class="layui-btn-group">
    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaCheck" title="查看"><i class="larry-icon">&#xe939;</i></a>
</div>
</script>
<script type="text/html" id="taskTool">
<div class="layui-btn-group">
    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaCheck" title="查看"><i class="larry-icon">&#xe939;</i></a>
</div>
</script>
<script type="text/html" id="projectTool">
<div class="layui-btn-group">
    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaCheck" title="查看"><i class="larry-icon">&#xe939;</i></a>
</div>
</script>
<script type="text/html" id="indexTable">
<span>{{d.LAY_TABLE_INDEX + 1}}</span>
</script>
<script type="text/html" id="taskAssignUserTmp">
<div>
    分配人员信息
</div>
</script>
<script type="text/html" id="contractTmp">
<div>
    合同信息
</div>
</script>
<script type="text/html" id="projectTmp">
<div>
    项目信息
</div>
</script>
</body>
</html>