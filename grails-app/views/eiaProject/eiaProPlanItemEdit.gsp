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
    <asset:javascript src="/eiaProject/eiaProPlanItemEdit.js"/>
    <asset:stylesheet src="/eiaProject/eiaProPlanItemEdit.css"/>
    <style>
        .layui-form .layui-form-label{width: 115px;}
        .layui-input-block{margin-left: 145px;}
    </style>
</head>

<body>
<div class="layui-fluid larry-wrapper pt0">
    <form class="layui-form proCreate">
        <blockquote class="layui-elem-quote larry-btn">
            <span class="showTitle"></span>
            <div class="layui-inline pl12">
                <div class="layui-btn-group top-group">
                    <button class="layui-btn" lay-submit="" lay-filter="save"><i class="larry-icon">&#xe9d1;</i> 保存</button>
                    <button type="reset" class="layui-btn layui-btn-primary resetBtn"><i class="larry-icon">&#xe69a;</i> 重置</button>
                </div>
            </div>
        </blockquote>

        <div class="layui-row mt15">
            <div class="layui-col-xs6">

                <div class="layui-form-item oldItem">
                    <label class="layui-form-label ">%{--<span class="col-f00">*</span>--}%节点审批人</label>
                    <div class="layui-input-block">
                        <select name="nodeUser" id="nodeUser" lay-verify="" lay-search>
                            <option value="">请选择</option>
                        </select>
                    </div>
                </div>
                <div class="layui-form-item canHideItem noRequired">
                    <label class="layui-form-label"><span class="col-f00">*</span>预计开始日期</label>
                    <div class="layui-input-block">
                        <input type="text" name="planStartDate" id="planStartDate" class="layui-input" lay-verify="required">
                    </div>
                </div>
                <div class="layui-form-item oldItem">
                    <label class="layui-form-label"><span class="col-f00">*</span>实际开始日期</label>
                    <div class="layui-input-block ">
                        <input type="text" name="actStartDate" id="actStartDate" class="layui-input" lay-verify="required">
                    </div>
                </div>
                <div class="layui-form-item oldItem xckcItem">
                    <label class="layui-form-label"><span class="col-f00">*</span>是否符合城市(园区)总规划</label>

                    <div class="layui-input-block">
                        <input type="radio" id="isCityPlan" name="ifCityPlan" value=true title="是" >
                        <input type="radio" id="notCityPlan" name="ifCityPlan" value=false title="否">
                    </div>
                </div>
                <div class="layui-form-item oldItem xckcItem">
                    <label class="layui-form-label"><span class="col-f00">*</span>踏勘日期</label>
                    <div class="layui-input-block">
                        <input type="text" id="exploreDate" name="exploreDate" class="layui-input " value="" lay-verify="required" >
                    </div>
                </div>
            </div>
            <div class="layui-col-xs6">
                <div class="layui-form-item proMonBlock oldItem">
                    <label class="layui-form-label ">%{--<span class="col-f00">*</span>--}%相关工作人员</label>
                    <div class="layui-input-block">
                        <input type="text" id="userNamesDept" name="userNamesDept" class="layui-input" lay-verify="" readonly>
                        <input type="hidden" id="userNames" name="userNames" value="">
                    </div>
                </div>
               %{-- <div class="layui-form-item canHideItem noRequired">
                    <label class="layui-form-label"><span class="col-f00">*</span>预计结束日期</label>
                    <div class="layui-input-block">
                        <input type="text" name="planEndDate" id="planEndDate" class="layui-input" lay-verify="">
                    </div>
                </div>--}%
                <div class="layui-form-item canHideItem noRequired">
                  <label class="layui-form-label"><span class="col-f00">*</span>预计完成天数</label>
                  <div class="layui-input-block">
                      <input type="text" name="workDayNum" id="workDayNum" class="layui-input" lay-verify="required|number">
                  </div>
              </div>

                <div class="layui-form-item oldItem">
                    <label class="layui-form-label"><span class="col-f00">*</span>实际结束日期</label>
                    <div class="layui-input-block">
                        <input type="text" id="actEndDate" name="actEndDate" class="layui-input " value="" lay-verify="required" >
                    </div>
                </div>

                <div class="layui-form-item oldItem xckcItem">
                    <label class="layui-form-label"><span class="col-f00">*</span>是否涉及水源、风景名胜等敏感区</label>

                    <div class="layui-input-block">
                        <input type="radio" id="isSensArea" name="ifSensArea" value=true title="是" lay-verify="required" >
                        <input type="radio" id="notSensArea" name="ifSensArea" value=false title="否" lay-verify="required">
                    </div>
                </div>
                <div class="layui-form-item oldItem xckcItem">
                    <label class="layui-form-label"><span class="col-f00">*</span>建设地点</label>
                    <div class="layui-input-block">
                        <input type="text" id="buildArea" name="buildArea" class="layui-input " value="" lay-verify="required" >
                    </div>
                </div>
            </div>
            <div class="layui-row mt15 dynInputs">
                <div class="layui-form-item oldItem">
                    <label class="layui-form-label"><span class="col-f00"></span>审批意见</label>
                    <div class="layui-input-block">
                        <textarea type="text" name="opinion" id="opinion" class="layui-textarea "  ></textarea>
                    </div>
                </div>

                <div class="layui-form-item oldItem">
                    <label class="layui-form-label"><span class="col-f00"></span>修改内容</label>
                    <div class="layui-input-block">
                        <textarea type="text" name="modiContent" id="modiContent" class="layui-textarea " ></textarea>
                    </div>
                </div>

                <div class="layui-form-item oldItem xckcItem">
                    <label class="layui-form-label"><span class="col-f00">*</span>踏勘记录</label>
                    <div class="layui-input-block">
                        <textarea type="text" name="exploreRecord" id="exploreRecord" class="layui-textarea"
                                  lay-verify="required" placeholder="四至范围,敏感目标等..."></textarea>
                    </div>
                </div>
            </div>
        </div>
        <input type="hidden" id="proPlanItemId" name="proPlanItemId" value="">
        <input type="hidden" id="proPlanId" name="proPlanId" value="">
        <input type="hidden" id="oid" name="oid" value="">
        <input type="hidden" id="eiaProjectId" name="eiaProjectId" value="">
        <input type="hidden" id="eiaEnvProjectId" name="eiaEnvProjectId" value="">
        <input type="hidden" id="inputUserId" name="inputUserId" value="">
        <input type="hidden" id="inputDept" name="inputDept" value="">
        <input type="hidden" id="inputDeptId" name="inputDeptId" value="">
        <input type="hidden" id="inputDeptCode" name="inputDeptCode" value="">
    </form>
</div>

%{--<script src="js/jquery/jquery-2.1.3.js"></script>
<script src="js/layuiFrame/common/frame/layui/layui.js"></script>
<script src="pagejs/eiaProjectInputLib.js"></script>
<script src="pagejs/eiaProjectCreate.js"></script>--}%
</body>
</html>