package com.lheia.eia.common

import com.lheia.eia.project.EiaEnvProject
import com.lheia.eia.project.EiaProject
import grails.converters.JSON

class QrCodeRequestController {

    def getQrCode() {
        def qrcode = params.qrcode
        def data = [:]
        if(qrcode){
            def eiaProject = EiaProject.findAllByRandomCode(qrcode)
            if(eiaProject){
                data.projectNo = eiaProject.projectNo
                data.projectName = eiaProject.projectName
                data.fileTypeChild = eiaProject.fileTypeChild
                data.buildArea = eiaProject.buildArea
                def eiaEnvProject = EiaEnvProject.findByEiaProjectId(eiaProject.id)
                if(eiaEnvProject){
                    data.projectMemo = eiaEnvProject.projectMemo
                }
                else{
                    data.projectMemo = '-'
                }
            }
            else {
                data.projectNo = '-'
                data.projectName = '-'
                data.fileTypeChild = '-'
                data.buildArea = '-'
                data.projectMemo = '-'
            }

        }
        render([success: true, msg: '获取成功', data:data] as JSON)
    }
}
