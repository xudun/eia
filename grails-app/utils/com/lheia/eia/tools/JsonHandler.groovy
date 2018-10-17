package com.lheia.eia.tools

import groovy.json.JsonSlurper

/**
 * Created by tianlei on 2018/03/28.
 */
class JsonHandler {

    static jsonToMap(String jsonText) {
        def slurper = new JsonSlurper()
        def sfe = slurper.parseText(jsonText)
        return sfe
    }

}
