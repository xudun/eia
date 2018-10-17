package com.lheia.eia.common

import com.lheia.eia.tools.HttpConnectTools
import com.lheia.eia.tools.JsonHandler
import grails.converters.JSON

import javax.servlet.ServletContext
import javax.servlet.http.Cookie
import javax.servlet.http.HttpSession

class SingleLoginController {
    /**
     * 用户登录
     * @return
     */
    def login() {
        /**
         * 先查看当前用户是否登录过,如果登录过则创建session
         */
        def staffCode = params.staffCode
        String workFlowUrl = params.workFlowUrl
        String url = workFlowUrl.replace('%', '?')
        if (params.tableNameId)
            url = url + '&tableNameId=' + params.tableNameId
        def oriSessionId = servletContext.getAttribute(staffCode)
        if (oriSessionId) {
            def param = [:]
            param.sessionId = oriSessionId
            println(oriSessionId)
            def resData = HttpConnectTools.getResponseJson(HttpUrlConstants.checkAndGetAuth, param)
            println('进到这里来了')
            def auth = JsonHandler.jsonToMap(resData)
            if (auth.msg == HttpMesConstants.RESPONSE_SC_OK) {
                session.staff = auth.data
                println(session.staff.funcCode)
                def roleId = session.staff.roleId
                println(''+session.staff.staffId+roleId)
                Cookie sessionId = new Cookie("sessionId", oriSessionId);
                Cookie roleCode = new Cookie("roleCode", roleId)
                sessionId.setMaxAge(-1)
                roleCode.setMaxAge(-1)
                sessionId.domain = HttpUrlConstants.cookieDomain
                sessionId.setPath(HttpUrlConstants.cookiePath)
                roleCode.domain = HttpUrlConstants.cookieDomain
                roleCode.setPath(HttpUrlConstants.cookiePath)
                response.addCookie(sessionId)
                response.addCookie(roleCode)
                redirect(url: url)
            }else{  }
        } else {
            HttpSession session = request.getSession()
            def param = [:]
            param.name = staffCode
            param.sessionId = session.getId()
            /**
             * 向api请求当前用户权限，返回登录状态json
             */
            def responseLoginStatusJson = HttpConnectTools.getResponseJson(HttpUrlConstants.getSingleLoginState, param)
            if (responseLoginStatusJson) {
                def ResponseMessage = JsonHandler.jsonToMap(responseLoginStatusJson).msg
                if (ResponseMessage == HttpMesConstants.RESPONSE_SC_OK) {
                    /**
                     * 返回员工系统权限信息json
                     */
                    def responseStaffRightJson = HttpConnectTools.getResponseJson(HttpUrlConstants.checkAndGetAuth, param)
                    if (responseStaffRightJson) {
                        def staff = JsonHandler.jsonToMap(responseStaffRightJson)
                        session.staff = staff.data
                        session.sessionId = session.getId()
                        ServletContext servletContext = request.getSession().getServletContext();
                        servletContext.setAttribute(session.staff.staffCode, session.sessionId);//是不是这里的错误
                        println('存入servletContext的staffCode:' + session.staff.staffCode + '++++++++++' + '的sessionId是：' + session.sessionId)
                        /**
                         * 当前权限切换角色时，判断权限是否更改，重新赋值权限到api接口应用，推送用户权限作用域在子系统。
                         */
                        def roleId = session.staff.roleId
                        Cookie sessionId = new Cookie("sessionId", session.getId());
                        Cookie roleCode = new Cookie("roleCode", roleId)
                        sessionId.setMaxAge(-1)
                        roleCode.setMaxAge(-1)
                        sessionId.domain = HttpUrlConstants.cookieDomain
                        sessionId.setPath(HttpUrlConstants.cookiePath)
                        roleCode.domain = HttpUrlConstants.cookieDomain
                        roleCode.setPath(HttpUrlConstants.cookiePath)
                        response.addCookie(sessionId)
                        response.addCookie(roleCode)
                        redirect(url: url)
                    }
                } else if (ResponseMessage == '不能够重复登录') {
                    render([success: false, msg: ResponseMessage] as JSON)
                } else {
                    render([success: false, msg: ResponseMessage] as JSON)
                }
            } else {
                render([success: false, msg: '连接出现错误'] as JSON)
            }
        }
    }
}
