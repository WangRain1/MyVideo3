package com.example.administrator.myvideo.bean;

/**
 * Created by Administrator on 2017/3/6.
 */

public class Content {


    String _id; //id

    String play; //视频url

    String title; //米子

    String text; //详情

    String img; //图片地址

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getPlay() {
        return play;
    }

    public void setPlay(String play) {
        this.play = play;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
