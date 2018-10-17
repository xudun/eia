<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="layout" content="main"/>
    <title>新增检测计划</title>
    <asset:stylesheet src="/eiaLab/eiaLabOfferPlanCreate.css"/>
    <asset:javascript src="/eiaLab/eiaPlanCalCreate.js"/>
</head>
<body>
<div class="layui-fluid larry-wrapper pt15">
    <div class="layer-title">
        <fieldset class="layui-elem-field layui-field-title site-title">
            <legend id="title">
                <a name="methodRender" class="pageTitle">新增检测计划及费用明细</a>
            </legend>
        </fieldset>
    </div>

    <form class="layui-form lab-plan-form">
        <div class="layui-row mt15">
            <div class="layui-col-xs12">
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">* </span>检测基质</label>
                    <div class="layui-input-block">
                        <select name="baseNameVal" id="baseNameVal" lay-filter="baseNameVal" lay-verify="required" lay-search="">
                            <option value="">请选择检测基质</option>
                        </select>
                        <input type="hidden" id="baseName" name="baseName">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">* </span>检测项目</label>
                    <div class="layui-input-block">
                        <select name="paramNameCnVal" id="paramNameCnVal" lay-filter="paramNameCnVal" lay-verify="required" lay-search="">
                            <option value="">请选择</option>
                        </select>
                        <input type="hidden" id="paramNameCn" name="paramNameCn">
                    </div>
                    </div>
                </div>
            </div>
        <div class="layui-row">
            <div class="layui-col-xs6">
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>预估最高费用（元）</label>
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
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>折扣（%）</label>
                    <div class="layui-input-block">
                        <input type="text" id="discount" name="discount" value="60" class="layui-input" lay-verify="ratenum">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">* </span>频次</label>
                    <div class="layui-input-block">
                        <input type="text" id="freqNum" name="freqNum" class="layui-input" lay-verify="required|intnumber">
                    </div>
                </div>
            </div>
            <div class="layui-col-xs12">
                <div class="layui-form-item">
                    <div class="layui-input-block">
                        <button class="layui-btn" lay-submit lay-filter="save">保存</button>
                        <button id="reset" class="layui-btn layui-btn-primary">重置</button>
                    </div>
                </div>
            </div>
        </div>
    </form>


</div>
</body>
</html>