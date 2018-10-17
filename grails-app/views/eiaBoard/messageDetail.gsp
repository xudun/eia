<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>消息详情</title>
    <asset:stylesheet src="/assets/stylesheets/eiaBoard/eiaTaskBoard.css"/>
    <asset:javascript src="/assets/javascripts/eiaBoard/messageDetail.js"/>
</head>
<body>
<div class="layui-fluid larry-wrapper pt0">
    <div class="layer-title">
        <fieldset class="layui-elem-field layui-field-title site-title">
            <legend id="title">
                <a name="methodRender" class="pageTitle">消息详情</a>
            </legend>
        </fieldset>
    </div>

    <form class="layui-form">
        <div class="layui-row mt15">
            <div class="layui-col-xs12">
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>发送人</label>
                    <div class="layui-input-block check-block" id="messageSource"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>接收人</label>
                    <div class="layui-input-block check-block" id="recipient"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>主题</label>
                    <div class="layui-input-block check-block" id="messageBody"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>内容</label>
                    <div class="layui-input-block check-block" id="messageContent"></div>
                </div>
            </div>
        </div>

    </form>
</div>
</body>
</html>