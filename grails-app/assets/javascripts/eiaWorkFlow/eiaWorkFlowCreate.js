layui.use(['jquery', 'layer', 'form', 'table'], function(){
    var $ = layui.jquery,
        form = layui.form,
        layer = layui.layer,
        table = layui.table;

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
    pageType == 0 ? $pageTitle.html('新增流程') : $pageTitle.html('编辑流程');

    //重置按钮显隐
    pageType == 0 ? "" : $('.resetBtn').hide();

    var eiaWorkFlowId ;
    if (pageType == 1) {
        eiaWorkFlowId = parent.$('#eiaWorkFlowId').val();
    } else {
        eiaWorkFlowId = $('#eiaWorkFlowId').val();
    }
    $('#eiaWorkFlowId').val(eiaWorkFlowId);

    //渲染表格
    table.render({
        id: 'eiaWorkFlowNodeList',
        elem: '#eiaWorkFlowNodeList',
        url:'/eia/eiaWorkFlowNode/getEiaWorkFlowNodeDataList?eiaWorkFlowId=' + eiaWorkFlowId,
        toolbar: '#tableTopTmp',
        defaultToolbar:['filter', 'print', 'exports'],
        cols: [[
            {fixed: 'left', title: '序号',width: '6%',align: "center",templet: "#indexTable"},
            {field:'nodesCode',width:'15%', title: '流程节点编码',align: "center"},
            {field:'nodesNum',width:'12%', title: '流程节点序号',align: "center"},
            {field:'nodesName',width:'15%', title: '流程节点名称',align: "center"},
            {field:'nodesAuthType',width:'17%', title: '流程节点权限控制类型',align: "center"},
            {field:'nodesIconName',width:'12%', title: '流程节点图标',align: "center",templet: '#iconTp'},
            {field:'nodesAuthCode',width:'15%', title: '节点权限编码',align: "center"},
            // {field:'nodesColor',width:'15%', title: '流程节点颜色',align: "center", templet: '#colorTp'},
            {fixed: 'right', title: '操作',width:"15%",align: "center", toolbar: '#mlTool',align: "center"}
        ]],
        page: true,
        even: true,
        limit: 10,
        done: function () {
            //新增流程按钮显隐
            pageType == 0 ? $('.addFlowNodeBtn').css('display','none') : $('.noticeTag').css('display','none');
        }
    });

    //基本信息回显
    if(pageType == 1){
        $.ajax({
            url:"/eia/eiaWorkFlow/getEiaWorkFlowDataMap?eiaWorkFlowId=" + eiaWorkFlowId,
            type:"POST",
            data:{},
            dataType: "json",
            success: function (result) {
                if (result) {
                    $('#workFlowCode').val(result.data.workFlowCode);
                    $('#workFlowName').val(result.data.workFlowName);
                    $('#workFlowVersion').val(result.data.workFlowVersion);
                }
            }
        });
    }

    // var tableUrl = pageType == 0 ? 'data/eiaWorkFlowNodeListNoData.json' : 'data/eiaWorkFlowNodeListData.json';

    //监听工具条
    table.on('tool(eiaWorkFlowNodeList)', function (obj) {
        var data = obj.data;
        $('#eiaWorkFlowNodeId').val(data.id);
        if (obj.event === 'eiaEdit') {    //编辑
            pageUrl = '/eia/eiaWorkFlowNode/eiaWorkFlowNodeCreate?pageType=1';
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
                    body.find('#eiaWorkFlowNodeId').val(data.id);
                },
                end: function () {
                    layui.table.reload("eiaWorkFlowNodeList",{
                        url:'/eia/eiaWorkFlowNode/getEiaWorkFlowNodeDataList?eiaWorkFlowId=' + eiaWorkFlowId
                    });
                    $('#eiaWorkFlowNodeId').val("");
                },
                min: function () {
                    $(".layui-layer-title").text("编辑流程节点");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        }
        else if(obj.event === 'eiaCheck'){    //查看
            pageUrl = '/eia/eiaWorkFlowNode/eiaWorkFlowNodeDetail';
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
                    body.find('#eiaWorkFlowNodeId').val(data.id);
                },
                end: function () {
                    $('#eiaWorkFlowNodeId').val("");
                },
                min: function () {
                    $(".layui-layer-title").text("流程节点详情");
                },
                restore: function () {
                    $(".layui-layer-title").text(" ");
                }
            });
        }
        else if(obj.event === 'eiaDel'){    //删除
            layer.confirm('确定要删除该流程节点吗?', {icon: 3}, function (index) {
                var loadingIndex = layer.load(1, {time: 10*1000,shade: 0.1});
                $.post("../eiaWorkFlowNode/eiaWorkFlowNodeDel", {eiaWorkFlowNodeId: data.id}, function (result) {
                    if (result.code == 0) {
                        layer.msg('删除成功！', {icon: 1, time: 1500,shade: 0.1}, function () {
                            table.reload("eiaWorkFlowNodeList");
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
    var addFlowNodeBtnShow = function(){
        $('.addFlowNodeBtn').css('display','inline-block');
        $('.noticeTag').css('display','none');
    };
    var addFlowNodeBtnHide = function(){
        $('.addFlowNodeBtn').css('display','none');
        $('.noticeTag').css('display','inline-block');
    };

    //表单提交
    form.on('submit(save)', function(data){
        var loadingIndex = layer.load(1, {time: 10*1000,shade: 0.1});
        var actionUrl = "../eiaWorkFlow/eiaWorkFlowSave";
        $.post(actionUrl, data.field, function (result) {
            if (result.code == 0) {
                layer.msg('保存成功', {icon: 1, time: 1000,shade: 0.1}, function () {
                    pageType = 1;
                    eiaWorkFlowId = result.data.id;
                    $('#eiaWorkFlowId').val(result.data.id);
                    parent.layui.table.reload("eiaWorkFlowList");
                    $pageTitle.html('编辑流程');
                    addFlowNodeBtnShow();
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
    table.on('toolbar(eiaWorkFlowNodeList)', function(obj){
        switch(obj.event){
            case 'addFlowNodeBtn':
                var pageUrl = '/eia/eiaWorkFlowNode/eiaWorkFlowNodeCreate?pageType=0';
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
                        layui.table.reload("eiaWorkFlowNodeList",{
                            url:'/eia/eiaWorkFlowNode/getEiaWorkFlowNodeDataList?eiaWorkFlowId=' + eiaWorkFlowId
                        });
                        $('#eiaWorkFlowNodeId').val("");
                    },
                    min: function () {
                        $(".layui-layer-title").text("新增流程节点");
                    },
                    restore: function () {
                        $(".layui-layer-title").text(" ");
                    }
                });
                break;
        }
    });
});