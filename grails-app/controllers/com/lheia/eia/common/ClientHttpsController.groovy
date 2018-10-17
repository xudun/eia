package com.lheia.eia.common

import javax.servlet.ServletContext

class ClientHttpsController {

    def setClientSessionId(){
        println(params.staffCode+"================登录")
        ServletContext servletContext = request.getSession().getServletContext();
        servletContext.setAttribute(params.staffCode, params.sessionId)
        return false
    }
    def logoutDestroyStaffCode(){
        println(params.staffCode+"================退出")
        ServletContext servletContext = request.getSession().getServletContext();
        servletContext.removeAttribute(params.staffCode)
        return true
    }
}
