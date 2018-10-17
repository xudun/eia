package eia

import com.lheia.eia.common.HttpMesConstants
import com.lheia.eia.common.HttpUrlConstants
import com.lheia.eia.tools.HttpConnectTools
import com.lheia.eia.tools.JsonHandler

import javax.servlet.ServletContext
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletResponse

class SecurityInterceptor {
    SecurityInterceptor() {
        matchAll()//.except(uri: '**/js/**')
    }

    boolean before() {
        /**
         * 如果是api发送消息到子系统直接过滤
         */
        if (actionName == "setClientSessionId" || actionName == "logoutDestroyStaffCode"|| actionName =="getQrCode" || actionName == 'login') {
            return true
        }
        ServletContext servletContext = request.getSession().getServletContext();
        /**
         * 获取浏览器Cookie
         */
        def cookies = request.getCookies()
        def clientSessionId = ""
        def roleCode = ""
        for (Cookie cookie : cookies) {
            if ("sessionId" == cookie.getName()) {
                clientSessionId = cookie.getValue()
            }
            if ("roleCode" == cookie.getName()) {
                roleCode = cookie.getValue()
            }
        }
        /**
         * 本地浏览器无sessionId
         */
        if (clientSessionId == null || clientSessionId == "") {
            if (request.xhr) {
                response.sendError HttpServletResponse.SC_UNAUTHORIZED
            } else {
                redirect([uri: HttpUrlConstants.LOGIN_FORWARD_JUMP])
            }
            return
        }
        /**
         * 服务器session中的sessionStaff为空且浏览器中sessionId存在
         */
        else if (session.staff == null && clientSessionId != "") {
            def param = [:]
            param.sessionId = clientSessionId
            def getAuth = HttpConnectTools.getResponseJson(HttpUrlConstants.checkAndGetAuth, param)
            def auth = JsonHandler.jsonToMap(getAuth)
            if (auth.msg == HttpMesConstants.RESPONSE_SC_OK) {
                session.staff = auth.data
            } else {
                if (request.xhr) {
                    response.sendError HttpServletResponse.SC_FORBIDDEN
                } else {
                    redirect([uri: HttpUrlConstants.ACCID_FORWARD_JUMP])
                }
                return
            }
        }
        /**
         * 当前登录的staff和session中的staff不一致(切换用户场景)
         */
        else if (session.staff != null && servletContext.getAttribute(session.staff.staffCode) == null) {
            def param = [:]
            param.sessionId = clientSessionId
            def getAuth = HttpConnectTools.getResponseJson(HttpUrlConstants.checkAndGetAuth, param)
            def auth = JsonHandler.jsonToMap(getAuth)
            if (auth.msg == HttpMesConstants.RESPONSE_SC_OK) {
                session.staff = auth.data
                servletContext.setAttribute(session.staff.staffCode, clientSessionId)
            } else {
                session.invalidate()
                if (request.xhr) {
                    response.sendError HttpServletResponse.SC_FORBIDDEN
                } else {
                    redirect([uri: HttpUrlConstants.ACCID_FORWARD_JUMP])
                }
                return
            }
        }
        /**
         * 本地浏览器cookie中有sessionId且servletContext中的sessionId和浏览器中的不同(不在同一地点登录场景)
         */
        else if (session.staff != null && servletContext.getAttribute(session.staff.staffCode) != clientSessionId) {
            session.invalidate()
            if (request.xhr) {
                response.sendError HttpServletResponse.SC_FORBIDDEN
            } else {
                redirect([uri: HttpUrlConstants.ACCID_FORWARD_JUMP])
            }
            return
        }
        /**
         * 浏览器有sessionId,session不为空且session和cookie中的sessionId一致
         */
        else if (clientSessionId != "" && session.staff != "" && servletContext.getAttribute(session.staff.staffCode) == clientSessionId) {
            if (session.roleCode == null) {
                session.roleCode = roleCode
            }
            if (session.roleCode != roleCode) {
                session.roleCode = roleCode
                def param = [:]
                param.sessionId = clientSessionId
                def getAuth = HttpConnectTools.getResponseJson(HttpUrlConstants.checkAndGetAuth, param)
                def auth = JsonHandler.jsonToMap(getAuth)
                if (auth.msg == HttpMesConstants.RESPONSE_SC_OK) {
                    session.staff = auth.data
                }
            }
        }
        true
    }

    boolean after() { true }

    void afterView() {
        // no-op
    }
}
