package com.feicui.newsup.ui;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.feicui.newsup.R;

public class FragMentMenu extends Fragment{
    private View view;//左边侧拉菜单的线性布局
    private RelativeLayout[] rls=new RelativeLayout[5];//线性布局里的五个相对布局

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_menu_left,container,false);
        rls[0]= (RelativeLayout) view.findViewById(R.id.rl_news);
        rls[1]= (RelativeLayout) view.findViewById(R.id.rl_reading);
        rls[2]= (RelativeLayout) view.findViewById(R.id.rl_local);
        rls[3]= (RelativeLayout) view.findViewById(R.id.rl_comment);
        rls[4]= (RelativeLayout) view.findViewById(R.id.rl_photo);
        for (int i=0;i<rls.length;i++){
            rls[i].setOnClickListener(clickListener);
        }
        return view;//返回左边侧拉菜单的线性布局视图
    }
    private View.OnClickListener clickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            for (int i=0;i<rls.length;i++){
                rls[i].setBackgroundColor(0);
            }
            switch (v.getId()){
                case  R.id.rl_news:
                    rls[0].setBackgroundColor(0x33c85555);
                    Toast.makeText(getActivity(), "News", Toast.LENGTH_LONG).show();
                    break;
                case  R.id.rl_reading:
                    rls[1].setBackgroundColor(0x33c85555);
                    Toast.makeText(getActivity(), "Reading", Toast.LENGTH_LONG).show();
                    break;
                case  R.id.rl_local:
                    rls[2].setBackgroundColor(0x33c85555);
                    Toast.makeText(getActivity(), "Local", Toast.LENGTH_LONG).show();
                    break;
                case  R.id.rl_comment:
                    rls[3].setBackgroundColor(0x33c85555);
                    Toast.makeText(getActivity(), "Comment", Toast.LENGTH_LONG).show();
                    break;
                case  R.id.rl_photo:
                    rls[4].setBackgroundColor(0x33c85555);
                    Toast.makeText(getActivity(), "Photo", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };
}
