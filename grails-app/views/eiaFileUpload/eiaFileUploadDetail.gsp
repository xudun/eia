<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title></title>
    <meta name="layout" content="main"/>
    <asset:javascript src="/eiaFileUpload/eiaFileUploadDetail.js"/>
    <asset:stylesheet src="/eiaFileUpload/eiaFileUploadDetail.css"/>
</head>

<body>
<div class="layui-fluid larry-wrapper pt0">
    <div class="layer-title">
        <fieldset class="layui-elem-field layui-field-title site-title">
            <legend id="title">
                <a name="methodRender" class="pageTitle">文件上传</a>
            </legend>
        </fieldset>
    </div>
    <form class="layui-form">
        <blockquote class="layui-elem-quote larry-btn">
            文件上传
        </blockquote>
        <div class="layui-form-item mt15">
            <label class="layui-form-label">文件类型</label>
            <div class="layui-input-block check-block" id="fileUploadType">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">文件上传</label>
            <div class="layui-input-block check-block">
                <span id="fileName" class="cursor-pointer"></span>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">文件备注</label>
            <div class="layui-input-block check-block" id="fileNote">
            </div>
        </div>
    </form>
</div>
</body>
</html>