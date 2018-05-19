//package com.mt.request;
//
//import com.mt.exchangerate.Config;
//
//import org.json.JSONObject;
//
///**
// * Created by Mr_L on 2018/5/2.
// */
//
//public class IFrateReq extends IRequest{
//    public IFrateReq(){
//        request.add("key" , Config._appKey);
//    }
//
//    @Override
//    public  String getResultURL(){
//        return Config.URL_BUY;
//    }
//
//    public JSONObject getJSONObiect(){
//        return request.getJSON(getResultURL() , true );
//    }
//}
