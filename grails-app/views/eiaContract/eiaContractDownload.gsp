<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>合同下载</title>
    <meta name="layout" content="main"/>
    <asset:stylesheet src="/eiaContract/eiaContractDownload.css"/>
    <asset:javascript src="/eiaContract/eiaContractDownload.js"/>
</head>

<body>
<div class="layui-fluid larry-wrapper pt15">
    <form class="layui-form con-download">
        <div class="layui-form-item">
            <div class="layui-input-block download-block">
                <input type="checkbox" id="contractSupply" name="contractSupply" lay-filter="contractSupply" title="合同补充协议模板" checked>
                <input type="checkbox" id="contractChange" name="contractChange" lay-filter="contractChange" title="合同变更协议模板">
            </div>

        </div>
        <input type="hidden" name="tempType" id="tempType" value="contractSupplyTmp" lay-verify="required">
        <input type="hidden" name="contractId" id="contractId" value="">
        <a class="layui-btn layui-btn-normal saveBtn" lay-submit="" lay-filter="save">下载</a>

    </form>
</div>
</body>
</html>