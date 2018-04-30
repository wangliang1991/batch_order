package com.liang.batchOrder.controller;

import com.liang.batchOrder.bean.SearchRequest;
import com.liang.batchOrder.bean.SearchResponse;
import com.liang.batchOrder.service.FlightSearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * Created by wangliang.wang on 2018/4/23.
 */
@Controller
public class SearchController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SearchController.class);

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
            LOGGER.error("search error", e);
        }

        return modelAndView;
    }
}
