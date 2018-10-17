<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>新增客户</title>
    <meta name="layout" content="main"/>
    <asset:javascript src="/eiaClient/eiaClientCreate.js"/>
</head>

<body>
<div class="layui-fluid larry-wrapper pt0">
    <div class="layer-title">
        <fieldset class="layui-elem-field layui-field-title site-title">
            <legend id="title">
                <a name="methodRender" class="pageTitle">新增客户</a>
            </legend>
        </fieldset>
    </div>

    <form class="layui-form">
        <blockquote class="layui-elem-quote larry-btn">
            客户基本信息
            <div class="layui-inline pl12">
                <div class="layui-btn-group top-group">
                    <button class="layui-btn" lay-submit="" lay-filter="save"><i class="larry-icon">&#xe9d1;</i> 保存</button>
                    <button type="reset" class="layui-btn layui-btn-primary resetBtn"><i class="larry-icon">&#xe69a;</i> 重置</button>
                </div>
            </div>
        </blockquote>
        <div class="layui-row mt15">
            <div class="layui-col-xs6">
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">* </span>客户名称</label>
                    <div class="layui-input-block">
                        <input type="text" id="clientName" name="clientName" class="layui-input" lay-verify="required" value="">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>邮政编码</label>
                    <div class="layui-input-block">
                        <input type="text" id="clientPostCode" name="clientPostCode" class="layui-input" lay-verify="number_noreq" value="">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>传真</label>
                    <div class="layui-input-block">
                        <input type="text" id="clientFax" name="clientFax" class="layui-input" lay-verify="number_noreq" value="">
                    </div>
                </div>
            </div>
            <div class="layui-col-xs6">
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">*</span>客户地址</label>
                    <div class="layui-input-block">
                        <input type="text" id="clientAddress" name="clientAddress" class="layui-input" lay-verify="required">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">*</span>法人代表</label>
                    <div class="layui-input-block">
                        <input type="text" id="clientCorporate" name="clientCorporate" class="layui-input"  lay-verify="required">
                    </div>
                </div>
            </div>
        </div>

        <!--联系人列表-->
        <div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
            <table id="contactsList" lay-filter="contactsList"></table>
        </div>

        <!--财务开户信息列表-->
        <div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
            <table id="eiaInvoiceList" lay-filter="eiaInvoiceList"></table>
        </div>

    </form>

</div>

<!--客户id-->
<input type="hidden" id="eiaClientId" name="eiaClientId" value="">
<input type="hidden" id="clientContactsId" name="clientContactsId" value="">
<input type="hidden" id="eiaClientConfigId" name="eiaClientConfigId" value="">
<script type="text/html" id="mlTool">
    <div class="layui-btn-group">
        <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaEdit" title="编辑"><i class="larry-icon">&#xe646;</i></a>
        <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaDel" title="删除"><i class="larry-icon">&#xe8d0;</i></a>
    </div>
</script>
<script type="text/html" id="clientTool">
<div class="layui-btn-group">
    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaEdit" title="编辑"><i class="larry-icon">&#xe646;</i></a>
    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaCissDel" title="删除"><i class="larry-icon">&#xe8d0;</i></a>
</div>
</script>
<script type="text/html" id="indexTable">
<span>{{d.LAY_TABLE_INDEX + 1}}</span>
</script>

<script type="text/html" id="tableTopTmp1">
    联系人信息
    <div class="layui-inline pl12">
        <a class="layui-btn layui-bg-pale addContactBtn" lay-event="addContactBtn"><i class="larry-icon">&#xe81c;</i> 新增</a>
        <a class="layui-btn layui-bg-red noticeTag"><i class="larry-icon">&#xe740;</i> 填写客户基本信息后即可添加联系人</a>
    </div>
</script>

<script type="text/html" id="tableTopTmp2">
    财务开户信息
    <div class="layui-inline pl12">
        <a class="layui-btn layui-bg-pale addInvoiceBtn" lay-event="addInvoiceBtn"><i class="larry-icon">&#xe81c;</i> 新增</a>
        <a class="layui-btn layui-bg-red noticeCissTag"><i class="larry-icon">&#xe740;</i> 填写客户基本信息后即可添加财务开户信息</a>
    </div>

</script>
</body>
</html>