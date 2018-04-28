package com.mt.exchangerate;

import com.mt.buddy.FromToBuddy;
import com.mt.request.IRateReq;

import org.json.JSONObject;

public class AcquireFromTo {

    private String amount;
    private String from;
    private String to;
    private IRateReq rr;
    private JSONObject ob;
    private JSONObject result;
    public AcquireFromTo(String amount , String from ,String to){
        this.amount = amount;
        this.from = from;
        this.to = to;
        rr = new IRateReq(amount , from , to);
        ob = rr.getJSONObject();
    }

    public FromToBuddy getFromToBuddy(){  // 解析json 返回具体数据
        return FromToBuddy.createFromJson(result);
    }
    public int getResultCode(){ // json数据是否有效
        try{
            int resultCode = ob.getInt(Config.RESULT_CODE);
            if(resultCode == Config.JSType.RESULT_CODE_OK){
                result = ob.getJSONObject("result");
                return resultCode;
            }else
                return Config.JSType.RESULT_CODE_UNKNOWN_ERROR;
        }catch (Exception e){
            e.printStackTrace();
            return Config.JSType.RESULT_CODE_UNKNOWN_ERROR;
        }

    }

    public JSONObject getResult(){
        JSONObject result ;
        try{
            result = ob;
            return result;
        }catch (Exception e){
            e.printStackTrace();
            return  null;
        }
    }

}
