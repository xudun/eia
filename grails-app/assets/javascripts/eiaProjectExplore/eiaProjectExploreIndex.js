/**
 * Created by XinXi-001 on 2018/4/16.
 */
layui.use(['jquery', 'layer', 'form','table'], function () {
    var $ = layui.jquery,
        layer = layui.layer,
        form = layui.form,
        table = layui.table;

    var ifAdd = getParamFromUrl(document.location.href, "ifAdd");

    //渲染表格
    table.render({
        id: 'eiaProjectList',
        elem: '#eiaProjectList',
        url: '/eia/eiaProjectExplore/eiaProjectExploreQueryPage',
        toolbar: '#tableTopTmp',
        defaultToolbar:['filter', 'print', 'exports'],
        cols: [[
            {fixed: 'left', title: '序号', width: '10%', align: "center", templet: "#indexTable"},
            {field: 'exploreNo', width: '10%', title: '内审单编号', align: "center"},
            {field: 'projectName', width: '20%', title: '项目名称', align: "center"},
            {field: 'environmentaType', width: '15%', title: '行业类别', align: "center"},
            {field: 'inputDept', width: '15%', title: '录入部门', align: "center"},
            {field: 'inputUser', width: '10%', title: '录入人', align: "center"},
            {fixed: 'right', title: '操作', width: '20%', align: "center", toolbar: '#mlTool', align: "center"}
        ]],
        page: true,
        even: true,
        limit: 10,
        done: function () {
            if(ifAdd == '1'){
                $('.addBtn').trigger('click');
            }
        }
    });
    //高级查询
    form.on('submit(query)', function () {
        table.reload("eiaProjectList",{
            where : {
                key : eiaProjectKey()
            }, page: {
                curr: 1
            }
        });
        return false;
    });
    //高级查询数据项
    function eiaProjectKey() {
        var fileType = $("#fileType").val();
        var buildArea = $("#buildArea").val();
        var key = {
            fileType: fileType,
            buildArea: buildArea,
        };
        return key;
    }
    //监听工具条
    table.on('tool(eiaProjectList)', function (obj) {
        var data = obj.data;
        var eiaProjectExploreId = data.id
       if (obj.event === 'projectDel') {    //删除
            layer.confirm('确定要删除该内审单吗?', {icon: 3}, function (index) {
                var loadingIndex = layer.load(1, {time: 10*1000,shade: 0.1});
                $.post("../eiaProjectExplore/eiaProjectExploreDel", {eiaProjectExploreId: data.id}, function (data) {
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
            }, function (index) {
                //取消
            });

        }
        else if (obj.event === 'projectDetail') {    //查看
            pageUrl = '/eia/eiaProjectExplore/eiaProjectExploreDetail?eiaProjectExploreId=' + eiaProjectExploreId;
            var index = layer.open({
                title: ' ',
                type: 2,
                shade: false,
                maxmin: true,
                skin: 'larry-green',
                area: ['100%', '100%'],
                content: pageUrl,
                success: function (layero, index) {
                    var body = layer.getChildFrame('body', index);
                    body.find('').val(data.eiaClientId);
                },
                end: function () {
                    table.reload("eiaProjectList");
                },
                min: function () {
                    $(".layui-layer-title").text("查看客户");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        }
        else if (obj.event == 'projectEdit') {
            pageUrl = '/eia/eiaProjectExplore/eiaProjectExploreCreate?eiaProjectExploreId=' + eiaProjectExploreId + '&pageType=edit';
            var index = layer.open({
                title: ' ',
                type: 2,
                shade: false,
                maxmin: true,
                skin: 'larry-green',
                area: ['100%', '100%'],
                content: pageUrl,
                success: function (layero, index) {
                },
                end: function () {
                    table.reload('eiaProjectList');
                },
                min: function () {

                },
                restore: function () {

                }
            });
        } else if (obj.event === 'projectFlow') {//流程
           actionUrl = '/eia/eiaProjectPlan/checkProjectFlow?eiaProjectId='+eiaProjectId;
           $.post(actionUrl, data.field, function (data) {
               if (data.code == 0) {
                   pageUrl = '/eia/eiaWorkFlow/eiaWorkFlowProject';
                   var index = layer.open({
                       title: ' ',
                       type: 2,
                       shade: false,
                       maxmin: true,
                       skin: 'larry-green',
                       area: ['100%', '100%'],
                       content: pageUrl,
                       before:function(){

                       },
                       success: function (layero, index) {
                       },
                       end: function () {
                           $('#eiaProjectId').val("");
                       },
                       min: function () {
                           $(".layui-layer-title").text("项目流程");
                       },
                       restore: function () {
                           $(".layui-layer-title").text(" ");
                       }
                   });
               } else {
                   var msg = data.msg
                   layer.msg(data.msg, {icon: 2, time: 2000}, function () {
                   });
               }
           });

        }
       else if (obj.event === 'projectExploreSub') {
           layer.confirm('确定要提交该内审单吗?', {icon: 3}, function (index) {
               var eiaCertId = data.id;
               var loadingIndex = layer.load(1, {time: 10 * 1000, shade: 0.1});
               $.post("../eiaProjectExplore/eiaProjectExploreSub", {eiaProjectExploreId: eiaProjectExploreId}, function (result) {
                   if (result.code == 0) {
                       table.reload('eiaProjectList');
                       layer.msg("内审单提交成功！", {icon: 1, time: 1500, shade: 0.1})
                   } else {
                       layer.msg(result.msg, {icon: 2, time: 1500, shade: 0.1})
                   }
                   layer.close(loadingIndex);
               });
           }, function (index) {
               //取消
           });
       }
       else if (obj.event === 'projectCoverDown') {     //封皮下载
           var loadingIndex = layer.load(1, {time: 10*1000,shade: 0.1});
           $.ajax({
               url: "/eia/exportProject/checkProjectType?eiaProjectId=" + eiaProjectId,
               type: "POST",
               data: {},
               dataType: "json",
               success: function (res) {
                   if (res.code == 0) {
                       window.location.href = "../exportProject/exportProject?eiaProjectId=" + eiaProjectId;
                       layer.msg('正在导出...', {icon: 16, shade: 0.01}, function () {
                           var index = parent.layer.getFrameIndex(window.name);
                           parent.layer.close(index);
                       })
                   } else {
                       layer.msg(res.msg, {icon: 2, time: 1000,shade: 0.1});
                   }
                   layer.close(loadingIndex);
               }
           });
       }else if(obj.event === 'projectExploreFlow'){
           ajaxBox("/eia/eiaWorkFlowBusi/checkWorkFlow", {
               tableNameId: data.id,
               tableName: "EiaProjectExplore"
           }, function (res) {
               if (res.code == 0) {
                   pageUrl = '/eia/eiaWorkFlow/eiaWorkFlowTrans';
                   $('#tableNameId').val(data.id);
                   $('#tableName').val("EiaProjectExplore");
                   var index = layer.open({
                       title: ' ',
                       type: 2,
                       shade: false,
                       maxmin: true,
                       skin: 'larry-green',
                       area: ['100%', '100%'],
                       content: pageUrl,
                       success: function (layero, index) {
                       },
                       end: function () {
                           $('#tableNameId').val("");
                           $('#tableName').val("");
                       },
                       min: function () {
                           $(".layui-layer-title").text("内审审批流程流程");
                       },
                       restore: function () {
                           $(".layui-layer-title").text(" ");
                       }
                   });
               }else{
                   layer.msg(res.msg,{time: 1500,shade: 0.1});
               }
           });
       }
    });

    //监听头部工具栏事件
    //监听事件
    table.on('toolbar(eiaProjectList)', function(obj){
        switch(obj.event){
            case 'getSelect':
                var projectName = $("#projectName").val();
                table.reload("eiaProjectList", {
                    where: {
                        projectName: projectName
                    }
                });
                break;
            case 'projectAdd':
                pageUrl = '/eia/eiaProjectExplore/eiaProjectExploreCreate?pageType=0';
                var index = layer.open({
                    title: ' ',
                    type: 2,
                    shade: false,
                    maxmin: true,
                    skin: 'larry-green',
                    area: ['100%', '100%'],
                    content: pageUrl,
                    success: function (layero, index) {
                        ifAdd = 0;
                    },
                    end: function () {
                        table.reload("eiaProjectList");
                        $("#eiaProjectId").val("");
                        $("#eiaTaskId").val("");
                        $("#eiaLabOfferId").val("");
                    },
                    min: function () {

                    },
                    restore: function () {

                    }
                });
                break;
        }
    });
});
function queryOpen() {
    $("#advanced-query").removeClass("display-none");
    $("#open").addClass("display-none");
    $("#close").removeClass("display-none");
}
function queryClose() {
    $("#advanced-query").addClass("display-none");
    $("#open").removeClass("display-none");
    $("#close").addClass("display-none");
}