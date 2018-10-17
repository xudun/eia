
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>预计信息详情</title>
    <meta name="layout" content="main"/>
    <asset:javascript src="/eiaAccountExpect/eiaExpectDetail.js"/>
</head>

<body>
<div class="layui-fluid larry-wrapper pt0">
    <div class="layer-title">
        <fieldset class="layui-elem-field layui-field-title site-title">
            <legend id="title">
                <a name="methodRender" class="pageTitle">预计信息详情</a>
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
                    <label class="layui-form-label"><span class="col-f00"></span>预计开票金额(万元)</label>
                    <div class="layui-input-block check-block" id="expectInvoiceMoney"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>预计收款金额(万元)</label>
                    <div class="layui-input-block check-block" id="expectIncomeMoney"></div>
                </div>
            </div>
            <div class="layui-col-xs6">
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>合同名称</label>
                    <div class="layui-input-block check-block" id="contractName"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>预计期次</label>
                    <div class="layui-input-block check-block" id="expectPeriod"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>专家费(万元)</label>
                    <div class="layui-input-block check-block" id="expertFee"></div>
                </div>

            </div>
        </div>

    </form>
</div>
</body>
</html>