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
    <asset:javascript src="/eiaProject/changeWorkFlow.js"/>
    <asset:stylesheet src="/eiaProject/eiaProPlanItemEdit.css"/>
</head>

<body>
<div class="layui-fluid larry-wrapper pt0">
    <form class="layui-form proCreate">
        <blockquote class="layui-elem-quote larry-btn">
            <span class="showTitle"></span>

            <div class="layui-inline pl12">
                <div class="layui-btn-group top-group">
                    <button class="layui-btn" lay-submit="" lay-filter="save"><i class="larry-icon">&#xe9d1;</i> 保存
                    </button>
                    <button type="reset" class="layui-btn layui-btn-primary resetBtn"><i
                            class="larry-icon">&#xe69a;</i> 重置</button>
                </div>
            </div>
        </blockquote>

        <div class="layui-row mt15">
            <div class="layui-col-xs6">

                <div class="layui-form-item">
                    <label class="layui-form-label">%{--<span class="col-f00">*</span>--}%节点编码</label>

                    <div class="layui-input-block">
                        <select name="nodesCode" id="nodesCode" lay-filter="nodesCodeSelect" lay-search>
                            <option value="">请选择</option>
                        </select>
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label">%{--<span class="col-f00">*</span>--}%节点审批人</label>

                    <div class="layui-input-block">
                        <select name="nodeUser" id="nodeUser" lay-verify="" lay-search>
                            <option value="">请选择</option>
                        </select>
                    </div>
                </div>

            </div>

            <div class="layui-col-xs6">

            </div>

            <div class="layui-row mt15 dynInputs">

            </div>
        </div>
        <input type="hidden" id="proPlanItemId" name="proPlanItemId" value="">
        <input type="hidden" id="proPlanId" name="proPlanId" value="">
        <input type="hidden" id="oid" name="oid" value="">
    </form>
</div>

</body>
</html>