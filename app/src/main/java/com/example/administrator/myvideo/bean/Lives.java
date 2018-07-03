package com.example.administrator.myvideo.bean;

/**
 * Created by Administrator on 2017/3/8.
 */

public class Lives {


    Creator creator; //首页展示的图片等信息

    String name; //房间名字

    String city; //诚实


    String stream_addr;//直播地址


    public Creator getCreator() {
        return creator;
    }

    public void setCreator(Creator creator) {
        this.creator = creator;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStream_addr() {
        return stream_addr;
    }

    public void setStream_addr(String stream_addr) {
        this.stream_addr = stream_addr;
    }
}
