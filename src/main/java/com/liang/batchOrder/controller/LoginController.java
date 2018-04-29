package com.liang.batchOrder.controller;

import com.google.common.collect.Maps;
import com.liang.batchOrder.bean.RequestBean;
import com.liang.batchOrder.constants.CookieConstant;
import com.liang.batchOrder.constants.UrlConstants;
import com.liang.batchOrder.service.CodeCrackService;
import com.liang.batchOrder.service.HttpService;
import com.liang.batchOrder.util.HtmlUtil;
import okhttp3.Response;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by wangliang.wang on 2018/4/18.
 */
@Controller
public class LoginController {

    private static final int RETRY_LOGIN_TIMES = 3;

    @Resource
    private HttpService httpService;

    @Resource
    private CodeCrackService codeCrackService;

    @RequestMapping("/login")
    public ModelAndView login(String userName, String password) {
        ModelAndView modelAndView = new ModelAndView("search");
        boolean loginStatus = false;

        try {
//            System.setProperty("http.proxyHost", "127.0.0.1");
//            System.setProperty("https.proxyHost", "127.0.0.1");
//            System.setProperty("http.proxyPort", "8888");
//            System.setProperty("https.proxyPort", "8888");

            //进入登录页
            Response ret = httpService.get(buildLoginIndexRequest());
            String lt = HtmlUtil.getHtmlNode(ret.body().string(), "input[name=lt]");
            //获取验证码
            String code = getCode();


            String loginRet = null;
            //进行登录,重试,三次
            for (int i = 0; i< RETRY_LOGIN_TIMES; i++) {
                loginRet = doLogin(userName, password, lt, code);
                if(StringUtils.isNotBlank(loginRet) && loginRet.contains("successful")) {
                    loginStatus = true;
                    break;
                }
            }

            if(loginStatus) {
                String url = HtmlUtil.getIndexUrl(StringUtils.trim(loginRet));
                //进行跳转
                Response response = httpService.get(buildTransferRequest(url));
                if(response.isRedirect()) {
                    Response index = httpService.get(buildIndexRequest());
                    loginStatus = index.isSuccessful();
                }

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            loginStatus = false;
        } finally {
            modelAndView.addObject("loginStatus", loginStatus);
        }

        return modelAndView;
    }

    public String doLogin(String userName, String password, String lt, String code) {
        try {
            Response response =  httpService.post(buildLoginRequest(userName, password, lt, code));
            return response.body().string();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    private RequestBean buildLoginIndexRequest() {
        RequestBean requestBean = new RequestBean();
        requestBean.setUrl(UrlConstants.LOGIN_INDEX_URL);
        return requestBean;
    }

    private RequestBean buildLoginRequest(String userName, String password, String lt, String code) {
        RequestBean requestBean = new RequestBean();
        requestBean.setUrl(UrlConstants.LOGIN_INDEX_URL);
        requestBean.setParamMap(buildParamMap(userName, password, lt, code));
        requestBean.setRequestCookieKey(CookieConstant.LOGIN_CODE_COOKIE_KEY);
        requestBean.setRefer(UrlConstants.LOGIN_INDEX_URL);
        return requestBean;
    }

    private RequestBean buildTransferRequest(String url) {
        RequestBean requestBean = new RequestBean();
        requestBean.setUrl(url);
        requestBean.setResponseCookieKey(CookieConstant.LOGIN_SESSION_COOKIE_KEY);
        requestBean.setRefer(UrlConstants.LOGIN_INDEX_URL);
        return requestBean;
    }

    private RequestBean buildIndexRequest() {
        RequestBean requestBean = new RequestBean();
        requestBean.setUrl(UrlConstants.INDEX_URL);
        requestBean.setRequestCookieKey(CookieConstant.LOGIN_SESSION_COOKIE_KEY);
        requestBean.setRefer(UrlConstants.LOGIN_INDEX_URL);
        return requestBean;
    }

    private String getCode() {
        byte[] imageData = httpService.getImage(UrlConstants.VERIFICATION_URL, null, CookieConstant.LOGIN_CODE_COOKIE_KEY);
        String code = null;
        if(imageData != null) {
            code = codeCrackService.doBaiDuCracking(imageData);
        }

        return StringUtils.trim(code);
    }

    private Map<String, String> buildParamMap(String userName, String password, String lt, String code) {
        Map<String, String> paramMap = Maps.newHashMap();

        paramMap.put("username", userName);
        paramMap.put("password", DigestUtils.md5Hex(password));
        paramMap.put("lt", lt);
        paramMap.put("rand", code);

        return paramMap;
    }

}
