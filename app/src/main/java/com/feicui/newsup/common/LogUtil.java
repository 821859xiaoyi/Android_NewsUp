package com.feicui.newsup.common;

import android.util.Log;

/**
 * 日志管理
 * Created by Administrator on 2016/12/21.
 */

public class LogUtil {
    public static final String TAG="新闻客户端";
    //调试日志的开关
    public static boolean isDebug=true;
    public static void d(String tag,String msg){
        if (isDebug) {
            Log.d(tag, msg);
        }
    }
    public static void d(String msg){
        if (isDebug){
            Log.d(LogUtil.TAG,msg);
        }
    }
}
