package com.feicui.newsup.model.entity;

/**
 * Created by Administrator on 2017/1/11.
 */

public class Toemail {
    private int result;
    private String explain;

    public void Toemail(){

    }

    public Toemail(int result, String explain) {
        this.result = result;
        this.explain = explain;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    @Override
    public String toString() {
        return "Toemail{" +
                "result=" + result +
                ", explain='" + explain + '\'' +
                '}';
    }
}
