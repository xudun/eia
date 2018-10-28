<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>项目信息</title>
    <meta name="layout" content="main"/>
    <asset:javascript src="/eiaProjectExplore/eiaProjectExplorePrint.js"/>
    <asset:stylesheet src="/eiaProject/eiaProjectCreate.css"/>
    <style type="text/css">
    table tr td{
        padding: 5px;
        border: 1px solid #383838;
        vertical-align: middle;
        line-height: 1.3em;
    }
    table tr td.tag{text-align: left;}
    </style>
</head>

<body>
<div id="print" class="layui-fluid larry-wrapper pt15 pb68">
    <div class="layer-title">
        <fieldset class="layui-elem-field layui-field-title site-title">
            <legend id="title">
                <a name="methodRender" class="pageTitle">环评项目初审单</a>
            </legend>
        </fieldset>
    </div>

    <blockquote class="layui-elem-quote larry-btn fixed-footer">
        <div class="layui-inline pl12">
            <div class="layui-btn-group top-group">
                <a class="layui-btn pl12" data-type="print"><i class="larry-icon">&#xe89a;</i> 打印</a>
            </div>
        </div>
    </blockquote>

</div>
<div class="margin0 w710">
    <h1 class="font20 margin0 mb10 a" style="text-align: center"></h1>
    <h1 class="font20 margin0 w200">环评项目初审单</h1>
    <table class="w100per font16 mb15 mt15">
        <tr>
            <td width="10%" rowspan="5" class="tag">项目基本情况</td>
            <td width="17%">项目名称</td>
            <td width="28%" id="projectName"></td>
            <td width="17%">地理位置</td>
            <td width="28%" id="buildArea"></td>

        </tr>
        <tr>
            <td width="17%">项目建设性质</td>
            <td width="28%" id="buildProp"></td>
            <td width="17%">工艺</td>
            <td width="28%" id="process"></td>
        </tr>
        <tr>
            <td width="17%">是否立项</td>
            <td width="28%" id="ifSet"></td>
            <td width="17%"  class="setInfo" >立项情况说明</td>
            <td width="28%" class="setInfo" id="setInfo"></td>
            <td width="17%" class="approvalType">审核类型</td>
            <td width="28%" class="approvalType" id="approvalType"></td>
        </tr>
        <tr>
            <td width="17%">改扩建企业存在问题</td>
            <td width="28%" id="existProblem"></td>
            <td width="17%">改扩建企业存在问题说明</td>
            <td width="28%" id="existProblemInfo"></td>
        </tr>
        <tr>
            <td width="17%">建设内容规模</td>
            <td width="73%" colspan="3" id="buildContent"></td>
        </tr>

        <tr>
            <td width="10%" rowspan="1" class="tag">污染物排放</td>
            <td width="17%">有无排水去向</td>
            <td width="28%" id="hasWaterWhere"></td>
            <td width="17%">有无总量</td>
            <td width="28%" id="hasTotal"></td>
        </tr>

        <tr>
            <td width="10%" rowspan="1" class="tag">产业政策</td>
            <td width="17%">产业结构指导目录</td>
            <td width="28%" id="industrialDir"></td>
            <td width="17%">外商投资指导目录</td>
            <td width="28%" id="foreignDir"></td>
        </tr>

        <tr>
            <td width="10%" rowspan="3" class="tag">厂址选择</td>
            <td width="17%">是否在园区</td>
            <td width="28%" id="inPark"></td>
            <td width="17%">园区产业定位布局要求</td>
            <td width="28%" id="parkLayout"></td>
        </tr>
        <tr>
            <td width="17%">卫生防护距离</td>
            <td width="28%" id="sanitaryDistance"></td>
            <td width="17%">涉及敏感区</td>
            <td width="28%" id="involveReserve"></td>
        </tr>
        <tr>
            <td width="17%">其他敏感目标</td>
            <td width="73%" colspan="3" id="otherSense"></td>
        </tr>

        <tr>
            <td width="10%" rowspan="2" class="tag">资质类别</td>
            <td width="17%">文件类型</td>
            <td width="28%" id="fileType"></td>
            <td width="17%">地下水评价等级</td>
            <td width="28%" id="groundWater"></td>
        </tr>
        <tr>
            <td width="17%">环境影响评价行业类别</td>
            <td width="73%" colspan="4" id="environmentaType"></td>
        </tr>
        <tr>
            <td width="10%" rowspan="1" class="tag">其他</td>
            <td width="17%">其他问题</td>
            <td width="73%" colspan="4" id="otherIssue"></td>
        </tr>
        <tr>
            <td width="10%" rowspan="1" class="tag">签字</td>
            <td width="17%">助理</td>
            <td width="28%" id="zlnodei"></td>
            <td width="17%">部门经理</td>
            <td width="28%" id="jlnodei"></td>
        </tr>
    </table>

</div>


</body>
</html>