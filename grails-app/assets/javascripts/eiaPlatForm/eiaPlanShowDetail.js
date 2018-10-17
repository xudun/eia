layui.use(['jquery', 'form'], function(){
    var $ = layui.jquery,
        form = layui.form;
    var eiaPlanShowId = parent.$('#eiaPlanShowId').val();
    if (!eiaPlanShowId) {
        eiaPlanShowId = getParamFromUrl(document.location.href,"eiaPlanShowId");
    }

    $('#eiaPlanShowId').val(eiaPlanShowId);

    //渲染编辑页数据
    $.ajax({
        url:"../eiaPlatForm/getEiaPlanShowDataMap?eiaPlanShowId=" + eiaPlanShowId,
        type:"POST",
        data:{},
        dataType: "json",
        success: function (result) {
            if (result) {
                console.log(result.eiaPlanShow);
                $('#title').text(result.eiaPlanShow.title);
                $('#source').text(result.eiaPlanShow.source);
                $('#pubDate').text(result.pubDate);
                $('#content').text(result.eiaPlanShow.content);
                // $('#geographicEast').text(result.eiaPlanShow.geographicEast);
                // $('#geographicStartEast').text(result.eiaPlanShow.geographicStartEast);
                // $('#geographicEndEast').text(result.eiaPlanShow.geographicEndEast);
                // $('#geographicNorth').text(result.eiaPlanShow.geographicNorth);
                // $('#geographicStartNorth').text(result.eiaPlanShow.geographicStartNorth);
                // $('#geographicEndNorth').text(result.eiaPlanShow.geographicEndNorth);
                $('#spiderFileUrl').attr('href', result.eiaPlanShow.spiderFileUrl);

                $('#spiderFileImagesUrl')
                    .attr('src', result.eiaPlanShow.spiderFileImagesUrl)
                    .on('click', function () {
                        layer.open({
                            type: 1,
                            title: '规划图片',
                            area: ['800px', '600px'],
                            skin: 'demo-class img-layer',
                            content: "<img class='layer-img-box' src='" + result.eiaPlanShow.spiderFileImagesUrl + "'>"
                        });
                    });
            }
        }
    });

});