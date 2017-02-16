package com.feicui.newsup.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.SoftReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * 获取图片的公共类
 *
 * 存图片
 * 1.从网络上获取图片 2.存到SoftReferences里 3.存到cache目录里
 *
 *  取图片
 * 1.从cache目录里取图片 2.从SoftReference里取图片 3.从网络上获取图片
 *
 * Created by Administrator on 2016/12/29.
 */

/**SoftReference可以避免内存溢出 OOM; 当内存不够用时，GC会回收SoftReference所引用的对象*/
public class LoadImage {
    private static Map<String,SoftReference<Bitmap>> softReferences=new HashMap<String,SoftReference<Bitmap>>();
    private Context context;
    public interface imageLoadListener{
        void imageLoadOK(Bitmap bitmap, String url);
    }
    private imageLoadListener listener;
    public LoadImage(Context context,imageLoadListener listener){//对成员变量初始化
        this.context=context;
        this.listener=listener;
    }
    /**
     * 先查看Cache目录里有没有，再看SoftReference缓存里有没有，都没有只能从网上请求图片
     */public Bitmap getBitmap(String url){
        Bitmap bitmap=null;
        if (url==null||url.length()<=0){
            return bitmap;
        }
        //先查看Cache目录里有没有
        bitmap=getBitmapFromCache(url);
        if (bitmap!=null){
            return bitmap;
        }
        //再看SoftReference缓存里有没有
        bitmap=getBitmapSoftReference(url);
        if (bitmap!=null){
            return bitmap;
        }
        //都没有只能从网络上请求图片
        getBitmapAsync(url);
        return bitmap;
    }


    /**
     * 从缓存中获取图片
     * @param url 图片路劲
     * @return 缓存文件中的图片
     */
    private Bitmap getBitmapFromCache(String url) {
        String name=url.substring(url.lastIndexOf("/")+1);
        //获取cache目录
        File cacheDir=context.getCacheDir();
        //得到该文件下所有文件
        File[] files=cacheDir.listFiles();
        if (files==null){
            return null;
        }
        //图片文件
        File bitFile=null;
        for(File file:files){//遍历cache目录下的所有图片
            if (file.getName().equals(name)){//将cache目录下的图片名称与我们传过来的图片名称进行匹配对比
                bitFile=file;
                break;
            }
        }
        if (bitFile==null){
            return null;
        }
        //把找到的文件 转换为bitmap
        Bitmap bitmap=BitmapFactory.decodeFile(bitFile.getAbsolutePath());
        return bitmap;
    }
    /**
     * 得到内存的图片
     */
    private Bitmap getBitmapSoftReference(String url) {
        Bitmap bitmap=null;
        //如果存在Map根据Key得到Value
        if (softReferences.containsKey(url)){
            bitmap=softReferences.get(url).get();//获得softReference里的图片
        }
        return bitmap;
    }
    private void getBitmapAsync(String url){
        //异步加载
        ImageAsyncTask imageAsyncTask=new ImageAsyncTask();
        //执行加载
        imageAsyncTask.execute(url);//传入地址
    }
    private class ImageAsyncTask extends AsyncTask<String,Void,Bitmap>{
        private String url;

        @Override
        protected Bitmap doInBackground(String... params) {
            url=params[0];
            Bitmap bitmap=null;
            try {
                URL url=new URL(params[0]);
                HttpURLConnection conn= (HttpURLConnection) url.openConnection();
                //得到数据的输入字节流
                InputStream is=conn.getInputStream();
                //得到图片
                bitmap= BitmapFactory.decodeStream(is);
                //存储到SoftReference中
                saveSoftReference(params[0],bitmap);
                //存储到cache目录中
                saveCacheFile(params[0],bitmap);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }
        /**
         * @param param url=网址地址
         * @param bitmap 来自网络的图片
         */
        private void saveCacheFile(String param, Bitmap bitmap) {
            String name=param.substring(param.lastIndexOf("/")+1);
            File cacheDir=context.getCacheDir();//获取路径data/data/application/cache
            if (!cacheDir.exists()){//如果不存在则创建
                cacheDir.mkdir();
            }
            //建立输出流
            OutputStream outputStream=null;
            try {
                //例如在cache:cache目录里   创建name:略
                outputStream=new FileOutputStream(new File(cacheDir,name));
                //存储图片到文件
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);//对图片进行压缩，100为不压缩，图片格式为后缀
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }finally {
                try {
                    if (outputStream!=null){
                        outputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        /**
         * @param param url=网址地址
         * @param bitmap 来自网络的图片
         */
        private void saveSoftReference(String param, Bitmap bitmap) {
            SoftReference<Bitmap> softbitmap=new SoftReference<Bitmap>(bitmap);//将图片bitmap存储到softbitmap里
            //再将softbitmap存储到softReference的HashMap中
            softReferences.put(param,softbitmap);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (listener!=null){
                //获得图片后，返回图片和路劲
                listener.imageLoadOK(bitmap,url);
            }
        }
    }
}
