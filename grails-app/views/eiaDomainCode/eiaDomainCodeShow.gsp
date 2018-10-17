<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title></title>
    <meta name="layout" content="main"/>
    <asset:javascript src="/eiaDomainCode/eiaDomainCodeShow.js"/>
</head>
<body>
<div class="layui-fluid larry-wrapper pt0">
    <div class="layer-title">
        <fieldset class="layui-elem-field layui-field-title site-title">
            <legend id="title">
                <a name="methodRender">查看系统配置</a>
            </legend>
        </fieldset>
    </div>
    <form class="layui-form line-height">
        <blockquote class="layui-elem-quote">
            系统配置信息
        </blockquote>
        <div class="layui-row mt15">
            <div class="layui-col-xs6">
                <div class="layui-form-item">
                    <label class="layui-form-label">编码</label>
                    <div class="layui-input-block" id="code">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">编码类型</label>
                    <div class="layui-input-block" id="domain"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">显示顺序</label>
                    <div class="layui-input-block" id="displayOrder"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">编码备注</label>
                    <div class="layui-input-block" id="codeRemark"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">父节点编码</label>
                    <div class="layui-input-block" id="parentCode"></div>
                </div>
            </div>
            <div class="layui-col-xs6">
                <div class="layui-form-item">
                    <label class="layui-form-label">编码描述</label>
                    <div class="layui-input-block" id="codeDesc"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">编码类型描述</label>
                    <div class="layui-input-block" id="domainDesc"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">编码级别</label>
                    <div class="layui-input-block" id="codeLevel"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">父节点</label>
                    <div class="layui-input-block" id="parentId"></div>
                </div>
            </div>
        </div>
    </form>
</div>
</body>
</html>