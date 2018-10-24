<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>任务信息新增</title>
    <meta name="layout" content="main"/>
    <asset:javascript src="/eiaTask/eiaTaskCreate.js"/>
</head>
<body class="pb68">
<div class="layui-fluid larry-wrapper pt0">
    <div class="layer-title">
        <fieldset class="layui-elem-field layui-field-title site-title">
            <legend id="title">
                <a name="methodRender">新增任务</a>
            </legend>
        </fieldset>
    </div>
    <form class="layui-form">

        <blockquote class="layui-elem-quote larry-btn fixed-footer">
            <div class="layui-inline pl12">
                <div class="layui-btn-group top-group">
                    <button class="layui-btn pl12" lay-submit="" lay-filter="save"><i class="larry-icon">&#xe9d1;</i> 保存</button>
                    <button type="reset" class="layui-btn layui-btn-primary resetBtn pl12"><i class="larry-icon">&#xe69a;</i> 重置</button>
                </div>
            </div>
        </blockquote>

        <div class="layui-row mt15">
            <div class="layui-col-xs6">
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>任务单号</label>
                    <div class="layui-input-block">
                        <input type="text" id="taskNo" name="taskNo" class="layui-input readonly" readonly value="">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">* </span>业务类型</label>
                    <div class="layui-input-block busiTypeGroup">
                        <input type="hidden" id="busiTypeCode" name="busiTypeCode" value="" lay-verify="required">
                        <input type="hidden" id="busiType" name="busiType" value="">
                    </div>
                </div>
            </div>
            <div class="layui-col-xs6">
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">* </span>任务名称</label>
                    <div class="layui-input-block">
                        <input type="text" id="taskName" name="taskName" class="layui-input" lay-verify="required">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">* </span>负责部门</label>
                    <div class="layui-input-block">
                        <input type="text" id="taskLeaderDept" name="taskLeaderDept" class="layui-input" lay-verify="required" readonly>
                        <input type="hidden" id="taskLeaderDeptId" name="taskLeaderDeptId" value="">
                    </div>
                </div>
            </div>
        </div>
        <div class="tree-elect-box">
            <label class="layui-form-label"><span class="col-f00">* </span>分配人员</label>
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
                        <div class="layui-form-item">
                            <div class="layui-btn ">主持编制人<i class="layui-icon">&#xe65b;</i></div>
                        </div>
                        <div class="layui-form-item">
                            <div class="layui-btn layui-btn-primary">项目审核人<i class="layui-icon">&#xe65b;</i></div>
                        </div>
                        <div class="layui-form-item">
                            <div class="layui-btn layui-btn-primary">项目编写人<i class="layui-icon">&#xe65b;</i></div>
                        </div>

                    </div>
                    <div class="layui-col-xs5">
                        <fieldset class="layui-elem-field">
                            <legend>已选人员</legend>
                            <div class="layui-field-box elected-box">
                                <fieldset role="1" class="layui-elem-field eb-group active">
                                    <legend><span class="col-f00"></span>主持编制人</legend>
                                    <div class="layui-field-box">

                                    </div>
                                    <input type="hidden" id="taskRoleToast" name="taskRoleToast" value="">
                                </fieldset>

                                <fieldset role="2" class="layui-elem-field eb-group">
                                    <legend><span class="col-f00">* </span>项目审核人</legend>
                                    <div class="layui-field-box ">

                                    </div>
                                    <input type="hidden" id="taskRoleExam" name="taskRoleExam" lay-verify="required" value="">
                                </fieldset>

                                <fieldset role="3" class="layui-elem-field eb-group">
                                    <legend><span class="col-f00">* </span>项目编写人</legend>
                                    <div class="layui-field-box ">

                                    </div>
                                    <input type="hidden" id="taskRoleWriter" name="taskRoleWriter" lay-verify="required" value="">
                                </fieldset>
                            </div>
                        </fieldset>
                    </div>

                </div>
            </div>

        </div>

    </form>
</div>
</body>
</html>