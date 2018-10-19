/**
 * ajax 请求失败后js处理方法
 */

if (typeof jQuery !== 'undefined') {
    (function ($) {
        $(document).ajaxError(function (e, jqxhr, settings, exception) {
            switch (jqxhr.status) {
                case 401:
                    //(top||window).loginForwardJump();
                    top.location.href = request_url_login_forward;
                    break;
                case 403:
                    top.location.href = request_url_accid_forward;
                    break;
                case 500:
                    alert('系统错误');
                    break;
                /*   case 0:
                 top.location.href = "http://localhost:8090/auth/login_forward_jump";
                 break;*/
                default:
                    alert('操作失败\n' + 'status code:' + jqxhr.status + (exception ? ('\n' + exception) : ''));
            }
        })
    })(jQuery);
}

/**
 * ajax请求401后,指向系统login_forward_jump页面
 */
function loginForwardJump() {
    //top.location.href = request_url_login_forward;
}
/**
 * 获取url中的明文参数
 * @returns {{}}
 */
function getParamByUrl(url){
    var param={};
    paramstr=url.split("?")[1];
    if(paramstr){
        var params=paramstr.split("&");
        for(var i in params){
            var node=params[i].split("=");
            param[node[0]]=node[1];
        }
    }
    return param;
}
var params = getParamByUrl(location.href)