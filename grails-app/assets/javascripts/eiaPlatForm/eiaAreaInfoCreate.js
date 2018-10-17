layui.use(['layer', 'table', 'jquery', 'form'], function(){
    var $ = layui.jquery,
        layer = layui.layer,
        table = layui.table,
        form = layui.form;

    var $speInpContainers = $('.dynInputs .layui-col-xs6');
    var eiaAreaInfoId = parent.$('#eiaAreaInfoId').val();
    $('#eiaAreaInfoId').val(eiaAreaInfoId);

    //工具函数：
    //在url中获取指定参数值
    var getParamFromUrl = function(url,param){
        if(url.indexOf('?')!==-1){
            var params = url.split("?")[1].split('&');
            for(var i=0; i<params.length; i++){
                if(params[i].indexOf(param) !== -1){
                    return params[i].replace(param+"=","");
                }
            }
            // console.log('该url中无参数'+param);
        }else{
            // console.log('该url中无参数');
        }

    };
    //设置select指定项选中
    var selectOptionByVal = function ($select, val) {
        var opt_arr = $('option',$($select));
        for(var i=0; i<opt_arr.length; i++){
            if($(opt_arr[i]).val() == val){
                $(opt_arr[i]).prop("selected",true);
                break;
            }
        }
    };

    //页面类型：0:新建 ； 1：编辑
    var pageType = getParamFromUrl(document.location.href,"pageType");

    //设置页面名称
    var $pageTitle = $('.pageTitle');
    pageType == 0 ? $pageTitle.html('新增区域/企业信息') : $pageTitle.html('编辑区域/企业信息');

    //重置按钮显隐
    pageType == 0 ? "" : $('.resetBtn').hide();

    //下拉树 所属区域
    $("#regionNameDrop").dropDownForZ({
        url:'/eia/eiaDomainCode/getTree?domain='+"PROVINCE_CITY",
        width:'99%',
        height:'350px',
        disableParent: true,
        selecedSuccess:function(data){  //选中回调
            if(!data.isParent){
                var temp = {
                    id: data.id,
                    name: data.name
                };
                $("#regionName").val(JSON.stringify(temp));
            }
        }
    });

    // 渲染 数据类别Select
    $.ajax({
        url:'/eia/eiaProject/getTreeByDomain?domain='+"DATA_CURRENCY",
        type:"POST",
        data:{},
        dataType: "json",
        success: function (data) {
            for(var i=0; i<data.length; i++){
                var str = "<option value='"+data[i].name+"'>"+data[i].name+"</option>";
                $('#dataType').append(str);
            }
            form.render('select');
        }
    });

    //监听 数据类别 渲染动态数据项
    form.on('select(dataType)', function(data){
        //渲染动态数据项（新建）
        $.ajax({
            url:"../eiaPlatForm/getEiaAreaInfoByDataType",
            type:"POST",
            data:{"dataType":data.value},
            dataType: "json",
            async: true,
            success: function (result) {
                $speInpContainers.empty();
                var con_index = 0;
                if (result.data) {
                    for (var i = 0; i < result.data.length; i++) {
                        inputs[result.data[i]].create.call(this, "", $speInpContainers.eq(con_index++ % 2));
                    }
                }
                form.render('select');
            }
        });
    });

    //基本信息回显
    $.ajax({
        url:"../eiaPlatForm/getEiaAreaInfoDataMap?eiaAreaInfoId=" + eiaAreaInfoId,
        type:"POST",
        data:{},
        dataType: "json",
        success: function (result) {
            if (result.data) {
                $('#projectName').val(result.data.projectName);
                $('#geographicEast').val(result.data.geographicEast);
                $('#geographicStartEast').val(result.data.geographicStartEast);
                $('#geographicEndEast').val(result.data.geographicEndEast);
                $('#geographicNorth').val(result.data.geographicNorth);
                $('#geographicStartNorth').val(result.data.geographicStartNorth);
                $('#geographicEndNorth').val(result.data.geographicEndNorth);
                if (result.data.regionName) {
                    $('#regionNameDropCode').val(result.data.regionName.id);
                    $('#regionNameDrop').val(result.data.regionName.name);
                    $('#regionName').val(result.data.regionName);
                }
                selectOptionByVal($('#dataType'), result.data.dataType);
                form.render('select');
                //渲染动态数据项（编辑）
                $.ajax({
                    url: "../eiaPlatForm/getEiaAreaInfoByDataType?eiaAreaInfoId=" + eiaAreaInfoId,
                    type: "POST",
                    data: {"dataType": result.data.dataType},
                    dataType: "json",
                    async: true,
                    success: function (inpResult) {
                        console.log(inpResult);
                        $speInpContainers.empty();
                        var con_index = 0;
                        for (var name in inpResult.data) {
                            inputs[name].create.call(this, result.data[name], $speInpContainers.eq(con_index++ % 2));
                        }
                        form.render('select');
                    }
                });
                $('.fileAddBtn').removeClass("display-none");
            } else {
                $('.noticeTag').removeClass("display-none");
            }
        }
    });

    $('.resetBtn').click(function () {
        $('input:hidden').val("");
    });

    //表单提交
    form.on('submit(save)', function(data){
        var loadingIndex = layer.load(1, {time: 10*1000,shade: 0.1});
        console.log(data.field); //当前容器的全部表单字段，名值对形式：{name: value}
        var actionUrl = "../eiaPlatForm/eiaAreaInfoSave";
        $.post(actionUrl, data.field, function (result) {
            if (result.code == 0) {
                layer.msg('保存成功', {icon: 1, time: 1000,shade: 0.1}, function () {
                    if (result.data.id) {
                        $('#eiaAreaInfoId').val(result.data.id);
                        $('#tableId').val(result.data.id);
                        tableId = result.data.id;
                        $('.fileAddBtn').removeClass("display-none");
                        $('.noticeTag').addClass("display-none");
                    }
                    parent.layui.table.reload("eiaAreaInfoList");
                });
            } else {
                layer.msg('保存失败', {icon: 2, time: 2000,shade: 0.1});
            }
            layer.close(loadingIndex);
        });
        return false;
    });
    /**
     * 文件上传
     */
    var tableName = "EiaAreaInfo";
    var tableId = eiaAreaInfoId;
    $("#tableName").val(tableName);
    $("#tableId").val(tableId);
    $('.larry-btn a.layui-btn').click(function () {
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });

    /**
     * 渲染文件上传列表
     */
    table.render({
        id: 'eiaFileUploadList',
        elem: '#eiaFileUploadList',
        url:'/eia/eiaFileUpload/getEiaFileUploadDataList?tableName=' + tableName + '&tableId=' + tableId,
        toolbar: '#tableTopTmp',
        defaultToolbar:['filter', 'print', 'exports'],
        cols: [[
            {type: 'numbers', title: '序号', align: "center"},
            {field:'fileName', title: '原文件名',align: "center"},
            {field:'inputUser', title: '上传人',align: "center"},
            {field:'uploadDate', title: '上传时间',align: "center"},
            {field:'fileSize', title: '大小',align: "center"},
            {field:'fileUploadType', title: '文件类型',align: "center"},
            {fixed: 'right', title: '操作' ,align: "center", toolbar: '#eiaFileUploadListBar',align: "center"}
        ]],
        page: true,
        even: true,
        limit: 10
    });

    /**
     * 监听工具条
     */
    table.on('tool(eiaFileUploadList)', function (obj) {
        var data = obj.data;
        if(obj.event === 'eiaFileUploadDel'){    //删除
            layer.confirm('确定要删除该附件吗?', {icon: 3}, function (index) {
                var loadingIndex = layer.load(1, {time: 10*1000,shade: 0.1});
                $.post("/eia/eiaFileUpload/eiaFileUploadDelete", {eiaFileUploadId:data.id},function (data) {
                    if (data.code == 0) {
                        layer.msg('删除成功！', {icon: 1, time: 1500,shade: 0.1}, function () {
                            obj.del();
                        });
                    } else {
                        if (data.msg) {
                            layer.msg("处理失败！<br/><div style='color:red;'>错误信息：" + data.msg + "</div>", {
                                icon: 2,
                                time: 1500
                            });
                        } else {
                            layer.msg('删除失败！', {icon: 2, time: 1500,shade: 0.1});
                        }
                    }
                    layer.close(loadingIndex);
                });
            });
        }else if(obj.event === 'eiaFileUploadDownload'){
            window.location.href =  request_url_root+"/eiaFileUpload/eiaFileUploadDownload?eiaFileUploadId=" + data.id
        }else if(obj.event === 'eiaFileUploadDetail'){
            var pageUrl = request_url_root+'/eiaFileUpload/eiaFileUploadDetail?eiaFileUploadId='+data.id;
            var index = layer.open({
                title:' ',
                type: 2,
                shade: false,
                maxmin: true,
                skin: 'larry-green',
                area: ['100%', '100%'],
                content: pageUrl,
                min: function () {
                    $(".layui-layer-title").text("文件上传查看");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        }
    });

    //监听头部工具栏事件
    //监听事件
    table.on('toolbar(eiaFileUploadList)', function(obj){
        switch(obj.event){
            case 'eiaFileUploadAdd':
                var pageUrl = request_url_root+'/eiaFileUpload/eiaFileUploadCreate';
                var index = layer.open({
                    title:' ',
                    type: 2,
                    shade: false,
                    maxmin: true,
                    skin: 'larry-green',
                    area: ['100%', '100%'],
                    content: pageUrl,
                    end: function () {

                        table.reload('eiaFileUploadList', {url: "/eia/eiaFileUpload/getEiaFileUploadDataList?tableName=" + tableName + "&tableId=" + tableId});
                    },
                    min: function () {
                        $(".layui-layer-title").text("文件上传");
                    },
                    restore: function () {
                        $(".layui-layer-title").text(" ");
                    }
                });
                break;
        }
    });
});