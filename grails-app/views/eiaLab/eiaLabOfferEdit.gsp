<%@ page contentType="text/html;charset=UTF-8" import="com.lheia.eia.common.FuncConstants" %>
<html>
<head>
    <title></title>
    <meta name="layout" content="main"/>
    <asset:stylesheet src="/eiaLab/eiaLabOfferCreate.css"/>
    <asset:javascript src="/eiaLab/eiaLabOfferEdit.js"/>
</head>
<body class="pb68">
<div class="layui-fluid larry-wrapper pt0">
    <div class="layer-title">
        <fieldset class="layui-elem-field layui-field-title site-title">
            <legend id="title">
            </legend>
        </fieldset>
    </div>
    <form class="layui-form">

        <blockquote class="layui-elem-quote larry-btn fixed-footer">
            <div class="layui-inline pl12">
                <div class="layui-btn-group top-group">
                    <g:if test="${ifOption == true}">
                        <button class="layui-btn pl12" lay-submit lay-filter="baseInfoSave"><i class="larry-icon">&#xe9d1;</i> 保存</button>
                    </g:if>
                </div>
            </div>
        </blockquote>

        <div class="layui-row mt15">
            <div class="layui-col-xs6">
                <div class="layui-form-item showProjectName">
                    <label class="layui-form-label"><span class="col-f00">* </span>项目名称</label>
                    <div class="layui-input-block layui-form" lay-filter="projectName">
                        <select id="projectName" name="projectName" lay-filter="projectNameSelect" lay-verify="required" lay-search="">
                        </select>
                        <input type="hidden" id="eiaProjectId" name="eiaProjectId">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">* </span>委托（单位）名称</label>
                    <div class="layui-input-block">
                        <input type="text" name="wtClientName" id="wtClientName" class="layui-input readonly" readonly lay-verify="required" value="联合泰泽环境科技发展有限公司">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">* </span>委托联系人</label>
                    <div class="layui-input-block">
                        <input type="text" name="wtClientContact" id="wtClientContact" class="layui-input input-search-button readonly" readonly lay-verify="required" placeholder="输入格式如：姓名（业务**部）">
                            %{--<i class="larry-icon font20 cursor-pointer" id="wtContactSelect">&#xe85b;</i>--}%
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">* </span>受检（单位）名称</label>
                    <div class="layui-input-block">
                        <input type="text" name="sjClientName" id="sjClientName" class="layui-input input-search-button readonly" lay-verify="required" readonly>
                            %{--<i class="larry-icon font20 cursor-pointer" id="sjClientSelect">&#xe85b;</i>--}%
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
                        <input type="text" id="ifYxTestStr" name="ifYxTestStr" class="layui-input readonly" readonly>
                        <input type="hidden" id="ifYxTest" name="ifYxTest">
                    </div>
                </div>
            </div>
            <div class="layui-col-xs6">
                <div class="layui-form-item showProjectAddr">
                    <label class="layui-form-label"><span class="col-f00">* </span>项目地址</label>
                    <div class="layui-input-block">
                        <input type="text" id="projectAddr" name="projectAddr"  class="layui-input readonly " lay-verify="required" readonly>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">* </span>委托（单位）地址</label>
                    <div class="layui-input-block">
                        <input type="text" name="wtClientAddr" id="wtClientAddr" class="layui-input readonly" lay-verify="required" readonly value="和平区小白楼街曲阜道80号504室">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="col-f00">* </span>委托联系人电话</label>
                    <div class="layui-input-block">
                        <input type="text" name="wtClientPhone" id="wtClientPhone" class="layui-input readonly" readonly lay-verify="required">
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
                    <div class="layui-input-block layui-form" >
                        <input id="projectType" name="projectType" lay-verify="required" class="layui-input readonly" readonly>
                    </div>
                </div>
            </div>
        </div>
        <input type="hidden" id="eiaLabOfferId" name="eiaLabOfferId" />
        <input type="hidden" id="wtClientId" name="wtClientId" />
        <input type="hidden" id="sjClientId" name="sjClientId" />
    </form>

    <table id="eiaLabOfferPlanList" lay-filter="eiaLabOfferPlanList"></table>

    <input type="hidden" id="eiaLabOfferPlanId">
    <input type="hidden" id="eiaLabOfferPlanCount">
    <form class="layui-form">
        <blockquote class="layui-elem-quote cal-quote">
            采样费用明细
            <div class="disc-box">
                <div class="layui-btn total-txt layui-btn-danger">采样费用小计：<span id="exploreSubTotal" value="0"></span> 元</div>
            </div>
        </blockquote>
        <div class="layui-row mt15">
            <div class="layui-col-xs3">
                <div class="layui-form-item">
                    <label class="layui-form-label">现场勘察费用（元）</label>
                    <div class="layui-input-block">
                        <input type="text" id="exploreFee" name="exploreFee" class="layui-input readonly" readonly>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">住宿费用（元）</label>
                    <div class="layui-input-block">
                        <input type="text" id="hotelFee" name="hotelFee" class="layui-input readonly" readonly>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">交通费用（元）</label>
                    <div class="layui-input-block">
                        <input type="text" id="trafficFee" name="trafficFee" class="layui-input readonly" readonly>
                    </div>
                </div>
            </div>
            <div class="layui-col-xs3">
                <div class="layui-form-item">
                    <label class="layui-form-label">现场勘察人数</label>
                    <div class="layui-input-block">
                        <input type="text" id="explorePeopleNum" name="explorePeopleNum" class="layui-input readonly" readonly>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">房间数</label>
                    <div class="layui-input-block">
                        <input type="text" id="roomNum" name="roomNum" class="layui-input readonly" readonly>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">差旅天数</label>
                    <div class="layui-input-block">
                        <input type="text" id="travelDayNum" name="travelDayNum" class="layui-input readonly" readonly>
                    </div>
                </div>
            </div>
            <div class="layui-col-xs3">
                <div class="layui-form-item">
                    <label class="layui-form-label">现场勘察天数</label>
                    <div class="layui-input-block">
                        <input type="text" id="exploreDayNum" name="exploreDayNum" class="layui-input readonly" readonly>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">住宿天数</label>
                    <div class="layui-input-block">
                        <input type="text" id="liveDayNum" name="liveDayNum" class="layui-input readonly" readonly>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">交通费用小计（元）</label>
                    <div class="layui-input-block">
                        <input type="text" id="trafficFeeTotal" name="trafficFeeTotal" class="layui-input readonly" readonly>
                    </div>
                </div>
            </div>
            <div class="layui-col-xs3">
                <div class="layui-form-item">
                    <label class="layui-form-label">勘查费用小计（元）</label>
                    <div class="layui-input-block">
                        <input type="text" id="exploreFeeTotal" name="exploreFeeTotal" class="layui-input readonly" readonly>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">住宿费用小计（元）</label>
                    <div class="layui-input-block">
                        <input type="text" id="hotelFeeTotal" name="hotelFeeTotal" class="layui-input readonly" readonly>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">采样费用折扣（%）</label>
                    <div class="layui-input-block">
                        <input type="text" id="exploreSubTotalDisc" name="exploreSubTotalDisc" class="layui-input readonly" readonly>
                    </div>
                </div>
            </div>
        </div>
        <blockquote class="layui-elem-quote cal-quote">
            其他费用明细
            <div class="disc-box">
                <div class="layui-btn total-txt-thin layui-btn-danger">其他费用小计：<span id="otherFeeTotal" value="0"></span> 元</div>
            </div>
        </blockquote>
        <div class="layui-row mt15">
            <div class="layui-col-xs3">
                <div class="layui-form-item">
                    <label class="layui-form-label">报告编制费用（元）</label>
                    <div class="layui-input-block">
                        <input type="text" id="workOutFee" name="workOutFee" class="layui-input readonly" readonly>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">其他费用折扣（%）</label>
                    <div class="layui-input-block">
                        <input type="text" id="otherFeeTotalDisc" name="otherFeeTotalDisc" class="layui-input readonly" readonly>
                    </div>
                </div>
            </div>
            <div class="layui-col-xs3">
                <div class="layui-form-item">
                    <label class="layui-form-label">专家费用（元）</label>
                    <div class="layui-input-block">
                        <input type="text" id="expertFee" name="expertFee" class="layui-input readonly" readonly>
                    </div>
                </div>
            </div>
            <div class="layui-col-xs3">
                <div class="layui-form-item">
                    <label class="layui-form-label">其他费用类型</label>
                    <div class="layui-input-block layui-form" lay-filter="otherFeeType">
                        <input type="text" id="otherFeeType" name="otherFeeType" class="layui-input readonly" readonly>
                    </div>
                </div>
            </div>
            <div class="layui-col-xs3">
                <div class="layui-form-item">
                    <label class="layui-form-label">其他费用小计（元）</label>
                    <div class="layui-input-block">
                        <input type="text" id="otherFee" name="otherFee" class="layui-input readonly" readonly>
                    </div>
                </div>
            </div>
        </div>
        <blockquote class="layui-elem-quote cal-quote">
            费用总计
        </blockquote>
        <div class="layui-row mt15">
            <div class="layui-col-xs3">
                <div class="layui-form-item">
                    <label class="layui-form-label">本单折扣（%）</label>
                    <div class="layui-input-block">
                        <input type="text" id="contractDiscount" name="contractDiscount" class="layui-input readonly" readonly>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">最终优惠价格（含税）</label>
                    <div class="layui-input-block">
                        <input type="text" id="busiFee" name="busiFee" class="layui-input readonly" readonly>
                    </div>
                </div>
            </div>
            <div class="layui-col-xs3">
                <div class="layui-form-item">
                    <label class="layui-form-label">税费（%）</label>
                    <div class="layui-input-block">
                        <input type="text" id="contractTax" name="contractTax" class="layui-input readonly" readonly>
                    </div>
                </div>
            </div>
            <div class="layui-col-xs3">
                <div class="layui-form-item">
                    <label class="layui-form-label">费用总计（元）</label>
                    <div class="layui-input-block">
                        <input type="text" id="countFee" name="countFee" class="layui-input readonly" readonly>
                    </div>
                </div>
            </div>
            <div class="layui-col-xs3">
                <div class="layui-form-item">
                    <label class="layui-form-label">费用总计（含税）</label>
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
<script id="editTitle" type="text/x-jquery-tmpl">
    <a name="methodRender">编辑报价</a>
</script>
<!--列表操作列-->
<script type="text/html" id="eiaFileUploadListBar">
<div class="layui-btn-group">
    {{# if((d.inputUserId== ${session.staff.staffId})||("s_admin"== "${session.staff.staffCode}")){}}
    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaFileUploadDel" title="删除"><i class="larry-icon">&#xe8d0;</i></a>
    {{# } }}
    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaFileUploadDownload" title="下载"><i class="larry-icon">&#xe8cd;</i></a>
</div>
</script>
<script type="text/html" id="planListTmp">
<div class="table-top cal-quote">
    检测计划费用明细
    <div class="disc-box">
        <div class="layui-btn total-txt-show planSub layui-btn-danger">检测费用小计：<span id="planSubTotal">0</span> 元</div>
    </div>
</div>
</script>
<script type="text/html" id="tableTopTmp">
<div class="table-top">
    文件上传
</div>
</script>
</body>
</html>