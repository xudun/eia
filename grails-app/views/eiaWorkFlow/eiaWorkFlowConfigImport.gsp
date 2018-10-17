<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>流程确认</title>
    <meta name="layout" content="main"/>
    <asset:stylesheet src="/eiaWorkFlow/eiaWorkFlowConfigImport.css"/>
</head>
<body>
<div class="layui-fluid larry-wrapper pt0 ">
    <div class="layer-title">
        <fieldset class="layui-elem-field layui-field-title site-title">
            <legend id="title">
                <a name="methodRender" class="pageTitle">工作流配置导入</a>
            </legend>
        </fieldset>
    </div>

    <form class="layui-form flow-conf">

        <textarea name="workFlowConfig" id="workFlowConfig" placeholder="请输入配置JSON" class="layui-textarea jsonConfig"  lay-verify="required" value=""></textarea>
        <button class="layui-btn layui-btn-normal saveBtnMid" lay-submit="" lay-filter="save">导入</button>
    </form>
</div>

<script type="text/html" id="indexTable">
    <span>{{d.LAY_TABLE_INDEX + 1}}</span>
</script>
<asset:javascript src="/eiaWorkFlow/eiaWorkFlowConfigImport.js"/>
</body>
</html>