<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title></title>
    <meta name="layout" content="main"/>
    <asset:javascript src="/eiaStamp/eiaStampPrintPage.js"/>
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
    <h1 class="font20 margin0 w200">印章申请审批单</h1>
    <table class="w100per font16 mb15 mt15">
        <tr>
            <td width="15%">申请部门</td>
            <td width="35%" id="inputDept"></td>
            <td width="15%">申请人</td>
            <td width="35%" id="inputUser"><img src=""></td>
        </tr>
        <tr>
            <td width="15%">申请时间</td>
            <td width="35%" id="appTime"></td>
            <td width="15%">用章次数</td>
            <td width="35%" id="stampNum"></td>
        </tr>
        <tr>
            <td width="15%">公司名称</td>
            <td width="85%" colspan="3" id="stampCompany"></td>
        </tr>
        <tr>
            <td width="15%">印章类型</td>
            <td width="85%" colspan="3" id="stampType"></td>
        </tr>

        <tr>
            <td width="15%">申请事由</td>
            <td width="85%" id="appReason" colspan="3" height="70px">

            </td>
        </tr>

        <tr>
            <td width="15%">是否外带</td>
            <td width="35%" id="ifTakeOut"><img src=""></td>
            <td width="15%">监督人</td>
            <td width="35%" id="supervisor"><img src=""></td>

        </tr>

        <tr>
            <td width="15%">部门经理</td>
            <td width="35%" id="deptManager"><img src=""></td>
            <td width="15%">总经理</td>
            <td width="35%" id="topManager"><img src=""></td>
        </tr>


        <tr>
            <td width="15%">行政部</td>
            <td width="35%" id="xzManager"><img src=""></td>
            <td width="15%"></td>
            <td width="35%" id="nuff"><img src=""></td>
        </tr>
    </table>
    <input type="hidden" id="eiaProjectId" name="eiaProjectId" value="">
    <input type="hidden" id="proPlanId" name="proPlanId" value="">
    <input type="hidden" id="eiaEnvProjectId" name="eiaEnvProjectId" value="">
</div>
</body>
</html>