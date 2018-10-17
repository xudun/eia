<%@ page import="com.lheia.eia.common.FuncConstants" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>财务管理</title>
    <meta name="layout" content="main"/>
    <asset:javascript src="/eiaInvoice/eiaInvoiceList.js"/>
</head>
<body>
<div class="layui-fluid larry-wrapper pt0">
    <div class="layui-tab layui-tab-brief" lay-filter="contractOfferTab">
        <ul class="layui-tab-title">
            <li class="layui-this">开票信息</li>
            <li>进账信息</li>
            <li>出账信息</li>
            <g:if test="${session.staff.funcCode.contains(FuncConstants.EIA_CWGL_CWRYZWGL_CWYJAUDIT) || session.staff.funcCode.contains(FuncConstants.EIA_CWGL_YWRYZWGL_CWYJ)}">
                <li>财务预收预付款费</li>
            </g:if>

        </ul>
        <div class="layui-tab-content" style="height: 100px;">
            <!--报价信息-->
            <div class="layui-tab-item layui-show">
                <!--列表-->
                <div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
                    <table id="eiaContractList" lay-filter="eiaContractList"></table>
                </div>

            </div>
            <!--合同信息-->
            <div class="layui-tab-item">
                <!--列表-->
                <div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
                    <table id="eiaInvoiceList" lay-filter="eiaInvoiceList"></table>
                </div>
            </div>
            <!--合同信息-->
            <div class="layui-tab-item">
                <!--列表-->
                <div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
                    <table id="eiaInvoiceOutList" lay-filter="eiaInvoiceOutList"></table>
                </div>
            </div>
            <!--预计信息-->
            <div class="layui-tab-item">
                <!--列表-->
                <div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
                    <table id="eiaAccountExpectList" lay-filter="eiaAccountExpectList"></table>
                </div>
            </div>
        </div>
    </div>
</div>
<input type="hidden" id="eiaInvoiceId" name="eiaInvoiceId" value="">

<script type="text/html" id="contractTool">
    <div class="layui-btn-group">
        <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaCheck" title="查看"><i class="larry-icon">&#xe896;</i></a>
    </div>
</script>
<script type="text/html" id="invoiceTool">
    <div class="layui-btn-group">
        <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaCheck" title="查看"><i class="larry-icon">&#xe896;</i></a>
    </div>
</script>
<script type="text/html" id="invoiceOutTool">
    <div class="layui-btn-group">
        <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaCheck" title="查看"><i class="larry-icon">&#xe896;</i></a>
    </div>
</script>
<script type="text/html" id="indexTable">
<span>{{d.LAY_TABLE_INDEX + 1}}</span>
</script>
</body>
</html>