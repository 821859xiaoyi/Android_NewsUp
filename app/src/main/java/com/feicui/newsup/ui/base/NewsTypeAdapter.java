package com.feicui.newsup.ui.base;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.feicui.newsup.R;
import com.feicui.newsup.model.entity.SubType;

/**
 * Created by Administrator on 2017/1/6.
 */

public class NewsTypeAdapter extends MyBaseAdapter<SubType>{
    private int selectPostion;//根据位置进行相应的颜色变换
    public NewsTypeAdapter(Context context) {
        super(context);
    }

    @Override
    public View getMyView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if (convertView==null){
            convertView=inflater.inflate(R.layout.item_list_typenews,null);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);//Holder对象里存储了布局和控件
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        SubType item=getItem(position);
        holder.tv_type.setText(item.getSubgrunp());
        if (selectPostion==position){
            holder.tv_type.setTextColor(Color.RED);
        }else{
            holder.tv_type.setTextColor(Color.BLACK);
        }
        return convertView;
    }
    public void  setSelectPostion(int postion){
        this.selectPostion=postion;
    }
    class ViewHolder{
        public TextView tv_type;
        public ViewHolder(View view){//view==convertView
            tv_type= (TextView) view.findViewById(R.id.tv_newstype_type);
        }
    }
}
