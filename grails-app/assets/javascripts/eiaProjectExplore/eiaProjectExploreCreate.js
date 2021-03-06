layui.use(['jquery', 'layer', 'form', 'element'], function () {
    var $ = layui.jquery,
        layer = layui.layer,
        form = layui.form,
        element = layui.element;
    var reqData = {};
    /**渲染select和checkBox**/
    $.post('/eia/eiaProjectExplore/getCheckboxAndSelectValue', null, function (res) {
        if (res.code == 0) {
            var data = res.data
            for (var i in data) {
                if (data[i].codeRemark == 'select') {
                    var str = "<option value='" + data[i].code + "'>" + data[i].codeDesc + "</option>";
                    $('#' + data[i].domain).append(str);
                } else if (data[i].codeRemark == 'checkbox') {
                    var str = "<input type='checkbox' lay-skin='primary' name='" + data[i].domain + "-" + data[i].code + "' data-domain='" + data[i].domain + "' lay-filter='" + data[i].domain + "Code'  data-name='" + data[i].code + "' lay-verify='required' value='" + data[i].codeDesc + "' title='" + data[i].codeDesc + "'>"
                    $('#' + data[i].domain).parent().append(str);
                }
            }
            form.render('select');
            form.render('checkbox');
        }
    });


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
            var eleId = name + "QT"
            if (data.elem.checked) {
                $("#" + eleId).removeClass("display-none");
                $("#" + eleId).find("input").attr("lay-verify", 'required')
            } else {
                $("#" + eleId).addClass("display-none");
                $("#" + eleId).find("input").removeAttr("lay-verify")
            }
        }
    });

    /***是否立项监听**/
    form.on('select(ifSet)', function (data) {
        if (data.value == "YES") {
            $("#ifSetNO").addClass("display-none");
            $("#ifSetNO").find("textarea").removeAttr("lay-verify")
            $("#ifSetYES").removeClass("display-none");
            $("#ifSetYES").find("select").attr("lay-verify", 'required')
        } else if (data.value == "NO") {
            $("#ifSetYES").addClass("display-none");
            $("#ifSetYES").find("select").removeAttr("lay-verify");
            $("#ifSetNO").removeClass("display-none");
            $("#ifSetNO").find("textarea").attr("lay-verify", 'required')
        }
        form.render('select')
    });


    $("#environmentaTypeDrop").dropDownForZ({
        url: '/eia/eiaProject/getTreeByDomain?domain=INS_TYPE_CODE',
        width: '99%',
        height: '350px',
        disableParent: true,
        ifSearch: true,
        selecedSuccess: function (data) {  //选中回调
            if (!data.isParent) {
                var str = data.attributes.mark + " " + data.name;
                $("#environmentaType").val(str);
                $("#environmentaTypeDrop").val(str);
            }
        }
    });

    /**文件类型***/
    //下拉树 文件类型
    $("#fileTypeDrop").dropDownForZ({
        url: '/eia/eiaProject/getFileTree',
        width: '99%',
        height: '350px',
        disableParent: true,
        selecedSuccess: function (data) {  //选中回调
            if (!data.isParent) {
                var temp = {
                    id: data.id,
                    name: data.name
                };
                $("#fileType").val(JSON.stringify(temp));
            }
        }
    });

    /****
     * 编辑时渲染数据项
     */
    if (params.pageType == "edit" || params.pageType == "flow" ) {
        $("#eiaProjectExploreId").val(params.eiaProjectExploreId)
        $.post("../eiaProjectExplore/getEiaProjectExploreDataMap", {eiaProjectExploreId: params.eiaProjectExploreId}, function (data) {
            var data = data.data;

            /***checkbox渲染***/
            $("input[type=checkbox]").each(function (index,elem) {
                var key = $(elem).data("domain");
                var valueNode = $(elem).data("name");
                if(data[key]){
                    reqData[key] = data[key].split(",")
                    reqData[key+"Code"] = data[key+"Code"].split(",")
                    elem.checked = (data[key+"Code"].indexOf(valueNode) >= 0)
                }
                /***其他input框显示隐藏***/
                if(valueNode == "QT"){
                    var eleId = key + valueNode
                    if (elem.checked) {
                        $("#" + eleId).removeClass("display-none");
                        $("#" + eleId).find("input").attr("lay-verify", 'required')
                    } else {
                        $("#" + eleId).addClass("display-none");
                        $("#" + eleId).find("input").removeAttr("lay-verify")
                    }
                }
            });
            form.render("checkbox");

            for (var key in data) {
                /**下拉树数据渲染**/
                if (key == "environmentaType") {
                    $("#environmentaTypeDrop").val(data[key])
                }
                if (key == "fileType") {
                    $("#fileTypeDrop").val(data[key])
                    $("#fileTypeDropCode").val(data[key])
                }

                $("#" + key).val(data[key])
                if(key == "ifSet")
                if (data[key] == "YES") {
                    $("#ifSetNO").addClass("display-none");
                    $("#ifSetNO").find("textarea").removeAttr("lay-verify")
                    $("#ifSetYES").removeClass("display-none");
                    $("#ifSetYES").find("select").attr("lay-verify", 'required')
                } else if (data[key] == "NO") {
                    $("#ifSetYES").addClass("display-none");
                    $("#ifSetYES").find("select").removeAttr("lay-verify");
                    $("#ifSetNO").removeClass("display-none");
                    $("#ifSetNO").find("textarea").attr("lay-verify", 'required')
                }
                form.render('select')
            }
            form.render('select')
            if(params.pageType == "flow"){
                var $foreignDir= $("#foreignDir")
                var $industrialDir = $("#industrialDir")
                $foreignDir.parent().append('<input type="text" id="foreignDir1" name="foreignDir" class="layui-input readonly" value="'+$("#foreignDir option:selected").text()+'" readOnly>' )
                $industrialDir.parent().append('<input type="text" id="industrialDir1" name="industrialDir" class="layui-input readonly" value="'+$("#industrialDir option:selected").text()+'" readOnly>')
                console.log($foreignDir);
                $foreignDir.next().remove();
                $foreignDir.remove();
                $industrialDir.next().remove();
                $industrialDir.remove();
                form.render('select')
            }
        })
    }



    form.on('submit(save)', function (data) {
        var loadingIndex = layer.load(1, {time: 10 * 1000, shade: 0.1});
        var actionUrl = "../eiaProjectExplore/eiaProjectExploreSave";
        $.post(actionUrl, data.field, function (data) {
            if (data.code == 0) {
                layer.msg('保存成功', {icon: 1, time: 1000, shade: 0.1}, function () {
                    parent.layui.table.reload("eiaProjectList");
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.layer.close(index)
                });
            } else {
                layer.msg(data.msg, {icon: 2, time: 1000, shade: 0.1});
            }
            layer.close(loadingIndex);
        });
        console.log(data.field);
        return false;
    })

    //地图绘制按钮
    $('.mapDrawBtn').click(function () {
        var index = layer.open({
            title: ' ',
            type: 2,
            shade: false,
            maxmin: true,
            skin: 'larry-green',
            area: ['100%', '100%'],
            content: '../eiaProject/eiaMapDraw',
            success: function (layero, index) {
                var body = layer.getChildFrame('body', index);
            },
            end: function () {

            },
            min: function () {
                $(".layui-layer-title").text("地图绘制");
            },
            restore: function () {
                $(".layui-layer-title").text(" ");
            }
        });
    });

});

