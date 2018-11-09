layui.use(['jquery', 'layer', 'form', 'element'], function () {
    var $ = layui.jquery,
        layer = layui.layer,
        form = layui.form,
        element = layui.element;

    $(function () {
        var $speInpContainers = $('.dynInputs .layui-col-xs6');

        var $taskId = $('#taskId'),  //任务id
            $eiaClientId = $('#eiaClientId'),  //客户id
            $competentDept = $('#competentDept'),  //主管部门
            $natureConstructio = $('#natureConstructio'),  //建设性质
            $eiaContractId = $('#eiaContractId'),  //合同
            $fileType = $('#fileType');  //文件类型


        var eiaProjectId = parent.$('#eiaProjectId').val();
        eiaProjectId = eiaProjectId ? eiaProjectId : getParamFromUrl(document.location.href, "eiaProjectId");
        var eiaTaskId = parent.$('#eiaTaskId').val();
        eiaTaskId = eiaTaskId ? eiaTaskId : getParamFromUrl(document.location.href, "eiaTaskId");

        $('#eiaTaskId').val(eiaTaskId)
        //工具函数：

        //页面类型：0:新建 ； 1：编辑
        var pageType = getParamFromUrl(document.location.href, "pageType");

        //设置页面名称
        var $pageTitle = $('.pageTitle');
        pageType == 0 ? $pageTitle.html('新增项目') : $pageTitle.html('编辑项目');

        //重置按钮显隐
        pageType == 0 ? "" : $('.resetBtn').hide();

        //渲染客户select选项
        $.ajax({
            url: "/eia/eiaClient/getEiaClientDataList",
            type: "POST",
            data: {},
            dataType: "json",
            async: true,
            success: function (data) {
                for (var name in data.data) {
                    var str = "<option value='" + data.data[name].id + "'>" + data.data[name].clientName + "</option>";
                    $eiaClientId.append(str);
                }
                form.render('select');
                form.render('radio');
            }
        });
        //渲染任务名称select选项
        $.ajax({
            url: "/eia/eiaTask/getEiaTaskName",
            type: "POST",
            data: {},
            dataType: "json",
            async: true,
            success: function (data) {
                for (var name in data) {
                    var str = "<option value='" + data[name].id + "'>" + data[name].taskName + "</option>";
                    $taskId.append(str);
                }
                form.render('select');
                form.render('radio');
            }
        });

        /**
         * 监听项目负责人选中
         */
        form.on('select(dutyUserList)', function (data) {
            $('#dutyUserId').val(data.value)
            $('#dutyUser').val($("#dutyUserList option:selected").text())
        });


        /*     //渲染主管部门select选项
             $.ajax({
                 url: '/eia/eiaProject/getCompetentDept',
                 type: "POST",
                 data: {},
                 dataType: "json",
                 async: true,
                 success: function (data) {
                     var seaReviewOrg = $('#seaReviewOrg');
                     seaReviewOrg.append('<option value="">选择审批部门</option>');
                     for (var name in data) {
                         var str = "<option value='" + data[name].codeDesc + "'>" + data[name].codeDesc + "</option>";
                         $('#seaReviewOrg').append(str);
                     }
                     form.render('select');
                     form.render('radio');
                 }
             });*/
        /*  //监听合同名称选中
          form.on('select(competentDep)', function (data) {
              $('#competentDep').val(data.val())
          });*/
        /*   //下拉树 主管部门
           $("#competentDepDrop").dropDownForZ({
               url: '/eia/eiaProject/getTreeByDomain?domain=CHECK_ORG',
               width: '99%',
               height: '350px',
               disableParent: true,
               selecedSuccess: function (data) {  //选中回调
                   if (!data.isParent) {
                       var temp = {
                           id: data.id,
                           name: data.name
                       };
                       $("#competentDep").val(JSON.stringify(temp));
                   }
                   form.render('select');
               }
           });*/
        //监听合同名称选中
        form.on('select(contractId)', function (data) {
            $('#eiaContractId').val(data.value)
        });
        //下拉树 文件类型
        $("#fileTypeDrop").dropDownForZ({
            //   url:'data/fileTypeTree.json',
            url: '/eia/eiaProject/getFileTreeByTaskId?eiaTaskId=' + eiaTaskId + '&eiaProjectId=' + eiaProjectId,
            width: '99%',
            height: '350px',
            disableParent: true,
            selecedSuccess: function (data) {  //选中回调
                if (!data.isParent) {
                    var temp = {
                        id: data.id,
                        name: data.name,
                        code: data.code
                    };
                    $("#fileType").val(JSON.stringify(temp));

                    //文件类型有值后，项目金额可填写
                    $('.fillMoneyBtn').css("display", 'block');
                    $('.show-tips').css("display", 'none');

                    //渲染特殊数据项
                    $.ajax({
                        url: "/eia/eiaProject/eiaEnvProjectDetail",
                        type: "POST",
                        data: {"fileType": data.id},
                        dataType: "json",
                        async: true,
                        success: function (data) {
                            $speInpContainers.empty();
                            var con_index = 0;
                            if (data.data) {
                                for (var i = 0; i < data.data.length; i++) {
                                    inputs[data.data[i]].create.call(this, "", $speInpContainers.eq(con_index++ % 2));
                                }
                            }
                            //根据下拉树选中的code更改#productFunction的label文字
                            var curParentCode = JSON.parse($('#fileType').val()).code;
                            if(curParentCode.indexOf('EPC_GH')!==-1){
                                $('#productFunction').closest('.layui-form-item').find('.label-txt').text('功能定位');
                            }
                            form.render('select');
                            form.render('radio');
                        }
                    });

                    /** 选择园区环评开展情况 */
                    $.ajax({
                        url: "../eiaProject/getParkEnvCodList",
                        type: 'POST',
                        success: function (data) {
                            var parkPlanning = $('#parkPlanning');
                            parkPlanning.append('<option value="">选择园区环评开展情况</option>');
                            if (data.data) {
                                for (var i = 0; i < data.data.length; i++) {
                                    parkPlanning.append('<option value="' + data.data[i].code + '">' + data.data[i].code + '</option>')
                                }
                            }
                            form.render('select');
                        }
                    });

                    /** 建设性质 */
                    $.ajax({
                        url: "../eiaProject/getBuildPropList",
                        type: 'POST',
                        success: function (data) {
                            var natureConstructio = $('#natureConstructio');
                            natureConstructio.append('<option value="">建设性质</option>');
                            if (data.data) {
                                for (var i = 0; i < data.data.length; i++) {
                                    natureConstructio.append('<option value="' + data.data[i].code + '">' + data.data[i].code + '</option>')
                                }
                            }
                            form.render('select');
                        }
                    });
                    form.render('select');
                    $('#projectMoney').val('');
                }
            }
        });

        //监听任务名称选中
        form.on('select(taskId)', function (data) {
            var curVal = data.value;
            $.ajax({
                url: "/eia/eiaProject/getClientIdByTaskId",
                type: "POST",
                data: {"taskId": curVal},
                dataType: "json",
                async: true,
                success: function (data) {
                    selectOptionByVal($eiaClientId, data.data.clientId);
                    form.render('select');
                }
            });
        });

        //项目金额hover提示
        $('.show-tips').hover(function () {
            layer.tips('选择文件类型后即可填写项目金额', '#tips', {
                area: ['auto', 'auto'],
                tips: [1, '#30b5ff'],
                time: 2000
            });
        }, function () {
            var index = layer.tips();
            layer.close(index);
        });

        //项目金额 按钮
        $('.fillMoneyBtn').click(function () {
            var index = layer.open({
                title: ' ',
                type: 2,
                shade: false,
                maxmin: true,
                skin: 'larry-green',
                area: ['100%', '100%'],
                content: 'eiaProjectFillSelect.html',
                success: function (layero, index) {
                    var body = layer.getChildFrame('body', index);
                },
                end: function () {

                },
                min: function () {
                    $(".layui-layer-title").text("项目金额");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        });

        //重置按钮
        $('.resetBtn').click(function () {
            $('input:hidden').val('');
            $speInpContainers.empty();
            //隐藏项目金额点击按钮
            $('.fillMoneyBtn').css("display", 'none');
            $('.show-tips').css("display", 'block');
        });
        /**
         * 编辑时回显数据
         */
        if (pageType == 1) {

            //项目金额可填写
            $('.fillMoneyBtn').css("display", 'block');
            $('.show-tips').css("display", 'none');

            //渲染固定数据
            $.ajax({
                url: "/eia/eiaProject/getEiaProjectDataMap?eiaProjectId=" + eiaProjectId,
                type: "POST",
                data: {},
                dataType: "json",
                async: true,
                success: function (result) {
                    selectOptionByVal($taskId, result.data.eiaTaskId);
                    selectOptionByVal($eiaClientId, result.data.eiaClientId);
                    $('#projectName').val(result.data.projectName);
                    $('#buildArea').val(result.data.buildArea);
                    $('#coordNorth').val(result.data.coordNorth);
                    $('#coordEast').val(result.data.coordEast);
                    $('#coordEndNorth').val(result.data.coordEndNorth);
                    $('#coordEndEast').val(result.data.coordEndEast);
                    $('#coordStartEast').val(result.data.coordStartEast);
                    $('#coordStartNorth').val(result.data.coordStartNorth);
                    $('#dutyUser').val(result.data.dutyUser);
                    $('#dutyUserId').val(result.data.dutyUserId);
                    $('#projectMoney').val(dotFour(result.data.projectMoney));
                    $('#projectComfee').val(dotFour(result.data.projectComfee));
                    $('#groundwaterFee').val(dotFour(result.data.groundwaterFee));
                    $('#otherFee').val(dotFour(result.data.otherFee));
                    $('#environmentalFee').val(dotFour(result.data.environmentalFee));
                    $('#expertFee').val(dotFour(result.data.expertFee));
                    $('#specialFee').val(dotFour(result.data.specialFee));
                    $('#detectFee').val(dotFour(result.data.detectFee));
                    $('#preIssCertFee').val(dotFour(result.data.preIssCertFee));
                    $('#preSurvCertFee').val(dotFour(result.data.preSurvCertFee));
                    $('#certServeFee').val(dotFour(result.data.certServeFee));
                    var staffId = $('#staffId').val();
                    if (staffId != result.data.inputUserId) {
                        $('.saveBtn').addClass("display-none");
                    }
                    //  selectOptionByVal($competentDep, result.data.competentDep);
                    //回显文件类型
                    $('#fileTypeDrop').val(result.data.fileType.name);
                    $('#fileTypeDropCode').val(result.data.fileType.id);
                    renderSepcialData();
                    $fileType.val(JSON.stringify(result.data.fileType));
                    $('#competentDept').val(result.data.competentDept);
                    if (!result.data.coordEast) {
                        $('#coordEast').attr('hidden');
                        $('#coordNorth').attr('hidden');

                    } else {
                        $('#coordStartEast').attr('hidden', true);
                        $('#coordStartNorth').attr('hidden');
                        $('#coordEndEast').attr('hidden');
                        $('#coordEndNorth').attr('hidden');
                    }
                    form.render('select');

                    /** 渲染项目负责人 */
                    $.ajax({
                        url: "../eiaProject/getProjectDutyUser",
                        type: 'POST',
                        data: {'eiaProjectId': eiaProjectId},
                        success: function (data) {
                            var dutyUserList = $('#dutyUserList');
                            dutyUserList.append('<option value="">选择项目负责人</option>');
                            if (data.data) {
                                if (result.data.dutyUserId) {
                                    for (var i = 0; i < data.data.length; i++) {
                                        dutyUserList.append('<option value="' + data.data[i].dutyUserId + '">' + data.data[i].dutyUser + '</option>')
                                        if (data.data[i].dutyUserId == result.data.dutyUserId) {
                                            dutyUserList.find("option[value = " + result.data.dutyUserId + "]").attr("selected", "selected");
                                        }
                                    }
                                } else {
                                    for (var i = 0; i < data.data.length; i++) {
                                        dutyUserList.append('<option value="' + data.data[i].dutyUserId + '">' + data.data[i].dutyUser + '</option>')
                                    }
                                }
                            }
                            form.render('select');
                        }
                    });
                }
            });
            //渲染可变数据
            var renderSepcialData = function () {
                $.ajax({
                    url: "/eia/eiaEnvProject/getEnvProDataMap?eiaProjectId=" + eiaProjectId,
                    type: "POST",
                    data: {},
                    dataType: "json",
                    async: true,
                    success: function (result) {
                        //根据返回的数据类型判断
                        if (result.data.length) {
                            //渲染特殊数据项
                            $.ajax({
                                url: "/eia/eiaProject/eiaEnvProjectDetail",
                                type: "POST",
                                data: {"fileType": $('#fileTypeDropCode').val()},
                                dataType: "json",
                                async: true,
                                success: function (data) {
                                    $speInpContainers.empty();
                                    var con_index = 0;
                                    if (data.data) {
                                        for (var i = 0; i < data.data.length; i++) {
                                            inputs[data.data[i]].create.call(this, "", $speInpContainers.eq(con_index++ % 2));
                                        }
                                    }
                                    getParkEnvCod.call();
                                    getBuildProp.call();

                                    //根据下拉树选中的code更改#productFunction的label文字
                                    var curParentCode = JSON.parse($('#fileType').val()).code;
                                    if(curParentCode.indexOf('EPC_GH')!==-1){
                                        $('#productFunction').closest('.layui-form-item').find('.label-txt').text('功能定位');
                                    }

                                    form.render('select');
                                    form.render('radio');
                                }
                            });
                        } else {
                            $speInpContainers.empty();
                            var con_index = 0;
                            for (var name in result.data) {
                                inputs[name].create.call(this, result.data[name], $speInpContainers.eq(con_index++ % 2));
                            }

                            //根据下拉树选中的code更改#productFunction的label文字
                            var curParentCode = JSON.parse($('#fileType').val()).code;
                            if(curParentCode.indexOf('EPC_GH')!==-1){
                                $('#productFunction').closest('.layui-form-item').find('.label-txt').text('功能定位');
                            }

                            //回显国民经济行业类型
                            if (result.data) {

                                $('#industryTypeDrop').val(result.data.industryType);
                                $('#industryType').val(result.data.industryType);
                                //$('#industryTypeDropCode').val(result.data.industryType.id);
                                //$('#industryType').val(JSON.stringify(result.data.fileType));

                                //回显国民经济行业类型
                                $('#environmentaTypeDrop').val(result.data.environmentaType);
                                $('#environmentaType').val(result.data.environmentaType);
                                //$('#industryTypeDropCode').val(result.data.industryType.id);
                                //$('environmentaType').val(JSON.stringify(result.data.environmentaType));

                                /** 选择园区环评开展情况 */
                                $.ajax({
                                    url: "../eiaProject/getParkEnvCodList",
                                    type: 'post',
                                    success: function (data) {
                                        var parkPlanning = $('#parkPlanning');
                                        parkPlanning.append('<option value="">选择园区环评开展情况</option>');
                                        if (data.data) {
                                            for (var i = 0; i < data.data.length; i++) {
                                                if (result.data) {
                                                    if (result.data.parkPlanning) {
                                                        if (result.data.parkPlanning == data.data[i].code) {
                                                            parkPlanning.append('<option value="' + data.data[i].code + '" selected>' + data.data[i].code + '</option>')
                                                        } else {
                                                            parkPlanning.append('<option value="' + data.data[i].code + '">' + data.data[i].code + '</option>')
                                                        }
                                                    } else {
                                                        parkPlanning.append('<option value="' + data.data[i].code + '">' + data.data[i].code + '</option>')
                                                    }
                                                } else {
                                                    parkPlanning.append('<option value="' + data.data[i].code + '">' + data.data[i].code + '</option>')
                                                }
                                            }
                                        }
                                        form.render('select');
                                    }
                                })

                                /** 建设性质 */
                                $.ajax({
                                    url: "../eiaProject/getBuildPropList",
                                    type: 'post',
                                    success: function (data) {
                                        var natureConstructio = $('#natureConstructio');
                                        natureConstructio.append('<option value="">建设性质</option>');
                                        if (data.data) {
                                            for (var i = 0; i < data.data.length; i++) {
                                                if (result.data) {
                                                    if (result.data.natureConstructio) {
                                                        if (result.data.natureConstructio == data.data[i].code) {
                                                            natureConstructio.append('<option value="' + data.data[i].code + '" selected>' + data.data[i].code + '</option>')
                                                        } else {
                                                            natureConstructio.append('<option value="' + data.data[i].code + '">' + data.data[i].code + '</option>')
                                                        }
                                                    } else {
                                                        natureConstructio.append('<option value="' + data.data[i].code + '">' + data.data[i].code + '</option>')
                                                    }
                                                } else {
                                                    natureConstructio.append('<option value="' + data.data[i].code + '">' + data.data[i].code + '</option>')
                                                }
                                            }
                                        }
                                        form.render('select');
                                    }
                                })

                                form.render('select');
                            }
                        }
                    }
                });
            }

        }
        form.on('submit(save)', function (data) {
            var loadingIndex = layer.load(1, {time: 10 * 1000, shade: 0.1});
            //     console.log(data.field); //当前容器的全部表单字段，名值对形式：{name: value}
            data.field['projectComfee'] = $('#projectComfee').val()
            data.field['groundwaterFee'] = $('#groundwaterFee').val()
            data.field['otherFee'] = $('#otherFee').val()
            data.field['environmentalFee'] = $('#environmentalFee').val()
            data.field['expertFee'] = $('#expertFee').val()
            var actionUrl = "../eiaProject/eiaProjectSave?eiaProjectId=" + eiaProjectId;
            $.post(actionUrl, data.field, function (data) {
                if (data.code == 0) {
                    layer.msg('保存成功', {icon: 1, time: 1000, shade: 0.1}, function () {
                        parent.layer.closeAll();
                        parent.layui.table.reload("eiaProjectList");
                    });
                } else {
                    layer.msg('保存失败', {icon: 2, time: 2000, shade: 0.1}, function () {
                        parent.layer.closeAll();
                    });
                }
                layer.close(loadingIndex);
            });
            //提交成功后关闭自身
            // var index = parent.layer.getFrameIndex(window.name);
            // parent.layer.close(index);

            return false;
        })
    })


    var getParkEnvCod = function () {
        /** 选择园区环评开展情况 */
        $.ajax({
            url: "../eiaProject/getParkEnvCodList",
            type: 'POST',
            success: function (data) {
                var parkPlanning = $('#parkPlanning');
                parkPlanning.append('<option value="">选择园区环评开展情况</option>');
                if (data.data) {
                    for (var i = 0; i < data.data.length; i++) {
                        parkPlanning.append('<option value="' + data.data[i].code + '">' + data.data[i].code + '</option>')
                    }
                }
                form.render('select');
            }
        });
    }

    var getBuildProp = function () {
        /** 建设性质 */
        $.ajax({
            url: "../eiaProject/getBuildPropList",
            type: 'POST',
            success: function (data) {
                var natureConstructio = $('#natureConstructio');
                natureConstructio.append('<option value="">建设性质</option>');
                if (data.data) {
                    for (var i = 0; i < data.data.length; i++) {
                        natureConstructio.append('<option value="' + data.data[i].code + '">' + data.data[i].code + '</option>')
                    }
                }
                form.render('select');
            }
        });
    }

});

