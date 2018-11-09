<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>项目信息</title>
    <meta name="layout" content="main"/>
    <asset:javascript src="/eiaProject/eiaProjectInputLib.js"/>
    <asset:javascript src="/eiaProject/eiaProjectMoneyFillInputLib.js"/>
    <asset:javascript src="/eiaProject/eiaProjectDetail.js"/>
    <asset:stylesheet src="/eiaProject/eiaProjectCreate.css"/>
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
            <div class="layui-col-xs6">
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>项目名称</label>
                    <div class="layui-input-block check-block" id="projectName"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>建设地点</label>
                    <div class="layui-input-block check-block" id="buildArea"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>建设地点中心坐标E</label>
                    <div class="layui-input-block check-block" id="coordEast"></div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>建设地点线性坐标起点E</label>
                    <div class="layui-input-block check-block" id="coordStartEast"></div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>建设地点线性坐标终点E</label>
                    <div class="layui-input-block check-block" id="coordEndEast"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>项目负责人</label>
                    <div class="layui-input-block check-block" id="dutyUser"></div>
                </div>
            </div>
            <div class="layui-col-xs6">
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>文件类型</label>
                    <div class="layui-input-block check-block" id="fileType"></div>
                    <input type="hidden" id="fileTypehidden" name="fileTypehidden" lay-verify="required" value="">

                </div>
                <div class="layui-form-item proMonBlock">
                    <label class="layui-form-label"><span class="col-f00"></span>项目金额</label>
                    <div class="layui-input-block check-block" id="projectMoney"><span class="cost-num"></span><span>&nbsp;&nbsp;(万元)</span></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">建设地点中心坐标N</label>
                    <div class="layui-input-block check-block" id="coordNorth"></div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label">建设地点线性坐标起点N</label>
                    <div class="layui-input-block check-block" id="coordStartNorth"></div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label">建设地点线性坐标终点N</label>
                    <div class="layui-input-block check-block" id="coordEndNorth"></div>
                </div>

            </div>
        </div>

        <div class="layui-row mt15 dynMoneyInputs">
            <div class="layui-col-xs6"></div>
            <div class="layui-col-xs6"></div>
        </div>

        <div class="layui-row mt15 dynInputs">
            <div class="layui-col-xs6"></div>
            <div class="layui-col-xs6"></div>
        </div>
    </form>
    <blockquote class="layui-elem-quote larry-btn">
        任务基本信息
    </blockquote>
    <div class="layui-row mt15">
        <div class="layui-col-xs6">
            <div class="layui-form-item">
                <label class="layui-form-label">任务单号</label>
                <div class="layui-input-block check-block" id="taskNo"></div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">业务类型</label>
                <div class="layui-input-block check-block" id="busiType"></div>
            </div>
        </div>
        <div class="layui-col-xs6">
            <div class="layui-form-item">
                <label class="layui-form-label">任务名称</label>
                <div class="layui-input-block check-block" id="taskName"></div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">负责部门</label>
                <div class="layui-input-block check-block" id="taskLeaderDept"></div>
            </div>
        </div>
    </div>
    <!--任务列表-->
    <div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
        <table id="eiaTaskList" lay-filter="eiaTaskList"></table>
    </div>
    <!--任务列表-->
    <div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
        <table id="eiaContractList" lay-filter="eiaContractList"></table>
    </div>
</div>

<script type="text/html" id="indexTable">
<span>{{d.LAY_TABLE_INDEX + 1}}</span>
</script>
<script type="text/html" id="contractTool">
<div class="layui-btn-group">
    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaCheck" title="查看"><i class="larry-icon">&#xe939;</i></a>
</div>
</script>
<script type="text/html" id="tableTaskTmp">
分配人员信息
</script>
<script type="text/html" id="tableContractTmp">
合同信息
</script>
</body>
</html>