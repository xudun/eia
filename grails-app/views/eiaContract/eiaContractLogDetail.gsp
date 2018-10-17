<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>合同变更详情</title>
    <meta name="layout" content="main"/>
    <asset:javascript src="/eiaContract/eiaContractLogFillInputLib.js"/>
    <asset:javascript src="/eiaContract/eiaContractLogDetail.js"/>
</head>

<body>
<div class="layui-fluid larry-wrapper pt0">
    <div class="layer-title">
        <fieldset class="layui-elem-field layui-field-title site-title">
            <legend id="title">
                <a name="methodRender" class="pageTitle">合同变更详情</a>
            </legend>
        </fieldset>
    </div>

    <form class="layui-form offerInfo">
        <!--合同变更id-->
        <input type="hidden" id="eiaContractLogId" name="eiaContractLogId">
        <div class="layui-row mt15">
            <div class="layui-col-xs6">
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>合同编号</label>
                    <div class="layui-input-block check-block">
                        <span id="contractNo"></span>&nbsp;
                        <i class="larry-icon font20 cursor-pointer display-none" id="contractNoShow">&#xe740;</i>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>合同委托方</label>
                    <div class="layui-input-block check-block">
                        <span id="contractTrust"></span>&nbsp;
                        <i class="larry-icon font20 cursor-pointer display-none" id="contractTrustShow">&#xe740;</i>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>合同类型</label>
                    <div class="layui-input-block check-block">
                        <span id="contractType"></span>&nbsp;
                        <i class="larry-icon font20 cursor-pointer display-none" id="contractTypeShow">&#xe740;</i>
                    </div>
                </div>
                <div class="layui-form-item has-tag">
                    <label class="layui-form-label"><span class="col-f00"></span>合同金额</label>
                    <div class="layui-input-block check-block">
                        <span class="cost-num" id="contractMoney"></span><span>&nbsp;&nbsp;(万元)</span>
                        <i class="larry-icon font20 cursor-pointer display-none" id="contractMoneyShow">&#xe740;</i>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>任务名称</label>
                    <div class="layui-input-block check-block">
                        <span id="taskName"></span>&nbsp;
                        <i class="larry-icon font20 cursor-pointer display-none" id="taskNameShow">&#xe740;</i>
                    </div>
                </div>
                <div class="layui-form-item has-tag">
                    <label class="layui-form-label"><span class="col-f00"></span>客户名称</label>
                    <div class="layui-input-block check-block">
                        <span id="eiaClientName"></span>&nbsp;
                        <i class="larry-icon font20 cursor-pointer display-none" id="eiaClientNameShow">&#xe740;</i>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>联系人</label>
                    <div class="layui-input-block check-block">
                        <span id="contactName"></span>&nbsp;
                        <i class="larry-icon font20 cursor-pointer display-none" id="contactNameShow">&#xe740;</i>
                    </div>
                </div>
                <div class="layui-form-item has-tag">
                    <label class="layui-form-label"><span class="col-f00"></span>甲方名称</label>
                    <div class="layui-input-block check-block">
                        <span id="ownerClientName"></span>&nbsp;
                        <i class="larry-icon font20 cursor-pointer display-none" id="ownerClientNameShow">&#xe740;</i>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>甲方联系人</label>
                    <div class="layui-input-block check-block">
                        <span id="ownerContactName"></span>&nbsp;
                        <i class="larry-icon font20 cursor-pointer display-none" id="ownerContactNameShow">&#xe740;</i>
                    </div>
                </div>
            </div>
            <div class="layui-col-xs6">
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span></label>
                    <div class="layui-input-block check-block">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>合同名称</label>
                    <div class="layui-input-block check-block">
                        <span id="contractName"></span>&nbsp;
                        <i class="larry-icon font20 cursor-pointer display-none" id="contractNameShow">&#xe740;</i>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>合同用途</label>
                    <div class="layui-input-block check-block">
                        <span id="contractUse"></span>&nbsp;
                        <i class="larry-icon font20 cursor-pointer display-none" id="contractUseShow">&#xe740;</i>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>签订时间</label>
                    <div class="layui-input-block check-block">
                        <span id="contractDate"></span>&nbsp;
                        <i class="larry-icon font20 cursor-pointer display-none" id="contractDateShow">&#xe740;</i>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>所在省份</label>
                    <div class="layui-input-block check-block">
                        <span id="province"></span>&nbsp;
                        <i class="larry-icon font20 cursor-pointer display-none" id="provinceShow">&#xe740;</i>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>客户地址</label>
                    <div class="layui-input-block check-block">
                        <span id="clientAddress"></span>&nbsp;
                        <i class="larry-icon font20 cursor-pointer display-none" id="clientAddressShow">&#xe740;</i>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>联系人电话</label>
                    <div class="layui-input-block check-block">
                        <span id="contactPhone"></span>&nbsp;
                        <i class="larry-icon font20 cursor-pointer display-none" id="contactPhoneShow">&#xe740;</i>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>甲方地址</label>
                    <div class="layui-input-block check-block">
                        <span id="ownerClientAddress"></span>&nbsp;
                        <i class="larry-icon font20 cursor-pointer display-none" id="ownerClientAddressShow">&#xe740;</i>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>甲方联系人电话</label>
                    <div class="layui-input-block check-block">
                        <span id="ownerContactPhone"></span>&nbsp;
                        <i class="larry-icon font20 cursor-pointer display-none" id="ownerContactPhoneShow">&#xe740;</i>
                    </div>
                </div>
            </div>
        </div>

        <div class="layui-row mt15 dynMoneyInputs">
            <div class="layui-col-xs6"></div>
            <div class="layui-col-xs6"></div>
        </div>

    </form>
</div>
</body>
</html>