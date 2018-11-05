<%@ page contentType="text/html;charset=UTF-8" import="com.lheia.eia.common.FuncConstants"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>合同报价</title>
    <meta name="layout" content="main"/>
    <asset:stylesheet src="/eiaContract/eiaConOfferIndex.css"/>
    <asset:javascript src="/eiaContract/eiaConOfferIndex.js"/>
</head>

<body>
<div class="layui-fluid larry-wrapper pt0">
    <div class="layui-tab layui-tab-brief" lay-filter="contractOfferTab">
        <ul class="layui-tab-title">
            <li class="layui-this" lay-id="contractTab" >合同信息</li>
            <li lay-id="offerTab">报价信息</li>
        </ul>
        <div class="layui-tab-content pt0" style="height: 100px;">
            <!--合同信息-->
            <div class="layui-tab-item layui-show">
                <!--列表-->
                <div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
                    <table id="eiaContractList" lay-filter="eiaContractList"></table>
                    <input type="hidden" id="advancedState" name="advancedState" value="0">
                    <input type="hidden" id="queryData" name="queryData" value="{}">
                </div>
            </div>
            <!--报价信息-->
            <div class="layui-tab-item ">
                <!--列表-->
                <div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
                    <table id="eiaOfferList" lay-filter="eiaOfferList"></table>
                </div>

            </div>
        </div>
    </div>
</div>
<script type="text/html" id="contractTool">
<div class="layui-btn-group">
    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaCheck" title="查看"><i class="larry-icon">&#xe896;</i></a>
    {{# if( d.contractNo ){ }}
        {{# if(d.inputUserId== ${session.staff.staffId}){}}
            <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="contractExport" title="合同模板导出"><i class="larry-icon">&#xe8cd;</i></a>
        {{# }}}
    {{# }}}
    {{# if(!d.ifSub){}}
    <g:if test="${session.staff.funcCode.contains(FuncConstants.EIA_HGGL_HTCJ_ADDSELF)}">
        {{# if(d.inputUserId== ${session.staff.staffId}){}}
            <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaEdit" title="编辑"><i class="larry-icon">&#xe646;</i></a>
            {{# if(d.inputUserId== ${session.staff.staffId} && !d.contractNo){}}
                <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaDel" title="删除"><i class="larry-icon">&#xe8d0;</i></a>
            {{# }}}
      {{# }}}
    </g:if>
    {{# if(d.inputUserId== ${session.staff.staffId}&& d.contractNo ){ }}
        <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="contractSub" title="合同提交"><i class="larry-icon">&#xe88f;</i></a>
    {{# }}}
    {{# }}}
    {{# if(d.ifSub){}}
        <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="contractFlow" title="查看流程"><i class="larry-icon">&#xea21;</i></a>
    {{# }}}
</div>
</script>
<script type="text/html" id="offerTool">
<div class="layui-btn-group">
    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaCheck" title="查看"><i class="larry-icon">&#xe896;</i></a>
    {{# if( d.offerState == '未签订' ) { }}
        {{# if(d.inputUserId== ${session.staff.staffId}){}}
            <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaEdit" title="编辑"><i class="larry-icon">&#xe646;</i></a>
        {{# } }}
        <g:if test="${session.staff.funcCode.contains(FuncConstants.EIA_HGGL_HTCJ_ADDSELF)}">
        {{# if ( d.ifSub == 0 ) { }}
            {{# if(d.inputUserId== ${session.staff.staffId}){}}
                <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaDel" title="删除"><i class="larry-icon">&#xe8d0;</i></a>
                <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="offerSub" title="报价提交"><i class="larry-icon">&#xe88f;</i></a>
                <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaGenerate" title="生成合同"><i class="larry-icon">&#xe920;</i></a>
            {{# } }}
        {{# } else if ( d.ifSub == 1 ) { }}
            <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="offerFlow" title="查看流程"><i class="larry-icon">&#xea21;</i></a>
            {{# if(d.inputUserId== ${session.staff.staffId} ) { }}
                 {{# if(d.isOfferDel) { }}
                    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaDel" title="删除"><i class="larry-icon">&#xe8d0;</i></a>
                {{# } }}
            <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaGenerate" title="生成合同"><i class="larry-icon">&#xe920;</i></a>
            {{# } }}
        {{# } }}
        </g:if>
    {{# } else { }}
        {{# if ( d.ifSub == 1 ) { }}
            <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="offerFlow" title="查看流程"><i class="larry-icon">&#xea21;</i></a>
        {{# } }}
    {{# } }}
    <g:if test="${session.staff.funcCode.contains(FuncConstants.EIA_HGGL_HTCJ_ADDSELF)}">
        {{# if(d.inputUserId== ${session.staff.staffId}){}}
            <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="offerExport" title="报价模板导出"><i class="larry-icon">&#xe8cd;</i></a>
        {{# }}}
    </g:if>

</div>
</script>
<script type="text/html" id="indexTable">
<input type="hidden" id="tableNameId" name="tableNameId" value="">
<input type="hidden" id="tableName" name="tableName" value="">
<span>{{d.LAY_TABLE_INDEX + 1}}</span>
</script>

<script type="text/html" id="tableTopTmp">
<!--查询及添加框-->
    <g:if test="${session.staff.funcCode.contains(FuncConstants.EIA_HGGL_HTCX_QUERY)}">
        <div class="layui-inline">
            <div class="layui-input-inline">
                <input type="text" name="contractName" value="" id="contractName" placeholder="合同名称、合同编号、录入部门、录入人" class="layui-input larry-search-input w300">
            </div>
        </div>
    </g:if>
    <div class="layui-btn-group top-group">
        <g:if test="${session.staff.funcCode.contains(FuncConstants.EIA_HGGL_HTCX_QUERY)}">
            <a class="layui-btn search_btn pl12" lay-event="contractSelect"><i class="larry-icon">&#xe939;</i> 查询</a>
            <a class="layui-btn search_btn pl12" lay-event="highSelect" state="0"><i class="larry-icon">&#xe939;</i> 高级查询</a>
        </g:if>
        <g:if test="${session.staff.funcCode.contains(FuncConstants.EIA_HGGL_HTCJ_ADDSELF)}">
            <a class="layui-btn layui-bg-pale pl12 contractAddBtn" lay-event="contractAdd" data-type="contractAdd"><i class="larry-icon">&#xe987;</i> 新增</a>
        </g:if>
    </div>
    <!--筛选栏-->
    <div class="filter-box mt15" state="0">
        <div class="filter-form">
            <div class="layui-row mt15">
                <div class="layui-col-xs6">
                    <div class="layui-form-item">
                        <label class="layui-form-label"><span class="col-f00"></span>甲方名称</label>
                        <div class="layui-input-block">
                            <input type="text" id="clientName" name="clientName" class="layui-input" value="">
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label"><span class="col-f00"></span>合同金额</label>
                        <div class="layui-input-block">
                            <div class="range-box">
                                <div class="layui-inline rb-input">
                                    <input type="text" id="conStartMoney" name="conStartMoney" class="layui-input" value="" placeholder="金额下限">
                                </div>
                                <span class="rb-text">—</span>
                                <div class="layui-inline rb-input">
                                    <input type="text" class="layui-input" id="conEndMoney" name="conEndMoney" placeholder="金额上限">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="layui-col-xs6">
                    <div class="layui-form-item">
                        <label class="layui-form-label"><span class="col-f00"></span>合同类型</label>
                        <div class="layui-input-block">
                            <input type="text" id="contractType" name="contractType" class="layui-input" readonly value="">
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label"><span class="col-f00"></span>签订时间</label>
                        <div class="layui-input-block">
                            <div class="range-box">
                                <div class="layui-inline rb-input">
                                    <input type="text" class="layui-input" id="startDate" name="startDate" placeholder="起始时间">
                                </div>
                                <span class="rb-text">—</span>
                                <div class="layui-inline rb-input">
                                    <input type="text" class="layui-input" id="endDate" name="endDate" placeholder="结束时间">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <ul class="filter-ul">
            <li class="filter-li" filterName="contractTrust">
                <div class="inner-box lf">受托方</div>
                <div class="inner-box rg filter-inner contractTrustFilter">
                    <span class="filter-item"  lay-event="filterItem">
                        <div class="fi-box">
                            <span>联合泰泽</span>
                            <i class="larry-icon">&#xe830;</i>
                        </div>
                    </span>
                    <span class="filter-item" lay-event="filterItem">
                        <div class="fi-box">
                            <span>联合赤道</span>
                            <i class="larry-icon">&#xe830;</i>
                        </div>
                    </span>
                </div>
            </li>
            <li class="filter-li" filterName="ifArc">
                <div class="inner-box lf">是否归档</div>
                <div class="inner-box rg filter-inner ifArcFilter">
                    <span class="filter-item" lay-event="filterItem">
                        <div class="fi-box">
                            <span>是</span>
                            <i class="larry-icon">&#xe830;</i>
                        </div>
                    </span>
                    <span class="filter-item" lay-event="filterItem">
                        <div class="fi-box">
                            <span>否</span>
                            <i class="larry-icon">&#xe830;</i>
                        </div>
                    </span>
                </div>
            </li>
            <li class="filter-li" filterName="ifAgency">
                <div class="inner-box lf">是否有中介合同</div>
                <div class="inner-box rg filter-inner ifAgencyFilter">
                    <span class="filter-item" lay-event="filterItem">
                        <div class="fi-box">
                            <span>是</span>
                            <i class="larry-icon">&#xe830;</i>
                        </div>
                    </span>
                    <span class="filter-item" lay-event="filterItem">
                        <div class="fi-box">
                            <span>否</span>
                            <i class="larry-icon">&#xe830;</i>
                        </div>
                    </span>
                </div>
            </li>
        </ul>
        <div class="filter-btn">
            <a class="layui-btn search_btn pl12" lay-event="contractSelect" data-type="contractSelect"><i class="larry-icon">&#xe939;</i> 查询</a>
            <a class="layui-btn layui-btn-primary pl12" lay-event="clearQuery" data-type="clearQuery"><i class="larry-icon">&#xe617;</i> 重置</a>
        </div>
    </div>
</script>

<script type="text/html" id="tableTopTmp2">
<!--查询及添加框-->
<div class="table-top">
    <g:if test="${session.staff.funcCode.contains(FuncConstants.EIA_HGGL_HTCX_QUERY)}">
        <div class="layui-inline">
            <div class="layui-input-inline">
                <input type="text" name="offerName" value="" id="offerName" placeholder="合同名称、报价编号、录入部门、录入人" class="layui-input larry-search-input w300">
            </div>
        </div>
    </g:if>
    <div class="layui-btn-group top-group">
        <a class="layui-btn search_btn pl12 " lay-event="offerSelect"><i class="larry-icon">&#xe939;</i> 查询</a>
        <g:if test="${session.staff.funcCode.contains(FuncConstants.EIA_HGGL_HTCJ_ADDSELF)}">
            <a class="layui-btn layui-bg-pale pl12 offerAddBtn" lay-event="offerAdd"><i class="larry-icon">&#xe987;</i> 新增</a>
        </g:if>
    </div>
</div>
</script>
</body>
</html>