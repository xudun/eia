<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title></title>
    <meta name="layout" content="main"/>
    <asset:javascript src="/eiaHrEvalScoreDetail/eiaHrEvalComment.js"/>
</head>

<body class="pb68">
<div class="layui-fluid larry-wrapper">

    <blockquote class="layui-elem-quote larry-btn fixed-footer">
        <div class="layui-inline pl12">
            <a class="layui-btn pl12" data-type="save"><i class="larry-icon">&#xe9d1;</i> 保存</a>
        </div>
    </blockquote>

    <form id="commentForm" class="layui-form mt15">
        <textarea placeholder="请输入评语" class="layui-textarea" name="leaderComments" id="leaderComments"></textarea>
    </form>
</div>
</body>
</html>