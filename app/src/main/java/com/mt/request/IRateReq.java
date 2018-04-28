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
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + Config.appCode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("amount" , amount);
        querys.put("from" , from);
        querys.put("to" , to);
       return  request.getJSON(getResultURL() , true , request.getResponse(Config.HOST , Config.PATH_CURRENCY ,
               Config.METHOD ,headers ,querys));
    }

}
