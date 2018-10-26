<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title></title>
    <meta name="layout" content="main"/>
    <asset:stylesheet src="/eiaLab/eiaLabOfferPlanCreate.css"/>
    <asset:javascript src="/eiaLab/eiaLabOfferPlanCreate.js"/>
</head>

<body class="pb68">
<div class="layui-fluid larry-wrapper pt0">
    <div class="layer-title">
        <fieldset class="layui-elem-field layui-field-title site-title">
            <legend id="title">
            </legend>
        </fieldset>
    </div>
    <form class="layui-form">
        <div class="layui-form-item">
            <label class="layui-form-label"><span class="col-f00">* </span>检测基质</label>
            <div class="layui-input-block layui-form" lay-filter="baseName">
                <select name="baseName" id="baseName" lay-filter="baseName" lay-verify="required" lay-search="">
                    %{--<option value="">请选择检测基质</option>--}%
                </select>
                <input type="hidden" id="labTestBaseId" name="labTestBaseId">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label"><span class="col-f00">* </span>检测项目</label>
            <div class="layui-input-block layui-form" lay-filter="paramNameCn">
                <select name="paramNameCn" id="paramNameCn" lay-filter="paramNameCn" lay-verify="required" lay-search="">
                    %{--<option value="">请选择检测参数</option>--}%
                </select>
                <input type="hidden" id="labTestParamId" name="labTestParamId">
            </div>
        </div>
        %{--<div class="layui-form-item display-none" id="ifShowScheme">--}%
            %{--<label class="layui-form-label"><span class="col-f00">* </span>检测标准</label>--}%
            %{--<div class="layui-input-block layui-form" lay-filter="schemeName">--}%
                %{--<select name="schemeName" id="schemeName" lay-filter="schemeName" lay-verify="required" lay-search="">--}%
                    %{--<option value="">请选择检测标准</option>--}%
                %{--</select>--}%
                %{--<input type="hidden" id="labTestSchemeId" name="labTestSchemeId">--}%
            %{--</div>--}%
        %{--</div>--}%
        <div class="layui-form-item">
            <label class="layui-form-label">备注</label>
            <div class="layui-input-block">
                <input type="text" id="memo" name="memo" class="layui-input">
            </div>
        </div>
        <div class="layui-col-xs6">
            %{--<div class="layui-form-item display-none" id="ifShowMoney">--}%
                %{--<label class="layui-form-label"><span class="col-f00">* </span>测试费用</label>--}%
                %{--<div class="layui-input-block">--}%
                    %{--<input type="text" id="schemeMoney" name="schemeMoney" class="layui-input" lay-verify="required|ratenum">--}%
                %{--</div>--}%
            %{--</div>--}%
            <div class="layui-form-item">
                <label class="layui-form-label">预估最高费用（元）</label>
                <div class="layui-input-block">
                    <input type="text" id="maxSchemeMoney" name="maxSchemeMoney" class="layui-input readonly" readonly>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label"><span class="col-f00">* </span>点位</label>
                <div class="layui-input-block">
                    <input type="text" id="pointNum" name="pointNum" class="layui-input" lay-verify="required|intnumber">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label"><span class="col-f00">* </span>天数周期</label>
                <div class="layui-input-block">
                    <input type="text" id="dayNum" name="dayNum" class="layui-input" lay-verify="required|intnumber">
                </div>
            </div>
        </div>
        <div class="layui-col-xs6">
            <div class="layui-form-item display-none" id="ifShowDiscount">
                <label class="layui-form-label">折扣（%）</label>
                <div class="layui-input-block">
                    <input type="text" id="discount" name="discount" class="layui-input" lay-verify="ratenum">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label"><span class="col-f00">* </span>频次</label>
                <div class="layui-input-block">
                    <input type="text" id="freqNum" name="freqNum" class="layui-input" lay-verify="required|intnumber">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">排列顺序</label>
                <div class="layui-input-block">
                    <input type="text" id="displayOrder" name="displayOrder" class="layui-input" lay-verify="intnumber">
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">参考限值标准</label>
            <div class="layui-input-block">
                <input type="text" id="referenceLimitStandard" name="referenceLimitStandard" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">参考限值</label>
            <div class="layui-input-block">
                <input type="text" id="referenceLimit" name="referenceLimit" class="layui-input">
            </div>
        </div>
        <input type="hidden" id="eiaLabOfferId" name="eiaLabOfferId">
        <input type="hidden" id="eiaLabOfferPlanId" name="eiaLabOfferPlanId">

        <blockquote class="layui-elem-quote larry-btn fixed-footer">
            <div class="layui-inline pl12">
                <div class="layui-btn-group top-group">
                    <button class="layui-btn pl12" lay-submit lay-filter="save">保存</button>
                    <i id="reset" class="layui-btn layui-btn-primary pl12">重置</i>
                </div>
            </div>
        </blockquote>
    </form>
</div>
<script id="addTitle" type="text/x-jquery-tmpl">
    <a name="methodRender">新增检测计划及费用明细</a>
</script>
<script id="editTitle" type="text/x-jquery-tmpl">
    <a name="methodRender">编辑检测计划及费用明细</a>
</script>
</body>
</html>