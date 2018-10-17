<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title></title>
    <meta name="layout" content="main"/>
    <asset:javascript src="/eiaProject/eiaProjectExplorePrint.js"/>
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
                <a name="methodRender" class="pageTitle">现场踏勘记录</a>
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
    <h1 class="font20 margin0 w150">现场踏勘记录</h1>
    <table class="w100per font16 mb15 mt15">
        <tr>
            <td width="15%">项目名称</td>
            <td width="35%" id="projectName"></td>
            <td width="15%">项目编号</td>
            <td width="35%" id="projectNo"></td>
        </tr>
        <tr>
            <td width="15%">项目类型</td>
            <td width="35%" id="fileTypeChild"></td>
            <td width="15%">踏勘时间</td>
            <td width="35%" id="exploreDate"></td>
        </tr>
        <tr>
            <td width="15%">项目主持人</td>
            <td width="35%" id="taskAssignUser"></td>
            <td width="15%">踏勘人员</td>
            <td width="35%" id="inputUser"></td>
        </tr>
        <tr>
            <td width="15%">项目选址</td>
            <td width="85%" colspan="3" id="buildArea"></td>
        </tr>
        <tr>
            <td width="15%">是否涉及水源、风景名胜等敏感区</td>
            <td width="35%" id="ifSensArea"></td>
            <td width="15%">是否符合城市(园区)总规划</td>
            <td width="35%" id="isCityPlan"></td>
        </tr>
        <tr>
            <td width="15%" rowspan="2">踏勘记录</td>
            <td width="85%" colspan="3" id="exploreRecord"></td>
        </tr>
        <tr>
            <td width="85%" colspan="3">
                <div>现场照片</div>
                <div id="uploadImg"></div>
            </td>
        </tr>
        <tr>
            <td width="15%">负责人签字</td>
            <td width="85%" colspan="3" id="sign"></td>
        </tr>
    </table>
    <input type="hidden" id="eiaProjectId" name="eiaProjectId" value="">
    <input type="hidden" id="proPlanId" name="proPlanId" value="">
    <input type="hidden" id="eiaEnvProjectId" name="eiaEnvProjectId" value="">
</div>
</body>
</html>