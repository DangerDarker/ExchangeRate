package com.mt.exchangerate;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Mr_L on 2018/4/29.
 */

public class Plugin {
    public static boolean isNetworkAvailable(Context c){
        try{
            ConnectivityManager connectivityManager
                    = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }catch (Exception e){
            e.printStackTrace();
            return  false;
        }

    }
}
