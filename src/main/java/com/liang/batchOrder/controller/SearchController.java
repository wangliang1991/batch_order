package com.liang.batchOrder.controller;

import com.liang.batchOrder.bean.RequestBean;
import com.liang.batchOrder.bean.SearchBean;
import com.liang.batchOrder.bean.SearchRequest;
import com.liang.batchOrder.bean.SearchResponse;
import com.liang.batchOrder.constants.CookieConstant;
import com.liang.batchOrder.constants.UrlConstants;
import com.liang.batchOrder.service.FlightSearchService;
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
    private FlightSearchService flightSearchService;

    @RequestMapping("/search")
    public ModelAndView search(SearchRequest searchRequest) {
        ModelAndView modelAndView = new ModelAndView("searchResult");
        modelAndView.addObject("searchResult", searchRequest);
        try{
            SearchResponse searchResponse = flightSearchService.search(searchRequest);
            modelAndView.addObject("searchResponse", searchResponse);
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return modelAndView;
    }
}
