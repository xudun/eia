<%@ page contentType="text/html;charset=UTF-8" import="com.lheia.eia.common.FuncConstants"%>
<%@ page import="com.lheia.eia.common.GeneConstants"%>
<html>
<head>
    <title></title>
    <meta name="layout" content="main"/>
    <asset:stylesheet src="/eiaLab/eiaLabOfferIndex.css"/>
    <asset:javascript src="/eiaLab/eiaLabOfferIndex.js"/>
</head>

<body><div class="layui-fluid larry-wrapper pt0">
    <!--列表-->
    <div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
        <table id="eiaLabOfferList" lay-filter="eiaLabOfferList"></table>
    </div>
    <input type="hidden" id="eiaLabOfferId" />
    <input type="hidden" id="clientId" />
    <input type="hidden" id="tableNameId" name="tableNameId" value="">
    <input type="hidden" id="tableName" name="tableName" value="">
</div>
<!--列表操作列-->
<script type="text/html" id="eiaLabOfferListBar">
    <div class="layui-btn-group">
        <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="offerShow" title="查看"><i class="larry-icon">&#xe939;</i></a>
        {{# if( d.offerState == '生成报价' ){ }}
            {{# if( d.pushState == '${GeneConstants.TAIZE_CREATE_INNER_OFFER}' ){ }}
                {{# if(d.inputUserId== ${session.staff.staffId}){}}
                    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="offerEdit" title="编辑"><i class="larry-icon">&#xe72b;</i></a>
                    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="offerDel" title="删除"><i class="larry-icon">&#xe8d0;</i></a>
                {{# }}}
                <g:if test="${session.staff.funcCode.contains(FuncConstants.EIA_XMGL_JCFA_SUBMIT)}">
                    {{# if( d.ifYxTest == true ){ }}
                        {{# if(d.inputUserId== ${session.staff.staffId}){}}
                            <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="offerSubmit" title="提交检测方案"><i class="larry-icon">&#xe88f;</i></a>
                        {{# }}}
                    {{# }}}
                </g:if>
            {{# } else if( d.pushState == '${GeneConstants.TAIZE_SUBMIT_INNER_OFFER}' ) { }}
                {{# if( d.ifHaveFlow == true ){ }}
                    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="offerFlow" title="查看流程"><i class="larry-icon">&#xea21;</i></a>
                {{# } }}
            {{# } }}
        {{# } }}
        {{# if( d.offerState != '${GeneConstants.OFFER_STOP}' ){ }}
            %{--<g:if test="${session.staff.funcCode.contains(FuncConstants.EIA_XMGL_JCFA_COPY)}">--}%
            %{--<a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="copyOffer" title="复制报价信息"><i class="larry-icon">&#xe8ee;</i></a>--}%
            %{--</g:if>--}%
            {{# if( d.pushState == '${GeneConstants.TAIZE_CREATE_INNER_OFFER}' ){ }}
                <g:if test="${session.staff.funcCode.contains(FuncConstants.EIA_XMGL_JCFA_STOP)}">
                    {{# if(d.inputUserId== ${session.staff.staffId}){}}
                    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="offerStop" title="报价作废"><i class="larry-icon">&#xe835;</i></a>
                    {{# }}}
                </g:if>
            {{# } }}
        {{# } else { }}
            <g:if test="${session.staff.funcCode.contains(FuncConstants.EIA_XMGL_JCFA_STOP)}">
                {{# if(d.inputUserId== ${session.staff.staffId}){}}
                    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="recoveryOffer" title="恢复报价"><i class="larry-icon">&#xe82e;</i></a>
                {{# }}}
            </g:if>
        {{# } }}
    </div>
</script>
<!--查询及添加框-->
<script type="text/html" id="tableTopTmp">
<div class="table-top">
    <div class="layui-inline">
        <g:if test="${session.staff.funcCode.contains(FuncConstants.EIA_XMGL_JCFA_QUERY)}">
            <div class="layui-input-inline">
                <input type="text" name="search" value="" id="projectName" placeholder="项目名称、受检单位、录入部门、录入人、报价编号" class="layui-input larry-search-input w400">
            </div>
        </g:if>
        <div class="layui-btn-group top-group">
            <g:if test="${session.staff.funcCode.contains(FuncConstants.EIA_XMGL_JCFA_QUERY)}">
                <a class="layui-btn search_btn pl12" lay-event="getSelect"><i class="larry-icon">&#xe896;</i> 查询</a>
            </g:if>
            <g:if test="${session.staff.funcCode.contains(FuncConstants.EIA_XMGL_JCFA_QUERY)}">
                <a class="layui-btn search_btn pl12" lay-event="highSelect" state="0"><i class="larry-icon">&#xe896;</i> 高级查询</a>
            </g:if>
            <g:if test="${session.staff.funcCode.contains(FuncConstants.EIA_XMGL_JCFA_ADD)}">
                <a class="layui-btn layui-bg-pale" lay-event="offerAdd"><i class="larry-icon">&#xe876; </i> 新增报价</a>
            </g:if>
            <a class="layui-btn layui-bg-pale" lay-event="planCal"><i class="larry-icon">&#xe876; </i> 检测费用计算器</a>
        </div>
    </div>
    <!--高级查询-->
    <div id="advanced-query" class="display-none">
        <fieldset class="layui-elem-field layui-field-title site-title">
            <legend><a name="methodRender">高级查询</a></legend>
        </fieldset>
        <form class="layui-form">
            <div class="layui-inline">
                <label class="layui-form-label">项目类型</label>
                <div class="layui-input-inline layui-form" lay-filter="projectType">
                    <select name="projectType" id="projectType">
                    </select>
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">所属部门</label>
                <div class="layui-input-inline">
                    <input type="text" name="qInputDept" autocomplete="off" id="qInputDept" class="layui-input">
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">录入人</label>
                <div class="layui-input-inline">
                    <input type="text" name="qInputUser" autocomplete="off" id="qInputUser" class="layui-input">
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">报价状态</label>
                <div class="layui-input-inline layui-form" lay-filter="offerState">
                    <select name="offerState" id="offerState">
                    </select>
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">是否宇相检测</label>
                <div class="layui-input-inline layui-form" lay-filter="ifYxTestChar">
                    <select name="ifYxTestChar" id="ifYxTestChar">
                        <option value="">请选择</option>
                        <option value="1">是</option>
                        <option value="0">否</option>
                    </select>
                </div>
            </div>
            <div class="layui-form-item larry-btn-btn">
                <button class="layui-btn pl12" lay-submit lay-filter="query"><i class="larry-icon">&#xe896;</i> 查询</button>
                <button type="reset" class="layui-btn layui-btn-primary pl12"><i class="larry-icon">&#xe6d7;</i> 重置</button>
            </div>
        </form>
    </div>
</div>
</script>
</body>
</html>