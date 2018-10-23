layui.use(['jquery', 'layer', 'form', 'element'], function () {
    var $ = layui.jquery,
        layer = layui.layer,
        form = layui.form,
        element = layui.element;
    var reqData = {};

    //监听复选框
    form.on('checkbox()', function (data) {
        var name = $(data.elem).data('domain')
        if (!reqData[name]) {
            reqData[name] = []
            reqData[name + "Code"] = []
        }
        if (data.elem.checked) {
            reqData[name].push(data.elem.value)
            reqData[name + "Code"].push($(data.elem).data('name'))
        } else {
            var index = reqData[name + "Code"].indexOf($(data.elem).data('name'));
            reqData[name].splice(index, 1);
            reqData[name + "Code"].splice(index, 1);
        }
        $('#' + name).val(reqData[name]);
        $('#' + name + "Code").val(reqData[name + "Code"]);


        //**其他新增input***/
        if ($(data.elem).data('name') == "QT") {

        }

    });

    /***是否立项监听**/
    form.on('select(ifSet)', function (data) {

        form.render('select')
    });


    /****
     * 编辑时渲染数据项
     */
        $("#eiaProjectExploreId").val(params.eiaProjectExploreId)
        $.post("../eiaProjectExplore/getEiaProjectExploreDataMapDomainCode", {eiaProjectExploreId: params.eiaProjectExploreId}, function (data) {
            var data = data.data;
            for (var key in data) {
                /**下拉树数据渲染**/
                if (key == "environmentaType") {
                    $("#environmentaTypeDrop").text(data[key])
                }

                if(key == "otherSense" || key == "existProblem"){
                    var eleId = key + "QT"
                    if (data[key+"Code"].indexOf("QT")>=0) {
                        $("#" + eleId).removeClass("display-none");
                    } else {
                        $("#" + eleId).addClass("display-none");
                    }
                }
                $("#" + key).text(data[key])
            }
            /****立项情况显示*/
            if(data.ifSet){
                if (data.ifSet == "是") {
                    $("#ifSetNO").addClass("display-none");
                    $("#ifSetYES").removeClass("display-none");
                } else if (data.ifSet == "否") {
                    $("#ifSetYES").addClass("display-none");
                    $("#ifSetNO").removeClass("display-none");
                }
            }

            /***其他****/
        })





});

