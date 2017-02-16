package com.feicui.newsup.common;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 第一种方法，连接网络获取数据
 * Created by Administrator on 2016/12/23.
 */

public class HttpUtil {
    public static String HttpURLConnectionNet(String path) throws IOException {
        String str = "";
        URL url;
        //建立连接
        url = new URL(path);//将网络路径传入
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();//强制转换
        //得到输入流(获取数据)
        InputStream in = httpURLConnection.getInputStream();//将获取到的数据变成字节输入流
        byte[] ch = new byte[1024];//每次读1k
        int count = 0;//实际读取到的字节量
        while ((count = in.read(ch)) != -1) {//没读到文件末尾继续读
            str += new String(ch, 0, count);//将读取到的数据放入str字符串中
        }
        in.close();
        return str;
    }
    /**
     * 第二种方法，连接网络获取数据
     * Created by Administrator on 2016/12/23.
     */
    //连接网络，返回数据
    public static String httpGet(String url) {
        //获取HttpClient对象
        // HttpClient httpClient=new DefaultHttpClient();
        try {
            HttpClient httpClient = getHttpClicent();
            //发送get请求，获取HttpGet对象
            HttpGet httpGet = new HttpGet(url);
            //从服务器获取响应(状态行，消息头，响应实体)
            HttpResponse response = httpClient.execute(httpGet);
            //获取响应实体，此实体包存储了数据
            HttpEntity entity=response.getEntity();
            //把响应实体类型变变成字符串类型
            String resultStr= EntityUtils.toString(entity,"CBK");
            return resultStr;
        } catch (IOException e) {
            e.printStackTrace();
            return "null";
        }
    }

    private static HttpClient mHttpClient; //返回一个HttpClient对象
    private static int Timeout = 5000;//设置超时时间
    private static int MaxTotalConnections = 8;//最大连接数量

    private static synchronized HttpClient getHttpClicent() {//此为网络连接方法，线程安全
        if (mHttpClient == null) {
            //建立参数对象
            HttpParams params = new BasicHttpParams();
            //设置连接池中超时时间
            ConnManagerParams.setTimeout(params, Timeout);
            ConnManagerParams.setMaxTotalConnections(params, MaxTotalConnections);
            HttpConnectionParams.setConnectionTimeout(params,Timeout);
            mHttpClient = new DefaultHttpClient(params);//创建HttpClient对象
        }
        return mHttpClient;
    }
}
