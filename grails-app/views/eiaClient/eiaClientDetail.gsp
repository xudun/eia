
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>查看客户</title>
    <meta name="layout" content="main"/>
    <asset:javascript src="/eiaClient/eiaClientDetail.js"/>
</head>

<body>
<div class="layui-fluid larry-wrapper pt0">
    <div class="layer-title">
        <fieldset class="layui-elem-field layui-field-title site-title">
            <legend id="title">
                <a name="methodRender" class="pageTitle">客户详情</a>
            </legend>
        </fieldset>
    </div>

    <form class="layui-form">
        <blockquote class="layui-elem-quote larry-btn">
            客户详情
        </blockquote>
        <div class="layui-row mt15">
            <div class="layui-col-xs6">
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>客户名称</label>
                    <div class="layui-input-block check-block" id="clientName"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>邮政编码</label>
                    <div class="layui-input-block check-block" id="clientPostCode"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>传真</label>
                    <div class="layui-input-block check-block" id="clientFax"></div>
                </div>
            </div>
            <div class="layui-col-xs6">
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>住所地址</label>
                    <div class="layui-input-block check-block" id="clientAddress"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>法人代表</label>
                    <div class="layui-input-block check-block" id="clientCorporate"></div>
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
        <!--任务信息列表-->
        <div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
            <table id="eiaTaskList" lay-filter="eiaTaskList"></table>
        </div>
        <!--列表-->
        <div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
            <table id="eiaOfferList" lay-filter="eiaOfferList"></table>
        </div>

        <!--列表-->
        <div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
            <table id="eiaContractList" lay-filter="eiaContractList"></table>
        </div>


        <!--列表-->
        <div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
            <table id="eiaProjectList" lay-filter="eiaProjectList"></table>
        </div>
    </form>

</div>
<!--客户id-->
<input type="hidden" id="eiaClientId" name="eiaClientId" value="">

<script type="text/html" id="mlTool">
    <div class="layui-btn-group">
        <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaCheck" title="查看"><i class="larry-icon">&#xe896;</i></a>
        <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaEdit" title="编辑"><i class="larry-icon">&#xe646;</i></a>
        <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaDel" title="删除"><i class="larry-icon">&#xe8d0;</i></a>
    </div>
</script>
<script type="text/html" id="contractTool">
<div class="layui-btn-group">
    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaCheck" title="查看"><i class="larry-icon">&#xe939;</i></a>
</div>
</script>
<script type="text/html" id="offerTool">
<div class="layui-btn-group">
    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaCheck" title="查看"><i class="larry-icon">&#xe939;</i></a>
</div>
</script>
<script type="text/html" id="projectTool">
<div class="layui-btn-group">
    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaCheck" title="查看"><i class="larry-icon">&#xe939;</i></a>
</div>
</script>
<script type="text/html" id="taskTool">
<div class="layui-btn-group">
    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaCheck" title="查看"><i class="larry-icon">&#xe939;</i></a>
</div>
</script>
<script type="text/html" id="indexTable">
<span>{{d.LAY_TABLE_INDEX + 1}}</span>
</script>
<script type="text/html" id="tableTopTmp">
联系人信息
</script>
<script type="text/html" id="tableTopTmp2">
财务开户信息
</script>
<script type="text/html" id="tableTopTmp6">
任务信息
</script>
<script type="text/html" id="tableTopTmp4">
报价信息
</script>

<script type="text/html" id="tableTopTmp3">
合同信息
</script>

<script type="text/html" id="tableTopTmp5">
项目信息
</script>
<input type="hidden" id="tableNameId" value=""/>
<input type="hidden" id="eiaProjectId" value=""/>
<input type="hidden" id="taskId" value=""/>

</body>
</html>