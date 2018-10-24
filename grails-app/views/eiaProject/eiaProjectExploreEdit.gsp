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
    <asset:javascript src="/eiaProject/eiaProjectExploreEdit.js"/>
    <asset:stylesheet src="/eiaProject/eiaProjectExploreEdit.css"/>
</head>

<body class="pb68">
<div class="layer-title">
    <fieldset class="layui-elem-field layui-field-title site-title">
        <legend id="title">
            <a name="methodRender" class="pageTitle">现场踏勘</a>
        </legend>
    </fieldset>
</div>

<div class="layui-fluid larry-wrapper pt0">
    <form class="layui-form proCreate">

        <blockquote class="layui-elem-quote larry-btn fixed-footer">
            <div class="layui-inline pl12">
                <div class="layui-btn-group top-group">
                    <button class="layui-btn saveBtn pl12" lay-submit="" lay-filter="save"><i class="larry-icon">&#xe9d1;</i> 保存</button>
                </div>
            </div>
        </blockquote>

        <div class="layui-row mt15">
            <div class="layui-col-xs6">
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">*</span>项目名称</label>

                    <div class="layui-input-block">
                        <input type="text" name="projectName" id="projectName" class="layui-input readonly"
                               lay-verify="required" readonly>
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">*</span>项目类型</label>

                    <div class="layui-input-block">
                        <input type="text" name="fileTypeChild" id="fileTypeChild" class="layui-input readonly"
                               lay-verify="required" readonly>
                    </div>
                </div>

               %{-- <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">*</span>项目主持人</label>

                    <div class="layui-input-block">
                        <input type="text" name="taskAssignUser" id="taskAssignUser" class="layui-input readonly"
                               lay-verify="" readonly>
                    </div>
                </div>--}%
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">*</span>项目主持人</label>
                    <div class="layui-input-block">
                        <select type="text" id="dutyUserList" name="dutyUserList" class="layui-input" lay-verify="required" lay-filter="dutyUserList"  value=""></select>
                        <input type="hidden" id="dutyUserId" name="dutyUserId" class="layui-input" lay-verify="required" value="">
                        <input type="hidden" id="dutyUser" name="dutyUser" class="layui-input" lay-verify="required" value="">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">*</span>是否涉及水源、风景名胜等敏感区</label>

                    <div class="layui-input-block">
                        <input type="radio" id="isSensArea" name="ifSensArea" value=true title="是" lay-verify="required" >
                        <input type="radio" id="notSensArea" name="ifSensArea" value=false title="否" lay-verify="required">
                    </div>
                </div>
            </div>

            <div class="layui-col-xs6">
                <div class="layui-form-item proMonBlock">
                    <label class="layui-form-label"><span class="col-f00">*</span>项目编号</label>

                    <div class="layui-input-block">
                        <input type="text" id="projectNo" name="projectNo" class="layui-input readonly"
                               lay-verify="required" readonly>
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">*</span>踏勘人员</label>

                    <div class="layui-input-block">
                        <input type="text" name="inputUser" id="inputUser" class="layui-input readonly" lay-verify=""
                               readonly>
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">*</span>踏勘时间</label>

                    <div class="layui-input-block">
                        <input type="text" name="exploreDate" id="exploreDate" class="layui-input" lay-verify="required">
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">*</span>是否符合城市(园区)总规划</label>

                    <div class="layui-input-block">
                        <input type="radio" id="isCityPlan" name="ifCityPlan" value=true title="是" >
                        <input type="radio" id="notCityPlan" name="ifCityPlan" value=false title="否">
                    </div>
                </div>
            </div>
        </div>

        <div class="mt15 dynInputs">
            <div class="layui-form-item">
                <label class="layui-form-label"><span class="col-f00">*</span>踏勘记录</label>

                <div class="layui-input-block">
                    <textarea type="text" name="exploreRecord" id="exploreRecord" class="layui-textarea"
                              lay-verify="required" placeholder="四至范围,敏感目标等..."></textarea>
                </div>
            </div>
        </div>
        <blockquote class="layui-elem-quote">
            现场图片
            <div class="layui-inline pl12">
                <div class="layui-btn-group top-group display-none">
                    <button class="layui-btn saveBtn display-none" type="button" id="selectFile"><i class="layui-icon">&#xe615;</i> 选择文件
                    </button>
                    <button class="layui-btn saveBtn display-none" type="button" id="uploadStart"><i class="layui-icon">&#xe67c;</i> 开始上传
                    </button>
                </div>
                <div class="layui-btn-group top-group">
                    <a class="layui-btn layui-bg-red noticeTag"><i class="larry-icon">&#xe740;</i> 填写现场踏勘基本信息后即可上传文件</a>
                </div>
            </div>
        </blockquote>

        <div class="layui-form-item">
            <label class="layui-form-label">现场预览图</label>

            <div class="layui-input-block">
                <blockquote class="layui-elem-quote layui-quote-nm" style="margin-top: 10px;">
                    <div class="layui-upload-list" id="preview"></div>
                </blockquote>
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">现场图</label>

            <div class="layui-input-block">
                <blockquote class="layui-elem-quote layui-quote-nm" style="margin-top: 10px;">
                    <div class="layui-upload-list" id="uploaded">
                    </div>
                </blockquote>
            </div>
        </div>
        <input type="hidden" id="eiaProjectId" name="eiaProjectId" value="">
        <input type="hidden" id="proPlanId" name="proPlanId" value="">
        <input type="hidden" id="eiaEnvProjectId" name="eiaEnvProjectId" value="">
        <input type="hidden" id="inputUserId" name="inputUserId" value="">
        <input type="hidden" id="inputDept" name="inputDept" value="">
        <input type="hidden" id="inputDeptId" name="inputDeptId" value="">
        <input type="hidden" id="inputDeptCode" name="inputDeptCode" value="">
        <input type="hidden" id="staffId" name="staffId" value="${session.staff.staffId}">
    </form>
</div>

</body>
</html>