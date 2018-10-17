<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="layout" content="main"/>
    <title>规划公示详情</title>
    <asset:stylesheet src="/eiaPlatForm/eiaPlanShow.css"/>
    <asset:javascript src="/eiaPlatForm/eiaPlanShowDetail.js"/>
</head>
<body>
<div class="layui-fluid larry-wrapper pt0">
    <div class="layer-title">
        <fieldset class="layui-elem-field layui-field-title site-title">
            <legend>
                <a name="methodRender" class="pageTitle">规划公示详情</a>
            </legend>
        </fieldset>
    </div>
    <form class="layui-form plan-show-form">
        <div class="layui-row mt15">
            <div class="layui-col-xs6">
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>标题</label>
                    <div class="layui-input-block check-block" id="title"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>来源机关</label>
                    <div class="layui-input-block check-block" id="source"></div>
                </div>
                %{--<div class="layui-form-item">--}%
                    %{--<label class="layui-form-label"><span class="col-f00"></span>建设地点中心坐标 E</label>--}%
                    %{--<div class="layui-input-block check-block" id="geographicEast"></div>--}%
                %{--</div>--}%
                %{--<div class="layui-form-item">--}%
                    %{--<label class="layui-form-label"><span class="col-f00"></span>建设地点线性坐标起点 E</label>--}%
                    %{--<div class="layui-input-block check-block" id="geographicStartEast"></div>--}%
                %{--</div>--}%
                %{--<div class="layui-form-item">--}%
                    %{--<label class="layui-form-label"><span class="col-f00"></span>建设地点线性坐标终点 E</label>--}%
                    %{--<div class="layui-input-block check-block" id="geographicEndEast"></div>--}%
                %{--</div>--}%
            </div>
            <div class="layui-col-xs6">
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>公布日期</label>
                    <div class="layui-input-block check-block" id="pubDate"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>原文链接</label>
                    <div class="layui-input-block check-block">
                        <a id="spiderFileUrl" href="" target="_blank" class="action-txt">点击后查看原文链接</a>
                    </div>
                </div>
                %{--<div class="layui-form-item">--}%
                    %{--<label class="layui-form-label"><span class="col-f00"></span>建设地点中心坐标 N</label>--}%
                    %{--<div class="layui-input-block check-block" id="geographicNorth"></div>--}%
                %{--</div>--}%
                %{--<div class="layui-form-item">--}%
                    %{--<label class="layui-form-label"><span class="col-f00"></span>建设地点线性坐标起点 N</label>--}%
                    %{--<div class="layui-input-block check-block" id="geographicStartNorth"></div>--}%
                %{--</div>--}%
                %{--<div class="layui-form-item">--}%
                    %{--<label class="layui-form-label"><span class="col-f00"></span>建设地点线性坐标终点 N</label>--}%
                    %{--<div class="layui-input-block check-block" id="geographicEndNorth"></div>--}%
                %{--</div>--}%
            </div>
            <div class="layui-col-xs12">
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>公布内容</label>
                    <div class="layui-input-block check-block" id="content"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">* </span>规划图片</label>
                    <div class="layui-input-block check-block">
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