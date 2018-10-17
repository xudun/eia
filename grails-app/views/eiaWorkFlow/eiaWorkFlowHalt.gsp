<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>流程终止</title>
    <meta name="layout" content="main"/>
    <style>
    .flow-confirm.layui-form .layui-form-label{width: 95px;}
    .flow-confirm .layui-input-block{margin-left: 125px;}
    </style>
</head>
<body>
<div class="layui-fluid larry-wrapper pt0 ">
    <div class="layer-title ">
        <fieldset class="layui-elem-field layui-field-title site-title">
            <legend>
                <a name="methodRender" class="pageTitle">流程中止</a>
            </legend>
        </fieldset>
    </div>
    <form class="layui-form flow-confirm">
        <blockquote class="layui-elem-quote larry-btn mb15">
            <span id="currNodesName"></span>
            <div class="layui-inline pl12">
                <div class="layui-btn-group top-group">
                    <button class="layui-btn"  lay-submit="" lay-filter="save">确定</button>
                </div>
            </div>
        </blockquote>
     %{--   <div class="layui-form-item">
            <label class="layui-form-label"><span class="col-f00">* </span>终止时间</label>
            <div class="layui-input-block">
                <input type="text" id="approvalDate" name="approvalDate" class="layui-input" lay-verify="required|date" placeholder="年-月-日" autocomplete="off" value="">
            </div>
        </div>--}%
        <div class="layui-form-item">
            <label class="layui-form-label"><span class="col-f00">* </span>终止原因</label>
            <div class="layui-input-block">
                <textarea name="opinion" id="opinion" placeholder="请输入审批意见" class="layui-textarea" lay-verify="required" value=""></textarea>
            </div>
        </div>


    </form>
</div>

<script type="text/html" id="indexTable">
<span>{{d.LAY_TABLE_INDEX + 1}}</span>
</script>
<asset:javascript src="/eiaWorkFlow/eiaWorkFlowProjectConfirm.js"/>
</body>
</html>