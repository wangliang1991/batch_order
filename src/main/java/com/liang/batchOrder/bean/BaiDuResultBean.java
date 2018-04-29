package com.liang.batchOrder.bean;

import java.util.List;

/**
 * Created by wangliang.wang on 2018/4/29.
 */
public class BaiDuResultBean {

    private String log_id;
    private List<Words> words_result;
    private int words_result_num;

    public String getLog_id() {
        return log_id;
    }

    public void setLog_id(String log_id) {
        this.log_id = log_id;
    }

    public List<Words> getWords_result() {
        return words_result;
    }

    public void setWords_result(List<Words> words_result) {
        this.words_result = words_result;
    }

    public int getWords_result_num() {
        return words_result_num;
    }

    public void setWords_result_num(int words_result_num) {
        this.words_result_num = words_result_num;
    }
}
