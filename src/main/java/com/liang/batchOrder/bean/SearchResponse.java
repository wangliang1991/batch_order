package com.liang.batchOrder.bean;

import java.util.List;

/**
 * Created by wangliang.wang on 2018/4/27.
 */
public class SearchResponse {

    private List<SearchResultBean> firstSearchResultBeanList;
    private List<SearchResultBean> secondSearchResultBeanList;
    private ReferencePriceBean referencePriceBean;

    public List<SearchResultBean> getFirstSearchResultBeanList() {
        return firstSearchResultBeanList;
    }

    public void setFirstSearchResultBeanList(List<SearchResultBean> firstSearchResultBeanList) {
        this.firstSearchResultBeanList = firstSearchResultBeanList;
    }

    public List<SearchResultBean> getSecondSearchResultBeanList() {
        return secondSearchResultBeanList;
    }

    public void setSecondSearchResultBeanList(List<SearchResultBean> secondSearchResultBeanList) {
        this.secondSearchResultBeanList = secondSearchResultBeanList;
    }

    public ReferencePriceBean getReferencePriceBean() {
        return referencePriceBean;
    }

    public void setReferencePriceBean(ReferencePriceBean referencePriceBean) {
        this.referencePriceBean = referencePriceBean;
    }
}
