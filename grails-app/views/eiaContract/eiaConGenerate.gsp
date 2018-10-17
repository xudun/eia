
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>生成合同</title>
    <meta name="layout" content="main"/>
    <asset:javascript src="/eiaContract/eiaConGenerate.js"/>
</head>

<body>
<div class="layui-fluid larry-wrapper pt0">
    <div class="layer-title">
        <fieldset class="layui-elem-field layui-field-title site-title">
            <legend id="title">
                <a name="methodRender" class="pageTitle">生成合同</a>
            </legend>
        </fieldset>
    </div>

    <form class="layui-form offerInfo">
        <input type="hidden" id="eiaClientId" name="eiaClientId" value="${eiaOffer?.eiaClientId}">
        <blockquote class="layui-elem-quote larry-btn">
            报价信息
            <div class="layui-inline pl12">
                <button class="layui-btn" lay-submit="" lay-filter="save"><i class="larry-icon">&#xe9d1;</i> 保存</button>
            </div>
        </blockquote>
        <div class="layui-row mt15">
            <div class="layui-col-xs6">
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00"></span>报价编号</label>
                    <div class="layui-input-block">
                        <input type="text" id="offerNo" name="offerNo" class="layui-input readonly"  value="" readonly>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">合同类型</label>
                    <div class="layui-input-block">
                        <input type="text" id="contractTypeDrop" name="contractTypeDrop" class="layui-input" >
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
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">* </span>任务名称</label>
                    <div class="layui-input-block">
                        <select name="taskId" id="taskId"  lay-verify="required" lay-search>
                            <option value="">请选择</option>
                        </select>
                    </div>
                </div>
               %{-- <div class="layui-form-item has-tag">
                    <label class="layui-form-label"><span class="col-f00">* </span>客户名称</label>
                    <div class="layui-input-block">
                        <input type="text" id="eiaClientName" name="eiaClientName" class="layui-input readonly" lay-verify="required" value="" readonly>
                    </div>
                    <div class="tag-right"><i class="larry-icon selectUserBtn">&#xe85b;</i></div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">* </span>联系人</label>
                    <div class="layui-input-block">
                        <input type="text" id="contactName" name="contactName" class="layui-input readonly" lay-verify="required" value="" readonly>
                    </div>
                </div>--}%

               %{-- <div class="layui-form-item has-tag">
                    <label class="layui-form-label"><span class="col-f00">* </span>环境监测费</label>
                    <div class="layui-input-block">
                        <input type="number" id="enviroMonitoringFee" name="enviroMonitoringFee" class="layui-input dotFourInput" lay-verify="required|number" value="">
                    </div>
                    <div class="tag-right"><span class="tag-unit">万元</span></div>
                </div>
                <div class="layui-form-item has-tag">
                    <label class="layui-form-label"><span class="col-f00">* </span>专家评审费</label>
                    <div class="layui-input-block">
                        <input type="number" id="expertFee" name="expertFee" class="layui-input dotFourInput" lay-verify="required|number" value="">
                    </div>
                    <div class="tag-right"><span class="tag-unit">万元</span></div>
                </div>
                <div class="layui-form-item has-tag">
                    <label class="layui-form-label"><span class="col-f00">* </span>其他费用</label>
                    <div class="layui-input-block">
                        <input type="number" id="otherFee" name="otherFee" class="layui-input dotFourInput" lay-verify="required|number" value="">
                    </div>
                    <div class="tag-right"><span class="tag-unit">万元</span></div>
                </div>--}%
            </div>
            <div class="layui-col-xs6">
                <div class="layui-form-item">
                    <label class="layui-form-label">合同名称</label>
                    <div class="layui-input-block">
                        <input type="text" id="contractName" name="contractName" class="layui-input"  value="">
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
                    <label class="layui-form-label"><span class="col-f00">* </span>所在省份</label>
                    <div class="layui-input-block">
                        <select name="province" id="province" lay-verify="required" lay-search>
                            <option value="">请选择</option>
                        </select>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">* </span>签订时间</label>
                    <div class="layui-input-block">
                        <input type="text" id="contractDate" name="contractDate" class="layui-input" lay-verify="required" value="">
                    </div>
                </div>


               %{-- <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">* </span>客户地址</label>
                    <div class="layui-input-block">
                        <input type="text" id="clientAddress" name="clientAddress" class="layui-input readonly" lay-verify="required" value="" readonly>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">* </span>联系人电话</label>
                    <div class="layui-input-block">
                        <input type="number" id="contactPhone" name="contactPhone" class="layui-input readonly" lay-verify="required|phone" value="" readonly>
                    </div>
                </div>--}%


                %{--<div class="layui-form-item has-tag">
                    <label class="layui-form-label"><span class="col-f00">* </span>报告编制费</label>
                    <div class="layui-input-block">
                        <input type="number" id="reportFees" name="reportFees" class="layui-input dotFourInput" lay-verify="required|number" value="">
                    </div>
                    <div class="tag-right"><span class="tag-unit">万元</span></div>
                </div>
                <div class="layui-form-item has-tag">
                    <label class="layui-form-label"><span class="col-f00">* </span>地下水专题评价费</label>
                    <div class="layui-input-block">
                        <input type="number" id="groundWater" name="groundWater" class="layui-input dotFourInput" lay-verify="required|number" value="">
                    </div>
                    <div class="tag-right"><span class="tag-unit">万元</span></div>
                </div>--}%

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
<!--合同id-->
<input type="hidden" id="contractId" name="contractId" value="${eiaOfferId}">
</body>
</html>