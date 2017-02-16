package com.feicui.newsup.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.feicui.newsup.R;
import com.feicui.newsup.ui.base.MyBaseActivity;

public class LogoActivity extends MyBaseActivity {
    private ImageView iv_logo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);
        iv_logo= (ImageView) findViewById(R.id.iv_logo);
        //解析动画文件，变成Animation对象
        Animation animation= AnimationUtils.loadAnimation(this,R.anim.logo);
        animation.setAnimationListener(animationListener);//设置动画监听
        iv_logo.startAnimation(animation);//启动动画
    }
    private Animation.AnimationListener animationListener=new Animation.AnimationListener() {
        //动画开始
        @Override
        public void onAnimationStart(Animation animation) {

        }
        //动画结束
        @Override
        public void onAnimationEnd(Animation animation) {
            openActivity((MainActivity.class));
            finish();

        }
        //重复动画
        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };
}
