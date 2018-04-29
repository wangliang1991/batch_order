package com.liang.batchOrder.bean;

import java.util.List;

/**
 * Created by wangliang.wang on 2018/4/25.
 */
public class SearchRequest {

    private List<SearchBean> searchBeanList;

    public List<SearchBean> getSearchBeanList() {
        return searchBeanList;
    }

    public void setSearchBeanList(List<SearchBean> searchBeanList) {
        this.searchBeanList = searchBeanList;
    }
}
