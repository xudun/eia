<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title></title>
    <meta name="layout" content="main"/>
    <asset:javascript src="/eiaFileUpload/eiaFileUploadCreate.js"/>
</head>

<body>
<div class="shade display-none"></div>
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
            <div class="layui-inline pl12">
                <div class="layui-btn-group top-group">
                    <button class="layui-btn" id="save" lay-submit="" lay-filter="save"><i class="larry-icon">&#xe9d1;</i> 保存</button>
                    <button type="reset" class="layui-btn layui-btn-primary resetBtn"><i class="larry-icon">&#xe69a;</i> 重置</button>
                </div>
            </div>
        </blockquote>
        <div class="layui-form-item mt15">
            <label class="layui-form-label"><span class="col-f00">* </span>文件类型</label>
            <div class="layui-input-block" lay-filter="fileUploadType">
                <select name="fileUploadType" id="fileUploadType" lay-filter="fileUploadType"  lay-verify="required">
                    <option value=""></option>
                </select>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">文件上传</label>
            <div class="layui-input-block">
                <button type="button" class="layui-btn" id="eiaFileUpload">
                    <i class="layui-icon">&#xe67c;</i>选择文件
                </button>
                <span class="ml15" id="fileName"></span>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">文件备注</label>
            <div class="layui-input-block">
                <textarea type="text" name="fileNote" id="fileNote" lay-verify="" autocomplete="off" class="layui-textarea"></textarea>
            </div>
        </div>
    </form>
</div>
</body>
</html>