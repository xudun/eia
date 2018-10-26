<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title></title>
    <meta name="layout" content="main"/>
    <asset:stylesheet src="/eiaLab/eiaLabOfferCreate.css"/>
    <asset:javascript src="/eiaLab/eiaLabOfferPlanGroupCreate.js"/>
</head>

<body class="pb68">
<div class="layui-fluid larry-wrapper pt0">
    <div class="layer-title">
        <fieldset class="layui-elem-field layui-field-title site-title">
            <legend id="title">
                <a name="methodRender">新增套餐</a>
            </legend>
        </fieldset>
    </div>
    <form class="layui-form">

        <blockquote class="layui-elem-quote larry-btn fixed-footer">
            <div class="layui-inline pl12">
                <div class="layui-btn-group top-group">
                    <button class="layui-btn pl12" lay-submit lay-filter="save"><i class="larry-icon">&#xe9d1;</i> 保存</button>
                </div>
            </div>
        </blockquote>

        <div class="layui-row mt15">
            <div class="layui-col-xs6">
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">* </span>套餐名称</label>
                    <div class="layui-input-block">
                        <input type="text" name="groupName" id="groupName" lay-filter="showParamGroup" class="layui-input input-search-button readonly" readonly lay-verify="required">
                        <i class="larry-icon font20 cursor-pointer" id="paramGroupSelect">&#xe85b;</i>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">点位</label>
                    <div class="layui-input-block">
                        <input type="text" id="pointNum" name="pointNum" class="layui-input" lay-verify="intnumber">
                    </div>
                </div>
            </div>
            <div class="layui-col-xs6">
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">* </span>套餐费用（元）</label>
                    <div class="layui-input-block">
                        <input type="text" id="groupFee" name="groupFee" class="layui-input readonly" lay-verify="required" readonly>
                    </div>
                </div>
            </div>
            <input type="hidden" id="eiaLabOfferId" name="eiaLabOfferId">
            <input type="hidden" id="labTestParamGroupId" name="labTestParamGroupId">
        </div>
    </form>
</div>
</body>
</html>