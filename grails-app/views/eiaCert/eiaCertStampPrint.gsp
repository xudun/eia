<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title></title>
    <style type="text/css">
        #stampPrint tr td{
            padding: 5px;
            border: 1px solid #383838;
            vertical-align: top;
            line-height: 30px;
        }
    </style>
</head>
<body>
<div id="print" class="layui-fluid larry-wrapper pt15">
    <div class="layer-title">
        <fieldset class="layui-elem-field layui-field-title site-title">
            <legend id="title">
                <a name="methodRender" class="pageTitle">印章使用申请表</a>
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
    <h1 class="font20 margin0 w150">印章使用申请表</h1>
    <table id="stampPrint" class="w100per font16 mb15 mt15">
        <tr>
            <td>用印名称：<span id="stampType"></span></td>
        </tr>
        <tr>
            <td>项目名称：<span id="projectName"></span></td>
        </tr>
        <tr>
            <td>文件类型：<span id="fileTypeChild"></span></td>
        </tr>
        <tr>
            <td>特殊情况说明：<span id="stampNote"></span></td>
        </tr>
        <tr>
            <td>
                <div>合规部意见：<span id="HGSH"></span></div>
                <div class="w33per float-left">合同号：<span id="contractNo"></span></div>
                <div class="w33per float-left">签订合同：<span id="ifSign"></span></div>
                责任运行卡：有
            </td>
        </tr>
        <tr>
            <td>
                <div>财务部意见：<span id="CWSH"></span></div>
                <div class="w33per float-left">合同额：<span id="contractMoney"></span></div>
                <div class="w33per float-left">监测费：<span id="enviroMonitoringFee"></span></div>
                <div class="w33per float-left">其它：<span id="otherFee"></span></div>
                <div class="w33per float-left">已到账：<span id="income"></span></div>
                到账比例：<span id="proportion"></span>
            </td>
        </tr>
        <tr>
            <td>部门负责人意见：<span id="BMJLSH"></span></td>
        </tr>
        <tr>
            <td>
                <div>总经理意见：<span id="ZJLSH"></span></div>
                <div id="currentDate" class="float-right mr15"></div>
            </td>
        </tr>
    </table>
</div>
<asset:javascript src="/eiaCert/eiaCertStampPrint.js"/>
</body>
</html>