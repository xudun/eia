<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>项目信息</title>
    <meta name="layout" content="main"/>
    <asset:javascript src="/eiaProjectExplore/eiaProjectExploreCreate.js"/>
    <asset:stylesheet src="/eiaProject/eiaProjectCreate.css"/>
    <asset:stylesheet src="/eiaProject/eiaProjectExploreCreate.css"/>
    <style>

    </style>
</head>

<body>
<div class="layui-fluid larry-wrapper pt0 pb68">
    <form class="layui-form proCreateExp">
        <input type="hidden" id="eiaProjectExploreId" name="eiaProjectExploreId" value="">

        <blockquote class="layui-elem-quote larry-btn fixed-footer">
            <div class="layui-inline pl12">
                <div class="layui-btn-group top-group">
                    <button class="layui-btn saveBtn pl12" lay-submit="" lay-filter="save"><i class="larry-icon">&#xe9d1;</i> 保存</button>
                    <button type="reset" class="layui-btn layui-btn-primary resetBtn pl12"><i class="larry-icon">&#xe69a;</i> 重置</button>
                </div>
            </div>
        </blockquote>

        <fieldset class="layui-elem-field layui-field-title site-title">
            <legend >
                <a name="methodRender" class="pageTitle">项目基本情况</a>
            </legend>
        </fieldset>
        <div class="layui-row mt15">
            <div class="layui-col-xs6">
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">*</span>项目名称</label>
                    <div class="layui-input-block">
                        <input type="text" id="projectName" name="projectName" class="layui-input" lay-verify="required"
                               value="">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">*</span>项目建设性质</label>
                    <div class="layui-input-block">
                        <select name="buildProp" id="buildProp" lay-filter="buildProp" lay-verify="required" lay-search>
                            <option value="">请选择</option>
                        </select>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">*</span>是否立项</label>

                    <div class="layui-input-block">
                        <select name="ifSet" id="ifSet" lay-filter="ifSet" lay-verify="required" lay-search>
                            <option value="">请选择</option>
                        </select>
                    </div>
                </div>


                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">*</span>改扩建企业存在问题</label>

                    <div class="layui-input-block existProblemGroup">
                        <input type="hidden" id="existProblemCode" name="existProblemCode" value=""
                               lay-verify="required">
                        <input type="hidden" id="existProblem" name="existProblem" value="">
                    </div>
                </div>

                <div class="layui-form-item display-none" id="existProblemQT">
                    <label class="layui-form-label"><span class="col-f00">*</span>改扩建企业存在问题说明</label>
                    <div class="layui-input-block">
                        <textarea name="existProblemInfo" id="existProblemInfo" placeholder="请输入内容" class="layui-textarea"></textarea>
                    </div>
                </div>

            </div>

            <div class="layui-col-xs6">
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">*</span>建设内容规模</label>
                    <div class="layui-input-block">
                        <input type="text" id="buildContent" name="buildContent" class="layui-input"
                               lay-verify="required" value="">
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">*</span>工艺</label>

                    <div class="layui-input-block">
                        <input type="text" id="process" name="process" class="layui-input" lay-verify="required"
                             placeholder="(一到五个关键字,用逗号隔开)"  value="">
                    </div>
                </div>

                <div class="layui-form-item proMonBlock">
                    <label class="layui-form-label"><span class="col-f00"></span>地理位置</label>

                    <div class="layui-input-block ">
                        <input type="text" id="buildArea" name="buildArea" class="layui-input" value="">
                        <input type="hidden" id="geoJson" name="geoJson" class="layui-input" value="">
                    </div>

                    <div class="action-block">
                        <div class="inner-box action-box">
                            <i class="larry-icon mapDrawBtn">&#xe9b3;</i>
                        </div>
                    </div>
                </div>

                <div class="layui-form-item display-none" id="ifSetYES">
                    <label class="layui-form-label"><span class="col-f00">*</span>审批类型</label>

                    <div class="layui-input-block">
                        <select name="approvalType" id="approvalType" lay-filter="approvalType" lay-verify="required"
                                lay-search>
                            <option value="">请选择</option>
                        </select>
                    </div>
                </div>
                <div class="layui-form-item display-none" id="ifSetNO">
                    <label class="layui-form-label"><span class="col-f00">*</span>立项情况说明</label>
                    <div class="layui-input-block">
                        <textarea name="setInfo" id="setInfo" placeholder="请输入内容" class="layui-textarea"></textarea>
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

                    <div class="layui-input-block">
                        <select name="hasWaterWhere" id="hasWaterWhere" lay-filter="hasWaterWhere" lay-verify="required"
                                lay-search>
                            <option value="">请选择</option>
                        </select>
                    </div>
                </div>
            </div>
            <div class="layui-col-xs6">
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">*</span>有无总量</label>

                    <div class="layui-input-block">
                        <select name="hasTotal" id="hasTotal" lay-filter="hasTotal" lay-verify="required"
                                lay-search>
                            <option value="">请选择</option>
                        </select>
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
                    <div class="layui-input-block">
                        <select name="industrialDir" id="industrialDir" lay-filter="industrialDir" lay-verify="required"
                                lay-search>
                            <option value="">请选择</option>
                        </select>
                    </div>
                </div>
            </div>
            <div class="layui-col-xs6">
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">*</span>外商投资指导目录</label>
                    <div class="layui-input-block">
                        <select name="foreignDir" id="foreignDir" lay-filter="foreignDir" lay-verify="required"
                                lay-search>
                            <option value="">请选择</option>
                        </select>
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

                <div class="layui-input-block">
                    <select name="inPark" id="inPark" lay-filter="inPark" lay-verify="required"
                            lay-search>
                        <option value="">请选择</option>
                    </select>
                </div>
            </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">*</span>卫生防护距离</label>
                    <div class="layui-input-block">
                        <select name="sanitaryDistance" id="sanitaryDistance" lay-filter="sanitaryDistance" lay-verify="required"
                                lay-search>
                            <option value="">请选择</option>
                        </select>
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">*</span>涉及敏感区</label>

                    <div class="layui-input-block checkboxGroup">
                        <input type="hidden" id="involveReserveCode" name="involveReserveCode" value=""
                               lay-verify="required">
                        <input type="hidden" id="involveReserve" name="involveReserve" value="">
                    </div>
                </div>
            </div>
            <div class="layui-col-xs6">
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">*</span>园区产业定位布局要求</label>

                    <div class="layui-input-block">
                        <select name="parkLayout" id="parkLayout" lay-filter="parkLayout" lay-verify="required"
                                lay-search>
                            <option value="">请选择</option>
                        </select>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">*</span>其他敏感目标</label>

                    <div class="layui-input-block checkboxGroup">
                        <input type="hidden" id="otherSenseCode" name="otherSenseCode" value=""
                               lay-verify="required">
                        <input type="hidden" id="otherSense" name="otherSense" value="">
                    </div>
                </div>
                <div class="layui-form-item display-none" id="otherSenseQT">
                    <label class="layui-form-label"><span class="col-f00">*</span>其他敏感目标说明</label>
                    <div class="layui-input-block">
                        <textarea name="otherSenseInfo" id="otherSenseInfo" placeholder="请输入内容" class="layui-textarea"></textarea>
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
                    <div class="layui-input-block">
                        <input type="text" id="fileTypeDrop" name="fileTypeDrop" class="layui-input"
                               lay-verify="required" readonly>
                        <input type="hidden" id="fileType" name="fileType" lay-verify="required" value="">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">* </span>环境影响评价行业类别</label>
                    <div class="layui-input-block">
                        <input type="text" id="environmentaTypeDrop" name="environmentaTypeDrop" class="layui-input" lay-verify="required" value="" readonly>
                        <input type="hidden" id="environmentaType" name="environmentaType" value="">
                    </div>
                </div>
            </div>
            <div class="layui-col-xs6">
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">*</span>地下水评价等级</label>
                    <div class="layui-input-block">
                        <select name="groundWater" id="groundWater" lay-filter="groundWater" lay-verify="required"
                                lay-search>
                            <option value="">请选择</option>
                        </select>
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

                    <div class="layui-input-block">
                        <textarea name="otherIssue" id="otherIssue" placeholder="例如:有没有投诉问题等" class="layui-textarea"></textarea>
                    </div>
                </div>
            </div>
        </div>

    </form>
</div>

%{--<script src="js/jquery/jquery-2.1.3.js"></script>
<script src="js/layuiFrame/common/frame/layui/layui.js"></script>
<script src="pagejs/eiaProjectInputLib.js"></script>
<script src="pagejs/eiaProjectCreate.js"></script>--}%
</body>
</html>