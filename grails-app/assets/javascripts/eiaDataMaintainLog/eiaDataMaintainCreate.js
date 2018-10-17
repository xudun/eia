layui.use(['jquery', 'form'], function(){
    var $ = layui.jquery,
        form = layui.form;

    var busiTypeChecked=[];

    var taskRoleToastChecked = {},
        taskRoleExamChecked = [],
        taskRoleWriterChecked = [];

    var busiTypeTitle = [];

    /**
     * 分配人员树设置
     * @type {{data: {simpleData: {enable: boolean, pIdKey: string}, key: {url: null}}, callback: {onClick: orgStaffTreeSetting.callback.onClick}}}
     */
    var taskAssignUserTreeSetting = {
        data: {
            simpleData: {enable: true, pIdKey: "parent"},
            key: {url: null}
        },
        callback: {
            onClick: function (event, treeId, treeNode, clickFlag) {
                setFuncChecked(treeNode);
            }
        }
    };
    var taskAssignUserTree;
    /**
     * 分配人员树加载
     * @param staffNode
     */
    function initTaskAssignUserTree() {
        $.ajax({
            async: false,
            cache: false,
            type: 'post',
            dataType: "json",
            url: "../eiaTask/getOrgStaffTreeList",//请求的action路径
            error: function () {//请求失败处理函数
                alert('请求失败');
            },
            success: function (data) { //请求成功后处理函数。
                var zNodes;
                zNodes = data;   //把后台封装好的简单Json格式赋给treeNodes
                if (zNodes.length > 0) {
                    taskAssignUserTree = $.fn.zTree.init($("#taskAssignUser"), taskAssignUserTreeSetting, zNodes);
                    taskAssignUserTree.expandAll(false);
                }else{
                    taskAssignUserTree = $.fn.zTree.init($("#taskAssignUser"), taskAssignUserTreeSetting, "");
                    taskAssignUserTree.expandAll(false);
                }
            }
        });
    }
    /**
     * 分配人员树选中显示
     * @param roleNode
     */

    function setFuncChecked(roleNode) {
        if(!roleNode.isParent) {
            var getIndexById = function (arr, id) {
                var result = -1;
                for (var i = 0; i < arr.length; i++) {
                    if (arr[i].id === id) {
                        result = i;
                        return result;
                    }
                }
                return result;
            };

            var curRole = $('.elected-box .eb-group.active').attr('role');
            switch (curRole){
                case '1':
                    var str = "<a id='"+roleNode.id+"' class='elected-item'>" +
                        "<i class='larry-icon'>&#xe658;</i>" +
                        "<span class='item-name'>"+roleNode.name+"</span>" +
                        "<span class='deleteItemBtn larry-icon'>&#xe96b;</span>" +
                        "</a>";
                    var $str = $(str).clone()
                        .on('click','.deleteItemBtn',function () {

                            $(this).closest('.layui-field-box').empty();
                            $('#taskRoleToast').val('');
                            taskRoleToastChecked = {};

                        });
                    $('.elected-box .active .layui-field-box').empty().append($str);
                    taskRoleToastChecked = [{"id":roleNode.id,"staffId":roleNode.id,"name":roleNode.name,"staffName":roleNode.staffName,"orgId":roleNode.orgId,"orgName":roleNode.orgName,"orgCode":roleNode.orgCode}]
                    $('#taskRoleToast').val(JSON.stringify(taskRoleToastChecked));
                    break;
                case '2':  //项目审核人
                    if(getIndexById(taskRoleExamChecked, roleNode.id) === -1){
                        var str = "<a id='"+roleNode.id+"' class='elected-item'>" +
                            "<i class='larry-icon'>&#xe658;</i>" +
                            "<span class='item-name'>"+roleNode.name+"</span>" +
                            "<span class='deleteItemBtn larry-icon'>&#xe96b;</span>" +
                            "</a>";
                        var $str = $(str).clone()
                            .on('click','.deleteItemBtn',function () {
                                var index = $(this).closest('.elected-item').attr('id');
                                taskRoleExamChecked.splice(getIndexById(taskRoleExamChecked,index),1);
                                taskRoleExamChecked.length ? $('#taskRoleExam').val(JSON.stringify(taskRoleExamChecked)) : $('#taskRoleExam').val("");
                                $(this).closest('.elected-item').remove();
                            });
                        $('.elected-box .active .layui-field-box').empty().append($str);
                        taskRoleExamChecked = [{"id":roleNode.id,"staffId":roleNode.id,"name":roleNode.name,"staffName":roleNode.staffName,"orgId":roleNode.orgId,"orgName":roleNode.orgName,"orgCode":roleNode.orgCode}]
                        $('#taskRoleExam').val(JSON.stringify(taskRoleExamChecked));
                    }
                    else{
                        layer.msg('变更人员名称已选中', {icon: 2,anim: 6});
                    }
                    break;
            }
        }
    }

    //分配人员角色按钮
    $('.mid-arrow .layui-btn').click(function () {
        var index = $(this).closest('.layui-form-item').index();
        $(this).removeClass('layui-btn-primary').closest('.layui-form-item').siblings().find('.layui-btn').addClass('layui-btn-primary');
        $('.elected-box .eb-group').eq(index).addClass('active').siblings().removeClass('active');
    });

    //重置按钮动作
    $('.resetBtn').click(function () {
        $('#busiType,#taskAssignUserArr,#taskChargeUser').val('');
        $('.elected-box .eb-group .layui-field-box').empty();
        busiTypeChecked = [];
        taskRoleToastChecked = {};
        taskRoleExamChecked = [];
        taskRoleWriterChecked = [];
    });
    $(document).ready(function () {
        initTaskAssignUserTree();
    });

    //表单提交
    form.on('submit(save)', function(data){
        var loadingIndex = layer.load(1, {time: 10*1000,shade: 0.1});
        var actionUrl = "../eiaDataMaintainLog/dataMaintainSave";
        $.post(actionUrl, data.field, function (data) {
            if (data.code == 0) {
                layer.msg('保存成功', {icon: 1, time: 1000,shade: 0.1}, function () {
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.layui.table.reload("getEiaTaskDataList");
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.layer.close(index)
                });
            } else {
                layer.msg(data.msg, {icon: 2, time: 1000,shade: 0.1});
            }
            layer.close(loadingIndex);
        });
        return false;
    });
});