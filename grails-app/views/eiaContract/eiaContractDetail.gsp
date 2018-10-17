
<%@ page contentType="text/html;charset=UTF-8" %>
<html xmlns="http://www.w3.org/1999/html">
<head>
    <title>合同详情</title>
    <meta name="layout" content="main"/>
    <asset:javascript src="/eiaContract/eiaMoneyFillInputLib.js"/>
    <asset:javascript src="/eiaContract/eiaContractDetail.js"/>
</head>

<body>
<div class="layui-fluid larry-wrapper pt0">
    <div class="layer-title">
        <fieldset class="layui-elem-field layui-field-title site-title">
            <legend id="title">
                <a name="methodRender" class="pageTitle">合同详情</a>
            </legend>
        </fieldset>
    </div>

    <form class="layui-form offerInfo">
        <div class="layui-row mt15">
            <div class="layui-col-xs6">
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>合同编号</label>
                    <div class="layui-input-block check-block" id="contractNo"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>合同类型</label>
                    <div class="layui-input-block check-block" id="contractType"></div>
                </div>
                <div class="layui-form-item has-tag">
                    <label class="layui-form-label"><span class="col-f00"></span>合同金额</label>
                    <div class="layui-input-block check-block" id="contractMoney"><span class="cost-num"></span><span>&nbsp;&nbsp;(万元)</span></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>合同受托方</label>
                    <div class="layui-input-block check-block" id="contractTrust"></div>
                </div>
                <div class="layui-form-item has-tag">
                    <label class="layui-form-label"><span class="col-f00"></span>客户名称</label>
                    <div class="layui-input-block check-block" id="eiaClientName"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>联系人</label>
                    <div class="layui-input-block check-block" id="contactName"></div>
                </div>
                <div class="layui-form-item has-tag">
                    <label class="layui-form-label"><span class="col-f00"></span>甲方名称</label>
                    <div class="layui-input-block check-block" id="ownerClientName"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>甲方联系人</label>
                    <div class="layui-input-block check-block" id="ownerContactName"></div>
                </div>
            </div>
            <div class="layui-col-xs6">
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>合同名称</label>
                    <div class="layui-input-block check-block" id="contractName"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>合同用途</label>
                    <div class="layui-input-block check-block" id="contractUse"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>签订时间</label>
                    <div class="layui-input-block check-block" id="contractDate"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>所在省份</label>
                    <div class="layui-input-block check-block" id="province"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>客户地址</label>
                    <div class="layui-input-block check-block" id="clientAddress"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>联系人电话</label>
                    <div class="layui-input-block check-block" id="contactPhone"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>甲方地址</label>
                    <div class="layui-input-block check-block" id="ownerClientAddress"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>甲方联系人电话</label>
                    <div class="layui-input-block check-block" id="ownerContactPhone"></div>
                </div>
            </div>
        </div>

        <div class="layui-row mt15 dynMoneyInputs">
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
    <!--项目列表-->
    <div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
        <table id="eiaProjectList" lay-filter="eiaProjectList"></table>
    </div>
</div>

<!--合同id-->
<input type="hidden" id="contractId" name="contractId" value="${eiaContract?.id}">
<input type="hidden" id="taskId" name="taskId" value="${eiaContract?.taskId}">
<script type="text/html" id="indexTable">
<span>{{d.LAY_TABLE_INDEX + 1}}</span>
</script>
<script type="text/html" id="projectTool">
<input type="hidden" id="eiaProjectId" value=""/>
<input type="hidden" id="tableNameId" value=""/>
<div class="layui-btn-group">
    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaCheck" title="查看"><i class="larry-icon">&#xe939;</i></a>
</div>
</script>

<script type="text/html" id="tableTaskTmp">
分配人员信息
</script>
<script type="text/html" id="tableTopTmp">
<div>
    项目信息
</div>
</script>
</body>
</html>