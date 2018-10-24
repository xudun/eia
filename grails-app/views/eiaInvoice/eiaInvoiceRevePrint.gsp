<meta name="layout" content="main" />
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="layout" content="main"/>
    <asset:stylesheet src="/eiaProject/eiaPrintReport.css"/>
</head>
<body>
<blockquote class="layui-elem-quote larry-btn">
    发票申请单
    <div class="layui-inline pl12">
        <div class="layui-btn-group top-group">
            <a class="layui-btn search_btn pl12" onclick="printReport()"><i class="larry-icon">&#xe89a;</i> 打印</a>
        </div>
    </div>
</blockquote>
<div id="dvData" class="margin0 w760">
    <h1 class="font20 margin0 w150">发票申请单</h1>
    <h2 class="font16 mt30 pl5">合同号 ：${eiaInvoice?.contractNo}</h2>
    <table id="printReport"  class="w100per font-16 mb15">
        <tr>
            <td width="15%">项目负责人</td>
            <td width="35%">
                ${eiaInvoice?.inputUser}
            </td>
            <td width="15%">开票申请人</td>
            <td width="35%">
                ${eiaInvoice?.inputUser}
            </td>
        </tr>
        <tr>
            <td width="15%">开票单位名称</td>
            <td width="35%">
                ${eiaInvoice?.clientAccountName}
            </td>
            <td width="15%">税务登记代码</td>
            <td width="35%">
                ${eiaInvoice?.taxRegCode}
            </td>
        </tr>
        <tr>
            <td width="15%">开户行户名</td>
            <td width="35%">
                ${eiaInvoice?.bankName}
            </td>
            <td width="15%">开户行账号</td>
            <td width="35%">
                ${eiaInvoice?.bankAccount}
            </td>

        </tr>
        <tr>
            <td width="15%">地址及电话</td>
            <td width="35%">
                ${eiaInvoice?.addrTel}
            </td>
            <td width="15%">开票类别</td>
            <td width="35%">
                <div class="pull-left w120">
                    ${eiaInvoice?.billType}
                </div>
            </td>

        </tr>
        <tr>
            <td width="15%">发票类别</td>
            <td width="35%">
                ${eiaInvoice?.invoiceType}
            </td>
            <td width="15%">开票金额(万元)</td>
            <td width="35%">
                ${eiaInvoice?.billMoney}
            </td>
        </tr>

        <tr>
            <td width="15%">预计收款日期</td>
            <td width="35%">
                ${eiaInvoice?.estDate?.format("yyyy-MM-dd")}
            </td>
            <td width="15%">实际收款情况</td>
            <td width="35%">
                ${eiaInvoice?.realMoney}
            </td>
        </tr>
        <tr>
            <td width="15%">发票备注</td>
            <td colspan="3" width="35%">
                ${eiaInvoice?.memo}
            </td>
        </tr>
        <tr>
            <td width="15%"><font><b>部门领导批示</b></font></td>
            <td width="35%">
                <img src="${deptUserSign}">
            </td>
            <td width="15%"><b>领票人签字</b></td>
            <td width="35%">
                <img src="${staffSign}">
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