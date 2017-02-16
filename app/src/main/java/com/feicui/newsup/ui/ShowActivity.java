package com.feicui.newsup.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.feicui.newsup.R;
import com.feicui.newsup.model.entity.News;

public class ShowActivity extends Activity {

    private ProgressBar progressBar;
    private WebView webView;
    private News news;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        progressBar= (ProgressBar) findViewById(R.id.progressBarl);
        webView= (WebView) findViewById(R.id.webView1);
        news= (News) getIntent().getSerializableExtra("newsitem");
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//网页缓存
        //设置加载全部后的监听
        webView.setWebViewClient(viewclisnt);
        //设置当加载时的监听
        webView.setWebChromeClient(chromeClient);
        //设置链接
        webView.loadUrl(news.getLink());
    }
    private WebViewClient viewclisnt=new WebViewClient(){
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            webView.loadUrl(url);
            return true;//返回true不跳转系统自带浏览器，只在activity里的webview里显示
        }
    };
    private WebChromeClient chromeClient=new WebChromeClient(){
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            progressBar.setProgress(newProgress);
            if (progressBar.getProgress()==100){//浏览网页加载进度，显示进度
                progressBar.setVisibility(View.GONE);
            }
        }
    };

}
