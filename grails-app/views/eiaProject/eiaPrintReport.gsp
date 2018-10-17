<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="layout" content="main"/>
    <asset:stylesheet src="/eiaProject/eiaPrintReport.css"/>
</head>
<body>
<blockquote class="layui-elem-quote larry-btn">
    <g:if test = "${reportType == 'YS'}">
        <span>一审意见</span>
    </g:if>
    <g:elseif test = "${reportType == 'ES'}">
        <span>二审意见</span>
    </g:elseif>
    <g:elseif test = "${reportType == 'SS'}">
        <span>三审意见</span>
    </g:elseif>
    <div class="layui-inline pl12">
        <div class="layui-btn-group top-group">
            <a class="layui-btn search_btn pl12" onclick="printReport()"><i class="larry-icon">&#xe89a;</i> 打印</a>
        </div>
    </div>
</blockquote>
<div id="dvData" class="margin0 w760">
    <g:if test = "${reportType == 'YS'}">
        <h1 class="font20 margin0 w300">环境影响报告书（表）审查单</h1>
    </g:if>
    <g:elseif test = "${reportType == 'ES'}">
        <h1 class="font20 margin0 w300">环境影响报告书（表）审核单</h1>
    </g:elseif>
    <g:elseif test = "${reportType == 'SS'}">
        <h1 class="font20 margin0 w300">环境影响报告书（表）审定单</h1>
    </g:elseif>
    <h2 class="font16 mt30 pl5 mb10"> 项目编号：${project?.projectNo}</h2>
    <table id="printReport" class="w100per font16">
        <tr>
            <td width="15%">项目名称</td>
            <td width="85%" colspan="3">${project?.projectName}</td>
        </tr>
        <tr>
            <td width="15%">客户名称</td>
            <td width="35%">${eiaClient?.clientName}</td>
            <td width="15%">项目所在地</td>
            <td width="35%">${project?.buildArea}</td>
        </tr>
        <tr>
            <td>项目负责人</td>
            <td>${project?.dutyUser}</td>
            <td>其他编制人员</td>
            <td>${project?.inputUser}</td>
        </tr>
    </table>
    <div id="reportLeft" class="font16">
        <g:if test = "${reportType == 'YS'}">
            <div class="pb5">一审意见：</div>
        </g:if>
        <g:elseif test = "${reportType == 'ES'}">
            <div class="pb5">二审意见：</div>
        </g:elseif>
        <g:elseif test = "${reportType == 'SS'}">
            <div class="pb5">审定意见：</div>
        </g:elseif>
        <div id="suggestion" class="ml5 mr5" style="min-height: 300px">
            ${raw(planItem?.opinion)}
        </div>
        <div>
            <br>
            <span class="w64per pull-left">日期：${planItem?.actEndDate?.format("yyyy-MM-dd")}</span>
            <br>
            <span class="w64per pull-left">审核人：${planItem?.nodeUserName}</span>
        </div>
    </div>
    <div id="reportRight" class="font16">
        <div class="pb5">修改意见：</div>
        <div id="modInstructions" class="ml5 mr5" style="min-height: 300px">
            ${raw(modiContent)}
        </div>
        <div>
            <br>
            <span class="w64per pull-left">日期：${workFlowNode?.dateCreated?.format("yyyy-MM-dd")}</span>
            <br>
            <span class="w64per pull-left">录入人：${project?.inputUser}</span>
        </div>
    </div>
    <table id="printReport" class="w100per font16 mb50">
        <tr>
            <td colspan="4">
                <div class="pb5">修改结果认可意见：</div>
                <div style="min-height: 160px">
                    &nbsp;&nbsp;
                </div>
                <div>
                    <span class="w25per float-right" style="line-height: 66px">日期：&nbsp;&nbsp;</span>

                    <span class="float-right">签名：<img src="${signImagePath}"></span>
                </div>
            </td>
        </tr>
    </table>
</div>
<script type="text/javascript">
    function printReport(){
        var oldstr = document.body.innerHTML;
        var printData=document.getElementById('dvData').innerHTML
        document.body.innerHTML=printData;
        window.print();
        document.body.innerHTML = oldstr;
    }
    $(function(){
        var heightLeft = $("#suggestion").prop("scrollHeight");
        var heightRight = $("#modInstructions").prop("scrollHeight");
        if(heightLeft>heightRight){
            $("#suggestion").css("height",heightLeft+"px");
            $("#modInstructions").css("height",heightLeft+"px");
        }else{
            $("#suggestion").css("height",heightRight+"px");
            $("#modInstructions").css("height",heightRight+"px");
        }
    })
</script>
</body>
</html>