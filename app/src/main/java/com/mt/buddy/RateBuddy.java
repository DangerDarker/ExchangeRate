package com.mt.buddy;

import android.util.Log;

import org.json.JSONObject;

public class RateBuddy {

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    private static String ename;
    private  String cname ;   //中文

    public String getCname() {
        return cname;
    }

    public void setCname(String name) {
        this.cname = name;
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

    private  String rate;
    private String date;
    public RateBuddy(String ename ,String name , String rate , String date){
        this.ename = ename;
        this.cname = name;
        this.rate = rate ;
        this.date = date;
    }
    public static RateBuddy createFromJson(JSONObject object , String Ename){
        ename = Ename;
        String cname = "";
        String rate = "";
        String date = "";
        try{
            cname = object.getString("name");
            rate = object.getString("rate");
            date = object.getString("updatetime");
        }catch (Exception e){
            e.printStackTrace();
        }
        Log.i("NAME", "createFromJson: "+cname+ename+rate+date);
        return new RateBuddy(ename ,cname , rate , date);
    }

    public static class RATE{
        public static final String NAME = "name";
        public static final String RATE = "rate";
        public static final String DATE = "date";
    }
}
