package com.liang.batchOrder.service;

import com.liang.batchOrder.bean.*;
import com.liang.batchOrder.constants.CookieConstant;
import com.liang.batchOrder.constants.UrlConstants;
import com.liang.batchOrder.util.HtmlUtil;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by wangliang.wang on 2018/4/17.
 */
@Service
public class FlightSearchService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FlightSearchService.class);

    @Resource
    private HttpService httpService;

    @Resource
    private CodeCrackService codeCrackService;

    public OrderNeedFromSearch searchForOrder(SearchRequest searchRequest) {
        OrderNeedFromSearch orderNeedFromSearch = null;
        try{
            String retHtml = doSearch(searchRequest);
            String key = HtmlUtil.getCodeKey(retHtml);
            String code = getCode();
            SearchResponse searchResponse = HtmlUtil.getSearchResult(retHtml);
            orderNeedFromSearch = buildOrderNeedFromSearch(key, code, searchResponse);
        }catch (Exception e) {
            LOGGER.error("search for order error", e);
        }

        return orderNeedFromSearch;
    }

    public SearchResponse search(SearchRequest searchRequest) {
        SearchResponse searchResponse = null;

        try{
            searchResponse = HtmlUtil.getSearchResult(doSearch(searchRequest));
        }catch (Exception e) {
            LOGGER.error("search for front error", e);
        }

        return searchResponse;
    }

    private String doSearch(SearchRequest searchRequest) {
        RequestBean requestBean = buildRequestBean();
        String retHtml = null;

        try{
            Response response = httpService.post(requestBean, buildParamRequest(searchRequest.getSearchBeanList()));
            retHtml = response.body().string();

            //LOGGER.info("search ret:{}", retHtml);
        }catch (Exception e) {
            LOGGER.error("do search error", e);
        }

        return retHtml;
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

    private String getCode() {
        byte[] imageData = httpService.getImage(UrlConstants.SEARCH_VERIFICATION_URL, CookieConstant.LOGIN_SESSION_COOKIE_KEY, null);
        String code = null;
        if(imageData != null) {
            code = codeCrackService.doBaiDuCracking(imageData);
        }

        return StringUtils.trim(code);
    }

    private OrderNeedFromSearch buildOrderNeedFromSearch(String key, String code, SearchResponse searchResponse) {
        OrderNeedFromSearch orderNeedFromSearch = new OrderNeedFromSearch();
        orderNeedFromSearch.setCode(new Tuple<>(key, code));
        ReferenceValueBean referenceValueBean = searchResponse.getReferencePriceBean().getReferenceValueBean();
        orderNeedFromSearch.setCabin(referenceValueBean.getCabin());
        orderNeedFromSearch.setPolicyId(String.valueOf(referenceValueBean.getId()));
        String prices = referenceValueBean.getPrices();
        orderNeedFromSearch.setFirstFlightPrice(prices.substring(0,prices.indexOf('/')));
        orderNeedFromSearch.setSecondFlightPrice(prices.substring(prices.indexOf('/') + 1));
        return orderNeedFromSearch;
    }
}
