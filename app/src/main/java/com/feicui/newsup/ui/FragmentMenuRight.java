package com.feicui.newsup.ui;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.feicui.newsup.R;
import com.feicui.sharesdk.OnekeyShare;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * Created by Administrator on 2017/1/4.
 */

public class FragmentMenuRight extends Fragment {
    private View view;
    private RelativeLayout relativeLayout_unlogin;
    private RelativeLayout relativeLayout_logined;
    private boolean islogin;
    private SharedPreferences sharedPreferences;
    private ImageView imageView1, iv_pic;
    private TextView textView1, updateTv;
    private String[] str;
//    DownloadCompleteReceiver receiver;
    /**
     * 分享到微信
     */
    private ImageView iv_friend;
    /**
     * 分享到QQ
     */
    private ImageView iv_qq;
    /**
     * 分享到朋友圈
     */
    private ImageView iv_friends;
    /**
     * 分享到微博
     */
    private ImageView iv_weibo;

    /**
     * 分享位置规定
     */
    public static final int WEBCHAT = 1, QQ = 2, WEBCHATMOMENTS = 3, SINA = 4;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_menu_right, container, false);
        sharedPreferences = getActivity().getSharedPreferences("userinfo",
                Context.MODE_PRIVATE);
        islogin = sharedPreferences.getBoolean("islogin", false);
        relativeLayout_unlogin = (RelativeLayout) view
                .findViewById(R.id.relativelayout_unlogin);
        relativeLayout_logined = (RelativeLayout) view
                .findViewById(R.id.relativelayout_logined);
        //头像图标
        imageView1 = (ImageView) view.findViewById(R.id.imageView1);
        //立刻登录
        textView1 = (TextView) view.findViewById(R.id.textView1);
        updateTv = (TextView) view.findViewById(R.id.update_version);
        // 初始化分享功能控件
        iv_friend = (ImageView) view.findViewById(R.id.fun_friend);
        iv_qq = (ImageView) view.findViewById(R.id.fun_qq);
        iv_friends = (ImageView) view.findViewById(R.id.fun_friends);
        iv_weibo = (ImageView) view.findViewById(R.id.fun_weibo);

        iv_friend.setOnClickListener(l);
        iv_qq.setOnClickListener(l);
        iv_friends.setOnClickListener(l);
        iv_weibo.setOnClickListener(l);

        imageView1.setOnClickListener(l);
        textView1.setOnClickListener(l);

        relativeLayout_logined.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), ActivityUser.class);
                startActivity(intent);
            }
        });
        return view;
    }
    private View.OnClickListener l=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.imageView1:
                case R.id.textView1:
                    //当点击时在MainActivity里显示FragmentLogin的布局
                    ((MainActivity)getActivity()).showFragmentLogin();
                    break;
                // 判断分享
                case R.id.fun_friend:// 分享到微信
                    showShare(WEBCHAT);
                    break;
                case R.id.fun_qq:// 分享到 qq
                    showShare(QQ);
                    break;
                case R.id.fun_friends:// 分享到盆友圈
                    showShare(WEBCHATMOMENTS);
                    break;
                case R.id.fun_weibo:// 分享到微博
                    showShare(SINA);
                    break;
            }
        }
    };
    /**
     * 全部分享界面显示
     *
     * @param platforms 分享的位置
     */
    private void showShare(int platforms) {
        ShareSDK.initSDK(getActivity());
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(getString(R.string.share));
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("Tower新闻客户端");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("Tower新闻客户端是一款好的新闻软件");

        switch (platforms) {
            case WEBCHAT:
                oks.setPlatform(Wechat.NAME);
                break;
            case WEBCHATMOMENTS:
                oks.setPlatform(WechatMoments.NAME);
                break;
            case QQ:
                oks.setPlatform(cn.sharesdk.tencent.qq.QQ.NAME);
                break;
            case SINA:
                oks.setPlatform(SinaWeibo.NAME);
                break;
        }
        // 启动分享GUI
        oks.show(getActivity());
    }

}
