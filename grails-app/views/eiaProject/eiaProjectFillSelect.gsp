<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>项目金额</title>
    %{--    <link rel="stylesheet" href="js/layuiFrame/common/frame/layui/css/layui.css">
        <link rel="stylesheet" href="js/layuiFrame/common/css/gobal.css">
        <link rel="stylesheet" href="stylesheets/common.css">
        <link rel="stylesheet" href="pagecss/styles.css">--}%
    <meta name="layout" content="main"/>
    <asset:javascript src="/eiaProject/eiaProjectMoneyFillInputLib.js"/>
    <asset:javascript src="/eiaProject/eiaProjectFillSelect.js"/>
    <asset:stylesheet src="eiaProject/eiaProjectCreate.css"/>
</head>
<body>
<div class="layui-fluid larry-wrapper pt0">
    <div class="layer-title">
        <fieldset class="layui-elem-field layui-field-title site-title">
            <legend id="title">
                <a name="methodRender" class="pageTitle">填写项目金额</a>
            </legend>
        </fieldset>
    </div>

    <form class="layui-form proMoney">
        <blockquote class="layui-elem-quote larry-btn">
            项目金额
            <div class="layui-inline pl12">
                <div class="layui-btn-group top-group">
                    <button class="layui-btn" lay-submit="" lay-filter="save"><i class="larry-icon">&#xe9d1;</i> 保存</button>
                    <button type="reset" class="layui-btn layui-btn-primary resetBtn"><i class="larry-icon">&#xe69a;</i> 重置</button>
                </div>
            </div>
        </blockquote>

        <div class="layui-row mt15 dynMoneyInputs">
            <div class="layui-col-xs6"></div>
            <div class="layui-col-xs6"></div>
        </div>


    </form>
</div>
%{--<script src="js/jquery/jquery-2.1.3.js"></script>
<script src="js/layuiFrame/common/frame/layui/layui.js"></script>
<script src="pagejs/eiaProjectMoneyFill.js"></script>--}%
</body>
</html>