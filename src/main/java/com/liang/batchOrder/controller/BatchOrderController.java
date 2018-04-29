package com.liang.batchOrder.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.liang.batchOrder.bean.RequestBean;
import com.liang.batchOrder.bean.SearchBean;
import com.liang.batchOrder.bean.SearchRequest;
import com.liang.batchOrder.bean.SubmitOrderBean;
import com.liang.batchOrder.bean.Tuple;
import com.liang.batchOrder.constants.CookieConstant;
import com.liang.batchOrder.constants.UrlConstants;
import com.liang.batchOrder.service.CodeCrackService;
import com.liang.batchOrder.service.HttpService;
import com.liang.batchOrder.util.DateTimeUtil;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.apache.commons.collections.CollectionUtils;
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
    public String search(SubmitOrderBean bean) {
//        String depDate = bean.getDepdateadded();
//        String arrDate = bean.getDepdateadded();
//
//        for (int i = 0; i < bean.getDays(); i++) {
//            SearchRequest searchRequest = new SearchRequest();
//            List<SearchBean> searchBeanList = Lists.newArrayList(
//                    new SearchBean("天津", "大连", depDate),
//                    new SearchBean("大连", "天津", arrDate));
//            searchRequest.setSearchBeanList(searchBeanList);
//            Tuple<String, String> codeTuple = CodeCrackService.get(searchRequest);
//
//            httpService.postAsync();
//
//            depDate = DateTimeUtil.addDayByNum(depDate, 1);
//            arrDate = DateTimeUtil.addDayByNum(arrDate, 1);
//        }

        return null;
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
