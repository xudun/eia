layui.use(['layer', 'table'],function(){
    var $ = layui.$,
        layer = layui.layer,
        table = layui.table;
    var otherFeeType = parent.$("#otherFeeType").val();
    var countFee = parent.$("#countFee").val();
    var contractTax = parent.$("#contractTax").val();
    var otherFee = parent.$("#otherFee").val();
    if (!otherFee) {
        otherFee = 0;
    }
    var tableIns = table.render({
        id:'otherFeeTypeList',
        elem: '#otherFeeTypeList',
        url: "../eiaLabOffer/getOtherFeeTypeList?otherFeeType=" + otherFeeType,
        cellMinWidth: 200,
        toolbar: '#tableTopTmp',
        defaultToolbar:['filter', 'print', 'exports'],
        cols: [
            [
                {type: 'checkbox', title: '选择', fixed: 'left'},
                {field: 'code', title: '费用名称', width: '50%', align: 'center'},
                {field: 'fee', title: '费用价格（元）', width: '50%', align: 'center', edit: 'text'}
            ]
        ],
        page: true,
        limit: 10
    });
    table.on('edit(otherFeeTypeList)', function(obj){
        var value = obj.value; //得到修改后的值
        var num = /^$|^-?\d{1,11}(\.\d{1,2})?$/;
        if (!value.match(num)) {
            layer.msg("请输入数字，最多保留两位小数");
            this.value = "";
        }
    });
    $('.larry-btn a.layui-btn').on('click', function () {
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });
    var active = {
        confirmFee:function() {
            var checkStatus = table.checkStatus('otherFeeTypeList'), data = checkStatus.data;
            var feeIds = "";
            var otherFeeType = "";
            var otherFeeTotal = 0;
            var otherFeeStr = "";
            for (var i in data) {
                feeIds += data[i].id + ",";
                otherFeeType += data[i].code + ",";
                if (data[i].fee) {
                    otherFeeTotal += parseFloat(data[i].fee);
                    otherFeeStr += data[i].code + ":" + data[i].fee + ",";
                } else {
                    otherFeeStr += data[i].code + ":" + "0" + ",";
                }
            }
            parent.$("#otherFeeType").val(otherFeeStr.substring(0, otherFeeStr.length - 1));
            parent.$("#otherFee").val(parseFloat(otherFeeTotal));
            parent.$("#countFee").val(parseInt(parseFloat(countFee) - parseFloat(otherFee) + parseFloat(otherFeeTotal)));
            parent.$("#countFeeTax").val(parseInt((parseFloat(countFee) - parseFloat(otherFee) + parseFloat(otherFeeTotal))*(1 + contractTax/100)));
            layer.msg("选择成功", {icon: 1, time: 1500}, function () {
                var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                parent.layer.close(index); //再执行关闭
            });

        }
    }

    //监听头部工具栏事件
    //监听事件
    table.on('toolbar(otherFeeTypeList)', function(obj){
        switch(obj.event){
            case 'confirmFee':
                var checkStatus = table.checkStatus('otherFeeTypeList'), data = checkStatus.data;
                var feeIds = "";
                var otherFeeType = "";
                var otherFeeTotal = 0;
                var otherFeeStr = "";
                for (var i in data) {
                    feeIds += data[i].id + ",";
                    otherFeeType += data[i].code + ",";
                    if (data[i].fee) {
                        otherFeeTotal += parseFloat(data[i].fee);
                        otherFeeStr += data[i].code + ":" + data[i].fee + ",";
                    } else {
                        otherFeeStr += data[i].code + ":" + "0" + ",";
                    }
                }
                parent.$("#otherFeeType").val(otherFeeStr.substring(0, otherFeeStr.length - 1));
                parent.$("#otherFee").val(parseFloat(otherFeeTotal));
                parent.$("#countFee").val(parseInt(parseFloat(countFee) - parseFloat(otherFee) + parseFloat(otherFeeTotal)));
                parent.$("#countFeeTax").val(parseInt((parseFloat(countFee) - parseFloat(otherFee) + parseFloat(otherFeeTotal))*(1 + contractTax/100)));
                layer.msg("选择成功", {icon: 1, time: 1500}, function () {
                    var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                    parent.layer.close(index); //再执行关闭
                });
                break;
        }
    });
});