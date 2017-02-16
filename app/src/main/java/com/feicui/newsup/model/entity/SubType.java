package com.feicui.newsup.model.entity;

/**
 * Created by Administrator on 2017/1/6.
 */

public class SubType {
    private String subgroup;
    private int subid;

    public SubType() {

    }

    public SubType(String subgrunp, int subid) {
        this.subgroup = subgrunp;
        this.subid = subid;
    }

    public String getSubgrunp() {
        return subgroup;
    }

    public void setSubgrunp(String subgrunp) {
        this.subgroup = subgrunp;
    }

    public int getSubid() {
        return subid;
    }

    public void setSubid(int subid) {
        this.subid = subid;
    }

    @Override
    public String toString() {
        return "SubType{" +
                "subgrunp='" + subgroup + '\'' +
                ", subid=" + subid +
                '}';
    }
}
