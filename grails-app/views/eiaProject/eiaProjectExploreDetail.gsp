<%--
  Created by IntelliJ IDEA.
  User: XinXi-001
  Date: 2018/5/7
  Time: 9:58
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title></title>
    <meta name="layout" content="main"/>
    <asset:javascript src="/eiaProject/eiaProjectExploreDetail.js"/>
    <asset:stylesheet src="/eiaProject/eiaProjectExploreDetail.css"/>
</head>

<body>
<div class="layer-title">
    <fieldset class="layui-elem-field layui-field-title site-title">
        <legend id="title">
            <a name="methodRender" class="pageTitle">现场踏勘</a>
        </legend>
    </fieldset>
</div>
<div class="layui-fluid larry-wrapper pt0">
    <form class="layui-form proCreate">
        <blockquote class="layui-elem-quote larry-btn">
            现场踏勘信息
        </blockquote>
        <div class="layui-row mt15">
            <div class="layui-col-xs6">
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>项目名称</label>
                    <div class="layui-input-block check-block" id="projectName"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>项目类型</label>
                    <div class="layui-input-block check-block" id="fileTypeChild"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>项目主持人</label>
                    <div class="layui-input-block check-block" id="taskAssignUser"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>是否涉及水源、风景名胜等敏感区</label>
                    <div class="layui-input-block check-block" id="ifSensArea"></div>
                </div>
            </div>
            <div class="layui-col-xs6">
                <div class="layui-form-item proMonBlock">
                    <label class="layui-form-label"><span class="col-f00"></span>项目编号</label>
                    <div class="layui-input-block check-block" id="projectNo"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>踏勘人员</label>
                    <div class="layui-input-block check-block" id="inputUser"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>踏勘时间</label>
                    <div class="layui-input-block check-block" id="exploreDate"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>是否符合城市(园区)总规划</label>
                    <div class="layui-input-block check-block" id="isCityPlan"></div>
                </div>

            </div>

            <div class="layui-col-xs12">
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>踏勘记录</label>
                    <div class="layui-input-block check-block" id="exploreRecord"></div>
                </div>
            </div>
        </div>

        <blockquote class="layui-elem-quote">
            现场图片
        </blockquote>
        <div class="layui-row mt15">
            <div class="layui-col-xs12">
                <div class="layui-form-item">
                    <label class="layui-form-label">现场图</label>
                    <div class="layui-input-block">
                        <blockquote class="layui-elem-quote layui-quote-nm" style="margin-top: 10px;">
                            <div class="layui-upload-list" id="uploaded">
                            </div>
                        </blockquote>
                    </div>
                </div>
            </div>
        </div>

        <input type="hidden" id="eiaProjectId" name="eiaProjectId" value="">
        <input type="hidden" id="proPlanId" name="proPlanId" value="">
        <input type="hidden" id="eiaEnvProjectId" name="eiaEnvProjectId" value="">
    </form>
</div>

</body>
</html>