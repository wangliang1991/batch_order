package com.liang.batchOrder.service;

import com.liang.batchOrder.bean.RequestBean;
import com.liang.batchOrder.util.CookieUtil;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.*;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by wangliang.wang on 2018/4/17.
 */
@Service
public class HttpService {

    private static final  OkHttpClient HTTP_CLIENT = new OkHttpClient()
            .newBuilder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .connectionPool(new ConnectionPool(60, 5, TimeUnit.MINUTES))
            .followRedirects(false)  //禁制OkHttp的重定向操作，我们自己处理重定向
            .followSslRedirects(false)
            .build();

    /**
     * 根据路径获取验证码图片
     *
     * @param urlStr 图片路径地址
     * @param responseCookieKey 图片需要设置的cookie
     */
    public File getImage(String urlStr, String responseCookieKey) {
        if(StringUtils.isBlank(urlStr)) {
            return null;
        }

        try {
            URL url = new URL(urlStr);
            //打开链接
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();

            //设置请求方式为"GET"
            conn.setRequestMethod("GET");
            //超时响应时间为5秒
            conn.setConnectTimeout(5 * 1000);

            String cookie = conn.getHeaderField("set-cookie");
            CookieUtil.setCookie(responseCookieKey, cookie.substring(0, cookie.indexOf(';')));

            //通过输入流获取图片数据
            InputStream inStream = conn.getInputStream();
            //得到图片的二进制数据，以二进制封装得到数据，具有通用性
            byte[] data = readInputStream(inStream);
            //new一个文件对象用来保存图片，默认保存当前工程根目录
            String path = "D://" + UUID.randomUUID().toString() + "code.jpg";
            File imageFile = new File(path);
            //创建输出流
            FileOutputStream outStream = new FileOutputStream(imageFile);
            //写入数据
            outStream.write(data);
            //关闭输出流
            outStream.close();

            return imageFile;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    /**
     * 发起get请求
     *
     * @param requestBean 请求bean
     */
    public Response get(RequestBean requestBean) {
        if(requestBean == null || StringUtils.isBlank(requestBean.getUrl())) {
            return null;
        }

        Response response;
        try {
            String urlWithParams = buildUrl(requestBean.getUrl(), requestBean.getParamMap());
            Request.Builder requestBuilder = new Request.Builder()
                    .url(urlWithParams)
                    .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.117 Safari/537.36")
                    .addHeader("Upgrade-Insecure-Requests", "1")
                    .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                    .addHeader("Connection", "keep-alive")
                    .addHeader("Accept-Language", "zh-CN,zh;q=0.9");
            if(StringUtils.isNotBlank(requestBean.getRequestCookieKey())) {
                requestBuilder.addHeader("Cookie", CookieUtil.getCookie(requestBean.getRequestCookieKey()));
            }

            if(StringUtils.isNotBlank(requestBean.getRefer())) {
                requestBuilder.addHeader("Referer", requestBean.getRefer());
            }
            response = HTTP_CLIENT.newCall(requestBuilder.build()).execute();
            if(StringUtils.isNotBlank(requestBean.getResponseCookieKey())) {
                String cookie = response.header("set-cookie");
                CookieUtil.setCookie(requestBean.getResponseCookieKey(), cookie.substring(0, cookie.indexOf(';')));
            }

            return response;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    /**
     * 发起post请求
     *
     * @param requestBean 请求bean
     */
    public Response post(RequestBean requestBean) {
        if(requestBean == null || StringUtils.isBlank(requestBean.getUrl())) {
            return null;
        }

        try {
            RequestBody body = buildRequestBody(requestBean.getParamMap());
            return post(requestBean, body);
        } catch (Exception e) {

        }

        return null;
    }

    /**
     * 发起post请求
     *
     * @param requestBean 请求bean
     */
    public Response post(RequestBean requestBean, RequestBody requestBody) {
        if(requestBean == null || StringUtils.isBlank(requestBean.getUrl()) || requestBody == null) {
            return null;
        }

        try {
            Request.Builder requestBuilder = new Request.Builder()
                    .url(requestBean.getUrl())
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.117 Safari/537.36")
                    .addHeader("Upgrade-Insecure-Requests", "1")
                    .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                    .addHeader("Referer",requestBean.getRefer())
                    .addHeader("Connection", "keep-alive")
                    .addHeader("Accept-Encoding","deflate")
                    .addHeader("Accept-Language", "zh-CN,zh;q=0.9")
                    .post(requestBody);

            if(StringUtils.isNotBlank(requestBean.getRequestCookieKey())) {
                requestBuilder.addHeader("Cookie", CookieUtil.getCookie(requestBean.getRequestCookieKey()));
            }
            Response response = HTTP_CLIENT.newCall(requestBuilder.build()).execute();
            if(StringUtils.isNotBlank(requestBean.getResponseCookieKey())) {
                String cookie = response.header("set-cookie");
                CookieUtil.setCookie(requestBean.getResponseCookieKey(), cookie.substring(0, cookie.indexOf(';')));
            }
            return response;
        } catch (Exception e) {

        }

        return null;
    }

    private String buildUrl(String url, Map<String, String> paramMap) {
        StringBuilder urlWithParams = new StringBuilder(url);

        if(paramMap != null && !paramMap.isEmpty()) {
            urlWithParams.append("?");
            for (Map.Entry<String, String> entry : paramMap.entrySet()) {
                urlWithParams.append(entry.getKey())
                        .append("=")
                        .append(entry.getValue())
                        .append("&");
            }
        }

        return urlWithParams.toString();
    }

    private RequestBody buildRequestBody(Map<String, String> paramMap) {
        FormBody.Builder formBody = new FormBody.Builder();

        if(paramMap != null && !paramMap.isEmpty()) {
            for (Map.Entry<String, String> entry : paramMap.entrySet()) {
                formBody.add(entry.getKey(), entry.getValue());
            }
        }

        return formBody.build();
    }

    private byte[] readInputStream(InputStream inStream) throws Exception{
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        //创建一个Buffer字符串
        byte[] buffer = new byte[1024];
        //每次读取的字符串长度，如果为-1，代表全部读取完毕
        int len = 0;
        //使用一个输入流从buffer里把数据读取出来
        while( (len=inStream.read(buffer)) != -1 ){
            //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
            outStream.write(buffer, 0, len);
        }
        //关闭输入流
        inStream.close();
        //把outStream里的数据写入内存
        return outStream.toByteArray();
    }

}
