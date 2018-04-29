package com.liang.batchOrder.controller;

import com.liang.batchOrder.bean.RequestBean;
import com.liang.batchOrder.bean.SearchBean;
import com.liang.batchOrder.bean.SearchRequest;
import com.liang.batchOrder.bean.SearchResponse;
import com.liang.batchOrder.constants.CookieConstant;
import com.liang.batchOrder.constants.UrlConstants;
import com.liang.batchOrder.service.HttpService;
import com.liang.batchOrder.util.HtmlUtil;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by wangliang.wang on 2018/4/23.
 */
@Controller
public class SearchController {

    @Resource
    private HttpService httpService;

    @RequestMapping("/search")
    public ModelAndView search(SearchRequest searchRequest) {
        ModelAndView modelAndView = new ModelAndView("searchResult");
        RequestBean requestBean = buildRequestBean();
        try{
            Response response = httpService.post(requestBean, buildParamRequest(searchRequest.getSearchBeanList()));
            String retHtml = response.body().string();
            SearchResponse searchResponse = HtmlUtil.getSearchResult(retHtml);
            modelAndView.addObject("searchResponse", searchResponse);
            System.out.println(retHtml);
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return modelAndView;
    }

    private RequestBean buildRequestBean() {
        RequestBean requestBean = new RequestBean();
        requestBean.setUrl(UrlConstants.SEARCH_URL);
        requestBean.setRequestCookieKey(CookieConstant.LOGIN_SESSION_COOKIE_KEY);
        requestBean.setRefer(UrlConstants.SEARCH_URL);

        return requestBean;
    }

    private RequestBody buildParamRequest(List<SearchBean> searchBeanList) {
        FormBody.Builder formBody = new FormBody.Builder();

        if(CollectionUtils.isNotEmpty(searchBeanList)) {
            formBody.add("logo", "group");
            int size = searchBeanList.size();

            try {
                for (int i = 0; i < 5; i++) {
                    if(i < size) {
                        SearchBean searchBean = searchBeanList.get(i);
                        formBody.add("origins", searchBean.getDepCity());
                        formBody.add("dests", searchBean.getArrCity());
                        formBody.add("departs", searchBean.getDepDate());
                    } else {
                        formBody.add("origins", "");
                        formBody.add("dests", "");
                        formBody.add("departs", "");
                    }
                }
            } catch (Exception e) {

            }
        }

        return formBody.build();


    }
}
