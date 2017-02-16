package com.feicui.newsup.ui.base;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.feicui.newsup.MyApplication;
import com.feicui.newsup.R;
import com.feicui.newsup.common.LogUtil;
import com.feicui.newsup.ui.FragMentMenu;


/**
 * 父类activity用来调式打印activity的生命周期和activity动画的进入和退出
 *
 * Created by Administrator on 2016/12/28.
 */

public class MyBaseActivity extends AppCompatActivity {
    //属性
    protected MyApplication app;//全局存储
    protected Dialog dialog;//对话框
    protected FrameLayout layout_screenoff;//关闭时动画
    protected int screen_w,screen_h;//屏幕的宽，高
    /**创建*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.d(LogUtil.TAG,getClass().getSimpleName()+"onCreate()");
        app= (MyApplication) getApplication();
        screen_w=getWindowManager().getDefaultDisplay().getWidth();
        screen_h=getWindowManager().getDefaultDisplay().getHeight();
    }
    /**开始*/
    @Override
    protected void onStart() {
        super.onStart();
        LogUtil.d(LogUtil.TAG,getClass().getSimpleName()+"onStart()");
    }
    /**重启*/
    @Override
    protected void onRestart() {
        super.onRestart();
        LogUtil.d(LogUtil.TAG,getClass().getSimpleName()+"onRestart()");
    }
    /**暂停*/
    @Override
    protected void onPause() {
        super.onPause();
        LogUtil.d(LogUtil.TAG,getClass().getSimpleName()+"onPause()");
    }
    /**停止*/
    @Override
    protected void onStop() {
        super.onStop();
        LogUtil.d(LogUtil.TAG,getClass().getSimpleName()+"onStop()");
    }
    /**销毁*/
    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.d(LogUtil.TAG,getClass().getSimpleName()+"onDestroy()");
    }
    //封装了跳转的方法
    //普通跳转
    public void openActivity(Class<?> pClass){
        openActivity(pClass,null);
    }
    //跳转传递数据
    public void openActivity(Class<?> pClass,Bundle pbBundle){
        openActivity(pClass,pbBundle,null);
    }
    //跳转带动画,数据，启动其他程序
    public void openActivity(Class<?> pClass, Bundle pbBundle, Uri uri){
        Intent intent=new Intent(this,pClass);
        //setData启动其他程序，比如打电话
        if (pbBundle!=null){
            intent.putExtras(pbBundle);
        }
        startActivity(intent);
        overridePendingTransition(R.anim.screen_right_in,R.anim.screen_down_out);
    }
    private Toast toast;
    public void showToast(int resID){
        showToast(getString(resID));
    }

    public void showToast(String string) {
        if (toast==null){
            toast=Toast.makeText(this,string,Toast.LENGTH_SHORT);
        }
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setText(string);
        toast.show();
    }
    /**
     * @param context 上下文
     * @param msg 例如：正在加载中。。。
     * @param cancelable 是否退出dialog
     */
    public void showLoadingDialog(Context context,String msg,boolean cancelable){
        //获得布局加载器
        LayoutInflater inflater=LayoutInflater.from(context);
        //将布局解析成一个线性布局对象
        View view=inflater.inflate(R.layout.dialog_loading,null);
        //拿到此布局的线性布局容器
        //LinearLayout layout= (LinearLayout) view.findViewById(R.id.dialog_view);
        //在view的线性布局上拿imageview
        ImageView iv= (ImageView) view.findViewById(R.id.iv_dialogloading_img);
        //在view的线性布局上拿textview
        TextView tv= (TextView) view.findViewById(R.id.tv_dialogloading_msg);
        //加载旋转动画
        Animation animation= AnimationUtils.loadAnimation(context,R.anim.loading_animation);
        //设置动画(启动动画)
        iv.startAnimation(animation);
        if (msg!=null){//自定义dialog提示信息
            tv.setText(msg);//例如：正在加载...
        }
        //自定义一个对话框,需要自定义对话框的样式
        dialog=new Dialog(context,R.style.loading_dialog);
        //cancelable设置true,点击回退，退出dialog
        dialog.setCancelable(cancelable);
        //将线性布局放到dialog中，给此线性布局设置width,height
        dialog.setContentView(view,new LinearLayout.LayoutParams
                (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        dialog.show();
    }
    public void cancelDialog(){
        if (dialog!=null){
            dialog.dismiss();
        }
    }
}
