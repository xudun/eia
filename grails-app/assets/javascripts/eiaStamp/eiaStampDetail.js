layui.use(['jquery', 'layer', 'form', 'laydate','table'], function () {
    var $ = layui.jquery,
        form = layui.form,
        laydate = layui.laydate,
        layer = layui.layer;
        table = layui.table;

    var eiaStampId = parent.$('#eiaStampId').val();
    if (!eiaStampId) {
        eiaStampId = parent.$('#tableNameId').val();
    }
    $('#eiaStampId').val(eiaStampId)

    /**
     * 印章是否外带
     */
    form.on('radio(ifTakeOut)', function (data) {
        /**是，选择负责人**/
        if (data.value == 'true') {
            $('.verify-item').attr("style", "display:block;")
            $('.verify-item').find('.layui-form-label span').text("* ");
            $('.verify-item').find('.layui-input-block input').attr('lay-verify', "required");
        } else {
            $('.verify-item').attr("style", "display:none;")
            $('.verify-item').find('.layui-input-block input').removeAttr('lay-verify', "required");
        }


    });
    var stampTypeChecked = []

    //监听复选框
    form.on('checkbox(eiaStampfilter)', function (data) {
        if (data.elem.checked) {
            stampTypeChecked.push(data.value);
        } else {
            var index = stampTypeChecked.indexOf(data.value);
            stampTypeChecked.splice(index, 1);
        }
        $('#stampTypeCode').val(stampTypeChecked);
    });


    //是否业务用章
  /*  form.on('radio(ifBussUse)', function(data){
        var val = data.value;
        if(val == 'true'){
            $('.fileUploadBox').css('display','none');
        }
        else if(val == 'false'){
            $('.fileUploadBox').css('display','block');
        }
    });*/
    $('.fileUploadBox').css('display','block');

    //监听公司名称选中
    form.on('select(stampCompany)', function (data) {
        $('#stampCompany').val(data.value)
        if (data.value.indexOf('分公司') > 0) {
            $("#stampType").find("input[type = 'checkbox'] ").attr("checked", true);
            form.render('checkbox')
        }
    });


            $.ajax({
                url: "../eiaStamp/getEiaStampDataMap?eiaStampId=" + eiaStampId,
                type: "post",
                cache: false,
                async: false,
                success: function (result) {
                    if (result.data) {
                        if(result.data.stampCompany){
                            $('#stampCompany').text(result.data.stampCompany)
                        }
                        /* if (result.data.stampCompany == '联合泰泽') {
                             $("input:radio[name=stampCompany][value='联合泰泽']").attr("checked", true);
                         } else if (result.data.stampCompany == "联合赤道") {
                             $("input:radio[name=stampCompany][value='联合赤道']").attr("checked", true);
                         }*/
                        if (result.data.stampType.indexOf('公章') >= 0) {
                            $("input:checkbox[name=stampType][value='公章']").attr("checked", true);
                            stampTypeChecked.push('公章')
                        }
                        if (result.data.stampType.indexOf('财务章') >= 0) {
                            $("input:checkbox[name=stampType][value='财务章']").attr("checked", true);
                            stampTypeChecked.push('财务章')

                        }
                        if (result.data.stampType.indexOf('法人章') >= 0) {
                            $("input:checkbox[name=stampType][value='法人章']").attr("checked", true);
                            stampTypeChecked.push('法人章')
                        }
                        if (result.data.stampType.indexOf('合同章') >= 0) {
                            $("input:checkbox[name=stampType][value='合同章']").attr("checked", true);
                            stampTypeChecked.push('合同章')
                        }
                        $('#stampTypeCode').val(stampTypeChecked);
                        $('#stampType').text(stampTypeChecked);
                        if (result.data.ifBussUse == true) {
                            $("input:radio[name=ifBussUse][value=true]").attr("checked", true);
                            $('#ifBussUse').text('业务用章')
                            $('.fileUploadBox').css('display','block');

                        } else if (result.data.ifBussUse == false) {
                            $('#ifBussUse').text('非业务用章')
                            $("input:radio[name=ifBussUse][value=false]").attr("checked", true);
                            $('.fileUploadBox').css('display','block');

                        }


                        if (result.data.ifTakeOut == true) {
                            $("input:radio[name=ifTakeOut][value=true]").attr("checked", true);
                            $('#ifTakeOut').text('是')
                        } else if (result.data.ifTakeOut == false) {
                            $("input:radio[name=ifTakeOut][value=false]").attr("checked", true);
                            $('#ifTakeOut').text('否')
                        }
                        if (true == result.data.ifTakeOut) {
                            $('.verify-item').attr("style", "display:block;")
                            $('.verify-item').find('.layui-form-label span').text("* ");
                            $('.verify-item').find('.layui-input-block input').attr('lay-verify', "required");
                        } else {
                            $('.verify-item').attr("style", "display:none;")
                            $('.verify-item').find('.layui-form-label span').text("");
                            $('.verify-item').find('.layui-input-block input').attr('lay-verify', "");
                        }
                        $('#appReason').val(result.data.appReason)
                        $('#stampNum').val(result.data.stampNum)
                        $('#supervisor').val(result.data.supervisor)
                        form.render('checkbox');
                        form.render('radio');
                        form.render('select');

                    }
                }
            });


    /**
     * 渲染文件上传列表
     */
    table.render({
            id: 'eiaFileUploadList',
            elem: '#eiaFileUploadList',
            url: '/eia/eiaFileUpload/getEiaFileUploadDataList',
            toolbar: '#tableTopTmp',
            defaultToolbar: ['filter', 'print', 'exports'],
            cols: [[
                {type: 'numbers', title: '序号', align: "center"},
                {
                    field: 'fileName', title: '原文件名', align: "center", width: 300, templet: function (d) {
                        return '<div><a href='+request_url_root+'/eiaFileUpload/viewCreditReport?eiaFileUploadId=' + d.id + ' target="_blank" class="layui-table-link">' + d.fileName + '</a></div>'
                    }
                },
                {field: 'inputUser', title: '上传人', align: "center"},
                {field: 'uploadDate', title: '上传时间', align: "center"},
                {field: 'fileSize', title: '大小', align: "center"},
                {field: 'fileUploadType', title: '文件类型', align: "center"},
                {fixed: 'right', title: '操作', align: "center", toolbar: '#eiaFileUploadListBar', align: "center"}
            ]],
            page: true,
            even: true,
            limit: 10,
            where: {
                tableName: 'EiaStamp',
                tableId: $('#eiaStampId').val(),
                fileUploadType: 'EiaStampFile'
            },
            done: function () {
            }
        }
    );

    table.reload("eiaFileUploadList");

    /**
     * 监听工具条
     */
    table.on('tool(eiaFileUploadList)', function (obj) {
        var data = obj.data;
        if (obj.event === 'eiaFileUploadDel') {    //删除
            layer.confirm('确定要删除该附件吗?', {icon: 3}, function (index) {
                var loadingIndex = layer.load(1, {time: 10 * 1000, shade: 0.1});
                $.post("/eia/eiaFileUpload/eiaFileUploadDelete", {eiaFileUploadId: data.id}, function (data) {
                    if (data.code == 0) {
                        layer.msg('删除成功！', {icon: 1, time: 1500, shade: 0.1}, function () {
                            obj.del();
                        });
                    } else {
                        if (data.msg) {
                            layer.msg("处理失败！<br/><div style='color:red;'>错误信息：" + data.msg + "</div>", {
                                icon: 2,
                                time: 1500
                            });
                        } else {
                            layer.msg('删除失败！', {icon: 2, time: 1500, shade: 0.1});
                        }
                    }
                    layer.close(loadingIndex);
                });
            });
        } else if (obj.event === 'eiaFileUploadDownload') {
            window.location.href = request_url_root + "/eiaFileUpload/eiaFileUploadDownload?eiaFileUploadId=" + data.id
        } else if (obj.event === 'eiaFileUploadDetail') {
            var pageUrl = request_url_root + '/eiaFileUpload/eiaFileUploadDetail?eiaFileUploadId=' + data.id;
            var index = layer.open({
                title: ' ',
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


    //重置按钮
    $('.resetBtn').click(function () {
        $('#contractTypeStr').val("");
    });

    //表单提交
    form.on('submit(save)', function (data) {
        var actionUrl = "../eiaStamp/eiaStampSave";
        $.post(actionUrl, data.field, function (data) {
            if (data.code == 0) {
                layer.msg('保存成功', {icon: 1, time: 1000, shade: 0.1}, function () {
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.layui.table.reload("eiaStampList");
                    parent.layer.close(index);
                });
            } else {
                layer.msg(data.msg, {icon: 2, time: 1000, shade: 0.1});
            }
        });
        return false;
    })

});