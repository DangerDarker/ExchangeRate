package com.mt.exchangerate;



import com.mt.buddy.RateBuddy;
import com.mt.request.SingleCurrencyReq;

import org.json.JSONObject;

public class AcquireSingleRate {
    private JSONObject list;   // 应该 存在时间 长
    private JSONObject result;
    private JSONObject ob;
    private SingleCurrencyReq scr ;
    public AcquireSingleRate(String country ){
        scr = new SingleCurrencyReq( country);
        ob = scr.getJSONObiect();
    }

    public RateBuddy getRateBuddy(String target){ //根据 目标 获取  target必须为  英文缩写 如：HKD 、 USD
        try{
            JSONObject jb = list.getJSONObject(target);
            return RateBuddy.createFromJson(jb , target);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public int getResultCode(){//  判断是否返回有效数据
        try{
            int  resultCode = ob.getInt(Config.RESULT_CODE);
            if(Config.JSType.RESULT_CODE_OK == resultCode){   //  ==0
                list = getList();
                if(list != null){
                    return resultCode;
                }else
                    return  Config.JSType.RESULT_CODE_UNKNOWN_ERROR;

            }else{
                return  Config.JSType.RESULT_CODE_UNKNOWN_ERROR;
            }
        }catch(Exception e){
            e.printStackTrace();
            return Config.JSType.RESULT_CODE_UNKNOWN_ERROR;
        }
    }
    public JSONObject getList() {
        result = getResult();
        try{
            if(result != null){
                JSONObject list = result.getJSONObject("list");
                return list;
            }
            else
                return  null;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public JSONObject getResult() {
        JSONObject result;
        try{
            result = ob.getJSONObject("result");
            return  result;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
