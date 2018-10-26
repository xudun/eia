<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>项目信息</title>
    <meta name="layout" content="main"/>
    <asset:javascript src="/eiaProject/eiaProjectLogCreate.js"/>
    <asset:javascript src="/eiaProject/eiaProjectInputLib.js"/>
    <asset:stylesheet src="/eiaProject/eiaProjectCreate.css"/>
</head>

<body class="pb68">
<div class="layui-fluid larry-wrapper pt0">
    <div class="layer-title">
        <fieldset class="layui-elem-field layui-field-title site-title">
            <legend id="title">
                <a name="methodRender" class="pageTitle">编辑项目</a>
            </legend>
        </fieldset>
    </div>
    <form class="layui-form proCreate">

        <blockquote class="layui-elem-quote larry-btn fixed-footer">
            <div class="layui-inline pl12">
                <div class="layui-btn-group top-group">
                    <button class="layui-btn" lay-submit="" lay-filter="save"><i class="larry-icon">&#xe9d1;</i> 保存</button>
                </div>
            </div>
        </blockquote>

        <div class="layui-row mt15">
            <div class="layui-col-xs6">
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">*</span>项目名称</label>
                    <div class="layui-input-block">
                        <input type="text" id="projectName" name="projectName" class="layui-input" lay-verify="required" value="">
                    </div>
                </div>
                <div class="layui-form-item proMonBlock">
                    <label class="layui-form-label"><span class="col-f00">*</span>建设地点</label>
                    <div class="layui-input-block">
                        <input type="text" id="buildArea" name="buildArea" class="layui-input" lay-verify="required" value="">
                    </div>
                    <div class="action-block">
                        <div class="inner-box action-box">
                            <i class="larry-icon mapDrawBtn">&#xe9b3;</i>
                        </div>
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">*</span>建设地点中心坐标E</label>
                    <div class="layui-input-block ">
                        <input type="text" id="coordEast" name="coordEast" class="layui-input readonly " value="" readonly>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">*</span>建设地点线性坐标起点E</label>
                    <div class="layui-input-block">
                        <input type="text" id="coordStartEast" name="coordStartEast" class="layui-input readonly" value="" readonly>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">*</span>建设地点线性坐标终点E</label>
                    <div class="layui-input-block">
                        <input type="text" id="coordEndEast" name="coordEndEast" class="layui-input readonly" value="" readonly>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">*</span>项目负责人</label>
                    <div class="layui-input-block">
                        <select type="text" id="dutyUserList" name="dutyUserList" class="layui-input" lay-verify="required" lay-filter="dutyUserList" value=""></select>
                        <input type="hidden" id="dutyUserId" name="dutyUserId" class="layui-input" lay-verify="required" value="">
                        <input type="hidden" id="dutyUser" name="dutyUser" class="layui-input" lay-verify="required" value="">

                    </div>
                </div>
            </div>
            <div class="layui-col-xs6">
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">*</span>文件类型</label>
                    <div class="layui-input-block">
                        <input type="text" id="fileTypeDrop" name="fileTypeDrop" class="layui-input"
                               lay-verify="required" readonly>
                        <input type="hidden" id="fileType" name="fileType" lay-verify="required" value="">
                    </div>
                </div>
                <div class="layui-form-item proMonBlock">
                    <label class="layui-form-label"><span class="col-f00">*</span>项目金额</label>
                    <div class="layui-input-block">
                        <input type="text" id="projectMoney" name="projectMoney" class="layui-input readonly" readonly lay-verify="required">
                    </div>
                    <div class="action-block">
                        <div class="inner-box unit-box">
                            <span class="unit">万元</span>
                        </div>
                        <div class="inner-box action-box">
                            <i class="larry-icon fillMoneyBtn">&#xe939;</i>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">*</span>建设地点中心坐标N</label>
                    <div class="layui-input-block">
                        <input type="text" id="coordNorth" name="coordNorth" class="layui-input readonly" value="" readonly>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">*</span>建设地点线性坐标起点N</label>
                    <div class="layui-input-block">
                        <input type="text" id="coordStartNorth" name="coordStartNorth" class="layui-input readonly" value="" readonly>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">*</span>建设地点线性坐标终点N</label>
                    <div class="layui-input-block">
                        <input type="text" id="coordEndNorth" name="coordEndNorth" class="layui-input readonly" value="" readonly>
                    </div>
                </div>
            </div>
        </div>
        <div class="layui-row mt15 dynInputs">
            <div class="layui-col-xs6"></div>
            <div class="layui-col-xs6"></div>
        </div>

        <!--项目金额详情-->
        <input type="hidden" name="projectComfee" id="projectComfee" value="">
        <input type="hidden" name="groundwaterFee" id="groundwaterFee" value="">
        <input type="hidden" name="otherFee" id="otherFee" value="">
        <input type="hidden" name="environmentalFee" id="environmentalFee" value="">
        <input type="hidden" name="expertFee" id="expertFee" value="">
        <input type="hidden" name="specialFee" id="specialFee" value="">
        <input type="hidden" name="detectFee" id="detectFee" value="">
        <input type="hidden" name="preIssCertFee" id="preIssCertFee" value="">
        <input type="hidden" name="preSurvCertFee" id="preSurvCertFee" value="">
        <input type="hidden" name="certServeFee" id="certServeFee" value="">
        <input type="hidden" name="eiaTaskId" id="eiaTaskId" value="">
    </form>
</div>
</body>
</html>