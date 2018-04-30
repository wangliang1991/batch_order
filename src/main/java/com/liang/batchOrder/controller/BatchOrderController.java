package com.liang.batchOrder.controller;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.RateLimiter;
import com.liang.batchOrder.bean.OrderNeedFromFront;
import com.liang.batchOrder.bean.OrderNeedFromSearch;
import com.liang.batchOrder.bean.SearchBean;
import com.liang.batchOrder.bean.SearchRequest;
import com.liang.batchOrder.service.FlightSearchService;
import com.liang.batchOrder.service.HttpService;
import com.liang.batchOrder.util.DateTimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by wangliang.wang on 2018/4/23.
 */
@Controller
public class BatchOrderController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BatchOrderController.class);

    @Resource
    private FlightSearchService flightSearchService;
    @Resource
    private HttpService httpService;

    @RequestMapping("/batchOrder")
    public ModelAndView search(final OrderNeedFromFront frontBean) {
        ModelAndView modelAndView = new ModelAndView("orderResult");
        String goDate = frontBean.getGoDate();
        String backDate = frontBean.getBackDate();

        //测试发现两秒提交一次
        final RateLimiter limiter = RateLimiter.create(0.5, 10, TimeUnit.MILLISECONDS);

        for (int i = 0; i < frontBean.getDays(); i++) {
            try {
                final SearchRequest searchRequest = new SearchRequest();
                List<SearchBean> searchBeanList = Lists.newArrayList(
                        new SearchBean("天津", "大连", goDate),
                        new SearchBean("大连", "天津", backDate));
                searchRequest.setSearchBeanList(searchBeanList);
                final String finalGoDate = goDate;
                final String finalBackDate = backDate;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final OrderNeedFromSearch searchBean = flightSearchService.searchForOrder(searchRequest);
                        httpService.postAsync(limiter,frontBean, searchBean, finalGoDate, finalBackDate);
                    }
                }).start();
                goDate = DateTimeUtil.addDayByNum(goDate, 1);
                backDate = DateTimeUtil.addDayByNum(backDate, 1);
            } catch (Exception e) {
                LOGGER.error("batch order error", e);
            }

        }

        return modelAndView;
    }
}
