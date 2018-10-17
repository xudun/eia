<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <asset:javascript src="/eiaHrEvalScoreDetail/eiaHrEvalEmpDetail.js"/>
</head>

<body>
<div class="layui-fluid larry-wrapper">
    <blockquote class="layui-elem-quote larry-btn fixed fixed-w">
        打分详细表
    </blockquote>
    <div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
        <table id="eiaHrEvalDetailList" lay-filter="eiaHrEvalDetailList"></table>
    </div>
    <input type="hidden"   id="staffId"  name="staffId" value="${staffId}" />
</div>
</body>
</html>