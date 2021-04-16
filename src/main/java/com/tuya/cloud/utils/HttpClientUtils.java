package com.tuya.cloud.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.Map;

@Slf4j
public class HttpClientUtils {
    private static PoolingHttpClientConnectionManager cm;
    private static final String EMPTY_STR = "";
    private static final String UTF_8 = "UTF-8";
    private static final Integer MAX_TOTAL = 50;
    private static final Integer MAX_PER_ROUTE = 5;

    private static void init() {
        if (cm == null) {
            cm = new PoolingHttpClientConnectionManager();
            // 整个连接池最大连接数
            cm.setMaxTotal(MAX_TOTAL);
            // 每路由最大连接数，默认值是2
            cm.setDefaultMaxPerRoute(MAX_PER_ROUTE);
        }
    }

    /**
     * @description: 通过连接池获取HttpClient
     * @return: httpClient
     * @author: 布鲁 龙盼盼 
     * @date: 2021/4/9
     */
    private static CloseableHttpClient getHttpClient() {
        init();
        return HttpClients.custom().setConnectionManager(cm).build();
    }

    /**
     * @param: url
     * @param: headers
     * @param: params
     * @description: http get请求
     * @return: java.lang.String
     * @author: 布鲁 龙盼盼 
     * @date: 2021/4/9
     */
    public static String get(String url, Map<String, Object> headers, Map<String, String> params) throws Exception {
        URIBuilder ub = new URIBuilder();
        ub.setPath(url);

        if (params != null) {
            ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
            ub.setParameters(pairs);
        }

        HttpGet httpGet = new HttpGet(ub.build());
        if (headers != null) {
            for (Map.Entry<String, Object> param : headers.entrySet()) {
                httpGet.addHeader(param.getKey(), String.valueOf(param.getValue()));
            }
        }
        return getResult(httpGet);
    }

    /**
     * @param: url
     * @param: headers
     * @param: content
     * @description: http post请求
     * @return: java.lang.String
     * @author: 布鲁 龙盼盼 
     * @date: 2021/4/9
     */
    public static String post(String url, Map<String, Object> headers, String content) throws Exception {
        HttpPost httpPost = new HttpPost(url);

        if (headers != null) {
            for (Map.Entry<String, Object> header : headers.entrySet()) {
                httpPost.addHeader(header.getKey(), String.valueOf(header.getValue()));
            }
        }

        if (content != null) {
            httpPost.setEntity(new StringEntity(content, ContentType.APPLICATION_JSON));
        }
        return getResult(httpPost);
    }

    /**
     * @param: params
     * @description: 转化参数
     * @return: java.util.ArrayList<org.apache.http.NameValuePair>
     * @author: 布鲁 龙盼盼 
     * @date: 2021/4/9
     */
    private static ArrayList<NameValuePair> covertParams2NVPS(Map<String, String> params) {
        ArrayList<NameValuePair> pairs = new ArrayList<>();
        for (Map.Entry<String, String> param : params.entrySet()) {
            if (param.getValue() != null) {
                pairs.add(new BasicNameValuePair(param.getKey(), param.getValue()));
            }
        }

        return pairs;
    }

    /**
     * @param: request
     * @description: 处理Http请求
     * @return: java.lang.String
     * @author: 布鲁 龙盼盼 
     * @date: 2021/4/9
     */
    private static String getResult(HttpRequestBase request) throws Exception {
        // CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpClient httpClient = getHttpClient();
        CloseableHttpResponse response = httpClient.execute(request);
        // response.getStatusLine().getStatusCode();
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            // long len = entity.getContentLength();// -1 表示长度未知
            return EntityUtils.toString(entity, UTF_8);
        }

        return EMPTY_STR;
    }

    /**
     * @param: url
     * @param: headers
     * @description: http put 请求
     * @return: java.lang.String
     * @author: 布鲁 龙盼盼 
     * @date: 2021/4/9
     */
    public static String put(String url, Map<String, Object> headers) throws Exception {
        return put(url, headers, null);
    }

    /**
     * @param: url
     * @param: headers
     * @param: content
     * @description: http put 请求
     * @return: java.lang.String
     * @author: 布鲁 龙盼盼 
     * @date: 2021/4/9
     */
    public static String put(String url, Map<String, Object> headers, String content) throws Exception {
        HttpPut httpPut = new HttpPut(url);

        if (headers != null) {
            for (Map.Entry<String, Object> header : headers.entrySet()) {
                httpPut.addHeader(header.getKey(), String.valueOf(header.getValue()));
            }
        }

        if (content != null) {
            httpPut.setEntity(new StringEntity(content, ContentType.APPLICATION_JSON));
        }
        return getResult(httpPut);
    }

    /**
     * @param: url
     * @param: headers
     * @description: 发送delete请求
     * @return: java.lang.String
     * @author: 布鲁 龙盼盼 
     * @date: 2021/4/9
     */
    public static String delete(String url, Map<String, Object> headers) throws Exception {

        HttpDelete httpDelete = new HttpDelete(url);
        if (headers != null) {
            for (Map.Entry<String, Object> header : headers.entrySet()) {
                httpDelete.addHeader(header.getKey(), String.valueOf(header.getValue()));
            }
        }
        return getResult(httpDelete);
    }
}
