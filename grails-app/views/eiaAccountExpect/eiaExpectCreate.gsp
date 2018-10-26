
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>预计信息</title>
    <meta name="layout" content="main"/>
    <asset:javascript src="/eiaAccountExpect/eiaExpectCreate.js"/>
</head>

<body class="pb68">
<div class="layui-fluid larry-wrapper pt0">
    <div class="layer-title">
        <fieldset class="layui-elem-field layui-field-title site-title">
            <legend id="title">
                <a name="methodRender" class="pageTitle">财务预收预付款费</a>
            </legend>
        </fieldset>
    </div>

    <form class="layui-form offerInfo">
        <g:hiddenField name="contractId" id="contractId" value=""/>

        <blockquote class="layui-elem-quote larry-btn fixed-footer">
            <div class="layui-inline pl12">
                <div class="layui-btn-group top-group">
                    <button class="layui-btn pl12" lay-submit="" lay-filter="save"><i class="larry-icon">&#xe9d1;</i> 保存</button>
                    <button type="reset" class="layui-btn layui-btn-primary resetBtn pl12"><i class="larry-icon">&#xe69a;</i> 重置</button>
                </div>
            </div>
        </blockquote>

        <div class="layui-row mt15">
            <div class="layui-col-xs6">
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">* </span>期次</label>
                    <div class="layui-input-block">
                        <input type="text" id="expectPeriod" name="expectPeriod" class="layui-input" lay-verify="required|date"  autocomplete="off" value="">
                    </div>
                </div>
                <div class="layui-form-item has-tag">
                    <label class="layui-form-label"><span class="col-f00">* </span>预计收款金额</label>
                    <div class="layui-input-block">
                        <input type="number" id="expectIncomeMoney" step='0.0001' name="expectIncomeMoney" class="layui-input" lay-verify="required">
                    </div>
                    <div class="tag-right"><span class="tag-unit">万元</span></div>
                </div>
                <div class="layui-form-item has-tag">
                    <label class="layui-form-label"><span class="col-f00">* </span>专家费</label>
                    <div class="layui-input-block">
                        <input type="number" id="expertFee" step='0.0001' name="expertFee" class="layui-input" lay-verify="required">
                    </div>
                    <div class="tag-right"><span class="tag-unit">万元</span></div>
                </div>
            </div>
            <div class="layui-col-xs6">
                <div class="layui-form-item  has-tag">
                    <label class="layui-form-label"><span class="col-f00">* </span>预计开票金额</label>
                    <div class="layui-input-block">
                        <input type="text" id="expectInvoiceMoney" name="expectInvoiceMoney" value="" class="layui-input"  lay-verify="required">
                    </div>
                    <div class="tag-right"><span class="tag-unit">万元</span></div>
                </div>
                <div class="layui-form-item has-tag">
                    <label class="layui-form-label"><span class="col-f00">* </span>检测费</label>
                    <div class="layui-input-block">
                        <input type="number" id="monitorFee" step='0.0001' name="monitorFee" class="layui-input" lay-verify="required">
                    </div>
                    <div class="tag-right"><span class="tag-unit">万元</span></div>
                </div>
                <div class="layui-form-item has-tag">
                    <label class="layui-form-label"><span class="col-f00">* </span>其他费</label>
                    <div class="layui-input-block">
                        <input type="number" id="otherFee" step='0.0001' name="otherFee" class="layui-input" lay-verify="required">
                    </div>
                    <div class="tag-right"><span class="tag-unit">万元</span></div>
                </div>
            </div>
        </div>

    </form>
</div>

</body>
</html>
