<meta name="layout" content="main" />
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="layout" content="main"/>
    <asset:stylesheet src="/eiaProject/eiaPrintReport.css"/>
</head>
<body class="pb68">
<blockquote class="layui-elem-quote larry-btn fixed-footer">
    <div class="layui-inline pl12">
        <div class="layui-btn-group top-group">
            <a class="layui-btn search_btn pl12" onclick="printReport()"><i class="larry-icon">&#xe89a;</i> 打印</a>
        </div>
    </div>
</blockquote>
<div id="dvData" class="margin0 w760">
        <h1 class="font20 margin0 w150">项目运行责任卡</h1>
    <h2 class="font16 mt30 pl5">项目编号 ：${dutyCardDataMap?.projectNo}</h2>
    <table id="printReport" class="w100per font16 mb50">
        <tr>
            <td>项目名称</td>
            <td colspan="3">${dutyCardDataMap?.projectName}</td>
        </tr>
        <tr>
            <td width="15%">合同签订时间</td>
            <td width="35%">${dutyCardDataMap?.contractDate}</td>
            <td width="15%">预计完成时间</td>
            <td width="35%">${dutyCardDataMap?.completeDate}</td>
        </tr>
        <tr>
            <td>项目负责人（负责人）</td>
            <td>${dutyCardDataMap?.dutyUser}</td>
            <td>项目组成员（录入人）</td>
            <td>${dutyCardDataMap?.projectGroup}</td>
        </tr>
        <tr>
            <td>项目审核人（二审）</td>
            <td>${dutyCardDataMap?.projectApproval2}</td>
            <td>项目审定人（三审）</td>
            <td>${dutyCardDataMap?.projectApproval3}</td>
        </tr>
        <tr>
            <td>项目运行记录</td>
            <td colspan="3" id="record">
                ${dutyCardDataMap?.record}
            </td>
        </tr>
        <tr>
            <td>审核</td>
            <td colspan="3" height="80px"><img src="${dutyCardDataMap?.approval}"></td>
        </tr>
        <tr>
            <td>审定</td>
            <td colspan="3" height="80px"><img src="${dutyCardDataMap?.review}"></td>
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
        var record = $("#record").text()
        console.log(record)
        $("#record").html(record)
    })
</script>
</body>
</html>