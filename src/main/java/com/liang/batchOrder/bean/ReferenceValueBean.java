package com.liang.batchOrder.bean;

/**
 * Created by wangliang.wang on 2018/4/27.
 */
public class ReferenceValueBean {
    private long id;
    private String prices;
    private String flightnos;
    private int minNum;
    private int maxnum;
    private String cabin;
    private String ptype;
    private String carrier;
    private int isJjj;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPrices() {
        return prices;
    }

    public void setPrices(String prices) {
        this.prices = prices;
    }

    public String getFlightnos() {
        return flightnos;
    }

    public void setFlightnos(String flightnos) {
        this.flightnos = flightnos;
    }

    public int getMinNum() {
        return minNum;
    }

    public void setMinNum(int minNum) {
        this.minNum = minNum;
    }

    public int getMaxnum() {
        return maxnum;
    }

    public void setMaxnum(int maxnum) {
        this.maxnum = maxnum;
    }

    public String getCabin() {
        return cabin;
    }

    public void setCabin(String cabin) {
        this.cabin = cabin;
    }

    public String getPtype() {
        return ptype;
    }

    public void setPtype(String ptype) {
        this.ptype = ptype;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public int getIsJjj() {
        return isJjj;
    }

    public void setIsJjj(int isJjj) {
        this.isJjj = isJjj;
    }
}
