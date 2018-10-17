<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.lheia.eia.common.FuncConstants; com.lheia.eia.common.GeneConstants" %>
<html>
<head>
    <title>员工考核列表</title>

    <asset:javascript src="/eiaHrEval/eiaHrEvalIndex.js"/>
    <meta name="layout" content="main"/>
</head>

<body>
<div class="layui-fluid larry-wrapper">

    <!--员工考核列表-->
    <div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
        <table id="eiaHrEvalList" lay-filter="eiaHrEvalList"></table>
    </div>
</div>
<input type="hidden" id="eiaHrEvalId" name="eiaHrEvalId" value="">
<input type="hidden" id="jobRatingType" name="jobRatingType" value="">
<input type="hidden" id="assessmentMonth" name="assessmentMonth" value="">
<input type="hidden" id="orgId" name="orgId" value="">
<!--员工考核列表操作列-->
<script type="text/html" id="indexTable">
<span>{{d.LAY_TABLE_INDEX + 1}}</span>
</script>
<script type="text/html" id="eiaHrEvalListBar">
<div class="layui-btn-group">
{{# if(d.jobRatingType=='助理评分表'){}}
%{--部门总经理--}%
<g:if test="${roleNameShow== GeneConstants.JOB_RATING_ROLE}">
    {{# if(d.isShowGrade){}}
        {{# if(d.orgId==${orgId}){}}
                <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaHrEvalDMToAssInput" title="经理给助理打分"><i class="larry-icon">&#xe72b;</i></a>
                <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaHrEvalEmpSubmit" title="提交"><i class="larry-icon">&#xe88f;</i></a>
        {{# }}}
    {{# }}}
    {{# if(!d.isShowGrade){}}
         {{# if(d.isEvalAll){}}
            <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaHrEvalDMToAssDetail" title="员工打分详情"><i class="larry-icon">&#xe939;</i></a>
         {{# }}}
        {{# if(!d.isEvalAll){}}
            <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaHrEvalEmpToAssDetail" title="员工打分详情"><i class="larry-icon">&#xe939;</i></a>
        {{# }}}
    {{# }}}
    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaHrEvalScoreAcc" title="助理得分情况"><i class="larry-icon">&#xea04;</i></a>

</g:if>
%{--管理员--}%
<g:if test="${roleNameShow== GeneConstants.JOB_RATING_ADMIN}">
    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaHrEvalScoreAcc" title="助理得分详情"><i class="larry-icon">&#xea04;</i></a>
</g:if>
<g:if test="${roleNameShow== GeneConstants.JOB_RATING_GMANAGER_ROLE}">
    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaHrEvalScoreEmp" title="员工得分详情"><i class="larry-icon">&#xea04;</i></a>
</g:if>
%{--员工、助理--}%
<g:if test="${roleNameShow== GeneConstants.JOB_RATING_PM_ROLE}">
    {{# if(d.isShowGrade){}}
        {{# if(d.orgId==${orgId}){}}
                <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaHrEvalEmpToAssInput" title="助理打分"><i class="larry-icon">&#xe72b;</i></a>
                <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaHrEvalEmpSubmit" title="提交"><i class="larry-icon">&#xe88f;</i></a>
        {{# }}}
    {{# }}}
    {{# if(!d.isShowGrade){}}
        <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaHrEvalEmpToAssDetail" title="员工打分详情"><i class="larry-icon">&#xe939;</i></a>
    {{# }}}
    {{# if(d.isShowScore){}}
        <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaHrEvalAssPrint" title="员工最终得分"><i class="larry-icon">&#xe82d;</i></a>
    {{# }}}
    <g:if test="${session.staff.funcCode.contains(FuncConstants.EIA_XZGL_YDKH_VIEWALL)}">
        <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaHrEvalScoreEmp" title="员工得分详情"><i class="larry-icon">&#xea04;</i></a>
    </g:if>
</g:if>
{{# }}}
{{# if(d.jobRatingType=='员工评分表'){}}
%{--部门总经理--}%
<g:if test="${roleNameShow== GeneConstants.JOB_RATING_ROLE}">
    {{# if(d.isShowGrade){}}
        {{# if(d.orgId==${orgId}){}}
            <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaHrEvalDMToEmpInput" title="经理给员工打分"><i class="larry-icon">&#xe72b;</i></a>
            <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaHrEvalEmpSubmit" title="提交"><i class="larry-icon">&#xe88f;</i></a>
        {{# }}}
    {{# }}}
    {{# if(!d.isShowGrade){}}
           {{# if(d.isEvalAll){}}
                <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaHrEvalDMToEmpDetail" title="员工打分详情"><i class="larry-icon">&#xe939;</i></a>
           {{# }}}
           {{# if(!d.isEvalAll){}}
                <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaHrEvalEmpToEmpDetail" title="员工打分详情"><i class="larry-icon">&#xe939;</i></a>
           {{# }}}
    {{# }}}
    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaHrEvalScoreEmp" title="员工得分详情"><i class="larry-icon">&#xea04;</i></a>

</g:if>
%{--管理员--}%
<g:if test="${roleNameShow== GeneConstants.JOB_RATING_ADMIN}">
    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaHrEvalScoreEmp" title="员工得分详情"><i class="larry-icon">&#xea04;</i></a>
</g:if>
<g:if test="${roleNameShow== GeneConstants.JOB_RATING_GMANAGER_ROLE}">
    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaHrEvalScoreEmp" title="员工得分详情"><i class="larry-icon">&#xea04;</i></a>
</g:if>
%{--员工、助理--}%
<g:if test="${roleNameShow== GeneConstants.JOB_RATING_PM_ROLE}">
    {{# if(d.isShowGrade){}}
        {{# if(d.orgId==${orgId}){}}
                <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaHrEvalEmpToEmpInput" title="员工打分"><i class="larry-icon">&#xe72b;</i></a>
                <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaHrEvalEmpSubmit" title="提交"><i class="larry-icon">&#xe88f;</i></a>
         {{# }}}
     {{# }}}
    {{# if(!d.isShowGrade){}}
        <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaHrEvalEmpToEmpDetail" title="员工打分详情"><i class="larry-icon">&#xe939;</i></a>
    {{# }}}
    {{# if(d.isShowScore){}}
        <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaHrEvalEmpPrint" title="员工最终得分"><i class="larry-icon">&#xe82d;</i></a>
    {{# }}}
    <g:if test="${session.staff.funcCode.contains(FuncConstants.EIA_XZGL_YDKH_VIEWALL)}">
        <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaHrEvalScoreEmp" title="员工得分详情"><i class="larry-icon">&#xea04;</i></a>
    </g:if>
</g:if>
{{# }}}
</div>
</script>
<script type="text/html" id="tableTopTmp">
    <div class="layui-inline pl12">
        <g:if test="${session.staff.funcCode.contains(FuncConstants.EIA_XZGL_YDKH_KHXQ)}">
            <a class="layui-btn search_btn pl12" lay-event="assDetailsAll" data-type="assDetailsAll"><i class="larry-icon"></i> 机构考核人员详情</a>
        </g:if>
    </div>
</script>
</body>
</html>