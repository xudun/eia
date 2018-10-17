<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="layout" content="main"/>
    <asset:stylesheet src="/eiaProject/eiaPrintReport.css"/>
</head>
<body>
<blockquote class="layui-elem-quote larry-btn">
    打印归档
    <div class="layui-inline pl12">
        <div class="layui-btn-group top-group">
            <a class="layui-btn search_btn pl12" onclick="printReport()"><i class="larry-icon">&#xe89a;</i> 打印</a>
        </div>
    </div>
</blockquote>
<div id="dvData" class="margin0 w710">
    <h1 class="font20 margin0 w300 mt30">业务技术档案存档资料袋</h1>
    <table id="printReport" class="w100per font16 mt30">
        <tr>
            <td>项目编号</td>
            <td colspan="5">${resMap.projectNo}</td>
        </tr>
        <tr>
            <td>项目名称</td>
            <td colspan="5">${resMap.projectName}</td>
        </tr>
        <tr>
            <td width="18%" rowspan="2" class="valign-middle">业务类别</td>
            <td width="18%" rowspan="2" class="valign-middle">${resMap.fileTypeChild}</td>
            <td width="10%">录入人</td>
            <td width="18%">${resMap.inputUser}</td>
            <td width="10%" rowspan="2" class="valign-middle">所属部门</td>
            <td width="20%" rowspan="2" class="valign-middle">${resMap.inputDept}</td>
        </tr>
        <tr>
            <td>负责人</td>
            <td>${resMap.projectLeader}</td>
        </tr>
        <tr>
            <td colspan="6">
                <span class="w50per float-left">1.委托书/项目合同：<span class="pl15">${resMap.ht}</span></span>
                <span class="w50per float-left">2.外委合同：<span class="pl15">${resMap.wwht}</span></span>
            </td>
        </tr>
        <tr>
            <td colspan="6">
                <span class="w50per float-left">3.监测报告：<span class="pl15">&nbsp;</span></span>
                <span class="w50per float-left">4.公参资料：<span class="pl15">&nbsp;</span></span>
            </td>
        </tr>
        <tr>
            <td colspan="6">
                <span class="w50per float-left">5.业务最终报告：<span class="pl15">&nbsp;</span></span>
                <span class="w50per float-left">6.运行责任卡及三级审核意见：<span class="pl15">&nbsp;</span></span>
            </td>
        </tr>
        <tr>
            <td colspan="6">
                <span class="w50per float-left">7.修改清单：<span class="pl15">&nbsp;</span></span>
                <span class="w50per pull-right">8.专家意见：<span class="pl15">&nbsp;</span></span>
            </td>
        </tr>
        <tr>
            <td colspan="6">
                <span class="w50per float-left">9.评估意见：<span class="pl15">&nbsp;</span></span>
            </td>
        </tr>
        <tr>
            <td colspan="6">
                10.批复（审查）文件:<span class="ml10 mr10">存</span>文号：
                <span style="text-decoration:underline">${wordNo}</span>
                <span class="ml10 mr10">审批（审查）部门:</span>
                <span style="text-decoration:underline">${resMap.competentDept}</span>
                <span class="ml10 mr10">审批（审查）时间:</span>
                <span style="text-decoration:underline">${approvalDate}</span>
            </td>
        </tr>
        <tr>
            <td colspan="6">
                其他材料（以下材料非必须）：
            </td>
        </tr>
        %{--<tr>
            <td colspan="6">
                <span class="w50per pull-left">项目现场踏勘影像资料：<span class="pl15">${fileArc.xmxctkyxzl}</span></span>
                <span class="w50per pull-left">项目评审影像记录：<span class="pl15">${fileArc.xmpsyxjl}</span></span>
            </td>
        </tr>--}%
        <tr>
            <td colspan="6">
                <div class="pb5">缺失材料说明：</div>
                <div style="min-height: 150px">
                    &nbsp;
                </div>
            </td>
        </tr>
        <tr>
            <td class="valign-middle">接收人（签字）</td>
            <td colspan="5"><img src="${resMap.signImagePath}"></td>
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
</script>
</body>
</html>
