/**
 *Created by HSH on 2018/6/25 11:47
 */
/**导航栏点击事件映射**/
layui.use(['form', 'layer','table','element'], function () {
    var form = layui.form,
        table = layui.table,
        layer = layui.layer,
        laypage = layui.laypage,
        element = layui.element,
        upload = layui.upload;

    var search
    /**更新渲染导航条**/
    element.render();
    $('.larry-btn a.layui-btn').on('click', function () {
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });
    /**回调函数**/
    var active = {
        getSelect: function () {
            $(".searchResult").removeClass("display-none");
            var keywords = $("#keywords").val();
            table.render({
                id: 'searchResult',
                elem: '#searchResult',
                page: true,
                limit: 10,
                url: '../eiaProject/getSearchKeywordsDataList',
                toolbar: ' ',
                defaultToolbar:['filter', 'print', 'exports'],
                method: 'post',
                cols: [[
                    {type: 'numbers', width: 64, title: '序号'},
                    {field: 'name', width: 200, title: '地名', align: 'center'},
                    {field: 'address', width: 260, title: '详细地址', align: 'center'},
                    {width: 70, title: '操作', align: 'center', toolbar: '#searchResultBar'}
                ]],
                where: {
                    keywords: keywords
                }
            });
            //监听工具条
            table.on('tool(searchResult)', function (obj) {
                var data = obj.data;
                if (obj.event === 'location') {
                    $(".searchResult").addClass("display-none");
                    var coor = data.location;
                    locationByCoord(coor.split(",").map(function (i) {
                        return i * 1
                    }));
                }
            });
        },
        //全屏
        fullScreen: function () {
            $("#fullScreen").parent().parent().find("li").addClass("display-none")
            $("#exitFullScreen").parent().removeClass("display-none")
            $(".amap-ui-control-layer").addClass("display-none")
            $(".amap-toolbar").addClass("display-none")
        },
        //退出全屏
        exitFullScreen: function () {
            $("#fullScreen").parent().parent().find("li").removeClass("display-none");
            $("#exitFullScreen").parent().addClass("display-none");
            $(".amap-ui-control-layer").removeClass("display-none");
            $(".amap-toolbar").removeClass("display-none");
        },
        //距离测量
        distance: function () {
            ruler.turnOn();
        },
        //面积测量
        area: function () {
            measureArea.measureArea();
        }
    };


   $.post( "../eiaProjectExplore/getEiaProjectExploreDataMapDomainCode", {eiaProjectExploreId: params.eiaProjectExploreId}, function (data) {
       var data = data.data;
       var dataResult = {};
       dataResult.config = {};
       dataResult["geoName"] = data.buildArea;
       dataResult["projectName"] = data.projectName;
       dataResult["objType"] = "main";
       dataResult["config"]["ifDisplayName"] = true;
       dataResult["config"]["strokeStyle"] = "solid";
       dataResult["config"]["strokeColor"] = "9EFFEF";
       dataResult["config"]["strokeWeight"] = "3";
       dataResult["config"]["strokeOpacity"] = "90";
       dataResult["config"]["fillOpacity"] = "20";
       dataResult["config"]["fillColor"] = "9EFFEF";
       dataResult["config"]["zIndex"]=0;
       dataResult["config"]["ifDisplay"]=true;
       dataResult.code = 'b1'
       dataResult.geoJson = data.geoJson;
       loadingDB(dataResult);
   })

})




