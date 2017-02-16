package com.feicui.newsup.model.entity;

/**
 * Created by Administrator on 2017/1/10.
 */
//此类可以存储登录登录信息也可以存储注册信息
public class Register {
    private String result;
    private String token;//用户令牌，用于验证用户，具有时效期
    private String explain;
    public Register(){

    }
    public Register(String result, String token, String explain) {
        this.result = result;
        this.token = token;
        this.explain = explain;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "Register{" +
                "result='" + result + '\'' +
                ", token='" + token + '\'' +
                ", explain='" + explain + '\'' +
                '}';
    }
}
