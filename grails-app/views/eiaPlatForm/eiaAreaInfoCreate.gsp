<%@ page contentType="text/html;charset=UTF-8" import="com.lheia.eia.common.FuncConstants"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="layout" content="main"/>
    <title>新增区域/企业信息</title>
    <asset:stylesheet src="/eiaPlatForm/eiaAreaInfo.css"/>
    <asset:javascript src="/eiaPlatForm/eiaAreaInfoInputLib.js"/>
    <asset:javascript src="/eiaPlatForm/eiaAreaInfoCreate.js"/>
</head>
<body class="pb68">
<div class="layui-fluid larry-wrapper pt15">
    <div class="layer-title">
        <fieldset class="layui-elem-field layui-field-title site-title">
            <legend id="title">
                <a name="methodRender" class="pageTitle"></a>
            </legend>
        </fieldset>
    </div>

    <form class="layui-form area-info-form">
        <input type="hidden" id="eiaAreaInfoId" name="eiaAreaInfoId">

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
                    <label class="layui-form-label"><span class="col-f00">* </span>区域/企业名称</label>
                    <div class="layui-input-block">
                        <input type="text" id="projectName" name="projectName" class="layui-input" lay-verify="required" value="">
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
                    <label class="layui-form-label"><span class="col-f00">* </span>所属区域</label>
                    <div class="layui-input-block">
                        <input type="text" id="regionNameDrop" name="regionNameDrop" class="layui-input" lay-verify="required" readonly>
                        <input type="hidden" id="regionName" name="regionName" value="">
                    </div>
                </div>
            </div>
            <div class="layui-col-xs6">
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">* </span>数据类别</label>
                    <div class="layui-input-block">
                        <select name="dataType" id="dataType" lay-filter="dataType" lay-verify="required" lay-search>
                            <option value="">请选择</option>
                        </select>
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
            </div>
        </div>

        <div class="layui-row mt15 dynInputs">
            <div class="layui-col-xs6"></div>
            <div class="layui-col-xs6"></div>
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
<div class="table-top">
    文件上传
    <div class="layui-btn-group pl12">
        <a class="layui-btn pl12 fileAddBtn display-none" lay-event="eiaFileUploadAdd"><i class="larry-icon">&#xe876;</i> 新增</a>
        <a class="layui-btn layui-bg-red noticeTag display-none"><i class="larry-icon">&#xe740;</i> 填写区域概况基本信息后即可添加附件</a>
    </div>
</div>
</g:if>
</script>
</body>
</html>