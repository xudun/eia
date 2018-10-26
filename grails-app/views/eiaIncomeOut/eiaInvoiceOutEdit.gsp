
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>编辑出账信息</title>
    <meta name="layout" content="main"/>
    <asset:javascript src="/eiaIncomeOut/eiaInvoiceOutEdit.js"/>
</head>

<body class="pb68">
<div class="layui-fluid larry-wrapper pt0">
    <div class="layer-title">
        <fieldset class="layui-elem-field layui-field-title site-title">
            <legend id="title">
                <a name="methodRender" class="pageTitle">编辑出账</a>
            </legend>
        </fieldset>
    </div>

    <form class="layui-form offerInfo">

        <blockquote class="layui-elem-quote larry-btn fixed-footer">
            <div class="layui-inline pl12">
                <div class="layui-btn-group top-group">
                    <button class="layui-btn pl12" lay-submit="" lay-filter="save"><i class="larry-icon">&#xe9d1;</i> 保存</button>
                </div>
            </div>
        </blockquote>

        <div class="layui-row mt15">
            <div class="layui-col-xs6">
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">* </span>费用类型</label>
                    <div class="layui-input-block">
                        <input type="text" id="costTypes" name="costTypes" value="其他" class="layui-input readonly" readonly >
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">* </span>有无票据</label>
                    <div class="layui-input-block">
                        <input type="text" id="thereNoBill" name="thereNoBill" value="无" class="layui-input readonly" readonly >
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">备注</label>
                    <div class="layui-input-block">
                        <input type="text" id="memo" name="memo" value="${eiaIncomeOut?.memo}"  class="layui-input" >
                    </div>
                </div>
            </div>
            <div class="layui-col-xs6">
                <div class="layui-form-item has-tag">
                    <label class="layui-form-label"><span class="col-f00">* </span>金额(万元)</label>
                    <div class="layui-input-block">
                        <input type="number" id="noteIncomeMoney" step='0.0001' name="noteIncomeMoney" value="${eiaIncomeOut?.noteIncomeMoney}" class="layui-input" lay-verify="required">
                    </div>
                    <div class="tag-right"><span class="tag-unit">万元</span></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">* </span>出账时间</label>
                    <div class="layui-input-block">
                        <input type="text" id="noteIncomeDateS" name="noteIncomeDateS" class="layui-input" lay-verify="required|date" placeholder="年-月-日" autocomplete="off" value="${eiaIncomeOut?.noteIncomeDate?.format("yyyy-MM-dd")}">
                    </div>
                </div>
            </div>
        </div>

    </form>
</div>

</body>
</html>
