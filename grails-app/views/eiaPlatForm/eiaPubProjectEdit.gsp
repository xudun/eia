<%@ page contentType="text/html;charset=UTF-8" import="com.lheia.eia.common.FuncConstants"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="layout" content="main"/>
    <title>修改审批公示企业</title>
    <asset:stylesheet src="/eiaPlatForm/eiaPubProject.css"/>
    <asset:javascript src="/eiaPlatForm/eiaPubProjectEdit.js"/>
</head>
<body>
<div class="layui-fluid larry-wrapper pt0">
    <div class="layer-title">
        <fieldset class="layui-elem-field layui-field-title site-title">
            <legend id="title">
                <a name="methodRender" class="pageTitle">修改审批公示企业</a>
            </legend>
        </fieldset>
    </div>

    <form class="layui-form pub-pro-form">
        <input type="hidden" id="eiaPubProjectId" name="eiaPubProjectId">
        <blockquote class="layui-elem-quote larry-btn">
            基本信息
            <div class="layui-inline pl12">
                <div class="layui-btn-group top-group">
                    <button class="layui-btn" lay-submit="" lay-filter="save"><i class="larry-icon">&#xe9d1;</i> 保存</button>
                    <!--<button type="reset" class="layui-btn layui-btn-primary resetBtn"><i class="larry-icon">&#xe69a;</i> 重置</button>-->
                </div>
            </div>
        </blockquote>
        <div class="layui-row mt15">
            <div class="layui-col-xs6">
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">* </span>项目名称</label>
                    <div class="layui-input-block">
                        <input type="text" id="projectName" name="projectName" class="layui-input" lay-verify="required" value="">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>审批部门</label>
                    <div class="layui-input-block">
                        <input type="text" id="approveDept" name="approveDept" class="layui-input" value="">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>建设性质</label>
                    <div class="layui-input-block">
                        <select name="natureConstructio" id="natureConstructio" lay-search>
                            <option value="">请选择</option>
                        </select>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>行业类型及代码</label>
                    <div class="layui-input-block">
                        <input type="text" id="industryTypeDrop" name="industryTypeDrop" class="layui-input" readonly>
                        <input type="hidden" id="industryType" name="industryType" value="">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>产品或功能</label>
                    <div class="layui-input-block">
                        <input type="text" id="productFunction" name="productFunction" class="layui-input" value="">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>环评单位</label>
                    <div class="layui-input-block">
                        <input type="text" id="eiaUnit" name="eiaUnit" class="layui-input" value="">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>建设地点</label>
                    <div class="layui-input-block">
                        <input type="text" id="projectLoc" name="projectLoc" class="layui-input" value="">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>建设地点中心坐标 E</label>
                    <div class="layui-input-block">
                        <input type="text" id="geographicEast" name="geographicEast" class="layui-input" value="">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>建设地点线性坐标起点 E</label>
                    <div class="layui-input-block">
                        <input type="text" id="geographicStartEast" name="geographicStartEast" class="layui-input" value="">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>建设地点线性坐标终点 E</label>
                    <div class="layui-input-block">
                        <input type="text" id="geographicEndEast" name="geographicEndEast" class="layui-input" value="">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>公示附件</label>
                    <div class="layui-input-block show-block">
                        <a id="spiderFileDownloadUrl" class="action-txt">点击后查看或下载公示附件</a>
                    </div>
                </div>
            </div>
            <div class="layui-col-xs6">
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>所属区域</label>
                    <div class="layui-input-block">
                        <input type="text" id="regionNameDrop" name="regionNameDrop" class="layui-input" readonly>
                        <input type="hidden" id="regionName" name="regionName" value="">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">* </span>审批年度</label>
                    <div class="layui-input-block" lay-filter="publictyYear">
                        <select name="publictyYear" id="publictyYear" lay-verify="required" lay-search>
                            <option value="">请选择</option>
                        </select>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>生产工艺</label>
                    <div class="layui-input-block">
                        <input type="text" id="productionEngineer" name="productionEngineer" class="layui-input" value="">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>环境影响评价行业类别</label>
                    <div class="layui-input-block">
                        <input type="text" id="environmentaTypeDrop" name="environmentaTypeDrop" class="layui-input" readonly>
                        <input type="hidden" id="environmentaType" name="environmentaType" value="">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>建设单位</label>
                    <div class="layui-input-block">
                        <input type="text" id="developmentOrg" name="developmentOrg" class="layui-input" value="">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">* </span>公示类型</label>
                    <div class="layui-input-block">
                        <select name="dataType" id="dataType" lay-verify="required" lay-search>
                            <option value="">请选择</option>
                            <option value="审批公示">审批公示</option>
                            <option value="验收公示">验收公示</option>
                        </select>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>公示日期</label>
                    <div class="layui-input-block">
                        <input type="text" id="pubDate" name="pubDate" class="layui-input" lay-verify="required|date" placeholder="年-月-日" autocomplete="off" value="">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>建设地点中心坐标 N</label>
                    <div class="layui-input-block">
                        <input type="text" id="geographicNorth" name="geographicNorth" class="layui-input" value="">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>建设地点线性坐标起点 N</label>
                    <div class="layui-input-block">
                        <input type="text" id="geographicStartNorth" name="geographicStartNorth" class="layui-input" value="">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>建设地点线性坐标终点 N</label>
                    <div class="layui-input-block">
                        <input type="text" id="geographicEndNorth" name="geographicEndNorth" class="layui-input" value="">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>原文链接</label>
                    <div class="layui-input-block show-block">
                        <a id="spiderFileUrl" href="" target="_blank" class="action-txt">点击后查看原文链接</a>
                    </div>
                </div>
            </div>
            <div class="layui-col-xs12">
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>项目基本情况说明</label>
                    <div class="layui-input-block">
                        <textarea id="projectMemo" name="projectMemo" placeholder="请输入内容" class="layui-textarea" value=""></textarea>
                    </div>
                </div>
            </div>
        </div>

        <g:if test="${session.staff.funcCode.contains(FuncConstants.EIA_GXPT_GXCX_UPLOAD)}">
            <!--列表-->
            <div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12 uploadFile">
                <table id="eiaFileUploadList" lay-filter="eiaFileUploadList"></table>
            </div>
            <input type="hidden" id="tableName" value=""/>
            <input type="hidden" id="tableId" value=""/>
        </g:if>
    </form>

</div>
<script type="text/html" id="eiaFileUploadListBar">
<div class="layui-btn-group">
    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaFileUploadDel" title="删除"><i class="larry-icon">&#xe8d0;</i></a>
    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaFileUploadDownload" title="下载"><i class="larry-icon">&#xe8cd;</i></a>
</div>
</script>
<script type="text/html" id="tableTopTmp">
<g:if test="${session.staff.funcCode.contains(FuncConstants.EIA_GXPT_GXCX_UPLOAD)}">
<div class="table-top cal-quote">
    文件上传
    <div class="layui-btn-group pl12">
        <a class="layui-btn pl12" lay-event="eiaFileUploadAdd"><i class="larry-icon">&#xe876;</i> 新增</a>
    </div>
</div>
</g:if>
</script>
</body>
</html>