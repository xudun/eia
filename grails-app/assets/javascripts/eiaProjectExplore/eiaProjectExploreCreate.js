layui.use(['jquery', 'layer', 'form', 'element'], function () {
    var $ = layui.jquery,
        layer = layui.layer,
        form = layui.form,
        element = layui.element;

    /**渲染select和checkBox**/
    $.post('/eia/eiaProjectExplore/getCheckboxAndSelectValue',null,function(res){
        if(res.code == 0){
            var data = res.data
            for(var i in data){
                if(data[i].codeRemark == 'select'){
                    var str = "<option value='" + data[i].code + "'>" + data[i].codeDesc + "</option>";
                    $('#'+data[i].domain).append(str);
                }else if(data[i].codeRemark == 'checkbox'){
                    var str = "<input type='checkbox' lay-skin='primary'  lay-filter='"+data[i].domain+"Code' id='"+data[i].code+"' name='"+data[i].code+"' lay-verify='required' value='"+data[i].code+"' title='"+data[i].codeDesc+"'>"
                    $('#'+data[i].domain).parent().append(str);
                }
            }
            form.render('select');
            form.render('checkbox');
        }
    })




});

