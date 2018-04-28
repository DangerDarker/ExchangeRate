package com.mt.buddy;

import org.json.JSONObject;

public class FromToBuddy {

    private String From;
    private String To;
    private String FromName;
    private String ToName;
    private String Date;
    private String Rate;
    private String Camount;

    public FromToBuddy(String from , String to , String fromname , String toname , String date , String rate
                      ,String camount){
        this.From = from;
        this.To =  to;
        this.FromName = fromname;
        this.ToName = toname;
        this.Date = date;
        this.Rate = rate;
        this.Camount = camount;
    }

    public static FromToBuddy createFromJson(JSONObject object){
        String from = "";
        String to = "";
        String fromname = "";
        String toname = "";
        String date = "";
        String rate = "";
        String camount = "";
        try{
            from = object.getString("from");
            to = object.getString("to");
            fromname = object.getString("fromname");
            toname = object.getString("toname");
            date = object.getString("updatetime");
            rate = object.getString("rate");
            camount = object.getString("camount");
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return new FromToBuddy(from , to , fromname ,toname , date , rate , camount );
    }


    //获取数据
    public String getFrom() {
        return From;
    }

    public void setFrom(String from) {
        From = from;
    }

    public String getTo() {
        return To;
    }

    public void setTo(String to) {
        To = to;
    }

    public String getFromName() {
        return FromName;
    }

    public void setFromName(String fromName) {
        FromName = fromName;
    }

    public String getToName() {
        return ToName;
    }

    public void setToName(String toName) {
        ToName = toName;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getRate() {
        return Rate;
    }

    public void setRate(String rate) {
        Rate = rate;
    }

    public String getCamount() {
        return Camount;
    }

    public void setCamount(String camount) {
        Camount = camount;
    }

}
