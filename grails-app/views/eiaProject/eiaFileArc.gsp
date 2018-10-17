<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>流程确认</title>
    <meta name="layout" content="main"/>
    <asset:stylesheet src="/eiaProject/eiaProjectCreate.css"/>
</head>
<body>
<div class="layui-fluid larry-wrapper pt0 ">
    <div class="layer-title">
        <fieldset class="layui-elem-field layui-field-title site-title">
            <legend>
                <a name="methodRender" class="pageTitle">打印归档</a>
            </legend>
        </fieldset>
    </div>
    <form class="layui-form flow-conf">
        <blockquote class="layui-elem-quote larry-btn mb15">
            <div class="layui-inline pl12">
                <div class="layui-btn-group top-group">
                    <button class="layui-btn" lay-submit="" lay-filter="save">提交</button>
                </div>
            </div>
        </blockquote>
        <div class="layui-form-item">
            <label class="layui-form-label"><span class="col-f00">* </span>审查文号</label>
            <div class="layui-input-block">
                <input type="text" id="wordNo" name="wordNo" class="layui-input" lay-verify="required" autocomplete="off" value="">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label"><span class="col-f00">* </span>审查时间</label>
            <div class="layui-input-block">
                <input type="text" id="approvalDate" name="approvalDate" class="layui-input" lay-verify="required|date" placeholder="年-月-日" autocomplete="off" value="">
            </div>
        </div>
    </form>
</div>


<asset:javascript src="/eiaProject/eiaFileArc.js"/>
</body>
</html>