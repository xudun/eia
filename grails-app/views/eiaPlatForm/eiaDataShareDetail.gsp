<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="layout" content="main"/>
    <title>项目详情</title>
    <asset:stylesheet src="/eiaPlatForm/eiaDataShare.css"/>
    <asset:javascript src="/eiaPlatForm/eiaDataShareDetail.js"/>
</head>
<body>
<div class="layui-fluid larry-wrapper pt0">
    <div class="layer-title">
        <fieldset class="layui-elem-field layui-field-title site-title">
            <legend>
                <a name="methodRender" class="pageTitle">项目详情</a>
            </legend>
        </fieldset>
    </div>

    <form class="layui-form data-share-form">
        <input type="hidden" id="eiaDataShareId" name="eiaDataShareId">
        <div class="layui-row mt15">
            <div class="layui-col-xs6">
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>项目编号</label>
                    <div class="layui-input-block check-block" id="projectNo"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>项目名称</label>
                    <div class="layui-input-block check-block" id="projectName"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>项目金额（万元）</label>
                    <div class="layui-input-block check-block" id="projectMoney"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>建设地点</label>
                    <div class="layui-input-block check-block" id="buildArea"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>地理坐标</label>
                    <div class="layui-input-block check-block" id="coordEast"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>文件类型</label>
                    <div class="layui-input-block check-block" id="fileType"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>项目投资金额（万元）</label>
                    <div class="layui-input-block check-block" id="projectInvestMoney"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>建设性质</label>
                    <div class="layui-input-block check-block" id="natureConstructio"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>项目代码</label>
                    <div class="layui-input-block check-block" id="projectCode"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>行业类型及代码</label>
                    <div class="layui-input-block check-block" id="industryType"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>环境影响评价行业类别</label>
                    <div class="layui-input-block check-block" id="environmentaType"></div>
                </div>
            </div>
            <div class="layui-col-xs6">
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>生产工艺</label>
                    <div class="layui-input-block check-block" id="productionEngineer"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>产品或功能</label>
                    <div class="layui-input-block check-block" id="productFunction"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>是否符合产业（行业）政策</label>
                    <div class="layui-input-block check-block" id="ifIndPolicy"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>录入人</label>
                    <div class="layui-input-block check-block" id="inputUser"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>项目负责人</label>
                    <div class="layui-input-block check-block" id="projectLeader"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>联系人</label>
                    <div class="layui-input-block check-block" id="contactName"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>联系电话</label>
                    <div class="layui-input-block check-block" id="contactPhone"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>审批时间</label>
                    <div class="layui-input-block check-block" id="approveDate"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>审批文号</label>
                    <div class="layui-input-block check-block" id="approveNo"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>审批部门</label>
                    <div class="layui-input-block check-block" id="approveDept"></div>
                </div>
            </div>
            <div class="layui-col-xs12">
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>项目基本情况说明</label>
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