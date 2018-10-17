<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>报价详情</title>
    <meta name="layout" content="main"/>
    <asset:javascript src="/eiaContract/eiaMoneyFillInputLib.js"/>
    <asset:javascript src="/eiaContract/eiaOfferDetail.js"/>
</head>

<body>
<div class="layui-fluid larry-wrapper pt0">
    <div class="layer-title">
        <fieldset class="layui-elem-field layui-field-title site-title">
            <legend id="title">
                <a name="methodRender" class="pageTitle">报价详情</a>
            </legend>
        </fieldset>
    </div>

    <form class="layui-form offerInfo">
        <div class="layui-row mt15">
            <div class="layui-col-xs6">
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>报价编号</label>
                    <div class="layui-input-block check-block" id="offerNo"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>合同受托方</label>
                    <div class="layui-input-block check-block" id="contractTrust"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>合同类型</label>
                    <div class="layui-input-block check-block" id="contractType"></div>
                </div>
                <div class="layui-form-item has-tag">
                    <label class="layui-form-label"><span class="col-f00"></span>报价金额</label>
                    <div class="layui-input-block check-block" id="offerMoney"><span class="cost-num"></span><span>&nbsp;&nbsp;(万元)</span></div>
                </div>
              %{--  <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>任务名称</label>
                    <div class="layui-input-block check-block" id="taskName"></div>
                </div>--}%
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>所在省份</label>
                    <div class="layui-input-block check-block" id="province"></div>
                </div>
                <div class="layui-form-item has-tag">
                    <label class="layui-form-label"><span class="col-f00"></span>客户名称</label>
                    <div class="layui-input-block check-block" id="eiaClientName"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>联系人</label>
                    <div class="layui-input-block check-block" id="contactName"></div>
                </div>
                <div class="layui-form-item has-tag">
                    <label class="layui-form-label"><span class="col-f00"></span>甲方名称</label>
                    <div class="layui-input-block check-block" id="ownerClientName"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>甲方联系人</label>
                    <div class="layui-input-block check-block" id="ownerContactName"></div>
                </div>
            </div>
            <div class="layui-col-xs6">
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span></label>
                    <div class="layui-input-block check-block"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>报价名称</label>
                    <div class="layui-input-block check-block" id="offerName"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>合同用途</label>
                    <div class="layui-input-block check-block" id="contractUse"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>报价时间</label>
                    <div class="layui-input-block check-block" id="offerDate"></div>
                </div>
                <div class="layui-form-item has-tag">
                    <label class="layui-form-label"><span class="col-f00"></span>项目规模</label>
                    <div class="layui-input-block check-block" id="projectScale"><span class="cost-num"></span><span>&nbsp;&nbsp;(万元)</span></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>客户地址</label>
                    <div class="layui-input-block check-block" id="clientAddress"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>联系人电话</label>
                    <div class="layui-input-block check-block" id="contactPhone"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>甲方地址</label>
                    <div class="layui-input-block check-block" id="ownerClientAddress"></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>甲方联系人电话</label>
                    <div class="layui-input-block check-block" id="ownerContactPhone"></div>
                </div>
            </div>
        </div>

        <div class="layui-row mt15 dynMoneyInputs">
            <div class="layui-col-xs6"></div>
            <div class="layui-col-xs6"></div>
        </div>
    </form>
</div>
<!--报价id-->
<input type="hidden" id="offerId" name="offerId" value="${eiaOffer?.id}">
</body>
</html>