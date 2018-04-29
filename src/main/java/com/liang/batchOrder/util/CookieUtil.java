package com.liang.batchOrder.util;

import com.google.common.collect.Maps;
import com.liang.batchOrder.constants.CookieConstant;

import java.util.Map;
import java.util.Objects;

/**
 * Created by l on 18-4-21.
 */
public class CookieUtil {

    //线程安全的
    private static final Map<String, String> COOKIE_MAP = Maps.newConcurrentMap();

    public static void setCookie(String key, String value) {
        if(Objects.equals(CookieConstant.LOGIN_SESSION_COOKIE_KEY, key)) {
            value = "16=0;7=1;" + value;
        }
        COOKIE_MAP.put(key, value);
    }

    public static String getCookie(String key) {
        return COOKIE_MAP.get(key);
    }
}
