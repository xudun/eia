<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title></title>
    <meta name="layout" content="main"/>
    <asset:stylesheet src="/eiaLab/eiaLabOfferCreate.css"/>
    <asset:javascript src="/eiaLab/otherFeeTypeSelect.js"/>
</head>

<body>
<div class="layui-fluid larry-wrapper pt0">
    <div class="layer-title">
        <fieldset class="layui-elem-field layui-field-title site-title">
            <legend id="title">
                <a name="methodRender">其他费用列表</a>
            </legend>
        </fieldset>
    </div>

    <!--查询及添加框-->
    <table id="otherFeeTypeList" lay-filter="otherFeeTypeList"></table>

</div>
<script type="text/html" id="tableTopTmp">
<div class="table-top">
    确认费用
    <div class="layui-inline pl12">
        <div class="layui-btn-group top-group">
            <a class="layui-btn" lay-event="confirmFee"><i class="larry-icon">&#xe9d1;</i> 选择</a>
        </div>
    </div>
</div>
</script>
</body>
</html>