<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>任务信息查看</title>
    <meta name="layout" content="main"/>
    <asset:javascript src="/eiaTask/eiaTaskLogDetail.js"/>
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
                <div class="layui-input-block check-block">
                    <span id="busiType"></span>&nbsp;
                    <i class="larry-icon font20 cursor-pointer display-none" id="busiTypeShow">&#xe740;</i>
                </div>
            </div>
        </div>
        <div class="layui-col-xs6">
            <div class="layui-form-item">
                <label class="layui-form-label">任务名称</label>
                <div class="layui-input-block check-block">
                    <span id="taskName"></span>&nbsp;
                    <i class="larry-icon font20 cursor-pointer display-none" id="taskNameShow">&#xe740;</i>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">负责部门</label>
                <div class="layui-input-block check-block">
                    <span id="taskLeaderDept"></span>&nbsp;
                    <i class="larry-icon font20 cursor-pointer display-none" id="taskLeaderDeptShow">&#xe740;</i>
                </div>
            </div>
        </div>
    </div>

    <!--列表-->
    <div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
        <table id="eiaTaskAssignLogList" lay-filter="eiaTaskAssignLogList"></table>
    </div>
</div>
<script type="text/html" id="stateTpl">
{{#  if(d.state != "无变更"){ }}
<span class="col-f00">{{d.state}}</span>
{{#  } else { }}
{{d.state}}
{{#  } }}
</script>
<script type="text/html" id="taskAssignUserTmp">
<div>
    分配人员信息
</div>
</script>
</body>
</html>