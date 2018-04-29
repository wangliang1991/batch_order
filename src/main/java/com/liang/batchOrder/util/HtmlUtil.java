package com.liang.batchOrder.util;

import com.google.common.collect.Lists;
import com.liang.batchOrder.bean.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;

/**
 * Created by wangliang.wang on 2018/4/17.
 */
public class HtmlUtil {

    public static String getHtmlNode(String html, String select) {
        Document doc = Jsoup.parse(html);
        Element element = doc.selectFirst(select);

        return element == null ? null : element.val();
    }

    public static String getIndexUrl(String html) {
        Document doc = Jsoup.parse(html);
        Elements elements = doc.select("p");

        for (Element element : elements) {
            if (element.text().contains("Click")) {
                return element.childNode(1).attr("href");
            }
        }

        return null;
    }

    public static SearchResponse getSearchResult(String html) {
        SearchResponse searchResponse = new SearchResponse();
        Document doc = Jsoup.parse(html);
        Elements elements = doc.select("table");
        int index = 0;
        int firstTableIndex = 9;
        int secondTableIndex = 12;
        int thirdTableIndex = 15;
        boolean hasReferencePrice = false;
        List<SearchResultBean> firstSearchResultBeanList = Lists.newArrayList();
        List<SearchResultBean> secondSearchResultBeanList = Lists.newArrayList();
        ReferencePriceBean referencePriceBean = null;

        for (Element element : elements) {
            if (index == firstTableIndex) {
                firstSearchResultBeanList = buildSearchResultBean(element);
            } else if (index == secondTableIndex) {
                secondSearchResultBeanList = buildSearchResultBean(element);
            } else if (index == thirdTableIndex) {
                referencePriceBean = getReferencePrice(element);
            }

            index++;
        }

        searchResponse.setFirstSearchResultBeanList(firstSearchResultBeanList);
        searchResponse.setSecondSearchResultBeanList(secondSearchResultBeanList);
        searchResponse.setReferencePriceBean(referencePriceBean);

        return searchResponse;
    }

    private static List<SearchResultBean> buildSearchResultBean(Element element) {
        List<SearchResultBean> searchResultBeanList = Lists.newArrayList();
        boolean first = true;

        Elements trs = element.select("tr");
        for (Element tr : trs) {
            Elements tds = tr.select("td");
            if (first) {
                first = false;
                continue;
            }
            int index = 0;
            SearchResultBean searchResultBean = new SearchResultBean();
            for (Element td : tds) {
                if (index == 0) {
                    index++;
                    continue;
                }
                setSearchBean(searchResultBean, td.text(), index);
                index++;
            }

            searchResultBeanList.add(searchResultBean);
        }

        return searchResultBeanList;
    }

    private static void setSearchBean(SearchResultBean searchResultBean, String value, int index) {
        switch (index) {
            case 1:
                searchResultBean.setFlightData(value);
                break;
            case 2:
                searchResultBean.setFlightNo(value);
                break;
            case 3:
                searchResultBean.setDepTime(value);
                break;
            case 4:
                searchResultBean.setArrTime(value);
                break;
            case 5:
                searchResultBean.setAllLimitMoney(value);
                break;
            case 6:
                searchResultBean.setLeftLimitMoney(value);
                break;
            case 7:
                searchResultBean.setPayMoney(value);
                break;
        }
    }

    private static ReferencePriceBean getReferencePrice(Element element) {
        ReferencePriceBean referencePriceBean = new ReferencePriceBean();
        boolean first = true;

        Elements trs = element.select("tr");
        for (Element tr : trs) {
            Elements tds = tr.select("td");
            if (first) {
                first = false;
                continue;
            }
            int index = 0;
            for (Element td : tds) {
                if (index == 0) {
                    index++;
                    String value = td.select("input").val();
                    ReferenceValueBean referenceValueBean = JacksonUtil.decode(value, ReferenceValueBean.class);
                    referencePriceBean.setReferenceValueBean(referenceValueBean);
                    continue;
                }
                setReferenceBean(referencePriceBean, td.text(), index);
                index++;
            }
        }

        return referencePriceBean;
    }

    private static void setReferenceBean(ReferencePriceBean referencePriceBean, String value, int index) {
        switch (index) {
            case 1:
                referencePriceBean.setRealCarrier(value);
                break;
            case 2:
                referencePriceBean.setFlightMatch(value);
                break;
            case 3:
                referencePriceBean.setFlightDateMatch(value);
                break;
            case 4:
                referencePriceBean.setSpan(value);
                break;
            case 5:
                referencePriceBean.setFlightDate(value);
                break;
            case 6:
                referencePriceBean.setReferencePrice(value);
                break;
            case 7:
                referencePriceBean.setAgencyFee(value);
                break;
            case 8:
                referencePriceBean.setPersonNum(value);
                break;
            case 9:
                referencePriceBean.setTraveler(value);
                break;
            case 10:
                referencePriceBean.setPre(value);
                break;
            case 11:
                referencePriceBean.setExt(value);
                break;
            case 12:
                referencePriceBean.setIsPolicyMatch(value);
                break;
            case 13:
                referencePriceBean.setIsSaleMatch(value);
                break;
        }
    }
}
