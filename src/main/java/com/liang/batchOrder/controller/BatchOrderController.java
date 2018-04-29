package com.liang.batchOrder.controller;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.RateLimiter;
import com.liang.batchOrder.bean.OrderNeedFromFront;
import com.liang.batchOrder.bean.OrderNeedFromSearch;
import com.liang.batchOrder.bean.SearchBean;
import com.liang.batchOrder.bean.SearchRequest;
import com.liang.batchOrder.bean.Tuple;
import com.liang.batchOrder.service.FlightSearchService;
import com.liang.batchOrder.service.HttpService;
import com.liang.batchOrder.util.DateTimeUtil;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by wangliang.wang on 2018/4/23.
 */
@Controller
public class BatchOrderController {
    @Resource
    private FlightSearchService flightSearchService;
    @Resource
    private HttpService httpService;

    @RequestMapping("/batchOrder")
    public void search(final OrderNeedFromFront frontBean) {
        String goDate = frontBean.getGoDate();
        String backDate = frontBean.getBackDate();
        final RateLimiter limiter = RateLimiter.create(2, 10, TimeUnit.MILLISECONDS);
        for (int i = 0; i < frontBean.getDays(); i++) {
            SearchRequest searchRequest = new SearchRequest();
            List<SearchBean> searchBeanList = Lists.newArrayList(
                    new SearchBean("天津", "大连", goDate),
                    new SearchBean("大连", "天津", backDate));
            searchRequest.setSearchBeanList(searchBeanList);
            final OrderNeedFromSearch searchBean = flightSearchService.searchCode(searchRequest);
            final String finalGoDate = goDate;
            final String finalBackDate = backDate;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    httpService.postAsync(limiter,frontBean, searchBean, finalGoDate, finalBackDate);
                }
            }).start();
            goDate = DateTimeUtil.addDayByNum(goDate, 1);
            backDate = DateTimeUtil.addDayByNum(backDate, 1);
        }
    }
}
