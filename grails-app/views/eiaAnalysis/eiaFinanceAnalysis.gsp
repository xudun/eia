<%@ page contentType="text/html;charset=UTF-8" import="com.lheia.eia.common.FuncConstants"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>财务统计分析</title>
    <meta name="layout" content="main"/>
    <asset:stylesheet src="/eiaBoard/eiaTaskBoard.css"/>
    <asset:javascript src="/eiaInvoice/tableAddSubTotalRow.js"/>
    <asset:javascript src="/eiaAnalysis/eiaFinanceAnalysis.js"/>
</head>

<body>
<div class="layui-fluid larry-wrapper pt0">
    <div class="layui-tab layui-tab-brief" lay-filter="financeTab">
        <ul class="layui-tab-title">
            <g:if test="${session.staff.funcCode.contains(FuncConstants.EIA_TJFX_CWTJFX_CWJCZTJ)}">
                <li class="cissAndStaff layui-this" tab-name="cissAndStaff">财务进出账统计</li>
                <input type="hidden" id="ifShowCissAndStaff" value="1">
            </g:if>
            <g:if test="${session.staff.funcCode.contains(FuncConstants.EIA_TJFX_CWTJFX_CWYJ)}">
                <li class="eiaAccountExpect" tab-name="eiaAccountExpect">财务预收预付款费</li>
                <input type="hidden" id="ifShowEiaAccountExpect" value="1">
            </g:if>
            <li class="invoiceIncome" tab-name="invoiceIncome">开票未进账<span class="layui-badge" id="invoiceNums" ></span></li>
        </ul>
        <div class="layui-tab-content pt0" style="height: 100px;">
            <g:if test="${session.staff.funcCode.contains(FuncConstants.EIA_TJFX_CWTJFX_CWJCZTJ)}">
                <!--财务进出账统计-->
                <div class="showCissAndStaffList layui-tab-item layui-show">
                    <!--列表-->
                    <div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
                        <table id="cissAndStaffList" lay-filter="cissAndStaffList" openState="0"></table>
                        <input type="hidden" id="cissStartTime" value="">
                        <input type="hidden" id="cissEndTime" value="">
                    </div>
                </div>
            </g:if>
            <g:if test="${session.staff.funcCode.contains(FuncConstants.EIA_TJFX_CWTJFX_CWYJ)}">
                <!--财务预收预付款费-->
                <div class="showEiaExpectList layui-tab-item ">
                    <!--列表-->
                    <div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
                        <table id="eiaExpectList" lay-filter="eiaExpectList"></table>
                        <input type="hidden" id="proExpectStartPeriod" value="">
                        <input type="hidden" id="proExpectEndPeriod" value="">
                    </div>
                </div>
            </g:if>
            <!--开票未进账-->
            <div class="showEiaInvoiceList layui-tab-item ">
                <!--列表-->
                <div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
                    <table id="eiaInvoiceList" lay-filter="eiaInvoiceList"></table>
                    <input type="hidden" id="invoiceOrgName" value="">
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/html" id="indexTable">
<span>{{d.LAY_TABLE_INDEX + 1}}</span>
</script>
<script type="text/html" id="depTp">
{{#  if(!d.ifStaff){ }}
{{#  if(!d.LAY_TABLE_INDEX){ }}
<a class="depLink"><i class="larry-icon tdIcon">&#xe8e2;</i>&nbsp;&nbsp;{{d.orgName}}</a>
{{#  } else { }}
<a class="depLink"><i class="larry-icon tdIcon">&#xe849;</i>&nbsp;&nbsp;{{d.orgName}}</a>
{{#  } }}
{{#  } else { }}
<a class="">{{d.orgName}}</a>
{{#  } }}
</script>
<!--财务进出帐统计-->
<script type="text/html" id="cissTmp">
<div class="layui-inline">
    <div class="layui-inline">
        <input type="text" class="layui-input" id="cissStartDate" name="cissStartDate" placeholder="签订起始时间">
    </div>

    <div class="layui-inline">
        <input type="text" class="layui-input" id="cissEndDate" name="cissEndDate" placeholder="签订结束时间">
    </div>
</div>
<div class="layui-btn-group top-group">
    <a class="layui-btn search_btn pl12" lay-event="getSelect"><i class="larry-icon">&#xe939;</i> 查询</a>
</div>
</script>
<!--开票未进帐-->
<script type="text/html" id="invoiceTmp">
<div class="layui-inline">
    <div class="layui-input-inline">
        <input type="text" name="contractName" value="" id="invoiceTitle" placeholder="合同名称、合同编号、录入部门、录入人" class="layui-input larry-search-input w300">
    </div>
</div>
<div class="layui-btn-group top-group">
    <a class="layui-btn search_btn pl12" lay-event="getSelect"><i class="larry-icon">&#xe939;</i> 查询</a>
</div>
</script>
<!--财务预计信息-->
<script type="text/html" id="expectTmp">
<div class="layui-inline">
    <div class="layui-input-inline">
        <input type="text" class="layui-input" id="expectStartPeriod" name="expectStartPeriod" placeholder="预计开始期次（年-月）">
    </div>
    <div class="layui-input-inline">
        <input type="text" class="layui-input" id="expectEndPeriod" name="expectEndPeriod" placeholder="预计结束期次（年-月）">
    </div>
</div>
<div class="layui-btn-group top-group">
    <a class="layui-btn search_btn pl12" lay-event="getSelect"><i class="larry-icon">&#xe939;</i> 查询</a>
</div>

</script>
</body>
</html>