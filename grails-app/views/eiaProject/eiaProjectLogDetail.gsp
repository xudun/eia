<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>项目变更信息</title>
    <meta name="layout" content="main"/>
    <asset:stylesheet src="/eiaProject/eiaProjectCreate.css"/>
    <asset:javascript src="/eiaProject/eiaProjectLogInputLib.js"/>
    <asset:javascript src="/eiaProject/eiaProjectLogFillInputLib.js"/>
    <asset:javascript src="/eiaProject/eiaProjectLogDetail.js"/>
    <style>
        .check-block i{
            vertical-align: middle;
            position: relative;
            top: -3px;
        }
    </style>
</head>
<body>
<div class="layui-fluid larry-wrapper pt0">
    <div class="layer-title">
        <fieldset class="layui-elem-field layui-field-title site-title">
            <legend id="title">
                <a name="methodRender" class="pageTitle">项目变更详情</a>
            </legend>
        </fieldset>
    </div>
    <form class="layui-form proCreate">
        <input type="hidden" id="eiaProjectLogId" name="eiaProjectLogId">
        <div class="layui-row mt15">
            <div class="layui-col-xs6">
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>项目名称</label>
                    <div class="layui-input-block check-block">
                        <span id="projectName"></span>&nbsp;
                        <i class="larry-icon font20 cursor-pointer display-none" id="projectNameShow">&#xe740;</i>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>建设地点</label>
                    <div class="layui-input-block check-block">
                        <span id="buildArea"></span>&nbsp;
                        <i class="larry-icon font20 cursor-pointer display-none" id="buildAreaShow">&#xe740;</i>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>项目负责人</label>
                    <div class="layui-input-block check-block">
                        <span id="dutyUser"></span>&nbsp;
                        <i class="larry-icon font20 cursor-pointer display-none" id="dutyUserShow">&#xe740;</i>
                    </div>
                </div>
            </div>
            <div class="layui-col-xs6">
                <div class="layui-form-item proMonBlock">
                    <label class="layui-form-label"><span class="col-f00"></span>文件类型</label>
                    <div class="layui-input-block check-block">
                        <span id="fileTypeChild"></span>&nbsp;
                        <i class="larry-icon font20 cursor-pointer display-none" id="fileTypeChildShow">&#xe740;</i>
                        <input type="hidden" id="fileTypehidden" name="fileTypehidden" lay-verify="required" value="">
                    </div>
                </div>
                <div class="layui-form-item proMonBlock">
                    <label class="layui-form-label"><span class="col-f00"></span>项目金额</label>
                    <div class="layui-input-block check-block">
                        <span id="projectMoney"></span><span>&nbsp;&nbsp;(万元)</span>
                        <i class="larry-icon font20 cursor-pointer display-none" id="projectMoneyShow">&#xe740;</i>
                    </div>
                </div>
            </div>
        </div>

        <div class="layui-row mt15 dynMoneyInputs">
            <div class="layui-col-xs6"></div>
            <div class="layui-col-xs6"></div>
        </div>

        <div class="layui-row mt15 dynInputs">
            <div class="layui-col-xs6"></div>
            <div class="layui-col-xs6"></div>
        </div>
    </form>
</div>
</body>
</html>