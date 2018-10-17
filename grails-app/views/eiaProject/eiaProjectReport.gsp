<%--
  Created by IntelliJ IDEA.
  User: ZhengHongYi
  Date: 2018/5/24
  Time: 10:46
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>审核意见</title>
    <meta name="layout" content="main"/>
<asset:stylesheet src="/eiaProject/eiaProjectReport.css"/>
</head>

<body>
<div class="layui-fluid larry-wrapper">
    <div class="layer-title">
        <fieldset class="layui-elem-field layui-field-title site-title">
            <legend id="title">
                <a name="methodRender" class="pageTitle">
    <g:if test="${reportType == 'YS'}">
        <h1 class="font20 margin0 w300">环境影响报告书（表）审查单</h1>
    </g:if>
    <g:elseif test="${reportType == 'ES'}">
        <h1 class="font20 margin0 w300">环境影响报告书（表）审核单</h1>
    </g:elseif>
    <g:elseif test="${reportType == 'SS'}">
        <h1 class="font20 margin0 w300">环境影响报告书（表）审定单</h1>
    </g:elseif>
    </legend>
</fieldset>
</div>
    <form class="layui-form">
        <div class="layui-row mt15">
            <div class="layui-col-xs6">
                <div class="layui-form-item">
                    <label class="layui-form-label">项目编号</label>
                    <div class="layui-input-block" id="contractNo">${project?.projectNo}</div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label">项目名称</label>

                    <div class="layui-input-block" id="contractDate">${project?.projectName}</div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label">客户名称</label>

                    <div class="layui-input-block" id="dutyUser">${eiaClient?.clientName}</div>
                </div>


            </div>

            <div class="layui-col-xs6">
                <div class="layui-form-item">
                    <label class="layui-form-label">项目负责人</label>

                    <div class="layui-input-block" id="projectName">${project?.dutyUser}</div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label">其他编制人员</label>

                    <div class="layui-input-block" id="inputUser">${project?.inputUser}</div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label">项目所在地</label>

                    <div class="layui-input-block" id="projectApproval2">${project?.buildArea}</div>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">
                <g:if test="${reportType == 'YS'}">
                    一审意见
                </g:if>
                <g:elseif test="${reportType == 'ES'}">
                    二审意见
                </g:elseif>
                <g:elseif test="${reportType == 'SS'}">
                    审定意见
                </g:elseif></label>

            <div class="layui-input-block" id="pOpinion">${raw(planItem?.opinion)}</div>
        </div>
        <div class="layui-col-xs6">
            <div class="layui-form-item">
                <label class="layui-form-label">日期</label>
                <div class="layui-input-block" id="actEndDate">${planItem?.actEndDate?.format("yyyy-MM-dd")}</div>
            </div>
        </div>
        <div class="layui-col-xs6">
            <div class="layui-form-item">
                <label class="layui-form-label">审核人</label>
                <div class="layui-input-block" id="pNodeUserName">${planItem?.nodeUserName}</div>
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">修改意见</label>
            <div class="layui-input-block" id="opinion">${raw(modiContent)}</div>
        </div>
        <div class="layui-col-xs6">
            <div class="layui-form-item">
                <label class="layui-form-label">日期</label>
                <div class="layui-input-block" id="dateCreated"> ${workFlowNode?.dateCreated?.format("yyyy-MM-dd")}</div>
            </div>
        </div>

        <div class="layui-col-xs6">
            <div class="layui-form-item">
                <label class="layui-form-label">录入人</label>
                <div class="layui-input-block" id="pInputUser">${project?.inputUser}</div>
            </div>
        </div>
    </form>

    </div>
    </body>
    </html>