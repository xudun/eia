<%@ page contentType="text/html;charset=UTF-8" import="com.lheia.eia.common.FuncConstants" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>合同统计分析</title>
    <meta name="layout" content="main"/>
    <asset:stylesheet src="/eiaBoard/eiaTaskBoard.css"/>
    <asset:javascript src="/eiaAnalysis/eiaContractAnalysis.js"/>
</head>

<body>
<div class="layui-fluid larry-wrapper pt0">
    <div class="layui-tab layui-tab-brief" lay-filter="contractTab">
        <ul class="layui-tab-title">
            <g:if test="${session.staff.funcCode.contains(FuncConstants.EIA_TJFX_HTTJFX_BMYWTJ)}">
                <li class="conFin layui-this" tab-name="conFin">部门合同统计</li>
                <input type="hidden" id="ifShowConFin" value="1">
            </g:if>
            <g:if test="${session.staff.funcCode.contains(FuncConstants.EIA_TJFX_HTTJFX_GRYWTJ)}">
                <li class="conFin layui-this" tab-name="conFin">个人合同统计</li>
                <input type="hidden" id="ifShowConFin" value="1">
            </g:if>
            <g:if test="${session.staff.funcCode.contains(FuncConstants.EIA_TJFX_HTTJFX_YWLXTJ)}">
                <li class="proTypeFin" tab-name="proTypeFin">合同类型统计</li>
                <input type="hidden" id="ifShowProTypeFin" value="1">
            </g:if>
        </ul>

        <div class="layui-tab-content pt0" style="height: 100px;">
            <g:if test="${session.staff.funcCode.contains(FuncConstants.EIA_TJFX_HTTJFX_BMYWTJ)}">
                <!--部门业务统计-->
                <div class="showConFinList layui-tab-item layui-show">
                    <!--列表-->
                    <div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
                        <table id="conFinList" lay-filter="conFinList" openState="0"></table>
                        <input type="hidden" id="dasStartTime" value="">
                        <input type="hidden" id="dasEndTime" value="">
                    </div>
                </div>
            </g:if>
            <g:if test="${session.staff.funcCode.contains(FuncConstants.EIA_TJFX_HTTJFX_YWLXTJ)}">
                <!--业务类型统计-->
                <div class="showProTypeFinList layui-tab-item">
                    <!--列表-->
                    <div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
                        <table id="proTypeFinList" lay-filter="proTypeFinList" openState="0"></table>
                        <input type="hidden" id="proStartTime" value="">
                        <input type="hidden" id="proEndTime" value="">
                        <input type="hidden" id="proOrgName" value="">
                    </div>
                </div>
            </g:if>
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
{{#  if(!d.ifStaff) { }}
<a class="">{{d.orgName}}</a>
{{#  } else { }}
<a class="">{{d.staffName}}</a>
{{#  } }}
{{#  } }}
</script>

<script type="text/html" id="contractTp">
{{#  if(!d.ifStaff){ }}
<a class="">{{d.contractCount}}</a>
{{#  } else { }}
<a class="depLink">{{d.contractCount}}</a>
{{#  } }}
</script>

<script type="text/html" id="conFinTp">
{{#  if(!d.ifStaff){ }}
<a class="">{{d.finCount}}</a>
{{#  } else { }}
<a class="depLink">{{d.finCount}}</a>
{{#  } }}
</script>


<script type="text/html" id="conUnFinTp">
{{#  if(!d.ifStaff){ }}
<a class="">{{d.unfinCount}}</a>
{{#  } else { }}
<a class="depLink">{{d.unfinCount}}</a>
{{#  } }}
</script>

<script type="text/html" id="proTypeTp">
{{#  if(d.ifParent){ }}
{{#  if(!d.LAY_TABLE_INDEX){ }}
<a class="depLink"><i class="larry-icon tdIcon">&#xe8e2;</i>&nbsp;&nbsp;{{d.codeDesc}}</a>
{{#  } else { }}
<a class="depLink"><i class="larry-icon tdIcon">&#xe849;</i>&nbsp;&nbsp;{{d.codeDesc}}</a>
{{#  } }}
{{#  } else { }}
<a class="">{{d.codeDesc}}</a>
{{#  } }}
</script>
<!--部门业务统计查询框-->
<script type="text/html" id="confinaTmp">
<div class="layui-inline">
    <div class="layui-inline">
        <input type="text" class="layui-input" id="deptStartDate" name="deptStartDate" placeholder="签订起始时间">
    </div>

    <div class="layui-inline">
        <input type="text" class="layui-input" id="deptEndDate" name="deptEndDate" placeholder="签订结束时间">
    </div>
</div>

<div class="layui-btn-group top-group">
    <a class="layui-btn search_btn pl12" lay-event="getSelect"><i class="larry-icon">&#xe939;</i> 查询</a>
</div>
</script>
<!--业务类型统计查询框-->
<script type="text/html" id="proTypeFinTmp">
<div class="layui-inline">
    <div class="layui-inline">
        <input type="text" class="layui-input" id="busiStartDate" name="busiStartDate" placeholder="签订起始时间">
    </div>

    <div class="layui-inline">
        <input type="text" class="layui-input" id="busiEndDate" name="busiEndDate" placeholder="签订结束时间">
    </div>

    <div class="layui-inline">
        <input type="text" name="inputDept" value="" id="inputDept" placeholder="部门名称" class="layui-input larry-search-input">
    </div>
</div>

<div class="layui-btn-group top-group">
    <a class="layui-btn search_btn pl12" lay-event="getSelect"><i class="larry-icon">&#xe939;</i> 查询</a>
</div>
</script>
</body>
</html>