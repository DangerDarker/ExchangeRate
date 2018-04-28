package com.mt.request;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class HttpUtils {

    public static HttpResponse doGet(String host, String path, String method,
                                     Map<String, String> headers,
                                     Map<String, String> querys)
            throws Exception {
        HttpClient httpClient = wrapClient(host);

    //    Log.i("HttpUtils " , "doGet: calles");
        HttpGet request = new HttpGet(buildUrl(host, path, querys));
        for (Map.Entry<String, String> e : headers.entrySet()) {
           // Log.i("HttpUtils", "doGet: " + e.getKey() + "   "+ e.getValue());
            request.addHeader(e.getKey(), e.getValue());
        }

        Log.i("HttpUtils", "doGet: "+request.getFirstHeader("Authorization"));
        HttpResponse response = httpClient.execute(request);
        Log.i("HttpUtils", "doGet: "+response.toString());
        return response;
    }










    private static String buildUrl(String host, String path, Map<String, String> querys) throws UnsupportedEncodingException {
        StringBuilder sbUrl = new StringBuilder();
        sbUrl.append(host);
        if (!StringUtils.isBlank(path)) {
            sbUrl.append(path);
        }
        if (null != querys) {
            StringBuilder sbQuery = new StringBuilder();
            for (Map.Entry<String, String> query : querys.entrySet()) {
                if (0 < sbQuery.length()) {
                    sbQuery.append("&");
                }
                if (StringUtils.isBlank(query.getKey()) && !StringUtils.isBlank(query.getValue())) {
                    sbQuery.append(query.getValue());
                }
                if (!StringUtils.isBlank(query.getKey())) {
                    sbQuery.append(query.getKey());
                    if (!StringUtils.isBlank(query.getValue())) {
                        sbQuery.append("=");
                        sbQuery.append(URLEncoder.encode(query.getValue(), "utf-8"));
                    }
                }
            }
            if (0 < sbQuery.length()) {
                sbUrl.append("?").append(sbQuery);
            }
        }

        Log.i("HttpUtils", "buildUrl: " +sbUrl.toString());
        return sbUrl.toString();
    }

    private static HttpClient wrapClient(String host) {
        HttpClient httpClient = new DefaultHttpClient();
//        if (host.startsWith("https://")) {
//            sslClient(httpClient);
//        }

        return httpClient;
    }

//    private static void sslClient(HttpClient httpClient) {
//        try {
//            SSLContext ctx = SSLContext.getInstance("TLS");
//            X509TrustManager tm = new X509TrustManager() {
//                public X509Certificate[] getAcceptedIssuers() {
//                    return null;
//                }
//                public void checkClientTrusted(X509Certificate[] xcs, String str) {
//
//                }
//                public void checkServerTrusted(X509Certificate[] xcs, String str) {
//
//                }
//            };
//            ctx.init(null, new TrustManager[] { tm }, null);
//            SSLSocketFactory ssf = new SSLSocketFactory(ctx);
//            ssf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
//            ClientConnectionManager ccm = httpClient.getConnectionManager();
//            SchemeRegistry registry = ccm.getSchemeRegistry();
//            registry.register(new Scheme("https", 443, ssf));
//        } catch (KeyManagementException ex) {
//            throw new RuntimeException(ex);
//        } catch (NoSuchAlgorithmException ex) {
//            throw new RuntimeException(ex);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
}