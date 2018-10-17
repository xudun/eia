<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>财务信息</title>
    <meta name="layout" content="main"/>
    <asset:javascript src="/eiaWorkFlow/eiaWorkFlowCertFinance.js"/>
</head>

<body>
<!--财务信息-->
<table class="layui-hide" id="contractFinList" lay-filter="contractFinList"></table>

<!--开票信息-->
<table id="eiaInvoiceList" lay-filter="eiaInvoiceList"></table>

<!--进账信息-->
<table id="eiaInvoiceIncomeList" lay-filter="eiaInvoiceIncomeList"></table>

<!--出账信息-->
<table id="eiaInvoiceOutList" lay-filter="eiaInvoiceOutList"></table>

<script type="text/html" id="contractFinTmp">
<div class="table-top">
    财务信息
</div>
</script>
<script type="text/html" id="eiaInvoiceTmp">
<div class="table-top">
    开票信息
</div>
</script>
<script type="text/html" id="invoiceIncomeTmp">
<div class="table-top">
    进账信息
</div>
</script>
<script type="text/html" id="invoiceOutTmp">
<div class="table-top">
    出账信息
</div>
</script>
</body>
</html>