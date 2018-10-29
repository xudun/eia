<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>项目信息</title>
    <meta name="layout" content="main"/>
    <asset:javascript src="/eiaProjectExplore/eiaProjectExploreDetail.js"/>
    <asset:stylesheet src="/eiaProject/eiaProjectCreate.css"/>
    <style>
    .proCreateExp.layui-form .layui-form-label{width: 165px;}
    .proCreateExp .layui-input-block{margin-left: 195px;line-height: 38px;}
    </style>
</head>

<body>
<div class="layui-fluid larry-wrapper pt0">
    <form class="layui-form proCreateExp">
        <input type="hidden" id="eiaProjectExploreId" name="eiaProjectExploreId" value="">
        <fieldset class="layui-elem-field layui-field-title site-title">
            <legend >
                <a name="methodRender" class="pageTitle">项目基本情况</a>
            </legend>
        </fieldset>
        <div class="layui-row mt15">
            <div class="layui-col-xs6">
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">*</span>项目名称</label>
                    <div class="layui-input-block" id="projectName">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">*</span>项目建设性质</label>
                    <div class="layui-input-block" id="buildProp">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">*</span>是否立项</label>

                    <div class="layui-input-block" id="ifSet">
                    </div>
                </div>


                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">*</span>改扩建企业存在问题</label>

                    <div class="layui-input-block existProblemGroup" id="existProblem">

                    </div>
                </div>

                <div class="layui-form-item display-none" id="existProblemQT">
                    <label class="layui-form-label"><span class="col-f00">*</span>改扩建企业存在问题说明</label>
                    <div class="layui-input-block" id="existProblemInfo">
                    </div>
                </div>

            </div>

            <div class="layui-col-xs6">
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">*</span>建设内容规模</label>
                    <div class="layui-input-block" id="buildContent">
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">*</span>工艺</label>
                    <div class="layui-input-block" id="process">
                    </div>
                </div>



                <div class="layui-form-item display-none" id="ifSetYES">
                    <label class="layui-form-label"><span class="col-f00">*</span>立项审批类型</label>

                    <div class="layui-input-block" id="approvalType">
                    </div>
                </div>
                <div class="layui-form-item display-none" id="ifSetNO">
                    <label class="layui-form-label"><span class="col-f00">*</span>立项情况说明</label>
                    <div class="layui-input-block" id="setInfo">
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">*</span>地理位置</label>
                    <div class="layui-input-block " id="buildArea">
                    </div>
                </div>

            </div>
        </div>

        <fieldset class="layui-elem-field layui-field-title site-title">
            <legend >
                <a name="methodRender" class="pageTitle">污染物排放</a>
            </legend>
        </fieldset>

        <div class="layui-row mt15">
            <div class="layui-col-xs6">
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">*</span>有无排水去向</label>
                    <div class="layui-input-block" id="hasWaterWhere">
                    </div>
                </div>
            </div>
            <div class="layui-col-xs6">
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">*</span>有无总量</label>
                    <div class="layui-input-block" id="hasTotal">
                    </div>
                </div>
            </div>
        </div>

        <fieldset class="layui-elem-field layui-field-title site-title">
            <legend >
                <a name="methodRender" class="pageTitle">产业政策</a>
            </legend>
        </fieldset>

        <div class="layui-row mt15">
            <div class="layui-col-xs6">
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">*</span>产业结构指导目录</label>
                    <div class="layui-input-block" id="industrialDir">
                    </div>
                </div>
            </div>
            <div class="layui-col-xs6">
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">*</span>外商投资指导目录</label>
                    <div class="layui-input-block" id="foreignDir">
                    </div>
                </div>
            </div>
        </div>

        <fieldset class="layui-elem-field layui-field-title site-title">
            <legend >
                <a name="methodRender" class="pageTitle">厂址选择</a>
            </legend>
        </fieldset>

        <div class="layui-row mt15">
            <div class="layui-col-xs6">
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">*</span>是否在园区</label>

                    <div class="layui-input-block" id="inPark">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">*</span>卫生防护距离</label>
                    <div class="layui-input-block" id="sanitaryDistance">
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">*</span>涉及敏感区</label>

                    <div class="layui-input-block checkboxGroup" id="involveReserve">
                    </div>
                </div>
            </div>
            <div class="layui-col-xs6">
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">*</span>园区产业定位布局要求</label>
                    <div class="layui-input-block" id="parkLayout">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">*</span>其他敏感目标</label>

                    <div class="layui-input-block checkboxGroup" id="otherSense">
                    </div>
                </div>
                <div class="layui-form-item display-none" id="otherSenseQT">
                    <label class="layui-form-label"><span class="col-f00">*</span>其他敏感目标说明</label>
                    <div class="layui-input-block" id="otherSenseInfo">
                    </div>
                </div>
            </div>
        </div>
        <fieldset class="layui-elem-field layui-field-title site-title">
            <legend >
                <a name="methodRender" class="pageTitle">资质类别</a>
            </legend>
        </fieldset>

        <div class="layui-row mt15">
            <div class="layui-col-xs6">
                <div class="layui-form-item">
                <label class="layui-form-label"><span class="col-f00">*</span>文件类型</label>
                <div class="layui-input-block"  id="fileType">
                </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">*</span>地下水评价等级</label>
                    <div class="layui-input-block" id="groundWater">
                    </div>
                </div>
            </div>
            <div class="layui-col-xs6">
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">* </span>环境影响评价行业类别</label>
                    <div class="layui-input-block" id="environmentaType">
                    </div>
                </div>

            </div>
        </div>

        <fieldset class="layui-elem-field layui-field-title site-title">
            <legend >
                <a name="methodRender" class="pageTitle">其他</a>
            </legend>
        </fieldset>

        <div class="layui-row mt15">
            <div class="layui-col-xs12">
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">*</span>其他问题</label>
                    <div class="layui-input-block" id="otherIssue">
                    </div>
                </div>
            </div>
        </div>

    </form>
</div>

</body>
</html>