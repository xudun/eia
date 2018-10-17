<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="layout" content="main"/>
    <title>审批公示详情</title>
    <asset:stylesheet src="/eiaPlatForm/eiaPubProject.css"/>
    <asset:javascript src="/eiaPlatForm/eiaPubProjectDetail.js"/>
</head>
<body>
<div class="layui-fluid larry-wrapper pt0">
    <div class="layer-title">
        <fieldset class="layui-elem-field layui-field-title site-title">
            <legend id="title">
                <a name="methodRender" class="pageTitle">审批公示详情</a>
            </legend>
        </fieldset>
    </div>

    <form class="layui-form pub-pro-form">
        <div class="layui-row mt15">
            <div class="layui-col-xs6">
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>项目名称</label>
                    <div class="layui-input-block check-block" id="projectName"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>审批部门</label>
                    <div class="layui-input-block check-block" id="approveDept"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>建设性质</label>
                    <div class="layui-input-block check-block" id="natureConstructio"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>行业类型及代码</label>
                    <div class="layui-input-block check-block" id="industryType"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>产品或功能</label>
                    <div class="layui-input-block check-block" id="productFunction"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>环评单位</label>
                    <div class="layui-input-block check-block" id="eiaUnit"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>建设地点</label>
                    <div class="layui-input-block check-block" id="projectLoc"></div>
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
                    <label class="layui-form-label"><span class="col-f00"></span>公示附件</label>
                    <div class="layui-input-block check-block">
                        <a id="spiderFileDownloadUrl" class="action-txt">点击后查看或下载公示附件</a>
                    </div>
                </div>
            </div>
            <div class="layui-col-xs6">
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>所属区域</label>
                    <div class="layui-input-block check-block" id="regionName"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>审批年度</label>
                    <div class="layui-input-block check-block" id="publictyYear"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>生产工艺</label>
                    <div class="layui-input-block check-block" id="productionEngineer"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>环境影响评价行业类别</label>
                    <div class="layui-input-block check-block" id="environmentaType"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>建设单位</label>
                    <div class="layui-input-block check-block" id="developmentOrg"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>公示类型</label>
                    <div class="layui-input-block check-block" id="dataType"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>公示日期</label>
                    <div class="layui-input-block check-block" id="pubDate"></div>
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
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>原文链接</label>
                    <div class="layui-input-block check-block">
                        <a id="spiderFileUrl" href="#" target="_blank" class="action-txt">点击后查看原文链接</a>
                    </div>
                </div>
            </div>
            <div class="layui-col-xs12">
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>项目基本情况说明</label>
                    <div class="layui-input-block check-block" id="projectMemo"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>附件</label>
                    <div class="layui-input-block check-block" id="files"></div>
                </div>
            </div>
        </div>
    </form>

</div>
</body>
</html>