
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>开票信息详情</title>
    <meta name="layout" content="main"/>
    <asset:javascript src="/eiaInvoice/eiaInvoiceReveDetail.js"/>
</head>

<body>
<div class="layui-fluid larry-wrapper pt0">
    <div class="layer-title">
        <fieldset class="layui-elem-field layui-field-title site-title">
            <legend id="title">
                <a name="methodRender" class="pageTitle">开票信息详情</a>
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
                    <label class="layui-form-label"><span class="col-f00"></span>合同金额(万元)</label>
                    <div class="layui-input-block check-block" id="contractMoney"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>开票金额(万元)</label>
                    <div class="layui-input-block check-block" id="billMoney"></div>
                </div>
                <div class="layui-form-item has-tag">
                    <label class="layui-form-label"><span class="col-f00"></span>申请开票时间</label>
                    <div class="layui-input-block check-block" id="billDate"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>预计收款日期</label>
                    <div class="layui-input-block check-block" id="estDate"></div>
                </div>
                <div class="layui-form-item has-tag">
                    <label class="layui-form-label">开票单位名称</label>
                    <div class="layui-input-block check-block" id="clientAccountName"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>开户行</label>
                    <div class="layui-input-block check-block" id="bankName"></div>
                </div>
                <div class="layui-form-item has-tag">
                    <label class="layui-form-label">税务登记代码</label>
                    <div class="layui-input-block check-block" id="taxRegCode"></div>
                </div>
            </div>
            <div class="layui-col-xs6">
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>合同名称</label>
                    <div class="layui-input-block check-block" id="contractName"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>发票申请机构</label>
                    <div class="layui-input-block check-block" id="billOrg"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>开票类别</label>
                    <div class="layui-input-block check-block" >
                        <span id="billType"></span>&nbsp;&nbsp;&nbsp;
                    </div>
                </div>
                <div class="layui-form-item" >
                    <label class="layui-form-label"><span class="col-f00"></span>发票类别</label>
                    <div class="layui-input-block check-block" id="invoiceType"></div>
                </div>
                <div class="layui-form-item has-tag">
                    <label class="layui-form-label"><span class="col-f00"></span>实际收款情况</label>
                    <div class="layui-input-block check-block" id="realMoney"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>开户行账号</label>
                    <div class="layui-input-block check-block" id="bankAccount"></div>
                </div>

                <div class="layui-form-item has-tag">
                    <label class="layui-form-label"><span class="col-f00"></span>地址及电话</label>
                    <div class="layui-input-block check-block" id="addrTel"></div>
                </div>
                <div class="layui-form-item has-tag">
                    <label class="layui-form-label"><span class="col-f00"></span>发票备注</label>
                    <div class="layui-input-block check-block" id="memo"></div>
                </div>
            </div>
        </div>

    </form>
</div>
</body>
</html>