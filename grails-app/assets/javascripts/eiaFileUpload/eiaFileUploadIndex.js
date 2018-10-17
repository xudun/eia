layui.use(['layer', 'table'], function () {
    var $ = layui.$,
        layer = layui.layer,
        table = layui.table;
    var params = getParamByUrl(window.location.href)
    var tableName = params["tableName"];
    var tableId = params["tableNameId"];
    var ifModi = params["ifModi"];
    var fileUploadType = params["fileUploadType"];
    var forceContract = params["forceContract"];
    /**强制显示合同文件**/
    if (forceContract && params["eiaContractId"]) {
        tableName = "EiaContract";
        tableId = params["eiaContractId"];
    }
    var eiaProjectId = parent.$('#eiaProjectId').val();
    $("#tableName").val(tableName);
    $("#tableId").val(tableId);
    $('.larry-btn a.layui-btn').click(function () {
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });

    if (tableName == "EiaProject") {
        $(".eiaProject").removeClass("display-none")
    }
    var active = {
        eiaFileUploadAdd: function () {
            var pageUrl = '../eiaFileUpload/eiaFileUploadCreate';
            var index = layer.open({
                title: ' ',
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
        },
        firstInsPrint: function () {
            var pageUrl = '../eiaProject/eiaPrintReport?eiaProjectId=' + eiaProjectId + '&reportType=YS';
            var index = layer.open({
                title: ' ',
                type: 2,
                shade: false,
                maxmin: true,
                skin: 'larry-green',
                area: ['100%', '100%'],
                content: pageUrl,
                end: function () {

                },
                min: function () {
                    $(".layui-layer-title").text("核查导出");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        },
        SecondInsPrint: function () {
            var pageUrl = '../eiaProject/eiaPrintReport?eiaProjectId=' + eiaProjectId + '&reportType=ES';
            var index = layer.open({
                title: ' ',
                type: 2,
                shade: false,
                maxmin: true,
                skin: 'larry-green',
                area: ['100%', '100%'],
                content: pageUrl,
                end: function () {

                },
                min: function () {
                    $(".layui-layer-title").text("核查导出");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        },
        ThirdInsPrint: function () {
            var pageUrl = '../eiaProject/eiaPrintReport?eiaProjectId=' + eiaProjectId + '&reportType=SS';
            var index = layer.open({
                title: ' ',
                type: 2,
                shade: false,
                maxmin: true,
                skin: 'larry-green',
                area: ['100%', '100%'],
                content: pageUrl,
                end: function () {

                },
                min: function () {
                    $(".layui-layer-title").text("核查导出");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        },
        proCardPrint: function () {
            var pageUrl = '../eiaProject/eiaPrintDutyCard?eiaProjectId=' + eiaProjectId;
            var index = layer.open({
                title: ' ',
                type: 2,
                shade: false,
                maxmin: true,
                skin: 'larry-green',
                area: ['100%', '100%'],
                content: pageUrl,
                end: function () {

                },
                min: function () {
                    $(".layui-layer-title").text("核查导出");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        },
        explorePrint: function () {
            var pageUrl = '../eiaProject/eiaProjectExplorePrint?eiaProjectId=' + eiaProjectId;
            var index = layer.open({
                title: ' ',
                type: 2,
                shade: false,
                maxmin: true,
                skin: 'larry-green',
                area: ['100%', '100%'],
                content: pageUrl,
                end: function () {

                },
                min: function () {
                    $(".layui-layer-title").text("核查导出");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        },
        fileArcPrint: function () {
            var pageUrl = '../eiaProject/eiaFileArc?eiaProjectId=' + eiaProjectId;
            var index = layer.open({
                title: ' ',
                type: 2,
                shade: false,
                maxmin: true,
                skin: 'larry-green',
                area: ['100%', '100%'],
                content: pageUrl,
                end: function () {

                },
                min: function () {
                    $(".layui-layer-title").text("归档资料打印");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        }
    }
    table.on('toolbar(eiaFileUploadList)', function (obj) {
        switch (obj.event) {
            case 'explorePrint':
                var pageUrl = '../eiaProject/eiaProjectExplorePrint?eiaProjectId=' + eiaProjectId;
                var index = layer.open({
                    title: ' ',
                    type: 2,
                    shade: false,
                    maxmin: true,
                    skin: 'larry-green',
                    area: ['100%', '100%'],
                    content: pageUrl,
                    end: function () {

                    },
                    min: function () {
                        $(".layui-layer-title").text("核查导出");
                    },
                    restore: function () {
                        $(".layui-layer-title").text(" ");
                    }
                });
                break
            case 'eiaFileUploadAdd':
                var pageUrl = '../eiaFileUpload/eiaFileUploadCreate';
                var index = layer.open({
                    title: ' ',
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
                break;
            case 'firstInsPrint':
                var pageUrl = '../eiaProject/eiaPrintReport?eiaProjectId=' + eiaProjectId + '&reportType=YS';
                var index = layer.open({
                    title: ' ',
                    type: 2,
                    shade: false,
                    maxmin: true,
                    skin: 'larry-green',
                    area: ['100%', '100%'],
                    content: pageUrl,
                    end: function () {

                    },
                    min: function () {
                        $(".layui-layer-title").text("核查导出");
                    },
                    restore: function () {
                        $(".layui-layer-title").text(" ");
                    }
                });
                break;
            case 'SecondInsPrint':
                var pageUrl = '../eiaProject/eiaPrintReport?eiaProjectId=' + eiaProjectId + '&reportType=ES';
                var index = layer.open({
                    title: ' ',
                    type: 2,
                    shade: false,
                    maxmin: true,
                    skin: 'larry-green',
                    area: ['100%', '100%'],
                    content: pageUrl,
                    end: function () {

                    },
                    min: function () {
                        $(".layui-layer-title").text("核查导出");
                    },
                    restore: function () {
                        $(".layui-layer-title").text(" ");
                    }
                });
                break;
            case 'ThirdInsPrint':
                var pageUrl = '../eiaProject/eiaPrintReport?eiaProjectId=' + eiaProjectId + '&reportType=SS';
                var index = layer.open({
                    title: ' ',
                    type: 2,
                    shade: false,
                    maxmin: true,
                    skin: 'larry-green',
                    area: ['100%', '100%'],
                    content: pageUrl,
                    end: function () {

                    },
                    min: function () {
                        $(".layui-layer-title").text("核查导出");
                    },
                    restore: function () {
                        $(".layui-layer-title").text(" ");
                    }
                });
                break;
            case 'proCardPrint':
                var pageUrl = '../eiaProject/eiaPrintDutyCard?eiaProjectId=' + eiaProjectId;
                var index = layer.open({
                    title: ' ',
                    type: 2,
                    shade: false,
                    maxmin: true,
                    skin: 'larry-green',
                    area: ['100%', '100%'],
                    content: pageUrl,
                    end: function () {

                    },
                    min: function () {
                        $(".layui-layer-title").text("核查导出");
                    },
                    restore: function () {
                        $(".layui-layer-title").text(" ");
                    }
                });
                break;
            case 'fileArcPrint':
                var pageUrl = '../eiaProject/eiaFileArc?eiaProjectId=' + eiaProjectId;
                var index = layer.open({
                    title: ' ',
                    type: 2,
                    shade: false,
                    maxmin: true,
                    skin: 'larry-green',
                    area: ['100%', '100%'],
                    content: pageUrl,
                    end: function () {

                    },
                    min: function () {
                        $(".layui-layer-title").text("归档资料打印");
                    },
                    restore: function () {
                        $(".layui-layer-title").text(" ");
                    }
                });
                break;
        }
    })


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
                        return '<div><a href="viewCreditReport?eiaFileUploadId=' + d.id + '" target="_blank" class="layui-table-link">' + d.fileName + '</a></div>'
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
                tableName: tableName,
                tableId: tableId,
                fileUploadType: fileUploadType
            },
            done: function () {
                if (tableName == "EiaProject") {
                    $(".eiaProject").removeClass("display-none")
                }
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
})