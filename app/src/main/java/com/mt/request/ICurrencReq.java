package com.mt.request;

import com.mt.exchangerate.Config;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;

public class ICurrencReq extends IRequest {
    private ArrayList<Currency> mList  = new ArrayList<>();
    public ICurrencReq(){
        super();
        request.add("appkey" ,Config.appKey);
    }
    @Override
    public  String getResultURL(){
        return Config.URL_CURRENCY;
    }
    public JSONObject getJSONObiect(){
        return request.getJSON(getResultURL() , true );
    }
}
