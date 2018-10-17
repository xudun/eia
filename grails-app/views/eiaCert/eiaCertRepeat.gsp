<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="layout" content="main"/>
    <title></title>
    <asset:stylesheet src="/eiaCert/eiaCert.css"/>
    <asset:javascript src="/eiaCert/eiaCertRepeat.js"/>
</head>
<body>
<div class="layui-fluid larry-wrapper pt15">
    <div class="layer-title">
        <fieldset class="layui-elem-field layui-field-title site-title">
            <legend id="title">
                <a name="methodRender" class="pageTitle">再次申请资质</a>
            </legend>
        </fieldset>
    </div>

    <form class="layui-form">
        <blockquote class="layui-elem-quote larry-btn">
            基本信息
            <div class="layui-inline pl12">
                <div class="layui-btn-group top-group">
                    <button class="layui-btn" lay-submit="" lay-filter="save"><i class="larry-icon">&#xe751;</i> 保存</button>
                </div>
            </div>
        </blockquote>
        <div class="layui-row mt15">
            <div class="layui-col-xs6">
                <div class="layui-form-item pro-name-item">
                    <label class="layui-form-label"><span class="col-f00"></span>项目名称</label>
                    <div class="layui-input-block check-block" id="projectName"></div>
                    <div class="tip-block">
                        <i class="larry-icon" id="tips">&#xe740;</i>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>是否申请资质</label>
                    <div class="layui-input-block check-block" id="ifApplyCert"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">* </span>资质报告(份)</label>
                    <div class="layui-input-block">
                        <input type="number" id="certNum" name="certNum" class="layui-input" lay-verify="required">
                    </div>
                </div>

            </div>
            <div class="layui-col-xs6">
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">* </span>申请日期</label>
                    <div class="layui-input-block">
                        <input type="text" id="applyDate" name="applyDate" class="layui-input" lay-verify="required">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>资质申请类型</label>
                    <div class="layui-input-block check-block" id="certType"></div>
                </div>
                <div class="layui-form-item display-none verify-item" id="meetDateDiv">
                    <label class="layui-form-label"><span class="col-f00">* </span>开会日期</label>
                    <div class="layui-input-block">
                        <input type="text" id="meetDate" name="meetDate" class="layui-input" lay-verify="required">
                    </div>
                </div>
            </div>
        </div>
    </form>
</div>
</body>
</html>