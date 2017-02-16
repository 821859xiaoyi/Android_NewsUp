package com.feicui.newsup.volleyhttp;


import android.content.Context;


import edu.feicui.mnewsupdate.volley.RequestQueue;
import edu.feicui.mnewsupdate.volley.Response;
import edu.feicui.mnewsupdate.volley.toolbox.StringRequest;
import edu.feicui.mnewsupdate.volley.toolbox.Volley;

/**
 * Created by Administrator on 2017/1/7.
 */
//Volley网络框架,okhttp,xutils

public class VolleyHttp {
    //创建一个RequestQueue对象
    //创建一个StringRequest对象
    //将StringRequest对象添加到RequestQueue中
    private RequestQueue mQueue;
    public VolleyHttp(Context context){
        mQueue= Volley.newRequestQueue(context);//创建一个RequestQueue对象
    }

    /**
     *
     * @param url 获取数据的请求地址
     * @param listener 数据获取成功后相应
     * @param errorListener 数据获取失败相应
     */
    public void getJsonObject(String url, Response.Listener<String> listener,Response.ErrorListener errorListener){
        StringRequest request=new StringRequest(url,listener,errorListener);//创建一个StringRequest对象
        mQueue.add(request);//将StringRequest对象添加到RequestQueue中
    }
}
