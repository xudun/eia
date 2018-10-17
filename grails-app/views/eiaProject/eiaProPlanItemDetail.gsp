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
    <asset:javascript src="/eiaProject/eiaProPlanItemDetial.js"/>
</head>

<body>
<div class="layui-fluid larry-wrapper pt0">
    <form class="layui-form proCreate">
        <blockquote class="layui-elem-quote larry-btn">
            节点信息
        </blockquote>

        <div class="layui-row mt15">
            <div class="layui-col-xs6">

                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>节点审批人</label>

                    <div class="layui-input-block">
                        <input type="text" name="nodeUser" id="nodeUser" class="layui-input readonly"
                               lay-verify="required" readonly>
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>预计开始日期</label>

                    <div class="layui-input-block">
                        <input type="text" name="planStartDate" id="planStartDate" class="layui-input readonly"
                               lay-verify="required" readonly>
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>实际开始日期</label>

                    <div class="layui-input-block ">
                        <input type="text" name="actStartDate" id="actStartDate" class="layui-input readonly"
                                readonly>
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>预计完成天数</label>

                    <div class="layui-input-block">
                        <input type="text" name="planFinDay" id="planFinDay" class="layui-input readonly" readonly>
                    </div>
                </div>
            </div>

            <div class="layui-col-xs6">
                <div class="layui-form-item proMonBlock">
                    <label class="layui-form-label"><span class="col-f00"></span>相关工作人员</label>

                    <div class="layui-input-block">
                        <input type="text" id="userNames" name="userNames" class="layui-input readonly" lreadonly>
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>预计结束日期</label>

                    <div class="layui-input-block">
                        <input type="text" name="planEndDate" id="planEndDate" class="layui-input readonly" lay-verify="" readonly>
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>实际结束日期</label>

                    <div class="layui-input-block">
                        <input type="text" id="actEndDate" name="actEndDate" class="layui-input readonly " value="" readonly>
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>实际完成天数</label>

                    <div class="layui-input-block ">
                        <input type="text" name="" id="actFinishDay" class="layui-input readonly" lay-verify="" readonly>
                    </div>
                </div>
            </div>

            <div class="layui-form-item">
                <label class="layui-form-label"><span class="col-f00"></span>审批意见</label>
                <div class="layui-input-block">
                     <textarea type="text" name="opinion" id="opinion" class="layui-textarea readonly"  readonly></textarea>
                </div>
            </div>

            <div class="layui-form-item">
                <label class="layui-form-label"><span class="col-f00"></span>修改内容</label>
                <div class="layui-input-block">
                    <textarea type="text" name="modiContent" id="modiContent" class="layui-textarea readonly" readonly></textarea>
                </div>
            </div>
        </div>
        <input type="hidden" id="proPlanItemId" name="proPlanItemId" value="">
        <input type="hidden" id="proPlanId" name="proPlanId" value="">
    </form>
</div>

%{--<script src="js/jquery/jquery-2.1.3.js"></script>
<script src="js/layuiFrame/common/frame/layui/layui.js"></script>
<script src="pagejs/eiaProjectInputLib.js"></script>
<script src="pagejs/eiaProjectCreate.js"></script>--}%
</body>
</html>