package com.feicui.newsup.model.biz.parser;

import android.content.Context;
import android.util.Log;

import com.feicui.newsup.common.CommonUtil;
import com.feicui.newsup.common.SystemUtils;
import com.feicui.newsup.volleyhttp.VolleyHttp;

import edu.feicui.mnewsupdate.volley.Response;

/**
 * Created by Administrator on 2017/1/10.
 */

public class UserManager {
    private Context context;
    private String imei;
    private static UserManager userManager;
    private UserManager(Context context){
        this.context=context;
        imei= SystemUtils.getIMEI(context);
    }
    //静态方式实现单例模式
    public static UserManager getInstance(Context context){
        if (userManager==null){
            synchronized (UserManager.class){
                if (userManager==null){
                    userManager=new UserManager(context);
                }
            }
        }
        return userManager;
    }

    /**
     * user_login?ver=版本号&uid=用户名&pwd=密码&device=0  这里0表示移动端，1表示PC端
     * @param context
     * @param listener 成功获得数据回调
     * @param errorListener 获取数据失败回调
     * @param args 可变参数 例如：(String i,String j,String k,String l...)==(String... args)
     */
    public void login(Context context, Response.Listener<String> listener,Response.ErrorListener errorListener,String... args){

        //登录：http://118.244.212.82:9092/newsClient/user_login?ver=1&uid=XiaoYi001&pwd=121212&device=0
        Log.d("TAG","Url="+CommonUtil.APPURL+"/user_login?ver="+args[0]+"&uid="+args[1]+"&pwd="+args[2]+"&device="+args[3] );
        new VolleyHttp(context).getJsonObject(CommonUtil.APPURL+"/user_login?ver="+args[0]+"&uid="+args[1]+"&pwd="+args[2]+"&device="+args[3],listener,errorListener);
    }

    /**
     * 注册
     * @param context
     * @param listener
     * @param errorListener
     * @param args
     */
    public void register(Context context,Response.Listener<String> listener,Response.ErrorListener errorListener,String... args){
        //http://118.244.212.82:9092/newsClient/user_register?ver=1&uid=weiqun7474&email=747474@qq.com&pwd=747474
        new VolleyHttp(context).getJsonObject(CommonUtil.APPURL+"/user_register?ver="+args[0]+"&uid="+args[1]+"&email="+args[2]+"&pwd="+args[3],listener,errorListener);
    }

    /**
     * 忘记密码
     * @param context
     * @param listener
     * @param errorListener
     * @param args args[0]版本号 args[1]注册邮箱
     */
    public void forget(Context context,Response.Listener<String> listener,Response.ErrorListener errorListener,String... args){
        new VolleyHttp(context).getJsonObject(CommonUtil.APPURL+"/user_forgetpass?ver="+args[0]+"&email="+args[1],listener,errorListener);
    }
}
