<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title></title>
    <meta name="layout" content="main"/>
    <asset:javascript src="/eiaContract/eiaOfferSealApply.js"/>
    <style type="text/css">
        table tr td{
            padding: 5px;
            border: 1px solid #383838;
            vertical-align: middle;
            line-height: 30px;
        }
    </style>
</head>
<body>
<div id="print" class="layui-fluid larry-wrapper pt15">
    <div class="layer-title">
        <fieldset class="layui-elem-field layui-field-title site-title">
            <legend id="title">
                <a name="methodRender" class="pageTitle">印章申请审批单</a>
            </legend>
        </fieldset>
    </div>
    <blockquote class="layui-elem-quote larry-btn mb15">
        <div class="layui-inline pl12">
            <div class="layui-btn-group top-group">
                <a class="layui-btn" data-type="print"><i class="larry-icon">&#xe850;</i> 打印</a>
            </div>
        </div>
    </blockquote>
</div>
<div class="margin0 w710">
    <h1 class="font20 margin0 mb10 a" style="text-align: center"></h1>
    <h1 class="font20 margin0 w200">报价印章申请审批单</h1>
    <table class="w100per font16 mb15 mt15">
        <tr>
            <td width="15%">申请部门</td>
            <td width="35%" id="inputDept"></td>
            <td width="15%">申请人</td>
            <td width="35%" id="inputUser"><img src=""></td>
        </tr>
        <tr>
            <td width="15%">申请时间</td>
            <td width="35%" id="applyDate"></td>
            <td width="15%"></td>
            <td width="35%"></td>
        </tr>
        <tr>
            <td width="15%">用印名称</td>
            <td width="85%" colspan="3" id="contractTrust"></td>
        </tr>
        <tr>
            <td width="15%">报价编号</td>
            <td width="85%" colspan="3" id="offerNo"></td>
        </tr>
        <tr>
            <td width="15%">报价名称</td>
            <td width="85%" colspan="3" id="offerName"></td>
        </tr>
        <tr>
            <td width="15%">合同类型</td>
            <td width="85%" colspan="3" id="contractType"></td>
        </tr>
        <tr>
            <td width="15%">费用明细</td>
            <td width="85%" colspan="3">
                <p>报价额：<span id="offerMoney"></span><span class="mr15">（万元）</span></p>
                <p>
                    编制费:<span class="mr15" id="reportFees"></span><span class="mr15">（万元）</span>
                    环境监测费用:<span id="enviroMonitoringFee"></span><span class="mr15">（万元）</span>
                    地下水专题:<span id="groundWater"></span><span class="mr15">（万元）</span>
                    专家费用:<span id="expertFee"></span><span class="mr15">（万元）</span>
                    其他费用:<span id="otherFee"></span><span class="mr15">（万元）</span>
                </p>
            </td>
        </tr>
        <tr>
            <td width="15%">经办人</td>
            <td width="35%" id="userName"><img src=""></td>
            <td width="15%">部门经理</td>
            <td width="35%" id="deptManager"><img src=""></td>
        </tr>
        <tr>
            <td width="15%">总经理</td>
            <td width="85%" colspan="3" id="topManager"><img src=""></td>
        </tr>
    </table>
    <input type="hidden" id="eiaProjectId" name="eiaProjectId" value="">
    <input type="hidden" id="proPlanId" name="proPlanId" value="">
    <input type="hidden" id="eiaEnvProjectId" name="eiaEnvProjectId" value="">
</div>
</body>
</html>