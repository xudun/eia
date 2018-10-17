layui.use(['jquery', 'layer', 'element', 'laypage', 'laydate'], function(){
    var $ = layui.jquery,
        layer = layui.layer,
        laydate = layui.laydate,
        laypage = layui.laypage,
        element = layui.element;

    var searchArr = [],
        searchStringify = '';
    var regionName = '';
    var pubDate = '';

    // Tab切换
    element.on('tab(eiaDataShareTab)', function(data){
        var cur_index = data.index;
        var filterUrl;
        switch (cur_index){
            case 0:
                filterUrl = "../eiaPlatForm/getEiaDataShareData";
                showFilter(filterUrl);
                showProjectItem(1,"", "", "");
                break;
            case 1:
                filterUrl = "../eiaPlatForm/getEiaPlanShowData";
                showFilter(filterUrl);
                eiaPlanShowTab();
                showPlanShowItem(1, "", "", "");
                break;
            case 2:
                filterUrl = "../eiaPlatForm/getEiaAreaInfoData";
                showFilter(filterUrl);
                eiaAreaInfoTab();
                showAreaInfoItem(1, "", "", "");
                break;
            case 3:
                filterUrl = "../eiaPlatForm/getEiaSensAreaData";
                showFilter(filterUrl);
                eiaSensAreaTab();
                showSensAreaItem(1, "", "", "");
                break;
        }
    });

    //规划公示Tab：公布日期
    laydate.render({
        elem: '#pubDate',
        done: function(value, date, endDate){
            pubDate = value;
            var dataShareSearch = $("#dataShareSearch").val();
            eiaPlanShowTab(dataShareSearch, searchStringify, pubDate);
            showPlanShowItem(1, dataShareSearch, searchStringify, pubDate)
        }
    });

    //下拉树 环评项目Tab：所属区域
    $("#regionNameProjectDrop").dropDownForZ({
        url:'/eia/eiaDomainCode/getTree?domain='+"PROVINCE_CITY",
        width:'99%',
        height:'350px',
        disableParent: true,
        selecedSuccess:function(data){  //选中回调
            if(!data.isParent){
                var temp = {
                    id: data.id,
                    name: data.name
                };
                $("#regionNameProject").val(JSON.stringify(temp));
                regionName = temp.name;
                var dataShareSearch = $("#dataShareSearch").val();
                eiaDataShareTab(dataShareSearch, searchStringify, regionName);
                showProjectItem(1, dataShareSearch, searchStringify, regionName);
            }
        }
    });
    //下拉树 区域概况Tab：所属区域
    $("#regionNameAreaInfoDrop").dropDownForZ({
        url:'/eia/eiaDomainCode/getTree?domain='+"PROVINCE_CITY",
        width:'99%',
        height:'350px',
        disableParent: true,
        selecedSuccess:function(data){  //选中回调
            if(!data.isParent){
                var temp = {
                    id: data.id,
                    name: data.name
                };
                $("#regionNameAreaInfo").val(JSON.stringify(temp));
                regionName = temp.name;
                var areaInfoSearch = $("#areaInfoSearch").val();
                eiaAreaInfoTab(areaInfoSearch, searchStringify, regionName);
                showAreaInfoItem(1, areaInfoSearch, searchStringify, regionName);
            }
        }
    });
    //下拉树 环境敏感区Tab：所属区域
    $("#regionNameSensAreaDrop").dropDownForZ({
        url:'/eia/eiaDomainCode/getTree?domain='+"PROVINCE_CITY",
        width:'99%',
        height:'350px',
        disableParent: true,
        selecedSuccess:function(data){  //选中回调
            if(!data.isParent){
                var temp = {
                    id: data.id,
                    name: data.name
                };
                $("#regionNameSensArea").val(JSON.stringify(temp));
                regionName = temp.name;
                var dataShareSearch = $("#dataShareSearch").val();
                eiaSensAreaTab(dataShareSearch, searchStringify, regionName);
                showSensAreaItem(1, dataShareSearch, searchStringify, regionName);
            }
        }
    });
    var eiaDataShareTab = function (dataShareSearch, searchStringify, regionName) {
        //环评项目Tab页分页
        $.ajax({
            url: "../eiaPlatForm/eiaDataShareQuery",
            data: {page: 1, limit: 10, dataShareSearchText: dataShareSearch, searchStringify: searchStringify, regionName: regionName},
            type: "post",
            cache: false,
            async: false,
            success: function (result) {
                laypage.render({
                    elem: 'projectPagination',
                    count: result.count,
                    jump: function (obj, first) {
                        if (!first) {
                            showProjectItem(obj.curr, dataShareSearch, searchStringify, regionName);
                        }
                    }
                });
            }
        });
    }
    var eiaPlanShowTab = function (dataShareSearch, searchStringify, pubDate) {
        //规划公示Tab页分页
        $.ajax({
            url: "../eiaPlatForm/platFormEiaPlanShowQuery",
            data: {page: 1, limit: 10, dataShareSearchPlanText: dataShareSearch, searchStringify: searchStringify, pubDate: pubDate},
            type: "post",
            cache: false,
            async: false,
            success: function (result) {
                laypage.render({
                    elem: 'planShowPagination',
                    count: result.count,
                    jump: function (obj, first) {
                        if (!first) {
                            showPlanShowItem(obj.curr, dataShareSearch, searchStringify, pubDate);
                        }
                    }
                });
            }
        });
    }
    var eiaAreaInfoTab = function (areaInfoSearch, searchStringify, regionName) {
        //区域概况Tab页分页
        $.ajax({
            url: "../eiaPlatForm/eiaAreaInfoQuery",
            data: {page: 1, limit: 10, areaInfoSearch: areaInfoSearch, searchStringify: searchStringify, regionName: regionName},
            type: "post",
            cache: false,
            async: false,
            success: function (result) {
                laypage.render({
                    elem: 'areaInfoPagination',
                    count: result.count,
                    jump: function (obj, first) {
                        if (!first) {
                            showAreaInfoItem(obj.curr, areaInfoSearch, searchStringify, regionName);
                        }
                    }
                });
            }
        });
    }
    var eiaSensAreaTab = function (sensAreaSearch, searchStringify, regionName) {
        //环境敏感区Tab页分页
        $.ajax({
            url: "../eiaPlatForm/eiaSensAreaQuery",
            data: {page: 1, limit: 10, sensAreaSearch: sensAreaSearch, searchStringify: searchStringify, regionName: regionName},
            type: "post",
            cache: false,
            async: false,
            success: function (result) {
                laypage.render({
                    elem: 'sensAreaPagination',
                    count: result.count,
                    jump: function (obj, first) {
                        if (!first) {
                            showSensAreaItem(obj.curr, sensAreaSearch, searchStringify, regionName);
                        }
                    }
                });
            }
        });
    }

    //渲染Tab页筛选项 方法
    var showFilter = function(url){
        $.ajax({
            url: url,
            type:"GET",
            data:{},
            dataType: "json",
            async: true,
            success: function (data) {
                console.log(data)
                for(var name in data){
                    var filterName = name,
                        filterItems = data[name];
                    $('.' + filterName + "Filter").empty();
                    $('#regionNameProjectDrop, #regionNameProject, #regionNameProjectDropCode').val("");
                    $('#regionNameAreaInfoDrop, #regionNameAreaInfo, #regionNameAreaInfoDropCode').val("");
                    $('#regionNameSensAreaDrop, #regionNameSensArea , #regionNameSensAreaDropCode').val("");
                    $('#pubDate').val("");
                    $('.larry-search-input').val("");
                    regionName = null;
                    pubDate = null;
                    searchStringify = null;
                    $('.filter-inner .filter-item').removeClass('active');
                    for(var i=0; i<filterItems.length; i++){
                        var str = "<span class='filter-item' cur_data='"+filterItems[i].name+"'>"+filterItems[i].name+"<span class='layui-badge-rim'>"+filterItems[i].value+"</span></span>";
                        var $str = $(str).on('click',function () {
                            if($(this).hasClass('active')){
                                $(this).removeClass('active');
                            }else{
                                $(this).addClass('active');
                            }
                            searchArr = [];
                            $('.filter-li .filter-inner',$(this).closest('.filter-ul')).each(function (index,element) {
                                var filterName = $(element).attr('filter_name');
                                var $choosedItem = $(element).find('.filter-item.active');
                                var choosedVal = [];
                                $choosedItem.each(function (index,elem) {
                                    choosedVal.push($(elem).attr('cur_data'));
                                });
                                var temp = {};
                                temp[filterName] = choosedVal;
                                searchArr.push(temp);
                                searchStringify = JSON.stringify(searchArr);
                            });
                            console.log(searchArr);
                            var curTab = $(this).closest('.layui-tab-item').attr('tab_name');
                            switch (curTab){
                                case "dataShare":
                                    var dataShareSearch = $("#dataShareSearch").val();
                                    eiaDataShareTab(dataShareSearch, searchStringify, regionName);
                                    showProjectItem(1, dataShareSearch, searchStringify, regionName);
                                    break;
                                case "planShow":
                                    var planShowSearch = $("#planShowSearch").val();
                                    eiaPlanShowTab(planShowSearch, searchStringify, pubDate);
                                    showPlanShowItem(1, planShowSearch, searchStringify, pubDate);
                                    break;
                                case "areaInfo":
                                    var areaInfoSearch = $('#areaInfoSearch').val();
                                    eiaAreaInfoTab(areaInfoSearch, searchStringify, regionName);
                                    showAreaInfoItem(1, areaInfoSearch, searchStringify, regionName);
                                    break;
                                case "sensArea":
                                    var sensAreaSearch = $('#sensAreaSearch').val();
                                    eiaSensAreaTab(sensAreaSearch, searchStringify, regionName);
                                    showSensAreaItem(1, sensAreaSearch, searchStringify, regionName);
                                    break;
                            }
                        });
                        $('.' + filterName + "Filter").append($str).attr('filter_name',filterName);
                    }
                }
            }
        });
    };

    //渲染环评项目Tab页显示数据 方法
    var showProjectItem = function(page, dataShareSearch, searchStringify, regionName){
        $.ajax({
            url:"../eiaPlatForm/eiaDataShareQuery",
            type:"POST",
            data:{page: page, limit: 10, dataShareSearchText: dataShareSearch, searchStringify: searchStringify, regionName: regionName},
            dataType: "json",
            async: true,
            success: function (result) {
                $('.project-ul').empty();
                if(result.data.length){
                    $('.project-ul').prev('.empty-note').css('display','none');
                    for(var i=0; i<result.data.length; i++){
                        var ifCollectClass = result.data[i].ifFav ? "active" : "",
                            ifCollectText = result.data[i].ifFav ? "取消" : "";
                        var str = "";
                        if (result.data[i].dataSource == "联合赤道" || result.data[i].dataSource == "联合泰泽") {
                            str = "<li class='show-li' eiaDataShareId='" + result.data[i].id + "' dataSource='" + result.data[i].dataSource + "'>" +
                                "                            <div class='sinner lf'>" +
                                "                                <a class='s-title projectName'>" + result.data[i].projectName + "</a>" +
                                "                                <div class='s-data-box'>" +
                                "                                    <div class='s-d-item'>" +
                                "                                        <p class='sd-p'>" +
                                "                                            <span class='sd-head'>录入人：</span>" +
                                "                                            <span class='sd-val'>" + result.data[i].inputUser + "</span>" +
                                "                                        </p>" +
                                "                                    </div>" +
                                "                                    <div class='s-d-item'>" +
                                "                                        <p class='sd-p'>" +
                                "                                            <span class='sd-head'>环境影响评价行业类型：</span>" +
                                "                                            <span class='sd-val'>" + result.data[i].environmentaType + "</span>" +
                                "                                        </p>" +
                                "                                    </div>" +
                                "                                    <div class='s-d-item'>" +
                                "                                        <p class='sd-p'>" +
                                "                                            <span class='sd-head'>行业类型及代码：</span>" +
                                "                                            <span class='sd-val'>" + result.data[i].industryType + "</span>" +
                                "                                        </p>" +
                                "                                    </div>" +
                                "                                    <div class='s-d-item'>" +
                                "                                        <p class='sd-p'>" +
                                "                                            <span class='sd-head'>生产工艺：</span>" +
                                "                                            <span class='sd-val'>" + result.data[i].productionEngineer + "</span>" +
                                "                                        </p>" +
                                "                                    </div>" +
                                "                                    <div class='s-d-item'>" +
                                "                                        <p class='sd-p'>" +
                                "                                            <span class='sd-head'>产品或功能：</span>" +
                                "                                            <span class='sd-val'>" + result.data[i].productFunction + "</span>" +
                                "                                        </p>" +
                                "                                    </div>" +
                                "                                    <div class='s-d-item'>" +
                                "                                        <p class='sd-p'>" +
                                "                                            <span class='sd-head'>项目负责人：</span>" +
                                "                                            <span class='sd-val'>" + result.data[i].projectLeader + "</span>" +
                                "                                        </p>" +
                                "                                    </div>" +
                                "                                    <div class='s-d-item'>" +
                                "                                        <p class='sd-p'>" +
                                "                                            <span class='sd-head'>建设性质：</span>" +
                                "                                            <span class='sd-val'>" + result.data[i].natureConstructio + "</span>" +
                                "                                        </p>" +
                                "                                    </div>" +
                                "                                    <div class='s-d-item'>" +
                                "                                        <p class='sd-p'>" +
                                "                                            <span class='sd-head'>审批时间：</span>" +
                                "                                            <span class='sd-val'>" + result.data[i].approveDate + "</span>" +
                                "                                        </p>" +
                                "                                    </div>" +
                                "                                    <div class='s-d-item'>" +
                                "                                        <p class='sd-p'>" +
                                "                                            <span class='sd-head'>审批部门：</span>" +
                                "                                            <span class='sd-val'>" + result.data[i].approveDept + "</span>" +
                                "                                        </p>" +
                                "                                    </div>" +
                                "                                    <div class='s-d-item'>" +
                                "                                        <p class='sd-p'>" +
                                "                                            <span class='sd-head'>审批文号：</span>" +
                                "                                            <span class='sd-val'>" + result.data[i].approveNo + "</span>" +
                                "                                        </p>" +
                                "                                    </div>" +
                                "                                </div>" +
                                "                                <div class='s-files-box'>" +
                                "                                    <div class='s-f-inner lf'>附件：</div>" +
                                "                                    <div class='s-f-inner rg files-item-box'></div>" +
                                "                                </div>" +
                                "                            </div>" +
                                "                            <div class='sinner rg'>" +
                                "                                <p class='s-rg-p'><i class='larry-icon'>&#xe778;</i>浏览：<span class='s-num'>" + result.data[i].viewsCount + "</span></p>" +
                                "                                <p class='s-rg-p'><i class='larry-icon download-icon'>&#xe92e;</i>下载：<span class='s-num'>" + result.data[i].downloadsCount + "</span></p>" +
                                "                                <a class='layui-btn layui-btn-primary layui-btn-radius collectBtn " + ifCollectClass + "'><i class='larry-icon'>&#xe92b;</i><span class='ifCollectTxt'>" + ifCollectText + "</span>收藏</a>" +
                                "                            </div>" +
                                "                        </li>";
                        } else {
                            str = "<li class='show-li' eiaDataShareId='" + result.data[i].id + "' dataSource='" + result.data[i].dataSource + "'>" +
                                "                            <div class='sinner lf'>" +
                                "                                <a class='s-title projectName'>" + result.data[i].projectName + "</a>" +
                                "                                <div class='s-data-box'>" +
                                "                                    <div class='s-d-item'>" +
                                "                                        <p class='sd-p'>" +
                                "                                            <span class='sd-head'>录入人：</span>" +
                                "                                            <span class='sd-val'>" + result.data[i].inputUser + "</span>" +
                                "                                        </p>" +
                                "                                    </div>" +
                                "                                    <div class='s-d-item'>" +
                                "                                        <p class='sd-p'>" +
                                "                                            <span class='sd-head'>建设性质：</span>" +
                                "                                            <span class='sd-val'>" + result.data[i].natureConstructio + "</span>" +
                                "                                        </p>" +
                                "                                    </div>" +
                                "                                    <div class='s-d-item'>" +
                                "                                        <p class='sd-p'>" +
                                "                                            <span class='sd-head'>环境影响评价行业类型：</span>" +
                                "                                            <span class='sd-val'>" + result.data[i].environmentaType + "</span>" +
                                "                                        </p>" +
                                "                                    </div>" +
                                "                                    <div class='s-d-item'>" +
                                "                                        <p class='sd-p'>" +
                                "                                            <span class='sd-head'>行业类型及代码：</span>" +
                                "                                            <span class='sd-val'>" + result.data[i].industryType + "</span>" +
                                "                                        </p>" +
                                "                                    </div>" +
                                "                                    <div class='s-d-item'>" +
                                "                                        <p class='sd-p'>" +
                                "                                            <span class='sd-head'>生产工艺：</span>" +
                                "                                            <span class='sd-val'>" + result.data[i].productionEngineer + "</span>" +
                                "                                        </p>" +
                                "                                    </div>" +
                                "                                    <div class='s-d-item'>" +
                                "                                        <p class='sd-p'>" +
                                "                                            <span class='sd-head'>产品或功能：</span>" +
                                "                                            <span class='sd-val'>" + result.data[i].productFunction + "</span>" +
                                "                                        </p>" +
                                "                                    </div>" +
                                "                                    <div class='s-d-item'>" +
                                "                                        <p class='sd-p'>" +
                                "                                            <span class='sd-head'>建设单位：</span>" +
                                "                                            <span class='sd-val'>" + result.data[i].developmentOrg + "</span>" +
                                "                                        </p>" +
                                "                                    </div>" +
                                "                                    <div class='s-d-item'>" +
                                "                                        <p class='sd-p'>" +
                                "                                            <span class='sd-head'>环评单位：</span>" +
                                "                                            <span class='sd-val'>" + result.data[i].eiaUnit + "</span>" +
                                "                                        </p>" +
                                "                                    </div>" +
                                "                                    <div class='s-d-item'>" +
                                "                                        <p class='sd-p'>" +
                                "                                            <span class='sd-head'>审批部门：</span>" +
                                "                                            <span class='sd-val'>" + result.data[i].approveDept + "</span>" +
                                "                                        </p>" +
                                "                                    </div>" +
                                "                                    <div class='s-d-item'>" +
                                "                                        <p class='sd-p'>" +
                                "                                            <span class='sd-head'>审批年度：</span>" +
                                "                                            <span class='sd-val'>" + result.data[i].publictyYear + "</span>" +
                                "                                        </p>" +
                                "                                    </div>" +
                                "                                    <div class='s-d-item'>" +
                                "                                        <p class='sd-p'>" +
                                "                                            <span class='sd-head'>公示附件：</span>" +
                                "                                            <a style='color: #1E9FFF' href='"+result.data[i].spiderFileUrl+"' class='sd-val downFile'>点击后查看或下载公示附件</a>" +
                                "                                        </p>" +
                                "                                    </div>" +
                                "                                    <div class='s-d-item'>" +
                                "                                        <p class='sd-p'>" +
                                "                                            <span class='sd-head'>原文链接：</span>" +
                                "                                            <a style='color: #1E9FFF' href='"+result.data[i].spiderUrl+"' class='sd-val downFile'>点击后查看原文链接</a>" +
                                "                                        </p>" +
                                "                                    </div>" +
                                "                                </div>" +
                                "                                <div class='s-files-box'>" +
                                "                                    <div class='s-f-inner lf'>附件：</div>" +
                                "                                    <div class='s-f-inner rg files-item-box'></div>" +
                                "                                </div>" +
                                "                            </div>" +
                                "                            <div class='sinner rg'>" +
                                "                                <p class='s-rg-p'><i class='larry-icon'>&#xe778;</i>浏览：<span class='s-num'>" + result.data[i].viewsCount + "</span></p>" +
                                "                                <p class='s-rg-p'><i class='larry-icon download-icon'>&#xe92e;</i>下载：<span class='s-num'>" + result.data[i].downloadsCount + "</span></p>" +
                                "                                <a class='layui-btn layui-btn-primary layui-btn-radius collectBtn " + ifCollectClass + "'><i class='larry-icon'>&#xe92b;</i><span class='ifCollectTxt'>" + ifCollectText + "</span>收藏</a>" +
                                "                            </div>" +
                                "                        </li>";
                        }

                        var $str = $(str);
                        if (result.data[i].fileList) {
                            var filesArr = result.data[i].fileList,
                                showLength = filesArr.length > 2 ? 2 : filesArr.length;
                            for (var n = 0; n < showLength; n++) {
                                var filestr = "<a class='file-text' fileId='"+filesArr[n].id+"'>" + filesArr[n].fileName + "</a>";
                                var $filestr = $(filestr).on('click',function () {
                                    var fileId = $(this).attr('fileId');
                                    downloadFile(fileId);
                                });
                                $('.files-item-box', $str).append($filestr);
                            }
                            var moreFilesStr = "<a class='moreFilesBtn'>更多...</a>";
                            filesArr.length > 2 ? $('.files-item-box', $str).append(moreFilesStr) : "";
                        }

                        $str.on('click', '.collectBtn', function () {  //收藏按钮
                            var eiaDataShareId = $(this).closest('.show-li').attr('eiaDataShareId');
                            var $this = $(this);
                            var curState = $this.hasClass('active'),
                                ifText = curState ? "取消" : "";
                            layer.confirm('是否'+ifText+'收藏?', {icon: 3}, function (index) {
                                var ifFav = true;
                                if (curState == true) {
                                    ifFav = false;
                                }
                                $.post("../eiaPlatForm/favEiaDataShareSave", {eiaDataShareId: eiaDataShareId, ifFav: ifFav}, function (result) {
                                    if (result.code == 0) {
                                        var msg = '收藏成功！';
                                        if (ifFav == false) {
                                            msg = '已取消收藏！';
                                        }
                                        layer.msg(msg, {icon: 1, time: 1500}, function () {});
                                    } else {
                                        if (result.msg) {
                                            layer.msg("处理失败！<br/><div style='color:red;'>错误信息：" + result.msg + "</div>", {
                                                icon: 2,
                                                time: 1500
                                            });
                                        } else {
                                            layer.msg('收藏失败！', {icon: 2, time: 1500});
                                        }
                                    }
                                });
                                //确定
                                curState ? $this.removeClass('active') : $this.addClass('active');
                                curState ? $('.ifCollectTxt',$this).html('') : $('.ifCollectTxt',$this).html('取消');
                            },function (index) {
                                //取消
                            });
                        })
                            .on('click', '.projectName,.moreFilesBtn', function () {   //打开详情页
                                var dataSource = $(this).closest('.show-li').attr('dataSource');
                                var eiaDataShareId = $(this).closest('.show-li').attr('eiaDataShareId');
                                var pageUrl = "";
                                if (dataSource == "联合赤道" || dataSource == "联合泰泽") {
                                    pageUrl = '../eiaPlatForm/eiaDataShareDetail?eiaDataShareId=' + eiaDataShareId;
                                } else {
                                    pageUrl = '../eiaPlatForm/eiaPubProjectDetail?eiaDataShareId=' + eiaDataShareId;
                                }
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
                                    },
                                    end: function () {
                                        $('#eiaDataShareId').val('');
                                    },
                                    min: function () {
                                        $(".layui-layer-title").text("项目详情");
                                    },
                                    restore: function () {
                                        $(".layui-layer-title").text(" ");
                                    }
                                });
                            });

                        $('.project-ul').append($str);
                    }
                }else{
                    $('.project-ul').prev('.empty-note').css('display','block');
                }

            }
        });
    };
    //渲染规划公示Tab页显示数据 方法
    var showPlanShowItem = function(page, planShowSearch, searchStringify, pubDate){
        $.ajax({
            url:"../eiaPlatForm/platFormEiaPlanShowQuery",
            type:"POST",
            data:{page: page, limit: 10, dataShareSearchPlanText: planShowSearch, searchStringify: searchStringify, pubDate: pubDate},
            dataType: "json",
            async: true,
            success: function (result) {
                $('.plan-show-ul').empty();
                if(result.data.length){
                    $('.plan-show-ul').prev('.empty-note').css('display','none');
                    for(var i=0; i<result.data.length; i++){
                        var ifCollectClass = result.data[i].ifFav ? "active" : "",
                            ifCollectText = result.data[i].ifFav ? "取消" : "";
                        var str = "<li class='show-li' eiaPlanShowId='"+result.data[i].id+"'>" +
                            "                            <div class='sinner lf'>" +
                            "                                <a class='s-title title'>"+result.data[i].title+"</a>" +
                            "                                <div class='s-data-box'>" +
                            "                                    <div class='s-d-item'>" +
                            "                                        <p class='sd-p'>" +
                            "                                            <span class='sd-head'>来源机关：</span>" +
                            "                                            <span class='sd-val'>"+result.data[i].source+"</span>" +
                            "                                        </p>" +
                            "                                    </div>" +
                            "                                    <div class='s-d-item'>" +
                            "                                        <p class='sd-p'>" +
                            "                                            <span class='sd-head'>公布日期：</span>" +
                            "                                            <span class='sd-val'>"+result.data[i].pubDate+"</span>" +
                            "                                        </p>" +
                            "                                    </div>" +
                            "                                    <div class='s-d-item'>" +
                            "                                        <p class='sd-p'>" +
                            "                                            <span class='sd-head'>原文链接：</span>" +
                            "                                            <span class='sd-val'><a href='"+result.data[i].spiderFileUrl+"' target='_blank' class='link-text'>查看原文链接</a></span>" +
                            "                                        </p>" +
                            "                                    </div>" +
                            "                                </div>" +
                            "                            </div>" +
                            "                            <div class='sinner rg'>" +
                            "                                <a class='layui-btn layui-btn-primary layui-btn-radius collectBtn singleBtn "+ifCollectClass+"'><i class='larry-icon'></i><span class='ifCollectTxt'>"+ifCollectText+"</span>收藏</a>" +
                            "                            </div>" +
                            "                        </li>";

                        var $str = $(str)
                            .on('click', '.collectBtn', function () {  //收藏按钮
                                var eiaPlanShowId = $(this).closest('.show-li').attr('eiaPlanShowId');
                                var $this = $(this);
                                var curState = $this.hasClass('active'),
                                    ifText = curState ? "取消" : "";
                                layer.confirm('是否'+ifText+'收藏?', {icon: 3}, function (index) {
                                    var ifFav = true;
                                    if (curState == true) {
                                        ifFav = false;
                                    }
                                    $.post("../eiaPlatForm/favEiaPlanShowSave", {eiaPlanShowId: eiaPlanShowId, ifFav: ifFav}, function (result) {
                                        if (result.code == 0) {
                                            var msg = '收藏成功！';
                                            if (ifFav == false) {
                                                msg = '已取消收藏！';
                                            }
                                            layer.msg(msg, {icon: 1, time: 1500}, function () {});
                                        } else {
                                            if (result.msg) {
                                                layer.msg("处理失败！<br/><div style='color:red;'>错误信息：" + result.msg + "</div>", {
                                                    icon: 2,
                                                    time: 1500
                                                });
                                            } else {
                                                layer.msg('收藏失败！', {icon: 2, time: 1500});
                                            }
                                        }
                                    });
                                    //确定
                                    curState ? $this.removeClass('active') : $this.addClass('active');
                                    curState ? $('.ifCollectTxt',$this).html('') : $('.ifCollectTxt',$this).html('取消');
                                },function (index) {
                                    //取消
                                });
                            })
                            .on('click', '.title,.moreFilesBtn', function () {   //打开详情页
                                var eiaPlanShowId = $(this).closest('.show-li').attr('eiaPlanShowId');
                                var pageUrl = '../eiaPlatForm/eiaPlanShowDetail?eiaPlanShowId=' + eiaPlanShowId;
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
                                    },
                                    end: function () {

                                    },
                                    min: function () {
                                        $(".layui-layer-title").text("规划公示详情");
                                    },
                                    restore: function () {
                                        $(".layui-layer-title").text(" ");
                                    }
                                });
                            });

                        $('.plan-show-ul').append($str);
                    }
                }else{
                    $('.plan-show-ul').prev('.empty-note').css('display','block');
                }

            }
        });
    };
    //渲染区域概况Tab页显示数据 方法
    var showAreaInfoItem = function(page, areaInfoSearch, searchStringify, regionName){
        $.ajax({
            url:"../eiaPlatForm/eiaAreaInfoQuery",
            type:"POST",
            data:{page: page, limit: 10, areaInfoSearch: areaInfoSearch, searchStringify: searchStringify, regionName: regionName},
            dataType: "json",
            async: true,
            success: function (result) {
                $('.area-info-ul').empty();
                if(result.data.length){
                    $('.area-info-ul').prev('.empty-note').css('display','none');
                    for(var i=0; i<result.data.length; i++){
                        var ifCollectClass = result.data[i].ifFav ? "active" : "",
                            ifCollectText = result.data[i].ifFav ? "取消" : "";
                        var str = "";
                        if (result.data[i].dataType == "行政区划图" || result.data[i].dataType == "自然环境简况" || result.data[i].dataType == "声功能区划") {
                            str = "<li class='show-li' eiaAreaInfoId='" + result.data[i].id + "'>" +
                                "                            <div class='sinner lf'>" +
                                "                                <a class='s-title projectName'>" + result.data[i].projectName + "</a>" +
                                "                                <div class='s-data-box'>" +
                                "                                    <div class='s-d-item'>" +
                                "                                        <p class='sd-p'>" +
                                "                                            <span class='sd-head'>所属区域：</span>" +
                                "                                            <span class='sd-val'>" + result.data[i].regionName + "</span>" +
                                "                                        </p>" +
                                "                                    </div>" +
                                "                                </div>" +
                                "                                <div class='s-files-box'>" +
                                "                                    <div class='s-f-inner lf'>附件：</div>" +
                                "                                    <div class='s-f-inner rg files-item-box'></div>" +
                                "                                </div>" +
                                "                            </div>" +
                                "                            <div class='sinner rg'>" +
                                "                                <a class='layui-btn layui-btn-primary layui-btn-radius collectBtn singleBtn " + ifCollectClass + "'><i class='larry-icon'></i><span class='ifCollectTxt'>" + ifCollectText + "</span>收藏</a>" +
                                "                            </div>" +
                                "                        </li>";
                        } else if (result.data[i].dataType == "污水处理厂") {
                            str = "<li class='show-li' eiaAreaInfoId='" + result.data[i].id + "'>" +
                                "                            <div class='sinner lf'>" +
                                "                                <a class='s-title projectName'>" + result.data[i].projectName + "</a>" +
                                "                                <div class='s-data-box'>" +
                                "                                    <div class='s-d-item'>" +
                                "                                        <p class='sd-p'>" +
                                "                                            <span class='sd-head'>地理坐标：</span>" +
                                "                                            <span class='sd-val'>" + result.data[i].coordinate +"</span>" +
                                "                                        </p>" +
                                "                                    </div>" +
                                "                                    <div class='s-d-item'>" +
                                "                                        <p class='sd-p'>" +
                                "                                            <span class='sd-head'>处理能力：</span>" +
                                "                                            <span class='sd-val'>" + result.data[i].handAbility + "</span>" +
                                "                                        </p>" +
                                "                                    </div>" +
                                "                                    <div class='s-d-item'>" +
                                "                                        <p class='sd-p'>" +
                                "                                            <span class='sd-head'>处理工艺：</span>" +
                                "                                            <span class='sd-val'>" + result.data[i].treatProcess + "</span>" +
                                "                                        </p>" +
                                "                                    </div>" +
                                "                                    <div class='s-d-item'>" +
                                "                                        <p class='sd-p'>" +
                                "                                            <span class='sd-head'>执行标准：</span>" +
                                "                                            <span class='sd-val'>" + result.data[i].execStandard + "</span>" +
                                "                                        </p>" +
                                "                                    </div>" +
                                "                                </div>" +
                                "                                <div class='s-files-box'>" +
                                "                                    <div class='s-f-inner lf'>附件：</div>" +
                                "                                    <div class='s-f-inner rg files-item-box'></div>" +
                                "                                </div>" +
                                "                            </div>" +
                                "                            <div class='sinner rg'>" +
                                "                                <a class='layui-btn layui-btn-primary layui-btn-radius collectBtn singleBtn " + ifCollectClass + "'><i class='larry-icon'></i><span class='ifCollectTxt'>" + ifCollectText + "</span>收藏</a>" +
                                "                            </div>" +
                                "                        </li>";
                        } else {
                            str = "<li class='show-li' eiaAreaInfoId='" + result.data[i].id + "'>" +
                                "                            <div class='sinner lf'>" +
                                "                                <a class='s-title projectName'>" + result.data[i].projectName + "</a>" +
                                "                                <div class='s-data-box'>" +
                                "                                    <div class='s-d-item'>" +
                                "                                        <p class='sd-p'>" +
                                "                                            <span class='sd-head'>环评单位：</span>" +
                                "                                            <span class='sd-val'>" + result.data[i].eiaUnit +"</span>" +
                                "                                        </p>" +
                                "                                    </div>" +
                                "                                    <div class='s-d-item'>" +
                                "                                        <p class='sd-p'>" +
                                "                                            <span class='sd-head'>审批部门：</span>" +
                                "                                            <span class='sd-val'>" + result.data[i].approveDept + "</span>" +
                                "                                        </p>" +
                                "                                    </div>" +
                                "                                    <div class='s-d-item'>" +
                                "                                        <p class='sd-p'>" +
                                "                                            <span class='sd-head'>审批文号：</span>" +
                                "                                            <span class='sd-val'>" + result.data[i].approveNo + "</span>" +
                                "                                        </p>" +
                                "                                    </div>" +
                                "                                </div>" +
                                "                                <div class='s-files-box'>" +
                                "                                    <div class='s-f-inner lf'>附件：</div>" +
                                "                                    <div class='s-f-inner rg files-item-box'></div>" +
                                "                                </div>" +
                                "                            </div>" +
                                "                            <div class='sinner rg'>" +
                                "                                <a class='layui-btn layui-btn-primary layui-btn-radius collectBtn singleBtn " + ifCollectClass + "'><i class='larry-icon'></i><span class='ifCollectTxt'>" + ifCollectText + "</span>收藏</a>" +
                                "                            </div>" +
                                "                        </li>";
                        }

                        var $str = $(str);
                        if (result.data[i].fileList) {
                            var filesArr = result.data[i].fileList,
                                showLength = filesArr.length > 2 ? 2 : filesArr.length;
                            for (var n = 0; n < showLength; n++) {
                                var filestr = "<a class='file-text' fileId='"+filesArr[n].id+"'>" + filesArr[n].fileName + "</a>";
                                var $filestr = $(filestr).on('click',function () {
                                    var fileId = $(this).attr('fileId');
                                    downloadFile(fileId);
                                });
                                $('.files-item-box', $str).append($filestr);
                            }
                            var moreFilesStr = "<a class='moreFilesBtn'>更多...</a>";
                            filesArr.length > 2 ? $('.files-item-box', $str).append(moreFilesStr) : "";
                        }

                        $str.on('click', '.collectBtn', function () {  //收藏按钮
                            var eiaAreaInfoId = $(this).closest('.show-li').attr('eiaAreaInfoId');
                            var $this = $(this);
                            var curState = $this.hasClass('active'),
                                ifText = curState ? "取消" : "";
                            layer.confirm('是否'+ifText+'收藏?', {icon: 3}, function (index) {
                                var ifFav = true;
                                if (curState == true) {
                                    ifFav = false;
                                }
                                $.post("../eiaPlatForm/favEiaAreaInfoSave", {eiaAreaInfoId: eiaAreaInfoId, ifFav: ifFav}, function (result) {
                                    if (result.code == 0) {
                                        var msg = '收藏成功！';
                                        if (ifFav == false) {
                                            msg = '已取消收藏！';
                                        }
                                        layer.msg(msg, {icon: 1, time: 1500}, function () {});
                                    } else {
                                        if (result.msg) {
                                            layer.msg("处理失败！<br/><div style='color:red;'>错误信息：" + result.msg + "</div>", {
                                                icon: 2,
                                                time: 1500
                                            });
                                        } else {
                                            layer.msg('收藏失败！', {icon: 2, time: 1500});
                                        }
                                    }
                                });
                                //确定
                                curState ? $this.removeClass('active') : $this.addClass('active');
                                curState ? $('.ifCollectTxt',$this).html('') : $('.ifCollectTxt',$this).html('取消');
                            },function (index) {
                                //取消
                            });
                        })
                            .on('click', '.projectName,.moreFilesBtn', function () {   //打开详情页
                                var eiaAreaInfoId = $(this).closest('.show-li').attr('eiaAreaInfoId');
                                var pageUrl = '../eiaPlatForm/eiaAreaInfoDetail?eiaAreaInfoId=' + eiaAreaInfoId;
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
                                    },
                                    end: function () {

                                    },
                                    min: function () {
                                        $(".layui-layer-title").text("区域概况详情");
                                    },
                                    restore: function () {
                                        $(".layui-layer-title").text(" ");
                                    }
                                });
                            });

                        $('.area-info-ul').append($str);
                    }
                }else{
                    $('.area-info-ul').prev('.empty-note').css('display','block');
                }

            }
        });
    };
    //渲染环境敏感区Tab页显示数据 方法
    var showSensAreaItem = function(page, sensAreaSearch, searchStringify, regionName){
        $.ajax({
            url:"../eiaPlatForm/eiaSensAreaQuery",
            type:"POST",
            data:{page: page, limit: 10, sensAreaSearch: sensAreaSearch, searchStringify: searchStringify, regionName: regionName},
            dataType: "json",
            async: true,
            success: function (result) {
                $('.sens-area-ul').empty();
                if(result.data.length){
                    $('.sens-area-ul').prev('.empty-note').css('display','none');
                    for(var i=0; i<result.data.length; i++){
                        var ifCollectClass = result.data[i].ifFav ? "active" : "",
                            ifCollectText = result.data[i].ifFav ? "取消" : "";
                        var str = "<li class='show-li' eiaSensAreaId='"+result.data[i].id+"'>" +
                            "                            <div class='sinner lf'>" +
                            "                                <a class='s-title projectName'>"+result.data[i].projectName+"</a>" +
                            "                                <div class='s-data-box'>" +
                            "                                    <div class='s-d-item'>" +
                            "                                        <p class='sd-p'>" +
                            "                                            <span class='sd-head'>所在区域：</span>" +
                            "                                            <span class='sd-val'>"+result.data[i].regionName+"</span>" +
                            "                                        </p>" +
                            "                                    </div>" +
                            "                                    <div class='s-d-item'>" +
                            "                                        <p class='sd-p'>" +
                            "                                            <span class='sd-head'>基本情况简介：</span>" +
                            "                                            <span class='sd-val'>"+result.data[i].projectMemo+"</span>" +
                            "                                        </p>" +
                            "                                    </div>" +
                            "                                </div>" +
                            "                                <div class='s-files-box'>" +
                            "                                    <div class='s-f-inner lf'>附件：</div>" +
                            "                                    <div class='s-f-inner rg files-item-box'></div>" +
                            "                                </div>" +
                            "                            </div>" +
                            "                            <div class='sinner rg'>" +
                            "                                <a class='layui-btn layui-btn-primary layui-btn-radius collectBtn singleBtn "+ifCollectClass+"'><i class='larry-icon'></i><span class='ifCollectTxt'>"+ifCollectText+"</span>收藏</a>" +
                            "                            </div>" +
                            "                        </li>";

                        var $str = $(str);
                        if (result.data[i].fileList) {
                            var filesArr = result.data[i].fileList,
                                showLength = filesArr.length > 2 ? 2 : filesArr.length;
                            for (var n = 0; n < showLength; n++) {
                                var filestr = "<a class='file-text' fileId='"+filesArr[n].id+"'>" + filesArr[n].fileName + "</a>";
                                var $filestr = $(filestr).on('click',function () {
                                    var fileId = $(this).attr('fileId');
                                    downloadFile(fileId);
                                });
                                $('.files-item-box', $str).append($filestr);
                            }
                            var moreFilesStr = "<a class='moreFilesBtn'>更多...</a>";
                            filesArr.length > 2 ? $('.files-item-box', $str).append(moreFilesStr) : "";
                        }

                        $str.on('click', '.collectBtn', function () {  //收藏按钮
                            var eiaSensAreaId = $(this).closest('.show-li').attr('eiaSensAreaId');
                            var $this = $(this);
                            var curState = $this.hasClass('active'),
                                ifText = curState ? "取消" : "";
                            layer.confirm('是否'+ifText+'收藏?', {icon: 3}, function (index) {
                                var ifFav = true;
                                if (curState == true) {
                                    ifFav = false;
                                }
                                $.post("../eiaPlatForm/favEiaSensAreaSave", {eiaSensAreaId: eiaSensAreaId, ifFav: ifFav}, function (result) {
                                    if (result.code == 0) {
                                        var msg = '收藏成功！';
                                        if (ifFav == false) {
                                            msg = '已取消收藏！';
                                        }
                                        layer.msg(msg, {icon: 1, time: 1500}, function () {});
                                    } else {
                                        if (result.msg) {
                                            layer.msg("处理失败！<br/><div style='color:red;'>错误信息：" + result.msg + "</div>", {
                                                icon: 2,
                                                time: 1500
                                            });
                                        } else {
                                            layer.msg('收藏失败！', {icon: 2, time: 1500});
                                        }
                                    }
                                });
                                //确定
                                curState ? $this.removeClass('active') : $this.addClass('active');
                                curState ? $('.ifCollectTxt',$this).html('') : $('.ifCollectTxt',$this).html('取消');
                            },function (index) {
                                //取消
                            });
                        })
                            .on('click', '.projectName,.moreFilesBtn', function () {   //打开详情页
                                var eiaSensAreaId = $(this).closest('.show-li').attr('eiaSensAreaId');
                                var pageUrl = '../eiaPlatForm/eiaSensAreaDetail?eiaSensAreaId=' + eiaSensAreaId;
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
                                    },
                                    end: function () {

                                    },
                                    min: function () {
                                        $(".layui-layer-title").text("环境敏感区详情");
                                    },
                                    restore: function () {
                                        $(".layui-layer-title").text(" ");
                                    }
                                });
                            });

                        $('.sens-area-ul').append($str);
                    }
                }else{
                    $('.sens-area-ul').prev('.empty-note').css('display','block');
                }

            }
        });
    };

    //初始加载
    showFilter("../eiaPlatForm/getEiaDataShareData");
    // showFilter('/eia/static/js/layuiFrame/data/eiaDSProjectFilter.json');
    showProjectItem(1, "", "");
    eiaDataShareTab();
    eiaPlanShowTab();
    eiaAreaInfoTab();
    eiaSensAreaTab();

    //新增按钮
    $('.larry-btn a.layui-btn').click(function () {
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });
    var active = {
        getSelectProject: function () {    //环评项目查询
            var dataShareSearch = $("#dataShareSearch").val();
            eiaDataShareTab(dataShareSearch, searchStringify, regionName);
            showProjectItem(1, dataShareSearch, searchStringify, regionName)
        },
        getSelectPlanShow: function () {    //规划公示查询
            var planShowSearch = $("#planShowSearch").val();
            eiaPlanShowTab(planShowSearch, searchStringify, pubDate);
            showPlanShowItem(1, planShowSearch, searchStringify, pubDate)
        },
        getSelectAreaInfo: function () {    //环境敏感区查询
            var areaInfoSearch = $("#areaInfoSearch").val();
            eiaAreaInfoTab(areaInfoSearch, searchStringify, regionName);
            showAreaInfoItem(1, areaInfoSearch, searchStringify, regionName)
        },
        getSelectSensShow: function () {    //环境敏感区查询
            var sensAreaSearch = $("#sensAreaSearch").val();
            eiaSensAreaTab(sensAreaSearch, searchStringify, regionName);
            showSensAreaItem(1, sensAreaSearch, searchStringify, regionName)
        }
    }
    function downloadFile(fileId) {
        if (fileId) {
            window.location.href = "../eiaPlatForm/downloadFile?fileId=" + fileId;
        }
    }
});