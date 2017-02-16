package com.feicui.newsup.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.feicui.newsup.model.entity.News;
import com.feicui.newsup.model.entity.SubType;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

/**
 * Created by Administrator on 2016/12/27.
 */
//数据库管理类
public class NewsDBManager {
    //单例模式
    private static NewsDBManager dbManager;
    private DBOpenHelper helper;
    public NewsDBManager(Context context){
        helper=new DBOpenHelper(context);
    }
    //同步锁实现单例模式
    public static NewsDBManager getNewsDBManager(Context context){
        if (dbManager==null){
            synchronized (NewsDBManager.class){//类对象
                if (dbManager==null){//当多线程访问时，确保是同一个对象
                    dbManager=new NewsDBManager(context);
                }
            }
        }
        return dbManager;
    }
    //添加新闻类型数据
    public boolean saveNewsType(List<SubType> list){//例如：{新闻，1} {军事，2}
        for (SubType type:list){
            //当调用此方法时，回调DBOpenHelper里的onCreate()方法
            SQLiteDatabase db=helper.getWritableDatabase();
            //判断数据里是否有此字段的数据
            Cursor cursor=db.rawQuery("select * from type where subid="+type.getSubid(),null);
            if (cursor.moveToFirst()){//如果cursor.moveToFirst()为true,数据库里有此字段的数据
                cursor.close();
                return false;
            }
            cursor.close();
            //如果数据库里没有此字段的数据，插入数据
            ContentValues values=new ContentValues();
            values.put("subid",type.getSubid());
            values.put("subgroup",type.getSubgrunp());
            db.insert("type",null,values);//数据存储在values中，将存储数据的values插入type表中
            db.close();
        }

        return true;
    }
    public List<SubType> pueryNewsType(){
        List<SubType> list=new ArrayList<>();
        SQLiteDatabase db=helper.getWritableDatabase();
        Cursor cursor=db.rawQuery("select * from type order by _id desc",null);
        while (cursor.moveToNext()){
            int subid=cursor.getInt(cursor.getColumnIndex("subid"));
            String subgroup=cursor.getString(cursor.getColumnIndex("subgroup"));
            SubType type=new SubType(subgroup,subid);
            list.add(type);
        }
        return list;//
    }
    //添加新闻数据
    public  void insertNews(List<News> list) {//新闻一组的插入
        if (list==null){return;}
        for (News news : list) {
            SQLiteDatabase db = helper.getWritableDatabase();//没有news表会回调onCreate()方法创建news这张表
            ContentValues values = new ContentValues();
            //把数据放到ContentValues中
            values.put("nid", news.getNid());
            values.put("title", news.getTitle());
            values.put("summary", news.getSummary());
            values.put("icon", news.getIcon());
            values.put("link", news.getLink());
            values.put("type", news.getType());
            values.put("stamp",news.getStamp());
            db.insert("news", "", values);
            db.close();
        }
    }
    //查询数据数量
    public long getCount(){
        SQLiteDatabase db=helper.getReadableDatabase();
        Cursor cursor=db.rawQuery("select count(*) from news",null);
        long len=0;
        if(cursor.moveToFirst()){
            len=cursor.getLong(0);
        }
        cursor.close();
        db.close();
        return len;
    }
    //查询数据

    /**
     * select*from 从表中查询所有字段
     * 从count以后查询offset条数据
     * @return
     */
    public ArrayList<News> queryNews(){
        ArrayList<News> newsList=new ArrayList<>();
        SQLiteDatabase db=helper.getWritableDatabase();
        String sq1="select * from news order by _id desc";
        Cursor cursor=db.rawQuery(sq1,null);
        if (cursor.moveToFirst()){
            do {
                int nid=cursor.getInt(cursor.getColumnIndex("nid"));
                String title=cursor.getString(cursor.getColumnIndex("title"));
                String summary=cursor.getString(cursor.getColumnIndex("summary"));
                String icon=cursor.getString(cursor.getColumnIndex("icon"));
                String link=cursor.getString(cursor.getColumnIndex("link"));
                int type=cursor.getInt(cursor.getColumnIndex("type"));
                String stamp=cursor.getString(cursor.getColumnIndex("stamp"));
                News news=new News(nid,title,summary,icon,link,type,stamp);
                Log.d("TAD",news+"");
                newsList.add(news);
            }while (cursor.moveToNext());
            cursor.close();
            db.close();
        }
        return newsList;
    }
}
