package com.mt.exchangerate;

import android.os.Environment;

import com.mt.buddy.CurrencyBuddy;

import java.util.ArrayList;
import java.util.HashMap;

public class Config {
    public static final String URL_CONVERT = "http://api.jisuapi.com/exchange/convert";//http://jisuhuilv.market.alicloudapi.com/exchange/convert";  //汇率转换接口
    public static final String URL_CURRENCY = "http://api.jisuapi.com/exchange/currency";//"http://jisuhuilv.market.alicloudapi.com/exchange/currency"; //所有货币查询接口
    public static final String URL_SINGLE = "http://api.jisuapi.com/exchange/single" ;//"http://jisuhuilv.market.alicloudapi.com/exchange/single";  //单个货币查询接口
    public static final String appCode = "7437ed2c132f40f78ed42f99c2d04781";
    public static final String appKey ="ca89d46849c88ab7" ;// "24864189";
    public static final String appSecret = "24b5ba0763969f9e7b825364817f42c4";
    public static final String URL_BUY = "http://web.juhe.cn:8080/finance/exchange/frate"; //外汇汇率接口
    public static final String _appKey = "44bbeaf2c4b7888c2eadb4875f492b89";  //http://web.juhe.cn:8080/finance/exchange/frate?key=您申请的APPKEY

    public static final String DATABASE_NAME = "com.mt.database";

    public static final String RESULT_CODE = "status" ;
    public static final int CODE_OK = 0;
    public static final String Currency = "currency";
    public static final String FILE_RATE = Environment.getExternalStorageDirectory().getAbsolutePath() + "/rate/";
    public static final String HOST = "http://jisuhuilv.market.alicloudapi.com";
    public static final String PATH_CURRENCY = "/exchange/currency";
    public static final String METHOD = "GET";
    public static HashMap<String ,CurrencyBuddy>  CURRENCY_MAP = new HashMap<>();
    public static ArrayList<CurrencyBuddy> CURRENCY_LIST = new ArrayList<>();
    public static class JSType{
        public static final int RESULT_CODE_OK = 0;
        public static final int RESULT_CODE_UNKNOWN_ERROR = 888;
    }
}
