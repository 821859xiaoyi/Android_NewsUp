package com.feicui.newsup.model.entity;

import java.io.Serializable;

/**
 * 实体类
 *
 * Created by Administrator on 2016/12/21.
 */

public class News implements Serializable{
    //新闻id
    private int nid;
    //标题
    private String title="";
    //摘要
    private String summary="";
    //图标
    private String icon="";
    //网页链接
    private String link="";
    //新闻类型
    private int type;

    //新闻时间
    private String stamp;
    public News(int nid, String title, String summary, String icon, String link, int type,String stamp) {
        this.nid = nid;
        this.type = type;
        this.link = link;
        this.icon = icon;
        this.summary = summary;
        this.title = title;
        this.stamp=stamp;
    }



    public int getNid() {
        return nid;
    }

    public void setNid(int nid) {
        this.nid = nid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getStamp() {
        return stamp;
    }

    public void setStamp(String stamp) {
        this.stamp = stamp;
    }

    @Override
    public String toString() {
        return "News{" +
                "nid=" + nid +
                ", title='" + title + '\'' +
                ", summary='" + summary + '\'' +
                ", icon='" + icon + '\'' +
                ", link='" + link + '\'' +
                ", type=" + type +
                ",stamp="+stamp+'\''+
                '}';
    }
}
