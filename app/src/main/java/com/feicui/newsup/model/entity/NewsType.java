package com.feicui.newsup.model.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/1/6.
 */

public class NewsType {
    private int gid;
    private String group;
    private List<SubType> subgrp;

    public NewsType() {
    }

    public NewsType(int gid, String group, List<SubType> subgrp) {
        this.gid = gid;
        this.group = group;
        this.subgrp = subgrp;
    }

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public List<SubType> getSubgrp() {
        return subgrp;
    }

    public void setSubgrp(List<SubType> subgrp) {
        this.subgrp = subgrp;
    }

    @Override
    public String toString() {
        return "NewsType{" +
                "gid=" + gid +
                ", group='" + group + '\'' +
                ", subgrp=" + subgrp +
                '}';
    }
}
