
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>报价新增</title>
    <meta name="layout" content="main"/>
    <asset:javascript src="/eiaContract/eiaOfferCreate.js"/>
</head>

<body class="pb68">
<div class="layui-fluid larry-wrapper pt0">
    <div class="layer-title">
        <fieldset class="layui-elem-field layui-field-title site-title">
            <legend id="title">
                <a name="methodRender" class="pageTitle">新增报价</a>
            </legend>
        </fieldset>
    </div>

    <form class="layui-form offerInfo">
        <input type="hidden" id="eiaClientId" name="eiaClientId" value="${eiaOffer?.eiaClientId}">
        <input type="hidden" id="ownerClientId" name="ownerClientId" value="${eiaOffer?.ownerClientId}">

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
                    <label class="layui-form-label"><span class="col-f00"></span>报价编号</label>
                    <div class="layui-input-block">
                        <input type="text" id="offerNo" name="offerNo" class="layui-input readonly" value="" readonly>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">* </span>合同受托方</label>
                    <div class="layui-input-block">
                        <input type='checkbox' lay-filter='lhtz' id='lhtz' name='lhtz' lay-verify='required' value='lhtz' title='联合泰泽' checked>
                        <input type='checkbox' lay-filter='lhcd' id='lhcd' name='lhcd' lay-verify='required' value='lhcd' title='联合赤道'>
                        <input type="hidden" name="contractTrust" id="contractTrust" value="联合泰泽">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">* </span>合同类型</label>
                    <div class="layui-input-block">
                        <input type="text" id="contractTypeDrop" name="contractTypeDrop" class="layui-input" lay-verify="required" readonly>
                        <input type="hidden" id="contractTypeStr" name="contractTypeStr" lay-verify="required" value="">
                    </div>
                </div>
                <div class="layui-form-item moneyBlock">
                    <label class="layui-form-label"><span class="col-f00">* </span>报价金额</label>
                    <div class="layui-input-block">
                        <input type="number" id="offerMoney" name="offerMoney" class="layui-input dotFourInput readonly" lay-verify="number" value="" readonly>
                    </div>
                    <div class="action-block">
                        <div class="inner-box unit-box">
                            <span class="unit">万元</span>
                        </div>
                        <div class="inner-box action-box">
                            <i class="larry-icon fillMoneyBtn">&#xe939;</i>
                            <i class="larry-icon show-tips" id="tips">&#xe740;</i>
                        </div>
                    </div>
                </div>
              %{--  <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">* </span>任务名称</label>
                    <div class="layui-input-block">
                        <select name="taskId" id="taskId" lay-verify="required" lay-search>
                            <option value="">请选择</option>
                        </select>
                    </div>
                </div>--}%
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">* </span>所在省份</label>
                    <div class="layui-input-block">
                        <select name="province" id="province" lay-verify="required" lay-search>
                            <option value="">请选择</option>
                        </select>
                    </div>
                </div>
                <div class="layui-form-item has-tag">
                    <label class="layui-form-label"><span class="col-f00">* </span>客户名称</label>
                    <div class="layui-input-block">
                        <input type="text" id="eiaClientName" name="eiaClientName" class="layui-input readonly" lay-verify="required" value="" placeholder="填实际付款单位" readonly>
                    </div>
                    <div class="tag-right"><i class="larry-icon selectUserBtn">&#xe85b;</i></div>
                </div>
                <div class="layui-form-item has-tag">
                    <label class="layui-form-label"><span class="col-f00">* </span>联系人</label>
                    <div class="layui-input-block">
                        <input type="text" id="contactName" name="contactName" class="layui-input readonly" lay-verify="required" value="" readonly>
                    </div>
                    <div class="tag-right">
                        <i class="larry-icon selectContactBtn">&#xe85b;</i>
                    </div>
                </div>
                <div class="layui-form-item has-tag">
                    <label class="layui-form-label">甲方名称</label>
                    <div class="layui-input-block">
                        <input type="text" id="ownerClientName" name="ownerClientName" class="layui-input readonly" placeholder="如果客户名称和甲方名称相同，可不填" readonly>
                    </div>
                    <div class="tag-right"><i class="larry-icon selectOwnerBtn">&#xe85b;</i></div>
                </div>
                <div class="layui-form-item has-tag">
                    <label class="layui-form-label">甲方联系人</label>
                    <div class="layui-input-block">
                        <input type="text" id="ownerContactName" name="ownerContactName" class="layui-input readonly">
                    </div>
                    <div class="tag-right">
                        <i class="larry-icon selectOwnerContactBtn">&#xe85b;</i>
                    </div>
                </div>
            </div>
            <div class="layui-col-xs6">
                <div class="layui-form-item">
                    <label class="layui-form-label"></label>
                    <div class="layui-input-block">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">* </span>报价名称</label>
                    <div class="layui-input-block">
                        <input type="text" id="offerName" name="offerName" class="layui-input" lay-verify="required" value="">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">* </span>合同用途</label>
                    <div class="layui-input-block">
                        <select name="contractUse" id="contractUse" lay-verify="required" lay-search>
                            <option value="">请选择</option>
                        </select>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">* </span>报价时间</label>
                    <div class="layui-input-block">
                        <input type="text" id="offerDateStr" name="offerDateStr" class="layui-input" lay-verify="required|date" placeholder="年-月-日" autocomplete="off" value="">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">项目规模（万元）</label>
                    <div class="layui-input-block">
                        <input type="number" id="projectScale" name="projectScale" class="layui-input dotFourInput" value="">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">* </span>客户地址</label>
                    <div class="layui-input-block">
                        <input type="text" id="clientAddress" name="clientAddress" class="layui-input readonly" lay-verify="required" value="" readonly>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">* </span>联系人电话</label>
                    <div class="layui-input-block">
                        <input type="text" id="contactPhone" name="contactPhone" class="layui-input readonly" lay-verify="required" value="" readonly>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">甲方地址</label>
                    <div class="layui-input-block">
                        <input type="text" id="ownerClientAddress" name="ownerClientAddress" class="layui-input readonly">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">甲方联系人电话</label>
                    <div class="layui-input-block">
                        <input type="text" id="ownerContactPhone" name="ownerContactPhone" class="layui-input readonly">
                    </div>
                </div>
            </div>
        </div>

        <!--金额详情-->
        <input type="hidden" name="reportFees" id="reportFees" value="0.0000">
        <input type="hidden" name="enviroMonitoringFee" id="enviroMonitoringFee" value="0.0000">
        <input type="hidden" name="expertFee" id="expertFee" value="0.0000">
        <input type="hidden" name="groundWater" id="groundWater" value="0.0000">
        <input type="hidden" name="otherFee" id="otherFee" value="0.0000">
        <input type="hidden" name="specialFee" id="specialFee" value="0.0000">
        <input type="hidden" name="serveFee" id="serveFee" value="0.0000">
        <input type="hidden" name="ecoDetectFee" id="ecoDetectFee" value="0.0000">
        <input type="hidden" name="preIssCertFee" id="preIssCertFee" value="0.0000">
        <input type="hidden" name="preSurvCertFee" id="preSurvCertFee" value="0.0000">
        <input type="hidden" name="certServeFee" id="certServeFee" value="0.0000">
    </form>
</div>
<!--报价id-->
<input type="hidden" id="offerId" name="offerId" value="${eiaOfferId}">

</body>
</html>