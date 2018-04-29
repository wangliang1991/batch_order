package com.liang.batchOrder.bean;

import java.util.Map;

/**
 * Created by wangliang.wang on 2018/4/23.
 */
public class RequestBean {

    //请求地址
    private String url;
    //参数
    private Map<String, String> paramMap;
    //请求携带cookie对应的key
    private String requestCookieKey;
    //返回需要设置cookie对应的key
    private String responseCookieKey;
    //上一个链接页面的url
    private String refer;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, String> getParamMap() {
        return paramMap;
    }

    public void setParamMap(Map<String, String> paramMap) {
        this.paramMap = paramMap;
    }

    public String getRequestCookieKey() {
        return requestCookieKey;
    }

    public void setRequestCookieKey(String requestCookieKey) {
        this.requestCookieKey = requestCookieKey;
    }

    public String getResponseCookieKey() {
        return responseCookieKey;
    }

    public void setResponseCookieKey(String responseCookieKey) {
        this.responseCookieKey = responseCookieKey;
    }

    public String getRefer() {
        return refer;
    }

    public void setRefer(String refer) {
        this.refer = refer;
    }
}
