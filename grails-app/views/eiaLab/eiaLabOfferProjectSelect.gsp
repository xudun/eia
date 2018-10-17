<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title></title>
    <meta name="layout" content="main"/>
    <asset:javascript src="/eiaLab/eiaLabOfferProjectSelect.js"/>
    <asset:stylesheet src="/eiaProject/eiaProPlanItemEdit.css"/>
</head>

<body>
<div class="layui-fluid larry-wrapper pt0">
    <div class="layer-title">
        <fieldset class="layui-elem-field layui-field-title site-title">
            <legend id="title">
                新增监测方案
            </legend>
        </fieldset>
    </div>
    <form class="layui-form proCreate">
        <blockquote class="layui-elem-quote larry-btn">
            项目信息
            <div class="layui-inline pl12">
                <div class="layui-btn-group top-group">
                    <button class="layui-btn" lay-submit="" lay-filter="save"><i class="larry-icon">&#xe9d1;</i> 保存</button>
                </div>
            </div>
        </blockquote>
        <div class="layui-row mt15">
            <div class="layui-form-item">
                <label class="layui-form-label"><span class="col-f00">* </span>项目名称</label>
                <div class="layui-input-block layui-form" lay-filter="projectName">
                    <select id="projectName" name="projectName" lay-filter="projectNameSelect" lay-verify="required" lay-search="">
                    </select>
                    <input type="hidden" id="eiaProjectId" name="eiaProjectId">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label"><span class="col-f00">* </span>项目地址</label>
                <div class="layui-input-block">
                    <input type="text" id="projectAddr" name="projectAddr"  class="layui-input readonly " lay-verify="required" readonly>
                </div>
            </div>
        </div>
        <input type="hidden" id="sampleType" name="sampleType" value="采样">
    </form>
</div>
</body>
</html>