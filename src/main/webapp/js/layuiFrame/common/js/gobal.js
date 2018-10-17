layui.config({
	base: request_url_root+'/static/js/layuiFrame/common/lib/'
});
layui.use(['jquery','layer','element','common'],function(){
	var $ = layui.$,
	layer = layui.layer,
	common = layui.common,
	device = layui.device(),
	element = layui.element;


	$(document).ready(function() {
		// 浏览器兼容检查
		if (device.ie && device.ie < 9) {
			common.larryCmsError('本系统最低支持ie8，您当前使用的是古老的 IE' + device.ie + '！ \n 建议使用IE9及以上版本的现代浏览器', common.larryCore.tit);
		}
	});
});

// 在url中获取指定参数值
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