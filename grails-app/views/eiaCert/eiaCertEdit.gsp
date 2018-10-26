<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="layout" content="main"/>
    <title></title>
    <asset:stylesheet src="/eiaCert/eiaCert.css"/>
    <asset:javascript src="/eiaCert/eiaCertEdit.js"/>
</head>
<body class="pb68">
<div class="layui-fluid larry-wrapper pt15">
    <div class="layer-title">
        <fieldset class="layui-elem-field layui-field-title site-title">
            <legend id="title">
                <a name="methodRender" class="pageTitle">资质编辑</a>
            </legend>
        </fieldset>
    </div>

    <form class="layui-form cert-form">

        <blockquote class="layui-elem-quote larry-btn fixed-footer">
            <div class="layui-inline pl12">
                <div class="layui-btn-group top-group">
                    <button class="layui-btn pl12" lay-submit="" lay-filter="save"><i class="larry-icon">&#xe751;</i> 保存</button>
                    <button type="reset" class="layui-btn layui-btn-primary resetBtn pl12"><i class="larry-icon">&#xe69a;</i> 重置</button>
                </div>
            </div>
        </blockquote>

        <div class="layui-row mt15">
            <div class="layui-col-xs6">
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>项目编号</label>
                    <div class="layui-input-block check-block" id="projectNo"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>申请部门</label>
                    <div class="layui-input-block check-block" id="inputDept"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>是否申请资质</label>
                    <div class="layui-input-block check-block" id="ifApplyCert"></div>
                </div>
                <div class="layui-form-item ">
                    <label class="layui-form-label"><span class="col-f00"></span>资质报告(份)</label>
                    <div class="layui-input-block">
                        <input type="number" id="certNum" name="certNum" class="layui-input" lay-verify="intnumber">
                    </div>
                </div>
                <div class="layui-form-item display-none verify-item" id="meetDateDiv">
                    <label class="layui-form-label"><span class="col-f00">* </span>开会日期</label>
                    <div class="layui-input-block">
                        <input type="text" id="meetDate" name="meetDate" class="layui-input" lay-verify="required">
                    </div>
                </div>
            </div>
            <div class="layui-col-xs6">
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>项目名称</label>
                    <div class="layui-input-block check-block" id="projectName"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>申请人</label>
                    <div class="layui-input-block check-block" id="inputUser"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>资质申请类型</label>
                    <div class="layui-input-block check-block" id="certType"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">* </span>申请日期</label>
                    <div class="layui-input-block">
                        <input type="text" id="applyDate" name="applyDate" class="layui-input" lay-verify="required">
                    </div>
                </div>
            </div>
        </div>
        <!--项目id-->
        <input type="hidden" id="eiaCertId" name="eiaCertId"  value="${eiaCertId}">
    </form>
</div>


</body>
</html>