<%@ page contentType="text/html;charset=UTF-8" import="com.lheia.eia.common.FuncConstants" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>任务看板</title>
    <meta name="layout" content="main"/>
    <asset:stylesheet src="/eiaBoard/eiaTaskBoard.css"/>
    <asset:javascript src="/eiaBoard/eiaTaskBoard.js"/>
    <asset:javascript src="/eiaInvoice/tableAddSubTotalRow.js"/>
</head>

<body>
<div class="layui-fluid larry-wrapper pt15">
    <div class="layui-row layui-col-space15" id="Minfo">
        <div class="layui-col-xs12 layui-col-sm12 layui-col-md12 layui-col-lg12">
            <div class="head-info larry-font-theme layui-bg-green">
                <div><span id="weather"></span></div>
                <i class="larry-icon larry-guanbi1" id="closeInfo"></i>
            </div>
        </div>
    </div>

    <!-- 快捷导航 -->
    <div class="layui-row mt15 larry-shortcut layui-col-space15" id="shortcut">
        <div class="layui-col-xs12 layui-col-sm6 layui-col-md4 layui-col-lg2 larry-col">
            <section class="shortcut larry-ico-rotate">
                <a class="pos-r lay" data-type="task">
                    <div class="larry-ico larry-bg-blue"><i class="larry-icon">&#xe93f;</i></div>

                    <div class="larry-value larry-bg-gray">
                        <cite>我的任务<span class="layui-badge" id="taskNums">100</span></cite>

                    </div>
                </a>
            </section>
        </div>

        <div class="layui-col-xs12 layui-col-sm6 layui-col-md4 layui-col-lg2 larry-col">
            <section class="shortcut pos-r larry-ico-rotate">
                <a class="pos-r lay" data-type="contract">
                    <div class="larry-ico larry-bg-red"><i class="larry-icon">&#xea04;</i></div>

                    <div class="larry-value larry-bg-gray">
                        <cite>我的合同 <span class="layui-badge" id="contractNums">50</span></cite>
                    </div>
                </a>
            </section>
        </div>

        <div class="layui-col-xs12 layui-col-sm6 layui-col-md4 layui-col-lg2 larry-col">
            <section class="shortcut pos-r larry-ico-rotate">
                <a class="pos-r lay" data-type="project">
                    <div class="larry-ico larry-bg-purple"><i class="larry-icon">&#xe88e;</i></div>

                    <div class="larry-value larry-bg-gray">
                        <cite>我的项目<span class="layui-badge" id="projectNums">100</span></cite>
                    </div>
                </a>
            </section>
        </div>

        <div class="layui-col-xs12 layui-col-sm6 layui-col-md4 layui-col-lg2 larry-col">
            <section class="shortcut pos-r larry-ico-rotate">
                <a class="pos-r lay" data-type="client">
                    <div class="larry-ico larry-bg-two"><i class="larry-icon">&#xe910;</i></div>
                    <div class="larry-value larry-bg-gray">
                        <cite>我的客户<span class="layui-badge" id="clientNums"></span></cite>
                    </div>
                </a>
            </section>
        </div>

        <div class="layui-col-xs12 layui-col-sm6 layui-col-md4 layui-col-lg2 larry-col">
            <section class="shortcut pos-r larry-ico-rotate">
                <a class="pos-r lay" data-type="config">
                    <div class="larry-ico larry-bg-orange"><i class="larry-icon">&#xe645;</i></div>
                    <div class="larry-value larry-bg-gray">
                        <cite class="un-nums">我的设置</cite>
                    </div>
                </a>
            </section>
        </div>

        <div class="layui-col-xs12 layui-col-sm6 layui-col-md4 layui-col-lg2 larry-col">
            <section class="shortcut pos-r larry-ico-rotate">
                <a class="pos-r lay" data-type="message">
                    <div class="larry-ico larry-bg-green"><i class="larry-icon">&#xe8e6;</i></div>
                    <div class="larry-value larry-bg-gray">
                        <cite class="un-nums">系统消息</cite>
                    </div>
                </a>
            </section>
        </div>
    </div>

    <!--tab选项卡-->
    <div class="layui-tab mt15 layui-tab-brief panel-tab" lay-filter="panel-tab">
        <ul class="layui-tab-title">
            %{--
                        <li class="layui-this" tab-name="workFlowBusi">待办事项<span class="layui-badge" id="workFlowBusiNums" ></span></li>
            --}%
            <li class="layui-this" tab-name="EiaContract">合同报价待办<span class="layui-badge" id="eiaContractNums"></span>
            </li>
            <li tab-name="EiaLabOffer">监测待办<span class="layui-badge" id="eiaLabOfferNums"></span></li>
            <li tab-name="EiaProject">项目待办<span class="layui-badge" id="eiaProjectNums"></span></li>
            <li tab-name="EiaCert">资质待办<span class="layui-badge" id="eiaCertNums"></span></li>
            <li tab-name="EiaStamp">印章待办<span class="layui-badge" id="eiaStampNums"></span></li>
            <li tab-name="workFlowComBusi">已办事项<span class="layui-badge" id="workFlowComBusiNums"></span></li>

            %{-- <li>消息提醒</li>--}%
        </ul>

        <div class="layui-tab-content">
            <!--合同待办事项-->
            <div class="layui-tab-item layui-show">
                <!--列表-->
                <div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
                    <table id="eiaContractList" lay-filter="eiaContractList"></table>
                    <input type="hidden" id="proWorkFlowContractTitle" value="">
                </div>
            </div>
            <!--宇相报价待办事项-->
            <div class="layui-tab-item">
                <!--列表-->
                <div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
                    <table id="eiaLabOfferList" lay-filter="eiaLabOfferList"></table>
                    <input type="hidden" id="proWorkFlowLabTitle" value="">
                </div>
            </div>
            <!--项目待办事项-->
            <div class="layui-tab-item">
                <!--列表-->
                <div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
                    <table id="eiaProjectList" lay-filter="eiaProjectList"></table>
                    <input type="hidden" id="proWorkFlowProjectTitle" value="">
                </div>
            </div>
            <!--资质待办事项-->
            <div class="layui-tab-item">
                <!--列表-->
                <div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
                    <table id="eiaCertList" lay-filter="eiaCertList"></table>
                    <input type="hidden" id="proWorkFlowCertTitle" value="">
                </div>
            </div>
            <!--印章待办事项-->
            <div class="layui-tab-item">
                <!--列表-->
                <div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
                    <table id="eiaStampList" lay-filter="eiaStampList"></table>
                    <input type="hidden" id="proWorkFlowStampTitle" value="">
                </div>
            </div>

            <!--已办事项-->
            <div class="layui-tab-item">
                <!--列表-->
                <div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">
                    <table id="eiaWorkFlowComList" lay-filter="eiaWorkFlowComList"></table>
                    <input type="hidden" id="proWorkFlowComTitle" value="">
                </div>
            </div>
            <!--消息提醒-->
            %{--<div class="layui-tab-item">--}%
            %{--<!--查询及添加框-->--}%
            %{--<blockquote class="layui-elem-quote larry-btn">--}%
            %{--<div class="layui-inline">--}%
            %{--<div class="layui-input-inline">--}%
            %{--<input type="text" class="layui-input" id="messageType" name="messageType" placeholder="消息类型">--}%
            %{--</div>--}%
            %{--</div>--}%
            %{--<div class="layui-btn-group top-group">--}%
            %{--<a class="layui-btn search_btn pl12" data-type="getSelectMessage"><i class="larry-icon">&#xe939;</i> 查询</a>--}%
            %{--</div>--}%
            %{--</blockquote>--}%

            %{--<!--列表-->--}%
            %{--<div class="layui-col-lg12 layui-col-md12 layui-col-sm12 layui-col-xs12">--}%
            %{--<table id="messageList" lay-filter="messageList"></table>--}%
            %{--</div>--}%
            %{--</div>--}%
        </div>
    </div>
</div>
<!--项目id-->
<input type="hidden" id="tableNameId" name="tableNameId" value="">
<input type="hidden" id="tableName" name="tableName" value="">

<script type="text/html" id="indexTable">
<span>{{d.LAY_TABLE_INDEX + 1}}</span>
</script>
<script type="text/html" id="eiaWorkFlowListTool">
<div class="layui-btn-group">
    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaEdit"><i class="larry-icon">&#xea21;</i></a>
</div>
</script>
<script type="text/html" id="eiaWorkFlowComListTool">
<div class="layui-btn-group">
    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaEdit"><i class="larry-icon">&#xea21;</i></a>
</div>
</script>
<script type="text/html" id="messageListTool">
<div class="layui-btn-group">
    <a class="layui-btn layui-btn-normal layui-btn-sm" lay-event="eiaCheck"><i class="larry-icon">&#xe896;</i></a>
</div>
</script>
<script type="text/html" id="depTp">
{{#  if(!d.ifStaff){ }}
{{#  if(!d.LAY_TABLE_INDEX){ }}
<a class="depLink"><i class="larry-icon tdIcon">&#xe8e2;</i>&nbsp;&nbsp;{{d.orgName}}</a>
{{#  } else { }}
<a class="depLink"><i class="larry-icon tdIcon">&#xe849;</i>&nbsp;&nbsp;{{d.orgName}}</a>
{{#  } }}
{{#  } else { }}
<a class="">{{d.orgName}}</a>
{{#  } }}
</script>
<script type="text/html" id="proTypeTp">
{{#  if(d.ifParent){ }}

{{#  if(!d.LAY_TABLE_INDEX){ }}
<a class="depLink"><i class="larry-icon tdIcon">&#xe8e2;</i>&nbsp;&nbsp;{{d.codeDesc}}</a>
{{#  } else { }}
<a class="depLink"><i class="larry-icon tdIcon">&#xe849;</i>&nbsp;&nbsp;{{d.codeDesc}}</a>
{{#  } }}

{{#  } else { }}
<a class="">{{d.codeDesc}}</a>
{{#  } }}
</script>
<script type="text/html" id="ifReadTp">
{{#  if(d.ifRead == "已读"){ }}
<span class="layui-badge layui-bg-gray">{{d.ifRead}}</span>
{{#  } else { }}
<span class="layui-badge layui-bg-blue">{{d.ifRead}}</span>
{{#  } }}
</script>
<script type="text/html" id="messageTypeTp">
{{#  if(d.messageType == "收到消息"){ }}
<span class="layui-badge layui-bg-blue">{{d.messageType}}</span>
{{#  } else { }}
<span class="layui-badge layui-bg-green">{{d.messageType}}</span>
{{#  } }}
%{--合同页签的查询框--}%
</script>
<script type="text/html" id="tableTopTmp1">
<div class="layui-inline">
    <div class="layui-input-inline">
        <input type="text" name="workFlowName" value="" id="workFlowContractTitle" placeholder="审批标题、创建人"
               class="layui-input larry-search-input">
    </div>
</div>

<div class="layui-btn-group top-group">
    <a class="layui-btn search_btn pl12" lay-event="getSelectWorkFlow" data-type="getSelectWorkFlow"><i
            class="larry-icon">&#xe939;</i> 查询</a>
</div>
</script>
%{--监测页查询框--}%
<script type="text/html" id="tableTopTmp2">
<div class="layui-inline">
    <div class="layui-input-inline">
        <input type="text" name="workFlowName" value="" id="workFlowLabTitle" placeholder="审批标题、创建人"
               class="layui-input larry-search-input">
    </div>
</div>

<div class="layui-btn-group top-group">
    <a class="layui-btn search_btn pl12" lay-event="getSelectWorkLabFlow" data-type="getSelectWorkLabFlow"><i
            class="larry-icon">&#xe939;</i> 查询</a>
</div>
</script>

%{--项目页查询框--}%
<script type="text/html" id="tableTopTmp3">
<div class="layui-inline">
    <div class="layui-input-inline">
        <input type="text" name="workFlowName" value="" id="workFlowProjectTitle"
               placeholder="审批标题、创建人" class="layui-input larry-search-input">
    </div>
</div>

<div class="layui-btn-group top-group">
    <a class="layui-btn search_btn pl12" lay-event="getSelectWorkProjectFlow" data-type="getSelectWorkProjectFlow"><i
            class="larry-icon">&#xe939;</i> 查询</a>
</div>
</script>

%{--资质页查询框--}%
<script type="text/html" id="tableTopTmp4">
<div class="layui-inline">
    <div class="layui-input-inline">
        <input type="text" name="workFlowName" value="" id="workFlowCertTitle" placeholder="审批标题、创建人" class="layui-input larry-search-input">
    </div>
</div>

<div class="layui-btn-group top-group">
    <a class="layui-btn search_btn pl12" lay-event="getSelectWorkCertFlow" data-type="getSelectWorkCertFlow"><i class="larry-icon">&#xe939;</i> 查询</a>
</div>
</script>


%{--印章页查询框--}%
<script type="text/html" id="tableTopTmp5">
<div class="layui-inline">
    <div class="layui-input-inline">
        <input type="text" name="workFlowName" value="" id="workFlowStampTitle" placeholder="审批标题、创建人" class="layui-input larry-search-input">
    </div>
</div>

<div class="layui-btn-group top-group">
    <a class="layui-btn search_btn pl12" lay-event="getSelectWorkFlowStamp" data-type="getSelectWorkFlowStamp"><i class="larry-icon">&#xe939;</i> 查询</a>
</div>
</script>

%{--已办事项查询框--}%
<script type="text/html" id="tableTopTmp6">
<div class="layui-inline">
    <div class="layui-input-inline">
        <input type="text" name="workFlowName" value="" id="workFlowComTitle" placeholder="审批标题、创建人" class="layui-input larry-search-input">
    </div>
</div>

<div class="layui-btn-group top-group">
    <a class="layui-btn search_btn pl12" lay-event="getSelectWorkComFlow" data-type="getSelectWorkComFlow"><i class="larry-icon">&#xe939;</i> 查询</a>
</div>
</script>
</body>
</html>