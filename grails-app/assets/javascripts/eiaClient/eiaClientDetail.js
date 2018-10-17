layui.use(['jquery', 'table'], function(){
    var $ = layui.jquery,
        table = layui.table;
    var eiaClientId = parent.$('#eiaClientId').val();
    $(function () {
        //渲染客户基本数据
        $.ajax({
            url:"/eia/eiaClient/getEiaClientDataMap?eiaClientId="+eiaClientId,
            type:"POST",
            data:{},
            dataType: "json",
            success: function (data) {
                $('#clientName').html(data.data.clientName);
                $('#clientAddress').html(data.data.clientAddress);
                $('#clientPostCode').html(data.data.clientPostCode);
                $('#clientCorporate').html(data.data.clientCorporate);
                $('#clientFax').html(data.data.clientFax);
            }
        });


        //联系人表格回显
        var tableUrl = "/eia/eiaClient/getEiaClientContactsDataList?eiaClientId="+eiaClientId;
        table.render({
            id: 'contactsList',
            elem: '#contactsList',
            url:tableUrl,
            toolbar: '#tableTopTmp',
            defaultToolbar:['filter', 'print', 'exports'],
            cols: [[
                {fixed: 'left', title: '序号',width:'7%',align: "center",templet: "#indexTable"},
                {field:'contactName',width:'20%', title: '联系人名称',align: "center"},
                {field:'contactPosition',width:'20%', title: '职务',align: "center"},
                {field:'contactPhone',width:'20%', title: '联系电话',align: "center"},
                {field:'contactEmail',width:"20%", title: '电子邮件',align: "center"},
                {field:'clientFax',width:'15%', title: '传真',align: "center"},
            ]],
            page: true,
            even: true,
            limit: 10
        });
        //财务开户表格回显
        var tableUrl = "/eia/eiaClientConfig/invoiceSelectQueryPage?eiaClientId="+eiaClientId+"&invoiceType=1"
        table.render({
            id: 'eiaInvoiceList',
            elem: '#eiaInvoiceList',
            url:tableUrl,
            toolbar: '#tableTopTmp2',
            defaultToolbar:['filter', 'print', 'exports'],
            cols: [[
                {fixed: 'left', title: '序号',width:'7%',align: "center",templet: "#indexTable"},
                {field:'clientName',width:'20%', title: '客户名称',align: "center"},
                {field:'taxRegCode',width:'20%', title: '税务登记代码',align: "center"},
                {field:'bankName',width:'20%', title: '开户行',align: "center"},
                {field:'bankAccount',width:'20%', title: '开户行户名',align: "center"},
                {field:'addrTel',width:"20%", title: '地址及电话',align: "center"},
            ]],
            page: true,
            even: true,
            limit: 10
        });
        //渲染合同表格
        table.render({
            id: 'eiaContractList',
            elem: '#eiaContractList',
            url: '/eia/eiaContract/getEiaContractDataList?eiaClientId='+eiaClientId,
            toolbar: '#tableTopTmp3',
            defaultToolbar:['filter', 'print', 'exports'],
            cols: [[
                {fixed: 'left', title: '序号',width:'5%',align: "center",templet: "#indexTable"},
                {field: 'contractNo', width: '15%', title: '合同编号', align: "center"},
                {field: 'contractName', width: '25%', title: '合同名称', align: "center"},
                {field: 'contractType', width: '15%', title: '合同类型', align: "center"},
                {field: 'contractMoney', width: '15%', title: '合同金额（万元）', align: "center"},
                {field: 'contractDate', width: '15%', title: '合同时间', align: "center"},
                {field: 'taskNo', width: '15%', title: '任务编号', align: "center"},
                {field: 'taskName', width: '20%', title: '任务名称', align: "center"},
                {field: 'inputDept', width: '15%', title: '录入部门', align: "center"},
                {field: 'inputUser', width: '10%', title: '录入人', align: "center"},
                {fixed: 'right', title: '操作', width: '10%', align: "center", toolbar: '#contractTool', align: "center"}
            ]],
            page: true,
            even: true,
            limit: 10
        });
        //渲染报价表格
        if(!eiaClientId){
            eiaClientId = -1;
        }
        table.render({
            id: 'eiaOfferList',
            elem: '#eiaOfferList',
            url: '/eia/eiaContract/getEiaOfferDataList?eiaClientId='+eiaClientId,
            toolbar: '#tableTopTmp4',
            defaultToolbar:['filter', 'print', 'exports'],
            cols: [[
                {fixed: 'left', title: '序号', width: '6%', align: "center", templet: "#indexTable"},
                {field: 'offerNo', width: '15%', title: '报价编号', align: "center"},
                {field: 'offerName', width: '25%', title: '合同名称', align: "center"},
                {field: 'contractType', width: '15%', title: '合同类型', align: "center"},
                {field: 'offerMoney', width: '15%', title: '报价金额（万元）', align: "center"},
                {field: 'offerDate', width: '15%', title: '报价日期', align: "center"},
                {field: 'inputDept', width: '15%', title: '录入部门', align: "center"},
                {field: 'inputUser', width: '10%', title: '录入人', align: "center"},
                {fixed: 'right', title: '操作', width: '10%', align: "center", toolbar: '#offerTool', align: "center"}
            ]],
            page: true,
            even: true,
            limit: 10
        });
        //渲染项目表格
        table.render({
            id: 'eiaProjectList',
            elem: '#eiaProjectList',
            url: '/eia/eiaProject/getEiaProjectDataList?eiaClientId='+eiaClientId,
            toolbar: '#tableTopTmp5',
            defaultToolbar:['filter', 'print', 'exports'],
            cols: [[
                {fixed: 'left', title: '序号',width:'5%',align: "center",templet: "#indexTable"},
                {field: 'projectNo', width: '15%', title: '项目编号', align: "center"},
                {field: 'projectName', width: '30%', title: '项目名称', align: "center"},
                {field: 'buildArea', width: '30%', title: '建设地点', align: "center"},
                {field: 'fileTypeChild', width: '15%', title: '文件类型', align: "center"},
                {field: 'projectMoney', width: '15%', title: '项目价格', align: "center"},
                {field: 'inputDept', width: '15%', title: '录入部门', align: "center"},
                {field: 'inputUser', width: '10%', title: '录入人', align: "center"},
                {field: 'dutyUser', width: '10%', title: '项目负责人', align: "center"},
                {fixed: 'right', title: '操作', width: '10%', align: "center", toolbar: '#projectTool', align: "center"}
            ]],
            page: true,
            even: true,
            limit: 10
        });
        //监听合同表格工具条
        table.on('tool(eiaContractList)', function (obj) {
            var data = obj.data;
            //$('#eiaContractId').val(data.id)
            if (obj.event === 'eiaCheck') {    //编辑
                var pageUrl = '/eia/eiaContract/eiaContractDetail?eiaContractId=' + data.id;
                var index = layer.open({
                    title: ' ',
                    type: 2,
                    shade: false,
                    maxmin: true,
                    skin: 'larry-green',
                    area: ['100%', '100%'],
                    content: pageUrl,
                    success: function (layero, index) {
                        var body = layer.getChildFrame('body', index);
                        body.find('#contractId').val(data.id);
                    },
                    end: function () {
                        $('#offerId').val("");
                    },
                    min: function () {
                        $(".layui-layer-title").text("查看合同");
                    },
                    restore: function () {
                        $(".layui-layer-title").text(" ");
                    }
                });
            }
        });
        //监听报价表格工具条
        table.on('tool(eiaOfferList)', function (obj) {
            var data = obj.data;
            //$('#eiaContractId').val(data.id)
            if (obj.event === 'eiaCheck') {    //编辑
                var pageUrl = '/eia/eiaContract/eiaOfferDetail?eiaOfferId=' + data.id;
                var index = layer.open({
                    title: ' ',
                    type: 2,
                    shade: false,
                    maxmin: true,
                    skin: 'larry-green',
                    area: ['100%', '100%'],
                    content: pageUrl,
                    success: function (layero, index) {
                        var body = layer.getChildFrame('body', index);
                        body.find('#contractId').val(data.id);
                    },
                    end: function () {
                        $('#offerId').val("");
                    },
                    min: function () {
                        $(".layui-layer-title").text("查看合同");
                    },
                    restore: function () {
                        $(".layui-layer-title").text(" ");
                    }
                });
            }
        });
        //监听项目表格工具条
        table.on('tool(eiaProjectList)', function (obj) {
            var data = obj.data;
            //$('#eiaContractId').val(data.id)
            if (obj.event === 'eiaCheck') {    //编辑
                pageUrl = '/eia/eiaProject/eiaProjectDetail?eiaProjectId=' + data.id;
                $("#eiaProjectId").val(data.id);
                $("#tableNameId").val(data.id);
                var index = layer.open({
                    title: ' ',
                    type: 2,
                    shade: false,
                    maxmin: true,
                    skin: 'larry-green',
                    area: ['100%', '100%'],
                    content: pageUrl,
                    success: function (layero, index) {
                        var body = layer.getChildFrame('body', index);
                        body.find('').val(data.eiaClientId);
                    },
                    end: function () {
                        table.reload("eiaProjectList");
                        $('#eiaClientId').val("");
                        $('#eiaTaskId').val("");
                    },
                    min: function () {
                        $(".layui-layer-title").text("查看客户");
                    },
                    restore: function () {
                        $(".layui-layer-title").text(" ");
                    }
                });
            }
        });

    });
});