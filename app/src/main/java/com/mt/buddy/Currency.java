package com.mt.buddy;

import org.litepal.crud.DataSupport;

/**
 * Created by Mr_L on 2018/4/28.
 */

public class Currency extends DataSupport{
    private String ename;
    private String cname;

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
}
