package com.mt.request;

import android.util.Log;

//import org.json.JSONArray;
import org.apache.http.HttpResponse;
import org.json.JSONObject;
import java.io.BufferedReader;
//import java.io.DataInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
//import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

public class HttpRequester{
    private static final String TAG = "HttpRequester";
    final StringBuilder sb = new StringBuilder();
  //  private String responseString = null;
    public HttpRequester(){
    }
    public void add(String key, String value){
        if(sb.length() > 0){
            sb.append("&");
        }
        sb.append(key).append("=").append(value);
    }

    public void add(String key, double value){
        if(sb.length() > 0){
            sb.append("&");
        }
        sb.append(key).append("=").append(value);
    }

    public void add(String key, long value){
        if(sb.length() > 0){
            sb.append("&");
        }
        sb.append(key).append("=").append(value);
    }

    public void add(String key, int value){
        if(sb.length() > 0){
            sb.append("&");
        }
        sb.append(key).append("=").append(value);
    }

    public void add(String key, boolean value){
        if(sb.length() > 0){
            sb.append("&");
        }
        sb.append(key).append("=").append(value);
    }

    // HTTP GET request
    public  String send(String urlString, boolean isGet){
        HttpURLConnection connection = null;
        URL url;
        InputStream din;
        BufferedReader cin;

        try {
            if (isGet) {
                if(sb.length() > 0){
                    url = new URL(new StringBuilder(urlString).append("?").append(sb).toString());
                }else{
                    url = new URL(urlString);
                }
                connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(120000);//120 seconds (mainly for get events time out usage)
                din = connection.getInputStream();
                cin = new BufferedReader(new InputStreamReader(
                        connection.getInputStream()));
                try {
                    StringBuilder contents = new StringBuilder();
                    for(String line ; (line = cin.readLine()) != null;){
                        contents.append(line);
                    }
                    return contents.toString();
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    cin.close();
                    if (din != null)
                        din.close();
                }

            } else{//POST
                url = new URL(urlString);
                connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setRequestMethod("POST");
                connection.setUseCaches(false);
                connection.setConnectTimeout(26000);//26 seconds timeout
                PrintWriter cout = new PrintWriter(connection.getOutputStream());
                if(sb.length() > 0){
                    cout.write(sb.toString());
                    cout.flush();
                }
                cout.close();

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection
                        .getInputStream()));
                StringBuilder contents = new StringBuilder();
                for ( String line; (line = reader.readLine()) != null; ){
                    contents.append( line );
                }

                return contents.toString();
            }
        }
        catch(UnknownHostException ex){
            return "UnknownHost";
        }
        catch (Exception e) {
            Log.e(TAG, "ERROR in send", e);
        } finally{
            if(connection != null){
                connection.disconnect();
            }
        }
        return "";
    }

    public JSONObject getJSON(String urlString, boolean isGet ) {
        JSONObject result = null;
        String get = null;
        try {
            Log.i(TAG, "urlString:" + urlString + "?" + sb.toString() + " start...");
            get = send(urlString , true);//(urlString, isGet);
            Log.i(TAG, "urlString:" + urlString +"?"+sb.toString() + " result~~~~~~~~~~~~~~~~~~~~:" + get);
            result = new JSONObject(get);
        } catch (Exception e) {
            // responseString = get;
            e.printStackTrace();
        }
        return result;
    }
    public JSONObject getJSON(String urlString, boolean isGet , String send) {
        JSONObject result = null;
        String get = null;
        try {
            Log.i(TAG, "urlString:" + urlString + "?" + sb.toString() + " start...");
            get = send;//(urlString, isGet);
            Log.i(TAG, "urlString:" + urlString +"?"+sb.toString() + " result~~~~~~~~~~~~~~~~~~~~:" + get);
            result = new JSONObject(get);
        } catch (Exception e) {
           // responseString = get;
            e.printStackTrace();
        }
        return result;
    }
    JSONObject getJSON(String urlString , String send){
        return getJSON(urlString ,false , send);
    }

}
