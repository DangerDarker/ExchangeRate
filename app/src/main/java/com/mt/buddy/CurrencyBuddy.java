package com.mt.buddy;

import org.json.JSONObject;

public class CurrencyBuddy {
    public String getEname() {
        return Ename;
    }

    public void setEname(String ename) {
        Ename = ename;
    }

    public String getCname() {
        return Cname;
    }

    public void setCname(String cname) {
        Cname = cname;
    }

    private String Ename ;
    private String Cname;
    public CurrencyBuddy(String Ename , String Cname){
        this.Ename = Ename;
        this.Cname = Cname;
    }
    public static CurrencyBuddy createFromJson(JSONObject object){
        String Ename = "";
        String Cname = "";
        try{
            Ename = object.getString("currency");
            Cname = object.getString("name");
        }catch (Exception e){
            e.printStackTrace();
        }
        return new CurrencyBuddy(Ename , Cname);
    }

}
