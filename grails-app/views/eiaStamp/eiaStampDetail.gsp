<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>印章申请信息</title>

    <meta name="layout" content="main"/>
    <asset:javascript src="/eiaStamp/eiaStampDetail.js"/>
    <asset:stylesheet src="/eiaProject/eiaProjectCreate.css"/>
</head>

<body>
<div class="layui-fluid larry-wrapper pt0">

    <form class="layui-form proCreate">
        <blockquote class="layui-elem-quote larry-btn">
            印章申请信息
        </blockquote>


        <div class="layui-row mt15">
            <div class="layui-col-xs6">
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">*</span>公司名称</label>
                    <div class="layui-input-block check-block" id="stampCompany"></div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">*</span>印章是否外带</label>
                    <div class="layui-input-block check-block" id="ifTakeOut"></div>

                </div>

                <div class="layui-form-item  verify-item ">
                    <label class="layui-form-label"><span class="col-f00">*</span>监督人</label>

                    <div class="layui-input-block ">
                        <input type="text" id="supervisor" name="supervisor" class="layui-input readonly" lay-verify="required"
                               value="" readonly>
                    </div>
                </div>

            </div>

            <div class="layui-col-xs6">

                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">*</span>印章类型</label>
                    <div class="layui-input-block check-block" id="stampType"></div>
                    <input type="hidden" name="stampTypeCode" id="stampTypeCode" lay-verify="required">
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">*</span>是否业务用章</label>
                    <div class="layui-input-block check-block" id="ifBussUse"></div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">*</span>用章次数</label>

                    <div class="layui-input-block">
                        <input type="text" id="stampNum" name="stampNum" class="layui-input readonly" lay-verify="required"
                               value="" readonly>
                    </div>
                </div>
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label"><span class="col-f00">*</span>申请事由</label>

            <div class="layui-input-block">
                <textarea name="appReason" id="appReason" placeholder="请输入提交内容" class="layui-textarea readonly"
                          lay-verify="required" value="" readonly></textarea>
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

</body>
</html>