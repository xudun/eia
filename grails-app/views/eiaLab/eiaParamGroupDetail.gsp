<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title></title>
    <meta name="layout" content="main"/>
    <asset:javascript src="/eiaLab/eiaParamGroupDetail.js"/>
</head>
<body>
<div class="layui-fluid larry-wrapper pt0">
    <div class="layer-title">
        <fieldset class="layui-elem-field layui-field-title site-title">
            <legend id="title">
                <a name="methodRender">查看检测套餐</a>
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
                    <label class="layui-form-label">套餐名称</label>
                    <div class="layui-input-block" id="groupName">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">批量折扣（%）</label>
                    <div class="layui-input-block" id="groupDiscount">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">批量频次</label>
                    <div class="layui-input-block" id="groupFreqNum">
                    </div>
                </div>
            </div>
            <div class="layui-col-xs6">
                <div class="layui-form-item">
                    <label class="layui-form-label">套餐价格（元）</label>
                    <div class="layui-input-block" id="groupFee">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">批量点位</label>
                    <div class="layui-input-block" id="groupPointNum">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">批量天数</label>
                    <div class="layui-input-block" id="groupDayNum">
                    </div>
                </div>
            </div>
        </div>
        <input type="hidden" id="labTestParamGroupId"/>
    </form>

    <table id="labTestCapGroupList" lay-filter="labTestCapGroupList"></table>
</div>
<script type="text/html" id="tableTopTmp">
<div class="table-top">
    检测套餐明细
</div>
</script>
</body>
</html>