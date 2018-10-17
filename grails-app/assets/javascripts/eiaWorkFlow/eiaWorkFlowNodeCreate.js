layui.use(['jquery', 'layer', 'form', 'table'], function(){
    var $ = layui.jquery,
        form = layui.form,
        layer = layui.layer,
        table = layui.table;

    var $nodesIconName = $('#nodesIconName'),
        $nodesColor = $('#nodesColor');

    //工具函数在url中获取指定参数值
    var getParamFromUrl = function(url,param){
        if(url.indexOf('?')!==-1){
            var params = url.split("?")[1].split('&');
            for(var i=0; i<params.length; i++){
                if(params[i].indexOf(param) !== -1){
                    return params[i].replace(param+"=","");
                }
            }
            // console.log('该url中无参数'+param);
        }else{
            // console.log('该url中无参数');
        }

    };

    //页面类型：0:新建 ； 1：编辑
    var pageType = getParamFromUrl(document.location.href,"pageType");
    //设置页面名称
    var $pageTitle = $('.pageTitle');
    pageType == 0 ? $pageTitle.html('新增流程节点') : $pageTitle.html('编辑流程节点');

    //重置按钮显隐
    pageType == 0 ? "" : $('.resetBtn').hide();

    var eiaWorkFlowId = parent.$('#eiaWorkFlowId').val();
    $('#eiaWorkFlowId').val(eiaWorkFlowId);
    var eiaWorkFlowNodeId = parent.$('#eiaWorkFlowNodeId').val();
    $('#eiaWorkFlowNodeId').val(eiaWorkFlowNodeId);

    //渲染表格
    table.render({
        id: 'eiaWorkFlowNodeProcessList',
        elem: '#eiaWorkFlowNodeProcessList',
        url:'/eia/eiaWorkFlowNodeProcess/getEiaWorkFlowNodeProcessDataList?eiaWorkFlowId=' + eiaWorkFlowId + '&eiaWorkFlowNodeId=' + eiaWorkFlowNodeId,
        toolbar: '#tableTopTmp',
        defaultToolbar:['filter', 'print', 'exports'],
        cols: [[
            {fixed: 'left', title: '序号',width:"7%",align: "center",templet: "#indexTable"},
            {field:'processName',width:'15%', title: '按钮名称',align: "center"},
            {field:'processShowName',width:'15%', title: '按钮别名',align: "center"},
            {field:'processNum',width:'15%', title: '按钮位置序号',align: "center"},
            {field:'processCode',width:'15%', title: '按钮编码',align: "center"},
            {field:'processUrl',width:'15%', title: '按钮URL',align: "center"},
            {field:'processUrlParams',width:'15%', title: '按钮URL参数',align: "center"},
            {field:'processColor',width:'15%', title: '按钮颜色',align: "center", templet: '#colorTp'},
            {field:'processIconName',width:'15%', title: '按钮图标',align: "center", templet: '#iconTp'},
            {fixed: 'right', title: '操作',width:"15%",align: "center", toolbar: '#mlTool',align: "center"}
        ]],
        page: true,
        even: true,
        limit: 10,
        done: function () {
            //新增节点动作按钮显隐
            pageType == 0 ? $('.addFlowNodeProcessBtn').css('display','none') : $('.noticeTag').css('display','none');
        }
    });

    //流程节点颜色监听
    $nodesColor.change(function () {
        var cur_val = $(this).val();
        $(this).closest('.layui-form-item').find('.layui-badge-rim').css('background',cur_val);
    });
    //流程节点图标监听
    $nodesIconName.change(function () {
        var cur_val = $(this).val();
        var str = "<i class='larry-icon'>"+cur_val+"</i>";
        $(this).closest('.layui-form-item').find('.sq-box').empty().append(str);
    });

    //基本信息回显
    if(pageType == 1){
        $.ajax({
            url:"/eia/eiaWorkFlowNode/getEiaWorkFlowNodeDataMap?eiaWorkFlowNodeId=" + eiaWorkFlowNodeId,
            type:"POST",
            data:{},
            dataType: "json",
            success: function (result) {
                if (result) {
                    $('#eiaWorkFlowNodeId').val(result.data.id);
                    $('#eiaWorkFlowId').val(result.data.eiaWorkFlowId);
                    $('#nodesCode').val(result.data.nodesCode);
                    $('#nodesName').val(result.data.nodesName);
                    $('#nodesAuthCode').val(result.data.nodesAuthCode);
                    $('#nodesUrl').val(result.data.nodesUrl);
                    $nodesColor.val(result.data.nodesColor);
                    $('#nodesNum').val(result.data.nodesNum);
                    $('#nodesAuthType').val(result.data.nodesAuthType);
                    $('#nodesTabTitle').val(result.data.nodesTabTitle);
                    $nodesIconName.val(result.data.nodesIconName);
                    if (result.data.nodesAuthType == "AUTH_CODE") {
                        $('#AUTH_CODE').attr("selected", "");
                    } else if (result.data.nodesAuthType == "USER_CODE") {
                        $('#USER_CODE').attr("selected", "");
                    }
                    form.render('select');
                    if(result.data.ifIcon==true){
                        $('#ifIconSwitch').next().trigger('click');
                    }

                    var str = "<i class='larry-icon'>" + result.data.nodesIconName + "</i>";
                    $nodesIconName.closest('.layui-form-item').find('.sq-box').empty().append(str);
                    $nodesColor.closest('.layui-form-item').find('.layui-badge-rim').css('background', result.data.nodesColor);

                }
            }
        });
    }

    //监听 是否显示图片 switch
    form.on('switch(ifIconSwitch)', function (data) {
        $('#ifIcon').val(data.elem.checked);
    });
    // var tableUrl = pageType == 0 ? 'data/eiaWorkFlowNodeProcessListNoData.json' : 'data/eiaWorkFlowNodeProcessListData.json';

    //监听工具条
    table.on('tool(eiaWorkFlowNodeProcessList)', function (obj) {
        var data = obj.data;
        $('#eiaWorkFlowNodeProcessId').val(data.id);
        if (obj.event === 'eiaEdit') {    //编辑
            pageUrl = '/eia/eiaWorkFlowNodeProcess/eiaWorkFlowNodeProCreate?pageType=1';
            var index = layer.open({
                title:' ',
                type: 2,
                shade: false,
                maxmin: true,
                skin: 'larry-green',
                area: ['100%', '100%'],
                content: pageUrl,
                success:function (layero, index) {
                    var body = layer.getChildFrame('body', index);
                    body.find('#eiaWorkFlowNodeProcessId').val(data.id);
                },
                end: function () {
                    layui.table.reload("eiaWorkFlowNodeProcessList",{
                        url:'/eia/eiaWorkFlowNodeProcess/getEiaWorkFlowNodeProcessDataList?eiaWorkFlowId=' + eiaWorkFlowId + '&eiaWorkFlowNodeId=' + eiaWorkFlowNodeId,
                    });
                    $('#eiaWorkFlowNodeProcessId').val("");
                },
                min: function () {
                    $(".layui-layer-title").text("编辑节点动作");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        }
        else if(obj.event === 'eiaCheck'){    //查看
            var pageUrl = '/eia/eiaWorkFlowNodeProcess/eiaWorkFlowNodeProDetail';
            var index = layer.open({
                title:' ',
                type: 2,
                shade: false,
                maxmin: true,
                skin: 'larry-green',
                area: ['100%', '100%'],
                content: pageUrl,
                success:function (layero, index) {
                    var body = layer.getChildFrame('body', index);
                    body.find('#eiaWorkFlowNodeProcessId').val(data.id);
                },
                end: function () {
                    $('#eiaWorkFlowNodeProcessId').val("");
                },
                min: function () {
                    $(".layui-layer-title").text("节点动作详情");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        }
        else if(obj.event === 'eiaDel'){    //删除
            layer.confirm('确定要删除该节点动作吗?', {icon: 3}, function (index) {
                var loadingIndex = layer.load(1, {time: 10*1000,shade: 0.1});
                $.post("../eiaWorkFlowNodeProcess/eiaWorkFlowNodeProcessDel", {eiaWorkFlowNodeProcessId: data.id}, function (result) {
                    if (result.code == 0) {
                        layer.msg('删除成功！', {icon: 1, time: 1500,shade: 0.1}, function () {
                            table.reload("eiaWorkFlowNodeProcessList");
                        });
                    } else {
                        if (result.msg) {
                            layer.msg("处理失败！<br/><div style='color:red;'>错误信息：" + result.msg + "</div>", {
                                icon: 2,
                                time: 1500
                            });
                        } else {
                            layer.msg('删除失败！', {icon: 2, time: 1500,shade: 0.1});
                        }
                    }
                    layer.close(loadingIndex);
                });
            },function (index) {
                //取消
            });
        }
    });

    //“新增流程节点”按钮的显示与隐藏方法
    var addFlowNodeProcessBtnShow = function(){
        $('.addFlowNodeProcessBtn').css('display','inline-block');
        $('.noticeTag').css('display','none');
    };
    var addFlowNodeProcessBtnHide = function(){
        $('.addFlowNodeProcessBtn').css('display','none');
        $('.noticeTag').css('display','inline-block');
    };

    //表单提交
    form.on('submit(save)', function(data){
        console.log(data.field); //当前容器的全部表单字段，名值对形式：{name: value}
        var loadingIndex = layer.load(1, {time: 10*1000,shade: 0.1});
        var actionUrl = "../eiaWorkFlowNode/eiaWorkFlowNodeSave";
        $.post(actionUrl, data.field, function (result) {
            if (result.code == 0) {
                layer.msg('保存成功', {icon: 1, time: 1000,shade: 0.1}, function () {
                    pageType = 1;
                    eiaWorkFlowNodeId = result.data.id;
                    $('#eiaWorkFlowId').val(result.data.eiaWorkFlowId);
                    $('#eiaWorkFlowNodeId').val(result.data.id);
                    parent.$('#eiaWorkFlowNodeId').val(result.data.id);
                    $pageTitle.html('编辑流程节点');
                    addFlowNodeProcessBtnShow();
                });
            } else {
                layer.msg('保存失败', {icon: 2, time: 2000,shade: 0.1});
            }
            layer.close(loadingIndex);
        });
        //提交成功后关闭自身
        // var index = parent.layer.getFrameIndex(window.name);
        // parent.layer.close(index);
        return false;
    });

    //监听头部工具栏事件
    //监听事件
    table.on('toolbar(eiaWorkFlowNodeProcessList)', function(obj){
        switch(obj.event){
            case 'addFlowNodeProcessBtn':
                var pageUrl = '/eia/eiaWorkFlowNodeProcess/eiaWorkFlowNodeProCreate?pageType=0';
                var index = layer.open({
                    title:' ',
                    type: 2,
                    shade: false,
                    maxmin: true,
                    skin: 'larry-green',
                    area: ['100%', '100%'],
                    content: pageUrl,
                    success:function () {
                        var body = layer.getChildFrame('body', index);
                    },
                    end: function () {
                        layui.table.reload("eiaWorkFlowNodeProcessList",{
                            url:'/eia/eiaWorkFlowNodeProcess/getEiaWorkFlowNodeProcessDataList?eiaWorkFlowId=' + eiaWorkFlowId + '&eiaWorkFlowNodeId=' + eiaWorkFlowNodeId,
                        });
                        $('#eiaWorkFlowNodeProcessId').val("");
                    },
                    min: function () {
                        $(".layui-layer-title").text("新增节点动作");
                    },
                    restore: function () {
                        $(".layui-layer-title").text(" ");
                    }
                });
                break;
        }
    });
});