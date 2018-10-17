<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>项目信息</title>
    <meta name="layout" content="main"/>
    %{--<asset:javascript src="/eiaProject/eiaProjectInputLib.js"/>--}%
    %{--<asset:javascript src="/eiaProject/eiaProjectDetail.js"/>--}%
</head>

<body>
<div class="layui-fluid larry-wrapper pt0">
    <div class="layer-title">
        <fieldset class="layui-elem-field layui-field-title site-title">
            <legend id="title">
                <a name="methodRender" class="pageTitle">项目详情</a>
            </legend>
        </fieldset>
    </div>

    <form class="layui-form proCreate">
        <div class="layui-row mt15">
            <div class="layui-form-item">
                <label class="layui-form-label"><span class="col-f00"></span>项目编号</label>

                <div class="layui-input-block check-block" id="projectNo" text="">${projectNo}</div>
            </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>项目名称</label>

                    <div class="layui-input-block check-block" id="projectName" text="">${projectName}</div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>文件类型</label>

                    <div class="layui-input-block check-block" id="fileType" text="">${fileType}</div>
                </div>
            <div class="layui-form-item">
                <label class="layui-form-label"><span class="col-f00"></span>建设地点</label>

                <div class="layui-input-block check-block" id="buildArea" text="">${buildArea}</div>
            </div>
        </div>

        <div class="layui-row mt15 dynInputs">
            <div class="layui-col-xs6"></div>

            <div class="layui-col-xs6"></div>
        </div>
    </form>
</div>

</body>
</html>