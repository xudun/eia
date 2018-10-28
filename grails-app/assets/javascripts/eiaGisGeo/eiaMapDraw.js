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
        point: function () {
            drawEvent("Point", function () {

            },function(geoJSON){
                console.log(geoJSON.toGeoJSON()[0].geometry.coordinates[0])
                parent.$("#coordStartEast").val("")
                parent.$("#coordEndEast").val("")
                parent.$("#coordStartNorth").val("")
                parent.$("#coordEndNorth").val("")
                parent.$("#coordEast").val(geoJSON.toGeoJSON()[0].geometry.coordinates[0])
                parent.$("#coordNorth").val(geoJSON.toGeoJSON()[0].geometry.coordinates[1])
                parent.$("#geoJson").val(JSON.stringify(geoJSON.toGeoJSON()[0].geometry))
                var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                regeocoder(coor2LLObj(geoJSON.toGeoJSON()[0].geometry.coordinates))
                parent.layer.close(index);
            });
        },
        line: function () {
            drawEvent("LineString", function () {
            }, function (geoJSON) {
                var coordArr = geoJSON.toGeoJSON()[0].geometry.coordinates
                parent.$("#coordEast").val("")
                parent.$("#coordNorth").val("")
                parent.$("#coordStartEast").val(coordArr[0][0])
                parent.$("#coordEndEast").val(coordArr[coordArr.length-1][0])
                parent.$("#coordStartNorth").val(coordArr[0][1])
                parent.$("#coordEndNorth").val(coordArr[coordArr.length-1][1])
                console.log(JSON.stringify(geoJSON.toGeoJSON()[0]))
                parent.$("#geoJson").val(JSON.stringify(geoJSON.toGeoJSON()[0].geometry))
                var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                regeocoder(coor2LLObj(geoJSON.toGeoJSON()[0].geometry.coordinates[0]))
                parent.layer.close(index);
            });
        },
        polygon: function () {
            drawEvent("Polygon" , function () {
            }, function (geoJSON) {
                var coordArr = geoJSON.toGeoJSON()[0].geometry.coordinates
                lnglatXY = centerPointByGeo(geoJSONTOGeo(geoJSON.toGeoJSON()[0].geometry)); //已知点坐标
                parent.$("#coordStartEast").val("")
                parent.$("#coordEndEast").val("")
                parent.$("#coordStartNorth").val("")
                parent.$("#coordEndNorth").val("")
                parent.$("#coordEast").val(lLObj2Coor(lnglatXY)[0])
                parent.$("#coordNorth").val(lLObj2Coor(lnglatXY)[1])
                parent.$("#geoJson").val(JSON.stringify(geoJSON.toGeoJSON()[0].geometry))
                var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                regeocoder(lnglatXY)
                parent.layer.close(index);
            });
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

    function regeocoder(lnglatXY) {  //逆地理编码
        var geocoder = new AMap.Geocoder({
            radius: 200,
            extensions: "all"
        });
        geocoder.getAddress(lnglatXY, function (status, result) {
            if (status === 'complete' && result.info === 'OK') {
                if (result.regeocode) {
                  //  if(!parent.$("#buildArea").val()){
                        parent.$("#buildArea").val(result.regeocode.formattedAddress);
                //    }
                }
            }
        });
    }

})




