package com.liang.batchOrder.bean;

public class OrderNeedFromSearch {
    private Tuple<String, String> code;
    private String policyId;
    private String firstFlightPrice;
    private String secondFlightPrice;
    private String cabin;

    public void setCode(Tuple<String, String> code) {
        this.code = code;
    }

    public void setPolicyId(String policyId) {
        this.policyId = policyId;
    }

    public void setFirstFlightPrice(String firstFlightPrice) {
        this.firstFlightPrice = firstFlightPrice;
    }

    public void setSecondFlightPrice(String secondFlightPrice) {
        this.secondFlightPrice = secondFlightPrice;
    }

    public void setCabin(String cabin) {
        this.cabin = cabin;
    }

    public Tuple<String, String> getCode() {
        return code;
    }

    public String getPolicyId() {
        return policyId;
    }

    public String getFirstFlightPrice() {
        return firstFlightPrice;
    }

    public String getSecondFlightPrice() {
        return secondFlightPrice;
    }

    public String getCabin() {
        return cabin;
    }
}
