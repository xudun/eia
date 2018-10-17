<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="layout" content="main"/>
    <title></title>
    <asset:javascript src="/eiaCert/eiaCertStamp.js"/>
    <style type="text/css">
        .check-block{
            margin-left: 115px;
        }
    </style>
</head>
<body>
<div class="layui-fluid larry-wrapper pt15">
    <div class="layer-title">
        <fieldset class="layui-elem-field layui-field-title site-title">
            <legend id="title">
                <a name="methodRender" class="pageTitle">印章使用申请表</a>
            </legend>
        </fieldset>
    </div>
    <form class="layui-form">
        <blockquote class="layui-elem-quote larry-btn mb15">
            <div class="layui-inline pl12">
                <div class="layui-btn-group top-group">
                    <a class="layui-btn" lay-submit="" id="save" data-type="save"><i class="larry-icon">&#xe9d1;</i> 保存</a>
                    <a class="layui-btn display-none" data-type="print" id="print"><i class="larry-icon">&#xe850;</i> 打印</a>
                    %{--<button type="reset" class="layui-btn layui-btn-primary resetBtn"><i class="larry-icon">&#xe69a;</i> 重置</button>--}%
                </div>
            </div>
        </blockquote>
        <div class="layui-form-item">
            <label class="layui-form-label">用印名称</label>
            <div class="layui-input-block">
                <input type="checkbox" name="like[LHCD]" value="LHCD" title="联合赤道">
                <input type="checkbox" name="like[LHTZ]" value="LHTZ" title="联合泰泽">
                <input type="checkbox" name="like[FR]" value="FR" title="法人章">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">项目名称</label>
            <div class="layui-input-block check-block" id="projectName">
            </div>
        </div>
        <div class="layui-row">
            <div class="layui-col-xs6">
                <div class="layui-form-item">
                    <label class="layui-form-label">文件类型</label>
                    <div class="layui-input-block check-block" id="fileTypeChild">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">合同号</label>
                    <div class="layui-input-block check-block" id="contractNo">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">合同额</label>
                    <div class="layui-input-block check-block" id="contractMoney">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">其它</label>
                    <div class="layui-input-block check-block" id="otherFee">
                    </div>
                </div>
            </div>
            <div class="layui-col-xs6">
                <div class="layui-form-item">
                    <label class="layui-form-label">签订合同</label>
                    <div class="layui-input-block check-block" id="ifSign">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">责任运行卡</label>
                    <div class="layui-input-block check-block" id="dutyCard">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">监测费</label>
                    <div class="layui-input-block check-block" id="enviroMonitoringFee">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">已到账</label>
                    <div class="layui-input-block check-block">
                        <span id="income"></span>万元（到账比例：<span id="proportion"></span>）
                    </div>
                </div>
            </div>
        </div>
        <div class="layui-form-item layui-form-text">
            <label class="layui-form-label">特殊说明</label>
            <div class="layui-input-block">
                <textarea id="stampNote" name="stampNote" placeholder="请输入内容" class="layui-textarea"></textarea>
            </div>
        </div>
    </form>
</div>
</body>
</html>