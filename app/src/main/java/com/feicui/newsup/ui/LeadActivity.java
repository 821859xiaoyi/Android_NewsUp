package com.feicui.newsup.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.feicui.newsup.R;
import com.feicui.newsup.ui.adapter.LeadImgAdapter;
import com.feicui.newsup.ui.base.MyBaseActivity;

import java.util.ArrayList;
import java.util.List;

public class LeadActivity extends MyBaseActivity {
    private ViewPager viewPager;
    private ImageView[] points=new ImageView[4];//四个滑动时运行的点
    private LeadImgAdapter adapter;
    private List<View> list=new ArrayList<>();//放了四张图片的页面
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lead);
        //在data/data目录下
        SharedPreferences sp=getSharedPreferences("runconfig",MODE_PRIVATE);
        boolean isFirstRun=sp.getBoolean("isFirstRun",true);
        if (!isFirstRun){
            openActivity(LogoActivity.class);
            finish();
            return;
        }
        points[0]= (ImageView) findViewById(R.id.iv_p1);
        points[1]= (ImageView) findViewById(R.id.iv_p2);
        points[2]= (ImageView) findViewById(R.id.iv_p3);
        points[3]= (ImageView) findViewById(R.id.iv_p4);
        setPoint(0);//给每个点设置透明度
        viewPager= (ViewPager) findViewById(R.id.viewpager);//viewGroup容器
        //放四个页面,把布局解析成对象放入集合
        list.add(getLayoutInflater().inflate(R.layout.lead_1,null));
        list.add(getLayoutInflater().inflate(R.layout.lead_2,null));
        list.add(getLayoutInflater().inflate(R.layout.lead_3,null));
        list.add(getLayoutInflater().inflate(R.layout.lead_4,null));
        adapter=new LeadImgAdapter(list);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener(){

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setPoint(position);
                if (position>=3){//当滑动到最后一个页面
                    openActivity(LogoActivity.class);
                    finish();
                    //data/data目录下
                    SharedPreferences sp=getSharedPreferences("runconfig",MODE_PRIVATE);
                    SharedPreferences.Editor editor=sp.edit();
                    editor.putBoolean("isFirstRun",false);
                    editor.commit();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setPoint(int index) {
        for (int i=0;i<points.length;i++){//遍历每一个点
            if (index==i){
                points[i].setAlpha(1.0f);
            }else{
                points[i].setAlpha(0.5f);
            }
        }

    }
}
