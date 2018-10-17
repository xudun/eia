/**
 *Created by HSH on 2018/2/8 15:26
 */
//ajax封装
/***
 *ajax
 */
function ajax(url,data,sucessFunc,async){
    ajaxBox(url,data,sucessFunc,async,null,null);
}
function ajaxBox(url,data,sucessFunc,async,errorFunc,beforeFunc){
    $.ajax({
        url:url,
        type:'POST', //GET
        async:async=="false"?false:true,    //或false,是否异步
        data:data,
        timeout:500000,    //超时时间
        dataType:'json',    //返回的数据格式：json/xml/html/script/jsonp/text
        beforeSend:beforeFunc?beforeFunc:function(xhr){
            // console.log(xhr);
            // console.log('发送前');
        },
        success:sucessFunc?sucessFunc:function(data,textStatus,jqXHR){
            console.log(data);
            console.log(textStatus);
            console.log(jqXHR);
        },
        error:errorFunc?errorFunc:function(xhr,textStatus){
            console.log('错误');
            console.log(xhr);
            console.log(textStatus);
            console.log(url);
        }
    })
}