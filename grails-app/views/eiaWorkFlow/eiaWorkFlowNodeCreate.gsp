<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>新增流程节点</title>
    <meta name="layout" content="main"/>
    <asset:stylesheet src="/eiaWorkFlow/eiaWorkFlow.css"/>
    <asset:javascript src="/eiaWorkFlow/eiaWorkFlowNodeCreate.js"/>
</head>
<body class="pb68">
<div class="layui-fluid larry-wrapper pt0">
    <div class="layer-title">
        <fieldset class="layui-elem-field layui-field-title site-title">
            <legend id="title">
                <a name="methodRender" class="pageTitle">新增流程节点</a>
            </legend>
        </fieldset>
    </div>

    <form class="layui-form flow-node-form">

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
                    <label class="layui-form-label"><span class="col-f00">* </span>流程节点编码</label>
                    <div class="layui-input-block">
                        <input type="text" id="nodesCode" name="nodesCode" class="layui-input" lay-verify="required" value="">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">* </span>流程节点名称</label>
                    <div class="layui-input-block">
                        <input type="text" id="nodesName" name="nodesName" class="layui-input" lay-verify="required" value="">
                    </div>
                </div>
                <div class="layui-form-item has-sq">
                    <label class="layui-form-label"><span class="col-f00">* </span>流程节点图标</label>
                    <div class="layui-input-block">
                        <input type="text" id="nodesIconName" name="nodesIconName" class="layui-input" lay-verify="required">
                    </div>
                    <div class="sq-box">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">* </span>节点tab页名称</label>
                    <div class="layui-input-block">
                        <input type="text" id="nodesTabTitle" name="nodesTabTitle" class="layui-input" lay-verify="required" value="">
                    </div>
                </div>
                <div class="layui-form-item has-sq">
                    <label class="layui-form-label">流程节点颜色</label>
                    <div class="layui-input-block">
                            <input type="text" id="nodesColor" name="nodesColor" class="layui-input" value="">
                    </div>
                    <div class="sq-box">
                        <span class="layui-badge-rim"></span>
                    </div>
                </div>
            </div>
            <div class="layui-col-xs6">
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">* </span>流程节点序号</label>
                    <div class="layui-input-block">
                        <input type="text" id="nodesNum" name="nodesNum" class="layui-input" lay-verify="required|intnumberZero">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">* </span>流程节点权限控制类型</label>
                    <div class="layui-input-block">
                        <select name="nodesAuthType" id="nodesAuthType" class="layui-input" lay-verify="required">
                            <option value="">请选择类型</option>
                            <option id="AUTH_CODE" value="AUTH_CODE">AUTH_CODE</option>
                            <option id="USER_CODE" value="USER_CODE">USER_CODE</option>
                        </select>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">* </span>流程节点URL</label>
                    <div class="layui-input-block">
                        <input type="text" id="nodesUrl" name="nodesUrl" class="layui-input" lay-verify="required" value="">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">节点权限编码</label>
                    <div class="layui-input-block">
                        <input type="text" id="nodesAuthCode" name="nodesAuthCode" class="layui-input" value="">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">*</span>是否显示图标</label>

                    <div class="layui-input-block">
                        <input type="checkbox" id="ifIconSwitch" name="ifIconSwitch" lay-filter="ifIconSwitch" lay-skin="switch" lay-text="是|否">
                        <input type="hidden" id="ifIcon" name="ifIcon" value="false">
                    </div>
                </div>
            </div>
        </div>

        <!--节点动作列表 -->
        <div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
            <table id="eiaWorkFlowNodeProcessList" lay-filter="eiaWorkFlowNodeProcessList"></table>
        </div>

        <!--流程节点id-->
        <input type="hidden" id="eiaWorkFlowId" name="eiaWorkFlowId">
        <input type="hidden" id="eiaWorkFlowNodeId" name="eiaWorkFlowNodeId">
        <input type="hidden" id="eiaWorkFlowNodeProcessId" name="eiaWorkFlowNodeProcessId">
    </form>
</div>

<script type="text/html" id="indexTable">
    <span>{{d.LAY_TABLE_INDEX + 1}}</span>
</script>
<script type="text/html" id="mlTool">
    <div class="layui-btn-group">
        <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaCheck" title="查看"><i class="larry-icon">&#xe896;</i></a>
        <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaEdit" title="编辑"><i class="larry-icon">&#xe646;</i></a>
        <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaDel" title="删除"><i class="larry-icon">&#xe8d0;</i></a>
    </div>
</script>
<script type="text/html" id="iconTp">
    <i class="larry-icon table-cell-icon">{{d.processIconName}}</i>
</script>
<script type="text/html" id="colorTp">
    <span class="layui-badge-rim" style="background:{{d.processColor}}"></span>
    <span class="layui-badge-rim">{{d.processColor}}</span>
</script>
<script type="text/html" id="tableTopTmp">
<div class="table-top">
    节点动作列表
    <div class="layui-inline pl12">
        <div class="layui-btn-group top-group">
            <a class="layui-btn layui-bg-pale addFlowNodeProcessBtn" lay-event="addFlowNodeProcessBtn"><i class="larry-icon">&#xe81c;</i> 新增</a>
            <a class="layui-btn layui-bg-red noticeTag"><i class="larry-icon">&#xe740;</i> 保存流程节点信息后即可添加流程节点动作</a>
        </div>
    </div>
</div>
</script>
</body>
</html>