<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="layout" content="main"/>
    <title>新增套餐</title>
    <asset:stylesheet src="/eiaLab/eiaPlanCal.css"/>
    <asset:javascript src="/eiaLab/eiaPlanCalGroupCreate.js"/>
</head>
<body>
<div class="layui-fluid larry-wrapper pt15">
    <div class="layer-title">
        <fieldset class="layui-elem-field layui-field-title site-title">
            <legend id="title">
                <a name="methodRender" class="pageTitle">新增套餐</a>
            </legend>
        </fieldset>
    </div>

    <form class="layui-form lab-plan-group-form">
        <blockquote class="layui-elem-quote">
            选择套餐&nbsp;&nbsp;&nbsp;&nbsp;
            <div class="layui-btn-group top-group">
                <button class="layui-btn ml15 pl12" lay-submit lay-filter="save"><i class="larry-icon">&#xe9d1;</i> 保存</button>
            </div>
        </blockquote>
        <div class="layui-row mt15">
            <div class="layui-col-xs6">
                <div class="layui-form-item has-add-btn">
                    <label class="layui-form-label"><span class="col-f00">* </span>套餐名称</label>
                    <div class="layui-input-block">
                        <input type="text" name="groupName" id="groupName" lay-filter="showParamGroup" class="layui-input input-search-button readonly" readonly lay-verify="required">
                    </div>
                    <div class="add-btn">
                        <i class="larry-icon font20 cursor-pointer" id="paramGroupSelect">&#xe85b;</i>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>点位</label>
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
        </div>
        <input type="hidden" id="labTestParamGroupId" name="labTestParamGroupId">
    </form>


</div>

</body>
</html>