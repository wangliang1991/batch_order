package com.liang.batchOrder.bean;

/**
 * Created by wangliang.wang on 2018/4/23.
 */
public class SearchBean {

    //出发城市
    private String depCity;
    //到达城市
    private String arrCity;
    //出发日期
    private String depDate;
    public SearchBean() {

    }
    public SearchBean(String depCity, String arrCity, String depDate) {
        this.depCity = depCity;
        this.arrCity = arrCity;
        this.depDate = depDate;
    }

    public String getDepCity() {
        return depCity;
    }

    public void setDepCity(String depCity) {
        this.depCity = depCity;
    }

    public String getArrCity() {
        return arrCity;
    }

    public void setArrCity(String arrCity) {
        this.arrCity = arrCity;
    }

    public String getDepDate() {
        return depDate;
    }

    public void setDepDate(String depDate) {
        this.depDate = depDate;
    }
}
