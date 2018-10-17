<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title></title>
    <meta name="layout" content="main"/>
    <asset:stylesheet src="/eiaLab/eiaLabOfferDetail.css"/>
    <asset:javascript src="/eiaLab/eiaLabOfferDetail.js"/>
</head>

<body>
<div class="layui-fluid larry-wrapper pt0">
    <div class="layer-title">
        <fieldset class="layui-elem-field layui-field-title site-title">
            <legend id="title">
                <a name="methodRender">查看监测方案</a>
            </legend>
        </fieldset>
    </div>
    <form class="layui-form line-height">
        <blockquote class="layui-elem-quote">
            基本信息
        </blockquote>
        <div class="layui-row mt15">
            <div class="layui-col-xs6">
                <div class="layui-form-item">
                    <label class="layui-form-label">项目名称</label>
                    <div class="layui-input-block" id="projectName">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">委托（单位）名称</label>
                    <div class="layui-input-block" id="wtClientName">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">委托联系人</label>
                    <div class="layui-input-block" id="wtClientContact">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">受检（单位）名称</label>
                    <div class="layui-input-block" id="sjClientName">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">现场联系人</label>
                    <div class="layui-input-block" id="sjClientContact">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">预计采样日期</label>
                    <div class="layui-input-block" id="reportDate">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">是否宇相检测</label>
                    <div class="layui-input-block" id="ifYxTest">
                    </div>
                </div>
            </div>
            <div class="layui-col-xs6">
                <div class="layui-form-item">
                    <label class="layui-form-label">项目地址</label>
                    <div class="layui-input-block" id="projectAddr">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">委托（单位）地址</label>
                    <div class="layui-input-block" id="wtClientAddr">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">委托联系人电话</label>
                    <div class="layui-input-block" id="wtClientPhone">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">受检（单位）地址</label>
                    <div class="layui-input-block" id="sjClientAddr">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">现场联系人电话</label>
                    <div class="layui-input-block" id="sjClientPhone">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">项目类型</label>
                    <div class="layui-input-block" id="projectType">
                    </div>
                </div>
                <div class="layui-form-item" id="ifShowTestCompany" style="display: none">
                    <label class="layui-form-label">委托检测方</label>
                    <div class="layui-input-block" id="testCompany">
                    </div>
                </div>
            </div>
        </div>
    </form>

    <table id="eiaLabOfferPlanList" lay-filter="eiaLabOfferPlanList"></table>

    <form class="layui-form line-height">
        <blockquote class="layui-elem-quote cal-quote">
            采样费用明细
            <div class="disc-box">
                <div class="layui-btn total-txt-show layui-btn-danger">采样费用小计：<span id="exploreSubTotal">0</span> 元</div>
            </div>
        </blockquote>
        <div class="layui-row mt15">
            <div class="layui-col-xs3">
                <div class="layui-form-item">
                    <label class="layui-form-label">现场勘察费用（元）</label>
                    <div class="layui-input-block" id="exploreFee">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">住宿费用（元）</label>
                    <div class="layui-input-block" id="hotelFee">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">交通费用（元）</label>
                    <div class="layui-input-block" id="trafficFee">
                    </div>
                </div>
            </div>
            <div class="layui-col-xs3">
                <div class="layui-form-item">
                    <label class="layui-form-label">现场勘察人数</label>
                    <div class="layui-input-block" id="explorePeopleNum">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">房间数</label>
                    <div class="layui-input-block" id="roomNum">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">差旅天数</label>
                    <div class="layui-input-block" id="travelDayNum">
                    </div>
                </div>
            </div>
            <div class="layui-col-xs3">
                <div class="layui-form-item">
                    <label class="layui-form-label">现场勘察天数</label>
                    <div class="layui-input-block" id="exploreDayNum">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">住宿天数</label>
                    <div class="layui-input-block" id="liveDayNum">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">交通费用小计（元）</label>
                    <div class="layui-input-block" id="trafficFeeTotal">
                    </div>
                </div>
            </div>
            <div class="layui-col-xs3">
                <div class="layui-form-item">
                    <label class="layui-form-label">勘查费用小计（元）</label>
                    <div class="layui-input-block" id="exploreFeeTotal">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">住宿费用小计（元）</label>
                    <div class="layui-input-block" id="hotelFeeTotal">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">采样费用折扣（%）</label>
                    <div class="layui-input-block" id="exploreSubTotalDisc"></div>
                </div>
            </div>
        </div>
        <blockquote class="layui-elem-quote cal-quote">
            其他费用明细
            <div class="disc-box">
                <div class="layui-btn total-txt-show layui-btn-danger">其他费用小计：<span id="otherFeeTotal">0</span> 元</div>
            </div>
        </blockquote>
        <div class="layui-row mt15">
            <div class="layui-col-xs3">
                <div class="layui-form-item">
                    <label class="layui-form-label">报告编制费用（元）</label>
                    <div class="layui-input-block" id="workOutFee">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">其他费用折扣（%）</label>
                    <div class="layui-input-block" id="otherFeeTotalDisc"></div>
                </div>
            </div>
            <div class="layui-col-xs3">
                <div class="layui-form-item">
                    <label class="layui-form-label">专家费用（元）</label>
                    <div class="layui-input-block" id="expertFee">
                    </div>
                </div>
            </div>
            <div class="layui-col-xs3">
                <div class="layui-form-item">
                    <label class="layui-form-label">其他费用类型</label>
                    <div class="layui-input-block" id="otherFeeType">
                    </div>
                </div>
            </div>
            <div class="layui-col-xs3">
                <div class="layui-form-item">
                    <label class="layui-form-label">其他费用小计（元）</label>
                    <div class="layui-input-block" id="otherFee">
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
                    <div class="layui-input-block" id="contractDiscount">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">最终优惠价格（含税）</label>
                    <div class="layui-input-block" id="busiFee">
                    </div>
                </div>
            </div>
            <div class="layui-col-xs3">
                <div class="layui-form-item">
                    <label class="layui-form-label">税费（%）</label>
                    <div class="layui-input-block" id="contractTax">
                    </div>
                </div>
            </div>
            <div class="layui-col-xs3">
                <div class="layui-form-item">
                    <label class="layui-form-label">费用总计（元）</label>
                    <div class="layui-input-block" id="countFee">
                    </div>
                </div>
            </div>
            <div class="layui-col-xs3">
                <div class="layui-form-item">
                    <label class="layui-form-label">费用总计（含税）</label>
                    <div class="layui-input-block" id="countFeeTax">
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
<script type="text/html" id="eiaFileUploadListBar">
<div class="layui-btn-group">
    {{# if((d.inputUserId== ${session.staff.staffId} && ${ifModi})||("s_admin"== "${session.staff.staffCode}")){}}
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