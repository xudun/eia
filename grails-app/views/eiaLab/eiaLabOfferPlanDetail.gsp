<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title></title>
    <meta name="layout" content="main"/>
    <asset:javascript src="/eiaLab/eiaLabOfferPlanDetail.js"/>
</head>

<body>
<div class="layui-fluid larry-wrapper pt0">
    <div class="layer-title">
        <fieldset class="layui-elem-field layui-field-title site-title">
            <legend id="title">
                <a name="methodRender">检测计划及费用明细</a>
            </legend>
        </fieldset>
    </div>
    <form class="layui-form line-height">
        <div class="layui-form-item">
            <label class="layui-form-label">检测基质</label>
            <div class="layui-input-block" id="baseName">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">检测项目</label>
            <div class="layui-input-block" id="paramNameCn">
            </div>
        </div>
        %{--<div class="layui-form-item display-none" id="ifShowScheme">--}%
            %{--<label class="layui-form-label">检测标准</label>--}%
            %{--<div class="layui-input-block" id="schemeName">--}%
            %{--</div>--}%
        %{--</div>--}%
        <div class="layui-form-item">
            <label class="layui-form-label">备注</label>
            <div class="layui-input-block" id="memo">
            </div>
        </div>
        <div class="layui-col-xs6">
            <div class="layui-form-item display-none" id="ifShowMoney">
                <label class="layui-form-label">最高费用（元）</label>
                <div class="layui-input-block" id="maxSchemeMoney">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">点位</label>
                <div class="layui-input-block" id="pointNum">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">天数周期</label>
                <div class="layui-input-block" id="dayNum">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">参考限值标准</label>
                <div class="layui-input-block" id="referenceLimitStandard">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">是否选于套餐</label>
                <div class="layui-input-block" id="ifGroup">
                </div>
            </div>
        </div>
        <div class="layui-col-xs6">
            <div class="layui-form-item display-none" id="ifShowDiscount">
                <label class="layui-form-label">折扣（%）</label>
                <div class="layui-input-block" id="discount">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">频次</label>
                <div class="layui-input-block" id="freqNum">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">排列顺序</label>
                <div class="layui-input-block" id="displayOrder">
                </div>
            </div>
        </div>
        <div class="layui-col-xs6">
            <div class="layui-form-item">
                <label class="layui-form-label">参考限值</label>
                <div class="layui-input-block" id="referenceLimit">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">套餐名称</label>
                <div class="layui-input-block" id="groupName">
                </div>
            </div>
        </div>
        <input type="hidden" id="labInnerOfferId" name="labInnerOfferId">
        <input type="hidden" id="labInnerOfferPlanId" name="labInnerOfferPlanId">
    </form>
</div>
</body>
</html>