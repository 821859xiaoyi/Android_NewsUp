package com.feicui.newsup.common;

import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

/**
 * Created by Administrator on 2017/1/6.
 */

public class SystemUtils {
    private static SystemUtils systemUtils;
    private Context context;
    private TelephonyManager telephonyManager;//手机管理和手机运营商的
    private ConnectivityManager connectivityManager;//管理网络连接
    private LocationManager locationManager;//提供一系列方法来处理地理位置的相关问题
    private SystemUtils(Context context){
        this.context=context;
        telephonyManager= (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        connectivityManager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        locationManager= (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }
    public static SystemUtils getInstance(Context context){//构造方法是private修饰的，强制要求调用单例模式创建对象
        if (systemUtils == null) {
            systemUtils=new SystemUtils(context);
        }
        return systemUtils;
    }
    //判断网络连接
    public boolean isNetConn(){
        //NetworkInfo类包含对wifi和mobile两种网络模式连接的详细信息
        NetworkInfo info=connectivityManager.getActiveNetworkInfo();//getActiveNetworkInfo()方法获取当前可用网络信息
        if (info!=null&&info.isConnected()){//判断当前网络是否存在，并可用于数据传输
            return true;
        }
        return false;
    }
    //获取手机的IMEI值  International Mobile Equipment Identity国际移动电话设备识别码
    public static String getIMEI(Context context){
        TelephonyManager telephonyManager= (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }
}
