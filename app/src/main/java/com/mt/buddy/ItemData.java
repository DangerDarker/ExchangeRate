package com.mt.buddy;

import org.litepal.crud.DataSupport;

/**
 * Created by Mr_L on 2018/5/9.
 */

public class ItemData extends DataSupport {
    private String ename;
    private String cname;
    private String rate;
    private String date;

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
