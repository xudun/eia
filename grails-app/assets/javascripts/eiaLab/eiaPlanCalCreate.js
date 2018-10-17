layui.use(['jquery', 'layer', 'form'], function(){
    var $ = layui.jquery,
        layer = layui.layer,
        form = layui.form;

    var $baseName = $('#baseNameVal');
    var $paramNameCn = $('#paramNameCnVal');

    //渲染检测基质
    $.ajax({
        url:"../eiaLabOfferPlan/getBaseList",
        type:"POST",
        data:{},
        dataType: "json",
        success: function (result) {
            for (var i=0; i<result.data.length; i++){
                var str = "<option value='"+result.data[i].id+"'>"+result.data[i].baseName+"</option>";
                $baseName.append(str);
            }
            form.render('select');
        }
    });


    //检测基质Select选中时动作
    form.on('select(baseNameVal)', function(data){
        $("#paramNameCnVal").html("");
        $("#maxSchemeMoney").val("");
        var dataName = $('option:selected',$(data.elem)).text();
        $('#baseName').val(dataName);
        var baseNameVal = data.value;
        if (!baseNameVal) {
            return
        }
        form.render('select');
        //渲染检测项目
        $.ajax({
            url:"../eiaLabOfferPlan/getParamList?labTestBaseId=" + baseNameVal,
            type:"get",
            cache: false,
            async: false,
            dataType: "json",
            success: function (paramResult) {
                $paramNameCn.append('<option value="">请选择检测项目</option>');
                for (var i=0; i<paramResult.data.length; i++){
                    var paramStr = "<option value='"+paramResult.data[i].id+"'>"+paramResult.data[i].paramNameCn+"</option>";
                    $paramNameCn.append(paramStr);
                }
                form.render('select');
            }
        });
    });

    //检测项目Select选中时动作
    form.on('select(paramNameCnVal)', function(data){
        var dataName = $('option:selected',$(data.elem)).text();
        $('#paramNameCn').val(dataName);
        var labTestBaseId = $('#baseNameVal').val();
        var labTestParamId = $('#paramNameCnVal').val();
        //渲染预估最高费用
        $.ajax({
            url: "../eiaLabOfferPlan/getMaxSchemeMoney?labTestParamId="+labTestParamId+"&labTestBaseId="+labTestBaseId,
            type: "get",
            cache: false,
            async: false,
            success: function (result) {
                $('#maxSchemeMoney').val(result.data);
            }
        });
    });

    //提交表单
    form.on('submit(save)', function (data) {
        console.log(data.field);
        var this_val = data.field,
            cur_list_data = JSON.parse(parent.$("#eiaPlanCalListData").val());

        this_val.discount = this_val.discount ? this_val.discount : 100;

        var discountFee = this_val.discount * this_val.maxSchemeMoney / 100;
        var subTotal = discountFee * this_val.pointNum * this_val.freqNum *this_val.dayNum;

        this_val.discountFee = discountFee;
        this_val.subTotal = subTotal;

        cur_list_data.push(this_val);
        parent.$("#eiaPlanCalListData").val(JSON.stringify(cur_list_data));

        //计算主页内的主小计
        var cur_subtotal = parseInt(parent.$("#subtotal").text());
        parent.$("#subtotal").text(cur_subtotal+this_val.subTotal);

        var index = parent.layer.getFrameIndex(window.name);
        parent.layer.close(index)
        // return false;
    });

});