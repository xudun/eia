<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="layout" content="main"/>
    <title></title>
    <asset:stylesheet src="/eiaCert/eiaCert.css"/>
    <asset:javascript src="/eiaCert/eiaCertDetail.js"/>
</head>
<body>
<div class="layui-fluid larry-wrapper pt15">
    <div class="layer-title">
        <fieldset class="layui-elem-field layui-field-title site-title">
            <legend id="title">
                <a name="methodRender" class="pageTitle">资质详情</a>
            </legend>
        </fieldset>
    </div>

    <form class="layui-form cert-form">
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
                <div class="layui-form-item cert-num-item">
                    <label class="layui-form-label"><span class="col-f00"></span>资质报告(份)</label>
                    <div class="layui-input-block check-block" id="certNum"></div>
                </div>
                <div class="layui-form-item display-none">
                    <label class="layui-form-label"><span class="col-f00"></span>开会时间</label>
                    <div class="layui-input-block check-block" id="meetDate"></div>
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
                    <label class="layui-form-label"><span class="col-f00"></span>申请时间</label>
                    <div class="layui-input-block check-block" id="applyDate"></div>
                </div>
            </div>
        </div>
    </form>
</div>

<!--项目id-->
<input type="hidden" id="eiaProjectId" name="eiaProjectId" >
<!--项目id-->
<input type="hidden" id="eiaCertId" name="eiaCertId"  value="${eiaCertId}">

</body>
</html>