package com.feicui.newsup.model.biz.parser;

import android.content.Context;
import android.util.Log;

import com.feicui.newsup.model.dao.NewsDBManager;
import com.feicui.newsup.model.entity.BaseEntity;
import com.feicui.newsup.model.entity.News;
import com.feicui.newsup.model.entity.NewsType;
import com.feicui.newsup.model.entity.SubType;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/26.
 */

public class ParserNews {
    private Context context;

    public ParserNews(Context context) {
        this.context = context;
    }
    public static List<SubType> parserTypeList(String json){
        Gson gson=new Gson();
        BaseEntity<List<NewsType>> entity=gson.fromJson(json,new TypeToken<BaseEntity<List<NewsType>>>(){}.getType());

        return entity.getData().get(0).getSubgrp();
    }
    public static List<News> parserNewsList(String json){
        Gson gson=new Gson();
        BaseEntity<List<News>> entity=gson.fromJson(json,new TypeToken<BaseEntity<List<News>>>(){}.getType());
        return entity.getData();
    }
    //新闻数据解析
    public ArrayList<News> parser(JSONObject jsonObject){
        //获取单例的NewsDBManager对象
        NewsDBManager dbManager=NewsDBManager.getNewsDBManager(context);
        ArrayList<News> newsList=new ArrayList<News>();

        try {
            //通过key=data获取所有新闻信息
            JSONArray jsonArray=jsonObject.getJSONArray("data");
            //循环遍历这个数组，获取所有新闻信息
            for (int i=0;i<jsonArray.length();i++){
                JSONObject object=jsonArray.getJSONObject(i);
                int nid=object.getInt("nid");
                String title=object.getString("title");
                String summary=object.getString("summary");
                String icon=object.getString("icon");
                String link=object.getString("link");
                int type=object.getInt("type");
                String stamp=object.getString("stamp");
                News news=new News(nid,title,summary,icon,link,type,stamp);//将获取到的数据放到一个News对象中
                //将获得到的数据放入数据库
                Log.d("TAG","nid="+nid);
                newsList.add(news);//将每个对象放入集合
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //插入多条新闻
        dbManager.insertNews(newsList);
        //返回新闻集合
        return newsList;
    }
}
