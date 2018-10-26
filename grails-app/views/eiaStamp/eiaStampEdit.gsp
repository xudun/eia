<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>印章申请信息</title>

    <meta name="layout" content="main"/>
    <asset:javascript src="/eiaStamp/eiaStampEdit.js"/>
    <asset:javascript src="/eiaProject/eiaProjectInputLib.js"/>
    <asset:stylesheet src="/eiaProject/eiaProjectCreate.css"/>
    <style>
    .fileUploadBox{display: none;}
</style>
</head>

<body class="pb68">
<div class="layui-fluid larry-wrapper pt0">

    <form class="layui-form proCreate">

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
             %{--   <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">*</span>公司名称</label>

                    <div class="layui-input-block">
                        <input type="radio" name="stampCompany" value="联合泰泽" title="联合泰泽" checked>
                        <input type="radio" name="stampCompany" value="联合赤道" title="联合赤道">
                    </div>
                </div>--}%
                <div class="layui-form-item" id="contractSelect">
                    <label class="layui-form-label"><span class="col-f00">*</span>公司名称</label>

                    <div class="layui-input-block">
                        <select name="stampCompany" id="stampCompany" lay-filter="stampCompany" lay-verify="required"
                                lay-search>
                            <option value="">请选择</option>
                        </select>
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">*</span>印章是否外带</label>

                    <div class="layui-input-block">
                        <input type="radio" name="ifTakeOut" lay-filter='ifTakeOut' value=true title="是" checked>
                        <input type="radio" name="ifTakeOut" lay-filter='ifTakeOut' value=false title="否">
                    </div>
                </div>

                <div class="layui-form-item  verify-item ">
                    <label class="layui-form-label"><span class="col-f00">*</span>监督人</label>

                    <div class="layui-input-block ">
                        <input type="text" id="supervisor" name="supervisor" class="layui-input " lay-verify="required"
                               value="">
                    </div>
                </div>

            </div>

            <div class="layui-col-xs6">

                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">*</span>印章类型</label>

                    <div class="layui-input-block eiaStampBox">
                        <input type="checkbox" name="stampType" lay-filter='eiaStampfilter' value="公章" title="公章">
                        <input type="checkbox" name="stampType" lay-filter="eiaStampfilter" value="财务章" title="财务章">
                        <input type="checkbox" name="stampType" lay-filter="eiaStampfilter" value="法人章" title="法人章">
                        <input type="checkbox" name="stampType" lay-filter="eiaStampfilter" value="合同章" title="合同章" >
                    </div>
                    <input type="hidden" name="stampTypeCode" id="stampTypeCode" lay-verify="required">
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label"> <span class="col-f00">*</span>是否业务用章</label>

                    <div class="layui-input-block">
                        <input type="radio" name="ifBussUse" lay-filter="ifBussUse" value="true" title="业务用章" checked>
                        <input type="radio" name="ifBussUse" lay-filter="ifBussUse" value="false" title="非业务用章">
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">*</span>用章次数</label>

                    <div class="layui-input-block">
                        <input type="text" id="stampNum" name="stampNum" class="layui-input" lay-verify="required|intnumber"
                               value="">
                    </div>
                </div>
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label"><span class="col-f00">*</span>申请事由</label>

            <div class="layui-input-block">
                <textarea name="appReason" id="appReason" placeholder="请输入提交内容" class="layui-textarea"
                          lay-verify="required" value=""></textarea>
            </div>
        </div>
        <input type="hidden" id="eiaStampId" name="eiaStampId">
    </form>
</div>


<div class="layui-fluid larry-wrapper pt0 fileUploadBox">
    <div class="layer-title">
        <fieldset class="layui-elem-field layui-field-title site-title">
            <legend id="title">
                <a name="methodRender" class="pageTitle">附件上传</a>
            </legend>
        </fieldset>
    </div>

    <!--列表-->
    <div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
        <table id="eiaFileUploadList" lay-filter="eiaFileUploadList"></table>
    </div>
</div>
<input type="hidden" id="tableName" value=""/>
<input type="hidden" id="tableId" value=""/>
<script type="text/html" id="eiaFileUploadListBar">
<div class="layui-btn-group">
    {{# if (d.fileUploadType!="EiaStampFile"){ }}
    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaFileUploadDetail" title="查看"><i class="larry-icon">&#xe939;</i></a>
    {{# } }}
    {{# if ( d.ifReadOnly ){ }}
    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaFileUploadDel" title="删除"><i class="larry-icon">&#xe8d0;</i></a>
    {{# } }}
    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaFileUploadDownload" title="下载"><i class="larry-icon">&#xe8cd;</i></a>
</div>
</script>

<script type="text/html" id="tableTopTmp">
<div class="layui-btn-group pl12">
    <a class="layui-btn pl12 eiaFileUploadAdd" lay-event='eiaFileUploadAdd'><i class="larry-icon">&#xe876;</i> 新增</a>
    <a class="layui-btn layui-bg-red noticeTag"><i class="larry-icon">&#xe740;</i> 保存基本信息后可新增文件</a>
</div>
</script>
</body>
</html>