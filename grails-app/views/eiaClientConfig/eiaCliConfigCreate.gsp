
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>客户列表</title>
    <meta name="layout" content="main"/>
    <asset:javascript src="/eiaClientConfig/eiaCliConfigCreate.js"/>
</head>

<body>

<body>
<div class="layui-fluid larry-wrapper pt0">
    <div class="layer-title">
        <fieldset class="layui-elem-field layui-field-title site-title">
            <legend id="title">
                <a name="methodRender" class="pageTitle">财务开户信息</a>
            </legend>
        </fieldset>
    </div>

    <form class="layui-form">
        <blockquote class="layui-elem-quote larry-btn">
            财务开户信息
            <div class="layui-inline pl12">
                <div class="layui-btn-group top-group">
                    <button class="layui-btn" lay-submit="" lay-filter="save"><i class="larry-icon">&#xe9d1;</i> 保存</button>
                    <button type="button" class="layui-btn layui-btn-primary resetBtn"><i class="larry-icon">&#xe69a;</i> 重置</button>
                </div>
            </div>
        </blockquote>
        <div class="layui-row mt15">
            <div class="layui-col-xs6">
                <div class="layui-form-item">
                    <label class="layui-form-label">开票单位名称</label>
                    <div class="layui-input-block">
                        <input type="text" id="clientName" name="clientName" class="layui-input" value="">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">开户行账号</label>
                    <div class="layui-input-block">
                        <input type="text" id="bankAccount" name="bankAccount" class="layui-input"  value="">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">* </span>地址及电话</label>
                    <div class="layui-input-block">
                        <input type="text" id="addrTel" name="addrTel" class="layui-input " lay-verify="required" value="">
                    </div>
                </div>
            </div>
            <div class="layui-col-xs6">
                <div class="layui-form-item">
                    <label class="layui-form-label">税务登记代码</label>
                    <div class="layui-input-block">
                        <input type="text" id="taxRegCode" name="taxRegCode" class="layui-input" value="">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">开户行</label>
                    <div class="layui-input-block">
                        <input type="text" id="bankName" name="bankName" class="layui-input"  value="">
                    </div>
                </div>
            </div>
        </div>
    </form>
</div>
</body>
</html>