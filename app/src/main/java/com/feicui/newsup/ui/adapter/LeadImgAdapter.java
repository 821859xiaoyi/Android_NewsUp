package com.feicui.newsup.ui.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2017/1/3.
 */

public class LeadImgAdapter extends PagerAdapter{
    private List<View> list;//放置四个页面，存储了四个imageview

    public LeadImgAdapter(List<View> list) {
        this.list = list;
    }
    //页面个数
    @Override
    public int getCount() {
        return list.size();
    }
    //判断当前是否显示正确
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }
    //销毁不关联页面
    /**
     *
     * @param container ViewPager
     * @param position 例如：0~3
     * @param object 例如:Imageview
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(list.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(list.get(position));
        return list.get(position);//返回含有图片的ImageView
    }
}
