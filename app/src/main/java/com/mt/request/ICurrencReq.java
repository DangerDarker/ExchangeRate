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

        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + Config.appCode);
        Map<String, String> querys = new HashMap<String, String>();
       // return  request.getJSON(getResultURL() , true , request.getResponse(Config.HOST , Config.PATH_CURRENCY ,
        //                                                                            Config.METHOD ,headers ,querys));
        return request.getJSON(getResultURL() , true );
    }
}
