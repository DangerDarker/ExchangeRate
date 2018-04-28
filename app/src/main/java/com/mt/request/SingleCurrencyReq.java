package com.mt.request;


import com.mt.exchangerate.Config;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SingleCurrencyReq extends IRequest{
    private String Currency;
    public SingleCurrencyReq(String Currency){
        super();
        this.Currency = Currency;
        request.add(Config.Currency , Currency);
        request.add("appkey" ,Config.appKey);
    }
    @Override
    public String getResultURL(){
        return Config.URL_SINGLE;
    }

    public JSONObject getJSONObiect(){
        return request.getJSON(getResultURL() , true );
    }
}
