package com.feicui.newsup.common;



import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/12/21.
 */

public class CommonUtil {
    /**联网的ip*/
//    public static String NETIP="118.244.212.82";
//    /**联网的路劲(端口 port(9092));newsClient是应用目录*/
//    public static String NETPATH="http://"+NETIP+":9092/newsClient";
//    /**通过SharedPreferences保存用户名键*/
//    public static final String SHARE_USER_NAME="userName";
//    /**通过SharedPreferences保存用户密码*/
//    public static final String SHARE_USER_PWD="userPwd";
//    /**通过SharedPreferences保存是否第一次登陆*/
//    public static final String SHARE_IS_FIRST_RUN="isFirstRun";
//    /**通过SharedPreferences存储路径*/
//    public static final String SHAREPATH="news_share";
    public static final String APPURL="http://118.244.212.82:9092/newsClient";
    public static final int VERSION_CODE=1;
    public static String getSystime(){
        String systime;
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy"+"年"+"MM"+"月"+"dd"+"日"+"hh:mm:ss");
        systime=dateFormat.format(new Date(System.currentTimeMillis()));
        return systime;
    }
    public static String getDate(){
        String Date;
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy"+"年"+"MM"+"月"+"dd"+"日"+"hh:mm:ss");
        Date=dateFormat.format(new Date(System.currentTimeMillis()));
        return Date;
    }

    public static boolean verifyEmail(String email){

        Pattern pattern = Pattern
                .compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)" +
                        "|(([a-zA-Z0-9\\-]+\\.)+))" +
                        "([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
        Matcher matcher=pattern.matcher(email);
        return matcher.matches();
    }
    /**
     *
     * @param password 穿过来的是密码用正则表达式来匹配格式是否正确
     * @return
     */
    //用正则表达式，判断密码是否正确
    public static boolean verifyPassword(String password){
        Pattern pattern=Pattern.compile("^[a-zA-Z0-9]{6,16}$");
        Matcher matcher=pattern.matcher(password);//用正则表达式匹配密码
        return matcher.matches();//如果匹配上了返回true
    }
}
