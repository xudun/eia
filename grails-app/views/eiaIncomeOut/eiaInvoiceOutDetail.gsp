
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>出账信息详情</title>
    <meta name="layout" content="main"/>
    <asset:javascript src="/eiaIncomeOut/eiaInvoiceOutDetail.js"/>
</head>

<body>
<div class="layui-fluid larry-wrapper pt0">
    <div class="layer-title">
        <fieldset class="layui-elem-field layui-field-title site-title">
            <legend id="title">
                <a name="methodRender" class="pageTitle">出账信息详情</a>
            </legend>
        </fieldset>
    </div>


    <form class="layui-form offerInfo">
        <div class="layui-row mt15">
            <div class="layui-col-xs6">
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>合同编号</label>
                    <div class="layui-input-block check-block" id="contractNo"></div>
                </div>

                <div class="layui-form-item has-tag">
                    <label class="layui-form-label"><span class="col-f00"></span>当前状态</label>
                    <div class="layui-input-block check-block" id="accountState"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>费用类型</label>
                    <div class="layui-input-block check-block" id="costTypes"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>有无票据</label>
                    <div class="layui-input-block check-block" id="thereNoBill"></div>
                </div>
            </div>
            <div class="layui-col-xs6">
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>合同名称</label>
                    <div class="layui-input-block check-block" id="contractName"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>出账金额(万元)</label>
                    <div class="layui-input-block check-block" id="noteIncomeMoney"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>出账时间</label>
                    <div class="layui-input-block check-block" id="noteIncomeDate"></div>
                </div>
            </div>
        </div>

    </form>
</div>
</body>
</html>