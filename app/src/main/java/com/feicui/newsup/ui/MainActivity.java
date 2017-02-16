package com.feicui.newsup.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.feicui.newsup.R;
import com.feicui.newsup.ui.base.MyBaseActivity;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

/**
 * Created by Administrator on 2017/1/3.
 */
public class MainActivity extends MyBaseActivity{
    public FragMentMenu fragMentMenu;//左侧菜单fragment
    public FragmentMenuRight fragmentMenuRight;//右侧菜单fragment
    public FragmenMain fragmenMain; //主页面fragment
    private FragmentLogin fragmentLogin;//登录页面的fragment
    private FragmentRegister fragmentRegister;//注册页面
    public static SlidingMenu slidingMenu;
    private ImageView iv_set,iv_user;
    private TextView tv_title;
    private FragmentForget fragmentForget;//忘记密码页面
    private FragMentMenu framentMain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //显示加载对话框
//        showLoadingDialog(this,"正在加载...",true);
        tv_title= (TextView) findViewById(R.id.textView1);
        iv_set= (ImageView) findViewById(R.id.imageView_set);
        iv_user= (ImageView) findViewById(R.id.imageView_user);
        iv_set.setOnClickListener(clickListener);
        iv_user.setOnClickListener(clickListener);
        initSlidingMenu();
        initFragment();
    }

    private void initFragment() {
        fragmenMain=new FragmenMain();
        getSupportFragmentManager().beginTransaction().replace(R.id.layout_content,fragmenMain).commit();
    }

    private void initSlidingMenu(){
        slidingMenu=new SlidingMenu(this);//创建slidingMenu对象

        //设置左右都可以滑动
        slidingMenu.setMode(SlidingMenu.LEFT_RIGHT);
        //设置滑动的屏幕范围，该设置为全屏区域都是可以滑动
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        //SlidingMenu划出时主页面显示的剩余宽度
        slidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        //使SlidingMenu附加在Activity上
        slidingMenu.attachToActivity(this,SlidingMenu.SLIDING_CONTENT);
        //设置左侧侧滑菜单布局容器
        slidingMenu.setMenu(R.layout.layout_menu);
        //设置右滑侧滑菜单布局容器
        slidingMenu.setSecondaryMenu(R.layout.layout_menu_right);
        //创建左侧Fragment对象
        fragMentMenu=new FragMentMenu();
        //创建右侧Fragment对象
        fragmentMenuRight = new FragmentMenuRight();
        //替换左滑菜单布局容器里的Fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.layout_menu,fragMentMenu).commit();
        //替换右滑菜单布局容器里的Fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.layout_menu_right,fragmentMenuRight).commit();
    }
    private View.OnClickListener clickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.imageView_set://点击左边按钮，显示左拉菜单
                    slidingMenu.showMenu();
                    break;
                case R.id.imageView_user://点击右边按钮，显示右拉菜单
                    slidingMenu.showSecondaryMenu();
                    break;
            }
        }
    };
    public void setTitle(String title){
        if (title!=null){
            tv_title.setText(title);
        }
    }
    //显示登陆页面
    public void showFragmentLogin() {
        setTitle("登录");
        slidingMenu.showContent();//显示内容
        if (fragmentLogin==null){
            fragmentLogin=new FragmentLogin();
        }
        //将MainActivity里的layout_content所有fragment替换成fragmentLogin
        getSupportFragmentManager().beginTransaction().replace(R.id.layout_content,fragmentLogin).commit();
    }

    public void showFragmentRegister() {
        setTitle("注册");
        slidingMenu.showContent();
        if (fragmentRegister==null){
            fragmentRegister=new FragmentRegister();
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.layout_content,fragmentRegister).commit();
    }

    public void showFramentForget() {
        setTitle("忘记密码");
        slidingMenu.showContent();
        if (fragmentForget==null){
            fragmentForget=new FragmentForget();
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.layout_content,fragmentForget).commit();

    }
    //显示新闻列表的Fragment
    public void showFramentMain() {
        setTitle("资讯");
        slidingMenu.showContent();
        if (framentMain==null){
            framentMain=new FragMentMenu();
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.layout_content,framentMain).commit();
    }
}
