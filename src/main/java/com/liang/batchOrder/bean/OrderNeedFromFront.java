package com.liang.batchOrder.bean;

public class OrderNeedFromFront {
    private int days;
    private String firstFlightName;
    private String secondFlightName;
    private String seatNum;
    private String mobile;
    private String goDate;
    private String backDate;

    public void setDays(int days) {
        this.days = days;
    }

    public void setFirstFlightName(String firstFlightName) {
        this.firstFlightName = firstFlightName;
    }

    public void setSecondFlightName(String secondFlightName) {
        this.secondFlightName = secondFlightName;
    }

    public void setSeatNum(String seatNum) {
        this.seatNum = seatNum;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setGoDate(String goDate) {
        this.goDate = goDate;
    }

    public void setBackDate(String backDate) {
        this.backDate = backDate;
    }

    public int getDays() {
        return days;
    }

    public String getFirstFlightName() {
        return firstFlightName;
    }

    public String getSecondFlightName() {
        return secondFlightName;
    }

    public String getSeatNum() {
        return seatNum;
    }

    public String getMobile() {
        return mobile;
    }

    public String getGoDate() {
        return goDate;
    }

    public String getBackDate() {
        return backDate;
    }
}
