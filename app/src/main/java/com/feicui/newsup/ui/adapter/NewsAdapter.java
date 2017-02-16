package com.feicui.newsup.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.feicui.newsup.R;
import com.feicui.newsup.common.LoadImage;
import com.feicui.newsup.model.entity.News;
import com.feicui.newsup.ui.base.MyBaseAdapter;

/**
 * Created by Administrator on 2016/12/28.
 */

public class NewsAdapter extends MyBaseAdapter<News> {
    //加载图片之前的默认图片
    private Bitmap defaultBitmap;
    private ListView listView;
    //图片工具类
    private LoadImage loadImage;
    public NewsAdapter(Context context, ListView lv) {
        super(context);
        //获取drawable下cccc图片，变成Bitmap类型
        defaultBitmap= BitmapFactory.decodeResource(context.getResources(), R.drawable.cccc);
        listView=lv;
        loadImage=new LoadImage(context,listener);
    }
    private LoadImage.imageLoadListener listener=new LoadImage.imageLoadListener() {
        @Override
        //在网络上获取到数据就执行此回调方法
        public void imageLoadOK(Bitmap bitmap, String url) {
            //类似于findViewById()得到item里的ImageView控件
            ImageView iv= (ImageView) listView.findViewWithTag(url);
            if (iv!=null){
                iv.setImageBitmap(bitmap);
            }
        }
    };
    /**
     * @param position 新闻条目的位置
     * @param convertView item布局就是我们写的相对布局
     * @param parent ListView
     * @return 组装好的convertView(将数据放到convertView对应的控件上)
     *
     */

    @Override
    public View getMyView(int position, View convertView, ViewGroup parent) {
        HoldView holdView;
        if (convertView==null){//当convertView为空时，布局初始化，控件初始化
            convertView=inflater.inflate(R.layout.item_list_news,null);
            holdView=new HoldView(convertView);
            convertView.setTag(holdView);
        }else {//不为空时，直接获取之前存储holdView(holdView对象中存储了三个控件)
            holdView= (HoldView) convertView.getTag();
        }
        //list是MyBaseAdapter存储新闻集合
        News news=myList.get(position);//获取每一条新闻
        holdView.tv_title.setText(news.getTitle());//将数据放到控件上
        holdView.tv_text.setText(news.getSummary());
        holdView.tv_from.setText(news.getStamp());
        holdView.iv_list_image.setImageBitmap(defaultBitmap);//默认图片
        //得到图片地址
        String urlImage=news.getIcon();
        //给每个图片控件存入编号，以图片地址作为标识
        holdView.iv_list_image.setTag(urlImage);
        /**
         * 获取图片
         * 第一次:从网络上获取，存到softReference里，再存到cache目录
         * 第一次之后：1.从cache目录里拿 2.从softReference里拿 3.从网络上获取
         *
         */
        Bitmap bitmap=loadImage.getBitmap(urlImage);
        if (bitmap!=null){
            holdView.iv_list_image.setImageBitmap(bitmap);
        }
        return convertView;
    }
    //存储convertView上控件
    public class HoldView{
        public ImageView iv_list_image;//新闻icon
        public TextView tv_title;//新闻title
        public TextView tv_text;//新闻summary
        public TextView tv_from;//新闻时间
        /**
         * @param view=convertView
         * */
        public HoldView(View view){
            //获得convertView上的imageView
            this.iv_list_image= (ImageView) view.findViewById(R.id.imageView1);
            //获得convertView上的TextView(新闻标题控件)
            this.tv_title= (TextView) view.findViewById(R.id.textView1);
            //获得convertView上的TextView(新闻摘要控件)
            this.tv_text= (TextView) view.findViewById(R.id.textView2);
            tv_from= (TextView) view.findViewById(R.id.textView3);
        }
    }
}
