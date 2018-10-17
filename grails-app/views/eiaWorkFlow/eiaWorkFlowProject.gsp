<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>项目流程</title>
    <meta name="layout" content="main"/>
    <asset:javascript src="/eiaWorkFlow/eiaWorkFlowProject.js"/>
    <asset:stylesheet src="/eiaProject/eiaProjectCreate.css"/>
</head>
<body>
<div class="layui-fluid larry-wrapper pt0 proj-flow">
    <div class="layer-title">
        <fieldset class="layui-elem-field layui-field-title site-title">
            <legend>
                <a name="methodRender" class="pageTitle">项目流程</a>
            </legend>
        </fieldset>
    </div>
    <!--流程步骤-->
    <div class="layui-row mt15 layui-col-space15 proj-flow-steps">
    </div>

    <!--tab选项卡-->
    <div class="layui-tab layui-tab-brief flow-tab" lay-filter="flowTab">
        <!--按钮部分-->
        <div class="layui-btn-group top-group larry-btn-box">

        </div>
        <ul class="layui-tab-title flow-tab-title">
            <li class="last-title">流程状态</li>
        </ul>

        <div class="layui-tab-content flow-tab-content">
            <!--流程状态-->
            <div class="layui-tab-item last-tab-item">
                <!--列表-->
                <div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12 flow-state-box">
                    <table id="flowStateList" lay-filter="flowStateList"></table>
                </div>
            </div>
        </div>
    </div>

    <!--下方选项卡-->
    <div class="layui-tab layui-tab-card" lay-filter="flowFormTab">
        <ul class="layui-tab-title">
            <li class="layui-this">流转意见</li>
            <li>与我相关</li>
        </ul>
        <div class="layui-tab-content">
            <!--流转意见-->
            <div class="layui-tab-item layui-show">
                <ul id="optionFlow" class="opinion-list">

                </ul>
            </div>
            <!--与我相关-->
            <div class="layui-tab-item">
                <ul id="optionAboutMe" class="opinion-list ">

                </ul>
            </div>
        </div>
    </div>
</div>

<!--项目id-->
<input type="hidden" id="eiaProjectId" name="eiaProjectId" value="">

<script type="text/html" id="indexTable">
<span>{{d.LAY_TABLE_INDEX + 1}}</span>
</script>
</body>
</html>