package com.liang.batchOrder.controller;

import com.google.common.collect.Maps;
import com.liang.batchOrder.bean.RequestBean;
import com.liang.batchOrder.bean.SearchBean;
import com.liang.batchOrder.constants.CookieConstant;
import com.liang.batchOrder.constants.UrlConstants;
import com.liang.batchOrder.service.HttpService;
import okhttp3.Response;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * Created by wangliang.wang on 2018/4/23.
 */
@Controller
public class BatchOrderController {

    @Resource
    private HttpService httpService;

    @RequestMapping("/batchOrder")
    public String search(List<SearchBean> searchBeanList, int batchCount) {
        for (int i = 0; i < batchCount; i++) {
            //todo change date
            Response response = httpService.post(buildRequestBean(searchBeanList));
            if(response.isSuccessful()) {
                doBatchOrder(response, batchCount);
            }
        }

        return null;
    }

    private void doBatchOrder(Response response, int batchCount) {

    }

    private RequestBean buildRequestBean(List<SearchBean> searchBeanList) {
        RequestBean requestBean = new RequestBean();
        requestBean.setUrl(UrlConstants.SEARCH_URL);
        requestBean.setParamMap(buildParamMap(searchBeanList));
        requestBean.setRequestCookieKey(CookieConstant.LOGIN_SESSION_COOKIE_KEY);
        requestBean.setRefer(UrlConstants.SEARCH_URL);

        return requestBean;
    }

    private Map<String, String> buildParamMap(List<SearchBean> searchBeanList) {
        Map<String, String> paramMap = Maps.newHashMap();
        paramMap.put("logo", "group");
        int size = searchBeanList.size();

        try {
            for (int i = 0; i < 5; i++) {
                if(i < size) {
                    SearchBean searchBean = searchBeanList.get(i);
                    paramMap.put("origins", URLEncoder.encode(searchBean.getDepCity(), "utf-8"));
                    paramMap.put("dests", URLEncoder.encode(searchBean.getDepCity(), "utf-8"));
                    paramMap.put("departs", searchBean.getDepDate());
                } else {
                    paramMap.put("origins", "");
                    paramMap.put("dests", "");
                    paramMap.put("departs", "");
                }
            }
        } catch (Exception e) {

        }

        return paramMap;
    }
}
