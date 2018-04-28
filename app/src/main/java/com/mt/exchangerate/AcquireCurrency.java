package com.mt.exchangerate;

import android.support.annotation.NonNull;

import com.mt.buddy.CurrencyBuddy;
import com.mt.request.ICurrencReq;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class AcquireCurrency implements  Iterable{
    private ArrayList<CurrencyBuddy> mCurrencyList = new ArrayList<>();
    private JSONObject ob ;
    private int lastIndex;
    private int k = -1;
    public AcquireCurrency(){

    }
    public ArrayList<CurrencyBuddy> getmCurrencyList() {
        return mCurrencyList;
    }
    public int  getCurrency(){
         ob = new ICurrencReq().getJSONObiect();
        try{
            int result = ob.getInt(Config.RESULT_CODE);
            if(result == 0){
                //JSONArray array = ob.getJSONArray("result");
                Iterator<CurrencyBuddy> it = iterator();
                if(it != null){
                    while(it.hasNext()){
                        CurrencyBuddy cb = it.next();
                        if(cb != null){
                            mCurrencyList.add(cb);
                        }
                    }
                    return Config.JSType.RESULT_CODE_OK;
                }else{
                    return Config.JSType.RESULT_CODE_UNKNOWN_ERROR;
                }
            }else{
                return  result;
            }
        }catch (Exception e){
            e.printStackTrace();
            return Config.JSType.RESULT_CODE_UNKNOWN_ERROR;
        }
    }

    @NonNull
    @Override
    public Iterator iterator() {
        try{
            final JSONArray array = ob.getJSONArray("result");

            if(array != null){
                lastIndex = array.length() - 1;
                return new Iterator(){
                    @Override
                    public boolean hasNext() {
                        return k < lastIndex;
                    }

                    @Override
                    public CurrencyBuddy next() {
                        try{
                            return getItem(array.getJSONObject(++k));
                        }catch(Exception e){
                           // Log.e(TAG, "ERROR in Event.next!!!()", e);
                            return null;
                        }
                    }

                    @Override
                    public void remove() {
                    }
                };
            }
            return null;
        }catch(Exception e){
          //  Log.e(getClass(), "ERROR in iterator()", e);
            return null;
        }
    }
    public CurrencyBuddy getItem(JSONObject ob){
        return CurrencyBuddy.createFromJson(ob);
    }


}
