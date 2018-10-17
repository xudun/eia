layui.use(['jquery', 'layer', 'form', 'laydate', 'table'], function(){
    var $ = layui.jquery,
        layer = layui.layer,
        laydate = layui.laydate,
        table = layui.table,
        form = layui.form;
    var eiaPubProjectId = getParamFromUrl(document.location.href,"eiaPubProjectId");
    $('#eiaPubProjectId').val(eiaPubProjectId);
    //工具函数：
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

    //下拉树 所属区域
    $("#regionNameDrop").dropDownForZ({
        url:'/eia/eiaDomainCode/getTree?domain='+"PROVINCE_CITY",
        width:'99%',
        height:'350px',
        disableParent: true,
        selecedSuccess:function(data){
            if(!data.isParent){
                var temp = {
                    id: data.id,
                    name: data.name
                };
                $("#regionName").val(JSON.stringify(temp));
            }
        }
    });
    //下拉树 行业类型及代码
    $("#industryTypeDrop").dropDownForZ({
        url:'/eia/eiaProject/getTreeByDomain?domain='+"GNS_TYPE_CODE",
        width:'99%',
        height:'350px',
        disableParent: true,
        selecedSuccess:function(data){  //选中回调
            if(!data.isParent){
                var str = data.attributes.mark + " " + data.name;
                $("#industryType").val(str);
                $("#industryTypeDrop").val(str);
            }
        }
    });
    //下拉树 环境影响评价行业类别
    $("#environmentaTypeDrop").dropDownForZ({
        url:'/eia/eiaProject/getTreeByDomain?domain='+"INS_TYPE_CODE",
        width:'99%',
        height:'350px',
        disableParent: true,
        selecedSuccess:function(data){  //选中回调
            if(!data.isParent){
                var str = data.attributes.mark + " " + data.name;
                $("#environmentaType").val(str);
                $("#environmentaTypeDrop").val(str);
            }
        }
    });

    // 渲染 审批年度Select
    $.ajax({
        url:'/eia/eiaDomainCode/getTree?domain='+"PUBLICTY_YEAR",
        type:"POST",
        data:{},
        dataType: "json",
        success: function (data) {
            console.log(data);
            for(var i=0; i<data.length; i++){
                var str = "<option value='"+data[i].name+"'>"+data[i].name+"</option>";
                $('#publictyYear').append(str);
            }
            form.render('select');
        }
    });
    // 渲染 建设性质Select
    $.ajax({
        url:'/eia/eiaDomainCode/getTree?domain='+"NATURE_CONSTRUCTION",
        type:"POST",
        data:{},
        dataType: "json",
        success: function (data) {
            console.log(data);
            for(var i=0; i<data.length; i++){
                var str = "<option value='"+data[i].name+"'>"+data[i].name+"</option>";
                $('#natureConstructio').append(str);
            }
            form.render('select');
        }
    });

    //日期
    laydate.render({
        elem: '#pubDate'
    });

    //渲染编辑页数据
    $.ajax({
        url:"../eiaPlatForm/getEiaPubProjectDataMap?eiaPubProjectId=" + eiaPubProjectId,
        type:"POST",
        data:{},
        dataType: "json",
        success: function (result) {
            if (result.data) {
                $('#projectName').val(result.data.projectName);
                $('#approveDept').val(result.data.approveDept);
                $('#industryTypeDrop').val(result.data.industryType);
                $('#industryType').val(result.data.industryType);
                $('#productFunction').val(result.data.productFunction);
                $('#eiaUnit').val(result.data.eiaUnit);
                $('#geographicEast').val(result.data.geographicEast);
                $('#geographicStartEast').val(result.data.geographicStartEast);
                $('#geographicEndEast').val(result.data.geographicEndEast);
                $('#regionNameDropCode').val(result.data.regionName.id);
                $('#regionNameDrop').val(result.data.regionName.name);
                $('#regionName').val(result.data.regionName);
                $('#productionEngineer').val(result.data.productionEngineer);
                $('#environmentaTypeDrop').val(result.data.environmentaType);
                $('#environmentaType').val(result.data.environmentaType);
                $('#developmentOrg').val(result.data.developmentOrg);
                $('#geographicNorth').val(result.data.geographicNorth);
                $('#geographicStartNorth').val(result.data.geographicStartNorth);
                $('#geographicEndNorth').val(result.data.geographicEndNorth);
                $('#projectMemo').val(result.data.projectMemo);
                $('#projectLoc').val(result.data.projectLoc);
                $('#pubDate').val(result.pubDate);

                selectOptionByVal($('#natureConstructio'), result.data.natureConstructio);
                selectOptionByVal($('#publictyYear'), result.data.publictyYear);
                selectOptionByVal($('#dataType'), result.data.dataType);
                form.render('select');

                $('#spiderFileDownloadUrl').attr('href', result.data.spiderFileDownloadUrl);
                $('#spiderFileUrl').attr('href', result.data.spiderFileUrl);
            }
        }
    });

    //表单提交
    form.on('submit(save)', function(data){
        var loadingIndex = layer.load(1, {time: 10*1000,shade: 0.1});
        // console.log(data.field); //当前容器的全部表单字段，名值对形式：{name: value}
        var actionUrl = "../eiaPlatForm/eiaPubProjectSave";
        $.post(actionUrl, data.field, function (data) {
            if (data.code == 0) {
                layer.msg('保存成功', {icon: 1, time: 1000,shade: 0.1}, function () {
                    parent.layui.table.reload("eiaPubProjectList");
                    parent.layer.closeAll();
                });
            } else {
                layer.msg('保存失败', {icon: 2, time: 2000,shade: 0.1}, function () {
                    parent.layer.closeAll();
                });
            }
            layer.close(loadingIndex);
        });
        return false;
    });
    /**
     * 文件上传
     */
    var tableName = "EiaPubProject";
    var tableId = eiaPubProjectId;
    $("#tableName").val(tableName);
    $("#tableId").val(tableId);

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