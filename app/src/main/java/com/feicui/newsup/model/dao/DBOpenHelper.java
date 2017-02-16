package com.feicui.newsup.model.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Administrator on 2016/12/27.
 */

public class DBOpenHelper extends SQLiteOpenHelper{
    //当创建DBOpenHelper对象时，调用super(context, "newsdb.db",null,1)创建newsdb.db文件
    public DBOpenHelper(Context context) {
        super(context, "newsdb.db",null,2);
    }
    //创建表时会调用此方法
    //text存储字符串，最大长度为2^31-1个字符
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table news(_id integer primary key autoincrement," +
                "nid integer,title text," +
                "summary text,icon text," +
                "link text,type integer,stamp text)");

        db.execSQL("create table type(_id integer primary key autoincrement," +
                "subid integer,subgroup text)");
        Log.d("TAG","onCreate()");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
