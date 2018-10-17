<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>节点动作详情</title>
    <meta name="layout" content="main"/>
    <asset:stylesheet src="/eiaWorkFlow/eiaWorkFlow.css"/>
    <asset:javascript src="/eiaWorkFlow/eiaWorkFlowNodeProDetail.js"/>
</head>
<body>
<div class="layui-fluid larry-wrapper pt0">
    <div class="layer-title">
        <fieldset class="layui-elem-field layui-field-title site-title">
            <legend id="title">
                <a name="methodRender" class="pageTitle">节点动作详情</a>
            </legend>
        </fieldset>
    </div>

    <form class="layui-form flow-node-form">
        <blockquote class="layui-elem-quote larry-btn">
            节点动作基本信息
        </blockquote>
        <div class="layui-row mt15">
            <div class="layui-col-xs6">
                <div class="layui-form-item">
                    <label class="layui-form-label">按钮名称</label>
                    <div class="layui-input-block check-block" id="processName"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">按钮位置序号</label>
                    <div class="layui-input-block check-block" id="processNum"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">按钮弹出页面URL</label>
                    <div class="layui-input-block check-block" id="processJumpUrl"></div>
                </div>
                <div class="layui-form-item has-sq">
                    <label class="layui-form-label">按钮颜色</label>
                    <div class="layui-input-block check-block">
                        <span class="check-value" id="processColor"></span>
                        <span class="layui-badge-rim"></span>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">按钮提交URL</label>
                    <div class="layui-input-block check-block" id="processUrl"></div>
                </div>
            </div>
            <div class="layui-col-xs6">
                <div class="layui-form-item">
                    <label class="layui-form-label">按钮别名</label>
                    <div class="layui-input-block check-block" id="processShowName"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">按钮编码</label>
                    <div class="layui-input-block check-block" id="processCode"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">按钮弹出页面参数</label>
                    <div class="layui-input-block check-block" id="processJumpUrlParams"></div>
                </div>
                <div class="layui-form-item has-sq">
                    <label class="layui-form-label">按钮图标</label>
                    <div class="layui-input-block check-block">
                        <span class="check-value" id="processIconName"></span>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">按钮提交URL参数</label>
                    <div class="layui-input-block check-block" id="processUrlParams"></div>
                </div>

            </div>
        </div>

    </form>
</div>

<!--按钮id-->
<input type="hidden" id="eiaWorkFlowNodeProcessId" name="eiaWorkFlowNodeProcessId">

</body>
</html>