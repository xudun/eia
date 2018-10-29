package com.lheia.eia.tools

import com.lheia.eia.common.HttpUrlConstants
import org.apache.http.HttpEntity
import org.apache.http.NameValuePair
import org.apache.http.client.ClientProtocolException
import org.apache.http.client.config.RequestConfig
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.client.methods.CloseableHttpResponse
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.methods.HttpPost
import org.apache.http.impl.client.CloseableHttpClient
import org.apache.http.impl.client.HttpClients
import org.apache.http.message.BasicNameValuePair
import org.apache.http.util.EntityUtils

/**
 * Created by XinXi-001 on 2017/11/21.
 */
class HttpConnectTools {

    static String HTTP_METHOD_POST="POST"
    static String HTTP_METHOD_GET="GET"

    static getResponseJson(String url, Map<String, String> params, String httpMethod) {
        /** 创建默认的CloseableHttpClient实例 **/
        CloseableHttpClient httpclient = HttpClients.createDefault()
        /**
         * 设置请求次数，时间等
         */
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(HttpUrlConstants.SOCKET_TIME_OUT)      /** 响应超时时间，超过此时间不再读取响应 **/
                .setConnectTimeout(HttpUrlConstants.CONNECT_TIME_OUT)     /** 链接建立的超时时间 **/
                .build();

        def httpRequest
        try {
            if (httpMethod == "POST") {
                /**
                 *   如果使用HttpPost方法提交HTTP POST请求,则需要使用HttpPost类的setEntity方法设置请求参数。
                 *   参数则必须用NameValuePair[]数组存储
                 */
                List<NameValuePair> formParams = new ArrayList<NameValuePair>()
                params.each { entry ->
                    formParams.add(new BasicNameValuePair(entry.key, entry.value.toString()))
                }
                /**
                 *  创建请求方法的实例，并指定请求URL。
                 *  如果需要发送GET请求，创建HttpGet对象；如果需要发送POST请求，创建HttpPost对象
                 */
                httpRequest = new HttpPost(url)
                UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(formParams, "UTF-8")
                httpRequest.setEntity(uefEntity)
            }else if(httpMethod=="GET"){
                url=url+"?"
                params.each { entry ->
                    url = url + entry.key + "=" + entry.value + "&"
                }
                httpRequest = new HttpGet(url);
            }
            httpRequest.setConfig(requestConfig);
            /**
             * 调用CloseableHttpClient对象的execute(HttpUriRequest request)发送请求，
             * 该方法返回一个CloseableHttpResponse
             */
            CloseableHttpResponse response = httpclient.execute(httpRequest)
            int code = response.getStatusLine().getStatusCode()
            if (code == 200) {
                try {
                    HttpEntity entity = response.getEntity()
                    if (entity != null) {
                        def responseJson = EntityUtils.toString(entity, "UTF-8")
                        return responseJson
                    } else {
                        return false
                    }
                }
                finally {
                    response.close()
                }
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace()
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace()
        } catch (IOException e) {
            e.printStackTrace()
        } finally {
            /** 关闭连接,释放资源 **/
            try {
                httpclient.close()
            } catch (IOException e) {
                e.printStackTrace()
            }
        }
    }

    static getResponseJson(String url, Map<String, String> params) {
        return getResponseJson(url,params,HTTP_METHOD_POST);
    }
}
