<!doctype html>
<html lang="en" class="no-js">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <title>
        联合赤道业务系统
    </title>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <script type="text/javascript">
        var request_url_root = '${request.contextPath}';
        var request_url_login_forward = '${com.lheia.eia.common.HttpUrlConstants.LOGIN_FORWARD_JUMP}';
        var request_url_accid_forward = '${com.lheia.eia.common.HttpUrlConstants.ACCID_FORWARD_JUMP}';
    </script>
    <link rel="icon" href="/assets/favicon.ico" type="image/x-icon"/>
    <!-- load css -->
    <link rel="stylesheet" href="${resource(dir: '/js/layuiFrame/common/frame/layui/css',file: 'layui.css')}" />
    <link rel="stylesheet" href="${resource(dir: '/js/layuiFrame/common/css',file: 'gobal.css')}" />
    <link rel="stylesheet" href="${resource(dir: '/js/layuiFrame/common/css',file: 'animate.css')}" />
    <asset:stylesheet src="common.css"/>
    <asset:stylesheet src="eiaTask/eiaTask.css"/>
    <asset:stylesheet src="eiaContract/eiaContract.css"/>
    <!--树ztree-->
    <link rel="stylesheet" href="${resource(dir: '/js/zTree_v3/css/metroStyle',file: 'metroStyle.css')}" />
    <!-- 加载js文件-->
    <script src="${resource(dir: '/js/jquery',file: 'jquery-2.1.3.js')}" ></script>
    <script src="${resource(dir: '/js/jquery',file: 'jquery.tmpl.min.js')}" ></script>
    <script src="${resource(dir: '/js/layuiFrame/common/frame/layui',file: 'layui.js')}" ></script>
    <script src="${resource(dir: '/js/layuiFrame/common/js',file: 'gobal.js')}" ></script>
    <!--树ztree-->
    <script src="${resource(dir: '/js/zTree_v3/js',file: 'jquery.ztree.all-3.5.min.js')}" ></script>
    <!--下拉树ztree-->
    <script src="${resource(dir: '/js/zTree_v3',file: 'dropDown.js')}" ></script>
    <!--多选下拉框-->
    <script src="${resource(dir: '/js/layuiFrame/common/frame/layui/lay',file: 'layui-mz-min.js')}" ></script>
    <!--多选下拉框2-->
    <script src="${resource(dir: '/js/layuiFrame/common/frame/layui/lay',file: 'formSelects.js')}" ></script>
    <script src="http://webapi.amap.com/maps?v=1.4.4&key=47858344d76296461eef327717ecc73d&plugin=AMap.PolyEditor,AMap.CircleEditor,AMap.Geocoder" ></script>
    <script src="//webapi.amap.com/ui/1.0/main.js?v=1.0.11"></script>
    <script src="${resource(dir: '/js/jquery',file: 'ajaxBox.js')}" ></script>
    <asset:javascript src="application.js"/>
    <g:layoutHead/>
</head>
<body>
    <g:layoutBody/>
</body>
</html>
