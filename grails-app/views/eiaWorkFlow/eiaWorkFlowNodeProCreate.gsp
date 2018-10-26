<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>新增节点动作</title>
    <meta name="layout" content="main"/>
    <asset:stylesheet src="/eiaWorkFlow/eiaWorkFlow.css"/>
    <asset:javascript src="/eiaWorkFlow/eiaWorkFlowNodeProCreate.js"/>
</head>
<body class="pb68">
<div class="layui-fluid larry-wrapper pt0">
    <div class="layer-title">
        <fieldset class="layui-elem-field layui-field-title site-title">
            <legend id="title">
                <a name="methodRender" class="pageTitle">新增节点动作</a>
            </legend>
        </fieldset>
    </div>

    <form class="layui-form flow-node-form">

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
                    <label class="layui-form-label"><span class="col-f00">* </span>按钮名称</label>
                    <div class="layui-input-block">
                        <input type="text" id="processName" name="processName" class="layui-input" lay-verify="required" value="">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">* </span>按钮位置序号</label>
                    <div class="layui-input-block">
                        <input type="text" id="processNum" name="processNum" class="layui-input" lay-verify="required|intnumberZero" value="">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">按钮弹出页面URL</label>
                    <div class="layui-input-block">
                        <input type="text" id="processJumpUrl" name="processJumpUrl" class="layui-input" value="">
                    </div>
                </div>

                <div class="layui-form-item has-sq">
                    <label class="layui-form-label"><span class="col-f00">* </span>按钮颜色</label>
                    <div class="layui-input-block">
                        <input type="text" id="processColor" name="processColor" class="layui-input" lay-verify="required" value="">
                    </div>
                    <div class="sq-box">
                        <span class="layui-badge-rim"></span>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">* </span>按钮提交URL</label>
                    <div class="layui-input-block">
                        <input type="text" id="processUrl" name="processUrl" class="layui-input" lay-verify="required" value="">
                    </div>
                </div>
            </div>
            <div class="layui-col-xs6">
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">* </span>按钮别名</label>
                    <div class="layui-input-block">
                        <input type="text" id="processShowName" name="processShowName" class="layui-input" lay-verify="required">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">* </span>按钮编码</label>
                    <div class="layui-input-block">
                        <input type="text" id="processCode" name="processCode" class="layui-input" lay-verify="required">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">按钮弹出页面参数</label>
                    <div class="layui-input-block">
                        <input type="text" id="processJumpUrlParams" name="processJumpUrlParams" class="layui-input" value="">
                    </div>
                </div>
                <div class="layui-form-item has-sq">
                    <label class="layui-form-label"><span class="col-f00">* </span>按钮图标</label>
                    <div class="layui-input-block">
                        <input type="text" id="processIconName" name="processIconName" class="layui-input" lay-verify="required">
                    </div>
                    <div class="sq-box">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">* </span>按钮提交URL参数</label>
                    <div class="layui-input-block">
                        <input type="text" id="processUrlParams" name="processUrlParams" class="layui-input" lay-verify="required">
                    </div>
                </div>
            </div>
        </div>

        <!--按钮id-->
        <input type="hidden" id="eiaWorkFlowId" name="eiaWorkFlowId">
        <input type="hidden" id="eiaWorkFlowNodeId" name="eiaWorkFlowNodeId">
        <input type="hidden" id="eiaWorkFlowNodeProcessId" name="eiaWorkFlowNodeProcessId">
    </form>
</div>

</body>
</html>