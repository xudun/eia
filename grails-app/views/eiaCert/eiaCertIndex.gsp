<%@ page import="com.lheia.eia.common.FuncConstants" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="layout" content="main"/>
    <title></title>
    <asset:stylesheet src="/eiaCert/eiaCert.css"/>
    <asset:javascript src="/eiaCert/eiaCertIndex.js"/>
</head>

<body>
<div class="layui-fluid larry-wrapper pt0">

    <!--列表-->
    <div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
        <table id="eiaProjectList" lay-filter="eiaProjectList"></table>
    </div>
</div>
<input type="hidden" id="eiaCertId" name="eiaCertId" value="">
<input type="hidden" id="tableName" name="tableName" value="">
<input type="hidden" id="tableNameId" name="tableNameId" value="">
<script type="text/html" id="indexTable">
<span>{{d.LAY_TABLE_INDEX + 1}}</span>
</script>
<script type="text/html" id="eiaProjectListTool">
<div class="layui-btn-group">

    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaCheck" title="查看"><i class="larry-icon">&#xe896;</i></a>
    {{#  if(d.ifSub){ }}
        <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="certFlow" title="处理流程"><i
            class="larry-icon">&#xea21;</i></a>
    {{#  }else{ }}
    {{# if(d.inputUserId== ${session.staff.staffId}){}}
        <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="certSub" title="提交流程"><i
                class="larry-icon">&#xe88f;</i></a>
        <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaDel" title="删除"><i class="larry-icon">&#xe8d0;</i>
    {{# }}}

    </a>
    {{#  } }}
    <g:if test="${session.staff.funcCode.contains(FuncConstants.EIA_HGGL_ZZBG_ADDSELF)}">
        {{# if(d.ifEnd){ }}
        <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="repeatSub" title="重复提交申请"><i
                class="larry-icon">&#xea1a;</i></a>
        {{#  } }}
    </g:if>

</div>
</script>
<script type="text/html" id="ifApplyCertTp">
{{#  if(d.ifApplyCert){ }}
<span>是</span>
{{#  } else { }}
<span>否</span>
{{#  } }}
</script>

<script type="text/html" id="certType">
{{#  if(d.certType == 'CERT_TYPE_APPROVAL'){ }}
<span>报批</span>
{{#  } else if(d.certType == 'CERT_TYPE_REVIEW') { }}
<span>送审</span>
{{#  } }}
</script>

<script type="text/html" id="ifEnd">
{{#  if(d.ifEnd){ }}
<span>流程结束</span>
{{#  } else { }}
<span>进行中</span>
{{#  } }}
</script>

<script type="text/html" id="tableTopTmp">
<!--查询及添加框-->
<div class="table-top">
    <div class="layui-inline">
        <div class="layui-input-inline">
            <input type="text" name="projectName" value="" id="projectName" placeholder="项目名称、项目编号、申请部门、申请人" class="layui-input larry-search-input w300">
        </div>
    </div>

    <div class="layui-btn-group top-group">
        <a class="layui-btn search_btn pl12" lay-event="getSelect"><i class="larry-icon">&#xe939;</i> 查询</a>
        <g:if test="${session.staff.funcCode.contains(FuncConstants.EIA_HGGL_ZZBG_ADDSELF)}">
            <a class="layui-btn layui-bg-pale pl12 addBtn" lay-event="certAdd"><i class="larry-icon">&#xe987;</i> 新增</a>
        </g:if>

    </div>
</div>

</script>
</body>
</html>