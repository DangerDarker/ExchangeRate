package com.mt.request;

import android.support.annotation.IntRange;

import com.mt.exchangerate.Config;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class IRateReq<T> extends IRequest {
    private JSONObject response;
    private final ArrayList<T> mListResult = new ArrayList<T>();
    private String amount;
    private String from;
    private String to;
    public IRateReq(String amount , String from ,String to){
        super();
        request.add("amount" , amount);
        request.add("from" , from);
        request.add("to" , to);
        this.amount = amount;
        this.from = from;
        this.to = to;
    }
    public IRateReq(){
        this("", "" , "");
    }
    @Override
    public String getResultURL(){
        return Config.URL_CONVERT;
    }

    public JSONObject getJSONObject(){

        return request.getJSON(getResultURL() , true);
    }

}
