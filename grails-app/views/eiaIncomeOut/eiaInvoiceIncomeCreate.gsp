
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>新增进账信息</title>
    <meta name="layout" content="main"/>
    <asset:javascript src="/eiaIncomeOut/eiaInvoiceIncomeCreate.js"/>
</head>

<body>
<div class="layui-fluid larry-wrapper pt0">
    <div class="layer-title">
        <fieldset class="layui-elem-field layui-field-title site-title">
            <legend id="title">
                <a name="methodRender" class="pageTitle">新增进账</a>
            </legend>
        </fieldset>
    </div>

    <form class="layui-form offerInfo">
        <g:hiddenField name="contractId" id="contractId" value="${contractId}"/>
        <blockquote class="layui-elem-quote larry-btn">
            进账信息
            <div class="layui-inline pl12">
                <div class="layui-btn-group top-group">
                    <button class="layui-btn" lay-submit="" lay-filter="save"><i class="larry-icon">&#xe9d1;</i> 保存</button>
                    <button type="reset" class="layui-btn layui-btn-primary resetBtn"><i class="larry-icon">&#xe69a;</i> 重置</button>
                </div>
            </div>
        </blockquote>
        <div class="layui-row mt15">
            <div class="layui-col-xs6">
                <div class="layui-form-item">
                    <label class="layui-form-label">客户名称</label>
                    <div class="layui-input-block check-block">   ${eiaClient?.clientName}</div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">合同名称</label>
                    <div class="layui-input-block check-block">   ${eiaContract?.contractName}</div>
                </div>
                <div class="layui-form-item has-tag">
                    <label class="layui-form-label"><span class="col-f00">* </span>进账金额(万元)</label>
                    <div class="layui-input-block">
                        <input type="number" id="noteIncomeMoney" step='0.0001' name="noteIncomeMoney" class="layui-input" lay-verify="required">
                    </div>
                    <div class="tag-right"><span class="tag-unit">万元</span></div>
                </div>

            </div>
            <div class="layui-col-xs6">
                <div class="layui-form-item">
                    <label class="layui-form-label">合同编号</label>
                    <div class="layui-input-block check-block">
                        ${eiaContract?.contractNo}
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">合同金额(万元)</label>
                    <div class="layui-input-block check-block">
                        ${eiaContract?.contractMoney}
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">* </span>进账时间</label>
                    <div class="layui-input-block">
                        <input type="text" id="noteIncomeDateS" name="noteIncomeDateS" class="layui-input" lay-verify="required|date" placeholder="年-月-日" autocomplete="off" value="">
                    </div>
                </div>
            </div>
        </div>

    </form>
</div>

</body>
</html>
