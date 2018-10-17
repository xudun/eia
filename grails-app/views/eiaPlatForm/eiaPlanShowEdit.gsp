<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="layout" content="main"/>
    <title>修改规划公示</title>
    <asset:stylesheet src="/eiaPlatForm/eiaPlanShow.css"/>
    <asset:javascript src="/eiaPlatForm/eiaPlanShowEdit.js"/>
</head>
<body>
<div class="layui-fluid larry-wrapper pt0">
    <div class="layer-title">
        <fieldset class="layui-elem-field layui-field-title site-title">
            <legend>
                <a name="methodRender" class="pageTitle">修改规划公示</a>
            </legend>
        </fieldset>
    </div>

    <form class="layui-form plan-show-form">
        <input type="hidden" id="eiaPlanShowId" name="eiaPlanShowId">
        <blockquote class="layui-elem-quote larry-btn">
            基本信息
            <div class="layui-inline pl12">
                <div class="layui-btn-group top-group">
                    <button class="layui-btn" lay-submit="" lay-filter="save"><i class="larry-icon">&#xe9d1;</i> 保存</button>
                    <!--<button type="reset" class="layui-btn layui-btn-primary resetBtn"><i class="larry-icon">&#xe69a;</i> 重置</button>-->
                </div>
            </div>
        </blockquote>
        <div class="layui-row mt15">
            <div class="layui-col-xs6">
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">* </span>标题</label>
                    <div class="layui-input-block">
                        <input type="text" id="title" name="title" class="layui-input" lay-verify="required" value="">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">* </span>来源机关</label>
                    <div class="layui-input-block">
                        <input type="text" id="source" name="source" class="layui-input" lay-verify="required" value="">
                    </div>
                </div>
            </div>
            <div class="layui-col-xs6">
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">* </span>公布日期</label>
                    <div class="layui-input-block">
                        <input type="text" id="pubDate" name="pubDate" class="layui-input" lay-verify="required|date" placeholder="年-月-日" autocomplete="off" value="">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>原文链接</label>
                    <div class="layui-input-block show-block">
                        <a id="spiderFileUrl" href="" target="_blank" class="action-txt">点击后查看原文链接</a>
                    </div>
                </div>
            </div>
            <div class="layui-col-xs12">
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">* </span>公布内容</label>
                    <div class="layui-input-block">
                        <textarea id="content" name="content" placeholder="请输入内容" class="layui-textarea" lay-verify="required" value=""></textarea>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">* </span>规划图片</label>
                    <div class="layui-input-block">
                        <div class="img-box">
                            <img id="spiderFileImagesUrl" src="" alt="规划图片">
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </form>

</div>
</body>
</html>