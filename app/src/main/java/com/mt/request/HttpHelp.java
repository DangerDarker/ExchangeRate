package com.mt.request;

import com.mt.robot.tuling.Tuling;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.util.concurrent.TimeUnit;

public class HttpHelp {

    private final static String API_URI = "http://www.tuling123.com/openapi/api";
    private final static String TULING_KEY = "67f8f58cc1e44f79aa2ad7dad1489a80";

    private OkHttpClient client;
    private Tuling tuling;

    public HttpHelp(Tuling tuling) {
        client = new OkHttpClient();
        //设置超时
        client.setConnectTimeout(5, TimeUnit.SECONDS);
        client.setWriteTimeout(10, TimeUnit.SECONDS);
        client.setReadTimeout(30, TimeUnit.SECONDS);
        this.tuling = tuling;
    }

    public Response get(String url) throws Exception {
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Content-Type", "application/json")
                .addHeader("text/json", "charset=utf-8")
                .build();
        Response response = client.newCall(request).execute();
        return response;
    }
}
