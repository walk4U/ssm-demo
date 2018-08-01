package com.jia.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: jia
 * @Date: 2018/8/01 16:55
 * @Description: http工具类
 */
public class HttpUtil {

    private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    private static Integer connectionRequestTimeout = 3000;

    private static Integer socketTimeOut = 3000;

    private static Integer connectTimeout = 3000;

    /**
     * 模拟Get 请求
     * @param url
     * @param parameters
     * @return
     */
    public static String doGet(String url, Map<String, String> parameters){
        StringBuffer sb = new StringBuffer();// 存储参数
        String params;// 编码之后的参数
        CloseableHttpResponse response = null;
        try {
            // 编码请求参数
            if(parameters.size()==1){
                for(String name : parameters.keySet()){
                    sb.append(name).append("=").append(URLEncoder.encode(parameters.get(name), "UTF-8"));
                }
                params=sb.toString();
            } else {
                for (String name : parameters.keySet()) {
                    sb.append(name).append("=").append(URLEncoder.encode(parameters.get(name),"UTF-8")).append("&");
                }
                String temp_params = sb.toString();
                params = temp_params.substring(0, temp_params.length() - 1);
            }
            String full_url = url + "?" + params;

            //单位毫秒
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectionRequestTimeout(connectionRequestTimeout).setConnectTimeout(connectTimeout)
                    .setSocketTimeout(socketTimeOut).build();
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(full_url);
            httpGet.setConfig(requestConfig);

            //发送http请求
            response = httpclient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                return null;
            } else {
                String result = EntityUtils.toString(response.getEntity(), "UTF-8");
                return result;
            }
        } catch (Exception e) {
            logger.error("http doGet exception -- > " + e);
            return null;
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    logger.error("IOException exception -- > " + e);
                }
            }
        }

    }

    /**
     * 模拟post请求
     * @param url
     * @param jsonContent
     * @return
     */
    public static String doPost(String url, String jsonContent){
        CloseableHttpResponse response = null;
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(url);
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(socketTimeOut)
                .setConnectTimeout(connectTimeout).build();
        httpPost.setConfig(requestConfig);
        httpPost.addHeader("Content-Type", "application/json");
        StringEntity requestEntity = new StringEntity(jsonContent, "utf-8");
        httpPost.setEntity(requestEntity);
        try {
            response = httpClient.execute(httpPost, new BasicHttpContext());
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                return null;
            }
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String resultStr = EntityUtils.toString(entity, "utf-8");
                return resultStr;
            } else {
                return null;
            }
        } catch (Exception e) {
            logger.error("http doPost exception -- > " + e);
            return null;
        } finally {
            if (response != null)
                try {
                response.close();
            } catch (IOException e) {
                logger.error("IOException exception -- > " + e);
            }
        }
    }

    public static void main(String[] args) {
        Map<String, String> parameters = new HashMap();
        parameters.put("age", "22");
        parameters.put("pageNum", "1");
        parameters.put("pageSize", "2");
        String result =doGet("http://localhost:8080/user/list", parameters);
        System.out.println(result);
    }
}
