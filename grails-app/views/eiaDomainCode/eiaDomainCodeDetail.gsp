<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title></title>
    <meta name="layout" content="main"/>
    <asset:javascript src="/eiaDomainCode/eiaDomainCodeDetail.js"/>
</head>
<body class="pb68">
<div class="layui-fluid larry-wrapper pt0">
    <div class="layer-title">
        <fieldset class="layui-elem-field layui-field-title site-title">
            <legend id="title">
            </legend>
        </fieldset>
    </div>
    <form class="layui-form">

        <blockquote class="layui-elem-quote larry-btn fixed-footer">
            <div class="layui-inline pl12">
                <div class="layui-btn-group top-group">
                    <button class="layui-btn ml15 pl12" lay-submit lay-filter="save"><i class="larry-icon">&#xe9d1;</i> 保存</button>
                </div>
            </div>
        </blockquote>

        <div class="layui-row mt15">
            <div class="layui-col-xs6">
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">* </span>编码</label>
                    <div class="layui-input-block">
                        <input type="text" name="code" id="code" class="layui-input" lay-verify="required">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">* </span>编码类型</label>
                    <div class="layui-input-block">
                        <input type="text" name="domain" id="domain" class="layui-input" lay-verify="required">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">* </span>显示顺序</label>
                    <div class="layui-input-block">
                        <input type="text" name="displayOrder" id="displayOrder" class="layui-input" lay-verify="required|intnumber">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">编码备注</label>
                    <div class="layui-input-block">
                        <input type="text" name="codeRemark" id="codeRemark" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">父节点编码</label>
                    <div class="layui-input-block">
                        <input type="text" name="parentCode" id="parentCode" class="layui-input">
                    </div>
                </div>
            </div>
            <div class="layui-col-xs6">
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">* </span>编码描述</label>
                    <div class="layui-input-block">
                        <input type="text" name="codeDesc" id="codeDesc" class="layui-input" lay-verify="required">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">* </span>编码类型描述</label>
                    <div class="layui-input-block">
                        <input type="text" name="domainDesc" id="domainDesc" class="layui-input" lay-verify="required">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">* </span>编码级别</label>
                    <div class="layui-input-block">
                        <input type="text" name="codeLevel" id="codeLevel" class="layui-input" lay-verify="required|intnumber">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">父节点</label>
                    <div class="layui-input-block">
                        <input type="text" name="parentId" id="parentId" class="layui-input" lay-verify="intnumber">
                    </div>
                </div>
            </div>
        </div>
        <input type="hidden" id="eiaDomainCodeId" name="eiaDomainCodeId" />
    </form>
</div>
<script id="addTitle" type="text/x-jquery-tmpl">
    <a name="methodRender">新增系统配置</a>
</script>
<script id="editTitle" type="text/x-jquery-tmpl">
    <a name="methodRender">编辑系统配置</a>
</script>
</body>
</html>