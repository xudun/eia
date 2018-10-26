<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title></title>
    <meta name="layout" content="main"/>
    <asset:stylesheet src="/eiaLab/eiaLabOfferCreate.css"/>
    <asset:javascript src="/eiaLab/eiaLabOfferPlanBatchDiscDetail.js"/>
</head>
<body class="pb68">
<div class="layui-fluid larry-wrapper pt0">
    <div class="layer-title">
        <fieldset class="layui-elem-field layui-field-title site-title">
            <legend id="title">
                <a name="methodRender">检测费用计算器</a>
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
                    <label class="layui-form-label">预估检测费用（元）</label>
                    <div class="layui-input-block">
                        <input type="text" name="maxSampleFee" id="maxSampleFee" class="layui-input readonly" readonly>
                    </div>
                </div>
            </div>
            <div class="layui-col-xs6">
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">* </span>批量打折（%）</label>
                    <div class="layui-input-block">
                        <input type="text" name="discount" id="discount" class="layui-input" lay-verify="required|ratenum">
                    </div>
                </div>
            </div>
        </div>
        <input type="hidden" id="eiaLabOfferId" name="eiaLabOfferId">
    </form>
</div>

</body>
</html>