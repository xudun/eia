<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="layout" content="main"/>
    <title>审批公示详情</title>
    <asset:stylesheet src="/eiaPlatForm/eiaSensArea.css"/>
    <asset:javascript src="/eiaPlatForm/eiaSensAreaDetail.js"/>
</head>
<body>
<div class="layui-fluid larry-wrapper pt0">
    <div class="layer-title">
        <fieldset class="layui-elem-field layui-field-title site-title">
            <legend id="title">
                <a name="methodRender" class="pageTitle">环境敏感区详情</a>
            </legend>
        </fieldset>
    </div>
    <form class="layui-form sens-area-form">
        <div class="layui-row mt15">
            <div class="layui-col-xs6">
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>名称</label>
                    <div class="layui-input-block check-block" id="projectName"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>建设地点中心坐标 E</label>
                    <div class="layui-input-block check-block" id="geographicEast"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>建设地点线性坐标起点 E</label>
                    <div class="layui-input-block check-block" id="geographicStartEast"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>建设地点线性坐标终点 E</label>
                    <div class="layui-input-block check-block" id="geographicEndEast"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>所属区域</label>
                    <div class="layui-input-block check-block" id="regionName"></div>
                </div>
            </div>
            <div class="layui-col-xs6">
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>数据类别</label>
                    <div class="layui-input-block check-block" id="dataType"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>建设地点中心坐标 N</label>
                    <div class="layui-input-block check-block" id="geographicNorth"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>建设地点线性坐标起点 N</label>
                    <div class="layui-input-block check-block" id="geographicStartNorth"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>建设地点线性坐标终点 N</label>
                    <div class="layui-input-block check-block" id="geographicEndNorth"></div>
                </div>
            </div>

            <div class="layui-col-xs12">
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>基本情况简介</label>
                    <div class="layui-input-block check-block" id="projectMemo"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>附件</label>
                    <div class="layui-input-block check-block" id="files">
                    </div>
                </div>
            </div>
        </div>
    </form>

</div>
</body>
</html>