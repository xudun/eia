<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>数据维护</title>
    <meta name="layout" content="main"/>
    <asset:javascript src="/eiaDataMaintainLog/eiaDataMaintainSubCreate.js"/>
</head>
<body class="pb68">
<div class="layui-fluid larry-wrapper pt0">
    <div class="layer-title">
        <fieldset class="layui-elem-field layui-field-title site-title">
            <legend id="title">
                <a name="methodRender">数据维护</a>
            </legend>
        </fieldset>
    </div>
    <form class="layui-form">

        <blockquote class="layui-elem-quote larry-btn fixed-footer">
            <div class="layui-inline pl12">
                <div class="layui-btn-group top-group">
                    <button class="layui-btn pl12" lay-submit="" lay-filter="save"><i class="larry-icon">&#xe9d1;</i> 保存</button>
                </div>
            </div>
        </blockquote>

        <div class="layui-row mt15">
            <div class="tree-elect-box">
                <label class="layui-form-label"><span class="col-f00">* </span>数据变更</label>
                <div class="layui-input-block">
                    <div class="layui-row mt15">
                        <div class="layui-col-xs5">
                            <fieldset class="layui-elem-field">
                                <legend>可选人员</legend>
                                <div class="layui-field-box">
                                    <ul id="taskAssignUser" class="ztree"></ul>
                                </div>
                            </fieldset>
                        </div>
                        <div class="layui-col-xs2 mid-arrow">
                            <div class="layui-form-item curMemBtn">
                                <div class="layui-btn ">当前人员名称<i class="layui-icon">&#xe65b;</i></div>
                            </div>
                            <div class="layui-form-item changeMemBtn">
                                <div class="layui-btn layui-btn-primary">变更人员名称<i class="layui-icon">&#xe65b;</i></div>
                            </div>
                        </div>
                        <div class="layui-col-xs5">
                            <fieldset class="layui-elem-field">
                                <legend>已选人员</legend>
                                <div class="layui-field-box elected-box">
                                    <fieldset role="1" class="layui-elem-field eb-group eb-group-cur active">
                                        <legend><span class="col-f00">* </span>当前人员名称</legend>
                                        <div class="layui-field-box">

                                        </div>
                                        <input type="hidden" id="taskRoleToast" name="taskRoleToast" lay-verify="required" value="">
                                    </fieldset>

                                    <fieldset role="2" class="layui-elem-field eb-group eb-group-change">
                                        <legend><span class="col-f00">* </span>变更人员名称</legend>
                                        <div class="layui-field-box ">

                                        </div>
                                        <input type="hidden" id="taskRoleExam" name="taskRoleExam" lay-verify="required" value="">
                                    </fieldset>
                                </div>
                            </fieldset>
                        </div>

                    </div>
                </div>

            </div>
            <!--合同id-->
            <input type="hidden" id="contractId" name="contractId" value="">
            <input type="hidden" id="offerId" name="offerId" value="">
            <input type="hidden" id="taskId" name="taskId" value="">
            <input type="hidden" id="projectId" name="projectId" value="">
            <input type="hidden" id="clientId" name="clientId" value="">
            <input type="hidden" id="projectPlanId" name="projectPlanId" value="">
            <input type="hidden" id="eiaCertId" name="eiaCertId" value="">
            <input type="hidden" id="maintainType" name="maintainType" value="">

    </form>
</div>
</body>
</html>