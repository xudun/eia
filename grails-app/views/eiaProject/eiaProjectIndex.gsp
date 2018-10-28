<%@ page import="com.lheia.eia.common.FuncConstants" contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>项目列表</title>
    <meta name="layout" content="main"/>
    <asset:javascript src="/eiaProject/eiaProjectIndex.js"/>
    <asset:stylesheet src="/eiaProject/eiaProjectIndex.css"/>
</head>

<body>
<div class="layui-fluid larry-wrapper pt0">
    <!--查询及添加框-->
    <input type="hidden" id="eiaProjectId" value=""/>
    <input type="hidden" id="eiaTaskId" value=""/>
    <input type="hidden" id="tableName" value="eiaProject"/>
    <input type="hidden" id="tableNameId" value=""/>
    <input type="hidden" id="eiaLabOfferId" value=""/>
    <input type="hidden" id="proPlanId" value=""/>

    <!--列表-->
    <div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
        <table id="eiaProjectList" lay-filter="eiaProjectList"></table>
        <!--高级查询栏状态：0：关闭；1：打开-->
        <input type="hidden" id="advancedState" name="advancedState" value="0">
        <input type="hidden" id="queryData" name="queryData" value="{}">
    </div>
</div>
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
<script type="text/html" id="indexTable">
<span>{{d.LAY_TABLE_INDEX + 1}}</span>
</script>
<script type="text/html" id="tableTopTmp">
<!--查询及添加框-->
<div class="layui-inline">
    <div class="layui-input-inline">
        <input type="text" name="projectName" value="" id="projectName" placeholder="项目名称、项目编号、项目负责人、录入部门、录入人" class="layui-input larry-search-input w300">
    </div>
</div>
<div class="layui-btn-group top-group">
    <a class="layui-btn search_btn pl12" lay-event="projectSelect"><i class="larry-icon">&#xe939;</i> 查询</a>
    <a class="layui-btn pl12" lay-event="advancedQuery"><i class="larry-icon">&#xe939;</i> 高级查询</a>
    <a class="layui-btn layui-bg-pale pl12" lay-event="projectAdd"><i class="larry-icon">&#xe987;</i> 新增</a>
</div>
<!--筛选栏-->
<div class="filter-box mt15" state="0">
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
</body>
</html>