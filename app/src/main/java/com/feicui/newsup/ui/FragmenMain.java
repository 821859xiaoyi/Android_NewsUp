package com.feicui.newsup.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.feicui.newsup.R;
import com.feicui.newsup.common.CommonUtil;
import com.feicui.newsup.common.SystemUtils;
import com.feicui.newsup.model.biz.parser.NewsManager;
import com.feicui.newsup.model.biz.parser.ParserNews;
import com.feicui.newsup.model.dao.NewsDBManager;
import com.feicui.newsup.model.entity.News;
import com.feicui.newsup.model.entity.SubType;
import com.feicui.newsup.ui.adapter.NewsAdapter;
import com.feicui.newsup.ui.base.NewsTypeAdapter;
import com.feicui.newsup.view.HorizontalListView;
import com.feicui.newsup.view.xlistview.XListView;

import java.util.List;

import edu.feicui.mnewsupdate.volley.Response;
import edu.feicui.mnewsupdate.volley.VolleyError;

/**
 * Created by Administrator on 2017/1/5.
 */

public class FragmenMain extends Fragment {
    private View view;//fragment里的视图
    //新闻分类Adapter
    private NewsTypeAdapter newsTypeAdapter;
    //新闻列表的adaper
    private NewsAdapter newsAdapter;
    //水平listview
    private HorizontalListView hl_type;
    //竖直的下拉刷新ListView
    private XListView listView;
    //数据库管理类
    private NewsDBManager dbManager;
    //当前fragment绑定的activity
    private MainActivity mainActivity;
    //mode代表上拉下拉，上拉为1，下拉为2
    private int mode;
    //新闻子类id默认为1
    private int subid=2;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_news_list,container,false);
        //初始化数据库管理类
        //getNewsDBManager(...)获取NewsDBManager对象,创建了一个newsdb.db；getActivity()在FragmenMain里获取依附的Activity
        dbManager= NewsDBManager.getNewsDBManager(getActivity());
        mainActivity= (MainActivity) getActivity();//getActivity()在FragmenMain里获取依附的Activity
        hl_type= (HorizontalListView) view.findViewById(R.id.hl_type);//获取布局里的水平listview
        if (hl_type!=null){
            newsTypeAdapter=new NewsTypeAdapter(getActivity());//构建适配器
            hl_type.setAdapter(newsTypeAdapter);//设置适配器
            hl_type.setOnItemClickListener(itemClickListener);
        }
        //发送网络请求，加载新闻分类数据
        loadNewsType();
        //初始化竖直XListView
        listView= (XListView) view.findViewById(R.id.listview);
        if (listView!=null){
            //初始化新闻列表适配器
            newsAdapter=new NewsAdapter(getActivity(),listView);
            //设置适配器
            listView.setAdapter(newsAdapter);
            //激活上拉加载
            listView.setPullLoadEnable(true);
            //激活下拉刷新
            listView.setPullRefreshEnable(true);
        }
        //发送网络请求，加载新闻列表数据
        loadNextNews(true);
        //设置上拉加载和下拉刷新监听器
        listView.setXListViewListener(listViewListener);
        //设置listView的监听器
        listView.setOnItemClickListener(newsItemListener);
        //加载数据时，dialog旋转
        mainActivity.showLoadingDialog(mainActivity,"正在加载...",true);
        return view;
    }

    private void loadPreNews() {
        //当条目不足两条时，不去加载
        if (listView.getCount()-2<0){
            return;
        }
        //获得nid的值
        int nid=newsAdapter.getItem(listView.getLastVisiblePosition()-2).getNid();
        mode=NewsManager.MODE_PREVIOUS;
//        if (SystemUtils.getInstance(getActivity()).isNetConn());
        if (true){
            NewsManager.loadNewsFromServer(getActivity(),nid,subid,mode,new VolleyNewsResponseHandle(),new VolleyErrorResponeHandler());

        }else{
            NewsManager.loadNewsFromLocal(getActivity(),nid,mode,new MyLoalResponseHandle());
        }
    }

    private void loadNextNews(boolean isNewsType) {
        int nid=0;
        if (!isNewsType){
            if (newsAdapter.getCount()>0){
                nid=newsAdapter.getItem(0).getNid();
            }
        }
        //从网络上加载数据
        mode=NewsManager.MODE_NEXT;
//        SystemUtils.getInstance(getActivity()).isNetConn();//只适用于真机
        if (true){
            //从网络上加载数据
            NewsManager.loadNewsFromServer(getActivity(),nid,subid,mode,new VolleyNewsResponseHandle(),new VolleyErrorResponeHandler());
        }else{
            //如果网络没连接，则从本地数据库中获取
            NewsManager.loadNewsFromLocal(getActivity(),nid,mode,new MyLoalResponseHandle());
        }
    }

    //加载新闻分类（军事，社会）
    private void loadNewsType() {
        if (dbManager.pueryNewsType().size()!=0){//如果数据库里有，从数据里获取
            //通过数据库获取响应的数据
            List<SubType> subTypes= (List<SubType>) dbManager.pueryNewsType();
            //更新adaper
            newsTypeAdapter.appendData(subTypes,true);//把数据添加到newsTypeAdapter集合中，true清除旧数据
            newsTypeAdapter.update();
        }else{//网络是连接的，则联网获取
//            if (SystemUtils.getInstance(getActivity()).isNetConn()){//此方法只适用于真机
            if (true){
                //Volley框架网络获取数据;VolleyTypeResponseHandler()成功获取数据；VolleyErrorResponeHandler()获取数据失败
                NewsManager.loadNewsType(getActivity(),new VolleyTypeResponseHandler(),new VolleyErrorResponeHandler());
            }
        }
    }
    //从本地加载数据(当从数据库中加载数据时回调此接口)
    class MyLoalResponseHandle implements NewsManager.LocalResponseHandler{

        @Override
        public void update(List<News> data, boolean isClearOld) {//回调函数
            newsAdapter.appendData(data,isClearOld);
            newsAdapter.update();
        }
    }
    //从网络上获取新闻分类数据
    class VolleyTypeResponseHandler implements Response.Listener<String>{

        @Override
        //联网获取数据成功回调onResponse(String response) 数据存储在response (即获取到的json字符创在response里)
        public void onResponse(String response) {
            Log.d("TAG","response="+response);
            List<SubType> subTypes= ParserNews.parserTypeList(response);//解析json数据
            dbManager.saveNewsType(subTypes);//存入数据库
            Log.d("TAG","subTypes="+subTypes);
            newsTypeAdapter.appendData(subTypes,true);//放入适配器的集合中
            newsTypeAdapter.update();//更新适配器

        }
    }
    //从网络上获取新闻列表数据
    class VolleyNewsResponseHandle implements Response.Listener<String> {
        @Override
        public void onResponse(String response) {

            //使用Gson解析json数据
            List<News> newses=ParserNews.parserNewsList(response);//解析成News类型的集合返回响应
            //从网络上获取收到之后放入数据库中
            dbManager.insertNews(newses);

            //mode初始值为NewsManager.MODE_NEXT
            boolean isClearOld=mode==NewsManager.MODE_NEXT?true:false;
            newsAdapter.appendData(newses,isClearOld);
            mainActivity.cancelDialog();
            newsAdapter.update();
        }
    }
    class VolleyErrorResponeHandler implements Response.ErrorListener{

        @Override
        public void onErrorResponse(VolleyError error) {
            mainActivity.cancelDialog();
            Toast.makeText(getActivity(), "服务器连接异常", Toast.LENGTH_SHORT).show();
        }
    }
    private AdapterView.OnItemClickListener itemClickListener=new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            SubType type= (SubType) parent.getItemAtPosition(position);
            subid=type.getSubid();
            newsTypeAdapter.setSelectPostion(position);
            newsTypeAdapter.update();
            loadNextNews(true);
            mainActivity.showLoadingDialog(getActivity(),"正在加载...",true);
        }
    };
    //新闻列表item点击
    private AdapterView.OnItemClickListener newsItemListener=new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent=new Intent(getActivity(),ShowActivity.class);
            News news= (News) parent.getItemAtPosition(position);
            Bundle bundle=new Bundle();
            bundle.putSerializable("newsitem",news);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    };
    private XListView.IXListViewListener listViewListener=new XListView.IXListViewListener() {
        @Override
        public void onRefresh() {//下拉刷新
            loadNextNews(false);
            listView.stopRefresh();//停止刷新
            listView.setRefreshTime(CommonUtil.getSystime());//放置刷新时间
        }

        @Override
        public void onLoadMore() {//上拉加载
            loadPreNews();
            listView.stopLoadMore();;//停止刷新
            listView.setRefreshTime(CommonUtil.getSystime());//放置刷新时间

        }
    };



}
