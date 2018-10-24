<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>新增联系人</title>
    <meta name="layout" content="main"/>
    <asset:javascript src="/eiaClient/eiaClientContactsCreate.js"/>
</head>

<body class="pb68">
<div class="layui-fluid larry-wrapper pt0">
    <div class="layer-title">
        <fieldset class="layui-elem-field layui-field-title site-title">
            <legend id="title">
                <a name="methodRender" class="pageTitle">新增联系人</a>
            </legend>
        </fieldset>
    </div>

    <form class="layui-form">

        <blockquote class="layui-elem-quote larry-btn fixed-footer">
            <div class="layui-inline pl12">
                <div class="layui-btn-group top-group">
                    <button class="layui-btn pl12" lay-submit="" lay-filter="save"><i class="larry-icon">&#xe9d1;</i> 保存</button>
                    <button type="reset" class="layui-btn layui-btn-primary resetBtn pl12"><i class="larry-icon">&#xe69a;</i> 重置</button>
                </div>
            </div>
        </blockquote>

        <div class="layui-row mt15">
            <div class="layui-col-xs6">
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">* </span>联系人名称</label>
                    <div class="layui-input-block">
                        <input type="text" id="contactName" name="contactName" class="layui-input" lay-verify="required" value="">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">*</span>职务</label>
                    <div class="layui-input-block">
                        <input type="text" id="contactPosition" name="contactPosition" class="layui-input" lay-verify="required">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>传真</label>
                    <div class="layui-input-block">
                        <input type="number" id="clientFax" name="clientFax" class="layui-input" value="">
                    </div>
                </div>

            </div>
            <div class="layui-col-xs6">
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">* </span>联系电话</label>
                    <div class="layui-input-block">
                        <input type="text" id="contactPhone" name="contactPhone" class="layui-input" lay-verify="required" value="">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">*</span>电子邮件</label>
                    <div class="layui-input-block">
                        <input type="text" id="contactEmail" name="contactEmail" class="layui-input" lay-verify="required">
                    </div>
                </div>
            </div>
        </div>

    </form>

</div>

<!--联系人id-->
<script type="text/html" id="mlTool">
    <div class="layui-btn-group">
        <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaEdit" title="编辑"><i class="larry-icon">&#xe646;</i></a>
        <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaDel" title="删除"><i class="larry-icon">&#xe8d0;</i></a>
    </div>
</script>
<script type="text/html" id="indexTable">
<span>{{d.LAY_TABLE_INDEX + 1}}</span>
</script>
</body>
</html>