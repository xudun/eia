<%@ page import="com.lheia.eia.common.FuncConstants"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>财务管理</title>
    <meta name="layout" content="main"/>
    <asset:javascript src="/eiaInvoice/tableAddSubTotalRow.js"/>
    <asset:javascript src="/eiaInvoice/eiaInvoiceIndex.js"/>
    <style>
        .layui-btn .expect-icon{
            width: 18px;
            height: 18px;            border: 1px solid #fff;
            display: inline-block;
            color: #fff;
            font-size: 12px;
            line-height: 18px;
            border-radius: 9px;
            position: relative;
            top: 5px;
            vertical-align: top;
        }
    </style>
</head>
<body>
<div class="layui-fluid larry-wrapper pt0">
    <div class="layui-tab layui-tab-brief" lay-filter="contractOfferTab">
        <ul class="layui-tab-title">
            <li class="layui-this">财务信息</li>
            <li>开票未确认<span class="layui-badge" id="kpqr"></span></li>
            <li>出账未确认<span class="layui-badge" id="czqr"></span></li>
            <li>开票已确认 <span class="layui-badge" id="czyqr"></span></li>
            <li>出账已确认<span class="layui-badge" id="czqry"></span></li>
            <g:if test="${isExcpet}">
                <li>预计未确认<span class="layui-badge" id="czyj"></span></li>
            </g:if>
        </ul>
        <div class="layui-tab-content" style="height: 100px;">
            <!--合同信息-->
            <div class="layui-tab-item layui-show">
                <!--查询及添加框-->
                <blockquote class="layui-elem-quote larry-btn">
                    <legend id="title">
                        <a name="methodRender" class="pageTitle"></a>
                    </legend>
                    <g:if test="${session.staff.funcCode.contains(FuncConstants.EIA_HGGL_HTCX_QUERY)}">
                        <div class="layui-inline">
                            <div class="layui-input-inline">
                                <input type="text" name="contractName" value="" id="contractNameC" placeholder="合同名称、合同编号、录入部门、录入人" class="layui-input larry-search-input">
                            </div>
                            <div class="layui-btn-group top-group">
                                <a class="layui-btn search_btn pl12" data-type="contractSelect"><i class="larry-icon">&#xe939;</i> 查询</a>
                            </div>
                        </div>
                    </g:if>
                </blockquote>

                <!--列表-->
                <div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
                    <table id="eiaContractList" lay-filter="eiaContractList"></table>
                </div>

            </div>
            <!--开票信息-->
            <div class="layui-tab-item">
                <!--查询及添加框-->
                <blockquote class="layui-elem-quote larry-btn">
                    <g:if test="${session.staff.funcCode.contains(FuncConstants.EIA_HGGL_HTCX_QUERY)}">
                        <div class="layui-inline">
                            <div class="layui-input-inline">
                                <input type="text" name="contractName" value="" id="contractNameK" placeholder="合同名称、合同编号、录入部门、录入人" class="layui-input larry-search-input">
                            </div>
                            <div class="layui-btn-group top-group">
                                <a class="layui-btn search_btn pl12" data-type="revSelect"><i class="larry-icon">&#xe939;</i> 查询</a>
                            </div>
                        </div>
                    </g:if>
                </blockquote>

                <!--列表-->
                <div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
                    <table id="eiaInvoiceList" lay-filter="eiaInvoiceList"></table>
                </div>
            </div>
            <!--出账信息-->
            <div class="layui-tab-item">
                <!--查询及添加框-->
                <blockquote class="layui-elem-quote larry-btn">
                    <g:if test="${session.staff.funcCode.contains(FuncConstants.EIA_HGGL_HTCX_QUERY)}">
                        <div class="layui-inline">
                            <div class="layui-input-inline">
                                <input type="text" name="contractName" value="" id="contractNameZ" placeholder="合同名称、合同编号、录入部门、录入人" class="layui-input larry-search-input">
                            </div>
                            <div class="layui-btn-group top-group">
                                <a class="layui-btn search_btn pl12" data-type="incomeSelect"><i class="larry-icon">&#xe939;</i> 查询</a>
                            </div>
                        </div>
                    </g:if>
                </blockquote>

                <!--列表-->
                <div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
                    <table id="eiaInvoiceOutList" lay-filter="eiaInvoiceOutList"></table>
                </div>
            </div>
            <!--开票已确信息-->
            <div class="layui-tab-item">
                <!--查询及添加框-->
                <blockquote class="layui-elem-quote larry-btn">
                    <g:if test="${session.staff.funcCode.contains(FuncConstants.EIA_HGGL_HTCX_QUERY)}">
                        <div class="layui-inline">
                            <div class="layui-input-inline">
                                <input type="text" name="contractName" value="" id="contractNameA" placeholder="合同名称、合同编号、录入部门、录入人" class="layui-input larry-search-input">
                            </div>
                            <div class="layui-btn-group top-group">
                                <a class="layui-btn search_btn pl12" data-type="revAlreadySelect"><i class="larry-icon">&#xe939;</i> 查询</a>
                            </div>
                        </div>
                    </g:if>
                </blockquote>

                <!--列表-->
                <div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
                    <table id="eiaInvoiceAlreadyList" lay-filter="eiaInvoiceAlreadyList"></table>
                </div>
            </div>
            <!--出账已确信息-->
            <div class="layui-tab-item">
                <!--查询及添加框-->
                <blockquote class="layui-elem-quote larry-btn">
                    <g:if test="${session.staff.funcCode.contains(FuncConstants.EIA_HGGL_HTCX_QUERY)}">
                        <div class="layui-inline">
                            <div class="layui-input-inline">
                                <input type="text" name="contractName" value="" id="contractNameKy" placeholder="合同名称、合同编号、录入部门、录入人" class="layui-input larry-search-input">
                            </div>
                            <div class="layui-btn-group top-group">
                                <a class="layui-btn search_btn pl12" data-type="outSelect"><i class="larry-icon">&#xe939;</i> 查询</a>
                            </div>
                        </div>
                    </g:if>
                </blockquote>

                <!--列表-->
                <div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
                    <table id="eiaOutAlreadyList" lay-filter="eiaOutAlreadyList"></table>
                </div>
            </div>
            <!--预计财务信息-->
            <div class="layui-tab-item">
                <!--查询及添加框-->
                <blockquote class="layui-elem-quote larry-btn">
                    <g:if test="${session.staff.funcCode.contains(FuncConstants.EIA_HGGL_HTCX_QUERY)}">
                        <div class="layui-inline">
                            <div class="layui-input-inline">
                                <input type="text" name="contractName" value="" id="contractNameY" placeholder="合同名称、合同编号、录入部门、录入人" class="layui-input larry-search-input">
                            </div>
                            <div class="layui-btn-group top-group">
                                <a class="layui-btn search_btn pl12" data-type="eiaExpectSelect"><i class="larry-icon">&#xe939;</i> 查询</a>
                            </div>
                        </div>
                    </g:if>
                </blockquote>
                <!--列表-->
                <div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
                    <table id="eiaExpectList" lay-filter="eiaExpectList"></table>
                </div>
            </div>
        </div>
    </div>
</div>
<input type="hidden" id="eiaInvoiceId" name="eiaInvoiceId" value="">
<input type="hidden" id="eiaIncomeOutId" name="eiaIncomeOutId" value="">
<input type="hidden" id="contractId" name="contractId" value="">
<input type="hidden" id="eiaAccountExpectId" name="eiaAccountExpectId" value="">
<script type="text/html" id="contractTool">
    <div class="layui-btn-group">
        {{# if(d.inputUserId== ${session.staff.staffId}){}}
            <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaEdit" title="开票"><i class="larry-icon">&#xe646;</i></a>
            <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaOut" title="出账"><i class="larry-icon">&#xe7cc;</i></a>
        {{# if(d.isExcpet){}}
         <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaExpect" title="财务预收预付款费"><span class="expect-icon">预</span></a>
        {{# }}}

        {{# }}}
        <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaCheck" title="详情"><i class="larry-icon">&#xe896;</i></a>
    </div>
</script>
<script type="text/html" id="invoiceTool">
    <div class="layui-btn-group">
        {{# if( d.accountState == '未提交' || d.accountState == '驳回' ){ }}
            {{# if(d.inputUserId== ${session.staff.staffId}){}}
                <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaEdit" title="编辑"><i class="larry-icon">&#xe646;</i></a>
                <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaSubmit" title="提交"><i class="larry-icon">&#xe88f;</i></a>
                <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaDel" title="删除" ><i class="larry-icon">&#xe8d0;</i></a>
            {{# }}}

        {{# } }}
        <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaCheck" title="查看"><i class="larry-icon">&#xe896;</i></a>
        <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaPrint"><i class="larry-icon">&#xe89a;</i></a>
    </div>
</script>
<script type="text/html" id="invoiceOutTool">
    <div class="layui-btn-group">
        {{# if( d.accountState == '未提交' || d.accountState == '驳回' ){ }}
            {{# if(d.inputUserId== ${session.staff.staffId}){}}
                <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaEdit" title="编辑"><i class="larry-icon">&#xe646;</i></a>
                <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaDel" title="删除"><i class="larry-icon">&#xe8d0;</i></a>
                <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaSubmit" title="提交"><i class="larry-icon">&#xe88f;</i></a>
            {{# }}}

        {{# } }}
        <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaCheck" title="查看"><i class="larry-icon">&#xe896;</i></a>

    </div>
</script>
<script type="text/html" id="invoiceAlreadyTool">
<div class="layui-btn-group">
    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaCheck" title="查看"><i class="larry-icon">&#xe896;</i></a>
    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaPrint"><i class="larry-icon">&#xe89a;</i></a>
</div>
</script>

<script type="text/html" id="expectTool">
<div class="layui-btn-group">
    {{# if( d.accountState == '未提交' || d.accountState == '驳回' ){ }}

    {{# if(d.inputUserId== ${session.staff.staffId}){}}
    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaEdit" title="编辑"><i class="larry-icon">&#xe646;</i></a>
    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaSubmit" title="提交"><i class="larry-icon">&#xe88f;</i></a>
    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaDel" title="删除"><i class="larry-icon">&#xe8d0;</i></a>
    {{# }}}
    {{# } }}
    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaCheck" title="查看"><i class="larry-icon">&#xe896;</i></a>
</div>
</script>

<script type="text/html" id="indexTable">
<span>{{d.LAY_TABLE_INDEX + 1}}</span>
</script>
</body>
</html>