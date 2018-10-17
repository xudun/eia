layui.use(['jquery', 'layer', 'table'], function(){
    var $ = layui.jquery,
        layer = layui.layer,
        table = layui.table;

    //渲染表格
    table.render({
        id: 'eiaHrEvalList',
        elem: '#eiaHrEvalList',
        //url:'/eia/eiaHrEval/getEiaHrEvalList',
        url:'/eia/eiaHrEval/getEiaHrEvalDataList',
        toolbar: '#tableTopTmp',
        defaultToolbar:['filter', 'print', 'exports'],
        cols: [[
            {fixed: 'left', title: '序号',width:'5%',align: "center",templet: "#indexTable"},
            {field:'orgName', title: '机构名称',align: "center"},
            {field:'assessmentMonth', title: '考核月份',align: "center"},
            {field:'jobRatingType', title: '类型',align: "center"},
            {fixed: 'right', title: '操作',width:380,align: "center", toolbar: '#eiaHrEvalListBar',align: "center"}
        ]],
        page: true,
        even: true,
        limit: 10
    });

    //监听工具条
    table.on('tool(eiaHrEvalList)', function (obj) {
        var data = obj.data;
        var eiaHrEvalId = data.id
        $('#eiaHrEvalId').val(data.id);
        $('#jobRatingType').val(data.jobRatingType);
        $('#assessmentMonth').val(data.assessmentMonth);
        $('#orgId').val(data.orgId);
        //员工互评打分
        if (obj.event === 'eiaHrEvalEmpToEmpInput') {
            $.ajax({
                type:'post',
                url:'/eia/eiaHrEvalScoreDetail/isHaveEmp?eiaHrEvalId='+eiaHrEvalId,
                success:function(data){
                    if(data =='isHave'){
                        layer.msg('该部门没有职员角色！', {icon:7});
                        return;
                    }else if(data =='isNotEval'){
                        layer.msg('您没有打分权限，不能参与打分！', {icon:7});
                        return
                    }else{
                        var type ="person"
                       var pageUrl = "/eia/eiaHrEvalScoreDetail/eiaHrEvalEmpToEmpInput?eiaHrEvalId="+eiaHrEvalId+"&type="+type;
                        layer.open({
                            type : 2,
                            title :" ",
                            shade: false,
                            maxmin: true,
                            skin: 'larry-green',
                            area: ['100%', '100%'],
                            content: pageUrl,
                            min: function () {
                                $(".layui-layer-title").text("员工考核表");
                            },
                            restore: function () {
                                $(".layui-layer-title").text(" ");
                            }
                        });
                    }
                }
            })
        }
        //员工给助理打分
        else if (obj.event === 'eiaHrEvalEmpToAssInput') {
            $.ajax({
                type:'post',
                url:'/eia/eiaHrEvalScoreDetail/isHaveAss?eiaHrEvalId='+eiaHrEvalId,
                success:function(data){
                    if(data =='isHave'){
                        layer.msg('该部门没有助理角色！', {icon:7});
                        return;
                    }else if(data =='isNotEval'){
                        layer.msg('您没有打分权限，不能参与打分！', {icon:7});
                        return
                    }else{
                        var type ="ass"
                        var pageUrl = "/eia/eiaHrEvalScoreDetail/eiaHrEvalEmpToAssInput?eiaHrEvalId="+eiaHrEvalId+"&type="+type;
                        layer.open({
                            type : 2,
                            title :" ",
                            shade: false,
                            maxmin: true,
                            skin: 'larry-green',
                            area: ['100%', '100%'],
                            content: pageUrl,
                            min: function () {
                                $(".layui-layer-title").text("助理考核表");
                            },
                            restore: function () {
                                $(".layui-layer-title").text(" ");
                            }
                        });
                    }
                }
            })
        }
        //员工给助理打分
        else if (obj.event == 'eiaHrEvalAssToAssInput') {
            $.ajax({
                type:'get',
                url:'/eia/static/js/layuiFrame/data/isHaveEmp.json?eiaHrEvalId='+eiaHrEvalId,
                success:function(data){
                    if(data =='assNum'){
                        layer.msg('您不能给自己打分！', {icon:7});
                        return;
                    }else{
                        var type ="ass"
                        var pageUrl = "/eia/eiaHrEval/eiaHrEvalEmpToAssInput?eiaHrEvalId="+eiaHrEvalId+"&type="+type;
                        layer.open({
                            type : 2,
                            title :" ",
                            shade: false,
                            maxmin: true,
                            skin: 'larry-green',
                            area: ['100%', '100%'],
                            content: pageUrl,
                            min: function () {
                                $(".layui-layer-title").text("助理考核表");
                            },
                            restore: function () {
                                $(".layui-layer-title").text(" ");
                            }
                        });
                    }
                }
            })
        }
        //部门经理给员工打分
        else if (obj.event === 'eiaHrEvalDMToEmpInput') {
            $.ajax({
                type:'post',
                url:'/eia/eiaHrEvalScoreDetail/isHaveEmp?eiaHrEvalId='+eiaHrEvalId,
                success:function(data){
                    if(data =='isHave'){
                        layer.msg('该部门没有职员角色！', {icon:7});
                        return;
                    }else{
                        var type ="emp"
                        var pageUrl = "/eia/eiaHrEvalScoreDetail/eiaHrEvalDMToEmpInput?eiaHrEvalId="+eiaHrEvalId+"&type="+type;
                        layer.open({
                            type : 2,
                            title :" ",
                            shade: false,
                            maxmin: true,
                            skin: 'larry-green',
                            area: ['100%', '100%'],
                            content: pageUrl,
                            min: function () {
                                $(".layui-layer-title").text("员工考核表");
                            },
                            restore: function () {
                                $(".layui-layer-title").text(" ");
                            }
                        });
                    }
                }
            })
        }
        //部门经理给助理打分
        else if (obj.event === 'eiaHrEvalDMToAssInput') {
            $.ajax({
                type:'get',
                url:'/eia/eiaHrEvalScoreDetail/isHaveAss?eiaHrEvalId='+eiaHrEvalId,
                success:function(data){
                    if(data =='isHave'){
                        layer.msg('该部门没有助理角色！', {icon:7});
                        return;
                    }else{
                        var type ="empAss"
                        var pageUrl = "/eia/eiaHrEvalScoreDetail/eiaHrEvalDMToAssInput?eiaHrEvalId="+eiaHrEvalId+"&type="+type;
                        layer.open({
                            type : 2,
                            title :" ",
                            shade: false,
                            maxmin: true,
                            skin: 'larry-green',
                            area: ['100%', '100%'],
                            content: pageUrl,
                            min: function () {
                                $(".layui-layer-title").text("助理考核表");
                            },
                            restore: function () {
                                $(".layui-layer-title").text(" ");
                            }
                        });
                    }
                }
            })
        }
        //员工互评打分详情
        else if(obj.event == 'eiaHrEvalEmpToEmpDetail'){
            var type ="person"
            var pageUrl = "/eia/eiaHrEvalScoreDetail/eiaHrEvalEmpToEmpDetail?eiaHrEvalId="+eiaHrEvalId+"&type="+type;
            layer.open({
                type : 2,
                title :" ",
                shade: false,
                maxmin: true,
                skin: 'larry-green',
                area: ['100%', '100%'],
                content: pageUrl,
                min: function () {
                    $(".layui-layer-title").text("员工考核表");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        }
        //员工给助理打分详情
        else if(obj.event == 'eiaHrEvalEmpToAssDetail'){
            var type ="ass";
            var pageUrl = "/eia/eiaHrEvalScoreDetail/eiaHrEvalEmpToAssDetail?eiaHrEvalId="+eiaHrEvalId+"&type="+type;
            layer.open({
                type : 2,
                title :" ",
                shade: false,
                maxmin: true,
                skin: 'larry-green',
                area: ['100%', '100%'],
                content: pageUrl,
                min: function () {
                    $(".layui-layer-title").text("员工考核表");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        }
        //经理给员工打分详情
        else if(obj.event == 'eiaHrEvalDMToEmpDetail'){
            var type ="mager"
            var pageUrl = "/eia/eiaHrEvalScoreDetail/eiaHrEvalDMToEmpDetail?eiaHrEvalId="+eiaHrEvalId+"&type="+type;
            layer.open({
                type : 2,
                title :" ",
                shade: false,
                maxmin: true,
                skin: 'larry-green',
                area: ['100%', '100%'],
                content: pageUrl,
                min: function () {
                    $(".layui-layer-title").text("员工考核表");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        }
        //经理给助理打分详情
        else if(obj.event == 'eiaHrEvalDMToAssDetail'){
            var type ="magerAss"
            var pageUrl = "/eia/eiaHrEvalScoreDetail/eiaHrEvalDMToAssDetail?eiaHrEvalId="+eiaHrEvalId+"&type="+type;
            layer.open({
                type : 2,
                title :" ",
                shade: false,
                maxmin: true,
                skin: 'larry-green',
                area: ['100%', '100%'],
                content: pageUrl,
                min: function () {
                    $(".layui-layer-title").text("员工考核表");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        }
        //员工得分打印
        else if(obj.event == 'eiaHrEvalEmpPrint'){
            var pageUrl = "/eia/eiaHrEvalScore/eiaHrEvalPrintEmp?eiaHrEvalId="+eiaHrEvalId;
            layer.open({
                type : 2,
                title :" ",
                shade: false,
                maxmin: true,
                skin: 'larry-green',
                area: ['100%', '100%'],
                content: pageUrl,
                min: function () {
                    $(".layui-layer-title").text("员工考核表");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        }
        //助理得分打印
        else if(obj.event == 'eiaHrEvalAssPrint'){
            var pageUrl = "/eia/eiaHrEvalScore/eiaHrEvalPrintAss?eiaHrEvalId="+eiaHrEvalId;
            layer.open({
                type : 2,
                title :" ",
                shade: false,
                maxmin: true,
                skin: 'larry-green',
                area: ['100%', '100%'],
                content: pageUrl,
                min: function () {
                    $(".layui-layer-title").text("助理考核表");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        }
        //员工打分提交
        else if(obj.event == 'eiaHrEvalEmpSubmit'){
            var loadingIndex = layer.load(1, {time: 10*1000,shade: 0.1});
            $.ajax({
                type:'POST',
                url:'/eia/eiaHrEvalScoreDetail/isGrade?eiaHrEvalId='+eiaHrEvalId,
                success:function(data){
                    if(data.code=='0'){
                        layer.msg('您尚未打分，不能提交！', {icon:7});
                        return;
                    }else{
                        layer.confirm("是否提交，提交后不能修改？",{icon:3}, function(index){
                            $.post("/eia/eiaHrEvalScoreDetail/empRatingSubmit",{eiaHrEvalId:eiaHrEvalId},function(data){
                                if(data.code=='0'){
                                    layer.msg('提交成功！',{icon:1,time:1500,shade: 0.1},function(){
                                        table.reload('eiaHrEvalList')
                                    });
                                }else{
                                    layer.msg('提交失败！',{icon:1,time:1500,shade: 0.1},function(){
                                        table.reload('eiaHrEvalList')
                                    });                    }
                            });
                        });
                    }
                },
                complete: function () {
                    layer.close(loadingIndex);
                }
            })
        }
        //员工得分情况
        else if(obj.event == 'eiaHrEvalEmp'){
            var pageUrl = "/eia/eiaHrEvalScore/eiaHrEvalEmp?eiaHrEvalId="+eiaHrEvalId;
            layer.open({
                type : 2,
                title :" ",
                shade: false,
                maxmin: true,
                skin: 'larry-green',
                area: ['100%', '100%'],
                content: pageUrl,
                min: function () {
                    $(".layui-layer-title").text("员工考核表");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        }
        //员工的得分详情计算
        else if(obj.event == 'eiaHrEvalScoreEmp'){
            $.ajax({
                type:'POST',
                url:'/eia/eiaHrEvalScore/isAllGrade?eiaHrEvalId='+eiaHrEvalId,
                success:function(data){
                    if(data == "empMag"){
                        layer.msg('助理没有打分完成！', {icon:7});
                        return;
                    }else if(data.split("@")[0]=="isScore"){
                        layer.msg('该部门未完成打分，未打分人员：'+data.split("@")[1], {icon:7,time:6000});
                        return;
                    }else if(data =='assMag'){
                        layer.msg('助理打分没有全部完成！', {icon:7});
                        return;
                    }else if(data =='magerMag'){
                        layer.msg('部门经理未完成员工打分！', {icon:7});
                        return;
                    }else if(data =='magerAssMag'){
                        layer.msg('部门经理未完成助理打分！', {icon:7});
                        return;
                    }else {
                        var title = "员工考核表";
                        var pageUrl = "/eia/eiaHrEvalScore/eiaHrEvalScoreEmp?eiaHrEvalId="+eiaHrEvalId;
                        layer.open({
                            type : 2,
                            title :"<span class='glyphicon glyphicon-plus'></span> " + title,
                            shade: false,
                            maxmin: true,
                            skin: 'layui-layer-rim',
                            area: ['100%', '100%'],
                            content: pageUrl
                        });
                    }
                }
            })
        }
        //助理的得分详情计算
        else if(obj.event == 'eiaHrEvalScoreAcc'){
            $.ajax({
                type:'POST',
                url:'/eia/eiaHrEvalScore/isAllGrade?eiaHrEvalId='+eiaHrEvalId+"&eiaScoreType=1",
                success:function(data){
                    if(data == "empMag"){
                        layer.msg('助理没有打分完成！', {icon:7});
                        return;
                    }else if(data.split("@")[0]=="isScore"){
                        layer.msg('该部门未完成打分，未打分人员：'+data.split("@")[1], {icon:7,time:6000});
                        return;
                    } else if(data =='assMag'){
                        layer.msg('助理打分没有全部完成！', {icon:7});
                        return;
                    }else if(data =='magerMag'){
                        layer.msg('部门经理未完成员工打分！', {icon:7});
                        return;
                    }else if(data =='magerAssMag'){
                        layer.msg('部门经理未完成助理打分！', {icon:7});
                        return;
                    } else if(data=='isNotAss'){
                        layer.msg('该部门没有助理角色！', {icon:7});
                        return;
                    }else{
                        var title = "员工考核表";
                        var pageUrl = "/eia/eiaHrEvalScore/eiaHrEvalScoreAss?eiaHrEvalId="+eiaHrEvalId;
                        layer.open({
                            type : 2,
                            title :"<span class='glyphicon glyphicon-plus'></span> " + title,
                            shade: false,
                            maxmin: true,
                            skin: 'layui-layer-rim',
                            area: ['100%', '100%'],
                            content: pageUrl
                        });
                    }
                }
            })
        }
    });
    $('.larry-btn a.layui-btn').click(function () {
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });
    var active = {
        //机构考核人员详情
        assDetailsAll: function () {
            var type = 'personAll'
            $.ajax({
                type: 'POST',
                url: '/eia/eiaHrEvalScore/isAllGrade',
                success: function (data) {
                    if (data == 'empMag') {
                        layer.msg('员工互评没有全部完成！', {icon: 7});
                        return;
                    } else if (data == 'assMag') {
                        layer.msg('助理打分没有全部完成！', {icon: 7});
                        return;
                    } else if (data == 'magerMag') {
                        layer.msg('部门经理打分没有完成员工打分！', {icon: 7});
                        return;
                    } else if (data == 'magerAssMag') {
                        layer.msg('部门经理打分完成助理打分！', {icon: 7});
                        return;
                    } else {
                        var pageUrl = "/eia/eiaHrEvalScore/eiaHrEvalStatis?type=" + type;
                        layer.open({
                            type: 2,
                            title: " ",
                            shade: false,
                            maxmin: true,
                            skin: 'larry-green',
                            area: ['100%', '100%'],
                            content: pageUrl,
                            min: function () {
                                $(".layui-layer-title").text("机构考核人员详情");
                            },
                            restore: function () {
                                $(".layui-layer-title").text(" ");
                            }
                        });
                    }
                }
            })
        }
    }

    table.on('toolbar(eiaHrEvalList)', function(obj) {
        switch (obj.event) {
            case 'assDetailsAll':
                var type = 'personAll'
                $.ajax({
                    type: 'POST',
                    url: '/eia/eiaHrEvalScore/isAllGrade',
                    success: function (data) {
                        if (data == 'empMag') {
                            layer.msg('员工互评没有全部完成！', {icon: 7});
                            return;
                        } else if (data == 'assMag') {
                            layer.msg('助理打分没有全部完成！', {icon: 7});
                            return;
                        } else if (data == 'magerMag') {
                            layer.msg('部门经理打分没有完成员工打分！', {icon: 7});
                            return;
                        } else if (data == 'magerAssMag') {
                            layer.msg('部门经理打分完成助理打分！', {icon: 7});
                            return;
                        } else {
                            var pageUrl = "/eia/eiaHrEvalScore/eiaHrEvalStatis?type=" + type;
                            layer.open({
                                type: 2,
                                title: " ",
                                shade: false,
                                maxmin: true,
                                skin: 'larry-green',
                                area: ['100%', '100%'],
                                content: pageUrl,
                                min: function () {
                                    $(".layui-layer-title").text("机构考核人员详情");
                                },
                                restore: function () {
                                    $(".layui-layer-title").text(" ");
                                }
                            });
                        }
                    }
                })
                break;
        }
    })
})