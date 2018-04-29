package com.liang.batchOrder.service;

import com.liang.batchOrder.bean.OrderNeedFromFront;
import com.liang.batchOrder.bean.OrderNeedFromSearch;
import com.liang.batchOrder.bean.RequestBean;
import com.liang.batchOrder.constants.CookieConstant;
import com.liang.batchOrder.util.CookieUtil;
import okhttp3.*;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpService.class);
    private static final OkHttpClient HTTP_CLIENT = new OkHttpClient()
            .newBuilder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .connectionPool(new ConnectionPool(60, 5, TimeUnit.SECONDS))
            .followRedirects(false)  //禁制OkHttp的重定向操作，我们自己处理重定向
            .followSslRedirects(false)
//            .proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("localhost", 8888)))
            .build();

    /**
     * 根据路径获取验证码图片
     *
     * @param urlStr            图片路径地址
     * @param requestCookieKey 请求需要的cookie
     * @param responseCookieKey 图片需要设置的cookie
     */
    public byte[] getImage(String urlStr, String requestCookieKey, String responseCookieKey) {
        if (StringUtils.isBlank(urlStr)) {
            return null;
        }

        try {
            URL url = new URL(urlStr);
            //打开链接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            if(StringUtils.isNotBlank(requestCookieKey)) {
                conn.setRequestProperty("Cookie", CookieUtil.getCookie(requestCookieKey));
            }


            //设置请求方式为"GET"
            conn.setRequestMethod("GET");
            //超时响应时间为5秒
            conn.setConnectTimeout(5 * 1000);

            if(StringUtils.isNotBlank(responseCookieKey)) {
                String cookie = conn.getHeaderField("set-cookie");
                CookieUtil.setCookie(responseCookieKey, cookie.substring(0, cookie.indexOf(';')));
            }

            //通过输入流获取图片数据
            InputStream inStream = conn.getInputStream();
            //得到图片的二进制数据，以二进制封装得到数据，具有通用性
            byte[] data = readInputStream(inStream);

            return data;
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
        if (requestBean == null || StringUtils.isBlank(requestBean.getUrl())) {
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
            if (StringUtils.isNotBlank(requestBean.getRequestCookieKey())) {
                requestBuilder.addHeader("Cookie", CookieUtil.getCookie(requestBean.getRequestCookieKey()));
            }

            if (StringUtils.isNotBlank(requestBean.getRefer())) {
                requestBuilder.addHeader("Referer", requestBean.getRefer());
            }
            response = HTTP_CLIENT.newCall(requestBuilder.build()).execute();
            if (StringUtils.isNotBlank(requestBean.getResponseCookieKey())) {
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
        if (requestBean == null || StringUtils.isBlank(requestBean.getUrl())) {
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
        if (requestBean == null || StringUtils.isBlank(requestBean.getUrl()) || requestBody == null) {
            return null;
        }

        try {
            Request.Builder requestBuilder = new Request.Builder()
                    .url(requestBean.getUrl())
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.117 Safari/537.36")
                    .addHeader("Upgrade-Insecure-Requests", "1")
                    .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                    .addHeader("Referer", requestBean.getRefer())
                    .addHeader("Connection", "keep-alive")
                    .addHeader("Accept-Encoding", "deflate")
                    .addHeader("Accept-Language", "zh-CN,zh;q=0.9")
                    .post(requestBody);

            if (StringUtils.isNotBlank(requestBean.getRequestCookieKey())) {
                requestBuilder.addHeader("Cookie", CookieUtil.getCookie(requestBean.getRequestCookieKey()));
            }
            Response response = HTTP_CLIENT.newCall(requestBuilder.build()).execute();
            if (StringUtils.isNotBlank(requestBean.getResponseCookieKey())) {
                String cookie = response.header("set-cookie");
                CookieUtil.setCookie(requestBean.getResponseCookieKey(), cookie.substring(0, cookie.indexOf(';')));
            }
            return response;
        } catch (Exception e) {

        }

        return null;
    }

    /**
     * 异步post请求
     */
    public void postAsync(OrderNeedFromFront frontBean, OrderNeedFromSearch searchBean, String goDate, String backDate) {
        if (frontBean == null || searchBean == null || StringUtils.isBlank(goDate) || StringUtils.isBlank(backDate)) {
            return;
        }
        FormBody.Builder formBody = new FormBody.Builder();
        formBody.add("policyId", searchBean.getPolicyId());
        formBody.add("isJjj", "0");
        formBody.add("needPriceCheck", "8LGS");
        formBody.add("logo", "group");
        formBody.add("lineStr", "TSN-DLC/DLC-TSN");
        formBody.add("insurerFlightno", "ALL");
        formBody.add("hotelFlightno", "");
        formBody.add("hotelPrice", "");
        formBody.add("org_cityadded", "TSN");
        formBody.add("dst_cityadded", "DLC");
        formBody.add("flightnoadded", frontBean.getFirstFlightName());
        formBody.add("depdateadded", goDate);
        formBody.add("cabinadded", searchBean.getCabin());
        formBody.add("priceadded", searchBean.getFirstFlightPrice());
        formBody.add("segmentpolicyId", "");
        formBody.add("insurer", "30.0");
        formBody.add("org_cityadded", "DLC");
        formBody.add("dst_cityadded", "TSN");
        formBody.add("flightnoadded", frontBean.getSecondFlightName());
        formBody.add("depdateadded", backDate);
        formBody.add("cabinadded", searchBean.getCabin());
        formBody.add("priceadded", searchBean.getSecondFlightPrice());
        formBody.add("segmentpolicyId", "");
        formBody.add(searchBean.getCode().getK(), searchBean.getCode().getV());
        formBody.add("seatnum", frontBean.getSeatNum());
        formBody.add("checkForeignGroup", "0");
        formBody.add("tel", "022-84349681");
        formBody.add("mobile", frontBean.getMobile());
        formBody.add("sendsms", "yes");
        formBody.add("remark", "\t");
        formBody.add("myDept", "0");
        formBody.add("content", "yes");

        Request.Builder requestBuilder = new Request.Builder()
                .url("http://gt.hnair.com/gt/order/frontend/submitorder/submitOrder.do?logo=group")
                .addHeader("Host", "gt.hnair.com")
                .addHeader("Origin", "http://gt.hnair.com")
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.117 Safari/537.36")
                .addHeader("Upgrade-Insecure-Requests", "1")
                .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                .addHeader("Referer", "http://gt.hnair.com/gt/backend/sun/searchPrice.do")
                .addHeader("Connection", "keep-alive")
                .addHeader("Accept-Encoding", "deflate")
                .addHeader("Accept-Language", "zh-CN,zh;q=0.9")
                .addHeader("Cookie", CookieUtil.getCookie(CookieConstant.LOGIN_SESSION_COOKIE_KEY))
                .post(formBody.build());
        HTTP_CLIENT.newCall(requestBuilder.build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LOGGER.error("ioException",e);
            }

            @Override
            public void onResponse(Call call, Response response) {
                try (ResponseBody body = response.body()) {
                    if (body != null) {
                        body.string();

                        LOGGER.info("提交成功");
                    }
                } catch (Exception e) {

                }

            }
        });
    }

    private String buildUrl(String url, Map<String, String> paramMap) {
        StringBuilder urlWithParams = new StringBuilder(url);

        if (paramMap != null && !paramMap.isEmpty()) {
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

        if (paramMap != null && !paramMap.isEmpty()) {
            for (Map.Entry<String, String> entry : paramMap.entrySet()) {
                formBody.add(entry.getKey(), entry.getValue());
            }
        }

        return formBody.build();
    }

    private byte[] readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        //创建一个Buffer字符串
        byte[] buffer = new byte[1024];
        //每次读取的字符串长度，如果为-1，代表全部读取完毕
        int len = 0;
        //使用一个输入流从buffer里把数据读取出来
        while ((len = inStream.read(buffer)) != -1) {
            //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
            outStream.write(buffer, 0, len);
        }
        //关闭输入流
        inStream.close();
        //把outStream里的数据写入内存
        return outStream.toByteArray();
    }

}
