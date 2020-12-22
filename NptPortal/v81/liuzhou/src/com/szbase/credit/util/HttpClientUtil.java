package com.szbase.credit.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.List;


/**
 * httpclient 调用第三方平台接口
 * Created by zhanglei on 15/8/24.
 */
public class HttpClientUtil {
    /**
     * http post 请求访问第三方平台
     *
     * @param rdpURL 接口url
     * @param params 请求参数
     * @return utf-8编码后的responseData
     * @throws IOException
     */
    public static String readInterfacePost(String rdpURL, List<NameValuePair> params) throws IOException {

        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(rdpURL);

        // 设置 HTTP POST 请求参数
        httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));

        // 设置回调函数，将 responseData 转成 utf-8 编码
        ResponseHandler<String> responseHandler = new BasicResponseHandler() {
            @Override
            public String handleResponse(final HttpResponse response)
                    throws HttpResponseException, IOException {
                StatusLine statusLine = response.getStatusLine();
                if (statusLine.getStatusCode() >= 300) {
                    throw new HttpResponseException(statusLine.getStatusCode(),
                            statusLine.getReasonPhrase());
                }

                HttpEntity entity = response.getEntity();
                if (entity == null) {
                    return null;
                }
                String charSet = EntityUtils.getContentCharSet(entity);
                String responseData = EntityUtils.toString(entity);
                return new String(responseData.getBytes(charSet), HTTP.UTF_8);
            }
        };
        String responseData;
        try {
            responseData = httpclient.execute(httpPost, responseHandler);
        } finally {
            httpclient.getConnectionManager().shutdown();
        }
        return responseData;
    }
}
