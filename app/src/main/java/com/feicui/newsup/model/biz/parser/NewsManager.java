package com.feicui.newsup.model.biz.parser;



import android.content.Context;
import android.location.LocationManager;
import android.util.Log;

import com.feicui.newsup.common.CommonUtil;
import com.feicui.newsup.common.SystemUtils;
import com.feicui.newsup.model.dao.NewsDBManager;
import com.feicui.newsup.model.entity.News;
import com.feicui.newsup.volleyhttp.VolleyHttp;

import java.util.List;

import edu.feicui.mnewsupdate.volley.Response;
import edu.feicui.mnewsupdate.volley.toolbox.StringRequest;


/**
 * Created by Administrator on 2017/1/7.
 */

/**
 * @param
 * @param
 * @param
 */
public class NewsManager {
    public static final int MODE_NEXT=1;//上拉加载
    public static final int MODE_PREVIOUS=2;//下拉刷新
    //从网络中加载新闻数据
    public static void loadNewsType(Context context, Response.Listener<String> listener, Response.ErrorListener errorListener){
        int ver= CommonUtil.VERSION_CODE;//news_sort?ver=版本号&imei=手机标示符
        String imei= SystemUtils.getIMEI(context);
        VolleyHttp http=new VolleyHttp(context);
        //http://118.244.212.82:9092/newsClient/news_sort?ver=1&imei=1
        http.getJsonObject(CommonUtil.APPURL+"/news_sort?ver="+ver+"&imei="+imei,listener,errorListener);
    }
    //从网络中加载新闻数据
    public static void loadNewsFromServer(Context context,int nid,int subid,int mode,Response.Listener<String> listener,Response.ErrorListener errorListener){
        int ver=CommonUtil.VERSION_CODE;
        String imei=SystemUtils.getIMEI(context);
        VolleyHttp http=new VolleyHttp(context);
        //http://118.244.212.82:9092/newsClient/news_list?ver=1&subid=1&dir=1&nid=1&stamp=20140321&cnt=20
        http.getJsonObject(CommonUtil.APPURL+"/news_list?ver="+ver+"&subid="+subid+"&dir="+mode+"&nid="+nid+"&stamp="+CommonUtil.getDate()+"&cnt="+20,listener,errorListener);
        Log.d("TAG","url="+(CommonUtil.APPURL+"/news_list?ver="+ver+"&subid="+subid+"&dir="+mode+"&nid="+nid+"&stamp="+CommonUtil.getDate()+"&cnt="+20));
    }
    //从数据库中加载新闻数据
    public static void loadNewsFromLocal(Context context, int nid, int mode, LocalResponseHandler handler){
        if (mode==1){
            List<News> newses= NewsDBManager.getNewsDBManager(context).queryNews();//从数据库中加载新闻数据
            handler.update(newses,false);
        }else if (mode==2){
            List<News> newses=NewsDBManager.getNewsDBManager(context).queryNews();
            handler.update(newses,false);
        }
    }
    //回调函数
    public interface LocalResponseHandler{
        public void update(List<News> data,boolean isClearOld);
    }
}
