layui.use(['jquery', 'layer', 'form', 'laydate', 'table'], function () {
    var $ = layui.jquery,
        form = layui.form,
        laydate = layui.laydate,
        layer = layui.layer,
        table = layui.table;
    $('.fileUploadBox').css('display', 'block');

    var eiaStampId = $('#eiaStampId').val();
    if (!eiaStampId) {
        eiaStampId = parent.$('#eiaStampId').val();
        if (!eiaStampId) {
            eiaStampId = parent.$('#tableNameId').val();
        }
        $('#eiaStampId').val(eiaStampId)
    }


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

  /*  //是否业务用章
    form.on('radio(ifBussUse)', function (data) {
        var val = data.value;
        if (val == 'true') {

            $('.fileUploadBox').css('display', 'none');
        }
        else if (val == 'false') {
        }
    });*/

    //监听公司名称选中
    form.on('select(stampCompany)', function (data) {
        $('#stampCompany').val(data.value)
        if (data.value.indexOf('分公司') > 0) {
            $("input:checkbox[name=stampType][value='合同章']").removeAttr("disabled", 'true');
            form.render('checkbox')
        } else {
            $("input:checkbox[name=stampType][value='合同章']").attr("disabled", 'true');
            $("input:checkbox[name=stampType][value='合同章']").removeAttr("checked", 'true');
            var index = stampTypeChecked.indexOf('合同章');
            stampTypeChecked.splice(index, 1);
            $('#stampTypeCode').val(stampTypeChecked);
            form.render('checkbox');
        }
        $('.eiaStampBox input').attr('checked', false);
        form.render('checkbox');
    });

    $.ajax({
        url: "/eia/eiaStamp/getStampCompList",
        type: "POST",
        data: {},
        dataType: "json",
        async: true,
        success: function (data) {
            for (var name in data.data) {
                var str = "<option value='" + data.data[name].code + "'>" + data.data[name].code + "</option>"
                $('#stampCompany').append(str);
            }


            $.ajax({
                url: "../eiaStamp/getEiaStampDataMap?eiaStampId=" + eiaStampId,
                type: "post",
                cache: false,
                async: false,
                success: function (result) {
                    if (result.data) {
                        if (result.data.stampCompany) {
                            $("#stampCompany").find("option[value = " + result.data.stampCompany + "]").attr("selected", "selected");
                            if (result.data.stampCompany.indexOf('分公司') > 0) {
                                $("input:checkbox[name=stampType][value='合同章']").removeAttr("disabled", true);
                            } else {
                                $("input:checkbox[name=stampType][value='合同章']").attr("disabled", true);
                            }
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
                            $('#ifBussUse').text('业务用章');
                             $('.fileUploadBox').css('display', 'block');
                        } else if (result.data.ifBussUse == false) {
                            $('#ifBussUse').text('非业务用章')
                            $("input:radio[name=ifBussUse][value=false]").attr("checked", true);
                             $('.fileUploadBox').css('display', 'block');
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


            form.render('select');
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
                $('#eiaStampId').val() == '' ? $('.eiaFileUploadAdd').css('display', 'none') : $('.noticeTag').css('display', 'none');
            }
        }
    );

    table.reload("eiaFileUploadList");

    /**
     * 附件新增
     */
    table.on('toolbar(eiaFileUploadList)', function (obj) {
        switch (obj.event) {
            case 'eiaFileUploadAdd':
                var pageUrl = '../eiaFileUpload/eiaFileUploadCreateNoType';
                var index = layer.open({
                    title: ' ',
                    type: 2,
                    shade: false,
                    maxmin: true,
                    skin: 'larry-green',
                    area: ['100%', '100%'],
                    content: pageUrl,
                    end: function () {
                        table.reload('eiaFileUploadList', {
                            where: {
                                tableName: 'EiaStamp',
                                tableId: $('#eiaStampId').val(),
                                fileUploadType: 'EiaStampFile'
                            }
                        });
                    },
                    min: function () {
                        $(".layui-layer-title").text("文件上传");
                    },
                    restore: function () {
                        $(".layui-layer-title").text(" ");
                    }
                });
                break;
            case 'eiaFileUploadDownload':
                window.location.href = request_url_root + "/eiaFileUpload/eiaFileUploadDownload?eiaFileUploadId=" + data.id
        }
    })



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
        var loadingIndex = layer.load(1, {time: 10 * 1000, shade: 0.1});
        $.post(actionUrl, data.field, function (data) {
            if (data.code == 0) {
                $('#eiaStampId').val(data.data.id)
                parent.$('#eiaStampId').val(data.data.id);
                if (data.data.ifBussUse == false) {
                    parent.//如果是非业务用章，显示上传按钮
                    layer.msg('保存成功', {icon: 1, time: 1000, shade: 0.1}, function () {
                        var index = parent.layer.getFrameIndex(window.name);
                        // parent.layui.table.reload("eiaStampList");
                        var eiaStampId = $('#eiaStampId').val();
                        if (eiaStampId) {
                            eiaFileUploadAddShow();
                        } else {
                            eiaFileUploadAddHide();
                        }
                    });
                } else {
                    //如果是业务用章
                    layer.msg('保存成功', {icon: 1, time: 1000, shade: 0.1}, function () {
                        var index = parent.layer.getFrameIndex(window.name);
                        parent.layui.table.reload("eiaStampList");
                        // parent.layui.table.reload("eiaStampList");
                        var eiaStampId = $('#eiaStampId').val();
                        if (eiaStampId) {
                            eiaFileUploadAddShow();
                        } else {
                            eiaFileUploadAddHide();
                        }
                    });
                }
            } else {
                layer.msg(data.msg, {icon: 2, time: 1000, shade: 0.1});
            }
            layer.close(loadingIndex);
        });
        return false;
    })


    var eiaFileUploadAddShow = function () {
        $('.eiaFileUploadAdd').css('display', 'inline-block');
        $('.noticeTag').css('display', 'none');
        $('.noticeCissTag').css('display', 'none');

    };
    var eiaFileUploadAddHide = function () {
        $('.eiaFileUploadAdd').css('display', 'none');
        $('.noticeTag').css('display', 'inline-block');
        $('.noticeCissTag').css('display', 'inline-block');

    };

});