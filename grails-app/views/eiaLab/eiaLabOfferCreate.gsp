<%@ page contentType="text/html;charset=UTF-8" import="com.lheia.eia.common.FuncConstants" %>
<html>
<head>
    <title></title>
    <meta name="layout" content="main"/>
    <asset:stylesheet src="/eiaLab/eiaLabOfferCreate.css"/>
    <asset:javascript src="/eiaLab/eiaLabOfferCreate.js"/>
</head>
<body>
<div class="layui-fluid larry-wrapper pt0">
    <div class="layer-title">
        <fieldset class="layui-elem-field layui-field-title site-title">
            <legend id="title">
            </legend>
        </fieldset>
    </div>
    <form class="layui-form">
        <blockquote class="layui-elem-quote">
            基本信息
            <div class="layui-inline pl12">
                <div class="layui-btn-group top-group">
                    <button class="layui-btn" lay-submit lay-filter="baseInfoSave"><i class="larry-icon">&#xe9d1;</i> 保存</button>
                    <a class="layui-btn display-none quitBtn" lay-submit lay-filter="saveAndQuit"><i class="larry-icon">&#xe9d1;</i> 保存并退出</a>
                </div>
            </div>
        </blockquote>
        <div class="layui-row mt15">
            <div class="layui-col-xs6">
                <div class="layui-form-item showProjectName display-none">
                    <label class="layui-form-label"><span class="col-f00">* </span>项目名称</label>
                    <div class="layui-input-block layui-form" lay-filter="projectName">
                        <select id="projectName" name="projectName" lay-filter="projectNameSelect" lay-verify="required" lay-search="">
                        </select>
                        <input type="hidden" id="eiaProjectId" name="eiaProjectId">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">* </span>委托（单位）名称</label>
                    <div class="layui-input-block layui-form">
                        <select id="wtClientName" name="wtClientName" lay-filter="wtClientNameSelect" lay-verify="required" lay-search="">
                            <option value="联合泰泽环境科技发展有限公司">联合泰泽环境科技发展有限公司</option>
                            <option value="联合赤道环境评价有限公司">联合赤道环境评价有限公司</option>
                        </select>
                        %{--<input type="text" name="wtClientName" id="wtClientName" class="layui-input readonly" readonly lay-verify="required" value="联合泰泽环境科技发展有限公司">--}%
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">* </span>委托联系人</label>
                    <div class="layui-input-block">
                        <input type="text" name="wtClientContact" id="wtClientContact" class="layui-input input-search-button" lay-verify="required" placeholder="输入格式如：姓名（业务**部）">
                            <i class="larry-icon font20 cursor-pointer" id="wtContactSelect">&#xe85b;</i>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">* </span>受检（单位）名称</label>
                    <div class="layui-input-block">
                        <input type="text" name="sjClientName" id="sjClientName" class="layui-input input-search-button readonly" lay-verify="required" readonly>
                            <i class="larry-icon font20 cursor-pointer" id="sjClientSelect">&#xe85b;</i>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">* </span>现场联系人</label>
                    <div class="layui-input-block">
                        <input type="text" name="sjClientContact" id="sjClientContact" class="layui-input readonly" lay-verify="required" readonly>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">* </span>是否宇相检测</label>
                    <div class="layui-input-block">
                        <input type="checkbox" id="ifYxTest" name="ifYxTest" lay-skin="switch" lay-filter="ifYxTest" lay-text="是|否" value="1">
                    </div>
                </div>
                <div class="layui-form-item" id="ifShowTestCompany" style="display: none">
                    <label class="layui-form-label"><span class="col-f00">* </span>委托检测方</label>
                    <div class="layui-input-block layui-form" lay-filter="testCompany">
                        <select id="testCompany" name="testCompany" lay-verify="required" lay-search="">
                        </select>
                    </div>
                </div>
            </div>
            <div class="layui-col-xs6">
                <div class="layui-form-item showProjectAddr display-none">
                    <label class="layui-form-label"><span class="col-f00">* </span>项目地址</label>
                    <div class="layui-input-block">
                        <input type="text" id="projectAddr" name="projectAddr"  class="layui-input readonly " lay-verify="required" readonly>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">* </span>委托（单位）地址</label>
                    <div class="layui-input-block">
                        <input type="text" name="wtClientAddr" id="wtClientAddr" class="layui-input readonly" lay-verify="required" readonly value="天津市和平区曲阜道80号">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">* </span>委托联系人电话</label>
                    <div class="layui-input-block">
                        <input type="text" name="wtClientPhone" id="wtClientPhone" class="layui-input" lay-verify="required">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">* </span>受检（单位）地址</label>
                    <div class="layui-input-block">
                        <input type="text" name="sjClientAddr" id="sjClientAddr" class="layui-input readonly" lay-verify="required" readonly>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">* </span>现场联系人电话</label>
                    <div class="layui-input-block">
                        <input type="text" name="sjClientPhone" id="sjClientPhone" class="layui-input readonly" lay-verify="required" readonly>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">* </span>项目类型</label>
                    <div class="layui-input-block layui-form" lay-filter="projectType">
                        <select id="projectType" name="projectType" lay-verify="required">
                        </select>
                    </div>
                </div>
            </div>
        </div>
        <input type="hidden" id="type" />
        <input type="hidden" id="eiaLabOfferId" name="eiaLabOfferId" />
        <input type="hidden" id="wtClientId" name="wtClientId" />
        <input type="hidden" id="sjClientId" name="sjClientId" />
        <input type="hidden" id="sjClientSource" name="sjClientSource" />
        <input type="hidden" id="labClientContractInfoId" name="labClientContractInfoId" />
    </form>
    %{--<blockquote class="layui-elem-quote larry-btn">--}%
        %{--检测计划及费用明细--}%
        %{--<div class="layui-inline pl12">--}%
            %{--<div class="layui-btn-group top-group">--}%
                %{--<a class="layui-btn layui-bg-pale display-none addPlanBtn" data-type="planAdd"><i class="larry-icon">&#xe876; </i> 新增项目</a>--}%
                %{--<a class="layui-btn layui-bg-red display-none noticeTag"><i class="larry-icon">&#xe740;</i> 填写报价基本信息后即可添加检测计划</a>--}%
                %{--<a class="layui-btn layui-bg-pale display-none addGroupBtn" data-type="planGroupAdd"><i class="larry-icon">&#xe876; </i> 新增套餐</a>--}%
                %{--<a class="layui-btn layui-bg-pale display-none discountBtn" data-type="batchDiscount"><i class="larry-icon">&#xe6bd;</i> 批量打折</a>--}%
            %{--</div>--}%
        %{--</div>--}%
        %{--<div class="layui-inline pl12 display-none" >--}%
        %{--</div>--}%
    %{--</blockquote>--}%
    %{--<table id="eiaLabOfferPlanList" lay-filter="eiaLabOfferPlanList"></table>--}%
    <input type="hidden" id="eiaLabOfferPlanId">
    <input type="hidden" id="eiaLabOfferPlanCount">
    <form class="layui-form display-none">
        <blockquote class="layui-elem-quote">
            委托书费用明细
            <div class="layui-inline pl12">
                <div class="layui-btn-group top-group">
                    <a class="layui-btn display-none feeSaveBtn" lay-submit lay-filter="exploreSave"><i class="larry-icon">&#xe9d1; </i> 保存</a>
                    <a class="layui-btn layui-bg-red display-none noticeTag"><i class="larry-icon">&#xe740;</i> 填写报价基本信息后即可添加费用</a>
                    <a class="layui-btn display-none quitBtn" lay-submit lay-filter="feeSaveQuit"><i class="larry-icon">&#xe9d1;</i> 保存并退出</a>
                </div>
            </div>
        </blockquote>
        <div class="layui-row mt15">
            <div class="layui-col-xs3">
                <div class="layui-form-item">
                    <label class="layui-form-label">现场勘察费用（元）</label>
                    <div class="layui-input-block">
                        <input type="text" id="exploreFee" name="exploreFee" class="layui-input" lay-verify="ratenum">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">住宿费用（元）</label>
                    <div class="layui-input-block">
                        <input type="text" id="hotelFee" name="hotelFee" class="layui-input" lay-verify="ratenum">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">交通费用（元）</label>
                    <div class="layui-input-block">
                        <input type="text" id="trafficFee" name="trafficFee" class="layui-input" lay-verify="ratenum">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">报告编制费用（元）</label>
                    <div class="layui-input-block">
                        <input type="text" id="workOutFee" name="workOutFee" class="layui-input" lay-verify="ratenum">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">折扣（%）</label>
                    <div class="layui-input-block">
                        <input type="text" id="contractDiscount" name="contractDiscount" class="layui-input" lay-verify="ratenum">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">优惠前价格（元）</label>
                    <div class="layui-input-block">
                        <input type="text" id="prePrefFee" name="prePrefFee" class="layui-input readonly" readonly>
                    </div>
                </div>
            </div>
            <div class="layui-col-xs3">
                <div class="layui-form-item">
                    <label class="layui-form-label">现场勘察人数</label>
                    <div class="layui-input-block">
                        <input type="text" id="explorePeopleNum" name="explorePeopleNum" class="layui-input" lay-verify="intnumberZero">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">房间数</label>
                    <div class="layui-input-block">
                        <input type="text" id="roomNum" name="roomNum" class="layui-input" lay-verify="intnumberZero">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">差旅天数</label>
                    <div class="layui-input-block">
                        <input type="text" id="travelDayNum" name="travelDayNum" class="layui-input" lay-verify="intnumberZero">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">专家费用（元）</label>
                    <div class="layui-input-block">
                        <input type="text" id="expertFee" name="expertFee" class="layui-input" lay-verify="ratenum">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">税费（%）</label>
                    <div class="layui-input-block">
                        <input type="text" id="contractTax" name="contractTax" class="layui-input" lay-verify="ratenum">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label layui-form-label-width">优惠前价格（含税：元）</label>
                    <div class="layui-input-block">
                        <input type="text" id="prePrefFeeTax" name="prePrefFeeTax" class="layui-input readonly" readonly>
                    </div>
                </div>
            </div>
            <div class="layui-col-xs3">
                <div class="layui-form-item">
                    <label class="layui-form-label">现场勘察天数</label>
                    <div class="layui-input-block">
                        <input type="text" id="exploreDayNum" name="exploreDayNum" class="layui-input" lay-verify="intnumberZero">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">住宿天数</label>
                    <div class="layui-input-block">
                        <input type="text" id="liveDayNum" name="liveDayNum" class="layui-input" lay-verify="intnumberZero">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">交通费用小计（元）</label>
                    <div class="layui-input-block">
                        <input type="text" id="trafficFeeTotal" name="trafficFeeTotal" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">其他费用类型</label>
                    <div class="layui-input-block layui-form" lay-filter="otherFeeType">
                        <input type="text" id="otherFeeType" name="otherFeeType" class="layui-input input-search-button-small readonly" readonly>
                        <i class="larry-icon font20 cursor-pointer" id="otherFeeTypeSel">&#xe85b;</i>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"></label>
                    <div class="layui-input-block">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">优惠后价格（元）</label>
                    <div class="layui-input-block">
                        <input type="text" id="countFee" name="countFee" class="layui-input readonly" readonly>
                    </div>
                </div>
            </div>
            <div class="layui-col-xs3">
                <div class="layui-form-item">
                    <label class="layui-form-label">勘查费用小计（元）</label>
                    <div class="layui-input-block">
                        <input type="text" id="exploreFeeTotal" name="exploreFeeTotal" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">住宿费用小计（元）</label>
                    <div class="layui-input-block">
                        <input type="text" id="hotelFeeTotal" name="hotelFeeTotal" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">检测计划小计（元）</label>
                    <div class="layui-input-block">
                        <input type="text" id="sampleFee" name="sampleFee" class="layui-input readonly" readonly>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">其他费用小计（元）</label>
                    <div class="layui-input-block">
                        <input type="text" id="otherFee" name="otherFee" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"></label>
                    <div class="layui-input-block">
                        <input type="hidden" id="preSampleFee" name="preSampleFee">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label layui-form-label-width">优惠后价格（含税：元）</label>
                    <div class="layui-input-block">
                        <input type="text" id="countFeeTax" name="countFeeTax" class="layui-input readonly" readonly>
                    </div>
                </div>
            </div>
        </div>
    </form>

    <!--列表-->
    <div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12 uploadFile">
        <table id="eiaFileUploadList" lay-filter="eiaFileUploadList"></table>
    </div>
    <input type="hidden" id="tableName" value=""/>
    <input type="hidden" id="tableId" value=""/>
</div>
<script id="addTitle" type="text/x-jquery-tmpl">
    <a name="methodRender">新增报价</a>
</script>
<script id="editTitle" type="text/x-jquery-tmpl">
    <a name="methodRender">编辑报价</a>
</script>
<!--列表操作列-->
<script type="text/html" id="eiaLabOfferPlanListBar">
    <div class="layui-btn-group">
        <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="planShow" title="查看"><i class="larry-icon">&#xe939;</i></a>
        <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="planEdit" title="修改"><i class="larry-icon">&#xe72b;</i></a>
        <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="planDel" title="删除"><i class="larry-icon">&#xe8d0;</i></a>
    </div>
</script>
<script type="text/html" id="eiaFileUploadListBar">
<div class="layui-btn-group">
    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaFileUploadDetail" title="查看"><i class="larry-icon">&#xe939;</i></a>
    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaFileUploadDel" title="删除"><i class="larry-icon">&#xe8d0;</i></a>
    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaFileUploadDownload" title="下载"><i class="larry-icon">&#xe8cd;</i></a>
</div>
</script>
<script type="text/html" id="tableTopTmp">
<div class="table-top larry-btn uploadFile">
    文件上传
    <div class="layui-btn-group pl12">
        <a class="layui-btn pl12 fileAddBtn display-none" lay-event="eiaFileUploadAdd"><i class="larry-icon">&#xe876;</i> 新增</a>
        <a class="layui-btn layui-bg-red noticeTag"><i class="larry-icon">&#xe740;</i> 填写监测方案基本信息后即可添加附件</a>
    </div>
</div>
</script>
</body>
</html>