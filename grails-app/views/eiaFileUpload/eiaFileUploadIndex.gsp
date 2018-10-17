<%@ page import="com.lheia.eia.common.WorkFlowConstants" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title></title>
    <meta name="layout" content="main"/>
    <asset:javascript src="/eiaFileUpload/eiaFileUploadIndex.js"/>
</head>

<body>
<div class="layui-fluid larry-wrapper pt0">
    <div class="layer-title">
        <fieldset class="layui-elem-field layui-field-title site-title">
            <legend id="title">
                <a name="methodRender" class="pageTitle">附件上传</a>
            </legend>
        </fieldset>
    </div>

    <!--列表-->
    <div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
        <table id="eiaFileUploadList" lay-filter="eiaFileUploadList"></table>
    </div>
</div>
<input type="hidden" id="tableName" value=""/>
<input type="hidden" id="tableId" value=""/>
<script type="text/html" id="eiaFileUploadListBar">
<div class="layui-btn-group">
    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaFileUploadDetail" title="查看"><i class="larry-icon">&#xe939;</i></a>
    {{# if ( d.ifReadOnly ){ }}
    {{# if((d.inputUserId== ${session.staff.staffId} && ${ifModi})||("s_admin"== "${session.staff.staffCode}")){}}
    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaFileUploadDel" title="删除"><i class="larry-icon">&#xe8d0;</i></a>
    {{# } }}
    {{# } }}
    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaFileUploadDownload" title="下载"><i class="larry-icon">&#xe8cd;</i></a>
</div>
</script>

<script type="text/html" id="tableTopTmp">
    <div class="layui-btn-group pl12">
        <a class="layui-btn pl12" lay-event='eiaFileUploadAdd' data-type="eiaFileUploadAdd"><i class="larry-icon">&#xe876;</i> 新增</a>
        <g:if test="${eiaProjectPlanItem?.contains(WorkFlowConstants.NODE_CODE_XCKC)}">
            <a class="layui-btn pl12 eiaProject display-none" lay-event="explorePrint" data-type="explorePrint"><i class="larry-icon">&#xe89a;</i> 踏勘记录打印</a>
        </g:if>
        <g:if test="${eiaProjectPlanItem?.contains(WorkFlowConstants.NODE_CODE_YS)}">
            <a class="layui-btn pl12 eiaProject display-none" lay-event="firstInsPrint" data-type="firstInsPrint"><i class="larry-icon">&#xe89a;</i> 一审审查打印</a>
        </g:if>
        <g:if test="${eiaProjectPlanItem?.contains(WorkFlowConstants.NODE_CODE_ES)}">
            <a class="layui-btn pl12 eiaProject display-none" lay-event="SecondInsPrint" data-type="SecondInsPrint"><i class="larry-icon">&#xe89a;</i> 二审审查打印</a>
        </g:if>
        <g:if test="${eiaProjectPlanItem?.contains(WorkFlowConstants.NODE_CODE_SS)}">
            <a class="layui-btn pl12 eiaProject display-none" lay-event="ThirdInsPrint" data-type="ThirdInsPrint"><i class="larry-icon">&#xe89a;</i> 三审审查打印</a>
        </g:if>
        <a class="layui-btn pl12 eiaProject display-none" lay-event="proCardPrint" data-type="proCardPrint"><i class="larry-icon">&#xe89a;</i> 责任运行卡打印</a>
        <a class="layui-btn pl12 eiaProject display-none" lay-event="fileArcPrint" data-type="fileArcPrint"><i class="larry-icon">&#xe89a;</i> 归档资料打印</a>
    </div>
</script>

</body>
</html>