<%@ page contentType="text/html;charset=UTF-8" import="com.lheia.eia.common.FuncConstants" %>
<%@ page import="com.lheia.eia.common.HttpUrlConstants" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>任务看板</title>
    <meta name="layout" content="main"/>
    <asset:stylesheet src="/eiaBoard/eiaWorkPlatForm.css"/>
    <asset:javascript src="/eiaBoard/eiaWorkPlatForm.js"/>
</head>

<body>
<div class="layui-fluid larry-wrapper pt15">
    <div class="larry-container">
        <div class="layui-row layui-col-space15">
            <div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
                <div class="layui-row layui-col-space15">
                    %{--快捷方式--}%
                    <div class="layui-col-lg7 layui-col-md7 layui-col-sm12 layui-col-xs12">
                        <section class="layui-card">
                            <div class="layui-card-header"><i class="larry-icon">&#xe877;</i>快捷导航</div>
                            <div class="layui-card-body larryms-shortcut">
                                <ul class="layui-row layui-col-space10 shortcut-group shortcut-lf">
                                    <li class="layui-col-xs2 ">
                                        <a larry-tab="iframe" data-name="EIA_RWGL_RWCJ_ADD"  data-url='${HttpUrlConstants.EIA_FORWARD_JUMP}eiaTask/eiaTaskIndex?ifAdd=1' data-id="larry-41" data-group="0" data-icon="larry-10109" data-font="larry-icon">
                                            <i class="larry-icon">&#xe87f;</i>
                                            <cite>任务创建</cite>
                                        </a>
                                    </li>
                                    <li class="layui-col-xs2">
                                        <a larry-tab="iframe" data-name="EIA_HGGL_BJCJ_ADD"  data-url='${HttpUrlConstants.EIA_FORWARD_JUMP}eiaContract/eiaConOfferIndex?tabName=offerTab&ifOfferAdd=1' data-id="larry-44" data-group="0" data-icon="larry-tiezishoucang" data-font="larry-icon">
                                            <i class="larry-icon">&#xe855;</i>
                                            <cite>报价创建</cite>
                                        </a>
                                    </li>
                                    <li class="layui-col-xs2">
                                        <a larry-tab="iframe" data-name="EIA_HGGL_HTCJ_ADD"  data-url='${HttpUrlConstants.EIA_FORWARD_JUMP}eiaContract/eiaConOfferIndex?ifContractAdd=1' data-id="larry-44" data-group="0" data-icon="larry-dingdanliebiao" data-font="larry-icon">
                                            <i class="larry-icon">&#xe920;</i>
                                            <cite>合同创建</cite>
                                        </a>
                                    </li>
                                    <li class="layui-col-xs2">
                                        <a larry-tab="iframe" data-name="EIA_XMGL_XMCS_ADDSELF"  data-url='${HttpUrlConstants.EIA_FORWARD_JUMP}eiaProjectExplore/eiaProjectExploreIndex?ifAdd=1' data-id="larry-85" data-group="0" data-icon="larry-xiangmuguanli" data-font="larry-icon">
                                            <i class="larry-icon">&#xe981;</i>
                                            <cite>项目初审创建</cite>
                                        </a>
                                    </li>
                                    <li class="layui-col-xs2">
                                        <a larry-tab="iframe" data-name="EIA_XMGL_JCFA_ADD"  data-url='${HttpUrlConstants.EIA_FORWARD_JUMP}eiaLabOffer/eiaLabOfferIndex?ifAdd=1' data-id="larry-51" data-group="0" data-icon="larry-jichushuju" data-font="larry-icon">
                                            <i class="larry-icon">&#xe926;</i>
                                            <cite>监测方案创建</cite>
                                        </a>
                                    </li>
                                    <li class="layui-col-xs2">
                                        <a larry-tab="iframe" data-name="EIA_HGGL_ZZBG_ADD"  data-url='${HttpUrlConstants.EIA_FORWARD_JUMP}eiaCert/eiaCertIndex?ifAdd=1' data-id="larry-48" data-group="0" data-icon="larry-renwu1" data-font="larry-icon">
                                            <i class="larry-icon">&#xea0b;</i>
                                            <cite>资质报告创建</cite>
                                        </a>
                                    </li>
                                    <li class="layui-col-xs2">
                                        <a larry-tab="iframe" data-name="EIA_RWGL_RWBG_ADD"  data-url='${HttpUrlConstants.EIA_FORWARD_JUMP}eiaTaskLog/eiaTaskLogIndex?ifAdd=1' data-id="larry-42" data-group="0" data-icon="larry-dingdanguanli" data-font="larry-icon">
                                            <i class="larry-icon">&#xe8a8;</i>
                                            <cite>任务变更</cite>
                                        </a>
                                    </li>
                                    <li class="layui-col-xs2">
                                        <a larry-tab="iframe" data-name="EIA_HGGL_HTBG_ADD"  data-url='${HttpUrlConstants.EIA_FORWARD_JUMP}eiaContractLog/eiaContractLogIndex?ifAdd=1' data-id="larry-45" data-group="0" data-icon="larry-chengyidingzhiicon-" data-font="larry-icon">
                                            <i class="larry-icon">&#xe97e;</i>
                                            <cite>合同变更</cite>
                                        </a>
                                    </li>
                                    <li class="layui-col-xs2">
                                        <a larry-tab="iframe" data-name="EIA_XMGL_XMBG_ADD"  data-url='${HttpUrlConstants.EIA_FORWARD_JUMP}eiaProjectLog/eiaProjectLogIndex?ifAdd=1' data-id="larry-53" data-group="0" data-icon="larry-rizhi" data-font="larry-icon">
                                            <i class="larry-icon">&#xe866;</i>
                                            <cite>项目变更</cite>
                                        </a>
                                    </li>
                                    <li class="layui-col-xs2">
                                        <a larry-tab="iframe" data-name="EIA_YWCX_KHCX"  data-url='${HttpUrlConstants.EIA_FORWARD_JUMP}eiaClient/eiaClientIndex' data-id="larry-56" data-group="0" data-icon="larry-quanxian" data-font="larry-icon">
                                            <i class="larry-icon">&#xe7e5;</i>
                                            <cite>客户查询</cite>
                                        </a>
                                    </li>
                                    <li class="layui-col-xs2">
                                        <a larry-tab="iframe" data-name="EIA_TJFX_HTTJFX"  data-url='${HttpUrlConstants.EIA_FORWARD_JUMP}eiaAnalysis/eiaContractAnalysis' data-id="larry-81" data-group="0" data-icon="larry-fcstubiao19" data-font="larry-icon">
                                            <i class="larry-icon">&#xe711;</i>
                                            <cite>合同统计分析</cite>
                                        </a>
                                    </li>
                                    <li class="layui-col-xs2">
                                        <a larry-tab="iframe" data-name="EIA_TJFX_XMTJFX"  data-url='${HttpUrlConstants.EIA_FORWARD_JUMP}eiaAnalysis/eiaProjectAnalysis' data-id="larry-82" data-group="0" data-icon="larry-shujufenxi" data-font="larry-icon">
                                            <i class="larry-icon">&#xe643;</i>
                                            <cite>项目统计分析</cite>
                                        </a>
                                    </li>
                                </ul>
                            </div>
                        </section>
                    </div>
                    <div class="layui-col-lg5 layui-col-md5 layui-col-sm12 layui-col-xs12">
                        <section class="layui-card">
                            <div class="layui-card-header"><i class="larry-icon">&#xe8ac;</i>待办事项</div>
                            <div class="layui-card-body larryms-shortcut">
                                <ul class="layui-row layui-col-space10 shortcut-group">
                                    <li class="layui-col-xs3">
                                        <a larry-tab="iframe" ifjump="true" data-url='${HttpUrlConstants.EIA_FORWARD_JUMP}eiaBoard/eiaProjectExpBacklog' data-id="larry-32" data-group="0" data-icon="larry-74wodedingdan" data-font="larry-icon">
                                            <i class="larry-icon">&#xe869;<em><span id="eiaProjectExploreNums"></span></em></i>
                                            <cite class="un-nums">项目初审</cite>
                                        </a>
                                    </li>
                                    <li class="layui-col-xs3">
                                        <a larry-tab="iframe" ifjump="true" data-url='${HttpUrlConstants.EIA_FORWARD_JUMP}eiaBoard/eiaConOfferBacklog' data-id="larry-32" data-group="0" data-icon="larry-wendang3" data-font="larry-icon">
                                            <i class="larry-icon">&#xe8d3;<em><span id="eiaContractNums"></span></em></i>
                                            <cite class="un-nums">合同报价</cite>
                                        </a>
                                    </li>
                                    <li class="layui-col-xs3">
                                        <a larry-tab="iframe" ifjump="true"  data-url='${HttpUrlConstants.EIA_FORWARD_JUMP}eiaBoard/eiaLabOfferBacklog' data-id="larry-32" data-group="0" data-icon="larry-tubiaozhexiantu" data-font="larry-icon">
                                            <i class="larry-icon">&#xe63a;<em><span id="eiaLabOfferNums"></span></em></i>
                                            <cite class="un-nums">监测方案</cite>
                                        </a>
                                    </li>
                                    <li class="layui-col-xs3">
                                        <a larry-tab="iframe" ifjump="true"  data-url='${HttpUrlConstants.EIA_FORWARD_JUMP}eiaBoard/eiaProjectBacklog' data-id="larry-32" data-group="0" data-icon="larry-qizhi" data-font="larry-icon">
                                            <i class="larry-icon">&#xe886;<em><span id="eiaProjectNums"></span></em></i>
                                            <cite class="un-nums">项目审核</cite>
                                        </a>
                                    </li>
                                    <li class="layui-col-xs3">
                                        <a larry-tab="iframe" ifjump="true"  data-url='${HttpUrlConstants.EIA_FORWARD_JUMP}eiaBoard/eiaCertBacklog' data-id="larry-32" data-group="0" data-icon="larry-wendang1" data-font="larry-icon">
                                            <i class="larry-icon">&#xe8e0;<em><span id="eiaCertNums"></span></em></i>
                                            <cite class="un-nums">资质报告</cite>
                                        </a>
                                    </li>
                                    <li class="layui-col-xs3">
                                        <a larry-tab="iframe" ifjump="true"  data-url='${HttpUrlConstants.EIA_FORWARD_JUMP}eiaBoard/eiaStampBacklog' data-id="larry-32" data-group="0" data-icon="larry-msnui-reg" data-font="larry-icon">
                                            <i class="larry-icon">&#xea04;<em><span id="eiaStampNums"></span></em></i>
                                            <cite class="un-nums">印章申请</cite>
                                        </a>
                                    </li>
                                    <li class="layui-col-xs3">
                                        <a larry-tab="iframe" ifjump="true"  data-url='${HttpUrlConstants.EIA_FORWARD_JUMP}eiaBoard/eiaWorkFlowBusiDone' data-id="larry-32" data-group="0" data-icon="larry-gouSolid" data-font="larry-icon">
                                            <i class="larry-icon">&#xe9e1;<em><span id="workFlowComBusiNums"></span></em></i>
                                            <cite class="un-nums">过往事项</cite>
                                        </a>
                                    </li>
                                </ul>
                            </div>
                        </section>
                    </div>

                    %{--两个表格--}%
                    <div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
                        <section class="layui-card">
                            <div class="layui-card-header"><i class="larry-icon">&#xe93e;</i>项目查询</div>
                            <div class="layui-card-body">
                                <table id="eiaProjectList" lay-filter="eiaProjectList"></table>
                                <!--高级查询栏状态：0：关闭；1：打开-->
                                <input type="hidden" id="proAdvancedState" name="advancedState" value="0">
                                <input type="hidden" id="proQueryData" name="queryData" value="{}">
                            </div>
                        </section>
                    </div>
                    <div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
                        <section class="layui-card">
                            <div class="layui-card-header"><i class="larry-icon">&#xea04;</i>合同查询</div>
                            <div class="layui-card-body">
                                <table id="eiaContractList" lay-filter="eiaContractList"></table>
                                <input type="hidden" id="conAdvancedState" name="conAdvancedState" value="0">
                                <input type="hidden" id="conQueryData" name="conQueryData" value="{}">
                            </div>
                        </section>
                    </div>

                    <input type="hidden" id="eiaProjectId" value=""/>
                    <input type="hidden" id="eiaTaskId" value=""/>
                    <input type="hidden" id="tableName" value="eiaProject"/>
                    <input type="hidden" id="tableNameId" value=""/>
                    <input type="hidden" id="eiaLabOfferId" value=""/>
                    <input type="hidden" id="proPlanId" value=""/>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/html" id="indexTable">
<span>{{d.LAY_TABLE_INDEX + 1}}</span>
</script>

<script type="text/html" id="mlTool">
<div class="layui-btn-group">
    {{# if(d.gisProjectId){}}
    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="projectGisShow" title="显示地理信息"><i class="larry-icon">&#xea6b;</i></a>
    {{# }}}
    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="projectDetail" title="查看"><i class="larry-icon">&#xe896;</i></a>
    {{# if(!d.ifSub){}}
    {{# if(d.inputUserId== ${session.staff.staffId}){}}
    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="projectEdit" title="编辑"><i class="larry-icon">&#xe646;</i></a>
    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="projectDel" title="删除"><i class="larry-icon">&#xe8d0;</i></a>
    {{# }}}
    {{# }}}
    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="projectFlow" title="查看流程"><i class="larry-icon">&#xea21;</i></a>
    <g:if test="${session.staff.funcCode.contains(FuncConstants.EIA_HGGL_ZZBG_CJZZ)}">
        <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="projectCoverDown" title="封皮下载"><i class="larry-icon">&#xe81b;</i></a>
        <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="certDownload" title="资质下载"><i class="larry-icon">&#xe8cd;</i></a>
    </g:if>
    <g:else>
        {{# if(d.inputUserId== ${session.staff.staffId}){}}
        <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="projectCoverDown" title="封皮下载"><i class="larry-icon">&#xe81b;</i></a>
        {{# }}}
    </g:else>
    <g:if test="${session.staff.staffCode=='s_admin'}">
        <a class="layui-btn layui-btn-warm layui-btn-sm" lay-event="changeWorkFlow" title="干预流程"><i class="larry-icon">&#xec8d;</i></a>
    </g:if>
</div>
</script>
<script type="text/html" id="proTableTopTmp">
<!--查询及添加框-->
<div class="layui-inline">
    <div class="layui-input-inline">
        <input type="text" name="projectName" value="" id="projectName" placeholder="项目名称、项目编号、项目负责人、录入部门、录入人" class="layui-input larry-search-input w300">
    </div>
</div>
<div class="layui-btn-group top-group">
    <a class="layui-btn search_btn pl12" lay-event="projectSelect"><i class="larry-icon">&#xe939;</i> 查询</a>
    <a class="layui-btn pl12" lay-event="advancedQuery"><i class="larry-icon">&#xe939;</i> 高级查询</a>
</div>
<!--筛选栏-->
<div class="filter-box mt15 filter-box-pro" state="0">
    <div class="filter-form">
        <div class="layui-row mt15">
            <div class="layui-col-xs6">
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>甲方名称</label>
                    <div class="layui-input-block">
                        <input type="text" id="eiaClientName" name="eiaClientName" class="layui-input" value="">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>建设地点</label>
                    <div class="layui-input-block">
                        <input type="text" id="buildArea" name="buildArea" class="layui-input" value="">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>项目金额</label>
                    <div class="layui-input-block">
                        <div class="range-box">
                            <div class="layui-inline rb-input">
                                <input type="text" id="projectStartMoney" name="projectStartMoney" class="layui-input" value="" placeholder="金额下限">
                            </div>
                            <span class="rb-text">—</span>
                            <div class="layui-inline rb-input">
                                <input type="text" class="layui-input" id="projectEndMoney" name="projectEndMoney" placeholder="金额上限">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="layui-col-xs6">
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>文件类型</label>
                    <div class="layui-input-block">
                        <input type="text" id="fileTypeChild" name="fileTypeChild" class="layui-input" readonly value="">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>审批文号</label>
                    <div class="layui-input-block">
                        <input type="text" id="seaReviewNo" name="seaReviewNo" class="layui-input" value="">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>审批时间</label>
                    <div class="layui-input-block">
                        <div class="range-box">
                            <div class="layui-inline rb-input">
                                <input type="text" class="layui-input" id="arcStartDate" name="arcStartDate" placeholder="起始时间">
                            </div>
                            <span class="rb-text">—</span>
                            <div class="layui-inline rb-input">
                                <input type="text" class="layui-input" id="arcEndDate" name="arcEndDate" placeholder="结束时间">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <ul class="filter-ul">
        <li class="filter-li" filterName="ifArc">
            <div class="inner-box lf">是否归档</div>
            <div class="inner-box rg filter-inner ifArcFilter">
                <div class="filter-item mr57" lay-event="filterItem">
                    <div class="fi-box">
                        <span>是</span>
                        <i class="larry-icon">&#xe830;</i>
                    </div>

                </div>
                <div class="filter-item" lay-event="filterItem">
                    <div class="fi-box">
                        <span>否</span>
                        <i class="larry-icon">&#xe830;</i>
                    </div>
                </div>
            </div>
        </li>
    </ul>
    <div class="filter-btn">
        <div class="layui-btn-group top-group">
            <a class="layui-btn search_btn pl12" lay-event="projectSelect" data-type="projectSelect"><i class="larry-icon">&#xe939;</i> 查询</a>
            <a class="layui-btn layui-btn-primary pl12" lay-event="clearQuery" data-type="clearQuery"><i class="larry-icon">&#xe617;</i> 重置</a>
        </div>
    </div>
</div>
</script>

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
<script type="text/html" id="conTableTopTmp">
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
        <a class="layui-btn pl12" lay-event="highSelect" state="0"><i class="larry-icon">&#xe939;</i> 高级查询</a>
    </g:if>
</div>
<!--筛选栏-->
<div class="filter-box mt15 filter-box-con" state="0">
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
        <div class="layui-btn-group top-group">
            <a class="layui-btn search_btn pl12" lay-event="contractSelect" data-type="contractSelect"><i class="larry-icon">&#xe939;</i> 查询</a>
            <a class="layui-btn layui-btn-primary pl12" lay-event="clearQuery" data-type="clearQuery"><i class="larry-icon">&#xe617;</i> 重置</a>
        </div>
    </div>
</div>
</script>
</body>
</html>