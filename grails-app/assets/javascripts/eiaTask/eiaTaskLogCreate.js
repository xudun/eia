layui.use(['jquery', 'form'], function () {
    var $ = layui.jquery,
        form = layui.form;

    var busiTypeChecked = [];

    var taskRoleToastChecked = [],
        taskRoleExamChecked = [],
        taskRoleWriterChecked = [];

    var busiTypeTitle = [];
    //渲染业务类型：
    var taskId = parent.$('#taskId').val();
    $.ajax({
        url: "../eiaTask/taskDomainBusType",
        type: "GET",
        data: {},
        dataType: "json",
        success: function (data) {
            $.each(data, function (index, value) {
                var str = "<input type='checkbox'  lay-filter='busiTypeCode' id='" + value.busiTypeCode + "' name='" + value.busiTypeCode + "' lay-verify='required' value='" + value.busiTypeCode + "' title='" + value.busiType + "'>"
                $('.busiTypeGroup').append(str);
            })
            form.render();
            $.ajax({
                url: "../eiaTask/eiaTaskDataMap?taskId=" + taskId,
                type: "post",
                cache: false,
                async: false,
                success: function (data) {
                    $('#taskName').val(data.data.taskName);
                    $('#taskNo').val(data.data.taskNo);
                    $('#taskLeaderDept').val(data.data.taskLeaderDept);
                    $('#taskLeaderDeptId').val(data.data.taskLeaderDeptId);
                    busiTypeChecked = data.data.busiTypeCode.split(',');
                    busiTypeTitle = data.data.busiType.split(',');
                    $('#busiTypeCode').val(busiTypeChecked);
                    $('#busiType').val(busiTypeTitle);
                    $.each(busiTypeChecked, function (index, value) {
                        $('#' + value).attr('checked', "checked");
                    });
                    form.render();
                }
            });
        }
    });

    //选中人员数组工具方法：根据id找索引
    var getIndexById = function (arr, id) {
        var result = -1;
        for (var i = 0; i < arr.length; i++) {
            if (arr[i].id == id) {
                result = i;
                return result;
            }
        }
        return result;
    };
    //渲染选中人员
    $.ajax({
        url: "../eiaTask/eiaTaskAssignDataList?taskId=" + taskId,
        //   url: "data/editTaskPersonData.json",
        type: "POST",
        data: {},
        dataType: "json",
        success: function (data) {
            $.each(data, function (index, elem) {
                var str = "<a id='" + elem.id + "' class='elected-item'>" +
                    "<i class='larry-icon'>&#xe658;</i>" +
                    "<span class='item-name'>" + elem.name + "</span>" +
                    "<span class='deleteItemBtn larry-icon'>&#xe96b;</span>" +
                    "</a>";
                var $str;
                if (elem.taskAssignRole == "主持编制人") {
                    $str = $(str).clone()
                        .on('click', '.deleteItemBtn', function () {
                            var index = $(this).closest('.elected-item').attr('id');
                            taskRoleToastChecked.splice(getIndexById(taskRoleToastChecked,index),1);
                            taskRoleToastChecked.length ? $('#taskRoleToast').val(JSON.stringify(taskRoleToastChecked)) : $('#taskRoleToast').val("");
                            $(this).closest('.elected-item').remove();
                        });
                    $('.elected-box .eb-group .layui-field-box').eq(0).append($str);
                    taskRoleToastChecked.push(elem);
                }
                else if(elem.taskAssignRole == "项目审核人"){
                    $str = $(str).clone()
                        .on('click', '.deleteItemBtn', function () {
                            var index = $(this).closest('.elected-item').attr('id');
                            taskRoleExamChecked.splice(getIndexById(taskRoleExamChecked,index),1);
                            taskRoleExamChecked.length ? $('#taskRoleExam').val(JSON.stringify(taskRoleExamChecked)) : $('#taskRoleExam').val("");
                            $(this).closest('.elected-item').remove();
                        });
                    $('.elected-box .eb-group .layui-field-box').eq(1).append($str);
                    taskRoleExamChecked.push(elem);
                }
                else if(elem.taskAssignRole == "项目编写人"){
                    $str = $(str).clone()
                        .on('click','.deleteItemBtn',function () {
                            var index = $(this).closest('.elected-item').attr('id');
                            taskRoleWriterChecked.splice(getIndexById(taskRoleWriterChecked,index),1);
                            taskRoleWriterChecked.length ? $('#taskRoleWriter').val(JSON.stringify(taskRoleWriterChecked)) : $('#taskRoleWriter').val("");
                            $(this).closest('.elected-item').remove();
                        });
                    $('.elected-box .eb-group .layui-field-box').eq(2).append($str);
                    taskRoleWriterChecked.push(elem);
                }
            });

            //         JSON.stringify(taskRoleToastChecked) == "{}" ? $('#taskRoleToast').val("") : $('#taskRoleToast').val(JSON.stringify(taskRoleToastChecked));
            taskRoleExamChecked.length ? $('#taskRoleExam').val(JSON.stringify(taskRoleExamChecked)) : $('#taskRoleExam').val("");
            taskRoleWriterChecked.length ? $('#taskRoleWriter').val(JSON.stringify(taskRoleWriterChecked)) : $('#taskRoleWriter').val("");
            taskRoleToastChecked.length ? $('#taskRoleToast').val(JSON.stringify(taskRoleToastChecked)) : $('#taskRoleToast').val("");

        }
    });


    //监听复选框
    form.on('checkbox(busiTypeCode)', function (data) {
        if (data.elem.checked) {
            busiTypeChecked.push(data.value);
            busiTypeTitle.push(data.elem.title);
        } else {
            var index = busiTypeChecked.indexOf(data.value);
            busiTypeChecked.splice(index, 1);
            busiTypeTitle.splice(index, 1);
        }
        $('#busiTypeCode').val(busiTypeChecked);
        $('#busiType').val(busiTypeTitle);

    });

    //下拉树
    $("#taskLeaderDept").dropDownForZ({
        url: "../eiaTask/getAuthOrgList",
        width: '99%',
        height: '350px',
        selecedSuccess: function (data) {
            $("#taskLeaderDeptId").val(data.orgId)
            var orgId = $("#orgId").val()
        }
    });
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
    function initTaskAssignUserTree(assignUserNode) {
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
                } else {
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
        if(!roleNode.isParent){
            var getIndexById = function (arr, id) {
                var result = -1;
                for(var i=0; i<arr.length; i++){
                    if(arr[i].id === id){
                        result = i;
                        return result;
                    }
                }
                return result;
            };
            var curRole = $('.elected-box .eb-group.active').attr('role');
            switch (curRole){
                case '1':  //主持编制人
                    if(getIndexById(taskRoleToastChecked, roleNode.id) === -1){
                        var str = "<a id='"+roleNode.id+"' class='elected-item'>" +
                            "<i class='larry-icon'>&#xe658;</i>" +
                            "<span class='item-name'>"+roleNode.name+"</span>" +
                            "<span class='deleteItemBtn larry-icon'>&#xe96b;</span>" +
                            "</a>";
                        var $str = $(str).clone()
                            .on('click','.deleteItemBtn',function () {
                                var index = $(this).closest('.elected-item').attr('id');
                                taskRoleToastChecked.splice(getIndexById(taskRoleToastChecked,index),1);
                                taskRoleToastChecked.length ? $('#taskRoleToast').val(JSON.stringify(taskRoleToastChecked)) : $('#taskRoleToast').val("");
                                $(this).closest('.elected-item').remove();
                            });
                        $('.elected-box .active .layui-field-box').append($str);
                        taskRoleToastChecked.push({"id":roleNode.id,"staffId":roleNode.id,"name":roleNode.name,"staffName":roleNode.staffName,"orgId":roleNode.orgId,"orgName":roleNode.orgName})
                        $('#taskRoleToast').val(JSON.stringify(taskRoleToastChecked));
                    }
                    else{
                        layer.msg('主持编制已选中', {icon: 2,anim: 6});
                    }
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
                        $('.elected-box .active .layui-field-box').append($str);
                        taskRoleExamChecked.push({"id":roleNode.id,"staffId":roleNode.id,"name":roleNode.name,"staffName":roleNode.staffName,"orgId":roleNode.orgId,"orgName":roleNode.orgName})
                        $('#taskRoleExam').val(JSON.stringify(taskRoleExamChecked));
                    }
                    else{
                        layer.msg('项目审核人已选中', {icon: 2,anim: 6});
                    }
                    break;
                case '3'://项目编写人
                    if(getIndexById(taskRoleWriterChecked, roleNode.id) === -1){
                        var str = "<a id='"+roleNode.id+"' class='elected-item'>" +
                            "<i class='larry-icon'>&#xe658;</i>" +
                            "<span class='item-name'>"+roleNode.name+"</span>" +
                            "<span class='deleteItemBtn larry-icon'>&#xe96b;</span>" +
                            "</a>";
                        var $str = $(str).clone()
                            .on('click','.deleteItemBtn',function () {
                                var index = $(this).closest('.elected-item').attr('id');
                                taskRoleWriterChecked.splice(getIndexById(taskRoleWriterChecked,index),1);
                                taskRoleWriterChecked.length ? $('#taskRoleWriter').val(JSON.stringify(taskRoleWriterChecked)) : $('#taskRoleWriter').val("");
                                $(this).closest('.elected-item').remove();
                            });
                        $('.elected-box .active .layui-field-box').append($str);
                        taskRoleWriterChecked.push({"id":roleNode.id,"staffId":roleNode.id,"name":roleNode.name,"staffName":roleNode.staffName,"orgId":roleNode.orgId,"orgName":roleNode.orgName});
                        $('#taskRoleWriter').val(JSON.stringify(taskRoleWriterChecked));
                    }
                    else{
                        layer.msg('项目编写人已选中', {icon: 2,anim: 6});
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

    $(document).ready(function () {
        initTaskAssignUserTree();
    });

    //表单提交
    form.on('submit(save)', function (data) {
        var loadingIndex = layer.load(1, {time: 10*1000,shade: 0.1});
        var actionUrl = "../eiaTaskLog/eiaTaskLogSave?taskId=" + taskId;
        $.post(actionUrl, data.field, function (data) {
            if (data.code == 0) {
                layer.msg('保存成功', {icon: 1, time: 1000,shade: 0.1}, function () {
                    parent.parent.layui.table.reload("getEiaTaskLogDataList");
                    parent.layui.table.reload("getEiaTaskDataList");
                    parent.parent.layer.closeAll();
                });
            } else {
                layer.msg(data.msg, {icon: 2, time: 1000,shade: 0.1});
            }
            layer.close(loadingIndex);
        });
        return false;
    });
});