package com.mt.robot.tuling;
public class Tuling {

    private final static String API_URI = "http://www.tuling123.com/openapi/api";
    private final static String TULING_KEY = "67f8f58cc1e44f79aa2ad7dad1489a80";

    private String info;    //消息内容
    private String userId;  //针对每一个用户的id
    private String loc;     //位置信息，编码方式为UTF-8
    private String lon;     //经度信息
    private String lat;     //纬度信息

    private String Uri;


    // 获取消息内容
    public String getInfo() {
        return info;
    }


    // 设置消息内容
    public void setInfo(String info) {
        this.info = info;
    }


     //获取userId
    public String getUserId() {
        return userId;
    }

   //设置userId
    public void setUserId(String userId) {
        this.userId = userId;
    }

  // 获取位置信息
    public String getLoc() {
        return loc;
    }

  // 设置位置信息
    public void setLoc(String loc) {
        this.loc = loc;
    }

  // 获取经度信息
    public String getLon() {
        return lon;
    }

   // 设置经度信息
    public void setLon(String lon) {
        this.lon = lon;
    }

    // 获取纬度信息
    public String getLat() {
        return lat;
    }

   // 设置纬度信息
    public void setLat(String lat) {
        this.lat = lat;
    }

    // 返回生成的uri
    public String generateUri() {
        String uri = API_URI + "?" + "key=" + TULING_KEY + "&" + "info=" + this.getInfo()
                + "&" + "userid=" + this.getUserId();
        if (this.getLoc() != null) {
            uri = uri + "&" + "loc=" + this.getLoc();
        }
        if (this.getLon() != null) {
            uri = uri + "&" + "lon=" + this.getLon();
        }
        if (this.getLat() != null) {
            uri = uri + "&" + "lat=" + this.getLat();
        }
        return uri.replaceAll(" ", "&nbsp");
    }
}

