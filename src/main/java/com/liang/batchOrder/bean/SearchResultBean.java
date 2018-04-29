package com.liang.batchOrder.bean;

/**
 * Created by wangliang.wang on 2018/4/25.
 */
public class SearchResultBean {

    private String flightData;

    private String flightNo;

    private String depTime;

    private String arrTime;

    private String allLimitMoney;

    private String leftLimitMoney;

    private String payMoney;

    public String getFlightData() {
        return flightData;
    }

    public void setFlightData(String flightData) {
        this.flightData = flightData;
    }

    public String getFlightNo() {
        return flightNo;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    public String getDepTime() {
        return depTime;
    }

    public void setDepTime(String depTime) {
        this.depTime = depTime;
    }

    public String getArrTime() {
        return arrTime;
    }

    public void setArrTime(String arrTime) {
        this.arrTime = arrTime;
    }

    public String getAllLimitMoney() {
        return allLimitMoney;
    }

    public void setAllLimitMoney(String allLimitMoney) {
        this.allLimitMoney = allLimitMoney;
    }

    public String getLeftLimitMoney() {
        return leftLimitMoney;
    }

    public void setLeftLimitMoney(String leftLimitMoney) {
        this.leftLimitMoney = leftLimitMoney;
    }

    public String getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(String payMoney) {
        this.payMoney = payMoney;
    }
}
