<%--
  Created by IntelliJ IDEA.
  User: ZhengHongYi
  Date: 2018/5/24
  Time: 10:46
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>项目运行责任卡</title>
    <meta name="layout" content="main"/>

    <asset:stylesheet src="/eiaProject/eiaProjectDutyCard.css"/>
</head>

<body>
<div class="layui-fluid larry-wrapper">
    <div class="layer-title">
        <fieldset class="layui-elem-field layui-field-title site-title">
            <legend id="title">
                <a name="methodRender" class="pageTitle">项目运行责任卡</a>
            </legend>
        </fieldset>
    </div>
    <form class="layui-form">
        <div class="layui-row mt15">
            <div class="layui-col-xs6">
                <div class="layui-form-item">
                    <label class="layui-form-label">项目编号</label>
                    <div class="layui-input-block" id="projectNo"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">合同签订时间</label>
                    <div class="layui-input-block" id="contractDate"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">项目负责人（负责人）</label>
                    <div class="layui-input-block" id="dutyUser"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">项目审核人（二审）</label>
                    <div class="layui-input-block" id="projectApproval2"></div>
                </div>
            </div>
            <div class="layui-col-xs6">
                <div class="layui-form-item">
                    <label class="layui-form-label">项目名称</label>
                    <div class="layui-input-block" id="projectName"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">预计完成时间</label>
                    <div class="layui-input-block" id="completeDate"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">项目组成员（录入人）</label>
                    <div class="layui-input-block" id="projectGroup"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">项目审定人（三审）</label>
                    <div class="layui-input-block" id="projectApproval3"></div>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">项目运行记录</label>
            <div class="layui-input-block" id="record"></div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">审核</label>
            <div class="layui-input-block" id="approval"></div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">审定</label>
            <div class="layui-input-block" id="review"></div>
        </div>

        <!--合同id-->
        <input type="hidden" id="eiaProjectId" name="eiaProjectId" value="${eiaProjectId}">
    </form>
    <asset:javascript src="/eiaProject/eiaProjectDutyCard.js"/>

</div>
</body>
</html>