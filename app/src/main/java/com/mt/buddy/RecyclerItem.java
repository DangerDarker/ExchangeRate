package com.mt.buddy;

import android.support.v7.widget.RecyclerView;

/**
 * Created by Mr_L on 2018/4/28.
 */

public class RecyclerItem {
    private RateBuddy rateBuddy;

    public RateBuddy getRateBuddy() {
        return rateBuddy;
    }

    public void setRateBuddy(RateBuddy rateBuddy) {
        this.rateBuddy = rateBuddy;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money * (Float.parseFloat(rateBuddy.getRate()));
    }

    private float money;
    public RecyclerItem(RateBuddy ratebuddy , String  flo){
        this.rateBuddy = ratebuddy;
        this.money = (Float.parseFloat(flo)) * (Float.parseFloat(ratebuddy.getRate()));
    }
}
