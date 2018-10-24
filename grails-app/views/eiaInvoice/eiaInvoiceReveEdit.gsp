
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>编辑开票信息</title>
    <meta name="layout" content="main"/>
    <asset:javascript src="/eiaInvoice/eiaInvoiceReveEdit.js"/>
    <style>
    #billOtherType{display:inline-block;}
    .font-title{line-height: 38px;font-size: 18px;}
    .font-title .layui-form-radio{vertical-align: top;margin-right: 0;}
    </style>
</head>

<body>
<div class="layui-fluid larry-wrapper pt0">
    <div class="layer-title">
        <fieldset class="layui-elem-field layui-field-title site-title">
            <legend id="title">
                <a name="methodRender" class="pageTitle">编辑开票</a>
            </legend>
        </fieldset>
    </div>

    <form class="layui-form offerInfo">
        <g:hiddenField name="eiaInvoiceId" id="eiaInvoiceId" value="${eiaInvoice?.id}"/>
        <blockquote class="layui-elem-quote larry-btn">
            开票信息
            <div class="layui-inline pl12">
                <div class="layui-btn-group top-group">
                    <button class="layui-btn" lay-submit="" lay-filter="save"><i class="larry-icon">&#xe9d1;</i> 保存</button>
                </div>
            </div>
        </blockquote>
        <div align="center">
            <div class="layui-form-item mt15">
                <font class="font-title">联合赤道环境评价有限公司发票申请单
                （<g:radio id="cd" name="billOrg" value="联合赤道" />联合赤道&nbsp;&nbsp;&nbsp;
                <g:radio id="tz" name="billOrg" value="联合泰泽" checked="true" />联合泰泽）
                </font>
            </div>
        </div>
        <div class="layui-row mt15">
            <div class="layui-col-xs6">
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>项目负责人</label>
                    <div class="layui-input-block check-block"> ${contract?.inputUser}</div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">开票申请人</label>
                    <div class="layui-input-block check-block"> ${contract?.inputUser}</div>
                    <input type="hidden" id="billingApplicant" name="billingApplicant" value= "${contract?.inputUser}" >

                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">* </span>发票类别</label>
                    <div class="layui-input-block">
                        <input type="radio" name="invoiceType"  lay-filter="invoiceType" id="zyfp" value="增值税专用发票" title="增值税专用发票 ">
                        <input type="radio" name="invoiceType" lay-filter="invoiceType"  id="ptfp" value="增值税普通发票" title="增值税普通发票 ">
                    </div>
                </div>
                <div class="layui-form-item has-tag">
                    <label class="layui-form-label"><span class="col-f00">* </span>开票单位名称</label>
                    <div class="layui-input-block">
                        <input type="text" id="clientName" name="clientName" class="layui-input readonly" value="${eiaInvoice?.clientName}"  lay-verify="required" readonly >
                    </div>
                    <div class="tag-right"><i class="larry-icon selectUserBtn">&#xe85b;</i></div>
                </div>
                <div class="layui-form-item verify-item" >
                    <label class="layui-form-label"><span class="col-f00">* </span>税务登记代码</label>
                    <div class="layui-input-block">
                        <input type="text" id="taxRegCode" name="taxRegCode" class="layui-input readonly" value="${eiaInvoice?.taxRegCode}" lay-verify="required" readonly>
                    </div>
                </div>
                <div class="layui-form-item verify-item">
                    <label class="layui-form-label"><span class="col-f00">* </span>开户行户名</label>
                    <div class="layui-input-block">
                        <input type="text" id="bankName" name="bankName" class="layui-input readonly" lay-verify="required" value="${eiaInvoice?.bankName}" readonly>
                    </div>
                </div>
                <div class="layui-form-item verify-item">
                    <label class="layui-form-label"><span class="col-f00">* </span>开户行账号</label>
                    <div class="layui-input-block">
                        <input type="text" id="bankAccount" name="bankAccount" class="layui-input readonly" lay-verify="required" value="${eiaInvoice?.bankAccount}" readonly>
                    </div>
                </div>
            </div>
            <div class="layui-col-xs6">
                <div class="layui-form-item has-tag">
                    <label class="layui-form-label"><span class="col-f00">* </span>开票金额</label>
                    <div class="layui-input-block">
                        <input type="number" id="billMoney" step='0.0001' name="billMoney" class="layui-input" lay-verify="required"  value="${eiaInvoice?.billMoney}">
                    </div>
                    <div class="tag-right"><span class="tag-unit">万元</span></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">* </span>预计收款日期</label>
                    <div class="layui-input-block">
                        <input type="text" id="estDateS" name="estDateS" class="layui-input" lay-verify="required|date" placeholder="年-月-日" autocomplete="off" value="${eiaInvoice?.estDate?.format("yyyy-MM-dd")}">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">* </span>申请开票时间</label>
                    <div class="layui-input-block">
                        <input type="text" id="billDateS" name="billDateS" class="layui-input" lay-verify="required|date" placeholder="年-月-日" autocomplete="off"value="${eiaInvoice?.billDate?.format("yyyy-MM-dd")}">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">* </span>实际收款情况</label>
                    <div class="layui-input-block">
                        <input type="radio" name="realMoney" value="已进账" id="yjz" title="已进账"checked="true">
                        <input type="radio" name="realMoney" value="未进账" id="wjz" title="未进账" checked>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">* </span>地址及电话</label>
                    <div class="layui-input-block">
                        <input type="text" id="addrTel" name="addrTel" class="layui-input readonly" lay-verify="required" value="${eiaInvoice?.addrTel}" readonly>
                    </div>
                </div>
                <div class="layui-form-item has-tag">
                    <label class="layui-form-label"><span class="col-f00">* </span>开票类别</label>
                    <div class="layui-input-block">
                        <input type="radio" name="billType" value="咨询费" id="zxf" title="咨询费">
                        <input type="radio" name="billType" value="其他" id="qt" title=" 其他" checked>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">发票备注</label>
                    <div class="layui-input-block">
                        <input type="text" id="memo" name="memo" class="layui-input" value="${eiaInvoice?.memo}">
                    </div>
                </div>
            </div>
        </div>

    </form>
</div>
<!--报价id-->
</body>
</html>
