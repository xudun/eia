layui.use(['layer','table'],function() {
    var $ = layui.$,
        layer = layui.layer,
        table = layui.table;
    var params = getParamByUrl(window.location.href)
    var tableName = params["tableName"];
    var tableId = params["tableNameId"];
    $("#tableName").val(tableName);
    $("#tableId").val(tableId);
    $('.larry-btn a.layui-btn').click(function () {
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });
    var active = {
        eiaFileUploadAdd: function () {
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
                    table.reload('eiaFileUploadList');
                },
                min: function () {
                    $(".layui-layer-title").text("文件上传");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        }
    }
    /**
     * 渲染文件上传列表
     */
    table.render({
        id: 'eiaFileUploadList',
        elem: '#eiaFileUploadList',
        url:'/eia/eiaFileUpload/getEiaFileUploadDataList',
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
        limit: 10,
        where:{
            tableName:tableName,
            tableId:tableId
        }
    });

    table.reload("eiaFileUploadList");
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
})